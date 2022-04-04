(def constant constantly)

(defn variable [x] (fn [a] (a x)))

(defn operation [op] (fn [& args] (fn [a] (apply op (mapv #(% a) args)))))

(def add (operation +))

(def sum add)

(def avg (operation #(/ (apply + %&) (count %&))))

(def subtract (operation -))

(def multiply (operation *))

(defn divRule 
	([arg] (/ 1 (double arg)))
	([arg & rest] (/ (double arg) (apply * rest))))

(def divide (operation divRule))

;; (def negate subtract)
(defn negate [arg] (subtract arg))

(def opers {'+ add '- subtract '* multiply '/ divide 'negate negate 'sum sum 'avg avg})

(defn parse_expr [constant variable operations expr]
  (cond
    (number? expr) (constant (double expr))
    (symbol? expr) (variable (str expr))
	(seq? expr) (apply (operations (first expr))
                             (mapv (partial parse_expr constant variable operations) (rest expr)))
	:else (throw (Exception. "wrong operation"))))
	
(defn parse_abs [constant variable operations]
  (fn [expr] (parse_expr constant variable operations (read-string expr))))

(def parseFunction (parse_abs constant variable opers))


; hw 10

(load-file "proto.clj")
(load-file "parser.clj")

(defn constructor_with_fields [proto & fields]
	(constructor
		(fn [this & vars] (reduce (fn [this [field var]] (assoc this field var)) this (mapv vector fields vars)))
        proto))

(def evaluate (method :evaluate))
(def toString (method :toString))
(def toStringInfix (method :toStringInfix))
(def diff (method :diff))

(declare ZERO)
(declare ONE)

(def Constant-prototype
	(let [value (field :const)]
    {:evaluate         (fn [this _] (value this))
     :toString         (fn [this] (format "%.1f" (value this)))
     :toStringInfix    (fn [this] (format "%.1f" (value this)))
     :diff             (fn [_ _] ZERO)}))

(def Constant (constructor_with_fields Constant-prototype :const))

(def ZERO (Constant 0.0))
(def ONE (Constant 1.0))

(defn first_low [a] (clojure.string/lower-case (str (get a 0))))

(def Variable-prototype
  (let [_name (field :name)]
    {:evaluate (fn [this vars] (vars (first_low (_name this))))
     :toString (fn [this] (_name this))
     :toStringInfix (fn [this] (_name this))
     :diff (fn [this name]
             (if (= (first_low (_name this)) name)
               ONE
               ZERO))}))

(def Variable (constructor_with_fields Variable-prototype :name))

(def Abs_Op-prototype
  (let [exprs (field :exprs) f (field :f) differ (field :differ) oper (field :oper)]
    {:evaluate (fn [this vars] (apply (f this) (mapv #(evaluate % vars) (exprs this))))
     :toString (fn [this] (str "(" (oper this) " " (clojure.string/join " " (mapv toString (exprs this))) ")"))
     :toStringInfix (fn [this] 
	 (if (> (count (exprs this)) 1)
		(str "(" (clojure.string/join (str " " (oper this) " ") (mapv toStringInfix (exprs this))) ")")
		(str (oper this) "(" (toStringInfix (first (exprs this))) ")")))
     :diff (fn [this a] ((differ this) (exprs this) (mapv #(diff % a) (exprs this))))}))
	 
(defn create_op [f oper differ]
	(constructor
		(fn [this & args] (assoc this :exprs args))
		((constructor_with_fields Abs_Op-prototype :f :oper :differ) f oper differ)))

(def Add (create_op + '+ (fn [_ diffs] (apply Add diffs))))

(def Subtract (create_op - '- (fn [_ diffs] (apply Subtract diffs))))

(def Negate (create_op - 'negate (fn [_ diffs] (Negate (first diffs)))))

(declare Multiply)

(defn mult-diff [funcs diffs]
	(if (== 1 (count diffs))
		(first diffs)
		(Add (Multiply (first funcs) (mult-diff (rest funcs) (rest diffs))) (Multiply (first diffs) (apply Multiply (rest funcs))))))

(def Multiply (create_op * '* mult-diff))

(declare Divide)

(defn div-diff [funcs diffs]
	(if (== 1 (count diffs))
		(Negate (Divide (first diffs) (first funcs) (first funcs)))
		(let [mult (apply Multiply (rest funcs))
					dmult (mult-diff (rest funcs) (rest diffs))]
			(Divide (Subtract (Multiply (first diffs) mult) (Multiply (first funcs) dmult))
				(Multiply mult mult)))))
		
(def Divide (create_op divRule '/ div-diff))

(def Sum (create_op + 'sum (fn [_ diffs] (apply Add diffs))))

(def Avg (create_op #(/ (apply + %&) (count %&)) 'avg 
	(fn [funcs diffs] (Divide (apply Add diffs)
                                (Constant (double (count funcs)))))))

(def IPow (create_op #(Math/pow %1 %2) '** nil))

(def ILog (create_op #(/ (Math/log (Math/abs %2)) (Math/log (Math/abs %1))) (quote "//") nil))

(def obj_opers {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'sum Sum 'avg Avg})
(def parse_opers {"+" Add "-" Subtract "*" Multiply "/" Divide "negate" Negate "**" IPow "//" ILog})

(def parseObject (parse_abs Constant Variable obj_opers))

; :NOTE:
; test:     ( 1 +
;
;
;						2)
(def skip_ws (+ignore (+star (+or (+char " \n\t") (+char "	") (+char "
")))))

(def parse_letters (+str (+plus (+char (apply str (filter #(Character/isLetter %) (mapv char (range 32 128))))))))
(def parse_digits (+str (+plus (+char "1234567890"))))
; :NOTE: +map избыточный, когда и так есть +seqf, который применяет функцию
(def parse_const
	(+seqf (fn [& a] (Constant (Double/parseDouble (reduce str a)))) (+opt (+char "-")) parse_digits (+opt (+seqf str (+char ".") parse_digits))))
(def parse_var (+map #(Variable (str %)) parse_letters))
(declare parse_gen)
(declare parse_unary)
(defn parse_atom [a] ((+seqn 0 skip_ws (+or parse_unary parse_var parse_const (+seqn 1 (+char "(") parse_gen skip_ws (+char ")")))) a))
; :NOTE: тяжело добавлять новые ключевые слова
(defn parse_word [w] (fn [a] ((+seqn 0 skip_ws (apply +seqf str (mapv #(+char (vector %)) (seq w)))) a)))
(def parse_neg (parse_word "negate"))
(def parse_unary (+seqf #((parse_opers %1) %2) parse_neg parse_atom))
(defn parse_op [& ops] (fn [a] ((apply +or (mapv parse_word ops)) a)))
(def parse_weak_op (parse_op "+" "-"))
(def parse_strong_op (parse_op "*" "/"))
(def parse_right_op (parse_op "**" "//"))
; :NOTE: тяжело добавлять операции с отличным от имеющихся приоритетом
(defn parse_right [a] ((+or (+seqf #((parse_opers (str %2)) %1 %3) parse_atom parse_right_op parse_right) parse_atom) a))
(def priorities [parse_strong_op parse_weak_op])
(defn parse_priority [parse_next_p parse_p_op] (fn [a] ((+seqf #(reduce (fn [a [b c]] ((parse_opers (str b)) a c)) %1 %2) 
	parse_next_p (+star (+seq parse_p_op parse_next_p))) a)))
(def parse_gen (reduce #(parse_priority %1 %2) parse_right priorities))
(def parseObjectInfix #(:value (parse_gen %)))
; (println (toString (parseObjectInfix "( 1 +	
;			
;			2)")))