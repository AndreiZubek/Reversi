package cs3500.reversi.model;

import cs3500.reversi.controller.ReversiController;

/**
 * Represents the mutable model interface for playing a game of reversi. Players can pass their
 * move or make a move into a tile.
 */
public interface ReversiModel extends ROReversiModel {

  /**
   * Passes play to the next player.
   *
   * @throws IllegalStateException If the game has not started yet
   */
  void pass() throws IllegalStateException;

  /**
   * Plays a piece for the current player at the given coordinates.
   *
   * @param col  The column of the space to play (0-indexed from the left)
   * @param row The row of the space to play (0-indexed from the top)
   * @throws IllegalStateException    If the game has not started yet
   * @throws IllegalArgumentException If the coordinates are not within the board
   * @throws IllegalStateException    If the move is not valid
   */
  void play(int col, int row) throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds a new controller to listen to the game.
   * @param controller The controller to be added
   * @throws IllegalArgumentException If the player is not acceptable
   * @throws IllegalStateException If the game already has two controllers
   */
  void addListener(ReversiController controller) throws IllegalArgumentException,
          IllegalStateException;
}