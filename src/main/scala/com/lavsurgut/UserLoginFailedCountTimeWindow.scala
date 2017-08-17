package com.lavsurgut


import java.time.Instant

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * Implements the count for failed login events for a given user in a time window
  * In case the count rises above 10 outputs in a time window outputs an event of alert type
  *
  ***/

object UserLoginFailedCountTimeWindow {

  def count(lines: DataStream[Event], window: Time): DataStream[Event] = {
    lines
      .filter(l => l.`type` == Event.login && l.status == Event.failed)
      .map(l => Count(l.userId, l, 1))
      .keyBy("userId")
      .timeWindow(window)
      .sum("count")
      .filter(_.count >= 10)
      .map(l => {
        Event(l.event.userId, Event.alert, l.event.userName, l.event.userId, l.event.sourceIp, l.event.browser, Instant.now.getEpochSecond, Event.pending)
      })
  }
}

case class Count(userId: String, event: Event, count: Int)
