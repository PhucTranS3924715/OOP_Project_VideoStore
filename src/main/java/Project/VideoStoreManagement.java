package Project;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;

public class VideoStoreManagement {
    private ArrayList<Customer> customers;
    private ArrayList<Item> items;
    private Customer currentUser;
    private final String adminUsername;
    private final String adminPassword;

    // Constructor
    public VideoStoreManagement() {
        this.customers = new ArrayList<Customer>();
        this.items = new ArrayList<Item>();
        this.currentUser = new Customer();
        this.adminUsername = "admin";
        this.adminPassword = "password";
    }
    //Allow VideoStoreApp to access the instance
    public Customer getCurrentUser() {
        return currentUser;
    }


    public boolean login(String username, String password) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                currentUser = customer;
                return true;
            }
        }
        return false;
    }

    public boolean adminLogin(String username, String password) {
        return Objects.equals(username, adminUsername) && Objects.equals(password, adminPassword);
    }

    public Customer findCustomerByID(String ID) {
        for (Customer customer : customers) {
            if (customer.getID().equals(ID)) return customer;
        }
        return null;
    }

    public Item findItemByID(String ID) {
        for (Item item : items) {
            if (item.getID().equals(ID)) return item;
        }
        return null;
    }

    // Method to check valid item ID
    public boolean isValidItemID(String id) {
        if (!id.matches("I\\d{3}-\\d{4}")) {
            return false;
        }
        int year = Integer.parseInt(id.substring(id.length() - 4));
        return year >= 1900 && year <= 2023;
    }

    // Method to check valid customer ID
    public boolean isValidCustomerID(String id) {
        return id.matches("C\\d{3}");
    }

    public boolean addItem() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter item ID: ");
        String ID = scan.nextLine();
        Item item = findItemByID(ID);
        if (item == null) {
            System.out.print("Enter item title: ");
            String title = scan.nextLine();
            System.out.print("Enter rental type (DVD/Blu-ray/Game): ");
            String rentalType = scan.nextLine();
            System.out.print("Enter loan type (2-day/1-week): ");
            String loanType = scan.nextLine();
            System.out.print("Enter number of copies: ");
            int noOfCopies = scan.nextInt();
            System.out.print("Enter rental fee:");
            float rentalFee = scan.nextFloat();
            scan.nextLine();
            if (Objects.equals(rentalType, "Record") || Objects.equals(rentalType, "DVD")) {
                System.out.print("Enter genre(Action/Horror/Drama/Comedy:");
                String genre = scan.nextLine();
                items.add(new Item(ID, title, rentalType, loanType, noOfCopies, rentalFee, "available", genre));
            } else {
                items.add(new Item(ID, title, rentalType, loanType, noOfCopies, rentalFee, "available", ""));
            }
            System.out.println("Item added!");
            return true;
        }
        System.out.println("The item already existed!");
        return false;
    }

    public boolean updateItem() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter item ID: ");
        String ID = scan.nextLine();
        Item item = findItemByID(ID);
        if (item != null) {
            System.out.print("Enter new ID: ");
            String newID = scan.nextLine();
            System.out.print("Enter new item title: ");
            String title = scan.nextLine();
            System.out.print("Enter new rental type (DVD/Blu-ray/Game): ");
            String rentalType = scan.nextLine();
            System.out.print("Enter new loan type (2-day/1-week): ");
            String loanType = scan.nextLine();
            System.out.print("Enter new number of copies: ");
            int noOfCopies = scan.nextInt();
            System.out.print("Enter new rental fee:");
            float rentalFee = scan.nextFloat();
            System.out.print("Enter new rental status (available/rented): ");
            String rentalStatus = scan.nextLine();
            System.out.print("Enter new genre(Action/Horror/Drama/Comedy:");
            String genre = scan.nextLine();
            item.setID(newID);
            item.setTitle(title);
            item.setRentalType(rentalType);
            item.setLoanType(loanType);
            item.setNoOfCopy(noOfCopies);
            item.setRentalFee(rentalFee);
            item.setRentalStatus(rentalStatus);
            item.setGenre(genre);
            System.out.println("Item updated!");
            return true;
        }
        System.out.println("No item found!");
        return false;
    }

    public boolean deleteItem() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter item ID you want to delete: ");
        String ID = scan.nextLine();
        Item item = findItemByID(ID);
        if (item != null) {
            items.remove(item);
            System.out.println("Item deleted");
            return true;
        }
        System.out.println("No item found");
        return false;
    }

    public boolean addCustomer() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter customer ID: ");
        String ID = scan.nextLine();
        Customer customer = findCustomerByID(ID);
        if (customer == null) {
            System.out.print("Enter name: ");
            String name = scan.nextLine();
            System.out.print("Enter address: ");
            String address = scan.nextLine();
            System.out.print("Enter phone number: ");
            String phone = scan.nextLine();
            System.out.print("Enter username: ");
            String username = scan.nextLine();
            System.out.print("Enter password: ");
            String password = scan.nextLine();
            customers.add(new Customer(ID, name, address, phone, 0, "guest", username, password, 0));
            System.out.println("Customer added!");
            return true;
        }
        System.out.println("The customer ID already existed!");
        return false;
    }

    public void updateCustomer(String idField, Text infoText, String nameField, String addressField,
                               String phoneField, String noOfRentalField, String customerTypeField,
                               String usernameField, String passwordField, String rewardPointField) {
        Customer customer = findCustomerByID(idField);
        if (customer != null) {
            // Display current info
            infoText.setText(customer.customerInfo());

            // Update customer info for non-empty fields
            if (!nameField.isEmpty()) {
                customer.setName(nameField);
            }
            if (!addressField.isEmpty()) {
                customer.setAddress(addressField);
            }
            if (!phoneField.isEmpty()) {
                customer.setPhone(phoneField);
            }
            if (!noOfRentalField.isEmpty()) {
                customer.setNoOfRental(Integer.parseInt(noOfRentalField));
            }
            if (!customerTypeField.isEmpty()) {
                customer.setCustomerType(customerTypeField);
            }
            if (!usernameField.isEmpty()) {
                customer.setUsername(usernameField);
            }
            if (!passwordField.isEmpty()) {
                customer.setPassword(passwordField);
            }
            if (!rewardPointField.isEmpty()) {
                customer.setRewardPoints(Integer.parseInt(rewardPointField));
            }

            infoText.setText("Customer updated!");
            infoText.setFill(Color.GREEN);
            return;
        }
        infoText.setText("No customer found!");
    }

    public void increaseNoOfCopies(String idField, Text infoText, String numberField) {
        // Get the item using ID
        Item item = findItemByID(idField);
        if (item != null) {
            item.setNoOfCopy(item.getNoOfCopy() + Integer.parseInt(numberField));
            infoText.setText("Number of items updated!");
            infoText.setFill(Color.GREEN);
            return;
        }
        infoText.setText("No item found!");
    }

    public boolean loadData() {
        try {
            // Load items
            File itemFile = new File("src/main/java/Project/items.txt");
            Scanner itemScanner = new Scanner(itemFile);
            while (itemScanner.hasNextLine()) {
                String itemData = itemScanner.nextLine();
                String[] itemParts = itemData.split(";");
                for (String itemPart : itemParts) {
                    String[] itemFields = itemPart.split(",");
                    Item item = new Item(itemFields[0], itemFields[1], itemFields[2], itemFields[3],
                            Integer.parseInt(itemFields[4]), Float.parseFloat(itemFields[5]), itemFields[6]);
                    if (itemFields.length > 7) {
                        item.setGenre(itemFields[7]);
                    }
                    items.add(item);
                }
            }
            itemScanner.close();

            // Load customers
            File customerFile = new File("src/main/java/Project/customers.txt");
            Scanner customerScanner = new Scanner(customerFile);
            String data = "";
            while (customerScanner.hasNextLine()) {
                data += customerScanner.nextLine();
            }
            // Split each customer data
            String[] parts = data.split(";");
            for (String part : parts) {
                if (part.trim().isEmpty()) {
                    continue;
                }
                // Split customer info and item list
                String[] fields = part.split("/");
                String[] customerFields = fields[0].split(",");
                Customer customer = new Customer(customerFields[0], customerFields[1], customerFields[2],
                        customerFields[3], Integer.parseInt(customerFields[4]), customerFields[5], customerFields[6],
                        customerFields[7], Integer.parseInt(customerFields[8]));
                if (fields.length > 1) {
                    String[] itemIDs = fields[1].split(",");
                    for (String itemID : itemIDs) {
                        Item item = findItemByID(itemID);
                        if (item != null) {
                            customer.addItem(item);
                        }
                    }
                }
                customers.add(customer);
            }
            customerScanner.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveData() {
        try {
            // Save customers
            FileWriter customerWriter = new FileWriter("src/main/java/Project/customers.txt");
            for (Customer customer : customers) {
                String customerString = customer.getID() + "," + customer.getName() + "," + customer.getAddress() +
                        "," + customer.getPhone() + "," + customer.getNoOfRental() + "," + customer.getCustomerType() + "," + customer.getUsername() + "," + customer.getPassword() + "," + customer.getRewardPoints();
                if (!customer.getItems().isEmpty()) {
                    customerString += "/";
                    for (Item item : customer.getItems()) {
                        customerString += item.getID() + ",";
                    }
                    customerString = customerString.substring(0, customerString.length() - 1);
                }
                customerString += ";\n";
                customerWriter.write(customerString);
            }
            customerWriter.close();

            // Save items
            FileWriter itemWriter = new FileWriter("src/main/java/Project/items.txt");
            for (Item item : items) {
                String itemString =
                        item.getID() + "," + item.getTitle() + "," + item.getRentalType() + "," + item.getLoanType() + "," + item.getNoOfCopy() + "," + item.getRentalFee() + "," + item.getRentalStatus();
                if (item.getGenre() != null) {
                    itemString += "," + item.getGenre();
                }
                itemString += ";\n";
                itemWriter.write(itemString);
            }
            itemWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Rent an item (decrease the number of copies held in stock)
     Return true if the item is successfully rented, false otherwise*/
    public boolean rentItem(String itemID, Label messageLabel) {
        Item item = findItemByID(itemID);

        if (item == null) {
            messageLabel.setText("No item found!");
            return false;
        }

        // Guest user cannot rent 2-day items
        if (currentUser.getCustomerType().equals("Guest") && item.getLoanType().equals("2-day")) {
            messageLabel.setText("Error: Guest customers cannot borrow 2-day items.");
            return false;
        }
        // Cannot rent unavailable items
        else if (item.getRentalStatus().equals("not available")) {
            messageLabel.setText("Error: This item is not available for rent.");
            return false;
        }
        // Cannot rent borrowed items
        else if (item.getRentalStatus().equals("borrowed")) {
            messageLabel.setText("Error: This item is already borrowed.");
            return false;
        }
        // After all invalid cases, customer can rent item
        else {
            // Guest cannot rent more than 2 items at a time
            if (currentUser.getCustomerType().equals("Guest") && currentUser.getItems().size() >= 2) {
                messageLabel.setText("Error: Guest customers can only rent a maximum of 2 items.");
                return false;
            } else {
                currentUser.getItems().add(item);
                item.setNoOfCopy(item.getNoOfCopy() - 1);
                // If it is the last item, change the status to "borrowed"
                if (item.getNoOfCopy() == 0) {
                    item.setRentalStatus("borrowed");
                }
                // If the customer is a VIP, increase reward points
                if (Objects.equals(currentUser.getCustomerType(), "VIP")){
                    currentUser.setRewardPoints(currentUser.getRewardPoints() + 10);
                }
                messageLabel.setText("Item rented successfully.");
                return true;
            }
        }
    }

    //Implement available item
    public ArrayList<Item> getAvailableItems() {
        ArrayList<Item> availableItems = new ArrayList<Item>();
        for (Item item : items) {
            if (item.getRentalStatus().equals("available")) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }

    /* Return an item (increase the number of copies held in stock)
     Return true if the item is successfully returned, false otherwise*/
    public boolean returnItem(String itemID) {
        // Find the item in the customer item list
        for (Item currentItem : currentUser.getItems()) {
            if (Objects.equals(currentItem.getID(), itemID)) { // If the item exists in the customer list
                // Remove the item from the customer list
                currentUser.getItems().remove(currentItem);
                // Update number of rental whenever a customer returns an item
                currentUser.setNoOfRental(currentUser.getNoOfRental() + 1);

                // Return the item and update the rental status and number of copies
                Item item = findItemByID(currentItem.getID());
                item.setRentalStatus("available");
                item.setNoOfCopy(item.getNoOfCopy() + 1);

                // Check if the customer is eligible for promotion
                if (currentUser.getCustomerType().equals("Guest") && currentUser.getNoOfRental() > 3) {
                    currentUser.setCustomerType("Regular");
                    currentUser.setNoOfRental(0);
                    System.out.println("Congratulations! You have been promoted to a Regular customer.");
                } else if (currentUser.getCustomerType().equals("Regular") && currentUser.getNoOfRental() > 5) {
                    currentUser.setCustomerType("VIP");
                    currentUser.setNoOfRental(0);
                    System.out.println("Congratulations! You have been promoted to a VIP customer.");
                }
                System.out.println("Return successful!");
                return true;
            }
        }
        System.out.println("You did not rent this item.");
        return false;
    }

    // Promote the customer to the next customer type if they meet the criteria
    public boolean promote() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter customer ID for promotion: ");
        String ID = scan.nextLine();
        Customer customer = findCustomerByID(ID);

        if (customer == null) {
            System.out.println("No customer found!");
            return false;
        }

        if (customer.getCustomerType().equals("Guest") && customer.getNoOfRental() > 3) {
            customer.setCustomerType("Regular");
            customer.setNoOfRental(0);
        } else if (customer.getCustomerType().equals("Regular") && customer.getNoOfRental() > 5) {
            customer.setCustomerType("VIP");
            customer.setNoOfRental(0);
        } else {
            System.out.println("Customer cannot be promoted.");
            return false;
        }

        System.out.println("Customer promoted successfully.");
        return true;
    }

    public void displayIDSortItem(GridPane gridPane) {
        Collections.sort(items, new IDSortItem());

        gridPane.getChildren().clear();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(5);

        Text[][] text = new Text[8][items.size() + 1];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < items.size() + 1; j++) {
                text[i][j] = new Text(); // Initialize each element of the array
            }
        }

        text[0][0].setText("  ID  ");
        text[1][0].setText("  Title  ");
        text[2][0].setText("  Rental Type  ");
        text[3][0].setText("  Loan Type  ");
        text[4][0].setText("  Number of Copies  ");
        text[5][0].setText("  Rental Fee  ");
        text[6][0].setText("  Rental Status  ");
        text[7][0].setText("  Genre  ");

        for (int i = 0; i < 8; i++) {
            gridPane.add(text[i][0], i, 0);
        }

        for (int i = 1; i < items.size() + 1; i++) {
            for (int j = 0; j < 8; j++) {
                switch (j) {
                    case 0:
                        text[0][i].setText(items.get(i - 1).getID());
                        gridPane.add(text[0][i], 0, i);
                        break;
                    case 1:
                        text[1][i].setText(items.get(i - 1).getTitle());
                        gridPane.add(text[1][i], 1, i);
                        break;
                    case 2:
                        text[2][i].setText(items.get(i - 1).getRentalType());
                        gridPane.add(text[2][i], 2, i);
                        break;
                    case 3:
                        text[3][i].setText(items.get(i - 1).getLoanType());
                        gridPane.add(text[3][i], 3, i);
                        break;
                    case 4:
                        text[4][i].setText(String.valueOf(items.get(i - 1).getNoOfCopy()));
                        gridPane.add(text[4][i], 4, i);
                        GridPane.setHalignment(text[4][i], HPos.CENTER);
                        break;
                    case 5:
                        text[5][i].setText(String.valueOf(items.get(i - 1).getRentalFee()));
                        GridPane.setHalignment(text[5][i], HPos.CENTER);
                        gridPane.add(text[5][i], 5, i);
                        break;
                    case 6:
                        text[6][i].setText(items.get(i - 1).getRentalStatus());
                        gridPane.add(text[6][i], 6, i);
                        break;
                    case 7:
                        text[7][i].setText(items.get(i - 1).getGenre());
                        gridPane.add(text[7][i], 7, i);
                        break;
                }
            }
        }

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text || node instanceof Label) {
                GridPane.setHalignment(node, HPos.CENTER);
            }
        }

        // Create a scroll pane and add the grid to it
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
    }

    public void displayTitleSortItem(GridPane gridPane) {
        Collections.sort(items, new TitleSortItem());

        gridPane.getChildren().clear();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(5);

        Text[][] text = new Text[8][items.size() + 1];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < items.size() + 1; j++) {
                text[i][j] = new Text(); // Initialize each element of the array
            }
        }

        text[0][0].setText("  ID  ");
        text[1][0].setText("  Title  ");
        text[2][0].setText("  Rental Type  ");
        text[3][0].setText("  Loan Type  ");
        text[4][0].setText("  Number of Copies  ");
        text[5][0].setText("  Rental Fee  ");
        text[6][0].setText("  Rental Status  ");
        text[7][0].setText("  Genre  ");

        for (int i = 0; i < 8; i++) {
            gridPane.add(text[i][0], i, 0);
        }

        for (int i = 1; i < items.size() + 1; i++) {
            for (int j = 0; j < 8; j++) {
                switch (j) {
                    case 0:
                        text[0][i].setText(items.get(i - 1).getID());
                        gridPane.add(text[0][i], 0, i);
                        break;
                    case 1:
                        text[1][i].setText(items.get(i - 1).getTitle());
                        gridPane.add(text[1][i], 1, i);
                        break;
                    case 2:
                        text[2][i].setText(items.get(i - 1).getRentalType());
                        gridPane.add(text[2][i], 2, i);
                        break;
                    case 3:
                        text[3][i].setText(items.get(i - 1).getLoanType());
                        gridPane.add(text[3][i], 3, i);
                        break;
                    case 4:
                        text[4][i].setText(String.valueOf(items.get(i - 1).getNoOfCopy()));
                        gridPane.add(text[4][i], 4, i);
                        GridPane.setHalignment(text[4][i], HPos.CENTER);
                        break;
                    case 5:
                        text[5][i].setText(String.valueOf(items.get(i - 1).getRentalFee()));
                        GridPane.setHalignment(text[5][i], HPos.CENTER);
                        gridPane.add(text[5][i], 5, i);
                        break;
                    case 6:
                        text[6][i].setText(items.get(i - 1).getRentalStatus());
                        gridPane.add(text[6][i], 6, i);
                        break;
                    case 7:
                        text[7][i].setText(items.get(i - 1).getGenre());
                        gridPane.add(text[7][i], 7, i);
                        break;
                }
            }
        }

        // Set every text in the grid to be center
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text || node instanceof Label) {
                GridPane.setHalignment(node, HPos.CENTER);
            }
        }

        // Create a scroll pane and add the grid to it
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
    }

    public void displayIDSortCustomer(GridPane gridPane) {
        Collections.sort(customers, new IDSortCustomer());

        gridPane.getChildren().clear();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(5);

        Text[][] text = new Text[9][customers.size() + 1];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < customers.size() + 1; j++) {
                text[i][j] = new Text(); // Initialize each element of the array
            }
        }

        text[0][0].setText("  ID  ");
        text[1][0].setText("  Name  ");
        text[2][0].setText("  Address  ");
        text[3][0].setText("  Phone  ");
        text[4][0].setText("  Number of Rental  ");
        text[5][0].setText("  Customer Type  ");
        text[6][0].setText("  Username  ");
        text[7][0].setText("  Password  ");
        text[8][0].setText("  Reward Points  ");

        for (int i = 0; i < 9; i++) {
            gridPane.add(text[i][0], i, 0);
        }

        for (int i = 1; i < customers.size() + 1; i++) {
            for (int j = 0; j < 9; j++) {
                switch (j) {
                    case 0:
                        text[0][i].setText(customers.get(i - 1).getID());
                        GridPane.setHalignment(text[0][i], HPos.CENTER);
                        gridPane.add(text[0][i], 0, i);
                        break;
                    case 1:
                        text[1][i].setText(customers.get(i - 1).getName());
                        gridPane.add(text[1][i], 1, i);
                        break;
                    case 2:
                        text[2][i].setText(customers.get(i - 1).getAddress());
                        gridPane.add(text[2][i], 2, i);
                        break;
                    case 3:
                        text[3][i].setText(customers.get(i - 1).getPhone());
                        GridPane.setHalignment(text[3][i], HPos.CENTER);
                        gridPane.add(text[3][i], 3, i);
                        break;
                    case 4:
                        text[4][i].setText(String.valueOf(customers.get(i - 1).getNoOfRental()));
                        GridPane.setHalignment(text[4][i], HPos.CENTER);
                        gridPane.add(text[4][i], 4, i);
                        break;
                    case 5:
                        text[5][i].setText(customers.get(i - 1).getCustomerType());
                        GridPane.setHalignment(text[5][i], HPos.CENTER);
                        gridPane.add(text[5][i], 5, i);
                        break;
                    case 6:
                        text[6][i].setText(customers.get(i - 1).getUsername());
                        gridPane.add(text[6][i], 6, i);
                        break;
                    case 7:
                        text[7][i].setText(customers.get(i - 1).getPassword());
                        gridPane.add(text[7][i], 7, i);
                        break;
                    case 8:
                        text[8][i].setText(String.valueOf(customers.get(i - 1).getRewardPoints()));
                        GridPane.setHalignment(text[8][i], HPos.CENTER);
                        gridPane.add(text[8][i], 8, i);
                        break;
                }
            }
        }

        // Set every text in the grid to be center
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text || node instanceof Label) {
                GridPane.setHalignment(node, HPos.CENTER);
            }
        }

        // Create a scroll pane and add the grid to it
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
    }

    public void displayNameSortCustomer(GridPane gridPane) {
        Collections.sort(customers, new NameSortCustomer());

        gridPane.getChildren().clear();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(5);

        Text[][] text = new Text[9][customers.size() + 1];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < customers.size() + 1; j++) {
                text[i][j] = new Text(); // Initialize each element of the array
            }
        }

        text[0][0].setText("  ID  ");
        text[1][0].setText("  Name  ");
        text[2][0].setText("  Address  ");
        text[3][0].setText("  Phone  ");
        text[4][0].setText("  Number of Rental  ");
        text[5][0].setText("  Customer Type  ");
        text[6][0].setText("  Username  ");
        text[7][0].setText("  Password  ");
        text[8][0].setText("  Reward Points  ");

        for (int i = 0; i < 9; i++) {
            gridPane.add(text[i][0], i, 0);
        }

        for (int i = 1; i < customers.size() + 1; i++) {
            for (int j = 0; j < 9; j++) {
                switch (j) {
                    case 0:
                        text[0][i].setText(customers.get(i - 1).getID());
                        GridPane.setHalignment(text[0][i], HPos.CENTER);
                        gridPane.add(text[0][i], 0, i);
                        break;
                    case 1:
                        text[1][i].setText(customers.get(i - 1).getName());
                        gridPane.add(text[1][i], 1, i);
                        break;
                    case 2:
                        text[2][i].setText(customers.get(i - 1).getAddress());
                        gridPane.add(text[2][i], 2, i);
                        break;
                    case 3:
                        text[3][i].setText(customers.get(i - 1).getPhone());
                        GridPane.setHalignment(text[3][i], HPos.CENTER);
                        gridPane.add(text[3][i], 3, i);
                        break;
                    case 4:
                        text[4][i].setText(String.valueOf(customers.get(i - 1).getNoOfRental()));
                        GridPane.setHalignment(text[4][i], HPos.CENTER);
                        gridPane.add(text[4][i], 4, i);
                        break;
                    case 5:
                        text[5][i].setText(customers.get(i - 1).getCustomerType());
                        GridPane.setHalignment(text[5][i], HPos.CENTER);
                        gridPane.add(text[5][i], 5, i);
                        break;
                    case 6:
                        text[6][i].setText(customers.get(i - 1).getUsername());
                        gridPane.add(text[6][i], 6, i);
                        break;
                    case 7:
                        text[7][i].setText(customers.get(i - 1).getPassword());
                        gridPane.add(text[7][i], 7, i);
                        break;
                    case 8:
                        text[8][i].setText(String.valueOf(customers.get(i - 1).getRewardPoints()));
                        GridPane.setHalignment(text[8][i], HPos.CENTER);
                        gridPane.add(text[8][i], 8, i);
                        break;
                }
            }
        }

        // Set every text in the grid to be center
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text || node instanceof Label) {
                GridPane.setHalignment(node, HPos.CENTER);
            }
        }

        // Create a scroll pane and add the grid to it
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
    }

    public void displayGroup(GridPane gridPane) {
        Collections.sort(customers, new customerTypeSort());

        gridPane.getChildren().clear();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(5);

        Text[][] text = new Text[9][customers.size() + 1];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < customers.size() + 1; j++) {
                text[i][j] = new Text(); // Initialize each element of the array
            }
        }

        text[0][0].setText("  ID  ");
        text[1][0].setText("  Name  ");
        text[2][0].setText("  Address  ");
        text[3][0].setText("  Phone  ");
        text[4][0].setText("  Number of Rental  ");
        text[5][0].setText("  Customer Type  ");
        text[6][0].setText("  Username  ");
        text[7][0].setText("  Password  ");
        text[8][0].setText("  Reward Points  ");

        for (int i = 0; i < 9; i++) {
            gridPane.add(text[i][0], i, 0);
        }

        for (int i = 1; i < customers.size() + 1; i++) {
            for (int j = 0; j < 9; j++) {
                switch (j) {
                    case 0:
                        text[0][i].setText(customers.get(i - 1).getID());
                        GridPane.setHalignment(text[0][i], HPos.CENTER);
                        gridPane.add(text[0][i], 0, i);
                        break;
                    case 1:
                        text[1][i].setText(customers.get(i - 1).getName());
                        gridPane.add(text[1][i], 1, i);
                        break;
                    case 2:
                        text[2][i].setText(customers.get(i - 1).getAddress());
                        gridPane.add(text[2][i], 2, i);
                        break;
                    case 3:
                        text[3][i].setText(customers.get(i - 1).getPhone());
                        GridPane.setHalignment(text[3][i], HPos.CENTER);
                        gridPane.add(text[3][i], 3, i);
                        break;
                    case 4:
                        text[4][i].setText(String.valueOf(customers.get(i - 1).getNoOfRental()));
                        GridPane.setHalignment(text[4][i], HPos.CENTER);
                        gridPane.add(text[4][i], 4, i);
                        break;
                    case 5:
                        text[5][i].setText(customers.get(i - 1).getCustomerType());
                        GridPane.setHalignment(text[5][i], HPos.CENTER);
                        gridPane.add(text[5][i], 5, i);
                        break;
                    case 6:
                        text[6][i].setText(customers.get(i - 1).getUsername());
                        gridPane.add(text[6][i], 6, i);
                        break;
                    case 7:
                        text[7][i].setText(customers.get(i - 1).getPassword());
                        gridPane.add(text[7][i], 7, i);
                        break;
                    case 8:
                        text[8][i].setText(String.valueOf(customers.get(i - 1).getRewardPoints()));
                        GridPane.setHalignment(text[8][i], HPos.CENTER);
                        gridPane.add(text[8][i], 8, i);
                        break;
                }
            }
        }

        // Set every text in the grid to be center
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text || node instanceof Label) {
                GridPane.setHalignment(node, HPos.CENTER);
            }
        }

        // Create a scroll pane and add the grid to it
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
    }

    public void displayItemNoCopies(GridPane gridPane) {
        gridPane.getChildren().clear();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(5);

        Text[][] text = new Text[8][items.size() + 1];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < items.size() + 1; j++) {
                text[i][j] = new Text(); // Initialize each element of the array
            }
        }

        text[0][0].setText("  ID  ");
        text[1][0].setText("  Title  ");
        text[2][0].setText("  Rental Type  ");
        text[3][0].setText("  Loan Type  ");
        text[4][0].setText("  Number of Copies  ");
        text[5][0].setText("  Rental Fee  ");
        text[6][0].setText("  Rental Status  ");
        text[7][0].setText("  Genre  ");

        for (int i = 0; i < 8; i++) {
            gridPane.add(text[i][0], i, 0);
        }

        for (int i = 1; i < items.size() + 1; i++) {
            for (int j = 0; j < 8; j++) {
                if (items.get(i - 1).getNoOfCopy() == 0) {
                    switch (j) {
                        case 0:
                            text[0][i].setText(items.get(i - 1).getID());
                            gridPane.add(text[0][i], 0, i);
                            break;
                        case 1:
                            text[1][i].setText(items.get(i - 1).getTitle());
                            gridPane.add(text[1][i], 1, i);
                            break;
                        case 2:
                            text[2][i].setText(items.get(i - 1).getRentalType());
                            gridPane.add(text[2][i], 2, i);
                            break;
                        case 3:
                            text[3][i].setText(items.get(i - 1).getLoanType());
                            gridPane.add(text[3][i], 3, i);
                            break;
                        case 4:
                            text[4][i].setText(String.valueOf(items.get(i - 1).getNoOfCopy()));
                            gridPane.add(text[4][i], 4, i);
                            GridPane.setHalignment(text[4][i], HPos.CENTER);
                            break;
                        case 5:
                            text[5][i].setText(String.valueOf(items.get(i - 1).getRentalFee()));
                            GridPane.setHalignment(text[5][i], HPos.CENTER);
                            gridPane.add(text[5][i], 5, i);
                            break;
                        case 6:
                            text[6][i].setText(items.get(i - 1).getRentalStatus());
                            gridPane.add(text[6][i], 6, i);
                            break;
                        case 7:
                            text[7][i].setText(items.get(i - 1).getGenre());
                            gridPane.add(text[7][i], 7, i);
                            break;
                    }
                }
            }
        }

        // Set every text in the grid to be center
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Text || node instanceof Label) {
                GridPane.setHalignment(node, HPos.CENTER);
            }
        }

        // Create a scroll pane and add the grid to it
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
    }

    public void searchItemID(GridPane gridPane, String input) {
        Collections.sort(items, new IDSortItem());

        gridPane.getChildren().clear();

        if (items.isEmpty()) {
            Text text = new Text();
            text.setText("No item available");
            gridPane.getChildren().add(text);
            return;
        }

        boolean found = false;
        int l = 0, r = items.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = input.compareTo(items.get(m).getID());

            if (res == 0) {
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setHgap(15);
                gridPane.setVgap(5);

                Text[][] text = new Text[8][2];

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 2; j++) {
                        text[i][j] = new Text(); // Initialize each element of the array
                    }
                }

                text[0][0].setText("  ID  ");
                text[1][0].setText("  Title  ");
                text[2][0].setText("  Rental Type  ");
                text[3][0].setText("  Loan Type  ");
                text[4][0].setText("  Number of Copies  ");
                text[5][0].setText("  Rental Fee  ");
                text[6][0].setText("  Rental Status  ");
                text[7][0].setText("  Genre  ");

                for (int i = 0; i < 8; i++) {
                    gridPane.add(text[i][0], i, 0);
                    GridPane.setHalignment(text[i][0], HPos.CENTER);
                }

                text[0][1].setText(items.get(m).getID());
                text[1][1].setText(items.get(m).getTitle());
                text[2][1].setText(items.get(m).getRentalType());
                text[3][1].setText(items.get(m).getLoanType());
                text[4][1].setText(items.get(m).getNoOfCopy() + "");
                text[5][1].setText(items.get(m).getRentalFee() + "");
                text[6][1].setText(items.get(m).getRentalStatus());
                text[7][1].setText(items.get(m).getGenre());

                for (int i = 0; i < 8; i++) {
                    gridPane.add(text[i][1], i, 1);
                }

                found = true;
            }

            if (res > 0) {
                l = m + 1;
            } else r = m - 1;
        }

        if (!found) {
            if (isValidItemID(input)) {
                Text warningText = new Text();
                warningText.setFill(Color.RED);
                warningText.setText("No item found");
                gridPane.getChildren().add(warningText);
            } else {
                Text warningText = new Text();
                warningText.setFill(Color.RED);
                warningText.setText("ID is invalid");
                gridPane.getChildren().add(warningText);
            }
        }
    }

    // Old version
    /*public void searchItemTitle(GridPane gridPane, String input) {
        Collections.sort(items, new TitleSortItem());

        gridPane.getChildren().clear();

        if (items.isEmpty()) {
            Text text = new Text();
            text.setText("No item available");
            gridPane.getChildren().add(text);
            return;
        }

        boolean found = false;
        for (Item item : items) {
            if (item.getTitle().contains(input)) {
                found = true;

                gridPane.setAlignment(Pos.CENTER);
                gridPane.setStyle("-fx-grid-lines-visible: true");

                String[] labels = {"  ID  ", "  Title  ", "  Rental type  ", "  Loan type  ", "  Number of copies  ",
                        "  Rental fee  ",
                        "  Rental status  ", "  Genre  "};

                for (int i = 0; i < labels.length; i++) {
                    Text label = new Text(labels[i]);
                    Text value = new Text();

                    switch (i) {
                        case 0:
                            value.setText( item.getID());
                            break;
                        case 1:
                            value.setText( item.getTitle());
                            break;
                        case 2:
                            value.setText( item.getRentalType());
                            break;
                        case 3:
                            value.setText( item.getLoanType());
                            break;
                        case 4:
                            value.setText( String.valueOf(item.getNoOfCopy()));
                            break;
                        case 5:
                            value.setText( String.valueOf(item.getRentalFee()));
                            break;
                        case 6:
                            value.setText( item.getRentalStatus());
                            break;
                        case 7:
                            value.setText( item.getGenre());
                            break;
                    }

                    gridPane.add(label, i, 0);
                    gridPane.add(value, i, 1);
                    GridPane.setHalignment(label, HPos.CENTER);
                    GridPane.setHalignment(value, HPos.CENTER);
                }
            }
        }

        if (!found) {
            Text warningText = new Text();
            warningText.setFill(Color.RED);
            warningText.setText("No item found");
            gridPane.getChildren().add(warningText);
        }
    }*/

    // Modified version of searchItemTitle
    public void searchItemTitle(GridPane itemTable, String input) {
        Collections.sort(items, new TitleSortItem());

        itemTable.getChildren().clear();

        if (items.isEmpty()) {
            Text text = new Text();
            text.setText("No item available");
            itemTable.getChildren().add(text);
            return;
        }

        if (input.isEmpty()) {
            Text warningText = new Text();
            warningText.setFill(Color.RED);
            warningText.setText("Please enter a title to search");
            itemTable.getChildren().add(warningText);
            return;
        }

        boolean found = false;
        int currentRow = 0; // Keep track of the current row

        // Add the labels to the first row of the grid
        String[] labels = {" ID ", " Title ", " Rental type ", " Loan type ", " Number of copies ", " Rental fee ",
                " Rental status ", " Genre "};
        for (int i = 0; i < labels.length; i++) {
            Text label = new Text(labels[i]);
            itemTable.add(label, i, currentRow);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        currentRow++;

        // Set the style of the grid
        itemTable.setAlignment(Pos.CENTER);
        itemTable.setHgap(15);
        itemTable.setVgap(5);

        for (Item item : items) {
            if (item.getTitle().contains(input)) {
                found = true;

                for (int i = 0; i < labels.length; i++) {
                    Text value = new Text();

                    switch (i) {
                        case 0:
                            value.setText(" " + item.getID() + " ");
                            break;
                        case 1:
                            value.setText(" " + item.getTitle() + " ");
                            break;
                        case 2:
                            value.setText(" " + item.getRentalType() + " ");
                            break;
                        case 3:
                            value.setText(" " + item.getLoanType() + " ");
                            break;
                        case 4:
                            value.setText(" " + String.valueOf(item.getNoOfCopy()) + " ");
                            break;
                        case 5:
                            value.setText(" " + String.valueOf(item.getRentalFee()) + " ");
                            break;
                        case 6:
                            value.setText(" " + item.getRentalStatus() + " ");
                            break;
                        case 7:
                            value.setText(" " + item.getGenre() + " ");
                            break;
                    }

                    // Add the value to the current row
                    itemTable.add(value, i, currentRow);
                    GridPane.setHalignment(value, HPos.CENTER);
                }
                // Increment the current row by 1
                currentRow++;
            }
        }

        if (!found) {
            Text warningText = new Text();
            warningText.setFill(Color.RED);
            warningText.setText("No item found");
            itemTable.getChildren().add(warningText);
        }
    }

    public void searchCustomerID(GridPane gridPane, String input) {
        Collections.sort(customers, new IDSortCustomer());

        gridPane.getChildren().clear();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(5);


        if (customers.isEmpty()) {
            Text text = new Text();
            text.setText("No customer available");
            gridPane.getChildren().add(text);
            return;
        }

        boolean found = false;
        int l = 0, r = customers.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = input.compareTo(customers.get(m).getID());

            if (res == 0) {
                Text[][] text = new Text[9][2];

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 2; j++) {
                        text[i][j] = new Text(); // Initialize each element of the array
                    }
                }

                text[0][0].setText("  ID  ");
                text[1][0].setText("  Name  ");
                text[2][0].setText("  Address  ");
                text[3][0].setText("  Phone  ");
                text[4][0].setText("  Number of Rental  ");
                text[5][0].setText("  Customer type  ");
                text[6][0].setText("  Username  ");
                text[7][0].setText("  Password  ");
                text[8][0].setText("  Reward Points  ");

                for (int i = 0; i < 9; i++) {
                    gridPane.add(text[i][0], i, 0);
                    GridPane.setHalignment(text[i][0], HPos.CENTER);
                }

                text[0][1].setText(customers.get(m).getID());
                text[1][1].setText(customers.get(m).getName());
                text[2][1].setText(customers.get(m).getAddress());
                text[3][1].setText(customers.get(m).getPhone());
                text[4][1].setText(customers.get(m).getNoOfRental() + "");
                text[5][1].setText(customers.get(m).getCustomerType());
                text[6][1].setText(customers.get(m).getUsername());
                text[7][1].setText(customers.get(m).getPassword());
                text[8][1].setText(customers.get(m).getRewardPoints() + "");

                for (int i = 0; i < 9; i++) {
                    gridPane.add(text[i][1], i, 1);
                }

                found = true;
            }

            if (res > 0) {
                l = m + 1;
            } else r = m - 1;
        }

        if (!found) {
            if (isValidCustomerID(input)) {
                Text warningText = new Text();
                warningText.setFill(Color.RED);
                warningText.setText("No customer found");
                gridPane.getChildren().add(warningText);
            } else {
                Text warningText = new Text();
                warningText.setFill(Color.RED);
                warningText.setText("ID is invalid");
                gridPane.getChildren().add(warningText);
            }
        }
    }

    // Old version
    /*public void searchCustomerName(GridPane gridPane, String input) {
        Collections.sort(customers, new NameSortCustomer());

        gridPane.getChildren().clear();

        if (customers.isEmpty()) {
            Text text = new Text();
            text.setText("No customer available");
            gridPane.getChildren().add(text);
            return;
        }

        boolean found = false;

        for (Customer customer : customers) {
            if (customer.getName().contains(input)) {
                found = true;

                //gridPane.setHgap(15);
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setStyle("-fx-grid-lines-visible: true");

                String[] labels = {"  ID  ", "  Name  ", "  Address  ", "  Phone  ", "  Number of Rental  ",
                        "Customer type  ", "  Username  ", "  Password  ", "  Reward Points  "};

                for (int i = 0; i < labels.length; i++) {
                    Text label = new Text(labels[i]);
                    Text value = new Text();

                    switch (i) {
                        case 0:
                            value.setText(customer.getID());
                            break;
                        case 1:
                            value.setText(customer.getName());
                            break;
                        case 2:
                            value.setText(customer.getAddress());
                            break;
                        case 3:
                            value.setText(customer.getPhone());
                            break;
                        case 4:
                            value.setText(String.valueOf(customer.getNoOfRental()));
                            break;
                        case 5:
                            value.setText(customer.getCustomerType());
                            break;
                        case 6:
                            value.setText(customer.getUsername());
                            break;
                        case 7:
                            value.setText(customer.getPassword());
                            break;
                        case 8:
                            value.setText(String.valueOf(customer.getRewardPoints()));
                            break;
                    }

                    gridPane.add(label, i, 0);
                    gridPane.add(value, i, 1);
                    GridPane.setHalignment(label, HPos.CENTER);
                    GridPane.setHalignment(value, HPos.CENTER);
                }
            }
        }

        if (!found) {
            Text warningText = new Text();
            warningText.setFill(Color.RED);
            warningText.setText("No customer found");
            gridPane.getChildren().add(warningText);
        }
    }*/

    // Modified version of searchCustomerName
    public void searchCustomerName(GridPane customerTable, String input) {
        Collections.sort(customers, new NameSortCustomer());

        customerTable.getChildren().clear();

        if (customers.isEmpty()) {
            Text text = new Text();
            text.setText("No customer available");
            customerTable.getChildren().add(text);
            return;
        }

        if (input.isEmpty()) {
            Text warningText = new Text();
            warningText.setFill(Color.RED);
            warningText.setText("Please enter a name to search");
            customerTable.getChildren().add(warningText);
            return;
        }

        boolean found = false;
        int currentRow = 0; // Keep track of the current row

        // Add the labels to the first row of the grid
        String[] labels = {" ID ", " Name ", " Address ", " Phone ", " Number of Rental ", " Customer type ", " " +
                "Username ", " Password ", " Reward Points "};
        for (int i = 0; i < labels.length; i++) {
            Text label = new Text(labels[i]);
            customerTable.add(label, i, currentRow);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        currentRow++;

        // Set the style of the grid
        customerTable.setAlignment(Pos.CENTER);
        customerTable.setVgap(5);
        customerTable.setHgap(15);

        for (Customer customer : customers) {
            if (customer.getName().contains(input)) {
                found = true;

                for (int i = 0; i < labels.length; i++) {
                    Text value = new Text();

                    switch (i) {
                        case 0:
                            value.setText(customer.getID());
                            break;
                        case 1:
                            value.setText(customer.getName());
                            break;
                        case 2:
                            value.setText(customer.getAddress());
                            break;
                        case 3:
                            value.setText(customer.getPhone());
                            break;
                        case 4:
                            value.setText(String.valueOf(customer.getNoOfRental()));
                            break;
                        case 5:
                            value.setText(customer.getCustomerType());
                            break;
                        case 6:
                            value.setText(customer.getUsername());
                            break;
                        case 7:
                            value.setText(customer.getPassword());
                            break;
                        case 8:
                            value.setText(String.valueOf(customer.getRewardPoints()));
                            break;
                    }

                    // Add the value to the current row
                    customerTable.add(value, i, currentRow);
                    GridPane.setHalignment(value, HPos.CENTER);
                }
                // Increment the current row by 1
                currentRow++;
            }
        }

        if (!found) {
            Text warningText = new Text();
            warningText.setFill(Color.RED);
            warningText.setText("No customer found");
            customerTable.getChildren().add(warningText);
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String welcomeTitle() {
        return "Welcome back, " + currentUser.getName() + "!";
    }

    public String getCurrentUserID() {
        return currentUser.getID();
    }
}

class IDSortCustomer implements Comparator<Customer> {
    @Override
    public int compare(Customer i1, Customer i2) {
        return i1.getID().compareTo(i2.getID());
    }
}

class NameSortCustomer implements Comparator<Customer> {
    @Override
    public int compare(Customer i1, Customer i2) {
        return i1.getName().compareTo(i2.getName());
    }
}

class customerTypeSort implements Comparator<Customer> {
    @Override
    public int compare(Customer c1, Customer c2) {
        return c1.getCustomerType().compareTo(c2.getCustomerType());
    }
}

class IDSortItem implements Comparator<Item> {
    @Override
    public int compare(Item i1, Item i2) {
        return i1.getID().compareTo(i2.getID());
    }
}

class TitleSortItem implements Comparator<Item> {
    @Override
    public int compare(Item i1, Item i2) {
        return i1.getTitle().compareTo(i2.getTitle());
    }
}
