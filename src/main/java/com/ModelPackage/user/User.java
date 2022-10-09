package com.ModelPackage.user;

import ControllerPackage.UserController;
import ViewPackage.AdministratorView;
import ViewPackage.CustomerView;
import com.ModelPackage.api.APIManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Objects;

public class User {
    private String id;
    private String givenName;
    private String familyName;
    private String username;
    private String phoneNumber;




    public User(String usernameGiven) throws IOException, InterruptedException, JSONException {
        APIManager users = new APIManager();
        HttpResponse<String> response = users.getUsers();
        JSONArray userArray = new JSONArray(response.body());
        //UserRole userRole;
        //for loop to populate the instance variables
        for (int i=0;i<userArray.length();++i){
            JSONObject user = userArray.getJSONObject(i);
            String arrayUsername = user.getString("userName");
            if (Objects.equals(arrayUsername, usernameGiven)){
                id = user.getString("id");
                givenName = user.getString("givenName");
                familyName = user.getString("phoneNumber");
                username = usernameGiven;
                phoneNumber = user.getString("phoneNumber");
                //give a role to user
                if (user.getBoolean("isCustomer")){
                    Customer userRole = new Customer();
                    CustomerView view = new CustomerView(userRole);
                    UserController userController = new UserController(view, userRole);

                    userRole.roleAction(this.givenName,this.id);
                }
                else if (user.getBoolean("isReceptionist")){
                    //if condition to check which view the administrator(receptionist/admin) wants to view
                    //take in user input
                    //based on that, create a view
                    Administrator userRole = new Administrator();
                    AdministratorView view = new AdministratorView(userRole);
                    UserController userController = new UserController(view, userRole);
                    userRole.roleAction(this.givenName,this.id);

                    //AdminView view = new AdminView();
                    //Admin code here


                }
                else if (user.getBoolean("isHealthcareWorker")){
                    Administerer userRole = new Administerer();
                    userRole.roleAction(this.givenName,this.id);
                }
                break;
            }
        }
    }


 
}
