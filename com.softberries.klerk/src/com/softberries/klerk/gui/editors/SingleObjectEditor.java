package com.softberries.klerk.gui.editors;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;

public abstract class SingleObjectEditor extends EditorPart {

	protected final FormToolkit toolkit = new FormToolkit(Display.getDefault());
	protected ScrolledForm form;
	protected boolean dirty;
	

	@Override
	public void doSaveAs() {
	}
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return dirty;
	}
	
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	protected void createSectionToolbar(Section section, FormToolkit toolkit) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(),
				SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if ((handCursor != null) && (handCursor.isDisposed() == false)) {
					handCursor.dispose();
				}
			}
		});

		// save
		ActionContributionItem saveMenuAction = new ActionContributionItem(ActionFactory.SAVE.create(getEditorSite().getWorkbenchWindow()));
		toolBarManager.add(saveMenuAction);
		toolBarManager.update(true);

		section.setTextClient(toolbar);
	}
	@Override
	public void setFocus() {
		
	}
}
