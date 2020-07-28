package couponsystem.facade;

import java.awt.List;
import java.util.ArrayList;

import couponsystem.dao.CouponsDAO;
import couponsystem.exceptions.ValidationException;
import couponsystem.models.Company;
import couponsystem.models.Coupon;
import couponsystem.models.Customer;

public class AdminFacade extends ClientFacade {
	private final static String EMAIL = "admin@admin.com";
	private final static String PASSWORD = "admin";

	public boolean login(String email, String password) {
		return email.equals(EMAIL) && password.equals(PASSWORD);
	}

	public void addCompany(Company company) throws ValidationException {
		ArrayList<Company> list = companiesDAO.getAllCompanies();
		for (Company i : list)
			if (i.getEmail().equals(company.getEmail()) || i.getName().equals(company.getName()))
				throw new ValidationException("Email or name alredy exist");
		companiesDAO.addCompany(company);
	}

	public void updateCompany(Company company) {
		Company existingCompany = companiesDAO.getOneCompany(company.getId());

		company.setName(existingCompany.getName());

		companiesDAO.updateCompany(company);
	}

	public void deleteCompany(int companyID) {
		ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
		for (Coupon i : coupons)
			if (i.getCompanyId() == companyID) {
				couponsDAO.deleteCouponPurchase(i.getId());
				couponsDAO.deleteCoupon(i.getId());
			}
		companiesDAO.deleteCompany(companyID);
	}

	public ArrayList<Company> getAllCompanies() {
		return companiesDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyID) {
		return companiesDAO.getOneCompany(companyID);
	}

	public void addCustomer(Customer customer) throws ValidationException {
		ArrayList<Customer> list = customersDAO.getAllCustomers();
		for (Customer i : list)
			if (i.getEmail().equals(customer.getEmail()))
				throw new ValidationException("Alredy have a customer with that email");
		customersDAO.addCustomer(customer);
	}

	public void updateCustomer(Customer customer) {
		customersDAO.updateCustomer(customer);
	}

	public void deleteCustomer(int customerID) {
		couponsDAO.deleteCouponPurchaseByCustomerId(customerID);
		customersDAO.deleteCustomer(customerID);
	}

	public ArrayList<Customer> getAllCustomers() {
		return customersDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerID) {
		return customersDAO.getOneCustomer(customerID);
	}
}
