import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.*;


import javax.swing.JFrame;


public class EvolutionaryAlgorithm {


    private Point point1 = new Point(100, 100);
    private Point point2 = new Point(100, 700);
    private Point point3 = new Point(700,700);
    private Point point4 = new Point(1300,500);
    private Point point5 = new Point(1300,100);
    private Point point6 = new Point(500,100);

    private Point start = new Point();
    private Point finish = new Point();

    private ArrayList<Point> pointsToFollow = new ArrayList<Point>();

    private static int populationSize = 100;
    private static int numberOfEvolutions = 20000;
    private static int mutationChance = 20;
    private static int maxNumberOfTurns = 14;
    private static int minNumberOfTurns = 5;

    public static PhysicsSystem phy = new PhysicsSystem();
    public static Wind wind = new Wind();
    public JFrame f;
    public Gui panel = new Gui();
    private Random rand = new Random();
    private ArrayList<Solution> population = new ArrayList<Solution>();

    public void runAlgorithm() throws Exception {

        pointsToFollow.add(point1);
        pointsToFollow.add(point2);
        pointsToFollow.add(point3);
        pointsToFollow.add(point4);
        pointsToFollow.add(point5);
        pointsToFollow.add(point6);

        wind.from = new Point(50, 10);
        wind.to = new Point(50, 100);
        wind.generateWindSpeed();
        wind.setWindAngle(phy.getAngleFromPoint(wind.from, wind.to));



        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(panel);
       // f.setSize(1600, 900);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);

        for(int i=0; i<pointsToFollow.size()-1;i++) {

            start = pointsToFollow.get(i);
            finish = pointsToFollow.get(i+1);

            population.clear();
            initialisePopulation();

            for (int x = 0; x < numberOfEvolutions; x++) {
                panel.paintPoints(pointsToFollow);
                Solution parent1 = findParent();
                Solution parent2 = findParent();
                Solution child = crossOver(parent1, parent2);
                //this.checkSolution(child);
                if (this.calculateTime(child) < this.calculateTime(this.getWorstSol()) && this.checkSolution(child)) {
                    //System.out.println(this.calculateTime(child));
                    population.add(child);
                    this.showGui(child, wind, this.getBestSol(), false);
                    //this.showGui(child,wind);
                    Thread.sleep(10);
                    population.remove(this.getWorstSol());
                }
            }
            this.showGui(this.getBestSol(), wind, this.getBestSol(), true);
        }
        System.out.println("FINISHED");
    }

    private Solution findParent() {
        Solution parent = null;

        this.sortPopulation();

        for (Solution sol : population) {
            //System.out.println(sol.getSolutionTime() + " " + sol.getSolution().size());
        }

        int parentID = rand.nextInt(99);
        parent = population.get(parentID);

        return parent;
    }

    private Solution crossOver(Solution parent1, Solution parent2) {
        Solution child = new Solution();
        int points = 0;

        if (parent1.getSolution().size() > parent2.getSolution().size()) {
            points = parent1.getSolution().size();
        } else points = parent2.getSolution().size();
        child.addPoint(start);

        for (int i = 1; i < (points - 1); i++) {
            int chance = rand.nextInt(2);
            if (chance == 1 && parent1.getSolution().size() > i) {
                child.addPoint(parent1.getSolution().get(i));
            } else if (parent2.getSolution().size() > i) {
                child.addPoint(parent2.getSolution().get(i));
            } else {
                child.addPoint(parent1.getSolution().get(i));
            }
        }

        child.setSolutionTime(this.calculateTime(child));
        child.addPoint(finish);

        return child;
    }


