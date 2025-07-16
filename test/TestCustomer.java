

public class TestCustomer {
        public static void test() {
        CustomerManager manager = new CustomerManager();
        
        // Thêm khách hàng
        manager.addCustomer("Nguyễn Văn A", (byte)25, "Hà Nội", "123456789", "0987654321");
        manager.addCustomer("Trần Thị B", (byte)30, "TP.HCM", "987654321", "0123456789");
        
        // Hiển thị tất cả
        manager.displayAllCustomers();
        
        // Cập nhật thông tin
        manager.updateCustomer(1, "Nguyễn Văn A Updated", (byte)26, "Hà Nội Updated", "123456789", "0987654321");
        
        // Xóa khách hàng
        manager.deleteCustomer(2);
        
        // Hiển thị lại sau khi thay đổi
        System.out.println("\nSau khi cập nhật và xóa:");
        manager.displayAllCustomers();
    }
}
