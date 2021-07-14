package io.by.pony1223._2_file_stream_reader_writer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class _7_ReaderWriterCopy {

  public static void main(String[] args) {

    /** 纯文本的拷贝 */
    File src = new File("F:/Picture/test/test.txt");
    File dest = new File("F:/Picture/test/test3.txt");

    Reader re = null;
    Writer wr = null;

    try {
      re = new FileReader(src);
      wr = new FileWriter(dest);

      char[] buffer = new char[1024];
      int len = 0;
      while (-1 != (len = re.read(buffer))) {
        wr.write(buffer);
        wr.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {

      try {
        if (wr != null) {

          wr.close();
        }
        if (re != null) {
          re.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
