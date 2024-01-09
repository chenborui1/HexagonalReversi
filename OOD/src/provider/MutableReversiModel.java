package provider;


/**
 * Interface for managing a Reversi game, allowing game initialization and player moves.
 */
public interface MutableReversiModel extends ReadOnlyReversiModel {
  /**
   * Make a move for the specified player at the given coordinates.
   *
   * @param color       Player's color.
   * @param coordinates Coordinates for the player's move.
   * @throws IllegalArgumentException if the move is invalid or coordinates are invalid.
   * @throws IllegalStateException    if the game state prevents this operation.
   */
  void makeMove(PlayerColor color, Coordinates coordinates)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Toggles the turn to a different one if the current turn is not a pass for the specified
   * player color. This method changes the current turn to the next player's turn of the specified
   * color if the current turn is not a pass. If the current turn is a pass, it remains unchanged.
   *
   * @param color The color of the player for whom the turn should be toggled.
   * @throws IllegalStateException if the game is in an invalid state to perform this action.
   */
  void optToPass(PlayerColor color)
          throws IllegalStateException;
}
