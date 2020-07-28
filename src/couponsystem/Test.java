package couponsystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import couponsystem.exceptions.CouponAlredyExsitsException;
import couponsystem.exceptions.CouponExpiredException;
import couponsystem.exceptions.CouponOutOfStockException;
import couponsystem.exceptions.ValidationException;
import couponsystem.facade.AdminFacade;
import couponsystem.facade.ClientFacade;
import couponsystem.facade.CompanyFacade;
import couponsystem.facade.CustomerFacade;
import couponsystem.job.CouponExpirationDailyJob;
import couponsystem.models.Category;
import couponsystem.models.ClientType;
import couponsystem.models.Company;
import couponsystem.models.Coupon;
import couponsystem.models.Customer;

public class Test {
	private static final CouponExpirationDailyJob job = new CouponExpirationDailyJob();

	public static void testAll() throws InterruptedException, ValidationException, CouponOutOfStockException,
			CouponExpiredException, CouponAlredyExsitsException {
		startJob();
		runAdminOperations();
		System.out.println("************************************");
		runCompanyOperations();
		System.out.println("************************************");
		runCustomerOperations();

		stopJob();

		ConnectionPool.getInstance().closeAllConnection();
	}

	private static void startJob() {
		Thread thread = new Thread(job);
		thread.start();
	}

	private static void stopJob() {
		job.stop();
	}

	private static void runAdminOperations() throws ValidationException {
		ClientFacade facade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Adminstrator);
		AdminFacade adminFacade = (AdminFacade) facade;

		Company c1 = new Company();
		c1.setEmail("d1");
		c1.setName("daniel");
		c1.setPassword("1234");
		Company c2 = new Company();
		c2.setEmail("d2");
		c2.setName("david");
		c2.setPassword("123");

		adminFacade.addCompany(c1);
		adminFacade.addCompany(c2);
		System.out.println("\nAdd company");
		ArrayList<Company> companies = adminFacade.getAllCompanies();
		print(companies);
		c1 = adminFacade.getOneCompany(1);
		System.out.println("\nGet one company");
		System.out.println("\nCompany id: " + c1.getId() + "\nName : " + c1.getName() + "\nEmail: " + c1.getEmail()
				+ "\nPassword: " + c1.getPassword());
		System.out.println("\nDelete company 1");
		adminFacade.deleteCompany(1);
		companies = adminFacade.getAllCompanies();
		print(companies);
		c2.setName("shlomo");
		c2.setId(2);
		c2.setEmail("@@@@@@@@");
		c2.setPassword("1234567890");
		adminFacade.updateCompany(c2);

