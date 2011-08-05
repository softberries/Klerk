package com.softberries.klerk.gui.helpers.table.editingsupport;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;


public abstract class DocumentItemCellEditingSupport extends EditingSupport{

	protected final TableViewer viewer;

	public DocumentItemCellEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
		
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		TextCellEditor tce = new TextCellEditor(viewer.getTable());
		return tce;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}
}
