package provider;

import java.util.List;
import java.util.Set;


import adopt.HexBoard;


/**
 * The Read-Only Reversi Model interface that outlines accessor methods for accessing game
 * state and information.
 */

public interface ReadOnlyReversiModel  {
  /**
   * Starts the game, initiating the process of playing.
   * This method is responsible for beginning the actual gameplay.
   * @throws IllegalStateException if the game has already been started.
   */
  void startGame() throws IllegalStateException;

  /**
   * Check if a move is valid for the player at specified coordinates.
   *
   * @param color       Player's color.
   * @param coordinates Coordinates to check.
   * @return True if the move is valid.
   * @throws IllegalArgumentException if the coordinates are invalid.
   * @throws IllegalStateException    if the game state prevents moves.
   */
  boolean isValidMove(PlayerColor color, Coordinates coordinates)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Calculates valid moves for the specified player color.
   *
   * @param color Player's color.
   * @return Set of valid move coordinates.
   * @throws IllegalArgumentException if the player color is invalid.
   * @throws IllegalStateException    if the game state prevents moves.
   */
  Set<Coordinates> calculateMoves(PlayerColor color) // Maybe return a list of cells?
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Check if the game is over.
   *
   * @return True if the game is over.
   * @throws IllegalStateException if the game state prevents this operation.
   */
  boolean isGameOver() throws IllegalStateException;

  /**
   * Get the disc state at the specified coordinates.
   *
   * @param coordinates Coordinates to retrieve the disc state.
   * @return Disc state at the coordinates.
   * @throws IllegalArgumentException if the coordinates are invalid.
   * @throws IllegalStateException    if the game state prevents this operation.
   */
  DiscState getDiscStateAt(Coordinates coordinates)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Get the player's score.
   *
   * @param color Player's color.
   * @return The player's score.
   * @throws IllegalStateException if the game state prevents this operation.
   */
  int getScore(PlayerColor color)
          throws IllegalStateException;

  /**
   * Gets a copy of the board used to play the game.
   *
   * @return A deep copy of the board.
   */
  HexBoard getCopyOfBoard();

  /**
   * Get the size of the game board (total number of cells).
   *
   * @return The size of the game board.
   * @throws IllegalStateException if the game state prevents this operation.
   */
  int getBoardSize() throws IllegalStateException;

  /**
   * Get the side length of the game board.
   *
   * @return The side length of the game board.
   * @throws IllegalStateException if the game state prevents this operation.
   */
  int getBoardSideLength() throws IllegalStateException;

  /**
   * Retrieves a list of Coordinates that are within bounds.
   *
   * @return A list of Coordinates within the specified bounds.
   */
  List<Coordinates> getInBoundsCoordinates();

  /**
   * Gets the length of the longest row on the Reversi board.
   *
   * @return The length of the longest row.
   */
  int getLongestRowLength();

  /**
   * Gets the current player's color whose turn it is.
   *
   * @return The color of the player whose turn it is.
   */
  PlayerColor getTurn();

  /**
   * Adds a listener to receive notifications when the model state changes
   * and notifications about the player's turn.
   *
   * @param listener The listener to be added.
   */
  void addModelFeaturesListener(ModelFeatures listener);
}

