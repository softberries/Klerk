package com.softberries.klerk.gui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;

import com.softberries.klerk.dao.to.Document;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridLayout;

public class SingleDocumentEditor extends EditorPart {

	public static final String ID = "com.softberries.klerk.gui.editors.SingleDocument";
	private Document document;
	private final FormToolkit toolkit = new FormToolkit(Display.getDefault());
	private ScrolledForm form;
	private Text txtNewText;

	public SingleDocumentEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		document = (Document) input.getAdapter(Document.class);
		setPartName(document.getTitle());
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		form = toolkit.createScrolledForm(parent);
		form.setText("INVOICE: " + document.getTitle());
		TableWrapLayout twlayout = new TableWrapLayout();
		twlayout.numColumns = 2;
		form.getBody().setLayout(twlayout);

		// general section
		Section sectionGeneral = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionGeneral.setText("Main");
		sectionGeneral.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});

		toolkit.createCompositeSeparator(sectionGeneral);
		sectionGeneral.setDescription("Invoice main properties");
		Composite sectionGeneralClient = toolkit
				.createComposite(sectionGeneral);
		TableWrapLayout twLayoutSectionGeneral = new TableWrapLayout();
		twLayoutSectionGeneral.numColumns = 2;
		sectionGeneralClient.setLayout(twLayoutSectionGeneral);
		// invoice title
		Label titleLbl = toolkit.createLabel(sectionGeneralClient, "Title:");
		Text titleTxt = toolkit.createText(sectionGeneralClient,
				this.document.getTitle(), SWT.BORDER);
		titleTxt.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		// invoice created date
		Label createdDateLbl = toolkit.createLabel(sectionGeneralClient,
				"Created Date:");
		DateTime createDate = new DateTime(sectionGeneralClient, SWT.DATE
				| SWT.BORDER);
		// invoice transaction date
		Label transactionDateLbl = toolkit.createLabel(sectionGeneralClient,
				"Transaction Date:");
		DateTime transactionDate = new DateTime(sectionGeneralClient, SWT.DATE
				| SWT.BORDER);
		// invoice transaction date
		Label dueDateLbl = toolkit.createLabel(sectionGeneralClient,
				"Due Date:");
		DateTime dueDate = new DateTime(sectionGeneralClient, SWT.DATE
				| SWT.BORDER);
		toolkit.adapt(createDate);
		sectionGeneral.setClient(sectionGeneralClient);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionGeneral.setLayoutData(data);
		// invoid items section
		Section sectionItems = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionItems.setText("Invoice items");
		sectionItems.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionItems);
		sectionItems.setDescription("List of items:");
		Composite sectionItemsClient = toolkit.createComposite(sectionItems);
		TableWrapLayout twLayoutSectionItems = new TableWrapLayout();
		twLayoutSectionItems.numColumns = 2;
		sectionItemsClient.setLayout(twLayoutSectionItems);
		sectionItems.setClient(sectionItemsClient);
		data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionItems.setLayoutData(data);
		// section summary
		Section sectionSummary = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionSummary.setText("Invoice Summary");
		sectionSummary.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionSummary);
		sectionSummary.setDescription("Summary");
		Composite sectionSummaryClient = toolkit
				.createComposite(sectionSummary);
		TableWrapLayout twLayoutSectionSummary = new TableWrapLayout();
		twLayoutSectionSummary.numColumns = 2;
		sectionSummaryClient.setLayout(twLayoutSectionSummary);
		sectionSummary.setClient(sectionSummaryClient);
		data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionSummary.setLayoutData(data);

		// section notes
		Section sectionNotes = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionNotes.setText("Notes");
		sectionNotes.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionNotes);
		Composite sectionNotesClient = toolkit.createComposite(sectionNotes);
		TableWrapLayout twLayoutsectionNotes = new TableWrapLayout();
		twLayoutsectionNotes.numColumns = 2;
		sectionNotesClient.setLayout(twLayoutsectionNotes);
		Label notesLbl = toolkit.createLabel(sectionNotesClient,
				this.document.getNotes());
		sectionNotes.setClient(sectionNotesClient);
		data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionNotes.setLayoutData(data);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
