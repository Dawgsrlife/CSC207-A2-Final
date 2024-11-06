package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;

/**
 * A class representing a triangle on the canvas
 *
 * @author mengale1
 */
public class Triangle extends Shape {

    /**
     * Initialize an isosceles Triangle with two given coordinates
     *
     * @param start starting coordinate
     * @param end   ending coordinate
     */
    public Triangle(Point start, Point end, PaintProperties pp) {
        super(start, end, "Triangle", pp);
    }

    /**
     * Paint the triangle
     * @param g2d
     */
    @Override
    void paint(GraphicsContext g2d) {
        // Grab points:
        double[] xPoints = getXCoordinates();
        double[] yPoints = getYCoordinates();

        // Fill the triangle with the border colour:
        if (getProperties().getStrokeThickness() != 0.0) {
            g2d.setStroke(getProperties().getStrokeColor());
            g2d.setLineWidth(getProperties().getStrokeThickness());
            g2d.strokePolygon(xPoints, yPoints, 3);
        }

        // If not filled, remove inner fill colour:
        if (getProperties().isFilled()) {
            fill(g2d);
        }
    }

    @Override
    protected void fill(GraphicsContext g2d) {
        g2d.setFill(getProperties().getFillColor());
        double width = getProperties().getStrokeThickness();
        double[] xPoints = getXCoordinates(width);
        double[] yPoints = getYCoordinates(width);
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    protected double[] getPaintInfo() {
        double startX = Math.min(getStart().x, getEnd().x);
        double startY = Math.min(getStart().y, getEnd().y);
        double width = Math.abs(getEnd().x - getStart().x);
        double height = Math.abs(getEnd().y - getStart().y);
        return new double[]{startX, startY, width, height};
    }

    @Override
    boolean includeCursor(Point p) {
        // TODO: need review on consistency to javafx.polygon
        return false;
    }

    private double[] getXCoordinates() {
        double[] info = getPaintInfo();
        double startX = info[0];
        double width = info[2];
        return new double[] {
                startX + width / 2,  // Top point (middle of the bounding box)
                startX,              // Bottom-left corner
                startX + width       // Bottom-right corner
        };
    }

    private double[] getYCoordinates() {
        double[] info = getPaintInfo();
        double startY = info[1];
        double height = info[3];
        return new double[] {
                startY,           // Top point (top of the bounding box)
                startY + height,  // Bottom-left corner
                startY + height   // Bottom-right corner
        };
    }

    private double[] getXCoordinates(double borderWidth) {
        double[] info = getPaintInfo();
        double startX = info[0] + borderWidth / 2;
        double width = info[2] - borderWidth;
        return new double[]{
                startX + width / 2, // Top point
                startX,             // Bottom-left point
                startX + width      // Bottom-right point
        };
    }

    private double[] getYCoordinates(double borderWidth) {
        double[] info = getPaintInfo();
        double startY = info[1] + borderWidth / 2;
        double height = info[3] - borderWidth;
        return new double[]{
                startY,             // Top point
                startY + height,    // Bottom-left point
                startY + height     // Bottom-right point
        };
    }
}
