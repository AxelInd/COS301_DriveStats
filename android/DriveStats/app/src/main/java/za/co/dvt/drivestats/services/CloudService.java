package za.co.dvt.drivestats.services;

import za.co.dvt.drivestats.resources.datasending.CallBack;

/**
 * Created by Nicholas on 2015-08-10.
 */
public interface CloudService {

    void getUserId(String email, CallBack callBack);

}
