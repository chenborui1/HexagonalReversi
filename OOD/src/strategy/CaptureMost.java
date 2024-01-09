package strategy;

import java.util.ArrayList;


import model.CellType;
import model.Coordinate;
import model.ReadonlyReversiModel;

/**
 * The CaptureMost class represents a Reversi strategy that aims to capture the most opponent's
 * pieces.
 * This strategy evaluates potential moves and chooses the one that results in the highest number
 * of captured pieces.
 */
public class CaptureMost implements ReversiStrategy {




  private ReadonlyReversiModel model;

  /**
   * Chooses the coordinate for the next move based on the strategy's evaluation.
   *
   * @param model  The Reversi game model.
   * @param player The current player making the move.
   * @return The chosen coordinate for the next move.
   * @throws IllegalArgumentException    If it's not the correct player's turn to use the strategy
   *                                     or the game is over.
   * @throws IllegalStateException       If the current player has no available moves left.
   */
  @Override
  public Coordinate chooseCoordinate(ReadonlyReversiModel model, CellType player) {
    int bestScore = 0;
    this.model = model;
    ArrayList<Coordinate> validPoints = new ArrayList<>();
    ArrayList<Coordinate> highestScorePoints = new ArrayList<>();

    if (model.getCurrentPlayerTurn() != player) {
      throw new IllegalArgumentException("Not the correct player's turn to use a strategy");
    }
    if (model.isGameOver()) {
      throw new IllegalArgumentException("The game is over");
    }

    if (!model.playerLegalMoves()) {
      throw new IllegalStateException("You don't have any available moves left");

    }
    for (int row = 0; row < model.getBoardSize(); row++) {
      for (int col = 0; col < model.getBoardSize(); col++) {
        Coordinate coordinate = new Coordinate(col, row);
        if (model.canMakeMove(coordinate.getRow(), coordinate.getCol())) {
          validPoints.add(coordinate);
        }
      }
    }

    //Checks every valid point and returns coordinates with the highest number of pieces flipped
    for (Coordinate c : validPoints) {
      if (getScore(c, model) >= bestScore) {
        bestScore = getScore(c, model);

      }
    }



    for (Coordinate c : validPoints) {
      if (getScore(c, model) == bestScore) {
        highestScorePoints.add(c);
      }
    }


    //Access the list of coordinates and get the top left most coordinate


    Coordinate bestMove;
    if (highestScorePoints.size() != 1) {

      bestMove = getTopLeft(highestScorePoints);
    } else {
      bestMove = highestScorePoints.get(0);
    }
    return bestMove;
  }

  /**
   * Constructs a SquarePanel with the specified Reversi game model.
   *
   * @param model The ReadonlyReversiModel to visualize.
   * @param c the coordinate.
   *
   * @return int of score.
   */
  public int getScore(Coordinate c, ReadonlyReversiModel model) {

    this.model = model;
    ArrayList<Coordinate> piecesBetween = getSamePieceBetween(c);

    return getMaxNumberOfPiecesFlipped(piecesBetween, c);


  }

  //Gets the top most then left most coordinate from a list of coordinates
  private Coordinate getTopLeft(ArrayList<Coordinate> topScoringCoordinates) {


    Coordinate uppermostLeftmost = topScoringCoordinates.get(0);

    for (Coordinate coordinate : topScoringCoordinates) {

      if (coordinate.getRow() < uppermostLeftmost.getRow()) {
        uppermostLeftmost = coordinate;
      } else {
        if (coordinate.getRow() == uppermostLeftmost.getRow() && coordinate.getCol()
                < uppermostLeftmost.getCol()) {
          uppermostLeftmost = coordinate;

        }

      }


    }

    return uppermostLeftmost;
  }


  //Gets the number of flipped pieces in a direction
  private int getMaxNumberOfPiecesFlipped(ArrayList<Coordinate> foundPieces,
                                          Coordinate placedCoordinate) {

    int numberOfPiecesFlipped = 0;

    for (Coordinate coordinate : foundPieces) {


      //Direction is top left
      if (coordinate.getCol() == placedCoordinate.getCol() && coordinate.getRow()
              < placedCoordinate.getRow()) {

        int tracker = 0;

        for (int i = placedCoordinate.getRow() - 1; i > coordinate.getRow(); i--) {


          tracker++;

        }

        if (tracker >= numberOfPiecesFlipped) {
          numberOfPiecesFlipped += tracker;
        }

      }

      //Direction is top right
      if (coordinate.getCol() > placedCoordinate.getCol() && coordinate.getRow()
              < placedCoordinate.getRow()) {

        int col = placedCoordinate.getCol() + 1;
        int row = placedCoordinate.getRow() - 1;

        int tracker = 0;

        while (col < coordinate.getCol() && row > coordinate.getRow()) {


          tracker++;
          // Move to the next diagonal element
          col++;
          row--;
        }


        if (tracker >= numberOfPiecesFlipped) {
          numberOfPiecesFlipped += tracker;
        }
      }

      //Direction is left
      if (coordinate.getCol() < placedCoordinate.getCol() && coordinate.getRow()
              == placedCoordinate.getRow()) {

        int tracker = 0;

        for (int i = placedCoordinate.getCol() - 1; i > coordinate.getCol(); i--) {


          tracker++;
        }

        if (tracker >= numberOfPiecesFlipped) {
          numberOfPiecesFlipped += tracker;
        }
      }

      //Direction is right
      if (coordinate.getCol() > placedCoordinate.getCol() && coordinate.getRow()
              == placedCoordinate.getRow()) {

        int tracker = 0;

        for (int i = placedCoordinate.getCol() + 1; i < coordinate.getCol(); i++) {


          tracker++;

        }

        if (tracker >= numberOfPiecesFlipped) {
          numberOfPiecesFlipped += tracker;
        }

      }

      //Direction is bottomRight
      if (coordinate.getCol() == placedCoordinate.getCol() && coordinate.getRow()
              > placedCoordinate.getRow()) {

        int tracker = 0;

        for (int i = placedCoordinate.getRow() + 1; i < coordinate.getRow(); i++) {


          tracker++;

        }

        if (tracker >= numberOfPiecesFlipped) {
          numberOfPiecesFlipped += tracker;
        }
      }

      //Direction is bottomLeft
      if (coordinate.getCol() < placedCoordinate.getCol() && coordinate.getRow()
              > placedCoordinate.getRow()) {


        int col = placedCoordinate.getCol() - 1;
        int row = placedCoordinate.getRow() + 1;

        int tracker = 0;

        while (col > coordinate.getCol() && row < coordinate.getRow()) {


          tracker++;


          // Move to the next diagonal element
          col--;
          row++;
        }

        if (tracker >= numberOfPiecesFlipped) {
          numberOfPiecesFlipped += tracker;
        }

      }
    }

    return numberOfPiecesFlipped;

  }


