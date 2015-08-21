package za.co.dvt.drivestats.services;

import za.co.dvt.drivestats.resources.datasending.AsyncRequest;
import za.co.dvt.drivestats.resources.datasending.AyncRequestFactory;
import za.co.dvt.drivestats.resources.datasending.CallBack;
import za.co.dvt.drivestats.resources.datasending.SoapMethod;

/**
 * Created by Nicholas on 2015-08-10.
 */
public class SoapCloudService implements CloudService {

    @Override
    public void getUserId(String email, CallBack callBack) {
        //TODO: Implement
        AsyncRequest request = AyncRequestFactory.createRequest(SoapMethod.GET_USER_ID);
        request.setProperty("email", email);
        request.send(callBack);
    }


}
