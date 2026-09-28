interface Add1 {
    void add(int a, int b);

}

public class Add {
    public static void main(String[] args) {
        Add1 add = (a, b) -> {System.out.println("The sum is: " + (a + b));
            System.out.println("The Sub is: " + (a - b));
            System.out.println("The Mul is: " + (a * b));
            System.out.println("The Div is: " + (a / b));
            System.out.println("The Rem is: " + (a % b));


        };
        add.add(10, 5);
    }

}
