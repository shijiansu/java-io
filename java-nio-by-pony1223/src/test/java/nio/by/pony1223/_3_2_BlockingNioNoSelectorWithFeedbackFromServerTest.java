package nio.by.pony1223;

import static java.lang.System.out;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.junit.jupiter.api.Test;

// 以前没有选择器的时候, 对于阻塞情况, 我们可以采用下面的方法.
// 发送完成, 自动自己关闭告知已发送完成.
class _3_2_BlockingNioNoSelectorWithFeedbackFromServerTest extends AbstractTest {

  // 服务端
  @Test
  void server() throws IOException {
    try (
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        FileChannel outChannel = FileChannel.open(
            Paths.get("output-of-3-2-blocking-nio"),
            StandardOpenOption.WRITE,
            StandardOpenOption.CREATE)) {
      ssChannel.bind(new InetSocketAddress(9898));
      SocketChannel sChannel = ssChannel.accept();
      ByteBuffer buf = ByteBuffer.allocate(1024);

      while (sChannel.read(buf) != -1) {
        buf.flip();
        outChannel.write(buf);
        buf.clear();
      }

      // [Start - 比3.1例子多出来的新代码] 发送反馈给客户端
      buf.put("服务端接收数据成功".getBytes());
      buf.flip();
      sChannel.write(buf);
      // [End]
    }
  }

  // 客户端
  @Test
  void client() throws IOException {
    for (int i = 0; i < 1; i++) { // 例子3只可以执行client一次, server便关闭
      try (
          SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
          FileChannel inChannel = FileChannel.open(
              Paths.get("input-file"),
              StandardOpenOption.READ)) {
        ByteBuffer buf = ByteBuffer.allocate(1024);

        while (inChannel.read(buf) != -1) {
          buf.flip();
          sChannel.write(buf);
          buf.clear();
        }

        sChannel.shutdownOutput();

        // [[Start - 比3.1例子多出来的新代码] 接收服务端的反馈
        int len;
        while ((len = sChannel.read(buf)) != -1) {
          buf.flip();
          out.println(new String(buf.array(), 0, len));
          buf.clear();
        }
        // [End]
      }
    }
  }
}
