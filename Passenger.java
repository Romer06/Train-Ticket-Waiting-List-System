public class Passenger {
    private String name;
    private int age;
    private String gender;
    private String ticketId;

    public Passenger(String name, int age, String gender, String ticketId){
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.ticketId=ticketId;
    }
    public void getDetails(){
         System.out.println("Name: "+name);
         System.out.println("Age: "+age);
         System.out.println("Gender: "+gender);
         System.out.println("Ticket ID: "+ticketId);
    }

    public String getTicketId(){
        return ticketId;
    }

    public String getGender(){
        return gender;
    }

    public String getName(){
        return name;
    }



}
