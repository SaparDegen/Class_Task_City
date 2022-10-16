package peaksoft;

import peaksoft.dbconfig.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static ArrayList<Country> countryList = new ArrayList<>();
    public static ArrayList<City> cityList = new ArrayList<>();
    public static Scanner scanN = new Scanner(System.in);
    public static Scanner scanS = new Scanner(System.in);

    public static void main( String[] args ) {
        commands();
        String a = "";
        while (!a.equals("0")) {
            a = buttons();
            if (a.equals("1")) {
                System.out.print("Input country id: ");
                int countryId = scanN.nextInt();
                System.out.print("Input country name: ");
                String countryName = scanS.nextLine();
                insertCountry(countryId, countryName);
            } else if (a.equals("2")) {
                System.out.print("Input city id: ");
                int cityId = scanN.nextInt();
                System.out.print("Input city name: ");
                String cityName = scanS.nextLine();
                System.out.print("Input country id: ");
                int countryId = scanN.nextInt();
                insertCity(cityId, cityName, countryId);
            } else if (a.equals("3")) {
                System.out.print("Input city-leader id: ");
                int leaderId = scanN.nextInt();
                System.out.print("Input first name: ");
                String firstName = scanS.nextLine();
                System.out.print("Input last name: ");
                String lastName = scanS.nextLine();
                System.out.print("Input city id: ");
                int cityId = scanN.nextInt();
                insertCityLeader(leaderId, firstName, lastName, cityId);
            } else if (a.equals("4")) {
                System.out.println("Id | City        | First_name  | Last_name   | Country     |");
                getCityTable();
            } else if (a.equals("5")) {
                System.out.println(countryList);
            } else if (a.equals("6")) {
                System.out.println(cityList);
            }
        }
    }

    public static void insertCountry(int countryId, String countryName) {
        String query = "insert into country(id, country_name) values (?,?);";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, countryId);
            prepared.setString(2, countryName);
            prepared.executeUpdate();

            Country country = new Country(countryId, countryName);
            countryList.add(country);

            System.out.println("A new country - " + countryName + ", was successfully added to database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertCity(int cityId, String cityName, int countryId) {
        String query = "insert into city(id, city, country_id) values (?,?,?);";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, cityId);
            prepared.setString(2, cityName);
            prepared.setInt(3, countryId);
            prepared.executeUpdate();

            City city = new City(cityId, cityName);
            cityList.add(city);

            System.out.println("A new city - " + cityName + ", was successfully added to database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertCityLeader(int leaderId, String firstName, String lastName, int cityId) {
        String query = "insert into city_leaders(id, first_name, last_name, city_id) values (?,?,?,?);";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement prepared = connection.prepareStatement(query)) {

            prepared.setInt(1, leaderId);
            prepared.setString(2, firstName);
            prepared.setString(3, lastName);
            prepared.setInt(4, cityId);
            prepared.executeUpdate();

            System.out.println("A new city-leader - " + firstName + " " + lastName + ", was successfully added to database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getCityTable() {
        String query = "select ci.id, ci.city, cl.first_name, cl.last_name, c.country_name from city ci\n" +
                "join city_leaders cl on ci.id = cl.city_id\n" +
                "join country c on c.id = ci.country_id;";

        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                String s1 = String.format("%-3s|", rs.getInt("id"));
                String s2 = String.format("%-12s|", rs.getString("city"));
                String s3 = String.format("%-12s|", rs.getString("first_name"));
                String s4 = String.format("%-12s|", rs.getString("last_name"));
                String s5 = String.format("%-12s|", rs.getString("country_name"));
                System.out.println(s1 + " " + s2 + " " + s3 + " " + s4 + " " + s5);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void commands() {
        System.out.println("--------------Commands-----------------------");
        System.out.println("Press 1 to input Country");
        System.out.println("Press 2 to input City");
        System.out.println("Press 3 to input City-leader");
        System.out.println("Press 4 to output City table");
        System.out.println("Press 5 to output Country List");
        System.out.println("Press 6 to output City List");
        System.out.println("Press 0 to exit the App");
        System.out.println("---------------------------------------------");
    }

    public static String buttons(){
        System.out.print("Choose a command: ");
        return scanS.nextLine();
    }
}
