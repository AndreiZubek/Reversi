package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the primary model for a reversi board. A game of reversi has
 * two players, black and white, and they play on a hexagon shaped board,
 * with black moving first.
 */
public class SquareReversiModel extends AbstractReversiModel {

  @Override
  public void startGame(int sideLength) {
    this.validateGameNotStarted();
    if (sideLength <= 2 || sideLength % 2 != 0) {
      throw new IllegalArgumentException("Side length must be positive and even");
    }

    // sideLength INVARIANT: if the sideLength is less than 3, an exception is thrown.
    // This variable is never reassigned, so it will retain this property.
    // It also cannot be mutated by any calls to any methods.
    this.middleWidth = sideLength;

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
    corners.add(new Posn(0,0));
    corners.add(new Posn(getBoardWidth() - 1, 0));
    corners.add(new Posn(getBoardWidth() - 1, getBoardWidth() - 1));
    corners.add(new Posn(0, getBoardWidth() - 1));
    return corners;
  }

  @Override
  public List<Direction> possibleDirections() {
    this.validateGameStarted();
    return List.of(Direction.values());
  }

  // Initializes the 2D array for the game board.
  protected void initEmptyBoard() {
    for (int i = 0; i < middleWidth; i++) {
      for (int j = 0; j < middleWidth; j++) {
        this.gameBoard[i][j] = TileType.EMPTY;
      }
    }
  }

  // Sets the starting pieces for the game.
  protected void initGameState() {
    int halfLength = this.middleWidth / 2;
    this.gameBoard[halfLength - 1][halfLength - 1] = TileType.BLACK;
    this.gameBoard[halfLength][halfLength - 1] = TileType.WHITE;
    this.gameBoard[halfLength][halfLength] = TileType.BLACK;
    this.gameBoard[halfLength - 1][halfLength] = TileType.WHITE;
  }

  // Validates that a board can be used for a game of Reversi
  protected void validateBoard(TileType[][] board) {
    int midWidth = board.length;
    if (midWidth < 4 || midWidth % 2 != 0) {
      throw new IllegalArgumentException("Board is not valid");
    }
    for (int i = 0; i < midWidth - 1; i++) {
      if (board[i].length != board[i + 1].length) {
        throw new IllegalArgumentException("Board is not valid");
      }
    }
    for (TileType[] column : board) {
      for (int j = 0; j < midWidth; j++) {
        if (column[j] == null) {
          throw new IllegalArgumentException("Board is not valid");
        }
      }
    }
  }
}