public class SathyaImpl implements Sathya{
    @Override
    public void display() {
        System.out.print("Hello, I am Sathya!");

    }

    public static void main(String[] args) {
        Sathya s = new SathyaImpl();
        s.display();
    }
}
