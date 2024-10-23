package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.Posn;
import cs3500.reversi.model.HexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.TileType;

/**
 * Tests for the package private methods on strategies.
 */
public class StrategyPackageTests {

  ReversiModel rm;
  AbstractStrategy strategy;

  @Before
  public void setUpReversiModel() {
    rm = new HexReversiModel();
    rm.startGame(4);
    strategy = new MaxFlipStrategy();
  }

  @Test
  public void testValidateCurrentPlayer() {
    setUpReversiModel();
    strategy.validateCurrentPlayer(rm, TileType.BLACK);
    Assert.assertThrows(IllegalStateException.class,
        () -> strategy.validateCurrentPlayer(rm, TileType.WHITE));
  }

  @Test
  public void testValidateArguments() {
    setUpReversiModel();
    strategy.validateArguments(rm, TileType.BLACK);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> strategy.validateArguments(rm, TileType.EMPTY));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> strategy.validateArguments(rm, null));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> strategy.validateArguments(null, TileType.BLACK));
  }

  @Test
  public void testFindPossibleMoves() {
    setUpReversiModel();
    List<Posn> possibleMoves = strategy.findPossibleMoves(rm);
    List<Posn> expectedMoves = Arrays.asList(
            new Posn(4, 1),
            new Posn(2, 2),
            new Posn(5, 2),
            new Posn(1, 4),
            new Posn(4, 4),
            new Posn(2, 5));
    Assert.assertEquals(6, possibleMoves.size());
    for (Posn p : expectedMoves) {
      Assert.assertTrue(possibleMoves.contains(p));
    }
  }

  @Test
  public void testFindBestMove() {
    setUpReversiModel();
    List<Posn> possibleMoves = Arrays.asList(
            new Posn(4, 1),
            new Posn(2, 2),
            new Posn(5, 2),
            new Posn(1, 4),
            new Posn(4, 4),
            new Posn(2, 5));
    Optional<Posn> move1 = strategy.findBestMove(possibleMoves, rm);
    Assert.assertTrue(move1.isPresent());
    Assert.assertEquals(new Posn(4,1), move1.get());
    rm.play(4,1);
    possibleMoves = Arrays.asList(
            new Posn(5, 0),
            new Posn(1, 4),
            new Posn(2, 5));
    Optional<Posn> move2 = strategy.findBestMove(possibleMoves, rm);
    Assert.assertTrue(move2.isPresent());
    Assert.assertEquals(new Posn(5,0), move2.get());
  }

  @Test
  public void testFindBestMoveNoMove() {
    Optional<Posn> move = strategy.findBestMove(new ArrayList<>(), rm);
    Assert.assertEquals(Optional.empty(), move);
  }
}