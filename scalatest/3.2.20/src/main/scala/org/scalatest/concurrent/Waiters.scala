/*
 * Copyright 2001-2013 Artima, Inc.
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
package org.scalatest.concurrent

import org.scalatest._
import Assertions.fail
import org.scalatest.exceptions.NotAllowedException
import org.scalatest.exceptions.TestFailedException
import org.scalatest.exceptions.StackDepthException
import time.{Nanoseconds, Second, Span}
import PatienceConfiguration._
import org.scalactic.source

/**
 * Trait that facilitates performing assertions outside the main test thread, such as assertions in callback methods
 * that are invoked asynchronously.
 *
 * <p>
 * Trait <code>Waiters</code> provides a <code>Waiter</code> class that you can use to orchestrate the inter-thread
 * communication required to perform assertions outside the main test thread, and a means to configure it.
 * </p>
 *
 * <p>
 * To use <code>Waiter</code>, create an instance of it in the main test thread:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> w = <span class="stReserved">new</span> <span class="stType">Waiter</span> <span class="stLineComment">// Do this in the main test thread</span>
 * </pre>
 *
 * <p>
 * At some point later, call <code>await</code> on the waiter:
 * </p>
 *
 * <pre class="stHighlighted">
 * w.await() <span class="stLineComment">// Call await() from the main test thread</span>
 * </pre>
 *
 * <p>
 * The <code>await</code> call will block until it either receives a report of a failed assertion from a different thread, at which
 * point it will complete abruptly with the same exception, or until it is <em>dismissed</em> by a different thread (or threads), at
 * which point it will return normally. You can optionally specify a timeout and/or a number
 * of dismissals to wait for. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest.time.SpanSugar._
 * <br/>w.await(timeout(<span class="stLiteral">300</span> millis), dismissals(<span class="stLiteral">2</span>))
 * </pre>
 *
 * <p>
 * The default value for <code>timeout</code>, provided via an implicit <code>PatienceConfig</code> parameter, is 150 milliseconds. The default value for
 * <code>dismissals</code> is 1. The <code>await</code> method will block until either it is dismissed a sufficient number of times by other threads or
 * an assertion fails in another thread. Thus if you just want to perform assertions in just one other thread, only that thread will be
 * performing a dismissal, so you can use the default value of 1 for <code>dismissals</code>.
 * </p>
 *
 * <p>
 * <code>Waiter</code> contains four overloaded forms of <code>await</code>, two of which take an implicit
 * <code>PatienceConfig</code> parameter. To change the default timeout configuration, override or hide
 * (if you imported the members of <code>Waiters</code> companion object instead of mixing in the
 * trait) <code>patienceConfig</code> with a new one that returns your desired configuration.
 * </p>
 *
 * <p>
 * To dismiss a waiter, you just invoke <code>dismiss</code> on it:
 * </p>
 *
 * <pre class="stHighlighted">
 * w.dismiss() <span class="stLineComment">// Call this from one or more other threads</span>
 * </pre>
 *
 * <p>
 * You may want to put <code>dismiss</code> invocations in a finally clause to ensure they happen even if an exception is thrown.
 * Otherwise if a dismissal is missed because of a thrown exception, an <code>await</code> call will wait until it times out.
 * </p>
 *
 * <p>
 * Note that if a <code>Waiter</code> receives <em>more </em> than the expected number of dismissals, it will not report
 * this as an error: <em>i.e.</em>, receiving greater than the number of expected dismissals without any failed assertion will simply
 * cause the the test to complete, not to fail. The only way a <code>Waiter</code> will cause a test to fail is if one of the
 * asynchronous assertions to which it is applied fails.
 * </p>
 *
 * <p>
 * Finally, to perform an assertion in a different thread, you just apply the <code>Waiter</code> to the assertion code. Here are
 * some examples:
 * </p>
 *
 * <pre class="stHighlighted">
 * w { assert(<span class="stLiteral">1</span> + <span class="stLiteral">1</span> === <span class="stLiteral">3</span>) }    <span class="stLineComment">// Can use assertions</span>
 * w { <span class="stLiteral">1</span> + <span class="stLiteral">1</span> should equal (<span class="stLiteral">3</span>) } <span class="stLineComment">// Or matchers</span>
 * w { <span class="stQuotedString">"hi"</span>.charAt(-<span class="stLiteral">1</span>) }        <span class="stLineComment">// Any exceptions will be forwarded to await</span>
 * </pre>
 *
 * <p>
 * Here's a complete example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> concurrent.Waiters
 * <span class="stReserved">import</span> scala.actors.Actor
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSuite</span> <span class="stReserved">extends</span> <span class="stType">FunSuite</span> <span class="stReserved">with</span> <span class="stType">Matchers</span> <span class="stReserved">with</span> <span class="stType">Waiters</span> {
 * <br/>  <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">Message</span>(text: <span class="stType">String</span>)
 * <br/>  <span class="stReserved">class</span> <span class="stType">Publisher</span> <span class="stReserved">extends</span> <span class="stType">Actor</span> {
 * <br/>    @volatile <span class="stReserved">private</span> <span class="stReserved">var</span> handle: <span class="stType">Message</span> =&gt; <span class="stType">Unit</span> = { (msg) =&gt; }
 * <br/>    <span class="stReserved">def</span> registerHandler(f: <span class="stType">Message</span> =&gt; <span class="stType">Unit</span>) {
 *       handle = f
 *     }
 * <br/>    <span class="stReserved">def</span> act() {
 *       <span class="stReserved">var</span> done = <span class="stReserved">false</span>
 *       <span class="stReserved">while</span> (!done) {
 *         react {
 *           <span class="stReserved">case</span> msg: <span class="stType">Message</span> =&gt; handle(msg)
 *           <span class="stReserved">case</span> <span class="stQuotedString">"Exit"</span> =&gt; done = <span class="stReserved">true</span>
 *         }
 *       }
 *     }
 *   }
 * <br/>  test(<span class="stQuotedString">"example one"</span>) {
 * <br/>    <span class="stReserved">val</span> publisher = <span class="stReserved">new</span> <span class="stType">Publisher</span>
 *     <span class="stReserved">val</span> message = <span class="stReserved">new</span> <span class="stType">Message</span>(<span class="stQuotedString">"hi"</span>)
 *     <span class="stReserved">val</span> w = <span class="stReserved">new</span> <span class="stType">Waiter</span>
 * <br/>    publisher.start()
 * <br/>    publisher.registerHandler { msg =&gt;
 *       w { msg should equal (message) }
 *       w.dismiss()
 *     }
 * <br/>    publisher ! message
 *     w.await()
 *     publisher ! <span class="stQuotedString">"Exit"</span>
 *   }
 * }
 * </pre>
 *
 * @author Bill Venners
 */
