package com.softberries.klerk.gui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.gui.editors.input.PersonEditorInput;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.PeopleModelProvider;
import com.softberries.klerk.gui.helpers.table.PersonComparator;
import com.softberries.klerk.gui.helpers.table.PersonFilter;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkComparator;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkFilter;

public class PeopleEditor extends GenericKlerkEditor{

	public static final String ID = "com.softberries.klerk.gui.editors.PeopleEditor";
	
	public PeopleEditor(SimpleKlerkComparator comp, SimpleKlerkFilter filter, Object input) {
		super(comp, filter, input);
	}

	public PeopleEditor(){
		super(new PersonComparator(), new PersonFilter(), PeopleModelProvider.INSTANCE.getPeople());
	}
	@Override
	public void addButtonClicked(){
		Person newP = new Person();
		newP.setFirstName("First name..");
		newP.setLastName("Last Name..");
		openSingleObjectEditor(new PersonEditorInput(newP), "some id");
	}
	@Override
	public void deleteButtonClicked() {
		viewer.setInput(PeopleModelProvider.INSTANCE.getPeople());
		viewer.refresh();
	}

	@Override
	public void editButtonClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshButtonClicked() {
		System.out.println("refresh model");
		viewer.setInput(PeopleModelProvider.INSTANCE.getPeople());
		viewer.refresh();
	}
	


	protected void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { Messages.ProductsEditor_Code, Messages.ProductsEditor_Name, Messages.ProductsEditor_Description};
		int[] bounds = { 100, 200, 100 };

		// First column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Person) cell.getElement()).getFirstName());
			}
		});

		// Second column is for the name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Person) cell.getElement()).getLastName());
			}
		});
		// Now the description
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Person) cell.getElement()).getEmail());
			}
		});
	}

	protected void onDoubleClick(IStructuredSelection selection){
		Person p = (Person) selection.getFirstElement();
		openSingleObjectEditor(new PersonEditorInput(p), "some id");
	}
}
