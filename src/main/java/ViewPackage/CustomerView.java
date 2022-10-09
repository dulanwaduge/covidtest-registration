package ViewPackage;

import com.ModelPackage.user.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerView implements View{
    //userRole is the model
    private UserRole userRole;

    private JLabel label;
    private JLabel entryLabel;
    private JTextField inputField;
    private JFrame frame;
    private JPanel panel;


    public CustomerView(UserRole userRole){
        this.userRole = userRole;
        this.Display();
    }

    @Override
    public void Display() {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(600,300));

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(this.getClass().getSimpleName());
        frame.pack();
        //The options and the selection on the GUI

        //label = new JLabel("\n 1. Check Booking \n 2. Search Sites to Book \n 3. Profile \n");
        label = new JLabel("<html> 1. Check Booking <br/> 2. Search Sites to Book <br/> 3. Profile <br/><html>");
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.weightx=0.5;
        constraints.weighty=0.5;
        panel.add(label,constraints);

        entryLabel = new JLabel("Enter Selection: ");
        constraints.gridx=0;
        constraints.gridy=2;
        panel.add(entryLabel,constraints);

        inputField = new JTextField(32);
        constraints.gridx=1;
        constraints.gridy=2;
        panel.add(inputField,constraints);

        frame.setVisible(true);

    }
    @Override
    public void addInputListener(ActionListener listener){
        inputField.addActionListener(listener);
    }

    public String getInputVal() { return inputField.getText(); }

    @Override
    public void update(String string){
        label.setText(string);
    }
    @Override
    public void cleartext(){
        inputField.setText("");
    }

}
