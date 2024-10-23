package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents the primary model for a reversi board. A game of reversi has
 * two players, black and white, and they play on a hexagon shaped board,
 * with black moving first.
 */
public class HexReversiModel extends AbstractReversiModel {

  // The length of one of the sides of the hexagon, it is an integer because the length of the
  // middle has to be a whole number
  // CLASS INVARIANT: sideLength is always positive and greater than or equal to 3
  private int sideLength;

  @Override
  public void startGame(int sideLength) {
    this.validateGameNotStarted();
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be at least 3 tiles wide");
    }

    // sideLength INVARIANT: if the sideLength is less than 3, an exception is thrown.
    // This variable is never reassigned, so it will retain this property.
    // It also cannot be mutated by any calls to any methods.
    this.sideLength = sideLength;

    // middleWidth INVARIANT: because sideLength is positive, this will always be positive.
    // It will be odd because sideLength multiplied by 2, which is always an even number, and
    // one less than that will be an odd number. Because sideLength is at least 3, the smallest
    // middleWidth can be is 5. middleWidth is never reassigned, so it will retain these
    // properties. It also cannot be mutated by any calls to any methods.
    this.middleWidth = sideLength * 2 - 1;

    // gameBoard INVARIANT: if any other method is called before startGame, an exception is thrown.
    // This is to ensure that gameBoard is never null. The dimensions are always middleWidth by
    // middleWidth because gameBoard is an array, so the size cannot be changed, and it is
    // initialized to be that size, so it is never changed. It also cannot be mutated by any
    // calls to any methods.
    this.gameBoard = new TileType[middleWidth][middleWidth];
    this.initEmptyBoard();
    this.initGameState();
    currentPlayer = TileType.BLACK;

    // passes INVARIANT: if any other method is called before startGame, an exception is thrown.
    // This is to ensure that passes is never null.
    passes = new ArrayList<>();
    gameStarted = true;
    this.notifyListeners();
  }

  @Override
  public void startGame(TileType[][] board) {
    this.validateGameNotStarted();
    this.validateBoard(board);

    // gameBoard INVARIANT: if any other method is called before startGame, an exception is thrown.
    // This is to ensure that gameBoard is never null. The dimensions are always middleWidth by
    // middleWidth because gameBoard is an array, so the size cannot be changed, and it is
    // initialized to be that size, so it is never changed. It also cannot be mutated by any
    // calls to any methods.
    this.gameBoard = board;
    this.middleWidth = board.length;
    this.sideLength = (this.middleWidth + 1) / 2;

    currentPlayer = TileType.BLACK;

    // passes INVARIANT: if any other method is called before startGame, an exception is thrown.
    // This is to ensure that passes is never null.
    passes = new ArrayList<>();
    gameStarted = true;
    this.notifyListeners();
  }

  @Override
  public List<Posn> getCorners() {
    this.validateGameStarted();
    List<Posn> corners = new ArrayList<>();
    corners.add(new Posn(getBoardSideLength() - 1, 0));
    corners.add(new Posn(getBoardWidth() - 1, 0));
    corners.add(new Posn(0, getBoardSideLength() - 1));
    corners.add(new Posn(getBoardWidth() - 1, getBoardSideLength() - 1));
    corners.add(new Posn(0, getBoardWidth() - 1));
    corners.add(new Posn(getBoardSideLength() - 1, getBoardWidth() - 1));
    return corners;
  }

  @Override
  public List<Direction> possibleDirections() {
    this.validateGameStarted();
    return new ArrayList<>(Arrays.asList(
            Direction.Up,
            Direction.Left,
            Direction.DownLeft,
            Direction.UpRight,
            Direction.Right,
            Direction.Down
    ));
  }

  @Override
  public int getBoardSideLength() {
    return this.sideLength;
  }

  // Initializes the 2D array for the game board.
  protected void initEmptyBoard() {
    for (int i = 0; i < middleWidth; i++) {
      for (int j = 0; j < middleWidth; j++) {
        this.gameBoard[i][j] = TileType.EMPTY;
      }
    }
    for (int i = 0; i < sideLength; i++) {
      for (int j = 0; i + j < sideLength - 1; j++) {
        this.gameBoard[i][j] = null;
      }
    }
    for (int i = middleWidth - 1; i > middleWidth - sideLength; i--) {
      for (int j = middleWidth - 1; i + j >= sideLength + middleWidth - 1; j--) {
        this.gameBoard[i][j] = null;
      }
    }
  }

  // Sets the starting pieces for the game.
  protected void initGameState() {
    this.gameBoard[this.sideLength - 2][this.sideLength - 1] = TileType.WHITE;
    this.gameBoard[this.sideLength - 1][this.sideLength - 2] = TileType.BLACK;
    this.gameBoard[this.sideLength - 1][this.sideLength] = TileType.WHITE;
    this.gameBoard[this.sideLength - 2][this.sideLength] = TileType.BLACK;
    this.gameBoard[this.sideLength][this.sideLength - 2] = TileType.WHITE;
    this.gameBoard[this.sideLength][this.sideLength - 1] = TileType.BLACK;
  }

  // Validates that a board can be used for a game of Reversi
  protected void validateBoard(TileType[][] board) {
    int midWidth = board.length;
    if (midWidth < 5 || midWidth % 2 != 1) {
      throw new IllegalArgumentException("Board is not valid");
    }
    for (int i = 0; i < midWidth - 1; i++) {
      if (board[i].length != board[i + 1].length) {
        throw new IllegalArgumentException("Board is not valid");
      }
    }
    int sideLen = (midWidth + 1) / 2;
    for (int i = 0; i < sideLen; i++) {
      for (int j = 0; i + j < sideLen - 1; j++) {
        if (board[i][j] != null) {
          throw new IllegalArgumentException("Board is not valid");
        }
      }
    }
    for (int i = midWidth - 1; i > midWidth - sideLen; i--) {
      for (int j = midWidth - 1; i + j >= sideLen + midWidth - 1; j--) {
        if (board[i][j] != null) {
          throw new IllegalArgumentException("Board is not valid");
        }
      }
    }
    for (int i = 0; i < midWidth; i++) {
      for (int j = sideLen - (1 + i); j < midWidth - (i + 3); j++) {
        if (j >= midWidth || j < 0) {
          continue;
        }
        if (board[i][j] == null) {
          throw new IllegalArgumentException("Board is not valid");
        }
      }
    }
  }

  // Validates that the given coordinates are within the game board
  protected void validateCoordinates(int col, int row) {
    super.validateCoordinates(col,row);
    if (Objects.isNull(this.gameBoard[col][row])) {
      throw new IllegalArgumentException("Coordinates are out of bounds for this game board");
    }
  }
}