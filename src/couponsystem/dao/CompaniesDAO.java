package couponsystem.dao;

import java.util.ArrayList;

import couponsystem.models.Company;

public interface CompaniesDAO {
	boolean isCompanyExists(String email, String password);
	void addCompany(Company company);
	void updateCompany(Company company);
	void deleteCompany(int companyID);
	ArrayList<Company> getAllCompanies();
	Company getOneCompany(int companyId);
}
