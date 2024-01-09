package controller;

import java.util.ArrayList;
import java.util.List;

import model.CellType;
import model.ReadonlyReversiModel;

/**
 * The HumanPlayer class represents a human player in the Reversi game.
 * It implements the Player interface and allows for interaction with the game through user input.
 */
public class HumanPlayer implements Player {

  ReadonlyReversiModel model;

  CellType pieceColor;

  /**
   * Constructs a HumanPlayer with the specified Reversi model and piece color.
   *
   * @param model      The ReadonlyReversiModel to interact with.
   * @param pieceColor The color of the human player's pieces.
   */
  public HumanPlayer(ReadonlyReversiModel model, CellType pieceColor) {
    this.model = model;
    this.pieceColor = pieceColor;

  }


  private List<PlayerActionFeatures> actionListeners = new ArrayList<>();


  /**
   * Adds a listener for player actions.
   *
   * @param listener The listener to be added.
   */
  public void addPlayerActionListener(PlayerActionFeatures listener) {
    actionListeners.add(listener);
  }

  /**
   * Gets the color of the human player's pieces.
   *
   * @return The color of the human player's pieces.
   */
  @Override
  public CellType getColor() {
    return pieceColor;
  }


  /**
   * Handles the event when a move is chosen, typically by an AI player.
   * Contains logic to determine the move and notifies listeners about the move.
   *
   * @param row    The row of the chosen move.
   * @param column The column of the chosen move.
   */

  // Method triggered when a move is chosen (e.g., by an AI player)
  public void handleMoveChosen(int row, int column) {
    // Logic to determine the move
    // ...

    // Notify listeners about the move

  }


  /**
   * Handles the event when a pass is chosen, typically by an AI player.
   * Contains logic to determine the pass and notifies listeners about the pass.
   */
  // Method triggered when a pass is chosen (e.g., by an AI player)
  public void handlePassChosen() {
    // Logic to determine the pass
    // ...

    // Notify listeners about the pass

  }
}
