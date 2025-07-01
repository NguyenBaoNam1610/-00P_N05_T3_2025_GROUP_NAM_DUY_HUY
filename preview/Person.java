public class Person {
    private String name; 
    // Getter

    public String getName() {
        return name;
    }

    // Setter
    public void setName(String newName) {
        this.name = newName;
    }

    public static void test(){
        Person p = new Person();
        p.setName("duy");
        System.out.println(p.name);
    }
}