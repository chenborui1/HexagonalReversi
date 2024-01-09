package view;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Objects;

import model.CellType;
import model.Coordinate;

/**
 * The SquareCell class represents a square cell in the Reversi game board.
 * It extends JPanel to provide GUI rendering capabilities.
 */
public class SquareCell extends JPanel {


  /**
   * The SquareCell class represents a square cell in the Reversi game board.
   * It extends JPanel to provide GUI rendering capabilities.
   */


  private final int row;
  private final int col;
  private final int width;
  private final CellType cellType;
  private final int initialYCoordinate;
  private final int initialXCoordinate;
  private boolean mouseClick;

  /**
   * Constructs a SquareCell with the specified row, column, width, gap, and cell type.
   *
   * @param row      The row index of the cell.
   * @param col      The column index of the cell.
   * @param width    The width of the square.
   * @param gap      The gap between squares.
   * @param cellType The type of the cell (BLACK, WHITE, or EMPTY).
   */
  public SquareCell(int row, int col, int width, int gap, CellType cellType) {
    this.row = row;
    this.col = col;
    this.width = width;
    this.cellType = Objects.requireNonNull(cellType);
    this.initialYCoordinate = row * (width + gap);
    this.initialXCoordinate = col * (width + gap);
    this.mouseClick = false;
  }

  /**
   * Draws the square cell on the provided Graphics context.
   *
   * @param g The Graphics context on which to draw the square.
   */
  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    if (this.mouseClick) {
      g2d.setColor(Color.cyan);
    } else {
      g2d.setColor(Color.lightGray);
    }

    g2d.fillRect(initialXCoordinate, initialYCoordinate, width, width);
    g2d.setColor(Color.black);
    g2d.drawRect(initialXCoordinate, initialYCoordinate, width, width);

    // render the disc
    if (cellType.equals(CellType.BLACK)) {
      g2d.setColor(Color.BLACK);
      g2d.fillOval(initialXCoordinate, initialYCoordinate, width, width);
    }

    if (cellType.equals(CellType.WHITE)) {
      g2d.setColor(Color.white);
      g2d.fillOval(initialXCoordinate, initialYCoordinate, width, width);
    }
  }

  /**
   * Checks if a given point is within the bounds of the square, indicating a mouse click.
   *
   * @param mouseLocation The coordinates of the mouse click.
   * @return True if the mouse click is within the square; false otherwise.
   */
  public boolean isMouseClick(Point2D mouseLocation) {
    return mouseLocation.getX() > initialXCoordinate
            && mouseLocation.getX() < initialXCoordinate + width
            && mouseLocation.getY() > initialYCoordinate
            && mouseLocation.getY() < initialYCoordinate + width;
  }

  /**
   * Gets the coordinate of the square cell.
   *
   * @return The coordinate of the square cell.
   */
  public Coordinate getCoordinate() {
    return new Coordinate(this.col, this.row);
  }

  /**
   * Gets the current state of the mouse click status.
   *
   * @return True if the square has been clicked; false otherwise.
   */
  public boolean getClicked() {
    return this.mouseClick;
  }

  /**
   * Toggles the mouse click status of the square.
   */
  public void click() {
    // You can implement additional logic here if needed
    this.mouseClick = !this.mouseClick;
  }
}


