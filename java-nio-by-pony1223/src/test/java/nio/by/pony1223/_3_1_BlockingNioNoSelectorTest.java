package nio.by.pony1223;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/*
 * 下面我看个例子来使用NIO 演示下阻塞式，即不采用选择器情况下：
 *
 * 一、使用 NIO 完成网络通信的三个核心：
 *
 * 1. 通道（Channel）：负责连接
 *        java.nio.channels.Channel 接口：
 *             |--SelectableChannel
 *                 |--SocketChannel
 *                 |--ServerSocketChannel
 *                 |--DatagramChannel
 *                 |--Pipe.SinkChannel
 *                 |--Pipe.SourceChannel
 *
 * 2. 缓冲区（Buffer）：负责数据的存取
 *
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 */
@TestMethodOrder(OrderAnnotation.class)
public class _3_1_BlockingNioNoSelectorTest extends AbstractTest {

  @Test
  @Order(1)
  void server_execute_first() throws IOException {
    try (
        ServerSocketChannel ssChannel = ServerSocketChannel.open(); // 1. 获取通道
        FileChannel outChannel = FileChannel.open(
            Paths.get("data/output-of-3-1-blocking-nio"),
            StandardOpenOption.WRITE,
            StandardOpenOption.CREATE)) {
      ssChannel.bind(new InetSocketAddress(9898)); // 2. 绑定连接
      SocketChannel sChannel = ssChannel.accept(); // 3. 获取客户端连接的通道
      ByteBuffer buf = ByteBuffer.allocate(1024); // 4. 分配指定大小的缓冲区

      while (sChannel.read(buf) != -1) { // 5. 接收客户端的数据，并保存到本地
        buf.flip();
        outChannel.write(buf);
        buf.clear();
      }
    }
  }

  @Test
  @Order(2)
  void client() throws IOException {
    try (
        SocketChannel sChannel = SocketChannel
            .open(new InetSocketAddress("127.0.0.1", 9898)); // 1. 获取通道
        FileChannel inChannel = FileChannel.open(
            Paths.get("data/input-file"),
            StandardOpenOption.READ)) {
      ByteBuffer buf = ByteBuffer.allocate(1024); // 2. 分配指定大小的缓冲区

      while (inChannel.read(buf) != -1) { // 3. 读取本地文件，并发送到服务端
        buf.flip();
        sChannel.write(buf);
        buf.clear();
      }
    }
  }
}
