import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Gui extends JPanel {

    int counter = 0;
    int offsetx = 300;
    int offsety = 100;
    private Solution path;
    private Wind direction;
    private ArrayList<Solution> solutions = new ArrayList<Solution>();
    private ArrayList<Point> points = new ArrayList<Point>();

    public Gui() {
        this.setBackground(Color.cyan);
    }

    public void paintPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public void repaintSolution(Solution solution, Wind wind, Solution finishedSolution, boolean last) {
        if (last == true) {
            solutions.add(finishedSolution);
        }
        path = solution;
        direction = wind;
    }

    public void paintComponent(Graphics g) {
        // Always call super.paintComponent (g):
        super.paintComponent(g);

        for (Point p : points) {
            this.drawCircle(g, (int) (p.getX()) + offsetx, (int) (p.getY()) + offsety, 5, "red");
        }

        for (int i = 0; i < solutions.size(); i++) {
            this.drawPath(solutions.get(i), g);
        }

        g.drawLine(direction.from.x, direction.from.y, direction.to.x, direction.to.y);
        g.fillOval(direction.to.x - 10, direction.to.y - 10, 10 * 2, 10 * 2);


        this.drawPath(path, g);
    }

    private void drawPath(Solution drawPath, Graphics g) {

        for (int i = 0; i < drawPath.getSolution().size(); i++) {
            if (i == 0 || i == drawPath.getSolution().size() - 1) {
                this.drawCircle(g, (int) (drawPath.getSolution().get(i).getX()) + offsetx, (int) (drawPath.getSolution().get(i).getY()) + offsety, 5, "red");
                if (i + 1 < drawPath.getSolution().size()) {
                    g.setColor(Color.BLACK);
                    g.drawLine((int) (drawPath.getSolution().get(i).getX()) + offsetx, (int) (drawPath.getSolution().get(i).getY()) + offsety, (int) (drawPath.getSolution().get(i + 1).getX()) + offsetx, (int) (drawPath.getSolution().get(i + 1).getY()) + offsety);
                }
            } else {
                this.drawCircle(g, (int) (drawPath.getSolution().get(i).getX()) + offsetx, (int) (drawPath.getSolution().get(i).getY()) + offsety, 5, "black");
                if (i + 1 < drawPath.getSolution().size()) {
                    g.drawLine((int) (drawPath.getSolution().get(i).getX()) + offsetx, (int) (drawPath.getSolution().get(i).getY()) + offsety, (int) (drawPath.getSolution().get(i + 1).getX()) + offsetx, (int) (drawPath.getSolution().get(i + 1).getY()) + offsety);
                }

            }
        }
    }

    private void drawCircle(Graphics g, int x, int y, int radius, String color) {
        if (color.equals("red")) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}
