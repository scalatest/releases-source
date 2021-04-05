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
package org.scalatest.funspec

import org.scalatest._

/**
 * A sister class to <code>org.scalatest.funspec.AnyFunSpec</code> that can pass a fixture object into its tests.
 *
 * <table><tr><td class="usage">
 * <strong>Recommended Usage</strong>:
 * Use class <code>FixtureAnyFunSpec</code> in situations for which <a href="AnyFunSpec.html"><code>AnyFunSpec</code></a>
 * would be a good choice, when all or most tests need the same fixture objects
 * that must be cleaned up afterwards. <em>Note: <code>FixtureAnyFunSpec</code> is intended for use in special situations, with class <code>AnyFunSpec</code> used for general needs. For
 * more insight into where <code>FixtureAnyFunSpec</code> fits in the big picture, see the <a href="AnyFunSpec.html#withFixtureOneArgTest"><code>withFixture(OneArgTest)</code></a> subsection of the <a href="AnyFunSpec.html#sharedFixtures">Shared fixtures</a> section in the documentation for class <code>AnyFunSpec</code>.</em>
 * </td></tr></table>
 * 
 * <p>
 * Class <code>FixtureAnyFunSpec</code> behaves similarly to class <code>org.scalatest.funspec.AnyFunSpec</code>, except that tests may have a
 * fixture parameter. The type of the
 * fixture parameter is defined by the abstract <code>FixtureParam</code> type, which is a member of this class.
 * This class also contains an abstract <code>withFixture</code> method. This <code>withFixture</code> method
 * takes a <code>OneArgTest</code>, which is a nested trait defined as a member of this class.
 * <code>OneArgTest</code> has an <code>apply</code> method that takes a <code>FixtureParam</code>.
 * This <code>apply</code> method is responsible for running a test.
 * This class's <code>runTest</code> method delegates the actual running of each test to <code>withFixture(OneArgTest)</code>, passing
 * in the test code to run via the <code>OneArgTest</code> argument. The <code>withFixture(OneArgTest)</code> method (abstract in this class) is responsible
 * for creating the fixture argument and passing it to the test function.
 * </p>
 * 
 * <p>
 * Subclasses of this class must, therefore, do three things differently from a plain old <code>org.scalatest.funspec.AnyFunSpec</code>:
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
 * If the fixture you want to pass into your tests consists of multiple objects, you will need to combine
 * them into one object to use this class. One good approach to passing multiple fixture objects is
 * to encapsulate them in a case class. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">FixtureParam</span>(file: <span class="stType">File</span>, writer: <span class="stType">FileWriter</span>)
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
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.oneargtest
 * <br/><span class="stReserved">import</span> org.scalatest.funspec
 * <span class="stReserved">import</span> java.io._
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">FixtureAnyFunSpec</span> {
 * <br/>  <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">FixtureParam</span>(file: <span class="stType">File</span>, writer: <span class="stType">FileWriter</span>)
 * <br/>  <span class="stReserved">def</span> withFixture(test: <span class="stType">OneArgTest</span>) = {
 * <br/>    <span class="stLineComment">// create the fixture</span>
 *     <span class="stReserved">val</span> file = File.createTempFile(<span class="stQuotedString">"hello"</span>, <span class="stQuotedString">"world"</span>)
 *     <span class="stReserved">val</span> writer = <span class="stReserved">new</span> <span class="stType">FileWriter</span>(file)
 *     <span class="stReserved">val</span> theFixture = <span class="stType">FixtureParam</span>(file, writer)
 * <br/>    <span class="stReserved">try</span> {
 *       writer.write(<span class="stQuotedString">"ScalaTest is "</span>) <span class="stLineComment">// set up the fixture</span>
 *       withFixture(test.toNoArgTest(theFixture)) <span class="stLineComment">// "loan" the fixture to the test</span>
 *     }
 *     <span class="stReserved">finally</span> writer.close() <span class="stLineComment">// clean up the fixture</span>
 *   }
 * <br/>  describe(<span class="stQuotedString">"Testing"</span>) {
 *     it(<span class="stQuotedString">"should be easy"</span>) { f =&gt;
 *       f.writer.write(<span class="stQuotedString">"easy!"</span>)
 *       f.writer.flush()
 *       assert(f.file.length === <span class="stLiteral">18</span>)
 *     }
 * <br/>    it(<span class="stQuotedString">"should be fun"</span>) { f =&gt;
 *       f.writer.write(<span class="stQuotedString">"fun!"</span>)
 *       f.writer.flush()
 *       assert(f.file.length === <span class="stLiteral">17</span>)
 *     }
 *   } 
 * }
 * </pre>
 *
 * <p>
 * If a test fails, the <code>OneArgTest</code> function will result in a [[org.scalatest.Failed Failed]] wrapping the exception describing the failure.
 * To ensure clean up happens even if a test fails, you should invoke the test function from inside a <code>try</code> block and do the cleanup in a
 * <code>finally</code> clause, as shown in the previous example.
 * </p>
 *
 * <a name="sharingFixturesAcrossClasses"></a><h2>Sharing fixtures across classes</h2>
 *
 * <p>
 * If multiple test classes need the same fixture, you can define the <code>FixtureParam</code> and <code>withFixture(OneArgTest)</code> implementations
 * in a trait, then mix that trait into the test classes that need it. For example, if your application requires a database and your integration tests
 * use that database, you will likely have many test classes that need a database fixture. You can create a "database fixture" trait that creates a
 * database with a unique name, passes the connector into the test, then removes the database once the test completes. This is shown in the following example:
 * </p>
 * 
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.fixture.funspec.sharing
 * <br/><span class="stReserved">import</span> java.util.concurrent.ConcurrentHashMap
 * <span class="stReserved">import</span> org.scalatest.funspec
 * <span class="stReserved">import</span> DbServer._
 * <span class="stReserved">import</span> java.util.UUID.randomUUID
 * <br/><span class="stReserved">object</span> <span class="stType">DbServer</span> { <span class="stLineComment">// Simulating a database server</span>
 *   <span class="stReserved">type</span> <span class="stType">Db</span> = <span class="stType">StringBuffer</span>
 *   <span class="stReserved">private</span> <span class="stReserved">val</span> databases = <span class="stReserved">new</span> <span class="stType">ConcurrentHashMap[String, Db]</span>
 *   <span class="stReserved">def</span> createDb(name: <span class="stType">String</span>): <span class="stType">Db</span> = {
 *     <span class="stReserved">val</span> db = <span class="stReserved">new</span> <span class="stType">StringBuffer</span>
 *     databases.put(name, db)
 *     db
 *   }
 *   <span class="stReserved">def</span> removeDb(name: <span class="stType">String</span>) {
 *     databases.remove(name)
 *   }
 * }
 * <br/><span class="stReserved">trait</span> <span class="stType">DbFixture</span> { <span class="stReserved">this</span>: <span class="stType">FixtureSuite</span> =&gt;
 * <br/>  <span class="stReserved">type</span> <span class="stType">FixtureParam</span> = <span class="stType">Db</span>
 * <br/>  <span class="stLineComment">// Allow clients to populate the database after</span>
 *   <span class="stLineComment">// it is created</span>
 *   <span class="stReserved">def</span> populateDb(db: <span class="stType">Db</span>) {}
 * <br/>  <span class="stReserved">def</span> withFixture(test: <span class="stType">OneArgTest</span>) = {
 *     <span class="stReserved">val</span> dbName = randomUUID.toString
 *     <span class="stReserved">val</span> db = createDb(dbName) <span class="stLineComment">// create the fixture</span>
 *     <span class="stReserved">try</span> {
 *       populateDb(db) <span class="stLineComment">// setup the fixture</span>
 *       withFixture(test.toNoArgTest(db)) <span class="stLineComment">// "loan" the fixture to the test</span>
 *     }
 *     <span class="stReserved">finally</span> removeDb(dbName) <span class="stLineComment">// clean up the fixture</span>
 *   }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">FixtureAnyFunSpec</span> <span class="stReserved">with</span> <span class="stType">DbFixture</span> {
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> populateDb(db: <span class="stType">Db</span>) { <span class="stLineComment">// setup the fixture</span>
 *     db.append(<span class="stQuotedString">"ScalaTest is "</span>)
 *   }
 * <br/>  describe(<span class="stQuotedString">"Testing"</span>) {
 *     it(<span class="stQuotedString">"should be easy"</span>) { db =&gt;
 *       db.append(<span class="stQuotedString">"easy!"</span>)
 *       assert(db.toString === <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *     }
 * <br/>    it(<span class="stQuotedString">"should be fun"</span>) { db =&gt;
 *       db.append(<span class="stQuotedString">"fun!"</span>)
 *       assert(db.toString === <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *     }
 *   }
 * <br/>  <span class="stLineComment">// This test doesn't need a Db</span>
 *   describe(<span class="stQuotedString">"Test code"</span>) {
 *     it(<span class="stQuotedString">"should be clear"</span>) { () =&gt;
 *       <span class="stReserved">val</span> buf = <span class="stReserved">new</span> <span class="stType">StringBuffer</span>
 *       buf.append(<span class="stQuotedString">"ScalaTest code is "</span>)
 *       buf.append(<span class="stQuotedString">"clear!"</span>)
 *       assert(buf.toString === <span class="stQuotedString">"ScalaTest code is clear!"</span>)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * Often when you create fixtures in a trait like <code>DbFixture</code>, you'll still need to enable individual test classes
 * to "setup" a newly created fixture before it gets passed into the tests. A good way to accomplish this is to pass the newly
 * created fixture into a setup method, like <code>populateDb</code> in the previous example, before passing it to the test
 * function. Classes that need to perform such setup can override the method, as does <code>ExampleSpec</code>.
 * </p>
 *
 * <p>
 * If a test doesn't need the fixture, you can indicate that by providing a no-arg instead of a one-arg function, as is done in the
 * third test in the previous example, &ldquo;<code>Test code should be clear</code>&rdquo;. In other words, instead of starting your function literal
 * with something like &ldquo;<code>db =&gt;</code>&rdquo;, you'd start it with &ldquo;<code>() =&gt;</code>&rdquo;. For such tests, <code>runTest</code>
 * will not invoke <code>withFixture(OneArgTest)</code>. It will instead directly invoke <code>withFixture(NoArgTest)</code>.
 * </p>
 *
 * <p>
 * Both examples shown above demonstrate the technique of giving each test its own "fixture sandbox" to play in. When your fixtures
 * involve external side-effects, like creating files or databases, it is a good idea to give each file or database a unique name as is
 * done in these examples. This keeps tests completely isolated, allowing you to run them in parallel if desired. You could mix
 * <a href="../ParallelTestExecution.html"><code>ParallelTestExecution</code></a> into either of these <code>ExampleSpec</code> classes, and the tests would run in parallel just fine.
 * </p>
 *
 * @author Bill Venners
 */

abstract class FixtureAnyFunSpec extends FixtureAnyFunSpecLike {

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
