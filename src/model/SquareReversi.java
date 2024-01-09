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
public class SquareReversi extends AbstractReversi {


  /**
   * Constructs a BasicReversi game with a predefined initial game board.
   *
   * @param riggedBoard A pre-arranged game board to start the game with.
   */
  public SquareReversi(HashMap<ICoordinate, CellType> riggedBoard, CellType playerTurn) {
    super(riggedBoard, playerTurn);

    currentPlayerTurn = playerTurn;


  }

  /**
   * Constructs a BasicReversi game with the specified board size.
   *
   * @param boardSize The size of the game board (number of rows and columns).
   */
  public SquareReversi(int boardSize) {
    super(boardSize);

    initializeGame();

  }

  @Override
  public void initializeGame() throws IllegalArgumentException {
    if (this.gameStarted) {
      throw new IllegalStateException("Game has already started");
    }

    //Different board size

    if (boardSize < 4 || boardSize % 2 != 0) {
      throw new IllegalArgumentException("Invalid board size");
    }


    //Instantiate the hexagonal game board through for loops

    for (int row = 0; row < boardSize; row++) {


      for (int column = 0; column < boardSize; column++) {
        gameBoard.put(new SquareCoordinate(column, row), CellType.EMPTY);
      }
    }




    // Add initial pieces in the center of the hexagonal board
    int middle = (boardSize / 2) - 1;

    startingPiecePositions(middle);

    //Make the player turn black first

    this.currentPlayerTurn = CellType.BLACK;

    //Update gameStart field

    this.gameStarted = true;

    //Update game state

    this.gameState = GameState.PLAYING;
  }

  @Override
  public void makeMove(CellType player, int row, int column) throws IllegalArgumentException {

    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (this.gameState != GameState.PLAYING) {
      throw new IllegalStateException("Game has concluded");
    }

    if (currentPlayerTurn != player) {
      throw new IllegalStateException("It is not your turn");
    }


    if (this.gameBoard.get(new SquareCoordinate(column, row)) == null) {
      throw new IllegalArgumentException("Row or column out of bounds");
    }


    //Start to check move disk legal move

    if (validateMove(row, column)) {

      //Update board


      this.gameBoard.put(new SquareCoordinate(column, row), this.currentPlayerTurn);

    } else {
      throw new IllegalStateException("Move is not valid");
    }

    //Flip the in between pieces

    flipPieces(getSamePieceBetween(column, row), new SquareCoordinate(column, row));


    previousPlayerTurn = currentPlayerTurn;
    currentPlayerTurn = nextPlayer();


    notifyPlayerChange(nextPlayer());
    notifyGameBoardChange();






    //After making a move number of skips should be set back to 0

    this.skipTimes = 0;

  }

