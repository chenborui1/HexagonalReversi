package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ModelStateFeatures;

/**
 * An abstract class representing the core functionality of a Reversi game model.
 * This class provides a foundation for implementing specific Reversi game models with customizable
 * rules and behaviors. It includes methods for game initialization, player moves,
 * game state tracking,
 * and more.
 */

public abstract class AbstractReversi implements ReversiModel {

  /**
   * The game board representing the positions of game pieces.
   */
  protected HashMap<ICoordinate, CellType> gameBoard;

  /*
   * The game board representing the initial state of the game.
   */
  //protected HashMap<Coordinate, CellType> startingGameBoard;

  /**
   * The size of the game board (number of rows and columns).
   */
  protected final int boardSize;

  /**
   * The current player's turn represented by their game piece color.
   * The field currentPlayerTurn is an invariant because it can only be
   * one of CellType. WHITE or CellType. BLACK. This invariant ensures
   * that the player's turn is always either white or black and
   * maintains the integrity of the game's current state.
   */

  protected CellType currentPlayerTurn;

  /**
   * The previous player's turn represented by their game piece color.
   */

  protected CellType previousPlayerTurn;

  /**
   * The number of consecutive skip moves by players.
   */
  protected int skipTimes;

  /**
   * A flag indicating whether the game has started or not.
   */
  protected boolean gameStarted; // A flag indicating whether the game has started or not.

  /**
   * The current state of the game (e.g., playing or over).
   */
  protected GameState gameState;




  private List<ModelStateFeatures> listeners = new ArrayList<>();


  /**
   * Creates a Reversi game with a specified initial game board.
   * Used to create more complex board and play the game using the supplied board
   *
   * @param board The initial game board represented as a map of coordinates and cell types.
   */

  public AbstractReversi(HashMap<ICoordinate, CellType> board, CellType playerTurn) {
    this.gameBoard = board;
    this.gameStarted = true;
    this.gameState = GameState.PLAYING;
    this.currentPlayerTurn = playerTurn;


    // Determine the board size based on the provided board's coordinates
    int givenBoardSize = 0;

    for (ICoordinate key : board.keySet()) {

      if (key.getRow() > givenBoardSize) {
        givenBoardSize = key.getRow();
      }
    }

    this.boardSize = givenBoardSize + 1;


  }

  /**
   * Creates a Reversi game with a specified board size.
   *
   * @param boardSize The size of the game board (number of rows and columns).
   */

  public AbstractReversi(int boardSize) {
    this.gameBoard = new HashMap<>();

    this.gameStarted = false;
    this.boardSize = boardSize;
    this.gameState = GameState.OVER;


  }

  @Override
  public void addModelStateListener(ModelStateFeatures listener) {
    listeners.add(listener);

  }

  // Abstract methods for game initialization and player moves


  public abstract void initializeGame() throws IllegalArgumentException;

  //Abstract make move method
  @Override
  public abstract void makeMove(CellType player, int row, int column)
          throws IllegalArgumentException;


  //Abstract start game method
  @Override
  public abstract void skip(CellType player) throws IllegalArgumentException;


  // Methods for game board information and manipulation

  @Override
  public HashMap<ICoordinate, CellType> getGameBoard() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }


    //Perform a deep copy

    // Create a new HashMap for the deep copy
    HashMap<ICoordinate, CellType> gameBoardCopy = new HashMap<>();

    // Iterate through the original gameBoard and perform a deep copy
    for (Map.Entry<ICoordinate, CellType> entry : this.gameBoard.entrySet()) {
      // Deep copy the Coordinate
      Coordinate keyCopy = new Coordinate(entry.getKey().getCol(), entry.getKey().getRow());

      // Deep copy the CellType (assuming it's a class with a copy constructor)
      CellType valueCopy = entry.getValue(); // Use the copy constructor

      // Add the deep-copied entry to the copy of the gameBoard
      gameBoardCopy.put(keyCopy, valueCopy);
    }

    return gameBoardCopy;

  }

  @Override
  public int getBoardSize() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }

    return this.boardSize;
  }


  @Override
  public CellType getCurrentPlayerTurn() {

    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (this.gameState != GameState.PLAYING) {
      throw new IllegalStateException("Game has concluded");
    }

    return this.currentPlayerTurn;
  }


  @Override
  public boolean isGameOver() {

    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }


    return this.gameState == GameState.OVER;
  }

  @Override
  public char getWinner() {

    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (this.gameState != GameState.OVER) {
      throw new IllegalStateException("Can't get winner if game is still in progress");
    }

    if (isGameOver()) {


      if (this.getScores()[0] > this.getScores()[1]) {
        return 'B';
      }

      if (this.getScores()[0] < this.getScores()[1]) {
        return 'W';
      } else {
        return 'N';
      }
    } else {
      throw new IllegalStateException("Cannot get winner when game is ongoing");
    }
  }

  @Override
  public int[] getScores() {

    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }

    int[] arr = new int[2];

    int blackScore = 0;
    int whiteScore = 0;
    for (int column = 0; column < boardSize; column++) {
      for (int row = 0; row < boardSize; row++) {

        if (this.gameBoard.get(new Coordinate(column, row)) == CellType.BLACK) {
          blackScore++;
        }

        if (this.gameBoard.get(new Coordinate(column, row)) == CellType.WHITE) {
          whiteScore++;
        }


      }
    }

    arr[0] = blackScore;
    arr[1] = whiteScore;


    return arr;
  }

  @Override
  public CellType nextPlayer() {

    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (this.gameState != GameState.PLAYING) {
      throw new IllegalStateException("Game has concluded");
    }

    if (this.currentPlayerTurn == CellType.WHITE) {
      return CellType.BLACK;
    } else {
      return CellType.WHITE;
    }
  }


  @Override
  public CellType getContent(int row, int column) {
    return this.gameBoard.get(new Coordinate(column, row));
  }

  @Override
  public boolean canMakeMove(int row, int column) {
    return validateMove(row, column);
  }

  @Override
  public boolean playerLegalMoves() {

    for (int row = 0; row < boardSize; row++) {
      for (int column = 0; column < boardSize; column++) {
        if (canMakeMove(row, column)) {
          return true;
        }
      }
    }

    return false;
  }




  //Starting pieces positions may be different for other boards
  protected abstract void startingPiecePositions(int middle);

  //Verify moving disk action type is legal
  protected abstract boolean validateMove(int row, int column);

  protected void notifyPlayerChange(CellType currPlayer) {
    for (ModelStateFeatures listener : listeners) {
      listener.handlePlayerChange(currPlayer);
    }
  }

  protected void notifyGameBoardChange() {
    for (ModelStateFeatures listener : listeners) {
      listener.handleGameBoardChange();
    }
  }



  protected void notifyGameOver() {
    for (ModelStateFeatures listener : listeners) {
      listener.handleGameOver();
    }
  }

  protected void notifyGameStart() {
    for (ModelStateFeatures listener : listeners) {
      listener.handleInitializeGame(CellType.BLACK);
    }
  }
}
