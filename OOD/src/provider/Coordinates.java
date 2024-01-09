package provider;

/**
 * Represents a pair of coordinates on a game board.
 */
public interface Coordinates {
  /**
   * Gets the value of the first coordinate.
   *
   * @return The first coordinate value.
   */
  int getFirstCoordinate();

  /**
   * Gets the value of the second coordinate.
   *
   * @return The second coordinate value.
   */
  int getSecondCoordinate();

  /**
   * Compares this Coordinates with another Coordinates.
   *
   * @param other The Coordinates to compare.
   * @return A value less than 0 if this is less than other,
   *         0 if they are equal, and a value greater than 0 if this is greater.
   */
  int compareCoordinates(Coordinates other);
}
