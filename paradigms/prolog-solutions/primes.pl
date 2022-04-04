divides(X, Y) :-
   0 is mod(X, Y).

%comp(X, Y) :- Y < X, divides(X, Y), !.
%comp(X, Y) :-
	Y < X, 
	Y1 is Y + 1,
	comp(X, Y1).

composite(1).
%composite(X) :- comp(X, 2).
prime(X) :- not(composite(X)).

create_comps_from(Base, Lim, Add) :-
	Base =< Lim,
	assert(composite(Base)),
	Base1 is Base + Add,
	create_comps_from(Base1, Lim, Add).

create_comps(L, R) :-
	L < R,
	prime(L),
	L_sq is L * L,
	L_sq < R,
	create_comps_from(L_sq, R, L).
	
create_comps(L, R) :-
	L < R,
	L1 is L + 1,
	create_comps(L1, R).

init(MAXN) :- create_comps(2, MAXN).

is_first_d(1, D, []) :- !.

is_first_d(N, D, [N]) :- D * D > N, !.

is_first_d(N, D, [H | T]) :-
		D =< N,
		divides(N, D),
		H is D,
		N1 is div(N, D),
		is_first_d(N1, 2, T).
		
is_first_d(N, D, [H | T]) :-
		D =< N,
		not(divides(N, D)),
		D1 is D + 1,
		is_first_d(N, D1, [H | T]).
		
multiply(N, N, []).
multiply(N, R, [H]) :- prime(H), N is R * H.
multiply(N, R, [H | [HT | T]]) :- R1 is R * H, prime(H), H =< HT, multiply(N, R1, [HT | T]).

prime_divisors(N, Divisors) :- integer(N), is_first_d(N, 2, Divisors).
prime_divisors(N, Divisors) :- not(integer(N)), multiply(N, 1, Divisors).

greatest([], _, A, B) :- B is A, !.

greatest(_, [], A, B) :- B is A, !.

greatest([H1 | T1], [H2 | T2], G_T, GCD) :-
	H1 is H2,
	G2_T is G_T * H1,
	greatest(T1, T2, G2_T, GCD).

greatest([H1 | T1], [H2 | T2], G_T, GCD) :-
	H1 < H2,
	greatest(T1, [H2 | T2], G_T, GCD).

greatest([H1 | T1], [H2 | T2], G_T, GCD) :-
	H1 > H2,
	greatest([H1 | T1], T2, G_T, GCD).

gcd(A, B, GCD) :- 
	prime_divisors(A, A_d),
	prime_divisors(B, B_d),
	greatest(A_d, B_d, 1, GCD).