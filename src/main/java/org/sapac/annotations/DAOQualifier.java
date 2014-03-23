package org.sapac.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface DAOQualifier {
	
	DAOType value();
	
	public enum DAOType {
		HIBERNATE;
	}
}