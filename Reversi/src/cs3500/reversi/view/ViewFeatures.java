package cs3500.reversi.view;

import cs3500.reversi.model.Posn;

/**
 * The features of a reversi game that are needed to play it.
 */
public interface ViewFeatures {

  /**
   * Allows a player to pass their move.
   */
  void pass();

  /**
   * Allows the user to play a move in a tile.
   */
  void play();

  /**
   * Allows the user to quit out of a game of reversi.
   */
  void quit();

  /**
   * Sets the position of the current player to the given position.
   *
   * @param p the position to set the position of the current player to
   */
  void position(Posn p);

  /**
   * Sets the visibility of the hint.
   *
   * @param toggle determines if the hint is visible or not
   */
  void toggleHint(boolean toggle);

  /**
   * Draws the hint at the given position.
   *
   * @param xPos the x position of the hint
   * @param yPos the y position of the hint
   */
  void drawHint(double xPos, double yPos);
}
