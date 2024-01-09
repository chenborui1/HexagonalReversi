package adopt;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.BasicReversi;
import model.CellType;
import model.ICoordinate;
import model.ReadonlyReversiModel;
import provider.ModelFeatures;
import provider.Coordinates;
import provider.DiscState;
import provider.PlayerColor;
import provider.ReadOnlyReversiModel;

/**
 * ModelAdopter serves as an adapter class, implementing both ReadOnlyReversiModel
 * and ReadonlyReversiModel interfaces.
 * It delegates the method calls to an instance of ReadonlyReversiModel,
 * providing a unified interface for accessing the game state and logic of a Reversi game.
 * This class is useful for adapting between different implementations
 * or versions of the Reversi model interface.
 */
public class ModelAdopter implements ReadOnlyReversiModel, ReadonlyReversiModel {

  ReadonlyReversiModel delegate;

  /**
   * Constructs a ModelAdopter with a specified ReadonlyReversiModel.
   *
   * @param model The ReadonlyReversiModel that this adapter will delegate to.
   */
  public ModelAdopter(ReadonlyReversiModel model) {

    this.delegate = model;

  }



  @Override
  public HashMap<ICoordinate, CellType> getGameBoard() {
    return delegate.getGameBoard();
  }

  @Override
  public CellType getCurrentPlayerTurn() {
    return delegate.getCurrentPlayerTurn();
  }

  @Override
  public char getWinner() {
    return delegate.getWinner();
  }

  @Override
  public int[] getScores() {
    return delegate.getScores();
  }

  @Override
  public CellType nextPlayer() {
    return delegate.nextPlayer();
  }

  @Override
  public CellType getContent(int row, int column) {
    return delegate.getContent(row, column);
  }

  @Override
  public boolean playerLegalMoves() {
    return delegate.playerLegalMoves();
  }

  @Override
  public boolean canMakeMove(int row, int column) {
    return delegate.canMakeMove(row, column);
  }

  @Override
  public void startGame() throws IllegalStateException {
    int happy = 1;
  }

  @Override
  public boolean isValidMove(PlayerColor color, Coordinates coordinates)
          throws IllegalArgumentException, IllegalStateException {
    return false;
  }

  /**
   * Calculates all possible moves for a given player color on the Reversi board.
   * This method determine the valid moves based on the current game state and the specified
   * player color. It creates a temporary BasicReversi model with a copy of the game board
   * and the player color to evaluate possible moves.
   *
   * @param color The color of the player (either PlayerColor.BLACK or PlayerColor.WHITE) for
   *              whom to calculate moves.
   * @return A set of Coordinates objects representing all possible moves for the specified player.
   * @throws IllegalArgumentException If the player color is not valid.
   * @throws IllegalStateException If the game state does not allow for move calculation.
   */
  @Override
  public Set<Coordinates> calculateMoves(PlayerColor color)
          throws IllegalArgumentException, IllegalStateException {




    Set<Coordinates> set = new HashSet<>();

    CellType colorCell;
    if (color == PlayerColor.BLACK) {
      colorCell = CellType.BLACK;
    }
    else {
      colorCell = CellType.WHITE;
    }

    HashMap<ICoordinate, CellType> copyBoard = delegate.getGameBoard();

    ReadonlyReversiModel model = new BasicReversi(copyBoard, colorCell);

    for (int row = 0; row < model.getBoardSize(); row ++) {

      for (int col = 0; col < model.getBoardSize(); col ++) {

        if (model.canMakeMove(row, col)) {
          set.add(new CoordinateAdopter(row, col));
        }
      }


    }



    return set;
  }

  @Override
  public boolean isGameOver() {
    return delegate.isGameOver();
  }

  /**
   * Retrieves the state of a disc at the specified coordinates on the Reversi board.
   * This method determines the disc state by querying the underlying delegate's content
   * at the given row and column derived from the coordinates.
   *
   * @param coordinates The coordinates where the disc state is to be checked.
   * @return The state of the disc at the specified coordinates. It returns DiscState.BLACK
   *         if the cell contains a black disc, DiscState.WHITE if it contains a white disc,
   *         and DiscState.UNPLACED if the cell is empty or unoccupied.
   * @throws IllegalArgumentException If the coordinates are invalid.
   * @throws IllegalStateException If the game state is not valid for querying disc states.
   */
  @Override
  public DiscState getDiscStateAt(Coordinates coordinates)
          throws IllegalArgumentException, IllegalStateException {


    int row = coordinates.getFirstCoordinate();
    int column = coordinates.getSecondCoordinate();

    if (delegate.getContent(row, column) == CellType.BLACK) {
      return DiscState.BLACK;
    }
    if (delegate.getContent(row, column) == CellType.WHITE) {
      return DiscState.WHITE;
    }
    else {
      return DiscState.UNPLACED;
    }

  }

  @Override
  public int getScore(PlayerColor color) throws IllegalStateException {
    return 0;
  }

  @Override
  public HexBoard getCopyOfBoard() {


    return new HexBoard(delegate.getGameBoard());
  }


  @Override
  public int getBoardSize() {
    return delegate.getBoardSize();
  }

  @Override
  public int getBoardSideLength() throws IllegalStateException {
    return (delegate.getBoardSize() + 1) / 2;
  }

  /**
   * Retrieves a list of coordinates that are within the bounds of the Reversi board and
   * have a non-null content.
   * This method iterates through each cell of the game board, as defined by the delegate's
   * board size,and adds the coordinates of cells with non-null content to the list.
   *
   * @return A list of Coordinates objects representing all the in-bounds coordinates on the
   *      game board with non-null content.
   *      IEach coordinate is adapted into the CoordinateAdopter format.
   */
  @Override
  public List<Coordinates> getInBoundsCoordinates() {

    List<Coordinates> list = new ArrayList<>();

    for (int row = 0; row < delegate.getBoardSize(); row++) {
      for (int col = 0; col < delegate.getBoardSize(); col++) {

        if (delegate.getContent(row, col) != null) {
          list.add(new CoordinateAdopter(row, col));
        }

      }
    }


    return list;
  }

  @Override
  public int getLongestRowLength() {
    return delegate.getBoardSize();
  }

  @Override
  public PlayerColor getTurn() {
    return null;
  }

  @Override
  public void addModelFeaturesListener(ModelFeatures listener) {
    int jkl = 1;
  }
}
