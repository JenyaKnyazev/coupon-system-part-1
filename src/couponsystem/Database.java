package couponsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static Connection connection;
	private static final String url = "jdbc:mysql://localhost:3306/coupon_system?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC&allowMultiQueries=true";
	private static final String username = "root";
	private static final String password = "1836547290";

	public static String getUrl() {
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}
	
	public static void deleteTableUsers() throws SQLException {

		//Connection connection = null;

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "drop table CustomersVsCoupons;\r\n" + 
					"drop table Coupons;\r\n" + 
					"drop table Categories;\r\n" + 
					"drop table Customers;\r\n" + 
					"drop table Companies;";
			// STEP 3
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	} 

	public static void createTableUsers() throws SQLException {

		//Connection connection = null;

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "create table Companies (\r\n" + 
					"	Id int not null primary key auto_increment,\r\n" + 
					"	Name varchar(100)not null,\r\n" + 
					"    Email varchar(100) not null,\r\n" + 
					"    Password varchar(100)not null\r\n" + 
					");\r\n" + 
					" create table Customers(\r\n" + 
					"	Id int not null primary key auto_increment,\r\n" + 
					"    FirstName varchar(100)not null ,\r\n" + 
					"    LastName varchar(100)not null,\r\n" + 
					"    Email varchar(100)not null,\r\n" + 
					"    Password varchar(100)not null\r\n" + 
					");\r\n" + 
					"create table Categories(\r\n" + 
					"	Id int not null primary key auto_increment,\r\n" + 
					"    Name varchar(100) not null\r\n" + 
					");\r\n" + 
					"create table Coupons(\r\n" + 
					"	Id int not null primary key auto_increment,\r\n" + 
					"    CompanyId int not null,\r\n" + 
					"    CategoryId INT NOT NULL,\r\n" + 
					"    Title VARCHAR(100) NOT NULL,\r\n" + 
					"    Description VARCHAR(250) NOT NULL,\r\n" + 
					"    StartDate DATE NOT NULL,\r\n" + 
					"    EndDate DATE NOT NULL,\r\n" + 
					"    Amount INT NOT NULL,\r\n" + 
					"    Price DOUBLE NOT NULL,\r\n" + 
					"    Image VARCHAR(100) NOT NULL,\r\n" + 
					"	FOREIGN KEY (CompanyId) REFERENCES Companies(Id),\r\n" + 
					"    FOREIGN KEY (CategoryId) REFERENCES Categories(Id)\r\n" + 
					");\r\n" + 
					"CREATE TABLE CustomersVsCoupons(\r\n" + 
					"	CustomerId INT NOT NULL,\r\n" + 
					"    CouponId INT NOT NULL,\r\n" + 
					"	FOREIGN KEY (CustomerId) REFERENCES Customers(Id),\r\n" + 
					"    FOREIGN KEY (CouponId) REFERENCES Coupons(Id),\r\n" + 
					"    PRIMARY KEY (CustomerId, CouponId)\r\n" + 
					");\r\n"
					+ "INSERT INTO Categories(name)values ('Restaurant');\r\n"
					+ "INSERT INTO Categories(name)values ('Coffie');\r\n"
					+ "INSERT INTO Categories(name)values ('Clothes');\r\n"
					+ "INSERT INTO Categories(name)values ('Bar');\r\n";
			// STEP 3
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

}