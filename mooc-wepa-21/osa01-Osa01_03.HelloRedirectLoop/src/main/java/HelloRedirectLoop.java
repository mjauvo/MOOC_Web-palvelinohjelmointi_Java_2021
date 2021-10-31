
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HelloRedirectLoop {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        int requestCounter = 0;

        while (true) {
            // wait for a request
            Socket socket = server.accept();

            // read a request
            Scanner IS = new Scanner(socket.getInputStream());
            String request = IS.nextLine();
            System.out.println(request);

            // write a response
            PrintWriter OS = new PrintWriter(socket.getOutputStream());

            while(request != "/quit") {
                OS.println("HTTP/1.1 302 Found");
                OS.println("Location: http://localhost:8080");
                OS.println();
                OS.flush();
                requestCounter++;
            }

            OS.close();
            IS.close();
            socket.close();
            System.out.println(requestCounter);
        }
    }
}
