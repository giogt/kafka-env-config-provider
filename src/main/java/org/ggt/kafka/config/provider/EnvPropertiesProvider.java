package org.ggt.kafka.config.provider;

import java.util.Map;
import java.util.Set;

public class EnvPropertiesProvider {

  public Map<String, String> getProperties() {
    return System.getenv();
  }

  public Map<String, String> getProperties(Set<String> keys) {
    MapBuilder<String, String> propsBuilder = new MapBuilder<>();
    for (String key : keys) {
      String value = System.getenv(key);
      propsBuilder.addEntry(key, value);
    }
    return propsBuilder.build();
  }

}
