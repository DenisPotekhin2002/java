:- load_library('alice.tuprolog.lib.DCGLibrary').

bin(op_add, A, B, R) :- R is A + B.
bin(op_subtract, A, B, R) :- R is A - B.
bin(op_multiply, A, B, R) :- R is A * B.
bin(op_divide, A, B, R) :- R is A / B.
un(op_negate, A, R) :- R is 0 - A.
un(op_sinh, A, R) :- E1 is exp(A), A_m is 0 - A, E2 is exp(A_m), E3 is E1 - E2, R is E3 / 2.
un(op_cosh, A, R) :- E1 is exp(A), A_m is 0 - A, E2 is exp(A_m), E3 is E1 + E2, R is E3 / 2.

oper(op_add, '+').
oper(op_subtract, '-').
oper(op_multiply, '*').
oper(op_divide, '/').
oper(op_negate, 'negate').
oper(op_sinh, 'sinh').
oper(op_cosh, 'cosh').

evaluate(const(T), Variables, T) :- number(T), !.
evaluate(variable(V), [(V, R) | T], R) :- !.
evaluate(variable(V), [(V2, R) | T], R) :- atom_chars(V, [V2 | _]).
evaluate(variable(V), [(H_K, H_V) | T], R) :- evaluate(variable(V), T, R).
evaluate(operation(Op, A, B), Variables, R) :- evaluate(A, Variables, AT), evaluate(B, Variables, BT), bin(Op, AT, BT, R).
evaluate(operation(Op, A), Variables, R) :- evaluate(A, Variables, AT), un(Op, AT, R).
infix_str(const(R), T) :- text_term(T, R), number(R), !.
infix_str(variable(R), T) :- text_term(T, R), atom(R), !.
infix_str(operation(Op, A, B), R) :- not(atom(R)), infix_str(A, AT), infix_str(B, BT), oper(Op, Oper), 
	atom_concat('(', AT, Conc1), atom_concat(' ', Oper, Conc2), atom_concat(' ', BT, Conc3), 
	atom_concat(Conc1, Conc2, Conc12), atom_concat(Conc3, ')', Conc34),
	atom_concat(Conc12, Conc34, R), !.
infix_str(operation(Op, A), R) :- not(atom(R)), infix_str(A, AT), oper(Op, Oper), 
	atom_concat(Oper, '(', Conc1), atom_concat(AT, ')',	Conc2),
	atom_concat(Conc1, Conc2, R), !.
skip_ws([]) --> [].
skip_ws([]) --> [' '], skip_ws([]).
op_p(op_add) --> skip_ws([]), ['+'].
op_p(op_subtract) --> skip_ws([]), ['-'].
op_p(op_multiply) --> skip_ws([]), ['*'].
op_p(op_divide) --> skip_ws([]), ['/'].
op_p(op_negate) --> skip_ws([]), ['n'], ['e'], ['g'], ['a'], ['t'], ['e'].
op_p(op_sinh) --> skip_ws([]), ['s'], ['i'], ['n'], ['h'].
op_p(op_cosh) --> skip_ws([]), ['c'], ['o'], ['s'], ['h'].
infix_p(variable(T)) --> skip_ws([]), var_p(Chars), {Chars = [_ | _], atom_chars(T, Chars)}.
var_p([]) --> [].
var_p([H | T]) --> 
	{member(H, ['x', 'y', 'z', 'X', 'Y', 'Z'])},
	[H],
	var_p(T).
infix_p(operation(Op, T)) --> op_p(Op), skip_ws([]), ['('], infix_p(T), skip_ws([]), [')'], skip_ws([]).
infix_p(const(T)) --> 
	skip_ws([]),
	digits_m_p(Chars), 
	{Chars = [_ | _], number_chars(T, Chars)}.
digits_m_p([]) --> [].
digits_m_p(['-' | [H | T]]) --> 
	{member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
	['-'], [H], 
	digits_p(T).
digits_m_p([H | T]) --> 
	{member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
	[H],
	digits_p(T).
digits_p([H | T]) --> 
	{member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
	[H],
	digits_p(T).
digits_p(['.' | T]) --> 
	['.'],
	digits_tail_p(T).
digits_tail_p([H]) --> {member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])}, [H].
digits_tail_p([H | T]) --> 
	{member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
	[H],
	digits_tail_p(T).
infix_p(operation(Op, A, B)) --> skip_ws([]), ['('], infix_p(A), [' '], op_p(Op), [' '], infix_p(B), skip_ws([]), [')'], skip_ws([]).
infix_str(E, S) :- ground(E), phrase(infix_p(E), Cs), atom_chars(S, Cs), !.
infix_str(E, A) :- atom(A), atom_chars(A, Cs), phrase(infix_p(E), Cs), !.