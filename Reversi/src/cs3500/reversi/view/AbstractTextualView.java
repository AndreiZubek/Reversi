package cs3500.reversi.view;

import cs3500.reversi.model.TileType;
import cs3500.reversi.model.ReversiModel;

/**
 * The textual view of a reversi game, a 'X' represents a black tile, 'O' represents a white tile.
 * A '_' represents no player occupies this space.
 */
public abstract class AbstractTextualView implements TextualView {

  protected ReversiModel rm;

  @Override
  public String toString() {
    return "";
  }

  // Returns the string representation of the given Player tile
  protected String tileType(TileType tile) {
    switch (tile) {
      case WHITE:
        return "O";
      case BLACK:
        return "X";
      case EMPTY:
        return "_";
      default:
        throw new IllegalArgumentException("Unknown player: " + this);
    }
  }
}