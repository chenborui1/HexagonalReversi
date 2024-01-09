package provider;


import java.io.IOException;

/**
 * The ReversiTextualView class represents a textual view of a Reversi game board. It implements
 * the TextualView interface and provides a textual representation of the Reversi game state.
 * This view displays the game board as a grid of 'X' for black discs, 'O' for white discs, and
 * '_' for unplaced cells. The grid is divided into two halves, representing the upper and lower
 * parts of the board.
 */

public class ReversiTextualView implements TextualView {
  private final ReadOnlyReversiModel model;
  private final Appendable appendable;

  /**
   * Constructs a ReversiTextualView with the given model and appendable object.
   *
   * @param model      The ReadOnlyReversiModel representing the game state to be displayed.
   * @param appendable The Appendable object to which the textual representation is appended.
   * @throws IllegalArgumentException if the model is null.
   */

  public ReversiTextualView(ReadOnlyReversiModel model, Appendable appendable) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
    this.appendable = appendable;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(renderTopHalf());
    sb.append(renderBottomHalf());
    return sb.toString();
  }

  private String renderTopHalf() {
    StringBuilder sb = new StringBuilder();
    int longestRowIndex = model.getBoardSideLength() - 1;
    int lastRowDistance = -1;

    for (Coordinates coords : model.getInBoundsCoordinates()) {
      int row = coords.getFirstCoordinate();
      DiscState discState = model.getDiscStateAt(coords);
      if (row < longestRowIndex) {
        int rowDistance = longestRowIndex - row;

        if (rowDistance != lastRowDistance) {
          lastRowDistance = rowDistance;
          for (int i = 0; i < rowDistance; i++) {
            sb.append(" ");
          }
        }
        if (discState == DiscState.BLACK) {
          sb.append("X ");
        } else if (discState == DiscState.WHITE) {
          sb.append("O ");
        } else {
          sb.append("_ ");
        }
      }

      if (coords.getSecondCoordinate() == (model.getBoardSideLength() * 2 - 2)) {
        if (row != longestRowIndex - 1) {
          sb.append("\n");
        }
      }
    }
    return sb.toString();
  }

  private String renderBottomHalf() {
    StringBuilder sb = new StringBuilder();
    int longestRowIndex = model.getBoardSideLength() - 1;
    int lastRowDistance = 0;

    for (Coordinates coords : model.getInBoundsCoordinates()) {
      int row = coords.getFirstCoordinate();
      DiscState discState = model.getDiscStateAt(coords);

      if (row >= longestRowIndex) {
        int rowDistance = row - longestRowIndex;

        if (rowDistance != lastRowDistance) {
          sb.append("\n");
          lastRowDistance = rowDistance;
          for (int i = 0; i < rowDistance; i++) {
            sb.append(" ");
          }
        }

        if (discState == DiscState.BLACK) {
          sb.append("X ");
        } else if (discState == DiscState.WHITE) {
          sb.append("O ");
        } else {
          sb.append("_ ");
        }
      }
    }
    return sb.toString();
  }


  @Override
  public void render() throws IOException {
    appendable.append(toString());
  }
}

