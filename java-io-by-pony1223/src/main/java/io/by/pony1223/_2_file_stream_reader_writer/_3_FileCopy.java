package io.by.pony1223._2_file_stream_reader_writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class _3_FileCopy {

  public static void main(String[] args) throws IOException {

    File src = new File("F:/Picture/1.jpg");
    File dest = new File("F:/Picture/copy1.jpg");

    InputStream is = null;
    OutputStream os = null;

    byte[] buffer = new byte[1024];
    int len = 0;

    try {
      is = new FileInputStream(src);
      os = new FileOutputStream(dest);

      // 从文件夹读取读取
      while (-1 != (len = is.read(buffer))) {
        // 写出到文件夹
        os.write(buffer, 0, len);
      }
      // 强制刷出
      os.flush();

    } finally {
      // 先打开的后关闭
      if (os != null) {
        os.close();
      }

      if (is != null) {
        is.close();
      }
    }
  }
}
