package Project;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



import java.util.*;


public class VideoStoreApp extends Application {
    private VideoStoreManagement vsm = new VideoStoreManagement();

    @Override
    public void start(Stage primaryStage) {
        if (vsm.loadData()) System.out.println("Load successful");

        loginStage(primaryStage);

        primaryStage.setOnCloseRequest(windowEvent -> {
            if (vsm.saveData()) System.out.println("Save successful");
        });
    }

    public void loginStage(Stage primaryStage) {
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
        buttonDesignForStage(loginButton);
        grid.add(loginButton, 1, 3);
        loginButton.setOnAction(e -> {
            String loginType = loginTypeComboBox.getValue();
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (loginType.equals("Customer")) {
                if (vsm.login(username, password)) {
                    loginStage.close();
                    customerHome(primaryStage);
                } else {
                    Text warningText = new Text();
                    warningText.setFill(Color.RED);
                    usernameField.setStyle("-fx-border-color: red;");
                    passwordField.setStyle("-fx-border-color: red;");
                    warningText.setText("Invalid username or password.");
                    grid.add(warningText, 1, 4);
                }
            } else {
                if (vsm.adminLogin(username, password)) {
                    loginStage.close();
                    adminHome(primaryStage);
                } else {
                    Text warningText = new Text();
                    warningText.setFill(Color.RED);
                    usernameField.setStyle("-fx-border-color: red;");
                    passwordField.setStyle("-fx-border-color: red;");
                    warningText.setText("Invalid username or password.");
                    grid.add(warningText, 1, 4);
                }
            }
        });

        // Create title and student info
        Text courseTitle = new Text("RMIT University – INTE2512 Object-Oriented Programming\nFINAL PROJECT – A VIDEO "
                + "STORE");
        courseTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        courseTitle.setTextAlignment(TextAlignment.CENTER);

        Text student1 = new Text("Tran Dai Phuc – S3924715");
        Text student2 = new Text("Nguyen Hong Anh – S3924711");
        Text student3 = new Text("Vo Hong Trien – S3907397");
        Text student4 = new Text("Vo Hoang Khanh – S3926310");

        GridPane studentInfo = new GridPane();
        studentInfo.setAlignment(Pos.CENTER);
        studentInfo.setHgap(10);
        studentInfo.setVgap(10);

        studentInfo.add(student1, 0, 0);
        studentInfo.add(student2, 0, 1);
        studentInfo.add(student3, 1, 0);
        studentInfo.add(student4, 1, 1);

        VBox screen = new VBox();
        screen.setAlignment(Pos.CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(courseTitle, studentInfo, grid);

        // Set up the scene and stage
        Scene scene = new Scene(screen, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Store Login");
        primaryStage.show();
    }

    public void customerHome(Stage primaryStage) {
        // Create home text
        Text homeText = new Text(vsm.welcomeTitle());
        homeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Create grid for buttons
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

        // Create the buttons
        Button rentItemButton = new Button("Rent item");
        rentItemButton.setPrefSize(120, 50);
        buttonDesignForStage(rentItemButton);
        ImageView rentItemIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/rentItem.png")).toExternalForm());
        rentItemIcon.setFitHeight(20);
        rentItemIcon.setFitWidth(20);
        rentItemButton.setGraphic(rentItemIcon);
        rentItemButton.setOnAction(actionEvent -> {
            rentItemStage(primaryStage);
        });

        Button returnItemButton = new Button("Return item");
        returnItemButton.setPrefSize(120, 50);
        buttonDesignForStage(returnItemButton);
        ImageView returnItemIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/returnItem" + ".png")).toExternalForm());
        returnItemIcon.setFitHeight(20);
        returnItemIcon.setFitWidth(20);
        returnItemButton.setGraphic(returnItemIcon);
        returnItemButton.setOnAction(actionEvent -> {
            returnItemStage(primaryStage, vsm.getCurrentUser());
        });

        Button rewardPointsButton = new Button("Reward points");
        rewardPointsButton.setPrefSize(120, 50);
        buttonDesignForStage(rewardPointsButton);
        ImageView rewardPointsIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images" +
                "/rewardPoints.png")).toExternalForm());
        rewardPointsIcon.setFitHeight(20);
        rewardPointsIcon.setFitWidth(20);
        rewardPointsButton.setGraphic(rewardPointsIcon);
        rewardPointsButton.setOnAction(actionEvent -> {
            rewardPointsStage(primaryStage, vsm.getCurrentUser());
        });

