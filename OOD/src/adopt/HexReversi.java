package adopt;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import model.BasicReversi;
import model.CellType;
import model.ICoordinate;
import provider.ModelFeatures;
import provider.Coordinates;
import provider.DiscState;
import provider.MutableReversiModel;
import provider.PlayerColor;

/**
 * Implements a hexagonal version of the Reversi game model. This class is responsible for
 * managing the game state, including the board, moves, and game logic. It utilizes a HashMap
 * to maintain the state of the board and a BasicReversi model for the game logic.
 */
public class HexReversi implements MutableReversiModel {

  HashMap<ICoordinate, CellType> boardMine;
  BasicReversi model;

  /**
   * Constructs a HexReversi game model with a specified initial board state.
   *
   * @param board The initial state of the board.
   */
  public HexReversi(HashMap<ICoordinate, CellType> board) {
    this.boardMine = board;
  }

  /**
   * Executes a move in the game based on the player's color and the specified coordinates.
   *
   * @param color The color of the player making the move.
   * @param coordinates The coordinates where the move is to be made.
   * @throws IllegalArgumentException If the move is not valid.
   * @throws IllegalStateException If the game is not in a state to accept a move.
   */
  @Override
  public void makeMove(PlayerColor color, Coordinates coordinates)
          throws IllegalArgumentException, IllegalStateException {
    // Method implementation
  }

  /**
   * Allows a player to opt to pass their turn.
   *
   * @param color The color of the player opting to pass.
   * @throws IllegalStateException If the game is not in a state to accept a pass.
   */
  @Override
  public void optToPass(PlayerColor color) throws IllegalStateException {
    // Method implementation
  }

  /**
   * Starts the game. Currently, this method requires implementation.
   *
   * @throws IllegalStateException If the game cannot be started.
   */
  @Override
  public void startGame() throws IllegalStateException {
    // Method implementation
  }

  /**
   * Checks if a move is valid for a given player color and coordinates.
   *
   * @param color The color of the player.
   * @param coordinates The coordinates of the intended move.
   * @return false, currently not implemented.
   * @throws IllegalArgumentException If the coordinates are invalid.
   * @throws IllegalStateException If the game state is not valid for checking moves.
   */
  @Override
  public boolean isValidMove(PlayerColor color, Coordinates coordinates)
          throws IllegalArgumentException, IllegalStateException {
    return false;
  }

  /**
   * Calculates possible moves for a given player color. Currently not implemented.
   *
   * @param color The color of the player.
   * @return null, implementation required.
   * @throws IllegalArgumentException If the player color is invalid.
   * @throws IllegalStateException If the game state is not valid for calculating moves.
   */
  @Override
  public Set<Coordinates> calculateMoves(PlayerColor color)
          throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  /**
   * Checks if the game is over. Currently, this method returns false and needs implementation.
   *
   * @return false, indicating the game is not over.
   * @throws IllegalStateException If the game state is invalid.
   */
  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  /**
   * Retrieves the state of a disc at specified coordinates. Currently, this method returns null.
   *
   * @param coordinates The coordinates of the disc.
   * @return null, implementation required.
   * @throws IllegalArgumentException If the coordinates are invalid.
   * @throws IllegalStateException If the game state is invalid.
   */
  @Override
  public DiscState getDiscStateAt(Coordinates coordinates)
          throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  /**
   * Gets the score for a given player color. This method calculates the score based on the current
   * board state.
   *
   * @param color The color of the player whose score is to be retrieved.
   * @return The score of the specified player.
   * @throws IllegalStateException If the game state is invalid for score calculation.
   */
  @Override
  public int getScore(PlayerColor color) throws IllegalStateException {
    CellType colorCell;


    if (color == PlayerColor.BLACK) {
      colorCell = CellType.BLACK;
    }
    else {
      colorCell = CellType.WHITE;
    }

    BasicReversi model =  new BasicReversi(boardMine, colorCell);

    int[] scores = model.getScores();

    int blackScore = scores[0];
    int whiteScore = scores[1];

    if (colorCell == CellType.BLACK) {
      return blackScore;
    }
    else {
      return whiteScore;
    }

  }

  /**
   * Gets a copy of the current board. Currently, this method returns null and needs implementation.
   *
   * @return null, implementation required.
   */
  @Override
  public HexBoard getCopyOfBoard() {
    return null;
  }

  /**
   * Returns the total size of the board. Currently, this method returns 0 and needs implementation.
   *
   * @return 0, indicating the board size.
   * @throws IllegalStateException If the game state is invalid.
   */
  @Override
  public int getBoardSize() throws IllegalStateException {
    return 0;
  }

  /**
   * Returns the side length of the board. Currently, this method returns 0 and needs
   * implementation.
   *
   * @return 0, indicating the board side length.
   * @throws IllegalStateException If the game state is invalid.
   */
  @Override
  public int getBoardSideLength() throws IllegalStateException {
    return 0;
  }

  /**
   * Retrieves a list of coordinates that are within the bounds of the board.
   * Currently, this method returns null and needs implementation.
   *
   * @return null, implementation required.
   */
  @Override
  public List<Coordinates> getInBoundsCoordinates() {
    return null;
  }

  /**
   * Returns the length of the longest row on the board. Currently, this method returns 0.
   *
   * @return 0, indicating the length of the longest row.
   */
  @Override
  public int getLongestRowLength() {
    return 0;
  }

  /**
   * Retrieves which player's turn it is. Currently, this method returns null and needs
   * implementation.
   *
   * @return null, implementation required.
   */
  @Override
  public PlayerColor getTurn() {
    return null;
  }

  /**
   * Adds a listener for model features. Currently, this method has no implementation.
   *
   * @param listener The listener to be added.
   */
  @Override
  public void addModelFeaturesListener(ModelFeatures listener) {
    // Method implementation
  }
}
