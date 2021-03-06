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
package com.softberries.klerk.gui.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class MainPerspective implements IPerspectiveFactory {

	public static final String ID = "com.softberries.klerk.gui.perspectives.MainPerspective";
	/**
	 * Creates the initial layout for a page.
	 */
	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		addFastViews(layout);
		addViewShortcuts(layout);
		addPerspectiveShortcuts(layout);
		layout.setEditorAreaVisible(true);
		layout.addView("com.softberries.klerk.gui.views.CategoriesView", IPageLayout.LEFT, 0.25f, IPageLayout.ID_EDITOR_AREA);
		layout.addView("com.softberries.klerk.gui.views.DocumentDetailsView", IPageLayout.RIGHT, 0.69f, IPageLayout.ID_EDITOR_AREA);	
		layout.addView("com.softberries.klerk.gui.views.ProductDetailsView", IPageLayout.BOTTOM, 0.5f, "com.softberries.klerk.gui.views.DocumentDetailsView");
	}

	/**
	 * Add fast views to the perspective.
	 */
	private void addFastViews(IPageLayout layout) {
	}

	/**
	 * Add view shortcuts to the perspective.
	 */
	private void addViewShortcuts(IPageLayout layout) {
	}

	/**
	 * Add perspective shortcuts to the perspective.
	 */
	private void addPerspectiveShortcuts(IPageLayout layout) {
	}

}
