package couponsystem.dao.impl;

import java.util.ArrayList;

import couponsystem.ConnectionPool;
import couponsystem.dao.CompaniesDAO;
import couponsystem.models.Company;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompaniesDBDAO implements CompaniesDAO {
	private final ConnectionPool _connectionPool;

	public CompaniesDBDAO(ConnectionPool connectionPool) {
		this._connectionPool = connectionPool;
	}

	@Override
	public boolean isCompanyExists(String email, String password) {
		Integer companyId = null;

		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "select id from Companies where Email = ? AND Password= ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				companyId = resultSet.getInt(1);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}

		return companyId != null;
	}

	@Override
	public void addCompany(Company company) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "INSERT INTO Companies (Name,Email,Password) VALUES (?,?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());

			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void updateCompany(Company company) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "UPDATE Companies SET Name=?,Email=?,Password=? WHERE Id=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.setInt(4, company.getId());

			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
 
	}

	@Override
	public void deleteCompany(int companyID) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "DELETE from COMPANIES where id =?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);

			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
	}

	@Override
	public ArrayList<Company> getAllCompanies() {
		
		ArrayList<Company> companies = new ArrayList<Company>();
		
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "SELECT * FROM companies";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Company company = new Company();
				company.setId(resultSet.getInt(1));
				company.setName(resultSet.getString(2));
				company.setEmail(resultSet.getString(3));
				company.setPassword(resultSet.getString(4));
				companies.add(company);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		
		return companies;
	}

	@Override
	public Company getOneCompany(int companyId) {
		Company company = null;
		Connection connection = null;
		
		try {
			connection = _connectionPool.getConnection();

			String sql = "SELECT * FROM companies where id=?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyId);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				company=new Company();
				company.setId(resultSet.getInt(1));
				company.setName(resultSet.getString(2));
				company.setEmail(resultSet.getString(3));
				company.setPassword(resultSet.getString(4));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		
		return company;
	}
	
}
