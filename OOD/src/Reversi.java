import controller.AIPlayer;
import controller.ReversiController;
import model.BasicReversi;
import controller.HumanPlayer;
import controller.Player;
import model.CellType;
import model.ReversiModel;
import adopt.ModelAdopter;
import adopt.StrategyAdopter;
import model.SquareReversi;
import provider.StrategyAnyOpenSpace;
import provider.StrategyMaximizeCurrentDiscs;
import provider.StrategyMoveToCorner;
import strategy.CaptureMost;
import view.IReversiView;
import view.ReversiView;
import view.SquareReversiView;


/**
 * The `Reversi` class is the entry point for running the Reversi game.
 * It initializes a Reversi board, makes some initial moves, and displays the game using the
 * ReversiView.
 */

public final class Reversi {

  /**
   * The main method to start the Reversi game.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {

    /*
    if (args.length > 2 || args.length == 0) {
      System.out.println("Incorrect number of command lines");
      System.exit(1);
    }

    String blackPlayer = args[0];
    String whitePlayer = args[1];

    SquareReversi model = new SquareReversi(8);

    ModelAdopter myModel = new ModelAdopter(model);

    IReversiView squareView1 = new SquareReversiView(myModel);
    IReversiView squareView2 = new SquareReversiView(myModel);

    //Instantiate player objects
    Player player1 = createPlayer(myModel, blackPlayer, CellType.BLACK);
    Player player2 = createPlayer(myModel, whitePlayer, CellType.WHITE);





    //Create a controller for each player
    ReversiController controller1 = new ReversiController(model, player1, squareView1);
    ReversiController controller2 = new ReversiController(model, player2, squareView2);



    //Start the game
    model.startGame();


*/


    if (args.length > 3 || args.length == 0) {
      System.out.println("Incorrect number of command lines");
      System.exit(1);
    }

    //The line argument for a player at index 0 will be the black player
    //The line argument for a player at index 1 will be the white player

    String blackPlayer = args[0];
    String whitePlayer = args[1];


    //Instantiate a new reversi model
    ReversiModel model = makeModel(args[2], 10);

    ModelAdopter myModel = new ModelAdopter(model);




    //Create views for each player
    IReversiView viewPlayer1 = createView(args[2], model);
    IReversiView viewPlayer2 = createView(args[2], model);






    //Instantiate player objects
    Player player1 = createPlayer(myModel, blackPlayer, CellType.BLACK);
    Player player2 = createPlayer(myModel, whitePlayer, CellType.WHITE);



    //Create a controller for each player
    ReversiController controller1 = new ReversiController(model, player1, viewPlayer1);
    ReversiController controller2 = new ReversiController(model, player2, viewPlayer2);



    //Start the game
    model.startGame();


  }

  /**
   * The condition method to start the Reversi game.
   *
   * @param type the type of game
   * @param size the size of board
   *
   * @return a reversimodel needed
   */
  private static ReversiModel makeModel(String type, int size) {
    if (type.equals("square")) {
      return new SquareReversi(size);
    }
    else if (type.equals("hexagon")) {
      return new BasicReversi(size + 1);
    }
    else {
      throw new IllegalArgumentException("Illegal input");
    }
  }

  private static IReversiView createView(String type, ReversiModel model) {
    if (type.equals("square")) {
      return new SquareReversiView(model);
    }
    else if (type.equals("hexagon")) {
      return new ReversiView(model);
    }
    else {
      throw new IllegalArgumentException("Illegal input");
    }
  }

  private static Player createPlayer(ModelAdopter model, String playerType, CellType cellType) {
    switch (playerType.toLowerCase()) {
      case "humanplayer":
        return new HumanPlayer(model, cellType);
      case "strategy1":
        return new AIPlayer(model, new CaptureMost(), cellType);
      case "providerstrategy1":
        return new AIPlayer(model,
                new StrategyAdopter(new StrategyMaximizeCurrentDiscs()), cellType);
      case "providerstrategyanyopenspace":
        return new AIPlayer(model, new StrategyAdopter(new StrategyAnyOpenSpace()), cellType);
      case "providerstrategy2":
        return new AIPlayer(model,
                new StrategyAdopter(new StrategyMoveToCorner(model.getCopyOfBoard())), cellType);
      default:
        throw new IllegalArgumentException("Invalid player type: " + playerType);
    }
  }
}