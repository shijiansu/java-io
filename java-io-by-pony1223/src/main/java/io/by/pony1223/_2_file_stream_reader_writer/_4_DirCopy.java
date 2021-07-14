package io.by.pony1223._2_file_stream_reader_writer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class _4_DirCopy {

  public static void main(String[] args) {
    // 源目录
    String srcPath = "F:/Picture/test";

    // 目标目录
    String destPath = "F:/Picture/dir";

    // 进行拷贝
    try {
      copyDir(srcPath, destPath);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /** 通过路径获得File对象 */
  public static void copyDir(String srcPath, String destPath)
      throws FileNotFoundException, IOException {
    // 拒绝自己拷贝给自己
    if (srcPath.equals(destPath)) {
      return;
    }
    File src = new File(srcPath);
    File dest = new File(destPath);
    copyDir(src, dest);
  }

  /**
   * 拷贝文件夹
   *
   * @param src 源File对象
   * @param dest 目标File对象
   */
  public static void copyDir(File src, File dest) throws FileNotFoundException, IOException {
    if (src.isDirectory()) { // 文件夹
      dest = new File(dest, src.getName());
      if (dest.getAbsolutePath().contains(src.getAbsolutePath())) {
        System.out.println("父目录不能拷贝到子目录中");
        return;
      }
    }
    copyDirDetail(src, dest);
  }

  /** 拷贝文件夹细节 */
  public static void copyDirDetail(File src, File dest) throws FileNotFoundException, IOException {
    if (src.isFile()) { // 文件
      copyFile(src, dest);
    } else if (src.isDirectory()) { // 文件夹
      // 确保目标文件夹存在
      dest.mkdirs();
      // 获取下一级目录|文件
      for (File sub : src.listFiles()) {
        copyDirDetail(sub, new File(dest, sub.getName()));
      }
    }
  }

  /** 文件的拷贝，得到File对象 */
  public static void copyFile(String srcPath, String destPath)
      throws FileNotFoundException, IOException {
    // 1、建立联系 源(存在且为文件) +目的地(文件可以不存在)
    copyFile(new File(srcPath), new File(destPath));
  }

  /** 文件的拷贝 */
  public static void copyFile(File src, File dest) throws FileNotFoundException, IOException {
    if (!src.isFile()) { // 不是文件或者为null
      System.out.println("只能拷贝文件");
      throw new IOException("只能拷贝文件");
    }
    // dest为已经存在的文件夹，不能建立于文件夹同名的文件
    if (dest.isDirectory()) {
      System.out.println(dest.getAbsolutePath() + "不能建立于文件夹同名的文件");
      throw new IOException(dest.getAbsolutePath() + "不能建立于文件夹同名的文件");
    }

    // 2、选择流
    InputStream is = new BufferedInputStream(new FileInputStream(src));
    OutputStream os = new BufferedOutputStream(new FileOutputStream(dest));

    // 3、文件拷贝   循环+读取+写出
    byte[] flush = new byte[1024];
    int len = 0;

    // 读取
    while (-1 != (len = is.read(flush))) {
      // 写出
      os.write(flush, 0, len);
    }
    os.flush(); // 强制刷出

    // 关闭流
    os.close();
    is.close();
  }
}
