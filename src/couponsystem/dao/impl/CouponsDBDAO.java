package couponsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import couponsystem.ConnectionPool;
import couponsystem.dao.CouponsDAO;
import couponsystem.models.Coupon;

public class CouponsDBDAO implements CouponsDAO {
	private final ConnectionPool _connectionPool;

	public CouponsDBDAO(ConnectionPool connectionPool) {
		this._connectionPool = connectionPool;
	}

	@Override
	public void addCoupon(Coupon coupon) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "INSERT INTO coupons(CompanyId,CategoryId,Title,Description,StartDate,EndDate,Amount,Price,Image) values (?,?,?,?,?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon.getCompanyId());
			statement.setInt(2, coupon.getCategoryId());
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, convertJavaDateToSqlDate(coupon.getStartDate()));
			statement.setDate(6, convertJavaDateToSqlDate(coupon.getEndDate()));
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());

			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "UPDATE coupons SET CompanyId=?,CategoryId=?,Title=?,Description=?,StartDate=?,EndDate=?,Amount=?,Price=?,Image=? WHERE Id=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon.getCompanyId());
			statement.setInt(2, coupon.getCategoryId());
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, convertJavaDateToSqlDate(coupon.getStartDate()));
			statement.setDate(6, convertJavaDateToSqlDate(coupon.getEndDate()));
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.setInt(10, coupon.getId());
			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void deleteCoupon(int couponID) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "DELETE FROM COUPONS WHERE ID=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
	}

	@Override
	public ArrayList<Coupon> getAllCoupons() {
		ArrayList<Coupon> coupons = new ArrayList<>();
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "SELECT * FROM COUPONS";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getInt(1));
				coupon.setCompanyId(resultSet.getInt(2));
				coupon.setCategoryId(resultSet.getInt(3));
				coupon.setTitle(resultSet.getString(4));
				coupon.setDescription(resultSet.getString(5));
				coupon.setStartDate(convertSQLDateToJavaDate(resultSet.getDate(6)));
				coupon.setEndDate(convertSQLDateToJavaDate(resultSet.getDate(7)));
				coupon.setAmount(resultSet.getInt(8));
				coupon.setPrice(resultSet.getDouble(9));
				coupon.setImage(resultSet.getString(10));
				coupons.add(coupon);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		return coupons;
	}

	@Override
	public Coupon getOneCoupon(int couponID) {
		Coupon coupon = null;
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "SELECT * FROM COUPONS where id =?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				coupon = new Coupon();
				coupon.setId(resultSet.getInt(1));
				coupon.setCompanyId(resultSet.getInt(2));
				coupon.setCategoryId(resultSet.getInt(3));
				coupon.setTitle(resultSet.getString(4));
				coupon.setDescription(resultSet.getString(5));
				coupon.setStartDate(convertSQLDateToJavaDate(resultSet.getDate(6)));
				coupon.setEndDate(convertSQLDateToJavaDate(resultSet.getDate(7)));
				coupon.setAmount(resultSet.getInt(8));
				coupon.setPrice(resultSet.getDouble(9));
				coupon.setImage(resultSet.getString(10));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		return coupon;

	}

	@Override
	public void addCouponPurchase(int customerID, int couponId) {
		Connection connection = null;

		try {
			connection = _connectionPool.getConnection();
			String sql = "INSERT INTO CustomersVsCoupons(CustomerId,CouponId) values (?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponId);
			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
	}
	@Override
	public ArrayList<Integer> getCouponPurchased(int customerId){
		Connection connection=null;
		ArrayList<Integer>list=new ArrayList<>();
		try {
			connection = _connectionPool.getConnection();
			String sql = "SELECT couponId FROM CustomersVsCoupons where CustomerId=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1,customerId);
			ResultSet result=statement.executeQuery();
			while(result.next()) {
				list.add(result.getInt(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}
		return list;
	}
	@Override
	public void deleteCouponPurchase(int customerID, int couponId) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "DELETE FROM CustomersVsCoupons where CustomerId=? AND couponId=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponId);
			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}

	}

	public void deleteCouponPurchaseByCustomerId(int customerID) {
		Connection connection=null;
		try {
			connection=_connectionPool.getConnection();
			String sql="DELETE FROM CustomersVsCoupons WHERE CustomerId=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(connection!=null)
				_connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void deleteCouponPurchase(int couponId) {
		Connection connection = null;
		try {
			connection = _connectionPool.getConnection();

			String sql = "DELETE FROM CustomersVsCoupons where couponId=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponId);
			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				_connectionPool.returnConnection(connection);
		}

	}

	private java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	private java.util.Date convertSQLDateToJavaDate(java.sql.Date date) {
		return new java.util.Date(date.getTime());
	}
}
