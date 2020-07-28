package couponsystem.dao;

import java.util.ArrayList;

import couponsystem.models.Coupon;

public interface CouponsDAO {
	void addCoupon(Coupon coupon);
	void updateCoupon(Coupon coupon);
	void deleteCoupon(int couponID);
	
	ArrayList<Coupon> getAllCoupons();
	Coupon getOneCoupon(int couponID);
	
	void addCouponPurchase(int customerID,int couponId);
	void deleteCouponPurchase(int customerID,int couponId);
	void deleteCouponPurchase(int couponId); 
	void deleteCouponPurchaseByCustomerId(int customerId);
	ArrayList<Integer> getCouponPurchased(int customerId);
}
