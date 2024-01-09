package provider;

import java.util.Optional;

import provider.Coordinates;
import provider.ReadOnlyReversiModel;
import provider.Player;

/**
 * The ReversiStrategy interface represents a strategy for choosing the next move in a game of
 * Reversi. Implementations of this interface provide logic for making decisions on which move
 * to choose based on the current state of the game and the player.
 */
public interface ReversiStrategy {

  /**
   * Chooses the next move to be made in a game of Reversi based on the given ReadOnlyReversiModel
   * and player. The chosen move is represented by its coordinates on the game board.
   *
   * @param model  The current state of the Reversi game as a ReadOnlyReversiModel.
   * @param player The player for whom the move is being chosen.
   * @return The Coordinates object representing the chosen move.
   * @throws IllegalArgumentException If the input model is not in a valid state or if the player
   *                                  is not eligible to make a move, this exception is thrown.
   */
  Optional<Coordinates> chooseMove(ReadOnlyReversiModel model, Player player)
          throws IllegalArgumentException;
}
