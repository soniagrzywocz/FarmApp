package project.controllers;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import project.MainApp;
import project.models.Drone;
import project.models.FarmItem;
import project.models.PhysicalDroneAdapter;


public class DashboardController {

    // Reference to the main application.
    private MainApp mainApp;
    private Drone drone;

    @FXML
    private Pane pane;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public DashboardController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Adds an element to the dashboard.
     *
     * @param farmItem The farm item to be added.
     */
    public void addElement(FarmItem farmItem) {
        pane.getChildren().add(farmItem.getElement());
        if (farmItem instanceof Drone) this.drone = (Drone)farmItem;
    }


    /**
     * Updates an element on the dashboard.
     *
     * @param farmItem The farm item to be updated.
     */
    public void updateElement(FarmItem farmItem) {
        // Remove from dashboard
        pane.getChildren().remove(farmItem.getElement());
        // Add to dashboard
        pane.getChildren().add(farmItem.getElement());
    }

    /**
     * Deletes an element from the dashboard.
     *
     * @param farmItem The farm item to be deleted.
     */
    public void deleteElement(FarmItem farmItem) {
        // Remove from dashboard
        pane.getChildren().remove(farmItem.getElement());
        if (farmItem instanceof Drone)
            this.drone = null;
    }

    /**
     * Called when the drone is visiting the item.
     *
     * @param item The farm item.
     */

    public void visitItem(FarmItem item) {
        assert !(item instanceof Drone) : "You cannot visit the drone itself.";

        // Get target coordinates
        int x1 = item.getPositionX();
        int y1 = item.getPositionY();
        int x2 = (item.getPositionX() + item.getLength()) - 75; // subtract drone image
        int y2 = (item.getPositionY() + item.getWidth()) - 75; // subtract drone image

        scanRectangle(x1, y1, x2, y2);
    }



    public void scanFarm() {
        double farmWidth = pane.getWidth();
        double farmHeight = pane.getHeight();
        double droneHeight = drone.getElement().getChildren().get(1).getBoundsInParent().getHeight();
        double droneWidth = drone.getElement().getChildren().get(1).getBoundsInParent().getWidth();

        TranslateTransition restart = new TranslateTransition(Duration.millis(500));
        restart.setFromX(drone.getPositionX());
        restart.setFromY(drone.getPositionY());
        restart.setToX(0);
        restart.setToY(0);

        SequentialTransition seqT = new SequentialTransition (drone.getElement(), restart);
        seqT.play();

        seqT.setOnFinished(e -> {
            drone.setPositionX(0);
            drone.setPositionY(0);
            scanningLoop(farmWidth, farmHeight, droneWidth, droneHeight);
        });

    }

    public void scanningLoop(double farmWidth, double farmHeight, double droneWidth, double droneHeight) {
        TranslateTransition down = new TranslateTransition(Duration.millis(500));
        down.setFromX(drone.getPositionX());
        down.setFromY(drone.getPositionY());
        down.setToX(drone.getPositionX());
        down.setToY(farmHeight - droneHeight);

        TranslateTransition right = new TranslateTransition(Duration.millis(100));
        right.setFromX(down.getToX());
        right.setFromY(down.getToY());
        right.setToX(down.getToX() + droneWidth);
        right.setToY(down.getToY());

        TranslateTransition up = new TranslateTransition(Duration.millis(500));
        up.setFromX(right.getToX());
        up.setFromY(right.getToY());
        up.setToX(right.getToX());
        up.setToY(0);

        TranslateTransition right2 = new TranslateTransition(Duration.millis(100));
        right2.setFromX(up.getToX());
        right2.setFromY(up.getToY());
        right2.setToX(up.getToX() + droneWidth);
        right2.setToY(up.getToY());

        SequentialTransition seqT = new SequentialTransition (drone.getElement(), down, right, up, right2);
        seqT.setOnFinished(e -> {
            drone.setPositionX((int) ((int) right2.getToX() - droneWidth));
            drone.setPositionY((int) right2.getToY());
            if ((drone.getPositionX() + (3*droneWidth)) < (farmWidth - droneWidth) - droneWidth) {
                scanningLoop(farmWidth, farmHeight, droneWidth, droneHeight);
                return;
            }
            returnDroneToStart();
        });
        seqT.play();
    }

    public void returnDroneToStart() {
        TranslateTransition restart = new TranslateTransition(Duration.millis(500));
        restart.setFromX(drone.getPositionX());
        restart.setFromY(drone.getPositionY());
        restart.setToX(0);
        restart.setToY(0);

        SequentialTransition seqT = new SequentialTransition (drone.getElement(), restart);
        seqT.play();

        seqT.setOnFinished(e -> {
            drone.setPositionX(0);
            drone.setPositionY(0);
        });

    }


    /**
     * Lets the drone scan a rectangle.
     *
     * @param x1 The first x-coordinate.
     * @param y1 The first y-coordinate.
     * @param x2 The second x-coordinate.
     * @param y2 The second y-coordinate.
     */
    public void scanRectangle(double x1, double y1, double x2, double y2) {
        assert (getDrone() != null) : "Farm has no drone.";

        // Go to x1, y1
        TranslateTransition t1 = new TranslateTransition(Duration.millis(2000));
        t1.setFromX(getDrone().getPositionX());
        t1.setFromY(getDrone().getPositionY());
        t1.setToX(x1);
        t1.setToY(y1);

        // Go to x1, y2
        TranslateTransition t2 = new TranslateTransition(Duration.millis(2000));
        t2.setFromX(x1);
        t2.setFromY(y1);
        t2.setToX(x1);
        t2.setToY(y2);

        // Go to x2, y2
        TranslateTransition t3 = new TranslateTransition(Duration.millis(2000));
        t3.setFromX(x1);
        t3.setFromY(y2);
        t3.setToX(x2);
        t3.setToY(y2);

        // Go to x2, y1
        TranslateTransition t4 = new TranslateTransition(Duration.millis(2000));
        t4.setFromX(x2);
        t4.setFromY(y2);
        t4.setToX(x2);
        t4.setToY(y1);

        // Go to x1, y1
        TranslateTransition t5 = new TranslateTransition(Duration.millis(2000));
        t5.setFromX(x2);
        t5.setFromY(y1);
        t5.setToX(x1);
        t5.setToY(y1);

        // Go back to origin
        TranslateTransition t6 = new TranslateTransition(Duration.millis(2000));
        t6.setFromX(x1);
        t6.setFromY(y1);
        t6.setToX(getDrone().getPositionX());
        t6.setToY(getDrone().getPositionY());

        SequentialTransition seqT = new SequentialTransition (getDrone().getElement(), t1, t2, t3, t4, t5, t6);
        seqT.play();
    }


    /**
     * Gets the drone.
     *
     * @return The drone.
     */
    public Drone getDrone() {
        return drone;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp The main application
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
