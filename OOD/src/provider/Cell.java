package provider;

/**
 * Represents a singular cell of a tiled board game.
 */
public interface Cell {

  /**
   * Gets the current state of the cell (e.g., disc color).
   *
   * @return The state of the cell.
   */
  DiscState getDiscState();

  /**
   * Sets the state of the cell (e.g., disc color).
   *
   * @param state The new state to set.
   */
  void setDiscState(DiscState state);

  /**
   * Flips the state of the cell, changing it to the opposite state.
   */
  void flipDiscState();
}
