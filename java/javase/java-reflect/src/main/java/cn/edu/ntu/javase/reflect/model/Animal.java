package cn.edu.ntu.javase.reflect.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zack <br>
 * @create 2020-02-10 15:04 <br>
 */
public interface Animal {

  Logger LOG = LoggerFactory.getLogger(Animal.class);

  /**
   * Get animal type.
   *
   * @return animal name
   */
  default String getType() {
    LOG.warn("un implement exception: UnsupportedOperationException");
    throw new UnsupportedOperationException();
  }
}
