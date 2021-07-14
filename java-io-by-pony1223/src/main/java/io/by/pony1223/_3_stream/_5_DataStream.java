package io.by.pony1223._3_stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 数据类型(基本+String)处理流 1、输入流 DataInputStream readXxx() 2、输出流 DataOutputStream writeXxx() 新增方法不能使用多态
 *
 * <p>java.io.EOFException :没有读取到相关的内容
 *
 * @author Administrator
 */
public class _5_DataStream {

  /** @param args */
  public static void main(String[] args) {
    try {
      byte[] data = write();
      read(data);
      System.out.println(data.length);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 从字节数组读取数据+类型 */
  public static void read(byte[] src) throws IOException {
    // 选择流
    DataInputStream dis =
        new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(src)));

    // 操作 读取的顺序与写出一致   必须存在才能读取
    double num1 = dis.readDouble();
    long num2 = dis.readLong();
    String str = dis.readUTF();

    dis.close();

    System.out.println(num1 + "-->" + num2 + "-->" + str);
  }

  /** 数据+类型输出到字节数组中 */
  public static byte[] write() throws IOException {
    // 目标数组
    byte[] dest;
    double point = 2.5;
    long num = 100L;
    String str = "数据类型";

    // 选择流 ByteArrayOutputStream  DataOutputStream
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(bos));
    // 操作 写出的顺序 为读取准备
    dos.writeDouble(point);
    dos.writeLong(num);
    dos.writeUTF(str);
    dos.flush();

    dest = bos.toByteArray();

    // 释放资源
    dos.close();

    return dest;
  }
}
