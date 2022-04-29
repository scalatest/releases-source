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
import org.scalatest.fixture
import org.scalatest.OutcomeOf.outcomeOf

/**
 * Trait that can pass a new <code>Conductor</code> fixture into tests.
 *
 * <p>
 * Here's an example of the use of this trait to test the <code>ArrayBlockingQueue</code>
 * class from <code>java.util.concurrent</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest.funsuite
 * <span class="stReserved">import</span> org.scalatest.concurrent.ConductorFixture
 * <span class="stReserved">import</span> org.scalatest.matchers.Matchers
 * <span class="stReserved">import</span> java.util.concurrent.ArrayBlockingQueue
 * <br/><span class="stReserved">class</span> <span class="stType">ArrayBlockingQueueSuite</span> <span class="stReserved">extends</span> <span class="stType">FixtureFunSuite</span> <span class="stReserved">with</span> <span class="stType">ConductorFixture</span> <span class="stReserved">with</span> <span class="stType">Matchers</span> {
 * <br/>  test(<span class="stQuotedString">"calling put on a full queue blocks the producer thread"</span>) { conductor =&gt; <span class="stReserved">import</span> conductor._
 * <br/>    <span class="stReserved">val</span> buf = <span class="stReserved">new</span> <span class="stType">ArrayBlockingQueue[Int]</span>(<span class="stLiteral">1</span>)
 * <br/>    thread(<span class="stQuotedString">"producer"</span>) {
 *       buf put <span class="stLiteral">42</span>
 *       buf put <span class="stLiteral">17</span>
 *       beat should be (<span class="stLiteral">1</span>)
 *     }
 * <br/>    thread(<span class="stQuotedString">"consumer"</span>) {
 *       waitForBeat(<span class="stLiteral">1</span>)
 *       buf.take should be (<span class="stLiteral">42</span>)
 *       buf.take should be (<span class="stLiteral">17</span>)
 *     }
 * <br/>    whenFinished {
 *       buf should be (<span class="stQuotedString">'empty</span>)
 *     }
 *   }
 * <br/>  test(<span class="stQuotedString">"calling take on an empty queue blocks the consumer thread"</span>) { conductor =&gt; <span class="stReserved">import</span> conductor._
 * <br/>    <span class="stReserved">val</span> buf = <span class="stReserved">new</span> <span class="stType">ArrayBlockingQueue[Int]</span>(<span class="stLiteral">1</span>)
 * <br/>    thread(<span class="stQuotedString">"producer"</span>) {
 *       waitForBeat(<span class="stLiteral">1</span>)
 *       buf put <span class="stLiteral">42</span>
 *       buf put <span class="stLiteral">17</span>
 *     }
 * <br/>    thread(<span class="stQuotedString">"consumer"</span>) {
 *       buf.take should be (<span class="stLiteral">42</span>)
 *       buf.take should be (<span class="stLiteral">17</span>)
 *       beat should be (<span class="stLiteral">1</span>)
 *     }
 * <br/>    whenFinished {
 *       buf should be (<span class="stQuotedString">'empty</span>)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * For an explanation of how these tests work, see the documentation for <a href="Conductors.html"><code>Conductors</code></a>.
 * </p>
 *
 * @author Bill Venners
 */
trait ConductorFixture extends TestSuiteMixin with Conductors { this: FixtureTestSuite =>

  /**
   * Defines type <code>Fixture</code> to be <code>Conductor</code>.
   */
  type FixtureParam = Conductor
  
  /**
   * Creates a new <code>Conductor</code>, passes the <code>Conductor</code> to the
   * specified test function, and ensures that <code>conduct</code> gets invoked
   * on the <code>Conductor</code>.
   *
   * <p>
   * After the test function returns (so long as it returns normally and doesn't
   * complete abruptly with an exception), this method will determine whether the
   * <code>conduct</code> method has already been called (by invoking
   * <code>conductingHasBegun</code> on the <code>Conductor</code>). If not,
   * this method will invoke <code>conduct</code> to ensure that the
   * multi-threaded scenario is actually conducted.
   * </p>
   *
   * <p>
   * This trait is stackable with other traits that override <code>withFixture(NoArgTest)</code>, because
   * instead of invoking the test function directly, it delegates responsibility for invoking the test
   * function to <code>withFixture(NoArgTest)</code>.
   * </p>
   */
  def withFixture(test: OneArgTest): Outcome = {
    val conductor = new Conductor
    withFixture(test.toNoArgTest(conductor)) match {
      case Succeeded if !conductor.conductingHasBegun =>
        outcomeOf { conductor.conduct() }
      case other => other
    }
  }
}
