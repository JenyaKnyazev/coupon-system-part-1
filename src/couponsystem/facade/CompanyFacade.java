package couponsystem.facade;

import java.util.ArrayList;

import couponsystem.exceptions.ValidationException;
import couponsystem.models.Category;
import couponsystem.models.Company;
import couponsystem.models.Coupon;

public class CompanyFacade extends ClientFacade {
	private int companyID;

	@Override
	public boolean login(String email, String password) {
		boolean res = companiesDAO.isCompanyExists(email, password);
		if (res) {
			ArrayList<Company> companies = companiesDAO.getAllCompanies();
			for (Company i : companies)
				if (i.getEmail().equals(email) && i.getPassword().equals(password)) {
					companyID = i.getId();
					break;
				}
		}
		return res;
	}

	public void addCoupon(Coupon coupon) throws ValidationException {
		ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
		for (Coupon c : coupons) {
			if (c.getCompanyId() == coupon.getCompanyId() && c.getTitle() != null && coupon.getTitle() != null
					&& c.getTitle().equals(coupon.getTitle())) {
				throw new ValidationException("cannot add coupon with the same title");
			}
		}
		coupon.setCompanyId(companyID);
		couponsDAO.addCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) {
		coupon.setCompanyId(companyID);
		couponsDAO.updateCoupon(coupon);
	}

	public void deleteCoupon(int couponID) {
		couponsDAO.deleteCouponPurchase(couponID);
		couponsDAO.deleteCoupon(couponID);
	}

	public ArrayList<Coupon> getCompanyCoupons() {
		ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();

		ArrayList<Coupon> results = new ArrayList<Coupon>();

		for (Coupon c : coupons) {
			if (c.getCompanyId() == companyID) {
				results.add(c);
			}
		}

		return results;
	}

	public ArrayList<Coupon> getCompanyCoupons(Category category) {
		ArrayList<Coupon> coupons = getCompanyCoupons();
		ArrayList<Coupon> result = new ArrayList<>();
		for (Coupon i : coupons)
			if (i.getCategoryId() == category.getId())
				result.add(i);
		return result;
	}

	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
		ArrayList<Coupon> coupons = getCompanyCoupons();
		ArrayList<Coupon> result = new ArrayList<>();
		for (Coupon i : coupons)
			if (i.getPrice() <= maxPrice)
				result.add(i);
		return result;
	}

	public Company getCompanyDetails() {
		return companiesDAO.getOneCompany(companyID);
	}

}
