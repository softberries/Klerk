package com.softberries.klerk;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class LogUtil {

	public static void logError(String msg){
		IStatus st = new Status(IStatus.ERROR,Activator.PLUGIN_ID, msg);
		Activator.getDefault().getLog().log(st);
	}
	public static void logInfo(String msg){
		IStatus st = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
		Activator.getDefault().getLog().log(st);
	}
	public static void logWarning(String msg){
		IStatus st = new Status(IStatus.WARNING,Activator.PLUGIN_ID, msg);
		Activator.getDefault().getLog().log(st);
	}
}
