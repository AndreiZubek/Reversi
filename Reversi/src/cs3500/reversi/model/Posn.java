package cs3500.reversi.model;

import java.util.Objects;

/**
 * Represents the position of a hexagon on a board in a game of reversi.
 */
public class Posn {
  private final int col;
  private final int row;

  /**
   * The constructor for a position of a tile in a game of reversi.
   *
   * @param col the column of the tile
   * @param row the row of the tile
   */
  public Posn(int col, int row) {
    this.col = col;
    this.row = row;
  }

  /**
   * Returns the column of a tile in the board of a game of reversi.
   *
   * @return the column of a tile
   */
  public int getCol() {
    return this.col;
  }

  /**
   * Returns the row of a tile in the board of a game of reversi.
   *
   * @return the row of a tile
   */
  public int getRow() {
    return this.row;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (! (o instanceof Posn)) {
      return false;
    }
    Posn p = (Posn) o;
    return (p.col == this.col && p.row == this.row);
  }

  @Override
  public int hashCode() {
    return Objects.hash(col, row);
  }
}