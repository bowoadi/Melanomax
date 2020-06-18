package org.tensorflow.demo;

import android.app.Application;
import android.content.Context;
import org.greenrobot.eventbus.EventBus;


/**
 * Created by adhit on 03/01/2018.
 */

public class Melanomax extends Application  {
    private static Context sContext;
    public static Context getContext() {
        return sContext;
    }
    public static Melanomax instance;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        instance = this;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

}
