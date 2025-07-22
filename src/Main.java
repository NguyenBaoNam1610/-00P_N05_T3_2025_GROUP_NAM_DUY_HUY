//import java.util.Scanner;

public class Main {
    CustomerManager customerManager = new CustomerManager();
    RoomManager roomManager = new RoomManager();
    BookingService bookingService = new BookingService();

    //Đặt phòng cho khách
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
}
