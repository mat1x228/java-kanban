package managerlogic.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;

import java.io.IOException;

import static statuscode.HttpStatusCode.INTERNAL_SERVER_ERROR;
import static statuscode.HttpStatusCode.OK;

public class HandlerHistory extends BaseHttpHandler implements HttpHandler {

    public HandlerHistory(Gson gson, TaskManager manager) {
        super(gson, manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] splitPath = path.split("/");
        String method = exchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                if (splitPath.length > 1 && "history".equals(splitPath[1])) {
                    if (exchange.getRequestURI().getQuery() == null) {
                        String response = gson.toJson(manager.getHistory());
                        sendText(exchange, response, OK.getCode());
                        return;
                    }
                }
            }
        } catch (Exception e) {
            sendText(exchange, INTERNAL_SERVER_ERROR.getDescription(), INTERNAL_SERVER_ERROR.getCode());
        } finally {
            exchange.close();
        }
    }
}
