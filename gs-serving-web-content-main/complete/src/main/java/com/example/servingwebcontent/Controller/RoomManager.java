import java.util.ArrayList;
import java.util.List;
import com.example.servingwebcontent.Data.Room;

public class RoomManager {
    private List<Room> roomList = new ArrayList<>();

    public void addRoom(Room room) {
        roomList.add(room);
        System.out.println("Đã thêm phòng: " + room);
    }

    public void showAllRooms() {
        System.out.println("Danh sách các phòng:");
        if (roomList.isEmpty()) {
            System.out.println("Không có phòng nào.");
        } else {
            for (Room room : roomList) {
                System.out.println(room);
            }
        }
    }

    public Room findRoomById(int id) {
        for (Room room : roomList) {
            if (room.id == id) {
                return room;
            }
        }
        return null;
    }

    public boolean updateRoom(int id, String type, double price, Boolean status) {
        Room room = findRoomById(id);
        if (room != null) {
            room.type = type != null ? type : room.type;
            room.price = price >= 0 ? price : room.price;
            room.status = status != null ? status : room.status;
            System.out.println("Đã cập nhật phòng có ID " + id);
            return true;
        } else {
            System.out.println("Không tìm thấy phòng có ID " + id);
            return false;
        }
    }

    // DELETE - Delete a room
    public boolean deleteRoom(int id) {
        Room room = findRoomById(id);
        if (room != null) {
            roomList.remove(room);
            System.out.println("Đã xoá phòng có ID " + id);
            return true;
        } else {
            System.out.println("Không tìm thấy phòng có ID " + id);
            return false;
        }
    }
}

