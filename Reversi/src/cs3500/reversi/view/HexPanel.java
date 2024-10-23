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
public class HexPanel extends AbstractReversiPanel implements KeyListener, ReversiPanel {

  private final int sideLength;

  /**
   * A constructor for a reversi panel.
   *
   * @param rm the reversi model to display
   */
  public HexPanel(ROReversiModel rm) {
    Objects.requireNonNull(rm);
    this.gameBoard = rm.getBoard();
    this.sideLength = rm.getBoardSideLength();
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
    double drawYPos = panelHeight - heightOfTile - 5;
    double translateX = Math.sqrt(3) / 2 * heightOfTile;
    boolean addSpace = true;
    for (int row = 0; row < width; row++) {
      for (int column = 0; column < width; column++) {
        if (row >= sideLength && addSpace) {
          addSpace = false;
          for (int i = row - sideLength; i >= 0; i--) {
            drawXPos = drawXPos + translateX / 2;
          }
        }
        if (gameBoard[row][column] == null) {
          drawXPos = drawXPos + translateX / 2;
        } else {
          Tile tile = new Hexagon(heightOfTile, drawXPos, drawYPos);
          this.addTile(g2d, column, row, tile, heightOfTile, drawXPos, drawYPos);
          drawXPos = drawXPos + translateX + 2.5;
        }
      }
      drawYPos = drawYPos - heightOfTile * 3 / 4 - 2.5;
      drawXPos = 0;
      addSpace = true;
    }
  }

  /**
   * A representation of a hexagon, whose size and location, along with its color and the tile of
   * the player currently on the hexagon, can be specified.
   */
  protected static class Hexagon extends Tile {
    double centerX;
    double centerY;

    public Hexagon(double size, double x, double y) {
      path = new Path2D.Double();

      centerY = y + size / 2d;

      centerX = x + size / 2d;

      for (int i = 0; i < 6; i++) {
        double angleDegrees = (60d * i) - 30d;
        double angleRad = Math.toRadians(angleDegrees);

        double xVertex = centerX + ((size / 2f) * Math.cos(angleRad));
        double yVertex = centerY + ((size / 2f) * Math.sin(angleRad));

        if (i == 0) {
          path.moveTo(xVertex, yVertex);
        } else {
          path.lineTo(xVertex, yVertex);
        }
      }
      path.closePath();
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }

      if (!(o instanceof Hexagon)) {
        return false;
      }

      Hexagon h = (Hexagon)o;

      return this.centerX == h.centerX && this.centerY == h.centerY;
    }

    @Override
    public int hashCode() {
      return Objects.hash(centerX, centerY);
    }
  }
}