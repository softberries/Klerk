package com.softberries.klerk.gui.editors;

import java.sql.SQLException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.softberries.klerk.dao.PeopleDao;
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.gui.editors.input.PersonEditorInput;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.PeopleModelProvider;
import com.softberries.klerk.gui.helpers.table.PersonComparator;
import com.softberries.klerk.gui.helpers.table.PersonFilter;
import com.softberries.klerk.gui.helpers.table.ProductsModelProvider;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkComparator;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkFilter;

public class PeopleEditor extends GenericKlerkEditor{

	public static final String ID = "com.softberries.klerk.gui.editors.PeopleEditor";
	
	private Person selectedPerson;
	
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
		openSingleObjectEditor(new PersonEditorInput(newP), SinglePersonEditor.ID);
	}
	@Override
	public void deleteButtonClicked() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		if(this.getSelectedPerson() == null || this.getSelectedPerson().getId() == null){
			MessageDialog.openInformation(shell, "Information", "Nothing to delete");
			return;
		}
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm", "Are you sure you want to delete this person?");
		if(confirmed){
			PeopleDao dao = new PeopleDao(DB_FOLDER_PATH);
			try {
				dao.delete(this.getSelectedPerson().getId());
				closeOpenedEditorForThisItem(new PersonEditorInput(this.getSelectedPerson()));
				PeopleModelProvider.INSTANCE.getPeople().remove(this.getSelectedPerson());
				this.setSelectedPerson(null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			viewer.setInput(ProductsModelProvider.INSTANCE.getProducts());
			viewer.refresh();
		}
	}

	@Override
	public void editButtonClicked() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		if(this.getSelectedPerson() == null || this.getSelectedPerson().getId() == null){
			MessageDialog.openInformation(shell, "Information", "Nothing to edit");
			return;
		}
		openSingleObjectEditor(new PersonEditorInput(this.getSelectedPerson()), SinglePersonEditor.ID);
	}

	@Override
	public void refreshButtonClicked() {
		System.out.println("refresh model");
		viewer.setInput(PeopleModelProvider.INSTANCE.getPeople());
		viewer.refresh();
	}
	


	@Override
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

	@Override
	protected void onDoubleClick(IStructuredSelection selection){
		Person p = (Person) selection.getFirstElement();
		openSingleObjectEditor(new PersonEditorInput(p), SinglePersonEditor.ID);
	}

	@Override
	protected void setSelectedObject(Object selection) {
		if(selection != null && selection instanceof Person){
			this.setSelectedPerson((Person) selection);
		}
	}

	/**
	 * @return the selectedPerson
	 */
	public Person getSelectedPerson() {
		return selectedPerson;
	}

	/**
	 * @param selectedPerson the selectedPerson to set
	 */
	public void setSelectedPerson(Person selectedPerson) {
		this.selectedPerson = selectedPerson;
	}
	
}
