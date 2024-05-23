package fram;

import shiyan.TCPClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame{
	private JPanel pane;
    private JTextField text;
    private JPasswordField password;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    login frame = new login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    
    public login() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(500, 250, 450, 300);
        pane = new JPanel();
        pane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(pane);
        pane.setLayout(null);

        JLabel lblNewLabel = new JLabel("ATM系统");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setBounds(180, 30, 170, 29);
        pane.add(lblNewLabel);

        JLabel label = new JLabel("卡  号：");
        label.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        label.setBounds(98, 106, 66, 19);
        pane.add(label);

        text = new JTextField();
        text.setBounds(171, 103, 132, 26);
        pane.add(text);
        text.setColumns(10);

        JLabel label_1 = new JLabel("密  码：");
        label_1.setBounds(98, 137, 61, 16);
        label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

        pane.add(label_1);

        password = new JPasswordField();
        password.setBounds(171, 132, 132, 26);
        pane.add(password);

        JButton button = new JButton("登录");
        button.setBounds(180, 192, 99, 29);
        pane.add(button);

      //  JButton button_1 = new JButton("注册");
      //  button_1.setBounds(253, 192, 99, 29);
     //   pane.add(button_1);
        TCPClient.TCP();
      

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = text.getText();
                String ps = String.valueOf(password.getPassword());
                
                TCPClient.setout(id);
                TCPClient.trs1();   
                if("500 AUTH REQUIRE".equals(TCPClient.getin())) {
                	TCPClient.setout(ps);
                    TCPClient.trs2();
                    if("525 OK!".equals(TCPClient.getin())) {
                        new withdraw().mainframe();
                    	}    
                    else {
                    	JOptionPane.showMessageDialog(null,"密码错误");  
                    }
                }
                
                else {
                	JOptionPane.showMessageDialog(null,"用户名错误");    
                }                         
             }
        });
   
    //    button_1.addActionListener(new ActionListener() {
     //       @Override
     //       public void actionPerformed(ActionEvent e) {
    //            setVisible(false);
    //            new Register().result();
     //      }
   //     });
         
    }
        

}
