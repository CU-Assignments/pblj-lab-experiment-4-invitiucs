import java.util.*;

class TicketBookingSystem {
    private final boolean[] seats;
    
    public TicketBookingSystem(int numSeats) {
        this.seats = new boolean[numSeats];
    }
    
    public synchronized boolean bookSeat(int seatNumber, String userName) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            System.out.println(userName + ": Invalid seat number!");
            return false;
        }
        if (!seats[seatNumber - 1]) {
            seats[seatNumber - 1] = true;
            System.out.println(userName + " booked seat " + seatNumber);
            return true;
        } else {
            System.out.println(userName + ": Seat " + seatNumber + " is already booked!");
            return false;
        }
    }
}

class User extends Thread {
    private final TicketBookingSystem bookingSystem;
    private final int seatNumber;
    private final String userName;

    public User(TicketBookingSystem bookingSystem, int seatNumber, String userName, int priority) {
        this.bookingSystem = bookingSystem;
        this.seatNumber = seatNumber;
        this.userName = userName;
        this.setPriority(priority);
    }

    @Override
    public void run() {
        bookingSystem.bookSeat(seatNumber, userName);
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        TicketBookingSystem bookingSystem = new TicketBookingSystem(5);
        
        List<User> users = new ArrayList<>();
        users.add(new User(bookingSystem, 1, "Anish (VIP)", Thread.MAX_PRIORITY));
        users.add(new User(bookingSystem, 2, "Bobby (Regular)", Thread.NORM_PRIORITY));
        users.add(new User(bookingSystem, 3, "Charlie (VIP)", Thread.MAX_PRIORITY));
        users.add(new User(bookingSystem, 4, "Bobby (Regular)", Thread.MIN_PRIORITY));
        users.add(new User(bookingSystem, 4, "Anish (VIP)", Thread.MAX_PRIORITY));
        users.add(new User(bookingSystem, 1, "Bobby (Regular)", Thread.NORM_PRIORITY));
        users.add(new User(bookingSystem, 3, "New User (Regular)", Thread.NORM_PRIORITY));
        users.add(new User(bookingSystem, 6, "User (Out of range)", Thread.NORM_PRIORITY));
        
        for (User user : users) {
            user.start();
        }
        
        for (User user : users) {
            try {
                user.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
