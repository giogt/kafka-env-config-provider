package org.ggt.kafka.config.provider;

import org.apache.kafka.common.config.ConfigData;
import org.apache.kafka.common.config.provider.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;

public class KafkaEnvConfigProvider implements ConfigProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaEnvConfigProvider.class);
  private static final Long TTL = Duration.of(5, ChronoUnit.MINUTES).toMillis();

  private final EnvPropertiesProvider propertiesProvider;

  public KafkaEnvConfigProvider() {
    this.propertiesProvider = new EnvPropertiesProvider();
  }

  @Override
  public ConfigData get(String path) {
    LOGGER.debug("get(path) method invoked [path={}] ...", path);
    ConfigData configData = new ConfigData(propertiesProvider.getProperties());
    LOGGER.debug("returning ConfigData [data={}, ttl={}]", configData.data(), configData.ttl());

    return configData;
  }

  @Override
  public ConfigData get(String path, Set<String> keys) {
    LOGGER.debug("get(path, keys) method invoked [path={}, keys={}]", path, keys);
    ConfigData configData = new ConfigData(propertiesProvider.getProperties(keys));
    LOGGER.debug("returning ConfigData [data={}, ttl={}]", configData.data(), configData.ttl());

    return configData;
  }

  @Override
  public void close() {
  }

  @Override
  public void configure(Map<String, ?> configs) {
    LOGGER.debug("received config parameters: {}", configs);
  }

}
