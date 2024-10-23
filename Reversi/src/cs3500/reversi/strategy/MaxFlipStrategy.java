package cs3500.reversi.strategy;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.TileType;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.ReversiModel;

/**
 * Chooses the move which flips the most of the opponent's pieces possible.
 */
public class MaxFlipStrategy extends AbstractStrategy {
  @Override
  public Optional<Posn> chooseMove(ReversiModel model, TileType player) {
    this.validateArguments(model, player);
    this.validateCurrentPlayer(model, player);
    List<Posn> possibleMoves = this.findPossibleMoves(model);
    return this.findBestMove(possibleMoves, model);
  }
}