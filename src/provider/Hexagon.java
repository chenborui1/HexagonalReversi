package provider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;


import provider.Coordinates;
import provider.DiscState;

/**
 * Represents a single hexagon tile on the game board.
 */
public class Hexagon {

  private final Polygon poly;
  private Color color;
  private final Coordinates coordinates;
  public final int hexagonLength;
  public static final double THETA = (Math.PI / 180) * 60.0;

  /**
   * Constructs a pointy hexagon centered at (x, y) with the specified color.
   *
   * @param x             The column coordinate representing horizontal position on the game board.
   * @param y             The row coordinate representing vertical position on the game board.
   * @param color         The color of the hexagon.
   * @param coordinates   The coordinates of the hexagon on the game board,
   *                      specifying its position in the game's coordinate system.
   * @param hexagonLength The length of the hexagon's sides.
   */
  public Hexagon(double x, double y, Color color, Coordinates coordinates, int hexagonLength) {
    this.color = color;
    this.hexagonLength = hexagonLength;
    poly = new Polygon();
    this.coordinates = coordinates;
    for (int i = 0; i < 6; i++) {
      double angleRad = Math.abs(THETA) * i - Math.PI / 6.0;
      int x1 = (int) (x + hexagonLength * Math.cos(angleRad));
      int y1 = (int) (y + hexagonLength * Math.sin(angleRad));
      poly.addPoint(x1, y1);
    }
  }

  /**
   * Sets the color of the hexagon.
   *
   * @param clr The new color of the hexagon.
   */
  public void setColor(Color clr) {
    color = clr;
  }

  /**
   * Gets the polygon representation of the hexagon.
   *
   * @return The polygon representing the hexagon.
   */
  public Polygon getPolygon() {
    return poly;
  }

  /**
   * Gets the color of the hexagon.
   *
   * @return The color of the hexagon.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Gets the coordinates of the hexagon on the game board, using the game's coordinate system.
   *
   * @return The coordinates of the hexagon.
   */
  public Coordinates getCoordinates() {
    return coordinates;
  }

  /**
   * Draws the hexagon on the specified graphics context with the given disc state.
   *
   * @param g         The graphics context on which to draw the hexagon.
   * @param discState The state of the disc (BLACK, WHITE, or UNPLACED).
   */
  public void draw(Graphics g, DiscState discState) {
    g.setColor(color);
    g.fillPolygon(poly);

    g.setColor(Color.BLACK);
    g.drawPolygon(poly);

    if (discState == DiscState.BLACK || discState == DiscState.WHITE) {
      int centerX = (int) poly.getBounds().getCenterX();
      int centerY = (int) poly.getBounds().getCenterY();

      int circleRadius = hexagonLength / 2;
      if (discState == DiscState.BLACK) {
        g.setColor(Color.BLACK);
      } else {
        g.setColor(Color.WHITE);
      }

      g.fillOval(centerX - circleRadius, centerY - circleRadius, 2 * circleRadius,
              2 * circleRadius);
    }
  }
}