    private Solution mutate(Solution sol) {
        Solution solution = sol;

        int randMutation = rand.nextInt(mutationChance);
        int randDirection = rand.nextInt(4);
        int randDistance = rand.nextInt(20);

        if (randMutation == 1) {
            int randPoint = rand.nextInt(solution.getSolution().size());
            if (randDirection == 0) {
                if (solution.getSolution().get(randPoint) != start && solution.getSolution().get(randPoint) != finish) {
                    double locationX = solution.getSolution().get(randPoint).getX();
                    double locationY = solution.getSolution().get(randPoint).getY();
                    solution.getSolution().get(randPoint).setLocation(locationX - randDistance, locationY);
                }
            } else if (randDirection == 1) {
                if (solution.getSolution().get(randPoint) != start && solution.getSolution().get(randPoint) != finish) {
                    double locationX = solution.getSolution().get(randPoint).getX();
                    double locationY = solution.getSolution().get(randPoint).getY();
                    solution.getSolution().get(randPoint).setLocation(locationX + randDistance, locationY);
                }
            } else if (randDirection == 2) {
                if (solution.getSolution().get(randPoint) != start && solution.getSolution().get(randPoint) != finish) {
                    double locationX = solution.getSolution().get(randPoint).getX();
                    double locationY = solution.getSolution().get(randPoint).getY();
                    solution.getSolution().get(randPoint).setLocation(locationX, locationY - randDistance);
                }
            } else if (randDirection == 3) {
                if (solution.getSolution().get(randPoint) != start && solution.getSolution().get(randPoint) != finish) {
                    double locationX = solution.getSolution().get(randPoint).getX();
                    double locationY = solution.getSolution().get(randPoint).getY();
                    solution.getSolution().get(randPoint).setLocation(locationX, locationY + randDistance);
                }
            }
        }

        return solution;
    }


    public boolean checkSolution(Solution solution) {

        boolean correct = true;

        for (int i = 0; i < solution.getSolution().size() - 1; i++) {
            double boatWindAngle = 0;

            boatWindAngle = phy.getBoatWindAngle(solution.getSolution().get(i), solution.getSolution().get(i + 1));


            if (boatWindAngle < 0) {
                boatWindAngle = Math.abs(boatWindAngle);
            }

            if (boatWindAngle < 32) {
                correct = false;
            }
            //  System.out.println(boatWindAngle);
        }
        if (correct == false) {
            //  System.out.println("Deleted");
        }
        //  System.out.println("==========================================");
        return correct;

    }


    private void sortPopulation() {
        Collections.sort(population, new Comparator<Solution>() {
            @Override
            public int compare(final Solution sol1, final Solution sol2) {
                if (sol1.getSolutionTime() > sol2.getSolutionTime()) {
                    return 1;
                } else if (sol1.getSolutionTime() < sol2.getSolutionTime()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private Solution getWorstSol() {
        Solution worst = null;

        for (Solution sol : population) {
            if (worst == null || this.calculateTime(worst) < this.calculateTime(sol)) {
                worst = sol;
            }
        }
        return worst;
    }

    private Solution getBestSol() {
        Solution best = null;

        for (Solution sol : population) {
            if (best == null || this.calculateTime(best) > this.calculateTime(sol)) {
                best = sol;
            }
        }

        return best;
    }

    private void initialisePopulation() {
        //Create 100 solutions and add them to the solution
        for (int p = 0; p < populationSize; p++) {
            Solution path = new Solution();

            Point midpoint = new Point();
            int radius = (int) (Math.round(start.distance(finish) - (start.distance(finish) / 2)) + (start.distance(finish) / 3));

            int x = (start.x + finish.x) / 2;
            int y = (start.y + finish.y) / 2;

            midpoint.x = x;
            midpoint.y = y;

            int max = maxNumberOfTurns;
            int min = minNumberOfTurns;
            int numberOfTurns = rand.nextInt((max - min) + 1) + min;

            path.addPoint(start);

            for (int i = 0; i < numberOfTurns; i++) {
                Point randomPoint = new Point();


                double angle = Math.random() * Math.PI * 2;
                double radiusRandom = rand.nextDouble() * radius;
                double xRandom = midpoint.x + radiusRandom * Math.cos(angle);
                double yRandom = midpoint.y + radiusRandom * Math.sin(angle);

                randomPoint.x = (int) xRandom;
                randomPoint.y = (int) yRandom;

                path.addPoint(randomPoint);
            }

            path.addPoint(finish);

            if (this.checkSolution(path) == true) {
                population.add(path);
            } else {
                p--;
            }
            path.setSolutionTime(this.calculateTime(path));

        }
    }


    private double calculateTime(Solution sol) {
        double soulutionTime = 0;

        for (int x = 0; x < sol.getSolution().size() - 1; x++) {

            soulutionTime += phy.timeBetweenPoints(sol.getSolution().get(x), sol.getSolution().get(x + 1), wind);

        }
        return soulutionTime;
    }

    public void showGui(Solution solution, Wind wind, Solution finished, boolean last) {
        panel.repaintSolution(solution, wind, finished, last);
        panel.repaint();
    }

}
