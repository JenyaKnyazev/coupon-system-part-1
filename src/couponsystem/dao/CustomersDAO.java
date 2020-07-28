package couponsystem.dao;

import java.util.ArrayList;

import couponsystem.models.Customer;

public interface CustomersDAO {
	boolean isCustomerExists(String email,String password);
	void addCustomer(Customer customer);
	void updateCustomer(Customer customer);
	void deleteCustomer(int customerID);
	ArrayList<Customer> getAllCustomers();
	Customer getOneCustomer(int customerID);
}
