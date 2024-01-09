package model;

/**
 * Represents a game piece in a reversi game.
 */
public enum CellType {


  /**
   * An empty cell type.
   */
  EMPTY,

  /**
   * The Black side of a game piece.
   */

  BLACK,

  /**
   * The White side of a game piece.
   */

  WHITE;


  @Override
  public String toString() {

    switch (this) {
      case EMPTY:

        return "_";
      case WHITE:
        return "O";

      case BLACK:
        return "X";


      default:
        throw new IllegalArgumentException("Unknown cell type");
    }
  }

}





