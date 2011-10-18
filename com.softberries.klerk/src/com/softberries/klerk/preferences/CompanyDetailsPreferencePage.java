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

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.softberries.klerk.Activator;
import com.softberries.klerk.gui.helpers.Messages;

public class CompanyDetailsPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public CompanyDetailsPreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Please provide your company details below");
	}

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(IPreferencesKeys.COMPANY_NAME, "Company Name:",
				getFieldEditorParent()));
		addField(new StringFieldEditor(IPreferencesKeys.COMPANY_VATID, "Vat ID:",
				getFieldEditorParent()));
		addField(new StringFieldEditor(IPreferencesKeys.COMPANY_COUNTRY, "Country:",
				getFieldEditorParent()));
		addField(new StringFieldEditor(IPreferencesKeys.COMPANY_CITY, "City:", 
				getFieldEditorParent()));
		addField(new StringFieldEditor(IPreferencesKeys.COMPANY_STREET, "Street:", 
				getFieldEditorParent()));
		addField(new StringFieldEditor(IPreferencesKeys.COMPANY_HOUSENR, "House Nr:", 
				getFieldEditorParent()));
		addField(new StringFieldEditor(IPreferencesKeys.COMPANY_FLATNR, "Flat Nr:", 
				getFieldEditorParent()));
		addField(new StringFieldEditor(IPreferencesKeys.COMPANY_POSTCODE, "Post Code:",
				getFieldEditorParent()));
	}

	@Override
	public void applyData(Object data) {
		super.applyData(data);
	}
	
}
