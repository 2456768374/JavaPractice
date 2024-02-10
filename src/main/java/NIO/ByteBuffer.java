package NIO;

import java.io.FileInputStream;
import java.nio.Buffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: Kritu
 * @Date: 2024/2/5 17:02
 * @Description: 测试ByteBuffer
 */
public class ByteBuffer {
    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("src/main/java/NIO/data.txt").getChannel()){
            java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(10);
            int read = channel.read(buffer);
            Buffer flip = buffer.flip();
            while (buffer.hasRemaining()){
                byte b = buffer.get();
                System.out.println((char) b);
            }

        }catch (Exception e){

        }
    }
}
