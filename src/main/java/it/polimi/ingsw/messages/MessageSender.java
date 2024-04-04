package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.data.Message;

public interface MessageSender {
    public void broadcast(Message message);
}
