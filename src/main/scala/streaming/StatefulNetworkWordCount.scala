/**
 * Copyright (C) 2015 Baifendian Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.spark.examples.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{ Minutes, Seconds, StreamingContext }

object StatefulNetworkWordCount {
  def updateFunction(newValues: Seq[Int], runningCount: Option[Int]): Option[Int] = {
    val newCount = newValues.reduce(_ + _) + runningCount.getOrElse(0)
    Some(newCount)
  }

  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println("Usage: <hostname> <port>")
      System.exit(1)
    }

    val sparkConf = new SparkConf().setAppName("StatefulNetworkWordCount")

    // Create the context with a 2 second batch size
    // 注意实际上 StreamingContext 也可以通过 StreamingContext.getOrCreate(checkpointDirectory, functionToCreateContext _) 来获取，
    // 这能保证 metadata 也是具备容灾功能的，一般不太需要
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("checkpoint")

    // Create a ReceiverInputDStream on target ip:port and count the
    // words in input stream of \n delimited test (eg. generated by 'nc')
    val lines = ssc.socketTextStream(args(0), args(1).toInt)

    val words       = lines.flatMap(_.split(" "))
    val wordDstream = words.map(x => (x, 1))

    val stateDstream = wordDstream.updateStateByKey(updateFunction _)

    // 设置 checkpoint 的周期
    stateDstream.checkpoint(Minutes(1))

    stateDstream.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
