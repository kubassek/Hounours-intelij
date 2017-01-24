import java.util.*;
import java.awt.*;

public class Solution{

    Random rand = new Random();
    private ArrayList<Point> solution = new ArrayList<Point>();
    private double solutionTime = 0;

    public Solution(){
    }

    public ArrayList<Point> getSolution() {
        return solution;
    }

    public void setSolution(ArrayList<Point> solution) {
        this.solution = solution;
    }

    public void addPoint(Point point){
        this.solution.add(point);
    }

    public double getSolutionTime() {
        return solutionTime;
    }

    public void setSolutionTime(double solutionTime) {
        this.solutionTime = solutionTime;
    }

}
