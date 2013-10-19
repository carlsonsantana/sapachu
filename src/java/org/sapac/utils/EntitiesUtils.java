/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sapac.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;

/**
 *
 * @author carlson
 */
public class EntitiesUtils {
	private EntitiesUtils() {}

	public static Collection<SelectItem> convertEntitieToSelectItem(Collection entities, String value, String label) {
		Collection<SelectItem> selectItems = new ArrayList<SelectItem>();
		if (!entities.isEmpty()) {
			try {
				Class object = entities.iterator().next().getClass();
				Field fieldValue = object.getDeclaredField(value);
				Field fieldLabel = object.getDeclaredField(label);

				fieldValue.setAccessible(true);
				fieldLabel.setAccessible(true);
				
				SelectItem selectItem;

				for (Object entitie : entities) {
					String stringValue = fieldValue.get(entitie).toString();
					String stringLabel = fieldLabel.get(entitie).toString();
					
					selectItem = new SelectItem(stringValue, stringLabel);
					
					selectItems.add(selectItem);
				}
			} catch (NoSuchFieldException ex) {
				Logger.getLogger(EntitiesUtils.class.getName()).log(Level.SEVERE, null, ex);
			} catch (SecurityException ex) {
				Logger.getLogger(EntitiesUtils.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalArgumentException ex) {
				Logger.getLogger(EntitiesUtils.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(EntitiesUtils.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return selectItems;
	}
}