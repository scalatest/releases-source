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
package org.scalatest;



/**
 * Annotation used to indicate that an otherwise discoverable test class should not be discovered.
 *
 * <p>
 * <em>Note: This is actually an annotation defined in Java, not a Scala trait. It must be defined in Java instead of Scala so it will be accessible
 * at runtime. It has been inserted into Scaladoc by pretending it is a trait.</em>
 * </p>
 *
 * <p>
 * ScalaTest will discover any class that either extends <a href="Suite.html"><code>Suite</code></a> and has a public, no-arg constructor, or is annotated with
 * a valid <a href="WrapWith.html"><code>WrapWith</code></a> annotation. If you wish to prevent a class from being discovered, simply annotate it
 * with <code>DoNotDiscover</code>, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest._
 * <br/>@<span class="stType">DoNotDiscover</span>
 * <span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">FlatSpec</span> {
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
 * ScalaTest will run classes annotated with <code>DoNotDiscover</code> if asked to explicitly, it just won't discover them. 
 * </p>
 * 
 * <p>
 * Note that because reflection is not supported on Scala.js, this annotation will only work on the JVM, not on Scala.js.
 * </p>
 */



 trait DoNotDiscover extends java.lang.annotation.Annotation 
