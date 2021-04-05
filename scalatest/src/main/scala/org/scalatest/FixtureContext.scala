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
package org.scalatest

/**
 * Marker trait for fixture-context objects, that enables them
 * to be used in testing styles that require type <code>Assertion</code>
 *
 * <p>
 * A fixture-context object is a way to share fixtures between different
 * tests that is most useful when different tests need different combinations
 * of fixture objects. The fixture-context object technique is only
 * appropriate if you don't need to clean up the fixtures after using them.
 * </p>
 *
 * <p>
 * To use this technique, you define instance variables intialized with fixture
 * objects in traits and/or classes, then in each test instantiate an object that
 * contains just the fixture objects needed by the test. Traits allow you to mix
 * together just the fixture objects needed by each test, whereas classes
 * allow you to pass data in via a constructor to configure the fixture objects.
 * Here's an example <code>FlatSpec</code> in which fixture objects are partitioned
 * into two traits and each test just mixes together the traits it needs:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.flatspec.fixturecontext
 * <br/><span class="stReserved">import</span> collection.mutable.ListBuffer
 * <span class="stReserved">import</span> org.scalatest.FlatSpec
 * <span class="stReserved">import</span> org.scalatest.FixtureContext
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">FlatSpec</span> {
 * <br/>  <span class="stReserved">trait</span> <span class="stType">Builder</span> <span class="stReserved">extends</span> <span class="stType">FixtureContext</span> {
 *     <span class="stReserved">val</span> builder = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>(<span class="stQuotedString">"ScalaTest is "</span>)
 *   }
 * <br/>  <span class="stReserved">trait</span> <span class="stType">Buffer</span> <span class="stReserved">extends</span> <span class="stType">FixtureContext</span> {
 *     <span class="stReserved">val</span> buffer = <span class="stType">ListBuffer</span>(<span class="stQuotedString">"ScalaTest"</span>, <span class="stQuotedString">"is"</span>)
 *   }
 * <br/>  <span class="stLineComment">// This test needs the StringBuilder fixture</span>
 *   <span class="stQuotedString">"Testing"</span> should <span class="stQuotedString">"be productive"</span> in <span class="stReserved">new</span> <span class="stType">Builder</span> {
 *     builder.append(<span class="stQuotedString">"productive!"</span>)
 *     assert(builder.toString === <span class="stQuotedString">"ScalaTest is productive!"</span>)
 *   }
 * <br/>  <span class="stLineComment">// This test needs the ListBuffer[String] fixture</span>
 *   <span class="stQuotedString">"Test code"</span> should <span class="stQuotedString">"be readable"</span> in <span class="stReserved">new</span> <span class="stType">Buffer</span> {
 *     buffer += (<span class="stQuotedString">"readable!"</span>)
 *     assert(buffer === <span class="stType">List</span>(<span class="stQuotedString">"ScalaTest"</span>, <span class="stQuotedString">"is"</span>, <span class="stQuotedString">"readable!"</span>))
 *   }
 * <br/>  <span class="stLineComment">// This test needs both the StringBuilder and ListBuffer</span>
 *   it should <span class="stQuotedString">"be clear and concise"</span> in <span class="stReserved">new</span> <span class="stType">Builder</span> <span class="stReserved">with</span> <span class="stType">Buffer</span> {
 *     builder.append(<span class="stQuotedString">"clear!"</span>)
 *     buffer += (<span class="stQuotedString">"concise!"</span>)
 *     assert(builder.toString === <span class="stQuotedString">"ScalaTest is clear!"</span>)
 *     assert(buffer === <span class="stType">List</span>(<span class="stQuotedString">"ScalaTest"</span>, <span class="stQuotedString">"is"</span>, <span class="stQuotedString">"concise!"</span>))
 *   }
 * }
 * </pre>
 *
 * <p>
 * Extending <code>FixtureContext</code>, which extends trait <code>org.scalatest.compatible.Assertion</code> makes
 * it more convenient to use fixture-context objects in styles, such as async styles, that require test bodies
 * to have type <code>Assertion</code>.
 * </p>
 */
trait FixtureContext extends compatible.Assertion

