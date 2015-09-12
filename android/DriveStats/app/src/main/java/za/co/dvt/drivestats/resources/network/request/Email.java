package za.co.dvt.drivestats.resources.network.request;

/**
 * Created by Nicholas on 2015-09-10.
 */
public class Email implements Request {

    private final String email;

    public Email(String email) {
        this.email = email;
    }

    @Override
    public String getParameters() {
        return email;
    }

}
