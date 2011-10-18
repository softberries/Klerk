/*******************************************************************************
 * Copyright (c) 2011 Softberries Krzysztof Grajek.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Softberries Krzysztof Grajek - initial API and implementation
 ******************************************************************************/
package com.softberries.klerk;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Utility class for logging messages within application
 * instead of System.out.println
 * 
 * @author krzysztof.grajek@softberries.com
 *
 */
public class LogUtil {

	/**
	 * Log error level message
	 * @param msg
	 */
	public static void logError(String msg){
		IStatus st = new Status(IStatus.ERROR,Activator.PLUGIN_ID, msg);
		Activator.getDefault().getLog().log(st);
	}
	/**
	 * Log info error message
	 * @param msg
	 */
	public static void logInfo(String msg){
		IStatus st = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
		Activator.getDefault().getLog().log(st);
	}
	/**
	 * Log warning level message
	 * @param msg
	 */
	public static void logWarning(String msg){
		IStatus st = new Status(IStatus.WARNING,Activator.PLUGIN_ID, msg);
		Activator.getDefault().getLog().log(st);
	}
}
