package model;

import org.junit.Assert;
import org.junit.Test;


import java.util.HashMap;

import view.ReversiTextualView;
import view.TextualView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test class for BasicReversi model implementation.
 */

public class BasicReversiTest {

  //Standard board with a board size of 7
  //Board is not rigged
  BasicReversi board = new BasicReversi(7);

  //Players are supplied with their respective color
  CellType black = CellType.BLACK;
  CellType white = CellType.WHITE;

  //Can be used to visualize the current game board for testing
  private final TextualView view = new ReversiTextualView(board);

  @Test
  public void testInitialzeGame() {

    //Tests to see if the board initialized correctly


    String viewExpected =

            "   _ _ _ _ \n" +
                    "  _ _ _ _ _ \n" +
                    " _ _ X O _ _ \n" +
                    "_ _ O _ X _ _ \n" +
                    " _ _ X O _ _  \n" +
                    "  _ _ _ _ _   \n" +
                    "   _ _ _ _    ";


    assertEquals(viewExpected, view.toString());


  }

  @Test
  public void testCorrectPlayerTurn() {


    //Black will always make the first move
    board.makeMove(black, 1, 4);

    //Attempt for black to make another move

    assertThrows(IllegalStateException.class, () -> board.makeMove(black, 0, 5));


  }

  @Test
  public void testCorrectPassing() {


    //Black will always make the first move
    board.makeMove(black, 1, 4);

    //Now make white pass their move

    board.skip(white);

    //Black should be the one to make their move
    //But we get an exception if white tries to make another move

    assertThrows(IllegalStateException.class, () -> board.makeMove(white, 0, 5));

    System.out.print(view);

    //Make black player make a move successfully
    board.makeMove(black, 4, 1);

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


    board.makeMove(black, 1, 4);


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

    assertThrows(IllegalArgumentException.class, () -> board.makeMove(black, 0, 0));


  }

  @Test
  public void testDoubleRowFlippedTiles() {

    //Creates a standard board with equal pieces of both colors in the center


    board.makeMove(black, 1, 4);
    board.makeMove(white, 0, 5);
    board.makeMove(black, 0, 4);
    board.makeMove(white, 4, 1);
    board.makeMove(black, 2, 2);

    HashMap<ICoordinate, CellType> riggedBoard = board.getGameBoard();

    riggedBoard.put(new Coordinate(1, 2), CellType.WHITE);


    BasicReversi newBoard = new BasicReversi(riggedBoard, CellType.BLACK);

    //Since first turn is always black we have to skip his turn for white to go first
    //after using rigged board
    newBoard.skip(black);

    TextualView newView = new ReversiTextualView(newBoard);

    //Textual view of the game board before making the move

    /*

        _ X O _
       _ _ X _ _
      O X X X _ _
     _ _ O _ X _ _
      _ O O O _ _
       _ _ _ _ _
        _ _ _ _

     */

    //Attempts to place a white piece on row 2 column 5
    //New updated and flipped board should be represented below

    newBoard.makeMove(white, 2, 5);

    /*

        _ X O _
       _ _ X _ _
      O O O O O _
     _ _ O _ O _ _
      _ O O O _ _
       _ _ _ _ _
        _ _ _ _

     */


    String viewExpected =

            "   _ X O _ \n" +
                    "  _ _ X _ _ \n" +
                    " O O O O O _ \n" +
                    "_ _ O _ O _ _ \n" +
                    " _ O O O _ _  \n" +
                    "  _ _ _ _ _   \n" +
                    "   _ _ _ _    ";


    assertEquals(viewExpected, newView.toString());

  }

  @Test
  public void testValidMoveTilesFlipped() {

    //Creates a standard board with equal pieces of both colors in the center


    board.makeMove(black, 1, 4);


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

    //Attempts to place a white piece on row 4 column 1
    //New updated and flipped board should be represented below

    board.makeMove(white, 4, 1);


    /*

        _ _ _ _
       _ _ X _ _
      _ _ X X _ _
     _ _ O _ X _ _
      _ O O O _ _
       _ _ _ _ _
        _ _ _ _

     */


    String viewExpected =

            "   _ _ _ _ \n" +
                    "  _ _ X _ _ \n" +
                    " _ _ X X _ _ \n" +
                    "_ _ O _ X _ _ \n" +
                    " _ O O O _ _  \n" +
                    "  _ _ _ _ _   \n" +
                    "   _ _ _ _    ";

    assertEquals(viewExpected, view.toString());


  }

