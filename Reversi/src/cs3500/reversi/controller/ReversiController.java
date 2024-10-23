package cs3500.reversi.controller;

import cs3500.reversi.model.Posn;

/**
 * This interface represents the controller for a reversi game. It interacts with the
 * model and the view in order to run the game. A player can take a turn making a move, and each
 * player can select a cell and decide if they want to play in it or pass their move.
 */
public interface ReversiController {


  /**
   * Allows the current player to make a move and displays the move to the view.
   */
  void takeTurn();

  /**
   * Sets the current player's position to the given position.
   *
   * @param p the position of the tile that the current player has selected
   */
  void setPosition(Posn p);
}
