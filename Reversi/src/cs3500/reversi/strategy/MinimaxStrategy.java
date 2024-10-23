package cs3500.reversi.strategy;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.SquareReversiModel;
import cs3500.reversi.model.TileType;
import cs3500.reversi.model.Posn;
import cs3500.reversi.model.HexReversiModel;
import cs3500.reversi.model.ReversiModel;

/**
 * Chooses the move which leaves the opponent with the lowest possible gain on their next move.
 */
public class MinimaxStrategy extends AbstractStrategy {
  Strategy opponentStrategy = new MaxFlipStrategy();

  @Override
  public Optional<Posn> chooseMove(ReversiModel model, TileType player) {
    this.validateArguments(model, player);
    this.validateCurrentPlayer(model, player);
    List<Posn> possibleMoves = this.findPossibleMoves(model);
    return this.findMinimaxMove(possibleMoves, model);
  }

  // returns the move that gives the opponent the least possible gain on their next move
  private Optional<Posn> findMinimaxMove(List<Posn> possibleMoves, ReversiModel model) {
    if (possibleMoves.isEmpty()) {
      return Optional.empty();
    }
    Optional<Posn> bestMove = Optional.empty();
    int minimaxScore = -1;
    for (Posn move : possibleMoves) {
      TileType currentPlayer = model.getCurrentPlayer();
      ReversiModel nextTurnModel;
      if (model.getBoardWidth() == model.getBoardSideLength()) {
        nextTurnModel = new SquareReversiModel();
      } else {
        nextTurnModel = new HexReversiModel();
      }
      nextTurnModel.startGame(model.getBoard());
      if (currentPlayer == TileType.WHITE) {
        nextTurnModel.pass();
      }
      nextTurnModel.play(move.getCol(), move.getRow());
      int score = nextTurnModel.getScoreFor(currentPlayer);
      TileType opponent = nextTurnModel.getCurrentPlayer();
      Optional<Posn> opponentMove = opponentStrategy.chooseMove(nextTurnModel, opponent);
      if (opponentMove.isEmpty()) {
        return Optional.of(move);
      }
      int scoreDiff = nextTurnModel.getScoreFor(currentPlayer) - score;
      if (scoreDiff < minimaxScore || minimaxScore < 0) {
        minimaxScore = scoreDiff;
        bestMove = Optional.of(move);
      }
    }
    return bestMove;
  }
}