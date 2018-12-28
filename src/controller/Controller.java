package controller;

import View.GUIView;
import model.Game;
import model.Inventory;
import model.Player;

public interface Controller {

	public void init(Game theGame);
	public void init(Player player);
	public void init(Inventory inventory);
}
