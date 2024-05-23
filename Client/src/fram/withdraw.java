package fram;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import shiyan.TCPClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class withdraw extends JFrame {
		private JPanel contentPane;
	    private JTextField text;

	    public void mainframe(){
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                	withdraw frame = new withdraw();
	                    frame.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }

	    /**
	     * Create the frame.
	     */
	    public withdraw() {
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(500, 250, 450, 300);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPane);
	        contentPane.setLayout(null);
	        setTitle("取款界面");
	        

	        JLabel label = new JLabel("请输入取款金额");
	        label.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
	        label.setForeground(Color.RED);
	        label.setBounds(151, 49, 140, 36);
	        contentPane.add(label);
	   
	        JLabel label1 = new JLabel("金  额：");
	        label1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
	        label1.setBounds(98, 106, 66, 19);
	        contentPane.add(label1);

	        text = new JTextField();
	        text.setBounds(171, 103, 132, 26);
	        contentPane.add(text);
	        text.setColumns(10);
	        
	        JButton button = new JButton("取款");
	        button.setBounds(130, 192, 90, 29);
	        contentPane.add(button);
	        
	        JButton button1 = new JButton("余额查询");
	        button1.setBounds(230, 192, 90, 29);
	        contentPane.add(button1);
	        
	        
	        
	        button1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	 TCPClient.trs3();
	            	 if(TCPClient.getin().startsWith("AMNT")) { 		 	
		                	JOptionPane.showMessageDialog(null,"余额为："+TCPClient.getin().substring(5)); 
	            	 }
	            	 else {
		                	JOptionPane.showMessageDialog(null,"数据库错误,查询失败");    
		                }   
	            }
	        });
	        
	        button.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String money = text.getText();	                
	                TCPClient.trs3();
	                if(TCPClient.getin().startsWith("AMNT")) {
	                	TCPClient.setout(money);
	                    TCPClient.trs4();
	                    if("525 OK!".equals(TCPClient.getin())) {
	                    	JOptionPane.showMessageDialog(null,"取款成功"); 
	                    	TCPClient.trs5();	                    	
	                    	}    
	                    else {
	                    	JOptionPane.showMessageDialog(null,"余额不足，请重新输入");  
	                    }
	                }	                                                    
	             }
	        });
	    }
	
}
