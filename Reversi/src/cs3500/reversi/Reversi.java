package cs3500.reversi;

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.controller.ReversiGUIController;
import cs3500.reversi.model.HexReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.SquareReversiModel;
import cs3500.reversi.model.TileType;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.AvoidOpponentCornerStrategy;
import cs3500.reversi.strategy.ChooseCornerStrategy;
import cs3500.reversi.strategy.MaxFlipStrategy;
import cs3500.reversi.strategy.MinimaxStrategy;
import cs3500.reversi.view.ReversiFrame;
import cs3500.reversi.view.ReversiView;

/**
 * Class to run the view for a game of reversi from.
 */
public final class Reversi {

  /**
   * Main method where you can run the graphical view for a game of reversi. The given input should
   * be 3 strings long. The first 2 should describe the players, and the third should be the size
   * of the game that you want to display. Refer to the README for information on how to enter the
   * player info.
   */
  public static void main(String[] args) {

    if (args == null) {
      throw new IllegalArgumentException("args is null");
    }
    if (args.length != 4) {
      throw new IllegalArgumentException("Please provide 4 arguments. The game type, the two "
              + "player types, and the board size.");
    }
    int boardSize;
    try {
      boardSize = Integer.parseInt(args[3]);
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("third argument has to be an integer");
    }

    ReversiModel model = createModel(args[0]);
    model.startGame(boardSize);

    ReversiView viewPlayer1 = new ReversiFrame(model);
    ReversiView viewPlayer2 = new ReversiFrame(model);
    Player player1 = createPlayer(args[1], TileType.BLACK);
    Player player2 = createPlayer(args[2], TileType.WHITE);
    ReversiController controller1 = new ReversiGUIController(viewPlayer1, model, player1);
    ReversiController controller2 = new ReversiGUIController(viewPlayer2, model, player2);

    if (!args[1].equals("human")) {
      player1.takeTurn(model, controller1);
    }
  }

  private static ReversiModel createModel(String type) {
    ReversiModel model;
    switch (type) {
      case "hex":
        model = new HexReversiModel();
        break;
      case "square":
        model = new SquareReversiModel();
        break;
      default:
        throw new IllegalArgumentException("Unknown game type");
    }
    return model;
  }

  // creates a player of the given type(human or AI) and the given color
  private static Player createPlayer(String type, TileType color) {
    Player player;

    switch (type) {
      case "human":
        player = new HumanPlayer(color);
        break;
      case "maxFlip":
        player = new AIPlayer(new MaxFlipStrategy(), color);
        break;
      case "avoidCorner":
        player = new AIPlayer(new AvoidOpponentCornerStrategy(), color);
        break;
      case "chooseCorner":
        player = new AIPlayer(new ChooseCornerStrategy(), color);
        break;
      case "minimax":
        player = new AIPlayer(new MinimaxStrategy(), color);
        break;
      default:
        throw new IllegalArgumentException("Unknown player type");
    }

    return player;
  }
}