package com.bennyhuo.tieguanyinsimple.runtime.utils;

import android.os.Bundle;

public class BundleUtils {
    public static <T> T get(Bundle bundle, String key) {
        return (T) bundle.get(key);
    }

    public static <T> T get(Bundle bundle, String key, Object defaultValue) {
        Object object = bundle.get(key);
        if(object == null){
            object = defaultValue;
        }
        return (T) object;
    }
}
