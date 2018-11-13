package jcarklin.co.za.bakingrecipes;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class BakingApplication extends Application {

    //ToDo Remove
    public static boolean test = true;
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}