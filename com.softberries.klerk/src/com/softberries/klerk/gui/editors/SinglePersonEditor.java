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
import java.util.ArrayList;

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
import com.softberries.klerk.dao.PeopleDao;
import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.dialogs.AddressDialog;
import com.softberries.klerk.gui.helpers.IImageKeys;
import com.softberries.klerk.gui.helpers.table.PeopleModelProvider;
import com.softberries.klerk.gui.helpers.table.editingsupport.CompanyAddressSelectedES;
import com.softberries.klerk.gui.validators.FieldNotEmptyValidator;

public class SinglePersonEditor extends SingleObjectEditor implements
ISelectionListener{

	public static final String ID = "com.softberries.klerk.gui.editors.SinglePersonEditor";
	private Person person;
	private Address currentAddress;
	private TableViewer addressTableViewer;
	
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		PeopleDao dao = new PeopleDao(GenericKlerkEditor.DB_FOLDER_PATH);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmail(email);
		try {
			if(person.getId() == null){
				dao.create(person);
				PeopleModelProvider.INSTANCE.getPeople().add(person);
			}else{
				LogUtil.logInfo("Updating person: " + person.getId());
				dao.update(person);
			}
			
		} catch (SQLException e) {
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, "Sorry, couldn't update companies database.", e);
			StatusManager.getManager().handle(status, StatusManager.SHOW);
		}
		enableSave(false);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		person = (Person) input.getAdapter(Product.class);
		setPartName(person.getFirstName() + " " + person.getLastName());
		if (person.getAddresses() == null) {
			person.setAddresses(new ArrayList<Address>());
		}
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getActiveWorkbenchWindow().getActivePage()
				.addSelectionListener(this);
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.email = person.getEmail();
	}

	@Override
	public void createPartControl(Composite parent) {
		form = toolkit.createScrolledForm(parent);
		form.setText(this.getPerson().getFullName());
		TableWrapLayout twlayout = new TableWrapLayout();
		twlayout.numColumns = 2;
		form.getBody().setLayout(twlayout);

		// general section
		Section sectionGeneral = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		sectionGeneral.setText("General Info");
		sectionGeneral.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});

		toolkit.createCompositeSeparator(sectionGeneral);
		createSectionToolbar(sectionGeneral, toolkit);

		sectionGeneral.setDescription("Personal data:");
		Composite sectionGeneralClient = toolkit
				.createComposite(sectionGeneral);
		TableWrapLayout twLayoutSectionGeneral = new TableWrapLayout();
		twLayoutSectionGeneral.numColumns = 4;
		sectionGeneralClient.setLayout(twLayoutSectionGeneral);
		// Person first name
		final Label fnLbl = toolkit.createLabel(sectionGeneralClient, "First Name:");
		final Text fnTxt = toolkit.createText(sectionGeneralClient, this.person.getFirstName(), SWT.BORDER);
		bindValidator(fnTxt, person, "firstName", new FieldNotEmptyValidator("This field cannot be empty!")); //$NON-NLS-1$
		
		fnTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				firstName = fnTxt.getText();
				setPartName(firstName + " " + lastName);
				enableSave(true);
			}
		});
		TableWrapData twd_codeTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_codeTxt.colspan = 3;
		twd_codeTxt.indent = 5;
		fnTxt.setLayoutData(twd_codeTxt);
		// person Last Name
		final Label lnLbl = toolkit.createLabel(sectionGeneralClient, "Last Name:");
		final Text lnTxt = toolkit.createText(sectionGeneralClient,
				this.person.getLastName(), SWT.BORDER);
		bindValidator(lnTxt, person, "lastName", new FieldNotEmptyValidator("This field cannot be empty!")); //$NON-NLS-1$
		
		lnTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				lastName = lnTxt.getText();
				setPartName(firstName + " " + lastName);
				enableSave(true);
			}
		});
		TableWrapData twd_titleTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_titleTxt.colspan = 3;
		twd_titleTxt.indent = 5;
		lnTxt.setLayoutData(twd_titleTxt);
		//Person telephone
		final Label telLbl = toolkit.createLabel(sectionGeneralClient, "Telephone:");
		final Text telTxt = toolkit.createText(sectionGeneralClient, this.person.getTelephone(), SWT.BORDER);
		telTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				person.setTelephone(telTxt.getText());
				enableSave(true);
			}
		});
		TableWrapData twd_telTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_telTxt.colspan = 3;
		twd_telTxt.indent = 5;
		telTxt.setLayoutData(twd_telTxt);
		//Person mobile
		final Label mobileLbl = toolkit.createLabel(sectionGeneralClient, "Mobile:");
		final Text mobileTxt = toolkit.createText(sectionGeneralClient, this.person.getMobile(), SWT.BORDER);
		mobileTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				person.setMobile(mobileTxt.getText());
				enableSave(true);
			}
		});
		TableWrapData twd_mobileTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_mobileTxt.colspan = 3;
		twd_mobileTxt.indent = 5;
		mobileTxt.setLayoutData(twd_mobileTxt);
		//Person email
		final Label emailLbl = toolkit.createLabel(sectionGeneralClient, "Email:");
		final Text emailTxt = toolkit.createText(sectionGeneralClient, this.person.getEmail(), SWT.BORDER);
		bindValidator(emailTxt, person, "email", new FieldNotEmptyValidator("This field cannot be empty!")); //$NON-NLS-1$
		
		emailTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				email = emailTxt.getText();
				enableSave(true);
			}
		});
		TableWrapData twd_emailTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_emailTxt.colspan = 3;
		twd_emailTxt.indent = 5;
		emailTxt.setLayoutData(twd_emailTxt);
		//Person www
		final Label wwwLbl = toolkit.createLabel(sectionGeneralClient, "Website:");
		final Text wwwTxt = toolkit.createText(sectionGeneralClient, this.person.getWww(), SWT.BORDER);
		wwwTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				person.setWww(wwwTxt.getText());
				enableSave(true);
			}
		});
		TableWrapData twd_wwwTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_wwwTxt.colspan = 3;
		twd_wwwTxt.indent = 5;
		wwwTxt.setLayoutData(twd_wwwTxt);
		
		sectionGeneral.setClient(sectionGeneralClient);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionGeneral.setLayoutData(data);
		// person addresses section
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
		addressTableViewer.setInput(this.person.getAddresses());
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
				String notes = a.getNotes();
				notes = notes.replaceAll("\\r\\n|\\r|\\n", " ");
				return notes;
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
					if (person.getAddresses() == null) {
						person.setAddresses(new ArrayList<Address>());
					}
					for (Address adrs : person.getAddresses()) {
						adrs.setMain(false);
					}
					person.getAddresses().add(adr);
					addressTableViewer.setInput(person.getAddresses());
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
							person.getAddresses().remove(currentAddress);
							currentAddress = null;
							addressTableViewer.refresh();
							enableSave(true);
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
						if(adr != null){
							enableSave(true);
							addressTableViewer.setInput(person.getAddresses());
							addressTableViewer.refresh();
						}
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
		if (selection != null && selection instanceof Address && part instanceof SinglePersonEditor) {
			Address adr = (Address) selection;
			this.currentAddress = adr;
			if(person.getAddresses().size() == 1){
				adr.setMain(true);
				addressTableViewer.refresh();
				return;
			}
			if (adr.isMain()) {
				for (Address a : person.getAddresses()) {
					if (!a.equals(adr)) {
						enableSave(true);
						a.setMain(false);

					}
				}
			}
			if (addressTableViewer != null
					&& !addressTableViewer.getControl()
							.isDisposed()) {
				addressTableViewer.refresh();
			}
		}
	}
	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	protected void enableSave(boolean drt) {
		if(drt &&
				!firstName.isEmpty() &&
				!lastName.isEmpty() &&
				!email.isEmpty() &&
				person.getAddresses().size() > 0
		){
			dirty = drt;
			//notify editor that its dirty/not dirty
			firePropertyChange(ISaveablePart.PROP_DIRTY);
		}else{
			dirty = false;
			firePropertyChange(ISaveablePart.PROP_DIRTY);
		}
	}

}
