package Project;

public class Item {
    private String ID;
    private String title;
    private String rentalType;
    private String loanType;
    private int noOfCopy;
    private float rentalFee;
    private String rentalStatus;
    private String genre;

    public Item(String ID, String title, String rentalType, String loanType, int noOfCopy, float rentalFee, String rentalStatus, String genre) {
        this.ID = ID;
        this.title = title;
        this.rentalType = rentalType;
        this.loanType = loanType;
        this.noOfCopy = noOfCopy;
        this.rentalFee = rentalFee;
        this.rentalStatus = rentalStatus;
        this.genre = genre;
    }

    public Item(String ID, String title, String rentalType, String loanType, int noOfCopy, float rentalFee, String rentalStatus) {
        this.ID = ID;
        this.title = title;
        this.rentalType = rentalType;
        this.loanType = loanType;
        this.noOfCopy = noOfCopy;
        this.rentalFee = rentalFee;
        this.rentalStatus = rentalStatus;
    }

    public Item() {
        this.ID = null;
        this.title = null;
        this.rentalType = null;
        this.loanType = null;
        this.noOfCopy = 0;
        this.rentalFee = 0;
        this.rentalStatus = null;
        this.genre = null;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getRentalType() {
        return rentalType;
    }

    public String getLoanType() {
        return loanType;
    }

    public int getNoOfCopy() {
        return noOfCopy;
    }

    public float getRentalFee() {
        return rentalFee;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public String getGenre() {
        return genre;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public void setNoOfCopy(int noOfCopy) {
        this.noOfCopy = noOfCopy;
    }

    public void setRentalFee(float rentalFee) {
        this.rentalFee = rentalFee;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Item" +
                "ID = " + ID +
                ", title = " + title +
                ", rentalType = " + rentalType +
                ", loanType = " + loanType +
                ", noOfCopy = " + noOfCopy +
                ", rentalFee = " + rentalFee +
                ", rentalStatus = " + rentalStatus +
                ", genre = " + genre;
    }
}
