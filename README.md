# alerting-system
Apache Flink + Kafka simple alerting system


# TODO:
In this implementation the system simply calculates the number of events using processing time of the machine. Also we just produce events by the end of the 30 minutes.
It will be nice to:
1. Use event time instead of processing time. For example by extracting the event time from creationDate field. This will help to deal with latencies in the overall processing and produce correct counts even on out-of-date events
2. Instead of counting and waiting for 30 minutes – fire an event immediately and then wait for 30 minutes. This way we can get a faster alert notification.
3. Add support for docker.
