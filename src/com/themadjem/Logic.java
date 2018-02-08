package com.themadjem;

import com.themadjem.fx.CalfSheet;
import com.themadjem.utils.Constants;
import com.themadjem.utils.GsonUtil;

/**
 * Loads the cows from the JSON file and sets up the calf sheet
 */
public class Logic implements Runnable {


    public static void main(String[] args) {
        new Thread(new Logic()).start();
//        doCowStuff();
    }

    public void run() {
        new CalfSheet(GsonUtil.loadGson(Constants.COWS_PATH, Herd.class));
    }

    /**
     * Saves the Herd of Cows as a json file
     *
     * @param herd herd of cows to save
     */
    public static void saveCows(Herd herd) {
        GsonUtil.saveGson(Constants.COWS_PATH, herd);
    }

    /**
     * Used to change something about the entire herd by running a single method
     */
    @SuppressWarnings("unused")
    private static void doCowStuff() {
        Herd herd = GsonUtil.loadGson(Constants.COWS_PATH, Herd.class);
        assert herd != null;
        herd.getCows().forEach(System.out::println);
        saveCows(herd);
    }
}
