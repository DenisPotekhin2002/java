(defn assert_tensor [dim a]
	(or (and (== 0 dim) (number? a)) (and (vector? a) (every? #(assert_tensor (dec dim) %) a))))

(defn assert_vector [a] (assert_tensor 1 a))

(defn assert_matrix [a] (assert_tensor 2 a))

(defn check_tens [a b]
	(or (and (number? a) (number? b)) (and (vector? a) (vector? b) (== (count a) (count b)) (every? true? (mapv check_tens a b)))))

(defn tens_oper [op tens]
	{:pre [(every? true? (mapv #(check_tens (first tens) %) (rest tens)))]
	:post [(check_tens (first tens) %)]}
	(cond 
		(empty? tens) []
		(every? number? tens) (apply op tens)
		(== (count tens) 1) (if (every? number? (first tens))
			(mapv op (first tens))
			(mapv #(tens_oper op [%]) (first tens))
		)
		:else (reduce (fn [t1 t2] (mapv #(tens_oper op [%1 %2]) t1 t2)) tens)))

(defn scal_tens_oper [op a mult]
	{:pre [(number? mult)]
	:post [(check_tens a %)]}
	(if (number? a)
		(op a mult)
		(mapv #(scal_tens_oper op % mult) a)))

(defn t_abs [op dim] (fn [& list]
	{:pre [(not (empty? list)) (assert_tensor dim (first list))]
	:post [(check_tens (first list) %)]}
	(tens_oper op list)))

(defn scal_t_abs [op dim] (fn [a & list]
	{:pre [(assert_tensor dim a)]
	:post [(check_tens a %)]}
	(scal_tens_oper op a (reduce op list))))
	
(def v+ (t_abs + 1))

(def v- (t_abs - 1))

(def v* (t_abs * 1))

(def vd (t_abs / 1))

(def v*s (scal_t_abs * 1))

(defn scalar ([& list] (apply + (reduce v* list))))

(defn vec_mult [a b]
	{:pre [(assert_vector a) (assert_vector b) (== (count a) (count b))]
	:post [(assert_vector %) (== (count a) (count %))]}
			;; :NOTE: проще написать
	(let [ax (first a), ay (first (rest a)), az (first (rest (rest a))), bx (first b), by (first (rest b)),	bz (first (rest (rest b)))]
		[(- (* ay bz) (* az by)), (- (* az bx) (* ax bz)), (- (* ax by) (* ay bx))]))

(defn vect [& list]	(reduce vec_mult list))

(def m+ (t_abs + 2))

(def m- (t_abs - 2))

(def m* (t_abs * 2))

(def md (t_abs / 2))

(def m*s (scal_t_abs * 2))

(defn m*v [mat & vecs]
	{:pre [(assert_matrix mat) (every? assert_vector vecs)]
	:post [(assert_vector %) (== (count mat) (count %))]}
	(mapv #(apply scalar % vecs) mat))

(defn transpose [m]
	{:pre [(assert_matrix m)]
	:post [(assert_matrix %) (or (== (count m) 0) (== (count (first %)) 0) (== (count m) (count (first %))))
		(or (== (count %) 0) (== (count (first m)) 0) (== (count %) (count (first m))))]}
	(apply mapv vector m))

(defn m*m [& args] (reduce (fn [mat1 mat2] (mapv #(m*v (transpose mat2) %) mat1)) args))

(defn check_simpl [a]
	(and (vector? a) (or (every? number? a)
		(every? true? (for [n (range 0 (count a))] (and (check_simpl (nth a n)) (== (count (nth a n)) (- (count a) n))))))))

(defn x_abs [op] (fn [& list]
	{:pre [(not (empty? list))(check_simpl (first list))]
	:post [(check_tens (first list) %)]}
	(tens_oper op list)))

(def x+ (x_abs +))

(def x- (x_abs -))

(def x* (x_abs *))

(def xd (x_abs /))