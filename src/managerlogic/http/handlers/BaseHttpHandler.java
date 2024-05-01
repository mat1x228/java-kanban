package managerlogic.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static statuscode.HttpStatusCode.OK;

public class BaseHttpHandler implements HttpHandler {

    protected TaskManager manager;
    protected Gson gson;

    BaseHttpHandler(Gson gson, TaskManager manager){
        this.gson = gson;
        this.manager = manager;
    }

    public void sendText(HttpExchange exchange, String response, int code) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(code, response.getBytes(Charset.defaultCharset()).length);
            os.write(response.getBytes(Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    public void sendNotFound(HttpExchange exchange, String response, int code) throws IOException {
        if (exchange != null) {
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(code, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            System.out.println("Ресурс не был найден.");
        }
    }

    public void sendHasInteractions(HttpExchange exchange, String response, int code) throws IOException {
        if (exchange != null) {
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(code, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            System.out.println("Обновление или создание задачи пересекается.");
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }

    public String readText(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder textBuilder = new StringBuilder();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line);
            }
        } finally {
            is.close();
            reader.close();
        }

        return textBuilder.toString();
    }
}



