package provider;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Represents the graphical frame for the Reversi game application.
 */
public class ReversiFrame implements Frame {

  private final JFrame frame = new JFrame();
  private final HexagonalBoardView panel;
  private final JLabel playerLabel;

  /**
   * Constructs a ReversiFrame with the specified ReadOnlyReversiModel.
   *
   * @param model The ReadOnlyReversiModel representing the game state.
   */
  public ReversiFrame(ReadOnlyReversiModel model) {
    HexagonalBoardView panel = new HexagonalBoardView(model);
    this.panel = panel;
    playerLabel = new JLabel();
    // Set BorderLayout for the frame
    frame.setLayout(new BorderLayout());
    frame.add(panel, BorderLayout.CENTER);
    frame.add(playerLabel, BorderLayout.NORTH);
    frame.setSize(new Dimension(800, 800));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setVisible(true);


  }

  public HexagonalBoardView getPanel() {
    return this.panel;
  }

  public JLabel getPlayerLabel() {
    return playerLabel;
  }
}
