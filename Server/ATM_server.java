import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ATM_server {
  
   
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/atm? useSSL=true";
        String user = "root";
        String pass = "2022kaoshangzc";
        log = new Log();
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            
            // 启动服务器
            ServerSocket serverSocket = new ServerSocket(2525);
            System.out.println("ATM Server started. ");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                moniter();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

      
        public void moniter() {
            try (
                    DataInputStream inFromClient = new DataInputStream(new DataInputStream(clientSocket.getInputStream()));
                    DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream())
            ) {
                String response;
                String username = null;
                String password = null;

                while (true) {
                    String request = inFromClient.readUTF();
                    System.out.println(request);

                    if (request.equals("BYE")) {
                        outToClient.writeUTF("BYE");
                        log.addlog("BYE");
                        log.addlog("BYE",username);
                    }

                    if (request.startsWith("HELO")) {
                        username = request.substring(5, request.length() - 1);
                        System.out.println(username);
                        // 检查用户ID是否存在
                        if (checkAccountExists(username)) {
                            response = "500 AUTH REQUIRED!";
                            log.addlog("500 AUTH REQUIRED!");
                            log.addlog("500 AUTH REQUIRE",username);
                        } else {
                            response = "401 ERROR!";
                            log.addlog("401 ERROR!");
                log.addlog("401 ERROR!",username);
                        }
                        outToClient.writeUTF(response);
                        
                    } else if (request.startsWith("PASS")) {
                        password = request.substring(5, request.length() - 1);
                        System.out.println(password);
                        // 检查密码是否正确
                        if (authenticate(username, password)) {
                            response = "525 OK!";
                            log.addlog("525 OK!");
                            log.addlog("525 OK!",username);
                        } else {
                            response = "401 ERROR!";
                            log.addlog("401 ERROR!");
            log.addlog("401 ERROR!",username);
                        }
                        outToClient.writeUTF(response);
                    } else if (request.startsWith("WDRA")) {
                        int amount = Integer.parseInt(request.substring(5, request.length() - 1));
                        boolean success = withdraw(username, amount);
                        if (success) {
                            response = "525 OK!"; // 取款成功
                            log.addlog("525 OK!");
                             log.addlog("525 OK!");
                    log.addlog("525 OK!",username);
                        } else {
                            response = "401 ERROR!"; // 取款失败
                            log.addlog("401 ERROR!");
                            log.addlog("401 ERROR!",username);
                        }
                        outToClient.writeUTF(response);
                    } else if (request.equals("BALA")) {
                        int balance = getBalance(username); // 传递正确的用户名参数
                        outToClient.writeUTF("AMNT:" + "<" + balance + ">");
                        log.addlog("AMNT:" + balance);
                        log.addlog("AMNT:" + balance,username);
                    }
                }
            } catch (IOException | SQLException e) {
                System.err.println("Error handling client request: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        boolean checkAccountExists(String username) throws SQLException {
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE username=?")) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        }

        boolean authenticate(String username, String password) throws SQLException {
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE username=? AND password=?")) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        }

       int getBalance(String username) throws SQLException {
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement statement = conn.prepareStatement("SELECT balance FROM accounts WHERE username=?")) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("balance");
                    }
                }
            }
            return 0; // 如果找不到账户，则返回0余额
        }

        boolean withdraw(String username, int amount) throws SQLException {

            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                // 查询当前余额
                int balance = getBalance(username);
                if (balance >= amount) {
                    // 如果余额充足，执行取款操作
                    balance -= amount;
                    // 更新数据库中的余额信息
                    try (PreparedStatement statement = conn.prepareStatement("UPDATE accounts SET balance=? WHERE username=?")) {
                        statement.setInt(1, balance);
                        statement.setString(2, username);
                        statement.executeUpdate();
                    }
                    return true; // 取款成功
                } else {
                    return false; // 余额不足，取款失败
                }
            }
        }
    }
}



