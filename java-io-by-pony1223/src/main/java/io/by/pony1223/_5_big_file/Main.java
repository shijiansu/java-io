package io.by.pony1223._5_big_file;

public class Main {

  public static void main(String[] args) {
    LargeFileReader.Builder builder =
        new LargeFileReader.Builder(
            "d:/reliability.txt",
            line -> {
              System.out.println(line);
            });
    builder.withTreahdSize(10).withCharset("gbk").withBufferSize(1024 * 1024);
    LargeFileReader bigFileReader = builder.build();
    bigFileReader.start();
  }
}
