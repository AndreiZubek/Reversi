package cs3500.reversi.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.reversi.model.Posn;
import cs3500.reversi.model.ROReversiModel;

/**
 * A mock view which is used to perform tests on the controller.
 */
public class MockReversiView extends ReversiFrame {
  public Appendable ap;
  private final List<ViewFeatures> featuresListeners;

  /**
   * A constructor for a mock view.
   *
   * @param ap the appendable to use
   * @param rm the model to use
   */
  public MockReversiView(Appendable ap, ROReversiModel rm) {
    super(rm);
    this.ap = ap;
    this.featuresListeners = new ArrayList<>();
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.featuresListeners.add(Objects.requireNonNull(features));
  }

  /**
   * Notifies the feature listener to set the current position to the given position.
   *
   * @param p the position to be set to
   */
  public void setPlayPosn(Posn p) {
    for (ViewFeatures features : featuresListeners) {
      features.position(p);
    }
  }

  /**
   * Notifies the feature listener to play a move.
   */
  public void play() {
    for (ViewFeatures features : featuresListeners) {
      features.play();
    }
  }

  /**
   * Notifies the feature listener to pass.
   */
  public void pass() {
    for (ViewFeatures features : featuresListeners) {
      features.pass();
    }
  }

  @Override
  public void quit() {
    try {
      this.ap.append("quit");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void invalidMove() {
    try {
      this.ap.append("invalid move");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void repaint() {
    try {
      this.ap.append("repainted");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void notYourTurn() {
    try {
      this.ap.append("not your turn");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void noMoveSelected() {
    try {
      this.ap.append("no move selected");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
