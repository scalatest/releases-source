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
package org.scalatest.tagobjects

import org.scalatest.Tag

/**
 * Tag object that indicates a test is a candidate for retrying on either failure, cancellation, or both.
 *
 * <p>
 * This tag object is intended to be used in conjunction with trait <a href="../Retries.html"><code>Retries</code></a>, to
 * identify tests that are candidates for retrying.
 * </p>
 * 
 * <p>
 * The corresponding tag annotation for this tag object is <code>org.scalatest.tags.Retryable</code>.
 * This tag object can be used to tag test functions (in style traits other than <code>Spec</code>, in which tests are methods
 * not functions) as being a candidate for retries.  See the "tagging tests" section in the documentation for your chosen styles to
 * see the syntax. Here's an example for <code>FlatSpec</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.tagobjects.retryable
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> tagobjects.Retryable
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">FlatSpec</span> <span class="stReserved">with</span> <span class="stType">Retries</span> {
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgTest</span>) = {
 *     <span class="stReserved">if</span> (isRetryable(test))
 *       withRetry { <span class="stReserved">super</span>.withFixture(test) }
 *     <span class="stReserved">else</span>
 *       <span class="stReserved">super</span>.withFixture(test)
 *   }
 * <br/>  <span class="stQuotedString">"An empty Set"</span> should <span class="stQuotedString">"have size 0"</span> taggedAs(<span class="stType">Retryable</span>) in {
 *     assert(Set.empty.size === <span class="stLiteral">0</span>)
 *   }
 * }
 * </pre>
 */
object Retryable extends Tag("org.scalatest.tags.Retryable") 
