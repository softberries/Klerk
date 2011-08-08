package com.softberries.klerk.gui.editors;

import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import net.sf.swtaddons.autocomplete.text.AutocompleteTextInput;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.wb.swt.ResourceManager;

import com.softberries.klerk.calc.DocumentCalculator;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.dao.to.SummaryTableItem;
import com.softberries.klerk.gui.dialogs.DocumentItemDialog;
import com.softberries.klerk.gui.helpers.IImageKeys;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.CompaniesModelProvider;
import com.softberries.klerk.gui.helpers.table.DocumentItemComparator;
import com.softberries.klerk.gui.helpers.table.PeopleModelProvider;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemBasePriceES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemPriceGrossAllES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemPriceNetAllES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemQuantityES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemSelectedES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemTaxPercentES;

public class SingleDocumentEditor extends EditorPart implements PropertyChangeListener{

	public static final String ID = "com.softberries.klerk.gui.editors.SingleDocument"; //$NON-NLS-1$
	
	
	private Document document;
	private final FormToolkit toolkit = new FormToolkit(Display.getDefault());
	private ScrolledForm form;
	private TableViewer itemsTableViewer;	
	private TableViewer summaryTableViewer;	
	private DocumentItemComparator comparator;
	private boolean dirty = false;
	private TableWrapData data_1;

	public SingleDocumentEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		this.dirty = false;
		firePropertyChange(ISaveablePart.PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		document = (Document) input.getAdapter(Document.class);
		setPartName(document.getTitle());
		for(DocumentItem di : this.document.getItems()){
			addPropertyChangeListeners(di);
		}
		this.document.setSummaryItems(new DocumentCalculator().getSummaryItems(this.document.getItems()));
	}

	private void addPropertyChangeListeners(DocumentItem di){
		di.addPropertyChangeListener("selected", this);
		di.addPropertyChangeListener("priceNetSingle", this);
		di.addPropertyChangeListener("quantity", this);
		di.addPropertyChangeListener("priceNetAll", this);
		di.addPropertyChangeListener("priceTaxSingle", this);
		di.addPropertyChangeListener("priceGrossSingle", this);
		di.addPropertyChangeListener("priceTaxAll", this);
		di.addPropertyChangeListener("priceNetAll", this);
		di.addPropertyChangeListener("priceGrossAll", this);
		di.addPropertyChangeListener("selected", this);
	}
	
	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		form = toolkit.createScrolledForm(parent);
		form.setText(Messages.SingleDocumentEditor_INVOICE + document.getTitle());
		TableWrapLayout twlayout = new TableWrapLayout();
		twlayout.numColumns = 2;
		form.getBody().setLayout(twlayout);

