package jetlang.example


import java.util.concurrent.CountDownLatch

object JetlangPing {
  val latch = new CountDownLatch(1)

  def decrementLatch(): Unit = {
    latch.countDown
  }

  def main(args: Array[String]): Unit = {
    // start the actors
    val start = System.currentTimeMillis;
    PingActor.start
    PongActor.start
    PingActor ! Ping
    latch.await
    println("elapsed = " + (System.currentTimeMillis - start))
    PingActor.exit
    PongActor.exit
    //Pool.shutdown
  }
}

case class Ping()
case class Pong()

object PingActor extends JetlangActor with JetlangThread {
  def act() = {
    case Ping => PongActor ! Pong
  }
}

object PongActor extends JetlangActor with JetlangThread {
  var count = 0

  def act() = {
    case Pong => {
      count = count + 1;
      if (count == 1000000)
        JetlangPing.decrementLatch
      else
        sender ! Ping
    }
  }
}