		companies = adminFacade.getAllCompanies();
		System.out.println("\nUpdate company");
		print(companies);
		System.out.println("\nADD Customers");
		Customer a = new Customer();
		a.setFirstName("David");
		a.setLastName("ABCD");
		a.setEmail("@@@");
		a.setPassword("11111");
		Customer b = new Customer();
		b.setFirstName("Shimon");
		b.setLastName("DDDD");
		b.setEmail("@@@YYY");
		b.setPassword("22222");
		adminFacade.addCustomer(a);
		adminFacade.addCustomer(b);
		ArrayList<Customer> customers = adminFacade.getAllCustomers();
		print(customers);
		System.out.println("\nUpdate customer");
		a.setId(1);
		a.setFirstName("Michael");
		a.setLastName("TTTTTTTT");
		a.setEmail("@@@");
		a.setPassword("123");
		adminFacade.updateCustomer(a);
		customers = adminFacade.getAllCustomers();
		print(customers);
		System.out.println("\nget one costumer");
		a = adminFacade.getOneCustomer(2);
		customers = new ArrayList<>();
		customers.add(a);
		print(customers);
		System.out.println("\nDelete customer 2");
		adminFacade.deleteCustomer(2);
		print(adminFacade.getAllCustomers());
	}

	private static void runCompanyOperations() throws ValidationException {
		CompanyFacade companyFacade = (CompanyFacade) LoginManager.getInstance().login("@@@@@@@@", "1234567890",
				ClientType.Company);
		Coupon a = new Coupon();
		a.setCategoryId(1);
		a.setTitle("BOB");
		a.setDescription("LOL");
		a.setStartDate(new Date("20/2/2020"));
		a.setEndDate(new Date("20/10/2020"));
		a.setAmount(15);
		a.setPrice(150.0);
		a.setImage("AAAAAAAAAAAAAAAAAAAA");
		Coupon b = new Coupon();
		b.setCategoryId(1);
		b.setTitle("RESTAURANT");
		b.setDescription("RRRRRRRRRRRRR");
		b.setStartDate(new Date("20/2/2020"));
		b.setEndDate(new Date("20/10/2020"));
		b.setAmount(15);
		b.setPrice(200.0);
		b.setImage("BBBBBBBBBBBBBBBBB");
		companyFacade.addCoupon(a);
		companyFacade.addCoupon(b);
		System.out.println("\nADD coupons");
		printCoupons(companyFacade.getCompanyCoupons());
		System.out.println("\nUpdate coupon 1");
		a.setId(1);
		a.setTitle("CCCCCCCCC");
		a.setDescription("CCCCCCCCCC");
		a.setStartDate(new Date("20/2/2020"));
		a.setEndDate(new Date("13/10/2020"));
		a.setAmount(3333);
		a.setPrice(333);
		a.setImage("CCCCCCCCCCCCCCCCCC");
		companyFacade.updateCoupon(a);
		printCoupons(companyFacade.getCompanyCoupons());
		System.out.println("\nDelete coupon 2");
		companyFacade.deleteCoupon(2);
		printCoupons(companyFacade.getCompanyCoupons());
		System.out.println("\nget coupons by category 1");
		Category category = new Category();
		category.setId(1);
		printCoupons(companyFacade.getCompanyCoupons(category));
		System.out.println("\nget coupons under max price 100");
		printCoupons(companyFacade.getCompanyCoupons(100));
		System.out.println("\nget company details");
		ArrayList<Company> temp = new ArrayList<>();
		temp.add(companyFacade.getCompanyDetails());
		print(temp);
	}

	private static void runCustomerOperations()
			throws CouponOutOfStockException, CouponExpiredException, CouponAlredyExsitsException {
		CustomerFacade customerFacade = (CustomerFacade) LoginManager.getInstance().login("@@@", "123",
				ClientType.Customer);
		Coupon a = new Coupon();
		a.setId(1);
		a.setCategoryId(1);
		a.setCompanyId(2);
		a.setTitle("BOB");
		a.setDescription("LOL");
		a.setStartDate(new Date("20/2/2020"));
		a.setEndDate(new Date("20/10/2020"));
		a.setAmount(15);
		a.setPrice(150.0);
		a.setImage("AAAAAAAAAAAAAAAAAAAA");
		
		System.out.println("\nPurchase coupon ");
		customerFacade.purchaseCoupon(a);
		System.out.println("\nget customers coupons");
		printCoupons(customerFacade.getCustomerCoupons());
		System.out.println("\nGet customers coupons by category");
		Category category = new Category();
		category.setId(1);
		category.setName("Coffie");
		printCoupons(customerFacade.getCustomerCoupons(category));
		System.out.println("\nGet customers coupons by max price");
		printCoupons(customerFacade.getCustomerCoupons(200));
		System.out.println("\nprint customer details");
		ArrayList<Customer> customer = new ArrayList<>();
		customer.add(customerFacade.getCustomerDetails());
		print(customer);
	}

	
	private static void print(ArrayList<Company> list) {
		for (Company i : list)
			System.out.println("\nCompany id: " + i.getId() + "\nName : " + i.getName() + "\nEmail: " + i.getEmail()
					+ "\nPassword: " + i.getPassword());
	}

	private static void print(List<Customer> list) {
		for (Customer i : list)
			System.out.println("\nCustomer id: " + i.getId() + "\nFirst Name: " + i.getFirstName() + "\nLast name: "
					+ i.getLastName() + "\nEmail: " + i.getEmail() + "\nPassword: " + i.getPassword());
	}

	private static void printCoupons(ArrayList<Coupon> coupons) {
		for (Coupon i : coupons)
			System.out.println("\nCoupon id: " + i.getId() + "\nCompany id: " + i.getCompanyId() + "\nCategory id: "
					+ i.getCategoryId() + "\nTitle: " + i.getTitle() + "\nDescription: " + i.getDescription()
					+ "\nStart Date: " + i.getStartDate() + "\nEnd Date: " + i.getEndDate() + "\nAmount: "
					+ i.getAmount() + "\nPrice: " + i.getPrice() + "\nImage: " + i.getImage());
	}
}
