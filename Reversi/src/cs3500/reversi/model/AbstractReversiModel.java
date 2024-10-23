package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.reversi.controller.ReversiController;

/**
 * Represents the primary model for a reversi board. A game of reversi has
 * two players, black and white, and they play on a hexagon shaped board,
 * with black moving first.
 */
public abstract class AbstractReversiModel implements ReversiModel {

  // We chose the axial coordinate system for representing hexagons as a 2-dimensional array
  // because the game board is always the same size, so it makes sense for the dimensions to
  // be immutable. We also liked how the coordinates of each of the tiles were indexed, with
  // the x coordinate increasing from left to right, and the y coordinate increasing from
  // top to bottom.

  // Coordinates are column (0-indexed from the left) then row (0-indexed from the top)
  // We chose a 2d array for the game board because the game board is always the same size,
  // so it makes sense for the dimensions to be immutable.
  // CLASS INVARIANT: the gameBoard is never null, and the dimensions of the board are always
  // middleWidth by middleWidth
  protected TileType[][] gameBoard;

  // The length of the board at its widest point, it is an integer because the length of the middle
  // has to be a whole number
  // CLASS INVARIANT: middleWidth is always positive, an odd number, and greater than or equal to 5
  protected int middleWidth;

  // The length of one of the sides of the hexagon, it is an integer because the length of the
  // middle has to be a whole number
  // CLASS INVARIANT: sideLength is always positive and greater than or equal to 3
  //private int sideLength;

  // True if the game has started, false otherwise
  protected boolean gameStarted;

  // A list of controllers listening to the current game
  protected final List<ReversiController> listeners = new ArrayList<>();

  // Represents whose turn it is
  protected TileType currentPlayer;

  // a list whose size determines the number of consecutive times players have passed
  // It is an arraylist because its size can vary
  // CLASS INVARIANT: passes is never null, if the size of passes is greater than or equal to
  // 2 then the game is always over
  protected List<TileType> passes;

  @Override
  public void addListener(ReversiController controller) {
    if (this.listeners.size() >= 2) {
      throw  new IllegalStateException("Too many players");
    }
    if (Objects.isNull(controller)) {
      throw new IllegalArgumentException("Player is not acceptable");
    }
    this.listeners.add(controller);
  }

  @Override
  public void startGame(int sideLength) {
    // need to override
  }

  @Override
  public void startGame(TileType[][] board) {
    // need to override
  }

  @Override
  public void pass() {
    this.validateGameStarted();
    this.passes.add(this.currentPlayer);
    this.changeCurrentPlayer();
    this.notifyListeners();
  }

  @Override
  public void play(int col, int row) {
    this.validateGameStarted();
    this.validateCoordinates(col, row);
    if (this.playerAt(col, row) != TileType.EMPTY) {
      throw new IllegalStateException("This move is not valid");
    }
    List<Direction> flipDirections = this.findFlipDirections(col, row);
    if (flipDirections.isEmpty()) {
      throw new IllegalStateException("This move is not valid");
    }
    for (Direction direction : flipDirections) {
      int nextCol = col + direction.getCol();
      int nextRow = row + direction.getRow();
      this.flipLine(nextCol, nextRow, direction);
    }
    this.setPlayer(col, row);
    this.changeCurrentPlayer();
    this.passes.clear();
    this.notifyListeners();
  }

  @Override
  public TileType[][] getBoard() {
    this.validateGameStarted();
    TileType[][] newBoard = new TileType[middleWidth][middleWidth];
    for (int i = 0; i < middleWidth; i++) {
      System.arraycopy(this.gameBoard[i], 0, newBoard[i], 0, middleWidth);
    }
    return newBoard;
  }

  @Override
  public TileType getCurrentPlayer() {
    this.validateGameStarted();
    return this.currentPlayer;
  }

  @Override
  public int getScoreFor(TileType player) {
    this.validateGameStarted();
    if (player != TileType.WHITE && player != TileType.BLACK) {
      throw new IllegalArgumentException("Cannot check the score for this player.");
    }
    int count = 0;
    for (int i = 0; i < middleWidth; i++) {
      for (int j = 0; j < middleWidth; j++) {
        if (gameBoard[i][j] == player) {
          count++;
        }
      }
    }
    return count;
  }

  @Override
  public boolean isGameOver() {
    this.validateGameStarted();
    // passes INVARIANT: if the size of passes is greater than or equal to 2, then the game is over
    return this.passes.size() >= 2;
  }

  @Override
  public TileType getWinner() {
    this.validateGameStarted();
    int blackCount = this.getScoreFor(TileType.BLACK);
    int whiteCount = this.getScoreFor(TileType.WHITE);
    if (blackCount > whiteCount) {
      return TileType.BLACK;
    } else if (whiteCount > blackCount) {
      return TileType.WHITE;
    } else {
      return TileType.EMPTY;
    }
  }

  @Override
  public TileType playerAt(int col, int row) {
    this.validateGameStarted();
    this.validateCoordinates(col, row);
    return this.gameBoard[col][row];
  }

  @Override
  public boolean isMoveValid(int col, int row) {
    this.validateGameStarted();
    try {
      if (this.playerAt(col, row) != TileType.EMPTY) {
        return false;
      }
      return !this.findFlipDirections(col, row).isEmpty();
    } catch (IllegalArgumentException iae) {
      return false;
    }
  }

