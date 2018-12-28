package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * List of account
 */
public class AccountList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8913918713099895654L;

	private List<Account> list;

	/**
	 * Constructor
	 */
	public AccountList() {

		this.list = new ArrayList<Account>();

		// Test Account
		this.list.add(new Account("jx", "password"));
	}
	
	/**
	 * Add Account into List
	 * @param account Account to be added
	 */
	public void add(Account account) {
		this.list.add(account);
	}
	
	/**
	 * Remove Account from List
	 * @param account Account to be removed
	 */
	public void remove(Account account) {
		this.list.remove(account);
	}

	/**
	 * 
	 * @param username Username to be checked
	 * @param password Password to be checked
	 * @return Whether the username-password combo exist or not
	 */
	public boolean checkPassword(String username, String password) {

		for(Account acc : this.list) {
			if(acc.getUsername().compareTo(username) == 0
					&& acc.getPassword().compareTo(password) == 0) {
				return true;
			}
		}
		return false;
	}
}
