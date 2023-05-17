package Project;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class VideoStoreApp extends Application {
    private VideoStoreManagement vsm = new VideoStoreManagement();

    @Override
    public void start(Stage primaryStage) {
        if (vsm.loadData()) System.out.println("Load successful");

        rentItemStage(primaryStage);

//        primaryStage.setOnCloseRequest(windowEvent -> {
//            if (vsm.saveData()) System.out.println("Save successful");
//        });
    }

    public void loginPage(Stage primaryStage) {
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
            if (loginType.equals("Customer")) {
                if (vsm.login(username, password)) {
                    loginStage.close();
                    rentItemStage(primaryStage);
                } else {
                    Text warningText = new Text();
                    warningText.setFill(Color.RED);
                    warningText.setText("Invalid username or password.");
                    grid.add(warningText, 1, 4);
                }
            } else {
                if (vsm.adminLogin(username, password)) {
                    // TODO: Call admin stage
                } else {
                    Text warningText = new Text();
                    warningText.setFill(Color.RED);
                    warningText.setText("Invalid username or password.");
                    grid.add(warningText, 1, 4);
                }
            }

        });

        // Set up the scene and stage
        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Store Login");
        primaryStage.show();
    }

    public void rentItemStage(Stage primaryStage) {
        // Create the main application window
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

        // Create the buttons
        Button rentItemButton = new Button("Rent item");
        Button returnItemButton = new Button("Return item");
        Button rewardPointsButton = new Button("Reward points");
        Button viewUpdateInfoButton = new Button("View/Update info");

        // Create an HBox to group the buttons together
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.getChildren().addAll(rentItemButton, returnItemButton, rewardPointsButton, viewUpdateInfoButton);

        // Add the button box to the grid
        GridPane.setConstraints(buttonBox, 0, 0, 5, 1);
        grid.getChildren().add(buttonBox);

        // Number of columns to display
        int numColumns = 3;

        // Add items to the grid
        for (int i = 0; i < vsm.getItems().size(); i++) {
            Item item = vsm.getItems().get(i);

            // Calculate the row and column indices
            int row = i / numColumns + 1;
            int column = i % numColumns;

            // Item ID label
            Label itemIDLabel = new Label(item.getID());

            // Item title label
            Label itemTitleLabel = new Label(item.getTitle());

            // Item image view
            ImageView itemImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/dvdmockup" +
                    ".jpg")).toExternalForm());
            itemImageView.setFitWidth(200);
            itemImageView.setFitHeight(200);

            // Rent button
            Button rentButton = new Button("Rent");
            rentButton.setVisible(false);

            // Show the rent button when the mouse enters the VBox
            VBox itemBox = new VBox(5);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.getChildren().addAll(itemImageView, itemIDLabel, itemTitleLabel, rentButton);
            itemBox.setOnMouseEntered(e -> rentButton.setVisible(true));
            itemBox.setOnMouseExited(e -> rentButton.setVisible(false));

            // Show the item information window when the rent button is clicked
            rentButton.setOnAction(e -> showItemInformation(item));

            // Add the VBox to the grid
            grid.add(itemBox, column, row);
        }

        rentItemButton.setOnAction(actionEvent -> {
            rentItemStage(primaryStage);
        });

        // Set up the scene and stage
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Store");
        primaryStage.show();
    }

    private void showItemInformation(Item item) {
        Stage showItemInformationStage = new Stage();

        // Create the item information window
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

        // Item ID label and field
        Label itemIDLabel = new Label("ID:");
        GridPane.setConstraints(itemIDLabel, 0, 0);
        Label itemIDField = new Label(item.getID());
        GridPane.setConstraints(itemIDField, 1, 0);

        // Item title label and field
        Label itemTitleLabel = new Label("Title:");
        GridPane.setConstraints(itemTitleLabel, 0, 1);
        Label itemTitleField = new Label(item.getTitle());
        GridPane.setConstraints(itemTitleField, 1, 1);

        // Item rental type label and field
        Label itemRentalTypeLabel = new Label("Rental Type:");
        GridPane.setConstraints(itemRentalTypeLabel, 0, 2);
        Label itemRentalTypeField = new Label(item.getRentalType());
        GridPane.setConstraints(itemRentalTypeField, 1, 2);

        // Item loan type label and field
        Label itemLoanTypeLabel = new Label("Loan Type:");
        GridPane.setConstraints(itemLoanTypeLabel, 0, 3);
        Label itemLoanTypeField = new Label(item.getLoanType());
        GridPane.setConstraints(itemLoanTypeField, 1, 3);

        // Item number of copies label and field
        Label itemNoOfCopiesLabel = new Label("Number of copies:");
        GridPane.setConstraints(itemNoOfCopiesLabel, 0, 4);
        Label itemNoOfCopiesField = new Label(String.valueOf(item.getNoOfCopy()));
        GridPane.setConstraints(itemNoOfCopiesField, 1, 4);

        // Item rental fee label and field
        Label itemRentalFeeLabel = new Label("Rental Fee:");
        GridPane.setConstraints(itemRentalFeeLabel, 0, 5);
        Label itemRentalFeeField = new Label(String.valueOf(item.getRentalFee()));
        GridPane.setConstraints(itemRentalFeeField, 1, 5);

        // Rent button
        Button rentButton = new Button("Rent");
        GridPane.setConstraints(rentButton, 0, 6);
        Label messageText = new Label();
        rentButton.setOnAction(e -> {
            boolean success = vsm.rentItem(item.getID(), messageText);
            if (success) {
                messageText.setTextFill(Color.GREEN);
                // Create a pause transition to delay closing the window
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event -> showItemInformationStage.close());
                pause.play();
            } else {
                messageText.setTextFill(Color.RED);
            }
        });

        // Close button
        Button closeButton = new Button("Close");
        GridPane.setConstraints(closeButton, 1, 6);
        closeButton.setOnAction(e -> {
            showItemInformationStage.close();
        });

        // Add all components to the grid
        grid.getChildren().addAll(itemIDLabel, itemIDField, itemTitleLabel, itemTitleField, itemRentalTypeLabel,
                itemRentalTypeField, itemLoanTypeLabel, itemLoanTypeField, itemNoOfCopiesLabel, itemNoOfCopiesField,
                itemRentalFeeLabel, itemRentalFeeField, rentButton, closeButton);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(grid, messageText);

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 400, 200);
        showItemInformationStage.setScene(scene);
        showItemInformationStage.setTitle("Item Information");
        showItemInformationStage.show();
    }

    public void returnItemStage(Stage primaryStage){
        // Create the main application window
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

        // Create the buttons
        Button rentItemButton = new Button("Rent item");
        Button returnItemButton = new Button("Return item");
        Button rewardPointsButton = new Button("Reward points");
        Button viewUpdateInfoButton = new Button("View/Update info");

        // Create an HBox to group the buttons together
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.getChildren().addAll(rentItemButton, returnItemButton, rewardPointsButton, viewUpdateInfoButton);

        // Add the button box to the grid
        GridPane.setConstraints(buttonBox, 0, 0, 5, 1);
        grid.getChildren().add(buttonBox);

        // TODO: Implement returnItemStage
    }

    public static void main(String[] args) {
        launch(args);
    }

}
