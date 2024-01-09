package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An implementation of the Reversi game with basic rules and functionality.
 * This class extends the abstract Reversi model and provides methods to initialize the game,
 * make moves, and perform game-specific operations.
 *
 * @see AbstractReversi
 */
public class BasicReversi extends AbstractReversi {


  /**
   * Constructs a BasicReversi game with a predefined initial game board.
   *
   * @param riggedBoard A pre-arranged game board to start the game with.
   */
  public BasicReversi(HashMap<ICoordinate, CellType> riggedBoard, CellType playerTurn) {
    super(riggedBoard, playerTurn);

    currentPlayerTurn = playerTurn;




  }

  /**
   * Constructs a BasicReversi game with the specified board size.
   *
   * @param boardSize The size of the game board (number of rows and columns).
   */
  public BasicReversi(int boardSize) {
    super(boardSize);

    initializeGame();

  }

  /**
   * Initializes the BasicReversi game by creating the game board and placing initial pieces.
   *
   * @throws IllegalArgumentException If the board size is invalid or the game has already started.
   */


  @Override
  public void initializeGame() throws IllegalArgumentException {

    if (this.gameStarted) {
      throw new IllegalStateException("Game has already started");
    }

    if (boardSize <= 4 || boardSize % 2 == 0) {
      throw new IllegalArgumentException("Invalid board size");
    }


    //Instantiate the hexagonal game board through for loops


    int initialWastedCounter = (boardSize - 1) / 2;


    // Populate the hexagonal grid based on boardSize
    for (int row = 0; row < (boardSize + 1) / 2; row++) {

      for (int col = 0; col < boardSize; col++) {


        Coordinate coordinate = new Coordinate(col, row);
        Coordinate positionReflect = new Coordinate((boardSize - 1)
                - col, (boardSize - 1) - row);


        if (initialWastedCounter > col) {
          this.gameBoard.put(coordinate, null); // Represents a "wasted space"
          this.gameBoard.put(positionReflect, null);
        } else {
          this.gameBoard.put(coordinate, CellType.EMPTY);
          this.gameBoard.put(positionReflect, CellType.EMPTY);
        }


      }

      if (this.gameBoard.get(new Coordinate(boardSize - 1, (boardSize - 1) / 2))
              == CellType.EMPTY) {
        break;
      }

      if (row < (boardSize - 1) / 2) {
        initialWastedCounter--;
      }

    }

    // Add initial pieces in the center of the hexagonal board
    int middle = (boardSize - 1) / 2;

    startingPiecePositions(middle);

    //Make the player turn black first

    this.currentPlayerTurn = CellType.BLACK;

    //Update gameStart field

    this.gameStarted = true;

    //Update game state

    this.gameState = GameState.PLAYING;



    //this.startingGameBoard = getGameBoard();

  }






  /**
   * Makes a move in the game by placing a game piece at the specified row and column.
   *
   * @param player The player's game piece color making the move.
   * @param row    The row where the game piece is placed.
   * @param column The column where the game piece is placed.
   * @throws IllegalStateException    If the game hasn't started, has concluded, or a player takes
   *                                  two turns in a row.
   * @throws IllegalArgumentException If the provided row or column is out of bounds or the move
   *                                  is not valid.
   */

  @Override
  public void makeMove(CellType player, int row, int column) {

    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (this.gameState != GameState.PLAYING) {
      throw new IllegalStateException("Game has concluded");
    }

    if (currentPlayerTurn != player) {
      throw new IllegalStateException("It is not your turn");
    }


    if (this.gameBoard.get(new Coordinate(column, row)) == null) {
      throw new IllegalArgumentException("Row or column out of bounds");
    }


    //Start to check move disk legal move

    if (validateMove(row, column)) {

      //Update board


      this.gameBoard.put(new Coordinate(column, row), this.currentPlayerTurn);

    } else {
      throw new IllegalStateException("Move is not valid");
    }

    //Flip the in between pieces

    flipPieces(getSamePieceBetween(column, row), new Coordinate(column, row));


    updatePlayerTurn();


    notifyPlayerChange(nextPlayer());
    updateGameBoard();






    //After making a move number of skips should be set back to 0

    this.skipTimes = 0;


  }

