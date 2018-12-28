package View;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import controller.InventoryEventHandler;
import controller.InventoryEventHandler.InventoryViewMode;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Inventory;
import model.Item;

/**
 * GUI View for the {@link model.Item}s in {@link model.Player}'s {@link model.Inventory}
 * @author JohnXu
 *
 */
public class ItemInventoryTab extends TableView<Item> implements Observer {

	private TableColumn<Item, String> nameCol;
	private TableColumn<Item, Number> countCol;
	private TableColumn<Item, Number> buttonCol;
	private TableColumn<Item, String> descriptionCol;

	private InventoryEventHandler invenController;
	private Inventory inven;

	private ObservableList<Item> itemList;

	private Item itemSelected;
	//private InventoryViewMode mode;

	/**
	 * Constructor
	 * @param inven Inventory
	 */
	public ItemInventoryTab(Inventory inven, InventoryEventHandler invenController) {

		this.inven = inven;
		this.invenController = invenController;
		this.itemList = FXCollections.observableArrayList();

		this.itemSelected = null;

		// Name Column
		this.nameCol = new TableColumn<Item, String>();
		nameCol.setText("Name");
		nameCol.setPrefWidth(300);
		nameCol.setSortable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		// Count Column
		this.countCol = new TableColumn<Item, Number>();
		countCol.setText("Count");
		countCol.setPrefWidth(50);
		countCol.setSortable(false);
		countCol.setCellValueFactory(new Callback<CellDataFeatures<Item, Number>, ObservableValue<Number>>() {
			@Override
			public ObservableValue<Number> call(CellDataFeatures<Item, Number> arg0) {
				return new SimpleIntegerProperty(getItemCount(arg0.getValue()));
			}
		});

		// UseItem Button Column
        this.buttonCol = new TableColumn<>("");
		this.buttonCol.setPrefWidth(100);
		this.buttonCol.setSortable(false);

        buttonCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Item, Number>, 
                ObservableValue<Number>>() {

            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Item, Number> arg0) {
                return new SimpleIntegerProperty(0);
            }
        });

        buttonCol.setCellFactory(
            new Callback<TableColumn<Item, Number>, TableCell<Item, Number>>() {

                @Override
                public TableCell<Item, Number> call(TableColumn<Item, Number> arg0) {
                    return new ButtonCell(ItemInventoryTab.this.inven, invenController.getActionEventHandler());
                }
            }
        );

		// Description Column
		this.descriptionCol = new TableColumn<Item, String>();
		descriptionCol.setText("Name");
		descriptionCol.setPrefWidth(300);
		descriptionCol.setSortable(false);
		//descriptionCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		this.setItems(this.itemList);
		this.getColumns().add(this.nameCol);
		this.getColumns().add(this.countCol);
		this.getColumns().add(this.buttonCol);
	}

	@Override
	/**
	 * Update method, implement from {@link Observer} interface
	 */
	public void update(Observable o, Object arg) {
		
		Inventory inven = (Inventory) o;

		this.itemList.clear();

		Set<Item> set = inven.getItems().keySet();

		for(Item item : set) {
			this.itemList.add(item);
		}
	}

	/**
	 * Item Selected, used for In-Battle mode
	 * @return Item selected through the view
	 */
	public Item getItemSelected() {
		return itemSelected;
	}

	private int getItemCount(Item item) {

		if(this.inven.getItems().containsKey(item)) {
			return this.inven.getItems().get(item);
		}
		else {
			return 0;
		}
	}

	private class ButtonCell extends TableCell<Item, Number> {

		private Inventory inven;
		private Button button;

		public ButtonCell(Inventory inven, EventHandler<ActionEvent> handler) {

			this.inven = inven;
			this.button = new Button("Use");
			this.button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {

					Item item = (Item) ButtonCell.this.getTableRow().getItem();

					assert(item != null);

					// For In-Battle, select the item, but not use it
					if(ItemInventoryTab.this.invenController.getMode() == InventoryViewMode.InBattleMode) {
						ItemInventoryTab.this.itemSelected = item;
						ItemInventoryTab.this.invenController.selectItem(item);
					}
					// For Out-Of-Battle, use the item directly
					else {
						ButtonCell.this.inven.useItem(item);
					}
				}
			});
		}

		@Override
		/**
		 * Display the button if the row is not empty and Item is use-able
		 */
		protected void updateItem(Number item, boolean empty) {

			super.updateItem(item, empty);

			if(empty || this.getTableRow() == null || this.getTableRow().getItem() == null) {
				this.setGraphic(null);
				return;
			}

			// InBattle-Item in in-battle
			if(ItemInventoryTab.this.invenController.getMode() == InventoryViewMode.InBattleMode
					&& ((Item) this.getTableRow().getItem()).isInBattleUse()) {
				this.setGraphic(button);
			}
			// OutOfBattle-Item in out-of-battle (OnMap-mode)
			else if(ItemInventoryTab.this.invenController.getMode() == InventoryViewMode.OnMapMode
					&& ! ((Item) this.getTableRow().getItem()).isOutOfBattleUse()) {
				this.setGraphic(button);
			}
			else {
				this.setGraphic(null);
			}
		}
	}

}
