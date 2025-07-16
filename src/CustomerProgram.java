// File: CustomerProgram.java
import java.util.ArrayList;
import java.util.List;

class Customer {
    private int id;
    private String name;
    private int age;
    private String address;
    private String cccd;
    private String sdt;

    public Customer(int id, String name, int age, String address, String cccd, String sdt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.cccd = cccd;
        this.sdt = sdt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(byte age) { this.age = age; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
}

class CustomerManager {
    private List<Customer> customers = new ArrayList<>();
    private int nextId = 1;

    // CREATE
    public void addCustomer(String name, int age, String address, String cccd, String sdt) {
        Customer customer = new Customer(nextId++, name, age, address, cccd, sdt);
        customers.add(customer);
        System.out.println("Thêm khách hàng thành công!");
    }

    // READ (all)
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("Không có khách hàng nào!");
            return;
        }
        
        System.out.println("Danh sách khách hàng:");
        for (Customer c : customers) {
            System.out.println(c.getId() + " - " + c.getName() + " - " + c.getAge() + 
                             " - " + c.getAddress() + " - " + c.getCccd() + " - " + c.getSdt());
        }
    }

    // READ (by id)
    public Customer getCustomerById(int id) {
        for (Customer c : customers) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // UPDATE
    public boolean updateCustomer(int id, String name, byte age, String address, String cccd, String sdt) {
        Customer customer = getCustomerById(id);
        if (customer != null) {
            customer.setName(name);
            customer.setAge(age);
            customer.setAddress(address);
            customer.setCccd(cccd);
            customer.setSdt(sdt);
            return true;
        }
        return false;
    }

    // DELETE
    public boolean deleteCustomer(int id) {
        Customer customer = getCustomerById(id);
        if (customer != null) {
            customers.remove(customer);
            return true;
        }
        return false;
    }
}
