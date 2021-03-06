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
package net.sf.swtaddons.autocomplete.text;

import org.eclipse.swt.widgets.Text;

import net.sf.swtaddons.autocomplete.AutocompleteContentProposalProvider;
import net.sf.swtaddons.autocomplete.AutocompleteSelectorContentProposalProvider;

public class AutocompleteTextSelector extends AutocompleteText {

	public AutocompleteTextSelector(Text text, String[] selectionItems) {
		super(text, selectionItems);
	}

	@Override
	protected AutocompleteContentProposalProvider getContentProposalProvider(String[] proposals) {
		return new AutocompleteSelectorContentProposalProvider(proposals, this.text);
	}

}
