package android.example.com.squawker.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sudharti on 11/14/17.
 */

public class FirebaseIdService extends FirebaseInstanceIdService {

    private static final String TAG = FirebaseInstanceId.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Token = " + token);
    }
}
