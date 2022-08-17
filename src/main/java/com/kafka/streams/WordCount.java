package com.kafka.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Arrays;
import java.util.Properties;

public class WordCount {
    public static void main(String[] args) {
        Properties configs = new Properties();
        configs.put(StreamsConfig.APPLICATION_ID_CONFIG, "word-count-stream");
        configs.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        configs.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> wordCountInput = builder.stream("word-count-input");
        KTable<String, Long> wordCounts = wordCountInput.mapValues(v -> v.toLowerCase())
                .flatMapValues(v -> Arrays.asList(v.split(" ")))
                .selectKey((key, value) -> value)
                .groupByKey()
                .count("Counts");
        wordCounts.to(Serdes.String(),Serdes.Long(),"word-count-output");

        KafkaStreams streams = new KafkaStreams(builder, configs);
        streams.start();
        System.out.println("topoxx: " + streams.toString());

        Runtime.getRuntime().addShutdownHook(new Thread((streams::close)));
    }
}
