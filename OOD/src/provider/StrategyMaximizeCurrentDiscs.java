package provider;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;



import adopt.HexBoard;
import adopt.HexReversi;

/**
 * This strategy aims to maximize the number of the player's own discs on the Reversi game board
 * with each move. It selects the move that results in the most discs being flipped to the player's
 * color.
 */
public class StrategyMaximizeCurrentDiscs extends AbstractStrategy {
  @Override
  public Optional<Coordinates> chooseMove(ReadOnlyReversiModel model, Player player)
          throws IllegalArgumentException {



    Set<Coordinates> possibleMoves = model.calculateMoves(player.getColor());



    if (!possibleMoves.isEmpty()) {
      Set<Coordinates> bestMoves = new HashSet<>();
      int maxScore = 0;
      Optional<Coordinates> bestMove;
      for (Coordinates coords: possibleMoves) {






        HexBoard boardCopy = new HexBoard(model.getCopyOfBoard());




        MutableReversiModel modelSimMove = new HexReversi(boardCopy.getDelegate());
        modelSimMove.startGame();


        if (player.getColor() == PlayerColor.WHITE) {

          modelSimMove.optToPass(PlayerColor.BLACK);


        }
        modelSimMove.makeMove(player.getColor(), coords);
        if (modelSimMove.getScore(player.getColor()) >= maxScore) {
          if (modelSimMove.getScore(player.getColor()) > maxScore) {
            bestMoves.clear();
          }
          bestMoves.add(coords);
          maxScore = modelSimMove.getScore(player.getColor());
        }


      }


      // Pick the uppermost left move if they tie
      bestMove = Optional.ofNullable(findUpperLeftMostCoordinate(bestMoves));

      return bestMove;
    }
    return Optional.empty();






  }



}