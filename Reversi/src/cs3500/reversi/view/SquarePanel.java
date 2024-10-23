package cs3500.reversi.view;

import java.awt.event.KeyListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Objects;

import java.awt.Graphics2D;

import cs3500.reversi.model.ROReversiModel;

/**
 * A panel for a game of reversi, which shows the state of the game as per the given model, and
 * allows for cells to be highlighted.
 */
public class SquarePanel extends AbstractReversiPanel implements KeyListener, ReversiPanel {

  /**
   * A constructor for a reversi panel.
   *
   * @param rm the reversi model to display
   */
  public SquarePanel(ROReversiModel rm) {
    Objects.requireNonNull(rm);
    this.gameBoard = rm.getBoard();
    this.width = rm.getBoardWidth();
    this.featuresListeners = new ArrayList<>();
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    // this.addKeyListener(new HintKeyListener(new HintOverlayPanel()));
    this.highlight = false;
    tileList = new ArrayList<>();
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.addKeyListener(this);
    hintOn = false;
    tileSelected = false;
  }

  // draws the current reversi board
  protected void drawBoard(Graphics2D g2d) {
    int panelHeight = getHeight();
    double heightOfTile = (double) panelHeight / width;
    double drawXPos = 0;
    double drawYPos = panelHeight - heightOfTile;
    for (int row = 0; row < width; row++) {
      for (int column = 0; column < width; column++) {
        Tile tile = new Square(heightOfTile, drawXPos, drawYPos);
        this.addTile(g2d, column, row, tile, heightOfTile, drawXPos, drawYPos);
        drawXPos = drawXPos + heightOfTile;
      }
      drawYPos = drawYPos - heightOfTile;
      drawXPos = 0;
    }
  }

  /**
   * A representation of a hexagon, whose size and location, along with its color and the tile of
   * the player currently on the hexagon, can be specified.
   */
  protected static class Square extends Tile {
    double centerX;
    double centerY;

    public Square(double size, double x, double y) {
      path = new Path2D.Double();

      centerY = y + size / 2d;

      centerX = x + size / 2d;

      double xVertex = centerX + size / 2d;
      double yVertex = centerY + size / 2d;
      path.moveTo(xVertex, yVertex);
      path.lineTo(xVertex - size, yVertex);
      path.lineTo(xVertex - size, yVertex - size);
      path.lineTo(xVertex, yVertex - size);

      path.closePath();
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }

      if (!(o instanceof Square)) {
        return false;
      }

      Square s = (Square)o;

      return this.centerX == s.centerX && this.centerY == s.centerY;
    }

    @Override
    public int hashCode() {
      return Objects.hash(centerX, centerY);
    }
  }
}