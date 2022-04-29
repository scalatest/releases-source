/*
 * Copyright 2001-2016 Artima, Inc.
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

import org.scalatest.exceptions.TimeoutField
import org.scalatest.time.Span
import org.scalatest._

/**
 * Trait that when mixed into an asynchronous suite class establishes a time limit for its tests.
 *
 * <p>
 * This trait overrides <code>withFixture</code>, wrapping a <code>super.withFixture(test)</code> call
 * in a <code>failAfter</code> invocation, specifying a timeout obtained by invoking <code>timeLimit</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * failAfter(timeLimit) {
 *   <span class="stReserved">super</span>.withFixture(test)
 * }
 * </pre>
 *
 * <p>
 * Note that the <code>failAfter</code> method executes the body of the by-name passed to it using the same
 * thread that invoked <code>failAfter</code>. This means that the calling of <code>withFixture</code> method
 * will be run using the same thread, but the test body may be run using a different thread, depending on the
 * <code>executionContext</code> set at the <code>AsyncTestSuite</code> level.
 * </p>
 *
 * <p>
 * The <code>timeLimit</code> field is abstract in this trait. Thus you must specify a time limit when you use it.
 * For example, the following code specifies that each test must complete within 200 milliseconds:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest.AsyncFunSpec
 * <span class="stReserved">import</span> org.scalatest.concurrent.AsyncTimeLimitedTests
 * <span class="stReserved">import</span> org.scalatest.time.SpanSugar._
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">AsyncFunSpec</span> <span class="stReserved">with</span> <span class="stType">AsyncTimeLimitedTests</span> {
 * <br/>  <span class="stLineComment">// Note: You may need to either write 200.millis or (200 millis), or</span>
 *   <span class="stLineComment">// place a semicolon or blank line after plain old 200 millis, to</span>
 *   <span class="stLineComment">// avoid the semicolon inference problems of postfix operator notation.</span>
 *   <span class="stReserved">val</span> timeLimit = <span class="stLiteral">200</span> millis
 * <br/>  describe(<span class="stQuotedString">"An asynchronous time-limited test"</span>) {
 *     it(<span class="stQuotedString">"should succeed if it completes within the time limit"</span>) {
 *       Thread.sleep(<span class="stLiteral">100</span>)
 *       succeed
 *     }
 *     it(<span class="stQuotedString">"should fail if it is taking too darn long"</span>) {
 *       Thread.sleep(<span class="stLiteral">300</span>)
 *       succeed
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * If you run the above <code>ExampleSpec</code>, the second test will fail with the error message:
 * </p>
 *
 * <p>
 * <code>The test did not complete within the specified 200 millisecond time limit.</code>
 * </p>
 *
 * <p>
 * Different from <a href="TimeLimitedTests.html"><code>TimeLimitedTests</code></a>, <code>AsyncTimeLimitedTests</code> does not
 * support <code>Interruptor</code> for now.
 * </p>
 *
 * @author Bill Venners
 * @author Chua Chee Seng
 */
trait AsyncTimeLimitedTests extends AsyncTestSuiteMixin with TimeLimits { this: AsyncTestSuite =>

  /**
    * A stackable implementation of <code>withFixture</code> that wraps a call to <code>super.withFixture</code> in a
    * <code>failAfter</code> invocation.
    *
    * @param test the test on which to enforce a time limit
    */
  abstract override def withFixture(test: NoArgAsyncTest): FutureOutcome = {
    try {
      failAfter(timeLimit) {
        super.withFixture(test)
      } change { outcome =>
        outcome match {
          case Exceptional(e: org.scalatest.exceptions.ModifiableMessage[_] with TimeoutField) =>
            Exceptional(e.modifyMessage(opts => Some(Resources.testTimeLimitExceeded(e.timeout.prettyString))))
          case other => other
        }
      }
    }
    catch {
      case e: org.scalatest.exceptions.ModifiableMessage[_] with TimeoutField =>
        throw e.modifyMessage(opts => Some(Resources.testTimeLimitExceeded(e.timeout.prettyString)))
      case other: Throwable => throw other
    }
  }

  /**
   * The time limit, in [[org.scalatest.time.Span <code>Span</code>]], in which each test in a <code>AsyncTestSuite</code> that mixes in
   * <code>AsyncTimeLimitedTests</code> must complete.
   */
  def timeLimit: Span
}
