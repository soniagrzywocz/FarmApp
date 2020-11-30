package project.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.MainApp;
import project.models.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class OverviewController {

    // Reference to the main application.
    private MainApp mainApp;

    @FXML private TreeView<FarmItem> treeView;
    @FXML private TextField name;
    @FXML private TextField posX;
    @FXML private TextField posY;
    @FXML private TextField length;
    @FXML private TextField width;
    @FXML private TextField height;
    @FXML private TextField price;
    @FXML private TextField priceSum;
    @FXML private TextField marketValue;
    @FXML private TextField marketValueSum;
    @FXML private Label priceSumLabel;
    @FXML private Label marketValueSumLabel;
    @FXML private RadioButton visitItemRadio;
    @FXML private RadioButton scanFarmRadio;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public OverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Set root item
        TreeItem<FarmItem> rootItem = new TreeItem<>(new ItemContainer());
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
        // Listener
        treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                treeViewSelectionChanged((TreeItem<FarmItem>) newValue);
            }
        });
    }


    /**
     * Called when the add drone button is clicked.
     *
     */
    @FXML
    private void addDroneButton() {
        FarmItem farmItem = getSelectedItem() != null ? getSelectedItem().getValue() : null;
        if (farmItem == null) {
            showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select an element.");
            return;
        }
        if (!(farmItem instanceof ItemContainer)) {
            showAlert(Alert.AlertType.INFORMATION, "Wrong Selection", "Please select a container.");
            return;
        }
        if (mainApp.getDashboardController().getDrone() != null) {
            showAlert(Alert.AlertType.INFORMATION, "Drone already exists", "Farm may only have one drone at a time.");
            return;
        }

        // Add drone to container...
        TreeItem<FarmItem> newItem = new TreeItem<>(new Drone(0,0,0,0,0));
        getSelectedItem().getChildren().add(newItem);
        // Add Drone to dashboard...
        mainApp.getDashboardController().addElement(newItem.getValue());
    }

    /**
     * Called when add item button is clicked.
     *
     */
    @FXML
    private void addItemButton() {
        FarmItem farmItem = getSelectedItem() != null ? getSelectedItem().getValue() : null;
        if (farmItem == null) {
            showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select an element.");
            return;
        }
        if (!(farmItem instanceof ItemContainer)) {
            showAlert(Alert.AlertType.INFORMATION, "Wrong Selection", "Please select a container.");
            return;
        }

        // Add item to container...
        TreeItem<FarmItem> newItem = new TreeItem<>(new Item(0,0,0,0,0,0,0));
        getSelectedItem().getChildren().add(newItem);
        // Add Item to dashboard...
        mainApp.getDashboardController().addElement(newItem.getValue());
    }

    /**
     * Called when add item container button is clicked.
     *
     */
    @FXML
    private void addItemContainerButton() {
        FarmItem farmItem = getSelectedItem() != null ? getSelectedItem().getValue() : null;
        if (farmItem == null) {
            showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select an element.");
            return;
        }
        if (!(farmItem instanceof ItemContainer)) {
            showAlert(Alert.AlertType.INFORMATION, "Wrong Selection", "Please select a container.");
            return;
        }

        // Add item container to container...
        TreeItem<FarmItem> newItem = new TreeItem<>(new ItemContainer(0,0,0,0,0,0,0));
        getSelectedItem().getChildren().add(newItem);
        // Add ItemContainer to dashboard...
        mainApp.getDashboardController().addElement(newItem.getValue());
    }

    /**
     * Called when the delete item button is clicked.
     *
     */
    @FXML
    private void deleteSelectedButton() {
        FarmItem farmItem = getSelectedItem() != null ? getSelectedItem().getValue() : null;
        if (farmItem == null) {
            showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select an element.");
            return;
        }
        if ((farmItem instanceof ItemContainer) && ((ItemContainer) farmItem).getRoot()) {
            showAlert(Alert.AlertType.INFORMATION, "Wrong Selection", "You cannot delete the root element.");
            return;
        }

        // Delete the item from the dashboard
        deleteTreeItems(getSelectedItem());
        // Delete item...
        TreeItem<FarmItem> c = getSelectedItem();
        getSelectedItem().getParent().getChildren().remove(c);
    }

    /**
     * Recursively deletes all items inside an item container and the element itself.
     *
     * @param item The element to be deleted.
     */
    private void deleteTreeItems(TreeItem<FarmItem> item) {
        for(TreeItem<FarmItem> farmItemElement : item.getChildren()) {
            if (farmItemElement.getValue() instanceof ItemContainer) {
                deleteTreeItems(farmItemElement);
            }
            mainApp.getDashboardController().deleteElement(farmItemElement.getValue());
        }
        // Delete element itself
        mainApp.getDashboardController().deleteElement(item.getValue());
    }

    /**
     * Called when the visit item button is called
     */
    private void visitItem() {
        FarmItem farmItem = getSelectedItem() != null ? getSelectedItem().getValue() : null;
        if (farmItem == null) {
            showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select an element.");
            return;
        }
        if (mainApp.getDashboardController().getDrone() == null) {
            showAlert(Alert.AlertType.INFORMATION, "No Drone Found", "Please add a drone to the farm.");
            return;
        }
        if (farmItem instanceof Drone) {
            showAlert(Alert.AlertType.INFORMATION, "Wrong Selection", "You cannot visit the drone itself.");
            return;
        }
        if (farmItem instanceof  ItemContainer && ((ItemContainer) farmItem).getRoot() == true) {
            showAlert(Alert.AlertType.INFORMATION, "Wrong Selection", "Please use the 'Scan Farm' button.");
            return;
        }
        if (farmItem.getLength() <= 75 || farmItem.getWidth() <= 75) {
            showAlert(Alert.AlertType.INFORMATION, "Drone Scan", "The scan might not be optimal because the drone is larger or equal than the item itself (75px).");
        }

        mainApp.getDashboardController().visitItem(farmItem);
    }

    private void scanFarm() {
        if (mainApp.getDashboardController().getDrone() == null) {
            showAlert(Alert.AlertType.INFORMATION, "No Drone Found", "Please add a drone to the farm.");
            return;
        }
        mainApp.getDashboardController().scanFarm();
    }

    @FXML
    private void launchSimulatedDrone() {
        if (visitItemRadio.isSelected()) {
            visitItem();
        }
        if (scanFarmRadio.isSelected()) {
            scanFarm();
        }
    }

    @FXML
    private void launchPhysicalDrone() throws IOException, InterruptedException {
        PhysicalDroneAdapter physicalDroneAdapter = new PhysicalDroneAdapter(new TelloDrone());
        if (visitItemRadio.isSelected()) {
            physicalDroneAdapter.visitItem(getSelectedItem().getValue());
        }
        if (scanFarmRadio.isSelected()) {
            physicalDroneAdapter.scanFarm();
        }
    }


    /**
     * Is called when the TreeView selection changes.
     *
     * @param selectedItem The selected TreeItem.
     */
    private void treeViewSelectionChanged(TreeItem<FarmItem> selectedItem) {
        if (selectedItem == null || selectedItem.getValue() == null)
            return;

        name.setText(selectedItem.getValue().getItemName());
        posX.setText(Integer.toString(selectedItem.getValue().getPositionX()));
        posY.setText(Integer.toString(selectedItem.getValue().getPositionY()));
        width.setText(Integer.toString(selectedItem.getValue().getWidth()));
        height.setText(Integer.toString(selectedItem.getValue().getHeight()));
        length.setText(Integer.toString(selectedItem.getValue().getLength()));
        price.setText(Integer.toString(selectedItem.getValue().getPrice()));
        priceSum.setText(Integer.toString(selectedItem.getValue().getPriceSum()));
        marketValue.setText(Integer.toString(selectedItem.getValue().getMarketValue()));
        marketValueSum.setText(Integer.toString(selectedItem.getValue().getMarketValueSum()));

        // Disable inputs
        width.setDisable(selectedItem.getValue() instanceof Drone);
        height.setDisable(selectedItem.getValue() instanceof Drone);
        length.setDisable(selectedItem.getValue() instanceof Drone);
        price.setDisable(selectedItem.getValue() instanceof Drone);
        // Show/hide PriceSum
        priceSum.setVisible(selectedItem.getValue() instanceof ItemContainer);
        priceSumLabel.setVisible(selectedItem.getValue() instanceof ItemContainer);
        marketValueSum.setVisible(selectedItem.getValue() instanceof ItemContainer);
        marketValueSumLabel.setVisible(selectedItem.getValue() instanceof ItemContainer);
    }

    /**
     * Called when save button is clicked.
     *
     */
    @FXML
    private void handleSave() {
        FarmItem farmItem = getSelectedItem() != null ? getSelectedItem().getValue() : null;
        if (farmItem == null) {
            showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select an element.");
            return;
        }
        if ((farmItem instanceof ItemContainer) && ((ItemContainer) farmItem).getRoot()) {
            showAlert(Alert.AlertType.INFORMATION, "Wrong Selection", "You cannot edit the root element.");
            return;
        }
        if (isInputValid()) {
            farmItem.setItemName(name.getText());
            farmItem.setPositionX(Integer.parseInt(posX.getText()));
            farmItem.setPositionY(Integer.parseInt(posY.getText()));
            // Set L, W, H
            if (!(farmItem instanceof Drone)) {
                farmItem.setWidth(Integer.parseInt(width.getText()));
                farmItem.setHeight(Integer.parseInt(height.getText()));
                farmItem.setLength(Integer.parseInt(length.getText()));
            }
            // Set price and marketValue
            if (!(farmItem instanceof Drone)) {
                int newMarketValue = Integer.parseInt(marketValue.getText());
                int newPrice = Integer.parseInt(price.getText());

                MarketVisitor valueCalc = new MarketVisitor();
                PriceVisitor priceCalc = new PriceVisitor();

                int oldMarketValue = valueCalc.visit(farmItem);
                int oldPrice = priceCalc.visit(farmItem);

                int marketValueDelta = newMarketValue - oldMarketValue;
                int priceDelta = newPrice - oldPrice;

                farmItem.setPrice(newPrice);
                farmItem.setMarketValue(newMarketValue);
                updateTreeItemsSum(getSelectedItem(), priceDelta, marketValueDelta);

            }
            treeView.refresh();
            // Update on dashboard...
            mainApp.getDashboardController().updateElement(farmItem);
        }
    }

    /**
     * Recursively updates the price sum.
     *
     * @param item The element which's price got updated.
     */
    private void updateTreeItemsSum(TreeItem<FarmItem> item, int priceDelta, int valueDelta) {
        // Get parent
        TreeItem<FarmItem> itemParent = item.getParent();
        if (itemParent == null)
            return;
        // Update price sum on parent
        itemParent.getValue().setPriceSum(itemParent.getValue().getPriceSum() + priceDelta);
        itemParent.getValue().setMarketValueSum(itemParent.getValue().getMarketValueSum() + valueDelta);
        // Recursively update parents
        if (!((ItemContainer)itemParent.getValue()).getRoot())
            updateTreeItemsSum(itemParent, priceDelta, valueDelta);
    }

    /**
     * Checks if the input is valid.
     *
     * @return A boolean specifying whether or not the input is valid.
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "Name field is empty!\n";
        }

        Map<TextField, String> intFields = new HashMap<TextField, String>() {{
            put(posX, "posX");
            put(posY, "posY");
            put(width, "width");
            put(height, "height");
            put(length, "length");
            put(price, "price");
        }};

        for (Map.Entry<TextField, String> entry : intFields.entrySet()) {
            if (entry.getKey().getText() == null || entry.getKey().getText().length() == 0) {
                errorMessage += entry.getValue() + " field is empty!\n";
            } else {
                // try to parse the intFields into an int.
                try {
                    Integer.parseInt(entry.getKey().getText());
                } catch (NumberFormatException e) {
                    errorMessage += "No valid " + entry.getValue() + " (must be an integer)!\n";
                }
            }
        };

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            showAlert(Alert.AlertType.ERROR, "Invalid Fields", "Please correct invalid fields", errorMessage);
            return false;
        }
    }

    /**
     * Shows an alert.
     *
     * @param type The type of alert.
     * @param title The title of the alert.
     * @param header The header of the alert.
     */
    private void showAlert(Alert.AlertType type, String title, String header) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);

        alert.showAndWait();
    }

    /**
     * Shows an alert.
     *
     * @param type The type of alert.
     * @param title The title of the alert.
     * @param header The header of the alert.
     * @param content The content of the alert.
     */
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }


    /**
     * Gets the selected item from the TreeView.
     *
     * @return The selected TreeItem.
     */
    private TreeItem<FarmItem> getSelectedItem() {
        TreeItem<FarmItem> selectedItem = treeView.getSelectionModel().getSelectedItem();
        return selectedItem;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp The main application.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
