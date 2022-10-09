package ViewPackage;

import ObserverPackage.UserViewObserver;

import java.awt.event.ActionListener;

public interface View extends UserViewObserver {
    void Display();
    void addInputListener(ActionListener listener);
    String getInputVal();
    void cleartext();
}
