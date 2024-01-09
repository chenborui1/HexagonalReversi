package view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import model.CellType;
import model.Coordinate;
import model.ICoordinate;
import model.ReadonlyReversiModel;

/**
 * The SquarePanel class represents a panel containing square cells to visualize the Reversi game
 * board.
 * It extends JPanel and handles mouse click events to interact with the square cells.
 */
public class SquarePanel extends JPanel {

  private HashMap<ICoordinate, CellType> board;
  private ArrayList<SquareCell> allSquares;
  private SquareCell squareClicked;

  private ReadonlyReversiModel model;

  private Coordinate squareClickedLogicalCoordinate;

  /**
   * Constructs a SquarePanel with the specified Reversi game model.
   *
   * @param model The ReadonlyReversiModel to visualize.
   */
  public SquarePanel(ReadonlyReversiModel model) {
    super();

    this.model = model;
    this.board = model.getGameBoard();
    this.setBackground(Color.DARK_GRAY);
    allSquares = createListOfSquares(board);
    this.setPreferredSize(new Dimension(700, 700));

    addMouseListener(new MyMouseListener());
  }

  /**
   * Updates the square grid based on the updated game board.
   */
  public void updateSquareGrid() {
    // Get updated board
    board = model.getGameBoard();

    ArrayList<SquareCell> updatedSquares = createListOfSquares(board);
    allSquares.clear();
    allSquares.addAll(updatedSquares);
    repaint();
  }

  /**
   * Mouse listener class for handling mouse events within the SquarePanel.
   */
  private class MyMouseListener extends MouseAdapter {
    @Override
    public void mouseReleased(MouseEvent e) {
      // Handle mouse click event
      int mouseX = e.getX();
      int mouseY = e.getY();

      boolean clickedOutside = true;

      // Check if the click is within any square
      for (SquareCell squareCell : allSquares) {
        if (squareCell.isMouseClick(new Point2D.Double(mouseX, mouseY))) {
          // Print the logical coordinates of the clicked square
          Coordinate coordinate = squareCell.getCoordinate();
          squareClickedLogicalCoordinate = coordinate;

          if (!squareCell.getClicked()) {
            System.out.println("Square clicked at logical coordinates: Column: "
                    + coordinate.getCol() + " Row: " + coordinate.getRow());

            squareCell.click();
            if (squareClicked != null) {
              squareClicked.click();
            }
            squareClicked = squareCell;
          } else {
            squareCell.click();
            squareClicked = null;
          }

          // Optionally, you can perform additional actions here
          break; // Stop checking once a square is clicked
        }
      }

      // Check if the click is within any square
      for (SquareCell squareCell : allSquares) {
        if (squareCell.isMouseClick(new Point2D.Double(mouseX, mouseY))) {
          clickedOutside = false;
        }
      }

      if (clickedOutside) {
        for (SquareCell squareCell : allSquares) {
          if (squareCell.getClicked()) {
            squareCell.click();
            squareClicked = null;
          }
        }
      }

      repaint();
    }
  }

  /**
   * Handles mouse release for testing.
   *
   * @param mouseX x position of the mouse.
   * @param mouseY y position of the mouse.
   * @return the square on which the mouse is.
   */
  public Coordinate handleMouseRelease(int mouseX, int mouseY) {
    for (SquareCell squareCell : allSquares) {
      if (squareCell.isMouseClick(new Point2D.Double(mouseX, mouseY))) {
        return squareCell.getCoordinate();
      }
    }
    return null;
  }

  /**
   * Gets the logical coordinates of the square that was clicked.
   *
   * @return The logical coordinates of the clicked square.
   */
  public Coordinate getSquareClickedCoordinates() {
    if (squareClicked != null) {
      return squareClickedLogicalCoordinate;
    }
    return null;
  }

  private ArrayList<SquareCell> createListOfSquares(HashMap<ICoordinate, CellType> board) {
    ArrayList<SquareCell> squareList = new ArrayList<>();

    // Determine the board size based on the provided board's coordinates
    int givenBoardSize = 0;

    for (ICoordinate key : board.keySet()) {
      if (key.getRow() > givenBoardSize) {
        givenBoardSize = key.getRow();
      }
    }

    int gap = 0;

    for (int row = 0; row < givenBoardSize + 1; row++) {
      for (int col = 0; col < givenBoardSize + 1; col++) {
        CellType boardCell = board.get(new Coordinate(col, row));

        if (boardCell != null) {
          SquareCell squareCell = new SquareCell(row, col, 70, gap, boardCell);
          squareList.add(squareCell);
        }
      }
    }

    return squareList;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (SquareCell squareCell : allSquares) {
      squareCell.draw(g2d);
    }
  }
}
