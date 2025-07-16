import java.util.ArrayList;
import java.util.List;

class Booking {
    public int C_id;
    public int R_id;  

    public Booking(int c_id, int r_id) {
        this.C_id = c_id;
        this.R_id = r_id;
    }

    @Override
    public String toString() {
        return "Booking{C_id=" + C_id + ", R_id=" + R_id + "}";
    }
}

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
        for (Booking b : bookings) {
            if (b.C_id == c_id) {
                // Cập nhật dữ liệu thay vì ghi đè object
                b.R_id = newBookingData.R_id;
                System.out.println("Booking updated: " + b);
                return true;
            }
        }
        return false;
    }

    public boolean deleteBooking(int c_id) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).C_id == c_id) {
                System.out.println("Booking deleted: " + bookings.get(i));
                bookings.remove(i);
                return true;
            }
        }
        return false;
    }

    public void displayBooking(){
        if(bookings.isEmpty()){
            System.out.println("Không có booking!");
            return;
        }

        System.out.println("Danh sách booking:");
        for(Booking x : bookings){
            System.err.println(x);
        }
    }
}
