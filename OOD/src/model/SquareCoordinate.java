package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Square coordinate use offset coordinate in a square reversi game.
 */
public class SquareCoordinate implements ICoordinate {
  private int row;
  private int col;


  /**
   * Creates a new Coordinate with the specified row and column values.
   *
   * @param col The column value.
   * @param row The row value.
   */
  public SquareCoordinate(int col, int row) {
    this.row = row;
    this.col = col;
  }

  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getCol() {
    return col;
  }


  /**
   * Gets the coordinates of the surrounding cells (top-right,
   * top-left, right, left, bottom-right, bottom-left, top, bottom)
   * relative to this Coordinate.
   *
   * @return An ArrayList of Coordinate objects representing the surrounding cells.
   */
  public ArrayList<SquareCoordinate> getSurroundingCells() {
    ArrayList<SquareCoordinate> surroundingCells = new ArrayList<>();
    SquareCoordinate right = new SquareCoordinate(col + 1,row);
    SquareCoordinate left = new SquareCoordinate(col - 1,row);
    SquareCoordinate bottom = new SquareCoordinate(col,row + 1);
    SquareCoordinate top = new SquareCoordinate(col,row - 1);
    SquareCoordinate bottomRight = new SquareCoordinate(col + 1,row + 1);
    SquareCoordinate topRight = new SquareCoordinate(col + 1,row - 1);
    SquareCoordinate bottomLeft = new SquareCoordinate(col - 1,row + 1);
    SquareCoordinate topLeft = new SquareCoordinate(col - 1,row - 1);

    surroundingCells.add(top);
    surroundingCells.add(bottom);
    surroundingCells.add(topRight);
    surroundingCells.add(topLeft);
    surroundingCells.add(right);
    surroundingCells.add(left);
    surroundingCells.add(bottomLeft);
    surroundingCells.add(bottomRight);


    return surroundingCells;
  }

  public String toString() {
    return "Coordinate: Column: " + getCol() + " Row: " + getRow();
  }

  /**
   * Checks if this Coordinate is equal to another object.
   *
   * @param obj The object to compare.
   * @return True if the objects are equal, false otherwise.
   */

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SquareCoordinate coord = (SquareCoordinate) obj;
    return row == coord.row && col == coord.col;
  }


  /**
   * Computes a hash code for this Coordinate based on its row and column values.
   *
   * @return The hash code value.
   */
  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
