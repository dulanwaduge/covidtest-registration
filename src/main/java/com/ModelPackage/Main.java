package com.ModelPackage;

import com.ModelPackage.login.Login;
import com.ModelPackage.TestingSitePackage.TestingSiteCollection;
import com.ModelPackage.user.User;
import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws JSONException, IOException, InterruptedException {
        //mvc will NOT be applied to the main class, as it is acting as our driver aswell.


        System.out.println("\n*********************************************************");
        System.out.println("*********  COVID-19 TEST REGISTRATION SYSTEM  ***********\n");
        System.out.println("1. Login to System");
        System.out.println("2. Search for Testing Sites");

        Scanner sc = new Scanner(System.in);
        boolean isWrongAnswer;
        String successfulLogin = "";
        do {
            isWrongAnswer = false;
            System.out.println("\nMake a selection: ");
            switch (sc.nextInt()) {
                case 1:
                    successfulLogin = Login.login();
                    break;
                case 2:
                    TestingSiteCollection.getInstance().search();
                    break;
                default:
                    System.out.println("choose from 1 to 2");
                    isWrongAnswer = true;
            }
        } while (isWrongAnswer);

        if (!Objects.equals(successfulLogin, "")) {
            //create a local user object for the user
            User newUser = new User(successfulLogin);


        }
    }
}
