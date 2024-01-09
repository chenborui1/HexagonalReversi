package adopt;

import java.util.Optional;

import model.CellType;
import provider.Features;
import provider.Player;
import provider.Coordinates;
import provider.PlayerColor;


/**
 * PlayerAdopter is an adapter class that implements the Player interface, adapting a CellType
 * to behave as a Player. This allows CellType, which typically represents a cell state in a board
 * game, to be used in contexts where a Player object is required, such as in game logic or
 * user interfaces.
 */
public class PlayerAdopter implements Player {

  CellType delegate;

  /**
   * Constructs a PlayerAdopter object with a specified CellType.
   *
   * @param player The CellType that this adapter represents as a player.
   */
  public PlayerAdopter(CellType player) {

    this.delegate = player;
  }

  @Override
  public String getName() {
    return null;
  }

  /**
   * Retrieves the color of the player. This method translates the CellType of the delegate
   * into a corresponding PlayerColor. If the delegate represents CellType.BLACK, it returns
   * PlayerColor.BLACK; otherwise, it returns PlayerColor.WHITE.
   *
   * @return PlayerColor.BLACK if the delegate is CellType.BLACK, otherwise PlayerColor.WHITE.
   */
  @Override
  public PlayerColor getColor() {

    if (delegate == CellType.BLACK) {
      return PlayerColor.BLACK;
    }
    else {
      return PlayerColor.WHITE;
    }


  }

  @Override
  public Player opponent() {
    return null;
  }

  @Override
  public void performMove(Optional<Coordinates> selectedCoords) {
    int a = 1;
  }

  @Override
  public void pass() {
    int a = 1;
  }

  @Override
  public void addFeaturesListener(Features listener) {
    int a = 1;
  }

  @Override
  public boolean isAI() {
    return false;
  }
}
