import java.util.ArrayList;

public class ReservationSystem {
    public ArrayList<Train> trainList = new ArrayList<>();
    
    public ReservationSystem() {
        initializeTrains();
    }

    private void initializeTrains() {
        // Updated to match the new 8-parameter constructor:
        // Train(id, destination, capacity, lowerPrice, middlePrice, upperPrice, sideLowerPrice, sideUpperPrice)
        
        Train train1 = new Train(101, "Chennai to Bangalore", 20, 1200, 1100, 1000, 1150, 950);
        Train train2 = new Train(102, "Chennai to Coimbatore", 40, 1300, 1200, 1100, 1250, 1050);
        Train train3 = new Train(103, "Chennai to Madurai", 5, 1000, 900, 800, 950, 750);
        
        trainList.add(train1);
        trainList.add(train2);
        trainList.add(train3);
    }
}
