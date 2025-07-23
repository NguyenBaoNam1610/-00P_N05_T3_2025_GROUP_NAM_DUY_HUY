import java.util.Objects;

public class Student implements Comparable<Student> {
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

    @Override
    public int compareTo(Student other) {
        // So sánh GPA theo thứ tự giảm dần
        int gpaComparison = Float.compare(other.gpa, this.gpa);
        if (gpaComparison != 0) {
            return gpaComparison;
        }
        // Nếu GPA bằng nhau, so sánh theo tên theo thứ tự tăng dần
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Float.compare(student.gpa, gpa) == 0 && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gpa);
    }

    @Override
    public String toString() {
        return name + " - " + gpa;
    }
}
