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
package org.scalatest.matchers

import org.scalactic._
import org.scalatest.enablers._
import org.scalatest.matchers.dsl._
import org.scalatest.FailureMessages
import MatchersHelper.andMatchersAndApply
import MatchersHelper.orMatchersAndApply
import org.scalatest.Resources
import scala.collection.GenTraversable
import scala.reflect.ClassTag
import scala.util.matching.Regex
import TripleEqualsSupport.Spread
import TripleEqualsSupport.TripleEqualsInvocation

/**
 * Trait extended by objects that can match a value of the specified type. The value to match is
 * passed to the matcher's <code>apply</code> method. The result is a <code>MatchResult</code>.
 * A matcher is, therefore, a function from the specified type, <code>T</code>, to a <code>MatchResult</code>.
 * <p></p> <!-- needed otherwise the heading below shows up in the wrong place. dumb scaladoc algo -->
 *
 * <h2>Creating custom matchers</h2>
 * 
 * <p>
 * If none of the built-in matcher syntax satisfies a particular need you have, you can create
 * custom <code>Matcher</code>s that allow
 * you to place your own syntax directly after <code>should</code>. For example, although you can ensure that a <code>java.io.File</code> has a name
 * that ends with a particular extension like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * file.getName should endWith (<span class="stQuotedString">".txt"</span>)
 * </pre>
 * 
 * <p>
 * You might prefer 
 * to create a custom <code>Matcher[java.io.File]</code>
 * named <code>endWithExtension</code>, so you could write expressions like:
 * </p>
 *
 * <pre class="stHighlighted">
 * file should endWithExtension (<span class="stQuotedString">"txt"</span>)
 * file should not endWithExtension <span class="stQuotedString">"txt"</span>
 * file should (exist and endWithExtension (<span class="stQuotedString">"txt"</span>))
 * </pre>
 * 
 * <p>
 * One good way to organize custom matchers is to place them inside one or more
 * traits that you can then mix into the suites that need them. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> matchers._
 * <br/><span class="stReserved">trait</span> <span class="stType">CustomMatchers</span> {
 * <br/>  <span class="stReserved">class</span> <span class="stType">FileEndsWithExtensionMatcher</span>(expectedExtension: <span class="stType">String</span>) <span class="stReserved">extends</span> <span class="stType">Matcher[java.io.File]</span> {
 * <br/>    <span class="stReserved">def</span> apply(left: <span class="stType">java.io.File</span>) = {
 *       <span class="stReserved">val</span> name = left.getName
 *       <span class="stType">MatchResult</span>(
 *         name.endsWith(expectedExtension),
 *         s<span class="stQuotedString">"""File $name did not end with extension "$expectedExtension""""</span>,
 *         s<span class="stQuotedString">"""File $name ended with extension "$expectedExtension""""</span>
 *       )
 *     }
 *   }
 * <br/>  <span class="stReserved">def</span> endWithExtension(expectedExtension: <span class="stType">String</span>) = <span class="stReserved">new</span> <span class="stType">FileEndsWithExtensionMatcher</span>(expectedExtension)
 * }
 * <br/><span class="stLineComment">// Make them easy to import with:</span>
 * <span class="stLineComment">// import CustomMatchers._</span>
 * <span class="stReserved">object</span> <span class="stType">CustomMatchers</span> <span class="stReserved">extends</span> <span class="stType">CustomMatchers</span>
 * </pre>
 *
 * <p>
 * Note: the <code>CustomMatchers</code> companion object exists to make it easy to bring the
 * matchers defined in this trait into scope via importing, instead of mixing in the trait. The ability
 * to import them is useful, for example, when you want to use the matchers defined in a trait in the Scala interpreter console.
 * </p>
 *
 * <p>
 * This trait contains one matcher class, <code>FileEndsWithExtensionMatcher</code>, and a <code>def</code> named <code>endWithExtension</code> that returns a new
 * instance of <code>FileEndsWithExtensionMatcher</code>. Because the class extends <code>Matcher[java.io.File]</code>,
 * the compiler will only allow it be used to match against instances of <code>java.io.File</code>. A matcher must declare an
 * <code>apply</code> method that takes the type decared in <code>Matcher</code>'s type parameter, in this case <code>java.io.File</code>.
 * The apply method will return a <code>MatchResult</code> whose <code>matches</code> field will indicate whether the match succeeded.
 * The <code>failureMessage</code> field will provide a programmer-friendly error message indicating, in the event of a match failure, what caused
 * the match to fail. 
 * </p>
 *
 * <p>
 * The <code>FileEndsWithExtensionMatcher</code> matcher in this example determines success by determining if the passed <code>java.io.File</code> ends with
 * the desired extension. It does this in the first argument passed to the <code>MatchResult</code> factory method:
 * </p>
 *
 * <pre class="stHighlighted">
 * name.endsWith(expectedExtension)
 * </pre>
 *
 * <p>
 * In other words, if the file name has the expected extension, this matcher matches.
 * The next argument to <code>MatchResult</code>'s factory method produces the failure message string:
 * </p>
 *
 * <pre class="stHighlighted">
 * s<span class="stQuotedString">"""File $name did not end with extension "$expectedExtension""""</span>,
 * </pre>
 *
 * <p>
 * For example, consider this matcher expression:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> Matchers._
 * <span class="stReserved">import</span> java.io.File
 * <span class="stReserved">import</span> CustomMatchers._
 * <br/><span class="stReserved">new</span> <span class="stType">File</span>(<span class="stQuotedString">"essay.text"</span>) should endWithExtension (<span class="stQuotedString">"txt"</span>)
 * </pre>
 *
 * <p>
 * Because the passed <code>java.io.File</code> has the name <code>essay.text</code>, but the expected extension is <code>"txt"</code>, the failure
 * message would be:
 * </p>
 *
 * <pre>
 * File essay.text did not have extension "txt"
 * </pre>
 *
 * <p>
 * For more information on the fields in a <code>MatchResult</code>, including the subsequent field (or fields) that follow the failure message,
 * please see the documentation for <a href="MatchResult.html"><code>MatchResult</code></a>.
 * </p>
 *
 * <a name="otherways"></a>
 * <h2>Creating dynamic matchers</h2>
 *
 * <p>
 * There are other ways to create new matchers besides defining one as shown above. For example, you might check that a file is hidden like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">new</span> <span class="stType">File</span>(<span class="stQuotedString">"secret.txt"</span>) should be (<span class="stQuotedString">'hidden</span>)
 * </pre>
 *
 * <p>
 * If you wanted to get rid of the tick mark, you could simply define <code>hidden</code> like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> hidden = <span class="stQuotedString">'hidden</span>
 * </pre>
 *
 * <p>
 * Now you can check that an file is hidden without the tick mark:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">new</span> <span class="stType">File</span>(<span class="stQuotedString">"secret.txt"</span>) should be (hidden)
 * </pre>
 *
 * <p>
 * You could get rid of the parens with by using <code>shouldBe</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">new</span> <span class="stType">File</span>(<span class="stQuotedString">"secret.txt"</span>) shouldBe hidden
 * </pre>
 *
 * <h2>Creating matchers using logical operators</h2>
 *
 * <p>
 * You can also use ScalaTest matchers' logical operators to combine existing matchers into new ones, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> beWithinTolerance = be &gt;= <span class="stLiteral">0</span> and be &lt;= <span class="stLiteral">10</span>
 * </pre>
 *
 * <p>
 * Now you could check that a number is within the tolerance (in this case, between 0 and 10, inclusive), like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * num should beWithinTolerance
 * </pre>
 *
 * <p>
 * When defining a full blown matcher, one shorthand is to use one of the factory methods in <code>Matcher</code>'s companion
 * object. For example, instead of writing this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> beOdd =
 *   <span class="stReserved">new</span> <span class="stType">Matcher[Int]</span> {
 *     <span class="stReserved">def</span> apply(left: <span class="stType">Int</span>) =
 *       <span class="stType">MatchResult</span>(
 *         left % <span class="stLiteral">2</span> == <span class="stLiteral">1</span>,
 *         left + <span class="stQuotedString">" was not odd"</span>,
 *         left + <span class="stQuotedString">" was odd"</span>
 *       )
 *   }
 * </pre>
 *
 * <p>
 * You could alternately write this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> beOdd =
 *   <span class="stType">Matcher</span> { (left: <span class="stType">Int</span>) =&gt;
 *     <span class="stType">MatchResult</span>(
 *       left % <span class="stLiteral">2</span> == <span class="stLiteral">1</span>,
 *       left + <span class="stQuotedString">" was not odd"</span>,
 *       left + <span class="stQuotedString">" was odd"</span>
 *     )
 *   }
 * </pre>
 *
 * <p>
 * Either way you define the <code>beOdd</code> matcher, you could use it like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stLiteral">3</span> should beOdd
 * <span class="stLiteral">4</span> should not (beOdd)
 * </pre>
 *
 * <a name="composingMatchers"></a>
 * <h2>Composing matchers</h2>
 *
 * <p>
 * You can also compose matchers. For example, the <code>endWithExtension</code> matcher from the example above
 * can be more easily created by composing a function with the existing <code>endWith</code> matcher:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import org.scalatest._
 * import org.scalatest._
 *
 * scala&gt; import Matchers._
 * import Matchers._
 *
 * scala&gt; import java.io.File
 * import java.io.File
 *
 * scala&gt; def endWithExtension(ext: String) = endWith(ext) compose { (f: File) =&gt; f.getPath }
 * endWithExtension: (ext: String)org.scalatest.matchers.Matcher[java.io.File]
 * </pre>
 *
 * <p>
 * Now you have a <code>Matcher[File]</code> whose <code>apply</code> method first
 * invokes the converter function to convert the passed <code>File</code> to a <code>String</code>,
 * then passes the resulting <code>String</code> to <code>endWith</code>. Thus, you could use this version 
 * <code>endWithExtension</code> like the previous one:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; new File("output.txt") should endWithExtension("txt")
 * </pre>
 *
 * <p>
 * In addition, by composing twice, you can modify the type of both sides of a match statement
 * with the same function, like this:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val f = be &gt; (_: Int)
 * f: Int =&gt; org.scalatest.matchers.Matcher[Int] = &lt;function1&gt;
 *
 * scala&gt; val g = (_: String).toInt
 * g: String =&gt; Int = &lt;function1&gt;
 *
 * scala&gt; val beAsIntsGreaterThan = (f compose g) andThen (_ compose g)
 * beAsIntsGreaterThan: String =&gt; org.scalatest.matchers.Matcher[String] = &lt;function1&gt;
 *
 * scala&gt; "8" should beAsIntsGreaterThan ("7")
 * </pre>
 *
 * <p>
 * At thsi point, however, the error message for the <code>beAsIntsGreaterThan</code>
 * gives no hint that the <code>Int</code>s being compared were parsed from <code>String</code>s:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; "7" should beAsIntsGreaterThan ("8")
 * org.scalatest.exceptions.TestFailedException: 7 was not greater than 8
 * </pre>
 *
 * <p>
 * To modify error message, you can use trait <a href="MatcherProducers.html"><code>MatcherProducers</code></a>, which
 * also provides a <code>composeTwice</code> method that performs the <code>compose</code> ...
 * <code>andThen</code> ... <code>compose</code> operation:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import matchers._
 * import matchers._
 *
 * scala&gt; import MatcherProducers._
 * import MatcherProducers._
 *
 * scala&gt; val beAsIntsGreaterThan = f composeTwice g // means: (f compose g) andThen (_ compose g)
 * beAsIntsGreaterThan: String =&gt; org.scalatest.matchers.Matcher[String] = &lt;function1&gt;
 *
 * scala&gt; "8" should beAsIntsGreaterThan ("7")
 * </pre>
 *
 * <p>
 * Of course, the error messages is still the same:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; "7" should beAsIntsGreaterThan ("8")
 * org.scalatest.exceptions.TestFailedException: 7 was not greater than 8
 * </pre>
 *
 * <p>
 * To modify the error messages, you can use <code>mapResult</code> from <code>MatcherProducers</code>. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val beAsIntsGreaterThan =
 *   f composeTwice g mapResult { mr =&gt;
 *     mr.copy(
 *       failureMessageArgs =
 *         mr.failureMessageArgs.map((LazyArg(_) { "\"" + _.toString + "\".toInt"})),
 *       negatedFailureMessageArgs =
 *         mr.negatedFailureMessageArgs.map((LazyArg(_) { "\"" + _.toString + "\".toInt"})),
 *       midSentenceFailureMessageArgs =
 *         mr.midSentenceFailureMessageArgs.map((LazyArg(_) { "\"" + _.toString + "\".toInt"})),
 *       midSentenceNegatedFailureMessageArgs =
 *         mr.midSentenceNegatedFailureMessageArgs.map((LazyArg(_) { "\"" + _.toString + "\".toInt"}))
 *     )
 *   }
 * beAsIntsGreaterThan: String =&gt; org.scalatest.matchers.Matcher[String] = &lt;function1&gt;
 * </pre>
 *
 * <p>
 * The <code>mapResult</code> method takes a function that accepts a <code>MatchResult</code> and produces a new
 * <code>MatchResult</code>, which can contain modified arguments and modified error messages. In this example,
 * the error messages are being modified by wrapping the old arguments in <a href="LazyArg.html"><code>LazyArg</code></a>
 * instances that lazily apply the given prettification functions to the <code>toString</code> result of the old args.
 * Now the error message is clearer:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; "7" should beAsIntsGreaterThan ("8")
 * org.scalatest.exceptions.TestFailedException: "7".toInt was not greater than "8".toInt
 * </pre>
 *
 * <h2>Matcher's variance</h2>
 *
 * <p>
 * <code>Matcher</code> is contravariant in its type parameter, <code>T</code>, to make its use more flexible.
 * As an example, consider the hierarchy:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">class</span> <span class="stType">Fruit</span>
 * <span class="stReserved">class</span> <span class="stType">Orange</span> <span class="stReserved">extends</span> <span class="stType">Fruit</span>
 * <span class="stReserved">class</span> <span class="stType">ValenciaOrange</span> <span class="stReserved">extends</span> <span class="stType">Orange</span>
 * </pre>
 *
 * <p>
 * Given an orange:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> orange = <span class="stType">Orange</span>
 * </pre>
 *
 * <p>
 * The expression "<code>orange should</code>" will, via an implicit conversion in <code>Matchers</code>,
 * result in an object that has a <code>should</code>
 * method that takes a <code>Matcher[Orange]</code>. If the static type of the matcher being passed to <code>should</code> is
 * <code>Matcher[Valencia]</code> it shouldn't (and won't) compile. The reason it shouldn't compile is that
 * the left value is an <code>Orange</code>, but not necessarily a <code>Valencia</code>, and a
 * <code>Matcher[Valencia]</code> only knows how to match against a <code>Valencia</code>. The reason
 * it won't compile is given that <code>Matcher</code> is contravariant in its type parameter, <code>T</code>, a
 * <code>Matcher[Valencia]</code> is <em>not</em> a subtype of <code>Matcher[Orange]</code>.
 * </p>
 *
 * <p>
 * By contrast, if the static type of the matcher being passed to <code>should</code> is <code>Matcher[Fruit]</code>,
 * it should (and will) compile. The reason it <em>should</em> compile is that given the left value is an <code>Orange</code>,
 * it is also a <code>Fruit</code>, and a <code>Matcher[Fruit]</code> knows how to match against <code>Fruit</code>s.
 * The reason it <em>will</em> compile is that given  that <code>Matcher</code> is contravariant in its type parameter, <code>T</code>, a
 * <code>Matcher[Fruit]</code> is indeed a subtype of <code>Matcher[Orange]</code>.
 * </p>
 *
 * @author Bill Venners
 */
