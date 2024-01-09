package view;


import controller.PlayerActionFeatures;


/**
 * The IReversiView interface represents the contract for a Reversi game view.
 * Implementing classes are expected to provide methods for managing the visibility of the view.
 */
public interface IReversiView {


  /**
   * Sets the visibility of the Reversi view.
   *
   * @param set True to set the view as visible, false to set it as invisible.
   */
  void setVisible(boolean set);

  /**
   * Updates the Reversi view to reflect changes in the game state.
   */

  void updateView();


  /**
   * Adds a listener for player action events in the Reversi view.
   *
   * @param listener The listener to be added.
   */
  void addPlayerActionListener(PlayerActionFeatures listener);


}
