public class Main {
    public static void main(String[] args) {
        /* Create Pen instance using default constructor and setters */
        Pen schneider = new Pen();

        schneider.setAmount(20);
        schneider.setColor("Black");
        schneider.setType("Gel");
        schneider.setSize(0.01);
        schneider.setPrice(20);
        schneider.totalAmount = 100;

        System.out.println(schneider.toString());
        schneider.printStaticSum();
        schneider.printSum();

        /* Create Pen instance using constructor with 4 fields specified */

        Pen schneider1 = new Pen(15, "Blue", "Gel", 20.15);
        schneider1.setSize(0.02);
        schneider1.totalAmount = 300;

        System.out.println(schneider1.toString());
        schneider1.printStaticSum();
        schneider1.printSum();

        /* Create Pen instance using constructor with all the fields specified */

        Pen schneider2 = new Pen(25, "Green", "Gel", 0.05, 20.15, 550);

        System.out.println(schneider2.toString());
        schneider2.printStaticSum();
        schneider2.printSum();
    }
}
