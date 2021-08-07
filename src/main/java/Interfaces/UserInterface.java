package Interfaces;

import Models.User;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public interface UserInterface {
    JSONObject Login(String email, String pass);
    boolean createUser(String email, String pass, String username);
}
