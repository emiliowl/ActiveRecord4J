/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitbrasil.activerecord.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * This field will be used by ActiveRecord to generate the uniqueId
 * 
 * @author emiliowl
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
    
}
