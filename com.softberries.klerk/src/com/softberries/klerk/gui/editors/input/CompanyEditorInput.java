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
package com.softberries.klerk.gui.editors.input;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.softberries.klerk.dao.to.Company;

public class CompanyEditorInput implements IEditorInput {

	private Company company;
	
	public CompanyEditorInput(Company c){
		this.company = c;
	}
	@Override
	public Object getAdapter(Class adapter) {
		return this.company;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return this.company.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return this.company.getName();
	}
	public Company getCompany() {
		return company;
	}
	public void setProduct(Company c) {
		this.company = c;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof CompanyEditorInput){
			CompanyEditorInput p = (CompanyEditorInput)arg0;
			boolean nameIsSame = this.getCompany().getName().equals(p.getCompany().getName());
			boolean vatidIsSame = this.getCompany().getVatid().equals(p.getCompany().getVatid());
			return nameIsSame && vatidIsSame;
		}else{
			return super.equals(arg0);
		}
	}
	
}
