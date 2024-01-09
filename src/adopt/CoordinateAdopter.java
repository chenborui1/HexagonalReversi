package adopt;

import provider.Coordinates;




/**
 * Represents a generic set of coordinates defined by row and column values.
 * This class is designed to adapt a simple two-dimensional coordinate system,
 * typically used in grid-based layouts such as board games or spatial grids.
 * It provides methods for accessing these coordinates and comparing them
 * with others.
 */
public class CoordinateAdopter implements Coordinates {

  private int row;
  private int column;

  /**
   * Constructs a CoordinateAdopter object with specified row and column values.
   *
   * @param row The row value of the coordinate.
   * @param column The column value of the coordinate.
   */
  public CoordinateAdopter(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Retrieves the row value of the coordinate.
   *
   * @return The row value.
   */
  @Override
  public int getFirstCoordinate() {
    return row;
  }

  /**
   * Retrieves the column value of the coordinate.
   *
   * @return The column value.
   */
  @Override
  public int getSecondCoordinate() {
    return column;
  }

  /**
   * Compares this set of coordinates with another set. The comparison is based first
   * on the row values and then on the column values. If this object's row is less than
   * the other's row, it returns -1. If rows are equal and this object's column is less than
   * the other's, it returns -1. Otherwise, it returns 1. If the rows and columns are equal,
   * it returns 0. If the other coordinate object is null, it returns -1.
   *
   * @param other The other Coordinates object to compare with.
   * @return An integer representing the comparison result.
   */
  @Override
  public int compareCoordinates(Coordinates other) {

    if (other == null) {
      return -1;
    }

    if (this.getFirstCoordinate() < other.getFirstCoordinate()) {
      return -1;
    }

    if (this.getFirstCoordinate() == other.getFirstCoordinate()
            || this.getFirstCoordinate() > other.getSecondCoordinate()) {
      if (this.getSecondCoordinate() < other.getSecondCoordinate()) {
        return -1;
      }
      else {
        return 1;
      }


    }



    return 0;


  }
}
