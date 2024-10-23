package cs3500.reversi.view;

import java.util.Objects;

import cs3500.reversi.model.TileType;
import cs3500.reversi.model.ReversiModel;

/**
 * The textual view of a reversi game, a 'X' represents a black tile, 'O' represents a white tile.
 * A '_' represents no player occupies this space.
 */
public class SquareTextualView extends AbstractTextualView {

  /**
   * The constructor for the textual view of a reversi game model.
   *
   * @param rm The reversi model to be displayed as a string
   */
  public SquareTextualView(ReversiModel rm) {
    this.rm = Objects.requireNonNull(rm);
  }

  @Override
  public String toString() {
    StringBuilder viewBuilder = new StringBuilder();
    TileType[][] gameBoard = this.rm.getBoard();
    int middleLength = this.rm.getBoardWidth();
    for (int i = 0; i < middleLength; i++) {
      for (int j = 0; j < middleLength; j++) {
        viewBuilder.append(this.tileType(gameBoard[j][i])).append(" ");
      }
      if (i < middleLength - 1) {
        viewBuilder.append(System.lineSeparator());
      }
    }
    return viewBuilder.toString();
  }
}