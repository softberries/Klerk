package com.softberries.klerk;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

    public static final String CMD_OPEN_DOCUMENTS_SALES = "com.softberries.klerk.commands.opendocuments_sales";
    public static final String CMD_OPEN_DOCUMENTS_PURCHASES = "com.softberries.klerk.commands.opendocuments_purchases";
    public static final String CMD_OPEN_INVENTORY_PRODUCTS = "com.softberries.klerk.commands.openinventory_products";
    public static final String CMD_OPEN_ADMINISTRATION_PEOPLE = "com.softberries.klerk.commands.openadministration_people";
    public static final String CMD_OPEN_INVENTORY_COMPANIES = "com.softberries.klerk.commands.openinventory_companies";
}
