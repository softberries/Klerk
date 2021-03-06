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

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.DocumentDao;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.dao.to.IDocumentType;
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.gui.editors.input.CompanyEditorInput;
import com.softberries.klerk.gui.editors.input.DocumentEditorInput;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.CompaniesModelProvider;
import com.softberries.klerk.gui.helpers.table.DocumentComparator;
import com.softberries.klerk.gui.helpers.table.DocumentFilter;
import com.softberries.klerk.gui.helpers.table.DocumentsModelProvider;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkComparator;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkFilter;

public abstract class DocumentsEditor extends GenericKlerkEditor{

	private Document selectedDocument;

	public DocumentsEditor(SimpleKlerkComparator comp, SimpleKlerkFilter filter, Object input) {
		super(comp, filter, input);
	}

	public DocumentsEditor(int DOC_TYPE){
		super(new DocumentComparator(), new DocumentFilter(), DocumentsModelProvider.INSTANCE.getDocuments(DOC_TYPE, true));//TODO
	}
	
	@Override
	protected void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { Messages.DocumentsEditor_title, Messages.DocumentsEditor_date_created, Messages.DocumentsEditor_transaction_date, Messages.DocumentsEditor_due_date, Messages.DocumentsEditor_place_created,
				Messages.DocumentsEditor_creator, Messages.DocumentsEditor_notes };
		int[] bounds = { 200, 100, 100, 100, 100, 100, 100 };

		// First column is for the title
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getTitle());
			}
		});

		
		// Now the date (created)
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Date date = ((Document) cell.getElement()).getCreatedDate();
				cell.setText(getDateFormatted(date));
			}
		});
		// Now the date (transaction)
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Date date = ((Document) cell.getElement()).getTransactionDate();
				cell.setText(getDateFormatted(date));
			}
		});
		// Now the date (due)
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Date date = ((Document) cell.getElement()).getDueDate();
				cell.setText(getDateFormatted(date));
			}
		});
		// place
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getPlaceCreated());
			}
		});
		// creator
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Document doc = ((Document) cell.getElement());
				cell.setText(doc.getCreator().getFirstName() + " " //$NON-NLS-1$
						+ doc.getCreator().getLastName());
			}
		});
		// Second column is for the notes
		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				String notes = ((Document) cell.getElement()).getNotes();
				if(notes != null){
					notes = notes.replaceAll("\\r\\n|\\r|\\n", " ");
				}
				cell.setText(notes);
			}
		});
	}

	private String getDateFormatted(Date date){
		DateFormat dtf = DateFormat.getDateInstance(DateFormat.DEFAULT,
				   Locale.getDefault());
		return dtf.format(date);
	}
	@Override
	protected void addButtonClicked() {
		Document newD = new Document();
		newD.setTitle("Faktura vat...");
		newD.setItems(new ArrayList<DocumentItem>());
		newD.setCreator(new Person());
		newD.setBuyer(new Company());
		newD.setSeller(new Company());
		newD.setDocumentType(getDocumentType());
		openEditor(newD, getDocumentType());
	}

	@Override
	protected void deleteButtonClicked() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		if(this.getSelectedDocument() == null || this.getSelectedDocument().getId() == null){
			MessageDialog.openInformation(shell, "Information", "Nothing to delete");
			return;
		}
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm", "Are you sure you want to delete this document?");
		if(confirmed){
			DocumentDao dao = new DocumentDao(DB_FOLDER_PATH);
			try {
				dao.delete(this.getSelectedDocument().getId());
				closeOpenedEditorForThisItem(new DocumentEditorInput(this.getSelectedDocument()));
				DocumentsModelProvider.INSTANCE.getDocuments(getDocumentType(), false).remove(this.getSelectedDocument());
				this.setSelectedDocument(null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			viewer.setInput(DocumentsModelProvider.INSTANCE.getDocuments(getDocumentType(), false));
			viewer.refresh();
		}
	}

	@Override
	protected void editButtonClicked() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		if(this.getSelectedDocument() == null || this.getSelectedDocument().getId() == null){
			MessageDialog.openInformation(shell, "Information", "Nothing to edit");
			return;
		}
		openEditor(this.getSelectedDocument(), getDocumentType());
	}

	@Override
	protected void refreshButtonClicked() {
		viewer.setInput(DocumentsModelProvider.INSTANCE.getDocuments(getDocumentType(), true));
		viewer.refresh();
	}

	@Override
	protected void onDoubleClick(IStructuredSelection selection) {
		Document d = (Document) selection.getFirstElement();
		openEditor(d, getDocumentType());
	}

	@Override
	protected void setSelectedObject(Object selection) {
		if(selection != null && selection instanceof Document){
			this.setSelectedDocument((Document) selection);
		}
	}
	/**
	 * @return the selectedDocument
	 */
	public Document getSelectedDocument() {
		return selectedDocument;
	}
	
	/**
	 * @param selectedDocument the selectedDocument to set
	 */
	public void setSelectedDocument(Document selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

	/**
	 * Define a document type for this class
	 * @return {@link IDocumentType} value
	 */
	public abstract int getDocumentType();
}
