package test;
import source.Booking;
import source.Customer;
public class TestBooking {
    public static void main(String[] args) {
        Booking booking = new Booking();
        booking.C_id = 1;
        booking.R_id = 3;
        booking.checkin = "25/06/2025";
        booking.checkout = "30/06/2025";

        System.out.println("Thông tin đặt phòng:");
        System.out.println("ID khách hàng: " + booking.C_id);
        System.out.println("ID phòng: " + booking.R_id);
        System.out.println("Ngày nhận phòng: " + booking.checkin);
        System.out.println("Ngày trả phòng: " + booking.checkout);
    }
}
