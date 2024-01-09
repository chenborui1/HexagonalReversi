package view;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controller.PlayerActionFeatures;

import model.ReadonlyReversiModel;

/**
 * The `SquareReversiView` class represents the graphical user interface for the Reversi game
 * using square cells.
 * It extends JFrame and provides a frame that displays the Reversi game board using a SquarePanel.
 */
public class SquareReversiView extends JFrame implements IReversiView, PlayerActionFeatures {

  protected List<PlayerActionFeatures> listeners = new ArrayList<>();

  private SquarePanel panel;

  /**
   * Constructs a SquareReversiView for the given Reversi model.
   *
   * @param model The Reversi model to be displayed.
   */
  public SquareReversiView(ReadonlyReversiModel model) {
    super();
    // Set the initial size of the frame
    setSize(model.getBoardSize() * 80, model.getBoardSize() * 80);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    SquarePanel panel = new SquarePanel(model);
    this.panel = panel;
    this.add(panel, BorderLayout.CENTER);
    // Add KeyListener to the frame
    addKeyListener(new MyKeyListener());
    setFocusable(true);


    this.pack();
  }

  @Override
  public void handleMoveChosen(int row, int column) {
    notifyMoveChosen(row, column);
  }

  @Override
  public void handlePassChosen() {
    notifyPassChosen();
  }

  @Override
  public void addPlayerActionListener(PlayerActionFeatures listener) {
    listeners.add(listener);
  }

  /**
   * Inner class for KeyListener to handle key events.
   */
  private class MyKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      // Handle key press event
      char keyChar = e.getKeyChar();
      if (keyChar == 'm' && panel.getSquareClickedCoordinates() != null) {
        handleMoveChosen(panel.getSquareClickedCoordinates().getRow(),
                panel.getSquareClickedCoordinates().getCol());
      } else if (keyChar == 'p') {
        handlePassChosen();
      }
    }
  }

  private void notifyMoveChosen(int row, int column) {
    for (PlayerActionFeatures listener : listeners) {
      listener.handleMoveChosen(row, column);
    }
  }

  private void notifyPassChosen() {
    for (PlayerActionFeatures listener : listeners) {
      listener.handlePassChosen();
    }
  }

  @Override
  public void updateView() {
    // Assuming SquarePanel has a method like updateSquareGrid to update the square representation
    panel.updateSquareGrid();

    // You may add more logic here to update other UI components based on the model state
  }
}
