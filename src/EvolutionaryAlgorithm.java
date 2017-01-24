import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.io.*;

import javax.swing.JFrame;


public class EvolutionaryAlgorithm{

    private Random rand = new Random();
    private static Point start = new Point(150, 20);
    private static Point finish = new Point(150, 400);
    private static int populationSize = 100;
    private ArrayList<Solution> population = new ArrayList<Solution>();
    public static PhysicsSystem phy = new PhysicsSystem();
    public static Wind wind = new Wind();
    public JFrame f;
    public Gui panel = new Gui();

    public void runAlgorithm() throws Exception {

        wind.to = new Point(50,100);
        wind.from = new Point(50,10);
        //	wind.setWindSpeed(4);

        wind.generateWindSpeed();
        //	wind.generatePoints();
        wind.setWindAngle(phy.getAngleFromPoint(wind.from, wind.to));

        System.out.println("The wind angle is: " + wind.getWindAngle());
        System.out.println("The wind speed is: " + wind.getWindSpeed());

        initialisePopulation();
        System.out.println(population.size());
        System.out.println(this.getBestSol().getSolution().size() + " Time: " + this.calculateTime((this.getBestSol())));

        f = new JFrame ();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add (panel);
        f.setSize(1080, 720);
        // Show the frame.
        f.setVisible (true);

        for(Solution s:population){
            //	this.showGui(s, wind);
            //	Thread.sleep(100);
        }


        for(int x=0; x<20000000;x++){
            Solution parent1 = findParent();
            Solution parent2 = findParent();
            Solution child = crossOver(parent1, parent2);
            this.showGui(child, wind);
            //this.checkSolution(child);
            if(this.calculateTime(child)<this.calculateTime(this.getWorstSol())){
                System.out.println(this.calculateTime(child));
                population.add(child);

                //this.showGui(child,wind);
                Thread.sleep(10);
                population.remove(this.getWorstSol());
            }
        }
        System.out.println("FINISHED :" + this.calculateTime(this.getBestSol()) + " VS " + this.calculateTime(this.getBestSol()));
        this.showGui(this.getBestSol(), wind);
        this.showGui(this.getBestSol(), wind);
    }

    private Solution findParent(){
        Solution parent = null;

        this.sortPopulation();

        for(Solution sol: population){
            //System.out.println(sol.getSolutionTime() + " " + sol.getSolution().size());
        }

        int parentID = rand.nextInt(99);
        parent = population.get(parentID);

        return parent;
    }

    private Solution crossOver(Solution parent1, Solution parent2){
        Solution child = new Solution();
        int points = 0;

        if(parent1.getSolution().size() > parent2.getSolution().size()){
            points = parent1.getSolution().size();
        }else points = parent2.getSolution().size();
        child.addPoint(start);

        for(int i=1; i<(points-1); i++){
            int chance = rand.nextInt(2);
            if(chance == 1 && parent1.getSolution().size()>i){
                child.addPoint(parent1.getSolution().get(i));
            } else if (parent2.getSolution().size()>i){
                child.addPoint(parent2.getSolution().get(i));
            } else{child.addPoint(parent1.getSolution().get(i));}
        }

        child.setSolutionTime(this.calculateTime(child));
        child.addPoint(finish);

        return child;
    }


