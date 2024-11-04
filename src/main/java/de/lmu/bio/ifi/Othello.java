package de.lmu.bio.ifi;

import de.lmu.bio.ifi.basicpackage.BasicBoard;
import szte.mi.Move;

import java.util.ArrayList;
import java.util.List;

public class Othello implements Game{

    private GameStatus currentStatus = GameStatus.RUNNING;
    private BasicBoard board;
    private boolean playerOneTurn = true;

    public Othello() {
       board = new BasicBoard();
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

    @Override
    public GameStatus gameStatus() {
        return currentStatus;
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