trait Matcher[-T] extends Function1[T, MatchResult] { outerInstance =>

  /**
   * Check to see if the specified object, <code>left</code>, matches, and report the result in
   * the returned <code>MatchResult</code>. The parameter is named <code>left</code>, because it is
   * usually the value to the left of a <code>should</code> or <code>must</code> invocation. For example,
   * in:
   *
   * <pre class="stHighlighted">
   * list should equal (<span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>))
   * </pre>
   *
   * The <code>equal (List(1, 2, 3))</code> expression results in a matcher that holds a reference to the
   * right value, <code>List(1, 2, 3)</code>. The <code>should</code> method invokes <code>apply</code>
   * on this matcher, passing in <code>list</code>, which is therefore the "<code>left</code>" value. The
   * matcher will compare the <code>list</code> (the <code>left</code> value) with <code>List(1, 2, 3)</code> (the right
   * value), and report the result in the returned <code>MatchResult</code>.
   *
   * @param left the value against which to match
   * @return the <code>MatchResult</code> that represents the result of the match
   */
  def apply(left: T): MatchResult

  /**
   * Compose this matcher with the passed function, returning a new matcher.
   *
   * <p>
   * This method overrides <code>compose</code> on <code>Function1</code> to
   * return a more specific function type of <code>Matcher</code>. For example, given
   * a <code>beOdd</code> matcher defined like this:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> beOdd =
   *   <span class="stReserved">new</span> <span class="stType">Matcher[Int]</span> {
   *     <span class="stReserved">def</span> apply(left: <span class="stType">Int</span>) =
   *       <span class="stType">MatchResult</span>(
   *         left % <span class="stLiteral">2</span> == <span class="stLiteral">1</span>,
   *         left + <span class="stQuotedString">" was not odd"</span>,
   *         left + <span class="stQuotedString">" was odd"</span>
   *       )
   *   }
   * </pre>
   *
   * <p>
   * You could use <code>beOdd</code> like this:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stLiteral">3</span> should beOdd
   * <span class="stLiteral">4</span> should not (beOdd)
   * </pre>
   *
   * <p>
   * If for some odd reason, you wanted a <code>Matcher[String]</code> that 
   * checked whether a string, when converted to an <code>Int</code>,
   * was odd, you could make one by composing <code>beOdd</code> with
   * a function that converts a string to an <code>Int</code>, like this:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> beOddAsInt = beOdd compose { (s: <span class="stType">String</span>) => s.toInt }
   * </pre>
   *
   * <p>
   * Now you have a <code>Matcher[String]</code> whose <code>apply</code> method first
   * invokes the converter function to convert the passed string to an <code>Int</code>,
   * then passes the resulting <code>Int</code> to <code>beOdd</code>. Thus, you could use
   * <code>beOddAsInt</code> like this:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stQuotedString">"3"</span> should beOddAsInt
   * <span class="stQuotedString">"4"</span> should not (beOddAsInt)
   * </pre>
   */
  override def compose[U](g: U => T): Matcher[U] =
    new Matcher[U] {
      def apply(u: U) = outerInstance.apply(g(u))
    }

// TODO: mention not short circuited, and the precendence is even between and and or

