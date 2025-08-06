class Room {
    int id;
    String type;
    double price;
    boolean status;

    public Room(int id, String type, double price, boolean status) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room{id=" + id + ", type='" + type + "', price=" + price + ", status=" + (status ? "Available" : "Not Available") + "}";
    }
}