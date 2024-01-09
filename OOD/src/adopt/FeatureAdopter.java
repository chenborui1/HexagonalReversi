package adopt;

import controller.PlayerActionFeatures;

import provider.Features;
import provider.Coordinates;

/**
 * This class serves as an adapter for the PlayerActionFeatures interface, implementing the
 * Features interface. It adapts the methods of the Features interface to work with a delegate
 * of type PlayerActionFeatures, thereby providing a bridge between the two interfaces.
 */
public class FeatureAdopter implements Features {

  PlayerActionFeatures delegate;

  /**
   * Constructs a FeatureAdopter object with a specific implementation of the PlayerActionFeatures.
   *
   * @param feature The PlayerActionFeatures implementation that this adapter will delegate to.
   */
  public FeatureAdopter(PlayerActionFeatures feature) {
    this.delegate = feature;
  }

  /**
   * Executes a move action by delegating the handling of move choices to the
   * PlayerActionFeatures implementation. It takes the coordinates and passes their individual
   * values to the delegate's handleMoveChosen method.
   *
   * @param coordinates The coordinates at which the move is to be executed.
   */
  @Override
  public void executeMove(Coordinates coordinates) {
    delegate.handleMoveChosen(coordinates.getFirstCoordinate(), coordinates.getSecondCoordinate());
  }

  /**
   * Executes a pass action by delegating to the PlayerActionFeatures implementation.
   * It calls the delegate's handlePassChosen method to handle the pass action.
   */
  @Override
  public void pass() {
    delegate.handlePassChosen();
  }
}