  private ArrayList<Coordinate> getSamePieceBetween(Coordinate c) {
    ArrayList<Coordinate> validAdjacentDisks = new ArrayList<>();
    ArrayList<String> validDirections = new ArrayList<>();

    for (Coordinate surroundingCoordinate : c.getSurroundingCells()) {
      if (model.getGameBoard().get(surroundingCoordinate) == model.nextPlayer()) {
        validAdjacentDisks.add(surroundingCoordinate);
      }

    }

    for (Coordinate validAdjacentCoordinate : validAdjacentDisks) {
      String direction = getDirection(validAdjacentCoordinate, c);

      validDirections.add(direction);
    }

    ArrayList<Coordinate> piecesToFlip = new ArrayList<>();

    for (String direction : validDirections) {

      piecesToFlip.addAll(samePieceExistsAndFlip(direction, c));


    }


    return piecesToFlip;
  }

  private ArrayList<Coordinate> samePieceExistsAndFlip(String direction, Coordinate c) {
    ArrayList<Coordinate> returnList = new ArrayList<>();


    if (direction.equals("left")) {


      for (int i = c.getCol() - 1; i >= 0; i--) {

        if (model.getGameBoard().get(new Coordinate(i, c.getRow()))
                == model.getCurrentPlayerTurn()) {

          returnList.add(new Coordinate(i, c.getRow()));


          break;

        }

      }


    }


    if (direction.equals("right")) {
      for (int i = c.getCol() + 1; i < model.getBoardSize(); i++) {
        if (model.getGameBoard().get(new Coordinate(i, c.getRow()))
                == model.getCurrentPlayerTurn()) {
          returnList.add(new Coordinate(i, c.getRow()));

          break;
        }
      }

    }

    if (direction.equals("topLeft")) {
      for (int i = c.getRow() - 1; i >= 0; i--) {
        if (model.getGameBoard().get(new Coordinate(c.getCol(), i))
                == model.getCurrentPlayerTurn()) {
          returnList.add(new Coordinate(c.getCol(), i));

          break;

        }
      }

    }
    if (direction.equals("topRight")) {

      int col = c.getCol() + 1;
      int row = c.getRow() - 1;

      while (col < model.getBoardSize() && row >= 0) {

        if (model.getGameBoard().get(new Coordinate(col, row)) == model.getCurrentPlayerTurn()) {
          returnList.add(new Coordinate(col, row));

          break;
        }
        // Move to the next diagonal element
        col++;
        row--;
      }


    }
    if (direction.equals("bottomLeft")) {

      int col = c.getCol() - 1;
      int row = c.getRow() + 1;

      while (col >= 0 && row < model.getBoardSize()) {

        if (model.getGameBoard().get(new Coordinate(col, row)) == model.getCurrentPlayerTurn()) {
          returnList.add(new Coordinate(col, row));

          break;
        }
        // Move to the next diagonal element
        col--;
        row++;
      }


    }
    if (direction.equals("bottomRight")) {
      for (int i = c.getRow() + 1; i < model.getBoardSize(); i++) {
        if (model.getGameBoard().get(new Coordinate(c.getCol(), i))
                == model.getCurrentPlayerTurn()) {
          returnList.add(new Coordinate(c.getCol(), i));

          break;
        }
      }

    }


    return returnList;
  }

  private String getDirection(Coordinate validAdjacentCoordinate, Coordinate c) {
    String direction = "";

    if (validAdjacentCoordinate.getCol() - c.getCol()
            == 0 && validAdjacentCoordinate.getRow() < c.getRow()) {
      direction = "topLeft";
    }
    if (validAdjacentCoordinate.getCol() > c.getCol()
            && validAdjacentCoordinate.getRow()
            < c.getRow()) {
      direction = "topRight";
    }
    if (validAdjacentCoordinate.getCol() < c.getCol()
            && validAdjacentCoordinate.getRow()
            - c.getRow() == 0) {
      direction = "left";
    }
    if (validAdjacentCoordinate.getCol() > c.getCol()
            && validAdjacentCoordinate.getRow()
            - c.getRow() == 0) {
      direction = "right";
    }
    if (validAdjacentCoordinate.getCol() < c.getCol()
            && validAdjacentCoordinate.getRow()
            > c.getRow()) {
      direction = "bottomLeft";
    }
    if (validAdjacentCoordinate.getCol() - c.getCol()
            == 0 && validAdjacentCoordinate.getRow()
            > c.getRow()) {
      direction = "bottomRight";
    }
    return direction;
  }
}
