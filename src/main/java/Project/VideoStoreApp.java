package Project;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;


public class VideoStoreApp extends Application {
    private final VideoStoreManagement vsm = new VideoStoreManagement();

    @Override
    public void start(Stage primaryStage) {
        if (vsm.loadData()) System.out.println("Load successful");

        loginStage(primaryStage);

        primaryStage.setOnCloseRequest(windowEvent -> {
            if (vsm.saveData()) System.out.println("Save successful");
        });
    }

    private void loginStage(Stage primaryStage) {
        Stage loginStage = new Stage();
        // Create the login form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(25));
        grid.setStyle("-fx-background-color: #F8F8F8; -fx-background-radius: 20;");

        // Login type label and combo box
        Label loginTypeLabel = new Label("Login as:");
        loginTypeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        GridPane.setHalignment(loginTypeLabel, HPos.RIGHT);
        grid.add(loginTypeLabel, 0, 0);

        ComboBox<String> loginTypeComboBox = new ComboBox<>();
        loginTypeComboBox.getItems().addAll("Customer", "Admin");
        loginTypeComboBox.setValue("Customer");
        loginTypeComboBox.setStyle("-fx-font-size: 14px;");
        grid.add(loginTypeComboBox, 1, 0);

        // Username label and field
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        grid.add(usernameLabel, 0, 1);

        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-font-size: 14px;");
        grid.add(usernameField, 1, 1);

        // Password label and field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        GridPane.setHalignment(passwordLabel, HPos.RIGHT);
        grid.add(passwordLabel, 0, 2);

        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-font-size: 14px;");
        grid.add(passwordField, 1, 2);

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 14px; -fx-background-color: #333333; -fx-text-fill: white;");
        grid.add(loginButton, 1, 3);
        GridPane.setMargin(loginButton, new Insets(10, 0, 0, 0));

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
        Text courseTitle = new Text("RMIT University – INTE2512 Object-Oriented Programming\nFINAL PROJECT – A VIDEO STORE");
        courseTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        courseTitle.setFill(Color.WHITE);
        courseTitle.setTextAlignment(TextAlignment.CENTER);

        Text student1 = new Text("Tran Dai Phuc – S3924715");
        Text student2 = new Text("Nguyen Hong Anh – S3924711");
        Text student3 = new Text("Vo Hong Trien – S3907397");
        Text student4 = new Text("Vo Hoang Khanh – S3926310");

        VBox studentInfo = new VBox(5);
        studentInfo.setAlignment(Pos.CENTER);
        studentInfo.getChildren().addAll(student1, student2, student3, student4);

        VBox screen = new VBox(20);
        screen.setAlignment(Pos.CENTER);
        screen.setPadding(new Insets(50));
        screen.setStyle("-fx-background-color: #92c6db;");
        screen.getChildren().addAll(courseTitle, studentInfo, grid);

        // Set up the scene and stage
        Scene scene = new Scene(screen, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Store Login");
        primaryStage.show();
    }

    private void buttonDesignForHeader(Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-border-color: #92c6db; -fx-border-width: 2;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: white; -fx-border-color: white; " + "-fx" + "-border-width: 2;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-border-color: #92c6db; " + "-fx-border-width: 2;"));
    }

    private void buttonDesignForStage(Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-border-color: #92c6db; -fx-border-width: 2;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #92c6db; -fx-border-color: #92c6db; " + "-fx-border-width: 2;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-border-color: #92c6db; " + "-fx-border-width: 2;"));
    }

    private HBox customerPageButtonBox(Stage primaryStage) {
        // Create the buttons
        Button homeButton = new Button("Home");
        homeButton.setOnAction(actionEvent -> customerHome(primaryStage));
        homeButton.setStyle("-fx-background-color: transparent; -fx-border-color:  #ffffff ; -fx-border-width: 2; " + "-fx-font-weight: bold;");
        homeButton.setOnMouseEntered(e -> homeButton.setStyle("-fx-background-color: white; -fx-border-color: white; " + "-fx-border-width: 2; -fx-font-weight: bold;"));
        homeButton.setOnMouseExited(e -> homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: " + " #ffffff ; -fx-border-width: 2; -fx-font-weight: bold;"));

        Button rentItemButton = new Button("Rent item");
        rentItemButton.setOnAction(actionEvent -> rentItemStage(primaryStage));
        buttonDesignForHeader(rentItemButton);

        Button returnItemButton = new Button("Return item");
        returnItemButton.setOnAction(actionEvent -> returnItemStage(primaryStage, vsm.getCurrentUser()));
        buttonDesignForHeader(returnItemButton);

        Button rewardPointsButton = new Button("Reward points");
        rewardPointsButton.setOnAction(actionEvent -> rewardPointsStage(primaryStage, vsm.getCurrentUser()));
        buttonDesignForHeader(rewardPointsButton);

        Button viewUpdateInfoButton = new Button("View/Update info");
        viewUpdateInfoButton.setOnAction(actionEvent -> viewUpdateInfoStage(primaryStage));
        buttonDesignForHeader(viewUpdateInfoButton);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(actionEvent -> loginStage(primaryStage));
        logoutButton.setStyle("-fx-background-color: transparent; -fx-border-color:  #ffffff ; -fx-border-width: 2; " + "-fx-font-weight: bold;");
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: white; -fx-border-color: " + "white; -fx-border-width: 2; -fx-font-weight: bold;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: transparent; " + "-fx-border" + "-color:  #ffffff ; -fx-border-width: 2; -fx-font-weight: bold;"));

        // Create an HBox to group the buttons together
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.getChildren().addAll(homeButton, rentItemButton, returnItemButton, rewardPointsButton, viewUpdateInfoButton, logoutButton);
        buttonBox.setStyle("-fx-background-color: #92c6db;");
        buttonBox.setPrefHeight(50);

        return buttonBox;
    }

    private void customerHome(Stage primaryStage) {
        // Create home text
        Text homeText = new Text(vsm.welcomeTitle());
        homeText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        homeText.setFill(Color.WHITE);

        // Create mascot image
        ImageView mascotImage = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/pompomhappy.png")).toExternalForm());
        mascotImage.setFitHeight(250);
        mascotImage.setFitWidth(250);

        // Create grid for buttons
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);
        grid.setHgap(20);

        // Create the buttons
        Button rentItemButton = createStyledButton("Rent Item", "/Images/rentItem.png");
        Button returnItemButton = createStyledButton("Return Item", "/Images/returnItem.png");
        Button rewardPointsButton = createStyledButton("Reward Points", "/Images/rewardPoints.png");
        Button viewUpdateInfoButton = createStyledButton("View/Update\nInformation", "/Images/info.png");
        Button logoutButton = createStyledButton("Logout", "/Images/logout.png");

        rentItemButton.setOnAction(actionEvent -> rentItemStage(primaryStage));

        returnItemButton.setOnAction(actionEvent -> returnItemStage(primaryStage, vsm.getCurrentUser()));

        rewardPointsButton.setOnAction(actionEvent -> rewardPointsStage(primaryStage, vsm.getCurrentUser()));

        viewUpdateInfoButton.setOnAction(actionEvent -> viewUpdateInfoStage(primaryStage));

        logoutButton.setOnAction(actionEvent -> loginStage(primaryStage));

        grid.addColumn(0, rentItemButton, returnItemButton);
        grid.addColumn(1, rewardPointsButton, viewUpdateInfoButton);
        grid.add(logoutButton, 1, 2);

        // Create VBox for the mascot image
        VBox mascotBox = new VBox(mascotImage);
        mascotBox.setAlignment(Pos.BOTTOM_LEFT);
        mascotBox.setPadding(new Insets(0, 10, 10, 10));

        // Create VBox to hold home text and grid
        VBox contentBox = new VBox(homeText, grid);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setSpacing(30);

        // Create HBox to hold mascotBox and contentBox
        HBox hBox = new HBox(mascotBox, contentBox);
        hBox.setAlignment(Pos.CENTER);

        // Create VBox to store everything
        VBox vBox = new VBox(hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(
                new BackgroundFill(Color.rgb(146, 198, 219), CornerRadii.EMPTY, Insets.EMPTY))
        );
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(20);

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 800, 700);
        setButtonStyles(rentItemButton, returnItemButton, rewardPointsButton, viewUpdateInfoButton, logoutButton);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Store");
        primaryStage.setOnCloseRequest(windowEvent -> Platform.exit());
        primaryStage.show();
    }

