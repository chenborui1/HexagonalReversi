package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import strategy.CaptureMost;
import strategy.ReversiStrategy;


/**
 * Test class for CaptureMost strategy using mock models.
 */
public class StrategyMockTests {

  //Empty transcript
  List<String> transcript = new ArrayList<>();

  CellType black = CellType.BLACK;
  CellType white = CellType.WHITE;


  @Test
  public void testStrategyCheckedAllPossibleCells() {


    ReadonlyReversiModel initializeModel = new BasicReversi(11);


    //New mock model
    MockModelStrategyOne mockModel = new MockModelStrategyOne(transcript, initializeModel);


    //Use capture most strategy for testing
    ReversiStrategy captureMost = new CaptureMost();

    //Get the best
    Coordinate bestMove = captureMost.chooseCoordinate(mockModel, black);


    Assert.assertTrue(transcript.contains("Legal moves: Column " + 6 + " Row: " + 3));
    Assert.assertTrue(transcript.contains("Legal moves: Column " + 3 + " Row: " + 6));
    Assert.assertTrue(transcript.contains("Legal moves: Column " + 4 + " Row: " + 4));
    Assert.assertTrue(transcript.contains("Legal moves: Column " + 4 + " Row: " + 7));
    Assert.assertTrue(transcript.contains("Legal moves: Column " + 7 + " Row: " + 4));
    Assert.assertTrue(transcript.contains("Legal moves: Column " + 6 + " Row: " + 6));


  }


  @Test
  public void testStrategyCheckedAllLegalMoves() {


    ReadonlyReversiModel initializeModel = new BasicReversi(11);

    HashMap<ICoordinate, CellType> board = initializeModel.getGameBoard();


    //New mock model
    MockModelStrategyOne mockModel = new MockModelStrategyOne(transcript, initializeModel);


    //Use capture most strategy for testing
    ReversiStrategy captureMost = new CaptureMost();

    //Get the best
    Coordinate bestMove = captureMost.chooseCoordinate(mockModel, black);


    //Checks for every key in the hashmap
    //Checks every single cell on the board including null cells
    for (ICoordinate key : board.keySet()) {
      Assert.assertTrue(transcript.contains("Cell checked: Column " + key.getCol() + " Row: "
              + key.getRow()));
    }


  }


  @Test
  public void testStrategyGotMostValuableByBreakingTie() {


    //Empty transcript
    List<String> transcript = new ArrayList<>();

    ReadonlyReversiModel initializeModel = new BasicReversi(11);


    //New mock model
    MockModelStrategyOne mockModel = new MockModelStrategyOne(transcript, initializeModel);


    //Use capture most strategy for testing
    ReversiStrategy captureMost = new CaptureMost();

    //Get the best
    Coordinate bestMove = captureMost.chooseCoordinate(mockModel, black);


    //Expected valuable coordinate
    Coordinate expectedValuableCoordinate = new Coordinate(6, 3);


    Assert.assertTrue(transcript.contains("Most valuable move is: Column: " + 6 + " Row: " + 3));


    System.out.println(transcript);
  }
}
