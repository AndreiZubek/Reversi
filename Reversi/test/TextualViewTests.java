import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.HexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.HexTextualView;
import cs3500.reversi.view.TextualView;

/**
 * The tests for the textual view of a reversi game.
 */
public class TextualViewTests {

  ReversiModel rm;
  ReversiModel smallBoard;
  TextualView tv;
  TextualView smallView;

  @Before
  public void setUpReversiTextual() {
    rm = new HexReversiModel();
    rm.startGame(4);
    smallBoard = new HexReversiModel();
    smallBoard.startGame(3);
    tv = new HexTextualView(rm);
    smallView = new HexTextualView(smallBoard);
  }

  @Test
  public void testEdgeOfBoardWhiteMove() {
    setUpReversiTextual();
    String beforeView =
            "  _ _ _ " + System.lineSeparator() +
                    " _ X O _ " + System.lineSeparator() +
                    "_ O _ X _ " + System.lineSeparator() +
                    " _ X O _  " + System.lineSeparator() +
                    "  _ _ _   ";
    Assert.assertEquals(beforeView, smallView.toString());
    smallBoard.play(3, 0);
    String expectedView =
            "  _ X _ " + System.lineSeparator() +
                    " _ X X _ " + System.lineSeparator() +
                    "_ O _ X _ " + System.lineSeparator() +
                    " _ X O _  " + System.lineSeparator() +
                    "  _ _ _   ";
    String actualView = smallView.toString();
    Assert.assertEquals(expectedView, actualView);
  }

  @Test
  public void testEdgeOfBoardBlackMove() {
    setUpReversiTextual();
    smallBoard.play(3, 0);
    String beforeView =
            "  _ X _ " + System.lineSeparator() +
                    " _ X X _ " + System.lineSeparator() +
                    "_ O _ X _ " + System.lineSeparator() +
                    " _ X O _  " + System.lineSeparator() +
                    "  _ _ _   ";
    Assert.assertEquals(beforeView, smallView.toString());
    smallBoard.play(4, 1);
    String expectedView =
            "  _ X _ " + System.lineSeparator() +
                    " _ X X O " + System.lineSeparator() +
                    "_ O _ O _ " + System.lineSeparator() +
                    " _ X O _  " + System.lineSeparator() +
                    "  _ _ _   ";
    String actualView = smallView.toString();
    Assert.assertEquals(expectedView, actualView);
  }

  @Test
  public void testWhiteMoveMultipleTilesChangedFromBlackToWhite() {
    setUpReversiTextual();
    rm.play(2, 2);
    rm.play(5, 2);
    rm.play(4, 4);
    rm.play(2, 5);
    rm.play(4, 1);
    String expectedBefore =
            "   _ _ _ _ " + System.lineSeparator() +
                    "  _ _ X _ _ " + System.lineSeparator() +
                    " _ X X X O _ " + System.lineSeparator() +
                    "_ _ X _ X _ _ " + System.lineSeparator() +
                    " _ _ X O X _  " + System.lineSeparator() +
                    "  _ _ O _ _   " + System.lineSeparator() +
                    "   _ _ _ _    ";
    Assert.assertEquals(expectedBefore, tv.toString());
    rm.play(2, 1);
    String expectedView =
            "   _ _ _ _ " + System.lineSeparator() +
                    "  O _ X _ _ " + System.lineSeparator() +
                    " _ O X X O _ " + System.lineSeparator() +
                    "_ _ O _ X _ _ " + System.lineSeparator() +
                    " _ _ O O X _  " + System.lineSeparator() +
                    "  _ _ O _ _   " + System.lineSeparator() +
                    "   _ _ _ _    ";
    String actualView = tv.toString();
    Assert.assertEquals(expectedView, actualView);
  }

