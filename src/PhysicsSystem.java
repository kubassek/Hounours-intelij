import java.util.*;
import java.awt.*;

public class PhysicsSystem {

    public ArrayList<Integer> windSpeed = new ArrayList<Integer>();
    public ArrayList<Integer> windDirection = new ArrayList<Integer>();
    public ArrayList<Double[]> boatSpeed = new ArrayList<Double[]>();
    public Point fromPoint;
    public Point toPoint;

    public PhysicsSystem(){
        windSpeed.addAll(Arrays.asList(4,5,6,7,8,9,10,12,14,16,20,25));
        windDirection.addAll(Arrays.asList(32,36,40,45,52,60,70,75,80,90,100,110,120,130,135,140,150,160,165,170,180));

        Double[] speed32 = {2.927,3.688,4.395,5.030,5.584,6.037,6.392,6.888,7.184,7.385,7.664,7.887};
        Double[] speed36 = {3.325,4.171,4.916,5.579,6.134,6.593,6.910,7.315,7.552,7.726,7.976,8.186};
        Double[] speed40 = {3.684,4.586,5.361,6.028,6.598,7.018,7.284,7.615,7.822,7.981,8.281,8.427};
        Double[] speed45 = {4.077,5.013,5.812,6.495,7.031,7.392,7.615,7.893,8.082,8.234,8.469,8.688};
        Double[] speed52 = {4.514,5.485,6.293,6.960,7.418,7.732,7.939,8.186,8.370,8.522,8.771,9.023};
        Double[] speed60 = {4.870,5.852,6.673,7.268,7.674,7.969,8.186,8.441,8.634,8.797,9.080,9.365};
        Double[] speed70 = {5.125,6.142,6.969,7.451,7.830,8.122,8.359,8.699,8.886,9.099,9.417,9.736};
        Double[] speed75 = {5.339,6.393,7.160,7.560,7.856,8.149,8.394,8.780,9.033,9.234,9.566,9.920};
        Double[] speed80 = {5.527,6.581,7.294,7.677,7.934,8.146,8.396,8.819,9.144,9.336,9.707,10.111};
        Double[] speed90 = {5.756,6.185,7.482,7.859,8.114,8.301,8.461,8.753,9.172,9.518,9.947,10.475};
        Double[] speed100 = {5.835,6.903,7.581,7.972,8.241,8.444,8.615,8.923,9.204,9.447,10.045,10.736};
        Double[] speed110 = {5.759,6.834,7.553,8.010,8.322,8.551,8.740,9.084,9.387,9.655,10.194,10.913};
        Double[] speed120 = {5.505,6.565,7.348,7.852,8.221,8.526,8.791,9.203,9.547,9.865,10.563,11.526};
        Double[] speed130 = {5.000,6.023,6.858,7.453,7.877,8.226,8.537,9.134,9.657,10.096,10.979,12.277};
        Double[] speed135 = {4.618,5.618,6.473,7.154,7.630,8.007,8.330,8.929,9.524,10.109,11.221,12.680};
        Double[] speed140 = {4.180,5.152,5.997,6.750,7.318,7.741,8.082,8.677,9.278,9.880,11.376,13.115};
        Double[] speed150 = {3.338,4.191,4.989,5.719,6.386,6.978,7.437,8.122,8.689,9.274,10.642,13.290};
        Double[] speed160 = {2.743,3.451,4.150,4.824,5.465,6.066,6.641,7.554,8.207,8.770,9.953,12.184};
        Double[] speed165 = {2.578,3.243,3.904,4.549,5.171,5.762,6.325,7.307,8.007,8.572,9.692,11.579};
        Double[] speed170 = {2.451,3.082,3.712,4.332,4.933,5.511,6.060,7.068,7.814,8.390,9.472,11.214};
        Double[] speed180 = {2.262,2.845,3.428,4.008,4.574,5.124,5.652,6.468,7.465,8.077,9.101,10.537};

        boatSpeed.add(speed32);
        boatSpeed.add(speed36);
        boatSpeed.add(speed40);
        boatSpeed.add(speed45);
        boatSpeed.add(speed52);
        boatSpeed.add(speed60);
        boatSpeed.add(speed70);
        boatSpeed.add(speed75);
        boatSpeed.add(speed80);
        boatSpeed.add(speed90);
        boatSpeed.add(speed100);
        boatSpeed.add(speed110);
        boatSpeed.add(speed120);
        boatSpeed.add(speed130);
        boatSpeed.add(speed135);
        boatSpeed.add(speed140);
        boatSpeed.add(speed150);
        boatSpeed.add(speed160);
        boatSpeed.add(speed165);
        boatSpeed.add(speed170);
        boatSpeed.add(speed180);
        boatSpeed.add(speed32);
        boatSpeed.add(speed32);
        boatSpeed.add(speed32);

        //Print out lists in table

        String wind = "\t";

        for(int i=0;i<windSpeed.size();i++){
            wind += windSpeed.get(i).toString() + "\t";
        }

        //System.out.println(wind);

        for(int j=0;j<21;j++){
            String speed = "";
            for(int i=0;i<12;i++){
                speed += boatSpeed.get(j)[i] + "\t";
            }
            //System.out.println(windDirection.get(j) + "\t" + speed);
        }


    }

