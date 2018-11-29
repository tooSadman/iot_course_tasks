package main.java.ua.lviv.iot;

import java.sql.*;
import java.util.Scanner;

public class Application {
    private static final String url =
            "jdbc:mysql://localhost:3306/lab6?serverTimezone=UTC&useSSL=false";
    private static final String user = "root";           // тут вводиш назву свого юзера в mysql (це по ідеї не має бути root)
    private static final String password = "powershot1488";   //  тут пароль до бд

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet rs = null;

    public static void main(String args[]) {
        try {
            //This will load the MySQL driver, each DB has its own driver //
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Get a connection to database //
            connection = DriverManager.getConnection(url, user, password);
            // Create a statement
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();


        startApp();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver is not loaded");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {
            //close connection ,statement and resultset
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } // ignore
            if (statement != null) try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private static void startApp() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select your command: ");
        System.out.println("-----View--------: \n" +
                "Aircrafts: v1\n" +
                "Classes: v2\n" +
                "Hangars: v3\n" +
                "-----Insert--------: \n" +
                "Aircrafts: i1\n" +
                "Classes: i2\n" +
                "Hangars: i3\n" +
                "-----Update--------: \n" +
                "Aircrafts: u1\n" +
                "Classes: u2\n" +
                "Hangars: u3\n" +
                "-----Delete--------: \n" +
                "Aircrafts: d1\n" +
                "Classes: d2\n" +
                "Hangars: d3\n" +
                "-----Insert AircraftsHangars--------: \n" +
                "ipc");

        String cmd = sc.next();

        if (cmd.equals("v1")) {
            readAircrafts();
        } else if (cmd.equals("v2")) {
            readClasses();
        } else if (cmd.equals("v3")) {
            readHangars();
        } else if (cmd.equals("i1")) {
            insertAircrafts();
        } else if (cmd.equals("i2")) {
            insertClass();
        } else if (cmd.equals("i3")) {
            insertHangar();
        } else if (cmd.equals("u1")) {
            updateAircrafts();
        } else if (cmd.equals("u2")) {
            updateClass();
        } else if (cmd.equals("u3")) {
            updateHangar();
        } else if (cmd.equals("d1")) {
            deleteAircraft();
        } else if (cmd.equals("d2")) {
            deleteClass();
        } else if (cmd.equals("d3")) {
            deleteHangar();
        } else if (cmd.equals("ipc")) {
            callProcedureForInsertToHangarAircrafts();
        } else {
            System.out.println("Invalid cmd, try again");
        }

        System.out.println("Procceed? (y/N)");
        cmd = sc.next();
        if (cmd.equals("y")) {
            startApp();
        } else {
            System.exit(0);
        }

    }



    private static void readAircrafts() throws SQLException{
        rs = statement.executeQuery("SELECT COUNT(*) FROM aircraft");

        //Process the result set
        while (rs.next()) {
            int count = rs.getInt(1);
            // Simply Print the results
            System.out.format("\ncount: %d\n", count);
        }


        rs = statement.executeQuery("SELECT * FROM aircraft");

        System.out.format("\n----------------------Table Aircrafts --------------------\n");
        System.out.format("%-16s %-16s %-10s %-16s \n", "pilot", "aircraft_name", "launched", "class_name");
        while (rs.next()) {
            String pilot = rs.getString("pilot");
            String aircraft_name = rs.getString("aircraft_name");
            int launched = rs.getInt("launched");
            String class_name = rs.getString("class_name");
            // Simply Print the results
            System.out.format("%-16s %-16s %-10d %-16s \n", pilot, aircraft_name, launched, class_name);
        }
    }

    private static void readClasses() throws SQLException {
        rs = statement.executeQuery("SELECT * FROM aircraft_class");

        //Process the result set
        System.out.format("\n---------------------Table Aircraft Classes --------------------\n");
        System.out.format("%-20s %-16s %-16s %-16s\n", "class_name", "num_of_rockets", "cost", "country");
        while (rs.next()) {
            String class_name = rs.getString("class_name");
            int num_of_rockets = rs.getInt("num_of_rockets");
            int cost = rs.getInt("cost");
            String country = rs.getString("country");
            // Simply Print the results
            System.out.format("%-20s %-16s %-16s %-16s\n", class_name, num_of_rockets, cost, country);
        }
    }

