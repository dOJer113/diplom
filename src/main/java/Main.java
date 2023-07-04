import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static final String Dir = "pdfs";

    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File(Dir));

        try (ServerSocket serverSocket = new ServerSocket(8989)) {

            System.out.println("Сервер запущен");

            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    String str = in.readLine();
                    System.out.println(str);

                    GsonBuilder builder = new GsonBuilder();
                    out.write(builder.setPrettyPrinting().create().toJson(engine.search(str)));
                    System.out.println(builder.setPrettyPrinting().create().toJson(engine.search(str)));


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (IOException e) {
            System.out.println("Не получилось запустить сервер ");
            e.printStackTrace();
        }

    }
}