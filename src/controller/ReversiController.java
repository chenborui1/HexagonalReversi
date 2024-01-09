package controller;

import model.CellType;
import model.ReversiModel;
import view.IReversiView;
import javax.swing.JOptionPane;


/**
 * The ReversiController class is responsible for controlling the Reversi game flow,
 * handling player actions and responding to state changes in the model and view.
 */
public class ReversiController implements PlayerActionFeatures, ModelStateFeatures {

  private final ReversiModel model;
  private final Player player;

  private final CellType playerColor;

  private final IReversiView view;

  /**
   * Constructs a ReversiController with the specified Reversi model, player, and view.
   *
   * @param model  The ReversiModel to interact with.
   * @param player The Player controlling the game.
   * @param view   The IReversiView for displaying the game.
   */
  public ReversiController(ReversiModel model, Player player, IReversiView view) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.playerColor = player.getColor();

    // Register as a listener for player actions from both the view and the player
    //view.addMoveListener(this);
    player.addPlayerActionListener(this);
    view.addPlayerActionListener(this);

    // Register as a listener for player change events from the model
    model.addModelStateListener(this);

    view.setVisible(true);

  }

  /**
   * Handles the event when a move is chosen by a player.
   *
   * @param row    The row of the chosen move.
   * @param column The column of the chosen move.
   */
  @Override
  public void handleMoveChosen(int row, int column) {
    try {
      //SwingUtilities.invokeLater(() -> {
      model.makeMove(playerColor, row, column);

      String message;
      String secondMessage;
      if (playerColor == CellType.BLACK) {
        message = "Black";
        secondMessage = "White";
      } else {
        message = "White";
        secondMessage = "Black";
      }


      JOptionPane.showMessageDialog(null,
              message + " has made a move.\n" + secondMessage + "'s turn to make move.",
              "Move notification", JOptionPane.INFORMATION_MESSAGE);

      // });

    } catch (IllegalStateException e) {
      String message = e.getMessage();
      JOptionPane.showMessageDialog(null, message, "",
              JOptionPane.INFORMATION_MESSAGE);
    }


  }

  /**
   * Handles the event when a pass is chosen by a player.
   */
  @Override
  public void handlePassChosen() {
    try {
      model.skip(playerColor);


      String message;
      if (playerColor == CellType.BLACK) {
        message = "Black has passed his turn.\n" + "White player's turn to make move.";
      } else {
        message = "White has passed his turn.\n" + "Black player's turn to make move.";
      }

      if (!model.isGameOver()) {
        JOptionPane.showMessageDialog(null, message, "",
                JOptionPane.INFORMATION_MESSAGE);
      }

    } catch (IllegalStateException e) {
      String message = e.getMessage();
      JOptionPane.showMessageDialog(null, message, "",
              JOptionPane.INFORMATION_MESSAGE);
    }


  }

  /**
   * Handles the event when there is a change in the current player's turn.
   *
   * @param currentColor The color of the current player.
   */
  @Override
  public void handlePlayerChange(CellType currentColor) {

    if (!model.isGameOver()) {
      try {

        if (model.getCurrentPlayerTurn() == playerColor) {

          player.handleMoveChosen(0, 0);


        }
      } catch (Exception e) {

        String message = e.getMessage();
        JOptionPane.showMessageDialog(null, message, "",
                JOptionPane.INFORMATION_MESSAGE);
      }
    }

  }

  /**
   * Handles the event when the game is over.
   */
  @Override
  public void handleGameOver() {
    String message = "";
    int[] scores = model.getScores();

    int blackScore = scores[0];
    int whiteScore = scores[1];
    if (model.getWinner() == 'B') {
      message = "Black has won with a score of: " + blackScore;
    }
    if (model.getWinner() == 'W') {
      message = "White has won with a score of: " + whiteScore;
    }

    if (model.getWinner() == 'N') {
      message = "Tie game";
    }


    JOptionPane.showMessageDialog(null, message, "Game concluded",
            JOptionPane.INFORMATION_MESSAGE);


  }


  /**
   * Handles the event when a new game is initialized.
   *
   * @param startingColor The color of the player who starts the game.
   */
  @Override
  public void handleInitializeGame(CellType startingColor) {


    if (!model.isGameOver()) {
      if (model.getCurrentPlayerTurn() == playerColor) {

        new Thread(() -> {
          if (playerColor == CellType.BLACK) {
            JOptionPane.showMessageDialog(null,
                    "Black player makes move first",
                    "Initialize Game", JOptionPane.INFORMATION_MESSAGE);
          }
        }).start();

        player.handleMoveChosen(0, 0);


      }

    }


  }

  /**
   * Handles the event when there is a change in the game board.
   */
  @Override
  public void handleGameBoardChange() {
    if (!model.isGameOver()) {
      view.updateView();
    }
  }


}
