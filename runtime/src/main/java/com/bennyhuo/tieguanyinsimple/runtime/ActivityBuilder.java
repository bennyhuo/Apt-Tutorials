package com.bennyhuo.tieguanyinsimple.runtime;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ActivityBuilder {

    public final static String BUILDER_NAME_POSIX = "Builder";

    public final static ActivityBuilder INSTANCE = new ActivityBuilder();

    private Application application;

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            performInject(activity, savedInstanceState);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            performSaveState(activity, outState);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    private void performInject(Activity activity, Bundle savedInstanceState){
        try {
            Class.forName(activity.getClass().getName() + BUILDER_NAME_POSIX).getDeclaredMethod("inject", Activity.class, Bundle.class).invoke(null, activity, savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performSaveState(Activity activity, Bundle outState){
        try {
            Class.forName(activity.getClass().getName() + BUILDER_NAME_POSIX).getDeclaredMethod("saveState", Activity.class, Bundle.class).invoke(null, activity, outState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(Context context){
        if(this.application != null) return;
        this.application = (Application) context.getApplicationContext();
        this.application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public void startActivity(Context context, Intent intent){
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

}
