package provider;

import java.util.Optional;

/**
 * Interface representing a player in a Reversi game.
 */
public interface Player {
  /**
   * Get the player's name or identifier.
   *
   * @return The player's name.
   */
  String getName();


  /**
   * Get the player's color.
   *
   * @return The player's color.
   */
  PlayerColor getColor();

  /**
   * Returns the opponent player.
   *
   * @return The opponent player.
   */
  Player opponent();

  /**
   * Performs a move on behalf of the player based on the provided coordinates.
   *
   * @param selectedCoords The optional coordinates of the selected move.
   */
  void performMove(Optional<Coordinates> selectedCoords);

  /**
   * Passes the turn for the current player.
   */
  void pass();

  /**
   * Adds a listener for features such as moves and passes.
   *
   * @param listener The listener to be added.
   */
  void addFeaturesListener(Features listener);

  /**
   * Checks if the player is an AI.
   *
   * @return true if the player is an AI, false otherwise.
   */
  boolean isAI();
}
