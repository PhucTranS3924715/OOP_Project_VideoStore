package Project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Objects;

public class VideoStoreApp extends Application {
    private VideoStoreManagement vsm = new VideoStoreManagement();

    @Override
    public void start(Stage primaryStage) {
        if (vsm.loadData())
            System.out.println("Load successful");
        mainApp();
        if (vsm.saveData())
            System.out.println("Save successful");
    }

    public void loginPage(){
        Stage loginStage = new Stage();
        // Create the login form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

        // Login type label and combo box
        Label loginTypeLabel = new Label("Login as:");
        grid.add(loginTypeLabel, 0, 0);
        ComboBox<String> loginTypeComboBox = new ComboBox<>();
        loginTypeComboBox.getItems().addAll("Customer", "Admin");
        loginTypeComboBox.setValue("Customer");
        grid.add(loginTypeComboBox, 1, 0);

        // Username label and field
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        // Password label and field
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        // Login button
        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 3);
        loginButton.setOnAction(e -> {
            String loginType = loginTypeComboBox.getValue();
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean success;
            if (loginType.equals("Customer")) {
                success = vsm.login(username, password);
            } else {
                success = vsm.adminLogin(username, password);
            }
            if (success) {
                loginStage.close();
                mainApp();
            } else {
                Text warningText = new Text();
                warningText.setFill(Color.RED);
                warningText.setText("Invalid username or password.");
                grid.add(warningText, 1, 4);
            }
        });

        // Set up the scene and stage
        Scene scene = new Scene(grid, 400, 200);
        loginStage.setScene(scene);
        loginStage.setTitle("Video Store Login");
        loginStage.show();
    }

    public void mainApp() {
        // Create the main application window
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);

        // Number of columns to display
        int numColumns = 3;

        // Add items to the grid
        for (int i = 0; i < vsm.getItems().size(); i++) {
            Item item = vsm.getItems().get(i);

            // Calculate the row and column indices
            int row = i / numColumns;
            int column = i % numColumns;

            // Item ID label
            Label itemIDLabel = new Label(item.getID());

            // Item title label
            Label itemTitleLabel = new Label(item.getTitle());

            // Item image view
            ImageView itemImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/dvdmockup.jpg")).toExternalForm());
            itemImageView.setFitWidth(200);
            itemImageView.setFitHeight(200);

            // Create a VBox to group the components together
            VBox itemBox = new VBox(5);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.getChildren().addAll(itemImageView, itemIDLabel, itemTitleLabel);

            // Add the VBox to the grid
            GridPane.setConstraints(itemBox, column, row);
            grid.getChildren().add(itemBox);
        }

        // Set up the scene and stage
        Scene scene = new Scene(grid);
        Stage customerScreen = new Stage();
        customerScreen.setScene(scene);
        customerScreen.setTitle("Video Store");
        customerScreen.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
