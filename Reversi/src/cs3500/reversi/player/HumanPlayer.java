package cs3500.reversi.player;

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.TileType;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.ViewFeatures;

/**
 * A human player in a game of reversi. They can choose to make whatever move they want, such as
 * passing or playing a move into a selected tile. They can also quit out of a game.
 */
public class HumanPlayer extends AbstractPlayer {

  public HumanPlayer(TileType color) {
    this.tileColor = color;
  }

  @Override
  public void takeTurn(ReversiModel model, ReversiController controller) {
    this.validateModel(model);
    this.validatePlayerTurn(model);
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    // need to override
  }
}