package com.softberries.klerk.gui.helpers.table.editingsupport;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import com.softberries.klerk.dao.to.Address;

public class CompanyAddressSelectedES extends EditingSupport {

	private final TableViewer viewer;

	public CompanyAddressSelectedES(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new CheckboxCellEditor(null, SWT.CHECK | SWT.READ_ONLY);

	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		Address adr = (Address) element;
		return adr.isMain();

	}

	@Override
	protected void setValue(Object element, Object value) {
		Address adr = (Address) element;
		adr.setMain((Boolean) value);
		viewer.refresh();
	}
}