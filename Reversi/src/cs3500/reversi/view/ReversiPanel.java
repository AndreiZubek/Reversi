package cs3500.reversi.view;

import cs3500.reversi.model.TileType;

/**
 * A reversi panel interface which can add a feature of a game of reversi to the panel.
 */
public interface ReversiPanel {

  /**
   * Adds a feature of the game of reversi to a panel.
   *
   * @param features the feature of the game to add
   */
  void addFeaturesListener(ViewFeatures features);

  /**
   * Updates the current game board stored in the panel.
   * @param gameBoard The board to be updated
   */
  void setGameBoard(TileType[][] gameBoard);
}
