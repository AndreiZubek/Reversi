package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cs3500.reversi.model.TileType;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.ReversiModel;

/**
 * An abstract implementation of Strategy which includes some basic functions
 * used in multiple strategies.
 */
public abstract class AbstractStrategy implements Strategy {

  // Validates that both arguments are acceptable
  protected void validateArguments(ReversiModel model, TileType player) {
    if (Objects.isNull(model) || Objects.isNull(player) || player == TileType.EMPTY) {
      throw new IllegalArgumentException("Invalid arguments given to the strategy");
    }
  }

  // Validates that it is the given player's turn
  protected void validateCurrentPlayer(ReversiModel model, TileType player) {
    if (model.getCurrentPlayer() != player) {
      throw new IllegalStateException("It is not this player's turn to move");
    }
  }

  // Finds all moves which are allowed for the given player
  protected List<Posn> findPossibleMoves(ReversiModel model) {
    List<Posn> possibleMoves = new ArrayList<>();
    int boardSize = model.getBoardWidth();
    for (int row = 0; row < boardSize; row ++) {
      for (int col = 0; col < boardSize; col++) {
        if (model.isMoveValid(col, row)) {
          possibleMoves.add(new Posn(col, row));
        }
      }
    }
    return possibleMoves;
  }

  // Finds the move which flips the most tiles for the current player from the provided list
  protected Optional<Posn> findBestMove(List<Posn> possibleMoves, ReversiModel model) {
    if (possibleMoves.isEmpty()) {
      return Optional.empty();
    }
    Optional<Posn> bestMove = Optional.empty();
    int maxScore = 0;
    for (Posn move : possibleMoves) {
      int flipCount = model.flipCount(move.getCol(), move.getRow());
      if (flipCount > maxScore) {
        maxScore = flipCount;
        bestMove = Optional.of(move);
      }
    }
    return bestMove;
  }
}