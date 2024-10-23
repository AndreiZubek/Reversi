package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.Direction;
import cs3500.reversi.model.TileType;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.ReversiModel;

/**
 * Chooses a move while avoiding spaces next to corners.
 */
public class AvoidOpponentCornerStrategy extends AbstractStrategy {
  private final Strategy fallback = new MaxFlipStrategy();

  @Override
  public Optional<Posn> chooseMove(ReversiModel model, TileType player) {
    this.validateArguments(model, player);
    this.validateCurrentPlayer(model, player);
    List<Posn> possibleMoves = this.findAvoidCornerMoves(model);
    Optional<Posn> bestMove = this.findBestMove(possibleMoves, model);
    if (bestMove.isPresent()) {
      return bestMove;
    }
    return fallback.chooseMove(model, player);
  }

  // returns the possible moves that avoid the corners
  private List<Posn> findAvoidCornerMoves(ReversiModel model) {
    List<Posn> possibleMoves = new ArrayList<>();
    int boardSize = model.getBoard().length;
    List<Posn> corners = model.getCorners();
    List<Posn> cornerAdjacent = new ArrayList<>();
    List<Direction> directions = model.possibleDirections();
    for (Posn corner : corners) {
      for (Direction direction : directions) {
        int col = corner.getCol() + direction.getCol();
        int row = corner.getRow() + direction.getRow();
        cornerAdjacent.add(new Posn(col,row));
      }
    }
    for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {
        boolean allowed = true;
        for (Posn posn : cornerAdjacent) {
          int posnCol = posn.getCol();
          int posnRow = posn.getRow();
          if (row == posnRow && col == posnCol) {
            allowed = false;
            break;
          }
        }
        if (allowed && model.isMoveValid(col, row)) {
          possibleMoves.add(new Posn(col, row));
        }
      }
    }
    return possibleMoves;
  }
}