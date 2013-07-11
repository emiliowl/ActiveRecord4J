/*
 * MSIT Software
 */
package com.msitbrasil.activerecord;

import com.msitbrasil.activerecord.driver.FileRecordDriver;
import com.msitbrasil.activerecord.driver.RecordDriver;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * Locator for the suitable RecordDriver
 * 
 * @author emiliowl
 */
public class RecordDriverLocator {
    
    private static final Map<String, RecordDriver> recordDrivers = new TreeMap<String, RecordDriver>();
    private static String defaultDriver = "default";
    
    static {
        recordDrivers.put(defaultDriver, new FileRecordDriver());
    }
    
    public static boolean registerDriver(RecordDriver driver) {
        recordDrivers.put(driver.getClass().getName(), driver);
        defaultDriver = driver.getClass().getName();
        return true;
    }
    
    public static RecordDriver locate() {
        return recordDrivers.get(defaultDriver);
    }
}
