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
package org.scalatestplus.junit;




/**
 * A suite of tests that can be run with either JUnit or ScalaTest. This class allows you to write JUnit 4 tests
 * with ScalaTest's more concise assertion syntax as well as JUnit's assertions (<code>assertEquals</code>, etc.).
 * You create tests by defining methods that are annotated with <code>Test</code>, and can create fixtures with
 * methods annotated with <code>Before</code> and <code>After</code>. For example:
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest.junit.JUnitSuite
 * <span class="stReserved">import</span> scala.collection.mutable.ListBuffer
 * <span class="stReserved">import</span> _root_.org.junit.Test
 * <span class="stReserved">import</span> _root_.org.junit.Before
 * <br/><span class="stReserved">class</span> <span class="stType">TwoSuite</span> <span class="stReserved">extends</span> <span class="stType">JUnitSuite</span> {
 * <br/>  <span class="stReserved">var</span> sb: <span class="stType">StringBuilder</span> = _
 *   <span class="stReserved">var</span> lb: <span class="stType">ListBuffer[String]</span> = _
 * <br/>  @<span class="stType">Before</span> <span class="stReserved">def</span> initialize() {
 *     sb = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>(<span class="stQuotedString">"ScalaTest is "</span>)
 *     lb = <span class="stReserved">new</span> <span class="stType">ListBuffer[String]</span>
 *   }
 * <br/>  @<span class="stType">Test</span> <span class="stReserved">def</span> verifyEasy() {
 *     sb.append(<span class="stQuotedString">"easy!"</span>)
 *     assert(sb.toString === <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *     assert(lb.isEmpty)
 *     lb += <span class="stQuotedString">"sweet"</span>
 *   }
 * <br/>  @<span class="stType">Test</span> <span class="stReserved">def</span> verifyFun() {
 *     sb.append(<span class="stQuotedString">"fun!"</span>)
 *     assert(sb.toString === <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *     assert(lb.isEmpty)
 *   }
 * }
 * </pre>
 *
 * <p>
 * To execute <code>JUnitSuite</code>s with ScalaTest's <code>Runner</code>, you must include JUnit's jar file on the class path or runpath.
 * This version of <code>JUnitSuite</code> was tested with JUnit version 4.10.
 * </p>
 *
 * <p>
 * Instances of this class are not thread safe.
 * </p>
 *
 * @author Bill Venners
 * @author Daniel Watson
 * @author Joel Neely
 */
class JUnitSuite extends JUnitSuiteLike
