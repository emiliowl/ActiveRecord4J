/*
 * all rights for MSIT SOFTWARE
 */
package com.msitbrasil.model.base;

import com.msitbrasil.activerecord.ActiveRecord;
import com.msitbrasil.activerecord.RecordDriverLocator;
import com.msitbrasil.activerecord.annotations.Id;
import com.msitbrasil.activerecord.driver.RecordDriver;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author emiliowl
 */
public class ActiveRecordTest {

    private ActiveRecord model;
    private MockRecordDriver _myMockDriver = new MockRecordDriver();

    public ActiveRecordTest() {
        RecordDriverLocator.registerDriver(_myMockDriver);
    }

    @Before
    public void setUp() {
        model = new ActiveRecord() {
            @Id
            String unique = "uniq";
        };
    }

    @After
    public void tearDown() {
        _myMockDriver.clear();
    }

    @Test
    public void shouldDelegateSaveToRecordDriverOnSave() {
        assertTrue(model.save());
        assertTrue(model.isPersisted());
    }

    @Test
    public void shouldDelegateDestroyToRecordDriverOnDestroy() {
        model.save();
        assertFalse(model.destroy().isPersisted());
    }

    @Test
    public void shouldDelegateFindToRecordDriverOnFind() {
        model.save();
        assertEquals(model, ActiveRecord.find(model.getClass().getName() + "uniq"));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionIfIdNotAnnotatedWhenCallGetUniqueId() throws IllegalAccessException {
        model = new ActiveRecord() { };
        model.getUniqueId();
    }

    @Test
    public void shouldGetUniqueIdBasedOnAnnotadedIdClass() throws IllegalAccessException {
        assertEquals(model.getClass().getName() + "uniq", model.getUniqueId());
    }

    /**
     * Mock driver implementation for tests purpose only
     */
    private class MockRecordDriver implements RecordDriver {
        
        public Map<String, ActiveRecord> dataBase = new TreeMap<String, ActiveRecord>();

        public MockRecordDriver() {
        }
        
        public void clear() {
            dataBase.clear();
        }

        public boolean save(ActiveRecord record) {
            try {
                record.setPersisted(true);
                dataBase.put(record.getUniqueId(), record.clone());
                return true;
            } catch(Exception ex) {
                Logger.getLogger(MockRecordDriver.class.getName()).log(Level.SEVERE, "Something bugged save method!", ex);
                record.setPersisted(false);
                return false;
            }
        }

        public ActiveRecord destroy(ActiveRecord record) {
            try {
                if (dataBase.remove(record.getUniqueId()) != null) {
                    record.setPersisted(false);
                } else {
                    throw new IllegalArgumentException("Record isn't persisted!");
                }
            } catch(Exception ex) {
                Logger.getLogger(MockRecordDriver.class.getName()).log(Level.SEVERE, "Something bugged destroy method!", ex);
                record = null;
            } finally {
                return record;
            }
        }

        public ActiveRecord find(String id) {
            try {
                return dataBase.get(id).clone();
            } catch(CloneNotSupportedException ex) {
                Logger.getLogger(MockRecordDriver.class.getName()).log(Level.SEVERE, "Something bugged find method!", ex);
                return null;
            }
            
        }
    }
}