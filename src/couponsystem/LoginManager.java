package couponsystem;

import couponsystem.facade.AdminFacade;
import couponsystem.facade.ClientFacade;
import couponsystem.facade.CompanyFacade;
import couponsystem.facade.CustomerFacade;
import couponsystem.models.ClientType;

public class LoginManager {
	private static LoginManager _instance;

	private LoginManager() {
	}

	public static LoginManager getInstance() {
		if (_instance == null) {
			synchronized (LoginManager.class) {
				if (_instance == null) {
					_instance = new LoginManager();
				}
			}
		}

		return _instance;
	}

	public ClientFacade login(String email, String password, ClientType clientType) {
		ClientFacade facade = null;;
		switch (clientType) {
			case Adminstrator:
				facade = new AdminFacade();
				break;
			case Company:
				facade = new CompanyFacade();
				break;
			case Customer:
				facade = new CustomerFacade();
				break;
		}
		
		if(facade == null) {
			return null;
		}
		
		
		return facade.login(email, password) ? facade : null;
	}
}
