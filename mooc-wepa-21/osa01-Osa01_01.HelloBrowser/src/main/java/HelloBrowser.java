
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HelloBrowser {

    public static void run() throws IOException {
        System.out.println("================");
        System.out.println(" THE INTERNETS!");
        System.out.println("================");
        System.out.println("\n");

        Scanner input = new Scanner(System.in);

        System.out.print("Where to? ");
        String address = input.nextLine();
        input.close();

        int port = 80;

        Socket socket = new Socket(address, port);

        // Send
        PrintWriter PW = new PrintWriter(socket.getOutputStream());
        PW.println("GET / HTTP/1.1");
        PW.println("Host: " + address);
        PW.println();
        PW.flush();

        System.out.println("==========");
        System.out.println(" RESPONSE");
        System.out.println("==========");
        System.out.println("\n");

        // Receive
        Scanner IS = new Scanner(socket.getInputStream());

        while(IS.hasNextLine()) {
            System.out.println(IS.nextLine());
        }

        PW.close();
        IS.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
