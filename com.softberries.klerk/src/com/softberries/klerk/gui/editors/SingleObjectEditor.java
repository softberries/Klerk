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
package com.softberries.klerk.gui.editors;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
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

	protected void bindValidator(Text field, Object obj, String property, IValidator validator) {
		DataBindingContext bindingContext = new DataBindingContext();
		UpdateValueStrategy ttm = new UpdateValueStrategy();
		ttm.setBeforeSetValidator(validator);
		//
		IObservableValue countryTxtObserveTextObserveWidget = SWTObservables.observeText(field, SWT.Modify);
		IObservableValue currentAddressCountryObserveValue = PojoObservables.observeValue(obj, property);
		Binding binding = bindingContext.bindValue(countryTxtObserveTextObserveWidget, currentAddressCountryObserveValue, ttm, null);
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);
		
	}
	@Override
	public void setFocus() {
		
	}
	protected abstract void enableSave(boolean drt);
}