  private void updateGameBoard() {
    notifyGameBoardChange();
  }

  /**
   * Allows a player to skip their turn.
   *
   * @param player The player who wants to skip their turn.
   * @throws IllegalStateException If the game hasn't started, has concluded, or a player takes
   *                               two turns in a row.
   */

  @Override
  public void skip(CellType player) {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (this.gameState != GameState.PLAYING) {
      throw new IllegalStateException("Game has concluded");
    }

    if (currentPlayerTurn != player) {
      throw new IllegalStateException("It is not your turn");
    }


    this.skipTimes++;

    if (this.skipTimes == 2) {
      updateGameBoard();
      this.gameState = GameState.OVER;
      notifyGameOver();

    } else {
      updatePlayerTurn();
      notifyPlayerChange(nextPlayer());
      updateGameBoard();
    }
  }

  @Override
  public void startGame() {
    notifyGameStart();
  }

  private void updatePlayerTurn() {

    previousPlayerTurn = currentPlayerTurn;
    currentPlayerTurn = nextPlayer();



  }


  /**
   * Flips pieces between the placed game piece and another game piece in a specified direction.
   *
   * @param foundPieces      The list of coordinates of pieces to flip.
   * @param placedCoordinate The coordinate where the game piece is placed.
   */

  private void flipPieces(ArrayList<Coordinate> foundPieces, Coordinate placedCoordinate) {


    for (Coordinate coordinate : foundPieces) {


      //Direction is top left
      if (coordinate.getCol() == placedCoordinate.getCol() && coordinate.getRow()
              < placedCoordinate.getRow()) {


        for (int i = placedCoordinate.getRow() - 1; i > coordinate.getRow(); i--) {


          this.gameBoard.replace(new Coordinate(placedCoordinate.getCol(), i), currentPlayerTurn);

        }

      }

      //Direction is top right
      if (coordinate.getCol() > placedCoordinate.getCol() && coordinate.getRow()
              < placedCoordinate.getRow()) {

        int col = placedCoordinate.getCol() + 1;
        int row = placedCoordinate.getRow() - 1;

        while (col < coordinate.getCol() && row > coordinate.getRow()) {


          this.gameBoard.replace(new Coordinate(col, row), currentPlayerTurn);
          // Move to the next diagonal element
          col++;
          row--;
        }


      }

      //Direction is left
      if (coordinate.getCol() < placedCoordinate.getCol() && coordinate.getRow()
              == placedCoordinate.getRow()) {


        for (int i = placedCoordinate.getCol() - 1; i > coordinate.getCol(); i--) {


          this.gameBoard.replace(new Coordinate(i, placedCoordinate.getRow()), currentPlayerTurn);
        }

      }

      //Direction is right
      if (coordinate.getCol() > placedCoordinate.getCol() && coordinate.getRow()
              == placedCoordinate.getRow()) {


        for (int i = placedCoordinate.getCol() + 1; i < coordinate.getCol(); i++) {


          this.gameBoard.replace(new Coordinate(i, placedCoordinate.getRow()), currentPlayerTurn);

        }

      }

      //Direction is bottomRight
      if (coordinate.getCol() == placedCoordinate.getCol() && coordinate.getRow()
              > placedCoordinate.getRow()) {


        for (int i = placedCoordinate.getRow() + 1; i < coordinate.getRow(); i++) {


          this.gameBoard.replace(new Coordinate(placedCoordinate.getCol(), i), currentPlayerTurn);

        }

      }

      //Direction is bottomLeft
      if (coordinate.getCol() < placedCoordinate.getCol() && coordinate.getRow()
              > placedCoordinate.getRow()) {


        int col = placedCoordinate.getCol() - 1;
        int row = placedCoordinate.getRow() + 1;


        while (col > coordinate.getCol() && row < coordinate.getRow()) {


          this.gameBoard.replace(new Coordinate(col, row), currentPlayerTurn);

          // Move to the next diagonal element
          col--;
          row++;
        }


      }
    }


  }


