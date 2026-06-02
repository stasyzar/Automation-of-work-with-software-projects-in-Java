package ua.edu.ukma;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/cars", exchange -> {
            String response = "[\n" +
                    "  {\"id\": 1, \"brand\": \"Toyota\", \"model\": \"Camry\"},\n" +
                    "  {\"id\": 2, \"brand\": \"BMW\", \"model\": \"X5\"}\n" +
                    "]";

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        server.start();
        System.out.println("🚗 Car Rental API запущено!");
        System.out.println("http://localhost:8080/cars");
    }
}