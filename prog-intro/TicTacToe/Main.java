package TicTacToe;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to play on a rombic board? Write 1 if yes and 0 otherwise");
        int romb = sc.nextInt();
        while (romb >1 || romb < 0){
            System.out.println("wrong number");
            romb = sc.nextInt();
        }
        int m;
        int n;
        int k;
        if (romb == 0) {
            System.out.println("Enter m, n and k");
            m = sc.nextInt();
            n = sc.nextInt();
            k = sc.nextInt();
            while (m * n * k == 0) {
                System.out.println("Bounds must be positive, try again");
                m = sc.nextInt();
                n = sc.nextInt();
                k = sc.nextInt();
            }
        }
        else {
            System.out.println("Enter m and k");
            m = sc.nextInt() * 2 - 1;
            n = m;
            k = sc.nextInt();
            while (m * n * k == 0) {
                System.out.println("Bounds must be positive, try again");
                m = sc.nextInt() * 2 - 1;
                n = m;
                k = sc.nextInt();
            }
        }
        System.out.println("Enter number of players");
        int num = sc.nextInt();
        while (num < 2 || num > 4){
            System.out.println("wrong number");
            num = sc.nextInt();
        }
        ArrayList<Player> list = new ArrayList<>();
        System.out.println("Enter " + num + " numbers (move order). For RandomPlayer write 0, for SequentialPlayer 1, for HumanPlayer 2");
        for (int i = 0; i < num; i++){
            int nextPlayer = sc.nextInt();
            while (nextPlayer < 0 || nextPlayer > 2){
                System.out.println("wrong number " + (i + 1) + ", enter last " + (num - 1) + " numbers again");
                nextPlayer = sc.nextInt();
            }
            if (nextPlayer == 0){
                list.add(new RandomPlayer());
            }
            if (nextPlayer == 1){
                list.add(new SequentialPlayer());
            }
            if (nextPlayer == 2){
                list.add(new HumanPlayer());
            }
        }
        final Game game = new Game(romb, num, m, n, k, true, list);
        int result;
        do {
            result = game.play(new TicTacToeBoard(romb, num, m, n, k));
            System.out.println("Game result: " + result);
        } while (result < 0);
    }
}
