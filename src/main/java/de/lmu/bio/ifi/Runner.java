package de.lmu.bio.ifi;

import de.lmu.bio.ifi.players.BMOArtificalPlayerforTest;
import szte.mi.Move;

import java.util.Random;

public class Runner {
    public static void main(String[] args) {
        int numGames = 20;
        int ai1Wins = 0;
        int ai2Wins = 0;
        int draws = 0;

        for (int gameNumber = 1; gameNumber <= numGames; gameNumber++) {
            System.out.println("Starting game " + gameNumber);

            // Initialize Othello game
            Othello game = new Othello();

            // Initialize two AI players
            BMOArtificalPlayerforTest ai1 = new BMOArtificalPlayerforTest();
            BMOArtificalPlayerforTest ai2 = new BMOArtificalPlayerforTest();
            ai1.init(0, 3000, new Random());
            ai2.init(1, 3000, new Random());

            Move lastMove = null;
            boolean ai1Turn = true;

            // Main game loop
            while (game.gameStatus() == GameStatus.RUNNING) {
                game.setPlayerOneTurn(ai1Turn);
                BMOArtificalPlayerforTest currentAI = ai1Turn ? ai1 : ai2;
                Move nextMove = currentAI.nextMove(lastMove, 300000, 3000);

                boolean valid = game.makeMove(ai1Turn, nextMove.x, nextMove.y);
                if(game.gameStatus() != GameStatus.RUNNING) {
                    break;
                }
                if(!valid){
                    break;
                }
                System.out.println(game);
                lastMove = nextMove;
                ai1Turn = !ai1Turn;
            }

            // Determine game outcome
            GameStatus status = game.getCurrentStatus();
            System.out.println("Game " + gameNumber + " ended with status: " + status);
            switch (status) {
                case PLAYER_1_WON : ai1Wins++; break;
                case PLAYER_2_WON : ai2Wins++; break;
                case DRAW : draws++; break;
            }
        }

        // Print final results
        System.out.println("Final Results:");
        System.out.println("AI 1 Wins: " + ai1Wins);
        System.out.println("AI 2 Wins: " + ai2Wins);
        System.out.println("Draws: " + draws);
    }
}
