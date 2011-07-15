package com.softberries.klerk.gui.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class MainPerspective implements IPerspectiveFactory {

	public static final String ID = "com.softberries.klerk.gui.perspectives.MainPerspective";
	/**
	 * Creates the initial layout for a page.
	 */
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		addFastViews(layout);
		addViewShortcuts(layout);
		addPerspectiveShortcuts(layout);
		layout.setEditorAreaVisible(true);
		layout.addView("com.softberries.klerk.gui.views.CategoriesView", IPageLayout.LEFT, 0.25f, IPageLayout.ID_EDITOR_AREA);
		layout.addView("com.softberries.klerk.gui.views.DocumentDetailsView", IPageLayout.RIGHT, 0.69f, IPageLayout.ID_EDITOR_AREA);
	
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
