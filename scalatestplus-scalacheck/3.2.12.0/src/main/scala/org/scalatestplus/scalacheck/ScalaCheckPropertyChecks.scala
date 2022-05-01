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
package org.scalatestplus
package scalacheck

import org.scalatest.prop.TableDrivenPropertyChecks

/**
  * Trait that facilitates property checks on data supplied by tables and ScalaCheck generators.
  *
  * <p>
  * This trait extends both <a href="TableDrivenPropertyChecks.html"><code>TableDrivenPropertyChecks</code></a> and
  * <a href="ScalaCheckDrivenPropertyChecks.html"><code>ScalaCheckDrivenPropertyChecks</code></a>. Thus by mixing in
  * this trait you can perform property checks on data supplied either by tables or generators. For the details of
  * table- and generator-driven property checks, see the documentation for each by following the links above.
  * </p>
  *
  * <p>
  * For a quick example of using both table and generator-driven property checks in the same suite of tests, however,
  * imagine you want to test this <code>Fraction</code> class:
  * </p>
  *
  * <pre class="stHighlighted">
  * <span class="stReserved">class</span> <span class="stType">Fraction</span>(n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) {
  * <br/>  require(d != <span class="stLiteral">0</span>)
  *   require(d != Integer.MIN_VALUE)
  *   require(n != Integer.MIN_VALUE)
  * <br/>  <span class="stReserved">val</span> numer = <span class="stReserved">if</span> (d &lt; <span class="stLiteral">0</span>) -<span class="stLiteral">1</span> * n <span class="stReserved">else</span> n
  *   <span class="stReserved">val</span> denom = d.abs
  * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> toString = numer + <span class="stQuotedString">" / "</span> + denom
  * }
  * </pre>
  *
  * <p>
  * If you mix in <code>PropertyChecks</code>, you could use a generator-driven property check to test that the passed values for numerator and
  * denominator are properly normalized, like this:
  * </p>
  *
  * <pre class="stHighlighted">
  * forAll { (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) =&gt;
  * <br/>  whenever (d != <span class="stLiteral">0</span> && d != Integer.MIN_VALUE
  *       && n != Integer.MIN_VALUE) {
  * <br/>    <span class="stReserved">val</span> f = <span class="stReserved">new</span> <span class="stType">Fraction</span>(n, d)
  * <br/>    <span class="stReserved">if</span> (n &lt; <span class="stLiteral">0</span> && d &lt; <span class="stLiteral">0</span> || n &gt; <span class="stLiteral">0</span> && d &gt; <span class="stLiteral">0</span>)
  *       f.numer should be &gt; <span class="stLiteral">0</span>
  *     <span class="stReserved">else</span> <span class="stReserved">if</span> (n != <span class="stLiteral">0</span>)
  *       f.numer should be &lt; <span class="stLiteral">0</span>
  *     <span class="stReserved">else</span>
  *       f.numer shouldEqual <span class="stLiteral">0</span>
  * <br/>    f.denom should be &gt; <span class="stLiteral">0</span>
  *   }
  * }
  * </pre>
  *
  * <p>
  * And you could use a table-driven property check to test that all combinations of invalid values passed to the <code>Fraction</code> constructor
  * produce the expected <code>IllegalArgumentException</code>, like this:
  * </p>
  *
  * <pre class="stHighlighted">
  * <span class="stReserved">val</span> invalidCombos =
  *   <span class="stType">Table</span>(
  *     (<span class="stQuotedString">"n"</span>,               <span class="stQuotedString">"d"</span>),
  *     (Integer.MIN_VALUE, Integer.MIN_VALUE),
  *     (<span class="stLiteral">1</span>,                 Integer.MIN_VALUE),
  *     (Integer.MIN_VALUE, <span class="stLiteral">1</span>),
  *     (Integer.MIN_VALUE, <span class="stLiteral">0</span>),
  *     (<span class="stLiteral">1</span>,                 <span class="stLiteral">0</span>)
  *   )
  * <br/>forAll (invalidCombos) { (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) =&gt;
  *   an [<span class="stType">IllegalArgumentException</span>] should be thrownBy {
  *     <span class="stReserved">new</span> <span class="stType">Fraction</span>(n, d)
  *   }
  * }
  * </pre>
  *
  * @author Bill Venners
  */
trait ScalaCheckPropertyChecks extends TableDrivenPropertyChecks with ScalaCheckDrivenPropertyChecks

/**
  * Companion object that facilitates the importing of <code>PropertyChecks</code> members as
  * an alternative to mixing it in. One use case is to import <code>PropertyChecks</code> members so you can use
  * them in the Scala interpreter.
  *
  * @author Bill Venners
  */
object ScalaCheckPropertyChecks extends ScalaCheckPropertyChecks
