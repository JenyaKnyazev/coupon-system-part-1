package couponsystem.job;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import couponsystem.ConnectionPool;
import couponsystem.dao.CouponsDAO;
import couponsystem.dao.impl.CouponsDBDAO;
import couponsystem.models.Coupon;

public class CouponExpirationDailyJob implements Runnable {
	private final CouponsDAO couponsDAO;
	private boolean quit;
	private LocalDate lastIterationDate;

	public CouponExpirationDailyJob() {
		couponsDAO = new CouponsDBDAO(ConnectionPool.getInstance());
		quit = false;
		lastIterationDate = null;
	}

	@Override
	public void run() {
		while (!quit) {
			if (lastIterationDate == null || lastIterationDate.isBefore(LocalDate.now())) {
				lastIterationDate = LocalDate.now();
				runJob();
			}
		}

	}

	public void stop() {
		quit = true;
	}

	private void runJob() {
		ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
		ArrayList<Coupon> expiredCoupons = findExpiredCoupons(coupons);
		for (Coupon i : expiredCoupons) {
			couponsDAO.deleteCouponPurchase(i.getId());
			couponsDAO.deleteCoupon(i.getId());
		}
	}

	private ArrayList<Coupon> findExpiredCoupons(ArrayList<Coupon> coupons) {
		ArrayList<Coupon> expiredCoupons = new ArrayList<>();
		for (Coupon i : coupons)
			if (i.getEndDate().before(new Date()))
				expiredCoupons.add(i);
		return expiredCoupons;
	}

}
