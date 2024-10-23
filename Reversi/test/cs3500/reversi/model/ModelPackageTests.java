package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Tests for the package private methods on the model.
 */
public class ModelPackageTests {

  @Test
  public void testValidateGameNotStarted() {
    HexReversiModel reversiBoard = new HexReversiModel();
    reversiBoard.validateGameNotStarted();
    reversiBoard.startGame(4);
    Assert.assertThrows(IllegalStateException.class, reversiBoard::validateGameNotStarted);
  }

  @Test
  public void testValidateGameStarted() {
    HexReversiModel reversiBoard = new HexReversiModel();
    Assert.assertThrows(IllegalStateException.class, reversiBoard::validateGameStarted);
    reversiBoard.startGame(4);
    reversiBoard.validateGameStarted();
  }

  @Test
  public void testValidateBoard() {
    HexReversiModel reversiBoard = new HexReversiModel();
    reversiBoard.startGame(4);
    reversiBoard.validateBoard(reversiBoard.getBoard());
    TileType[][] tooSmallBoard = new TileType[1][1];
    tooSmallBoard[0][0] = TileType.BLACK;
    Assert.assertThrows(IllegalArgumentException.class,
        () -> reversiBoard.validateBoard(tooSmallBoard));
    TileType[][] unevenBoard = reversiBoard.getBoard();
    TileType[] uneven = new TileType[6];
    Arrays.fill(uneven, TileType.EMPTY);
    unevenBoard[2] = uneven;
    Assert.assertThrows(IllegalArgumentException.class,
        () -> reversiBoard.validateBoard(unevenBoard));
    TileType[][] nullBoard = new TileType[7][7];
    Assert.assertThrows(IllegalArgumentException.class,
        () -> reversiBoard.validateBoard(nullBoard));
    TileType[][] fullBoard = new TileType[7][7];
    for (int i = 0; i < 7; i++) {
      Arrays.fill(fullBoard[i], TileType.EMPTY);
    }
    Assert.assertThrows(IllegalArgumentException.class,
        () -> reversiBoard.validateBoard(fullBoard));
  }

  @Test
  public void testValidateCoordinates() {
    HexReversiModel reversiBoard = new HexReversiModel();
    reversiBoard.startGame(4);
    reversiBoard.validateCoordinates(3, 3);
    reversiBoard.validateCoordinates(1, 5);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> reversiBoard.validateCoordinates(0, 0));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> reversiBoard.validateCoordinates(6, 4));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> reversiBoard.validateCoordinates(-1, 3));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> reversiBoard.validateCoordinates(3, 10));
  }

  @Test
  public void testChangeCurrentPlayer() {
    HexReversiModel reversiBoard = new HexReversiModel();
    reversiBoard.startGame(4);
    Assert.assertEquals(TileType.BLACK, reversiBoard.getCurrentPlayer());
    reversiBoard.changeCurrentPlayer();
    Assert.assertEquals(TileType.WHITE, reversiBoard.getCurrentPlayer());
  }

  @Test
  public void testSetPlayer() {
    HexReversiModel reversiBoard = new HexReversiModel();
    reversiBoard.startGame(4);
    Assert.assertEquals(TileType.EMPTY, reversiBoard.playerAt(3, 3));
    reversiBoard.setPlayer(3, 3);
    Assert.assertEquals(TileType.BLACK, reversiBoard.playerAt(3, 3));
    Assert.assertEquals(TileType.BLACK, reversiBoard.playerAt(3, 2));
    Assert.assertThrows(IllegalStateException.class,
        () -> reversiBoard.setPlayer(3, 2));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> reversiBoard.setPlayer(10, -1));
  }

  @Test
  public void testIsSurrounded() {
    HexReversiModel reversiBoard = new HexReversiModel();
    reversiBoard.startGame(4);
    Assert.assertTrue(reversiBoard.isSurrounded(2, 3, Direction.UpRight));
    Assert.assertTrue(reversiBoard.isSurrounded(2, 3, Direction.Down));
    Assert.assertFalse(reversiBoard.isSurrounded(2, 3, Direction.Right));
  }

  @Test
  public void testFlipLine() {
    HexReversiModel reversiBoard = new HexReversiModel();
    reversiBoard.startGame(4);
    Assert.assertEquals(TileType.WHITE, reversiBoard.playerAt(2, 3));
    reversiBoard.flipLine(2, 3, Direction.UpRight);
    Assert.assertEquals(TileType.BLACK, reversiBoard.playerAt(2, 3));
    reversiBoard.setPlayer(1, 4);
    reversiBoard.changeCurrentPlayer();
    reversiBoard.flipLine(1, 4, Direction.Right);
    Assert.assertEquals(TileType.WHITE, reversiBoard.playerAt(1, 4));
    Assert.assertEquals(TileType.WHITE, reversiBoard.playerAt(2, 4));
  }
}