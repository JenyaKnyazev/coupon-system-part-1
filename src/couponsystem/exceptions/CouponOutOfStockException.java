package couponsystem.exceptions;

public class CouponOutOfStockException extends Exception{
	public CouponOutOfStockException() {
		super("Coupon is out of stock");
	}
}
