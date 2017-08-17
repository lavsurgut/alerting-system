package com.lavsurgut

import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.util.serialization.{DeserializationSchema, SerializationSchema}
import spray.json._
import JsonDeSerializationProtocol._
import spray.json.JsonParser.ParsingException
/**
  * A serializer / Deserializer for converting [[Event]] objects from/to byte sequences
  * for Kafka.
  */
class EventJsonDeSerializer extends DeserializationSchema[Event] with SerializationSchema[Event] {

  override def deserialize(bytes: Array[Byte]): Event = {
    val json = new String(bytes, "UTF-8")
    try {
      json.parseJson.convertTo[Event]
    } catch {
      case e: DeserializationException => null
      case e: ParsingException => null
    }

  }

  override def serialize(t: Event): Array[Byte] = {
    t.toJson.compactPrint.getBytes("UTF-8")
  }

  override def isEndOfStream(t: Event): Boolean = false

  override def getProducedType: TypeInformation[Event] = {
    createTypeInformation[Event]
  }
}