package ViewPackage;

import com.ModelPackage.user.UserRole;

import java.awt.event.ActionListener;

public class AdministratorView implements View {
    //userRole is the model
    private UserRole userRole;

    public AdministratorView(UserRole userRole){
        this.userRole = userRole;
    }

    @Override
    public void Display() {

    }
    @Override
    public void addInputListener(ActionListener listener){

    }
    @Override
    public String getInputVal() {
        return "";
    }
    public void update(String string){

    }
    @Override
    public void cleartext(){

    }
}
