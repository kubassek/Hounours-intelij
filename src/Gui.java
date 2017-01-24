import java.awt.*;
import javax.swing.*;

public class Gui extends JPanel{

    private Solution path;
    private Wind direction;
    int offsetx = 300;
    int offsety = 100;
    public Gui(){
        this.setBackground (Color.cyan);
    }

    public void repaintSolution(Solution solution, Wind wind){
        path = solution;
        direction = wind;
    }

    public void paintComponent (Graphics g)
    {
        // Always call super.paintComponent (g):
        super.paintComponent(g);

        g.drawLine(direction.from.x, direction.from.y, direction.to.x, direction.to.y);
        g.fillOval(direction.to.x - 10,  direction.to.y - 10, 10*2, 10*2);

        for(int i=0; i<path.getSolution().size(); i++){
            if(i==0 || i==path.getSolution().size()-1){
                this.drawCircle(g, (int)(path.getSolution().get(i).getX()) + offsetx, (int)(path.getSolution().get(i).getY()) +  offsety, 5, "red");
                if(i+1<path.getSolution().size()){
                    g.setColor(Color.BLACK);
                    g.drawLine((int)(path.getSolution().get(i).getX()) + offsetx, (int)(path.getSolution().get(i).getY()) + offsety, (int)(path.getSolution().get(i+1).getX())+offsetx, (int)(path.getSolution().get(i+1).getY())+offsety);
                }
            }
            else{
                this.drawCircle(g, (int)(path.getSolution().get(i).getX()) + offsetx, (int)(path.getSolution().get(i).getY()) + offsety, 5, "black");
                if(i+1<path.getSolution().size()){
                    g.drawLine((int)(path.getSolution().get(i).getX()) + offsetx, (int)(path.getSolution().get(i).getY()) +offsety, (int)(path.getSolution().get(i+1).getX())+offsetx, (int)(path.getSolution().get(i+1).getY())+offsety);
                }

            }
        }
    }

    private void drawCircle(Graphics g, int x, int y, int radius, String color) {
        if(color.equals("red")){
            g.setColor(Color.RED);
        }
        else{
            g.setColor(Color.BLACK);
        }
        g.fillOval(x-radius, y-radius, radius*2, radius*2);
    }
}
