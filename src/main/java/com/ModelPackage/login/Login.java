package com.ModelPackage.login;

import com.ModelPackage.api.APIManager;
import org.json.JSONException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

@SpringBootApplication
public class Login {

    //Method to get user's input for username
    private static String userNameInput() {
        Scanner userNameInputScanner = new Scanner(System.in);
        System.out.println("**************** Please enter your Username and Password ****************\n");
        System.out.println("Username: ");
        return userNameInputScanner.next();
    }

    //Method to get user's input for password
    private static String passwordInput() {
        Scanner passwordInputScanner = new Scanner(System.in);
        System.out.println("Password: ");
        return passwordInputScanner.next();
    }

    public static String login() throws IOException, InterruptedException, JSONException {

        //*********** Login Subsystem code *******************
        String username = userNameInput();
        APIManager login = new APIManager();
        HttpResponse<String> response = login.userLogin(username, passwordInput());

        if (response.statusCode() == 200) {
            System.out.println("Login Successful\n");
            System.out.println("Logged in as: " + username);
            return username;
        }
        else{
            System.out.println("Login Unsuccessful");
            System.out.println(response.statusCode());
            return "";
        }
    }
}
