/*
 * Copyright 2001-2022 Artima, Inc.
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
                          
import org.scalacheck.Arbitrary
import org.scalacheck.Shrink
import org.scalacheck.Prop
import org.scalacheck.Gen
import org.scalacheck.Prop.{BooleanOperators => _, _}
import org.scalatest.exceptions.DiscardedEvaluationException
import org.scalatest.prop.Whenever
import org.scalactic._

/**
 * Trait containing methods that faciliate property checks against generated data.
 *
 * <p>
 * This trait contains <code>forAll</code> methods that provide various ways to check properties using
 * generated data. Use of this trait requires that ScalaCheck be on the class path when you compile and run your tests.
 * It also contains a <code>wherever</code> method that can be used to indicate a property need only hold whenever
 * some condition is true.
 * </p>
 *
 * <p>
 * For an example of trait <code>ScalaCheckDrivenPropertyChecks</code> in action, imagine you want to test this <code>Fraction</code> class:
 * </p>
 *  
 * <pre class="stHighlighted">
 * <span class="stReserved">class</span> <span class="stType">Fraction</span>(n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) {
 * <br/>  require(d != <span class="stLiteral">0</span>)
 *   require(d != Integer.MIN_VALUE)
 *   require(n != Integer.MIN_VALUE)
 * <br/>  <span class="stReserved">val</span> numer = <span class="stReserved">if</span> (d < <span class="stLiteral">0</span>) -<span class="stLiteral">1</span> * n <span class="stReserved">else</span> n
 *   <span class="stReserved">val</span> denom = d.abs
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> toString = numer + <span class="stQuotedString">" / "</span> + denom
 * }
 * </pre>
 *
 * <p>
 * To test the behavior of <code>Fraction</code>, you could mix in or import the members of <code>ScalaCheckDrivenPropertyChecks</code>
 * (and <code>Matchers</code>) and check a property using a <code>forAll</code> method, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll { (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) =>
 * <br/>  whenever (d != <span class="stLiteral">0</span> && d != Integer.MIN_VALUE
 *       && n != Integer.MIN_VALUE) {
 * <br/>    <span class="stReserved">val</span> f = <span class="stReserved">new</span> <span class="stType">Fraction</span>(n, d)
 * <br/>    <span class="stReserved">if</span> (n < <span class="stLiteral">0</span> && d < <span class="stLiteral">0</span> || n > <span class="stLiteral">0</span> && d > <span class="stLiteral">0</span>)
 *       f.numer should be > <span class="stLiteral">0</span>
 *     <span class="stReserved">else</span> <span class="stReserved">if</span> (n != <span class="stLiteral">0</span>)
 *       f.numer should be < <span class="stLiteral">0</span>
 *     <span class="stReserved">else</span>
 *       f.numer should be === <span class="stLiteral">0</span>
 * <br/>    f.denom should be > <span class="stLiteral">0</span>
 *   }
 * }
 * </pre>
 *
 * <p>
 * Trait <code>ScalaCheckDrivenPropertyChecks</code> provides overloaded <code>forAll</code> methods
 * that allow you to check properties using the data provided by a ScalaCheck generator. The simplest form
 * of <code>forAll</code> method takes two parameter lists, the second of which is implicit. The first parameter list
 * is a "property" function with one to six parameters. An implicit <code>Arbitrary</code> generator and <code>Shrink</code> object needs to be supplied for
 * The <code>forAll</code> method will pass each row of data to
 * each parameter type. ScalaCheck provides many implicit <code>Arbitrary</code> generators for common types such as
 * <code>Int</code>, <code>String</code>, <code>List[Float]</code>, <em>etc.</em>, in its <code>org.scalacheck.Arbitrary</code> companion
 * object. So long as you use types for which ScalaCheck already provides implicit <code>Arbitrary</code> generators, you needn't
 * worry about them. Same for <code>Shrink</code> objects, which are provided by ScalaCheck's <code>org.scalacheck.Shrink</code> companion
 * object. Most often you can simply pass a property function to <code>forAll</code>, and the compiler will grab the implicit
 * values provided by ScalaCheck.
 * </p>
 *
 * <p>
 * The <code>forAll</code> methods use the supplied <code>Arbitrary</code> generators to generate example
 * arguments and pass them to the property function, and
 * generate a <code>ScalaCheckDrivenPropertyCheckFailedException</code> if the function
 * completes abruptly for any exception that would <a href="../Suite.html#errorHandling">normally cause</a> a test to
 * fail in ScalaTest other than <code>DiscardedEvaluationException</code>. An
 * <code>DiscardedEvaluationException</code>,
 * which is thrown by the <code>whenever</code> method (defined in trait <code>Whenever</code>, which this trait extends) to indicate
 * a condition required by the property function is not met by a row
 * of passed data, will simply cause <code>forAll</code> to discard that row of data.
 * </p>
 *
 * <a name="supplyingArgumentNames"></a><h2>Supplying argument names</h2>
 *
 * <p>
 * You can optionally specify string names for the arguments passed to a property function, which will be used
 * in any error message when describing the argument values that caused the failure. To supply the names, place them in a comma separated list
 * in parentheses after <code>forAll</code> before the property function (a curried form of <code>forAll</code>). Here's
 * an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>) =>
 *   a.length + b.length should equal ((a + b).length + <span class="stLiteral">1</span>) <span class="stLineComment">// Should fail</span>
 * }
 * </pre>
 *
 * <p>
 * When this fails, you'll see an error message that includes this:
 * </p>
 *
 * <pre>
 * Occurred when passed generated values (
 *   a = "",
 *   b = ""
 * )
 * </pre>
 *
 * <p>
 * When you don't supply argument names, the error message will say <code>arg0</code>, <code>arg1</code>, <em>etc.</em>.
 * For example, this property check:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll { (a: <span class="stType">String</span>, b: <span class="stType">String</span>) =>
 *   a.length + b.length should equal ((a + b).length + <span class="stLiteral">1</span>) <span class="stLineComment">// Should fail</span>
 * }
 * </pre>
 *
 * <p>
 * Will fail with an error message that includes:
 * </p>
 *
 * <pre>
 * Occurred when passed generated values (
 *   arg0 = "",
 *   arg1 = ""
 * )
 * </pre>
 *
 * <a name="supplyingGenerators"></a><h2>Supplying generators</h2>
 *
 * <p>
 * ScalaCheck provides a nice library of compositors that makes it easy to create your own custom generators. If you
 * want to supply custom generators to a property check, place them in parentheses after <code>forAll</code>, before
 * the property check function (a curried form of <code>forAll</code>).
 * </p>
 *
 * <p>
 * For example, to create a generator of even integers between (and including) -2000 and 2000, you could write this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalacheck.Gen
 * <br/><span class="stReserved">val</span> evenInts = <span class="stReserved">for</span> (n <- Gen.choose(-<span class="stLiteral">1000</span>, <span class="stLiteral">1000</span>)) <span class="stReserved">yield</span> <span class="stLiteral">2</span> * n
 * </pre>
 *
 * <p>
 * Given this generator, you could use it on a property check like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (evenInts) { (n) => n % <span class="stLiteral">2</span> should equal (<span class="stLiteral">0</span>) }
 * </pre>
 *
 * <p>
 * Custom generators are necessary when you want to pass data types not supported by ScalaCheck's arbitrary generators,
 * but are also useful when some of the values in the full range for the passed types are not valid. For such values you
 * would use a <code>whenever</code> clause. In the <code>Fraction</code> class shown above, neither the passed numerator or
 * denominator can be <code>Integer.MIN_VALUE</code>, and the passed denominator cannot be zero. This shows up in the
 * <code>whenever</code> clause like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * whenever (d != <span class="stLiteral">0</span> && d != Integer.MIN_VALUE
 *     && n != Integer.MIN_VALUE) { ...
 * </pre>
 *
 * <p>
 * You could in addition define generators for the numerator and denominator that only produce valid values, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> validNumers =
 *   <span class="stReserved">for</span> (n <- Gen.choose(Integer.MIN_VALUE + <span class="stLiteral">1</span>, Integer.MAX_VALUE)) <span class="stReserved">yield</span> n
 * <span class="stReserved">val</span> validDenoms =
 *   <span class="stReserved">for</span> (d <- validNumers <span class="stReserved">if</span> d != <span class="stLiteral">0</span>) <span class="stReserved">yield</span> d
 * </pre>
 *
 * <p>
 * You could then use them in the property check like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (validNumers, validDenoms) { (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) =>
 * <br/>  whenever (d != <span class="stLiteral">0</span> && d != Integer.MIN_VALUE
 *       && n != Integer.MIN_VALUE) {
 * <br/>    <span class="stReserved">val</span> f = <span class="stReserved">new</span> <span class="stType">Fraction</span>(n, d)
 * <br/>    <span class="stReserved">if</span> (n < <span class="stLiteral">0</span> && d < <span class="stLiteral">0</span> || n > <span class="stLiteral">0</span> && d > <span class="stLiteral">0</span>)
 *       f.numer should be > <span class="stLiteral">0</span>
 *     <span class="stReserved">else</span> <span class="stReserved">if</span> (n != <span class="stLiteral">0</span>)
 *       f.numer should be < <span class="stLiteral">0</span>
 *     <span class="stReserved">else</span>
 *       f.numer should be === <span class="stLiteral">0</span>
 * <br/>    f.denom should be > <span class="stLiteral">0</span>
 *   }
 * }
 * </pre>
 *
 * <p>
 * Note that even if you use generators that don't produce the invalid values, you still need the
 * <code>whenever</code> clause. The reason is that once a property fails, ScalaCheck will try and shrink
 * the values to the smallest values that still cause the property to fail. During this shrinking process ScalaCheck
 * may pass invalid values. The <code>whenever</code> clause is still needed to guard against those values. (The
 * <code>whenever</code> clause also clarifies to readers of the code exactly what the property is in a succinct
 * way, without requiring that they find and understand the generator definitions.)
 * </p>
 *
 * <a name="supplyingGeneratorsAndArgNames"></a><h2>Supplying both generators and argument names</h2>
 *
 * <p>
 * If you want to supply both generators and named arguments, you can do so by providing a list of <code>(&lt;generator&gt;, &lt;name&gt;)</code> pairs
 * in parentheses after <code>forAll</code>, before the property function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll ((validNumers, <span class="stQuotedString">"n"</span>), (validDenoms, <span class="stQuotedString">"d"</span>)) { (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) =>
 * <br/>  whenever (d != <span class="stLiteral">0</span> && d != Integer.MIN_VALUE
 *       && n != Integer.MIN_VALUE) {
 * <br/>    <span class="stReserved">val</span> f = <span class="stReserved">new</span> <span class="stType">Fraction</span>(n, d)
 * <br/>    <span class="stReserved">if</span> (n < <span class="stLiteral">0</span> && d < <span class="stLiteral">0</span> || n > <span class="stLiteral">0</span> && d > <span class="stLiteral">0</span>)
 *       f.numer should be > <span class="stLiteral">0</span>
 *     <span class="stReserved">else</span> <span class="stReserved">if</span> (n != <span class="stLiteral">0</span>)
 *       f.numer should be < <span class="stLiteral">0</span>
 *     <span class="stReserved">else</span>
 *       f.numer should be === <span class="stLiteral">0</span>
 * <br/>    f.denom should be > <span class="stLiteral">0</span>
 *   }
 * }
 * </pre>
 *
 * <p>
 * Were this property check to fail, it would mention the names n and d in the error message, like this:
 * </p>
 *
 * <pre>
 * Occurred when passed generated values (
 *   n = 17,
 *   d = 21
 * )
 * </pre>
 *
 * <a name="propCheckConfig"></a><h2>Property check configuration</h2>
 *
 * <p>
 * The property checks performed by the <code>forAll</code> methods of this trait can be flexibly configured via the services
 * provided by supertrait <code>Configuration</code>.  The five configuration parameters for property checks along with their 
 * default values and meanings are described in the following table:
 * </p>
 *
 * <table style="border-collapse: collapse; border: 1px solid black">
 * <tr>
 * <th style="background-color: #CCCCCC; border-width: 1px; padding: 3px; text-align: center; border: 1px solid black">
 * <strong>Configuration Parameter</strong>
 * </th>
 * <th style="background-color: #CCCCCC; border-width: 1px; padding: 3px; text-align: center; border: 1px solid black">
 * <strong>Default Value</strong>
 * </th>
 * <th style="background-color: #CCCCCC; border-width: 1px; padding: 3px; text-align: center; border: 1px solid black">
 * <strong>Meaning</strong>
 * </th>
 * </tr>
 * <tr>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * minSuccessful
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * 100
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 * the minimum number of successful property evaluations required for the property to pass
 * </td>
 * </tr>
 * <tr>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * maxDiscardedFactor
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * 500
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 * the maximum discarded factor property evaluations allowed during a property check
 * </td>
 * </tr>
 * <tr>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * minSize
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * 0
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 * the minimum size parameter to provide to ScalaCheck, which it will use when generating objects for which size matters (such as strings or lists)
 * </td>
 * </tr>
 * <tr>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * sizeRange
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * 100
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 * the size range parameter to provide to ScalaCheck, which it will use when generating objects for which size matters (such as strings or lists)
 * </td>
 * </tr>
 * <tr>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * workers
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: center">
 * 1
 * </td>
 * <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 * specifies the number of worker threads to use during property evaluation
 * </td>
 * </tr>
 * </table>
 *
 * <p>
 * The <code>forAll</code> methods of trait <code>ScalaCheckDrivenPropertyChecks</code> each take a <code>PropertyCheckConfiguration</code>
 * object as an implicit parameter. This object provides values for each of the five configuration parameters. Trait <code>Configuration</code>
 * provides an implicit <code>val</code> named <code>generatorDrivenConfig</code> with each configuration parameter set to its default value. 
 * If you want to set one or more configuration parameters to a different value for all property checks in a suite you can override this
 * val (or hide it, for example, if you are importing the members of the <code>ScalaCheckDrivenPropertyChecks</code> companion object rather
 * than mixing in the trait.) For example, if
 * you want all parameters at their defaults except for <code>minSize</code> and <code>sizeRange</code>, you can override
 * <code>generatorDrivenConfig</code>, like this:
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">implicit</span> <span class="stReserved">override</span> <span class="stReserved">val</span> generatorDrivenConfig =
 *   <span class="stType">PropertyCheckConfiguration</span>(minSize = <span class="stLiteral">10</span>, sizeRange = <span class="stLiteral">10</span>)
 * </pre>
 *
 * <p>
 * Or, hide it by declaring a variable of the same name in whatever scope you want the changed values to be in effect:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">implicit</span> <span class="stReserved">val</span> generatorDrivenConfig =
 *   <span class="stType">PropertyCheckConfiguration</span>(minSize = <span class="stLiteral">10</span>, sizeRange = <span class="stLiteral">10</span>)
 * </pre>
 *
 * <p>
 * In addition to taking a <code>PropertyCheckConfiguration</code> object as an implicit parameter, the <code>forAll</code> methods of trait
 * <code>ScalaCheckDrivenPropertyChecks</code> also take a variable length argument list of <code>PropertyCheckConfigParam</code>
 * objects that you can use to override the values provided by the implicit <code>PropertyCheckConfiguration</code> for a single <code>forAll</code>
 * invocation. For example, if you want to set <code>minSuccessful</code> to 500 for just one particular <code>forAll</code> invocation,
 * you can do so like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (minSuccessful(<span class="stLiteral">500</span>)) { (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) => ...
 * </pre>
 *
 * <p>
 * This invocation of <code>forAll</code> will use 500 for <code>minSuccessful</code> and whatever values are specified by the 
 * implicitly passed <code>PropertyCheckConfiguration</code> object for the other configuration parameters.
 * If you want to set multiple configuration parameters in this way, just list them separated by commas:
 * </p>
 * 
 * <pre class="stHighlighted">
 * forAll (minSuccessful(<span class="stLiteral">500</span>), maxDiscardedFactor(<span class="stLiteral">0.6</span>)) { (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) => ...
 * </pre>
 *
 * <p>
 * If you are using an overloaded form of <code>forAll</code> that already takes an initial parameter list, just
 * add the configuration parameters after the list of generators, names, or generator/name pairs, as in:
 * </p>
 * 
 * <pre class="stHighlighted">
 * <span class="stLineComment">// If providing argument names</span>
 * forAll (<span class="stQuotedString">"n"</span>, <span class="stQuotedString">"d"</span>, minSuccessful(<span class="stLiteral">500</span>), maxDiscardedFactor(<span class="stLiteral">0.6</span>)) {
 *   (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) => ...
 * <br/><span class="stLineComment">// If providing generators</span>
 * forAll (validNumers, validDenoms, minSuccessful(<span class="stLiteral">500</span>), maxDiscardedFactor(<span class="stLiteral">0.6</span>)) {
 *   (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) => ...
 * <br/><span class="stLineComment">// If providing (&lt;generators&gt;, &lt;name&gt;) pairs</span>
 * forAll ((validNumers, <span class="stQuotedString">"n"</span>), (validDenoms, <span class="stQuotedString">"d"</span>), minSuccessful(<span class="stLiteral">500</span>), maxDiscardedFactor(<span class="stLiteral">0.6</span>)) {
 *   (n: <span class="stType">Int</span>, d: <span class="stType">Int</span>) => ...
 * </pre>
 *
 * <p>
 * For more information, see the documentation for supertrait <a href="Configuration.html"><code>Configuration</code></a>.
 * </p>
 * 
 * @author Bill Venners
 */
