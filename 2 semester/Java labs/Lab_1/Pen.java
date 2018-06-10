public class Pen {

//import org.omg.CORBA.StringHolder;

    private int amount;
    private String color;
    private String type;
    private double size;
    private double price;

    static int totalAmount;

    /* Constructors: default, 4 fields, all fields */

    Pen() {
    }

    ;

    public Pen(int amount, String color, String type, double price) {
        this.amount = amount;
        this.color = color;
        this.type = type;
        this.price = price;
    }

    public Pen(int amount, String color, String type, double size, double price, int totalAmount) {
        this.amount = amount;
        this.color = color;
        this.type = type;
        this.price = price;
        this.size = size;
        this.totalAmount = totalAmount;
    }

    /* ACCESS METHODS */

    //amount
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    //color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //size
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    //price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /* Methods */

    public String toString() {
        return getAmount() + " " + getPrice() + " " + getColor() + " " + getSize() + " " + getType();
    }

    public static void printStaticSum() {
        System.out.println("totalAmount: " + totalAmount);
    }

    public void printSum() {
        System.out.println("totalAmount: " + totalAmount);
    }

    public void resetValues(int amount, String color, String type, double size, double price, int totalAmount) {
        this.amount = amount;
        this.color = color;
        this.type = type;
        this.size = size;
        this.price = price;
        this.totalAmount = totalAmount;
    }

}