  //For a basic Reversi board these are the starting piece positions

  /**
   * Determines the starting positions of game pieces for the BasicReversi board.
   *
   * @param middle The middle row/column index of the game board.
   */
  @Override
  protected void startingPiecePositions(int middle) {
    this.gameBoard.put(new Coordinate(middle, middle + 1), CellType.WHITE);
    this.gameBoard.put(new Coordinate(middle, middle - 1), CellType.BLACK);
    this.gameBoard.put(new Coordinate(middle - 1, middle), CellType.WHITE);
    this.gameBoard.put(new Coordinate(middle + 1, middle), CellType.BLACK);
    this.gameBoard.put(new Coordinate(middle - 1, middle + 1), CellType.BLACK);
    this.gameBoard.put(new Coordinate(middle + 1, middle - 1), CellType.WHITE);

  }


  //Helper method to validate a move disc move for the respective board

  /**
   * Validates a move to check if it's legal for the respective game board.
   *
   * @param row    The row where the game piece is placed.
   * @param column The column where the game piece is placed.
   * @return true if the move is valid, false otherwise.
   */
  @Override
  protected boolean validateMove(int row, int column) {

    Coordinate placePosition = new Coordinate(column, row);

    //Checks if the place position is empty or null
    if (this.gameBoard.get(placePosition) != CellType.EMPTY) {
      return false;
    }

    //Checks if there is at least one opponent disk adjacent to any
    // direction of the disk being placed
    //Also stores the coordinates of the adjacent disks that exist in an arraylist

    ArrayList<Coordinate> validAdjacentDisks = new ArrayList<>();
    ArrayList<String> validDirections = new ArrayList<>();

    for (Coordinate surroundingCoordinate : placePosition.getSurroundingCells()) {
      if (this.gameBoard.get(surroundingCoordinate) == nextPlayer()) {
        validAdjacentDisks.add(surroundingCoordinate);
      }


    }

    //Checks if there is at least one adjacent disk found
    if (validAdjacentDisks.isEmpty()) {
      return false;
    }

    //Checks if there is a same game piece color in the adjacent coordinate's direction

    //First add the directions into String arraylist
    for (Coordinate validAdjacentCoordinate : validAdjacentDisks) {
      String direction = getDirection(validAdjacentCoordinate, placePosition);

      validDirections.add(direction);
    }


    //All pieces in between these coordinates in this direction need to be flipped
    ArrayList<Coordinate> piecesToFlip = new ArrayList<>();


    //For every direction we add the different color piece coordinates into the piecesToFlip
    //array list to check whether a different color exists in that direction

    for (String direction : validDirections) {

      piecesToFlip.addAll(samePieceExistsAndFlip(direction, placePosition));


    }

    //Checks if there was at least one piece that exists in all directions
    if (piecesToFlip.isEmpty()) {
      return false;
    }

    //Checks if there are any empty spaces in a valid adjacent disk direction
    for (Coordinate coordinate : piecesToFlip) {

      String direction = getDirection(coordinate, placePosition);
      if (isThereSpaceBetween(direction, placePosition, coordinate)) {
        return false;
      }
    }


    return true;
  }

  /**
   * Retrieves a list of coordinates representing the pieces that are of the same type (color)
   * as the next player's
   * and are positioned between the specified column and row and adjacent to the next player's
   * piece.
   *
   * @param column The column index of the reference position.
   * @param row    The row index of the reference position.
   * @return An ArrayList of coordinates representing the pieces that can be flipped to the next
   *         player's color.
   */

