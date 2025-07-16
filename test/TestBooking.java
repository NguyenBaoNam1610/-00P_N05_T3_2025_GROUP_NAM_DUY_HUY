
public class TestBooking {
    public static void test() {
        BookingService book = new BookingService();

        //create booking
        book.createBooking(new Booking(1, 5));
        book.createBooking(new Booking(3,   6));

        //read
        System.out.println(book.getBookingByCustomerId(3));

        //update
        book.updateBooking(1, new Booking(2, 4));
        System.out.println("Sau khi update c_id = 2, r_id = 4");
        book.displayBooking();

        //detele
        book.deleteBooking(3);
        System.out.println("Sau khi xóa booking có c_id = 3");
        book.displayBooking();

    }
}
