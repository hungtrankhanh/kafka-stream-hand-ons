# launch a Kafka consumer
/bin/kafka-console-consumer --bootstrap-server localhost:9092 \
    --topic favorite-color-stream-KTABLE-AGGREGATE-STATE-STORE-0000000010-changelog \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

/bin/kafka-console-consumer --bootstrap-server localhost:9092 \
    --topic favorite-color-stream-KTABLE-AGGREGATE-STATE-STORE-0000000010-repartition \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer

# launch the streams application

/bin/kafka-console-producer --broker-list localhost:9092 --topic favorite-color-input

# then produce data to it
/bin/kafka-console-producer --broker-list localhost:9092 --topic word-count-input

/bin/kafka-topics --bootstrap-server=localhost:9092 --list


