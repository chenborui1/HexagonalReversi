package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import strategy.CaptureMost;
import strategy.ReversiStrategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for CaptureMost strategy implementation.
 */
public class CaptureMostTest {
  ReadonlyReversiModel board = new BasicReversi(7);


  CellType black = CellType.BLACK;
  CellType white = CellType.WHITE;


  @Test
  public void testInitialBoardStrategyBreakTie() {

    //For board size of 7

    ReversiStrategy captureMost = new CaptureMost();

    Coordinate bestMove = captureMost.chooseCoordinate(board, black);


    //Top most takes precedence first

    assertEquals(new Coordinate(4, 1), bestMove);
    assertNotEquals(new Coordinate(2, 2), bestMove);
  }

  @Test
  public void testNonTrivialBoardStrategy() {

    //For board size of 11
    BasicReversi board = new BasicReversi(11);

    // Initial moves
    board.makeMove(CellType.BLACK, 6, 3);
    board.makeMove(CellType.WHITE, 6, 2);
    board.makeMove(CellType.BLACK, 7, 4);
    board.makeMove(CellType.WHITE, 3, 6);
    board.makeMove(CellType.BLACK, 4, 4);
    board.makeMove(CellType.WHITE, 6, 6);
    board.makeMove(CellType.BLACK, 4, 7);


    HashMap<ICoordinate, CellType> riggedBoard = board.getGameBoard();

    ReadonlyReversiModel newBoard = new BasicReversi(riggedBoard, white);


    ReversiStrategy captureMost = new CaptureMost();

    Coordinate bestMove = captureMost.chooseCoordinate(newBoard, white);

    Coordinate expectedValuableMove = new Coordinate(4, 3);

    Assert.assertEquals(expectedValuableMove, bestMove);




  }


}
