package model;


import org.junit.Test;

import view.SquareReversiTextualView;
import view.TextualView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests for square reversi implementation.
 */
public class SquareReversiTest {

  //Standard board with a board size of 7
  //Board is not rigged
  SquareReversi board = new SquareReversi(8);

  //Players are supplied with their respective color
  CellType black = CellType.BLACK;
  CellType white = CellType.WHITE;

  //Can be used to visualize the current game board for testing
  private final TextualView view = new SquareReversiTextualView(board);


  @Test
  public void testInitialzeGame() {

    //Tests to see if the board initialized correctly


    String viewExpected =
            "_ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ \n" +
                    "_ _ _ X O _ _ _ \n" +
                    "_ _ _ O X _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ \n";


    assertEquals(viewExpected, view.toString());


  }

  @Test
  public void testCorrectPlayerTurn() {


    //Black will always make the first move
    board.makeMove(black, 3,5 );

    //Attempt for black to make another move

    assertThrows(IllegalStateException.class, () -> board.makeMove(black, 0, 5));


  }

  @Test
  public void testCorrectPassing() {


    //Black will always make the first move
    board.makeMove(black, 3, 5);

    //Now make white pass their move

    board.skip(white);

    //Black should be the one to make their move
    //But we get an exception if white tries to make another move

    assertThrows(IllegalStateException.class, () -> board.makeMove(white, 0, 5));

    System.out.print(view);

    //Make black player make a move successfully
    board.makeMove(black, 4, 2);

    //If both players skip game is over
    //Game state is over and no moves can be played anymore

    board.skip(white);
    board.skip(black);

    //Try to skip one more time but game is over
    assertThrows(IllegalStateException.class, () -> board.skip(white));

  }

  @Test
  public void testInvalidMoveNoTilesFlipped() {

    //Creates a standard board with equal pieces of both colors in the center


    board.makeMove(black, 3, 5);


    //Textual view of the game board before making the move

    /*

        _ _ _ _
       _ _ X _ _
      _ _ X X _ _
     _ _ O _ X _ _
      _ _ X O _ _
       _ _ _ _ _
        _ _ _ _

     */

    //Attempts to place a white piece on row 4 column 4
    //Attempted move location is marked with a '!' on the textual view below

    /*

        _ _ _ _
       _ _ X _ _
      _ _ X X _ _
     _ _ O _ X _ _
      _ _ X O ! _
       _ _ _ _ _
        _ _ _ _

     */
    assertThrows(IllegalStateException.class, () -> board.makeMove(white, 4, 4));


  }

  @Test
  public void testMoveOutOfBounds() {

    //Creates a standard board with equal pieces of both colors in the center


    //Attempts to move a disk out of dimensions of board size
    assertThrows(IllegalArgumentException.class, () -> board.makeMove(black, 100, 200));

    //Attempts to move a disk within dimensions of board size but is null
    //on coordinate plane

    assertThrows(IllegalStateException.class, () -> board.makeMove(black, 0, 0));


  }

  @Test
  public void testIsGameOver() {


    // Simulate a game and check if it ends properly
    //board.makeMove(black, 1, 4);
    //board.makeMove(white, 4, 1);

    //If both players skip then game has concluded

    board.skip(black);
    board.skip(white);

    assertTrue(board.isGameOver());
  }

  @Test
  public void testGetScores() {


    // Simulate a game with a known outcome
    board.makeMove(black, 3, 5);
    board.makeMove(white, 4, 5);

    //Current board view after moves

    /*

        _ _ _ _
       _ _ X _ _
      _ _ X X _ _
     _ _ O _ X _ _
      _ O O O _ _
       _ _ _ _ _
        _ _ _ _

     */


    int[] expectedScores = {0, 0}; // Expected scores after the game

    assertArrayEquals(expectedScores, board.getScores());
  }

  @Test
  public void testNextPlayer() {


    //First player is black
    assertEquals(CellType.WHITE, board.nextPlayer()); // After the first move

    //Black player turn
    board.skip(black);

    //White player turn
    //So next player should be black if we want the next player after black just skipped
    assertEquals(CellType.BLACK, board.nextPlayer()); // After the second move
  }
}
