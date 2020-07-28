package couponsystem.exceptions;

public class CouponAlredyExsitsException extends Exception{
	public CouponAlredyExsitsException() {
		super("Coupon alredy purchased");
	}
}
