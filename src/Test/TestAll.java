package Test;

import static org.junit.Assert.*;



import org.junit.Test;

import controller.BattleHandler;
import controller.BattleHandler.BattleAction;
import model.Pokemon;
import model.PokemonGenerator;
import model.PokemonSpecie;
import model.PokemonType;
import model.SinglePlayerGame;

import model.AvailableItemList;
import model.AvailablePokemonSpecieList;
import model.Direction;
import model.Inventory;
import model.Item;

import model.Player;
import model.Game;

/**
 * Test model
 *
 */
public class TestAll {
	
	@Test
	public void testPokemonSpecie(){
		PokemonSpecie ps = new PokemonSpecie("Raticate", PokemonType.Normal, 100, 0.4, 50, "filename");

		assertEquals(ps.getName(), "Raticate");
		assertEquals(ps.getType(), PokemonType.Normal);
		assert(ps.getEncounterFactor() == 0.4);

		Pokemon p = ps.createPokemon();
		assert(p.getSpecie() == ps);
		
	} // testPokemonSpecie
	
	
	@Test
	public void testCaught(){

		Item rock = AvailableItemList.search("Rock");
		Item bait = AvailableItemList.search("Bait");
		Item pokeBall = AvailableItemList.search("Poke Ball");

		PokemonGenerator generator = new PokemonGenerator(AvailablePokemonSpecieList.getInstance());

		BattleHandler bh = new BattleHandler(null);
		Player player = new Player("name", 10, 10);
		player.getInventory().initializeInventory();

		Pokemon p = generator.generate();
		bh.startBattle(player, p);

		int prevNum;
		boolean battleEnded;
		prevNum = player.getInventory().getItems().get(pokeBall);
		bh.validAction(BattleAction.UsePokeBall);
		//battleEnded = bh.round(BattleAction.UsePokeBall);
		assert(player.getInventory().getItems().get(pokeBall) == prevNum-1);
	} // testCaught
	
	@Test 
	public void testInventory(){

		Player player = new Player("p1", 0, 0);
		Inventory inventory = player.getInventory();
		Item rock = AvailableItemList.search("Rock");
		Item bait = AvailableItemList.search("Bait");
		Item pokeBall = AvailableItemList.search("Poke Ball");
		
		inventory.initializeInventory();
		assert(inventory.getItems().containsKey(pokeBall));
		assert(inventory.getItems().get(pokeBall) == 30);
		
		// test addItem
		inventory.addItem(pokeBall);
		assert(inventory.getItems().containsKey(pokeBall));
		assert(inventory.getItems().get(pokeBall) == 31);
		
		// test useItem
		inventory.useItem(rock);
		assert(inventory.getItems().containsKey(rock));
		assert(inventory.getItems().get(rock) == 9);
		
		
	} // testInventory
	
	
	@Test
	public void testPlayer(){
		String name = "eddie";
		Player eddie = new Player(name, 10, 5);
		
		assertEquals("eddie", eddie.getName());
		assert(0 == eddie.getSteps());

		// Test move
		eddie.move(Direction.EAST);
		assert(eddie.getX() == 11);
		assert(eddie.getY() == 5);

		eddie.move(Direction.SOUTH);
		assert(eddie.getX() == 11);
		assert(eddie.getY() == 6);

		eddie.move(Direction.WEST);
		assert(eddie.getX() == 10);
		assert(eddie.getY() == 6);

		eddie.move(Direction.NORTH);
		assert(eddie.getX() == 10);
		assert(eddie.getY() == 5);


		// Test Teleport
		eddie.teleport(1, 12, 34);
		assert(eddie.getActiveMapRegionIndex() == 1);
		assert(eddie.getX() == 12);
		assert(eddie.getY() == 34);

	} // testPlayer
	
	@Test
	public void testGame(){
		SinglePlayerGame sg = new SinglePlayerGame("player1");
		Player player = sg.getPlayer();

		for(int i = 0; i < Game.maxStep+1; i++) {
			player.move(Direction.SOUTH);
		}
		assert(sg.lose());
		
	} // testGame

}
