/*
 * all rights for MSIT SOFTWARE
 */
package com.msitbrasil.activerecord.driver;

import com.msitbrasil.activerecord.ActiveRecord;

/**
 *
 * This is the RecordDriver interface that define the minimum methods required for your persistance implementation Driver.
 * You should always create a RecordDriver when you want to persist your objects to a new place (DB, File, Webservice, etc)
 * Assert to wire the new RecordDriver to ActiveRecord when calling it.
 * 
 * @author emiliowl
 */
public interface RecordDriver {
    public boolean save(ActiveRecord record);
    public ActiveRecord destroy(ActiveRecord record);
    public ActiveRecord find(String id);
}