        Button viewUpdateInfoButton = new Button("View/Update\ninformation");
        viewUpdateInfoButton.setPrefSize(120, 50);
        buttonDesignForStage(viewUpdateInfoButton);
        viewUpdateInfoButton.setAlignment(Pos.CENTER);
        ImageView viewInfoIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/info.png")).toExternalForm());
        viewInfoIcon.setFitHeight(20);
        viewInfoIcon.setFitWidth(20);
        viewUpdateInfoButton.setGraphic(viewInfoIcon);
        viewUpdateInfoButton.setOnAction(actionEvent -> {
            viewUpdateInfoStage(primaryStage);
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(120);
        buttonDesignForStage(logoutButton);
        ImageView logoutIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/logout.png")).toExternalForm());
        logoutIcon.setFitHeight(20);
        logoutIcon.setFitWidth(20);
        logoutButton.setGraphic(logoutIcon);
        logoutButton.setOnAction(actionEvent -> {
            loginStage(primaryStage);
        });

        grid.add(rentItemButton, 0, 0);
        grid.add(returnItemButton, 1, 0);
        grid.add(rewardPointsButton, 0, 1);
        grid.add(viewUpdateInfoButton, 1, 1);
        grid.add(logoutButton, 1, 2);

        // Create vertical box to store everything
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(homeText, grid);

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 500, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Store");
        primaryStage.show();
    }

    private void buttonDesignForHeader(Button button){
        button.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-border-width: 2;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2;"));
    }

    private void buttonDesignForStage(Button button){
        button.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #00abe4; -fx-border-color: #00abe4; -fx-border-width: 2;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2;"));
    }

    private HBox customerPageButtonBox(Stage primaryStage) {
        // Create the buttons
        Button homeButton = new Button("Home");
        homeButton.setOnAction(actionEvent -> {
            customerHome(primaryStage);
        });
        homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; -fx-font-weight: bold;");
        homeButton.setOnMouseEntered(e -> homeButton.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-border-width: 2; -fx-font-weight: bold;"));
        homeButton.setOnMouseExited(e -> homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; -fx-font-weight: bold;"));

        Button rentItemButton = new Button("Rent item");
        rentItemButton.setOnAction(actionEvent -> {
            rentItemStage(primaryStage);
        });
        buttonDesignForHeader(rentItemButton);

        Button returnItemButton = new Button("Return item");
        returnItemButton.setOnAction(actionEvent -> {
            returnItemStage(primaryStage, vsm.getCurrentUser());
        });
        buttonDesignForHeader(returnItemButton);

        Button rewardPointsButton = new Button("Reward points");
        rewardPointsButton.setOnAction(actionEvent -> {
            rewardPointsStage(primaryStage, vsm.getCurrentUser());
        });
        buttonDesignForHeader(rewardPointsButton);

        Button viewUpdateInfoButton = new Button("View/Update info");
        viewUpdateInfoButton.setOnAction(actionEvent -> {
            viewUpdateInfoStage(primaryStage);
        });
        buttonDesignForHeader(viewUpdateInfoButton);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(actionEvent -> {
            loginStage(primaryStage);
        });
        logoutButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; -fx-font-weight: bold;");
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-border-width: 2; -fx-font-weight: bold;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; -fx-font-weight: bold;"));

        // Create an HBox to group the buttons together
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.getChildren().addAll(homeButton, rentItemButton, returnItemButton, rewardPointsButton,
                viewUpdateInfoButton, logoutButton);
        buttonBox.setStyle("-fx-background-color: #00abe4;");
        buttonBox.setPrefHeight(50);

        return buttonBox;
    }

    public void rentItemStage(Stage primaryStage) {
        // Number of columns to display items
        int numColumns = 3;

        // Create grid to store items
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

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
            ImageView itemImageView =
                    new ImageView(Objects.requireNonNull(getClass().getResource("/Images/dvdmockup" + ".jpg")).toExternalForm());
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

        // Create a scroll pane to add the grid
        ScrollPane scrollPane = new ScrollPane();
        StackPane stackPane = new StackPane(grid);
        scrollPane.setContent(stackPane);


        // Vertical box to store everything
        VBox vBox = new VBox(10, customerPageButtonBox(primaryStage), scrollPane);
        vBox.setAlignment(Pos.TOP_CENTER);

        // Set up the scene and stage
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rent Item");
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

    public void returnItemStage(Stage primaryStage, Customer currentUser) {
        // Number of columns to display items
        int numColumns = 3;

        // Retrieve rented items of the current user
        List<Item> rentedItems = currentUser.getItems();

        // Create grid to store items
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

        // Add items to the grid
        if (rentedItems.isEmpty()) {
            // Display message if inventory is empty
            Label emptyLabel = new Label("Your inventory is currently empty.");
            emptyLabel.setStyle("-fx-font-family: 'Arial';" + "-fx-font-weight: bold;" + "-fx-font-size: " +
                    "30px;");
            GridPane.setHalignment(emptyLabel, HPos.CENTER); // Center the label horizontally
            GridPane.setValignment(emptyLabel, VPos.CENTER); // Center the label vertically

            grid.add(emptyLabel, 0, 0, numColumns, 1); // Span the label across all columns
        } else {
            for (int i = 0; i < rentedItems.size(); i++) {
                Item item = rentedItems.get(i);

                // Calculate the row and column indices
                int row = i / numColumns + 1;
                int column = i % numColumns;

                // Item ID label
                Label itemIDLabel = new Label(item.getID());

                // Item title label
                Label itemTitleLabel = new Label(item.getTitle());

                // Item image view
                ImageView itemImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/Images" +
                        "/dvdmockup.jpg")).toExternalForm());
                itemImageView.setFitWidth(200);
                itemImageView.setFitHeight(200);

                // Return button
                Button returnButton = new Button("Return");

                // Show the return button when the mouse enters the VBox
                VBox itemBox = new VBox(5);
                itemBox.setAlignment(Pos.CENTER);
                itemBox.getChildren().addAll(itemImageView, itemIDLabel, itemTitleLabel, returnButton);
                itemBox.setOnMouseEntered(e -> returnButton.setVisible(true));
                itemBox.setOnMouseExited(e -> returnButton.setVisible(false));

                // Perform return action when the return button is clicked
                returnButton.setOnAction(e -> {
                    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationDialog.setTitle("Return Confirmation");
                    confirmationDialog.setHeaderText("Return Item");
                    confirmationDialog.setContentText("Are you sure you want to return this item?");
                    Optional<ButtonType> result = confirmationDialog.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        boolean success = vsm.returnItem(item.getID());
                        if (success) {
                            // Remove the returned item from the grid
                            grid.getChildren().remove(itemBox);
                        }
                    }
                });

                // Add the VBox to the grid
                grid.add(itemBox, column, row);
            }
        }

        // Create a scroll pane to add the grid
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(grid);

        // Vertical box to store everything
        VBox vBox = new VBox(10, customerPageButtonBox(primaryStage), scrollPane);
        vBox.setAlignment(Pos.TOP_CENTER);

        // Set up the scene and stage
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Return Item");
        primaryStage.show();
    }

    //REWARD POINTS
    public void rewardPointsStage(Stage primaryStage, Customer currentUser) {
        // Create a label to display the reward points
        Label rewardPointsLabel = new Label("Total Reward Points: " + currentUser.getRewardPoints());
        rewardPointsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Create an ImageView for the image
        ImageView image = new ImageView(getClass().getResource("/Images/pompomicon.png").toExternalForm());
        image.setFitWidth(50);
        image.setFitHeight(50);

        // Create a button to redeem reward points
        Button redeemButton = new Button("Redeem Points");
        redeemButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-family: Arial; " +
                "-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Create a button to go back to the main menu
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-family: Arial; " +
                "-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Set up the action for the redeem button
        redeemButton.setOnAction(e -> {
            int rewardPoints = currentUser.getRewardPoints();
            if (rewardPoints >= 100) {
                // Ask the user whether they want to spend the points or continue accumulating
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Redeem Reward Points");
                alert.setHeaderText("You have " + rewardPoints + " reward points.");
                alert.setContentText("Do you want to spend 100 reward points to rent an item for free?");

                // Add buttons to the alert dialog
                ButtonType spendPointsButton = new ButtonType("Spend Points");
                ButtonType continueButton = new ButtonType("Continue Accumulating");
                alert.getButtonTypes().setAll(spendPointsButton, continueButton);

                // Handle the user's choice
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == spendPointsButton) {
                    // Deduct 100 reward points
                    currentUser.setRewardPoints(rewardPoints - 100);
                    // Show the rent item stage
                    showRentItemStage(primaryStage, currentUser);
                }
            }
        });
        // Set up the action for the back button
        backButton.setOnAction(e -> {
            customerHome(primaryStage);
        });
        // Create an HBox to contain the image and buttons
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(image, redeemButton, backButton);

        // Set up the layout
        VBox vBox = new VBox(20, rewardPointsLabel, redeemButton, backButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Reward Points");
        primaryStage.show();
    }

