package io.by.pony1223;

import static java.lang.System.out;

import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractTest {

  private Instant start;

  @BeforeEach
  void start() {
    start = Instant.now();
  }

  @AfterEach
  void end() {
    out.println("----------------------------------------");
    out.println("测试方法耗时为:" + Duration.between(start, Instant.now()).toMillis() + "ms");
    out.println("----------------------------------------");
  }
}
