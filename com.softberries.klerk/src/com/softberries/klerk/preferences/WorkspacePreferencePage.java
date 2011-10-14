package com.softberries.klerk.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import com.softberries.klerk.Activator;

public class WorkspacePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public WorkspacePreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Workspace preferences");
	}

	@Override
	protected void createFieldEditors() {
		addField(new DirectoryFieldEditor(
				"WORKSPACE_DIR_PATH", "Workspace Directory", //$NON-NLS-1$
				getFieldEditorParent()));
	}

	@Override
	public void applyData(Object data) {
		//copy resources to the new directory
		
		super.applyData(data);
	}

}
