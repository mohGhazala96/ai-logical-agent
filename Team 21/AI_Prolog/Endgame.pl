% includes the knowledge base, please change the file to KB2.pl if you want to access the second knowledge base 
:-include('KB1.pl').

% this predicate checks if ironman is within the grid and doesn't make invalid movement
inside(X, Y):-
    grid(L, Z), X<L, Y<Z, X>=0, Y>=0.

% this axiom moves ironman and only enters if the list of stones is empty which means that no stones are yet collected
ironman((X, Y), [], result(A, S)):-inside(X, Y),(
    ((Z is X-1, ironman((Z, Y), [], S), A='down')); % Z is X-1 changes the ironman's coordinate to down
    ((Z is X+1, ironman((Z, Y), [], S), A='up')); % Z is X+1 changes the ironman's coordinate to up
    ((Z is Y-1, ironman((X, Z), [], S), A='right')); % Z is Y-1 changes the ironman's coordinate to right
    ((Z is Y+1, ironman((X, Z), [], S), A='left'))). % Z is Y+1 changes the ironman's coordinate to left

% this axiom moves ironman and collects a stone once ironman is in its place then it passes the list (Tail) of the collected stones
% (Sx, Sy) are the current coordinates of the current stone
ironman((X, Y), [(Sx, Sy)|T], result(A, S)):-inside(X, Y),(
    (X = Sx, Y = Sy, A = 'collect', ironman((X, Y), T, S)); % X = Sx, Y = Sy checks if ironman is in the same coordinate as the stone
    ((Z is X-1, ironman((Z, Y), [(Sx, Sy)|T], S), A='down')); % Z is X-1 changes the ironman's coordinate to down
    ((Z is X+1, ironman((Z, Y), [(Sx, Sy)|T], S), A='up')); % Z is X+1 changes the ironman's coordinate to up
    ((Z is Y-1, ironman((X, Z), [(Sx, Sy)|T], S), A='right')); % Z is Y-1 changes the ironman's coordinate to right
    ((Z is Y+1, ironman((X, Z), [(Sx, Sy)|T], S), A='left'))). % Z is Y+1 changes the ironman's coordinate to left

% this axiom checks if iromman collects all stones and goes to the same position as thanos
won(result(A,S)):-
    thanos(X,Y),
    stonesF(ST), % returns the list of stones which is then passed to the following predicate
    ironman((X, Y), ST, S),
    A ='snap'.

snapped(S):-
    snapped(S, 1).
snapped(S, X):-
    (call_with_depth_limit(won(S), X, W), W \= 'depth_limit_exceeded'); (Z is X+1, snapped(S, Z)). %iterative deeeping for finding the final solution
