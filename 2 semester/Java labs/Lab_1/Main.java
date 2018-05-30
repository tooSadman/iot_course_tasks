public class Main {
    public static void main(String [] args) {
      /* Create Pen instance using default constructor and setters */
        Pen Schneider = new Pen();

        Schneider.setAmount(20);
        Schneider.setColor("Black");
        Schneider.setType("Gel");
        Schneider.setSize(0.01);
        Schneider.setPrice(20);
        Schneider.totalAmount = 100;

        System.out.println(Schneider.toString());
        Schneider.printStaticSum();
        Schneider.printSum();


        /* Create Pen instance using constructor with 4 fields specified */

        Pen Schneider1 = new Pen(15, "Blue", "Gel", 20.15);
        Schneider1.setSize(0.02);
        Schneider1.totalAmount = 300;

        System.out.println(Schneider1.toString());
        Schneider1.printStaticSum();
        Schneider1.printSum();


        /* Create Pen instance using constructor with all the fields specified */

        Pen Schneider2 = new Pen(25, "Green", "Gel", 0.05, 20.15, 550);

        System.out.println(Schneider2.toString());
        Schneider2.printStaticSum();
        Schneider2.printSum();
    }
}
