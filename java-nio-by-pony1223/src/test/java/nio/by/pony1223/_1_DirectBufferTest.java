package nio.by.pony1223;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

/*
 * 一、缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 *
 * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *
 * 对于上述数据类型, 缓冲区的管理方式几乎一致，都是通过 allocate() 获取缓冲区
 *
 * 二、缓冲区存取数据的两个核心方法：
 * put():存入数据到缓冲区中
 * get():获取缓冲区中的数据
 *
 * 三、缓冲区中的四个核心属性：
 * capacity:容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。
 * limit:界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
 * position:位置，表示缓冲区中正在操作数据的位置。
 * mark:标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置
 * 0 <= mark <= position <= limit <= capacity
 *
 * 四、两种缓冲区 - 直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 */
class _1_DirectBufferTest extends AbstractTest {

  @Test
  void direct_buffer_api() {
    String str = "abcde";

    // 1. 分配一个指定大小的缓冲区
    out.println("-----------------allocate()----------------");
    ByteBuffer buf = ByteBuffer.allocate(1024);
    out.println(buf.position()); // 0
    out.println(buf.limit()); // 1024
    out.println(buf.capacity()); // 1024

    // 2. 利用 put() 存入数据到缓冲区中
    out.println("-----------------put()----------------");
    buf.put(str.getBytes());
    out.println(buf.position()); // 5
    out.println(buf.limit()); // 1024
    out.println(buf.capacity()); // 1024

    // 3. 切换读取数据模式
    out.println("-----------------flip()----------------");
    buf.flip(); // 设置界限, 重设位置
    out.println(buf.position()); // 0
    out.println(buf.limit()); // 5
    out.println(buf.capacity()); // 1024

    // 4. 利用 get() 读取缓冲区中的数据
    out.println("-----------------get()----------------");
    byte[] dst = new byte[buf.limit()];
    buf.get(dst);
    out.println(new String(dst, 0, dst.length)); // abcde
    out.println(buf.position()); // 5
    out.println(buf.limit()); // 5
    out.println(buf.capacity()); // 1024

    // 5. rewind():可重复读 - 重设到get之前的状态
    out.println("-----------------rewind()----------------");
    buf.rewind();
    out.println(buf.position()); // 0
    out.println(buf.limit()); // 5
    out.println(buf.capacity()); // 1024

    // 6. clear():清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
    out.println("-----------------clear()----------------");
    buf.clear();
    out.println(buf.position()); // 0
    out.println(buf.limit()); // 1024
    out.println(buf.capacity()); // 1024
    out.println((char) buf.get()); // a
  }

  /** 直接缓冲区使用例子 */
  @Test
  void usage() {
    // 放入缓冲区
    String str = "abcde";
    ByteBuffer buf = ByteBuffer.allocate(1024);
    buf.put(str.getBytes());
    buf.flip();

    byte[] dst = new byte[buf.limit()];
    buf.get(dst, 0, 2);
    out.println("String: " + new String(dst, 0, 2));
    out.println("Position: " + buf.position());
    buf.mark(); // mark():标记 - 此时位置为2

    buf.get(dst, 2, 2);
    out.println("String: " + new String(dst, 2, 2));
    out.println("Position: " + buf.position());

    // [注意] reset():reset到mark的位置 - 这个名字不太好呀... 不是reset到0
    buf.reset();
    out.println("After reset, position: " + buf.position());

    // 判断缓冲区中是否还有剩余数据
    if (buf.hasRemaining()) {
      // 获取缓冲区中可以操作的数量
      out.println("Remaining: " + buf.remaining());
    }
    //    String: ab
    //    Position: 2
    //    String: cd
    //    Position: 4
    //    After reset, position: 2
    //    Remaining: 3
  }

  /** 输出直接缓冲区信息是否 */
  @Test
  void is_direct() {
    ByteBuffer buf = ByteBuffer.allocateDirect(1024); // 分配直接缓冲区
    assertTrue(buf.isDirect());
  }
}
