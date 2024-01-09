package strategy;

import model.CellType;
import model.Coordinate;
import model.ReadonlyReversiModel;

/**
 * The ReversiStrategy interface defines the contract for implementing different strategies
 * that determine the next move in a Reversi game.
 */
public interface ReversiStrategy {

  /**
   * Chooses the coordinate for the next move based on the strategy's evaluation.
   *
   * @param model  The Reversi game model providing the current state of the game.
   * @param player The current player making the move.
   * @return The chosen coordinate for the next move.
   */
  Coordinate chooseCoordinate(ReadonlyReversiModel model, CellType player);
}
