package module.themejunky.com.tj_gae;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;

import module.themejunky.com.tj_gae.utils.EnumTraker;
import module.themejunky.com.tj_gae.utils.Stuff;

/**
 * Created by Alin on 6/15/2017.
 * Email : alinlastun@gmail.com
 */

public class Module_GoogleAnalyticsEvents {

    /* class instance */
    private static Module_GoogleAnalyticsEvents instance = null;
    private final Context context;
    /* Google Analytics...stuff */
    private HashMap<EnumTraker.TrackerName, Tracker> mTrackers = new HashMap<>();
    /* Google Analytics traking id ; if provided....google analytics is in the hood */
    private String propertyId;
    /* Collection class of...stuff methods */
    private Stuff myStuff;
    /* for debugind */
    private Boolean debugMode = false;
    private String nameDebuger;
    private ArrayList<String> logsCollector;

    /**
     * CONSTRUCTOR_CLASS
     *
     * @param context       - activity
     * @param propertyId - if provided ( != nu;; ) Google Analytics is on ( traking id )
     */
    public Module_GoogleAnalyticsEvents(Context context, String propertyId) {
        this.context = context;
        this.propertyId = propertyId;
        this.logsCollector = new ArrayList<>();
        this.myStuff = new Stuff(context);

        logsCollector.add("**********************Start Module GAE**********************");
        logsCollector.add("*");
        logsCollector.add("* Variables : ");
        logsCollector.add("* Context : " + context);
        logsCollector.add("* PropertyId : " + propertyId);
    }
    @SuppressWarnings("unused")
    public static synchronized Module_GoogleAnalyticsEvents getInstance(Context context, String propertyId) {
        if (instance == null)
            instance = new Module_GoogleAnalyticsEvents(context, propertyId);
        return instance;
    }

    /**
     * SETTER METHOD
     *
     * @param nameDebuger    -  Log.d() TAG ; if provided also enable debuging
     * @return current instance
     */
    @SuppressWarnings("unused")
    public Module_GoogleAnalyticsEvents setDebug(String nameDebuger) {
        this.nameDebuger = nameDebuger;
        this.debugMode = true;
        return this;
    }

    /** Analytics stuff.... initialization...bla-bla
     *
     * @param trackerId - google stuff...
     * @return traker... i think
     */
    private synchronized Tracker getTracker(EnumTraker.TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            Tracker t = (trackerId == EnumTraker.TrackerName.APP_TRACKER) ? analytics.newTracker(propertyId)
                    : (trackerId == EnumTraker.TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }


    /**
     * By this method we send google events; Every object is transformed in string before sending
     * @param category - event category as object
     * @param action - event action as object
     * @param label - event label as object
     */
    @SuppressWarnings("unused")
    public synchronized void getEvents(Object category, Object action, Object label) {

        try {
            String categoryS = myStuff.getStringFromObject(category);
            String actionS = myStuff.getStringFromObject(action);
            String labelS = myStuff.getStringFromObject(label);

            Tracker tracker = getTracker(EnumTraker.TrackerName.APP_TRACKER);
            tracker.send(new HitBuilders.EventBuilder().setCategory(categoryS).setAction(actionS).setLabel(labelS).build());

            logsCollector.add("*");
            logsCollector.add("* New Event : ");
            logsCollector.add("* Category : " + categoryS);
            logsCollector.add("* Action : " + actionS);
            logsCollector.add("* Label : " + labelS);

            myStuff.showLogs(debugMode, nameDebuger, logsCollector);
        } catch (Exception e) {
            logsCollector.add("*");
            logsCollector.add("* GAE ERROR : "+e.getMessage());
            myStuff.showLogs(debugMode, nameDebuger, logsCollector);
        }
    }
}


