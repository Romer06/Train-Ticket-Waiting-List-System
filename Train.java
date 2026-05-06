import java.util.LinkedList;
import java.util.Queue;

public class Train{
    private int trainId;
    private int totalCapacity;
    private int availableSeats;
    private Passenger[] lower;
    private Passenger[] middle;
    private Passenger[] upper;
    private Passenger[] sideLower;
    private Passenger[] sideUpper;
    private double lowerPrice;
    private double upperPrice;
    private double middlePrice;
    private double sideLowerPrice;
    private double sideUpperPrice;
    private String destination;
    private Queue<Passenger> waitingList = new LinkedList<>(); 

public Train(int busId, String destination, int cap, double lp, double mp, double up, double slp, double sup){
    this.trainId=busId;
    this.destination=destination;
    this.totalCapacity=cap;
    this.lowerPrice = lp;
    this.middlePrice = mp;
    this.upperPrice = up;
    this.sideLowerPrice = slp;
    this.sideUpperPrice = sup;
    this.availableSeats = cap;

    
    int size = cap /5;
    this.lower = new Passenger[size];
    this.middle = new Passenger[size];
    this.upper = new Passenger[size];
    this.sideLower = new Passenger[size];
    this.sideUpper = new Passenger[size];
}

public int getTrainId(){
    return trainId;
}
public void getTrainDetails(){
    System.out.println("Route: "+ destination);
    System.out.println("Total Capacity: "+ totalCapacity);
    System.out.println("Seats Left: "+ availableSeats);


}

public void setTrainId(int trainId){
    this.trainId = trainId;
}

public void displayLayout() {
    System.out.println("\n======= TRAIN SEAT LAYOUT =======");
    
    // Call the helper for each of the 5 sections
    printSection("LOWER BERTH", lower, lowerPrice);
    printSection("MIDDLE BERTH", middle, middlePrice);
    printSection("UPPER BERTH", upper, upperPrice);
    printSection("SIDE LOWER", sideLower, sideLowerPrice);
    printSection("SIDE UPPER", sideUpper, sideUpperPrice);

    // Show Waiting List Status
    System.out.println("\n---------------------------------");
    System.out.println("Available Seats: " + availableSeats);
    System.out.println("Waiting List Count: " + waitingList.size());
    System.out.println("=================================");
}

// Helper method to avoid repeating the same loop logic 5 times
private void printSection(String sectionName, Passenger[] section, double price) {
    System.out.println("\n--- " + sectionName + " ---");
    System.out.println("Price: ₹" + price);
    
    for (int i = 0; i < section.length; i++) {
        if (section[i] == null) {
            // Seat is empty, show the number
            System.out.print("[" + (i + 1) + "] ");
        } else {
            // Seat is taken, show Gender-Sold
            String genderIcon = section[i].getGender().equalsIgnoreCase("Male") ? "M" : "F";
            System.out.print("[" + genderIcon + "-Sold] ");
        }

        // New line every 2 seats for a clean grid look[cite: 1]
        if ((i + 1) % 2 == 0) {
            System.out.println();
        }
    }
    System.out.println(); 
}

public void bookSeat(int seatNumber, int deckChoice, String name, int age, String gender) {
    int index = seatNumber - 1;
    String tId = "TICK" + (int)(Math.random() * 1000);
    Passenger p = new Passenger(name, age, gender, tId);

    // 1. Check if the entire train is full (Waiting List Logic)
    if (availableSeats == 0) {
        System.out.println("Train is FULL! Adding " + name + " to the Waiting List....");
        System.out.println("Your Ticket ID is: " + tId + ". You'll be moved to a seat automatically upon cancellation.");
        waitingList.add(p); // FIFO: Added to the end of the queue
        return;
    }

    // 2. Point to the correct array based on user choice
    Passenger[] selectedDeck;
    double selectedPrice;

    switch (deckChoice) {
        case 1:
            selectedDeck = lower;
            selectedPrice = lowerPrice;
            break;
        case 2:
            selectedDeck = middle;
            selectedPrice = middlePrice;
            break;
        case 3:
            selectedDeck = upper;
            selectedPrice = upperPrice;
            break;
        case 4:
            selectedDeck = sideLower;
            selectedPrice = sideLowerPrice;
            break;
        case 5:
            selectedDeck = sideUpper;
            selectedPrice = sideUpperPrice;
            break;
        default:
            System.out.println("Invalid deck choice! :(");
            return;
    }

    // 3. Perform the booking logic on the 'selectedDeck'
    if (index < 0 || index >= selectedDeck.length) {
        System.out.println("Invalid seat number for this section! :(");
    } else if (selectedDeck[index] != null) {
        // If the specific seat is taken, we also add them to the Waiting List
        System.out.println("Seat already booked. Adding " + name + " to the Waiting List...");
        waitingList.add(p);
        System.out.println("Your Ticket ID is: " + tId);
    } else {
        // Successful booking
        selectedDeck[index] = p;
        availableSeats--;
        System.out.println("Seat " + seatNumber + " booked successfully! :)");
        System.out.println("Section Price: ₹" + selectedPrice);
        System.out.println("Your Ticket ID is: " + tId + ", Bon voyage!");
    }
}

public boolean cancelByOnlyId(String ticketId) {
    // We group all 5 arrays into a 2D array to search them all efficiently
    Passenger[][] allSections = {lower, middle, upper, sideLower, sideUpper};

    for (Passenger[] section : allSections) {
        for (int i = 0; i < section.length; i++) {
            if (section[i] != null && section[i].getTicketId().equalsIgnoreCase(ticketId)) {
                processCancellation(section, i); // Our helper handles the FIFO logic
                return true; // Found and cancelled
            }
        }
    }
    return false; // Not found in any section
}

private void processCancellation(Passenger[] deck, int index) {
    System.out.println("Cancelling ticket for: " + deck[index].getName());
    
    // 1. Clear the confirmed seat
    deck[index] = null;
    availableSeats++;

    // 2. Check the Waiting List (FIFO principle)[cite: 1]
    if (!waitingList.isEmpty()) {
        // 3. poll() gets the first person who was queued[cite: 1]
        Passenger nextPerson = waitingList.poll(); 
        
        // Move them into the now-empty confirmed seat[cite: 1]
        deck[index] = nextPerson;
        availableSeats--; 
        
        System.out.println("Waitlist Update: " + nextPerson.getName() + " is now CONFIRMED for this seat!");
    } else {
        System.out.println("Seat is now empty. No passengers in waiting list.");
    }
}

public Passenger searchByTicketId(String ticketId) {
    // Array of names to match our arrays for clear printing[cite: 1]
    String[] sectionNames = {"Lower", "Middle", "Upper", "Side Lower", "Side Upper"};
    Passenger[][] allSections = {lower, middle, upper, sideLower, sideUpper};

    for (int s = 0; s < allSections.length; s++) {
        Passenger[] section = allSections[s];
        for (int i = 0; i < section.length; i++) {
            if (section[i] != null && section[i].getTicketId().equalsIgnoreCase(ticketId)) {
                System.out.println("\nLocation: " + sectionNames[s] + " Berth, Seat No: " + (i + 1));
                return section[i];
            }
        }
    }
    return null; // Not found in the entire train[cite: 1]
}
}