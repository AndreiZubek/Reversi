package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.TileType;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.ReversiModel;

/**
 * Chooses a move while prioritizing corners if possible.
 */
public class ChooseCornerStrategy extends AbstractStrategy {
  private final Strategy fallback = new AvoidOpponentCornerStrategy();

  @Override
  public Optional<Posn> chooseMove(ReversiModel model, TileType player) {
    this.validateArguments(model, player);
    this.validateCurrentPlayer(model, player);
    List<Posn> possibleMoves = this.findPossibleCornerMoves(model);
    Optional<Posn> move = this.findBestMove(possibleMoves, model);
    if (move.isPresent()) {
      return move;
    }
    return fallback.chooseMove(model, player);
  }

  // returns a list of moves that prioritize corners
  private List<Posn> findPossibleCornerMoves(ReversiModel model) {
    List<Posn> possibleMoves = new ArrayList<>();
    List<Posn> corners = model.getCorners();
    for (Posn corner : corners) {
      if (model.isMoveValid(corner.getCol(), corner.getRow())) {
        possibleMoves.add(corner);
      }
    }
    return possibleMoves;
  }
}