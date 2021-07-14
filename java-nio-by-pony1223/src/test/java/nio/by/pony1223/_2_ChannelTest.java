package nio.by.pony1223;

import static java.lang.System.out;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.jupiter.api.Test;

/*
 * 一、通道（Channel）:用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输.
 * Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 *
 * 二、通道的主要实现类
 *     java.nio.channels.Channel 接口:
 *         |--FileChannel
 *         |--SocketChannel
 *         |--ServerSocketChannel
 *         |--DatagramChannel
 *
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 *         本地 IO:
 *         FileInputStream/FileOutputStream
 *         RandomAccessFile
 *
 *         网络IO:
 *         Socket
 *         ServerSocket
 *         DatagramSocket
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 *
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 *
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）:将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）:将多个缓冲区中的数据聚集到通道中
 *
 * 六、字符集:Charset
 * 编码:字符串 -> 字节数组
 * 解码:字节数组  -> 字符串
 *
 */
class _2_ChannelTest extends AbstractTest {

  // Need to replace below large file location when doing testing
  private String input = "/Users/shijiansu/large-file.mp4";
  private String output = "/Users/shijiansu/large-file-copied.mp4";

  // 通道（非直接缓冲区）
  @Test
  void channel_without_direct_buffer() throws IOException { // 367
    try (
        // 1. 获取通道
        FileInputStream fis = new FileInputStream(input);
        FileOutputStream fos = new FileOutputStream(output);
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel()) {

      // 2. 分配指定大小的缓冲区
      ByteBuffer buf = ByteBuffer.allocate(1024);

      // 3. 将通道中的数据存入缓冲区中
      while (inChannel.read(buf) != -1) {
        buf.flip(); // 切换读取数据的模式
        // 4. 将缓冲区中的数据写入通道中
        outChannel.write(buf);
        buf.clear(); // 清空缓冲区
      }
    }
    // 796.1MB - 耗费时间为:8004
  }

  // 直接缓冲区 (内存映射文件)
  @Test
  void direct_buffer() throws IOException { // 78
    try (
        FileChannel inChannel = FileChannel.open(Paths.get(input), StandardOpenOption.READ);
        FileChannel outChannel =
            FileChannel.open(
                Paths.get(output),
                StandardOpenOption.WRITE,
                StandardOpenOption.READ,
                StandardOpenOption.CREATE)) {
      // 内存映射文件
      MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
      MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());

      // 直接对缓冲区进行数据的读写操作
      byte[] dst = new byte[inMappedBuf.limit()];
      inMappedBuf.get(dst);
      outMappedBuf.put(dst);
    }
    // 796.1MB - 耗费时间为:5072
  }

  // 通道数据传输 + 直接缓冲区 ->这个方式最快
  @Test
  void channel_with_direct_buffer() throws IOException { // 38
    try (
        FileChannel inChannel = FileChannel.open(Paths.get(input), StandardOpenOption.READ);
        FileChannel outChannel =
            FileChannel.open(
                Paths.get(output),
                StandardOpenOption.WRITE,
                StandardOpenOption.READ,
                StandardOpenOption.CREATE)) {
      outChannel.transferFrom(inChannel, 0, inChannel.size());
    }
    // 796.1MB - 耗费时间为:717
  }

  // 分散和聚集
  @Test
  void scatter_and_gather() throws IOException {
    RandomAccessFile raf1 = new RandomAccessFile("input-file", "rw");
    // 1. 获取通道
    FileChannel channel1 = raf1.getChannel();

    // 2. 分配指定大小的缓冲区
    ByteBuffer buf1 = ByteBuffer.allocate(10);
    ByteBuffer buf2 = ByteBuffer.allocate(1024);

    // 3. 分散读取
    ByteBuffer[] bufs = {buf1, buf2};
    channel1.read(bufs);

    for (ByteBuffer b : bufs) {
      b.flip();
    }

    out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
    out.println("-----------------");
    out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

    // 4. 聚集写入
    RandomAccessFile raf2 = new RandomAccessFile("input-file-of-4-file", "rw");
    FileChannel channel2 = raf2.getChannel();
    channel2.write(bufs);
  }

  @Test
  void available_charsets() {
    Map<String, Charset> map = Charset.availableCharsets();
    Set<Entry<String, Charset>> set = map.entrySet();
    for (Entry<String, Charset> entry : set) {
      out.println(entry.getKey() + " = " + entry.getValue());
    }
  }

  // 字符集
  @Test
  void charset_encoder() throws IOException {
    Charset cs1 = Charset.forName("GBK");
    CharsetEncoder ce = cs1.newEncoder(); // 获取编码器
    CharsetDecoder cd = cs1.newDecoder(); // 获取解码器

    CharBuffer charBuffer = CharBuffer.allocate(1024);
    charBuffer.put("尚硅谷威武！");
    charBuffer.flip();

    // 编码
    ByteBuffer byteBuffer = ce.encode(charBuffer);
    for (int i = 0; i < 12; i++) {
      out.println(byteBuffer.get());
    }

    // 解码
    byteBuffer.flip(); // 为读取准备
    CharBuffer charBuffer2 = cd.decode(byteBuffer);
    out.println(charBuffer2.toString());

    out.println("------------------------------------------------------");

    // 另一个解码器
    byteBuffer.flip();
    Charset cs2 = Charset.forName("GBK");
    CharBuffer charBuffer3 = cs2.decode(byteBuffer);
    out.println(charBuffer3.toString());
  }
}
