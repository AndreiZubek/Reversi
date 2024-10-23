package cs3500.reversi.view;

/**
 * Gives players in a game of reversi hints.
 */
public interface PlayerHints {

  /**
   * Sets the number of pieces that would be flipped by the current cell selected to the given
   * number.
   *
   * @param newNumFlipped the number of pieces that would get flipped
   */
  void setNumFlipped(int newNumFlipped);
}
