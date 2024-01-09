package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controller.PlayerActionFeatures;

import model.ReadonlyReversiModel;

/**
 * The `ReversiView` class represents the graphical user interface for the Reversi game.
 * It extends JFrame and provides a frame that displays the Reversi game board using a HexPanel.
 */
public class ReversiView extends JFrame implements IReversiView, PlayerActionFeatures {

  private List<PlayerActionFeatures> listeners = new ArrayList<>();

  private HexPanel panel;


  /**
   * Constructs a ReversiView for the given Reversi model.
   *
   * @param model The Reversi model to be displayed.
   */
  public ReversiView(ReadonlyReversiModel model) {
    super();
    // Set the initial size of the frame
    setSize(model.getBoardSize() * 70, model.getBoardSize() * 80);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    HexPanel panel = new HexPanel(model);
    this.panel = panel;
    this.add(panel);


    // Add KeyListener to the frame
    addKeyListener(new MyKeyListener());
    setFocusable(true);

    this.setPreferredSize(new Dimension(700, 700));
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
      if (keyChar == 'm' && panel.getHexagonClickedCoordinates() != null) {


        handleMoveChosen(panel.getHexagonClickedCoordinates().getRow(),
                panel.getHexagonClickedCoordinates().getCol());



      } else if (keyChar == 'p') {

        handlePassChosen();

      } else if (keyChar == 'h') {
        panel.toggleHint();
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
    // Assuming HexPanel has a method like updateHexagonGrid to update the hexagon representation
    panel.updateHexagonGrid();

    // You may add more logic here to update other UI components based on the model state
  }




}