  @Test
  public void testGameBoardDeepCopy() {


    // Create a deep copy of the game board
    HashMap<ICoordinate, CellType> gameBoardCopy = board.getGameBoard();

    // Check if the game board and its copy are equal
    assertEquals(board.getGameBoard(), gameBoardCopy);

    // Modify the original game board
    board.makeMove(black, 1, 4);

    // Check if the copy remains the same
    assertNotEquals(board.getGameBoard(), gameBoardCopy);
  }

  @Test
  public void testGetBoardSize() {


    int expectedBoardSize = 7; // Assuming an 11x11 board

    assertEquals(expectedBoardSize, board.getBoardSize());
  }


  @Test
  public void testGetCurrentPlayerTurn() {


    CellType expectedInitialPlayer = CellType.BLACK; // Assuming black plays first

    assertEquals(expectedInitialPlayer, board.getCurrentPlayerTurn());

    // Make a move and test if the current player changes
    board.makeMove(black, 1, 4);

    CellType expectedNextPlayer = CellType.WHITE;
    assertEquals(expectedNextPlayer, board.getCurrentPlayerTurn());
  }

  @Test
  public void testIsGameOver() {


    // Simulate a game and check if it ends properly
    board.makeMove(black, 1, 4);
    board.makeMove(white, 4, 1);

    //If both players skip then game has concluded

    board.skip(black);
    board.skip(white);

    assertTrue(board.isGameOver());
  }

  @Test
  public void testGetWinner() {


    // Simulate a game and check if it ends properly
    board.makeMove(black, 1, 4);
    board.makeMove(white, 4, 1);

    //If both players skip then game has concluded

    board.skip(black);
    board.skip(white);

    assertTrue(board.isGameOver());

    //Can only get winner if game is over
    assertEquals('N', board.getWinner()); // Expected to be a draw
  }

  @Test
  public void testGetScores() {


    // Simulate a game with a known outcome
    board.makeMove(black, 1, 4);
    board.makeMove(white, 4, 1);

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


    int[] expectedScores = {4, 4}; // Expected scores after the game

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


  @Test
  public void testGetContent() {


    //First player is black
    assertEquals(CellType.WHITE, board.getContent(3, 2)); //Checking for white cell

    assertNull(board.getContent(0, 0)); //Checking for null out of bounds

    assertEquals(CellType.BLACK, board.getContent(2, 3)); //Checking for black cell

    assertEquals(CellType.EMPTY, board.getContent(2, 2)); //Checking for empty cell


  }

  @Test
  public void testCanMakeMoves() {

    //Testing black player moves since black is current player when game starts
    Assert.assertFalse(board.canMakeMove(5, 4));
    Assert.assertTrue(board.canMakeMove(4, 4));
    Assert.assertFalse(board.canMakeMove(0, 0));
    Assert.assertFalse(board.canMakeMove(0, 4));


  }


  @Test
  public void testCheckPlayerLegalMoves() {


    //Rigging board

    HashMap<ICoordinate, CellType> riggedBoard = board.getGameBoard();

    riggedBoard.put(new Coordinate(2, 2), CellType.BLACK);
    riggedBoard.put(new Coordinate(5, 2), CellType.BLACK);
    riggedBoard.put(new Coordinate(4, 4), CellType.BLACK);
    riggedBoard.put(new Coordinate(1, 4), CellType.BLACK);
    riggedBoard.put(new Coordinate(2, 5), CellType.BLACK);
    riggedBoard.put(new Coordinate(4, 1), CellType.BLACK);


    BasicReversi newBoard = new BasicReversi(riggedBoard, CellType.BLACK);

    //What the board looks like

    TextualView view = new ReversiTextualView(newBoard);
    System.out.println(view.toString());


    //Checking if black has any legal moves left
    Assert.assertFalse(newBoard.playerLegalMoves());


  }


}