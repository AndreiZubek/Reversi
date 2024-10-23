package cs3500.reversi.strategy;

import java.util.Optional;

import cs3500.reversi.model.TileType;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.ReversiModel;

/**
 * A Strategy is a function object which helps a player determine which move is their
 * current best option.
 */
public interface Strategy {

  /**
   * Determines which space to play at for the given player.
   * @param model The Reversi board to find a move for.
   * @param player The player to find a move for
   * @return The position to play at.
   * @throws IllegalStateException If the given player is not allowed to move at this time.
   * @throws IllegalArgumentException If either the board or player are unacceptable.
   */
  Optional<Posn> chooseMove(ReversiModel model, TileType player)
          throws IllegalStateException, IllegalArgumentException;
}