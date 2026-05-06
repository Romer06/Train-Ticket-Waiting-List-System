import java.util.Scanner;

public class TrainReservationTest {
    public static void main(String[] args) {
        ReservationSystem system = new ReservationSystem();
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("\n---- Welcome to Train Ticket Waiting List System ----");

        do {
            System.out.println("\n1. View All Trains");
            System.out.println("2. Book a Seat");
            System.out.println("3. Search Ticket by ID");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = Integer.valueOf(sc.nextLine());

            switch (choice) {
                case 1:
                    for (Train t : system.trainList) {
                        System.out.println("\nTrain ID: " + t.getTrainId());
                        t.getTrainDetails();
                    }
                    break;

                case 2:
                    System.out.print("Enter Passenger Name: ");
                    String pName = sc.nextLine();
                    System.out.print("Enter Passenger Age: ");
                    int pAge = Integer.valueOf(sc.nextLine());
                    System.out.print("Enter Gender (Male/Female): ");
                    String pGender = sc.nextLine();
                    System.out.print("Enter Train ID: ");
                    int id = Integer.valueOf(sc.nextLine());

                    boolean trainFound = false;
                    for (Train t : system.trainList) {
                        if (id == t.getTrainId()) {
                            t.displayLayout();
                            System.out.println("\n--- Choose Your Berth ---");
                            System.out.println("1. Lower  2. Middle  3. Upper  4. Side Lower  5. Side Upper");
                            System.out.print("Selection: ");
                            int deckChoice = Integer.valueOf(sc.nextLine());

                            System.out.print("Enter Seat Number: ");
                            int seatNum = Integer.valueOf(sc.nextLine());

                            t.bookSeat(seatNum, deckChoice, pName, pAge, pGender);
                            trainFound = true;
                            break;
                        }
                    }
                    if (!trainFound) {
                        System.out.println("Train not found :(");
                    }
                    break;

                case 3:
                    System.out.print("Enter the Ticket ID: ");
                    String pId = sc.nextLine();
                    boolean globallyFound = false;
                    for (Train t : system.trainList) {
                        Passenger foundPerson = t.searchByTicketId(pId);
                        if (foundPerson != null) {
                            System.out.println("--- Ticket Found! ---");
                            System.out.println("Train ID: " + t.getTrainId());
                            foundPerson.getDetails();
                            globallyFound = true;
                            break;
                        }
                    }
                    if (!globallyFound) {
                        System.out.println("No passenger found with ID: " + pId);
                    }
                    break;

                case 4:
                    System.out.print("Enter the Ticket ID to cancel: ");
                    String cancelId = sc.nextLine();
                    boolean cancelDone = false;

                    for (Train t : system.trainList) {
                        // This calls your boolean method that handles FIFO waiting list logic
                        if (t.cancelByOnlyId(cancelId)) {
                            cancelDone = true;
                            break;
                        }
                    }
                    if (!cancelDone) {
                        System.out.println("Could not find a confirmed ticket with ID: " + cancelId);
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using the system! :)");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);
    }
}