    private Button createStyledButton(String text, String iconPath) {
        Button button = new Button(text);
        button.setPrefSize(200, 100);
        button.setBackground(new Background(
                new BackgroundFill(Color.rgb(240, 240, 240), new CornerRadii(10), Insets.EMPTY))
        );
        button.setTextFill(Color.BLACK);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        InputStream iconStream = getClass().getResourceAsStream(iconPath);
        if (iconStream != null) {
            Image icon = new Image(iconStream);
            ImageView iconView = new ImageView(icon);
            iconView.setFitWidth(30);
            iconView.setFitHeight(30);
            button.setGraphic(iconView);
        } else {
            System.err.println("Icon not found: " + iconPath);
        }

        return button;
    }

    private void setButtonStyles(Button... buttons) {
        for (Button button : buttons) {
            button.setOnMouseEntered(event -> button.setBackground(new Background(
                    new BackgroundFill(Color.rgb(220, 220, 220), new CornerRadii(10), Insets.EMPTY))
            ));
            button.setOnMouseExited(event -> button.setBackground(new Background(
                    new BackgroundFill(Color.rgb(240, 240, 240), new CornerRadii(10), Insets.EMPTY))
            ));
        }
    }

    private void rentItemStage(Stage primaryStage) {
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
            itemIDLabel.setFont(Font.font("Arial"));

            // Item title label
            Label itemTitleLabel = new Label(item.getTitle());
            itemTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            // Item image view
            ImageView itemImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/dvdmockup" + ".jpg")).toExternalForm());
            itemImageView.setFitWidth(200);
            itemImageView.setFitHeight(200);

            // Rent button
            Button rentButton = new Button("Rent");
            buttonDesignForStage(rentButton);
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

        // Create a new instance of the customerPageButtonBox
        //HBox buttonBox = customerPageButtonBox(primaryStage, buttons);

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
        showItemInformationStage.initModality(Modality.APPLICATION_MODAL);

        // Create the item information window
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10); // Increase the vertical gap for better separation
        grid.setHgap(5);

