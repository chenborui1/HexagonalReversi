package adopt;

import java.util.HashMap;
import java.util.List;

import model.CellType;
import model.ICoordinate;
import provider.Board;
import provider.Cell;
import provider.Coordinates;
import provider.DiscState;

/**
 * Represents a hexagonal board for a board game. This class provides functionality to manipulate
 * and access the state of the board. It operates with a delegate HashMap that maps coordinates
 * to cell types, enabling the management of the game's grid.
 */
public class HexBoard implements Board {

  HexBoard board;
  HashMap<ICoordinate, CellType> delegate;

  /**
   * Constructs a HexBoard with a reference to another HexBoard.
   * This constructor is used for creating a new board that shares
   * the state with another board.
   *
   * @param board The HexBoard to be referenced.
   */
  public HexBoard(HexBoard board) {
    this.board = board;
    this.delegate = board.getDelegate();
  }

  /**
   * Constructs a HexBoard with a specified delegate.
   * This constructor initializes the board with a given delegate HashMap.
   *
   * @param delegate The HashMap mapping coordinates to cell types.
   */
  public HexBoard(HashMap<ICoordinate, CellType> delegate) {
    this.delegate = delegate;
  }

  /**
   * Retrieves the cell at the specified coordinates.
   *
   * @param coordinates The coordinates of the cell to retrieve.
   * @return The Cell at the specified coordinates. Currently returns null.
   */
  @Override
  public Cell getCellAt(Coordinates coordinates) {
    return null;
  }

  /**
   * Sets the state of a cell at specified coordinates.
   *
   * @param coordinates The coordinates of the cell to modify.
   * @param state The new state of the cell.
   */
  @Override
  public void setCellAt(Coordinates coordinates, DiscState state) {
    int a = 1;
  }

  /**
   * Flips the state of a cell at specified coordinates.
   *
   * @param coordinates The coordinates of the cell to flip.
   */
  @Override
  public void flipCellAt(Coordinates coordinates) {
    int a = 1;
  }

  /**
   * Returns the side length of the board.
   * Currently, this method returns 0 and needs implementation.
   *
   * @return The side length of the board.
   */
  @Override
  public int getBoardSideLength() {
    return 0;
  }

  /**
   * Returns the total size of the board.
   * Currently, this method returns 0 and needs implementation.
   *
   * @return The total size of the board.
   */
  @Override
  public int getBoardSize() {
    return 0;
  }

  /**
   * Retrieves a list of coordinates that are within the bounds of the board.
   * Currently, this method returns null and needs implementation.
   *
   * @return A List of Coordinates within the bounds of the board.
   */
  @Override
  public List<Coordinates> getInBoundsCoordinates() {
    return null;
  }

  /**
   * Accesses the delegate HashMap used by this HexBoard.
   *
   * @return The delegate HashMap mapping coordinates to cell types.
   */
  public HashMap<ICoordinate, CellType> getDelegate() {
    return delegate;
  }

}
