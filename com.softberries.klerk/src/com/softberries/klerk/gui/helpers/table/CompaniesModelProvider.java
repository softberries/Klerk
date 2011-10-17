package com.softberries.klerk.gui.helpers.table;

import java.sql.SQLException;
import java.util.List;

import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.gui.editors.GenericKlerkEditor;

public enum CompaniesModelProvider {
	INSTANCE;

	private List<Company> companies;

	private CompaniesModelProvider() {
		try {
			companies = new CompanyDao(GenericKlerkEditor.DB_FOLDER_PATH).findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Company> getCompanies() {
		return companies;
	}

}
