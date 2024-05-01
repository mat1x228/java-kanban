package managerlogic.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;
import tasks.SubTask;

import java.io.IOException;

import static statuscode.HttpStatusCode.*;

public class HandlerSubTask extends BaseHttpHandler implements HttpHandler {

    public HandlerSubTask(Gson gson, TaskManager manager){
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
                    if (splitPath[1].equals("subtasks") && exchange.getRequestURI().getQuery() == null) {
                        String response = gson.toJson(manager.getSubTaskStorage());
                        sendText(exchange, response, OK.getCode());
                        break;
                    } else if (splitPath[1].equals("subtasks") && exchange.getRequestURI().getQuery() != null) {
                        int id = Integer.parseInt(exchange.getRequestURI().getQuery().replaceFirst("id=", ""));
                        String response = gson.toJson(manager.getSubtaskById(id));
                        sendText(exchange, response, OK.getCode());
                        break;
                    }

                case "POST":
                    if (splitPath[1].equals("subtasks")) {
                        String requestBody = readText(exchange);
                        if (requestBody != null) {
                            SubTask subtask = gson.fromJson(requestBody, SubTask.class);
                            if (subtask.getId() != 0) {
                                manager.updateSubTask(subtask);
                            } else {
                                manager.addSubtask(subtask);
                            }
                            String response = gson.toJson(subtask);
                            sendText(exchange, response, CREATED.getCode());
                            break;
                        }
                    }

                case "DELETE":
                    if (splitPath[1].equals("subtasks") && exchange.getRequestURI().getQuery() != null) {
                        int id = Integer.parseInt(exchange.getRequestURI().getQuery().replaceFirst("id=", ""));
                        manager.removeSubtaskById(id);
                        sendText(exchange, "", OK.getCode());
                        break;
                    }
            }
        } catch (NumberFormatException e) {
            sendText(exchange, "Invalid ID format", BAD_REQUEST.getCode());
        } catch (Exception e) {
            sendText(exchange, e.getMessage(), INTERNAL_SERVER_ERROR.getCode());
        } finally {
            exchange.close();
        }
    }
}

