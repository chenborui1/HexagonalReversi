package provider;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * This strategy aims to choose a move that moves the player's piece closer to the corners of
 * the Reversi game board. The corners are considered valuable positions, and the strategy
 * seeks to maximize the player's presence near these areas.
 */
public class StrategyMoveToCorner extends AbstractStrategy {
  public StrategyMoveToCorner(Board board) {
    initializeBoard(board);
  }

  @Override
  public Optional<Coordinates> chooseMove(ReadOnlyReversiModel model, Player player)
          throws IllegalArgumentException {
    Set<Coordinates> moves = model.calculateMoves(player.getColor());
    Set<Coordinates> outerMoves = new HashSet<>();
    Optional<Coordinates> bestMove;

    for (Coordinates move : moves) {
      if (getCornerCoordinates().contains(move)) {
        outerMoves.add(move);
      }
    }
    if (!outerMoves.isEmpty()) {
      bestMove = Optional.ofNullable(findUpperLeftMostCoordinate(outerMoves));
      return bestMove;
    } else {
      return new StrategyMoveToOuterLayer(model.getCopyOfBoard()).chooseMove(model, player);
    }
  }
}