  @Override
  public boolean hasValidMove() {
    this.validateGameStarted();
    for (int col = 0; col < this.middleWidth; col++) {
      for (int row = 0; row < this.middleWidth; row++) {
        try {
          if (this.isMoveValid(col, row)) {
            return true;
          }
        } catch (IllegalArgumentException iae) {
          continue;
        }
      }
    }
    return false;
  }

  @Override
  public int flipCount(int col, int row) {
    this.validateGameStarted();
    this.validateCoordinates(col, row);
    if (!this.isMoveValid(col, row)) {
      throw new IllegalStateException("This move is invalid");
    }
    List<Direction> directions = this.findFlipDirections(col, row);
    int flipSum = 0;
    for (Direction direction : directions) {
      int nextCol = col + direction.getCol();
      int nextRow = row + direction.getRow();
      flipSum += this.flipLength(nextCol, nextRow, direction);
    }
    flipSum++;
    return flipSum;
  }

  @Override
  public List<Direction> possibleDirections() {
    return new ArrayList<>();
  }

  @Override
  public int getBoardWidth() {
    return this.middleWidth;
  }

  @Override
  public int getBoardSideLength() {
    return this.middleWidth;
  }

  // notifies the player whose turn it now is to take their turn
  protected void notifyListeners() {
    for (ReversiController listener : this.listeners) {
      listener.takeTurn();
    }
  }

  // returns a list of directions in which a valid move can be made
  protected List<Direction> findFlipDirections(int col, int row) {
    List<Direction> directions = new ArrayList<>();
    List<Direction> possibleDirections = this.possibleDirections();
    for (Direction direction : possibleDirections) {
      int nextCol = col + direction.getCol();
      int nextRow = row + direction.getRow();
      try {
        this.validateCoordinates(nextCol, nextRow);
      } catch (IllegalArgumentException iae) {
        continue;
      }
      if ((!(this.playerAt(nextCol, nextRow)
              == this.currentPlayer
              || this.playerAt(nextCol, nextRow)
              == TileType.EMPTY))
              && this.isSurrounded(nextCol, nextRow, direction)) {
        directions.add(direction);
      }
    }
    return directions;
  }

  // sets the tile at the given coordinates to the current player
  protected void setPlayer(int col, int row) {
    this.validateCoordinates(col, row);
    if (this.gameBoard[col][row] != TileType.EMPTY) {
      throw new IllegalStateException("Tile is already taken");
    }
    this.gameBoard[col][row] = this.currentPlayer;
  }

  // returns true if there is another tile of the same player as the player
  // at the given coordinates
  protected boolean isSurrounded(int col, int row, Direction direction) {
    int nextCol = col + direction.getCol();
    int nextRow = row + direction.getRow();
    try {
      this.validateCoordinates(nextCol, nextRow);
    } catch (IllegalArgumentException iae) {
      return false;
    }
    if (this.playerAt(nextCol, nextRow) == TileType.EMPTY) {
      return false;
    }
    if (this.playerAt(nextCol, nextRow) == this.currentPlayer) {
      return true;
    }
    return this.isSurrounded(nextCol, nextRow, direction);
  }

  // flips the tile in the given direction from the given coordinates
  protected void flipLine(int col, int row, Direction direction) {
    this.validateCoordinates(col, row);
    if ((this.playerAt(col, row) != this.currentPlayer)) {
      if (this.playerAt(col, row) == TileType.EMPTY) {
        throw new IllegalStateException("Cannot flip an unclaimed tile");
      }
      this.gameBoard[col][row] = this.currentPlayer;
      int nextCol = col + direction.getCol();
      int nextRow = row + direction.getRow();
      this.flipLine(nextCol, nextRow, direction);
    }
  }

  // returns how far the next tile that is the same color as
  // the current player
  protected int flipLength(int col, int row, Direction direction) {
    int nextCol = col + direction.getCol();
    int nextRow = row + direction.getRow();
    if (this.playerAt(nextCol, nextRow) == this.currentPlayer) {
      return 1;
    }
    else {
      return this.flipLength(nextCol, nextRow, direction) + 1;
    }
  }

  // Initializes the 2D array for the game board.
  protected void initEmptyBoard() {
    // needs to be extended
  }

  // Sets the starting pieces for the game.
  protected void initGameState() {
    // needs to be extended
  }

  // Changes the current player to the next
  protected void changeCurrentPlayer() {
    if (this.currentPlayer == TileType.WHITE) {
      this.currentPlayer = TileType.BLACK;
    } else if (this.currentPlayer == TileType.BLACK) {
      this.currentPlayer = TileType.WHITE;
    } else {
      throw new IllegalStateException("The current player is not viable");
    }
  }

  // Throws an exception if the game has already started
  protected void validateGameNotStarted() {
    if (this.gameStarted) {
      throw new IllegalStateException("Game has already started");
    }
  }

  // Validates that the game has started
  protected void validateGameStarted() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not yet started");
    }
  }

  // Validates that a board can be used for a game of Reversi
  protected void validateBoard(TileType[][] board) {
    // needs to be extended
  }

  // Validates that the given coordinates are within the game board
  protected void validateCoordinates(int col, int row) {
    if (col < 0
            || col > this.middleWidth - 1
            || row < 0
            || row > this.middleWidth - 1) {
      throw new IllegalArgumentException("Coordinates are out of bounds for this game board");
    }
  }
}