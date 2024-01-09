package adopt;


import provider.Coordinates;

/**
 * Represents the coordinates for a hexagonal grid using an offset coordinate system.
 * In this system, coordinates are defined by two values, typically referred to as x and y,
 * which determine the position in the hex grid. This class provides methods to access
 * and compare these coordinates.
 */
public class OffsetHexCoordinates implements Coordinates {

  private int xhat;
  private int yhat;

  /**
   * Constructs an OffsetHexCoordinates object with specified x and y values.
   * These values represent the position in the hexagonal grid.
   *
   * @param xhat The x-coordinate in the offset hex grid.
   * @param yhat The y-coordinate in the offset hex grid.
   */
  public OffsetHexCoordinates(int xhat, int yhat) {
    this.xhat = xhat;
    this.yhat = yhat;
  }

  /**
   * Retrieves the x-coordinate in the offset hex grid.
   *
   * @return The x-coordinate.
   */
  @Override
  public int getFirstCoordinate() {
    return xhat;
  }

  /**
   * Retrieves the y-coordinate in the offset hex grid.
   *
   * @return The y-coordinate.
   */
  @Override
  public int getSecondCoordinate() {
    return yhat;
  }

  /**
   * Compares this set of coordinates with another set. This method is currently
   * a stub and returns 0, indicating no comparison logic is implemented.
   *
   * @param other The other Coordinates object to compare with.
   * @return An integer representing the comparison result. Currently returns 0.
   */
  @Override
  public int compareCoordinates(Coordinates other) {
    return 0;
  }
}
