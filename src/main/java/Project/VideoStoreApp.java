package Project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VideoStoreApp extends Application {
    private VideoStoreManagement vsm = new VideoStoreManagement();
    private Text warningText = new Text();

    @Override
    public void start(Stage primaryStage) {
        vsm.loadData();
        loginPage();
        vsm.saveData();
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

    public void mainApp(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}
