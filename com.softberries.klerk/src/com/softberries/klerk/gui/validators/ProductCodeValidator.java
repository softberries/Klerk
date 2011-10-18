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
package com.softberries.klerk.gui.validators;

import java.util.List;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public class ProductCodeValidator implements IValidator{

	private List<String> productCodes;
	private String oldProductCode;
	
	public ProductCodeValidator(List<String> codes, String oldProductCode){
		this.productCodes = codes;
		this.oldProductCode = oldProductCode;
	}
	@Override
	public IStatus validate(Object value) {
		String s = String.valueOf(value);
		for(String p : productCodes){
			if(p.equalsIgnoreCase(s) && !p.equalsIgnoreCase(oldProductCode)){
				return ValidationStatus.error("Product code: " + s + " already exists!");
			}
		}
		return ValidationStatus.ok();
		
	}

}