    private static void readHangars() throws SQLException {

        rs = statement.executeQuery("SELECT * FROM hangar");

        //Process the result set
        System.out.format("\n-------------------Table Hanagars--------------------\n");
        System.out.format("%-16s %-16s %-16s\n", "City", "num_of_aircrafts", "Country");
        while (rs.next()) {
            String hangar_city = rs.getString("hangar_city");
            int num_of_aircrafts = rs.getInt("num_of_aircrafts");
            String country = rs.getString("country");
            // Simply Print the results
            System.out.format("%-16s %-16s %-16s\n", hangar_city, num_of_aircrafts, country);
        }

    }


    private static void insertAircrafts() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a pilot's Name: ");
        String pilot = input.next();
        System.out.println("Input a aircraft Name: ");
        String aircraft_name = input.next();
        System.out.println("Input a aircraft's Experience: ");
        int exp = input.nextInt();
        System.out.println("Input a aircraft's class: ");
        String club = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO aircraft VALUES (?, ?, ? , ?)");
        preparedStatement.setString(1, pilot);
        preparedStatement.setString(2, aircraft_name);
        preparedStatement.setInt(3, exp);
        preparedStatement.setString(4, club);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: " + n);

    }

    private static void insertClass() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a class Name: ");
        String name = input.next();
        System.out.println("Input a class Number of rockets: ");
        String numRockets = input.next();
        System.out.println("Input a class Cost: ");
        String cost = input.next();
        System.out.println("Input a class Country: ");
        String country = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO aircraft_class VALUES (?, ?, ? , ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, numRockets);
        preparedStatement.setString(3, cost);
        preparedStatement.setString(4, country);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: " + n);

    }

    private static void insertHangar() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a hangar city: ");
        String name = input.next();
        System.out.println("Input a hangar num of aircrafts: ");
        String num_of_aircrafts = input.next();
        System.out.println("Input a hangar Country: ");
        String country = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO aircraft_class VALUES (?, ?, ? , ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setString(3, num_of_aircrafts);
        preparedStatement.setString(4, country);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: " + n);

    }


    private static void updateAircrafts() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input pilot's name for the aircraft you want to update: ");
        String pilot = input.next();
        System.out.println("Input name of the aircraft you want to update: ");
        String aircraft_name = input.next();
        System.out.println("Input new pilot: ");
        String newcapitain = input.next();
        System.out.println("Input new aircraft name: ");
        String newship_name = input.next();

        CallableStatement callableStatement;
        callableStatement = connection.prepareCall("{CALL update_aircraft(?, ?, ?, ?)}");
        callableStatement.setString(1,pilot);
        callableStatement.setString(2, aircraft_name);
        callableStatement.setString(3, newcapitain);
        callableStatement.setString(4, newship_name);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next()) {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: " + msg);
        }
    }

    private static void updateClass() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input name for the class you want to update: ");
        String oldName = input.next();
        System.out.println("Input new class name: ");
        String newName = input.next();


        CallableStatement callableStatement;
        callableStatement = connection.prepareCall("{CALL update_class(?, ?)}");
        callableStatement.setString(1,oldName);
        callableStatement.setString(2, newName);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next()) {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: " + msg);
        }
    }


    private static void updateHangar() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input city name for the hangar you want to update: ");
        String oldName = input.next();
        System.out.println("Input new hangar city: ");
        String newName = input.next();


        CallableStatement callableStatement;
        callableStatement = connection.prepareCall("{CALL update_hangar(?, ?)}");
        callableStatement.setString(1,oldName);
        callableStatement.setString(2, newName);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next()) {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: " + msg);
        }
    }

    private static void deleteAircraft() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a pilot's name to delete: ");
        String pilot = input.next();
        System.out.println("Input a aircraft name to delete: ");
        String aircraft_name = input.next();


        //PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM aircraft WHERE pilot=? AND aircraft_name=?");
        preparedStatement.setString(1, pilot);
        preparedStatement.setString(2, aircraft_name);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: " + n);
    }

    private static void deleteClass() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a name of class to delete: ");
        String name = input.next();

        //PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM aircraft_class WHERE class_name=?");
        preparedStatement.setString(1, name);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: " + n);
    }

    private static void deleteHangar() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a city name of hangar to delete: ");
        String hangar_city = input.next();



        //PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM hangars WHERE hangar_city=?");
        preparedStatement.setString(1, hangar_city);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: " + n);
    }

    private static void callProcedureForInsertToHangarAircrafts() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput Pilot For Aircraft: ");
        String pilot = input.next();
        System.out.println("Input Hangar City Name: ");
        String hangar_city = input.next();

        CallableStatement callableStatement;
        callableStatement = connection.prepareCall("{CALL insert_hangaraircraft(?, ?)}");
        callableStatement.setString(1, pilot);
        callableStatement.setString(2, hangar_city);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next()) {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: " + msg);
        }
    }

}

