package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import model.CellType;
import model.Coordinate;
import model.ICoordinate;
import model.ReadonlyReversiModel;
import strategy.CaptureMost;

/**
 * The HexPanel class represents a panel containing hexagonal cells to visualize the Reversi game
 * board.
 * It extends JPanel and handles mouse click events to interact with the hexagonal cells.
 */
public class HexPanel extends JPanel {


  private HashMap<ICoordinate, CellType> board;
  private ArrayList<HexagonCell> allHexagons;
  private HexagonCell hexagonClicked;

  private ReadonlyReversiModel model;

  private Coordinate hexagonClickedLogicalCoordinate;

  /**
   * Constructs a HexPanel with the specified Reversi game model.
   *
   * @param model The ReadonlyReversiModel to visualize.
   */
  public HexPanel(ReadonlyReversiModel model) {
    super();


    this.model = model;
    this.board = model.getGameBoard();
    this.setBackground(Color.DARK_GRAY);
    allHexagons = createListOfHexagons(board);




    addMouseListener(new MyMouseListener());

  }

  /**
   * Updates the hexagon grid based on the updated game board.
   */
  public void updateHexagonGrid() {
    //Get updated board
    board = model.getGameBoard();

    ArrayList<HexagonCell> updatedHexagons = createListOfHexagons(board);
    allHexagons.clear();
    allHexagons.addAll(updatedHexagons);
    repaint();
  }



  /**
   * toggle the hints needed.
   *
   */
  public void toggleHint() {
    for (HexagonCell hex : this.allHexagons) {
      hex.toggleHint();
    }
  }


  /**
   * Mouse listener class for handling mouse events within the HexPanel.
   */
  private class MyMouseListener extends MouseAdapter {
    @Override
    public void mouseReleased(MouseEvent e) {
      // Handle mouse click event
      int mouseX = e.getX();
      int mouseY = e.getY();


      boolean clickedOutside = true;


      // Check if the click is within any hexagon
      for (HexagonCell hexagonCell : allHexagons) {
        if (hexagonCell.isMouseClick(new Point2D.Double(mouseX, mouseY))) {


          // Print the logical coordinates of the clicked hexagon
          Coordinate coordinate = hexagonCell.getCoordinate();
          hexagonClickedLogicalCoordinate = coordinate;


          if (!hexagonCell.getClicked()) {
            System.out.println("Hexagon clicked at logical coordinates: Column: "
                    + coordinate.getCol() + " Row: " + coordinate.getRow());

            hexagonCell.click();
            if (hexagonClicked != null) {
              hexagonClicked.click();
            }
            hexagonClicked = hexagonCell;
          } else {
            hexagonCell.click();
            int i = new CaptureMost().getScore(hexagonCell.getCoordinate(), model);
            System.out.println(i);
            hexagonCell.setFlipped(i);
            hexagonClicked = null;
          }


          // Optionally, you can perform additional actions here
          break; // Stop checking once a hexagon is clicked
        }

      }

      // Check if the click is within any hexagon
      for (HexagonCell hexagonCell : allHexagons) {
        if (hexagonCell.isMouseClick(new Point2D.Double(mouseX, mouseY))) {

          clickedOutside = false;


        }


      }


      if (clickedOutside) {

        for (HexagonCell hexagonCell : allHexagons) {
          if (hexagonCell.getClicked()) {

            hexagonCell.click();
            hexagonClicked = null;


          }


        }
      }

      repaint();
    }

  }

  /**
   * Handles mouse release for testing.
   * @param mouseX x position of the mouse.
   * @param mouseY y position of the mouse.
   * @return the hexagon on which the mouse is.
   */
  public Coordinate handleMouseRelease(int mouseX, int mouseY) {
    for (HexagonCell hexagonCell : allHexagons) {
      if (hexagonCell.isMouseClick(new Point2D.Double(mouseX, mouseY))) {
        return hexagonCell.getCoordinate();
      }
    }
    return null;
  }


  /**
   * Gets the logical coordinates of the hexagon that was clicked.
   *
   * @return The logical coordinates of the clicked hexagon.
   */
  public Coordinate getHexagonClickedCoordinates() {
    if (hexagonClicked != null) {
      return hexagonClickedLogicalCoordinate;
    }
    return null;

  }


  private ArrayList<HexagonCell> createListOfHexagons(HashMap<ICoordinate, CellType> board) {

    ArrayList<HexagonCell> hexagonList = new ArrayList<>();

    // Determine the board size based on the provided board's coordinates
    int givenBoardSize = 0;

    for (ICoordinate key : board.keySet()) {

      if (key.getRow() > givenBoardSize) {
        givenBoardSize = key.getRow();
      }
    }

    int gap = -(givenBoardSize / 2) - 1;
    boolean pastHalfRow = false;


    for (int row = 0; row < givenBoardSize + 1; row++) {

      if (row > (givenBoardSize / 2)) {
        pastHalfRow = true;
      }

      if (!pastHalfRow) {
        gap++;
      } else {
        gap++;
      }


      for (int col = 0; col < givenBoardSize + 1; col++) {

        CellType boardCell = board.get(new Coordinate(col, row));

        if (boardCell != null) {
          HexagonCell hexagonCell;
          if (row != givenBoardSize / 2) {
            hexagonCell = new HexagonCell(row, col, 30, gap, boardCell);
          } else {
            hexagonCell = new HexagonCell(row, col, 30, 0, boardCell);
            gap = 0;
          }
          hexagonList.add(hexagonCell);
        }
      }


    }


    return hexagonList;
  }





  @Override
  public void paintComponent(Graphics g) {



    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;


    // Determine the board size based on the provided board's coordinates
    int givenBoardSize = 0;

    for (ICoordinate key : board.keySet()) {

      if (key.getRow() > givenBoardSize) {
        givenBoardSize = key.getRow();
      }
    }


    for (HexagonCell hexagonCell : allHexagons) {

      hexagonCell.draw(g2d);
    }


  }


}
