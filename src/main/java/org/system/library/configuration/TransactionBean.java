package org.system.library.configuration;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//TODO: check if needed after.
@Component
public class TransactionBean {

  private final Map<String, Object> beans = new HashMap<>();

  public <O> O get(String key, O object) {
    return beans.get(key) != null ? (O) beans.get(key) : object;
  }

  public void add(String key, Object object) {
    beans.put(key, object);
  }
}