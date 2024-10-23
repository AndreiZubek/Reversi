package cs3500.reversi.controller;

import cs3500.reversi.model.Posn;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.Hint;
import cs3500.reversi.view.ReversiView;
import cs3500.reversi.view.ViewFeatures;

/**
 * The controller for a reversi game, which interacts with the view and the model to display
 * the current state of the game. It notifies the player if their move is invalid, if
 * it is not their turn to make a move, or if they try to make a move, and they do not have a tile
 * selected. Players can pass, quit the game, or select a tile and play a move there.
 */
public class ReversiGUIController implements ReversiController {
  private final ReversiView view;
  private final ReversiModel model;
  private final Player player;
  private Posn currentPosition;
  private final Hint hint;

  /**
   * The constructor for a reversi controller.
   *
   * @param view the view where the board is displayed
   * @param model the model on which the game is played on
   * @param player the player which will play on the board
   */
  public ReversiGUIController(ReversiView view, ReversiModel model, Player player) {
    this.view = view;
    this.model = model;
    this.player = player;
    this.model.addListener(this);
    ViewFeaturesImpl vf = new ViewFeaturesImpl();
    this.view.addFeatureListener(vf);
    this.player.addFeatureListener(vf);
    this.currentPosition = null;
    this.view.display(true);

    hint = new Hint(3);
    hint.setBounds(400, 400, 20, 20);
    view.addHint(hint);
    hint.setVisible(false);
  }

  @Override
  public void takeTurn() {
    this.view.repaint();
    if (this.model.getCurrentPlayer() == this.player.getPlayerColor()) {
      this.player.takeTurn(this.model, this);
      this.view.repaint();
    }
  }

  @Override
  public void setPosition(Posn p) {
    this.currentPosition = p;
  }

  // listens to the key inputs from the view and makes the appropriate moves on the model, and
  // then updates the view.
  private class ViewFeaturesImpl implements ViewFeatures {
    @Override
    public void pass() {
      try {
        if (model.getCurrentPlayer() == player.getPlayerColor()) {
          model.pass();
          hint.setVisible(false);
          view.repaint();
          if (model.isGameOver()) {
            view.gameOver(model.getWinner());
            view.repaint();
          }
        }
        else {
          view.notYourTurn();
        }
      }
      catch (IllegalStateException e) {
        view.invalidMove();
      }
    }

    @Override
    public void play() {
      if (currentPosition != null) {
        try {
          if (model.getCurrentPlayer() == player.getPlayerColor()) {
            model.play(currentPosition.getCol(), currentPosition.getRow());
            hint.setVisible(false);
            view.repaint();
            setPosition(null);
          }
          else {
            view.notYourTurn();
          }
        }
        catch (IllegalStateException | IllegalArgumentException e) {
          view.invalidMove();
        }
      }
      else {
        view.noMoveSelected();
      }
    }

    @Override
    public void quit() {
      view.quit();
    }

    @Override
    public void position(Posn p) {
      setPosition(p);
    }

    @Override
    public void toggleHint(boolean toggle) {
      if (model.getCurrentPlayer() == player.getPlayerColor()) {
        hint.setVisible(toggle);
      }
      if (toggle && currentPosition != null) {
        try {
          if (model.getCurrentPlayer() == player.getPlayerColor()) {
            int numFlipped = model.flipCount(currentPosition.getCol(), currentPosition.getRow());
            hint.setNumFlipped(numFlipped - 1);
          }
        }
        catch (IllegalStateException | IllegalArgumentException e) {
          hint.setNumFlipped(0);
        }
      }
    }

    @Override
    public void drawHint(double xPos, double yPos) {
      int x = (int) xPos;
      int y = (int) yPos;
      hint.setBounds(x + 10, 320 - y, 20, 20);

      if (currentPosition != null) {
        try {
          if (model.getCurrentPlayer() == player.getPlayerColor()) {
            int numFlipped = model.flipCount(currentPosition.getCol(), currentPosition.getRow());
            hint.setNumFlipped(numFlipped - 1);
            hint.setBounds(x + 10, 320 - y, 20, 20);
            setPosition(null);
          }
        }
        catch (IllegalStateException | IllegalArgumentException e) {
          hint.setNumFlipped(0);
          hint.setBounds(x + 10, 320 - y, 20, 20);
        }
      }
    }
  }
}
