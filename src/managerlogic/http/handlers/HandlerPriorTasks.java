package managerlogic.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;

import java.io.IOException;

import static statuscode.HttpStatusCode.OK;

public class HandlerPriorTasks extends BaseHttpHandler implements HttpHandler {

    public HandlerPriorTasks(Gson gson, TaskManager manager) {
        super(gson, manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] splitPath = path.split("/");
        String method = exchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                if ("prioritized".equals(splitPath[1]) && exchange.getRequestURI().getQuery() == null) {
                    String response = gson.toJson(manager.getPrioritizedTasks());
                    sendText(exchange, OK.getDescription(), OK.getCode());
                    return;
                }
            }
        } finally {
            exchange.close();
        }
    }

}