		// general section
		Section sectionGeneral = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		sectionGeneral.setText(Messages.SingleDocumentEditor_Main);
		sectionGeneral.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});

		toolkit.createCompositeSeparator(sectionGeneral);
		createSectionToolbar(sectionGeneral, toolkit);

		sectionGeneral.setDescription(Messages.SingleDocumentEditor_Invoice_main_properties);
		Composite sectionGeneralClient = toolkit
				.createComposite(sectionGeneral);
		TableWrapLayout twLayoutSectionGeneral = new TableWrapLayout();
		twLayoutSectionGeneral.numColumns = 4;
		sectionGeneralClient.setLayout(twLayoutSectionGeneral);
		// invoice title
		Label titleLbl = toolkit.createLabel(sectionGeneralClient, Messages.SingleDocumentEditor_Title);
		final Text titleTxt = toolkit.createText(sectionGeneralClient,
				this.document.getTitle(), SWT.BORDER);
		titleTxt.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				document.setTitle(titleTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_titleTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_titleTxt.colspan = 3;
		titleTxt.setLayoutData(twd_titleTxt);
		// invoice created date
		Label createdDateLbl = toolkit.createLabel(sectionGeneralClient,
				Messages.SingleDocumentEditor_Created_Date);
		final DateTime createDate = new DateTime(sectionGeneralClient, SWT.DATE
				| SWT.BORDER);
		createDate.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				document.setCreatedDate(new Date(createDate.getYear(), createDate.getMonth(), createDate.getDay()));
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		// invoice seller
		Label sellerLbl = toolkit.createLabel(sectionGeneralClient, Messages.SingleDocumentEditor_Seller);
		TableWrapData twd_sellerLbl = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_sellerLbl.indent = 55;
		sellerLbl.setLayoutData(twd_sellerLbl);
		Text sellerTxt = toolkit.createText(sectionGeneralClient, "", SWT.BORDER); //$NON-NLS-1$
		TableWrapData twd_sellerTxt = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_sellerTxt.indent = 5;
		twd_sellerTxt.align = TableWrapData.FILL;
		sellerTxt.setLayoutData(twd_sellerTxt);
		sellerTxt.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		// invoice transaction date
		Label transactionDateLbl = toolkit.createLabel(sectionGeneralClient,
				Messages.SingleDocumentEditor_Transaction_Date);
		final DateTime transactionDate = new DateTime(sectionGeneralClient, SWT.DATE
				| SWT.BORDER);
		transactionDate.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				document.setTransactionDate(new Date(transactionDate.getYear(), transactionDate.getMonth(), transactionDate.getDay()));
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		// invoice buyer
		Label buyerLbl = toolkit.createLabel(sectionGeneralClient, Messages.SingleDocumentEditor_Buyer);
		TableWrapData twd_buyerLbl = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_buyerLbl.indent = 55;
		buyerLbl.setLayoutData(twd_buyerLbl);
		Text buyerTxt = toolkit.createText(sectionGeneralClient, "", SWT.BORDER); //$NON-NLS-1$
		new AutocompleteTextInput(buyerTxt, getCompanyNames(CompaniesModelProvider.INSTANCE.getCompanies()));
		TableWrapData twd_buyerTxt = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_buyerTxt.indent = 5;
		twd_buyerTxt.align = TableWrapData.FILL;
		
		buyerTxt.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				//TO-DO set buyer on document
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		buyerTxt.setLayoutData(twd_buyerTxt);
		// invoice due date
		Label dueDateLbl = toolkit.createLabel(sectionGeneralClient,
				Messages.SingleDocumentEditor_due_date);
		final DateTime dueDate = new DateTime(sectionGeneralClient, SWT.DATE
				| SWT.BORDER);
		dueDate.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				document.setDueDate(new Date(dueDate.getYear(), dueDate.getMonth(), dueDate.getDay()));
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		//invoice created by
		Label createdByLbl = toolkit.createLabel(sectionGeneralClient, Messages.SingleDocumentEditor_created_by);
		TableWrapData twd_createdByLbl = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_createdByLbl.indent = 55;
		createdByLbl.setLayoutData(twd_createdByLbl);
		final Text createdByTxt = toolkit.createText(sectionGeneralClient, "buyer", SWT.BORDER); //$NON-NLS-1$
		new AutocompleteTextInput(createdByTxt, getPeopleNames(PeopleModelProvider.INSTANCE.getPeople()));
		createdByTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				document.setCreator(findSelectedCreator(createdByTxt.getText()));
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
			private Person findSelectedCreator(String selected) {
				for(Person p : PeopleModelProvider.INSTANCE.getPeople()){
					String fullName = p.getFirstName() + " " + p.getLastName();
					if(fullName.equals(selected)){
						return p;
					}
				}
				return null;
			}
		});
		TableWrapData twd_createdByTxt = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_createdByTxt.indent = 5;
		
		twd_createdByTxt.align = TableWrapData.FILL;
		createdByTxt.setLayoutData(twd_createdByTxt);
		sectionGeneral.setClient(sectionGeneralClient);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionGeneral.setLayoutData(data);
		// invoice items section
		Section sectionItems = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		sectionItems.setText(Messages.SingleDocumentEditor_Invoice_Items);
		sectionItems.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionItems);
		createItemsSectionToolbar(sectionItems, toolkit);
		Composite sectionItemsClient = toolkit.createComposite(sectionItems);
		TableWrapLayout twLayoutSectionItems = new TableWrapLayout();
		twLayoutSectionItems.numColumns = 2;
		sectionItemsClient.setLayout(twLayoutSectionItems);

		createTableViewer(sectionItemsClient);

		sectionItems.setClient(sectionItemsClient);
		data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionItems.setLayoutData(data);
		
		
		// section summary
		Section sectionSummary = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		sectionSummary.setText(Messages.SingleDocumentEditor_Invoice_Summary);
		sectionSummary.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionSummary);
		sectionSummary.setDescription(Messages.SingleDocumentEditor_Summary);
		
		
		Composite sectionSummaryClient = toolkit
				.createComposite(sectionSummary);
		TableWrapLayout twLayoutSectionSummary = new TableWrapLayout();
		twLayoutSectionSummary.numColumns = 4;
		sectionSummaryClient.setLayout(twLayoutSectionSummary);
		Composite filler = toolkit.createComposite(sectionSummaryClient);
		data_1 = new TableWrapData(TableWrapData.FILL_GRAB);
		data_1.grabVertical = true;
		data_1.valign = TableWrapData.FILL;
		data_1.colspan = 3;
		filler.setLayoutData(data_1);
		
		createSummaryTableViewer(sectionSummaryClient);
		
		sectionSummary.setClient(sectionSummaryClient);
		data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionSummary.setLayoutData(data);

		// section notes
		Section sectionNotes = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		sectionNotes.setText(Messages.SingleDocumentEditor_Notes);
		sectionNotes.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionNotes);

		Composite sectionNotesClient = toolkit.createComposite(sectionNotes);
		TableWrapLayout twLayoutsectionNotes = new TableWrapLayout();
		twLayoutsectionNotes.numColumns = 2;
		sectionNotesClient.setLayout(twLayoutsectionNotes);
		final Text notesTxt = toolkit.createText(sectionNotesClient,
				this.document.getNotes(), SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		TableWrapData twd_notesTxt = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_notesTxt.grabVertical = true;
		twd_notesTxt.grabHorizontal = true;
		twd_notesTxt.heightHint = 180;
		twd_notesTxt.colspan = 1;
		notesTxt.setLayoutData(twd_notesTxt);
		notesTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				document.setNotes(notesTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		sectionNotes.setClient(sectionNotesClient);
		data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionNotes.setLayoutData(data);
	}
	private String[] getPeopleNames(List<Person> people) {
		String[] result = new String[people.size()];
		for(int i = 0; i<people.size();i++){
			Person p = people.get(i);
			result[i] = p.getFirstName() + " " + p.getLastName();
		}
		return result;
	}
	private String[] getCompanyNames(List<Company> companies) {
		String[] result = new String[companies.size()];
		for(int i = 0; i<companies.size();i++){
			Company c = companies.get(i);
			result[i] = c.getName() + " " + "VAT ID: " + c.getVatid();
		}
		return result;
	}
	private void createSummaryTableViewer(Composite parent){
		summaryTableViewer = new TableViewer(parent, SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		createColumnsSummary(parent, summaryTableViewer);
		final Table table = summaryTableViewer.getTable();
		TableWrapData twd_table = new TableWrapData(TableWrapData.LEFT, TableWrapData.FILL, 1, 1);
		twd_table.grabVertical = true;
		table.setLayoutData(twd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		summaryTableViewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		summaryTableViewer.setInput(this.document.getSummaryItems());
		
		TableWrapData twd = new TableWrapData(TableWrapData.FILL);
		twd.colspan = 1;
		summaryTableViewer.getControl().setLayoutData(twd);
		
	}
	
	private void createTableViewer(Composite parent) {
		itemsTableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, itemsTableViewer);
		final Table table = itemsTableViewer.getTable();
		TableWrapData twd_table = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_table.grabVertical = true;
		table.setLayoutData(twd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		itemsTableViewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		itemsTableViewer.setInput(this.document.getItems());
		// Make the selection available to other views
		getSite().setSelectionProvider(itemsTableViewer);

		TableWrapData twd = new TableWrapData(TableWrapData.FILL_GRAB);
		twd.colspan = 2;
		itemsTableViewer.getControl().setLayoutData(twd);
		comparator = new DocumentItemComparator();
		itemsTableViewer.setComparator(comparator);
		
	}

	private void createSectionToolbar(Section section, FormToolkit toolkit) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(),
				SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if ((handCursor != null) && (handCursor.isDisposed() == false)) {
					handCursor.dispose();
				}
			}
		});

		// save
		ActionContributionItem saveMenuAction = new ActionContributionItem(ActionFactory.SAVE.create(getEditorSite().getWorkbenchWindow()));
		toolBarManager.add(saveMenuAction);
		toolBarManager.update(true);

		section.setTextClient(toolbar);
	}
	private void createItemsSectionToolbar(Section section, FormToolkit toolkit) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(),
				SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if ((handCursor != null) && (handCursor.isDisposed() == false)) {
					handCursor.dispose();
				}
			}
		});
		toolBarManager.add(new AddItemControlContribution());
		toolBarManager.add(new DeleteItemControlContribution());
		toolBarManager.update(true);
		section.setTextClient(toolbar);
	}

	// This will create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { Messages.SingleDocumentEditor_Selected, Messages.SingleDocumentEditor_Code, Messages.SingleDocumentEditor_Name, Messages.SingleDocumentEditor_Base_Price, Messages.SingleDocumentEditor_Quantity, Messages.SingleDocumentEditor_Price_Net,
				Messages.SingleDocumentEditor_Tax_Percent, Messages.SingleDocumentEditor_Tax, Messages.SingleDocumentEditor_Price_Gross};
		int[] bounds = {24, 150, 200, 100, 100, 100, 100, 100, 100};
		
		//selected
		TableViewerColumn col = createTableViewerColumn(itemsTableViewer, titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

			@Override
			public Image getImage(Object element) {
				if (((DocumentItem) element).isSelected()) {
					return IImageKeys.CHECKED;
				} else {
					return IImageKeys.UNCHECKED;
				}
			}
		});
		col.setEditingSupport(new DocumentItemSelectedES(viewer));
		// code
		col = createTableViewerColumn(itemsTableViewer,titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getProduct().getCode();
			}
		});

		// name
		col = createTableViewerColumn(itemsTableViewer,titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getProduct().getName();
			}
		});
		// price for single item
		col = createTableViewerColumn(itemsTableViewer,titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getPriceNetSingle();
			}
		});
		col.setEditingSupport(new DocumentItemBasePriceES(viewer));
		// quantity
		col = createTableViewerColumn(itemsTableViewer,titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getQuantity();
			}
		});
		col.setEditingSupport(new DocumentItemQuantityES(viewer));
		// price net all
		col = createTableViewerColumn(itemsTableViewer,titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getPriceNetAll();
			}
		});
		col.setEditingSupport(new DocumentItemPriceNetAllES(viewer));
		// tax percent
		col = createTableViewerColumn(itemsTableViewer,titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getTaxValue();
			}
		});
		col.setEditingSupport(new DocumentItemTaxPercentES(viewer));
		// tax value
		col = createTableViewerColumn(itemsTableViewer,titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getPriceTaxAll();
			}
		});
		// price gross
		col = createTableViewerColumn(itemsTableViewer,titles[8], bounds[8], 8);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getPriceGrossAll();
			}
		});
		col.setEditingSupport(new DocumentItemPriceGrossAllES(viewer));
		
	}

	private void createColumnsSummary(final Composite parent, final TableViewer viewer) {
		String[] titles = {"Name", "Value"};
		int[] bounds = {200, 200};
		
		//name
		TableViewerColumn col = createTableViewerColumn(summaryTableViewer, titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SummaryTableItem p = (SummaryTableItem) element;
				return p.getName();
			}
		});

		// value
		col = createTableViewerColumn(summaryTableViewer, titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SummaryTableItem p = (SummaryTableItem) element;
				return p.getValue();
			}
		});
		
	}
	private TableViewerColumn createTableViewerColumn(TableViewer viewer, String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(
				viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		column.addSelectionListener(getSelectionAdapter(column, colNumber));
		return viewerColumn;

	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column,
			final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				itemsTableViewer.getTable().setSortDirection(dir);
				System.out.println("column selected"); //$NON-NLS-1$
				itemsTableViewer.refresh();
			}
		};
		return selectionAdapter;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	private class AddItemControlContribution extends ControlContribution{

		protected AddItemControlContribution() {
			super("Add");
		}

		@Override
		protected Control createControl(Composite parent) {
			Button button = new Button(parent, SWT.PUSH);
			button.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/add.png"));
			
	        button.addSelectionListener(new SelectionAdapter() {
	        @Override
	    	public void widgetSelected(SelectionEvent e) {
	                DocumentItemDialog dialog = new DocumentItemDialog(PlatformUI.getWorkbench().
	                        getActiveWorkbenchWindow().getShell());
	                Product p = dialog.getItemFromDialog();
	                //TODO - this just fills the values with 0.0
	                DocumentItem it = new DocumentItem();
	                it.setProduct(p);
	                it.setDocumentId(document.getId());
	                it.setPriceGrossAll("0.00");
	                it.setPriceGrossSingle("0.0");
	                it.setPriceNetAll("0.00");
	                it.setPriceNetSingle("0.00");
	                it.setPriceTaxAll("0.00");
	                it.setPriceTaxSingle("0.00");
	                it.setQuantity("0.00");
	                it.setTaxValue("0.00");
	                addPropertyChangeListeners(it);
	                document.getItems().add(it);
	                itemsTableViewer.refresh();
        			dirty = true;
	                firePropertyChange(ISaveablePart.PROP_DIRTY);
	            }
	        });
			return button;
		}
		
	}
	private class DeleteItemControlContribution extends ControlContribution{

		protected DeleteItemControlContribution() {
			super("Delete");
		}

		@Override
		protected Control createControl(Composite parent) {
			Button button = new Button(parent, SWT.PUSH);
			button.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/remove.png"));
			
	        button.addSelectionListener(new SelectionAdapter() {
	        @Override
	    	public void widgetSelected(SelectionEvent e) {
		        	List<DocumentItem> toDel = new ArrayList<DocumentItem>();
		        	for(DocumentItem it : document.getItems()){
		        		if(it.isSelected()){
		        			toDel.add(it);
		        		}
	                }
		        	Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		        	boolean confirmed = MessageDialog.openConfirm(shell, "Confirm", "Are you sure you want to delete these items?");
            		if(confirmed){
            			document.getItems().removeAll(toDel);
            			itemsTableViewer.refresh();
            			dirty = true;
            			firePropertyChange(ISaveablePart.PROP_DIRTY);
            		}
	            }
	        });
			return button;
		}
		
	}

	@Override
	public void propertyChange(java.beans.PropertyChangeEvent arg0) {
		List<SummaryTableItem> list = new DocumentCalculator().getSummaryItems(this.document.getItems());
		summaryTableViewer.setInput(list);
		summaryTableViewer.refresh();
		form.reflow(true);
		dirty = true;
		firePropertyChange(ISaveablePart.PROP_DIRTY);
	}
	
}
