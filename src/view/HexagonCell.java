package view;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Objects;

import model.CellType;
import model.Coordinate;

import static java.awt.Color.RED;

/**
 * The HexagonCell class represents a hexagonal cell in the Reversi game board.
 * It extends JPanel to provide GUI rendering capabilities.
 */
public class HexagonCell extends JPanel {

  private final int row;
  private final int col;


  private final int width;
  private final CellType cellType;
  private final int halfWidth;
  private final int[] xPoints;
  private final int[] yPoints;
  private final int initialYCoordinate;
  private final int initialXCoordinate;
  public boolean mouseClick;

  private boolean hintsOn;
  private int flipped;

  /**
   * Constructs a HexagonCell with the specified row, column, width, gap, and cell type.
   *
   * @param row      The row index of the cell.
   * @param col      The column index of the cell.
   * @param width    The width of the hexagon.
   * @param gap      The gap between hexagons.
   * @param cellType The type of the cell (BLACK, WHITE, or EMPTY).
   */
  public HexagonCell(int row, int col, int width, int gap, CellType cellType) {



    this.row = row;
    this.col = col;
    this.width = width;
    this.cellType = Objects.requireNonNull(cellType);
    this.halfWidth = width + 3 / 2;
    int gap1 = gap * this.halfWidth;
    this.initialYCoordinate = this.row * 45;
    this.initialXCoordinate = this.col * (this.halfWidth * 2) + gap1 + this.halfWidth;
    this.mouseClick = false;


    // Define the hexagon's vertices
    this.xPoints = new int[]{initialXCoordinate, initialXCoordinate + halfWidth,
                             initialXCoordinate + halfWidth, initialXCoordinate,
                             initialXCoordinate - halfWidth, initialXCoordinate - halfWidth};
    this.yPoints = new int[]{initialYCoordinate, initialYCoordinate + width / 2,
                             initialYCoordinate + width / 2 + width, initialYCoordinate
                             + width + width,
                             initialYCoordinate + width / 2 + width, initialYCoordinate
                             + width / 2};
  }

  /**
   * Draws the hexagon cell on the provided Graphics context.
   *
   * @param g The Graphics context on which to draw the hexagon.
   */
  public void draw(Graphics g) {

    Graphics2D g2d = (Graphics2D) g;


    if (this.mouseClick) {

      g2d.setColor(Color.cyan);
    } else {
      g2d.setColor(Color.lightGray);
    }


    g2d.fillPolygon(xPoints, yPoints, 6);
    g2d.setColor(Color.black);
    g2d.drawPolygon(xPoints, yPoints, 6);
    if (hintsOn && mouseClick) {
      g2d.setColor(RED);
      g2d.drawString(Integer.toString(flipped),
              initialXCoordinate - halfWidth / 2, initialYCoordinate + halfWidth / 2);
    }
    // render the disc
    if (cellType.equals(CellType.BLACK)) {
      g2d.setColor(Color.BLACK);
      g2d.fillOval(initialXCoordinate - halfWidth / 2, initialYCoordinate + halfWidth / 2,
              width - 2, width - 2);
    }

    if (cellType.equals(CellType.WHITE)) {
      g2d.setColor(Color.white);
      g2d.fillOval(initialXCoordinate - halfWidth / 2, initialYCoordinate + halfWidth / 2,
              width - 2, width - 2);
    }




  }


  /**
   * Checks if a given point is within the bounds of the hexagon, indicating a mouse click.
   *
   * @param mouseLocation The coordinates of the mouse click.
   * @return True if the mouse click is within the hexagon; false otherwise.
   */
  public boolean isMouseClick(Point2D mouseLocation) {
    // minus to prevent click two tiles at the same time
    return mouseLocation.getX() > initialXCoordinate - halfWidth - 1
            && mouseLocation.getX() < initialXCoordinate + halfWidth
            && mouseLocation.getY() > initialYCoordinate - 1
            && mouseLocation.getY() < initialYCoordinate + width + width;
  }

  /**
   * Gets the coordinate of the hexagon cell.
   *
   * @return The coordinate of the hexagon cell.
   */
  public Coordinate getCoordinate() {
    return new Coordinate(this.col, this.row);
  }

  /**
   * Gets the current state of the mouse click status.
   *
   * @return True if the hexagon has been clicked; false otherwise.
   */
  public boolean getClicked() {
    return this.mouseClick;
  }

  /**
   * Toggles the mouse click status of the hexagon.
   */
  public void click() {
    this.mouseClick = !this.mouseClick;
  }

  public void toggleHint() {
    hintsOn = !hintsOn;
  }

  public void setFlipped(int i) {
    this.flipped = i;
  }
}
