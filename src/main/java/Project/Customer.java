package Project;

import java.util.ArrayList;

public class Customer {
    private String ID;
    private String name;
    private String address;
    private String phone;
    private int noOfRental;
    private String customerType;
    private String username;
    private String password;
    private int rewardPoints;
    private ArrayList<Item> items;

    public Customer(String ID, String name, String address, String phone, int noOfRental, String customerType,
                    String username, String password, int rewardPoints, ArrayList<Item> items) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.noOfRental = noOfRental;
        this.customerType = customerType;
        this.username = username;
        this.password = password;
        this.rewardPoints = rewardPoints;
        this.items = items;
    }

    public Customer(String ID, String name, String address, String phone, int noOfRental, String customerType,
                    String username, String password, int rewardPoints) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.noOfRental = noOfRental;
        this.customerType = customerType;
        this.username = username;
        this.password = password;
        this.rewardPoints = rewardPoints;
        this.items = new ArrayList<Item>();
    }

    public Customer() {
        this.rewardPoints = 0;
        this.items = new ArrayList<Item>();
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getNoOfRental() {
        return noOfRental;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNoOfRental(int noOfRental) {
        this.noOfRental = noOfRental;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    @Override
    public String toString() {
        String customer = "ID: " + ID +
                "\nName: " + name +
                "\nAddress: " + address +
                "\nPhone: " + phone +
                "\nNumber of rental: " + noOfRental +
                "\nCustomer type: " + customerType +
                "\nUsername: " + username +
                "\nPassword: " + password +
                "\nReward points: " + rewardPoints;
        if (!items.isEmpty()){
            customer += "\nItems:\n" + items;
        }
        return customer;
    }

    // A shorter version of toString method (only display item's ID and title)
    public String customerInfo() {
        String customer = "ID: " + ID +
                "\nName: " + name +
                "\nAddress: " + address +
                "\nPhone: " + phone +
                "\nNumber of rental: " + noOfRental +
                "\nCustomer type: " + customerType +
                "\nUsername: " + username +
                "\nPassword: " + password +
                "\nReward points: " + rewardPoints;
        if (!items.isEmpty()) {
            customer += "\nItems:";
            for (Item item : items) {
                customer += "\nID: " + item.getID() + ", Title: " + item.getTitle();
            }
        }
        return customer;
    }

}
