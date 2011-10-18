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
package com.softberries.klerk.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.softberries.klerk.Activator;
import com.softberries.klerk.gui.helpers.Messages;

public class DatabasePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private StringFieldEditor sfePass = null;
	
	public DatabasePreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.DatabasePreferencePage_Database_connection_settings);
	}

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor("DB_URL", Messages.DatabasePreferencePage_database_url, //$NON-NLS-1$
				getFieldEditorParent()));
		addField(new StringFieldEditor("DB_NAME", Messages.DatabasePreferencePage_database_name, //$NON-NLS-1$
				getFieldEditorParent()));
		addField(new StringFieldEditor("DB_USERNAME", Messages.DatabasePreferencePage_database_username, //$NON-NLS-1$
				getFieldEditorParent()));
		sfePass = new StringFieldEditor("DB_PASSWORD", Messages.DatabasePreferencePage_database_password, getFieldEditorParent()); //$NON-NLS-1$
		sfePass.getTextControl(getFieldEditorParent()).setEchoChar('*') ;
		addField(sfePass);
	}

	@Override
	public void applyData(Object data) {
		//check if connection can be established
		super.applyData(data);
	}
	

}