trait Waiters extends PatienceConfiguration {

  /**
   * A configuration parameter that specifies the number of dismissals to wait for before returning normally
   * from an <code>await</code> call on a <code>Waiter</code>.
   *
   * @param value the number of dismissals for which to wait
   * @throws IllegalArgumentException if specified <code>value</code> is less than or equal to zero.
   *
   * @author Bill Venners
   */
  final case class Dismissals(value: Int)  // TODO check for IAE if negative

  /**
   * Returns a <code>Dismissals</code> configuration parameter containing the passed value, which
   * specifies the number of dismissals to wait for before returning normally from an <code>await</code>
   * call on a <code>Waiter</code>.
   */
  def dismissals(value: Int) = Dismissals(value)

  /**
   * Class that facilitates performing assertions outside the main test thread, such as assertions in callback methods
   * that are invoked asynchronously.
   *
   * <p>
   * To use <code>Waiter</code>, create an instance of it in the main test thread:
   * </p>
   *
   * <pre class=stHighlight">
   * val w = new Waiter // Do this in the main test thread
   * </pre>
   *
   * <p>
   * At some point later, call <code>await</code> on the waiter:
   * </p>
   *
   * <pre class="stHighlighted">
   * w.await() <span class="stLineComment">// Call await() from the main test thread</span>
   * </pre>
   *
   * <p>
   * The <code>await</code> call will block until it either receives a report of a failed assertion from a different thread, at which
   * point it will complete abruptly with the same exception, or until it is <em>dismissed</em> by a different thread (or threads), at
   * which point it will return normally. You can optionally specify a timeout and/or a number
   * of dismissals to wait for. Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalatest.time.SpanSugar._
   * <br/>w.await(timeout(<span class="stLiteral">300</span> millis), dismissals(<span class="stLiteral">2</span>))
   * </pre>
   *
   * <p>
   * The default value for <code>timeout</code>, provided via an implicit <code>PatienceConfig</code> parameter, is 150 milliseconds. The default value for
   * <code>dismissals</code> is 1. The <code>await</code> method will block until either it is dismissed a sufficient number of times by other threads or
   * an assertion fails in another thread. Thus if you just want to perform assertions in just one other thread, only that thread will be
   * performing a dismissal, so you can use the default value of 1 for <code>dismissals</code>.
   * </p>
   *
   * <p>
   * <code>Waiter</code> contains four overloaded forms of <code>await</code>, two of which take an implicit
   * <code>PatienceConfig</code> parameter. To change the default timeout configuration, override or hide
   * (if you imported the members of <code>Waiters</code> companion object instead of mixing in the
   * trait) <code>patienceConfig</code> with a new one that returns your desired configuration.
   * </p>
   *
   * <p>
   * To dismiss a waiter, you just invoke <code>dismiss</code> on it:
   * </p>
   *
   * <pre class="stHighlighted">
   * w.dismiss() <span class="stLineComment">// Call this from one or more other threads</span>
   * </pre>
   *
   * <p>
   * You may want to put <code>dismiss</code> invocations in a finally clause to ensure they happen even if an exception is thrown.
   * Otherwise if a dismissal is missed because of a thrown exception, an <code>await</code> call will wait until it times out.
   * </p>
   *
   * <p>
   * Finally, to perform an assertion in a different thread, you just apply the <code>Waiter</code> to the assertion code. Here are
   * some examples:
   * </p>
   *
   * <pre class="stHighlighted">
   * w { assert(<span class="stLiteral">1</span> + <span class="stLiteral">1</span> === <span class="stLiteral">3</span>) }    <span class="stLineComment">// Can use assertions</span>
   * w { <span class="stLiteral">1</span> + <span class="stLiteral">1</span> should equal (<span class="stLiteral">3</span>) } <span class="stLineComment">// Or matchers</span>
   * w { <span class="stQuotedString">"hi"</span>.charAt(-<span class="stLiteral">1</span>) }        <span class="stLineComment">// Any exceptions will be forwarded to await</span>
   * </pre>
   *
   * <p>
   * Here's a complete example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalatest._
   * <span class="stReserved">import</span> concurrent.Waiters
   * <span class="stReserved">import</span> scala.actors.Actor
   * <br/><span class="stReserved">class</span> <span class="stType">ExampleSuite</span> <span class="stReserved">extends</span> <span class="stType">FunSuite</span> <span class="stReserved">with</span> <span class="stType">Matchers</span> <span class="stReserved">with</span> <span class="stType">Waiters</span> {
   * <br/>  <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">Message</span>(text: <span class="stType">String</span>)
   * <br/>  <span class="stReserved">class</span> <span class="stType">Publisher</span> <span class="stReserved">extends</span> <span class="stType">Actor</span> {
   * <br/>    @volatile <span class="stReserved">private</span> <span class="stReserved">var</span> handle: <span class="stType">Message</span> =&gt; <span class="stType">Unit</span> = { (msg) =&gt; }
   * <br/>    <span class="stReserved">def</span> registerHandler(f: <span class="stType">Message</span> =&gt; <span class="stType">Unit</span>) {
   *       handle = f
   *     }
   * <br/>    <span class="stReserved">def</span> act() {
   *       <span class="stReserved">var</span> done = <span class="stReserved">false</span>
   *       <span class="stReserved">while</span> (!done) {
   *         react {
   *           <span class="stReserved">case</span> msg: <span class="stType">Message</span> =&gt; handle(msg)
   *           <span class="stReserved">case</span> <span class="stQuotedString">"Exit"</span> =&gt; done = <span class="stReserved">true</span>
   *         }
   *       }
   *     }
   *   }
   * <br/>  test(<span class="stQuotedString">"example one"</span>) {
   * <br/>    <span class="stReserved">val</span> publisher = <span class="stReserved">new</span> <span class="stType">Publisher</span>
   *     <span class="stReserved">val</span> message = <span class="stReserved">new</span> <span class="stType">Message</span>(<span class="stQuotedString">"hi"</span>)
   *     <span class="stReserved">val</span> w = <span class="stReserved">new</span> <span class="stType">Waiter</span>
   * <br/>    publisher.start()
   * <br/>    publisher.registerHandler { msg =&gt;
   *       w { msg should equal (message) }
   *       w.dismiss()
   *     }
   * <br/>    publisher ! message
   *     w.await()
   *     publisher ! <span class="stQuotedString">"Exit"</span>
   *   }
   * }
   * </pre>
   *
   * @author Bill Venners
   */
  class Waiter {

