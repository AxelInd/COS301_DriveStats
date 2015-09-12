package za.co.dvt.drivestats.resources.network.response;

/**
 * Created by Nicholas on 2015-09-10.
 */
public class UserId implements Response {

    private String loginResult;

    public UserId() {
    }

    public UserId(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public Long getUserId() {
        return Long.parseLong(loginResult);
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }
}
