package com.softberries.klerk.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.softberries.klerk.Activator;

public class DatabasePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public DatabasePreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Database connection settings");
	}

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor("DB_URL", "&Database URL:",
				getFieldEditorParent()));
		addField(new StringFieldEditor("DB_NAME", "&Database name:",
				getFieldEditorParent()));
		addField(new StringFieldEditor("DB_USERNAME", "&Database username:",
				getFieldEditorParent()));
		StringFieldEditor sfePass = new StringFieldEditor("DB_PASSWORD", "&Database password:", getFieldEditorParent());
		sfePass.getTextControl(getFieldEditorParent()).setEchoChar('*') ;
		addField(sfePass);
	}

}
