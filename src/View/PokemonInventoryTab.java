package View;

import java.util.Observable;
import java.util.Observer;

import controller.InventoryEventHandler.InventoryViewMode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Inventory;
import model.Pokemon;

/**
 * GUI View for the {@link model.Pokemon}s in {@link model.Player}'s {@link model.Inventory}
 * @author JohnXu
 *
 */
public class PokemonInventoryTab extends TableView<Pokemon> implements Observer {

	private TableColumn<Pokemon, String> specieNameCol;
	private TableColumn<Pokemon, Number> levelCol;

	private ObservableList<Pokemon> pokemonList;
	private InventoryViewMode mode;

	/**
	 * Constructor
	 */
	public PokemonInventoryTab() {
		
		this.pokemonList = FXCollections.observableArrayList();

		// Name Column
		this.specieNameCol = new TableColumn<Pokemon, String>();
		specieNameCol.setText("Name");
		specieNameCol.setPrefWidth(300);
		specieNameCol.setSortable(false);
		specieNameCol.setCellValueFactory(new Callback<CellDataFeatures<Pokemon, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Pokemon, String> arg0) {
				return new SimpleStringProperty(arg0.getValue().getSpecie().getName());
			}
		});

		// Level Column
		this.levelCol = new TableColumn<Pokemon, Number>();
		levelCol.setText("level");
		levelCol.setPrefWidth(50);
		levelCol.setSortable(false);
		levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));

		this.setItems(this.pokemonList);
		this.getColumns().add(this.specieNameCol);
		this.getColumns().add(this.levelCol);
	}

	@Override
	/**
	 * Update method, implement from {@link Observer} interface
	 */
	public void update(Observable o, Object arg) {
		
		Inventory inven = (Inventory) o;

		this.pokemonList.clear();

		for(Pokemon p : inven.getPokemons()) {
			this.pokemonList.add(p);
		}
	}

}
