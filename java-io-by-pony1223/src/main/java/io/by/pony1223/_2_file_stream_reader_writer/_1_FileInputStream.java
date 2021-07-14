package io.by.pony1223._2_file_stream_reader_writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class _1_FileInputStream {

  public static void main(String[] args) {
    // 1、建立联系 File对象
    File src = new File("E:/study/java/HelloWorld.java");
    // 2、选择流
    InputStream is = null; // 提升作用域
    try {
      is = new FileInputStream(src);
      // 3、操作 不断读取 缓冲数组
      byte[] car = new byte[1024];
      int len = 0; // 接收 实际读取大小
      // 循环读取
      StringBuilder sb = new StringBuilder();
      while (-1 != (len = is.read(car))) {
        // 输出 字节数组转成字符串
        String info = new String(car, 0, len);
        sb.append(info);
      }
      System.out.println(sb.toString());

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("文件不存在");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("读取文件失败");
    } finally {
      try {
        // 4、释放资源
        if (null != is) {
          is.close();
        }
      } catch (Exception e2) {
        System.out.println("关闭文件输入流失败");
      }
    }
  }
}
