package QQServer.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Kritu
 * @Date: 2024/2/3 18:04
 * @Description:
 */

@Data
public class Message implements Serializable {
    /*
    * serialVersionUID版本控制标识符，
    * 确保java对象在序列化和反序列化过程中的版本一致性
    * 不一致的情况：
    * 1. 类结构发生变化
    * 2. 修改了serialVersionUID
    * 3. 不同版本的类进行序列化和反序列化
    * 4. 类加载器的变化
    * */
    private static final long serialVersionUID = 1L;
    private String sender;
    private String getter;
    private String content;
    private String sendTime;
    private String mesType;//消息类型[可以在接口定义消息类型]

    //进行扩展 和文件相关的成员
    private byte[] fileBytes;
    private int fileLen = 0;
    private String dest; //将文件传输到哪里
    private String src; //源文件路径

}
