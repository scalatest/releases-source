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

import org.scalactic._
import org.scalatest.exceptions.StackDepthException
import org.scalatest.exceptions.TestFailedException

/**
 * Trait that provides an implicit conversion that adds a <code>valueAt</code> method
 * to <code>PartialFunction</code>, which will return the value (result) of the function applied to the argument passed to <code>valueAt</code>,
 * or throw <code>TestFailedException</code> if the partial function is not defined at the argument.
 *
 * <p>
 * This construct allows you to express in one statement that a partial function should be defined for a particular input,
 * and that its result value should meet some expectation. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * pf.valueAt(<span class="stQuotedString">"IV"</span>) should equal (<span class="stLiteral">4</span>)
 * </pre>
 *
 * <p>
 * Or, using an assertion instead of a matcher expression:
 * </p>
 *
 * <pre class="stHighlighted">
 * assert(pf.valueAt(<span class="stQuotedString">"IV"</span>) === <span class="stLiteral">4</span>)
 * </pre>
 *
 * <p>
 * Were you to simply invoke <code>apply</code> on the <code>PartialFunction</code>, passing in an input value, 
 * if the partial function wasn't defined at that input, it would throw some exception, but likely not one
 * that provides a <a href="exceptions/StackDepth.html">stack depth</a>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stLineComment">// Note: a Map[K, V] is a PartialFunction[K, V]</span>
 * <span class="stReserved">val</span> pf: <span class="stType">PartialFunction[String, Int]</span> = <span class="stType">Map</span>(<span class="stQuotedString">"I"</span> -&gt; <span class="stLiteral">1</span>, <span class="stQuotedString">"II"</span> -&gt; <span class="stLiteral">2</span>, <span class="stQuotedString">"III"</span> -&gt; <span class="stLiteral">3</span>, <span class="stQuotedString">"IV"</span> -&gt; <span class="stLiteral">4</span>)
 * <br/>pf(<span class="stQuotedString">"V"</span>) should equal (<span class="stLiteral">5</span>) <span class="stLineComment">// pf("V") throws NoSuchElementException</span>
 * </pre>
 *
 * <p>
 * The <code>NoSuchElementException</code> thrown in this situation would cause the test to fail, but without providing a stack depth pointing
 * to the failing line of test code. This stack depth, provided by <a href="exceptions/TestFailedException.html"><code>TestFailedException</code></a> (and a
 * few other ScalaTest exceptions), makes it quicker for
 * users to navigate to the cause of the failure. Without <code>PartialFunctionValues</code>, to get
 * a stack depth exception you would need to make two statements, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> pf: <span class="stType">PartialFunction[String, Int]</span> = <span class="stType">Map</span>(<span class="stQuotedString">"I"</span> -&gt; <span class="stLiteral">1</span>, <span class="stQuotedString">"II"</span> -&gt; <span class="stLiteral">2</span>, <span class="stQuotedString">"III"</span> -&gt; <span class="stLiteral">3</span>, <span class="stQuotedString">"IV"</span> -&gt; <span class="stLiteral">4</span>)
 * <br/>pf.isDefinedAt(<span class="stQuotedString">"V"</span>) should be (<span class="stReserved">true</span>) <span class="stLineComment">// throws TestFailedException</span>
 * pf(<span class="stQuotedString">"V"</span>) should equal (<span class="stLiteral">5</span>)
 * </pre>
 *
 * <p>
 * The <code>PartialFunctionValues</code> trait allows you to state that more concisely:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> pf: <span class="stType">PartialFunction[String, Int]</span> = <span class="stType">Map</span>(<span class="stQuotedString">"I"</span> -&gt; <span class="stLiteral">1</span>, <span class="stQuotedString">"II"</span> -&gt; <span class="stLiteral">2</span>, <span class="stQuotedString">"III"</span> -&gt; <span class="stLiteral">3</span>, <span class="stQuotedString">"IV"</span> -&gt; <span class="stLiteral">4</span>)
 * <br/>pf.valueAt(<span class="stQuotedString">"V"</span>) should equal (<span class="stLiteral">5</span>) <span class="stLineComment">// pf.valueAt("V") throws TestFailedException</span>
 * </pre>
 */
trait PartialFunctionValues {

  import scala.language.implicitConversions

  /**
   * Implicit conversion that adds a <code>valueAt</code> method to <code>PartialFunction</code>.
   *
   * @param pf the <code>PartialFunction</code> on which to add the <code>valueAt</code> method
   */
  implicit def convertPartialFunctionToValuable[A, B](pf: PartialFunction[A, B])(implicit pos: source.Position): Valuable[A, B] = new Valuable(pf, pos)
  
  /**
   * Wrapper class that adds a <code>valueAt</code> method to <code>PartialFunction</code>, allowing
   * you to make statements like:
   *
   * <pre class="stHighlighted">
   * pf.valueAt(<span class="stQuotedString">"VI"</span>) should equal (<span class="stLiteral">6</span>)
   * </pre>
   *
   * @param pf An <code>PartialFunction</code> to convert to <code>Valuable</code>, which provides the <code>valueAt</code> method.
   */
  class Valuable[A, B](pf: PartialFunction[A, B], pos: source.Position) {

    /**
     * Returns the result of applying the wrapped <code>PartialFunction</code> to the passed input, if it is defined at that input, else
     * throws <code>TestFailedException</code> with a detail message indicating the <code>PartialFunction</code> was not defined at the given input.
     */
    def valueAt(input: A): B = {
      if (pf.isDefinedAt(input)) {
        pf.apply(input)
      }
      else
        throw new TestFailedException((_: StackDepthException) => Some(Resources.partialFunctionValueNotDefined(input.toString)), None, pos)
    }
  }
}

/**
 * Companion object that facilitates the importing of <code>PartialFunctionValues</code> members as 
 * an alternative to mixing it in. One use case is to import <code>PartialFunctionValues</code>'s members so you can use
 * the <code>valueAt</code> method on <code>PartialFunction</code> in the Scala interpreter:
 *
 * <pre class="stREPL">
 * $ scala -cp scalatest-1.7.jar
 * Welcome to Scala version 2.9.1.final (Java HotSpot(TM) 64-Bit Server VM, Java 1.6.0_29).
 * Type in expressions to have them evaluated.
 * Type :help for more information.
 * 
 * scala&gt; import org.scalatest._
 * import org.scalatest._
 * 
 * scala&gt; import matchers.Matchers._
 * import matchers.Matchers._
 * 
 * scala&gt; import PartialFunctionValues._
 * import PartialFunctionValues._
 * 
 * scala&gt; val pf: PartialFunction[String, Int] = Map("I" -&gt; 1, "II" -&gt; 2, "III" -&gt; 3, "IV" -&gt; 4)
 * pf: PartialFunction[String,Int] = Map(I -&gt; 1, II -&gt; 2, III -&gt; 3, IV -&gt; 4)
 * 
 * scala&gt; pf("IV") should equal (4)
 * 
 * scala&gt; pf("V") should equal (5)
 * java.util.NoSuchElementException: key not found: V
 *   at scala.collection.MapLike$class.default(MapLike.scala:224)
 *   at scala.collection.immutable.Map$Map4.default(Map.scala:167)
 *   ...
 * </pre>
 */
object PartialFunctionValues extends PartialFunctionValues
