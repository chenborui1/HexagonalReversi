package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The Coordinate class represents a two-dimensional coordinate with row and column values.
 * It is used to represent positions on a game board.
 */
public class Coordinate implements ICoordinate {
  private int row;
  private int col;


  /**
   * Creates a new Coordinate with the specified row and column values.
   *
   * @param col The column value.
   * @param row The row value.
   */
  public Coordinate(int col, int row) {
    this.row = row;
    this.col = col;
  }

  /**
   * Gets the row value of this Coordinate.
   *
   * @return The row value.
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the column value of this Coordinate.
   *
   * @return The column value.
   */
  public int getCol() {
    return col;
  }


  //Gets the coordinates of the surrounding cells

  /**
   * Gets the coordinates of the surrounding cells (top-right,
   * top-left, right, left, bottom-right, bottom-left)
   * relative to this Coordinate.
   *
   * @return An ArrayList of Coordinate objects representing the surrounding cells.
   */


  public ArrayList<Coordinate> getSurroundingCells() {

    ArrayList<Coordinate> surroundingCells = new ArrayList<>();
    Coordinate topRight = new Coordinate(col + 1, row - 1);
    Coordinate topLeft = new Coordinate(col, row - 1);
    Coordinate right = new Coordinate(col + 1, row);
    Coordinate left = new Coordinate(col - 1, row);
    Coordinate bottomRight = new Coordinate(col, row + 1);
    Coordinate bottomLeft = new Coordinate(col - 1, row + 1);

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
    Coordinate coord = (Coordinate) obj;
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
