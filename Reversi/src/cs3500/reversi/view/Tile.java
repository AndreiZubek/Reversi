package cs3500.reversi.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

abstract class Tile {

  protected Path2D.Double path;

  protected final Color borderColor = Color.BLACK;

  // draws this Tile with the given color and a circle in the middle if specified
  void draw(Graphics2D g2d, Color fillColor, Color circleColor, boolean drawTile,
                      double size) {
    g2d.setColor(borderColor);
    g2d.setStroke(new BasicStroke(5));
    g2d.draw(path);

    g2d.setColor(fillColor);
    g2d.fill(path);

    double centerX = path.getBounds2D().getCenterX();
    double centerY = path.getBounds2D().getCenterY();

    if (drawTile) {
      double circleRadius = size / 4;

      Ellipse2D.Double circle = new Ellipse2D.Double(centerX - circleRadius,
              centerY - circleRadius, 2 * circleRadius, 2 * circleRadius);
      g2d.setColor(circleColor);
      g2d.fill(circle);
    }
  }

  // determines if this Tile contains the given point
  boolean containsPoint(double x, double y) {
    return path.contains(x, y);
  }
}