  private ArrayList<Coordinate> getSamePieceBetween(int column, int row) {
    Coordinate placePosition = new Coordinate(column, row);
    ArrayList<Coordinate> validAdjacentDisks = new ArrayList<>();
    ArrayList<String> validDirections = new ArrayList<>();

    for (Coordinate surroundingCoordinate : placePosition.getSurroundingCells()) {
      if (this.gameBoard.get(surroundingCoordinate) == nextPlayer()) {
        validAdjacentDisks.add(surroundingCoordinate);
      }

    }

    for (Coordinate validAdjacentCoordinate : validAdjacentDisks) {
      String direction = getDirection(validAdjacentCoordinate, placePosition);

      validDirections.add(direction);
    }


    //All pieces in between these coordinates in this direction need to be flipped
    ArrayList<Coordinate> piecesToFlip = new ArrayList<>();


    //For every direction we add the different color piece coordinates into the piecesToFlip
    //array list to check whether a different color exists in that direction

    for (String direction : validDirections) {

      piecesToFlip.addAll(samePieceExistsAndFlip(direction, placePosition));


    }


    return piecesToFlip;

  }


  /**
   * Checks if there is an empty space between two coordinates in a specified direction.
   *
   * @param direction        The direction to check for empty spaces. Should be one of:
   *                         - "topLeft"
   *                         - "topRight"
   *                         - "left"
   *                         - "right"
   *                         - "bottomRight"
   *                         - "bottomLeft"
   * @param placedCoordinate The starting coordinate.
   * @param coordinate       The ending coordinate.
   * @return true if there is an empty space between the coordinates in the specified direction,
   *         false otherwise.
   */

  private boolean isThereSpaceBetween(String direction, Coordinate placedCoordinate,
                                      Coordinate coordinate) {


    //Direction is top left
    if (direction.equals("topLeft")) {


      for (int i = placedCoordinate.getRow() - 1; i > coordinate.getRow(); i--) {

        if (this.gameBoard.get(new Coordinate(placedCoordinate.getCol(), i)) == CellType.EMPTY) {
          return true;
        }


      }

    }

    //Direction is top right
    if (direction.equals("topRight")) {

      int col = placedCoordinate.getCol() + 1;
      int row = placedCoordinate.getRow() - 1;

      while (col < coordinate.getCol() && row > coordinate.getRow()) {


        if (this.gameBoard.get(new Coordinate(col, row)) == CellType.EMPTY) {
          return true;
        }

        // Move to the next diagonal element
        col++;
        row--;
      }


    }

    //Direction is left
    if (direction.equals("left")) {


      for (int i = placedCoordinate.getCol() - 1; i > coordinate.getCol(); i--) {

        if (this.gameBoard.get(new Coordinate(i, placedCoordinate.getRow())) == CellType.EMPTY) {
          return true;
        }

      }

    }

    //Direction is right
    if (direction.equals("right")) {


      for (int i = placedCoordinate.getCol() + 1; i < coordinate.getCol(); i++) {

        if (this.gameBoard.get(new Coordinate(i, placedCoordinate.getRow())) == CellType.EMPTY) {
          return true;
        }


      }

    }

    //Direction is bottomRight
    if (direction.equals("bottomRight")) {


      for (int i = placedCoordinate.getRow() + 1; i < coordinate.getRow(); i++) {

        if (this.gameBoard.get(new Coordinate(placedCoordinate.getCol(), i)) == CellType.EMPTY) {
          return true;
        }

      }

    }

    //Direction is bottomLeft
    if (direction.equals("bottomLeft")) {


      int col = placedCoordinate.getCol() - 1;
      int row = placedCoordinate.getRow() + 1;


      while (col > coordinate.getCol() && row < coordinate.getRow()) {

        if (this.gameBoard.get(new Coordinate(col, row)) == CellType.EMPTY) {
          return true;
        }


        // Move to the next diagonal element
        col--;
        row++;
      }


    }


    return false;
  }

  //Helper method that loops all game pieces in the specified direction and checks whether
  //same game piece exists

  /**
   * Helper method that loops through all game pieces in the specified direction and checks whether
   * pieces of the same type exist. It returns a list of coordinates for the found pieces.
   *
   * @param direction     The direction in which to search for same-type game pieces.
   * @param placePosition The position where a game piece is placed.
   * @return A list of coordinates of same-type game pieces found in the specified direction.
   */

