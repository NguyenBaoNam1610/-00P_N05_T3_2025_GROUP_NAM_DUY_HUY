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
        List<Booking> bookings = bookingsService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("Chưa có booking nào.");
            return;
        }
        for (Boonking b : bookings) {
            Customer c = customerManager.getCustomerById(b.C_id);
            Room r = roomManager.findRoomById(b.R_id);
            System.out.println("Khách:" + (c != null ? c.getName() : "Không tìm thấy") +
                    ", Phòng: " + (r != null ? r.type : "Không tìm thấy"));
        }
    }
}