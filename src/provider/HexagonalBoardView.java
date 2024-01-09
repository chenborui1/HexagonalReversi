package provider;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Represents a graphical view of a Reversi game board using hexagonal tiles.
 */
public class HexagonalBoardView extends JPanel implements Panel {

  private final List<List<Hexagon>> hexagons;
  private final ReadOnlyReversiModel model;
  private Hexagon temporaryHex;
  private int hexagonLength;
  private Features featuresListener;

  /**
   * Constructs a HexagonalBoardView with the given ReadOnlyReversiModel.
   *
   * @param model The ReadOnlyReversiModel representing the game state.
   */
  public HexagonalBoardView(ReadOnlyReversiModel model) {
    setLayout(new BorderLayout());
    int windowSize = 800;
    this.setSize(new Dimension(windowSize, windowSize));

    this.model = model;
    //this.model.startGame();
    hexagonLength = (int) (getHeight() / (model.getLongestRowLength() * 2));
    hexagons = new ArrayList<>(model.getLongestRowLength());
    for (int i = 0; i < model.getLongestRowLength(); i++) {
      hexagons.add(new ArrayList<>());
    }
    initializeHexagons();

    HexagonalBoardMouseListener mouseListener = new HexagonalBoardMouseListener(this);
    addMouseListener(mouseListener);
    HexagonalBoardKeyListener keyListener = new HexagonalBoardKeyListener(this);
    addKeyListener(keyListener);
    setFocusable(true);

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        hexagonLength = (int) (getHeight() / (model.getLongestRowLength() * 2));
        hexagons.clear();
        for (int i = 0; i < model.getLongestRowLength(); i++) {
          hexagons.add(new ArrayList<>());
        }
        initializeHexagons();
        repaint();
      }
    });
  }

  /**
   * Updates the hexagonal board view on the EDT (Event Dispatch Thread).
   */
  public void updateBoard() {
    SwingUtilities.invokeLater(() -> {
      hexagons.clear();
      for (int i = 0; i < model.getLongestRowLength(); i++) {
        hexagons.add(new ArrayList<>());
      }
      initializeHexagons();
      repaint();
    });
  }

  /**
   * Displays a message indicating the player's turn.
   *
   * @param message The message to be displayed.
   */
  public void showPlayerTurnMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Turn Notification",
            JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Displays an error message.
   *
   * @param message The error message to be displayed.
   */
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays a message indicating the end of the game.
   *
   * @param message The message to be displayed.
   */
  public void showGameOverMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Game Over",
            JOptionPane.INFORMATION_MESSAGE);
  }


  /**
   * Adds a features listener to this view.
   *
   * @param listener The features listener to be added.
   */
  public void addFeaturesListener(Features listener) {
    this.featuresListener = listener;
  }

  /**
   * Initializes the hexagons on the game board.
   */
  private void initializeHexagons() {
    renderHexagonsInHalf(true);
    renderHexagonsInHalf(false);
  }

  /**
   * Renders hexagons in either the top or bottom half of the game board.
   *
   * @param topHalf If true, renders hexagons in the top half. Otherwise, render in the bottom half.
   */
  private void renderHexagonsInHalf(boolean topHalf) {
    int longestRowIndex = model.getBoardSideLength() - 1;
    int offsetX = calculateOffsetX(longestRowIndex);
    int offsetY = calculateOffsetY(longestRowIndex);

    for (Coordinates coords : model.getInBoundsCoordinates()) {
      int row = coords.getFirstCoordinate();
      if ((topHalf && row < longestRowIndex) || (!topHalf && row >= longestRowIndex)) {
        int rowDistance = (int) (hexagonLength * Math.sqrt(3.0) / 2) * (longestRowIndex - row);

        Hexagon hexagon = renderHexagon(coords, rowDistance, offsetX, offsetY);
        hexagons.get(row).add(hexagon);
      }
    }
  }

  /**
   * Calculates the horizontal offset for rendering hexagons based on the longest row index.
   *
   * @param longestRowIndex The index of the longest row in the game board.
   * @return The horizontal offset.
   */
  private int calculateOffsetX(int longestRowIndex) {
    return getWidth() / 2 - (int) (longestRowIndex * (hexagonLength * Math.sqrt(3.0)));
  }

  /**
   * Calculates the vertical offset for rendering hexagons based on the longest row index.
   *
   * @param longestRowIndex The index of the longest row in the game board.
   * @return The vertical offset.
   */
  private int calculateOffsetY(int longestRowIndex) {
    return getHeight() / 2 - (int) (longestRowIndex * (int) (hexagonLength * 1.5));
  }

  /**
   * Renders a hexagon on the game board.
   *
   * @param coords      The coordinates of the hexagon, using the game's coordinate system.
   * @param rowDistance The spacing distance of the hexagon in rows.
   * @param offsetX     The horizontal offset.
   * @param offsetY     The vertical offset.
   * @return The rendered hexagon.
   */
  private Hexagon renderHexagon(Coordinates coords, int rowDistance, int offsetX, int offsetY) {
    return new Hexagon(
            coords.getSecondCoordinate() * hexagonLength * Math.sqrt(3.0) - rowDistance
                    + offsetX,
            coords.getFirstCoordinate() * (int) (hexagonLength * 1.5) + offsetY,
            Color.GRAY, coords, hexagonLength
    );
  }

  /**
   * Gets the Hexagon at the specified row and column.
   *
   * @param row The row index.
   * @param col The column index.
   * @return The Hexagon at the specified row and column.
   */
  public Hexagon getHexagon(int row, int col) {
    return hexagons.get(row).get(col);
  }

  /**
   * Gets the number of rows in the hexagonal board.
   *
   * @return The number of rows.
   */
  public int getNumRow() {
    return hexagons.size();
  }

  /**
   * Gets the number of columns in the specified row.
   *
   * @param row The row index.
   * @return The number of columns in the specified row.
   */
  public int getNumCol(int row) {
    return hexagons.get(row).size();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setBackground(new Color(72, 84, 75));

    for (List<Hexagon> row : hexagons) {
      for (Hexagon h : row) {
        h.draw(g, model.getDiscStateAt(h.getCoordinates()));

      }
    }
  }

  /**
   * Mouse listener for handling hexagon-related events.
   */
  private class HexagonalBoardMouseListener extends MouseAdapter {
    private HexagonalBoardView outerBoard;

    /**
     * Constructs a HexagonalBoardMouseListener with the given outer HexagonalBoardView.
     *
     * @param outerBoard The outer HexagonalBoardView.
     */
    private HexagonalBoardMouseListener(HexagonalBoardView outerBoard) {
      this.outerBoard = outerBoard;
    }

    @Override
    public void mousePressed(MouseEvent e) {
      handleHexagonClick();
      outerBoard.repaint();
    }

    /**
     * Handles mouse click events on hexagons for selecting (highlighting) hexagons that are
     * UNPLACED.
     */
    private void handleHexagonClick() {
      Point point = MouseInfo.getPointerInfo().getLocation();
      SwingUtilities.convertPointFromScreen(point, outerBoard);

      boolean hexagonClicked = false;

      for (int i = 0; i < getNumRow(); i++) {
        for (int j = 0; j < getNumCol(i); j++) {
          Hexagon hexagon = getHexagon(i, j);
          if (hexagon.getPolygon().contains(point) && hexagon.getColor() == Color.GRAY) {
            Coordinates coordinates = hexagon.getCoordinates();
            DiscState discState = model.getDiscStateAt(coordinates);

            if (discState == DiscState.UNPLACED) {
              handleUnplacedDisc(hexagon);
            } else {
              handlePlacedDisc(coordinates);
            }

            hexagonClicked = true;
          }
        }
      }

      if (!hexagonClicked && temporaryHex != null) {
        temporaryHex.setColor(Color.GRAY);
        temporaryHex = null;
      }
    }

    /**
     * Handles the case when an unplaced disc hexagon is clicked.
     *
     * @param hexagon The clicked hexagon.
     */
    private void handleUnplacedDisc(Hexagon hexagon) {
      if (temporaryHex != null) {
        temporaryHex.setColor(Color.GRAY);
      }

      temporaryHex = hexagon;
      hexagon.setColor(new Color(255, 180, 199));
    }

    /**
     * Handles the case when a placed disc hexagon is clicked.
     *
     * @param coordinates The coordinates of the clicked hexagon.
     */
    private void handlePlacedDisc(Coordinates coordinates) {
      if (temporaryHex != null) {
        temporaryHex.setColor(Color.GRAY);
        temporaryHex = null;
      }
    }
  }

  /**
   * Key listener for handling keyboard-related events.
   */
  private class HexagonalBoardKeyListener extends KeyAdapter {
    private HexagonalBoardView outerBoard;

    /**
     * Constructs a HexagonalBoardKeyListener with the given outer HexagonalBoardView.
     *
     * @param outerBoard The outer HexagonalBoardView.
     */
    private HexagonalBoardKeyListener(HexagonalBoardView outerBoard) {
      this.outerBoard = outerBoard;
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyChar() == 'p') {
        //System.out.println("Passed your turn.");
        featuresListener.pass();
      } else if (e.getKeyChar() == KeyEvent.VK_ENTER && temporaryHex != null) {
        Coordinates coordinates = temporaryHex.getCoordinates();
        featuresListener.executeMove(coordinates);
      }
    }
  }
}
