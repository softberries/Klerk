package com.softberries.klerk.gui.helpers;

import java.util.ArrayList;
import java.util.List;

import com.softberries.klerk.Activator;
import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.gui.helpers.table.CompaniesModelProvider;
import com.softberries.klerk.preferences.IPreferencesKeys;

/**
 * This is a helper class, it creates special
 * {@code Company} objects using for eg. stored preferences
 * @author kris
 *
 */
public class CompanyFactory {

	public Company getCompanyFromPreferences(){
		Company c = new Company();
		Address adr = new Address();
		c.setName(Activator.getDefault().getPreferenceStore().getString(IPreferencesKeys.COMPANY_NAME));
		c.setVatid(Activator.getDefault().getPreferenceStore().getString(IPreferencesKeys.COMPANY_VATID));
		adr.setCity(Activator.getDefault().getPreferenceStore().getString(IPreferencesKeys.COMPANY_CITY));
		adr.setCountry(Activator.getDefault().getPreferenceStore().getString(IPreferencesKeys.COMPANY_COUNTRY));
		adr.setFlatNumber(Activator.getDefault().getPreferenceStore().getString(IPreferencesKeys.COMPANY_FLATNR));
		adr.setHouseNumber(Activator.getDefault().getPreferenceStore().getString(IPreferencesKeys.COMPANY_HOUSENR));
		adr.setMain(true);
		adr.setPostCode(Activator.getDefault().getPreferenceStore().getString(IPreferencesKeys.COMPANY_POSTCODE));
		adr.setStreet(Activator.getDefault().getPreferenceStore().getString(IPreferencesKeys.COMPANY_STREET));
		c.setAddress(adr);
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(adr);
		c.setAddresses(addresses);
		return c;
	}
	public Company getCompanyByName(String fullName){
		for(Company c : CompaniesModelProvider.INSTANCE.getCompanies()){
			if(c.toString().equals(fullName)){
				return c;
			}
		}
		return null;
	}
}
