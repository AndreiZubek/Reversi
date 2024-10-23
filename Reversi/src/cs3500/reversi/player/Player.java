package cs3500.reversi.player;

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.TileType;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.ViewFeatures;

/**
 * The interface for a player of a game of reversi, which can be either human or an AI. Players can
 * interact with the board by passing, playing a move in a tile, or quitting the game if they want.
 */
public interface Player {

  /**
   * Causes the player to attempt to take a turn on the given board.
   * @param model The board to play on
   * @param controller The controller for this player
   * @throws IllegalStateException if it is not this player's turn
   * @throws IllegalArgumentException if the model is unacceptable
   */
  void takeTurn(ReversiModel model, ReversiController controller)
          throws IllegalStateException, IllegalArgumentException;

  /**
   * Returns the color assigned to this player.
   * @return The tile color
   */
  TileType getPlayerColor();

  /**
   * Adds a new features listener for this player.
   * @param features The features to be added
   */
  void addFeatureListener(ViewFeatures features);
}