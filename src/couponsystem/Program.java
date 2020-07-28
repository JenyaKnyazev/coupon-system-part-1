package couponsystem;

import java.sql.SQLException;
import java.util.Date;

import couponsystem.dao.CompaniesDAO;
import couponsystem.dao.impl.CompaniesDBDAO;
import couponsystem.exceptions.CouponAlredyExsitsException;
import couponsystem.exceptions.CouponExpiredException;
import couponsystem.exceptions.CouponOutOfStockException;
import couponsystem.exceptions.ValidationException;
import couponsystem.job.CouponExpirationDailyJob;
import couponsystem.models.Company;

public class Program {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException, ValidationException, CouponOutOfStockException, CouponExpiredException, CouponAlredyExsitsException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Database.deleteTableUsers();
		Database.createTableUsers();
		
		
		Test.testAll();
	}
}
