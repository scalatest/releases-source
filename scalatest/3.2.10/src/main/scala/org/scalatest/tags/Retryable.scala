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
package org.scalatest.tags;




/**
 * Tag annotation that indicates a test is a candidate for retrying on either failure, cancellation, or both.
 *
 * <p>
 * <em>Note: This is actually an annotation defined in Java, not a Scala trait. It must be defined in Java instead of Scala so it will be accessible
 * at runtime. It has been inserted into Scaladoc by pretending it is a trait.</em>
 * </p>
 *
 * <p>
 * This tag annotation is intended to be used in conjunction with trait <a href="../Retries.html"><code>Retries</code></a>, to
 * identify tests that are candidates for retrying.
 * </p>
 * 
 * <p>
 * If you wish to mark all tests in a test class as being candidates for retries, you can annotate the test class with
 * <code>@Retryable</code>, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.flatspec.retryableall
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> tags.Retryable
 * <br/>@<span class="stType">Retryable</span>
 * <span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">FlatSpec</span> <span class="stReserved">with</span> <span class="stType">Retries</span> {
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgTest</span>) = {
 *     <span class="stReserved">if</span> (isRetryable(test))
 *       withRetry { <span class="stReserved">super</span>.withFixture(test) }
 *     <span class="stReserved">else</span>
 *       <span class="stReserved">super</span>.withFixture(test)
 *   }
 * <br/>  <span class="stQuotedString">"An empty Set"</span> should <span class="stQuotedString">"have size 0"</span> in {
 *     assert(Set.empty.size === <span class="stLiteral">0</span>)
 *   }
 * <br/>  it should <span class="stQuotedString">"produce NoSuchElementException when head is invoked"</span> in {
 *     intercept[<span class="stType">NoSuchElementException</span>] {
 *       Set.empty.head
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * When you mark a test class with a tag annotation, ScalaTest will mark each test defined in that class with that tag.
 * Thus, marking the <code>SetSpec</code> in the above example with the <code>@Retryable</code> tag annotation means that both tests
 * in the class are candidates for retrying.
 * </p>
 *
 * <p>
 * Another use case for <code>@Retryable</code> is to mark test <em>methods</em> as being candidates for retries in traits
 * <a href="Spec.html"><code>Spec</code></a> and <a href="fixture/Spec.html"><code>fixture.Spec</code></a>. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.spec.disk
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> tags.Disk
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">RefSpec</span> <span class="stReserved">with</span> <span class="stType">Retries</span> {
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgTest</span>) = {
 *     <span class="stReserved">if</span> (isRetryable(test))
 *       withRetry { <span class="stReserved">super</span>.withFixture(test) }
 *     <span class="stReserved">else</span>
 *       <span class="stReserved">super</span>.withFixture(test)
 *   }
 * <br/>  @<span class="stType">Retryable</span> <span class="stReserved">def</span> <span class="literalIdentifier">&#96;an empty Set should have size 0&#96;</span> {
 *     assert(Set.empty.size === <span class="stLiteral">0</span>)
 *   }
 * <br/>  <span class="stReserved">def</span> <span class="literalIdentifier">&#96;invoking head on an empty Set should produce NoSuchElementException&#96;</span> {
 *     intercept[<span class="stType">NoSuchElementException</span>] {
 *       Set.empty.head
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * The main use case of annotating a test or suite of tests is to select or deselect them during runs by supplying tags to include and/or exclude. For more information,
 * see the relevant section in the documentation of object <a href="../tools/Runner$.html#specifyingTagsToIncludeAndExclude"><code>Runner</code></a>.
 * </p>
 * 
 * <p>
 * Note that because reflection is not supported on Scala.js, this annotation will only work on the JVM, not on Scala.js.
 * </p>
 */




 trait Retryable extends java.lang.annotation.Annotation 
