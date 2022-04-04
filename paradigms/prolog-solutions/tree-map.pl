max(A, B, A) :- A >= B.
max(A, B, B) :- A < B.

getH(null, 0).
getH(node(_, _, H, _, _), H).

getDiff(L, R, Diff) :- 
	getH(L, L_H),
	getH(R, R_H),
	Diff is L_H - R_H.

check_eq_H(L, R, Add) :-
	getH(L, L_H),
	getH(R, R_H),
	R_A_H is R_H + Add,
	L_H is R_A_H.

bal_big(R_L, N_H, N_L_H) :-
	getH(R_L, R_L_H),
	N_H is R_L_H + 2,
	N_L_H is R_L_H + 1.

balance(null, null).
balance(node(X, V, H, L, R), node(X, V, H, L, R)) :- 
	getDiff(L, R, Diff),
	A_D is abs(Diff),
	A_D =< 1.
balance(node(X, V, H, L, node(R_X, R_V, R_H, R_L, R_R)),
		node(R_X, R_V, N_H, node(X, V, N_L_H, L, R_L), R_R)) :- 
	getDiff(L, node(R_X, R_V, R_H, R_L, R_R), Diff),
	Diff < -1,
	check_eq_H(R_R, L, 1),
	bal_big(R_L, N_H, N_L_H).
balance(node(X, V, H, node(L_X, L_V, L_H, L_L, L_R), R), 
		node(L_X, L_V, N_H, L_L, node(X, V, N_R_H, L_R, R))) :- 
	getDiff(node(L_X, L_V, L_H, L_L, L_R), R, Diff),
	Diff > 1,
	check_eq_H(L_L, R, 1),
	bal_big(L_R, N_H, N_R_H).
balance(node(X, V, H, L, node(R_X, R_V, R_H, node(R_L_X, R_L_V, R_L_H, R_L_L, R_L_R), R_R)),
		node(R_L_X, R_L_V, R_H, node(X, V, R_L_H, L, R_L_L), node(R_X, R_V, R_L_H, R_L_R, R_R))) :- 
	getDiff(L, node(R_X, R_V, R_H, node(R_L_X, R_L_V, R_L_H, R_L_L, R_L_R), R_R), Diff),
	Diff < -1,
	check_eq_H(R_R, L, 0).
balance(node(X, V, H, node(L_X, L_V, L_H, L_L, node(L_R_X, L_R_V, L_R_H, L_R_L, L_R_R)), R),
		node(L_R_X, L_R_V, L_H, node(L_X, L_V, L_R_H, L_L, L_R_L), node(X, V, L_R_H, L_R_R, R))) :- 
	getDiff(node(L_X, L_V, L_H, L_L, node(L_R_X, L_R_V, L_R_H, L_R_L, L_R_R)), R, Diff),
	Diff > 1,
	check_eq_H(L_L, R, 0).
	
renew_H_bal((X, Val, Left_Ch, Right_Ch), Node) :-
	getH(Left_Ch, L_H),
	getH(Right_Ch, R_H),
	max(L_H, R_H, Max_H),
	New_H is Max_H + 1,
	balance(node(X, Val, New_H, Left_Ch, Right_Ch), Node).
	
map_insert(null, (X, Val), node(X, Val, 1, null, null)).
map_insert(node(X, Val, H, Left_Ch, Right_Ch), (New_X, New_Val),
    Node) :-
    New_X < X, 
	map_insert(Left_Ch, (New_X, New_Val), New_Left_Ch),
	renew_H_bal((X, Val, New_Left_Ch, Right_Ch), Node).
map_insert(node(X, Val, H, Left_Ch, Right_Ch), (New_X, New_Val),
    Node) :-
    New_X >= X,
	map_insert(Right_Ch, (New_X, New_Val), New_Right_Ch),
	renew_H_bal((X, Val, Left_Ch, New_Right_Ch), Node).
	
left(node(X, Val, H, null, Right_Ch), X, Val, Right_Ch) :- !.
left(node(X, Val, H, Left_Ch, Right_Ch), Left_X, Left_Val,
    Node) :-
	left(Left_Ch, Left_X, Left_Val, New_Left_Ch),
	renew_H_bal((X, Val, New_Left_Ch, Right_Ch), Node).

map_remove(null, _, null).
map_remove(node(X, _, _, Left_Ch, null), X, Left_Ch).
map_remove(node(X, _, H, Left_Ch, Right_Ch), X, Node) :-
	left(Right_Ch, New_X, New_Val, New_Right_Ch),
	renew_H_bal((New_X, New_Val, Left_Ch, New_Right_Ch), Node).
map_remove(node(Node_X, Node_Val, Node_H, Left_Ch, Right_Ch), X, Node) :-
    X < Node_X,
    map_remove(Left_Ch, X, Res_Tree),
	renew_H_bal((Node_X, Node_Val, Res_Tree, Right_Ch), Node).
map_remove(node(Node_X, Node_Val, Node_H, Left_Ch, Right_Ch), X, Node) :-
    X > Node_X,
    map_remove(Right_Ch, X, Res_Tree),
	renew_H_bal((Node_X, Node_Val, Left_Ch, Res_Tree), Node).
	
map_get(node(X, Val, H, Left_Ch, Right_Ch), X, Val) :- !.
map_get(node(Node_X, Node_Val, Node_H, Left_Ch, Right_Ch), X, Res) :-
    Node_X < X,
    map_get(Right_Ch, X, Res).
map_get(node(Node_X, Node_Val, Node_H, Left_Ch, Right_Ch), X, Res) :-
    Node_X > X,
    map_get(Left_Ch, X, Res).
	
map_put(TreeMap, X, Val, Res) :-
    map_get(TreeMap, X, _),
    map_remove(TreeMap, X, NewTree),
    map_insert(NewTree, (X, Val), Res),
    !.
map_put(TreeMap, X, Val, Res) :-
    map_insert(TreeMap, (X, Val), Res).

map_build([], null) :- !.
map_build([(X, Val) | T], TreeMap) :-
    map_build(T, New_T),
    map_put(New_T, X, Val, TreeMap).

map_getLast(node(X, Val, H, Left_Ch, null), (X, Val)) :- !.
map_getLast(node(X, Val, H, Left_Ch, Right_Ch), (Key, Value)) :-
	map_getLast(Right_Ch, (Key, Value)), !.
	
map_removeLast(null, null) :- !.
map_removeLast(node(X, Val, H, Left_Ch, null), Left_Ch) :- !.	
map_removeLast(node(X, Val, H, Left_Ch, Right_Ch), Node) :-
	map_removeLast(Right_Ch, New_Right_Ch), 
	renew_H_bal((X, Val, Left_Ch, New_Right_Ch), Node),
	!.