package za.co.dvt.drivestats.threadmanagment.sensorthread;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class SensorState {

    private Float maxXDeflection = new Float(0);

    private Float maxYDeflection = new Float(0);

    private Float maxZDeflection = new Float(0);

    private double maxSpeed;

    private double[] location;

    private byte[] xLock = new byte[0];
    private byte[] yLock = new byte[0];
    private byte[] zLock = new byte[0];
    private byte[] speedLock = new byte[0];
    private byte[] locationLock = new byte[0];

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

    public double getSpeed() {
        synchronized (speedLock) {
            return maxSpeed;
        }
    }

    public void setSpeed(double maxSpeed) {
        synchronized (speedLock) {
            this.maxSpeed = maxSpeed;
        }
    }

    public double[] getLocation() {
        synchronized (locationLock) {
            //TODO: reformat the location to conform with standards
            return location;
        }
    }

    public void setLocation(double... location) {
        synchronized (locationLock) {
            this.location = location;
        }
    }
}
