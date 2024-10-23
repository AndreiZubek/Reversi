package cs3500.reversi.player;

import java.util.Objects;

import cs3500.reversi.model.ROReversiModel;
import cs3500.reversi.model.TileType;

/**
 * An abstract implementation of Strategy which includes some basic functions
 * used in multiple strategies.
 */
public abstract class AbstractPlayer implements Player {

  protected TileType tileColor;

  @Override
  public TileType getPlayerColor() {
    return this.tileColor;
  }

  // Validates the model given to the player is acceptable
  protected void validateModel(ROReversiModel model) {
    if (Objects.isNull(model)) {
      throw new IllegalArgumentException("Model is unacceptable");
    }
  }

  // Validates that it is this player's turn in the given game
  protected void validatePlayerTurn(ROReversiModel model) {
    if (model.getCurrentPlayer() != this.tileColor) {
      throw new IllegalStateException("Not this player's turn");
    }
  }
}