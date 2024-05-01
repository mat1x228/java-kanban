package managerlogic.http;

import com.google.gson.*;

import interfaces.TaskManager;
import managerlogic.InMemoryTaskManager;

import com.sun.net.httpserver.HttpServer;
import managerlogic.http.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    final private int PORT = 8080;
    private static HttpServer server;
    private final TaskManager manager;

    public HttpTaskServer(TaskManager manager) {
        this.manager = manager;
    }

    public static void main(String[] args) {
        try {
            HttpTaskServer httpTaskServer = new HttpTaskServer(new InMemoryTaskManager());
            httpTaskServer.start();
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        Gson gson = new Gson();

        server.createContext("/tasks", new HandlerTask(gson, manager));
        server.createContext("/subtasks", new HandlerSubTask(gson, manager));
        server.createContext("/epics", new HandlerEpic(gson, manager));
        server.createContext("/history", new HandlerHistory(gson, manager));
        server.createContext("/prioritized", new HandlerPriorTasks(gson, manager));

        server.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public static void stop(int delay) {
        server.stop(delay);
        System.out.println("HTTP Task Server остановлен");
    }
}