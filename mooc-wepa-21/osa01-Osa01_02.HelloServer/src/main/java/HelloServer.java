import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class HelloServer {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);

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
                OS.println("200");
                OS.println("/index.html");
                OS.flush();
            }

            OS.close();
            IS.close();
            socket.close();
        }
    }
}
