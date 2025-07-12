

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings = new ArrayList<>();

    public void createBooking(Booking booking) {
        bookings.add(booking);
        System.out.println("Booking created: " + booking);
    }

    public Booking getBookingByCustomerId(int c_id) {
        for (Booking b : bookings) {
            if (b.C_id == c_id) {
                return b;
            }
        }
        return null;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    public boolean updateBooking(int c_id, Booking newBookingData) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).C_id == c_id) {
                bookings.set(i, newBookingData);
                System.out.println("Booking updated: " + newBookingData);
                return true;
            }
        }
        return false;
    }

    public boolean deleteBooking(int c_id) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).C_id == c_id) {
                bookings.remove(i);
                System.out.println("Booking deleted for C_id=" + c_id);
                return true;
            }
        }
        return false;
    }
}