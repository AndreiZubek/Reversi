package cs3500.reversi.model;

import java.util.List;

/**
 * Represents the observable model interface for a game of reversi.
 */
public interface ROReversiModel {

  /**
   * Starts a game of Reversi with the given board layout.
   * @param board The board to use
   * @throws IllegalStateException If the game has already been started
   * @throws IllegalArgumentException If the given board is not valid
   */
  void startGame(TileType[][] board) throws IllegalStateException, IllegalArgumentException;

  /**
   * Starts a game of Reversi with an initial layout.
   *
   * @throws IllegalStateException If the game has already been started
   */
  void startGame(int sideLength) throws IllegalStateException;

  /**
   * Gets a copy of the current board to display.
   *
   * @return A 2D array of cells representing the board
   * @throws IllegalStateException If the game has not been started yet
   */
  TileType[][] getBoard() throws IllegalStateException;

  /**
   * Return the player whose turn it currently is.
   *
   * @return The current player
   * @throws IllegalStateException If the game has not been started yet
   */
  TileType getCurrentPlayer() throws IllegalStateException;

  /**
   * Checks the current score for a given player.
   * @param player The player to check the score for.
   * @return The score for the requested player.
   * @throws IllegalStateException If the game has not started yet.
   * @throws IllegalArgumentException If the passed player is not one of Black or White.
   */
  int getScoreFor(TileType player) throws IllegalStateException, IllegalArgumentException;

  /**
   * Signals if the game has ended.
   *
   * @return True if the game is over, else False
   * @throws IllegalStateException If the game has not started yet
   */
  boolean isGameOver() throws IllegalStateException;

  /**
   * Determines who has won the game.
   * @return The winner.
   * @throws IllegalStateException If the game has not started yet.
   */
  TileType getWinner() throws IllegalStateException;

  /**
   * Gets the player color at the given coordinates.
   *
   * @param col The tile in the row (0-indexed from the left)
   * @param row The row of the desired tile (0-indexed from the top)
   * @return The player color or null if the space has not been played yet
   * @throws IllegalArgumentException If the coordinates are not valid
   * @throws IllegalStateException    If the game has not been started yet
   */
  TileType playerAt(int col, int row) throws IllegalArgumentException, IllegalStateException;

  /**
   * Determines if the given move is allowed in the current game state for the current player.
   * @param col The column to check at (0-indexed from the left)
   * @param row The row to check at (0-indexed from the top)
   * @return True iff the move is valid
   * @throws IllegalArgumentException If any of the arguments are invalid for the current board.
   * @throws IllegalStateException If the game has not started yet.
   */
  boolean isMoveValid(int col, int row)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Determines if the current player has any valid moves.
   * @return True iff the player can make a valid move on the current board.
   * @throws IllegalArgumentException If the player is not valid
   * @throws IllegalStateException If the game has not started yet.
   */
  boolean hasValidMove() throws IllegalArgumentException, IllegalStateException;

  /**
   * Determines how many spaces will be flipped by the given move.
   * @param col The column to check at (0-indexed from the left)
   * @param row The row to check at (0-indexed from the top)
   * @return The number of tiles that will be flipped
   * @throws IllegalArgumentException If the coordinates are invalid
   * @throws IllegalStateException If the game has not started yet
   * @throws IllegalStateException If the move is not valid
   */
  int flipCount(int col, int row)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns a list of the corners of the game board.
   * @return A list of Posn each representing a corner
   * @throws IllegalStateException If the game has not started yet
   */
  List<Posn> getCorners() throws IllegalStateException;

  /**
   * Returns a list of the directions of movement allowed on the game board.
   * @return A list of Direction for the board
   * @throws IllegalStateException If the game has not started yet
   */
  List<Direction> possibleDirections() throws IllegalStateException;

  /**
   * Gets the width of the board at its widest point.
   *
   * @return The width of the board
   * @throws IllegalStateException If the game has not been started yet
   */
  int getBoardWidth() throws IllegalStateException;

  /**
   * Gets length of the side of the board.
   *
   * @return The side length of the board
   * @throws IllegalStateException If the game has not been started yet
   */
  int getBoardSideLength() throws IllegalStateException;
}