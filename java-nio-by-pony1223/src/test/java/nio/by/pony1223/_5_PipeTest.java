package nio.by.pony1223;

import static java.lang.System.out;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import org.junit.jupiter.api.Test;

class _5_PipeTest extends AbstractTest {

  @Test
  void pipe() throws IOException {
    Pipe pipe = Pipe.open(); // 1. 获取管道
    ByteBuffer buf = ByteBuffer.allocate(1024); // 2. 将缓冲区中的数据写入管道

    Pipe.SinkChannel sinkChannel = pipe.sink();
    buf.put("通过单向管道发送数据".getBytes());
    buf.flip();
    sinkChannel.write(buf);

    // 3. 读取缓冲区中的数据
    Pipe.SourceChannel sourceChannel = pipe.source();
    buf.flip();
    int len = sourceChannel.read(buf);
    out.println(new String(buf.array(), 0, len));

    sourceChannel.close();
    sinkChannel.close();
  }
}
