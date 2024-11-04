package de.lmu.bio.ifi;

public class Runner {
    public static void main(String[] args) {

        Othello game1 = new Othello();
        game1.makeMove(true,5,4);
        System.out.println(game1.toString());
        game1.makeMove(false,5,3);
        System.out.println(game1.toString());
        game1.makeMove(true,4,2);
        System.out.println(game1.toString());
        game1.makeMove(false,5,5);
        System.out.println(game1.toString());
        game1.makeMove(true,2,4);
        System.out.println(game1.toString());
        game1.makeMove(false,2,3);
        System.out.println(game1.toString());
        game1.makeMove(true,1,2);
        System.out.println(game1.toString());
    }
}
