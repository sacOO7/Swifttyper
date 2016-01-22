package sample;

import javafx.scene.control.TextField;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by SAC on 18-08-2015.
 */
@ServerEndpoint(value = "/echo")
public class ServerWebsocket {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    public static TextField input;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        logger.info("Connected ... " + session.getId());
        session.getBasicRemote().sendText("Give me Text");
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        input.setText(message);
        return "";
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }
}