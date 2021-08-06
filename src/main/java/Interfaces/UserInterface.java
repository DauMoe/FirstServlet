package Interfaces;

import Models.User;

public interface UserInterface {
    boolean Login(String email, String pass);
    boolean createUser(String email, String pass, String username);
}
