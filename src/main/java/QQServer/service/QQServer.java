package QQServer.service;
import QQServer.common.Message;
import QQServer.common.MessageType;
import QQServer.common.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: Kritu
 * @Date: 2024/2/3 18:11
 * @Description:
 */
public class QQServer {
    private ServerSocket ss = null;
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
    static { //在静态代码块，初始化 validUsers
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("至尊宝", new User("至尊宝", "123456"));
        validUsers.put("紫霞仙子", new User("紫霞仙子", "123456"));
        validUsers.put("菩提老祖", new User("菩提老祖", "123456"));
    }
    private boolean checkUser(String userId, String passwd){
        /*
        * equals比较的是两个对象的值是否相等，
        * == 比较的是基本数据类型的值、引用数据类型的地址是否相等，
        * 因此基本数据类型推荐使用==，引用数据类型推荐使用equals。
        * */
        User user = validUsers.get(userId);
        // 1.查询是否有此人
        if (user != null){
            // 2.验证密码
            if(user.getPasswd().equals(passwd)){
                return true;
            }
        }
        return false;
    }

    public QQServer(){
        try{
            System.out.println("服务器启动，开始监听9999端口");
            // 1.启动新闻推送线程
            new Thread(new SendNewsToAllService()).start();
            // 2.启动ServerSocket
            ss = new ServerSocket(9999);

            while (true){
                Socket clientSocket = ss.accept();
                // 得到socket的 **对象** 输入输出流
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                // 通过对象流获取对象
                /*
                * 如果不进行序列化，那么只能通过字节进行传输，
                * 这样需要自己对数据进行编码和解码，
                * 所以使用java的序列化传输数据最方便
                * */
                User user = (User) objectInputStream.readObject();

                // 验证对象
                Message message = new Message();
                if (checkUser(user.getUserId(), user.getPasswd())){
                    // 验证通过，将message返回给客户端
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    objectOutputStream.writeObject(message);

                    // 创建一个线程，保持和客户端的通话
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(clientSocket, user.getUserId());
                    serverConnectClientThread.start();
                    //把该线程对象，放入到一个集合中，进行管理.
                    ManageClientThreads.addClientThread(user.getUserId(), serverConnectClientThread);
                }else {
                    // 登录失败
                    System.out.println("用户 id=" + user.getUserId() + " pwd=" + user.getPasswd() + " 验证失败");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    objectOutputStream.writeObject(message);
                    clientSocket.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            //如果服务器退出了while，说明服务器端不在监听，因此关闭ServerSocket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