    //111Get index of closest wind speed
    public int getWindSpeedIndex(int speed){
        int index = windSpeed.indexOf(speed);
        return index;
    }

    //Get index of closest wind direction
    public int getDirectionIndex(double windAngle){
        int index = 0;
        double smallestDifference = -1;

        for(int i=0; i<windDirection.size();i++){
            //Initialise a smallest difference
            if(smallestDifference == -1){
                smallestDifference = windDirection.get(20) - windAngle;
                index = 20;
            }
            else{
                double difference = windDirection.get(20-i) - windAngle;
                //Remove negative values
                if(difference < 0){
                    difference = difference * -1;
                }
                //If new smallest value is found make it the new index
                if(difference < smallestDifference){
                    smallestDifference = difference;
                    index = 20-i;
                }
            }
        }
        return index;
    }

    public double getBoatSpeed(int windSpeed, double direction){
        double boatSpeedResult = 0;
        int i = getWindSpeedIndex(windSpeed);
        int j = getDirectionIndex(direction);

        boatSpeedResult = boatSpeed.get(j)[i];

        return boatSpeedResult;
    }


    //Here is a code snippet from my Android Gesture library. It works and is fully tested.
    public double getAngleFromPoint(Point firstPoint, Point secondPoint) {

        if((secondPoint.x > firstPoint.x)) {//above 0 to 180 degrees

            return (Math.atan2((secondPoint.x - firstPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);

        }
        else if((secondPoint.x < firstPoint.x)) {//above 180 degrees to 360/0

            return 360 - (Math.atan2((firstPoint.x - secondPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);

        }//End if((secondPoint.x > firstPoint.x) && (secondPoint.y <= firstPoint.y))

        return Math.atan2(0 ,0);

    }//End public float getAngleFromPoint(Point firstPoint, Point secondPoint)


    public double timeBetweenPoints(Point from, Point to, Wind randomWind){
        double time = 0;
        double distance = 0;
        double angleBetweenPoints = 0;
        double windAngle = randomWind.getWindAngle();
        double boatWindAngle = 0;

        distance = from.distance(to);
        angleBetweenPoints = this.getAngleFromPoint(from, to);
        boatWindAngle = angleBetweenPoints - windAngle;

        if(boatWindAngle < 0){
            boatWindAngle = Math.abs(boatWindAngle);
        }

        if(boatWindAngle > 180){
            boatWindAngle = 360 - boatWindAngle;
        }

        time = distance / this.getBoatSpeed(randomWind.getWindSpeed(), boatWindAngle);
        return time;
    }

}
