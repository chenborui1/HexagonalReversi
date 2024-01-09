package model;

import java.util.HashMap;

/**
 * The ReadonlyReversiModel interface defines the methods required for a read-only representation
 * of a Reversi game model. Implementations of this interface should provide information about the
 * current state of the game board, players, and moves without allowing modifications to the game
 * state.
 */
public interface ReadonlyReversiModel {


  /**
   * Get the size of the game board.
   *
   * @return The size of the game board, which is a
   *         single integer representing the number of rows/columns.
   */

  int getBoardSize();


  /**
   * Get the current state of the game board, including disc positions.
   *
   * @return A 2D array representing the game board. The HashMap contains
   *         coordinates (keys) and the corresponding CellType (values).
   */

  HashMap<ICoordinate, CellType> getGameBoard();


  /**
   * Get the current player's color ('B' for black, 'W' for white).
   *
   * @return The current player's color, represented as a CellType e
   *         num (either CellType.BLACK or CellType.WHITE).
   */


  CellType getCurrentPlayerTurn();


  /**
   * Returns whether the game is over.
   * Game is over when both players skip after each other
   *
   * @return True if the game is over; false otherwise.
   */
  boolean isGameOver();

  /**
   * Get the winner of the game ('B' for black, 'W' for white, 'N' for no winner).
   *
   * @return The winner's color ('B' for black, 'W' for white) or 'N' for a tie or an ongoing game.
   */

  char getWinner();

  /**
   * Get the score for both players (number of discs).
   *
   * @return An array where the first element is the black player's score and
   *         the second element is the white player's score.
   */
  int[] getScores();


  /**
   * Returns the color of the player who has the next turn.
   *
   * @return A CellType representing the color of the player who has
   *         the next turn ('B' for black or 'W' for white).
   */

  CellType nextPlayer();


  /**
   * Gets the content of a cell at the specified coordinates.
   *
   * @param row    The row index of the cell.
   * @param column The column index of the cell.
   * @return The CellType (content) of the cell at the given coordinates.
   */

  CellType getContent(int row, int column);


  /**
   * Checks if the current player has legal moves to make on the game board.
   *
   * @return true if the current player has legal moves, false otherwise.
   */

  boolean playerLegalMoves();

  /**
   * Checks if a move can be made by the current player at the specified coordinates.
   *
   * @param row    The row index of the cell.
   * @param column The column index of the cell.
   * @return true if a move can be made, false otherwise.
   */

  boolean canMakeMove(int row, int column);
}
