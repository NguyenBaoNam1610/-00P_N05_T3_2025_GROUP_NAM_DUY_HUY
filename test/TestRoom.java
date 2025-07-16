
public class TestRoom {
    public static void test() {
        RoomManager manager = new RoomManager();

        // add room
        manager.addRoom(new Room(1, "Single", 100, true));
        manager.addRoom(new Room(2, "Double", 150, true));
        manager.addRoom(new Room(3, "Suite", 300, false));

        // Hiển thị tất cả các phòng
        manager.showAllRooms();

        // update
        manager.updateRoom(2, "Deluxe", 180, true);

        // delete
        manager.deleteRoom(3);

        // Hiển thị lại tất cả các phòng
        manager.showAllRooms();
    }
}
