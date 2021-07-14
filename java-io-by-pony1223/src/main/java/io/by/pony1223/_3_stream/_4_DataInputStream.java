package io.by.pony1223._3_stream;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class _4_DataInputStream {

  public static void main(String args[]) throws Exception { // 所有异常抛出
    DataInputStream dis = null; // 声明数据输入流对象
    File f = new File("d:" + File.separator + "order.txt"); // 文件的保存路径
    dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f))); // 实例化数据输入流对象
    String name; // 接收名称
    float price; // 接收价格
    int num = 0; // 接收数量
    char temp[]; // 接收商品名称
    int len; // 保存读取数据的个数
    char c; // '\u0000'
    try {
      while (true) {
        temp = new char[200]; // 开辟空间
        len = 0;
        while ((c = dis.readChar()) != '\t') { // 接收内容
          temp[len] = c;
          len++; // 读取长度加1
        }
        name = new String(temp, 0, len); // 将字符数组变为String
        price = dis.readFloat(); // 读取价格
        dis.readChar(); // 读取\t
        num = dis.readInt(); // 读取int
        dis.readChar(); // 读取\n
        System.out.printf("名称：%s；价格：%5.2f；数量：%d\n", name, price, num);
      }
    } catch (Exception e) {
    }
    dis.close();
  }
}
