package ControllerPackage;

import ObserverPackage.UserViewObserver;
import ViewPackage.*;
import com.ModelPackage.TestingSitePackage.TestingSiteCollection;
import com.ModelPackage.user.*;
import org.json.JSONException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class UserController implements UserViewObserver {
    //View is view. userRole is model
    private View theView;
    private Customer customer;
    private Administrator administrator;


    //3 constructors, each for a different view
    public UserController(CustomerView customerView, Customer customer) {
        this.theView = customerView;
        this.customer = customer;
        //Set listener to handle on-click event of the Save button
        this.theView.addInputListener(new InputListener());
    }
    public UserController(AdministratorView administratorView, Administrator administrator) {
        this.theView = administratorView;
        this.administrator = administrator;
    }
    public UserController(AdminView adminView, Administrator admin) {
        this.theView = adminView;
        this.administrator = admin;
    }

    @Override
    public void update(String string) {

    }

    class InputListener implements ActionListener{
        String stage = "";
        public void actionPerformed(ActionEvent e) {

                String input = theView.getInputVal();
                theView.cleartext();
                //counter to count how many actions (inputs) have been performed.

                if (stage.equals("")){

                    switch (Integer.parseInt(input)){
                        case 1:

                            theView.update("Enter Pin to check booking");
                            stage = "check booking";
                            break;
                        case 2:
                            stage = "search and book";
                            try {
                                TestingSiteCollection.getInstance().search();
                            } catch (JSONException | IOException | InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            theView.update("Enter site Id to Book at TestingSitePackage: ");
                        case 3:
                    }
                }
                else if (stage.equals("check booking")){
                    try {
                        theView.update(customer.booking.checkPin(input,customer.id));
                    } catch (IOException | InterruptedException | JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                else if (stage.equals("search and book")){

                }

        }
    }

}
