package couponsystem.facade;

import couponsystem.ConnectionPool;
import couponsystem.dao.CompaniesDAO;
import couponsystem.dao.CouponsDAO;
import couponsystem.dao.CustomersDAO;
import couponsystem.dao.impl.CompaniesDBDAO;
import couponsystem.dao.impl.CouponsDBDAO;
import couponsystem.dao.impl.CustomersDBDAO;

public abstract class ClientFacade {
	protected CompaniesDAO companiesDAO;
	protected CouponsDAO couponsDAO;
	protected CustomersDAO customersDAO;
	public abstract boolean login(String email,String password); 
	public ClientFacade() {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		
		companiesDAO=new CompaniesDBDAO(connectionPool);
		couponsDAO=new CouponsDBDAO(connectionPool);
		customersDAO=new CustomersDBDAO(connectionPool);
	}
}