  @Override
  public void skip(CellType player) throws IllegalArgumentException {
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

      notifyGameBoardChange();
      this.gameState = GameState.OVER;
      notifyGameOver();

    } else {
      previousPlayerTurn = currentPlayerTurn;
      currentPlayerTurn = nextPlayer();
      notifyPlayerChange(nextPlayer());
      notifyGameBoardChange();
    }
  }



  @Override
  public void startGame() {
    notifyGameStart();
  }



  @Override
  protected void startingPiecePositions(int middle) {
    gameBoard.put(new SquareCoordinate(middle, middle), CellType.BLACK);
    gameBoard.put(new SquareCoordinate(middle + 1, middle), CellType.WHITE);
    gameBoard.put(new SquareCoordinate(middle, middle + 1), CellType.WHITE);
    gameBoard.put(new SquareCoordinate(middle + 1, middle + 1), CellType.BLACK);
  }

  @Override
  protected boolean validateMove(int row, int column) {

    SquareCoordinate placePosition = new SquareCoordinate(column, row);

    //Checks if the place position is empty or null
    if (this.gameBoard.get(placePosition) != CellType.EMPTY) {
      return false;
    }

    //Checks if there is at least one opponent disk adjacent to any
    // direction of the disk being placed
    //Also stores the coordinates of the adjacent disks that exist in an arraylist

    ArrayList<SquareCoordinate> validAdjacentDisks = new ArrayList<>();
    ArrayList<String> validDirections = new ArrayList<>();

    for (SquareCoordinate surroundingCoordinate : placePosition.getSurroundingCells()) {
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
    for (SquareCoordinate validAdjacentCoordinate : validAdjacentDisks) {
      String direction = getDirection(validAdjacentCoordinate, placePosition);

      validDirections.add(direction);
    }


    //All pieces in between these coordinates in this direction need to be flipped
    ArrayList<SquareCoordinate> piecesToFlip = new ArrayList<>();


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
    for (SquareCoordinate coordinate : piecesToFlip) {

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

  private ArrayList<SquareCoordinate> getSamePieceBetween(int column, int row) {
    SquareCoordinate placePosition = new SquareCoordinate(column, row);
    ArrayList<SquareCoordinate> validAdjacentDisks = new ArrayList<>();
    ArrayList<String> validDirections = new ArrayList<>();

    for (SquareCoordinate surroundingCoordinate : placePosition.getSurroundingCells()) {
      if (this.gameBoard.get(surroundingCoordinate) == nextPlayer()) {
        validAdjacentDisks.add(surroundingCoordinate);
      }

    }

    for (SquareCoordinate validAdjacentCoordinate : validAdjacentDisks) {
      String direction = getDirection(validAdjacentCoordinate, placePosition);

      validDirections.add(direction);
    }


    //All pieces in between these coordinates in this direction need to be flipped
    ArrayList<SquareCoordinate> piecesToFlip = new ArrayList<>();


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

  private boolean isThereSpaceBetween(String direction, SquareCoordinate placedCoordinate,
                                      SquareCoordinate coordinate) {


    //Direction is top left
    if (direction.equals("topLeft")) {

      int col = placedCoordinate.getCol() - 1;
      int row = placedCoordinate.getRow() - 1;

      while (col > coordinate.getCol() && row > coordinate.getRow()) {


        if (this.gameBoard.get(new SquareCoordinate(col, row)) == CellType.EMPTY) {
          return true;
        }

        // Move to the next diagonal element
        col--;
        row--;
      }



    }

    //Direction is top right
    if (direction.equals("topRight")) {

      int col = placedCoordinate.getCol() + 1;
      int row = placedCoordinate.getRow() - 1;

      while (col < coordinate.getCol() && row > coordinate.getRow()) {


        if (this.gameBoard.get(new SquareCoordinate(col, row)) == CellType.EMPTY) {
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

        if (this.gameBoard.get(
                new SquareCoordinate(i, placedCoordinate.getRow())) == CellType.EMPTY) {
          return true;
        }

      }

    }

    //Direction is right
    if (direction.equals("right")) {


      for (int i = placedCoordinate.getCol() + 1; i < coordinate.getCol(); i++) {

        if (this.gameBoard.get(
                new SquareCoordinate(i, placedCoordinate.getRow())) == CellType.EMPTY) {
          return true;
        }


      }

    }

    //Direction is bottomRight
    if (direction.equals("bottomRight")) {

      int col = placedCoordinate.getCol() + 1;
      int row = placedCoordinate.getRow() + 1;


      while (col < coordinate.getCol() && row < coordinate.getRow()) {

        if (this.gameBoard.get(new SquareCoordinate(col, row)) == CellType.EMPTY) {
          return true;
        }


        // Move to the next diagonal element
        col++;
        row++;
      }




    }

    //Direction is bottomLeft
    if (direction.equals("bottomLeft")) {


      int col = placedCoordinate.getCol() - 1;
      int row = placedCoordinate.getRow() + 1;


      while (col > coordinate.getCol() && row < coordinate.getRow()) {

        if (this.gameBoard.get(new SquareCoordinate(col, row)) == CellType.EMPTY) {
          return true;
        }


        // Move to the next diagonal element
        col--;
        row++;
      }


    }

    if (direction.equals("top")) {


      for (int i = placedCoordinate.getRow() - 1; i > coordinate.getRow(); i--) {

        if (this.gameBoard.get(
                new SquareCoordinate(placedCoordinate.getCol(), i)) == CellType.EMPTY) {
          return true;
        }


      }

    }

    if (direction.equals("bottom")) {


      for (int i = placedCoordinate.getRow() + 1; i < coordinate.getRow(); i++) {

        if (this.gameBoard.get(
                new SquareCoordinate(placedCoordinate.getCol(), i)) == CellType.EMPTY) {
          return true;
        }


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

  private ArrayList<SquareCoordinate> samePieceExistsAndFlip(String direction,
                                                             SquareCoordinate placePosition) {

    ArrayList<SquareCoordinate> returnList = new ArrayList<>();


    if (direction.equals("left")) {


      for (int i = placePosition.getCol() - 1; i >= 0; i--) {

        if (this.gameBoard.get(new SquareCoordinate(i, placePosition.getRow()))
                == this.currentPlayerTurn) {

          returnList.add(new SquareCoordinate(i, placePosition.getRow()));


          break;

        }

      }


    }


    if (direction.equals("right")) {
      for (int i = placePosition.getCol() + 1; i < boardSize; i++) {
        if (this.gameBoard.get(new SquareCoordinate(i, placePosition.getRow()))
                == this.currentPlayerTurn) {
          returnList.add(new SquareCoordinate(i, placePosition.getRow()));

          break;
        }
      }

    }

    if (direction.equals("topLeft")) {

      int col = placePosition.getCol() - 1;
      int row = placePosition.getRow() - 1;

      while (col >= 0 && row >= 0) {

        if (this.gameBoard.get(new SquareCoordinate(col, row)) == this.currentPlayerTurn) {
          returnList.add(new SquareCoordinate(col, row));

          break;
        }
        // Move to the next diagonal element
        col--;
        row--;
      }

    }
    if (direction.equals("topRight")) {

      int col = placePosition.getCol() + 1;
      int row = placePosition.getRow() - 1;

      while (col < boardSize && row >= 0) {

        if (this.gameBoard.get(new SquareCoordinate(col, row)) == this.currentPlayerTurn) {
          returnList.add(new SquareCoordinate(col, row));

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

        if (this.gameBoard.get(new SquareCoordinate(col, row)) == this.currentPlayerTurn) {
          returnList.add(new SquareCoordinate(col, row));

          break;
        }
        // Move to the next diagonal element
        col--;
        row++;
      }


    }
    if (direction.equals("bottomRight")) {

      int col = placePosition.getCol() + 1;
      int row = placePosition.getRow() + 1;

      while (col < boardSize && row < boardSize) {

        if (this.gameBoard.get(new SquareCoordinate(col, row)) == this.currentPlayerTurn) {
          returnList.add(new SquareCoordinate(col, row));

          break;
        }
        // Move to the next diagonal element
        col++;
        row++;
      }

    }
    if (direction.equals("bottom")) {
      for (int i = placePosition.getRow() + 1; i < boardSize; i++) {
        if (this.gameBoard.get(new SquareCoordinate(placePosition.getCol(), i))
                == this.currentPlayerTurn) {
          returnList.add(new SquareCoordinate(placePosition.getCol(), i));

          break;
        }
      }

    }
    if (direction.equals("top")) {
      for (int i = placePosition.getRow() - 1; i >= 0; i--) {
        if (this.gameBoard.get(new SquareCoordinate(placePosition.getCol(), i))
                == this.currentPlayerTurn) {
          returnList.add(new SquareCoordinate(placePosition.getCol(), i));

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
  private String getDirection(SquareCoordinate validAdjacentCoordinate,
                              SquareCoordinate placePosition) {
    String direction = "";

    if (validAdjacentCoordinate.getCol() - placePosition.getCol()
            == -1 && validAdjacentCoordinate.getRow() < placePosition.getRow()) {
      direction = "topLeft";
    }
    if (validAdjacentCoordinate.getCol() - placePosition.getCol() == 1
            && validAdjacentCoordinate.getRow()
            < placePosition.getRow()) {
      direction = "topRight";
    }
    if (validAdjacentCoordinate.getCol() < placePosition.getCol()
            && validAdjacentCoordinate.getRow()
            == placePosition.getRow()) {
      direction = "left";
    }
    if (validAdjacentCoordinate.getCol() > placePosition.getCol()
            && validAdjacentCoordinate.getRow()
            == placePosition.getRow()) {
      direction = "right";
    }
    if (validAdjacentCoordinate.getCol() - placePosition.getCol() == -1
            && validAdjacentCoordinate.getRow()
            > placePosition.getRow()) {
      direction = "bottomLeft";
    }
    if (validAdjacentCoordinate.getCol() - placePosition.getCol()
            == 1 && validAdjacentCoordinate.getRow()
            > placePosition.getRow()) {
      direction = "bottomRight";
    }
    if (validAdjacentCoordinate.getCol() == placePosition.getCol()
            && validAdjacentCoordinate.getRow()
            < placePosition.getRow()) {
      direction = "top";
    }
    if (validAdjacentCoordinate.getCol() == placePosition.getCol()
            && validAdjacentCoordinate.getRow()
            > placePosition.getRow()) {
      direction = "bottom";
    }
    return direction;
  }

  private void flipPieces(ArrayList<SquareCoordinate> foundPieces,
                          SquareCoordinate placedCoordinate) {


    for (SquareCoordinate coordinate : foundPieces) {


      //Direction is bottom right
      if (coordinate.getCol() < placedCoordinate.getCol() && coordinate.getRow()
              < placedCoordinate.getRow()) {

        int col = placedCoordinate.getCol() - 1;
        int row = placedCoordinate.getRow() - 1;

        while (col > coordinate.getCol() && row > coordinate.getRow()) {


          this.gameBoard.replace(new SquareCoordinate(col, row), currentPlayerTurn);
          // Move to the next diagonal element
          col--;
          row--;
        }


      }

      //Direction is top left
      if (coordinate.getCol() > placedCoordinate.getCol() && coordinate.getRow()
              > placedCoordinate.getRow()) {

        int col = placedCoordinate.getCol() + 1;
        int row = placedCoordinate.getRow() + 1;

        while (col < coordinate.getCol() && row < coordinate.getRow()) {


          this.gameBoard.replace(new SquareCoordinate(col, row), currentPlayerTurn);
          // Move to the next diagonal element
          col++;
          row++;
        }


      }

      //Direction is left
      if (coordinate.getCol() < placedCoordinate.getCol() && coordinate.getRow()
              == placedCoordinate.getRow()) {


        for (int i = placedCoordinate.getCol() - 1; i > coordinate.getCol(); i--) {


          this.gameBoard.replace(
                  new SquareCoordinate(i, placedCoordinate.getRow()), currentPlayerTurn);
        }

      }

      //Direction is right
      if (coordinate.getCol() > placedCoordinate.getCol() && coordinate.getRow()
              == placedCoordinate.getRow()) {


        for (int i = placedCoordinate.getCol() + 1; i < coordinate.getCol(); i++) {


          this.gameBoard.replace(
                  new SquareCoordinate(i, placedCoordinate.getRow()), currentPlayerTurn);

        }

      }

      //Direction is top right
      if (coordinate.getCol() < placedCoordinate.getCol() && coordinate.getRow()
              > placedCoordinate.getRow()) {

        int col = placedCoordinate.getCol() - 1;
        int row = placedCoordinate.getRow() + 1;

        while (col > coordinate.getCol() && row < coordinate.getRow()) {


          this.gameBoard.replace(new SquareCoordinate(col, row), currentPlayerTurn);
          // Move to the next diagonal element
          col--;
          row++;
        }


      }

      //Direction is top left
      if (coordinate.getCol() > placedCoordinate.getCol() && coordinate.getRow()
              < placedCoordinate.getRow()) {

        int col = placedCoordinate.getCol() + 1;
        int row = placedCoordinate.getRow() - 1;

        while (col < coordinate.getCol() && row > coordinate.getRow()) {


          this.gameBoard.replace(new SquareCoordinate(col, row), currentPlayerTurn);
          // Move to the next diagonal element
          col++;
          row--;
        }


      }

      //top
      if (coordinate.getCol() == placedCoordinate.getCol() && coordinate.getRow()
              < placedCoordinate.getRow()) {


        for (int i = placedCoordinate.getRow() - 1; i > coordinate.getRow(); i--) {


          this.gameBoard.replace(
                  new SquareCoordinate(placedCoordinate.getCol(), i), currentPlayerTurn);
        }

      }


      //Direction is bottom
      if (coordinate.getCol() == placedCoordinate.getCol() && coordinate.getRow()
              > placedCoordinate.getRow()) {


        for (int i = placedCoordinate.getRow() + 1; i < coordinate.getRow(); i++) {


          this.gameBoard.replace(
                  new SquareCoordinate(placedCoordinate.getCol(), i), currentPlayerTurn);

        }

      }
    }


  }


}
