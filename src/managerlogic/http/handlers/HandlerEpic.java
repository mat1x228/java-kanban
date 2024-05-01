package managerlogic.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;
import tasks.Epic;

import java.io.IOException;

import static statuscode.HttpStatusCode.*;

public class HandlerEpic extends BaseHttpHandler implements HttpHandler {
    public HandlerEpic(Gson gson, TaskManager manager) {
        super(gson, manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] splitPath = path.split("/");
        String method = httpExchange.getRequestMethod();

        try {
            switch (method) {
                case "GET":
                    if (splitPath.length >= 2 && splitPath[1].equals("epics")) {
                        if (splitPath.length == 2 && httpExchange.getRequestURI().getQuery() == null) {
                            String response = gson.toJson(manager.getEpicStorage());
                            sendText(httpExchange, response, OK.getCode());
                            return;
                        } else if (splitPath.length == 2 && httpExchange.getRequestURI().getQuery() != null) {
                            int id = Integer.parseInt(httpExchange.getRequestURI().getQuery().replaceFirst("id=", ""));
                            String response = gson.toJson(manager.getEpicById(id));
                            sendText(httpExchange, response, OK.getCode());
                            return;
                        } else if (splitPath.length >= 3 && splitPath[2].equals("subtasks") && httpExchange.getRequestURI().getQuery() != null) {
                            int id = Integer.parseInt(httpExchange.getRequestURI().getQuery().replaceFirst("id=", ""));
                            String response = gson.toJson(manager.getEpicsSubtasks(id));
                            sendText(httpExchange, response, OK.getCode());
                            return;
                        }
                    }
                    break;

                case "POST":
                    if (splitPath.length >= 2 && splitPath[1].equals("epics")) {
                        String requestBody = readText(httpExchange);
                        if (requestBody != null) {
                            Epic epic = gson.fromJson(requestBody, Epic.class);
                            manager.addEpic(epic);
                            String response = gson.toJson(epic);
                            sendText(httpExchange, response, CREATED.getCode());
                            return;
                        }
                    }
                    break;

                case "DELETE":
                    if (splitPath.length >= 2 && splitPath[1].equals("epics") && httpExchange.getRequestURI().getQuery() != null) {
                        int id = Integer.parseInt(httpExchange.getRequestURI().getQuery().replaceFirst("id=", ""));
                        manager.removeEpicById(id);
                        sendText(httpExchange, "", OK.getCode());
                        return;
                    }
                    break;

                default:
                    sendText(httpExchange, NOT_FOUND.getDescription(), NOT_FOUND.getCode());
            }
        } catch (NumberFormatException e) {
            sendText(httpExchange, "Invalid ID format", BAD_REQUEST.getCode());
        } catch (Exception e) {
            sendText(httpExchange, e.getMessage(), INTERNAL_SERVER_ERROR.getCode());
        } finally {
            httpExchange.close();
        }
    }
}
