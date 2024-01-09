package model;

import java.util.HashMap;
import java.util.List;



/**
 * MockModelStrategyOne is a mock implementation of the ReadonlyReversiModel
 * interface. It records the transcript of coordinates inspected during the execution
 * of a strategy.
 */
public class MockModelStrategyOne extends BasicReversi implements ReadonlyReversiModel {

  /**
   * The transcript of coordinates inspected during the execution of the strategy.
   */
  protected final List<String> transcript;

  /**
   * The actual model that is being mocked.
   */
  protected final ReadonlyReversiModel model;

  /**
   * Predetermined most valuable move for initial game board of size 7 for black move.
   */
  private final Coordinate mostValuableMove = new Coordinate(6, 3);


  /**
   * Constructs a MockModelAllLegalMovesStrategyOne with the given transcript and model.
   *
   * @param transcript The list to store the transcript of inspected coordinates.
   * @param model      The model to mock.
   * @throws IllegalArgumentException if transcript or model is null.
   */
  public MockModelStrategyOne(List<String> transcript, ReadonlyReversiModel model) {
    super(model.getGameBoard(), model.getCurrentPlayerTurn());

    if (transcript == null || model == null) {
      throw new IllegalArgumentException("Invalid arguments");
    } else {
      this.transcript = transcript;
      this.model = model;

    }

  }

  // Overrides from ReadonlyReversiModel interface
  @Override
  public int getBoardSize() {
    transcript.add("getBoardSize");
    return model.getBoardSize();
  }

  @Override
  public HashMap<ICoordinate, CellType> getGameBoard() {
    transcript.add("getGameBoard");
    return model.getGameBoard();
  }

  @Override
  public CellType getCurrentPlayerTurn() {
    transcript.add("getCurrentPlayerTurn");
    return model.getCurrentPlayerTurn();
  }

  @Override
  public boolean isGameOver() {
    transcript.add("isGameOver");
    return model.isGameOver();
  }

  @Override
  public char getWinner() {
    transcript.add("getWinner");
    return model.getWinner();
  }

  @Override
  public int[] getScores() {
    transcript.add("getScores");
    return model.getScores();
  }

  @Override
  public CellType nextPlayer() {
    transcript.add("nextPlayer");
    return model.nextPlayer();
  }

  @Override
  public CellType getContent(int row, int column) {
    transcript.add("getContent");
    return model.getContent(row, column);
  }

  @Override
  public boolean playerLegalMoves() {
    transcript.add("playerLegalMoves");
    return model.playerLegalMoves();
  }

  /**
   * Mock model manipulates canMakeMove method to add
   * all legal moves at the current board state
   * to an arraylist of legal moves available.
   * Mock model manipulates method to add all cells on board that were checked.
   * Mock model records transcript of the predetermined move when it is found.
   * @param row    The row index of the cell.
   * @param column The column index of the cell.
   * @return
   */
  @Override
  public boolean canMakeMove(int row, int column) {
    Coordinate currentCoordinate = new Coordinate(column, row);

    transcript.add("Cell checked: Column " + currentCoordinate.getCol() + " Row: "
            + currentCoordinate.getRow());

    if (model.canMakeMove(row, column)) {
      transcript.add("Legal moves: Column " + currentCoordinate.getCol() + " Row: "
              + currentCoordinate.getRow());
      if (currentCoordinate.equals(mostValuableMove)) {
        transcript.add("Most valuable move is: Column: " + currentCoordinate.getCol() + " Row: "
                + currentCoordinate.getRow());
      }
    }


    return model.canMakeMove(row, column);
  }



}
