package io.by.pony1223._1_file;

import static java.lang.System.out;

import io.by.pony1223.AbstractTest;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class FileTest extends AbstractTest {

  @Test
  void separator() {
    out.println(File.pathSeparator);
    out.println(File.separator);

    String path = "E:\\study\\java\\HelloWorld.java";
    String path1 = "E:" + File.separator
        + "study" + File.separator
        + "java" + File.separator
        + "HelloWorld.java";
    String path2 = "E:/study/java/HelloWorld.java"; // 推荐方式
  }

  @Test
  void path() {
    String parentPath = "E:/study/java";
    String name = "HelloWorld.java";

    // 相对路径
    File src = new File(new File(parentPath), name);
    // 输出
    out.println(src.getName());
    out.println(src.getPath());

    // 绝对路径
    src = new File("E:/study/java/HelloWorld.java");
    out.println(src.getName());
    out.println(src.getPath());

    // 没有盘符: 以 user.dir构建
    src = new File("test.txt"); // 使用相对路径，注意如果在路径中没有盘符，文件则放在工程项目下
    // src =new File(".");
    out.println(src.getName()); // test.txt
    out.println(src.getPath()); // test.txt
    out.println(
        src.getAbsolutePath()); // G:\DevelopeHome\MyEclipseWorkSpace\Collections\test.txt
    /**
     * getPath:如果构建文件路径是绝对路径则返回完整路径，否则返回相对路径 getAbsolutePath:返回绝对路径（完整路径）
     * getCanonicalPath:不但是全路径，而且把..或者.这样的符号解析出来。
     */
  }

  @Test
  void file_apis() {
    File f = new File("/");
    // 判断文件是否存在
    out.println(f.exists()); // true
    // 判断文件是否可读,可写canWrite()
    out.println(f.canRead()); // true
    // 判断文件路径是否为绝对路径, 有盘符则为绝对路径
    out.println(f.isAbsolute()); // true
    // 判断是文件isFile还是文件夹isDirectory
    out.println(f.isDirectory()); // false
  }

  void create_and_delete_file() throws IOException {
    File f = new File("F:/Java/test.txt");
    if (!f.exists()) {
      boolean flag = f.createNewFile();
      out.println(flag ? "success" : "fail"); // fail
    }
    f.delete();
  }

  void create_temp_file() throws IOException {
    // 当我们创建一个新的File对象, 所给的路径不是根盘路径时, 文件会自动放在项目文件夹下.
    // 但是使用静态方法创建一个临时文件, 所给路径不是根盘路径时, 文件是放在C盘下的某文件夹下的
    File f1 = File.createTempFile("wees", ".temp", new File("F:/JAVA"));
    // F:\JAVA\wees7063380687067821023.temp
    out.println(f1.getAbsolutePath());

    File f2 = File.createTempFile("wes", ".temp");
    // C:\Users\wwyDEPP\AppData\Local\Temp\wes8047158297297613408.temp
    out.println(f2.getAbsolutePath());
  }

  @Test
  void list_all_folders() throws IOException {
    String path = "F:/Picture/test";
    File file = new File(path);
    file.mkdir();

    out.println("输出目录文件夹下所包括的文件名");
    String path2 = "F:/Picture";
    File file2 = new File(path2);
      if (file2.isDirectory()) {
        String[] strs = file2.list();
        for (String s : strs) {
          out.println(s);
        }
      }

      out.println("输出目录文件夹下所包括的文件");
      if (file2.isDirectory()) {
        File[] files = file2.listFiles();
        for (File f : files) {
          out.println(f.getAbsolutePath());
        }
      }

      out.println("输出目录文件夹下所包括的特定文件(.txt),命令设计模式");
      if (file2.isDirectory()) {
        File[] files = file2.listFiles();
        files = file2.listFiles(
            (dir, name) -> new File(dir, name).isFile() && name.endsWith(".txt"));

        for (File f : files) {
          out.println(f.getAbsolutePath());
        }
    }
  }
}
