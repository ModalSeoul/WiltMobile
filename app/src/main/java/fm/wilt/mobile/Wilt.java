package fm.wilt.mobile;

import android.app.Application;
import android.content.Context;

import fm.wilt.mobile.jwilt.Api;

/**
 * Created by ですですです on 1/20/2017.
 */

public class Wilt extends Application {

    private static Wilt instance;
    private static String authToken;

    public static Wilt getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
