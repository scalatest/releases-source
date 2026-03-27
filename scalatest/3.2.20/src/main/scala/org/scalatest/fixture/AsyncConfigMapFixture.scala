/*
 * Copyright 2001-2014 Artima, Inc.
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
package org.scalatest.fixture

import org.scalatest._

/**
  * Trait that when mixed into a <a href="../FixtureAsyncTestSuite.html"><code>FixtureAsyncTestSuite</code></a> passes
  * the config map passed to <code>runTest</code> as a fixture into each test.
  *
  * <p>
  * Here's an example in which tests just check to make sure <code>"hello"</code> and <code>"world"</code>
  * are defined keys in the config map:
  * </p>
  *
  * <pre class="stHighlighted">
  * <span class="stReserved">package</span> org.scalatest.examples.fixture.configmapfixture
  * <br/><span class="stReserved">import</span> org.scalatest._
  * <br/><span class="stReserved">class</span> <span class="stType">ExampleAsyncSpec</span> <span class="stReserved">extends</span> <span class="stType">fixture.AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">fixture.AsyncConfigMapFixture</span> <span class="stReserved">with</span> <span class="stType">Matchers</span> {
  * <br/>  <span class="stQuotedString">"The config map"</span> should <span class="stQuotedString">"contain hello"</span> in { configMap =&gt;
  *     <span class="stLineComment">// Use the configMap passed to runTest in the test</span>
  *     configMap should contain key <span class="stQuotedString">"hello"</span>
  *   }
  * <br/>  it should <span class="stQuotedString">"contain world"</span> in { configMap =&gt;
  *     configMap should contain key <span class="stQuotedString">"world"</span>
  *   }
  * }
  * </pre>
  *
  * <p>
  * If you run this class without defining <code>"hello"</code> and <code>"world"</code>
  * in the confg map, the tests will fail:
  * </p>
  *
  * <pre class="stREPL">
  * scala&gt; org.scalatest.run(new ExampleSpec)
  * <span class="stGreen">ExampleSpec:
  * The config map</span>
  * <span class="stRed">- should contain hello *** FAILED ***
  *   Map() did not contain key "hello" (<console>:20)
  * - should contain world *** FAILED ***
  *   Map() did not contain key "world" (<console>:24)</span>
  * </pre>
  *
  * <p>
  * If you do define <code>"hello"</code> and <code>"world"</code> keys
  * in the confg map, the tests will success:
  * </p>
  *
  * <pre class="stREPL">
  * scala&gt; org.scalatest.run(new ExampleSpec, configMap = Map("hello" -&gt; "hi", "world" -&gt; "globe"))
  * <span class="stGreen">ExampleSpec:
  * The config map
  * - should contain hello
  * - should contain world</span>
  * </pre>
  *
  * @author Bill Venners
  * @author Chee Seng
  */
trait AsyncConfigMapFixture { this: FixtureAsyncTestSuite =>

  /**
    * The type of the <code>configMap</code>, which is <code>ConfigMap</code>.
    */
  type FixtureParam = ConfigMap

  /**
    * Invoke the test function, passing to the the test function the <code>configMap</code>
    * obtained by invoking <code>configMap</code> on the passed <code>OneArgTest</code>.
    *
    * <p>
    * To enable stacking of traits that define <code>withFixture(OneArgAsyncTest)</code>, this method does not
    * invoke the test function directly. Instead, it delegates responsibility for invoking the test function
    * to <code>withFixture(OneArgAsyncTest)</code>.
    * </p>
    *
    * @param test the <code>OneArgAsyncTest</code> to invoke, passing in the
    *   <code>configMap</code> fixture
    */
  def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    withFixture(test.toNoArgAsyncTest(test.configMap))
  }
}
