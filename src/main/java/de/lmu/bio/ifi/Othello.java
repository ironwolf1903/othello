package de.lmu.bio.ifi;

import de.lmu.bio.ifi.basicpackage.BasicBoard;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.List;

public class Othello implements Game{

    private GameStatus currentStatus = GameStatus.RUNNING;

    private BasicBoard board;

    private boolean playerOneTurn = true;

    private int playerOneScore = 0;

    private int playerTwoScore = 0;



    public void setPlayerOneTurn(boolean playerOneTurn) {
        this.playerOneTurn = playerOneTurn;
    }

    public BasicBoard getBoard() {
        return board;
    }

    public boolean isPlayerOneTurn() {
        return playerOneTurn;
    }

    public Othello() {
       board = new BasicBoard();
    }

    public Othello cloneGame(){
        Othello clonedGame = new Othello();
        clonedGame.setBoard(this.board.cloneBoard());
        clonedGame.setPlayerOneScore(this.getPlayerOneScore());
        clonedGame.setPlayerTwoScore(this.getPlayerTwoScore());
        clonedGame.setCurrentStatus(this.getCurrentStatus());

        clonedGame.setPlayerOneTurn(this.isPlayerOneTurn());
        return clonedGame;
    }

    @Override
    public boolean makeMove(boolean playerOne, int x, int y) {
        if(x >= board.getBoard().length || x < 0 || y >= board.getBoard().length || y < 0){
            return false;
        }
        if(playerOneTurn != playerOne){
            return false;
        }
        if(board.getBoard()[y][x] != 0){
            return false;
        }
        Move currentMove = new Move(x,y);
        List<Move> validMoveList = getPossibleMoves(playerOne);

        boolean isValid = false;
        for(Move move : validMoveList){
            if(move.x == currentMove.x && move.y == currentMove.y){
                isValid = true;
                break;
            }
        }
        if(!isValid){
            return false;
        }
        int assignInt = playerOne ? 1 : 2;
        board.getBoard()[y][x] = assignInt;
        flipStones(new Move(x,y),playerOne);
        gameStatus();
        playerOneTurn = !playerOneTurn;
        return true;
    }

    public void flipStones(Move currentMove, boolean playerOne){
        int neighborInt = playerOne ? 2 : 1;
        int currentPlayer = playerOne ? 1 : 2;
        int x = currentMove.x;
        int y = currentMove.y;
        for (int k = y - 1; k <= y + 1 ; k++) {
            if(k < 0 || k >= board.getBoard().length){
                continue;
            }
            for (int l = x - 1; l <= x + 1; l++) {
                if(l < 0 || l >= board.getBoard().length){
                    continue;
                }
                if(board.getBoard()[k][l] != neighborInt) {
                    continue;
                }
                int difIandK = k - y;
                int difJandL = l - x;
                List<Move> stonesToFlip = new ArrayList<>();
                for (int m = k, z = l ; m < board.getBoard().length && m >= 0 && z < board.getBoard().length && z >= 0; m += difIandK, z += difJandL) {
                    if(board.getBoard()[m][z] == neighborInt){
                        stonesToFlip.add(new Move(z,m));
                    }
                    if(board.getBoard()[m][z] == 0){
                        break;
                    }
                    if(board.getBoard()[m][z] == currentPlayer){
                        for(Move move: stonesToFlip){
                            board.getBoard()[move.y][move.x] = currentPlayer;
                        }
                        break;
                    }
                }
            }
        }
    }

    public GameStatus getCurrentStatus() {
        return currentStatus;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }



    public void setCurrentStatus(GameStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setBoard(BasicBoard board) {
        this.board = board;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }



    @Override
    public GameStatus gameStatus() {

        int p1Score = 0;
        int p2Score = 0;

        // Calculate scores for both players by iterating through the board
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard().length; j++) {
                if (board.getBoard()[i][j] == 1) {
                    p1Score++;
                } else if (board.getBoard()[i][j] == 2) {
                    p2Score++;
                }
            }
        }
        playerOneScore = p1Score;
        playerTwoScore = p2Score;

