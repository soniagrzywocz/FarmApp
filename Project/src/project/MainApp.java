//Cameron Wood
package project;

import java.io.IOException;

import project.controllers.DashboardController;
import project.controllers.OverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    // Singleton instance
    private static MainApp _mainApp = null;

    // Controllers
    private DashboardController dashboardController;
    private OverviewController overviewController;

    private Stage _primaryStage;
    private BorderPane _rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this._primaryStage = primaryStage;
        this._primaryStage.setTitle("Farm Manager");

        initRootLayout();

        showDashboard();
        showOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/RootLayout.fxml"));
            _rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(_rootLayout);
            _primaryStage.setResizable(false);
            _primaryStage.setScene(scene);
            _primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the overview.
     */
    public void showOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/Overview.fxml"));
            AnchorPane overview = loader.load();

            // Set overview to the left side root layout.
            _rootLayout.setLeft(overview);

            // Give the controller access to the main app.
            overviewController = loader.getController();
            overviewController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the dashboard.
     */
    public void showDashboard() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/Dashboard.fxml"));
            AnchorPane dashboard = loader.load();

            // Set dashboard to the right side root layout.
            _rootLayout.setRight(dashboard);

            // Give the controller access to the main app.
            dashboardController = loader.getController();
            dashboardController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the controller of the dashboard.
     *
     * @return The dashboard controller
     */
    public DashboardController getDashboardController() {
        return dashboardController;
    }

    /**
     * Gets the controller of the overview.
     *
     * @return The overview controller
     */
    public OverviewController getOverviewController() {
        return overviewController;
    }

    /**
     * Creates a singleton class.
     */
    public static MainApp getInstance()
    {
        if (_mainApp == null)
            _mainApp = new MainApp();

        return _mainApp;
    }
}
