package com.softberries.klerk.gui.helpers.table;

import java.util.List;

import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.to.Company;

public enum CompaniesModelProvider {
	INSTANCE;

	private List<Company> companies;

	private CompaniesModelProvider() {
		companies = new CompanyDao().findAllCompanies();
	}

	public List<Company> getCompanies() {
		return companies;
	}

}
