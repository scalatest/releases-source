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
package org.scalatest


/**
 * Trait defining abstract "lifecycle" methods that are implemented in <a href="AsyncTestSuite.html#lifecycle-methods"><code>AsyncTestSuite</code></a>
 * and can be overridden in stackable modification traits.
 *
 * <p>
 * The main use case for this trait is to override <code>withFixture</code> in a mixin trait.
 * Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">trait</span> <span class="stType">Builder</span> <span class="stReserved">extends</span> <span class="stType">AsyncTestSuiteMixin</span> { <span class="stReserved">this</span>: <span class="stType">AsyncTestSuite</span> =&gt;
 * <br/>  <span class="stReserved">final</span> <span class="stReserved">val</span> builder = <span class="stReserved">new</span> <span class="stType">ThreadSafeStringBuilder</span>
 * <br/>  <span class="stReserved">abstract</span> <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgAsyncTest</span>) = {
 *     builder.append(<span class="stQuotedString">"ScalaTest is "</span>)
 *     complete {
 *       <span class="stReserved">super</span>.withFixture(test) <span class="stLineComment">// To be stackable, must call super.withFixture</span>
 *     } lastly {
 *       builder.clear()
 *     }
 *   }
 * }
 * </pre>
 */
trait AsyncTestSuiteMixin extends SuiteMixin { this: AsyncTestSuite =>

  /**
   * Run the passed test function in the context of a fixture established by this method.
   *
   * <p>
   * This method should set up the fixture needed by the tests of the
   * current suite, invoke the test function, and if needed, register a callback
   * on the resulting <code>FutureOutcome</code> to perform any clean
   * up needed after the test completes. Because the <code>NoArgAsyncTest</code> function
   * passed to this method takes no parameters, preparing the fixture will require
   * side effects, such as reassigning instance <code>var</code>s in this <code>Suite</code> or initializing
   * a globally accessible external database. If you want to avoid reassigning instance <code>var</code>s
   * you can use <a href="FixtureAsyncTestSuite.html">FixtureAsyncTestSuite</a>.
   * </p>
   *
   * @param test the no-arg async test function to run with a fixture
   */
  protected def withFixture(test: NoArgAsyncTest): FutureOutcome
}

