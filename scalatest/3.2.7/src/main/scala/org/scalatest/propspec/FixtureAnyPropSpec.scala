/*
 * Copyright 2001-2019 Artima, Inc.
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
package org.scalatest.propspec

import org.scalatest.{Finders, Suite}

/**
 * A sister class to <code>org.scalatest.propspec.AnyPropSpec</code> that can pass a fixture object into its tests.
 *
 * <table><tr><td class="usage">
 * <strong>Recommended Usage</strong>:
 * Use class <code>FixtureAnyPropSpec</code> in situations for which <a href="AnyPropSpec.html"><code>AnyPropSpec</code></a>
 * would be a good choice, when all or most tests need the same fixture objects
 * that must be cleaned up afterwards. <em>Note: <code>FixtureAnyPropSpec</code> is intended for use in special
 * situations, with class <code>AnyPropSpec</code> used for general needs. For
 * more insight into where <code>FixtureAnyPropSpec</code> fits in the big picture, see
 * the <a href="AnyPropSpec.html#withFixtureOneArgTest"><code>withFixture(OneArgTest)</code></a> subsection of
 * the <a href="AnyPropSpec.html#sharedFixtures">Shared fixtures</a> section in the documentation for class <code>AnyPropSpec</code>.</em>
 * </td></tr></table>
 * 
 * <p>
 * Class <code>FixtureAnyPropSpec</code> behaves similarly to class <code>org.scalatest.propspec.AnyPropSpec</code>, except that tests may have a
 * fixture parameter. The type of the
 * fixture parameter is defined by the abstract <code>FixtureParam</code> type, which is a member of this class.
 * This class also has an abstract <code>withFixture</code> method. This <code>withFixture</code> method
 * takes a <code>OneArgTest</code>, which is a nested trait defined as a member of this class.
 * <code>OneArgTest</code> has an <code>apply</code> method that takes a <code>FixtureParam</code>.
 * This <code>apply</code> method is responsible for running a test.
 * This class's <code>runTest</code> method delegates the actual running of each test to <code>withFixture</code>, passing
 * in the test code to run via the <code>OneArgTest</code> argument. The <code>withFixture</code> method (abstract in this class) is responsible
 * for creating the fixture argument and passing it to the test function.
 * </p>
 * 
 * <p>
 * Subclasses of this class must, therefore, do three things differently from a plain old <code>org.scalatest.propspec.AnyPropSpec</code>:
 * </p>
 * 
 * <ol>
 * <li>define the type of the fixture parameter by specifying type <code>FixtureParam</code></li>
 * <li>define the <code>withFixture(OneArgTest)</code> method</li>
 * <li>write tests that take a fixture parameter</li>
 * <li>(You can also define tests that don't take a fixture parameter.)</li>
 * </ol>
 *
 * <p>
 * Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.fixture.propspec
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> prop.PropertyChecks
 * <span class="stReserved">import</span> java.io._
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">propspec.FixtureAnyPropSpec</span> <span class="stReserved">with</span> <span class="stType">PropertyChecks</span> <span class="stReserved">with</span> <span class="stType">Matchers</span> {
 * <br/>  <span class="stLineComment">// 1. define type FixtureParam</span>
 *   <span class="stReserved">type</span> <span class="stType">FixtureParam</span> = <span class="stType">FileReader</span>
 * <br/>  <span class="stLineComment">// 2. define the withFixture method</span>
 *   <span class="stReserved">def</span> withFixture(test: <span class="stType">OneArgTest</span>) = {
 * <br/>    <span class="stReserved">val</span> <span class="stType">FileName</span> = <span class="stQuotedString">"TempFile.txt"</span>
 * <br/>    <span class="stLineComment">// Set up the temp file needed by the test</span>
 *     <span class="stReserved">val</span> writer = <span class="stReserved">new</span> <span class="stType">FileWriter</span>(<span class="stType">FileName</span>)
 *     <span class="stReserved">try</span> {
 *       writer.write(<span class="stQuotedString">"Hello, test!"</span>)
 *     }
 *     <span class="stReserved">finally</span> {
 *       writer.close()
 *     }
 * <br/>    <span class="stLineComment">// Create the reader needed by the test</span>
 *     <span class="stReserved">val</span> reader = <span class="stReserved">new</span> <span class="stType">FileReader</span>(<span class="stType">FileName</span>)
 * <br/>    <span class="stReserved">try</span> {
 *       <span class="stLineComment">// Run the test using the temp file</span>
 *       test(reader)
 *     }
 *     <span class="stReserved">finally</span> {
 *       <span class="stLineComment">// Close and delete the temp file</span>
 *       reader.close()
 *       <span class="stReserved">val</span> file = <span class="stReserved">new</span> <span class="stType">File</span>(<span class="stType">FileName</span>)
 *       file.delete()
 *     }
 *   }
 * <br/>  <span class="stLineComment">// 3. write property-based tests that take a fixture parameter</span>
 *   <span class="stLineComment">// (Hopefully less contrived than the examples shown here.)</span>
 *   property(<span class="stQuotedString">"can read from a temp file"</span>) { reader =&gt;
 *     <span class="stReserved">var</span> builder = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>
 *     <span class="stReserved">var</span> c = reader.read()
 *     <span class="stReserved">while</span> (c != -<span class="stLiteral">1</span>) {
 *       builder.append(c.toChar)
 *       c = reader.read()
 *     }
 *     <span class="stReserved">val</span> fileContents = builder.toString
 *     forAll { (c: <span class="stType">Char</span>) =&gt;
 *       whenever (c != <span class="stQuotedString">'H'</span>) {
 *         fileContents should not startWith c.toString
 *       }
 *     }
 *   }
 * <br/>  property(<span class="stQuotedString">"can read the first char of the temp file"</span>) { reader =&gt;
 *     <span class="stReserved">val</span> firstChar = reader.read()
 *     forAll { (c: <span class="stType">Char</span>) =&gt;
 *       whenever (c != <span class="stQuotedString">'H'</span>) {
 *         c should not equal firstChar
 *       }
 *     }
 *   }
 * <br/>  <span class="stLineComment">// (You can also write tests that don't take a fixture parameter.)</span>
 *   property(<span class="stQuotedString">"can write tests that don't take the fixture"</span>) { () =&gt;
 *     forAll { (i: <span class="stType">Int</span>) => i + i should equal (<span class="stLiteral">2</span> * i) }
 *   }
 * }
 * </pre>
 *
 * <p>
 * Note: to run the examples on this page, you'll need to include <a href="http://www.scalacheck.org">ScalaCheck</a> on the classpath in addition to ScalaTest.
 * </p>
 *
 * <p>
 * In the previous example, <code>withFixture</code> creates and initializes a temp file, then invokes the test function,
 * passing in a <code>FileReader</code> connected to that file.  In addition to setting up the fixture before a test,
 * the <code>withFixture</code> method also cleans it up afterwards. If you need to do some clean up
 * that must happen even if a test fails, you should invoke the test function from inside a <code>try</code> block and do
 * the cleanup in a <code>finally</code> clause, as shown in the previous example.
 * </p>
 *
 * <p>
 * If a test fails, the <code>OneArgTest</code> function will result in a [[org.scalatest.Failed Failed]] wrapping the
 * exception describing the failure.
 * The reason you must perform cleanup in a <code>finally</code> clause is that in case an exception propagates back through
 * <code>withFixture</code>, the <code>finally</code> clause will ensure the fixture cleanup happens as that exception
 * propagates back up the call stack to <code>runTest</code>.
 * </p>
 *
 * <p>
 * If a test doesn't need the fixture, you can indicate that by providing a no-arg instead of a one-arg function.
 * In other words, instead of starting your function literal
 * with something like &ldquo;<code>reader =&gt;</code>&rdquo;, you'd start it with &ldquo;<code>() =&gt;</code>&rdquo;, as is done
 * in the third test in the above example. For such tests, <code>runTest</code>
 * will not invoke <code>withFixture(OneArgTest)</code>. It will instead directly invoke <code>withFixture(NoArgTest)</code>.
 * </p>
 *
 * <a name="multipleFixtures"></a>
 * <h2>Passing multiple fixture objects</h2>
 *
 * <p>
 * If the fixture you want to pass into your tests consists of multiple objects, you will need to combine
 * them into one object to use this class. One good approach to passing multiple fixture objects is
 * to encapsulate them in a case class. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">FixtureParam</span>(builder: <span class="stType">StringBuilder</span>, buffer: <span class="stType">ListBuffer[String]</span>)
 * </pre>
 *
 * <p>
 * To enable the stacking of traits that define <code>withFixture(NoArgTest)</code>, it is a good idea to let
 * <code>withFixture(NoArgTest)</code> invoke the test function instead of invoking the test
 * function directly. To do so, you'll need to convert the <code>OneArgTest</code> to a <code>NoArgTest</code>. You can do that by passing
 * the fixture object to the <code>toNoArgTest</code> method of <code>OneArgTest</code>. In other words, instead of
 * writing &ldquo;<code>test(theFixture)</code>&rdquo;, you'd delegate responsibility for
 * invoking the test function to the <code>withFixture(NoArgTest)</code> method of the same instance by writing:
 * </p>
 *
 * <pre>
 * withFixture(test.toNoArgTest(theFixture))
 * </pre>
 *
 * <p>
 * Here's a complete example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.fixture.propspec.multi
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> prop.PropertyChecks
 * <span class="stReserved">import</span> scala.collection.mutable.ListBuffer
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">propspec.FixtureAnyPropSpec</span> <span class="stReserved">with</span> <span class="stType">PropertyChecks</span> <span class="stReserved">with</span> <span class="stType">Matchers</span> {
 * <br/>  <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">FixtureParam</span>(builder: <span class="stType">StringBuilder</span>, buffer: <span class="stType">ListBuffer[String]</span>)
 * <br/>  <span class="stReserved">def</span> withFixture(test: <span class="stType">OneArgTest</span>) = {
 * <br/>    <span class="stLineComment">// Create needed mutable objects</span>
 *     <span class="stReserved">val</span> stringBuilder = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>(<span class="stQuotedString">"ScalaTest is "</span>)
 *     <span class="stReserved">val</span> listBuffer = <span class="stReserved">new</span> <span class="stType">ListBuffer[String]</span>
 *     <span class="stReserved">val</span> theFixture = <span class="stType">FixtureParam</span>(stringBuilder, listBuffer)
 * <br/>    <span class="stLineComment">// Invoke the test function, passing in the mutable objects</span>
 *     withFixture(test.toNoArgTest(theFixture))
 *   }
 * <br/>  property(<span class="stQuotedString">"testing should be easy"</span>) { f =&gt;
 *     f.builder.append(<span class="stQuotedString">"easy!"</span>)
 *     assert(f.builder.toString === <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *     assert(f.buffer.isEmpty)
 *     <span class="stReserved">val</span> firstChar = f.builder(<span class="stLiteral">0</span>)
 *     forAll { (c: <span class="stType">Char</span>) =&gt;
 *       whenever (c != <span class="stQuotedString">'S'</span>) {
 *         c should not equal firstChar
 *       }
 *     }
 *     f.buffer += <span class="stQuotedString">"sweet"</span>
 *   }
 * <br/>  property(<span class="stQuotedString">"testing should be fun"</span>) { f =&gt;
 *     f.builder.append(<span class="stQuotedString">"fun!"</span>)
 *     assert(f.builder.toString === <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *     assert(f.buffer.isEmpty)
 *     <span class="stReserved">val</span> firstChar = f.builder(<span class="stLiteral">0</span>)
 *     forAll { (c: <span class="stType">Char</span>) =&gt;
 *       whenever (c != <span class="stQuotedString">'S'</span>) {
 *         c should not equal firstChar
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * @author Bill Venners
 */

abstract class FixtureAnyPropSpec extends org.scalatest.propspec.FixtureAnyPropSpecLike {

  /**
   * Returns a user friendly string for this suite, composed of the
   * simple name of the class (possibly simplified further by removing dollar signs if added by the Scala interpeter) and, if this suite
   * contains nested suites, the result of invoking <code>toString</code> on each
   * of the nested suites, separated by commas and surrounded by parentheses.
   *
   * @return a user-friendly string for this suite
   */
  override def toString: String = Suite.suiteToString(None, this)
}
