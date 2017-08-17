package com.lavsurgut

/**
  * Data type for events.
  *
  * @param id       The id of the Event.
  * @param type     The type of the Event.
  * @param userName The username of the Event.
  * @param userId   The user id of the Event.
  * @param sourceIp The source IPv4 address of the Event.
  * @param browser  The browser used in the Event.
  * @param creationDate The creation date.
  * @param status   The status of the event.
  */
case class Event(
                  id: String,
                  `type`: Event.EventType,
                  userName: String,
                  userId: String,
                  sourceIp: String,
                  browser: String,
                  creationDate: Long,
                  status: Event.StatusType) {
}

/**
  * Companion object to the Event type with event type definition and utility methods.
  */
object Event {

  type EventType = String

  val login : EventType = "login"
  val alert : EventType = "alert"

  type StatusType = String
  val failed : StatusType = "failed"
  val success : StatusType = "success"
  val pending: StatusType = "pending"

}
