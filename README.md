# kafka-env-config-provider

A config provider for `Kafka`, able to retrieve secrets from environment variables.

This is meant to be used in `Kafka` connectors configuration, to be able to get
values that are defined in environment variables.

This is particularly useful when running in the cloud, since configuration for
Docker images (or Kubernetes pods) is normally achieved through environment variables.

This config provider can also be used as a way to externalize secrets when using
`Kafka`, as described [here](https://docs.confluent.io/current/connect/security.html#externalizing-secrets).
A possible way to use this for secrets would be delegating the actual secrets management
to something external (e.g., a Kubernetes operator that gets the secrets from
`AWS` `Secrets Management` and stores it as Kubernetes secrets) and just pass the
secrets to your application as environment variables.

Using a config provider ensures that secrets can be passed to your `Kafka` connectors
without being neither exposed nor logged by the `Kafka` connect infrastructure.

## Installation

In order to install this config provider on a `Kafka connect` instance:

- build the `kafka-env-config-provider` jar using gradle:
  ```bash
  gradle build
  ```
- include the jar under `build/libs` in the `Kafka connect` instance you are
  using in a separate specific directory
  (e.g., `/usr/local/share/java/kafka-connect/plugins/kafka-env-config-provider`)
  (if are using Docker, create an image that extends the `Kafka connect` standard
  one from Confluent, adding this jar)
- in the `Kafka connect` worker configuration, add the directory where you
  placed the jar to the plugins path
  - if you are using the `confluent-oss` platform, edit the file
    `etc/schema-registry/connect-avro-distributed.properties` and add the jar
    directory to the `plugin.path` property as follows:
    ```
    plugin.path=share/java,/usr/local/share/java/kafka-connect/plugins/kafka-env-config-provider
    ```
  - if you are extending the `confluentinc/cp-kafka-connect` Docker image, set
    the environment variable `CONNECT_PLUGIN_PATH` value with the jar directory
    when running the container

## Usage

In order to use this config provider in `Kafka connect`, you need to declare it
in the worker configuration as follows:

- in the `confluent-oss` platform, edit the file
  `etc/schema-registry/connect-avro-distributed.properties` adding the
  following properties:
  ```
  config.providers=env
  config.providers.env.class=org.ggt.kafka.config.provider.KafkaEnvConfigProvider
  ```
- if you are extending the `confluentinc/cp-kafka-connect` Docker image, add the
  following environment variables when running the container:
  ```bash
  CONNECT_CONFIG_PROVIDERS=env
  CONNECT_CONFIG_PROVIDERS_ENV_CLASS=org.ggt.kafka.config.provider.KafkaEnvConfigProvider
  ```

You can then reference environment variables from `Kafka connect` connector
configurations as follows:

```
${env:ENVIRONMENT_VARIABLE_NAME}
```