  private ArrayList<Coordinate> samePieceExistsAndFlip(String direction, Coordinate placePosition) {

    ArrayList<Coordinate> returnList = new ArrayList<>();


    if (direction.equals("left")) {


      for (int i = placePosition.getCol() - 1; i >= 0; i--) {

        if (this.gameBoard.get(new Coordinate(i, placePosition.getRow()))
                == this.currentPlayerTurn) {

          returnList.add(new Coordinate(i, placePosition.getRow()));


          break;

        }

      }


    }


    if (direction.equals("right")) {
      for (int i = placePosition.getCol() + 1; i < boardSize; i++) {
        if (this.gameBoard.get(new Coordinate(i, placePosition.getRow()))
                == this.currentPlayerTurn) {
          returnList.add(new Coordinate(i, placePosition.getRow()));

          break;
        }
      }

    }

    if (direction.equals("topLeft")) {
      for (int i = placePosition.getRow() - 1; i >= 0; i--) {
        if (this.gameBoard.get(new Coordinate(placePosition.getCol(), i))
                == this.currentPlayerTurn) {
          returnList.add(new Coordinate(placePosition.getCol(), i));

          break;

        }
      }

    }
    if (direction.equals("topRight")) {

      int col = placePosition.getCol() + 1;
      int row = placePosition.getRow() - 1;

      while (col < boardSize && row >= 0) {

        if (this.gameBoard.get(new Coordinate(col, row)) == this.currentPlayerTurn) {
          returnList.add(new Coordinate(col, row));

          break;
        }
        // Move to the next diagonal element
        col++;
        row--;
      }


    }
    if (direction.equals("bottomLeft")) {

      int col = placePosition.getCol() - 1;
      int row = placePosition.getRow() + 1;

      while (col >= 0 && row < boardSize) {

        if (this.gameBoard.get(new Coordinate(col, row)) == this.currentPlayerTurn) {
          returnList.add(new Coordinate(col, row));

          break;
        }
        // Move to the next diagonal element
        col--;
        row++;
      }


    }
    if (direction.equals("bottomRight")) {
      for (int i = placePosition.getRow() + 1; i < boardSize; i++) {
        if (this.gameBoard.get(new Coordinate(placePosition.getCol(), i))
                == this.currentPlayerTurn) {
          returnList.add(new Coordinate(placePosition.getCol(), i));

          break;
        }
      }

    }


    return returnList;
  }

  //Helper method to determine which direction the valid adjacent piece was facing

  /**
   * Helper method to determine which direction a valid adjacent piece is facing
   * relative to the placed game piece.
   *
   * @param validAdjacentCoordinate The coordinate of a valid adjacent game piece.
   * @param placePosition           The position where a game piece is placed.
   * @return A string representing the direction of the valid adjacent piece.
   */
  private String getDirection(Coordinate validAdjacentCoordinate, Coordinate placePosition) {
    String direction = "";

    if (validAdjacentCoordinate.getCol() - placePosition.getCol()
            == 0 && validAdjacentCoordinate.getRow() < placePosition.getRow()) {
      direction = "topLeft";
    }
    if (validAdjacentCoordinate.getCol() > placePosition.getCol()
            && validAdjacentCoordinate.getRow()
            < placePosition.getRow()) {
      direction = "topRight";
    }
    if (validAdjacentCoordinate.getCol() < placePosition.getCol()
            && validAdjacentCoordinate.getRow()
            - placePosition.getRow() == 0) {
      direction = "left";
    }
    if (validAdjacentCoordinate.getCol() > placePosition.getCol()
            && validAdjacentCoordinate.getRow()
            - placePosition.getRow() == 0) {
      direction = "right";
    }
    if (validAdjacentCoordinate.getCol() < placePosition.getCol()
            && validAdjacentCoordinate.getRow()
            > placePosition.getRow()) {
      direction = "bottomLeft";
    }
    if (validAdjacentCoordinate.getCol() - placePosition.getCol()
            == 0 && validAdjacentCoordinate.getRow()
            > placePosition.getRow()) {
      direction = "bottomRight";
    }
    return direction;
  }


}
