package provider;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a strategy for moving to the outer layer of the Reversi board.
 * Extends AbstractStrategy for common functionality.
 */
public class StrategyMoveToOuterLayer extends AbstractStrategy {

  /**
   * Constructs a StrategyMoveToOuterLayer with the specified board.
   *
   * @param board The Reversi board.
   */
  public StrategyMoveToOuterLayer(Board board) {
    initializeBoard(board);
  }

  @Override
  public Optional<Coordinates> chooseMove(ReadOnlyReversiModel model, Player player)
          throws IllegalArgumentException {
    Set<Coordinates> moves = model.calculateMoves(player.getColor());
    Set<Coordinates> outerMoves = new HashSet<>();
    Optional<Coordinates> bestMove;

    for (Coordinates move : moves) {
      if (getOuterMostLayerCoordinates().contains(move)) {
        outerMoves.add(move);
      }
    }
    if (!outerMoves.isEmpty()) {
      bestMove = Optional.ofNullable(findUpperLeftMostCoordinate(outerMoves));
      return bestMove;
    } else {
      return new StrategyMaximizeCurrentDiscs().chooseMove(model, player);
    }
  }
}