    private final val creatingThread = Thread.currentThread

    /*
     * @volatile is not sufficient to ensure atomic compare and set, or increment.
     * Given that the code which modifies these variables should be synchronized anyway
     * (because these variables are used in the wait condition predicate), 
     * the @volatile is superfluous. 
     */
    /* @volatile */ private var dismissedCount = 0
    /* @volatile */ private var thrown: Option[Throwable] = None

    private def setThrownIfEmpty(t: Throwable): Unit = {
      /*
       * synchronized to serialize access to `thrown` which is used in the wait condition, 
       * and to ensure the compare and set is atomic.
       * (Arguably, a race condition on setting `thrown` is harmless, but synchronization makes
       * the class easier to reason about.)
       */
      synchronized {
        if (thrown.isEmpty) thrown = Some(t)
      }
    }

    /**
     * Executes the passed by-name, and if it throws an exception, forwards it to the thread that calls <code>await</code>, unless
     * a by-name passed during a previous invocation of this method threw an exception.
     *
     * <p>
     * This method returns normally whether or not the passed function completes abruptly. If called multiple times, only the
     * first invocation that yields an exception will "win" and have its exception forwarded to the thread that calls <code>await</code>.
     * Any subsequent exceptions will be "swallowed." This method may be invoked by multiple threads concurrently, in which case it is a race
     * to see who wins and has their exception forwarded to <code>await</code>. The <code>await</code> call will eventually complete
     * abruptly with the winning exception, or return normally if that instance of <code>Waiter</code> is dismissed. Any exception thrown by
     * a by-name passed to <code>apply</code> after the <code>Waiter</code> has been dismissed will also be "swallowed."
     * </p>
     *
     * @param fun the by-name function to execute
     */
    def apply(fun: => Unit): Unit = {
      try {
        fun
      } catch { // Exceptions after the first are swallowed (need to get to dismissals later)
        case t: Throwable =>
          setThrownIfEmpty(t)
          synchronized {
            notifyAll()
          }
      }
    }

