package model;

import controller.ModelStateFeatures;

/**
 * The ReversiModel interface represents the model of a Reversi (Othello) game.
 * It defines the essential methods for managing the game state and gameplay.
 */

public interface ReversiModel extends ReadonlyReversiModel {




  /**
   * Adds a listener for state change events in the Reversi model.
   *
   * @param listener The listener to be added.
   */

  void addModelStateListener(ModelStateFeatures listener);

  /**
   * Make a move on the game board.
   * Making a move is placing a disk on the board that is legal.
   *
   * @param player The CellType of the player making the move ('B' for black, 'W' for white).
   */

  void makeMove(CellType player, int row, int column);

  /**
   * A player skipping their move.
   *
   * @param player The CellType of the player skipping their move.
   */
  void skip(CellType player);

  /**
   * Notifies listener that game has started.
   */
  void startGame();
}
