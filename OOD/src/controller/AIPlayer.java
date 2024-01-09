package controller;

import java.util.ArrayList;
import java.util.List;

import model.CellType;
import model.ReadonlyReversiModel;
import strategy.ReversiStrategy;

/**
 * The AIPlayer class represents an AI-controlled player in the Reversi game.
 * It implements the Player interface and uses a specified ReversiStrategy for making moves.
 */
public class AIPlayer implements Player {

  private ReadonlyReversiModel model;

  private final ReversiStrategy strategy;

  private final CellType pieceColor;

  private List<PlayerActionFeatures> actionListeners = new ArrayList<>();

  /**
   * Constructs an AIPlayer with the specified Reversi model, strategy, and piece color.
   *
   * @param model      The ReadonlyReversiModel to interact with.
   * @param strategy   The ReversiStrategy used for making moves.
   * @param pieceColor The color of the AI player's pieces.
   */
  public AIPlayer(ReadonlyReversiModel model, ReversiStrategy strategy, CellType pieceColor) {
    this.model = model;
    this.strategy = strategy;
    this.pieceColor = pieceColor;


  }


  /**
   * Adds a listener for player actions.
   *
   * @param listener The listener to be added.
   */

  public void addPlayerActionListener(PlayerActionFeatures listener) {
    actionListeners.add(listener);
  }


  /**
   * Gets the color of the AI player's pieces.
   *
   * @return The color of the AI player's pieces.
   */
  @Override
  public CellType getColor() {
    return pieceColor;
  }


  /**
   * Notifies listeners that a move has been chosen by the AI player.
   *
   * @param row    The row of the chosen move.
   * @param column The column of the chosen move.
   */
  private void notifyMoveChosen(int row, int column) {


    for (PlayerActionFeatures listener : actionListeners) {

      listener.handleMoveChosen(row, column);
    }


  }


  /**
   * Notifies listeners that a pass has been chosen by the AI player.
   */

  private void notifyPassChosen() {


    for (PlayerActionFeatures listener : actionListeners) {
      listener.handlePassChosen();
    }


  }

  /**
   * Handles the event when a move is chosen. Uses the specified strategy to choose a coordinate.
   * Notifies listeners of the chosen move.
   *
   * @param row    The row of the chosen move.
   * @param column The column of the chosen move.
   */
  public void handleMoveChosen(int row, int column) {

    try {


      int optimalRow = strategy.chooseCoordinate(model, pieceColor).getRow();
      int optimalColumn = strategy.chooseCoordinate(model, pieceColor).getCol();
      notifyMoveChosen(optimalRow, optimalColumn);


    } catch (IllegalStateException e) {
      handlePassChosen();
    }


  }

  /**
   * Handles the event when a pass is chosen. Notifies listeners of the chosen pass.
   */
  public void handlePassChosen() {

    notifyPassChosen();
  }
}
