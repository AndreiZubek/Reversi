package cs3500.reversi.model;

/**
 * Represents the directions to move from one hexagon to another in a reversi game.
 */
public enum Direction {
  Up(-1, 0),
  Left(0, -1),
  DownLeft(1, -1),
  UpRight(-1, 1),
  Right(0, 1),
  Down(1, 0),
  UpLeft(-1, -1),
  DownRight(1, 1);


  private final int row;

  private final int col;

  /**
   * Returns the horizontal movement of this direction.
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Returns the vertical movement of this direction.
   */
  public int getCol() {
    return this.col;
  }

  Direction(int row, int col) {
    this.row = row;
    this.col = col;
  }
}