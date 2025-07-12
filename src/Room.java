
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private List<Room> roomList = new ArrayList<>();

    // CREATE
    public void addRoom(Room room) {
        roomList.add(room);
        System.out.println(" Đã thêm phòng: " + room);
    }

    // READ
    public void showAllRooms() {
        System.out.println(" Danh sách các phòng:");
        for (Room room : roomList) {
            System.out.println(room);
        }
    }

    public Room findRoomById(int id) {
        for (Room room : roomList) {
            if (room.id == id) return room;
        }
        return null;
    }

    // UPDATE
    public boolean updateRoom(int id, String type, double price, Boolean status) {
        Room room = findRoomById(id);
        if (room != null) {
            room.type = type;
            room.price = price;
            room.status = status;
            System.out.println(" Đã cập nhật phòng có ID " + id);
            return true;
        } else {
            System.out.println(" Không tìm thấy phòng có ID " + id);
            return false;
        }
    }

    // DELETE
    public boolean deleteRoom(int id) {
        Room room = findRoomById(id);
        if (room != null) {
            roomList.remove(room);
            System.out.println(" Đã xoá phòng có ID " + id);
            return true;
        } else {
            System.out.println(" Không tìm thấy phòng có ID " + id);
            return false;
        }
    }
}
