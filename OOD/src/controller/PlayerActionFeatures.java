package controller;

/**
 * The PlayerActionFeatures interface defines methods for handling
 * player actions in the Reversi game.
 */
public interface PlayerActionFeatures {

  /**
   * Handles the event when a move is chosen by a player.
   *
   * @param row    The row of the chosen move.
   * @param column The column of the chosen move.
   */
  void handleMoveChosen(int row, int column);

  /**
   * Handles the event when a pass is chosen by a player.
   */
  void handlePassChosen();
}
