import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import cs3500.reversi.model.TileType;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.HexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategy.AvoidOpponentCornerStrategy;
import cs3500.reversi.strategy.ChooseCornerStrategy;
import cs3500.reversi.strategy.MaxFlipStrategy;
import cs3500.reversi.strategy.MinimaxStrategy;

/**
 * Tests for the chooseMove method of the various strategies.
 */
public class HexStrategyTests {

  ReversiModel rm;

  @Before
  public void setUpReversiModel() {
    rm = new HexReversiModel();
    rm.startGame(4);
  }

  @Test
  public void testStrategyThrowsInvalidBoard() {
    ReversiModel newBoard = new HexReversiModel();
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new MaxFlipStrategy().chooseMove(null, TileType.BLACK));
    Assert.assertThrows(IllegalStateException.class,
        () -> new MaxFlipStrategy().chooseMove(newBoard, TileType.BLACK));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new AvoidOpponentCornerStrategy().chooseMove(null, TileType.BLACK));
    Assert.assertThrows(IllegalStateException.class,
        () -> new AvoidOpponentCornerStrategy().chooseMove(newBoard, TileType.BLACK));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new ChooseCornerStrategy().chooseMove(null, TileType.BLACK));
    Assert.assertThrows(IllegalStateException.class,
        () -> new ChooseCornerStrategy().chooseMove(newBoard, TileType.BLACK));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new MinimaxStrategy().chooseMove(null, TileType.BLACK));
    Assert.assertThrows(IllegalStateException.class,
        () -> new MinimaxStrategy().chooseMove(newBoard, TileType.BLACK));
  }

  @Test
  public void testStrategyThrowsInvalidPlayer() {
    setUpReversiModel();
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new MaxFlipStrategy().chooseMove(rm, null));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new MaxFlipStrategy().chooseMove(rm, TileType.EMPTY));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new AvoidOpponentCornerStrategy().chooseMove(rm, null));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.EMPTY));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new ChooseCornerStrategy().chooseMove(rm, null));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new ChooseCornerStrategy().chooseMove(rm, TileType.EMPTY));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new MinimaxStrategy().chooseMove(rm, null));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new MinimaxStrategy().chooseMove(rm, TileType.EMPTY));
  }

  @Test
  public void testStrategyThrowsOutOfTurn() {
    setUpReversiModel();
    Assert.assertThrows(IllegalStateException.class,
        () -> new MaxFlipStrategy().chooseMove(rm, TileType.WHITE));
    new MaxFlipStrategy().chooseMove(rm, TileType.BLACK);
    rm.pass();
    Assert.assertThrows(IllegalStateException.class,
        () -> new MaxFlipStrategy().chooseMove(rm, TileType.BLACK));
    new MaxFlipStrategy().chooseMove(rm, TileType.WHITE);
    rm.pass();
    Assert.assertThrows(IllegalStateException.class,
        () -> new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.WHITE));
    new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.BLACK);
    rm.pass();
    Assert.assertThrows(IllegalStateException.class,
        () -> new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.BLACK));
    new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.WHITE);
    rm.pass();
    Assert.assertThrows(IllegalStateException.class,
        () -> new ChooseCornerStrategy().chooseMove(rm, TileType.WHITE));
    new ChooseCornerStrategy().chooseMove(rm, TileType.BLACK);
    rm.pass();
    Assert.assertThrows(IllegalStateException.class,
        () -> new ChooseCornerStrategy().chooseMove(rm, TileType.BLACK));
    new ChooseCornerStrategy().chooseMove(rm, TileType.WHITE);
  }

  @Test
  public void testMaxFlipStrategy() {
    setUpReversiModel();
    Optional<Posn> move1 = new MaxFlipStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move1.isPresent());
    Assert.assertEquals(new Posn(4,1), move1.get());
    rm.play(4,1);
    Optional<Posn> move2 = new MaxFlipStrategy().chooseMove(rm, TileType.WHITE);
    Assert.assertTrue(move2.isPresent());
    Assert.assertEquals(new Posn(5,0), move2.get());
    rm.play(5,0);
    Optional<Posn> move3 = new MaxFlipStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move3.isPresent());
    Assert.assertEquals(new Posn(2,2), move3.get());
  }

  @Test
  public void testMaxFlipStrategy2() {
    setUpReversiModel();
    rm.play(4,1);
    rm.play(1,4);
    Optional<Posn> move = new MaxFlipStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move.isPresent());
    Assert.assertEquals(new Posn(0,5), move.get());
  }

  @Test
  public void testMaxFlipStrategyNoMove() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    board[3][2] = TileType.EMPTY;
    board[4][3] = TileType.EMPTY;
    board[2][4] = TileType.EMPTY;
    rm = new HexReversiModel();
    rm.startGame(board);
    Optional<Posn> move = new MaxFlipStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move.isEmpty());
  }

  @Test
  public void testAvoidOpponentCornerStrategy() {
    setUpReversiModel();
    Optional<Posn> move1 = new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move1.isPresent());
    Assert.assertEquals(new Posn(4,1), move1.get());
    rm.play(4,1);
    Optional<Posn> move2 = new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.WHITE);
    Assert.assertTrue(move2.isPresent());
    Assert.assertEquals(new Posn(5,2), move2.get());
    rm.play(5,2);
    Optional<Posn> move3 = new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move3.isPresent());
    Assert.assertEquals(new Posn(4,4), move3.get());
  }

  @Test
  public void testAvoidOpponentCornerStrategy2() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    board[3][2] = TileType.EMPTY;
    board[2][3] = TileType.EMPTY;
    board[0][4] = TileType.BLACK;
    board[1][3] = TileType.WHITE;
    board[2][2] = TileType.WHITE;
    board[3][1] = TileType.WHITE;
    rm = new HexReversiModel();
    rm.startGame(board);
    Optional<Posn> move = new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move.isPresent());
    Assert.assertEquals(new Posn(4,1), move.get());
  }

  @Test
  public void testAvoidOpponentCornerStrategyFallback() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    board[3][2] = TileType.EMPTY;
    board[2][3] = TileType.EMPTY;
    board[4][2] = TileType.EMPTY;
    board[4][3] = TileType.EMPTY;
    board[2][4] = TileType.EMPTY;
    board[3][4] = TileType.EMPTY;
    board[1][3] = TileType.BLACK;
    board[2][2] = TileType.WHITE;
    rm = new HexReversiModel();
    rm.startGame(board);
    Optional<Posn> move = new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move.isPresent());
    Assert.assertEquals(new Posn(3,1), move.get());
  }

  @Test
  public void testAvoidOpponentCornerStrategyNoMove() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    board[3][2] = TileType.EMPTY;
    board[4][3] = TileType.EMPTY;
    board[2][4] = TileType.EMPTY;
    rm = new HexReversiModel();
    rm.startGame(board);
    Optional<Posn> move = new AvoidOpponentCornerStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move.isEmpty());
  }

  @Test
  public void ChooseCornerStrategy() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    board[4][2] = TileType.EMPTY;
    board[4][3] = TileType.EMPTY;
    board[2][4] = TileType.EMPTY;
    board[3][4] = TileType.EMPTY;
    board[1][2] = TileType.BLACK;
    board[2][1] = TileType.WHITE;
    board[0][5] = TileType.BLACK;
    board[1][4] = TileType.WHITE;
    board[2][3] = TileType.WHITE;
    board[3][2] = TileType.WHITE;
    board[4][1] = TileType.WHITE;
    rm = new HexReversiModel();
    rm.startGame(board);
    Optional<Posn> move = new ChooseCornerStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move.isPresent());
    Assert.assertEquals(new Posn(3,0), move.get());
  }

  @Test
  public void testChooseCornerStrategyFallback() {
    setUpReversiModel();
    Optional<Posn> move1 = new ChooseCornerStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move1.isPresent());
    Assert.assertEquals(new Posn(4,1), move1.get());
    rm.play(4,1);
    Optional<Posn> move2 = new ChooseCornerStrategy().chooseMove(rm, TileType.WHITE);
    Assert.assertTrue(move2.isPresent());
    Assert.assertEquals(new Posn(5,2), move2.get());
    rm.play(5,2);
    Optional<Posn> move3 = new ChooseCornerStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move3.isPresent());
    Assert.assertEquals(new Posn(4,4), move3.get());
  }

  @Test
  public void ChooseCornerStrategyNoMove() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    board[3][2] = TileType.EMPTY;
    board[4][3] = TileType.EMPTY;
    board[2][4] = TileType.EMPTY;
    rm = new HexReversiModel();
    rm.startGame(board);
    Optional<Posn> move = new ChooseCornerStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move.isEmpty());
  }

  @Test
  public void testMinimaxStrategy() {
    setUpReversiModel();
    Optional<Posn> move1 = new MinimaxStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move1.isPresent());
    Assert.assertEquals(new Posn(4,1), move1.get());
    rm.play(4,1);
    Optional<Posn> move2 = new MinimaxStrategy().chooseMove(rm, TileType.WHITE);
    Assert.assertTrue(move2.isPresent());
    Assert.assertEquals(new Posn(5,0), move2.get());
    rm.play(5,0);
    Optional<Posn> move3 = new MinimaxStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move3.isPresent());
    Assert.assertEquals(new Posn(4,0), move3.get());
  }

  @Test
  public void testMinimaxStrategy2() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    board[3][2] = TileType.EMPTY;
    board[4][2] = TileType.EMPTY;
    board[3][4] = TileType.EMPTY;
    board[0][5] = TileType.WHITE;
    board[1][4] = TileType.BLACK;
    board[2][3] = TileType.WHITE;
    board[1][6] = TileType.WHITE;
    board[2][5] = TileType.BLACK;
    board[3][4] = TileType.BLACK;
    board[4][3] = TileType.WHITE;
    board[5][2] = TileType.WHITE;
    rm = new HexReversiModel();
    rm.startGame(board);
    Optional<Posn> move = new MinimaxStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move.isPresent());
    Assert.assertEquals(new Posn(6,1), move.get());
  }

  @Test
  public void testMinimaxStrategyNoMove() {
    setUpReversiModel();
    TileType[][] board = rm.getBoard();
    board[3][2] = TileType.EMPTY;
    board[4][3] = TileType.EMPTY;
    board[2][4] = TileType.EMPTY;
    rm = new HexReversiModel();
    rm.startGame(board);
    Optional<Posn> move = new MinimaxStrategy().chooseMove(rm, TileType.BLACK);
    Assert.assertTrue(move.isEmpty());
  }
}