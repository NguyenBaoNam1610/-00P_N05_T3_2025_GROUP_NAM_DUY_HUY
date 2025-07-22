public class TestMain {
    public static void test() {
        Main mainn = new Main();

        mainn.customerManager.addCustomer("An", 25, "HN", "123456789", "0123456789");
        mainn.customerManager.addCustomer("Binh", 30, "HN", "987654321", "0987654321");

        mainn.roomManager.addRoom(new Room(1, "Single", 100, true));
        mainn.roomManager.addRoom(new Room(2, "Double", 150, true));

        mainn.bookRoomForCustomer(1, 1);
        mainn.bookRoomForCustomer(2, 2);
    }
}

