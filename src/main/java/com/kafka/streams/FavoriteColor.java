package com.kafka.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

public class FavoriteColor {
    public static void main(String[] args) {
        Properties configs = new Properties();
        configs.put(StreamsConfig.APPLICATION_ID_CONFIG, "favorite-color-stream");
        configs.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        configs.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        configs.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> startStream = builder.stream("favorite-color-input");
        startStream
                .filter((k, v) -> v.contains(","))
                .mapValues( v -> v.split(","))
                .selectKey((k,v) -> v[0].toLowerCase())
                .mapValues(v -> v[1].toLowerCase())
                .to(Serdes.String(), Serdes.String(), "user-color");

        KTable<String, String> startTable = builder.table("user-color");
        KTable<String, Long> colorTable = startTable.groupBy((k,v) -> KeyValue.pair(v,v)).count();
        colorTable.to(Serdes.String(),Serdes.Long(), "favorite-color-output");

        KafkaStreams streams = new KafkaStreams(builder, configs);
        streams.start();
        System.out.println("topoxx: " + streams.toString());


        Runtime.getRuntime().addShutdownHook(new Thread((streams::close)));
    }
}
