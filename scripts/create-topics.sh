echo "Waiting for Kafka to come online..."

cub kafka-ready -b kafka:9092 1 20

# create the word-count-Input topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic word-count-input \
  --replication-factor 1 \
  --partitions 4 \
  --create

# create the word-count-output topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic word-count-output \
  --replication-factor 1 \
  --partitions 4 \
  --create

# create the word-count-output topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic favorite-color-input \
  --replication-factor 1 \
  --partitions 4 \
  --create

# create the word-count-output topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic user-color \
  --replication-factor 1 \
  --partitions 4 \
  --create

# create the word-count-output topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic favorite-color-output \
  --replication-factor 1 \
  --partitions 4 \
  --create

sleep infinity
