package com.lavsurgut

import spray.json.{DefaultJsonProtocol, JsonFormat}

/**
  * DeSerialization from/to JSON for different objects
  */
object JsonDeSerializationProtocol extends DefaultJsonProtocol {
  implicit val format: JsonFormat[Event] = jsonFormat8(Event.apply)
}