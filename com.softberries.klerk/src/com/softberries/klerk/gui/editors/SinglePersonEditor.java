package com.softberries.klerk.gui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.helpers.Messages;

public class SinglePersonEditor extends SingleObjectEditor{

	public static final String ID = "com.softberries.klerk.gui.editors.SinglePersonEditor";
	private Person person;
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		person = (Person) input.getAdapter(Product.class);
		setPartName(person.getFirstName() + " " + person.getLastName());
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
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionGeneral.setText("General Info");
		sectionGeneral.addExpansionListener(new ExpansionAdapter() {
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
		fnTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				person.setFirstName(fnTxt.getText());
				setPartName(person.getFullName());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_codeTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_codeTxt.colspan = 3;
		fnTxt.setLayoutData(twd_codeTxt);
		// person Last Name
		final Label lnLbl = toolkit.createLabel(sectionGeneralClient, "Last Name:");
		final Text lnTxt = toolkit.createText(sectionGeneralClient,
				this.person.getLastName(), SWT.BORDER);
		lnTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				person.setLastName(lnTxt.getText());
				form.setText(person.getFullName());
				setPartName(person.getFullName());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_titleTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_titleTxt.colspan = 3;
		lnTxt.setLayoutData(twd_titleTxt);
		//Person telephone
		final Label telLbl = toolkit.createLabel(sectionGeneralClient, "Telephone:");
		final Text telTxt = toolkit.createText(sectionGeneralClient, this.person.getTelephone(), SWT.BORDER);
		telTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				person.setTelephone(telTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_telTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_telTxt.colspan = 3;
		telTxt.setLayoutData(twd_telTxt);
		//Person mobile
		final Label mobileLbl = toolkit.createLabel(sectionGeneralClient, "Mobile:");
		final Text mobileTxt = toolkit.createText(sectionGeneralClient, this.person.getMobile(), SWT.BORDER);
		mobileTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				person.setMobile(mobileTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_mobileTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_mobileTxt.colspan = 3;
		mobileTxt.setLayoutData(twd_mobileTxt);
		//Person email
		final Label emailLbl = toolkit.createLabel(sectionGeneralClient, "Email:");
		final Text emailTxt = toolkit.createText(sectionGeneralClient, this.person.getEmail(), SWT.BORDER);
		emailTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				person.setEmail(fnTxt.getText());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_emailTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_emailTxt.colspan = 3;
		emailTxt.setLayoutData(twd_emailTxt);
		//Person www
		final Label wwwLbl = toolkit.createLabel(sectionGeneralClient, "Website:");
		final Text wwwTxt = toolkit.createText(sectionGeneralClient, this.person.getWww(), SWT.BORDER);
		wwwTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				person.setWww(wwwTxt.getText());
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
		// person addresses section
		Section sectionDescription = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionDescription.setText("Addresses:");
		sectionDescription.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionDescription);
		sectionDescription.setDescription("Addresses List:");
		Composite sectionDescClient = toolkit.createComposite(sectionDescription);
		TableWrapLayout twLayoutSectionDesc = new TableWrapLayout();
		twLayoutSectionDesc.numColumns = 1;
		sectionDescClient.setLayout(twLayoutSectionDesc);
		final Text mainAddressTxt = toolkit.createText(sectionDescClient,
				"some main address", SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		mainAddressTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				//TO-DO fix addresses section
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_descTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_descTxt.heightHint = 180;
		twd_descTxt.grabVertical = true;
		twd_descTxt.colspan = 1;
		mainAddressTxt.setLayoutData(twd_descTxt);
		sectionDescription.setClient(sectionDescClient);
		data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionDescription.setLayoutData(data);
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

}