  /**
   * Returns a matcher whose <code>apply</code> method returns a <code>MatchResult</code>
   * that represents the logical-and of the results of the wrapped and the passed matcher applied to
   * the same value.
   *
   * <p>
   * The reason <code>and</code> has an upper bound on its type parameter is so that the <code>Matcher</code>
   * resulting from an invocation of <code>and</code> will have the correct type parameter. If you call
   * <code>and</code> on a <code>Matcher[Orange]</code>, passing in a <code>Matcher[Valencia]</code>,
   * the result will have type <code>Matcher[Valencia]</code>. This is correct because both a
   * <code>Matcher[Orange]</code> and a <code>Matcher[Valencia]</code> know how to match a
   * <code>Valencia</code> (but a <code>Matcher[Valencia]</code> doesn't know how to
   * match any old <code>Orange</code>).  If you call
   * <code>and</code> on a <code>Matcher[Orange]</code>, passing in a <code>Matcher[Fruit]</code>,
   * the result will have type <code>Matcher[Orange]</code>. This is also correct because both a
   * <code>Matcher[Orange]</code> and a <code>Matcher[Fruit]</code> know how to match an
   * <code>Orange</code> (but a <code>Matcher[Orange]</code> doesn't know how to
   * match any old <code>Fruit</code>).
   * </p>
   *
   * @param the matcher to logical-and with this matcher
   * @return a matcher that performs the logical-and of this and the passed matcher
   */
  def and[U <: T](rightMatcher: Matcher[U]): Matcher[U] =
    new Matcher[U] {
      def apply(left: U): MatchResult = {
        andMatchersAndApply(left, outerInstance, rightMatcher)
      }
      override def toString: String = "(" + Prettifier.default(outerInstance) + ") and (" + Prettifier.default(rightMatcher) + ")"
    }

  import scala.language.higherKinds