    private Solution mutate(Solution sol){
        Solution solution = sol;

        int randMutation = rand.nextInt(20);
        int randDirection = rand.nextInt(4);
        int randDistance = rand.nextInt(20);

        if(randMutation == 1){
            int randPoint = rand.nextInt(solution.getSolution().size());
            if(randDirection == 0){
                if(solution.getSolution().get(randPoint) != start && solution.getSolution().get(randPoint) != finish){
                    double locationX = solution.getSolution().get(randPoint).getX();
                    double locationY = solution.getSolution().get(randPoint).getY();
                    solution.getSolution().get(randPoint).setLocation(locationX-randDistance, locationY);
                }
            }else if(randDirection == 1){
                if(solution.getSolution().get(randPoint) != start && solution.getSolution().get(randPoint) != finish){
                    double locationX = solution.getSolution().get(randPoint).getX();
                    double locationY = solution.getSolution().get(randPoint).getY();
                    solution.getSolution().get(randPoint).setLocation(locationX+randDistance, locationY);
                }
            }else if(randDirection == 2){
                if(solution.getSolution().get(randPoint) != start && solution.getSolution().get(randPoint) != finish){
                    double locationX = solution.getSolution().get(randPoint).getX();
                    double locationY = solution.getSolution().get(randPoint).getY();
                    solution.getSolution().get(randPoint).setLocation(locationX, locationY - randDistance);
                }
            }else if(randDirection == 3){
                if(solution.getSolution().get(randPoint) != start && solution.getSolution().get(randPoint) != finish){
                    double locationX = solution.getSolution().get(randPoint).getX();
                    double locationY = solution.getSolution().get(randPoint).getY();
                    solution.getSolution().get(randPoint).setLocation(locationX, locationY + randDistance);
                }
            }
        }

        return solution;
    }


    public boolean checkSolution(Solution solution){

        boolean correct = true;

        for(int i=0; i<solution.getSolution().size()-1;i++){
            double angleBetweenPoints = 0;
            double windAngle = wind.getWindAngle();
            double boatWindAngle = 0;

            angleBetweenPoints = phy.getAngleFromPoint(solution.getSolution().get(i), solution.getSolution().get(i+1));
            boatWindAngle = angleBetweenPoints - windAngle;

            if(boatWindAngle < 0){
                boatWindAngle = Math.abs(boatWindAngle);
            }

            if(boatWindAngle > 180){
                boatWindAngle = 360 - boatWindAngle;
            }

            if(boatWindAngle<32){
                correct = false;
            }
            System.out.println(boatWindAngle);
        }
        if(correct == false){
            System.out.println("Deleted");
        }
        System.out.println("==========================================");
        return correct;

    }


    private void sortPopulation(){
        Collections.sort(population, new Comparator<Solution>() {
            @Override public int compare(final Solution sol1, final Solution sol2) {
                if (sol1.getSolutionTime() > sol2.getSolutionTime()) {
                    return 1;
                } else if (sol1.getSolutionTime() < sol2.getSolutionTime()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private Solution getWorstSol(){
        Solution worst = null;

        for(Solution sol : population){
            if(worst == null || this.calculateTime(worst) < this.calculateTime(sol)){
                worst = sol;
            }
        }
        return worst;
    }

    private Solution getBestSol(){
        Solution best = null;

        for(Solution sol : population){
            if(best == null || this.calculateTime(best) > this.calculateTime(sol)){
                best = sol;
            }
        }

        return best;
    }

    private void initialisePopulation(){
        //Create 100 solutions and add them to the solution
        for(int p=0; p<populationSize; p++){
            Solution path = new Solution();

            Point midpoing;

            

            int max = 14;
            int min = 1;
            int numberOfTurns = rand.nextInt((max - min) + 1) + min;

            path.addPoint(start);

            for(int i=0; i<numberOfTurns; i++ ){
                Point randomPoint = new Point();
                randomPoint.x = rand.nextInt(((start.x + 400) - (start.x - 400)) + 1) + (start.x - 400);
                randomPoint.y = rand.nextInt(((finish.y) - (start.y)) + 1) + (start.y);
                path.addPoint(randomPoint);
            }

            path.addPoint(finish);

            if(this.checkSolution(path)==true){
                population.add(path);
            }else{
                p--;
            }
            path.setSolutionTime(this.calculateTime(path));

        }
    }



    private double calculateTime(Solution sol){
        double soulutionTime = 0;

        for(int x=0;x<sol.getSolution().size()-1;x++){

            soulutionTime += phy.timeBetweenPoints(sol.getSolution().get(x), sol.getSolution().get(x+1), wind);

        }
        return soulutionTime;
    }

    public void showGui(Solution solution, Wind wind){

        panel.repaintSolution(solution, wind);
        panel.repaint();

    }

}