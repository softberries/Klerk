package com.softberries.klerk.gui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.dao.to.Product;

public class SingleCompanyEditor extends SingleObjectEditor {

	public static final String ID = "com.softberries.klerk.gui.editors.SingleCompanyEditor";
	private Company company;
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
