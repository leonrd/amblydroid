package com.amblydroid.modules;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;

public class AmblyModule extends ReactContextBaseJavaModule {

    public final static String TAG = "AMBLY";
    private static String sCompilerOutputDirectoryPath;

    public AmblyModule(ReactApplicationContext reactContext) {
        super(reactContext);

        if (sCompilerOutputDirectoryPath == null) {
            setCompilerOutputDirectoryPath(reactContext.getFilesDir().getAbsolutePath() + "/cljs-out");
        }
    }

    public static String getCompilerOutputDirectoryPath() {
        return sCompilerOutputDirectoryPath;
    }

    public static void setCompilerOutputDirectoryPath(String compilerOutputDirectoryPath) {
        if (ensureDirectories(compilerOutputDirectoryPath)) {
            sCompilerOutputDirectoryPath = compilerOutputDirectoryPath;
        }
    }

    public static boolean ensureDirectories(String path) {

        File f = new File(path);
        if (!f.mkdirs() && !f.isDirectory()) {
            Log.e(TAG, "Can't create directory " + path);
            return false;
        }

        return true;
    }

    @Override
    public String getName() {
        return "AmblyModule";
    }

    @ReactMethod
    public void AMBLY_IMPORT_SCRIPT(String path) {

        ReactBridge bridge = getReactApplicationContext().getCatalystInstance().getBridge();
        String fullPath = getCompilerOutputDirectoryPath() + "/" + path;

        if (path != null && bridge != null) {
            bridge.loadScriptFromNetworkCached(fullPath, fullPath);
        }
    }
}
