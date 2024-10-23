package cs3500.reversi.view;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import cs3500.reversi.model.TileType;
import cs3500.reversi.model.ROReversiModel;

/**
 * A reversi frame which can add a feature of a game of reversi to the frame and display
 * a model for a game of reversi. Tiles can be highlighted, and coordinates for those
 * tiles are displayed. You can resize the frame.
 */
public class ReversiFrame extends JFrame implements ReversiView  {

  private final ROReversiModel model;

  private final AbstractReversiPanel panel;

  private JLabel scoreLabel;

  private JLabel currentPlayerLabel;

  /**
   * A constructor for a frame of a reversi game, you can resize it and display the given reversi
   * model.
   *
   * @param model the given read only reversi model to display
   */
  public ReversiFrame(ROReversiModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);
    if (model.getBoardSideLength() == model.getBoardWidth()) {
      this.panel = new SquarePanel(model);
    } else {
      this.panel = new HexPanel(model);
    }
    this.model = model;
    this.add(panel);

    panel.setLayout(null);

    this.initializeScoreLabels();
    this.pack();
  }

  @Override
  public void addHint(Hint hint) {
    panel.add(hint);
  }

  // updates the score labels for each player and updates which is the current player
  private void updateLabels() {
    scoreLabel.setText("<html>Black Score: "
            + model.getScoreFor(TileType.BLACK)
            + "<br>White Score: "
            + model.getScoreFor(TileType.WHITE)
            + "</html>");
    if (this.model.getCurrentPlayer() == TileType.BLACK) {
      currentPlayerLabel.setText("Current Player: Black");
    } else {
      currentPlayerLabel.setText("Current Player: White");
    }
  }

  // initializes the score of each player to the number of tiles they have on the board, which
  // is initially 3
  private void initializeScoreLabels() {
    scoreLabel = new JLabel("<html>Black Score: 3<br>White Score: 3</html>");
    currentPlayerLabel = new JLabel("Current Player: Black");

    this.getContentPane().add(scoreLabel, BorderLayout.PAGE_START);
    this.getContentPane().add(currentPlayerLabel, BorderLayout.SOUTH);
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void repaint() {
    this.updateLabels();
    this.panel.setGameBoard(model.getBoard());
    this.panel.repaint();
  }

  @Override
  public void invalidMove() {
    JOptionPane.showMessageDialog(this, "Invalid move");
  }

  @Override
  public void notYourTurn() {
    JOptionPane.showMessageDialog(this, "It is not your turn");
  }

  @Override
  public void gameOver(TileType winner) {
    if (winner == TileType.EMPTY) {
      JOptionPane.showMessageDialog(this, "Tie game!");
      return;
    }
    JOptionPane.showMessageDialog(this, winner.toString() + " won the game!");
  }

  @Override
  public void noMoveSelected() {
    JOptionPane.showMessageDialog(this, "Select a tile to play");
  }

  @Override
  public void quit() {
    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
}
