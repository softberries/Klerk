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

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public class FieldNotEmptyValidator implements IValidator{

	private String msg;
	
	public FieldNotEmptyValidator(String msg){
		this.msg = msg;
	}
	@Override
	public IStatus validate(Object value) {
		String s = String.valueOf(value);
		if(s == null || s.length() == 0){
			return ValidationStatus.error(msg);
		}else{
			return ValidationStatus.ok();
		}
	}

}
