package test;

import source.Room;

public class TestRoom {
    public static void main(String[] args) {
        Room room = new Room();
        room.id = 1;
        room.type = "penhouse";
        room.price = 10000;
        room.status = true;

        System.out.println("Thông tin phòng: ");
        System.out.println("Số phòng: " + room.id);
        System.out.println("Kiểu phòng: " + room.type);
        System.out.println("Giá phòng: " + room.price);
        System.out.println("Trang thái: " + room.status);
    }
}
