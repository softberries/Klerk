package com.softberries.klerk.gui.editors;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.CompaniesModelProvider;
import com.softberries.klerk.gui.helpers.table.CompanyComparator;
import com.softberries.klerk.gui.helpers.table.CompanyFilter;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkComparator;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkFilter;

public class CompaniesEditor extends GenericKlerkEditor {

	public CompaniesEditor(SimpleKlerkComparator comp,
			SimpleKlerkFilter filter, Object input) {
		super(comp, filter, input);
	}

	public CompaniesEditor() {
		super(new CompanyComparator(), new CompanyFilter(),
				CompaniesModelProvider.INSTANCE.getCompanies());
	}

	public static final String ID = "com.softberries.klerk.gui.editors.CompaniesEditor"; //$NON-NLS-1$

	@Override
	protected void createColumns(Composite parent, TableViewer viewer) {
		String[] titles = { Messages.CompaniesEditor_name, Messages.CompaniesEditor_vatid, Messages.CompaniesEditor_email, Messages.CompaniesEditor_www, Messages.CompaniesEditor_telephone,
				Messages.CompaniesEditor_mobile };
		int[] bounds = { 100, 100, 100, 100, 100, 100 };

		// First column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Company) cell.getElement()).getName());
			}
		});

		// Second column is for the name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Company) cell.getElement()).getVatid());
			}
		});
		// Now the description
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Company) cell.getElement()).getEmail());
			}
		});
		// Now the description
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Company) cell.getElement()).getWww());
			}
		});
		// Now the description
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Company) cell.getElement()).getTelephone());
			}
		});
		// Now the description
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Company) cell.getElement()).getMobile());
			}
		});
	}

	@Override
	protected void addButtonClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void deleteButtonClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void editButtonClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void refreshButtonClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDoubleClick(IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

}
