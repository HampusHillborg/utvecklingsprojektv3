package Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactList implements Serializable{
        private User belongsToUser;
        private ArrayList<String> contacts;

        public ContactList(User user, ArrayList<String> contacts){
            this.belongsToUser = user;
            this.contacts = contacts;
        }

        public ArrayList<String> getContacts(){
            return contacts;
        }
    }

