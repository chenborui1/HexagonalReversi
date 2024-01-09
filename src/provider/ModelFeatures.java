package provider;


/**
 * Represents the features that a Reversi model can provide to its controller.
 */
public interface ModelFeatures {

  /**
   * Notifies the listener that the model state has changed.
   */
  void notifyModelStateChange();


  /**
   * Notifies the listener that it's the turn of a specific player.
   *
   * @param playerColor The color of the player whose turn it is.
   */
  void notifyPlayerChange(PlayerColor playerColor);
}

