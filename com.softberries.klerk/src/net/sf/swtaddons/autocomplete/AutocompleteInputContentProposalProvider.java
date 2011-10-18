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
package net.sf.swtaddons.autocomplete;


public class AutocompleteInputContentProposalProvider extends AutocompleteContentProposalProvider {

	/**
	 * Construct a ContentProposalProvider whose content proposals are
	 * the specified array of Objects.  This ContentProposalProvider will
	 * SUGGEST a completion for the input but will not force the input
	 * to be one of the proposals
	 * 
	 * @param proposals
	 *            the array of Strings to be returned whenever proposals are
	 *            requested.
	 */
	public AutocompleteInputContentProposalProvider(String[] proposals) {
		super(proposals);
	}

}
