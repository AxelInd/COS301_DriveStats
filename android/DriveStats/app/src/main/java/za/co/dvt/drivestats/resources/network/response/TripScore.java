package za.co.dvt.drivestats.resources.network.response;

/**
 * Created by Nicholas on 2015-09-10.
 */
public class TripScore implements Response {

    private String addTripResult;

    public TripScore() {
    }

    public TripScore(String addTripResult) {
        this.addTripResult = addTripResult;
    }

    public String getAddTripResult() {
        return addTripResult;
    }

    public void setAddTripResult(Double addTripResult) {
        this.addTripResult = Double.toString(addTripResult);
    }
}
