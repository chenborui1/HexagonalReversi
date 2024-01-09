# Reversi Project

Overview The Reversi game implementation is a Java application that
allows you to play the classic Reversi (Othello) board game. Reversi is
a two-player strategy game where the goal is to have the most pieces of
your color on the board when the game ends.

Purpose The purpose of this codebase is to provide an interactive
Reversi game that can be played between two players. It also serves as a
demonstration of implementing a board game using Java. The game's rules
are based on the standard Reversi rules.

Quick Start To get started with playing the Reversi game, follow these
steps: 1. Navigate to the BasicReversi Test class 2. Scroll to bottom
and create a public test method The model is defined on the top with a
game size set to 7 as default. To start a game with the default size
board with equal number of black and white pieces at the center of the
board write the following command in your test class:

board.initializeGame();

To make a move (skip or move a disk) the following commands are:

board.makeMove(Celltype player , row, column); board.skip(Celltype
player);

Both players are defined at the top of the test class. Input "black" for
a black player and "white" for white player.

When moving a disk the supplied parameter is (Player, row, column).

Examples:

board.makeMove(black, 1, 4);

(Black player will put a black disk on row 1 and column 4)

board.skip(white);

(white player's turn is skipped)

If you attempt to make invalid moves or moves that aren't possible the
console should throw exceptions. These include brekaing the standard
Reversi rules given in the assignment.

You can also print out the textual view of the current board state by
printing out view.toString. The textual view object is defined on the
top of the class

If you wish to rig a board see the example test
"testDoubleRowFlippedTiles" in the test class for more commands.

Key Components: 1. BasicReversi (Model) Represents the core logic of the
Reversi game. Manages the game board, player turns, and move
validations. 2. ReversiTextualView (View) Provides a text-based view of
the game board. Renders the current state of the game for the players to
see.

Key Subcomponents: Within the BasicReversi component: Coordinate:
Represents a position on the game board. CellType: Enumerates the
possible cell states (empty, black, white). Various helper methods for
move validation and piece flipping.

Source Organization: model: Contains the BasicReversi class along with
its subcomponents. view: Contains the ReversiTextualView class.

Hexagonal Coordinate System Representation:

I used the Map Storage in Axial Coordinates section design
imeplementation to represent my board link:
https://www.redblobgames.com/grids/hexagons/

Instead of using a 2D array or array of array I used a hashmap to store
every individual Game board cell. So my game board type is a hashmap.

In my hashmap I take in two data types. A Coordinate object and a
CellType Enum.

A cell in a board game can be represented by its position and the
content.

The CellType Enum contains the enums EMPTY, WHITE, BLACK. These are the
states of a game cell in the board. The Coordinate object is created by
supplying a integer row and integer column. The hashmap key is the
coordinate object since every cell is unique. The value will be the
state of the board cell.

Varying board sizes is managed by supplying an integer into the model
object. This integer must be an odd number and is greater than the
integer 4 because there is no game to be played if the board size is
smaller than 5.

If a board size of 5 is supplied the initialize game method will
populate the hashmap with a 5 X 5 grid. Therefore 25 unique elements.
Maximu Row and column numbers will be the board size - 1. The row and
column numbers are 0 inclusive to board size - 1.

Example: initialize a game board with size 5

0 1 2 3 4 Column
0 - - - - -
1 - - - - -
2 - - - - -
3 - - - - -
4 - - - - -
Row

Each "-" line is a unique cell in the game board. This is not a
hexagonal shape however so my initialize game will assign every cell its
state to create a hexagon shape.

The initialize method loops through every element and their coordinate
making wasted space in the grid null and actual hexagon game cells EMPTY
to initialize the game board.

After initialization:

0 1 2 3 4 Column
0 n n - - -
1 n - - - -
2 - - - - -
3 - - - - n
4 - - - n n
Row

n are the null assigned values for a game size of 5. If we were to
retrieve the cell in the board with row = 0 column = 0, it would return
null. All dashes in the diagram would return EMPTY after initialization
of the game.

Example 2: Uninitialized board: Board size = 11

0 1 2 3 4 5 6 7 8 9 10
0 - - - - - - - - - - -
1 - - - - - - - - - - -
2 - - - - - - - - - - -
3 - - - - - - - - - - -
4 - - - - - - - - - - -
5 - - - - - - - - - - -
6 - - - - - - - - - - -
7 - - - - - - - - - - -
8 - - - - - - - - - - -
9 - - - - - - - - - - -
10 - - - - - - - - - - -

Initialized Board:

0 1 2 3 4 5 6 7 8 9 10 Column
0 N N N N N - - - - - -
1 N N N N - - - - - - -
2 N N N - - - - - - - -
3 N N - - - - - - - - -
4 N - - - - - - - - - -
5 - - - - - - - - - - -
6 - - - - - - - - - - N
7 - - - - - - - - - N N
8 - - - - - - - - N N N
9 - - - - - - - N N N N
10 - - - - - - N N N N N

Row

Player interface design:

The player interface would include the methods getName(): This method
returns the name of the player. It allows any player, human or AI, to
specify their name.

getPlayerColor(): Players in Reversi are typically assigned a color
(BLACK or WHITE). This method returns the player's color. It's crucial
to keep track of the player's color for the game logic.

makeMove(ReversiModel gameModel): This method enables the player to make
a move in the Reversi game. It receives the game model as a parameter,
allowing the player to interact with the current game state. The
implementation of this method will vary for human and AI players.

By defining this interface, we ensure that all types of players (human
or AI) can participate in the game.

The interface will be implemented by a AbstractPlayer class since we do
not know if the player is human or AI.

This design promotes modularity, allowing different player
implementations to be easily integrated into the game. For example, a
HumanPlayer class would implement makeMove() to handle user input, while
an AIPlayer class would implement its logic to make moves autonomously.
Both AIPlayer and HumanPlayer classes would extend to the abstract class
Player. The abstract class Player would then implement the Player
interface.

For now I have represented the player as a CellType BLACK or WHITE since
the player's identity in the game is characterized by the color of their
game piece. Until further information is given on the assignment the
move and skip methods both take in a cell type instead of a player for
now.

Changes for part 2:

Additional Methods in BasicReversi Implementation :
In the BasicReversi class, the following methods have been added to enhance
 the functionality of the Reversi game implementation:

1. getContent(int row, int column)
This method allows you to retrieve the content of a specific cell on the
game board based on its row and column coordinates.

2. canMakeMove(int row, int column)
This method checks if a move can be made at the specified row and column.
It internally calls the validateMove method, ensuring that the move follows
the rules of the Reversi game.

3. playerLegalMoves()
This method checks if the current player has legal moves on the board.
It iterates through all cells on the board and checks if a legal move can
be made at each position. If at least one legal move is found, the method
returns true; otherwise, it returns false. This is useful for determining
if a player can make a move during their turn.


Added new ReadOnlyReversiModel Interface

The ReadonlyReversiModel interface defines
the methods required for a read-only representation
of a Reversi game model. Implementations of this
interface should provide information about the current
state of the game board, players, and moves without
allowing modifications to the game state.


The methods in this interface are only methods that do not
modify the game state. These include:
int getBoardSize()
HashMap<Coordinate, CellType> getGameBoard()
CellType getCurrentPlayerTurn()
boolean isGameOver()
char getWinner()
int[] getScores()
CellType nextPlayer()
CellType getContent(int row, int column)
boolean playerLegalMoves()
boolean canMakeMove(int row, int column)

The ReadOnlyReversiModel is used heavily on the Reversi view implementation illustrated below.




Added View Implementation and Interface:
Below are the interfaces and classes explanations associated with the
new view implementation:


IReversiView Interface:

The IReversiView interface outlines the contract for a Reversi game
view. Classes implementing this interface are expected to provide
methods for managing the visibility of the view. The model passed
into the view implementation is always a ReadOnlyReversiModel.

Methods:

-setVisible(boolean set)

public void setVisible(boolean set);
This method sets the visibility of the Reversi view. The set parameter
determines whether to set the view as visible (true) or invisible (false).

-Purpose
The purpose of this interface is to establish a common contract for
Reversi game views. By defining a standardized method for managing
visibility, it allows different implementations of the view to be
easily integrated into the game. This promotes flexibility and
modularity in the design, enabling the use of various view implementations
without affecting the core functionality of the game.





HexagonCell Class:

The HexagonCell class is an essential component in the graphical representation
of the Reversi game board. It extends JPanel to leverage GUI rendering c
apabilities. This class is responsible for rendering individual hexagonal
cells on the game board, each representing a playable position.


-Constructor

public HexagonCell(int row, int col, int width, int gap, CellType cellType)
The constructor initializes a HexagonCell with the specified
row, column, width, gap, and cell type. The parameters define
the position and appearance of the hexagon on the game board.
The constructor calculates the coordinates of the hexagon's
vertices based on its position and dimensions.

-Drawing Method

public void draw(Graphics g)
The draw method is responsible for rendering the hexagon on the provided Graphics context.
It fills the hexagon with a color based on its state, outlines it in black,
and renders a disc (either black or white) based on the CellType.
The hexagon's appearance changes if it has been clicked, highlighting it with a cyan color.

-Click Handling

public boolean isMouseClick(Point2D mouseLocation)
The isMouseClick method checks whether a given point (representing a mouse click)
is within the bounds of the hexagon. This method is crucial for determining
if a player has clicked on a specific cell during their turn.

-Coordinate and Click Status

public Coordinate getCoordinate()
public boolean getClicked()
public void click()
These methods provide information about the hexagon. getCoordinate
returns the coordinate of the hexagon on the game board. getClicked
returns the current state of the mouse click status for the hexagon.
The click method toggles the mouse click status, allowing for easy
tracking of user interactions.




HexPanel Class:

The HexPanel class visualizes the Reversi game board. It extends JPanel and
is responsible for managing and rendering a collection of hexagonal cells.
Additionally, it handles mouse click events to enable user interaction with the hexagonal cells.

-Constructor

public HexPanel(ReadonlyReversiModel model)
The constructor takes a ReadonlyReversiModel as a parameter, representing
the game model to be visualized. It initializes the panel with a dark gray
background and creates a list of hexagonal cells using the createListOfHexagons method.

-Mouse Listener

private class MyMouseListener extends MouseAdapter
This inner class is a MouseListener that handles mouse events within the
 HexPanel. It specifically responds to mouse release events (mouseReleased).
When a hexagon is clicked, it captures the logical coordinates of the
clicked hexagon, prints the information to the console, and manages
the visual state of the hexagons based on the click.

-createListOfHexagons Method

private ArrayList<HexagonCell> createListOfHexagons(HashMap<Coordinate, CellType> board)
This method generates a list of HexagonCell objects based on the provided
game board. It determines the board size, iterates through the board's
coordinates, and creates hexagonal cells for non-null board cells.
The method calculates the gap between hexagons to create a visually appealing layout.

-paintComponent Method

@Override
public void paintComponent(Graphics g)
The paintComponent method is overridden to customize the rendering of the HexPanel.
It calls the draw method for each HexagonCell in the list, ensuring that
the entire game board is visually represented.





ReversiView Class:

The ReversiView class represents the graphical user interface (GUI) for the Reversi game.
It extends JFrame and provides a frame that displays the Reversi game board using a HexPanel.

-Constructor

public ReversiView(ReadonlyReversiModel model);
This constructor initializes a ReversiView for the given ReadonlyReversiModel.
It sets up the frame's size, default close operation, layout, and adds a HexPanel
to display the Reversi game board. Additionally, it attaches a KeyListener
to the frame to handle key events.

Inner Class:
-MyKeyListener

private static class MyKeyListener extends KeyAdapter {
  @Override
  public void keyPressed(KeyEvent e) {
    // Handle key press event
    char keyChar = e.getKeyChar();
    if (keyChar == 'm') {
      System.out.println("Player moved");
    } else if (keyChar == 'p') {
      System.out.println("Player passed");
    }
  }
}

This inner class serves as a KeyListener to handle key events
within the ReversiView.
It specifically listens for the 'm' and 'p' keys, printing
messages to the console when they are pressed.

Reverse Class:

This class contains the main method to run and display the view for
a Reverse model. In the main method we create a readonly reverse
model for a rigged and unrigged game board. When you run the main
method the Reverse view for the given board inputted should be
launched. Once in the view you may press 'm' key to print out to
the console that the current player indicated to make a move. If
the 'p' key is pressed then it will print to the console that the
player has indicated to pass. Additionally clicking on a game
board cell will highlight that cell and turn the cell to a cyan
color. It will also print out in the console that hexagon cell's
logical coordinates. The user can deselect a selected cell
by (1) clicking on it again, (2) clicking on another cell, or
(3) clicking outside the boundary of the board. Clicking
outside the boundary of the board does not cause the view
to crash or throw an exception.


Implemented Strategies

Also implemented the first strategy called CaptureMost.
This strategy picks the legal move on a game board for the
current player prioritizing the maximum number of cells that
can be flipped.


Interface:

-ReversiStrategy

The ReversiStrategy interface defines the contract for
implementing different strategies that determine the next
 move in a Reversi game.

Interface Overview:

public interface ReversiStrategy {
}
This interface provides a method signature for choosing the
coordinate for the next move based on a specific strategy's evaluation.

Method:

Coordinate chooseCoordinate(ReadonlyReversiModel model, CellType player);

This method is responsible for choosing the coordinate for
the next move based on the strategy's evaluation. It takes two parameters:
model: The ReadonlyReversiModel providing the current state of the Reversi game.
player: The CellType representing the current player making the move.
The implementing classes will provide their own logic for
evaluating the game state and deciding the best coordinate for the next move.


CaptureMost Class:

The CaptureMost class represents a Reversi strategy that
aims to capture the most opponent's pieces. It implements
the ReversiStrategy interface, providing a method to
choose the next move based on evaluating potential moves
and selecting the one that results in the highest
number of captured pieces.


Class Overview

public class CaptureMost implements ReversiStrategy {
}
This class implements the ReversiStrategy interface,
indicating that it provides a strategy for making moves
in a Reversi game.

Fields:

private int bestScore = 0;
private ReadonlyReversiModel model;
bestScore: Keeps track of the highest score achieved
during the evaluation of potential moves.
model: Reference to the Reversi game model.

Methods:

@Override
public Coordinate chooseCoordinate(ReadonlyReversiModel model, CellType player) {
}

chooseCoordinate: Implements the method from the
ReversiStrategy interface. It chooses the coordinate
for the next move based on the strategy's evaluation,
considering the player making the move. It throws
exceptions for cases where it's not the correct
player's turn, the game is over, or the current player has no available moves left.


private int getScore(Coordinate c)
private Coordinate getTopLeft(ArrayList<Coordinate> topScoringCoordinates)
private int getMaxNumberOfPiecesFlipped(ArrayList<Coordinate> foundPieces, Coordinate placedCoordinate)
private ArrayList<Coordinate> getSamePieceBetween(Coordinate c)
private ArrayList<Coordinate> samePieceExistsAndFlip(String direction, Coordinate c)
private String getDirection(Coordinate validAdjacentCoordinate, Coordinate c)
These are helper methods used in the strategy for evaluating
potential moves and calculating the number of captured pieces in different directions.

Overall Purpose:
The CaptureMost class is designed to provide a strategy for
 making moves in a Reversi game. It evaluates potential moves,
choosing the one that results in the highest number of captured
opponent's pieces. The class is part of a strategy pattern,
allowing different strategies to be employed by a Reversi player.


Added Mock Model for Strategy Testing:

Classes:

MockModelStrategyOne
The MockModelStrategyOne class is a mock implementation of the
ReadonlyReversiModel interface. It records the transcript of
coordinates inspected during the execution of a strategy that checks all legal moves.

Fields:
List<String> transcript

Purpose: The transcript of coordinates inspected during the execution of the strategy.
Access Modifier: protected
ReadonlyReversiModel model

Purpose: The actual model that is being mocked.
Access Modifier: protected
Coordinate mostValuableMove

Purpose: The predetermined most valuable move.
Access Modifier: private final
Constructors:
MockModelStrategyOne(List<String> transcript, ReadonlyReversiModel model)
Purpose: Constructs a MockModelStrategyOne with the given transcript and model.
Parameters:
transcript: The list to store the transcript of inspected coordinates.
model: The model to mock.
Exceptions:
IllegalArgumentException if transcript or model is null.
Methods:
int getBoardSize()

Purpose: Get the size of the game board.
Returns: The size of the game board, which is a single integer representing the number of rows/columns.
HashMap<Coordinate, CellType> getGameBoard()

Purpose: Get the current state of the game board, including disc positions.
Returns: A HashMap containing coordinates (keys) and the corresponding CellType (values).
CellType getCurrentPlayerTurn()

Purpose: Get the current player's color ('B' for black, 'W' for white).
Returns: The current player's color, represented as a CellType (either CellType.BLACK or CellType.WHITE).
boolean isGameOver()

Purpose: Returns whether the game is over. The game is over when both players skip after each other.
Returns: true if the game is over; false otherwise.
char getWinner()

Purpose: Get the winner of the game ('B' for black, 'W' for white, 'N' for no winner).
Returns: The winner's color ('B' for black, 'W' for white) or 'N' for a tie or an ongoing game.
int[] getScores()

Purpose: Get the score for both players (number of discs).
Returns: An array where the first element is the black player's score
and the second element is the white player's score.
CellType nextPlayer()

Purpose: Returns the color of the player who has the next turn.
Returns: A CellType representing the color of the player who has the
next turn ('B' for black or 'W' for white).
CellType getContent(int row, int column)

Purpose: Gets the content of a cell at the specified coordinates.
Parameters:
row: The row index of the cell.
column: The column index of the cell.
Returns: The CellType (content) of the cell at the given coordinates.
boolean playerLegalMoves()

Purpose: Checks if the current player has legal moves to make on the game board.
Returns: true if the current player has legal moves, false otherwise.
boolean canMakeMove(int row, int column)

Purpose: Mock model manipulates the canMakeMove method to add all legal
moves at the current board state to an array list of legal moves available.
Parameters:
row: The row index of the cell.
column: The column index of the cell.
Returns: true if a move can be made, false otherwise.
List<String> getTranscript()

Purpose: Get the transcript of coordinates inspected during the execution of the strategy.
Returns: The list containing the transcript of inspected coordinates.

Overall Purpose:
The MockModelStrategyOne class serves as a mock implementation
of the ReadonlyReversiModel interface, specifically designed to
record the transcript of coordinates inspected during the execution
of a strategy that checks all legal moves. It mimics the behavior
of a Reversi game model while providing additional functionality
to log and retrieve information about the strategy's execution.

Created new Test classes:

-CaptureMostTest
This test class tests the CaptureMost class public methods

-StrategyMockTest
This test class tests all the strategy 1 mocks on the CaptureMost class
To see if the strategy is working as intended. Each test prints out the transcript in the console
Each test checks whether that transcript contains the tested strategy functionality

Changes for part 3:

New Interfaces implemented and created:

-Player
-ModelStateFeatures
-PlayerActionFeatures


New classes implemented and created:

-AIPlayer
-HumanPlayer
-ReversiController
-MockController

New methods implemented to Reversi Model Interface:

-void startGame()
-void addModelStateListener(ModelStateFeatures listener)

New methods implemented to IReversiView interface:

-void updateView()
-addPlayerActionListener(PlayerActionFeatures listener)

Purpose and justification of interfaces and classes created are mentioned in 
the documentation in their own class files.
The flow of the program and how the listeners interact with view and model are also in the documentation. 


Overview:
Created a controller for the reversi model to allow the game to be played. Since Reversi 
is a two player game each player must have their own controller, view, and their game 
piece color. Command line arguments are supplied into the main method to instantiate 
what types of players are to be created. Either a human player of AI player. We 
instantiate a Reversi model first. Then create two IReversiViews that take in a 
ReadOnlyReversiModel for each player. We then instantiate the player objects 
according to the command lines that were chosen. Then we create respective 
controllers for each player where each controller takes in the same mutable 
model, a player, and a view. Once the controllers are created we run the startGame method for our model. 
To test if the controller is handling the game state changes and listening to the model, views, or 
players I created a mock controller class as well to help with the testing of the controller.

How to run the game:

1.Download the assignment7 jar file given to you
2.Open terminal or command prompt and change your directory to where the jar file is located
3.Run the command: java -jar assignment7.jar "INSERT COMMAND ARGUMENT HERE" "INSERT COMMAND ARGUMENT HERE"

The command arguments that you can run are:

"humanplayer"
"strategy1"

You must insert two command line arguments for the game to run. These arguments 
determine what type of player you want to have when you start the game. 
For example if you supply the arguments "humanplayer" "humanplayer" the 
game will be played with two players interacting with the views by 
pressing keys to make a move or skip. 
If you supply the arguments "humanplayer" "strategy1", a human player and an AI player will be created. 
The AI player is automatically assigned the specific strategy of your command argument. 
Since only one strategy has been implemented, strategy1 is the capture most strategy in the assignment. 

IMPORTANT:
The first player command argument supplied into terminal will have the BLACK game pieces
The second player command argument supplied into terminal will have the WHITE game pieces
(More information given in the Reversi main method class)

When the program is run two view for each player will be set visible. 
One view will overlap another making it look like only one view is created. 
THE BLACK PLAYER'S VIEW IS THE HIDDEN VIEW THAT IS COVERED WHEN THE GAME IS STARTED
THE WHITE PLAYER's VIEW IS THE VISIBLE VIEW WHEN THE GAME IS STARTED

How to play the game:

The player with the black gamepiece will always have the first turn. 
A message dialogue will appear at the start of the game for the black player notifying it is his turn first.

For Human players:

If you supplied a human player in the command argument you can make a move or pass by pressing certain keys

1.To make a move as a human you must first select a cell on your own view by 
clicking on that cell. The cell should then highlight a cyan color. 

2.Press the 'm' key while the cell is selected to make your move. 

3.Press the 'p' key to pass your current turn (it does not matter whether 
you selected a cell or not if you choose to pass)


For AI players:

The AI players will automatically make their move if it is their gamepiece color's turn. 


Notifications when playing the game:

1.Whenever any player makes a move, a message dialogue box will show up for 
the opposing player notifying them that he has made a move and that it is 
the opposing player's turn to make a move. 
If playing against an AI player as a human player for example two messages 
will be displayed if a human player makes a move. A notification will be 
sent to the AI player's view telling it that the human player has made a 
move and that it's the AI player's turn. Since the AI player makes a move 
almost instantly the AI player will then send a notification to the human 
player that the AI player has made a move and now its the human player's 
turn. Therefore two notifications will instantly pop up on the screen. 

2.Whenever any player passes, a message dialogue box will show up for 
the opposing player notifying them that the player has passed his turn. 

3.If both player pass consecutively, then a Game ending dialogue box 
will show up stating who the winner was and what score they had. 
After this message is shown interacting with the view further will result 
in a new message box popping up saying that the game has concluded. 

4. If you try to make a move or pass on a human player view when it 
is not your turn a notification will be sent to the player saying 
that "It is not your turn".

5. If you try to interact with an AI player's view at any time a 
message will be shown saying "It is not your turn". Humans can't interact 
with the AI player view because the AI player makes its move instantly 
and switches the current player turn instantly as well. So interacting 
with the AI player's view will be fruitless. 

6. If you run the game with two AI players playing against each other 
a notification will be sent to the Black AI player saying that it's their turn. 
Once that message is closed the game will be played almost instantly 
generating every move's notification instantly. 
This will result in the game ending notification to be displayed 
for both players and every move notification to be displayed as well. 

Changes for part 4:


New Command line options:

"providerstrategy1"
"providerstrategyanyopenspace"

To run my provider's strategies use these command lines.

Features that I was able to get working:

I was able to successfully implement the provider's view with my controller and model
implementation.
I can run any of my own strategies or command lines on my view and the provider's view.
I managed to get two of the provider's strategy implementation to work which was their captuermost
strategy and a strategy they wrote themselves called strategyanyopenspace.

I could not get their strategymovetocorner and strategymovetoouterlayer strategies to implement in
my
Code base. This is because the providers supplied me with code that cannot be compiled.

For all of his strategies he relied on creating a mutable Reversimodel.
He instantiated his own HexReversi model to simulate moves on the board to
determine the most valuable moves in his strategies. Since I do not have
access to his model implementation I cannot compile his code to run his strategies fully.
However I was able to get his capturemost strategy and providerstatgyanyopenspace to
run by creating his ReversiModel implementations in the adopter package.
I created artificial classes HexBoard, HexReversi, and OffsetHexCoordinates.
These were the classes that were needed to compile his code but I did not receive.
In these classes my only choice was to adopt my mutable model implementation and simulate
making moves on my model implementation. Therefore in these classes I adopted my mutable model implementation to run for his strategies
so I could implement his strategies.
He also creates strategy with a constructor that
Takes in a board which is incorrect. This caused me not to be able to implement his
StrategyMoveToCorner since it wasn't technically a function object. For some reason he takes in
A Board when instantiating this strategy but not the others. Therefore I could not implement
His StrategyMoveToCorner and StrategyMoveToOuterLayer.


I tried asking my provider repeatedly to fix this issue in his strategy
implementation by not simulating moves with his own mutable model but he did not fix his code.
Therefore I couldn't implement one of this extra credit strategies effectively.

Since his strategy code base was not compilable I had trouble implementing the feature of getting
my own strategy1 AIPlayer to run with his StrategyMaximizeCurrentDiscs strategy. Though the both
AI players eventually make all their moves if you run the jar file you have to click through a lot
of message dialogues to reach the end of the AIPlayers making moves. I could not get his strategy
to work on my view.

Running the jar file:

When the jar file is ran it will create my own view and the provider's view.
My own view will be for the black player and the provider's view will have the white player's color.


Example:Creates my own view with my own human player implementation and a provider view
with their strategy implementation
After changing directory to where my java file is
use the command: java -jar assignment8.jar "humanplayer" "providerstrategy1"

If you decide to put my own human player implementation for my provider's view 
You must select a cell on his view and press the enter to make a move. Press the key 'p' to pass.

change for the last homework(extra credit)
New interface:
ICoordinate which is implemented by SquareCoordinate and Coordinate(for hexagon)
New classes implemented and created:
-SquareReversi
-SquareCoordinate
-SquareCell
-SquareReversiTexualView
-SquareReversiView
-SquarePanel

The square reversi game is quite similar to the hexagon reversi game
and we add hints in our panel as needed for level 0.
the new command enter should be like this:
"humanplayer" "strategy1" "square"































