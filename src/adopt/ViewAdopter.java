package adopt;

import controller.PlayerActionFeatures;
import provider.ReversiFrame;
import view.IReversiView;


/**
 * ViewAdopter is an adapter class that implements the IReversiView interface, adapting a
 * ReversiFrame to work as a view component in a Reversi game application. This adapter allows for
 * the integration of a specific ReversiFrame implementation with the broader application,
 * conforming to the IReversiView interface.
 */
public class ViewAdopter implements IReversiView {



  ReversiFrame delegate;


  /**
   * Constructs a ViewAdopter with a ModelAdopter, initializing a new ReversiFrame with the
   * given model.
   *
   * @param model The ModelAdopter that provides the model to the ReversiFrame.
   */
  public ViewAdopter(ModelAdopter model) {
    this.delegate = new ReversiFrame(model);
  }

  /**
   * Sets the visibility of the Reversi game panel. When set to true, the game panel
   * becomes visible.
   *
   * @param set A boolean indicating the desired visibility state.
   */
  @Override
  public void setVisible(boolean set) {
    delegate.getPanel().setVisible(true);

  }

  /**
   * Updates the view of the Reversi game. This method triggers a visual update of the game board
   * on the panel.
   */
  @Override
  public void updateView() {

    delegate.getPanel().updateBoard();
  }

  /**
   * Adds a listener for player actions to the game panel. The listener is adapted to the
   * FeatureAdopter class before being added, facilitating the interaction between the game's panel
   * and the player action features.
   *
   * @param listener The PlayerActionFeatures listener to be added to the game panel.
   */
  @Override
  public void addPlayerActionListener(PlayerActionFeatures listener) {


    delegate.getPanel().addFeaturesListener(new FeatureAdopter(listener));
  }
}
