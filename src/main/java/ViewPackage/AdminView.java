package ViewPackage;

import ObserverPackage.BookingObserver;
import com.ModelPackage.user.UserRole;

import java.awt.event.ActionListener;

public class AdminView implements View, BookingObserver {
    //userRole is the model
    private UserRole userRole;

    public AdminView(UserRole userRole){
        this.userRole = userRole;
    }

    @Override
    public void Display() {

    }
    @Override
    public void addInputListener(ActionListener listener){

    }
    public String getInputVal() {
        return "";
    }
    public void update(String string){

    }
    @Override
    public void cleartext(){

    }

    @Override
    public void update() {

    }
}
