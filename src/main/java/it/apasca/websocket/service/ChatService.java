package it.apasca.websocket.service;

import it.apasca.websocket.controller.ChatController.IncomingMessage;

// trasforma  incoming messages in outgoing messages
public interface ChatService {

    public void join(String userID , String roomTitle) throws Exception;
    public void leave(String userID , String roomTitle) throws Exception;
    public void send(IncomingMessage incomingMessage) throws Exception;

}
