package cs3500.reversi.view;

import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.model.TileType;
import cs3500.reversi.model.Posn;

/**
 * A panel for a game of reversi, which shows the state of the game as per the given model, and
 * allows for cells to be highlighted.
 */
public abstract class AbstractReversiPanel extends JPanel implements KeyListener, ReversiPanel {

  protected int width;
  protected TileType[][] gameBoard;
  protected List<ViewFeatures> featuresListeners;
  protected boolean highlight;
  private Tile toHighlight;
  protected ArrayList<Tile> tileList;
  protected boolean hintOn;
  protected boolean tileSelected;

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(350, 350);
  }

  @Override
  public void addFeaturesListener(ViewFeatures features) {
    this.featuresListeners.add(Objects.requireNonNull(features));
  }

  @Override
  public void setGameBoard(TileType[][] gameBoard) {
    this.gameBoard = gameBoard;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    int height = getHeight();

    g2d.translate(0, height);
    g2d.scale(1, -1);

    tileList.clear();
    this.drawBoard(g2d);
  }

  // draws the current reversi board
  protected void drawBoard(Graphics2D g2d) {
    // needs to be extended
  }

  void addTile(Graphics2D g2d, int column, int row, Tile tile,
               double heightOfTile, double drawXPos, double drawYPos) {
    tileList.add(tile);
    if (highlight && toHighlight.equals(tile)) {
      if (tileType(gameBoard[column][row]) == 3) {
        tile.draw(g2d, Color.CYAN, Color.GRAY, false, 0);
        if (tileSelected) {
          this.handleDrawHint(drawXPos, drawYPos);
        }
      } else if (tileType(gameBoard[column][row]) == 2) {
        tile.draw(g2d, Color.CYAN, Color.BLACK, true, heightOfTile);
        handleToggle(false);
        tileSelected = false;
      } else if (tileType(gameBoard[column][row]) == 1) {
        tile.draw(g2d, Color.CYAN, Color.WHITE, true, heightOfTile);
        handleToggle(false);
        tileSelected = false;
      }
      System.out.println("X position: " + column + "  Y position: " + row);
      Posn currentPosition = new Posn(column, row);
      this.handlePosnReceived(currentPosition);
    } else if (tileType(gameBoard[column][row]) == 3) {
      tile.draw(g2d, Color.GRAY, Color.GRAY, false, 0);
    } else if (tileType(gameBoard[column][row]) == 2) {
      tile.draw(g2d, Color.GRAY, Color.BLACK, true, heightOfTile);
    } else if (tileType(gameBoard[column][row]) == 1) {
      tile.draw(g2d, Color.GRAY, Color.WHITE, true, heightOfTile);
    }
  }

  // updates the features in the featureListeners to draw the hint at the given coordinates
  protected void handleDrawHint(double xPos, double yPos) {
    for (ViewFeatures features : featuresListeners) {
      features.drawHint(xPos, yPos + 350 - getHeight());
    }
  }

  // updates the features in the featureListeners arraylist to the given position
  protected void handlePosnReceived(Posn p) {
    for (ViewFeatures features : featuresListeners) {
      features.position(p);
    }
  }

  // returns an int that represents the type of tile that is passed in
  protected int tileType(TileType tile) {
    switch (tile) {
      case WHITE:
        return 1;
      case BLACK:
        return 2;
      case EMPTY:
        return 3;
      default:
        throw new IllegalArgumentException("Unknown player: " + this);
    }
  }

  // a class that listens to mouse input and responds accordingly, updating
  // the view when needed. Functionality includes highlighting a cell that
  // gets clicked, unhighlighting that cell if it is clicked again, or
  // unhighlighting the cell if a click is outside the game board.
  protected class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      int height = getHeight();
      int mouseY = height - e.getY();
      int mouseX = e.getX();

      for (Tile t : tileList) {
        if (t.containsPoint(mouseX, mouseY) && !t.equals(toHighlight)) {
          highlight = true;
          toHighlight = t;
          repaint();
          tileSelected = true;
          handleToggle(hintOn);
          return;
        }
        else if (t.containsPoint(mouseX, mouseY) && t.equals(toHighlight)) {
          toHighlight = null;
          handlePosnReceived(null);
          repaint();
          tileSelected = false;
          handleToggle(false);
          return;
        }
      }
      highlight = false;
      toHighlight = null;
      handlePosnReceived(null);
      repaint();
      tileSelected = false;
      handleToggle(false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      highlight = false;
    }
  }

  // toggles the visibility of the hint feature to the given boolean
  protected void handleToggle(boolean b) {
    for (ViewFeatures features : featuresListeners) {
      features.toggleHint(b);
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // need to override in order to implement KeyListener
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();

    if (key == KeyEvent.VK_P) {
      for (ViewFeatures features : featuresListeners) {
        features.pass();
      }
    } else if (key == KeyEvent.VK_SPACE) {
      for (ViewFeatures features : featuresListeners) {
        features.play();
      }
    } else if (key == KeyEvent.VK_Q) {
      for (ViewFeatures features : featuresListeners) {
        features.quit();
      }
    } else if (key == KeyEvent.VK_H) {
      hintOn = !hintOn;
      handleToggle(hintOn && tileSelected);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // need to override in order to implement KeyListener
  }

  @Override
  public boolean isOptimizedDrawingEnabled() {
    return false;
  }
}