  /**
   * Returns a <code>MatcherFactory</code> whose <code>matcher</code> method returns a <code>Matcher</code>,
   * which has <code>apply</code> method that returns a <code>MatchResult</code> that represents the logical-and
   * of the results of the wrapped and the passed <code>MatcherFactory</code> applied to the same value.
   *
   * @param rightMatcherFactory1 the <code>MatcherFactory</code> to logical-and with this <code>MatcherFactory</code>
   * @return a <code>MatcherFactory</code> that performs the logical-and of this and the passed <code>MatcherFactory</code>
   */
  def and[U, TC1[_]](rightMatcherFactory1: MatcherFactory1[U, TC1]): MatcherFactory1[T with U, TC1] =
    new MatcherFactory1[T with U, TC1] {
      def matcher[V <: T with U : TC1]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val rightMatcher = rightMatcherFactory1.matcher
            andMatchersAndApply(left, outerInstance, rightMatcher)
          }
        }
      }
      override def toString: String = "(" + Prettifier.default(outerInstance) + ") and (" + Prettifier.default(rightMatcherFactory1) + ")"
    }

  /**
   * Returns a matcher whose <code>apply</code> method returns a <code>MatchResult</code>
   * that represents the logical-or of the results of this and the passed matcher applied to
   * the same value.
   *
   * <p>
   * The reason <code>or</code> has an upper bound on its type parameter is so that the <code>Matcher</code>
   * resulting from an invocation of <code>or</code> will have the correct type parameter. If you call
   * <code>or</code> on a <code>Matcher[Orange]</code>, passing in a <code>Matcher[Valencia]</code>,
   * the result will have type <code>Matcher[Valencia]</code>. This is correct because both a
   * <code>Matcher[Orange]</code> and a <code>Matcher[Valencia]</code> know how to match a
   * <code>Valencia</code> (but a <code>Matcher[Valencia]</code> doesn't know how to
   * match any old <code>Orange</code>).  If you call
   * <code>or</code> on a <code>Matcher[Orange]</code>, passing in a <code>Matcher[Fruit]</code>,
   * the result will have type <code>Matcher[Orange]</code>. This is also correct because both a
   * <code>Matcher[Orange]</code> and a <code>Matcher[Fruit]</code> know how to match an
   * <code>Orange</code> (but a <code>Matcher[Orange]</code> doesn't know how to
   * match any old <code>Fruit</code>).
   * </p>
   *
   * @param rightMatcher the matcher to logical-or with this matcher
   * @return a matcher that performs the logical-or of this and the passed matcher
   */
  def or[U <: T](rightMatcher: Matcher[U]): Matcher[U] =
    new Matcher[U] {
      def apply(left: U): MatchResult = {
        orMatchersAndApply(left, outerInstance, rightMatcher)
      }
      override def toString: String = "(" + Prettifier.default(outerInstance) + ") or (" + Prettifier.default(rightMatcher) + ")"
    }

  /**
   * Returns a <code>MatcherFactory</code> whose <code>matcher</code> method returns a <code>Matcher</code>,
   * which has <code>apply</code> method that returns a <code>MatchResult</code> that represents the logical-or
   * of the results of the wrapped and the passed <code>MatcherFactory</code> applied to the same value.
   *
   * @param rightMatcherFactory1 the <code>MatcherFactory</code> to logical-or with this <code>MatcherFactory</code>
   * @return a <code>MatcherFactory</code> that performs the logical-or of this and the passed <code>MatcherFactory</code>
   */
  def or[U, TC1[_]](rightMatcherFactory1: MatcherFactory1[U, TC1]): MatcherFactory1[T with U, TC1] =
    new MatcherFactory1[T with U, TC1] {
      def matcher[V <: T with U : TC1]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val rightMatcher = rightMatcherFactory1.matcher
            orMatchersAndApply(left, outerInstance, rightMatcher)
          }
          override def toString: String = "(" + Prettifier.default(outerInstance) + ") or (" + Prettifier.default(rightMatcherFactory1) + ")"
        }
      }
      override def toString: String = "(" + Prettifier.default(outerInstance) + ") or (" + Prettifier.default(rightMatcherFactory1) + ")"
    }

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndHaveWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and have length (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                   ^
     * </pre>
     **/
    def length(expectedLength: Long): MatcherFactory1[T, Length] = and(MatcherWords.have.length(expectedLength))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and have size (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                                 ^
     * </pre>
     **/
    def size(expectedSize: Long): MatcherFactory1[T, Size] = and(MatcherWords.have.size(expectedSize))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and have message (<span class="stQuotedString">"A message from Mars"</span>)
     *                   ^
     * </pre>
     **/
    def message(expectedMessage: String): MatcherFactory1[T, Messaging] = and(MatcherWords.have.message(expectedMessage))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and have size (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
   *          ^
   * </pre>
   **/
  def and(haveWord: HaveWord): AndHaveWord = new AndHaveWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndContainWord(prettifier: Prettifier, pos: source.Position) {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                      ^
     * </pre>
     **/
    def apply[U](expectedElement: Any): MatcherFactory1[T with U, Containing] = outerInstance.and(MatcherWords.contain(expectedElement))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain key (<span class="stQuotedString">"one"</span>)
     *                      ^
     * </pre>
     **/
    def key(expectedKey: Any): MatcherFactory1[T, KeyMapping] = outerInstance.and(MatcherWords.contain.key(expectedKey))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain value (<span class="stLiteral">1</span>)
     *                      ^
     * </pre>
     **/
    def value(expectedValue: Any): MatcherFactory1[T, ValueMapping] = outerInstance.and(MatcherWords.contain.value(expectedValue))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain theSameElementsAs <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def theSameElementsAs(right: GenTraversable[_]): MatcherFactory1[T, Aggregating] = 
      outerInstance.and(MatcherWords.contain.theSameElementsAs(right))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain theSameElementsInOrderAs <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def theSameElementsInOrderAs(right: GenTraversable[_]): MatcherFactory1[T, Sequencing] = 
      outerInstance.and(MatcherWords.contain.theSameElementsInOrderAs(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain inOrderOnly (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def inOrderOnly(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Sequencing] =
      outerInstance.and(MatcherWords.contain.inOrderOnly(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain allOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def allOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.contain.allOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain allElementsOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def allElementsOf(elements: GenTraversable[Any]): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.contain.allElementsOf(elements))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain inOrder (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def inOrder(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Sequencing] =
      outerInstance.and(MatcherWords.contain.inOrder(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain inOrderElementsOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def inOrderElementsOf(elements: GenTraversable[Any]): MatcherFactory1[T, Sequencing] =
      outerInstance.and(MatcherWords.contain.inOrderElementsOf(elements))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain oneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def oneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Containing] =
      outerInstance.and(MatcherWords.contain.oneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain oneElementOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def oneElementOf(elements: GenTraversable[Any]): MatcherFactory1[T, Containing] =
      outerInstance.and(MatcherWords.contain.oneElementOf(elements))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain atLeastOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def atLeastOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.contain.atLeastOneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain atLeastOneElementOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def atLeastOneElementOf(elements: GenTraversable[Any]): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.contain.atLeastOneElementOf(elements))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain only (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def only(right: Any*): MatcherFactory1[T, Aggregating] = 
      outerInstance.and(MatcherWords.contain.only(right.toList: _*)(prettifier, pos))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain noneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def noneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Containing] =
      outerInstance.and(MatcherWords.contain.noneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain noElementsOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def noElementsOf(elements: GenTraversable[Any]): MatcherFactory1[T, Containing] =
      outerInstance.and(MatcherWords.contain.noElementsOf(elements))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain atMostOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def atMostOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.contain.atMostOneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and contain atMostOneElementOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                      ^
     * </pre>
     **/
    def atMostOneElementOf(elements: GenTraversable[Any]): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.contain.atMostOneElementOf(elements))
  }
  
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and contain key (<span class="stQuotedString">"one"</span>)
   *          ^
   * </pre>
   **/
  def and(containWord: ContainWord)(implicit prettifier: Prettifier, pos: source.Position): AndContainWord = new AndContainWord(prettifier, pos)

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndBeWord {

    // SKIP-SCALATESTJS,NATIVE-START
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and be a (<span class="stQuotedString">'file</span>)
     *                 ^
     * </pre>
     **/
    def a(symbol: Symbol): Matcher[T with AnyRef] = and(MatcherWords.be.a(symbol))
    // SKIP-SCALATESTJS,NATIVE-END

    /**
     * This method enables the following syntax, where <code>file</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and be a (file)
     *                 ^
     * </pre>
     **/
    def a[U](bePropertyMatcher: BePropertyMatcher[U]): Matcher[T with AnyRef with U] = and(MatcherWords.be.a(bePropertyMatcher))

    /**
     * This method enables the following syntax, where <code>positiveNumber</code> and <code>validNumber</code> are <a href="AMatcher.html"><code>AMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and be a (validNumber)
     *                 ^
     * </pre>
     **/
    def a[U](aMatcher: AMatcher[U]): Matcher[T with U] = and(MatcherWords.be.a(aMatcher))

    // SKIP-SCALATESTJS,NATIVE-START
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and be an (<span class="stQuotedString">'apple</span>)
     *                 ^
     * </pre>
     **/
    def an(symbol: Symbol): Matcher[T with AnyRef] = and(MatcherWords.be.an(symbol))
    // SKIP-SCALATESTJS,NATIVE-END

    /**
     * This method enables the following syntax, where <code>apple</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and be an (apple)
     *                 ^
     * </pre>
     **/
    def an[U](bePropertyMatcher: BePropertyMatcher[U]): Matcher[T with AnyRef with U] = and(MatcherWords.be.an(bePropertyMatcher))
    
    /**
     * This method enables the following syntax, where <code>integerNumber</code> is an <a href="AnMatcher.html"><code>AnMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and be an (integerNumber)
     *                 ^
     * </pre>
     **/
    def an[U](anMatcher: AnMatcher[U]): Matcher[T with U] = and(MatcherWords.be.an(anMatcher))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and be theSameInstanceAs (string)
     *                 ^
     * </pre>
     **/
    def theSameInstanceAs(anyRef: AnyRef): Matcher[T with AnyRef] = and(MatcherWords.be.theSameInstanceAs(anyRef))
    
    /**
     * This method enables the following syntax, where <code>fraction</code> refers to a <code>PartialFunction</code>:
     *
     * <pre class="stHighlighted">
     * aMatcher and be definedAt (<span class="stLiteral">8</span>)
     *                 ^
     * </pre>
     **/
    def definedAt[A, U <: PartialFunction[A, _]](right: A): Matcher[T with U] = and(MatcherWords.be.definedAt(right))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and be a (<span class="stQuotedString">'file</span>)
   *          ^
   * </pre>
   **/
  def and(beWord: BeWord): AndBeWord = new AndBeWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndFullyMatchWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and fullyMatch regex (decimal)
     *                         ^
     * </pre>
     **/
    def regex(regexString: String): Matcher[T with String] = and(MatcherWords.fullyMatch.regex(regexString))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and fullyMatch regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                         ^
     * </pre>
     **/
    def regex(regexWithGroups: RegexWithGroups): Matcher[T with String] = and(MatcherWords.fullyMatch.regex(regexWithGroups))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and fullyMatch regex (decimalRegex)
     *                         ^
     * </pre>
     **/
    def regex(regex: Regex): Matcher[T with String] = and(MatcherWords.fullyMatch.regex(regex))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and fullyMatch regex (decimalRegex)
   *          ^
   * </pre>
   **/
  def and(fullyMatchWord: FullyMatchWord): AndFullyMatchWord = new AndFullyMatchWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndIncludeWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and include regex (decimal)
     *                      ^
     * </pre>
     **/
    def regex(regexString: String): Matcher[T with String] = and(MatcherWords.include.regex(regexString))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and include regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                      ^
     * </pre>
     **/
    def regex(regexWithGroups: RegexWithGroups): Matcher[T with String] = and(MatcherWords.include.regex(regexWithGroups))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and include regex (decimalRegex)
     *                      ^
     * </pre>
     **/
    def regex(regex: Regex): Matcher[T with String] = and(MatcherWords.include.regex(regex))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and include regex (<span class="stQuotedString">"wor.d"</span>)
   *          ^
   * </pre>
   **/
  def and(includeWord: IncludeWord): AndIncludeWord = new AndIncludeWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndStartWithWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and startWith regex (decimal)
     *                        ^
     * </pre>
     **/
    def regex(regexString: String): Matcher[T with String] = and(MatcherWords.startWith.regex(regexString))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and startWith regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                        ^
     * </pre>
     **/
    def regex(regexWithGroups: RegexWithGroups): Matcher[T with String] = and(MatcherWords.startWith.regex(regexWithGroups))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and startWith regex (decimalRegex)
     *                        ^
     * </pre>
     **/
    def regex(regex: Regex): Matcher[T with String] = and(MatcherWords.startWith.regex(regex))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and startWith regex (<span class="stQuotedString">"1.7"</span>)
   *          ^
   * </pre>
   **/
  def and(startWithWord: StartWithWord): AndStartWithWord = new AndStartWithWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndEndWithWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and endWith regex (decimal)
     *                      ^
     * </pre>
     **/
    def regex(regexString: String): Matcher[T with String] = and(MatcherWords.endWith.regex(regexString))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and endWith regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                      ^
     * </pre>
     **/
    def regex(regexWithGroups: RegexWithGroups): Matcher[T with String] = and(MatcherWords.endWith.regex(regexWithGroups))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and endWith regex (decimalRegex)
     *                      ^
     * </pre>
     **/
    def regex(regex: Regex): Matcher[T with String] = and(MatcherWords.endWith.regex(regex))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and endWith regex (decimalRegex)
   *          ^
   * </pre>
   **/
  def and(endWithWord: EndWithWord): AndEndWithWord = new AndEndWithWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndNotWord {

    /**
     * Get the <code>Matcher</code> instance, currently used by macro only.
     */
    val owner = outerInstance

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not equal (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                  ^
     * </pre>
     **/
    def equal(any: Any): MatcherFactory1[T, Equality] =
      outerInstance.and(MatcherWords.not.apply(MatcherWords.equal(any)))

    /**
     * This method enables the following syntax, for the "primitive" numeric types:
     *
     * <pre class="stHighlighted">
     * aMatcher and not equal (<span class="stLiteral">17.0</span> +- <span class="stLiteral">0.2</span>)
     *                  ^
     * </pre>
     **/
    def equal[U](spread: Spread[U]): Matcher[T with U] = outerInstance.and(MatcherWords.not.equal(spread))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not equal (<span class="stReserved">null</span>)
     *                  ^
     * </pre>
     **/
    def equal(o: Null): Matcher[T] = {
      outerInstance and {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
            MatchResult(
              left != null,
              Resources.rawEqualedNull,
              Resources.rawDidNotEqualNull,
              Resources.rawMidSentenceEqualedNull,
              Resources.rawDidNotEqualNull,
              Vector.empty, 
              Vector(left)
            )
          }
          override def toString: String = "not equal null"
        }
      }
    }

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                  ^
     * </pre>
     **/
    def be(any: Any): Matcher[T] =
      outerInstance.and(MatcherWords.not.apply(MatcherWords.be(any)))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not have length (<span class="stLiteral">3</span>)
     *                  ^
     * </pre>
     **/
    def have(resultOfLengthWordApplication: ResultOfLengthWordApplication): MatcherFactory1[T, Length] =
      outerInstance.and(MatcherWords.not.apply(MatcherWords.have.length(resultOfLengthWordApplication.expectedLength)))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not have size (<span class="stLiteral">3</span>)
     *                  ^
     * </pre>
     **/
    def have(resultOfSizeWordApplication: ResultOfSizeWordApplication): MatcherFactory1[T, Size] =
      outerInstance.and(MatcherWords.not.apply(MatcherWords.have.size(resultOfSizeWordApplication.expectedSize)))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not have message (<span class="stQuotedString">"Message from Mars!"</span>)
     *                  ^
     * </pre>
     **/
    def have(resultOfMessageWordApplication: ResultOfMessageWordApplication): MatcherFactory1[T, Messaging] =
      outerInstance.and(MatcherWords.not.apply(MatcherWords.have.message(resultOfMessageWordApplication.expectedMessage)))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not have (author (<span class="stQuotedString">"Melville"</span>))
     *                  ^
     * </pre>
     **/
    def have[U](firstPropertyMatcher: HavePropertyMatcher[U, _], propertyMatchers: HavePropertyMatcher[U, _]*): Matcher[T with U] =
      outerInstance.and(MatcherWords.not.apply(MatcherWords.have(firstPropertyMatcher, propertyMatchers: _*)))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be &lt; (<span class="stLiteral">6</span>)
     *                  ^
     * </pre>
     **/
    def be[U](resultOfLessThanComparison: ResultOfLessThanComparison[U]): Matcher[T with U] =
      outerInstance.and(MatcherWords.not.be(resultOfLessThanComparison))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be (<span class="stReserved">null</span>)
     *                  ^
     * </pre>
     **/
    def be(o: Null): Matcher[T with AnyRef] = outerInstance.and(MatcherWords.not.be(o))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be &gt; (<span class="stLiteral">6</span>)
     *                  ^
     * </pre>
     **/
    def be[U](resultOfGreaterThanComparison: ResultOfGreaterThanComparison[U]): Matcher[T with U] =
      outerInstance.and(MatcherWords.not.be(resultOfGreaterThanComparison))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be &lt;= (<span class="stLiteral">2</span>)
     *                  ^
     * </pre>
     **/
    def be[U](resultOfLessThanOrEqualToComparison: ResultOfLessThanOrEqualToComparison[U]): Matcher[T with U] =
      outerInstance.and(MatcherWords.not.be(resultOfLessThanOrEqualToComparison))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be &gt;= (<span class="stLiteral">6</span>)
     *                  ^
     * </pre>
     **/
    def be[U](resultOfGreaterThanOrEqualToComparison: ResultOfGreaterThanOrEqualToComparison[U]): Matcher[T with U] =
      outerInstance.and(MatcherWords.not.be(resultOfGreaterThanOrEqualToComparison))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be === (<span class="stLiteral">6</span>)
     *                  ^
     * </pre>
     **/
    def be(tripleEqualsInvocation: TripleEqualsInvocation[_]): Matcher[T] =
      outerInstance.and(MatcherWords.not.be(tripleEqualsInvocation))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be (<span class="stQuotedString">'empty</span>)
     *                  ^
     * </pre>
     **/
    def be(symbol: Symbol): Matcher[T with AnyRef] = outerInstance.and(MatcherWords.not.be(symbol))

    /**
     * This method enables the following syntax, where <code>odd</code> is a <a href="BeMatcher.html"><code>BeMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be (odd)
     *                  ^
     * </pre>
     **/
    def be[U](beMatcher: BeMatcher[U]): Matcher[T with U] = outerInstance.and(MatcherWords.not.be(beMatcher))

    /**
     * This method enables the following syntax, where <code>directory</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be (directory)
     *                  ^
     * </pre>
     **/
    def be[U](bePropertyMatcher: BePropertyMatcher[U]): Matcher[T with AnyRef with U] = outerInstance.and(MatcherWords.not.be(bePropertyMatcher))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be a (<span class="stQuotedString">'file</span>)
     *                  ^
     * </pre>
     **/
    def be(resultOfAWordApplication: ResultOfAWordToSymbolApplication): Matcher[T with AnyRef] = outerInstance.and(MatcherWords.not.be(resultOfAWordApplication))

    /**
     * This method enables the following syntax, where <code>validMarks</code> is an <a href="AMatcher.html"><code>AMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be a (validMarks)
     *                  ^
     * </pre>
     **/
    def be[U](resultOfAWordApplication: ResultOfAWordToAMatcherApplication[U]): Matcher[T with U] = outerInstance.and(MatcherWords.not.be(resultOfAWordApplication))
    
    /**
     * This method enables the following syntax, where <code>directory</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be a (directory)
     *                  ^
     * </pre>
     **/
    def be[U <: AnyRef](resultOfAWordApplication: ResultOfAWordToBePropertyMatcherApplication[U]): Matcher[T with U] = outerInstance.and(MatcherWords.not.be(resultOfAWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be an (<span class="stQuotedString">'apple</span>)
     *                  ^
     * </pre>
     **/
    def be(resultOfAnWordApplication: ResultOfAnWordToSymbolApplication): Matcher[T with AnyRef] = outerInstance.and(MatcherWords.not.be(resultOfAnWordApplication))

    /**
     * This method enables the following syntax, where <code>directory</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be an (directory)
     *                  ^
     * </pre>
     **/
    def be[T <: AnyRef](resultOfAnWordApplication: ResultOfAnWordToBePropertyMatcherApplication[T]) = outerInstance.and(MatcherWords.not.be(resultOfAnWordApplication))

    /**
     * This method enables the following syntax, where <code>invalidMarks</code> is an <a href="AnMatcher.html"><code>AnMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be an (invalidMarks)
     *                  ^
     * </pre>
     **/
    def be[U](resultOfAnWordApplication: ResultOfAnWordToAnMatcherApplication[U]): Matcher[T with U] = outerInstance.and(MatcherWords.not.be(resultOfAnWordApplication))

    import language.experimental.macros

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be a [<span class="stType">Book</span>]
     *                  ^
     * </pre>
     **/
    def be(aType: ResultOfATypeInvocation[_]): Matcher[T] = macro TypeMatcherMacro.andNotATypeMatcher
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be an [<span class="stType">Apple</span>]
     *                  ^
     * </pre>
     **/
    def be(anType: ResultOfAnTypeInvocation[_]): Matcher[T] = macro TypeMatcherMacro.andNotAnTypeMatcher
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be theSameInstanceAs (otherString)
     *                  ^
     * </pre>
     **/
    def be(resultOfTheSameInstanceAsApplication: ResultOfTheSameInstanceAsApplication): Matcher[T with AnyRef] = outerInstance.and(MatcherWords.not.be(resultOfTheSameInstanceAsApplication))

    /**
     * This method enables the following syntax, for the "primitive" numeric types:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be (<span class="stLiteral">17.0</span> +- <span class="stLiteral">0.2</span>)
     *                  ^
     * </pre>
     **/
    def be[U](spread: Spread[U]): Matcher[T with U] = outerInstance.and(MatcherWords.not.be(spread))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be definedAt (<span class="stLiteral">8</span>)
     *                  ^
     * </pre>
     **/
    def be[A, U <: PartialFunction[A, _]](resultOfDefinedAt: ResultOfDefinedAt[A]): Matcher[T with U] =
      outerInstance.and(MatcherWords.not.be(resultOfDefinedAt))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be sorted
     *                  ^
     * </pre>
     **/
    def be(sortedWord: SortedWord) = 
      outerInstance.and(MatcherWords.not.be(sortedWord))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be readable
     *                  ^
     * </pre>
     **/
    def be(readableWord: ReadableWord) = 
      outerInstance.and(MatcherWords.not.be(readableWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be writable
     *                  ^
     * </pre>
     **/
    def be(writableWord: WritableWord) = 
      outerInstance.and(MatcherWords.not.be(writableWord))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be empty
     *                  ^
     * </pre>
     **/
    def be(emptyWord: EmptyWord) = 
      outerInstance.and(MatcherWords.not.be(emptyWord))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be defined
     *                  ^
     * </pre>
     **/
    def be(definedWord: DefinedWord) = 
      outerInstance.and(MatcherWords.not.be(definedWord))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not fullyMatch regex (decimal)
     *                  ^
     * </pre>
     **/
    def fullyMatch(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[T with String] =
      outerInstance.and(MatcherWords.not.fullyMatch(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not include regex (decimal)
     *                  ^
     * </pre>
     **/
    def include(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[T with String] =
      outerInstance.and(MatcherWords.not.include(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not include (<span class="stQuotedString">"1.7"</span>)
     *                  ^
     * </pre>
     **/
    def include(expectedSubstring: String): Matcher[T with String] =
      outerInstance.and(MatcherWords.not.include(expectedSubstring))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not startWith regex (decimal)
     *                  ^
     * </pre>
     **/
    def startWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[T with String] =
      outerInstance.and(MatcherWords.not.startWith(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not startWith (<span class="stQuotedString">"1.7"</span>)
     *                  ^
     * </pre>
     **/
    def startWith(expectedSubstring: String): Matcher[T with String] =
      outerInstance.and(MatcherWords.not.startWith(expectedSubstring))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not endWith regex (decimal)
     *                  ^
     * </pre>
     **/
    def endWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[T with String] =
      outerInstance.and(MatcherWords.not.endWith(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not endWith (<span class="stQuotedString">"1.7"</span>)
     *                  ^
     * </pre>
     **/
    def endWith(expectedSubstring: String): Matcher[T with String] =
      outerInstance.and(MatcherWords.not.endWith(expectedSubstring))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain (<span class="stLiteral">3</span>)
     *                  ^
     * </pre>
     **/
    def contain[U](expectedElement: U): MatcherFactory1[T, Containing] =
      outerInstance.and(MatcherWords.not.contain(expectedElement))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain oneOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfOneOfApplication): MatcherFactory1[T, Containing] =
      outerInstance.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain oneElementOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfOneElementOfApplication): MatcherFactory1[T, Containing] =
      outerInstance.and(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain atLeastOneOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfAtLeastOneOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain atLeastOneElementOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfAtLeastOneElementOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain noneOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfNoneOfApplication): MatcherFactory1[T, Containing] =
      outerInstance.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain noElementsOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfNoElementsOfApplication): MatcherFactory1[T, Containing] =
      outerInstance.and(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain theSameElementsAs (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfTheSameElementsAsApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain theSameElementsInOrderAs (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfTheSameElementsInOrderAsApplication): MatcherFactory1[T, Sequencing] =
      outerInstance.and(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain only (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfOnlyApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain inOrderOnly (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfInOrderOnlyApplication): MatcherFactory1[T, Sequencing] =
      outerInstance.and(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain allOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfAllOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain allElementsOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfAllElementsOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain inOrder (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfInOrderApplication): MatcherFactory1[T, Sequencing] =
      outerInstance.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain inOrderElementsOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                  ^
     * </pre>
     **/
    def contain(right: ResultOfInOrderElementsOfApplication): MatcherFactory1[T, Sequencing] =
      outerInstance.and(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain atMostOneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                          ^
     * </pre>
     **/
    def contain(right: ResultOfAtMostOneOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain atMostOneOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                          ^
     * </pre>
     **/
    def contain(right: ResultOfAtMostOneElementOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.and(MatcherWords.not.contain(right))

// TODO: Write tests and impl for contain ResultOfKey/ValueWordApplication
    /**
     * This method enables the following syntax given a <code>Matcher</code>:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain key (<span class="stQuotedString">"three"</span>)
     *                  ^
     * </pre>
     **/
    def contain(resultOfKeyWordApplication: ResultOfKeyWordApplication): MatcherFactory1[T, KeyMapping] =
      outerInstance.and(MatcherWords.not.contain(resultOfKeyWordApplication))

    /**
     * This method enables the following syntax given a <code>Matcher</code>:
     *
     * <pre class="stHighlighted">
     * aMatcher and not contain value (<span class="stLiteral">3</span>)
     *                  ^
     * </pre>
     **/
    def contain(resultOfValueWordApplication: ResultOfValueWordApplication): MatcherFactory1[T, ValueMapping] =
      outerInstance.and(MatcherWords.not.contain(resultOfValueWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher and not matchPattern { <span class="stReserved">case</span> <span class="stType">Person</span>(<span class="stQuotedString">"Bob"</span>, _) =>}
     *                  ^
     * </pre>
     **/
    def matchPattern(right: PartialFunction[Any, _]): Matcher[Any] = macro MatchPatternMacro.andNotMatchPatternMatcher
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and not contain value (<span class="stLiteral">3</span>)
   *          ^
   * </pre>
   **/
  def and(notWord: NotWord): AndNotWord = new AndNotWord
  
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and exist
   *          ^
   * </pre>
   **/
  def and(existWord: ExistWord): MatcherFactory1[T, Existence] = 
    outerInstance.and(MatcherWords.exist.matcherFactory)
    
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher and not (exist)
   *          ^
   * </pre>
   **/
  def and(notExist: ResultOfNotExist): MatcherFactory1[T, Existence] = 
    outerInstance.and(MatcherWords.not.exist)

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrHaveWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or have length (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                  ^
     * </pre>
     **/
    def length(expectedLength: Long): MatcherFactory1[T, Length] = or(MatcherWords.have.length(expectedLength))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or have size (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                  ^
     * </pre>
     **/
    def size(expectedSize: Long): MatcherFactory1[T, Size] = or(MatcherWords.have.size(expectedSize))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or have message (<span class="stQuotedString">"Message from Mars!"</span>)
     *                  ^
     * </pre>
     **/
    def message(expectedMessage: String): MatcherFactory1[T, Messaging] = or(MatcherWords.have.message(expectedMessage))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or have size (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
   *          ^
   * </pre>
   **/
  def or(haveWord: HaveWord): OrHaveWord = new OrHaveWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrContainWord(prettifier: Prettifier, pos: source.Position) {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                     ^
     * </pre>
     **/
    def apply[U](expectedElement: Any): MatcherFactory1[T with U, Containing] = outerInstance.or(MatcherWords.contain(expectedElement))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain key (<span class="stQuotedString">"one"</span>)
     *                     ^
     * </pre>
     **/
    def key(expectedKey: Any): MatcherFactory1[T, KeyMapping] = outerInstance.or(MatcherWords.contain.key(expectedKey))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain value (<span class="stLiteral">1</span>)
     *                     ^
     * </pre>
     **/
    def value(expectedValue: Any): MatcherFactory1[T, ValueMapping] = outerInstance.or(MatcherWords.contain.value(expectedValue))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain theSameElementsAs <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def theSameElementsAs(right: GenTraversable[_]): MatcherFactory1[T, Aggregating] = 
      outerInstance.or(MatcherWords.contain.theSameElementsAs(right))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain theSameElementsInOrderAs <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def theSameElementsInOrderAs(right: GenTraversable[_]): MatcherFactory1[T, Sequencing] = 
      outerInstance.or(MatcherWords.contain.theSameElementsInOrderAs(right))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain allOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def allOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.contain.allOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain allElementsOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def allElementsOf(elements: GenTraversable[Any]): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.contain.allElementsOf(elements))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain inOrder (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def inOrder(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Sequencing] =
      outerInstance.or(MatcherWords.contain.inOrder(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain inOrderElementsOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def inOrderElementsOf(elements: GenTraversable[Any]): MatcherFactory1[T, Sequencing] =
      outerInstance.or(MatcherWords.contain.inOrderElementsOf(elements))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain oneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def oneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Containing] =
      outerInstance.or(MatcherWords.contain.oneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain oneElementOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def oneElementOf(elements: GenTraversable[Any]): MatcherFactory1[T, Containing] =
      outerInstance.or(MatcherWords.contain.oneElementOf(elements))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain atLeastOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def atLeastOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.contain.atLeastOneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain atLeastOneElementOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def atLeastOneElementOf(elements: GenTraversable[Any]): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.contain.atLeastOneElementOf(elements))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain only (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def only(right: Any*): MatcherFactory1[T, Aggregating] = 
      outerInstance.or(MatcherWords.contain.only(right.toList: _*)(prettifier, pos))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain inOrderOnly (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def inOrderOnly(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Sequencing] =
      outerInstance.or(MatcherWords.contain.inOrderOnly(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain noneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def noneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Containing] =
      outerInstance.or(MatcherWords.contain.noneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain noElementsOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def noElementsOf(elements: GenTraversable[Any]): MatcherFactory1[T, Containing] =
      outerInstance.or(MatcherWords.contain.noElementsOf(elements))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain atMostOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def atMostOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.contain.atMostOneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or contain atMostOneOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                     ^
     * </pre>
     **/
    def atMostOneElementOf(elements: GenTraversable[Any]): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.contain.atMostOneElementOf(elements))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or contain value (<span class="stLiteral">1</span>)
   *          ^
   * </pre>
   **/
  def or(containWord: ContainWord)(implicit prettifier: Prettifier, pos: source.Position): OrContainWord = new OrContainWord(prettifier, pos)
  
  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrBeWord {

    // SKIP-SCALATESTJS,NATIVE-START
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or be a (<span class="stQuotedString">'directory</span>)
     *                ^
     * </pre>
     **/
    def a(symbol: Symbol): Matcher[T with AnyRef] = or(MatcherWords.be.a(symbol))
    // SKIP-SCALATESTJS,NATIVE-END

    /**
     * This method enables the following syntax, where <code>directory</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher or be a (directory)
     *                ^
     * </pre>
     **/
    def a[U](bePropertyMatcher: BePropertyMatcher[U]): Matcher[T with AnyRef with U] = or(MatcherWords.be.a(bePropertyMatcher))
    
    /**
     * This method enables the following syntax, where <code>positiveNumber</code> and <code>validNumber</code> are <a href="AMatcher.html"><code>AMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher or be a (validNumber)
     *                ^
     * </pre>
     **/
    def a[U](aMatcher: AMatcher[U]): Matcher[T with U] = or(MatcherWords.be.a(aMatcher))

    // SKIP-SCALATESTJS,NATIVE-START
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or be an (<span class="stQuotedString">'apple</span>)
     *                ^
     * </pre>
     **/
    def an(symbol: Symbol): Matcher[T with AnyRef] = or(MatcherWords.be.an(symbol))
    // SKIP-SCALATESTJS,NATIVE-END

    /**
     * This method enables the following syntax, where <code>orange</code> and <code>apple</code> are <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher or be an (apple)
     *                ^
     * </pre>
     **/
    def an[U](bePropertyMatcher: BePropertyMatcher[U]): Matcher[T with AnyRef with U] = or(MatcherWords.be.an(bePropertyMatcher))

    /**
     * This method enables the following syntax, where <code>oddNumber</code> and <code>integerNumber</code> are <a href="AnMatcher.html"><code>AnMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher or be an (integerNumber)
     *                ^
     * </pre>
     **/
    def an[U](anMatcher: AnMatcher[U]): Matcher[T with U] = or(MatcherWords.be.an(anMatcher))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or be theSameInstanceAs (otherString)
     *                ^
     * </pre>
     **/
    def theSameInstanceAs(anyRef: AnyRef): Matcher[T with AnyRef] = or(MatcherWords.be.theSameInstanceAs(anyRef))
    
    /**
     * This method enables the following syntax, where <code>fraction</code> refers to a <code>PartialFunction</code>:
     *
     * <pre class="stHighlighted">
     * aMatcher or be definedAt (<span class="stLiteral">8</span>)
     *                ^
     * </pre>
     **/
    def definedAt[A, U <: PartialFunction[A, _]](right: A): Matcher[T with U] = or(MatcherWords.be.definedAt(right))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or be a (<span class="stQuotedString">'directory</span>)
   *          ^
   * </pre>
   **/
  def or(beWord: BeWord): OrBeWord = new OrBeWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrFullyMatchWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or fullyMatch regex (decimal)
     *                        ^
     * </pre>
     **/
    def regex(regexString: String): Matcher[T with String] = or(MatcherWords.fullyMatch.regex(regexString))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or fullyMatch regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                        ^
     * </pre>
     **/
    def regex(regexWithGroups: RegexWithGroups): Matcher[T with String] = or(MatcherWords.fullyMatch.regex(regexWithGroups))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or fullyMatch regex (decimal)
     *                        ^
     * </pre>
     **/
    def regex(regex: Regex): Matcher[T with String] = or(MatcherWords.fullyMatch.regex(regex))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or fullyMatch regex (decimal)
   *          ^
   * </pre>
   **/
  def or(fullyMatchWord: FullyMatchWord): OrFullyMatchWord = new OrFullyMatchWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrIncludeWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or include regex (decimal)
     *                     ^
     * </pre>
     **/
    def regex(regexString: String): Matcher[T with String] = or(MatcherWords.include.regex(regexString))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or include regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                     ^
     * </pre>
     **/
    def regex(regexWithGroups: RegexWithGroups): Matcher[T with String] = or(MatcherWords.include.regex(regexWithGroups))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or include regex (decimal)
     *                     ^
     * </pre>
     **/
    def regex(regex: Regex): Matcher[T with String] = or(MatcherWords.include.regex(regex))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or include regex (<span class="stQuotedString">"1.7"</span>)
   *          ^
   * </pre>
   **/
  def or(includeWord: IncludeWord): OrIncludeWord = new OrIncludeWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrStartWithWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or startWith regex (decimal)
     *                       ^
     * </pre>
     **/
    def regex(regexString: String): Matcher[T with String] = or(MatcherWords.startWith.regex(regexString))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or startWith regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                       ^
     * </pre>
     **/
    def regex(regexWithGroups: RegexWithGroups): Matcher[T with String] = or(MatcherWords.startWith.regex(regexWithGroups))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or startWith regex (decimal)
     *                       ^
     * </pre>
     **/
    def regex(regex: Regex): Matcher[T with String] = or(MatcherWords.startWith.regex(regex))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or startWith regex (<span class="stQuotedString">"1.7"</span>)
   *          ^
   * </pre>
   **/
  def or(startWithWord: StartWithWord): OrStartWithWord = new OrStartWithWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrEndWithWord {

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or endWith regex (decimal)
     *                     ^
     * </pre>
     **/
    def regex(regexString: String): Matcher[T with String] = or(MatcherWords.endWith.regex(regexString))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or endWith regex (<span class="stQuotedString">"d(e*)f"</span> withGroup <span class="stQuotedString">"ee"</span>)
     *                     ^
     * </pre>
     **/
    def regex(regexWithGroups: RegexWithGroups): Matcher[T with String] = or(MatcherWords.endWith.regex(regexWithGroups))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or endWith regex (decimal)
     *                     ^
     * </pre>
     **/
    def regex(regex: Regex): Matcher[T with String] = or(MatcherWords.endWith.regex(regex))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or endWith regex (<span class="stQuotedString">"7b"</span>)
   *          ^
   * </pre>
   **/
  def or(endWithWord: EndWithWord): OrEndWithWord = new OrEndWithWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrNotWord {

    /**
     * Get the <code>Matcher</code> instance, currently used by macro only.
     */
    val owner = outerInstance

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not equal (<span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def equal(any: Any): MatcherFactory1[T, Equality] =
      outerInstance.or(MatcherWords.not.apply(MatcherWords.equal(any)))

    /**
     * This method enables the following syntax for the "primitive" numeric types:
     *
     * <pre class="stHighlighted">
     * aMatcher or not equal (<span class="stLiteral">17.0</span> +- <span class="stLiteral">0.2</span>)
     *                 ^
     * </pre>
     **/
    def equal[U](spread: Spread[U]): Matcher[T with U] = outerInstance.or(MatcherWords.not.equal(spread))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not equal (<span class="stReserved">null</span>)
     *                 ^
     * </pre>
     **/
    def equal(o: Null): Matcher[T] = {
      outerInstance or {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
            MatchResult(
              left != null,
              Resources.rawEqualedNull,
              Resources.rawDidNotEqualNull,
              Resources.rawMidSentenceEqualedNull,
              Resources.rawDidNotEqualNull,
              Vector.empty, 
              Vector(left)
            )
          }
          override def toString: String = "not equal null"
        }
      }
    }

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be (<span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def be(any: Any): Matcher[T] =
      outerInstance.or(MatcherWords.not.apply(MatcherWords.be(any)))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not have length (<span class="stLiteral">3</span>)
     *                 ^
     * </pre>
     **/
    def have(resultOfLengthWordApplication: ResultOfLengthWordApplication): MatcherFactory1[T, Length] =
      outerInstance.or(MatcherWords.not.apply(MatcherWords.have.length(resultOfLengthWordApplication.expectedLength)))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not have size (<span class="stLiteral">3</span>)
     *                 ^
     * </pre>
     **/
    def have(resultOfSizeWordApplication: ResultOfSizeWordApplication): MatcherFactory1[T, Size] =
      outerInstance.or(MatcherWords.not.apply(MatcherWords.have.size(resultOfSizeWordApplication.expectedSize)))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not have message (<span class="stQuotedString">"Message from Mars!"</span>)
     *                 ^
     * </pre>
     **/
    def have(resultOfMessageWordApplication: ResultOfMessageWordApplication): MatcherFactory1[T, Messaging] =
      outerInstance.or(MatcherWords.not.apply(MatcherWords.have.message(resultOfMessageWordApplication.expectedMessage)))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not have (author (<span class="stQuotedString">"Melville"</span>))
     *                 ^
     * </pre>
     **/
    def have[U](firstPropertyMatcher: HavePropertyMatcher[U, _], propertyMatchers: HavePropertyMatcher[U, _]*): Matcher[T with U] =
      outerInstance.or(MatcherWords.not.apply(MatcherWords.have(firstPropertyMatcher, propertyMatchers: _*)))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be (<span class="stReserved">null</span>)
     *                 ^
     * </pre>
     **/
    def be(o: Null): Matcher[T with AnyRef] = outerInstance.or(MatcherWords.not.be(o))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be &lt; (<span class="stLiteral">8</span>)
     *                 ^
     * </pre>
     **/
    def be[U](resultOfLessThanComparison: ResultOfLessThanComparison[U]): Matcher[T with U] =
      outerInstance.or(MatcherWords.not.be(resultOfLessThanComparison))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be &gt; (<span class="stLiteral">6</span>)
     *                 ^
     * </pre>
     **/
    def be[U](resultOfGreaterThanComparison: ResultOfGreaterThanComparison[U]): Matcher[T with U] =
      outerInstance.or(MatcherWords.not.be(resultOfGreaterThanComparison))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be &lt;= (<span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def be[U](resultOfLessThanOrEqualToComparison: ResultOfLessThanOrEqualToComparison[U]): Matcher[T with U] =
      outerInstance.or(MatcherWords.not.be(resultOfLessThanOrEqualToComparison))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be &gt;= (<span class="stLiteral">6</span>)
     *                 ^
     * </pre>
     **/
    def be[U](resultOfGreaterThanOrEqualToComparison: ResultOfGreaterThanOrEqualToComparison[U]): Matcher[T with U] =
      outerInstance.or(MatcherWords.not.be(resultOfGreaterThanOrEqualToComparison))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be === (<span class="stLiteral">8</span>)
     *                 ^
     * </pre>
     **/
    def be(tripleEqualsInvocation: TripleEqualsInvocation[_]): Matcher[T] =
      outerInstance.or(MatcherWords.not.be(tripleEqualsInvocation))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be (<span class="stQuotedString">'empty</span>)
     *                 ^
     * </pre>
     **/
    def be(symbol: Symbol): Matcher[T with AnyRef] = outerInstance.or(MatcherWords.not.be(symbol))

    /**
     * This method enables the following syntax, where <code>odd</code> is a <a href="BeMatcher.html"><code>BeMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be (odd)
     *                 ^
     * </pre>
     **/
    def be[U](beMatcher: BeMatcher[U]): Matcher[T with U] = outerInstance.or(MatcherWords.not.be(beMatcher))

    /**
     * This method enables the following syntax, where <code>file</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be (file)
     *                 ^
     * </pre>
     **/
    def be[U](bePropertyMatcher: BePropertyMatcher[U]): Matcher[T with AnyRef with U] = outerInstance.or(MatcherWords.not.be(bePropertyMatcher))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be a (<span class="stQuotedString">'file</span>)
     *                 ^
     * </pre>
     **/
    def be(resultOfAWordApplication: ResultOfAWordToSymbolApplication): Matcher[T with AnyRef] = outerInstance.or(MatcherWords.not.be(resultOfAWordApplication))

    /**
     * This method enables the following syntax, where <code>validMarks</code> is an <a href="AMatcher.html"><code>AMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be a (validMarks)
     *                 ^
     * </pre>
     **/
    def be[U](resultOfAWordApplication: ResultOfAWordToAMatcherApplication[U]): Matcher[T with U] = outerInstance.or(MatcherWords.not.be(resultOfAWordApplication))
    
    /**
     * This method enables the following syntax, where <code>file</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be a (file)
     *                 ^
     * </pre>
     **/
    def be[U <: AnyRef](resultOfAWordApplication: ResultOfAWordToBePropertyMatcherApplication[U]): Matcher[T with U] = outerInstance.or(MatcherWords.not.be(resultOfAWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be an (<span class="stQuotedString">'apple</span>)
     *                    ^
     * </pre>
     **/
    def be(resultOfAnWordApplication: ResultOfAnWordToSymbolApplication): Matcher[T with AnyRef] = outerInstance.or(MatcherWords.not.be(resultOfAnWordApplication))

    /**
     * This method enables the following syntax, where <code>apple</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be an (apple)
     *                 ^
     * </pre>
     **/
    def be[U <: AnyRef](resultOfAnWordApplication: ResultOfAnWordToBePropertyMatcherApplication[U]): Matcher[T with U] = outerInstance.or(MatcherWords.not.be(resultOfAnWordApplication))

    /**
     * This method enables the following syntax, where <code>invalidMarks</code> is an <a href="AnMatcher.html"><code>AnMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcher and not be an (invalidMarks)
     *                  ^
     * </pre>
     **/
    def be[U](resultOfAnWordApplication: ResultOfAnWordToAnMatcherApplication[U]): Matcher[T with U] = outerInstance.or(MatcherWords.not.be(resultOfAnWordApplication))

    import language.experimental.macros

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be a [<span class="stType">Book</span>]
     *                 ^
     * </pre>
     **/
    def be(aType: ResultOfATypeInvocation[_]): Matcher[T] = macro TypeMatcherMacro.orNotATypeMatcher
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be an [<span class="stType">Book</span>]
     *                 ^
     * </pre>
     **/
    def be(anType: ResultOfAnTypeInvocation[_]): Matcher[T] = macro TypeMatcherMacro.orNotAnTypeMatcher
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be theSameInstanceAs (string)
     *                    ^
     * </pre>
     **/
    def be(resultOfTheSameInstanceAsApplication: ResultOfTheSameInstanceAsApplication): Matcher[T with AnyRef] = outerInstance.or(MatcherWords.not.be(resultOfTheSameInstanceAsApplication))

    /**
     * This method enables the following syntax for the "primitive" numeric types:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be (<span class="stLiteral">17.0</span> +- <span class="stLiteral">0.2</span>)
     *                 ^
     * </pre>
     **/
    def be[U](spread: Spread[U]): Matcher[T with U] = outerInstance.or(MatcherWords.not.be(spread))
    
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be definedAt (<span class="stLiteral">8</span>)
     *                 ^
     * </pre>
     **/
    def be[A, U <: PartialFunction[A, _]](resultOfDefinedAt: ResultOfDefinedAt[A]): Matcher[T with U] =
      outerInstance.or(MatcherWords.not.be(resultOfDefinedAt))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be sorted
     *                 ^
     * </pre>
     **/
    def be(sortedWord: SortedWord) = 
      outerInstance.or(MatcherWords.not.be(sortedWord))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be readable
     *                 ^
     * </pre>
     **/
    def be(readableWord: ReadableWord) = 
      outerInstance.or(MatcherWords.not.be(readableWord))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be empty
     *                 ^
     * </pre>
     **/
    def be(emptyWord: EmptyWord) = 
      outerInstance.or(MatcherWords.not.be(emptyWord))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be writable
     *                 ^
     * </pre>
     **/
    def be(writableWord: WritableWord) = 
      outerInstance.or(MatcherWords.not.be(writableWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not be defined
     *                 ^
     * </pre>
     **/
    def be(definedWord: DefinedWord) = 
      outerInstance.or(MatcherWords.not.be(definedWord))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not fullyMatch regex (decimal)
     *                 ^
     * </pre>
     **/
    def fullyMatch(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[T with String] =
      outerInstance.or(MatcherWords.not.fullyMatch(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not include regex (decimal)
     *                 ^
     * </pre>
     **/
    def include(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[T with String] =
      outerInstance.or(MatcherWords.not.include(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not include (<span class="stQuotedString">"1.7"</span>)
     *                 ^
     * </pre>
     **/
    def include(expectedSubstring: String): Matcher[T with String] =
      outerInstance.or(MatcherWords.not.include(expectedSubstring))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not startWith regex (decimal)
     *                 ^
     * </pre>
     **/
    def startWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[T with String] =
      outerInstance.or(MatcherWords.not.startWith(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not startWith (<span class="stQuotedString">"1.7"</span>)
     *                 ^
     * </pre>
     **/
    def startWith(expectedSubstring: String): Matcher[T with String] =
      outerInstance.or(MatcherWords.not.startWith(expectedSubstring))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not endWith regex (decimal)
     *                 ^
     * </pre>
     **/
    def endWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[T with String] =
      outerInstance.or(MatcherWords.not.endWith(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not endWith (<span class="stQuotedString">"1.7"</span>)
     *                 ^
     * </pre>
     **/
    def endWith(expectedSubstring: String): Matcher[T with String] =
      outerInstance.or(MatcherWords.not.endWith(expectedSubstring))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain (<span class="stLiteral">3</span>)
     *                 ^
     * </pre>
     **/
    def contain[U](expectedElement: U): MatcherFactory1[T, Containing] =
      outerInstance.or(MatcherWords.not.contain(expectedElement))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain oneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfOneOfApplication): MatcherFactory1[T, Containing] =
      outerInstance.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain oneElementOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfOneElementOfApplication): MatcherFactory1[T, Containing] =
      outerInstance.or(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain atLeastOneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfAtLeastOneOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain atLeastOneElementOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfAtLeastOneElementOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain noneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfNoneOfApplication): MatcherFactory1[T, Containing] =
      outerInstance.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain noElementsOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfNoElementsOfApplication): MatcherFactory1[T, Containing] =
      outerInstance.or(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain theSameElementsAs (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfTheSameElementsAsApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain theSameElementsInOrderAs (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfTheSameElementsInOrderAsApplication): MatcherFactory1[T, Sequencing] =
      outerInstance.or(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain inOrderOnly (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfInOrderOnlyApplication): MatcherFactory1[T, Sequencing] =
      outerInstance.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain only (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfOnlyApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain allOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfAllOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain allElementsOf <span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfAllElementsOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain inOrder (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfInOrderApplication): MatcherFactory1[T, Sequencing] =
      outerInstance.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain inOrderElementsOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                 ^
     * </pre>
     **/
    def contain(right: ResultOfInOrderElementsOfApplication): MatcherFactory1[T, Sequencing] =
      outerInstance.or(MatcherWords.not.contain(right))
      
    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher not contain atMostOneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *              ^
     * </pre>
     **/
    def contain(right: ResultOfAtMostOneOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher not contain atMostOneElementOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *              ^
     * </pre>
     **/
    def contain(right: ResultOfAtMostOneElementOfApplication): MatcherFactory1[T, Aggregating] =
      outerInstance.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>Matcher</code>:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain key (<span class="stQuotedString">"three"</span>)
     *                 ^
     * </pre>
     **/
    def contain(resultOfKeyWordApplication: ResultOfKeyWordApplication): MatcherFactory1[T, KeyMapping] =
      outerInstance.or(MatcherWords.not.contain(resultOfKeyWordApplication))

    /**
     * This method enables the following syntax given a <code>Matcher</code>:
     *
     * <pre class="stHighlighted">
     * aMatcher or not contain value (<span class="stLiteral">3</span>)
     *                 ^
     * </pre>
     **/
    def contain(resultOfValueWordApplication: ResultOfValueWordApplication): MatcherFactory1[T, ValueMapping] =
      outerInstance.or(MatcherWords.not.contain(resultOfValueWordApplication))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcher or not matchPattern { <span class="stReserved">case</span> <span class="stType">Person</span>(<span class="stQuotedString">"Bob"</span>, _) =>}
     *                 ^
     * </pre>
     **/
    def matchPattern(right: PartialFunction[Any, _]): Matcher[Any] = macro MatchPatternMacro.orNotMatchPatternMatcher
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or not contain value (<span class="stLiteral">3</span>)
   *          ^
   * </pre>
   **/
  def or(notWord: NotWord): OrNotWord = new OrNotWord
  
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or exist
   *          ^
   * </pre>
   **/
  def or(existWord: ExistWord): MatcherFactory1[T, Existence] = 
    outerInstance.or(MatcherWords.exist.matcherFactory)
    
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcher or not (exist)
   *          ^
   * </pre>
   **/
  def or(notExist: ResultOfNotExist): MatcherFactory1[T, Existence] = 
    outerInstance.or(MatcherWords.not.exist)

  /**
   * Creates a new <code>Matcher</code> that will produce <code>MatchResult</code>s by applying the original <code>MatchResult</code>
   * produced by this <code>Matcher</code> to the passed <code>prettify</code> function.  In other words, the <code>MatchResult</code>
   * produced by this <code>Matcher</code> will be passed to <code>prettify</code> to produce the final <code>MatchResult</code>
   *
   * @param prettify a function to apply to the original <code>MatchResult</code> produced by this <code>Matcher</code>
   * @return a new <code>Matcher</code> that will produce <code>MatchResult</code>s by applying the original <code>MatchResult</code>
   *         produced by this <code>Matcher</code> to the passed <code>prettify</code> function
   */
  def mapResult(prettify: MatchResult => MatchResult): Matcher[T] =
    new Matcher[T] {
      def apply(o: T): MatchResult = prettify(outerInstance(o))
    }

  /**
   * Creates a new <code>Matcher</code> that will produce <code>MatchResult</code>s that contain error messages constructed
   * using arguments that are transformed by the passed <code>prettify</code> function.  In other words, the <code>MatchResult</code>
   * produced by this <code>Matcher</code> will use arguments transformed by <code>prettify</code> function to construct the final
   * error messages.
   *
   * @param prettify a function with which to transform the arguments of error messages.
   * @return a new <code>Matcher</code> that will produce <code>MatchResult</code>s that contain error messages constructed
   *         using arguments transformed by the passed <code>prettify</code> function.
   */
  def mapArgs(prettify: Any => String): Matcher[T] =
    new Matcher[T] {
      def apply(o: T): MatchResult = {
        val mr = outerInstance(o)
        mr.copy(
          failureMessageArgs = mr.failureMessageArgs.map((LazyArg(_) { prettify })),
          negatedFailureMessageArgs = mr.negatedFailureMessageArgs.map((LazyArg(_) { prettify })),
          midSentenceFailureMessageArgs = mr.midSentenceFailureMessageArgs.map((LazyArg(_) { prettify })),
          midSentenceNegatedFailureMessageArgs = mr.midSentenceNegatedFailureMessageArgs.map((LazyArg(_) { prettify }))
        )
      }
    }
}

/**
 * Companion object for trait <code>Matcher</code> that provides a
 * factory method that creates a <code>Matcher[T]</code> from a
 * passed function of type <code>(T =&gt; MatchResult)</code>.
 *
 * @author Bill Venners
 */
object Matcher {

  /**
   * Factory method that creates a <code>Matcher[T]</code> from a
   * passed function of type <code>(T =&gt; MatchResult)</code>.
   *
   * @author Bill Venners
   */
  def apply[T](fun: T => MatchResult)(implicit ev: ClassTag[T]): Matcher[T] =
    new Matcher[T] {
      def apply(left: T) = fun(left)
      override def toString: String = "Matcher[" + ev.runtimeClass.getName + "](" + ev.runtimeClass.getName + " => MatchResult)"
    }
}

