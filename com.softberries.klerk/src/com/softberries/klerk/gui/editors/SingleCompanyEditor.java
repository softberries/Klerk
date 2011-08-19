package com.softberries.klerk.gui.editors;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.wb.swt.ResourceManager;

import com.softberries.klerk.Activator;
import com.softberries.klerk.LogUtil;
import com.softberries.klerk.dao.AddressDao;
import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.gui.dialogs.AddressDialog;
import com.softberries.klerk.gui.helpers.IImageKeys;
import com.softberries.klerk.gui.helpers.table.CompaniesModelProvider;
import com.softberries.klerk.gui.helpers.table.editingsupport.CompanyAddressSelectedES;

public class SingleCompanyEditor extends SingleObjectEditor implements
		ISelectionListener {

	public SingleCompanyEditor() {
	}

	public static final String ID = "com.softberries.klerk.gui.editors.SingleCompanyEditor";
	private Company company;
	private TableViewer addressTableViewer;
	private Address currentAddress;

	@Override
	public void doSave(IProgressMonitor monitor) {
		CompanyDao dao = new CompanyDao();
		try {
			if (company.getId() == null) {
				dao.create(company);
				CompaniesModelProvider.INSTANCE.getCompanies().add(company);
			} else {
				LogUtil.logInfo("Updating company: " + company.getId());
				dao.update(company);
			}

		} catch (SQLException e) {
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
					"Sorry, couldn't update companies database.", e);
			StatusManager.getManager().handle(status, StatusManager.SHOW);
		}
		dirty = false;
		firePropertyChange(ISaveablePart.PROP_DIRTY);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		company = (Company) input.getAdapter(Company.class);
		if (company.getAddresses() == null) {
			company.setAddresses(new ArrayList<Address>());
		}
		setPartName(company.getName());
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getActiveWorkbenchWindow().getActivePage()
				.addSelectionListener(this);
	}

	@Override
	public void createPartControl(Composite parent) {
		form = toolkit.createScrolledForm(parent);
		form.setText(this.getCompany().getName());
		TableWrapLayout twlayout = new TableWrapLayout();
		twlayout.numColumns = 2;
		form.getBody().setLayout(twlayout);

		// general section
		Section sectionGeneral = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | ExpandableComposite.TWISTIE
						| ExpandableComposite.EXPANDED);
		sectionGeneral.setText("General Info");
		sectionGeneral.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});

		toolkit.createCompositeSeparator(sectionGeneral);
		createSectionToolbar(sectionGeneral, toolkit);

		sectionGeneral.setDescription("General info:");
		Composite sectionGeneralClient = toolkit
				.createComposite(sectionGeneral);
		TableWrapLayout twLayoutSectionGeneral = new TableWrapLayout();
		twLayoutSectionGeneral.numColumns = 4;
		sectionGeneralClient.setLayout(twLayoutSectionGeneral);
		// Company name
		final Label nameLbl = toolkit.createLabel(sectionGeneralClient,
				"Name:");
		final Text nameTxt = toolkit.createText(sectionGeneralClient,
				this.company.getName(), SWT.BORDER);
		nameTxt.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				company.setName(nameTxt.getText());
				setPartName(company.getName());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_nameTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_nameTxt.colspan = 3;
		nameTxt.setLayoutData(twd_nameTxt);
		// company vat id
		final Label vatLbl = toolkit.createLabel(sectionGeneralClient,
				"Vat ID:");
		final Text vatTxt = toolkit.createText(sectionGeneralClient,
				this.company.getVatid(), SWT.BORDER);
		vatTxt.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				company.setVatid(vatTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_vatTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_vatTxt.colspan = 3;
		vatTxt.setLayoutData(twd_vatTxt);
		// Company telephone
		final Label telLbl = toolkit.createLabel(sectionGeneralClient,
				"Telephone:");
		final Text telTxt = toolkit.createText(sectionGeneralClient,
				this.company.getTelephone(), SWT.BORDER);
		telTxt.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				company.setTelephone(telTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_telTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_telTxt.colspan = 3;
		telTxt.setLayoutData(twd_telTxt);
		// Person mobile
		final Label mobileLbl = toolkit.createLabel(sectionGeneralClient,
				"Mobile:");
		final Text mobileTxt = toolkit.createText(sectionGeneralClient,
				this.company.getMobile(), SWT.BORDER);
		mobileTxt.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				company.setMobile(mobileTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_mobileTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_mobileTxt.colspan = 3;
		mobileTxt.setLayoutData(twd_mobileTxt);
		// Company email
		final Label emailLbl = toolkit.createLabel(sectionGeneralClient,
				"Email:");
		final Text emailTxt = toolkit.createText(sectionGeneralClient,
				this.company.getEmail(), SWT.BORDER);
		emailTxt.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				company.setEmail(nameTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_emailTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_emailTxt.colspan = 3;
		emailTxt.setLayoutData(twd_emailTxt);
		// Company www
		final Label wwwLbl = toolkit.createLabel(sectionGeneralClient,
				"Website:");
		final Text wwwTxt = toolkit.createText(sectionGeneralClient,
				this.company.getWww(), SWT.BORDER);
		wwwTxt.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				company.setWww(wwwTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_wwwTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_wwwTxt.colspan = 3;
		wwwTxt.setLayoutData(twd_wwwTxt);

		sectionGeneral.setClient(sectionGeneralClient);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionGeneral.setLayoutData(data);
		// company addresses section
		Section sectionAddresses = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | ExpandableComposite.TWISTIE
						| ExpandableComposite.EXPANDED);
		sectionAddresses.setText("Addreses:");
		sectionAddresses.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionAddresses);
		createSectionAddressToolbar(sectionAddresses, toolkit);

		sectionAddresses.setDescription("Addreses List:");
		Composite sectionAddressClient = toolkit
				.createComposite(sectionAddresses);

		TableWrapLayout twLayoutSectionItems = new TableWrapLayout();
		twLayoutSectionItems.numColumns = 2;
		sectionAddressClient.setLayout(twLayoutSectionItems);

		createTableViewer(sectionAddressClient);

		sectionAddresses.setClient(sectionAddressClient);
		data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionAddresses.setLayoutData(data);
	}

	private void createTableViewer(Composite parent) {
		addressTableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, addressTableViewer);
		final Table table = addressTableViewer.getTable();
		TableWrapData twd_table = new TableWrapData(TableWrapData.LEFT,
				TableWrapData.TOP, 1, 1);
		twd_table.grabVertical = true;
		table.setLayoutData(twd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		addressTableViewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		addressTableViewer.setInput(this.company.getAddresses());
		// Make the selection available to other views
		getSite().setSelectionProvider(addressTableViewer);

		TableWrapData twd = new TableWrapData(TableWrapData.FILL_GRAB);
		twd.colspan = 2;
		twd.heightHint = 200;
		addressTableViewer.getControl().setLayoutData(twd);

	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Main", "Street", "House/Flat Number", "City",
				"Post Code", "Country", "Notes" };
		int[] bounds = { 100, 250, 150, 100, 100, 100, 100 };

		// selected
		TableViewerColumn col = createTableViewerColumn(addressTableViewer,
				titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

			@Override
			public Image getImage(Object element) {
				if (((Address) element).isMain()) {
					return IImageKeys.CHECKED;
				} else {
					return IImageKeys.UNCHECKED;
				}
			}
		});
		col.setEditingSupport(new CompanyAddressSelectedES(viewer));
		// street
		col = createTableViewerColumn(addressTableViewer, titles[1], bounds[1],
				1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Address a = (Address) element;
				return a.getStreet();
			}
		});

		// house number / flat number
		col = createTableViewerColumn(addressTableViewer, titles[2], bounds[2],
				2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Address a = (Address) element;
				return a.getHouseNumber() + "/" + a.getFlatNumber();
			}
		});
		// city
		col = createTableViewerColumn(addressTableViewer, titles[3], bounds[3],
				3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Address a = (Address) element;
				return a.getCity();
			}
		});
		// post code
		col = createTableViewerColumn(addressTableViewer, titles[4], bounds[4],
				4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Address a = (Address) element;
				return a.getPostCode();
			}
		});
		// country
		col = createTableViewerColumn(addressTableViewer, titles[5], bounds[5],
				5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Address a = (Address) element;
				return a.getCountry();
			}
		});
		// notes
		col = createTableViewerColumn(addressTableViewer, titles[6], bounds[6],
				6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Address a = (Address) element;
				return a.getNotes();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(TableViewer viewer,
			String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	protected void createSectionAddressToolbar(Section section,
			FormToolkit toolkit) {
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
		toolBarManager.add(new EditItemControlContribution());
		toolBarManager.add(new DeleteItemControlContribution());
		toolBarManager.update(true);
		section.setTextClient(toolbar);
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	private class AddItemControlContribution extends ControlContribution {
		protected AddItemControlContribution() {
			super("Add");
		}

		@Override
		protected Control createControl(Composite parent) {
			Button button = new Button(parent, SWT.PUSH);
			button.setImage(ResourceManager.getPluginImage(
					"com.softberries.klerk", "icons/png/add.png"));

			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					AddressDialog dialog = new AddressDialog(PlatformUI
							.getWorkbench().getActiveWorkbenchWindow()
							.getShell(), new Address());
					Address adr = dialog.getAddressFromDialog();
					if(adr == null){
						return;
					}
					adr.setMain(true);// set as main address by default
					if (company.getAddresses() == null) {
						company.setAddresses(new ArrayList<Address>());
					}
					for (Address adrs : company.getAddresses()) {
						adrs.setMain(false);
					}
					company.getAddresses().add(adr);
					addressTableViewer.setInput(company.getAddresses());
					dirty = true;
					firePropertyChange(ISaveablePart.PROP_DIRTY);
				}
			});
			return button;
		}

	}

	private class DeleteItemControlContribution extends ControlContribution {

		protected DeleteItemControlContribution() {
			super("Delete");
		}

		@Override
		protected Control createControl(Composite parent) {
			Button button = new Button(parent, SWT.PUSH);
			button.setImage(ResourceManager.getPluginImage(
					"com.softberries.klerk", "icons/png/remove.png"));

			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (currentAddress != null) {
						Shell shell = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell();
						boolean confirmed = MessageDialog
								.openConfirm(shell, "Confirm",
										"Are you sure you want to delete this address?");
						if (confirmed) {
							company.getAddresses().remove(currentAddress);
							currentAddress = null;
							addressTableViewer.refresh();
							dirty = true;
							firePropertyChange(ISaveablePart.PROP_DIRTY);
						}
					}
				}
			});
			return button;
		}

	}

	private class EditItemControlContribution extends ControlContribution {

		protected EditItemControlContribution() {
			super("Edit");
		}

		@Override
		protected Control createControl(Composite parent) {
			Button button = new Button(parent, SWT.PUSH);
			button.setImage(ResourceManager.getPluginImage(
					"com.softberries.klerk", "icons/png/edit.png"));

			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (currentAddress != null) {
						AddressDialog dialog = new AddressDialog(PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getShell(), currentAddress);
						Address adr = dialog.getAddressFromDialog();
						dirty = true;
						firePropertyChange(ISaveablePart.PROP_DIRTY);
						addressTableViewer.setInput(company.getAddresses());
						addressTableViewer.refresh();
					}
				}
			});
			return button;
		}

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		System.out.println("SELECTION SCE: " + sel + "PART: " + part);
		Object selection = ((IStructuredSelection) sel).getFirstElement();
		if (selection != null && selection instanceof Address) {
			Address adr = (Address) selection;
			this.currentAddress = adr;
			
			if (adr.isMain()) {
				for (Address a : company.getAddresses()) {
					if (!a.equals(adr)) {
						dirty = true;
						firePropertyChange(ISaveablePart.PROP_DIRTY);
						a.setMain(false);
						if (addressTableViewer != null
								&& !addressTableViewer.getControl()
										.isDisposed()) {
							addressTableViewer.refresh();
						}

					}
				}
			}
		}
	}

	@Override
	protected void enableSave(boolean drt) {
		// TODO implement enableSave for company editor
		
	}

}
