package controller;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.BasicReversi;
import model.CellType;
import model.ReversiModel;
import strategy.CaptureMost;
import view.IReversiView;
import view.ReversiView;

/**
 * Tests for reversi controller using a mock model.
 */
public class ReversiControllerTest {
  List<String> transcript = new ArrayList<>();


  private static Player createPlayer(ReversiModel model, String playerType, CellType cellType) {
    switch (playerType.toLowerCase()) {
      case "humanplayer":
        return new HumanPlayer(model, cellType);
      case "strategy1":
        return new AIPlayer(model, new CaptureMost(), cellType);
      default:
        throw new IllegalArgumentException("Invalid player type: " + playerType);
    }
  }

  @Test
  public void testControllerHandlesAIPlayerMoveAtStart() {


    //The line argument for a player at index 0 will be the black player
    //The line argument for a player at index 1 will be the white player

    String[] args = new String[2];

    args[0] = "strategy1";
    args[1] = "humanplayer";



    String blackPlayer = args[0];
    String whitePlayer = args[1];


    //Instantiate a new reversi model
    ReversiModel model = new BasicReversi(11);


    //Create views for each player
    IReversiView viewPlayer1 = new ReversiView(model);
    IReversiView viewPlayer2 = new ReversiView(model);

    //Instantiate player objects
    Player player1 = createPlayer(model, blackPlayer, CellType.BLACK);
    Player player2 = createPlayer(model, whitePlayer, CellType.WHITE);



    //Create a controller for each player
    MockController controller1 = new MockController(model, player1, viewPlayer1, transcript);
    MockController controller2 = new MockController(model, player2, viewPlayer2, transcript);

    //Start the game
    model.startGame();

    System.out.println(transcript);

    //Test that the AI player made a move when he is the black player at the start of the game
    //Controller handleMove is called
    Assert.assertTrue(transcript.contains("AIPlayer X has made a move at row: 3 column: 6"));

    //Test that the player turn changed and the notification is sent
    Assert.assertTrue(transcript.contains("Player turn changed"));

    //Test that the view has been updated
    Assert.assertTrue(transcript.contains("view has been updated"));




  }

  @Test
  public void testControllerHandlesAIPlayerSkip() {


    //The line argument for a player at index 0 will be the black player
    //The line argument for a player at index 1 will be the white player

    String[] args = new String[2];

    args[0] = "strategy1";
    args[1] = "strategy1";


    String blackPlayer = args[0];
    String whitePlayer = args[1];


    //Instantiate a new reversi model
    ReversiModel model = new BasicReversi(11);


    //Create views for each player
    IReversiView viewPlayer1 = new ReversiView(model);
    IReversiView viewPlayer2 = new ReversiView(model);

    //Instantiate player objects
    Player player1 = createPlayer(model, blackPlayer, CellType.BLACK);
    Player player2 = createPlayer(model, whitePlayer, CellType.WHITE);



    //Create a controller for each player
    MockController controller1 = new MockController(model, player1, viewPlayer1, transcript);
    MockController controller2 = new MockController(model, player2, viewPlayer2, transcript);

    //Start the game
    model.startGame();

    System.out.println(transcript);




    Assert.assertTrue(transcript.contains("AIPlayer X has passed"));
    Assert.assertTrue(transcript.contains("AIPlayer O has passed"));




  }

  @Test
  public void testControllerHandlesGameOverForBothPlayers() {


    //The line argument for a player at index 0 will be the black player
    //The line argument for a player at index 1 will be the white player

    String[] args = new String[2];

    args[0] = "strategy1";
    args[1] = "strategy1";


    String blackPlayer = args[0];
    String whitePlayer = args[1];


    //Instantiate a new reversi model
    ReversiModel model = new BasicReversi(11);


    //Create views for each player
    IReversiView viewPlayer1 = new ReversiView(model);
    IReversiView viewPlayer2 = new ReversiView(model);

    //Instantiate player objects
    Player player1 = createPlayer(model, blackPlayer, CellType.BLACK);
    Player player2 = createPlayer(model, whitePlayer, CellType.WHITE);



    //Create a controller for each player
    MockController controller1 = new MockController(model, player1, viewPlayer1, transcript);
    MockController controller2 = new MockController(model, player2, viewPlayer2, transcript);

    //Start the game
    model.startGame();

    System.out.println(transcript);




    Assert.assertTrue(transcript.contains("AIPlayer O Game is over"));
    Assert.assertTrue(transcript.contains("AIPlayer X Game is over"));




  }

  @Test
  public void testControllerHandlesGameInitializeForBothPlayers() {


    //The line argument for a player at index 0 will be the black player
    //The line argument for a player at index 1 will be the white player

    String[] args = new String[2];

    args[0] = "humanplayer";
    args[1] = "humanplayer";


    String blackPlayer = args[0];
    String whitePlayer = args[1];


    //Instantiate a new reversi model
    ReversiModel model = new BasicReversi(11);


    //Create views for each player
    IReversiView viewPlayer1 = new ReversiView(model);
    IReversiView viewPlayer2 = new ReversiView(model);

    //Instantiate player objects
    Player player1 = createPlayer(model, blackPlayer, CellType.BLACK);
    Player player2 = createPlayer(model, whitePlayer, CellType.WHITE);



    //Create a controller for each player
    MockController controller1 = new MockController(model, player1, viewPlayer1, transcript);
    MockController controller2 = new MockController(model, player2, viewPlayer2, transcript);

    //Start the game
    model.startGame();

    System.out.println(transcript);




    Assert.assertTrue(transcript.contains("HumanPlayer X game has started " +
            "and notified first player turn"));
    Assert.assertTrue(transcript.contains("HumanPlayer O game has started " +
            "and notified first player turn"));




  }


}
