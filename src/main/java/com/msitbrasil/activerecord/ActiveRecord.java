/*
 * all rights for MSIT SOFTWARE
 */
package com.msitbrasil.activerecord;

import com.msitbrasil.activerecord.annotations.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * This is the class that all Model Object that want to have storable behavior
 * should extend on app.
 *
 * @author emiliowl
 */
public abstract class ActiveRecord implements Serializable, Cloneable {

    private boolean persisted = false;

    public boolean isPersisted() {
        return persisted;
    }

    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }

    @Override
    public ActiveRecord clone() throws CloneNotSupportedException {
        return (ActiveRecord)super.clone();
    }
    
    /**
     *
     * Save the actual Model in the base with the implementation driver
     * supplied.
     *
     * @return boolean
     */
    public boolean save() {
        return RecordDriverLocator.locate().save(this);
    }

    /**
     *
     * Destroy the actual Model in the base with the implementation driver
     * supplied.
     *
     * @return boolean
     */
    public ActiveRecord destroy() {
        return RecordDriverLocator.locate().destroy(this);
    }
    
    /**
     *
     * Find the actual Model in the base with the implementation driver
     * supplied.
     *
     * @return ActiveRecord Implementor
     */
    public static ActiveRecord find(String uniqueId) {
        return RecordDriverLocator.locate().find(uniqueId);
    }

    /**
     *
     * Return an unique ID for this specific object, based on the annotated field with @Id.
     * It's responsibility of the implementor of ActiveRecord Child to annotate a trustable
     * unique field with @Id.
     *
     * @return java.lang.String
     */
    public String getUniqueId() throws IllegalAccessException {
        return this.getClass().getName().concat(this.getUniqueIdFieldValue());

    }

    private String getUniqueIdFieldValue() throws IllegalAccessException {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                return field.get(this).toString();
            }
        }
        throw new IllegalStateException("No annotated field with @Id");
    }

    @Override
    public int hashCode() {
        try {
            return this.getUniqueId().hashCode();
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ActiveRecord.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ActiveRecord)) {
            return false;
        }
                
        return this.hashCode() == o.hashCode();
    }
    
    
    
}