package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;
import websocket.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    // map of int to connection
    public final ConcurrentHashMap<Integer, ArrayList<Connection>> connectionsMap = new ConcurrentHashMap<>();


    public void add(Integer gameID,String visitorName, Session session) {
        var connection = new Connection(visitorName, session);
        if (connectionsMap.get(gameID) == null){
            ArrayList<Connection> connectionList = new ArrayList<>();
            connectionList.add(connection);
            connectionsMap.put(gameID,connectionList);
        }
        else {
            connectionsMap.get(gameID).add(connection);
        }
    }

    public void remove(Integer gameID) {
        connectionsMap.remove(gameID);
    }

    public void broadcast(Integer gameID, String excludeVisitorName, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        if (connectionsMap.get(gameID) != null) {
            for (var c : connectionsMap.get(gameID)) {
                if (c.session.isOpen()) {
                    if (!c.visitorName.equals(excludeVisitorName)) {
                        c.send(new Gson().toJson(notification));
                    }
                } else {
                    removeList.add(c);
                }
            }

            // Clean up any connections that were left open.
            for (var c : removeList) {
                connectionsMap.get(gameID).remove(c);
            }
        }
    }
}