import edu.princeton.cs.algs4.StdOut;

public class HelloGoodbye {
    public static void main(String[] args) {
        if (args.length != 0) {
            String firstName = args[0], secondName = args[1];
            StdOut.println("Hello " + firstName + " and " + secondName);
            StdOut.println("Goodbye " + secondName + " and " + firstName);
        }
    }
}
