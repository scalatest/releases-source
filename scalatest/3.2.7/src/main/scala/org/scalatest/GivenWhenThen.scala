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
 * Trait that contains methods named <code>given</code>, <code>when</code>, <code>then</code>, and <code>and</code>,
 * which take a string message and implicit <a href="Informer.html"><code>Informer</code></a>, and forward the message to the informer.
 *
 * <p>
 * Here's an example:
 * </p>
 * 
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.flatspec.info
 * <br/><span class="stReserved">import</span> collection.mutable
 * <span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">FlatSpec</span> <span class="stReserved">with</span> <span class="stType">GivenWhenThen</span> {
 * <br/>  <span class="stQuotedString">"A mutable Set"</span> should <span class="stQuotedString">"allow an element to be added"</span> in {
 *     <span class="stType">Given</span>(<span class="stQuotedString">"an empty mutable Set"</span>)
 *     <span class="stReserved">val</span> set = mutable.Set.empty[<span class="stType">String</span>]
 * <br/>    <span class="stType">When</span>(<span class="stQuotedString">"an element is added"</span>)
 *     set += <span class="stQuotedString">"clarity"</span>
 * <br/>    <span class="stType">Then</span>(<span class="stQuotedString">"the Set should have size 1"</span>)
 *     assert(set.size === <span class="stLiteral">1</span>)
 * <br/>    <span class="stType">And</span>(<span class="stQuotedString">"the Set should contain the added element"</span>)
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
trait GivenWhenThen { this: Informing =>

  /**
   * Forwards a message to an implicit <code>Informer</code>, preceded by "Given."
   *
   * @param message the message to forward to the passed informer
   */
  def Given(message: String)(implicit pos: source.Position): Unit = {
    info(Resources.givenMessage(message))(pos)
  }
  
  /**
   * Forwards a message to an implicit <code>Informer</code>, preceded by "When ".
   *
   * @param message the message to forward to the passed informer
   */
  def When(message: String)(implicit pos: source.Position): Unit = {
    info(Resources.whenMessage(message))(pos)
  }
  
  /**
   * Forwards a message to an implicit <code>Informer</code>, preceded by "Then ".
   *
   * @param message the message to forward to the passed informer
   */
  def Then(message: String)(implicit pos: source.Position): Unit = {
    info(Resources.thenMessage(message))(pos)
  }
  
   /**
   * Forwards a message to an implicit <code>Informer</code>, preceded by "And ".
   *
   * @param message the message to forward to the passed informer
   */
  def And(message: String)(implicit pos: source.Position): Unit = {
    info(Resources.andMessage(message))(pos)
  }
}