        boolean thisNoLegal = getPossibleMoves(isPlayerOneTurn()).isEmpty();
        if (thisNoLegal) {
            this.setPlayerOneTurn(!isPlayerOneTurn());
            boolean opNoLegal = getPossibleMoves(isPlayerOneTurn()).isEmpty();
            this.setPlayerOneTurn(!isPlayerOneTurn());
            if (opNoLegal) {
                if (playerOneScore == playerTwoScore) {
                    currentStatus = GameStatus.DRAW;
                    return currentStatus;
                } else {
                    currentStatus = (playerOneScore > playerTwoScore) ? GameStatus.PLAYER_1_WON : GameStatus.PLAYER_2_WON;
                    return currentStatus;
                }
            } else {
                currentStatus = GameStatus.RUNNING;
                return currentStatus;
            }
        } else {
            currentStatus = GameStatus.RUNNING;
            return currentStatus;
        }
         // if (previousNoLegal && thisNoLegal) {
//            if (playerOneScore == playerTwoScore) {
//                currentStatus = GameStatus.DRAW;
//            } else {
//                currentStatus = (playerOneScore > playerTwoScore) ? GameStatus.PLAYER_1_WON : GameStatus.PLAYER_2_WON;
//            }
//
//        } else if (!previousNoLegal && thisNoLegal){
//            previousNoLegal = true;
//            currentStatus = GameStatus.RUNNING;
//        } else if (previousNoLegal && !thisNoLegal) {
//            previousNoLegal = false;
//        }
//        else {
//            currentStatus = GameStatus.RUNNING;
//        }
//        return currentStatus;

    }


    @Override
    public List<Move> getPossibleMoves(boolean playerOne) {
        if(playerOneTurn != playerOne){
            return null;
        }
        int neighborInt = playerOne ? 2 : 1;
        int currentPlayer = playerOne ? 1 : 2;
        List<Move> movesList = new ArrayList<>();
        for (int i = 0; i < board.getBoard().length ; i++) {
            for (int j = 0; j < board.getBoard().length ; j++) {
                if(board.getBoard()[i][j] != 0){
                    continue;
                }
                boolean alreadyIn = false;
                for (int k = i - 1; k <= i + 1 ; k++) {
                    if(k < 0 || k >= board.getBoard().length){
                        continue;
                    }
                    for (int l = j - 1; l <= j + 1; l++) {
                        if(alreadyIn){
                            break;
                        }
                        if(l < 0 || l >= board.getBoard().length){
                            continue;
                        }
                        if(board.getBoard()[k][l] != neighborInt) {
                            continue;
                        }
                        int difIandK = k - i;
                        int difJandL = l - j;
                        for (int m = k, z = l ; m < board.getBoard().length && m >= 0 && z < board.getBoard().length && z >= 0; m += difIandK, z += difJandL) {
                            if(board.getBoard()[m][z] == currentPlayer){
                                Move possibleMove = new Move(j,i);
                                movesList.add(possibleMove);
                                alreadyIn = true;
                                break;
                            }
                            if(board.getBoard()[m][z] == 0){
                                break;
                            }
                        }
                    }
                }
            }
        }
        return movesList;
    }
    @Override
    public String toString() {
        StringBuilder boardAsString = new StringBuilder();
        for(int i=0; i < board.getBoard().length; i++){
            for (int j = 0; j < board.getBoard().length; j++){
                String s = "";
                switch(board.getBoard()[i][j]){
                    case 1 : s = "X"; break;
                    case 2 : s = "O"; break;
                    default: s = "."; break;
                }
                boardAsString.append(s);
                if(j != board.getBoard().length - 1){
                    boardAsString.append(" ");
                } else {
                    if(i != board.getBoard().length - 1) {
                        boardAsString.append("\n");
                    }
                }
            }
        }
        return boardAsString.toString();
    }


}
