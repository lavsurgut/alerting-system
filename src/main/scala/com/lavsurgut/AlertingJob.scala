package com.lavsurgut

import java.util.concurrent.TimeUnit

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer08, FlinkKafkaProducer08}

/**
  * Simple Streaming job that calculates login failure events in JSON format from Kafka
  * and publishes JSON event of type alert to Kafka
  */
object AlertingJob {

  import UserLoginFailedCountTimeWindow._

  val window = Time.of(1800, TimeUnit.SECONDS)

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val kafkaConsumerProperties = Map(
      "group.id" -> "flink",
      "bootstrap.servers" -> "localhost:9092",
      "zookeeper.connect" -> "localhost:2181"
    )

    val kafkaConsumer = new FlinkKafkaConsumer08[Event](
      "event",
      new EventJsonDeSerializer,
      kafkaConsumerProperties
    )

    val kafkaProducer = new FlinkKafkaProducer08[Event](
      "localhost:9092",
      "alert",
      new EventJsonDeSerializer
    )

    val lines = env.addSource(kafkaConsumer)

    val alertCounts = count(lines, window)

    alertCounts.addSink(kafkaProducer)

    env.execute()
  }

  implicit def map2Properties(map: Map[String, String]): java.util.Properties = {
    (new java.util.Properties /: map) { case (props, (k, v)) => props.put(k, v); props }
  }

}