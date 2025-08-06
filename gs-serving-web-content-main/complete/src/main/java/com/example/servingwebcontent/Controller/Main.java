import java.util.List;

public class Main {
    CustomerManager customerManager = new CustomerManager();
    RoomManager roomManager = new RoomManager();
    BookingService bookingService = new BookingService();

    // Đặt phòng cho khách
    public boolean bookRoomForCustomer(int customerId, int roomId) {
        Customer c = customerManager.getCustomerById(customerId);
        Room r = roomManager.findRoomById(roomId);
        if (c == null) {
            System.out.println("Không tìm thấy khách hàng.");
            return false;
        }
        if (r == null || !r.status) {
            System.out.println("Phòng không hợp lệ hoặc đã được đặt.");
            return false;
        }
        bookingService.createBooking(new Booking(customerId, roomId));
        r.status = false;
        System.out.println("Đặt phòng thành công cho khách hàng " + c.getName());
        return true;
    }

    public void showAllBookings() {
        List<Booking> bookings = new BookingService().getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("Chưa có booking nào.");
            return;
        }
        for (Booking b : bookings) {
            Customer c = customerManager.getCustomerById(b.C_id);
            Room r = roomManager.findRoomById(b.R_id);
            System.out.println("Khách:" + (c != null ? c.getName() : "Không tìm thấy") +
                    ", Phòng: " + (r != null ? r.type : "Không tìm thấy"));
        }
    }
    public boolean cancelBookingBycustomer(int customerId) {
    Booking b = bookingService.getBookingByCustomerId(customerId);
    if (b == null) {
        System.out.println("Khách hàng chưa có booking.");
        return false;
    }
    Room r = roomManager.findRoomById(b.R_id);

    if (bookingService.deleteBooking(customerId)) {
        if (r != null) {
            r.status = true;
        }
        System.out.println("Đã hủy booking của khách hàng ID: " + customerId);
        return true;
    }

    System.out.println("Không thể hủy booking của khách hàng ID: " + customerId);
    return false;
}

}