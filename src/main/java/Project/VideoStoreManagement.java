package Project;

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

    private Customer findCustomerByID(String ID) {
        for (Customer customer : customers) {
            if (customer.getID().equals(ID))
                return customer;
        }
        return null;
    }

    private Item findItemByID(String ID) {
        for (Item item : items) {
            if (item.getID().equals(ID))
                return item;
        }
        return null;
    }

    // Method to check valid item ID
    private static boolean isValidItemID(String id) {
        if (!id.matches("I\\d{3}-\\d{4}")) {
            return false;
        }
        int year = Integer.parseInt(id.substring(id.length() - 4));
        return year >= 1900 && year <= 2023;
    }

    // Method to check valid customer ID
    private static boolean isValidCustomerID(String id) {
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

    public boolean updateCustomer() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter customer ID: ");
        String ID = scan.nextLine();
        Customer customer = findCustomerByID(ID);
        if (customer != null) {
            System.out.print("Enter new name: ");
            String name = scan.nextLine();
            System.out.print("Enter new address: ");
            String address = scan.nextLine();
            System.out.print("Enter new phone number: ");
            String phone = scan.nextLine();
            System.out.print("Enter new username: ");
            String username = scan.nextLine();
            System.out.print("Enter new password: ");
            String password = scan.nextLine();
            customer.setName(name);
            customer.setAddress(address);
            customer.setPhone(phone);
            customer.setUsername(username);
            customer.setPassword(password);
            System.out.println("Customer updated!");
            return true;
        }
        System.out.println("No customer found!");
        return false;
    }

    public boolean increaseNoOfCopies() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter item ID: ");
        String ID = scan.nextLine();
        Item item = findItemByID(ID);
        if (item != null) {
            System.out.print("Enter number of new stock: ");
            int copies = scan.nextInt();
            item.setNoOfCopy(item.getNoOfCopy() + copies);
            System.out.println("Number of items udpated!");
            return true;
        }
        System.out.println("No item found!");
        return false;
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
                    Item item = new Item(itemFields[0], itemFields[1], itemFields[2], itemFields[3], Integer.parseInt(itemFields[4]), Float.parseFloat(itemFields[5]), itemFields[6]);
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
                Customer customer = new Customer(customerFields[0], customerFields[1], customerFields[2], customerFields[3], Integer.parseInt(customerFields[4]), customerFields[5], customerFields[6], customerFields[7], Integer.parseInt(customerFields[8]));
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
                String customerString = customer.getID() + "," + customer.getName() + "," + customer.getAddress() + "," + customer.getPhone() + "," + customer.getNoOfRental() + "," + customer.getCustomerType() + "," + customer.getUsername() + "," + customer.getPassword() + "," + customer.getRewardPoints();
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
                String itemString = item.getID() + "," + item.getTitle() + "," + item.getRentalType() + "," + item.getLoanType() + "," + item.getNoOfCopy() + "," + item.getRentalFee() + "," + item.getRentalStatus();
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

    // Rent an item (decrease the number of copies held in stock)
    // Return true if the item is successfully rented, false otherwise
    public boolean rentItem() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter item ID: ");
        String ID = scan.nextLine();
        Item item = findItemByID(ID);

        if (item == null) {
            System.out.println("No item found!");
            return false;
        }

        if (currentUser.getCustomerType().equals("Guest") && item.getLoanType().equals("2-day")) {
            System.out.println("Error: Guest customers cannot borrow 2-day items.");
            return false;
        } else if (item.getRentalStatus().equals("not available")) {
            System.out.println("Error: This item is not available for rent.");
            return false;
        } else if (item.getRentalStatus().equals("borrowed")) {
            System.out.println("Error: This item is already borrowed.");
            return false;
        } else {
            if (currentUser.getCustomerType().equals("Guest") && currentUser.getItems().size() >= 2) {
                System.out.println("Error: Guest customers can only rent a maximum of 2 items.");
                return false;
            } else {
                currentUser.getItems().add(item);
                item.setNoOfCopy(item.getNoOfCopy() - 1);
                if (item.getNoOfCopy() == 0) {
                    item.setRentalStatus("borrowed");
                }
                System.out.println("Item rented successfully.");
                if (currentUser.getCustomerType().equals("VIP")) {
                    currentUser.setRewardPoints(currentUser.getRewardPoints() + 10);
                    if (currentUser.getRewardPoints() >= 100) {
                        boolean useRewardPoints = promptForRewardPointsUsage();
                        if (useRewardPoints) {
                            chooseFreeRental();
                            currentUser.setRewardPoints(currentUser.getRewardPoints() - 100);
                        }
                    }
                }
                return true;
            }
        }
    }

    public boolean promptForRewardPointsUsage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Congratulations! You have earned a free rental. Would you like to use your reward points? (Y/N)");
        String input = scanner.nextLine().toLowerCase();
        return input.equals("Y");
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

    public void chooseFreeRental() {
        Scanner scanner = new Scanner(System.in);
        if (currentUser.getCustomerType().equals("VIP") && currentUser.getRewardPoints() >= 100) {
            System.out.println("Congratulations! You have earned a free rental.");
            System.out.println("Please choose an item from the following list:");
            ArrayList<Item> availableItems = getAvailableItems();
            for (int i = 0; i < availableItems.size(); i++) {
                System.out.println((i + 1) + ". " + availableItems.get(i).toString());
            }
            int selection = scanner.nextInt();
            Item selectedItem = availableItems.get(selection - 1);

            Item item = findItemByID(selectedItem.getID());     // This item will not be null
            currentUser.addItem(item);
            item.setNoOfCopy(item.getNoOfCopy() - 1);
            item.setRentalStatus("borrowed");
            currentUser.setRewardPoints(currentUser.getRewardPoints() - 100);
            System.out.println("Item rented successfully.");
        } else {
            System.out.println("Error: You do not have enough reward points to rent a free item.");
        }
    }

    // Return an item (increase the number of copies held in stock)
    // Return true if the item is successfully returned, false otherwise
    public boolean returnItem() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter item ID to return: ");
        String ID = scan.nextLine();
        // Find the item in the customer item list
        for (Item currentItem : currentUser.getItems()) {
            if (Objects.equals(currentItem.getID(), ID)) {      // If the item exists in the customer list
                // Remove the item from the customer list
                currentUser.getItems().remove(currentItem);
                // Update number of rental whenever a customer return an item
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

                System.out.println("Return successfully!");
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

    private void displayIDSortItem() {
        Collections.sort(items, new IDSortItem());
        for (Item item : items) {
            System.out.println(item);
        }
    }

    private void displayTitleSortItem() {
        Collections.sort(items, new TitleSortItem());
        for (Item item : items) {
            System.out.println(item);
        }
    }

    public void displayItem() {
        Scanner sc = new Scanner(System.in);
        int input;
        System.out.println("Display item by:\n1. ID\n2. Title");
        input = sc.nextInt();
        if (input == 1) {
            displayIDSortItem();
        } else {
            displayTitleSortItem();
        }
    }

    private void displayIDSortCustomer() {
        Collections.sort(customers, new IDSortCustomer());
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private void displayNameSortCustomer() {
        Collections.sort(customers, new NameSortCustomer());
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public void displayCustomer() {
        Scanner sc = new Scanner(System.in);
        int input;
        System.out.println("Display customer by:\n1. ID\n2. Name");
        input = sc.nextInt();
        if (input == 1) {
            displayIDSortCustomer();
        } else {
            displayNameSortCustomer();
        }
    }

    public void displayGroup() {
        Collections.sort(customers, new customerTypeSort());
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public void displayItemNoCopies() {
        for (Item item : items) {
            if (item.getNoOfCopy() == 0) {
                System.out.println("ID: " + item.getID() + "\nName: " + item.getTitle());
            }
        }
    }

    private void searchItemID(String input) {
        if (items.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        Collections.sort(items, new IDSortItem());
        boolean found = false;
        int l = 0, r = items.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = input.compareTo(items.get(m).getID());

            if (res == 0) {
                System.out.println("Title: " + items.get(m).getTitle()
                        + "\nGenre: " + items.get(m).getGenre()
                        + "\nRental Type: " + items.get(m).getRentalType()
                        + "\nNumber of copies available: " + items.get(m).getNoOfCopy());
                found = true;
            }

            if (res > 0) {
                l = m + 1;
            } else
                r = m - 1;
        }
        if (!found) {
            System.out.println("Item not found.");
        }
    }

    private void searchItemTitle(String input) {
        if (items.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        Collections.sort(items, new TitleSortItem());
        boolean found = false;
        int l = 0, r = items.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = input.compareTo(items.get(m).getTitle());

            if (res == 0) {
                System.out.println("Title: " + items.get(m).getTitle()
                        + "\nGenre: " + items.get(m).getGenre()
                        + "\nRental Type: " + items.get(m).getRentalType()
                        + "\nNumber of copies available: " + items.get(m).getNoOfCopy());
                found = true;
            }

            if (res > 0) {
                l = m + 1;
            } else
                r = m - 1;
        }
        if (!found) {
            System.out.println("Item not found");
        }
    }

    public void searchItem() {
        System.out.println("Search Item by:\n1. ID\n2. Title");
        Scanner sc = new Scanner(System.in);
        int choice;
        String input;
        choice = sc.nextInt();
        sc.nextLine();
        if (choice == 1) {
            System.out.println("Please input ID:");
            input = sc.nextLine();
            searchItemID(input);
        } else {
            System.out.println("Please input title:");
            input = sc.nextLine();
            searchItemTitle(input);
        }
    }

    private void searchCustomerID(String input) {
        if (customers.isEmpty()) {
            System.out.println("No customer.");
            return;
        }

        Collections.sort(customers, new IDSortCustomer());
        boolean found = false;
        int l = 0, r = customers.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = input.compareTo(customers.get(m).getID());

            if (res == 0) {
                System.out.println("Name: " + customers.get(m).getName()
                        + "\nID: " + customers.get(m).getID()
                        + "\nRental Type: " + customers.get(m).getPhone()
                        + "\nNumber of copies available: " + customers.get(m).getAddress());
                found = true;
            }

            if (res > 0) {
                l = m + 1;
            } else
                r = m - 1;
        }
        if (!found) {
            System.out.println("Customer not found.");
        }
    }

    private void searchCustomerName(String input) {
        if (customers.isEmpty()) {
            System.out.println("No customer.");
            return;
        }

        Collections.sort(customers, new NameSortCustomer());
        boolean found = false;
        int l = 0, r = customers.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            int res = input.compareTo(customers.get(m).getName());

            if (res == 0) {
                System.out.println("Name: " + customers.get(m).getName()
                        + "\nID: " + customers.get(m).getID()
                        + "\nRental Type: " + customers.get(m).getPhone()
                        + "\nNumber of copies available: " + customers.get(m).getAddress());
                found = true;
            }

            if (res > 0) {
                l = m + 1;
            } else
                r = m - 1;
        }
        if (!found) {
            System.out.println("Customer not found.");
        }
    }

    public void searchCustomer() {
        System.out.println("Search customer by:\n1. ID\n2. Name");
        Scanner sc = new Scanner(System.in);
        int choice;
        String input;
        choice = sc.nextInt();
        sc.nextLine();
        if (choice == 1) {
            System.out.println("Please input ID:");
            input = sc.nextLine();
            searchCustomerID(input);
        } else {
            System.out.println("Please input name:");
            input = sc.nextLine();
            searchCustomerName(input);
        }
    }

    public static void main(String[] args) {
        VideoStoreManagement vsm = new VideoStoreManagement();
        vsm.loadData();     // Do not remove this line;

        //vsm.addItem();

        vsm.saveData();     // Do not remove this line
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
