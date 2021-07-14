package io.by.pony1223._2_file_stream_reader_writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class _6_FileWriter {

  public static void main(String[] args) {
    /** 纯文本写出 */
    // 1.获取File对象
    File dest = new File("F:/Picture/test/test2.txt");

    // 2.选择流
    Writer writer = null;

    try {
      // true代码追加文件，false代码覆盖，默认false覆盖
      writer = new FileWriter(dest, true);
      String str = "我们都是好孩子！";

      // 3.写出，强制刷出
      writer.write(str);

      // 可追加
      writer.append("hahaahaha");

      writer.flush();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }
}
