package view;

import java.io.IOException;
import java.util.HashMap;

import model.CellType;
import model.Coordinate;
import model.ICoordinate;
import model.ReversiModel;
import model.SquareReversi;
import view.TextualView;

/**
 * This class provides a text-based view for the square Reversi game.
 * It renders the current state of the game board in a textual format.
 */
public class SquareReversiTextualView implements TextualView {


  private final ReversiModel model;
  private final Appendable output;


  /**
   * Constructs a textual view for the Reversi game.
   *
   * @param model The Reversi model to be rendered.
   */

  public SquareReversiTextualView(SquareReversi model) {
    this.model = model;
    this.output = new StringBuilder();
  }

  /**
   * Renders the current state of the Reversi game by appending it to the output.
   *
   * @throws IOException If an I/O error occurs while rendering.
   */
  @Override
  public void render() throws IOException {

    output.append(toString());

  }

  /**
   * Generates a textual representation of the current Klondike game state.
   *
   * @return The text-based view of the Klondike game.
   */

  @Override
  public String toString() {
    StringBuilder view = new StringBuilder();
    int boardSize = model.getBoardSize();
    HashMap<ICoordinate, CellType> gameBoard = model.getGameBoard();

    for (int row = 0; row < boardSize; row++) {

      for (int col = 0; col < boardSize; col++) {


        view.append(gameBoard.get(new Coordinate(col, row)).toString()).append(" ");


      }

      view.append("\n");
    }


    return view.toString();

  }






}
