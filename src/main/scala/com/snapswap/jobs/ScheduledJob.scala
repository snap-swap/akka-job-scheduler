package com.snapswap.jobs

import scala.concurrent.duration.FiniteDuration

trait ScheduledJob extends Runnable {
  def name: String

  def initialDelay: FiniteDuration

  def interval: Option[FiniteDuration]
}
