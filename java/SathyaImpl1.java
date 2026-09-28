public class SathyaImpl1 {
    public static void main(String[] args) {
        Sathya s =new Sathya() {
            @Override
            public void display() {
                System.out.print("Hello, I am Sathya!");
            }
        };
        s.display();
    }
}
