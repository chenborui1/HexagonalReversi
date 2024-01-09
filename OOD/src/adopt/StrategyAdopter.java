package adopt;

import java.util.Optional;

import model.CellType;
import model.Coordinate;
import model.ReadonlyReversiModel;
import provider.Coordinates;
import provider.ReversiStrategy;

/**
 * StrategyAdopter is an adapter class that implements the ReversiStrategy interface. It adapts a
 * ReversiStrategy implementation to work with the specific requirements of a Reversi game,
 * facilitating the interaction between the game model and strategy logic. This class bridges
 * the gap between different interfaces used in the game's model and strategy layers.
 */
public class StrategyAdopter implements strategy.ReversiStrategy {

  ReversiStrategy delegate;

  /**
   * Constructs a StrategyAdopter with a specific implementation of the ReversiStrategy.
   *
   * @param strategy The ReversiStrategy that this adapter will delegate to.
   */
  public StrategyAdopter(ReversiStrategy strategy) {

    this.delegate = strategy;
  }

  /**
   * Chooses a coordinate for a move based on the strategy implemented by the delegate.
   * This method adapts the ReversiStrategy's chooseMove method to the format expected by the
   * Reversi game logic.
   *
   * @param model The ReadonlyReversiModel representing the current state of the game.
   * @param player The CellType representing the current player.
   * @return A Coordinate object representing the chosen move.
   */
  @Override
  public Coordinate chooseCoordinate(ReadonlyReversiModel model, CellType player) {

    ModelAdopter model1 = new ModelAdopter(model);




    Optional<Coordinates> coordinates = delegate.chooseMove(model1, new PlayerAdopter(player));


    int row = coordinates.get().getFirstCoordinate();
    int col = coordinates.get().getSecondCoordinate();






    return new Coordinate(col, row);
  }
}