    /**
     * Wait for an exception to be produced by the by-name passed to <code>apply</code> or the specified number of dismissals.
     *
     * <p>
     * This method may only be invoked by the thread that created the <code>Waiter</code>.
     * Each time this method completes, its internal dismissal count is reset to zero, so it can be invoked multiple times. However,
     * once <code>await</code> has completed abruptly with an exception produced during a call to <code>apply</code>, it will continue
     * to complete abruptly with that exception. The default value for the <code>dismissals</code> parameter is 1.
     * </p>
     *
     * <p>
     * The <code>timeout</code> parameter allows you to specify a timeout after which a <code>TestFailedException</code> will be thrown with
     * a detail message indicating the <code>await</code> call timed out. The default value for <code>timeout</code> is -1, which indicates
     * no timeout at all. Any positive value (or zero) will be interpreted as a timeout expressed in milliseconds. If no calls to <code>apply</code>
     * have produced an exception and an insufficient number of dismissals has been received by the time the <code>timeout</code> number
     * of milliseconds has passed, <code>await</code> will complete abruptly with <code>TestFailedException</code>.
     * </p>
     *
     * @param timeout the number of milliseconds timeout, or -1 to indicate no timeout (default is -1)
     * @param dismissals the number of dismissals to wait for (default is 1)
     */
    private def awaitImpl(timeout: Span, pos: source.Position, dismissals: Int = 1): Unit = {
      if (Thread.currentThread != creatingThread)
        throw new NotAllowedException(Resources.awaitMustBeCalledOnCreatingThread, None, pos)

      val startTime: Long = System.nanoTime
      val endTime: Long = startTime + timeout.totalNanos
      def timedOut: Boolean = endTime < System.nanoTime
      synchronized {
        while (dismissedCount < dismissals && !timedOut && thrown.isEmpty) {
          val timeLeft: Span = {
            val diff = endTime - System.nanoTime
            if (diff > 0) Span(diff, Nanoseconds) else Span.Zero
          }
          wait(timeLeft.millisPart, timeLeft.nanosPart)
        }
        // it should never be the case that we get all the expected dismissals and still throw 
        // a timeout failure - clients trying to debug code would find that very surprising.
        // Calls to timedOut subsequent to while loop exit constitute a kind of "double jeopardy".
        // This if-else must be in the synchronized block because it accesses and assigns dismissalsCount.
        if (thrown.isDefined)
          throw thrown.get
        else if (dismissedCount >= dismissals)
          dismissedCount = 0 // reset the dismissed count to support multiple await calls        
        else if (timedOut)
          throw new TestFailedException((sde: StackDepthException) => Some(Resources.awaitTimedOut), None, pos, None)
        else throw new Exception("Should never happen: thrown was not defined; dismissedCount was not greater than dismissals; and timedOut was false")
      }
    }

