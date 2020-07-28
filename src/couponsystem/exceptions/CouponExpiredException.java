package couponsystem.exceptions;

public class CouponExpiredException extends Exception{
	public CouponExpiredException() {
		super("Coupon date is expired");
	}
}
