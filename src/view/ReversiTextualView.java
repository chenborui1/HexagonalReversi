package view;


import java.io.IOException;
import java.util.HashMap;

import model.CellType;
import model.Coordinate;
import model.ICoordinate;
import model.ReversiModel;

/**
 * The `ReversiTextualView` class provides a text-based view for the Reversi game.
 * It renders the current state of the game board in a textual format.
 */
public class ReversiTextualView implements TextualView {

  private final ReversiModel model;
  private final Appendable output;


  /**
   * Constructs a textual view for the Reversi game.
   *
   * @param model The Reversi model to be rendered.
   */

  public ReversiTextualView(ReversiModel model) {
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
    int counter = 1;


    for (int rows = 0; rows < boardSize; rows++) {


      if (rows > (boardSize - 1) / 2) {


        view.append(howManySpaces(counter));

        counter++;

      }
      for (int columns = 0; columns < boardSize; columns++) {

        if (gameBoard.get(new Coordinate(columns, rows)) == null) {
          view.append(" ");
        } else {
          view.append(gameBoard.get(new Coordinate(columns, rows)).toString()).append(" ");

        }
      }

      if (rows != boardSize - 1) {
        view.append("\n");
      }
    }


    return view.toString();

  }


  /**
   * Generates a string with a specified number of spaces.
   *
   * @param spaces The number of spaces to generate.
   * @return A string containing the specified number of spaces.
   */
  private String howManySpaces(int spaces) {

    return " ".repeat(Math.max(0, spaces));
  }


}
