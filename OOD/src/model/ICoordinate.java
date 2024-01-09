package model;


/**
 * The Coordinate interface that contains some basic method that a coordinate should have.
 */
public interface ICoordinate {
  /**
   * Gets the row value of this Coordinate.
   *
   * @return The row value.
   */
  public int getRow();

  /**
   * Gets the column value of this Coordinate.
   *
   * @return The column value.
   */
  public int getCol();


  //Gets the coordinates of the surrounding cells

  /**
   * Gets the coordinates of the surrounding cells (top-right,
   * top-left, right, left, bottom-right, bottom-left)
   * relative to this Coordinate.
   *
   * @return An ArrayList of Coordinate objects representing the surrounding cells.
   */


  public String toString();

  /**
   * Checks if this Coordinate is equal to another object.
   *
   * @param obj The object to compare.
   * @return True if the objects are equal, false otherwise.
   */

  @Override
  public boolean equals(Object obj);


  /**
   * Computes a hash code for this Coordinate based on its row and column values.
   *
   * @return The hash code value.
   */
  @Override
  public int hashCode();
}
