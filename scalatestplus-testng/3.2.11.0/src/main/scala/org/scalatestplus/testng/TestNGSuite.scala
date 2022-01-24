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
package org.scalatestplus.testng



/**
 * A suite of tests that can be run with either TestNG or ScalaTest. This class allows you to mark any
 * method as a test using TestNG's <code>@Test</code> annotation, and supports all other TestNG annotations.
 * Here's an example:
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest.testng.TestNGSuite
 * <span class="stReserved">import</span> org.testng.annotations.Test
 * <span class="stReserved">import</span> org.testng.annotations.Configuration
 * <span class="stReserved">import</span> scala.collection.mutable.ListBuffer
 * <br/><span class="stReserved">class</span> <span class="stType">MySuite</span> <span class="stReserved">extends</span> <span class="stType">TestNGSuite</span> {
 * <br/>  <span class="stReserved">var</span> sb: <span class="stType">StringBuilder</span> = _
 *   <span class="stReserved">var</span> lb: <span class="stType">ListBuffer[String]</span> = _
 * <br/>  @<span class="stType">Configuration</span>(beforeTestMethod = <span class="stReserved">true</span>)
 *   <span class="stReserved">def</span> setUpFixture() {
 *     sb = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>(<span class="stQuotedString">"ScalaTest is "</span>)
 *     lb = <span class="stReserved">new</span> <span class="stType">ListBuffer[String]</span>
 *   }
 * <br/>  @<span class="stType">Test</span>(invocationCount = <span class="stLiteral">3</span>)
 *   <span class="stReserved">def</span> easyTest() {
 *     sb.append(<span class="stQuotedString">"easy!"</span>)
 *     assert(sb.toString === <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *     assert(lb.isEmpty)
 *     lb += <span class="stQuotedString">"sweet"</span>
 *   }
 * <br/>  @<span class="stType">Test</span>(groups = <span class="stType">Array</span>(<span class="stQuotedString">"com.mycompany.groups.SlowTest"</span>))
 *   <span class="stReserved">def</span> funTest() {
 *     sb.append(<span class="stQuotedString">"fun!"</span>)
 *     assert(sb.toString === <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *     assert(lb.isEmpty)
 *   }
 * }
 * </pre>
 *
 * <p>
 * To execute <code>TestNGSuite</code>s with ScalaTest's <code>Runner</code>, you must include TestNG's jar file on the class path or runpath.
 * This version of <code>TestNGSuite</code> was tested with TestNG version 6.3.1.
 * </p>
 *
 * @author Josh Cough
 * @author Bill Venners
 */
class TestNGSuite extends TestNGSuiteLike
