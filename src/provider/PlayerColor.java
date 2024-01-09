package provider;

/**
 * Enum representing player colors in a Reversi game.
 */
public enum PlayerColor {
  BLACK("Black"),
  WHITE("White");

  private final String colorString;

  /**
   * Constructs a PlayerColor enum with the given color string.
   *
   * @param colorString The color string.
   */
  PlayerColor(String colorString) {
    this.colorString = colorString;
  }

  /**
   * Get the color string associated with the PlayerColor.
   *
   * @return The color string.
   */
  public String getColorString() {
    return colorString;
  }
}

