package shiyan;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class TCPClient {
	static public Socket socket;
	static private BufferedReader br;
	static private PrintWriter pw ;
	static private String output;
	static private String input;
	static private String out;
	
	public static void setout(String tt) {
		output=tt;		
	}
	
	static public String getin() {
		return input;
   }
	
	static public void trs1() {
		 try {
		     out =  "HELO "+output;
		     pw.println(out+"\n");
		     pw.flush();
		     input = br.readLine();
		     System.out.println("来自服务端的响应： " + input);

		 }catch (Exception e){
	            e.printStackTrace();
	        }
     }
	
	static public void trs2() {
		 try {
			 out ="PASS "+output;
		     pw.println(out+"\n");
		     pw.flush();
		     input = br.readLine();
		     System.out.println("来自服务端的响应： " + input);
		     
		 }catch (Exception e){
	            e.printStackTrace();
	        }
    }
	
	static public void trs3() {
		 try {
			 out = "BALA";
		     pw.println(out+"\n");
		     pw.flush();
		     input = br.readLine();
		     System.out.println("来自服务端的响应： " + input);
		     
		 }catch (Exception e){
	            e.printStackTrace();
	        }
   }
	
	static public void trs4() {
		 try {
		     out = "WDRA "+output;
		     pw.println(out+"\n");
		     pw.flush();
		     input = br.readLine();
		     System.out.println("来自服务端的响应： " + input);
		     
		 }catch (Exception e){
	            e.printStackTrace();
	        }
  }
	
	static public void trs5() {
		 try {
		     out = "Bye";
		     pw.println(out+"\n");
		     pw.flush();
		     input = br.readLine();
		     System.out.println("来自服务端的响应： " + input);
		     
		 }catch (Exception e){
	            e.printStackTrace();
	        }
 }
	
	static public void TCP() {
		try {
			socket = new Socket("192.168.103.213", 2525);
			
			 br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            //获取客户端用户输入
	            //scanner = new Scanner(System.in);
	            //向服务端发送请求
	            pw = new PrintWriter(socket.getOutputStream());
	        	
	            System.out.println("准备接收请求……");
	          //  trs1();
	     //       System.out.println(TCPClient.getin());

		}catch (Exception e){
            e.printStackTrace();
        }
    
	}	
	
}