import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.reversi.model.Direction;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.TileType;
import cs3500.reversi.model.HexReversiModel;
import cs3500.reversi.model.ReversiModel;

/**
 * Tests for public methods of a Reversi game.
 */
public class HexModelTests {

  ReversiModel rm;

  @Before
  public void setUpReversiModel() {
    rm = new HexReversiModel();
    rm.startGame(4);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidStartGameSmallArgs() {
    ReversiModel rm = new HexReversiModel();
    rm.startGame(2);
  }

  @Test
  public void testGetSideLength() {
    setUpReversiModel();
    Assert.assertEquals(4, rm.getBoardSideLength());
  }

  @Test
  public void testGetWidth() {
    setUpReversiModel();
    Assert.assertEquals(7, rm.getBoardWidth());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidStartGameNegativeArgs() {
    ReversiModel rm = new HexReversiModel();
    rm.startGame(-4);
  }

  @Test (expected = IllegalStateException.class)
  public void testStartGameAlrStarted() {
    setUpReversiModel();
    rm.startGame(5);
  }

  @Test
  public void testGameNotStartedExceptions() {
    rm = new HexReversiModel();
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
    Assert.assertEquals(7, gameBoard.length);
    Assert.assertEquals(7, gameBoard[0].length);
    Assert.assertEquals(7, gameBoard[6].length);
    Assert.assertNull(gameBoard[0][0]);
    Assert.assertNull(gameBoard[2][0]);
    Assert.assertNull(gameBoard[6][6]);
    Assert.assertEquals(TileType.BLACK, gameBoard[3][2]);
    Assert.assertEquals(TileType.WHITE, gameBoard[4][2]);
    Assert.assertEquals(TileType.BLACK, gameBoard[2][4]);
    Assert.assertEquals(TileType.WHITE, gameBoard[2][3]);
    Assert.assertEquals(TileType.BLACK, gameBoard[4][3]);
    Assert.assertEquals(TileType.WHITE, gameBoard[3][4]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[3][3]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[3][0]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[6][0]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[3][6]);
    Assert.assertEquals(TileType.EMPTY, gameBoard[0][6]);
  }

  @Test
  public void testStartGameExistingBoard() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    rm = new HexReversiModel();
    Assert.assertThrows(IllegalStateException.class, () -> rm.playerAt(3, 3));
    board[3][2] = TileType.WHITE;
    board[2][3] = TileType.BLACK;
    rm.startGame(board);
    Assert.assertEquals(TileType.EMPTY, rm.playerAt(3, 3));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(2, 3));
    Assert.assertEquals(TileType.WHITE, rm.playerAt(3, 2));
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
    Assert.assertThrows(IllegalArgumentException.class, () -> rm.play(0, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> rm.play(-1, -2));
    Assert.assertThrows(IllegalStateException.class, () -> rm.play(3, 3));
  }

  @Test
  public void testPlayerMove() {
    setUpReversiModel();
    rm.play(4, 1);
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 1));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 2));
    rm.play(5, 0);
    Assert.assertEquals(TileType.WHITE, rm.playerAt(5, 0));
    Assert.assertEquals(TileType.WHITE, rm.playerAt(4, 1));
    Assert.assertEquals(TileType.WHITE, rm.playerAt(3, 2));
  }

  @Test
  public void testPlayMultipleDirections() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    rm = new HexReversiModel();
    board[3][2] = TileType.WHITE;
    board[2][3] = TileType.BLACK;
    rm.startGame(board);
    rm.play(4, 1);
    Assert.assertEquals(TileType.BLACK, rm.playerAt(3, 2));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 2));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 1));
  }

  @Test
  public void testPlayAndPass() {
    setUpReversiModel();
    rm.play(4, 1);
    rm.pass();
    rm.play(2, 5);
    Assert.assertEquals(TileType.BLACK, rm.playerAt(2, 5));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(3, 4));
  }

  @Test
  public void testIsMoveValid() {
    setUpReversiModel();
    Assert.assertTrue(rm.isMoveValid(4, 1));
    Assert.assertFalse(rm.isMoveValid(5, 0));
    rm.play(4, 1);
    Assert.assertTrue(rm.isMoveValid(5, 0));
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
    Assert.assertEquals(3, rm.getScoreFor(TileType.WHITE));
    Assert.assertEquals(3, rm.getScoreFor(TileType.BLACK));
    rm.play(4, 1);
    Assert.assertEquals(5, rm.getScoreFor(TileType.BLACK));
    Assert.assertEquals(2, rm.getScoreFor(TileType.WHITE));
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
    rm.play(4, 1);
    Assert.assertEquals(TileType.BLACK, rm.getWinner());
    rm.play(5, 0);
    Assert.assertEquals(TileType.WHITE, rm.getWinner());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testPlayerAtInvalidCoordinates() {
    setUpReversiModel();
    rm.playerAt(0, 0);
  }

  @Test
  public void testPlayerAt() {
    setUpReversiModel();
    Assert.assertEquals(TileType.WHITE, rm.playerAt(2, 3));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(3, 2));
    rm.play(2, 2);
    Assert.assertEquals(TileType.BLACK, rm.playerAt(2, 2));
    rm.play(5, 2);
    Assert.assertEquals(TileType.WHITE, rm.playerAt(5, 2));
  }

  @Test
  public void testFlipCount() {
    setUpReversiModel();
    Assert.assertEquals(2, rm.flipCount(4,1));
    rm.play(4,1);
    Assert.assertEquals(3, rm.flipCount(5,0));
  }

  @Test
  public void testGetCorners() {
    setUpReversiModel();
    List<Posn> corners = rm.getCorners();
    Assert.assertEquals(6, corners.size());
    Assert.assertEquals(new Posn(3,0), corners.get(0));
    Assert.assertEquals(new Posn(6,0), corners.get(1));
    Assert.assertEquals(new Posn(0,3), corners.get(2));
    Assert.assertEquals(new Posn(6,3), corners.get(3));
    Assert.assertEquals(new Posn(0,6), corners.get(4));
    Assert.assertEquals(new Posn(3,6), corners.get(5));
  }

  @Test
  public void testPossibleDirections() {
    setUpReversiModel();
    List<Direction> directions = rm.possibleDirections();
    Assert.assertEquals(6, directions.size());
    for (Direction direction : Direction.values()) {
      if (direction != Direction.UpLeft && direction != Direction.DownRight) {
        Assert.assertTrue(directions.contains(direction));
      }
    }
  }
}
