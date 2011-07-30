package com.softberries.klerk.gui.editors;

import java.sql.SQLException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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
import org.eclipse.ui.statushandlers.StatusManager;

import com.softberries.klerk.Activator;
import com.softberries.klerk.LogUtil;
import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.gui.helpers.table.CompaniesModelProvider;

public class SingleCompanyEditor extends SingleObjectEditor {

	public static final String ID = "com.softberries.klerk.gui.editors.SingleCompanyEditor";
	private Company company;
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		CompanyDao dao = new CompanyDao();
		try {
			if(company.getId() == null){
				dao.create(company);
				CompaniesModelProvider.INSTANCE.getCompanies().add(company);
			}else{
				LogUtil.logInfo("Updating company: " + company.getId());
				dao.update(company);
			}
			
		} catch (SQLException e) {
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, "Sorry, couldn't update companies database.", e);
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
		setPartName(company.getName());
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
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionGeneral.setText("General Info");
		sectionGeneral.addExpansionListener(new ExpansionAdapter() {
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
		final Label nameLbl = toolkit.createLabel(sectionGeneralClient, "First Name:");
		final Text nameTxt = toolkit.createText(sectionGeneralClient, this.company.getName(), SWT.BORDER);
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
		final Label vatLbl = toolkit.createLabel(sectionGeneralClient, "Vat ID:");
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
		//Company telephone
		final Label telLbl = toolkit.createLabel(sectionGeneralClient, "Telephone:");
		final Text telTxt = toolkit.createText(sectionGeneralClient, this.company.getTelephone(), SWT.BORDER);
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
		//Person mobile
		final Label mobileLbl = toolkit.createLabel(sectionGeneralClient, "Mobile:");
		final Text mobileTxt = toolkit.createText(sectionGeneralClient, this.company.getMobile(), SWT.BORDER);
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
		//Company email
		final Label emailLbl = toolkit.createLabel(sectionGeneralClient, "Email:");
		final Text emailTxt = toolkit.createText(sectionGeneralClient, this.company.getEmail(), SWT.BORDER);
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
		//Company www
		final Label wwwLbl = toolkit.createLabel(sectionGeneralClient, "Website:");
		final Text wwwTxt = toolkit.createText(sectionGeneralClient, this.company.getWww(), SWT.BORDER);
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
		// person addresses section
		Section sectionDescription = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionDescription.setText("Addreses:");
		sectionDescription.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionDescription);
		sectionDescription.setDescription("Addreses List:");
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
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

}
