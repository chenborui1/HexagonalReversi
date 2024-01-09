package view;

import org.junit.Test;

import java.util.HashMap;

import model.BasicReversi;
import model.CellType;
import model.Coordinate;
import model.ICoordinate;
import model.ReadonlyReversiModel;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the logic functionality of a HexPanel.
 */
public class HexPanelTest {

  /**
   * Test to see whether the clicked coordinates return the correct hexagon.
   */
  @Test
  public void testClickedCoordinates() {
    HashMap<ICoordinate, CellType> mockBoard = new HashMap<>();
    mockBoard.put(new Coordinate(1, 1), CellType.BLACK);
    mockBoard.put(new Coordinate(2, 2), CellType.WHITE);

    ReadonlyReversiModel mockModel = new BasicReversi(mockBoard, CellType.BLACK);

    HexPanel hexPanel = new HexPanel(mockModel);
    Coordinate result = hexPanel.handleMouseRelease(100, 100);

    assertEquals(new Coordinate(1, 1), result);
  }
}
