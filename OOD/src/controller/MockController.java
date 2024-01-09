package controller;

import java.util.List;
import model.CellType;
import model.ReversiModel;
import view.IReversiView;


/**
 * The MockController class is a testing implementation of the ReversiController.
 * It extends the functionality of the ReversiController to capture game events and
 * actions in a transcript for testing purposes.
 */

public class MockController extends ReversiController
        implements ModelStateFeatures, PlayerActionFeatures {

  // Transcript to capture game events and actions
  private final List<String> transcript;

  private final Player player;


  /**
   * Constructs a MockController for testing purposes.
   *
   * @param model      The ReversiModel to be used in the controller.
   * @param player     The Player (HumanPlayer or AIPlayer) associated with the controller.
   * @param view       The IReversiView to be used in the controller.
   * @param transcript The list to capture the transcript of game events and actions.
   */
  public MockController(ReversiModel model, Player player,
                        IReversiView view, List<String> transcript) {
    super(model, player, view);
    this.transcript = transcript;
    this.player = player;
  }


  /**
   * Converts a Player instance to its string representation.
   *
   * @param player The Player to be converted.
   * @return The string representation of the Player.
   */
  private String playerToString(Player player) {
    if (player instanceof AIPlayer) {
      return "AIPlayer";
    } else {
      return "HumanPlayer";
    }
  }

  @Override
  public void handleMoveChosen(int row, int column) {
    transcript.add(playerToString(player) + " " + player.getColor()
            + " has made a move at row: " + row + " column: " + column);
    super.handleMoveChosen(row, column);

  }

  @Override
  public void handlePassChosen() {
    transcript.add(playerToString(player) + " " + player.getColor() + " has passed");
    super.handlePassChosen();
  }

  @Override
  public void handlePlayerChange(CellType currentColor) {

    transcript.add("Player turn changed");

    super.handlePlayerChange(currentColor);


  }

  @Override
  public void handleGameOver() {

    transcript.add(playerToString(player) + " " + player.getColor() + " Game is over");

    super.handleGameOver();


  }


  @Override
  public void handleInitializeGame(CellType startingColor) {

    transcript.add(playerToString(player) + " " + player.getColor()
            + " game has started and notified first player turn");


    super.handleInitializeGame(startingColor);


  }

  @Override
  public void handleGameBoardChange() {

    transcript.add(playerToString(player) + " " + player.getColor() + " view has been updated");
    super.handleGameBoardChange();
  }

}
