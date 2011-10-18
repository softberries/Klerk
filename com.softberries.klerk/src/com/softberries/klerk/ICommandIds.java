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
package com.softberries.klerk;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 * @author krzysztof.grajek@softberries.com
 */
public interface ICommandIds {

	/**
	 * Command id used for opening list of sales documents
	 */
    public static final String CMD_OPEN_DOCUMENTS_SALES = "com.softberries.klerk.commands.opendocuments_sales";
    /**
     * Command id used for opening list of purchase documents
     */
    public static final String CMD_OPEN_DOCUMENTS_PURCHASES = "com.softberries.klerk.commands.opendocuments_purchases";
    /**
     * Command id used for opening list of products
     */
    public static final String CMD_OPEN_INVENTORY_PRODUCTS = "com.softberries.klerk.commands.openinventory_products";
    /**
     * Command id used for opening list of people
     */
    public static final String CMD_OPEN_ADMINISTRATION_PEOPLE = "com.softberries.klerk.commands.openadministration_people";
    /**
     * Command id used for opening companies list
     */
    public static final String CMD_OPEN_INVENTORY_COMPANIES = "com.softberries.klerk.commands.openinventory_companies";
}
