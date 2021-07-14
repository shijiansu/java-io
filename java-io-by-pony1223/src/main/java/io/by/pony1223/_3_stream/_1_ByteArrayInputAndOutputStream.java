package io.by.pony1223._3_stream;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class _1_ByteArrayInputAndOutputStream {

  /** 输入流 操作与 文件输入流操作一致 读取字节数组 */
  public static void read(byte[] src) throws IOException {
    // 数据源传入

    // 选择流
    InputStream is = new BufferedInputStream(new ByteArrayInputStream(src));
    // 操作
    byte[] flush = new byte[1024];
    int len = 0;
    while (-1 != (len = is.read(flush))) {
      System.out.println(new String(flush, 0, len));
    }
    // 释放资源
    is.close();
  }

  /** 输出流 操作与文件输出流 有些不同， 有新增方法，不能使用多态 */
  public static byte[] write() throws IOException {
    // 目的地
    byte[] dest;
    // 选择流   不同点
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    // 操作 写出
    String msg = "操作与 文件输入流操作一致";
    byte[] info = msg.getBytes();
    bos.write(info, 0, info.length);
    // 获取数据
    dest = bos.toByteArray();
    // 释放资源
    bos.close();
    return dest;
  }
}
