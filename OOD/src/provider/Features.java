package provider;

/**
 * Represents a set of features that can be triggered by external actions.
 */
public interface Features {

  /**
   * Executes a move based on the provided coordinates.
   *
   * @param coordinates The coordinates indicating the move.
   */
  void executeMove(Coordinates coordinates);

  /**
   * Signals the intention to pass the current turn.
   */
  void pass();
}

