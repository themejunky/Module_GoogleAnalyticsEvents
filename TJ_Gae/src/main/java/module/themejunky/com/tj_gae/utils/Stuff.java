package module.themejunky.com.tj_gae.utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Stuff {

    private Context context;

    public Stuff(Context context) {
        this.context = context;
    }

    /**
     * if "debugMode" == "true" print the collected logs
     */
    public synchronized void showLogs(Boolean debugMode, String nameDebuger, ArrayList<String> logsCollector) {
        if (debugMode) {
            for (String log : logsCollector) {
                Log.d(nameDebuger, "" + log);
            }
            logsCollector.clear();
        }
    }

    /**
     * Tranform provided Object in String if...recognizez
     * @param newObject - object to transfor in String
     * @return THE STRING!
     */

    public synchronized String getStringFromObject(Object newObject) {

        String returnString = "ObjectNotRecognized";

        if (newObject instanceof String) {
            returnString = (String) newObject;
        } else if (newObject instanceof Integer) {
            returnString = context.getResources().getString((int)newObject);
        } else if (newObject instanceof Class<?>) {
            returnString = ((Class<?>) newObject).getSimpleName();
        }

        return returnString;
    }
}
