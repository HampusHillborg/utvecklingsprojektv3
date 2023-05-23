package Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class StoredMessages {

    private HashMap<User, ArrayList<Message>> storedMessages;

    public StoredMessages(){
        this.storedMessages = new HashMap<>();
    }

    public synchronized void put(User user, Message message){
        if(storedMessages.get(user) == null){
            ArrayList<Message> storedMessage = new ArrayList<>();
            storedMessages.put(user, storedMessage);
        }
        ArrayList<Message> messageList = storedMessages.get(user);
        messageList.add(message);
        storedMessages.replace(user, messageList);
    }

    public synchronized ArrayList<Message> get(User user){
        ArrayList<Message> unsentMsgsToUser = storedMessages.get(user);
        return unsentMsgsToUser;
    }

    public synchronized boolean contains(User user){
        return storedMessages.containsKey(user);
    }

}


