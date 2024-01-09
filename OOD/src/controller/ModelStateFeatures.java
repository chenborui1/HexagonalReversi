package controller;

import model.CellType;


/**
 * The ModelStateFeatures interface defines methods to handle various
 * state changes in the Reversi game model.
 */
public interface ModelStateFeatures {

  /**
   * Handles the change of the current player in the game.
   *
   * @param playerColor The color of the player whose turn it is.
   */
  void handlePlayerChange(CellType playerColor);

  /**
   * Handles the game-over event.
   */
  void handleGameOver();

  /**
   * Handles the initialization of a new game, specifying the starting player's color.
   *
   * @param startingColor The color of the player who starts the game.
   */
  void handleInitializeGame(CellType startingColor);

  /**
   * Handles the change in the game board.
   */
  void handleGameBoardChange();
}
