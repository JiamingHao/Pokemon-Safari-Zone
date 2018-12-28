package controller;

import View.LoginView;
import View.StartingView;
import controller.ViewChange.GUIViewType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Account;
import model.AccountList;

public class LoginViewHandler implements EventHandler<ActionEvent> {

	private LoginView loginView;
	private StartingView startingView;
	private AccountList accList;
	private ViewChange viewChange;

	public LoginViewHandler(ViewChange viewChange, AccountList accList) {
		this.viewChange = viewChange;
		this.accList = accList;
	}

	public void init(LoginView loginView, StartingView startingView) {
		this.loginView = loginView;
		this.startingView = startingView;
	}

	@Override
	public void handle(ActionEvent arg0) {

		// Check username & password
		if(! this.accList.checkPassword(this.loginView.getUsername(), this.loginView.getPassword())) {
			// If failed
			System.out.println("LoginViewHandler: Login failed");
			return;
		}

		this.startingView.setUsername(this.loginView.getUsername());
		this.viewChange.switchView(GUIViewType.GUIStartingView, null);
	}

}
