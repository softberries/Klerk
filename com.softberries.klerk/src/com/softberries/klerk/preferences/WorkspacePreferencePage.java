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
