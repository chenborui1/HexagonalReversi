package controller;

import model.CellType;

/**
 * The Player interface represents a player in the Reversi game.
 * It extends the PlayerActionFeatures interface, providing additional
 * functionality for player actions.
 */
public interface Player extends PlayerActionFeatures {
  // Method to get the name of the player

  /**
   * Adds a listener for player actions.
   *
   * @param listener The listener to be added.
   */
  void addPlayerActionListener(PlayerActionFeatures listener);


  /**
   * Gets the color of the player's pieces.
   *
   * @return The color of the player's pieces.
   */
  CellType getColor();


}
