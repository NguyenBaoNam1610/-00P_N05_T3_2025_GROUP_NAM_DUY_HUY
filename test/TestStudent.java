import java.util.*;

public class TestStudent {
    public static void main(String[] args) {
        // Creating Student objects
        Student s1 = new Student("Fred", 3.0F);
        Student s2 = new Student("Sam", 3.5F);
        Student s3 = new Student("Steve", 2.1F);

        // Comparison to check GPA
        if (s3.compareTo(s2) < 0) {
            System.out.println(s3.getName() + " has a lower GPA than " + s2.getName());
        }

        // TreeSet to store students (automatically sorts by GPA due to Comparable)
        Set<Student> studentSet = new TreeSet<>();
        studentSet.add(s1);
        studentSet.add(s2);
        studentSet.add(s3);

        // Iterator to access elements of the TreeSet
        Iterator<Student> i = studentSet.iterator();

        // Iterating over the TreeSet to print names of students
        while (i.hasNext()) {
            System.out.println(i.next().getName());
        }
    }
}

// Student class implementing Comparable to enable sorting by GPA
class Student implements Comparable<Student> {
    private String name;
    private float gpa;

    public Student(String name, float gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public float getGpa() {
        return gpa;
    }

    // Implementing compareTo method for sorting by GPA
    @Override
    public int compareTo(Student other) {
        return Float.compare(this.gpa, other.gpa);
    }
}
