import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        LoginForm loginForm=new LoginForm(null);
        if(loginForm.user!=null)
        {
        loginForm.setVisible(false);
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
   //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        }


    }

}
