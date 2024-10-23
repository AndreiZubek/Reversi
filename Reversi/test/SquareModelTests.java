import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.reversi.model.Direction;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.SquareReversiModel;
import cs3500.reversi.model.TileType;
import cs3500.reversi.model.ReversiModel;

/**
 * Tests for public methods of a Reversi game.
 */
public class SquareModelTests {

  ReversiModel rm;

  @Before
  public void setUpReversiModel() {
    rm = new SquareReversiModel();
    rm.startGame(8);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidStartGameSmallArgs() {
    ReversiModel rm = new SquareReversiModel();
    rm.startGame(0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidStartGameOddArgs() {
    ReversiModel rm = new SquareReversiModel();
    rm.startGame(5);
  }

  @Test
  public void testGetSideLength() {
    setUpReversiModel();
    Assert.assertEquals(8, rm.getBoardSideLength());
  }

  @Test
  public void testGetWidth() {
    setUpReversiModel();
    Assert.assertEquals(8, rm.getBoardWidth());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidStartGameNegativeArgs() {
    ReversiModel rm = new SquareReversiModel();
    rm.startGame(-4);
  }

  @Test (expected = IllegalStateException.class)
  public void testStartGameAlrStarted() {
    setUpReversiModel();
    rm.startGame(5);
  }

  @Test
  public void testGameNotStartedExceptions() {
    rm = new SquareReversiModel();
    Assert.assertThrows(IllegalStateException.class, () -> rm.pass());
    Assert.assertThrows(IllegalStateException.class, () -> rm.play(3, 3));
    Assert.assertThrows(IllegalStateException.class, () -> rm.getBoard());
    Assert.assertThrows(IllegalStateException.class, () -> rm.getCurrentPlayer());
    Assert.assertThrows(IllegalStateException.class, () -> rm.getScoreFor(TileType.BLACK));
    Assert.assertThrows(IllegalStateException.class, () -> rm.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> rm.getWinner());
    Assert.assertThrows(IllegalStateException.class, () -> rm.playerAt(3, 3));
    Assert.assertThrows(IllegalStateException.class, () -> rm.isMoveValid(3, 3));
    Assert.assertThrows(IllegalStateException.class, () -> rm.hasValidMove());
    Assert.assertThrows(IllegalStateException.class, () -> rm.flipCount(3,3));
    Assert.assertThrows(IllegalStateException.class, () -> rm.getCorners());
    Assert.assertThrows(IllegalStateException.class, () -> rm.possibleDirections());
  }

  @Test
  public void testCheckInitialBoard() {
    setUpReversiModel();
    TileType[][] gameBoard = rm.getBoard();
    Assert.assertEquals(8, gameBoard.length);
    Assert.assertEquals(8, gameBoard[0].length);
    Assert.assertEquals(8, gameBoard[6].length);
    Assert.assertEquals(TileType.BLACK, gameBoard[3][3]);
    Assert.assertEquals(TileType.WHITE, gameBoard[4][3]);
    Assert.assertEquals(TileType.BLACK, gameBoard[4][4]);
    Assert.assertEquals(TileType.WHITE, gameBoard[3][4]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[3][2]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[3][0]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[6][0]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[3][6]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[0][6]);
  }

  @Test
  public void testStartGameExistingBoard() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    rm = new SquareReversiModel();
    Assert.assertThrows(IllegalStateException.class, () -> rm.playerAt(3, 3));
    board[2][4] = TileType.BLACK;
    board[3][3] = TileType.WHITE;
    rm.startGame(board);
    Assert.assertEquals(TileType.EMPTY, rm.playerAt(3, 2));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(2, 4));
    Assert.assertEquals(TileType.WHITE, rm.playerAt(3, 3));
  }

  @Test
  public void testPassChangesPlayer() {
    setUpReversiModel();
    Assert.assertEquals(TileType.BLACK, rm.getCurrentPlayer());
    rm.pass();
    Assert.assertEquals(TileType.WHITE, rm.getCurrentPlayer());
    rm.pass();
    Assert.assertEquals(TileType.BLACK, rm.getCurrentPlayer());
  }

  @Test
  public void testPlayThrowsInvalidSpace() {
    setUpReversiModel();
    Assert.assertThrows(IllegalArgumentException.class, () -> rm.play(0, -1));
    Assert.assertThrows(IllegalArgumentException.class, () -> rm.play(-1, 2));
    Assert.assertThrows(IllegalStateException.class, () -> rm.play(3, 3));
  }

  @Test
  public void testPlayerMove() {
    setUpReversiModel();
    rm.play(4, 2);
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 2));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 3));
    rm.play(5, 2);
    Assert.assertEquals(TileType.WHITE, rm.playerAt(5, 2));
    Assert.assertEquals(TileType.WHITE, rm.playerAt(4, 3));
  }

  @Test
  public void testPlayMultipleDirections() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    rm = new SquareReversiModel();
    board[2][4] = TileType.BLACK;
    board[3][3] = TileType.WHITE;
    rm.startGame(board);
    rm.play(4, 2);
    Assert.assertEquals(TileType.BLACK, rm.playerAt(3, 3));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 3));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 2));
  }

  @Test
  public void testPlayAndPass() {
    setUpReversiModel();
    rm.play(4, 2);
    rm.pass();
    rm.play(2, 4);
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 3));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(3, 4));
  }

  @Test
  public void testIsMoveValid() {
    setUpReversiModel();
    Assert.assertTrue(rm.isMoveValid(4, 2));
    Assert.assertFalse(rm.isMoveValid(5, 2));
    rm.play(4, 2);
    Assert.assertTrue(rm.isMoveValid(5, 2));
  }

  @Test
  public void testHasValidMove() {
    setUpReversiModel();
    Assert.assertTrue(rm.hasValidMove());
    Assert.assertTrue(rm.hasValidMove());
  }

  @Test
  public void testGetScoreForThrows() {
    setUpReversiModel();
    Assert.assertThrows(IllegalArgumentException.class, () -> rm.getScoreFor(null));
    Assert.assertThrows(IllegalArgumentException.class, () -> rm.getScoreFor(TileType.EMPTY));
  }

  @Test
  public void testGetScoreFor() {
    setUpReversiModel();
    Assert.assertEquals(2, rm.getScoreFor(TileType.WHITE));
    Assert.assertEquals(2, rm.getScoreFor(TileType.BLACK));
    rm.play(4, 2);
    Assert.assertEquals(4, rm.getScoreFor(TileType.BLACK));
    Assert.assertEquals(1, rm.getScoreFor(TileType.WHITE));
  }

  @Test
  public void testGameOver() {
    setUpReversiModel();
    Assert.assertFalse(rm.isGameOver());
    rm.pass();
    Assert.assertFalse(rm.isGameOver());
    rm.pass();
    Assert.assertTrue(rm.isGameOver());
  }

  @Test
  public void testGetWinner() {
    setUpReversiModel();
    Assert.assertEquals(TileType.EMPTY, rm.getWinner());
    rm.play(4, 2);
    Assert.assertEquals(TileType.BLACK, rm.getWinner());
    rm.play(5, 2);
    Assert.assertEquals(TileType.EMPTY, rm.getWinner());
    rm.pass();
    rm.play(5, 4);
    Assert.assertEquals(TileType.WHITE, rm.getWinner());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayerAtInvalidCoordinates() {
    setUpReversiModel();
    rm.playerAt(-1, 0);
  }

  @Test
  public void testPlayerAt() {
    setUpReversiModel();
    Assert.assertEquals(TileType.WHITE, rm.playerAt(4, 3));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(3, 3));
    rm.play(4, 2);
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 3));
    rm.play(5, 2);
    Assert.assertEquals(TileType.WHITE, rm.playerAt(4, 3));
  }

  @Test
  public void testFlipCount() {
    setUpReversiModel();
    Assert.assertEquals(2, rm.flipCount(4,2));
    rm.play(4,2);
    Assert.assertEquals(2, rm.flipCount(5,2));
  }

  @Test
  public void testGetCorners() {
    setUpReversiModel();
    List<Posn> corners = rm.getCorners();
    Assert.assertEquals(4, corners.size());
    Assert.assertEquals(new Posn(0,0), corners.get(0));
    Assert.assertEquals(new Posn(7,0), corners.get(1));
    Assert.assertEquals(new Posn(7,7), corners.get(2));
    Assert.assertEquals(new Posn(0,7), corners.get(3));
  }
  
  @Test
  public void testPossibleDirections() {
    setUpReversiModel();
    List<Direction> directions = rm.possibleDirections();
    Assert.assertEquals(8, directions.size());
    for (Direction direction : Direction.values()) {
      Assert.assertTrue(directions.contains(direction));
    }
  }
}
