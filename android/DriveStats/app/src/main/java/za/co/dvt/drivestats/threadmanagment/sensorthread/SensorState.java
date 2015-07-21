package za.co.dvt.drivestats.threadmanagment.sensorthread;


public class SensorState {

    private volatile float maxXDeflection = 0f;

    private volatile float maxYDeflection = 0f;

    private volatile float maxZDeflection = 0f;

    private volatile double maxSpeed = 0d;

    private volatile double[] location = {0d, 0d};

    private static final SensorState Instance = new SensorState();

    private SensorState() {
    }

    public static SensorState getInstance() {
        return Instance;
    }

    private float maxDeflection(float current, float compare) {
        if (Math.abs(current) > Math.abs(compare)) return current;
        else return compare;
    }

    public float getCorrectedMaxXDeflection() {
        float latestReading = roundAccelerometerTwoDecimals(maxXDeflection);
        maxXDeflection = 0f;
        return latestReading;
    }

    public void setMaxXDeflection(float maxXDeflection) {
        this.maxXDeflection = maxDeflection(this.maxXDeflection, maxXDeflection);
    }

    public float getCorrectedMaxYDeflection() {
        float latestReading = roundAccelerometerTwoDecimals(maxYDeflection);
        maxYDeflection = 0f;
        return latestReading;
    }

    public void setMaxYDeflection(float maxYDeflection) {
        this.maxYDeflection = maxDeflection(this.maxYDeflection, maxYDeflection);
    }

    public float getCorrectedMaxZDeflection() {
        float latestReading = roundAccelerometerTwoDecimals(maxZDeflection);
        maxZDeflection = 0f;
        return latestReading;
    }

    public void setMaxZDeflection(float maxZDeflection) {
        this.maxZDeflection = maxDeflection(this.maxZDeflection, maxZDeflection);
    }

    private float roundAccelerometerTwoDecimals(float number) {
        return Math.round(number * 1000f) / 1000f;
    }

    public double getSpeed() {
        //TODO: Convert from M/s to KPH
        return Math.round(maxSpeed * 100) / 100;
    }

    public void setSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double[] getLocation() {
        //TODO: reformat the location to conform with standards
        return location;
    }

    public void setLocation(double... location) {
        this.location = location;
    }
}
