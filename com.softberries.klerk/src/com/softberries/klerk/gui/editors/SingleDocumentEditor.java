package com.softberries.klerk.gui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
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
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.softberries.klerk.Activator;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.gui.helpers.IImageKeys;
import com.softberries.klerk.gui.helpers.table.DocumentItemComparator;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemBasePriceES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemPriceGrossAllES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemPriceNetAllES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemQuantityES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemSelectedES;
import com.softberries.klerk.gui.helpers.table.editingsupport.DocumentItemTaxPercentES;

public class SingleDocumentEditor extends EditorPart {

	public static final String ID = "com.softberries.klerk.gui.editors.SingleDocument";
	

	private Document document;
	private final FormToolkit toolkit = new FormToolkit(Display.getDefault());
	private ScrolledForm form;
	private TableViewer itemsTableViewer;	
	private DocumentItemComparator comparator;

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
		createSectionToolbar(sectionGeneral, toolkit);

		sectionGeneral.setDescription("Invoice main properties");
		Composite sectionGeneralClient = toolkit
				.createComposite(sectionGeneral);
		TableWrapLayout twLayoutSectionGeneral = new TableWrapLayout();
		twLayoutSectionGeneral.numColumns = 4;
		sectionGeneralClient.setLayout(twLayoutSectionGeneral);
		// invoice title
		Label titleLbl = toolkit.createLabel(sectionGeneralClient, "Title:");
		Text titleTxt = toolkit.createText(sectionGeneralClient,
				this.document.getTitle(), SWT.BORDER);
		TableWrapData twd_titleTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_titleTxt.colspan = 3;
		titleTxt.setLayoutData(twd_titleTxt);
		// invoice created date
		Label createdDateLbl = toolkit.createLabel(sectionGeneralClient,
				"Created Date:");
		DateTime createDate = new DateTime(sectionGeneralClient, SWT.DATE
				| SWT.BORDER);
		// invoice seller
		Label sellerLbl = toolkit.createLabel(sectionGeneralClient, "Seller:");
		TableWrapData twd_sellerLbl = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_sellerLbl.indent = 50;
		sellerLbl.setLayoutData(twd_sellerLbl);
		Text sellerTxt = toolkit.createText(sectionGeneralClient, "seller", SWT.BORDER);
		TableWrapData twd_sellerTxt = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_sellerTxt.align = TableWrapData.FILL;
		sellerTxt.setLayoutData(twd_sellerTxt);
		// invoice transaction date
		Label transactionDateLbl = toolkit.createLabel(sectionGeneralClient,
				"Transaction Date:");
		DateTime transactionDate = new DateTime(sectionGeneralClient, SWT.DATE
				| SWT.BORDER);
		// invoice buyer
		Label buyerLbl = toolkit.createLabel(sectionGeneralClient, "Buyer:");
		TableWrapData twd_buyerLbl = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_buyerLbl.indent = 50;
		buyerLbl.setLayoutData(twd_buyerLbl);
		Text buyerTxt = toolkit.createText(sectionGeneralClient, "buyer", SWT.BORDER);
		TableWrapData twd_buyerTxt = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_buyerTxt.align = TableWrapData.FILL;
		buyerTxt.setLayoutData(twd_buyerTxt);
		// invoice transaction date
		Label dueDateLbl = toolkit.createLabel(sectionGeneralClient,
				"Due Date:");
		DateTime dueDate = new DateTime(sectionGeneralClient, SWT.DATE
				| SWT.BORDER);
		toolkit.adapt(createDate);
		//invoice created by
		Label createdByLbl = toolkit.createLabel(sectionGeneralClient, "Created by:");
		TableWrapData twd_createdByLbl = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_createdByLbl.indent = 50;
		createdByLbl.setLayoutData(twd_createdByLbl);
		Text createdByTxt = toolkit.createText(sectionGeneralClient, "buyer", SWT.BORDER);
		TableWrapData twd_createdByTxt = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP, 1, 1);
		twd_createdByTxt.align = TableWrapData.FILL;
		createdByTxt.setLayoutData(twd_createdByTxt);
		sectionGeneral.setClient(sectionGeneralClient);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionGeneral.setLayoutData(data);
		// invoice items section
		Section sectionItems = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionItems.setText("Invoice items");
		sectionItems.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionItems);
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

	private void createTableViewer(Composite parent) {
		itemsTableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, itemsTableViewer);
		final Table table = itemsTableViewer.getTable();
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
			public void widgetDisposed(DisposeEvent e) {
				if ((handCursor != null) && (handCursor.isDisposed() == false)) {
					handCursor.dispose();
				}
			}
		});

		// save
		CommandContributionItemParameter saveContributionParameter = new CommandContributionItemParameter(
				this.getSite(), null, "org.eclipse.ui.window.preferences",
				CommandContributionItem.STYLE_PUSH);
		String imageKey = IImageKeys.ALL_CATEGORIES;
		AbstractUIPlugin plugin = Activator.getDefault();
		ImageRegistry imageRegistry = plugin.getImageRegistry();
		saveContributionParameter.icon = imageRegistry.getDescriptor(imageKey);

		CommandContributionItem saveMenu = new CommandContributionItem(
				saveContributionParameter);

		toolBarManager.add(saveMenu);

		toolBarManager.update(true);

		section.setTextClient(toolbar);
	}

	// This will create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Code", "Name", "Base Price", "Quantity", "Price Net",
				"Tax[%]", "Tax", "Price Gross", "Selected"};
		int[] bounds = { 100, 200, 100, 100, 100, 100, 100, 100, 40};

		// code
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getProduct().getCode();
			}
		});

		// name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getProduct().getName();
			}
		});
		// price for single item
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getPriceNetSingle();
			}
		});
		col.setEditingSupport(new DocumentItemBasePriceES(viewer));
		// quantity
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getQuantity();
			}
		});
		col.setEditingSupport(new DocumentItemQuantityES(viewer));
		// price net all
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getPriceNetAll();
			}
		});
		col.setEditingSupport(new DocumentItemPriceNetAllES(viewer));
		// tax percent
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getTaxValue();
			}
		});
		col.setEditingSupport(new DocumentItemTaxPercentES(viewer));
		// tax value
		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getPriceTaxAll();
			}
		});
		// price gross
		col = createTableViewerColumn(titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				DocumentItem p = (DocumentItem) element;
				return p.getPriceGrossAll();
			}
		});
		col.setEditingSupport(new DocumentItemPriceGrossAllES(viewer));
		
		col = createTableViewerColumn(titles[8], bounds[8], 8);
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
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(
				itemsTableViewer, SWT.NONE);
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
				System.out.println("column selected");
				itemsTableViewer.refresh();
			}
		};
		return selectionAdapter;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
