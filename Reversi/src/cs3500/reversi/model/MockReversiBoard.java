package cs3500.reversi.model;

import java.io.IOException;

/**
 * Represents the primary model for a reversi board. A game of reversi has
 * two players, black and white, and they play on a hexagon shaped board,
 * with black moving first.
 */
public class MockReversiBoard extends HexReversiModel {

  public Appendable transcript;

  public MockReversiBoard(Appendable a) {
    this.transcript = a;
  }

  @Override
  public void pass() {
    this.appendString("Passed" + "\n");
    super.pass();
  }

  @Override
  public void play(int col, int row) {
    this.appendString("Played at col: " + col + " row: " + row + "\n");
    super .play(col, row);
  }

  @Override
  public int getScoreFor(TileType player) {
    this.appendString("Checked score for " + player.toString() + "\n");
    return super.getScoreFor(player);
  }

  @Override
  public boolean isMoveValid(int col, int row) {
    this.appendString("Checked move at col: " + col + " row: " + row + "\n");
    return super.isMoveValid(col, row);
  }

  @Override
  public int flipCount(int col, int row) {
    this.appendString("Checked valid move score at col: " + col + " row: " + row + "\n");
    return super.flipCount(col, row);
  }

  // appends the given string to the transcript
  private void appendString(String string) {
    try {
      transcript.append(string);
    } catch (IOException ioe) {
      System.out.print("Could not append");
    }
  }
}