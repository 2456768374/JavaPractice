package QQServer.service;


import QQServer.common.Message;
import QQServer.common.MessageType;
import QQServer.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class SendNewsToAllService implements Runnable {


    @Override
    public void run() {

        //为了可以推送多次新闻，使用while
        while (true) {
            System.out.println("请输入服务器要推送的新闻/消息[输入exit表示退出推送服务线程]");
            // 程序会卡在这个地方不断等待输入
            // readString的readKeyBoard使用while (scanner.hasNextLine())
            // 会一直卡住
            String news = Utility.readString(100);
            if("exit".equals(news)) {
                break;
            }
            //构建一个消息 , 群发消息
            Message message = new Message();
            message.setSender("服务器");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("服务器推送消息给所有人 说: " + news);

            //遍历当前所有的通信线程，得到socket,并发送message
            HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();

            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onLineUserId = iterator.next().toString();
                try {
                    // 拿到客户端Socket
                    ObjectOutputStream oos =
                            new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                    // 发送消息到每个客户端
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
