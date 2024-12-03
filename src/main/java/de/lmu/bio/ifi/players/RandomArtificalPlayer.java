package de.lmu.bio.ifi.players;

import de.lmu.bio.ifi.Othello;
import szte.mi.Move;
import szte.mi.Player;

import java.util.List;
import java.util.Random;

public class RandomArtificalPlayer implements Player {
    private Othello playerOthello;
    private Random random;
    private boolean beginFirst;

    @Override
    public void init(int order, long t, Random rnd) {
        playerOthello = new Othello();
        random = rnd;
        beginFirst = order == 0;
    }

    @Override
    public Move nextMove(Move prevMove, long tOpponent, long t) {
        //the first Move or no possible move for opponent
        if(prevMove != null){
            playerOthello.makeMove(playerOthello.isPlayerOneTurn(), prevMove.x, prevMove.y);
        }
        List<Move> possibleMoves = playerOthello.getPossibleMoves(playerOthello.isPlayerOneTurn());



        if(!possibleMoves.isEmpty()){
            Move currentMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
            playerOthello.makeMove(playerOthello.isPlayerOneTurn(),currentMove.x,currentMove.y);
            return currentMove;
        }
        playerOthello.setPlayerOneTurn(!playerOthello.isPlayerOneTurn());
        return null;
    }
}
