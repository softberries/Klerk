package com.softberries.klerk.gui.helpers.table;

import java.sql.SQLException;
import java.util.List;

import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.to.Company;

public enum CompaniesModelProvider {
	INSTANCE;

	private List<Company> companies;

	private CompaniesModelProvider() {
		try {
			companies = new CompanyDao().findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Company> getCompanies() {
		return companies;
	}

}
