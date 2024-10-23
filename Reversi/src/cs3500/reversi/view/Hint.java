package cs3500.reversi.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

/**
 * A hint which tells the current player how many of the opponents pieces will be flipped by
 * playing the cell that is selected.
 */
public class Hint extends JPanel implements PlayerHints {

  /**
   * Constructor for a hint, takes in the number of tiles that would be flipped by a move.
   *
   * @param numFlipped the number of tiles that would be flipped
   */
  public Hint(int numFlipped) {
    this.setLayout(new OverlayLayout(this));
    JLabel hintNum = new JLabel(String.valueOf(numFlipped));
    hintNum.setForeground(Color.BLACK);
    hintNum.setFont(new Font("Arial", Font.PLAIN, 15));

    this.setBackground(Color.CYAN);

    this.add(hintNum);
  }

  @Override
  public void setNumFlipped(int newNumFlipped) {
    Component[] components = this.getComponents();
    if (components.length > 0 && components[0] instanceof JLabel) {
      JLabel hintNum = (JLabel) components[0];
      hintNum.setText(String.valueOf(newNumFlipped));
    }
  }
}