        // Title label
        Label titleLabel = new Label("Item Information");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 25)); // Set font weight and size
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1); // Span 2 columns
        GridPane.setHalignment(titleLabel, HPos.CENTER); // Align to center

        // Item ID label and field
        Label itemIDLabel = new Label("ID:");
        itemIDLabel.setFont(Font.font("System", FontWeight.BOLD, 12)); // Set font weight to bold
        GridPane.setConstraints(itemIDLabel, 0, 1);
        Label itemIDField = new Label(item.getID());
        GridPane.setConstraints(itemIDField, 1, 1);

        // Item title label and field
        Label itemTitleLabel = new Label("Title:");
        itemTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 12)); // Set font weight to bold
        GridPane.setConstraints(itemTitleLabel, 0, 2);
        Label itemTitleField = new Label(item.getTitle());
        GridPane.setConstraints(itemTitleField, 1, 2);

        // Item rental type label and field
        Label itemRentalTypeLabel = new Label("Rental Type:");
        itemRentalTypeLabel.setFont(Font.font("System", FontWeight.BOLD, 12)); // Set font weight to bold
        GridPane.setConstraints(itemRentalTypeLabel, 0, 3);
        Label itemRentalTypeField = new Label(item.getRentalType());
        GridPane.setConstraints(itemRentalTypeField, 1, 3);

        // Item loan type label and field
        Label itemLoanTypeLabel = new Label("Loan Type:");
        itemLoanTypeLabel.setFont(Font.font("System", FontWeight.BOLD, 12)); // Set font weight to bold
        GridPane.setConstraints(itemLoanTypeLabel, 0, 4);
        Label itemLoanTypeField = new Label(item.getLoanType());
        GridPane.setConstraints(itemLoanTypeField, 1, 4);

        // Item number of copies label and field
        Label itemNoOfCopiesLabel = new Label("Number of copies:");
        itemNoOfCopiesLabel.setFont(Font.font("System", FontWeight.BOLD, 12)); // Set font weight to bold
        GridPane.setConstraints(itemNoOfCopiesLabel, 0, 5);
        Label itemNoOfCopiesField = new Label(String.valueOf(item.getNoOfCopy()));
        GridPane.setConstraints(itemNoOfCopiesField, 1, 5);

        // Item rental fee label and field
        Label itemRentalFeeLabel = new Label("Rental Fee:");
        itemRentalFeeLabel.setFont(Font.font("System", FontWeight.BOLD, 12)); // Set font weight to bold
        GridPane.setConstraints(itemRentalFeeLabel, 0, 6);
        Label itemRentalFeeField = new Label(String.valueOf(item.getRentalFee()));
        GridPane.setConstraints(itemRentalFeeField, 1, 6);

        // Rent button
        Button rentButton = new Button("Rent");
        buttonDesignForStage(rentButton);
        GridPane.setConstraints(rentButton, 0, 6);
        GridPane.setMargin(rentButton, new Insets(60, 0, 0, 0));

        Label messageText = new Label();
        VBox.setMargin(messageText, new Insets(20, 0, 0, 0));

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
        closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: #a792db; -fx-border-width: 2;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: #a792db; -fx-border-color: #a792db; " + "-fx-border-width: 2;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: " + "#a792db; " + "-fx-border-width: 2;"));

        GridPane.setConstraints(closeButton, 1, 6);
        GridPane.setMargin(closeButton, new Insets(60, 0, 0, 60));
        closeButton.setOnAction(e -> showItemInformationStage.close());

        // Set the row constraints
        RowConstraints titleRow = new RowConstraints();
        titleRow.setPrefHeight(40); // Increase the preferred height for the title row
        RowConstraints contentRow = new RowConstraints();
        contentRow.setPrefHeight(25); // Set the preferred height for the content rows
        grid.getRowConstraints().addAll(titleRow, contentRow, contentRow, contentRow, contentRow, contentRow, contentRow);

        // Add all components to the grid
        grid.getChildren().addAll(
                titleLabel,
                itemIDLabel, itemIDField, itemTitleLabel, itemTitleField,
                itemRentalTypeLabel, itemRentalTypeField, itemLoanTypeLabel, itemLoanTypeField,
                itemNoOfCopiesLabel, itemNoOfCopiesField, itemRentalFeeLabel, itemRentalFeeField,
                rentButton, closeButton
        );

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(grid, messageText);

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 300, 400);
        showItemInformationStage.setScene(scene);
        showItemInformationStage.setTitle("Item Information");
        showItemInformationStage.show();
    }

    private void returnItemStage(Stage primaryStage, Customer currentUser) {
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
            Label emptyLabel = new Label("Your inventory is currently empty");
            emptyLabel.setStyle("-fx-font-family: 'Arial';" + "-fx-font-weight: bold;" + "-fx-font-size: " + "30px;");
            VBox.setMargin(emptyLabel, new Insets(10));

            ImageView emptyImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/Images" + "/hertawonder.png")).toExternalForm());
            emptyImageView.setFitWidth(200);
            emptyImageView.setFitHeight(200);

            VBox emptyBox = new VBox(emptyLabel, emptyImageView);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setSpacing(10);

            GridPane.setHalignment(emptyBox, HPos.CENTER); // Center the VBox horizontally
            GridPane.setValignment(emptyBox, VPos.CENTER); // Center the VBox vertically

            grid.add(emptyBox, 0, 0, numColumns, 1); // Span the VBox across all columns
        } else {
            for (int i = 0; i < rentedItems.size(); i++) {
                Item item = rentedItems.get(i);

                // Calculate the row and column indices
                int row = i / numColumns + 1;
                int column = i % numColumns;

                // Item ID label
                Label itemIDLabel = new Label(item.getID());
                itemIDLabel.setFont(Font.font("Arial"));

                // Item title label
                Label itemTitleLabel = new Label(item.getTitle());
                itemTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));


                // Item image view
                ImageView itemImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/Images" + "/dvdmockup.jpg")).toExternalForm());
                itemImageView.setFitWidth(300);
                itemImageView.setFitHeight(300);

                // Return button
                Button returnButton = new Button("Return");
                returnButton.setVisible(false);
                buttonDesignForStage(returnButton);

                // Show the return button when the mouse enters the VBox
                VBox itemBox = new VBox(5);
                itemBox.setAlignment(Pos.CENTER);
                itemBox.getChildren().addAll(itemImageView, itemIDLabel, itemTitleLabel, returnButton);
                itemBox.setOnMouseEntered(e -> returnButton.setVisible(true));
                itemBox.setOnMouseExited(e -> returnButton.setVisible(false));

                // Perform return action when the return button is clicked
                returnButton.setOnAction(e -> {
                    Stage warningStage = new Stage();
                    warningStage.initModality(Modality.APPLICATION_MODAL);

                    Label warningLabel = new Label("Are you sure you want to return this item?");
                    warningLabel.setFont(Font.font("System", FontWeight.BOLD, 10));

                    Button yesButton = new Button("Yes");
                    yesButton.setStyle("-fx-background-color: transparent; -fx-border-color: #a792db; " + "-fx-border-width: 2;");
                    yesButton.setOnMouseEntered(e1 -> yesButton.setStyle("-fx-background-color: #a792db; " + "-fx-border-color: #a792db; " + "-fx-border-width: 2;"));
                    yesButton.setOnMouseExited(e1 -> yesButton.setStyle("-fx-background-color: transparent; " + "-fx-border-color: #a792db; " + "-fx-border-width: 2;"));

                    Button noButton = new Button("No");
                    buttonDesignForStage(noButton);

                    HBox buttonBox = new HBox(10, yesButton, noButton);
                    buttonBox.setAlignment(Pos.CENTER);

                    VBox warningBox = new VBox(10, warningLabel, buttonBox);
                    warningBox.setAlignment(Pos.CENTER);

                    Scene warningScene = new Scene(warningBox, 300, 100);
                    warningStage.setScene(warningScene);
                    warningStage.show();

                    yesButton.setOnAction(event -> {
                        boolean success = vsm.returnItem(item.getID());
                        if (success) {
                            // Remove the returned item from the grid
                            grid.getChildren().remove(itemBox);

                            // Check if the customer is eligible for promotion
                            if (currentUser.getCustomerType().equals("Guest") && currentUser.getNoOfRental() > 3) {
                                currentUser.setCustomerType("Regular");
                                currentUser.setNoOfRental(0);

                                // Create a new stage for the promotion message
                                Stage promotionStage = new Stage();
                                promotionStage.initModality(Modality.APPLICATION_MODAL);
                                promotionStage.setTitle("Promotion");

                                // Create the promotion message
                                Label promotionLabel = new Label("Congratulations! You have been promoted to a " + "Regular customer.");
                                promotionLabel.setWrapText(true);

                                // Create the close button
                                Button closeButton = new Button("Close");
                                closeButton.setOnAction(event2 -> promotionStage.close());

                                // Create the layout and add the controls
                                VBox root = new VBox(10, promotionLabel, closeButton);
                                root.setAlignment(Pos.CENTER);
                                root.setPadding(new Insets(10));

                                // Set the scene and show the stage
                                Scene scene = new Scene(root);
                                promotionStage.setScene(scene);
                                promotionStage.show();
                            } else if (currentUser.getCustomerType().equals("Regular") && currentUser.getNoOfRental() > 5) {
                                currentUser.setCustomerType("VIP");
                                currentUser.setNoOfRental(0);

                                // Create a new stage for the promotion message
                                Stage promotionStage = new Stage();
                                promotionStage.initModality(Modality.WINDOW_MODAL);
                                promotionStage.setTitle("Promotion");

                                // Create the promotion message
                                Label promotionLabel = new Label("Congratulations! You have been promoted to a VIP " + "customer.");
                                promotionLabel.setWrapText(true);

                                // Create the close button
                                Button closeButton = new Button("Close");
                                closeButton.setOnAction(event2 -> promotionStage.close());

                                // Create the layout and add the controls
                                VBox root = new VBox(10, promotionLabel, closeButton);
                                root.setAlignment(Pos.CENTER);
                                root.setPadding(new Insets(10));

                                // Set the scene and show the stage
                                Scene scene = new Scene(root);
                                promotionStage.setScene(scene);
                                promotionStage.show();
                            }
                            // Check if inventory is empty after returning the item
                            if (grid.getChildren().isEmpty()) {
                                Label emptyLabel = new Label("Your inventory is currently empty");
                                emptyLabel.setStyle("-fx-font-family: 'Arial';" + "-fx-font-weight: bold;" + "-fx" + "-font-size: " + "30px;");
                                VBox.setMargin(emptyLabel, new Insets(10));

                                ImageView emptyImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/hertawonder" + ".png")).toExternalForm());
                                emptyImageView.setFitWidth(300);
                                emptyImageView.setFitHeight(300);

                                VBox emptyBox = new VBox(emptyLabel, emptyImageView);
                                emptyBox.setAlignment(Pos.CENTER);
                                emptyBox.setSpacing(10);
                                GridPane.setHalignment(emptyLabel, HPos.CENTER);
                                GridPane.setValignment(emptyLabel, VPos.CENTER);
                                grid.add(emptyBox, 0, 0, numColumns, 1);
                            }
                            warningStage.close();
                        }
                    });
                    noButton.setOnAction(event -> warningStage.close());
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
    private void rewardPointsStage(Stage primaryStage, Customer currentUser) {
        // Check if the customer is a VIP
        if (!currentUser.getCustomerType().equals("VIP")) {
            // Create a new stage for the warning message
            Stage warningStage = new Stage();
            warningStage.initModality(Modality.APPLICATION_MODAL);
            warningStage.setTitle("Access Denied");

            // Create the warning message
            Label warningLabel = new Label("Sorry!! This section is VIP only.");
            warningLabel.setWrapText(true);

            // Create the close button
            Button closeButton = new Button("Close");
            buttonDesignForStage(closeButton);
            closeButton.setOnAction(event -> warningStage.close());

            // Create the layout and add the controls
            VBox root = new VBox(10, warningLabel, closeButton);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(10));

            // Set the scene and show the stage
            Scene scene = new Scene(root, 300, 100);
            warningStage.setScene(scene);
            warningStage.show();
            return;
        }

        // Create a label to display the reward points
        Label rewardPointsLabel = new Label("Total Reward Points: " + currentUser.getRewardPoints());
        rewardPointsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 35));

        // Create an ImageView for the image
        ImageView image = new ImageView();
        String imagePath = "file:src/main/resources/Images/pompomreward.png";
        image.setImage(new Image(imagePath));
        image.setFitWidth(350);
        image.setFitHeight(350);

        // Create a button to redeem reward points
        Button redeemButton = new Button("Redeem Points");
        redeemButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: #cc92db; " + "-fx-font-family: Arial; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-width: 2;");
        redeemButton.setOnMouseEntered(e -> redeemButton.setStyle("-fx-background-color: #cc92db; -fx-text-fill: " + "white; -fx-border-color: #cc92db; -fx-font-family: Arial; -fx-font-size: 14px; -fx-padding: 10px 20px; " + "-fx-border-width: 2;"));
        redeemButton.setOnMouseExited(e -> redeemButton.setStyle("-fx-background-color: white; -fx-text-fill: black; " + "-fx-border-color: #cc92db; -fx-font-family: Arial; -fx-font-size: 14px; -fx-padding: 10px 20px; " + "-fx-border-width: 2;"));

        // Create a button to go back to the main menu
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: #a792db; " + "-fx-font-family: Arial; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-width: 2;");
        backButton.setOnMouseEntered(e1 -> backButton.setStyle("-fx-background-color: #a792db; -fx-text-fill: white; " + "-fx-border-color: #a792db; -fx-font-family: Arial; -fx-font-size: 14px; -fx-padding: 10px 20px; " + "-fx-border-width: 2;"));
        backButton.setOnMouseExited(e1 -> backButton.setStyle("-fx-background-color: white; -fx-text-fill: black; " + "-fx-border-color: #a792db; -fx-font-family: Arial; -fx-font-size: 14px; -fx-padding: 10px 20px; " + "-fx-border-width: 2;"));

        // Set up the action for the redeem button
        redeemButton.setOnAction(e -> {
            int rewardPoints = currentUser.getRewardPoints();
            if (rewardPoints >= 100) {
                // Create a new stage for the confirmation dialog
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setTitle("Redeem Reward Points");

                // Create the confirmation message
                Text messageText = new Text("You have " + rewardPoints + " reward points!!!\nDo you want to spend " + "100" + " reward " + "points to rent an item for free?");
                messageText.setTextAlignment(TextAlignment.CENTER);

                // Create the buttons
                Button spendPointsButton = new Button("Spend Points");
                spendPointsButton.setStyle("-fx-background-color: transparent; -fx-border-color: #4CAF50; " + "-fx" + "-border-width: 2;");
                spendPointsButton.setOnMouseEntered(actionEvent -> spendPointsButton.setStyle("-fx-background-color: " + "#4CAF50; -fx-border-color: #4CAF50; " + "-fx-border-width: 2;"));
                spendPointsButton.setOnMouseExited(actionEvent -> spendPointsButton.setStyle("-fx-background-color: " + "transparent; -fx-border-color: #4CAF50; " + "-fx-border-width: 2;"));

                spendPointsButton.setOnAction(event -> {
                    // Show the rent item stage
                    showRentItemStage(primaryStage);
                    // Close the dialog stage
                    dialogStage.close();
                });
                Button continueButton = new Button("Continue Accumulating");
                buttonDesignForStage(continueButton);
                continueButton.setOnAction(event -> dialogStage.close());

                // Create the layout and add the controls
                HBox buttonBox = new HBox(10, spendPointsButton, continueButton);
                buttonBox.setAlignment(Pos.CENTER);
                VBox root = new VBox(10, messageText, buttonBox);
                root.setAlignment(Pos.CENTER);
                root.setPadding(new Insets(10));

                // Set the scene and show the stage
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);
                dialogStage.show();
            } else {
                // Display a warning message if the user doesn't have enough reward points
                Stage warningStage = new Stage();
                warningStage.initModality(Modality.APPLICATION_MODAL);
                warningStage.setTitle("Insufficient Reward Points");

                // Create the warning message
                Text warningLabel = new Text("You don't have enough reward points to redeem.");
                warningLabel.setTextAlignment(TextAlignment.CENTER);

                // Create the close button
                Button closeButton = new Button("Close");
                buttonDesignForStage(closeButton);
                closeButton.setOnAction(event -> warningStage.close());

                // Create the layout and add the controls
                VBox root = new VBox(10, warningLabel, closeButton);
                root.setAlignment(Pos.CENTER);
                root.setPadding(new Insets(10));

                // Set the scene and show the stage
                Scene scene = new Scene(root);
                warningStage.setScene(scene);
                warningStage.show();
            }
        });

        // Set up the action for the back button
        backButton.setOnAction(e -> customerHome(primaryStage));

        // Create a VBox to contain the reward points label and buttons
        VBox box = new VBox(25);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(rewardPointsLabel, redeemButton, backButton);

        // Create a GridPane to position the elements
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.add(box, 0, 0);
        gridPane.add(image, 1, 0);

        // Create a StackPane and set the background color to white
        StackPane stackPane = new StackPane(gridPane);
        stackPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Create a VBox to store everything
        VBox vBox = new VBox(10, customerPageButtonBox(primaryStage), stackPane);

        // Set up the layout
        Scene scene = new Scene(vBox, 900, 600);

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Reward Points");
        primaryStage.show();
    }

    private void showRentItemStage(Stage primaryStage) {
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
            itemIDLabel.setFont(Font.font("Arial"));

            // Item title label
            Label itemTitleLabel = new Label(item.getTitle());
            itemTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            // Item image view
            ImageView itemImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/dvdmockup" + ".jpg")).toExternalForm());
            itemImageView.setFitWidth(200);
            itemImageView.setFitHeight(200);

            // Rent button
            Button rentButton = new Button("Rent");
            buttonDesignForStage(rentButton);
            rentButton.setVisible(false);

            // Show the rent button when the mouse enters the VBox
            VBox itemBox = new VBox(5);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.getChildren().addAll(itemImageView, itemIDLabel, itemTitleLabel, rentButton);
            itemBox.setOnMouseEntered(e -> rentButton.setVisible(true));
            itemBox.setOnMouseExited(e -> rentButton.setVisible(false));

            // Show the item information window when the rent button is clicked
            Label messageText = new Label();

            rentButton.setOnAction(e -> {
                boolean success = vsm.rentItemRewardPoint(item.getID(), messageText);
                Stage infoStage = new Stage();
                infoStage.initModality(Modality.WINDOW_MODAL);
                if (success) {
                    // Create a new stage for the information message

                    infoStage.setTitle("Rental Successful");

                    // Create the information message
                    Label infoLabel = new Label("Congratulations! You have successfully rented an item for free.");
                    infoLabel.setWrapText(true);

                    // Create the close button
                    Button closeButton = new Button("Close");
                    buttonDesignForStage(closeButton);
                    closeButton.setOnAction(event -> {
                        // Close the information stage
                        infoStage.close();
                        // Return to the home page
                        customerHome(primaryStage);
                    });

                    // Create the layout and add the controls
                    VBox root = new VBox(10, infoLabel, closeButton);
                    root.setAlignment(Pos.CENTER);
                    root.setPadding(new Insets(10));

                    // Set the scene and show the stage
                    Scene scene = new Scene(root);
                    infoStage.setScene(scene);
                } else {
                    // Create a new stage for the information message

                    infoStage.setTitle("Rental Failed");

                    // Create the information message
                    Label infoLabel = new Label(messageText.getText());
                    infoLabel.setWrapText(true);

                    // Create the close button
                    Button closeButton = new Button("Close");
                    buttonDesignForStage(closeButton);
                    closeButton.setOnAction(event -> {
                        // Close the information stage
                        infoStage.close();
                        // Return to the home page
                        showRentItemStage(primaryStage);
                    });

                    // Create the layout and add the controls
                    VBox root = new VBox(10, infoLabel, closeButton);
                    root.setAlignment(Pos.CENTER);
                    root.setPadding(new Insets(10));

                    // Set the scene and show the stage
                    Scene scene = new Scene(root);
                    infoStage.setScene(scene);
                }
                infoStage.show();
            });

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

    private void viewUpdateInfoStage(Stage primaryStage) {
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
        buttonDesignForStage(updateButton);
        HBox updateBox = new HBox(updateButton);
        updateBox.setAlignment(Pos.CENTER);

        // Set font weight to bold for information fields
        nameLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 15px;");
        addressLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 15px;");
        phoneLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 15px;");
        usernameLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 15px;");
        passwordLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 15px;");

        // Create a Separator
        Separator separator = new Separator();

        // Display current customer info
        Customer customer = vsm.findCustomerByID(vsm.getCurrentUserID());
        if (customer != null) {
            infoText.setText(customer.customerInfo());
        } else {
            infoText.setText("No customer found!");
        }

        // Set action for updateButton to call updateCustomer method
        updateButton.setOnAction(event -> vsm.updateCustomer(vsm.getCurrentUserID(), infoText, nameField.getText(), addressField.getText(), phoneField.getText(), "", "", usernameField.getText(), passwordField.getText(), ""));

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
        vBox.getChildren().addAll(customerPageButtonBox(primaryStage), infoBox, separator, updateBox);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(vBox, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("View/Update information");
        primaryStage.show();
    }

    private void adminHome(Stage primaryStage) {
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
        ImageView addItemIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/add.png")).toExternalForm());
        addItemIcon.setFitHeight(20);
        addItemIcon.setFitWidth(20);
        addItemButton.setGraphic(addItemIcon);
        addItemButton.setOnAction(actionEvent -> addItemStage(primaryStage));

        Button updateItemButton = new Button("Update item");
        updateItemButton.setPrefSize(120, 50);
        buttonDesignForStage(updateItemButton);
        ImageView updateItemIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/update.png")).toExternalForm());
        updateItemIcon.setFitHeight(20);
        updateItemIcon.setFitWidth(20);
        updateItemButton.setGraphic(updateItemIcon);
        updateItemButton.setOnAction(actionEvent -> updateItemStage(primaryStage));

        Button increaseItemButton = new Button("Increase\ncopies");
        increaseItemButton.setPrefSize(120, 50);
        buttonDesignForStage(increaseItemButton);
        ImageView increaseItemIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/add.png")).toExternalForm());
        increaseItemIcon.setFitHeight(20);
        increaseItemIcon.setFitWidth(20);
        increaseItemButton.setGraphic(increaseItemIcon);
        increaseItemButton.setOnAction(actionEvent -> increaseItemStage(primaryStage));

        Button deleteItemButton = new Button("Delete item");
        deleteItemButton.setPrefSize(120, 50);
        buttonDesignForStage(deleteItemButton);
        ImageView deleteItemIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/delete.png")).toExternalForm());
        deleteItemIcon.setFitHeight(20);
        deleteItemIcon.setFitWidth(20);
        deleteItemButton.setGraphic(deleteItemIcon);
        deleteItemButton.setOnAction(actionEvent -> deleteItemStage(primaryStage));

        Button displayItemButton = new Button("Display item");
        displayItemButton.setPrefSize(120, 50);
        buttonDesignForStage(displayItemButton);
        ImageView displayItemIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/display.png")).toExternalForm());
        displayItemIcon.setFitHeight(20);
        displayItemIcon.setFitWidth(20);
        displayItemButton.setGraphic(displayItemIcon);
        displayItemButton.setOnAction(actionEvent -> displayItemStage(primaryStage));

        Button searchItemButton = new Button("Search item");
        searchItemButton.setPrefSize(120, 50);
        buttonDesignForStage(searchItemButton);
        ImageView searchItemIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/search.png")).toExternalForm());
        searchItemIcon.setFitHeight(20);
        searchItemIcon.setFitWidth(20);
        searchItemButton.setGraphic(searchItemIcon);
        searchItemButton.setOnAction(actionEvent -> searchItemStage(primaryStage));

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
        ImageView addCustomerIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/add.png")).toExternalForm());
        addCustomerIcon.setFitHeight(20);
        addCustomerIcon.setFitWidth(20);
        addCustomerButton.setGraphic(addCustomerIcon);
        addCustomerButton.setOnAction(actionEvent -> addCustomerStage(primaryStage));

        Button updateCustomerButton = new Button("Update\ncustomer");
        updateCustomerButton.setPrefSize(120, 50);
        buttonDesignForStage(updateCustomerButton);
        ImageView updateCustomerIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/update" + ".png")).toExternalForm());
        updateCustomerIcon.setFitHeight(20);
        updateCustomerIcon.setFitWidth(20);
        updateCustomerButton.setGraphic(updateCustomerIcon);
        updateCustomerButton.setOnAction(actionEvent -> updateCustomerStage(primaryStage));

        Button displayCustomerButton = new Button("Display\ncustomer");
        displayCustomerButton.setPrefSize(120, 50);
        buttonDesignForStage(displayCustomerButton);
        ImageView displayCustomerIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/display" + ".png")).toExternalForm());
        displayCustomerIcon.setFitHeight(20);
        displayCustomerIcon.setFitWidth(20);
        displayCustomerButton.setGraphic(displayCustomerIcon);
        displayCustomerButton.setOnAction(actionEvent -> displayCustomerStage(primaryStage));

        Button searchCustomerButton = new Button("Search\ncustomer");
        searchCustomerButton.setPrefSize(120, 50);
        buttonDesignForStage(searchCustomerButton);
        ImageView searchCustomerIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/search" + ".png")).toExternalForm());
        searchCustomerIcon.setFitHeight(20);
        searchCustomerIcon.setFitWidth(20);
        searchCustomerButton.setGraphic(searchCustomerIcon);
        searchCustomerButton.setOnAction(actionEvent -> searchCustomerStage(primaryStage));

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(120);
        buttonDesignForStage(logoutButton);
        ImageView logoutIcon = new ImageView(Objects.requireNonNull(getClass().getResource("/Images/logout.png")).toExternalForm());
        logoutIcon.setFitHeight(20);
        logoutIcon.setFitWidth(20);
        logoutButton.setGraphic(logoutIcon);
        logoutButton.setOnAction(actionEvent -> loginStage(primaryStage));

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
        homeButton.setOnAction(actionEvent -> adminHome(primaryStage));
        homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; " + "-fx-font-weight: bold;");
        homeButton.setOnMouseEntered(e -> homeButton.setStyle("-fx-background-color: white; -fx-border-color: white; " + "-fx-border-width: 2; -fx-font-weight: bold;"));
        homeButton.setOnMouseExited(e -> homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: " + "#00abe4; -fx-border-width: 2; -fx-font-weight: bold;"));

        // Buttons for items management
        Button addItemButton = new Button("Add item");
        addItemButton.setOnAction(actionEvent -> addItemStage(primaryStage));
        buttonDesignForHeader(addItemButton);

        Button updateItemButton = new Button("Update item");
        updateItemButton.setOnAction(actionEvent -> updateItemStage(primaryStage));
        buttonDesignForHeader(updateItemButton);

        Button increaseItemButton = new Button("Increase item");
        increaseItemButton.setOnAction(actionEvent -> increaseItemStage(primaryStage));
        buttonDesignForHeader(increaseItemButton);

        Button deleteItemButton = new Button("Delete item");
        deleteItemButton.setOnAction(actionEvent -> deleteItemStage(primaryStage));
        buttonDesignForHeader(deleteItemButton);

        Button displayItemButton = new Button("Display item");
        displayItemButton.setOnAction(actionEvent -> displayItemStage(primaryStage));
        buttonDesignForHeader(displayItemButton);

        Button searchItemButton = new Button("Search item");
        searchItemButton.setOnAction(actionEvent -> searchItemStage(primaryStage));
        buttonDesignForHeader(searchItemButton);

        // Buttons for customers management
        Button addCustomerButton = new Button("Add customer");
        addCustomerButton.setOnAction(actionEvent -> addCustomerStage(primaryStage));
        buttonDesignForHeader(addCustomerButton);

        Button updateCustomerButton = new Button("Update customer");
        updateCustomerButton.setOnAction(actionEvent -> updateCustomerStage(primaryStage));
        buttonDesignForHeader(updateCustomerButton);

        Button displayCustomerButton = new Button("Display customer");
        displayCustomerButton.setOnAction(actionEvent -> displayCustomerStage(primaryStage));
        buttonDesignForHeader(displayCustomerButton);

        Button searchCustomerButton = new Button("Search customer");
        searchCustomerButton.setOnAction(actionEvent -> searchCustomerStage(primaryStage));
        buttonDesignForHeader(searchCustomerButton);

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(actionEvent -> loginStage(primaryStage));
        logoutButton.setStyle("-fx-background-color: transparent; -fx-border-color: #00abe4; -fx-border-width: 2; " + "-fx-font-weight: bold;");
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: white; -fx-border-color: " + "white; -fx-border-width: 2; -fx-font-weight: bold;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: transparent; " + "-fx-border" + "-color: #00abe4; -fx-border-width: 2; -fx-font-weight: bold;"));

        // Create an HBox to group the buttons together
        HBox itemBox = new HBox();
        itemBox.setAlignment(Pos.CENTER);
        itemBox.setSpacing(20);
        itemBox.getChildren().addAll(homeButton, addItemButton, updateItemButton, increaseItemButton, deleteItemButton, displayItemButton, searchItemButton, logoutButton);

        HBox customerBox = new HBox();
        customerBox.setAlignment(Pos.CENTER);
        customerBox.setSpacing(20);
        customerBox.getChildren().addAll(addCustomerButton, updateCustomerButton, displayCustomerButton, searchCustomerButton);

        VBox buttonBox = new VBox(5, itemBox, customerBox);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-background-color: #00abe4;");
        buttonBox.setPrefHeight(50);

        return buttonBox;
    }

    private void addItemStage(Stage primaryStage) {
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
                    idField.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2;");
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
                    idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
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
                idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                infoText.setText("Invalid item ID format! Must follow format \"Ixxx-yyyy\"");
            }
        });

        // Set action for add button
        addButton.setOnAction(actionEvent -> vsm.addItem(idField.getText(), infoText, titleField.getText(), rentalTypeComboBox.getValue(), loanTypeComboBox.getValue(), noOfCopyField.getText(), rentalFeeField.getText(), genreComboBox.getValue()));

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
        Scene scene = new Scene(screen, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Item");
        primaryStage.show();
    }

    private void updateItemStage(Stage primaryStage) {
        // Create ID label, field and check button
        Label idLabel = new Label("Enter item ID:");
        TextField idField = new TextField();

        Button checkButton = new Button("Check");
        buttonDesignForStage(checkButton);

        HBox idBox = new HBox(10, idLabel, idField, checkButton);
        idBox.setAlignment(Pos.CENTER);

        // Create other labels and fields
        Text infoText = new Text();

        Label newIDLabel = new Label("Enter new ID:");
        TextField newIDField = new TextField();

        Label titleLabel = new Label("Enter new title:");
        TextField titleField = new TextField();

        Label rentalTypeLabel = new Label("Enter new rental type:");
        ComboBox<String> itemRentalTypeComboBox = new ComboBox<>();
        itemRentalTypeComboBox.getItems().addAll("Record", "DVD", "Game");
        itemRentalTypeComboBox.setValue("");

        Label loanTypeLabel = new Label("Enter new loan type:");
        ComboBox<String> itemLoanTypeComboBox = new ComboBox<>();
        itemLoanTypeComboBox.getItems().addAll("2-day", "1-week");
        itemLoanTypeComboBox.setValue("");

        Label noOfCopiesLabel = new Label("Enter new no. of copies :");
        TextField noOfCopiesField = new TextField();

        Label rentalFeeLabel = new Label("Enter new rental fee:");
        TextField rentalFeeField = new TextField();

        Label rentaStatusLabel = new Label("Enter new rental status:");
        ComboBox<String> itemrentalStatusComboBox = new ComboBox<>();
        itemrentalStatusComboBox.getItems().addAll("borrowed ", "available", "not available");
        itemrentalStatusComboBox.setValue("");

        Label genreLabel = new Label("Enter new genre:");
        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("Action", "Horror", "Drama", "Comedy");
        genreComboBox.setValue("");

        // Create update button
        Button updateButton = new Button("Update Item");
        HBox updateBox = new HBox(updateButton);
        updateBox.setAlignment(Pos.CENTER);

        // Initially hide all fields below the ID field
        infoText.setVisible(false);
        newIDLabel.setVisible(false);
        newIDField.setVisible(false);
        titleLabel.setVisible(false);
        titleField.setVisible(false);
        rentalTypeLabel.setVisible(false);
        itemRentalTypeComboBox.setVisible(false);
        loanTypeLabel.setVisible(false);
        itemLoanTypeComboBox.setVisible(false);
        noOfCopiesLabel.setVisible(false);
        noOfCopiesField.setVisible(false);
        rentalFeeLabel.setVisible(false);
        rentalFeeField.setVisible(false);
        rentaStatusLabel.setVisible(false);
        itemrentalStatusComboBox.setVisible(false);
        genreLabel.setVisible(false);
        genreComboBox.setVisible(false);
        updateButton.setVisible(false);

        // Set action for checkButton to check if the entered ID is valid
        checkButton.setOnAction(event -> {
            infoText.setText("");
            String ID = idField.getText();
            if (vsm.isValidItemID(ID)) {    // If the ID is valid
                // Show all fields below the ID field
                infoText.setVisible(true);
                newIDLabel.setVisible(true);
                newIDField.setVisible(true);
                titleLabel.setVisible(true);
                titleField.setVisible(true);
                rentalTypeLabel.setVisible(true);
                itemRentalTypeComboBox.setVisible(true);
                loanTypeLabel.setVisible(true);
                itemLoanTypeComboBox.setVisible(true);
                noOfCopiesLabel.setVisible(true);
                noOfCopiesField.setVisible(true);
                rentalFeeLabel.setVisible(true);
                rentalFeeField.setVisible(true);
                rentaStatusLabel.setVisible(true);
                itemrentalStatusComboBox.setVisible(true);
                genreLabel.setVisible(true);
                genreComboBox.setVisible(true);
                updateButton.setVisible(true);

                // Display current customer info
                Item item = vsm.findItemByID(ID);
                if (item != null) {
                    infoText.setFill(Color.BLACK);
                    idField.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2;");
                    infoText.setText(item.itemInfo());
                } else {
                    infoText.setFill(Color.RED);
                    idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                    infoText.setText("No item found!");
                }
            } else {
                // Hide all labels and fields if ID is invalid
                infoText.setVisible(false);
                newIDLabel.setVisible(false);
                newIDField.setVisible(false);
                titleLabel.setVisible(false);
                titleField.setVisible(false);
                rentalTypeLabel.setVisible(false);
                itemRentalTypeComboBox.setVisible(false);
                loanTypeLabel.setVisible(false);
                itemLoanTypeComboBox.setVisible(false);
                noOfCopiesLabel.setVisible(false);
                noOfCopiesField.setVisible(false);
                rentalFeeLabel.setVisible(false);
                rentalFeeField.setVisible(false);
                rentaStatusLabel.setVisible(false);
                itemrentalStatusComboBox.setVisible(false);
                genreLabel.setVisible(false);
                genreComboBox.setVisible(false);
                updateButton.setVisible(false);

                infoText.setFill(Color.RED);
                idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                infoText.setText("Invalid item ID!");
                infoText.setVisible(true);
            }
        });

        // Set action for updateButton to call updateCustomer method
        updateButton.setOnAction(event -> vsm.updateItem(idField.getText(), infoText, newIDField.getText(), titleField.getText(), itemRentalTypeComboBox.getValue(), itemLoanTypeComboBox.getValue(), noOfCopiesField.getText(), rentalFeeField.getText(), itemrentalStatusComboBox.getValue(), genreComboBox.getValue()));

        // Create a GridPane to hold text fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(newIDLabel, 0, 0);
        grid.add(newIDField, 1, 0);
        grid.add(titleLabel, 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(rentalTypeLabel, 0, 2);
        grid.add(itemRentalTypeComboBox, 1, 2);
        grid.add(loanTypeLabel, 0, 3);
        grid.add(itemLoanTypeComboBox, 1, 3);
        grid.add(noOfCopiesLabel, 0, 4);
        grid.add(noOfCopiesField, 1, 4);
        grid.add(rentalFeeLabel, 0, 5);
        grid.add(rentalFeeField, 1, 5);
        grid.add(rentaStatusLabel, 0, 6);
        grid.add(itemrentalStatusComboBox, 1, 6);
        grid.add(genreLabel, 0, 7);
        grid.add(genreComboBox, 1, 7);

        // Create info box to store infoText and grid
        VBox infoBox = new VBox(15, infoText, grid);
        infoBox.setAlignment(Pos.CENTER);

        // Create a VBox to store everything
        VBox screen = new VBox();
        screen.setAlignment(Pos.TOP_CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(adminPageButtonBox(primaryStage), idBox, infoBox, updateBox);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(screen, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Update Item");
        primaryStage.show();
    }

    private void increaseItemStage(Stage primaryStage) {
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
                idField.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2;");
                // Display current customer info
                Item item = vsm.findItemByID(ID);
                if (item != null) {
                    infoText.setFill(Color.BLACK);
                    infoText.setText(item.itemInfo());
                } else {
                    infoText.setFill(Color.RED);
                    idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                    infoText.setText("No item found!");
                }
            } else {
                // Hide all labels and fields if ID is invalid
                infoText.setVisible(false);
                numberLabel.setVisible(false);
                numberField.setVisible(false);
                increaseButton.setVisible(false);

                infoText.setFill(Color.RED);
                idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                infoText.setText("Invalid item ID!");
                infoText.setVisible(true);
            }
        });

        // Set action for increase button to call increaseNoOfCopies method
        increaseButton.setOnAction(actionEvent -> vsm.increaseNoOfCopies(idField.getText(), infoText, numberField.getText()));

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
        Scene scene = new Scene(screen, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Increase Number of Items");
        primaryStage.show();
    }

    private void deleteItemStage(Stage primaryStage) {
        Label idLabel = new Label("Enter item ID:");
        TextField idField = new TextField();

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: red; -fx-border-color: red; " + "-fx-border-width: 2;"));
        deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: transparent; " + "-fx-border" + "-color: red; " + "-fx-border-width: 2;"));

        Button checkButton = new Button("Check");
        buttonDesignForStage(checkButton);

        HBox idBox = new HBox(10, idLabel, idField, checkButton, deleteButton);
        idBox.setAlignment(Pos.CENTER);

        // Create info text box to display item information or error messages
        Text infoText = new Text();
        infoText.setVisible(false);
        deleteButton.setVisible(false);

        checkButton.setOnAction(event -> {
            infoText.setText("");
            String ID = idField.getText();
            if (vsm.isValidItemID(ID)) { // If the ID is valid
                // Show all fields below the ID field

                // Display current customer info
                Item item = vsm.findItemByID(ID);
                if (item != null) {
                    deleteButton.setVisible(true);

                    infoText.setVisible(true);
                    infoText.setFill(Color.BLACK);
                    idField.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2;");
                    infoText.setText(item.itemInfo());
                } else {
                    infoText.setFill(Color.RED);
                    idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                    infoText.setText("No item found!");
                }
            } else {
                // Hide all labels and fields if ID is invalid
                infoText.setVisible(false);

                deleteButton.setVisible(false);
                infoText.setFill(Color.RED);
                idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                infoText.setText("Invalid item ID!");
                infoText.setVisible(true);
            }
        });

        deleteButton.setOnAction(event -> {
            // Show warning window
            Stage warningStage = new Stage();
            warningStage.initModality(Modality.APPLICATION_MODAL);

            Label warningLabel = new Label("Are you sure you want to delete this item?");

            Button yesButton = new Button("Yes");
            yesButton.setStyle("-fx-background-color: transparent; -fx-border-color: red; -fx-border-width: 2;");
            yesButton.setOnMouseEntered(e -> yesButton.setStyle("-fx-background-color: red; -fx-border-color: red; " + "-fx-border-width: 2;"));
            yesButton.setOnMouseExited(e -> yesButton.setStyle("-fx-background-color: transparent; -fx-border-color: " + "red; " + "-fx-border-width: 2;"));

            Button noButton = new Button("No");
            buttonDesignForStage(noButton);

            HBox buttonBox = new HBox(10, yesButton, noButton);
            buttonBox.setAlignment(Pos.CENTER);

            VBox warningBox = new VBox(10, warningLabel, buttonBox);
            warningBox.setAlignment(Pos.CENTER);

            Scene warningScene = new Scene(warningBox, 300, 100);
            warningStage.setScene(warningScene);
            warningStage.show();

            yesButton.setOnAction(e -> {
                String ID = idField.getText();
                vsm.deleteItem(ID, infoText); // Call deleteItem method with entered ID
                infoText.setFill(Color.GREEN);
                infoText.setText("Item with ID " + ID + " has been deleted.");
                infoText.setVisible(true);
                warningStage.close();
            });

            noButton.setOnAction(e -> warningStage.close());
        });

        // Create a VBox to store everything
        VBox screen = new VBox();
        screen.setAlignment(Pos.TOP_CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(adminPageButtonBox(primaryStage), idBox, infoText, deleteButton);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(screen, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Delete Item");
        primaryStage.show();
    }

    private void displayItemStage(Stage primaryStage) {
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
            switch (selectedOption) {
                case "Sort by ID" -> {
                    sortTable.getChildren().clear();
                    vsm.displayIDSortItem(gridPane);
                }
                case "Sort by Title" -> {
                    sortTable.getChildren().clear();
                    vsm.displayTitleSortItem(gridPane);
                }
                case "With no Copies" -> {
                    sortTable.getChildren().clear();
                    vsm.displayItemNoCopies(gridPane);
                }
            }
            sortTable.getChildren().addAll(buttonBox, gridPane);
        });

        // Vertical box to store everything
        VBox vBox = new VBox(10, adminPageButtonBox(primaryStage), buttonBox, sortTable);
        vBox.setAlignment(Pos.TOP_CENTER);

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Display Item");
        primaryStage.show();
    }

    private void searchItemStage(Stage primaryStage) {
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
                searchTable.getChildren().clear();
                vsm.searchItemID(itemTable, newValue);
            } else if (selectedOption.equals("Search by Title")) {
                searchTable.getChildren().clear();
                vsm.searchItemTitle(itemTable, newValue);
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

    private void addCustomerStage(Stage primaryStage) {
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
                    idField.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2;");
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
                    idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
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
                idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                infoText.setText("Invalid customer ID format! Must follow format \"Cxxx\"");
            }
        });

        // Set action for add button
        addButton.setOnAction(actionEvent -> vsm.addCustomer(idField.getText(), infoText, nameField.getText(), addressField.getText(), phoneField.getText(), usernameField.getText(), passwordField.getText()));

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
        Scene scene = new Scene(screen, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Customer");
        primaryStage.show();
    }

    private void updateCustomerStage(Stage primaryStage) {
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
            // If the ID is valid
            if (vsm.isValidCustomerID(ID)) {
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
                    idField.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2;");
                    infoText.setFill(Color.BLACK);
                    infoText.setText(customer.customerInfo());
                } else {
                    infoText.setFill(Color.RED);
                    idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
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
                idField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                infoText.setText("Invalid customer ID!");
                infoText.setVisible(true);
            }
        });

        // Set action for updateButton to call updateCustomer method
        updateButton.setOnAction(event -> vsm.updateCustomer(idField.getText(), infoText, nameField.getText(), addressField.getText(), phoneField.getText(), noOfRentalField.getText(), customerTypeComboBox.getValue(), usernameField.getText(), passwordField.getText(), rewardPointField.getText()));

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
        VBox infoBox = new VBox(15, infoText, grid);
        infoBox.setAlignment(Pos.CENTER);

        // Create a VBox to store everything
        VBox screen = new VBox();
        screen.setAlignment(Pos.TOP_CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(adminPageButtonBox(primaryStage), idBox, infoBox, updateBox);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(screen, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Update Customer");
        primaryStage.show();
    }

    private void displayCustomerStage(Stage primaryStage) {
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
            switch (selectedOption) {
                case "Sort by ID" -> {
                    sortTable.getChildren().clear();
                    vsm.displayIDSortCustomer(gridPane);
                }
                case "Sort by Name" -> {
                    sortTable.getChildren().clear();
                    vsm.displayNameSortCustomer(gridPane);
                }
                case "Sort by Group" -> {
                    sortTable.getChildren().clear();
                    vsm.displayGroup(gridPane);
                }
            }
            sortTable.getChildren().addAll(buttonBox, gridPane);
        });

        // Vertical box to store everything
        VBox vBox = new VBox(10, adminPageButtonBox(primaryStage), buttonBox, sortTable);
        vBox.setAlignment(Pos.TOP_CENTER);

        // Set up the scene and stage
        Scene scene = new Scene(vBox, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Display Customer");
        primaryStage.show();
    }

    private void searchCustomerStage(Stage primaryStage) {
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
                searchTable.getChildren().clear();
                vsm.searchCustomerID(customerTable, newValue);
            } else if (selectedOption.equals("Search by Name")) {
                searchTable.getChildren().clear();
                vsm.searchCustomerName(customerTable, newValue);
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