  @Test
  public void testBlackMoveMultipleTilesChangedFromWhiteToBlack() {
    setUpReversiTextual();
    rm.play(2, 2);
    rm.play(5, 2);
    rm.play(4, 4);
    rm.play(2, 5);
    String expectedBefore =
            "   _ _ _ _ " + System.lineSeparator() +
                    "  _ _ _ _ _ " + System.lineSeparator() +
                    " _ X X O O _ " + System.lineSeparator() +
                    "_ _ X _ O _ _ " + System.lineSeparator() +
                    " _ _ X O X _  " + System.lineSeparator() +
                    "  _ _ O _ _   " + System.lineSeparator() +
                    "   _ _ _ _    ";
    Assert.assertEquals(expectedBefore, tv.toString());
    rm.play(4, 1);
    String expectedView =
            "   _ _ _ _ " + System.lineSeparator() +
                    "  _ _ X _ _ " + System.lineSeparator() +
                    " _ X X X O _ " + System.lineSeparator() +
                    "_ _ X _ X _ _ " + System.lineSeparator() +
                    " _ _ X O X _  " + System.lineSeparator() +
                    "  _ _ O _ _   " + System.lineSeparator() +
                    "   _ _ _ _    ";
    String actualView = tv.toString();
    Assert.assertEquals(expectedView, actualView);
  }

  @Test
  public void testFirstMoveBlack() {
    setUpReversiTextual();
    String expectedBefore =
            "   _ _ _ _ " + System.lineSeparator() +
                    "  _ _ _ _ _ " + System.lineSeparator() +
                    " _ _ X O _ _ " + System.lineSeparator() +
                    "_ _ O _ X _ _ " + System.lineSeparator() +
                    " _ _ X O _ _  " + System.lineSeparator() +
                    "  _ _ _ _ _   " + System.lineSeparator() +
                    "   _ _ _ _    ";
    Assert.assertEquals(expectedBefore, tv.toString());
    rm.play(2, 2);
    String moveView =
            "   _ _ _ _ " + System.lineSeparator() +
                    "  _ _ _ _ _ " + System.lineSeparator() +
                    " _ X X O _ _ " + System.lineSeparator() +
                    "_ _ X _ X _ _ " + System.lineSeparator() +
                    " _ _ X O _ _  " + System.lineSeparator() +
                    "  _ _ _ _ _   " + System.lineSeparator() +
                    "   _ _ _ _    ";
    String view = tv.toString();
    Assert.assertEquals(moveView, view);
  }

  @Test
  public void testSecondMoveWhite() {
    setUpReversiTextual();
    rm.play(2, 2);
    String expectedBefore =
            "   _ _ _ _ " + System.lineSeparator() +
                    "  _ _ _ _ _ " + System.lineSeparator() +
                    " _ X X O _ _ " + System.lineSeparator() +
                    "_ _ X _ X _ _ " + System.lineSeparator() +
                    " _ _ X O _ _  " + System.lineSeparator() +
                    "  _ _ _ _ _   " + System.lineSeparator() +
                    "   _ _ _ _    ";
    Assert.assertEquals(expectedBefore, tv.toString());
    rm.play(5, 2);
    String moveView =
            "   _ _ _ _ " + System.lineSeparator() +
                    "  _ _ _ _ _ " + System.lineSeparator() +
                    " _ X X O O _ " + System.lineSeparator() +
                    "_ _ X _ O _ _ " + System.lineSeparator() +
                    " _ _ X O _ _  " + System.lineSeparator() +
                    "  _ _ _ _ _   " + System.lineSeparator() +
                    "   _ _ _ _    ";
    String view = tv.toString();
    Assert.assertEquals(moveView, view);
  }

  @Test
  public void testInitialGameState() {
    setUpReversiTextual();
    String initView =
            "   _ _ _ _ " + System.lineSeparator() +
                    "  _ _ _ _ _ " + System.lineSeparator() +
                    " _ _ X O _ _ " + System.lineSeparator() +
                    "_ _ O _ X _ _ " + System.lineSeparator() +
                    " _ _ X O _ _  " + System.lineSeparator() +
                    "  _ _ _ _ _   " + System.lineSeparator() +
                    "   _ _ _ _    ";
    String view = tv.toString();
    Assert.assertEquals(initView, view);

    String initSmallView =
            "  _ _ _ " + System.lineSeparator() +
                    " _ X O _ " + System.lineSeparator() +
                    "_ O _ X _ " + System.lineSeparator() +
                    " _ X O _  " + System.lineSeparator() +
                    "  _ _ _   ";
    String smallView = this.smallView.toString();
    Assert.assertEquals(initSmallView, smallView);
  }
}
