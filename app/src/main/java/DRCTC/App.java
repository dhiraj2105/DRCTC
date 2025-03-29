package DRCTC;

import DRCTC.entities.Train;
import DRCTC.entities.User;
import DRCTC.services.UserBookingSerivce;
import DRCTC.util.UserServiceUtil;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        System.out.println("--- Running Train Booking System ---");

        Scanner scanner = new Scanner((System.in));

        int option = -1;

        UserBookingSerivce userBookingSerivce;

        try {
            userBookingSerivce = new UserBookingSerivce();
        } catch (IOException e) {
            System.out.println("There is something wrong");
            return;
        }

        while (option != 7) {
            System.out.println("Choose Option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("0. Exit the App");
            option = scanner.nextInt();

            System.out.println("Enter Name : ");
            String name = scanner.next();
            System.out.println("Enter the password : ");
            String password = scanner.next();

            switch (option) {
                case 1:
                    User userToSignup = new User(name, password,
                            UserServiceUtil.hashPassword(password),
                            new ArrayList<>(), UUID.randomUUID().toString());

                    userBookingSerivce.signUp(userToSignup);
                    break;
                case 2:
                    User userToLogin = new User(name, password,
                            UserServiceUtil.hashPassword(password),
                            new ArrayList<>(), UUID.randomUUID().toString());
                    try {
                        userBookingSerivce = new UserBookingSerivce(userToLogin);
                    } catch (IOException e) {
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Fetching your bookings");
                    userBookingSerivce.fetchBooking();
                    break;
                case 4:
                    System.out.println("Type your source station");
                    String source = scanner.next();
                    System.out.println("Type your destination station");
                    String destination = scanner.next();

                    List<Train> trains = userBookingSerivce.getTrains(source, destination);
                    int index = 1;
                    for (Train t : trains) {
                        System.out.println(index + " Train id : " + t.getTrainId());
                        for (Map.Entry<String, String> entry : t.getStationTimes().entrySet()) {
                            System.out.println("Station " + entry.getKey() + " time: " + entry.getValue());
                        }
                    }
                    System.out.println("Select a train by typing 1,2,3...");
                    Train trainSelectedForBooking = trains.get(scanner.nextInt());
                    break;
                case 5:
                    System.out.println("Select a seat out of these seats");
                    List<List<Integer>> seats = userBookingSerivce.fetchSeats(trainSelectedForBooking);
                    for (List<Integer> row : seats) {
                        for (Integer val : row) {
                            System.out.println(val + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("Select the seat by typing the row and column");
                    System.out.println("Enter thw row");
                    int row = scanner.nextInt();
                    System.out.println("Enter the column");
                    int col = scanner.nextInt();
                    System.out.println("Booking your seat...");
                    Boolean booked = userBookingSerivce.bookTrainSeat(trainSelectedForBooking, row, col);
                    if (booked.equals(Boolean.TRUE)) {
                        System.out.println("Booked!");
                    } else {
                        System.out.println("Can't book this seat");
                    }
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
                    break;
            }
        }
    }
}
