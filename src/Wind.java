import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Wind {

    public Point from = new Point();
    public Point to = new Point();
    private int windSpeed;

    private double windAngle = 0;

    Random rand = new Random();
    public ArrayList<Integer> windSpeedList = new ArrayList<Integer>();

    public Wind(){
        windSpeedList.addAll(Arrays.asList(4,5,6,7,8,9,10,12,14,16,20,25));
    }

    public void generatePoints(){
        from.x = rand.nextInt(50);
        from.y = rand.nextInt(50);
        to.x = rand.nextInt(50);
        to.y = rand.nextInt(50);
    }

    public void generateWindSpeed(){
        windSpeed = windSpeedList.get(rand.nextInt(12));
    }

    public double getWindAngle() {
        return windAngle;
    }

    public void setWindAngle(double windAngle) {

        if(windAngle < 0){
            windAngle = Math.abs(windAngle);
        }

        if(windAngle > 360){
            windAngle = windAngle - 360;
        }

        this.windAngle = windAngle;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

}
