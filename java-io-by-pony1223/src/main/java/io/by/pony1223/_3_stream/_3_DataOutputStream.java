package io.by.pony1223._3_stream;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class _3_DataOutputStream {

  public static void main(String[] args) throws Exception { // 所有异常抛出
    File f = new File("d:" + File.separator + "order.txt"); // 文件的保存路径
    // 声明数据输出流对象
    try (DataOutputStream dos =
        new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
      String[] names = {"衬衣", "手套", "围巾"}; // 商品名称
      float[] prices = {98.3f, 30.3f, 50.5f}; // 商品价格
      int[] nums = {3, 2, 1}; // 商品数量
      for (int i = 0; i < names.length; i++) { // 循环输出
        dos.writeChars(names[i]); // 写入字符串
        dos.writeChar('\t'); // 写入分隔符
        dos.writeFloat(prices[i]); // 写入价格
        dos.writeChar('\t'); // 写入分隔符
        dos.writeInt(nums[i]); // 写入数量
        dos.writeChar('\n'); // 换行
      }
    } // 实例化数据输出流对象
  }
}
