package provider;

import java.util.List;

/**
 * Represents the game board of a tiled board game.
 * The board consists of cells, each containing a disc state.
 */
public interface Board {

  /**
   * Retrieves the cell at the specified coordinates on the board.
   *
   * @param coordinates The coordinates of the cell.
   * @return The cell at the given coordinates.
   */
  Cell getCellAt(Coordinates coordinates);

  /**
   * Sets the cell at the specified coordinates to the given disc state.
   *
   * @param coordinates The coordinates of the cell to set.
   * @param state       The new disc state to set.
   */
  void setCellAt(Coordinates coordinates, DiscState state);

  /**
   * Flips the disc state of the cell at the specified coordinates.
   *
   * @param coordinates The coordinates of the cell to flip.
   */
  void flipCellAt(Coordinates coordinates);

  /**
   * Gets the side length of the board.
   *
   * @return The side length of the board.
   */
  int getBoardSideLength();

  /**
   * Gets the total number of cells on the board.
   *
   * @return The size of the board.
   */
  int getBoardSize();

  /**
   * Retrieves a list of coordinates for all in-bounds cells on the board.
   *
   * @return A list of coordinates within the bounds of the board.
   */
  List<Coordinates> getInBoundsCoordinates();
}
