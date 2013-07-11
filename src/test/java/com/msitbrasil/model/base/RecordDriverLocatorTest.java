/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitbrasil.model.base;

import com.msitbrasil.activerecord.driver.FileRecordDriver;
import com.msitbrasil.activerecord.RecordDriverLocator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emiliowl
 */
public class RecordDriverLocatorTest {
    
    public RecordDriverLocatorTest() {
    }
    
    @Test
    public void locateDriverShouldRetrieveTheDefaultRecordDriverImplementation() {
        RecordDriverLocator.registerDriver(new FileRecordDriver());
        assertTrue(RecordDriverLocator.locate() instanceof FileRecordDriver);
    }
}