    /**
     * Wait for an exception to be produced by the by-name passed to <code>apply</code>, or one dismissal,
     * sleeping an interval between checks and timing out after a timeout, both configured
     * by an implicit <code>PatienceConfig</code>.
     *
     * <p>
     * This method may only be invoked by the thread that created the <code>Waiter</code>.
     * Each time this method completes, its internal dismissal count is reset to zero, so it can be invoked multiple times. However,
     * once <code>await</code> has completed abruptly with an exception produced during a call to <code>apply</code>, it will continue
     * to complete abruptly with that exception.
     * </p>
     *
     * <p>
     * The <code>timeout</code> parameter allows you to specify a timeout after which a
     * <code>TestFailedException</code> will be thrown with a detail message indicating the <code>await</code> call
     * timed out. If no calls to <code>apply</code> have produced an exception and an insufficient number of
     * dismissals has been received by the time the <code>timeout</code> has expired, <code>await</code> will
     * complete abruptly with <code>TestFailedException</code>.
     * </p>
     *
     * <p>
     * As used here, a "check" is checking to see whether an exception has been thrown by a by-name passed
     * to <code>apply</code> or a dismissal has occurred. The "interval" is the amount
     * of time the thread that calls <code>await</code> will sleep between "checks."
     * </p>
     *
     * @param config the <code>PatienceConfig</code> object containing the <code>timeout</code> parameter
     */
    def await()(implicit config: PatienceConfig, pos: source.Position): Unit = {
      awaitImpl(config.timeout, pos)
    }

    /**
     * Wait for an exception to be produced by the by-name passed to <code>apply</code>, or one dismissal,
     * timing out after the specified timeout and sleeping an interval between checks configured
     * by an implicit <code>PatienceConfig</code>.
     *
     * <p>
     * This method may only be invoked by the thread that created the <code>Waiter</code>.
     * Each time this method completes, its internal dismissal count is reset to zero, so it can be invoked multiple times. However,
     * once <code>await</code> has completed abruptly with an exception produced during a call to <code>apply</code>, it will continue
     * to complete abruptly with that exception.
     * </p>
     *
     * <p>
     * The <code>timeout</code> parameter allows you to specify a timeout after which a
     * <code>TestFailedException</code> will be thrown with a detail message indicating the <code>await</code> call
     * timed out. If no calls to <code>apply</code> have produced an exception and an insufficient number of
     * dismissals has been received by the time the <code>timeout</code> has expired, <code>await</code> will
     * complete abruptly with <code>TestFailedException</code>.
     * </p>
     *
     * <p>
     * As used here, a "check" is checking to see whether an exception has been thrown by a by-name passed
     * to <code>apply</code> or a dismissal has occurred. The "interval" is the amount
     * of time the thread that calls <code>await</code> will sleep between "checks."
     * </p>
     *
     * @param timeout:  the <code>Timeout</code> configuration parameter containing the specified timeout
     */
    def await(timeout: Timeout)(implicit pos: source.Position): Unit = {
      awaitImpl(timeout.value, pos)
    }

