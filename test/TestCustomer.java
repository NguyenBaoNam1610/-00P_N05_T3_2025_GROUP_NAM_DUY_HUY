package test;

import source.Customer;

public class TestCustomer {
    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.id = 1;
        customer.name = "Nguyễn Văn A";
        customer.age = 30;
        customer.address = "Hà Nội";
        customer.cccd = "1234567890";
        customer.sdt = "0987654321";

        System.out.println("Thông tin khách hàng:");
        System.out.println("Tên: " + customer.name);
        System.out.println("SĐT: " + customer.sdt);
        System.out.println("Địa chỉ: " + customer.address);
        System.out.println("CCCD: " + customer.cccd);
    }
}
