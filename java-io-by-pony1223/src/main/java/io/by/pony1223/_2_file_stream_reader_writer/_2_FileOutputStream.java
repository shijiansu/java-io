package io.by.pony1223._2_file_stream_reader_writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class _2_FileOutputStream {

  public static void main(String[] args) {
    File dest = new File("E:/study/java/test.txt");
    OutputStream os = null;
    try {
      os = new FileOutputStream(dest);
      String str = "hahahaha";
      // 字符串转字节数组
      byte[] data = str.getBytes();

      os.write(data, 0, data.length);

      // 强制刷新出去
      os.flush();

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {

      try {
        if (null != os) {
          os.close();
        }
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
