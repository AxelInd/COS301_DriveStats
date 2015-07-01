package za.co.dvt.drivestats.threadmanagment.sensorthread;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class SensorState {

    private Float maxXDeflection;

    private Float maxYDeflection;

    private Float maxZDeflection;

    private double maxSpeed;

    private String location;

    private Boolean xLock = new Boolean(true);
    private Boolean yLock = new Boolean(true);
    private Boolean zLock = new Boolean(true);
    private Boolean speedLock = new Boolean(true);
    private Boolean locationLock = new Boolean(true);

    private static final SensorState Instance = new SensorState();

    private SensorState() {}

    public static SensorState getInstance() {
        return Instance;
    }

    private float maxDeflection(float current, float compare) {
        return Math.max(Math.abs(current), Math.abs(compare));
    }

    public float getMaxXDeflection() {
        synchronized (xLock) {
            float value = maxXDeflection;
            maxXDeflection = 0f;
            return value;
        }
    }

    public void setMaxXDeflection(float maxXDeflection) {
        synchronized (xLock) {
            this.maxXDeflection = maxDeflection(this.maxXDeflection, maxXDeflection);
        }
    }

    public float getMaxYDeflection() {
        synchronized (yLock) {
            return maxYDeflection;
        }
    }

    public void setMaxYDeflection(float maxYDeflection) {
        synchronized (yLock) {
            this.maxYDeflection = maxDeflection(this.maxYDeflection, maxYDeflection);
        }
    }

    public float getMaxZDeflection() {
        synchronized (zLock) {
            return maxZDeflection;
        }
    }

    public void setMaxZDeflection(float maxZDeflection) {
        synchronized (zLock) {
            this.maxZDeflection = maxDeflection(this.maxZDeflection, maxZDeflection);
        }
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getLocation() {
        //TODO: reformat the location to conform with standards
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
