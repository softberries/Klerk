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
	@Override
	public String toString() {
		return getName();
	}
	public String getIconID() {
		return iconID;
	}
}
