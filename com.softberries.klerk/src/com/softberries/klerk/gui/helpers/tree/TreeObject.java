package com.softberries.klerk.gui.helpers.tree;


public class TreeObject {
	private String name;
	private String iconID;
	private TreeParent parent;
	
	public TreeObject(String name, String iconId) {
		this.name = name;
		this.iconID = iconId;
	}
	public String getName() {
		return name;
	}
	public void setParent(TreeParent parent) {
		this.parent = parent;
	}
	public TreeParent getParent() {
		return parent;
	}
	public String toString() {
		return getName();
	}
	public String getIconID() {
		return iconID;
	}
}