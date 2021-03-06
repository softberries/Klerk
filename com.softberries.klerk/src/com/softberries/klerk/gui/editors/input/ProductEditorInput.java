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

import com.softberries.klerk.dao.to.Product;

public class ProductEditorInput implements IEditorInput {

	private Product product;
	
	public ProductEditorInput(Product p){
		this.product = p;
	}
	@Override
	public Object getAdapter(Class adapter) {
		return this.product;
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
		return this.product.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return this.product.getName();
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof ProductEditorInput){
			ProductEditorInput p = (ProductEditorInput)arg0;
			return this.getProduct().getCode().equals(p.getProduct().getCode());
		}else{
			return super.equals(arg0);
		}
	}
	
}
