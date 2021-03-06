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
package org.scalatest.fixture

import org.scalatest._

/**
  * Trait that when mixed into a <a href="../FixtureAsyncTestSuite.html"><code>FixtureAsyncTestSuite</code></a> passes the
  * <a href="../TestData.html"><code>TestData</code></a> passed to <code>withFixture</code> as a fixture into each test.
  *
  * <p>
  * For example, here's how you could access the test's name in each test using <code>AsyncTestDataFixture</code>:
  * </p>
  *
  * <pre class="stHighlighted">
  * <span class="stReserved">package</span> org.scalatest.examples.fixture.testdatafixture
  * <br/><span class="stReserved">import</span> org.scalatest._
  * <br/><span class="stReserved">class</span> <span class="stType">ExampleAsyncSpec</span> <span class="stReserved">extends</span> <span class="stType">fixture.AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">fixture.AsyncTestDataFixture</span> {
  * <br/>  <span class="stQuotedString">"Accessing the test data"</span> should <span class="stQuotedString">"be easy!"</span> in { td =&gt;
  *     assert(td.name == <span class="stQuotedString">"Accessing the test data should be easy!"</span>)
  *   }
  * <br/>  it should <span class="stQuotedString">"be fun!"</span> in { td =&gt;
  *     assert(td.name == <span class="stQuotedString">"Accessing the test data should be fun!"</span>)
  *   }
  * }
  * </pre>
  *
  * @author Bill Venners
  */
trait AsyncTestDataFixture { this: FixtureAsyncTestSuite =>

  /**
    * The type of the fixture, which is <code>TestData</code>.
    */
  type FixtureParam = TestData

  /**
    * Invoke the test function, passing to the the test function
    * the <code>TestData</code> for the test.
    *
    * <p>
    * To enable stacking of traits that define <code>withFixture(NoArgTest)</code>, this method does not
    * invoke the test function directly. Instead, it delegates responsibility for invoking the test function
    * to <code>withFixture(NoArgTest)</code>.
    * </p>
    *
    * @param test the <code>OneArgTest</code> to invoke, passing in the
    *   <code>TestData</code> fixture
    * @return an <code>Outcome</code> instance
    */
  def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    withFixture(test.toNoArgAsyncTest(test))
  }
}
