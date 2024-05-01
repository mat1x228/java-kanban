package managerlogic.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import com.google.gson.Gson;
import interfaces.TaskManager;
import tasks.Task;

import static statuscode.HttpStatusCode.*;

public class HandlerTask extends BaseHttpHandler implements HttpHandler {

    public HandlerTask(Gson gson, TaskManager manager) {
        super(gson, manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] splitPath = path.split("/");
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET":
                    if (splitPath[1].equals("tasks") && exchange.getRequestURI().getQuery() == null) {
                        String response = gson.toJson(manager.getTaskStorage());
                        sendText(exchange, response, OK.getCode());
                        break;
                    } else if (splitPath[1].equals("tasks") && exchange.getRequestURI().getQuery() != null) {
                        int id = Integer.parseInt(exchange.getRequestURI().getQuery().replaceFirst("id=", ""));
                        String response = gson.toJson(manager.getTaskById(id));
                        sendText(exchange, response, OK.getCode());
                        break;
                    }

                case "POST":
                    if (splitPath[1].equals("tasks")) {
                        String requestBody = readText(exchange);
                        if (requestBody != null) {
                            Task task = gson.fromJson(requestBody, Task.class);
                            Integer taskId = task.getId();
                            if (taskId != 0) {
                                manager.updateTask(task);
                                String response = gson.toJson(task);
                                sendText(exchange, response, OK.getCode());
                            } else {
                                manager.addTask(task);
                                String response = gson.toJson(task);
                                sendText(exchange, response, CREATED.getCode());
                            }
                        } else {
                            sendText(exchange, BAD_REQUEST.getDescription(), BAD_REQUEST.getCode());
                        }
                        break;
                    }

                case "DELETE":
                    if (splitPath[1].equals("tasks") && exchange.getRequestURI().getQuery() != null) {
                        int id = Integer.parseInt(exchange.getRequestURI().getQuery().replaceFirst("id=", ""));
                        manager.removeTaskById(id);
                        sendText(exchange, "", OK.getCode());
                        break;
                    }

                default:
                    sendText(exchange, NOT_FOUND.getDescription(), NOT_FOUND.getCode());
            }
        } catch (Exception e) {
            sendText(exchange, e.getMessage(), INTERNAL_SERVER_ERROR.getCode());
        } finally {
            exchange.close();
        }
    }
}