trait ScalaCheckDrivenPropertyChecks extends Whenever with org.scalatestplus.scalacheck.ScalaCheckConfiguration {

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators, modifying the values in the implicitly passed 
   * <code>PropertyGenConfig</code> object with explicitly passed parameter values.
   *
   * <p>
   * This method creates a <code>ConfiguredPropertyCheck</code> object that has six overloaded apply methods
   * that take a function. Thus it is used with functions of all six arities.
   * Here are some examples:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>) =>
   *   a.length should equal ((a).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>) =>
   *   a.length + b.length should equal ((a + b).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length should equal ((a + b + c).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length should equal ((a + b + c + d).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length should equal ((a + b + c + d + e).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>, f: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length + f.length should equal ((a + b + c + d + e + f).length)
   * }
   * </pre>
   *
   * @param configParams a variable length list of <code>PropertyCheckConfigParam</code> objects that should override corresponding
   *   values in the <code>PropertyCheckConfiguration</code> implicitly passed to the <code>apply</code> methods of the <code>ConfiguredPropertyCheck</code>
   *   object returned by this method.
   */
  def forAll(configParams: PropertyCheckConfigParam*): ConfiguredPropertyCheck = new ConfiguredPropertyCheck(configParams)

  /**
   * Performs a configured property checks by applying property check functions passed to its <code>apply</code> methods to arguments
   * supplied by implicitly passed generators, modifying the values in the 
   * <code>PropertyGenConfig</code> object passed implicitly to its <code>apply</code> methods with parameter values passed to its constructor.
   *
   * <p>
   * Instances of this class are returned by trait <code>ScalaCheckDrivenPropertyChecks</code> <code>forAll</code> method that accepts a variable length
   * argument list of <code>PropertyCheckConfigParam</code> objects. Thus it is used with functions of all six arities.
   * Here are some examples:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>) =>
   *   a.length should equal ((a).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>) =>
   *   a.length + b.length should equal ((a + b).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length should equal ((a + b + c).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length should equal ((a + b + c + d).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length should equal ((a + b + c + d + e).length)
   * }
   * <br/>forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>, f: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length + f.length should equal ((a + b + c + d + e + f).length)
   * }
   * </pre>
   *
   * <p>
   * In the first example above, the <code>ConfiguredPropertyCheck</code> object is returned by:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>))
   * </pre>
   *
   * <p>
   * The code that follows is an invocation of one of the <code>ConfiguredPropertyCheck</code> <code>apply</code> methods:
   * </p>
   *
   * <pre class="stHighlighted">
   * { (a: <span class="stType">String</span>) =>
   *   a.length should equal ((a).length)
   * }
   * </pre>
   *
   * @param configParams a variable length list of <code>PropertyCheckConfigParam</code> objects that should override corresponding
   *   values in the <code>PropertyCheckConfiguration</code> implicitly passed to the <code>apply</code> methods of instances of this class.
   *
   * @author Bill Venners
  */
  class ConfiguredPropertyCheck(configParams: Seq[PropertyCheckConfigParam]) {

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators, modifying the values in the implicitly passed 
   * <code>PropertyGenConfig</code> object with parameter values passed to this object's constructor.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>) =>
   *   a.length should equal ((a).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
    def apply[A, ASSERTION](fun: (A) => ASSERTION)
      (implicit
        config: PropertyCheckConfiguration,
        arbA: Arbitrary[A], shrA: Shrink[A],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
      ): asserting.Result = {
        val propF = { (a: A) =>
          val (unmetCondition, succeeded, exception) =
            try {
              val (succeeded, cause) = asserting.succeed(fun(a))
              (false, succeeded, cause)
            }
            catch {
              case e: DiscardedEvaluationException => (true, false, None)
              case e: Throwable => (false, false, Some(e))
            }
          !unmetCondition ==> (
            if (exception.isEmpty) {
              if (succeeded)
                Prop.passed
              else
                Prop.falsified
            }
            else
              Prop.exception(exception.get)
          )
        }
        val prop = Prop.forAll(propF)
        val params = getScalaCheckParams(configParams, config)
        asserting.check(prop, params, prettifier, pos)
    }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators, modifying the values in the implicitly passed 
   * <code>PropertyGenConfig</code> object with parameter values passed to this object's constructor.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>) =>
   *   a.length + b.length should equal ((a + b).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
    def apply[A, B, ASSERTION](fun: (A, B) => ASSERTION)
      (implicit
        config: PropertyCheckConfiguration,
        arbA: Arbitrary[A], shrA: Shrink[A],
        arbB: Arbitrary[B], shrB: Shrink[B],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
      ): asserting.Result = {
        val propF = { (a: A, b: B) =>
          val (unmetCondition, succeeded, exception) =
            try {
              val (succeeded, cause) = asserting.succeed(fun(a, b))
              (false, succeeded, cause)
            }
            catch {
              case e: DiscardedEvaluationException => (true, false, None)
              case e: Throwable => (false, false, Some(e))
            }
          !unmetCondition ==> (
            if (exception.isEmpty) {
              if (succeeded)
                Prop.passed
              else
                Prop.falsified
            }
            else
              Prop.exception(exception.get)
          )
        }
        val prop = Prop.forAll(propF)
        val params = getScalaCheckParams(configParams, config)
        asserting.check(prop, params, prettifier, pos)
    }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators, modifying the values in the implicitly passed 
   * <code>PropertyGenConfig</code> object with parameter values passed to this object's constructor.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length should equal ((a + b + c).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
    def apply[A, B, C, ASSERTION](fun: (A, B, C) => ASSERTION)
      (implicit
        config: PropertyCheckConfiguration,
        arbA: Arbitrary[A], shrA: Shrink[A],
        arbB: Arbitrary[B], shrB: Shrink[B],
        arbC: Arbitrary[C], shrC: Shrink[C],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
      ): asserting.Result = {
        val propF = { (a: A, b: B, c: C) =>
          val (unmetCondition, succeeded, exception) =
            try {
              val (succeeded, cause) = asserting.succeed(fun(a, b, c))
              (false, succeeded, cause)
            }
            catch {
              case e: DiscardedEvaluationException => (true, false, None)
              case e: Throwable => (false, false, Some(e))
            }
          !unmetCondition ==> (
            if (exception.isEmpty) {
              if (succeeded)
                Prop.passed
              else
                Prop.falsified
            }
            else
              Prop.exception(exception.get)
          )
        }
        val prop = Prop.forAll(propF)
        val params = getScalaCheckParams(configParams, config)
        asserting.check(prop, params, prettifier, pos)
    }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators, modifying the values in the implicitly passed 
   * <code>PropertyGenConfig</code> object with parameter values passed to this object's constructor.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length should equal ((a + b + c + d).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
    def apply[A, B, C, D, ASSERTION](fun: (A, B, C, D) => ASSERTION)
      (implicit
        config: PropertyCheckConfiguration,
        arbA: Arbitrary[A], shrA: Shrink[A],
        arbB: Arbitrary[B], shrB: Shrink[B],
        arbC: Arbitrary[C], shrC: Shrink[C],
        arbD: Arbitrary[D], shrD: Shrink[D],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
      ): asserting.Result = {
        val propF = { (a: A, b: B, c: C, d: D) =>
          val (unmetCondition, succeeded, exception) =
            try {
              val (succeeded, cause) = asserting.succeed(fun(a, b, c, d))
              (false, succeeded, cause)
            }
            catch {
              case e: DiscardedEvaluationException => (true, false, None)
              case e: Throwable => (false, false, Some(e))
            }
          !unmetCondition ==> (
            if (exception.isEmpty) {
              if (succeeded)
                Prop.passed
              else
                Prop.falsified
            }
            else
              Prop.exception(exception.get)
          )
        }
        val prop = Prop.forAll(propF)
        val params = getScalaCheckParams(configParams, config)
        asserting.check(prop, params, prettifier, pos)
    }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators, modifying the values in the implicitly passed 
   * <code>PropertyGenConfig</code> object with parameter values passed to this object's constructor.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length should equal ((a + b + c + d + e).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
    def apply[A, B, C, D, E, ASSERTION](fun: (A, B, C, D, E) => ASSERTION)
      (implicit
        config: PropertyCheckConfiguration,
        arbA: Arbitrary[A], shrA: Shrink[A],
        arbB: Arbitrary[B], shrB: Shrink[B],
        arbC: Arbitrary[C], shrC: Shrink[C],
        arbD: Arbitrary[D], shrD: Shrink[D],
        arbE: Arbitrary[E], shrE: Shrink[E],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
      ): asserting.Result = {
        val propF = { (a: A, b: B, c: C, d: D, e: E) =>
          val (unmetCondition, succeeded, exception) =
            try {
              val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e))
              (false, succeeded, cause)
            }
            catch {
              case e: DiscardedEvaluationException => (true, false, None)
              case e: Throwable => (false, false, Some(e))
            }
          !unmetCondition ==> (
            if (exception.isEmpty) {
              if (succeeded)
                Prop.passed
              else
                Prop.falsified
            }
            else
              Prop.exception(exception.get)
          )
        }
        val prop = Prop.forAll(propF)
        val params = getScalaCheckParams(configParams, config)
        asserting.check(prop, params, prettifier, pos)
    }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators, modifying the values in the implicitly passed 
   * <code>PropertyGenConfig</code> object with parameter values passed to this object's constructor.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (minSize(<span class="stLiteral">1</span>), sizeRange(<span class="stLiteral">9</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>, f: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length + f.length should equal ((a + b + c + d + e + f).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
    def apply[A, B, C, D, E, F, ASSERTION](fun: (A, B, C, D, E, F) => ASSERTION)
      (implicit
        config: PropertyCheckConfiguration,
        arbA: Arbitrary[A], shrA: Shrink[A],
        arbB: Arbitrary[B], shrB: Shrink[B],
        arbC: Arbitrary[C], shrC: Shrink[C],
        arbD: Arbitrary[D], shrD: Shrink[D],
        arbE: Arbitrary[E], shrE: Shrink[E],
        arbF: Arbitrary[F], shrF: Shrink[F],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
      ): asserting.Result = {
        val propF = { (a: A, b: B, c: C, d: D, e: E, f: F) =>
          val (unmetCondition, succeeded, exception) =
            try {
              val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e, f))
              (false, succeeded, cause)
            }
            catch {
              case e: DiscardedEvaluationException => (true, false, None)
              case e: Throwable => (false, false, Some(e))
            }
          !unmetCondition ==> (
            if (exception.isEmpty) {
              if (succeeded)
                Prop.passed
              else
                Prop.falsified
            }
            else
              Prop.exception(exception.get)
          )
        }
        val prop = Prop.forAll(propF)
        val params = getScalaCheckParams(configParams, config)
        asserting.check(prop, params, prettifier, pos)
    }
  }
                              
  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll { (a: <span class="stType">String</span>) =>
   *   a.length should equal ((a).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, ASSERTION](fun: (A) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(Seq(), config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function with the specified
   * argument names to arguments supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (<span class="stQuotedString">"a"</span>) { (a: <span class="stType">String</span>) =>
   *   a.length should equal ((a).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, ASSERTION](nameA: String, configParams: PropertyCheckConfigParam*)(fun: (A) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA)))
  }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll (famousLastWords) { (a: <span class="stType">String</span>) =>
   *   a.length should equal ((a).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, ASSERTION](genA: Gen[A], configParams: PropertyCheckConfigParam*)(fun: (A) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function to named arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll ((famousLastWords, <span class="stQuotedString">"a"</span>)) { (a: <span class="stType">String</span>) =>
   *   a.length should equal ((a).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, ASSERTION](genAndNameA: (Gen[A], String), configParams: PropertyCheckConfigParam*)(fun: (A) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {

      val (genA, nameA) = genAndNameA

      val propF = { (a: A) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA)))
  }
                                    
  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll { (a: <span class="stType">String</span>, b: <span class="stType">String</span>) =>
   *   a.length + b.length should equal ((a + b).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, ASSERTION](fun: (A, B) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(Seq(), config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function with the specified
   * argument names to arguments supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>) =>
   *   a.length + b.length should equal ((a + b).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, ASSERTION](nameA: String, nameB: String, configParams: PropertyCheckConfigParam*)(fun: (A, B) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB)))
  }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll (famousLastWords, famousLastWords) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>) =>
   *   a.length + b.length should equal ((a + b).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, ASSERTION](genA: Gen[A], genB: Gen[B], configParams: PropertyCheckConfigParam*)(fun: (A, B) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function to named arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll ((famousLastWords, <span class="stQuotedString">"a"</span>), (famousLastWords, <span class="stQuotedString">"b"</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>) =>
   *   a.length + b.length should equal ((a + b).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, ASSERTION](genAndNameA: (Gen[A], String), genAndNameB: (Gen[B], String), configParams: PropertyCheckConfigParam*)(fun: (A, B) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {

      val (genA, nameA) = genAndNameA
      val (genB, nameB) = genAndNameB

      val propF = { (a: A, b: B) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB)))
  }
                                    
  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length should equal ((a + b + c).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, ASSERTION](fun: (A, B, C) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
      arbC: Arbitrary[C], shrC: Shrink[C],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(Seq(), config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function with the specified
   * argument names to arguments supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length should equal ((a + b + c).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, ASSERTION](nameA: String, nameB: String, nameC: String, configParams: PropertyCheckConfigParam*)(fun: (A, B, C) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
      arbC: Arbitrary[C], shrC: Shrink[C],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB, nameC)))
  }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll (famousLastWords, famousLastWords, famousLastWords) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length should equal ((a + b + c).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, ASSERTION](genA: Gen[A], genB: Gen[B], genC: Gen[C], configParams: PropertyCheckConfigParam*)(fun: (A, B, C) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
      shrC: Shrink[C],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB, genC)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function to named arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll ((famousLastWords, <span class="stQuotedString">"a"</span>), (famousLastWords, <span class="stQuotedString">"b"</span>), (famousLastWords, <span class="stQuotedString">"c"</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length should equal ((a + b + c).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, ASSERTION](genAndNameA: (Gen[A], String), genAndNameB: (Gen[B], String), genAndNameC: (Gen[C], String), configParams: PropertyCheckConfigParam*)(fun: (A, B, C) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
      shrC: Shrink[C],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {

      val (genA, nameA) = genAndNameA
      val (genB, nameB) = genAndNameB
      val (genC, nameC) = genAndNameC

      val propF = { (a: A, b: B, c: C) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB, genC)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB, nameC)))
  }
                                    
  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length should equal ((a + b + c + d).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, ASSERTION](fun: (A, B, C, D) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
      arbC: Arbitrary[C], shrC: Shrink[C],
      arbD: Arbitrary[D], shrD: Shrink[D],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C, d: D) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(Seq(), config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function with the specified
   * argument names to arguments supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length should equal ((a + b + c + d).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, ASSERTION](nameA: String, nameB: String, nameC: String, nameD: String, configParams: PropertyCheckConfigParam*)(fun: (A, B, C, D) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
      arbC: Arbitrary[C], shrC: Shrink[C],
      arbD: Arbitrary[D], shrD: Shrink[D],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C, d: D) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB, nameC, nameD)))
  }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll (famousLastWords, famousLastWords, famousLastWords, famousLastWords) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length should equal ((a + b + c + d).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, ASSERTION](genA: Gen[A], genB: Gen[B], genC: Gen[C], genD: Gen[D], configParams: PropertyCheckConfigParam*)(fun: (A, B, C, D) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
      shrC: Shrink[C],
      shrD: Shrink[D],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C, d: D) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB, genC, genD)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function to named arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll ((famousLastWords, <span class="stQuotedString">"a"</span>), (famousLastWords, <span class="stQuotedString">"b"</span>), (famousLastWords, <span class="stQuotedString">"c"</span>), (famousLastWords, <span class="stQuotedString">"d"</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length should equal ((a + b + c + d).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, ASSERTION](genAndNameA: (Gen[A], String), genAndNameB: (Gen[B], String), genAndNameC: (Gen[C], String), genAndNameD: (Gen[D], String), configParams: PropertyCheckConfigParam*)(fun: (A, B, C, D) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
      shrC: Shrink[C],
      shrD: Shrink[D],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {

      val (genA, nameA) = genAndNameA
      val (genB, nameB) = genAndNameB
      val (genC, nameC) = genAndNameC
      val (genD, nameD) = genAndNameD

      val propF = { (a: A, b: B, c: C, d: D) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB, genC, genD)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB, nameC, nameD)))
  }
                                    
  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length should equal ((a + b + c + d + e).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, E, ASSERTION](fun: (A, B, C, D, E) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
      arbC: Arbitrary[C], shrC: Shrink[C],
      arbD: Arbitrary[D], shrD: Shrink[D],
      arbE: Arbitrary[E], shrE: Shrink[E],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C, d: D, e: E) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(Seq(), config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function with the specified
   * argument names to arguments supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length should equal ((a + b + c + d + e).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, E, ASSERTION](nameA: String, nameB: String, nameC: String, nameD: String, nameE: String, configParams: PropertyCheckConfigParam*)(fun: (A, B, C, D, E) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
      arbC: Arbitrary[C], shrC: Shrink[C],
      arbD: Arbitrary[D], shrD: Shrink[D],
      arbE: Arbitrary[E], shrE: Shrink[E],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C, d: D, e: E) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB, nameC, nameD, nameE)))
  }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll (famousLastWords, famousLastWords, famousLastWords, famousLastWords, famousLastWords) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length should equal ((a + b + c + d + e).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, E, ASSERTION](genA: Gen[A], genB: Gen[B], genC: Gen[C], genD: Gen[D], genE: Gen[E], configParams: PropertyCheckConfigParam*)(fun: (A, B, C, D, E) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
      shrC: Shrink[C],
      shrD: Shrink[D],
      shrE: Shrink[E],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C, d: D, e: E) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB, genC, genD, genE)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function to named arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll ((famousLastWords, <span class="stQuotedString">"a"</span>), (famousLastWords, <span class="stQuotedString">"b"</span>), (famousLastWords, <span class="stQuotedString">"c"</span>), (famousLastWords, <span class="stQuotedString">"d"</span>), (famousLastWords, <span class="stQuotedString">"e"</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length should equal ((a + b + c + d + e).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, E, ASSERTION](genAndNameA: (Gen[A], String), genAndNameB: (Gen[B], String), genAndNameC: (Gen[C], String), genAndNameD: (Gen[D], String), genAndNameE: (Gen[E], String), configParams: PropertyCheckConfigParam*)(fun: (A, B, C, D, E) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
      shrC: Shrink[C],
      shrD: Shrink[D],
      shrE: Shrink[E],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {

      val (genA, nameA) = genAndNameA
      val (genB, nameB) = genAndNameB
      val (genC, nameC) = genAndNameC
      val (genD, nameD) = genAndNameD
      val (genE, nameE) = genAndNameE

      val propF = { (a: A, b: B, c: C, d: D, e: E) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB, genC, genD, genE)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB, nameC, nameD, nameE)))
  }
                                    
  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>, f: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length + f.length should equal ((a + b + c + d + e + f).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, E, F, ASSERTION](fun: (A, B, C, D, E, F) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
      arbC: Arbitrary[C], shrC: Shrink[C],
      arbD: Arbitrary[D], shrD: Shrink[D],
      arbE: Arbitrary[E], shrE: Shrink[E],
      arbF: Arbitrary[F], shrF: Shrink[F],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C, d: D, e: E, f: F) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e, f))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(Seq(), config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function with the specified
   * argument names to arguments supplied by implicitly passed generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * forAll (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>, f: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length + f.length should equal ((a + b + c + d + e + f).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, E, F, ASSERTION](nameA: String, nameB: String, nameC: String, nameD: String, nameE: String, nameF: String, configParams: PropertyCheckConfigParam*)(fun: (A, B, C, D, E, F) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      arbA: Arbitrary[A], shrA: Shrink[A],
      arbB: Arbitrary[B], shrB: Shrink[B],
      arbC: Arbitrary[C], shrC: Shrink[C],
      arbD: Arbitrary[D], shrD: Shrink[D],
      arbE: Arbitrary[E], shrE: Shrink[E],
      arbF: Arbitrary[F], shrF: Shrink[F],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C, d: D, e: E, f: F) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e, f))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB, nameC, nameD, nameE, nameF)))
  }

  /**
   * Performs a property check by applying the specified property check function to arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll (famousLastWords, famousLastWords, famousLastWords, famousLastWords, famousLastWords, famousLastWords) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>, f: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length + f.length should equal ((a + b + c + d + e + f).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, E, F, ASSERTION](genA: Gen[A], genB: Gen[B], genC: Gen[C], genD: Gen[D], genE: Gen[E], genF: Gen[F], configParams: PropertyCheckConfigParam*)(fun: (A, B, C, D, E, F) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
      shrC: Shrink[C],
      shrD: Shrink[D],
      shrE: Shrink[E],
      shrF: Shrink[F],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {
      val propF = { (a: A, b: B, c: C, d: D, e: E, f: F) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e, f))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB, genC, genD, genE, genF)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos)
  }

  /**
   * Performs a property check by applying the specified property check function to named arguments
   * supplied by the specified generators.
   *
   * <p>
   * Here's an example:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalacheck.Gen
   * <br/><span class="stLineComment">// Define your own string generator:</span>
   * <span class="stReserved">val</span> famousLastWords = <span class="stReserved">for</span> {
   *   s <- Gen.oneOf(<span class="stQuotedString">"the"</span>, <span class="stQuotedString">"program"</span>, <span class="stQuotedString">"compiles"</span>, <span class="stQuotedString">"therefore"</span>, <span class="stQuotedString">"it"</span>, <span class="stQuotedString">"should"</span>, <span class="stQuotedString">"work"</span>)
   * } <span class="stReserved">yield</span> s
   * <br/>forAll ((famousLastWords, <span class="stQuotedString">"a"</span>), (famousLastWords, <span class="stQuotedString">"b"</span>), (famousLastWords, <span class="stQuotedString">"c"</span>), (famousLastWords, <span class="stQuotedString">"d"</span>), (famousLastWords, <span class="stQuotedString">"e"</span>), (famousLastWords, <span class="stQuotedString">"f"</span>)) { (a: <span class="stType">String</span>, b: <span class="stType">String</span>, c: <span class="stType">String</span>, d: <span class="stType">String</span>, e: <span class="stType">String</span>, f: <span class="stType">String</span>) =>
   *   a.length + b.length + c.length + d.length + e.length + f.length should equal ((a + b + c + d + e + f).length)
   * }
   * </pre>
   *
   * @param fun the property check function to apply to the generated arguments
   */
  def forAll[A, B, C, D, E, F, ASSERTION](genAndNameA: (Gen[A], String), genAndNameB: (Gen[B], String), genAndNameC: (Gen[C], String), genAndNameD: (Gen[D], String), genAndNameE: (Gen[E], String), genAndNameF: (Gen[F], String), configParams: PropertyCheckConfigParam*)(fun: (A, B, C, D, E, F) => ASSERTION)
    (implicit
      config: PropertyCheckConfiguration,
      shrA: Shrink[A],
      shrB: Shrink[B],
      shrC: Shrink[C],
      shrD: Shrink[D],
      shrE: Shrink[E],
      shrF: Shrink[F],
        asserting: CheckerAsserting[ASSERTION],
        prettifier: Prettifier,
        pos: source.Position
    ): asserting.Result = {

      val (genA, nameA) = genAndNameA
      val (genB, nameB) = genAndNameB
      val (genC, nameC) = genAndNameC
      val (genD, nameD) = genAndNameD
      val (genE, nameE) = genAndNameE
      val (genF, nameF) = genAndNameF

      val propF = { (a: A, b: B, c: C, d: D, e: E, f: F) =>
        val (unmetCondition, succeeded, exception) =
          try {
            val (succeeded, cause) = asserting.succeed(fun(a, b, c, d, e, f))
            (false, succeeded, cause)
          }
          catch {
            case e: DiscardedEvaluationException => (true, false, None)
            case e: Throwable => (false, false, Some(e))
          }
        !unmetCondition ==> (
          if (exception.isEmpty) {
            if (succeeded)
              Prop.passed
            else
              Prop.falsified
          }
          else
            Prop.exception(exception.get)
        )
      }
      val prop = Prop.forAll(genA, genB, genC, genD, genE, genF)(propF)
      val params = getScalaCheckParams(configParams, config)
      asserting.check(prop, params, prettifier, pos, Some(List(nameA, nameB, nameC, nameD, nameE, nameF)))
  }
                                    }


object ScalaCheckDrivenPropertyChecks extends ScalaCheckDrivenPropertyChecks
                                                                   