import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.*;


import javax.swing.JFrame;


public class EvolutionaryAlgorithm {

    public static PhysicsSystem phy = new PhysicsSystem();
    public static Wind wind = new Wind();
    private static Point start = new Point(150, 20);
    private static Point finish = new Point(150, 400);
    private static int populationSize = 100;
    public JFrame f;
    public Gui panel = new Gui();
    private Random rand = new Random();
    private ArrayList<Solution> population = new ArrayList<Solution>();

    public void runAlgorithm() throws Exception {

        wind.to = new Point(50, 100);
        wind.from = new Point(50, 10);
        //	wind.setWindSpeed(4);

        wind.generateWindSpeed();
        //	wind.generatePoints();
        wind.setWindAngle(phy.getAngleFromPoint(wind.from, wind.to));

        //System.out.println("The wind angle is: " + wind.getWindAngle());
        // System.out.println("The wind speed is: " + wind.getWindSpeed());

        initialisePopulation();
        // System.out.println(population.size());
        // System.out.println(this.getBestSol().getSolution().size() + " Time: " + this.calculateTime((this.getBestSol())));

        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(panel);
        f.setSize(1080, 720);
        // Show the frame.
        f.setVisible(true);

        for (Solution s : population) {
            this.showGui(s, wind);
/*
                System.out.println(s.getSolutionTime() + "\n" + "==================================================");
                for(int i=0; i<s.getSolution().size()-1;i++) {

                        double angleBetweenPoints = phy.getAngleFromPoint(s.getSolution().get(i), s.getSolution().get(i + 1));
                        double boatWindAngle = angleBetweenPoints;

                        if(boatWindAngle > 180){
                            boatWindAngle = 360 - boatWindAngle;
                        }
                        System.out.print(boatWindAngle + " || ");


                }
*/

            System.out.println((Math.atan2((wind.to.x - wind.from.x), (wind.to.y - wind.from.y)) * 180 / Math.PI));

            for (int i = 0; i < s.getSolution().size() - 1; i++) {

                double angleBetweenPoints = phy.getAngleFromPoint(s.getSolution().get(i), s.getSolution().get(i + 1));
               // System.out.println("angle between points = " + (angleBetweenPoints));
               // System.out.println("angleBetweenPoints - windAngle = " + (angleBetweenPoints-wind.getWindAngle()) + "\n\n");

                double angle2 = (Math.atan2((s.getSolution().get(i + 1).x - s.getSolution().get(i).x), (s.getSolution().get(i + 1).y - s.getSolution().get(i).y)) * 180 / Math.PI);
                System.out.println("secondPoint.x > firstPoint.x = " + angle2);
                //System.out.println("angle - windAngle = " + (angle2 + wind.getWindAngle()) + "\n\n");
                /*
                double angle1 = (Math.atan2((s.getSolution().get(i).x - s.getSolution().get(i + 1).x), (s.getSolution().get(i).y - s.getSolution().get(i + 1).y)) * 180 / Math.PI);
                System.out.println("secondPoint.x < firstPoint.x = " + angle1 + "\n\n");
                System.out.println("angle - windAngle = " + (angle1 + wind.getWindAngle()));
                */

            }
            Thread.sleep(100000000);
        }


        for (int x = 0; x < 20000; x++) {
            Solution parent1 = findParent();
            Solution parent2 = findParent();
            Solution child = crossOver(parent1, parent2);
            this.showGui(child, wind);

            //this.checkSolution(child);
            if (this.calculateTime(child) < this.calculateTime(this.getWorstSol())) {
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

        int randMutation = rand.nextInt(20);
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
            double angleBetweenPoints = 0;
            double windAngle = wind.getWindAngle();
            double boatWindAngle = 0;


            angleBetweenPoints = phy.getAngleFromPoint(solution.getSolution().get(i), solution.getSolution().get(i + 1));
            boatWindAngle = angleBetweenPoints - windAngle;


            if (boatWindAngle < 0) {
                boatWindAngle = Math.abs(boatWindAngle);
            }

            if (boatWindAngle > 180) {
                boatWindAngle = 360 - boatWindAngle;
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

            int max = 14;
            int min = 1;
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

    public void showGui(Solution solution, Wind wind) {

        panel.repaintSolution(solution, wind);
        panel.repaint();

    }

}
