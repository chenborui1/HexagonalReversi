package provider;

/**
 * Represents the possible states of a game board cell.
 */
public enum DiscState {
  UNPLACED("Unplaced"),
  BLACK("Black"),
  WHITE("White");

  private final String stateString;

  DiscState(String stateString) {
    this.stateString = stateString;
  }

  /**
   * Get the string representation of the disc state.
   *
   * @return The string representation.
   */
  public String getStateString() {
    return stateString;
  }
}

