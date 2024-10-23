package cs3500.reversi.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.TileType;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategy.MaxFlipStrategy;
import cs3500.reversi.strategy.Strategy;
import cs3500.reversi.view.ViewFeatures;

/**
 * An AI player that uses the given strategy to select which move they will play when it is their
 * turn in a game of reversi.
 */
public class AIPlayer extends AbstractPlayer {

  Strategy strategy;

  List<ViewFeatures> features;

  /**
   * A constructor for an AI player of the given color.
   *
   * @param color the color of the player
   */
  public AIPlayer(TileType color) {
    this.strategy = new MaxFlipStrategy();
    this.tileColor = color;
    this.features = new ArrayList<>();
  }

  /**
   * A constructor for an AI player of the given color and strategy.
   *
   * @param color the color of the player
   * @param strategy the strategy of the player
   */
  public AIPlayer(Strategy strategy, TileType color) {
    this.strategy = strategy;
    this.tileColor = color;
    this.features = new ArrayList<>();
  }

  @Override
  public void takeTurn(ReversiModel model, ReversiController controller) {
    this.validateModel(model);
    this.validatePlayerTurn(model);
    Optional<Posn> move = this.strategy.chooseMove(model, this.tileColor);
    if (move.isEmpty()) {
      for (ViewFeatures feature : this.features) {
        feature.pass();
      }
    }
    else {
      Posn playPosn = move.get();
      controller.setPosition(playPosn);
      for (ViewFeatures feature : this.features) {
        feature.play();
      }
    }
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.features.add(features);
  }
}