package provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;




import adopt.OffsetHexCoordinates;

/**
 * Abstract base class for Reversi strategies with utility methods.
 *
 * <p>Implements common methods for coordinate generation and move selection.</p>
 */
public abstract class AbstractStrategy implements ReversiStrategy {
  private Board board;

  /**
   * Initializes the strategy with the specified game board.
   *
   * @param board The game board to use.
   */
  protected void initializeBoard(Board board) {
    this.board = board;
  }

  @Override
  public Optional<Coordinates> chooseMove(ReadOnlyReversiModel model, Player player) {
    return Optional.empty();
  }

  /**
   * Generates a list of coordinates based on the specified parameters.
   *
   * @param inBoundsCoords The list of in-bounds coordinates.
   * @param startCoord     The starting coordinates.
   * @param directions     The array of hex directions.
   * @param length         The length of the generated list.
   * @return The list of generated coordinates.
   */
  protected List<Coordinates> generateCoordinates(List<Coordinates> inBoundsCoords,
                                                  Coordinates startCoord,
                                                  HexDirection[] directions, int length) {
    List<Coordinates> result = new ArrayList<>();
    Coordinates currentCoord = startCoord;

    for (HexDirection direction : directions) {
      for (int i = 0; i < length; i++) {
        result.add(currentCoord);
        currentCoord = getNeighborCoordinates(currentCoord, direction);
      }
    }

    return result;
  }

  /**
   * Retrieves the outermost layer coordinates on the game board.
   *
   * @return The list of outermost layer coordinates.
   */
  protected List<Coordinates> getOuterMostLayerCoordinates() {
    List<Coordinates> inBoundsCoords = board.getInBoundsCoordinates();
    HexDirection[] outerLayerDirections = {HexDirection.RIGHT, HexDirection.BOTTOMRIGHT,
        HexDirection.BOTTOMLEFT,
        HexDirection.LEFT, HexDirection.TOPLEFT, HexDirection.TOPRIGHT};

    return generateCoordinates(board.getInBoundsCoordinates(), inBoundsCoords.get(0),
            outerLayerDirections, board.getBoardSideLength() - 1);
  }

  /**
   * Retrieves the coordinates of the second outermost layer on the game board.
   *
   * @return The list of second outermost layer coordinates.
   */
  protected List<Coordinates> getSecondOuterMostLayerCoordinates() {
    List<Coordinates> inBoundsCoords = board.getInBoundsCoordinates();
    HexDirection[] secondOuterLayerDirections = {HexDirection.RIGHT, HexDirection.BOTTOMRIGHT,
        HexDirection.BOTTOMLEFT,
        HexDirection.LEFT, HexDirection.TOPLEFT, HexDirection.TOPRIGHT};

    return generateCoordinates(board.getInBoundsCoordinates(),
            getNeighborCoordinates(inBoundsCoords.get(0), HexDirection.BOTTOMRIGHT),
            secondOuterLayerDirections, board.getBoardSideLength() - 2);
  }

  /**
   * Retrieves the coordinates of the corners on the game board.
   *
   * @return The list of corner coordinates.
   */
  protected List<Coordinates> getCornerCoordinates() {
    List<Coordinates> inBoundsCoords = board.getInBoundsCoordinates();
    HexDirection[] cornerDirections = {HexDirection.RIGHT, HexDirection.BOTTOMRIGHT,
        HexDirection.BOTTOMLEFT,
        HexDirection.LEFT, HexDirection.TOPLEFT, HexDirection.TOPRIGHT};

    List<Coordinates> cornerCoordinates = new ArrayList<>();
    Coordinates currentCornerCoordinate = inBoundsCoords.get(0);
    cornerCoordinates.add(currentCornerCoordinate);

    for (HexDirection direction : cornerDirections) {
      for (int i = 0; i < board.getBoardSideLength() - 1; i++) {
        currentCornerCoordinate = getNeighborCoordinates(currentCornerCoordinate, direction);
      }
      if (direction != HexDirection.TOPRIGHT) {
        cornerCoordinates.add(currentCornerCoordinate);
      }
    }

    return cornerCoordinates;
  }

  /**
   * Finds the upper-leftmost coordinate from the specified set.
   *
   * @param coordinates The set of coordinates to search.
   * @return The upper-leftmost coordinate.
   */
  protected Coordinates findUpperLeftMostCoordinate(Set<Coordinates> coordinates) {
    Coordinates upperLeftMost = null;
    for (Coordinates coords : coordinates) {
      if (coords.compareCoordinates(upperLeftMost) == -1) {
        upperLeftMost = coords;
      }
    }
    return upperLeftMost;
  }

  /**
   * Calculates the neighboring coordinates based on the specified direction.
   *
   * @param coordinates The base coordinates.
   * @param direction   The hex direction.
   * @return The neighboring coordinates.
   */
  protected Coordinates getNeighborCoordinates(Coordinates coordinates, HexDirection direction) {
    int xHat = coordinates.getFirstCoordinate();
    int yHat = coordinates.getSecondCoordinate();

    switch (direction) {
      case TOPLEFT:
        xHat = coordinates.getFirstCoordinate() - 1;
        yHat = coordinates.getSecondCoordinate();
        break;
      case TOPRIGHT:
        xHat = coordinates.getFirstCoordinate() - 1;
        yHat = coordinates.getSecondCoordinate() + 1;
        break;
      case RIGHT:
        xHat = coordinates.getFirstCoordinate();
        yHat = coordinates.getSecondCoordinate() + 1;
        break;
      case BOTTOMRIGHT:
        xHat = coordinates.getFirstCoordinate() + 1;
        yHat = coordinates.getSecondCoordinate();
        break;
      case BOTTOMLEFT:
        xHat = coordinates.getFirstCoordinate() + 1;
        yHat = coordinates.getSecondCoordinate() - 1;
        break;
      case LEFT:
        xHat = coordinates.getFirstCoordinate();
        yHat = coordinates.getSecondCoordinate() - 1;
        break;
      default:
    }
    return new OffsetHexCoordinates(xHat, yHat);


  }
}
