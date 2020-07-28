package couponsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import couponsystem.ConnectionPool;
import couponsystem.dao.CustomersDAO;
import couponsystem.models.Company;
import couponsystem.models.Customer;

public class CustomersDBDAO implements CustomersDAO {
	ConnectionPool _connectionPool;
	
	public CustomersDBDAO(ConnectionPool connectionPool) {
		this._connectionPool=connectionPool;
	}
	
	@Override
	public boolean isCustomerExists(String email, String password) {
		Integer customerId = null;

		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "select id from Customers where Email = ? AND Password= ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				customerId = resultSet.getInt(1);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		return customerId!=null;
	}

	@Override
	public void addCustomer(Customer customer) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "INSERT INTO Customers (FirstName,LastName,Email,Password) VALUES (?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		
	}

	@Override
	public void updateCustomer(Customer customer) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "UPDATE Customers SET FirstName=?,LastName=?,Email=?,Password=? WHERE Id=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setInt(5, customer.getId());
			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		
	}

	@Override
	public void deleteCustomer(int customerID) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "DELETE from Customers where id =?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);

			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "SELECT * FROM customers";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getInt(1));
				customer.setFirstName(resultSet.getString(2));
				customer.setLastName(resultSet.getString(3));
				customer.setEmail(resultSet.getString(4));
				customer.setPassword(resultSet.getString(5));
				customers.add(customer);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		
		return customers;
	}

	@Override
	public Customer getOneCustomer(int customerID) {
		Customer customer = null;
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "SELECT * FROM customers where id=?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				customer=new Customer();
				customer.setId(resultSet.getInt(1));
				customer.setFirstName(resultSet.getString(2));
				customer.setLastName(resultSet.getString(3));
				customer.setEmail(resultSet.getString(4));
				customer.setPassword(resultSet.getString(5));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		return customer;
	}

}
