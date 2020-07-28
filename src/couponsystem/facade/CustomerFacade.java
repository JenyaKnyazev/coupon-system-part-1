package couponsystem.facade;

import java.util.ArrayList;
import java.util.Date;

import couponsystem.exceptions.CouponAlredyExsitsException;
import couponsystem.exceptions.CouponExpiredException;
import couponsystem.exceptions.CouponOutOfStockException;
import couponsystem.models.Category;
import couponsystem.models.Company;
import couponsystem.models.Coupon;
import couponsystem.models.Customer;

public class CustomerFacade extends ClientFacade {
	private int customerID;

	@Override
	public boolean login(String email, String password) {
		boolean res = customersDAO.isCustomerExists(email, password);
		if (res) {
			ArrayList<Customer> customers = customersDAO.getAllCustomers();
			for (Customer i : customers)
				if (i.getEmail().equals(email) && i.getPassword().equals(password)) {
					customerID = i.getId();
					break;
				}
		}
		return res;
	}

	private boolean checkIfCouponPurchesed(Coupon coupon) {
		ArrayList<Integer> list = couponsDAO.getCouponPurchased(customerID);
		for (Integer i : list)
			if (i.equals(coupon.getId()))
				return true;
		return false;
	}

	public void purchaseCoupon(Coupon coupon)
			throws CouponOutOfStockException, CouponExpiredException, CouponAlredyExsitsException {
		Date now = new Date();
		if (coupon.getAmount() == 0)
			throw new CouponOutOfStockException();

		if (coupon.getEndDate().before(now))
			throw new CouponExpiredException();

		if (checkIfCouponPurchesed(coupon))
			throw new CouponAlredyExsitsException();

		couponsDAO.addCouponPurchase(customerID, coupon.getId());
		coupon.setAmount(coupon.getAmount() - 1);
		couponsDAO.updateCoupon(coupon);
	}

	public ArrayList<Coupon> getCustomerCoupons() {
		ArrayList<Integer> customerCoupons = couponsDAO.getCouponPurchased(customerID);

		ArrayList<Coupon> results = new ArrayList<Coupon>();

		for (Integer couponId : customerCoupons) {
			Coupon c = couponsDAO.getOneCoupon(couponId);
			if (c != null) {
				results.add(c);
			}
		}

		return results;
	}

	public ArrayList<Coupon> getCustomerCoupons(Category category) {
		ArrayList<Coupon> coupons = getCustomerCoupons();

		ArrayList<Coupon> results = new ArrayList<Coupon>();

		for (Coupon coupon : coupons) {
			if (coupon.getCategoryId() == category.getId()) {
				results.add(coupon);
			}
		}

		return results;
	}

	public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {
		ArrayList<Coupon> coupons = getCustomerCoupons();

		ArrayList<Coupon> results = new ArrayList<Coupon>();

		for (Coupon coupon : coupons) {
			if (coupon.getPrice() <= maxPrice) {
				results.add(coupon);
			}
		}

		return results;
	}

	public Customer getCustomerDetails() {
		return customersDAO.getOneCustomer(customerID);
	}
}
