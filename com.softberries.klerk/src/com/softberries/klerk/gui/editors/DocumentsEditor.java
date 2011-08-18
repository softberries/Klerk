package com.softberries.klerk.gui.editors;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.gui.editors.input.DocumentEditorInput;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.DocumentComparator;
import com.softberries.klerk.gui.helpers.table.DocumentFilter;
import com.softberries.klerk.gui.helpers.table.DocumentsModelProvider;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkComparator;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkFilter;

public class DocumentsEditor extends GenericKlerkEditor{

	public static final String ID = "com.softberries.klerk.gui.editors.DocumentsEditor"; //$NON-NLS-1$
	public DocumentsEditor(SimpleKlerkComparator comp, SimpleKlerkFilter filter, Object input) {
		super(comp, filter, input);
	}

	public DocumentsEditor(){
		super(new DocumentComparator(), new DocumentFilter(), DocumentsModelProvider.INSTANCE.getDocuments());
	}
	
	@Override
	protected void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { Messages.DocumentsEditor_title, Messages.DocumentsEditor_notes, Messages.DocumentsEditor_date_created, Messages.DocumentsEditor_transaction_date, Messages.DocumentsEditor_due_date, Messages.DocumentsEditor_place_created,
				Messages.DocumentsEditor_creator };
		int[] bounds = { 100, 100, 100, 100, 100, 100, 100 };

		// First column is for the title
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getTitle());
			}
		});

		// Second column is for the notes
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getNotes());
			}
		});
		// Now the date (created)
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getCreatedDate()
						.toLocaleString());
			}
		});
		// Now the date (transaction)
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement())
						.getTransactionDate().toLocaleString());
			}
		});
		// Now the date (due)
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getDueDate()
						.toLocaleString());
			}
		});
		// place
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getPlaceCreated());
			}
		});
		// creator
		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Document doc = ((Document) cell.getElement());
				cell.setText(doc.getCreator().getFirstName() + " " //$NON-NLS-1$
						+ doc.getCreator().getLastName());
			}
		});

	}

	@Override
	protected void addButtonClicked() {
		
	}

	@Override
	protected void deleteButtonClicked() {
		
	}

	@Override
	protected void editButtonClicked() {
		
	}

	@Override
	protected void refreshButtonClicked() {
		
	}

	@Override
	protected void onDoubleClick(IStructuredSelection selection) {
		Document d = (Document) selection.getFirstElement();
		openSingleObjectEditor(new DocumentEditorInput(d), SingleDocumentEditor.ID);
	}

	@Override
	protected void setSelectedObject(Object selection) {
		
	}

}
