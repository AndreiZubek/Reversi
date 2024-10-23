package cs3500.reversi.view;

import cs3500.reversi.model.TileType;

/**
 * A reversi frame interface which can add a feature of a game of reversi to the frame and display
 * the frame. You can call the various methods in this interface to display different messages.
 */
public interface ReversiView {

  /**
   * Adds a feature of the game of reversi to a frame.
   *
   * @param features the feature of the game to add
   */
  void addFeatureListener(ViewFeatures features);

  /**
   * Displays the current frame.
   *
   * @param show true to display the frame, false otherwise
   */
  void display(boolean show);

  /**
   * Repaint the view for the current state.
   */
  void repaint();

  /**
   * Displays an error message to the player for an invalid move.
   */
  void invalidMove();

  /**
   * Displays an error message to the player for an out of turn move.
   */
  void notYourTurn();

  /**
   * Displays an error message to the user that no position was chosen to move at.
   */
  void noMoveSelected();

  /**
   * Displays the winner of a reversi game.
   */
  void gameOver(TileType winner);

  /**
   * Allows a human player to quit out of a reversi game.
   */
  void quit();

  /**
   * Adds the given hint to the view.
   *
   * @param hint the hint to add
   */
  void addHint(Hint hint);
}
