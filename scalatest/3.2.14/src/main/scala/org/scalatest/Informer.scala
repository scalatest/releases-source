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

import org.scalactic.source

/**
 * Trait to which custom information about a running suite of tests can be reported.
 * 
 * <p>
 * An <code>Informer</code> is essentially
 * used to wrap a <code>Reporter</code> and provide easy ways to send custom information
 * to that <code>Reporter</code> via an <code>InfoProvided</code> event.
 * <code>Informer</code> contains an <code>apply</code> method that takes a string and
 * an optional payload object of type <code>Any</code>.
 * The <code>Informer</code> will forward the passed <code>message</code> string to the
 * <a href="Reporter.html"><code>Reporter</code></a> as the <code>message</code> parameter, and the optional
 * payload object as the <code>payload</code> parameter, of an <a href="InfoProvided.html"><code>InfoProvided</code></a> event.
 * </p>
 *
 * <p>
 * Here's an example in which the <code>Informer</code> is used both directly via <code>info</code>
 * method of trait <a href="FlatSpec.html"><code>FlatSpec</code></a> and indirectly via the methods of
 * trait <a href="GivenWhenThen.html"><code>GivenWhenThen</code></a>:
 * </p>
 * 
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.flatspec.info
 * <br/><span class="stReserved">import</span> collection.mutable
 * <span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">FlatSpec</span> <span class="stReserved">with</span> <span class="stType">GivenWhenThen</span> {
 * <br/>  <span class="stQuotedString">"A mutable Set"</span> should <span class="stQuotedString">"allow an element to be added"</span> in {
 *     given(<span class="stQuotedString">"an empty mutable Set"</span>)
 *     <span class="stReserved">val</span> set = mutable.Set.empty[<span class="stType">String</span>]
 * <br/>    when(<span class="stQuotedString">"an element is added"</span>)
 *     set += <span class="stQuotedString">"clarity"</span>
 * <br/>    then(<span class="stQuotedString">"the Set should have size 1"</span>)
 *     assert(set.size === <span class="stLiteral">1</span>)
 * <br/>    and(<span class="stQuotedString">"the Set should contain the added element"</span>)
 *     assert(set.contains(<span class="stQuotedString">"clarity"</span>))
 * <br/>    info(<span class="stQuotedString">"That's all folks!"</span>)
 *   }
 * }
 * </pre>
 *
 * <p>
 * If you run this <code>SetSpec</code> from the interpreter, you will see the following output:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new SetSpec)
 * <span class="stGreen">A mutable Set
 * - should allow an element to be added
 *   + Given an empty mutable Set 
 *   + When an element is added 
 *   + Then the Set should have size 1 
 *   + And the Set should contain the added element 
 *   + That's all folks! </span>
 * </pre>
 *
 * @author Bill Venners
 */
trait Informer {
       // TODO: Make sure all the informer implementations check for null
  /**
   * Provide information and optionally, a payload, to the <code>Reporter</code> via an
   * <code>InfoProvided</code> event.
   *
   * @param message a string that will be forwarded to the wrapped <code>Reporter</code>
   *   via an <code>InfoProvided</code> event.
   * @param payload an optional object which will be forwarded to the wrapped <code>Reporter</code>
   *   as a payload via an <code>InfoProvided</code> event.
   *
   * @throws NullArgumentException if <code>message</code> or <code>payload</code> reference is <code>null</code>
   */
  def apply(message: String, payload: Option[Any] = None)(implicit pos: source.Position): Unit
}