    /**
     * Wait for an exception to be produced by the by-name passed to <code>apply</code>, or the specified
     * number of dismissals, sleeping an interval between checks and timing out after a timeout, both configured
     * by an implicit <code>PatienceConfig</code>.
     *
     * <p>
     * This method may only be invoked by the thread that created the <code>Waiter</code>.
     * Each time this method completes, its internal dismissal count is reset to zero, so it can be invoked multiple times. However,
     * once <code>await</code> has completed abruptly with an exception produced during a call to <code>apply</code>, it will continue
     * to complete abruptly with that exception.
     * </p>
     *
     * <p>
     * The <code>timeout</code> parameter allows you to specify a timeout after which a
     * <code>TestFailedException</code> will be thrown with a detail message indicating the <code>await</code> call
     * timed out. If no calls to <code>apply</code> have produced an exception and an insufficient number of
     * dismissals has been received by the time the <code>timeout</code> has expired, <code>await</code> will
     * complete abruptly with <code>TestFailedException</code>.
     * </p>
     *
     * <p>
     * As used here, a "check" is checking to see whether an exception has been thrown by a by-name passed
     * to <code>apply</code> or the specified number of dismissals has occurred. The "interval" is the amount
     * of time the thread that calls <code>await</code> will sleep between "checks."
     * </p>
     *
     * @param dismissals:  the <code>Dismissals</code> configuration parameter containing the number of
     *    dismissals for which to wait
     * @param config the <code>PatienceConfig</code> object containing the <code>timeout</code> parameter
     */
    def await(dismissals: Dismissals)(implicit config: PatienceConfig, pos: source.Position): Unit = {
      awaitImpl(config.timeout, pos, dismissals.value)
    }

    /**
     * Wait for an exception to be produced by the by-name passed to <code>apply</code>, or the specified
     * number of dismissals, timing out after the specified timeout and sleeping an interval between checks configured
     * by an implicit <code>PatienceConfig</code>.
     *
     * <p>
     * This method may only be invoked by the thread that created the <code>Waiter</code>.
     * Each time this method completes, its internal dismissal count is reset to zero, so it can be invoked multiple times. However,
     * once <code>await</code> has completed abruptly with an exception produced during a call to <code>apply</code>, it will continue
     * to complete abruptly with that exception.
     * </p>
     *
     * <p>
     * The <code>timeout</code> parameter allows you to specify a timeout after which a
     * <code>TestFailedException</code> will be thrown with a detail message indicating the <code>await</code> call
     * timed out. If no calls to <code>apply</code> have produced an exception and an insufficient number of
     * dismissals has been received by the time the <code>timeout</code> has expired, <code>await</code> will
     * complete abruptly with <code>TestFailedException</code>.
     * </p>
     *
     * <p>
     * As used here, a "check" is checking to see whether an exception has been thrown by a by-name passed
     * to <code>apply</code> or the specified number of dismissals has occurred. The "interval" is the amount
     * of time the thread that calls <code>await</code> will sleep between "checks."
     * </p>
     *
     * @param timeout:  the <code>Timeout</code> configuration parameter containing the specified timeout
     * @param dismissals:  the <code>Dismissals</code> configuration parameter containing the number of
     *    dismissals for which to wait
     */
    def await(timeout: Timeout, dismissals: Dismissals)(implicit pos: source.Position): Unit = {
      awaitImpl(timeout.value, pos, dismissals.value)
    }

    /**
     * Increases the dismissal count by one.
     *
     * <p>
     * Once the dismissal count has reached the value passed to <code>await</code> (and no prior invocations of <code>apply</code>
     * produced an exception), <code>await</code> will return normally.
     * </p>
     */
    def dismiss(): Unit = {
      /*
       * Synchronized to serialize access to `dismissedCount` used in the wait condition,
       * and to make the increment atomic. 
       */
      synchronized {
        dismissedCount += 1
        notifyAll()
      }
    }
  }
}

/**
 * Companion object that facilitates the importing of <code>Waiters</code> members as
 * an alternative to mixing in the trait. One use case is to import <code>Waiters</code>'s members so you can use
 * them in the Scala interpreter.
 */
object Waiters extends Waiters

