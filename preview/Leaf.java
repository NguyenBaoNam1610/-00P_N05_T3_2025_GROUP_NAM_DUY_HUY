public class Leaf {
    int i = 0;

    Leaf increment() {
        i++;
        return this; // Leaf
    }

    void print() {
        System.out.println("i = " + i);
    }

    public static void test() {
        Leaf x = new Leaf();
        x.increment().increment().increment().increment().increment().increment().increment().increment().increment()
                .print();
    }
}