    public void showRentItemStage(Stage primaryStage, Customer currentUser) {
        // Number of columns to display items
        int numColumns = 3;

        // Create grid to store items
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

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
            ImageView itemImageView =
                    new ImageView(Objects.requireNonNull(getClass().getResource("/Images/dvdmockup.jpg")).toExternalForm());
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

        // Create a scroll pane to add the grid
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(grid);

        // Vertical box to store everything
        VBox vBox = new VBox(10, customerPageButtonBox(primaryStage), scrollPane);
        vBox.setAlignment(Pos.TOP_CENTER);

        // Set up the scene and stage
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rent Item");
        primaryStage.show();
    }
    //END OF REWARD POINTS

    public void viewUpdateInfoStage(Stage primaryStage) {
        // Create labels and fields
        Text infoText = new Text();
        Label nameLabel = new Label("Enter new name:");
        TextField nameField = new TextField();
        Label addressLabel = new Label("Enter new address:");
        TextField addressField = new TextField();
        Label phoneLabel = new Label("Enter new phone number:");
        TextField phoneField = new TextField();
        Label usernameLabel = new Label("Enter new username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Enter new password:");
        TextField passwordField = new TextField();

        Button updateButton = new Button("Update information");
        HBox updateBox = new HBox(updateButton);
        updateBox.setAlignment(Pos.CENTER);

        // Display current customer info
        Customer customer = vsm.findCustomerByID(vsm.getCurrentUserID());
        if (customer != null) {
            infoText.setText(customer.customerInfo());
        } else {
            infoText.setText("No customer found!");
        }

        // Set action for updateButton to call updateCustomer method
        updateButton.setOnAction(event -> {
            vsm.updateCustomer(vsm.getCurrentUserID(), infoText, nameField.getText(), addressField.getText(),
                    phoneField.getText(), "", "", usernameField.getText(), passwordField.getText(), "");
        });

        // Create a grid to hold the text fields
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(10);
        infoGrid.setVgap(10);
        infoGrid.add(nameLabel, 0, 0);
        infoGrid.add(nameField, 1, 0);
        infoGrid.add(addressLabel, 0, 1);
        infoGrid.add(addressField, 1, 1);
        infoGrid.add(phoneLabel, 0, 2);
        infoGrid.add(phoneField, 1, 2);
        infoGrid.add(usernameLabel, 0, 3);
        infoGrid.add(usernameField, 1, 3);
        infoGrid.add(passwordLabel, 0, 4);
        infoGrid.add(passwordField, 1, 4);

        HBox infoBox = new HBox(10, infoText, infoGrid);
        infoBox.setAlignment(Pos.CENTER);

        // Create a VBox to store everything
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(customerPageButtonBox(primaryStage), infoBox, updateBox);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(vBox, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View/Update information");
        primaryStage.show();
    }

    public void adminHome(Stage primaryStage) {
        // Create home text
        Text homeText = new Text("Welcome back, admin!");
        homeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Create item grid for buttons
        GridPane itemGrid = new GridPane();
        itemGrid.setPadding(new Insets(10, 10, 10, 10));
        itemGrid.setAlignment(Pos.CENTER);
        itemGrid.setVgap(5);
        itemGrid.setHgap(5);

        Text itemManagementText = new Text("Items management");
        itemManagementText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        // Create the buttons for item management
        Button addItemButton = new Button("Add item");
        addItemButton.setPrefSize(120, 50);
        buttonDesignForStage(addItemButton);
        ImageView addItemIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/add.png")).toExternalForm());
        addItemIcon.setFitHeight(20);
        addItemIcon.setFitWidth(20);
        addItemButton.setGraphic(addItemIcon);
        addItemButton.setOnAction(actionEvent -> {
            addItemStage(primaryStage);
        });

        Button updateItemButton = new Button("Update item");
        updateItemButton.setPrefSize(120, 50);
        buttonDesignForStage(updateItemButton);
        ImageView updateItemIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/update.png")).toExternalForm());
        updateItemIcon.setFitHeight(20);
        updateItemIcon.setFitWidth(20);
        updateItemButton.setGraphic(updateItemIcon);
        updateItemButton.setOnAction(actionEvent -> {
            updateItemStage(primaryStage);
        });

        Button increaseItemButton = new Button("Increase\ncopies");
        increaseItemButton.setPrefSize(120, 50);
        buttonDesignForStage(increaseItemButton);
        ImageView increaseItemIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/add.png")).toExternalForm());
        increaseItemIcon.setFitHeight(20);
        increaseItemIcon.setFitWidth(20);
        increaseItemButton.setGraphic(increaseItemIcon);
        increaseItemButton.setOnAction(actionEvent -> {
            increaseItemStage(primaryStage);
        });

        Button deleteItemButton = new Button("Delete item");
        deleteItemButton.setPrefSize(120, 50);
        buttonDesignForStage(deleteItemButton);
        ImageView deleteItemIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/delete.png")).toExternalForm());
        deleteItemIcon.setFitHeight(20);
        deleteItemIcon.setFitWidth(20);
        deleteItemButton.setGraphic(deleteItemIcon);
        deleteItemButton.setOnAction(actionEvent -> {
            deleteItemStage(primaryStage);
        });

        Button displayItemButton = new Button("Display item");
        displayItemButton.setPrefSize(120, 50);
        buttonDesignForStage(displayItemButton);
        ImageView displayItemIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/display.png")).toExternalForm());
        displayItemIcon.setFitHeight(20);
        displayItemIcon.setFitWidth(20);
        displayItemButton.setGraphic(displayItemIcon);
        displayItemButton.setOnAction(actionEvent -> {
            displayItemStage(primaryStage);
        });

        Button searchItemButton = new Button("Search item");
        searchItemButton.setPrefSize(120, 50);
        buttonDesignForStage(searchItemButton);
        ImageView searchItemIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/search.png")).toExternalForm());
        searchItemIcon.setFitHeight(20);
        searchItemIcon.setFitWidth(20);
        searchItemButton.setGraphic(searchItemIcon);
        searchItemButton.setOnAction(actionEvent -> {
            searchItemStage(primaryStage);
        });

        itemGrid.add(addItemButton, 0, 0);
        itemGrid.add(updateItemButton, 0, 1);
        itemGrid.add(increaseItemButton, 0, 2);
        itemGrid.add(deleteItemButton, 1, 0);
        itemGrid.add(displayItemButton, 1, 1);
        itemGrid.add(searchItemButton, 1, 2);

        VBox itemForm = new VBox();
        itemForm.setAlignment(Pos.CENTER);
        itemForm.getChildren().addAll(itemManagementText, itemGrid);

        // Create customer grid for buttons
        GridPane customerGrid = new GridPane();
        customerGrid.setPadding(new Insets(10, 10, 10, 10));
        customerGrid.setAlignment(Pos.CENTER);
        customerGrid.setVgap(5);
        customerGrid.setHgap(5);

        Text customerManagementText = new Text("Customers management");
        customerManagementText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        // Create the buttons for customer management
        Button addCustomerButton = new Button("Add customer");
        addCustomerButton.setPrefSize(120, 50);
        buttonDesignForStage(addCustomerButton);
        ImageView addCustomerIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/add.png")).toExternalForm());
        addCustomerIcon.setFitHeight(20);
        addCustomerIcon.setFitWidth(20);
        addCustomerButton.setGraphic(addCustomerIcon);
        addCustomerButton.setOnAction(actionEvent -> {
            addCustomerStage(primaryStage);
        });

        Button updateCustomerButton = new Button("Update\ncustomer");
        updateCustomerButton.setPrefSize(120, 50);
        buttonDesignForStage(updateCustomerButton);
        ImageView updateCustomerIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/update" + ".png")).toExternalForm());
        updateCustomerIcon.setFitHeight(20);
        updateCustomerIcon.setFitWidth(20);
        updateCustomerButton.setGraphic(updateCustomerIcon);
        updateCustomerButton.setOnAction(actionEvent -> {
            updateCustomerStage(primaryStage);
        });

        Button displayCustomerButton = new Button("Display\ncustomer");
        displayCustomerButton.setPrefSize(120, 50);
        buttonDesignForStage(displayCustomerButton);
        ImageView displayCustomerIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/display" + ".png")).toExternalForm());
        displayCustomerIcon.setFitHeight(20);
        displayCustomerIcon.setFitWidth(20);
        displayCustomerButton.setGraphic(displayCustomerIcon);
        displayCustomerButton.setOnAction(actionEvent -> {
            displayCustomerStage(primaryStage);
        });

        Button searchCustomerButton = new Button("Search\ncustomer");
        searchCustomerButton.setPrefSize(120, 50);
        buttonDesignForStage(searchCustomerButton);
        ImageView searchCustomerIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/search" + ".png")).toExternalForm());
        searchCustomerIcon.setFitHeight(20);
        searchCustomerIcon.setFitWidth(20);
        searchCustomerButton.setGraphic(searchCustomerIcon);
        searchCustomerButton.setOnAction(actionEvent -> {
            searchCustomerStage(primaryStage);
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(120);
        buttonDesignForStage(logoutButton);
        ImageView logoutIcon =
                new ImageView(Objects.requireNonNull(getClass().getResource("/Images/logout.png")).toExternalForm());
        logoutIcon.setFitHeight(20);
        logoutIcon.setFitWidth(20);
        logoutButton.setGraphic(logoutIcon);
        logoutButton.setOnAction(actionEvent -> {
            loginStage(primaryStage);
        });

        customerGrid.add(addCustomerButton, 0, 0);
        customerGrid.add(updateCustomerButton, 0, 1);
        customerGrid.add(displayCustomerButton, 1, 0);
        customerGrid.add(searchCustomerButton, 1, 1);
        customerGrid.add(logoutButton, 1, 2);

        VBox customerForm = new VBox();
        customerForm.setAlignment(Pos.TOP_CENTER);
        customerForm.getChildren().addAll(customerManagementText, customerGrid);

        // Create the complete form include item and customer form
        HBox completeForm = new HBox();
        completeForm.setAlignment(Pos.TOP_CENTER);
        completeForm.getChildren().addAll(itemForm, customerForm);

        // Create a VBox to store everything
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getChildren().addAll(homeText, completeForm);

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Store");
        primaryStage.show();
    }

    private VBox adminPageButtonBox(Stage primaryStage) {
        // Home button
        Button homeButton = new Button("Home");
        homeButton.setOnAction(actionEvent -> {
            adminHome(primaryStage);
        });
        homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; -fx-font-weight: bold;");
        homeButton.setOnMouseEntered(e -> homeButton.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-border-width: 2; -fx-font-weight: bold;"));
        homeButton.setOnMouseExited(e -> homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; -fx-font-weight: bold;"));

        // Buttons for items management
        Button addItemButton = new Button("Add item");
        addItemButton.setOnAction(actionEvent -> {
            addItemStage(primaryStage);
        });
        buttonDesignForHeader(addItemButton);

        Button updateItemButton = new Button("Update item");
        updateItemButton.setOnAction(actionEvent -> {
            updateItemStage(primaryStage);
        });
        buttonDesignForHeader(updateItemButton);

        Button increaseItemButton = new Button("Increase item");
        increaseItemButton.setOnAction(actionEvent -> {
            increaseItemStage(primaryStage);
        });
        buttonDesignForHeader(increaseItemButton);

        Button deleteItemButton = new Button("Delete item");
        deleteItemButton.setOnAction(actionEvent -> {
            deleteItemStage(primaryStage);
        });
        buttonDesignForHeader(deleteItemButton);

        Button displayItemButton = new Button("Display item");
        displayItemButton.setOnAction(actionEvent -> {
            displayItemStage(primaryStage);
        });
        buttonDesignForHeader(displayItemButton);

        Button searchItemButton = new Button("Search item");
        searchItemButton.setOnAction(actionEvent -> {
            searchItemStage(primaryStage);
        });
        buttonDesignForHeader(searchItemButton);

        // Buttons for customers management
        Button addCustomerButton = new Button("Add customer");
        addCustomerButton.setOnAction(actionEvent -> {
            addCustomerStage(primaryStage);
        });
        buttonDesignForHeader(addCustomerButton);

        Button updateCustomerButton = new Button("Update customer");
        updateCustomerButton.setOnAction(actionEvent -> {
            updateCustomerStage(primaryStage);
        });
        buttonDesignForHeader(updateCustomerButton);

        Button displayCustomerButton = new Button("Display customer");
        displayCustomerButton.setOnAction(actionEvent -> {
            displayCustomerStage(primaryStage);
        });
        buttonDesignForHeader(displayCustomerButton);

        Button searchCustomerButton = new Button("Search customer");
        searchCustomerButton.setOnAction(actionEvent -> {
            searchCustomerStage(primaryStage);
        });
        buttonDesignForHeader(searchCustomerButton);

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(actionEvent -> {
            loginStage(primaryStage);
        });
        logoutButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; -fx-font-weight: bold;");
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-border-width: 2; -fx-font-weight: bold;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; -fx-font-weight: bold;"));

        // Create an HBox to group the buttons together
        HBox itemBox = new HBox();
        itemBox.setAlignment(Pos.CENTER);
        itemBox.setSpacing(20);
        itemBox.getChildren().addAll(homeButton, addItemButton, updateItemButton, increaseItemButton,
                deleteItemButton, displayItemButton, searchItemButton, logoutButton);

        HBox customerBox = new HBox();
        customerBox.setAlignment(Pos.CENTER);
        customerBox.setSpacing(20);
        customerBox.getChildren().addAll(addCustomerButton, updateCustomerButton, displayCustomerButton,
                searchCustomerButton);

        VBox buttonBox = new VBox(5, itemBox, customerBox);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-background-color: #00abe4;");
        buttonBox.setPrefHeight(50);

        return buttonBox;
    }

    public void addItemStage(Stage primaryStage) {
        // Create ID label, field and check button
        Label idLabel = new Label("Enter item ID:");
        TextField idField = new TextField();

        Button checkButton = new Button("Check");
        buttonDesignForStage(checkButton);

        HBox idBox = new HBox(10, idLabel, idField, checkButton);
        idBox.setAlignment(Pos.CENTER);

        // Create other labels and fields
        Text infoText = new Text();
        Label titleLabel = new Label("Enter title:");
        TextField titleField = new TextField();

        Label rentalTypeLabel = new Label("Enter rental type:");
        ComboBox<String> rentalTypeComboBox = new ComboBox<>();
        rentalTypeComboBox.getItems().addAll("Record", "DVD", "Game");
        rentalTypeComboBox.setValue("");

        Label loanTypeLabel = new Label("Enter loan type");
        ComboBox<String> loanTypeComboBox = new ComboBox<>();
        loanTypeComboBox.getItems().addAll("2-day", "1-week");
        loanTypeComboBox.setValue("");

        Label noOfCopyLabel = new Label("Enter number of copy:");
        TextField noOfCopyField = new TextField();

        Label rentalFeeLabel = new Label("Enter rental fee: ");
        TextField rentalFeeField = new TextField();

        Label genreLabel = new Label("Enter genre:");
        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("Action", "Horror", "Drama", "Comedy");
        genreComboBox.setValue("");

        // Create add button
        Button addButton = new Button("Add Item");
        HBox addBox = new HBox(addButton);
        addBox.setAlignment(Pos.CENTER);

        // Initially hide all labels and fields
        infoText.setVisible(false);
        titleLabel.setVisible(false);
        titleField.setVisible(false);
        rentalTypeLabel.setVisible(false);
        rentalTypeComboBox.setVisible(false);
        loanTypeLabel.setVisible(false);
        loanTypeComboBox.setVisible(false);
        noOfCopyLabel.setVisible(false);
        noOfCopyField.setVisible(false);
        rentalFeeLabel.setVisible(false);
        rentalFeeField.setVisible(false);
        genreLabel.setVisible(false);
        genreComboBox.setVisible(false);
        addButton.setVisible(false);

        // Set action for checkButton to check if the entered ID is valid
        checkButton.setOnAction(event -> {
            infoText.setText("");
            String ID = idField.getText();
            if (vsm.isValidItemID(ID)) {    // Check if the ID is valid
                // Show all labels and fields
                infoText.setVisible(true);
                titleLabel.setVisible(true);
                titleField.setVisible(true);
                rentalTypeLabel.setVisible(true);
                rentalTypeComboBox.setVisible(true);
                loanTypeLabel.setVisible(true);
                loanTypeComboBox.setVisible(true);
                noOfCopyLabel.setVisible(true);
                noOfCopyField.setVisible(true);
                rentalFeeLabel.setVisible(true);
                rentalFeeField.setVisible(true);

                genreLabel.setVisible(true);
                genreComboBox.setVisible(true);
                addButton.setVisible(true);
                Item customer = vsm.findItemByID(ID);

                // If there are no IDs matched the current ID => can add new item to the list
                if (customer == null) {
                    infoText.setFill(Color.GREEN);
                    idField.setStyle("-fx-border-color: green;");
                    infoText.setText("Item ID is valid");
                }
                // If the ID already exist
                else {
                    // Hide all labels and fields
                    titleLabel.setVisible(false);
                    titleField.setVisible(false);
                    rentalTypeLabel.setVisible(false);
                    rentalTypeComboBox.setVisible(false);
                    loanTypeLabel.setVisible(false);
                    loanTypeComboBox.setVisible(false);
                    noOfCopyLabel.setVisible(false);
                    noOfCopyField.setVisible(false);
                    rentalFeeLabel.setVisible(false);
                    rentalFeeField.setVisible(false);
                    genreLabel.setVisible(false);
                    genreComboBox.setVisible(false);
                    addButton.setVisible(false);

                    infoText.setFill(Color.RED);
                    idField.setStyle("-fx-border-color: red;");
                    infoText.setText("Item ID already exists!");
                }
            } else {
                infoText.setVisible(true);
                titleLabel.setVisible(false);
                titleField.setVisible(false);
                rentalTypeLabel.setVisible(false);
                rentalTypeComboBox.setVisible(false);
                loanTypeLabel.setVisible(false);
                loanTypeComboBox.setVisible(false);
                noOfCopyLabel.setVisible(false);
                noOfCopyField.setVisible(false);
                rentalFeeLabel.setVisible(false);
                rentalFeeField.setVisible(false);
                genreLabel.setVisible(false);
                genreComboBox.setVisible(false);
                addButton.setVisible(false);

                infoText.setFill(Color.RED);
                idField.setStyle("-fx-border-color: red;");
                infoText.setText("Invalid item ID format! Must follow format \"Ixxx-yyyy\"");
            }
        });

        // Set action for add button
        addButton.setOnAction(actionEvent -> {
            vsm.addItem(idField.getText(), infoText, titleField.getText(), rentalTypeComboBox.getValue(),
                    loanTypeComboBox.getValue(), noOfCopyField.getText(), rentalFeeField.getText(),
                    genreComboBox.getValue());
        });

        // Create a GridPane to hold text fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(rentalTypeLabel, 0, 1);
        grid.add(rentalTypeComboBox, 1, 1);
        grid.add(loanTypeLabel, 0, 2);
        grid.add(loanTypeComboBox, 1, 2);
        grid.add(noOfCopyLabel, 0, 3);
        grid.add(noOfCopyField, 1, 3);
        grid.add(rentalFeeLabel, 0, 4);
        grid.add(rentalFeeField, 1, 4);
        grid.add(genreLabel, 0, 5);
        grid.add(genreComboBox, 1, 5);

        // Create VBox to store the grid and infoText
        VBox infoBox = new VBox(5, grid, infoText);
        infoBox.setAlignment(Pos.CENTER);

        // Create a VBox to store everything
        VBox screen = new VBox();
        screen.setAlignment(Pos.TOP_CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(adminPageButtonBox(primaryStage), idBox, infoBox, addBox);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(screen, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Item");
        primaryStage.show();
    }

    public void updateItemStage(Stage primaryStage) {
        // TODO: Implement stage
    }

    public void increaseItemStage(Stage primaryStage) {
        // Create ID label, field and check button
        Label idLabel = new Label("Enter item ID:");
        TextField idField = new TextField();

        Button checkButton = new Button("Check");
        buttonDesignForStage(checkButton);

        HBox idBox = new HBox(10, idLabel, idField, checkButton);
        idBox.setAlignment(Pos.CENTER);

        // Create a status text and number field
        Text infoText = new Text();
        Label numberLabel = new Label("Enter number of new copies:");
        TextField numberField = new TextField();

        // Create a button to increase the copies
        Button increaseButton = new Button("Increase copies");
        buttonDesignForStage(increaseButton);

        // Initially hide all fields below the ID field
        infoText.setVisible(false);
        numberLabel.setVisible(false);
        numberField.setVisible(false);
        increaseButton.setVisible(false);

        // Set action for checkButton to check if the entered ID is valid
        checkButton.setOnAction(event -> {
            infoText.setText("");
            String ID = idField.getText();
            if (vsm.isValidItemID(ID)) {    // If ID is valid
                // Show all fields below the ID field
                infoText.setVisible(true);
                numberLabel.setVisible(true);
                numberField.setVisible(true);
                increaseButton.setVisible(true);
                idField.setStyle("-fx-border-color: green;");
                // Display current customer info
                Item item = vsm.findItemByID(ID);
                if (item != null) {
                    infoText.setFill(Color.BLACK);
                    infoText.setText(item.itemInfo());
                } else {
                    infoText.setFill(Color.RED);
                    idField.setStyle("-fx-border-color: red;");
                    infoText.setText("No item found!");
                }
            } else {
                // Hide all labels and fields if ID is invalid
                infoText.setVisible(false);
                numberLabel.setVisible(false);
                numberField.setVisible(false);
                increaseButton.setVisible(false);

                infoText.setFill(Color.RED);
                idField.setStyle("-fx-border-color: red;");
                infoText.setText("Invalid item ID!");
                infoText.setVisible(true);
            }
        });

        // Set action for increase button to call increaseNoOfCopies method
        increaseButton.setOnAction(actionEvent -> {
            vsm.increaseNoOfCopies(idField.getText(), infoText, numberField.getText());
        });

        // Create a GridPane to hold text fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.add(numberLabel, 0, 0);
        grid.add(numberField, 1, 0);

        // Create info box to store infoText and grid
        VBox infoBox = new VBox(15, infoText, grid);
        infoBox.setAlignment(Pos.CENTER);

        // Create a VBox to store everything
        VBox screen = new VBox();
        screen.setAlignment(Pos.TOP_CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(adminPageButtonBox(primaryStage), idBox, infoBox, increaseButton);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(screen, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Increase Number of Items");
        primaryStage.show();
    }

    public void deleteItemStage(Stage primaryStage) {
        // TODO: Implement stage
    }

    public void displayItemStage(Stage primaryStage) {
        Button sortButton = new Button("Display item");
        buttonDesignForStage(sortButton);

        ComboBox<String> sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll("Sort by ID", "Sort by Title", "With no Copies");
        sortComboBox.setValue("Sort by ID");

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);

        buttonBox.getChildren().addAll(sortButton, sortComboBox);

        GridPane gridPane = new GridPane();
        VBox sortTable = new VBox();
        sortTable.setSpacing(10);
        sortButton.setOnAction(e -> {
            String selectedOption = sortComboBox.getValue();
            if (selectedOption.equals("Sort by ID")) {
                sortTable.getChildren().clear();
                vsm.displayIDSortItem(gridPane);
            } else if (selectedOption.equals("Sort by Title")) {
                sortTable.getChildren().clear();
                vsm.displayTitleSortItem(gridPane);
            } else if (selectedOption.equals("With no Copies")) {
                sortTable.getChildren().clear();
                vsm.displayItemNoCopies(gridPane);
            }
            sortTable.getChildren().addAll(buttonBox, gridPane);
        });

        // Vertical box to store everything
        VBox vBox = new VBox(10, adminPageButtonBox(primaryStage), buttonBox, sortTable);
        vBox.setAlignment(Pos.TOP_CENTER);

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Store");
        primaryStage.show();
    }

    /*public void searchItemStage(Stage primaryStage) {
        ComboBox<String> searchComboBox = new ComboBox<>();
        searchComboBox.getItems().addAll("Search by ID", "Search by Title");
        searchComboBox.setValue("Search by ID");

        TextField searchField = new TextField();
        GridPane itemTable = new GridPane();
        itemTable.setAlignment(Pos.CENTER);

        VBox searchTable = new VBox();

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e ->{
            String selectedOption = searchComboBox.getValue();
            if (selectedOption.equals("Search by ID")) {
                String ID = searchField.getText();
                searchTable.getChildren().clear();
                vsm.searchItemID(itemTable, ID);
                searchTable.getChildren().addAll(itemTable);
            } else if (selectedOption.equals("Search by Title")) {
                String ID = searchField.getText();
                searchTable.getChildren().clear();
                vsm.searchItemTitle(itemTable, ID);
                searchTable.getChildren().addAll(itemTable);
            }
        });

        GridPane searchBox = new GridPane();
        searchBox.setHgap(5);
        searchBox.setVgap(5);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.add(searchField, 0, 0);
        searchBox.add(searchButton, 1, 0);
        searchBox.add(searchComboBox, 2, 0);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(adminPageButtonBox(primaryStage), searchBox, searchTable);

        Scene scene = new Scene(vBox, 900, 600);

        primaryStage.setTitle("Search item");
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/

    // Modified version of searchItemStage
    public void searchItemStage(Stage primaryStage) {
        ComboBox<String> searchComboBox = new ComboBox<>();
        searchComboBox.getItems().addAll("Search by ID", "Search by Title");
        searchComboBox.setValue("Search by ID");

        TextField searchField = new TextField();
        GridPane itemTable = new GridPane();
        itemTable.setAlignment(Pos.CENTER);

        VBox searchTable = new VBox();

        // Add a listener to the text field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String selectedOption = searchComboBox.getValue();
            if (selectedOption.equals("Search by ID")) {
                String ID = newValue;
                searchTable.getChildren().clear();
                vsm.searchItemID(itemTable, ID);
            } else if (selectedOption.equals("Search by Title")) {
                String title = newValue;
                searchTable.getChildren().clear();
                vsm.searchItemTitle(itemTable, title);
            }
            searchTable.getChildren().addAll(itemTable);
        });

        GridPane searchBox = new GridPane();
        searchBox.setHgap(5);
        searchBox.setVgap(5);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.add(searchField, 0, 0);
        // Remove the search button
        //searchBox.add(searchButton, 1, 0);
        searchBox.add(searchComboBox, 2, 0);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(adminPageButtonBox(primaryStage), searchBox, searchTable);

        Scene scene = new Scene(vBox, 900, 600);

        primaryStage.setTitle("Search item");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addCustomerStage(Stage primaryStage) {
        // Create ID label, field and check button
        Label idLabel = new Label("Enter customer ID:");
        TextField idField = new TextField();

        Button checkButton = new Button("Check");
        buttonDesignForStage(checkButton);

        HBox idBox = new HBox(10, idLabel, idField, checkButton);
        idBox.setAlignment(Pos.CENTER);

        // Create other labels and fields
        Text infoText = new Text();
        Label nameLabel = new Label("Enter name:");
        TextField nameField = new TextField();
        Label addressLabel = new Label("Enter address:");
        TextField addressField = new TextField();
        Label phoneLabel = new Label("Enter phone number:");
        TextField phoneField = new TextField();
        Label usernameLabel = new Label("Create username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Create password:");
        TextField passwordField = new TextField();

        // Create add button
        Button addButton = new Button("Add Customer");
        HBox addBox = new HBox(addButton);
        addBox.setAlignment(Pos.CENTER);

        // Initially hide all labels and fields
        infoText.setVisible(false);
        nameLabel.setVisible(false);
        nameField.setVisible(false);
        addressLabel.setVisible(false);
        addressField.setVisible(false);
        phoneLabel.setVisible(false);
        phoneField.setVisible(false);
        usernameLabel.setVisible(false);
        usernameField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
        addButton.setVisible(false);

        // Set action for checkButton to check if the entered ID is valid
        checkButton.setOnAction(event -> {
            infoText.setText("");
            String ID = idField.getText();
            if (vsm.isValidCustomerID(ID)) {    // Check if the ID is valid
                // Show all labels and fields
                infoText.setVisible(true);
                nameLabel.setVisible(true);
                nameField.setVisible(true);
                addressLabel.setVisible(true);
                addressField.setVisible(true);
                phoneLabel.setVisible(true);
                phoneField.setVisible(true);
                usernameLabel.setVisible(true);
                usernameField.setVisible(true);
                passwordLabel.setVisible(true);
                passwordField.setVisible(true);
                addButton.setVisible(true);
                Customer customer = vsm.findCustomerByID(ID);
                // If there are no IDs matched the current ID => can add new customer to the list
                if (customer == null) {
                    infoText.setFill(Color.GREEN);
                    idField.setStyle("-fx-border-color: green;");
                    infoText.setText("Customer ID is valid");
                }
                // If the ID already exist
                else {
                    // Hide all labels and fields
                    nameLabel.setVisible(false);
                    nameField.setVisible(false);
                    addressLabel.setVisible(false);
                    addressField.setVisible(false);
                    phoneLabel.setVisible(false);
                    phoneField.setVisible(false);
                    usernameLabel.setVisible(false);
                    usernameField.setVisible(false);
                    passwordLabel.setVisible(false);
                    passwordField.setVisible(false);
                    addButton.setVisible(false);

                    infoText.setFill(Color.RED);
                    idField.setStyle("-fx-border-color: red;");
                    infoText.setText("Customer ID already exists!");
                }
            } else {
                nameLabel.setVisible(false);
                nameField.setVisible(false);
                addressLabel.setVisible(false);
                addressField.setVisible(false);
                phoneLabel.setVisible(false);
                phoneField.setVisible(false);
                usernameLabel.setVisible(false);
                usernameField.setVisible(false);
                passwordLabel.setVisible(false);
                passwordField.setVisible(false);
                addButton.setVisible(false);

                infoText.setVisible(true);
                infoText.setFill(Color.RED);
                idField.setStyle("-fx-border-color: red;");
                infoText.setText("Invalid customer ID format! Must follow format \"Cxxx\"");
            }
        });

        // Set action for add button
        addButton.setOnAction(actionEvent -> {
            vsm.addCustomer(idField.getText(), infoText, nameField.getText(), addressField.getText(),
                    phoneField.getText(), usernameField.getText(), passwordField.getText());
        });

        // Create a GridPane to hold text fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(addressLabel, 0, 1);
        grid.add(addressField, 1, 1);
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneField, 1, 2);
        grid.add(usernameLabel, 0, 3);
        grid.add(usernameField, 1, 3);
        grid.add(passwordLabel, 0, 4);
        grid.add(passwordField, 1, 4);

        // Create VBox to store the grid and infoText
        VBox infoBox = new VBox(5, grid, infoText);
        infoBox.setAlignment(Pos.CENTER);

        // Create a VBox to store everything
        VBox screen = new VBox();
        screen.setAlignment(Pos.TOP_CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(adminPageButtonBox(primaryStage), idBox, infoBox, addBox);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(screen, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Customer");
        primaryStage.show();
    }

    public void updateCustomerStage(Stage primaryStage) {
        // Create ID label, field and check button
        Label idLabel = new Label("Enter customer ID:");
        TextField idField = new TextField();

        Button checkButton = new Button("Check");
        buttonDesignForStage(checkButton);

        HBox idBox = new HBox(10, idLabel, idField, checkButton);
        idBox.setAlignment(Pos.CENTER);

        // Create other labels and fields
        Text infoText = new Text();
        Label nameLabel = new Label("Enter new name:");
        TextField nameField = new TextField();
        Label addressLabel = new Label("Enter new address:");
        TextField addressField = new TextField();
        Label phoneLabel = new Label("Enter new phone number:");
        TextField phoneField = new TextField();
        Label noOfRentalLabel = new Label("Enter new no. of rental:");
        TextField noOfRentalField = new TextField();
        Label customerTypeLabel = new Label("Choose new customer type:");
        ComboBox<String> customerTypeComboBox = new ComboBox<>();
        customerTypeComboBox.getItems().addAll("", "Guest", "Regular", "VIP");
        customerTypeComboBox.setValue("");
        Label usernameLabel = new Label("Enter new username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Enter new password:");
        TextField passwordField = new TextField();
        Label rewardPointLabel = new Label("Enter new reward point:");
        TextField rewardPointField = new TextField();

        // Create update button
        Button updateButton = new Button("Update Customer");
        HBox updateBox = new HBox(updateButton);
        updateBox.setAlignment(Pos.CENTER);

        // Initially hide all fields below the ID field
        infoText.setVisible(false);
        nameLabel.setVisible(false);
        nameField.setVisible(false);
        addressLabel.setVisible(false);
        addressField.setVisible(false);
        phoneLabel.setVisible(false);
        phoneField.setVisible(false);
        noOfRentalLabel.setVisible(false);
        noOfRentalField.setVisible(false);
        customerTypeLabel.setVisible(false);
        customerTypeComboBox.setVisible(false);
        usernameLabel.setVisible(false);
        usernameField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
        rewardPointLabel.setVisible(false);
        rewardPointField.setVisible(false);
        updateButton.setVisible(false);

        // Set action for checkButton to check if the entered ID is valid
        checkButton.setOnAction(event -> {
            infoText.setText("");
            String ID = idField.getText();
            if (vsm.isValidCustomerID(ID)) {    // If the ID is valid
                // Show all fields below the ID field
                infoText.setVisible(true);
                nameLabel.setVisible(true);
                nameField.setVisible(true);
                addressLabel.setVisible(true);
                addressField.setVisible(true);
                phoneLabel.setVisible(true);
                phoneField.setVisible(true);
                noOfRentalLabel.setVisible(true);
                noOfRentalField.setVisible(true);
                customerTypeLabel.setVisible(true);
                customerTypeComboBox.setVisible(true);
                usernameLabel.setVisible(true);
                usernameField.setVisible(true);
                passwordLabel.setVisible(true);
                passwordField.setVisible(true);
                rewardPointLabel.setVisible(true);
                rewardPointField.setVisible(true);
                updateButton.setVisible(true);

                // Display current customer info
                Customer customer = vsm.findCustomerByID(ID);
                if (customer != null) {
                    idField.setStyle("-fx-border-color: green;");
                    infoText.setFill(Color.BLACK);
                    infoText.setText(customer.customerInfo());
                } else {
                    infoText.setFill(Color.RED);
                    idField.setStyle("-fx-border-color: red;");
                    infoText.setText("No customer found!");
                }
            } else {
                // Hide all labels and fields if ID is invalid
                infoText.setVisible(false);
                nameLabel.setVisible(false);
                nameField.setVisible(false);
                addressLabel.setVisible(false);
                addressField.setVisible(false);
                phoneLabel.setVisible(false);
                phoneField.setVisible(false);
                noOfRentalLabel.setVisible(false);
                noOfRentalField.setVisible(false);
                customerTypeLabel.setVisible(false);
                customerTypeComboBox.setVisible(false);
                usernameLabel.setVisible(false);
                usernameField.setVisible(false);
                passwordLabel.setVisible(false);
                passwordField.setVisible(false);
                rewardPointLabel.setVisible(false);
                rewardPointField.setVisible(false);
                updateButton.setVisible(false);

                infoText.setFill(Color.RED);
                idField.setStyle("-fx-border-color: red;");
                infoText.setText("Invalid customer ID!");
                infoText.setVisible(true);
            }
        });

        // Set action for updateButton to call updateCustomer method
        updateButton.setOnAction(event -> {
            vsm.updateCustomer(idField.getText(), infoText, nameField.getText(), addressField.getText(),
                    phoneField.getText(), noOfRentalField.getText(), customerTypeComboBox.getValue(),
                    usernameField.getText(), passwordField.getText(), rewardPointField.getText());
        });

        // Create a GridPane to hold text fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(addressLabel, 0, 1);
        grid.add(addressField, 1, 1);
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneField, 1, 2);
        grid.add(noOfRentalLabel, 0, 3);
        grid.add(noOfRentalField, 1, 3);
        grid.add(customerTypeLabel, 0, 4);
        grid.add(customerTypeComboBox, 1, 4);
        grid.add(usernameLabel, 0, 5);
        grid.add(usernameField, 1, 5);
        grid.add(passwordLabel, 0, 6);
        grid.add(passwordField, 1, 6);
        grid.add(rewardPointLabel, 0, 7);
        grid.add(rewardPointField, 1, 7);

        // Create info box to store infoText and grid
        HBox infoBox = new HBox(15, infoText, grid);
        infoBox.setAlignment(Pos.CENTER);

        // Create a VBox to store everything
        VBox screen = new VBox();
        screen.setAlignment(Pos.TOP_CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(adminPageButtonBox(primaryStage), idBox, infoBox, updateBox);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(screen, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Update Customer");
        primaryStage.show();
    }

    public void displayCustomerStage(Stage primaryStage) {
        Button sortButton = new Button("Display customer");
        buttonDesignForStage(sortButton);

        ComboBox<String> sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll("Sort by ID", "Sort by Name", "Sort by Group");
        sortComboBox.setValue("Sort by ID");

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);

        buttonBox.getChildren().addAll(sortButton, sortComboBox);

        GridPane gridPane = new GridPane();
        VBox sortTable = new VBox();
        sortTable.setSpacing(10);
        sortButton.setOnAction(e -> {
            String selectedOption = sortComboBox.getValue();
            if (selectedOption.equals("Sort by ID")) {
                sortTable.getChildren().clear();
                vsm.displayIDSortCustomer(gridPane);
            } else if (selectedOption.equals("Sort by Name")) {
                sortTable.getChildren().clear();
                vsm.displayNameSortCustomer(gridPane);
            } else if (selectedOption.equals("Sort by Group")) {
                sortTable.getChildren().clear();
                vsm.displayGroup(gridPane);
            }
            sortTable.getChildren().addAll(buttonBox, gridPane);
        });

        // Vertical box to store everything
        VBox vBox = new VBox(10, adminPageButtonBox(primaryStage), buttonBox, sortTable);
        vBox.setAlignment(Pos.TOP_CENTER);

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Store");
        primaryStage.show();
    }

    /*public void searchCustomerStage(Stage primaryStage) {
        ComboBox<String> searchComboBox = new ComboBox<>();
        searchComboBox.getItems().addAll("Search by ID", "Search by Name");
        searchComboBox.setValue("Search by ID");

        TextField searchField = new TextField();
        GridPane customerTable = new GridPane();
        customerTable.setAlignment(Pos.CENTER);

        VBox searchTable = new VBox();

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e ->{
            String selectedOption = searchComboBox.getValue();
            if (selectedOption.equals("Search by ID")) {
                searchTable.getChildren().clear();
                vsm.searchCustomerID(customerTable, searchField.getText());
                searchTable.getChildren().addAll(customerTable);
            } else if (selectedOption.equals("Search by Name")) {
                searchTable.getChildren().clear();
                vsm.searchCustomerName(customerTable, searchField.getText());
                searchTable.getChildren().addAll(customerTable);
            }
        });

        GridPane searchBox = new GridPane();
        searchBox.setHgap(5);
        searchBox.setVgap(5);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.add(searchField, 0, 0);
        searchBox.add(searchButton, 1, 0);
        searchBox.add(searchComboBox, 2, 0);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(adminPageButtonBox(primaryStage), searchBox, searchTable);

        Scene scene = new Scene(vBox, 900, 600);

        primaryStage.setTitle("Search Customer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/

    // Modified version of searchCustomerStage
    public void searchCustomerStage(Stage primaryStage) {
        ComboBox<String> searchComboBox = new ComboBox<>();
        searchComboBox.getItems().addAll("Search by ID", "Search by Name");
        searchComboBox.setValue("Search by ID");

        TextField searchField = new TextField();
        GridPane customerTable = new GridPane();
        VBox searchTable = new VBox();

        // Add a listener to the text field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String selectedOption = searchComboBox.getValue();
            if (selectedOption.equals("Search by ID")) {
                String ID = newValue;
                searchTable.getChildren().clear();
                vsm.searchCustomerID(customerTable, ID);
            } else if (selectedOption.equals("Search by Name")) {
                String name = newValue;
                searchTable.getChildren().clear();
                vsm.searchCustomerName(customerTable, name);
            }
            searchTable.getChildren().addAll(customerTable);
        });

        GridPane searchBox = new GridPane();
        searchBox.setHgap(5);
        searchBox.setVgap(5);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.add(searchField, 0, 0);
        // Remove the search button
        //searchBox.add(searchButton, 1, 0);
        searchBox.add(searchComboBox, 2, 0);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(adminPageButtonBox(primaryStage), searchBox, searchTable);

        Scene scene = new Scene(vBox, 900, 600);

        primaryStage.setTitle("Search Customer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
