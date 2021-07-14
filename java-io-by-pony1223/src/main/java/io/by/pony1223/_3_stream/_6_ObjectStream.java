package io.by.pony1223._3_stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class _6_ObjectStream {

  static class Address implements Serializable {
    String country;
    String city;
  }

  static class User implements Serializable { // 必须实现Serializable接口

    private static final long serialVersionUID = 1L;
    String uid;
    String pwd;
    transient String userName = "名字"; // 新添加的成员变量//添加关键字transient后，序列化时忽略
    Address address; // 成员变量为Address

    public User(String uid, String pwd) {
      this.uid = uid;
      this.pwd = pwd;
    }

    @Override
    public String toString() {
      return "账号:" + this.uid + " 密码:" + this.pwd;
    }
  }

  public static void main(String[] args) throws IOException {
    // 假设将对象信息写入到obj.txt文件中，事先已经在硬盘中建立了一个obj.txt文件
    File f = new File("F:\\obj.txt");
    writeObjec(f);
    System.out.println("OK");
  }

  // 定义方法把对象的信息写到硬盘上------>对象的序列化。
  public static void writeObjec(File f) throws IOException {
    FileOutputStream outputStream = new FileOutputStream(f); // 创建文件字节输出流对象
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(new User("酒香逢", "123"));
    // 最后记得关闭资源，objectOutputStream.close()内部已经将outputStream对象资源释放了，所以只需要关闭objectOutputStream即可
    objectOutputStream.close();
  }

  // 把文件中的对象信息读取出来-------->对象的反序列化
  public static void readObject(File f) throws IOException, ClassNotFoundException {
    FileInputStream inputStream = new FileInputStream(f); // 创建文件字节输出流对象
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    User user = (User) objectInputStream.readObject();
    System.out.println(user);
  }
}
