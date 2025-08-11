package com.example.servingwebcontent.Controller;
// File: CustomerProgram.java
import java.util.ArrayList;
import java.util.List;
import com.example.servingwebcontent.Model.Customer;


//import com.example.servingwebcontent.Data.Customer;

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
