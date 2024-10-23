import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.controller.ReversiGUIController;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.HexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TileType;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.strategy.MinimaxStrategy;
import cs3500.reversi.view.MockReversiView;

/**
 * Tests the controller for a game of reversi in various ways.
 */
public class ControllerTests {
  MockReversiView mp;
  ReversiModel rm;
  Appendable ap;

  MockReversiView mp2;
  Appendable ap2;
  ReversiGUIController c;
  ReversiGUIController c2;

  @Before
  public void setUp() {
    rm = new HexReversiModel();
    ap = new StringBuilder();
    rm.startGame(4);
    mp = new MockReversiView(ap, rm);
    c = new ReversiGUIController(mp, rm, new HumanPlayer(TileType.BLACK));

    ap2 = new StringBuilder();
    mp2 = new MockReversiView(ap2, rm);
    c2 = new ReversiGUIController(mp2, rm, new HumanPlayer(TileType.WHITE));
  }

  @Test
  public void controllerImplementedMethodsTests() {
    setUp();
    c.takeTurn();
    Assert.assertTrue(ap.toString().contains("repainted"));
    c.setPosition(new Posn(4, 1));
    mp.play();
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 1));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 2));
  }

  @Test
  public void testAIMoves() {
    ReversiModel rm1 = new HexReversiModel();
    Appendable ap4 = new StringBuilder();
    rm1.startGame(4);
    MockReversiView mp4 = new MockReversiView(ap, rm1);
    ReversiGUIController c = new ReversiGUIController(mp4, rm1, new HumanPlayer(TileType.BLACK));

    Appendable ap3 = new StringBuilder();
    MockReversiView mp3 = new MockReversiView(ap3, rm1);
    ReversiGUIController c2 = new ReversiGUIController(mp3, rm1,
            new AIPlayer(new MinimaxStrategy(), TileType.WHITE));
    Assert.assertEquals(TileType.BLACK, rm1.getCurrentPlayer());
    mp4.pass();
    Assert.assertEquals(TileType.BLACK, rm1.getCurrentPlayer());
    Assert.assertEquals(2, rm1.getScoreFor(TileType.BLACK));
    Assert.assertEquals(5, rm1.getScoreFor(TileType.WHITE));
  }

  @Test
  public void testStartPlayerIsBlack() {
    setUp();
    Assert.assertEquals(TileType.BLACK, rm.getCurrentPlayer());
  }

  @Test
  public void testPassWorks() {
    setUp();
    Assert.assertEquals(TileType.BLACK, rm.getCurrentPlayer());
    c.takeTurn();
    mp.pass();
    Assert.assertEquals(TileType.WHITE, rm.getCurrentPlayer());
  }

  @Test
  public void testPlayWorks() {
    setUp();
    Assert.assertEquals(TileType.BLACK, rm.getCurrentPlayer());
    mp.setPlayPosn(new Posn(4, 1));
    c.takeTurn();
    mp.play();
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 1));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 2));
  }

  @Test
  public void testQuitWorks() {
    setUp();
    mp.quit();
    Assert.assertTrue(ap.toString().contains("quit"));
  }

  @Test
  public void gameBetweenHumansPassWorks() {
    setUp();
    Assert.assertEquals(TileType.BLACK, rm.getCurrentPlayer());
    c.takeTurn();
    mp.pass();
    Assert.assertEquals(TileType.WHITE, rm.getCurrentPlayer());
    c2.takeTurn();
    mp2.pass();
    Assert.assertEquals(TileType.BLACK, rm.getCurrentPlayer());
  }

  @Test
  public void gameBetweenHumansGameOverPassTwice() {
    setUp();
    c.takeTurn();
    mp.pass();
    c2.takeTurn();
    mp2.pass();
    Assert.assertTrue(rm.isGameOver());
  }

  @Test
  public void gameBetweenHumansPlayAndPass() {
    setUp();
    mp.setPlayPosn(new Posn(4, 1));
    c.takeTurn();
    mp.play();
    c2.takeTurn();
    mp2.pass();
    mp.setPlayPosn(new Posn(2, 5));
    c.takeTurn();
    mp.play();
    Assert.assertEquals(TileType.BLACK, rm.playerAt(2, 5));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(3, 4));
  }

  @Test
  public void gameBetweenHumansTestScore() {
    setUp();
    Assert.assertEquals(rm.getScoreFor(TileType.BLACK), 3);
    Assert.assertEquals(rm.getScoreFor(TileType.WHITE), 3);
    mp.setPlayPosn(new Posn(4, 1));
    mp.play();
    mp2.pass();
    mp.setPlayPosn(new Posn(2, 5));
    mp.play();
    Assert.assertEquals(7, rm.getScoreFor(TileType.BLACK));
    Assert.assertEquals(1, rm.getScoreFor(TileType.WHITE));
  }

  @Test
  public void gameBetweenHumansMultiplePlays() {
    setUp();
    mp.setPlayPosn(new Posn(4, 1));
    c.takeTurn();
    mp.play();
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 1));
    Assert.assertEquals(TileType.BLACK, rm.playerAt(4, 2));
    mp2.setPlayPosn(new Posn(5, 0));
    c2.takeTurn();
    mp2.play();
    Assert.assertEquals(TileType.WHITE, rm.playerAt(5, 0));
    Assert.assertEquals(TileType.WHITE, rm.playerAt(4, 1));
    Assert.assertEquals(TileType.WHITE, rm.playerAt(3, 2));
  }

  @Test
  public void gameBetweenHumansBadMoves() {
    setUp();
    mp2.pass();
    Assert.assertTrue(ap2.toString().contains("not your turn"));
    mp2.play();
    Assert.assertTrue(ap2.toString().contains("not your turn"));
    mp.play();
    Assert.assertTrue(ap.toString().contains("no move selected"));
    mp.setPlayPosn(new Posn(5, 0));
    mp.play();
    Assert.assertTrue(ap.toString().contains("invalid move"));
    mp.setPlayPosn(new Posn(-4, -4));
    mp.play();
    Assert.assertTrue(ap.toString().contains("invalid move"));
  }
}
