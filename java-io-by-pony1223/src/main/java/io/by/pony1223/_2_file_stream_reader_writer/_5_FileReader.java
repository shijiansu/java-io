package io.by.pony1223._2_file_stream_reader_writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class _5_FileReader {

  public static void main(String[] args) {
    /** 纯文本读取 */
    // 1.建立联系
    File src = new File("F:/Picture/test/test.txt");

    // 2.选择流
    Reader reader = null;
    try {
      reader = new FileReader(src);

      // 3.char数组读取
      char[] flush = new char[1024];
      int len = 0;

      while (-1 != (len = reader.read(flush))) {
        String str = new String(flush, 0, len);
        System.out.println(str);
      }

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      // 4.关闭资源
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }
}
