package com.snapswap.jobs.modules

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import scaldi.Module
import java.time.ZoneOffset
import java.time.ZonedDateTime

import com.snapswap.jobs.ScheduledJob

import scala.concurrent.ExecutionContext

class ScheduleModule extends Module {

  binding to new ScheduledJobs {
    override def run(): Unit = {
      implicit val system = inject[ActorSystem]
      implicit val ctx = inject[ExecutionContext]

      val log = inject[LoggingAdapter]

      val jobs = inject[Seq[ScheduledJob]]

      val cancellable = jobs.map { job =>
        val utc = ZonedDateTime.now(ZoneOffset.UTC)
        val startTime = utc.plusSeconds(job.initialDelay.toSeconds).toString

        job.interval match {
          case Some(interval) =>
            log.debug(s"Job '${job.name}' will start in ${job.initialDelay} at $startTime, then to repeat every $interval")
            system.scheduler.schedule(job.initialDelay, interval, job)
          case None =>
            log.debug(s"Job '${job.name}' will start once in ${job.initialDelay} at $startTime")
            system.scheduler.scheduleOnce(job.initialDelay, job)
        }
      }

      system.registerOnTermination {
        cancellable.foreach(_.cancel)
      }
    }

  }
}

trait ScheduledJobs extends Runnable