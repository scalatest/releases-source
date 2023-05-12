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
package org.scalatest.wordspec

/**
 * A sister class to <code>org.scalatest.wordspec.AsyncWordSpec</code> that can pass a fixture object into its tests.
 *
 * <table><tr><td class="usage">
 * <strong>Recommended Usage</strong>:
 * Use class <code>FixtureAsyncWordSpec</code> in situations for which <a href="AsyncWordSpec.html"><code>AsyncWordSpec</code></a>
 * would be a good choice, when all or most tests need the same fixture objects
 * that must be cleaned up afterwards. <em>Note: <code>FixtureAsyncWordSpec</code> is intended for use in special situations, with class <code>AsyncWordSpec</code> used for general needs. For
 * more insight into where <code>FixtureAsyncWordSpec</code> fits in the big picture, see the <a href="AsyncWordSpec.html#withFixtureOneArgAsyncTest"><code>withFixture(OneArgAsyncTest)</code></a> subsection of the <a href="AsyncWordSpec.html#sharedFixtures">Shared fixtures</a> section in the documentation for class <code>AsyncWordSpec</code>.</em>
 * </td></tr></table>
 *
 * <p>
 * Class <code>FixtureAsyncWordSpec</code> behaves similarly to class <code>org.scalatest.wordspec.AsyncWordSpec</code>, except that tests may have a
 * fixture parameter. The type of the
 * fixture parameter is defined by the abstract <code>FixtureParam</code> type, which is a member of this class.
 * This class also contains an abstract <code>withFixture</code> method. This <code>withFixture</code> method
 * takes a <code>OneArgAsyncTest</code>, which is a nested trait defined as a member of this class.
 * <code>OneArgAsyncTest</code> has an <code>apply</code> method that takes a <code>FixtureParam</code>.
 * This <code>apply</code> method is responsible for running a test.
 * This class's <code>runTest</code> method delegates the actual running of each test to <code>withFixture(OneArgAsyncTest)</code>, passing
 * in the test code to run via the <code>OneArgAsyncTest</code> argument. The <code>withFixture(OneArgAsyncTest)</code> method (abstract in this class) is responsible
 * for creating the fixture argument and passing it to the test function.
 * </p>
 *
 * <p>
 * Subclasses of this class must, therefore, do three things differently from a plain old <code>org.scalatest.wordspec.AsyncWordSpec</code>:
 * </p>
 *
 * <ol>
 * <li>define the type of the fixture parameter by specifying type <code>FixtureParam</code></li>
 * <li>define the <code>withFixture(OneArgAsyncTest)</code> method</li>
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
 * To enable the stacking of traits that define <code>withFixture(NoArgAsyncTest)</code>, it is a good idea to let
 * <code>withFixture(NoArgAsyncTest)</code> invoke the test function instead of invoking the test
 * function directly. To do so, you'll need to convert the <code>OneArgAsyncTest</code> to a <code>NoArgAsyncTest</code>. You can do that by passing
 * the fixture object to the <code>toNoArgAsyncTest</code> method of <code>OneArgAsyncTest</code>. In other words, instead of
 * writing &ldquo;<code>test(theFixture)</code>&rdquo;, you'd delegate responsibility for
 * invoking the test function to the <code>withFixture(NoArgAsyncTest)</code> method of the same instance by writing:
 * </p>
 *
 * <pre>
 * withFixture(test.toNoArgAsyncTest(theFixture))
 * </pre>
 *
 * <p>
 * Here's a complete example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncwordspec.oneargasynctest
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <span class="stReserved">import</span> scala.concurrent.ExecutionContext
 * <br/><span class="stLineComment">// Defining actor messages</span>
 * <span class="stReserved">sealed</span> <span class="stReserved">abstract</span> <span class="stReserved">class</span> <span class="stType">StringOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">object</span> <span class="stType">Clear</span> <span class="stReserved">extends</span> <span class="stType">StringOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">Append</span>(value: <span class="stType">String</span>) <span class="stReserved">extends</span> <span class="stType">StringOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">object</span> <span class="stType">GetValue</span>
 * <br/><span class="stReserved">class</span> <span class="stType">StringActor</span> { <span class="stLineComment">// Simulating an actor</span>
 *   <span class="stReserved">private</span> <span class="stReserved">final</span> <span class="stReserved">val</span> sb = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>
 *   <span class="stReserved">def</span> !(op: <span class="stType">StringOp</span>): <span class="stType">Unit</span> =
 *     synchronized {
 *       op <span class="stReserved">match</span> {
 *         <span class="stReserved">case</span> <span class="stType">Append</span>(value) => sb.append(value)
 *         <span class="stReserved">case</span> <span class="stType">Clear</span> => sb.clear()
 *       }
 *     }
 *   <span class="stReserved">def</span> ?(get: GetValue.type)(<span class="stReserved">implicit</span> c: <span class="stType">ExecutionContext</span>): <span class="stType">Future[String]</span> =
 *     <span class="stType">Future</span> {
 *       synchronized { sb.toString }
 *     }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">wordspec.FixtureAsyncWordSpec</span> {
 * <br/>  <span class="stReserved">type</span> <span class="stType">FixtureParam</span> = <span class="stType">StringActor</span>
 * <br/>  <span class="stReserved">def</span> withFixture(test: <span class="stType">OneArgAsyncTest</span>): <span class="stType">FutureOutcome</span> = {
 * <br/>    <span class="stReserved">val</span> actor = <span class="stReserved">new</span> <span class="stType">StringActor</span>
 *     complete {
 *       actor ! <span class="stType">Append</span>(<span class="stQuotedString">"ScalaTest is "</span>) <span class="stLineComment">// set up the fixture</span>
 *       withFixture(test.toNoArgAsyncTest(actor))
 *     } lastly {
 *       actor ! <span class="stType">Clear</span> <span class="stLineComment">// ensure the fixture will be cleaned up</span>
 *     }
 *   }
 * <br/>  <span class="stQuotedString">"Testing"</span> should {
 *     <span class="stQuotedString">"be easy"</span> in { actor =&gt;
 *       actor ! <span class="stType">Append</span>(<span class="stQuotedString">"easy!"</span>)
 *       <span class="stReserved">val</span> futureString = actor ? <span class="stType">GetValue</span>
 *       futureString map { s =&gt;
 *         assert(s == <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *       }
 *     }
 * <br/>    <span class="stQuotedString">"be fun"</span> in { actor =>
 *       actor ! <span class="stType">Append</span>(<span class="stQuotedString">"fun!"</span>)
 *       <span class="stReserved">val</span> futureString = actor ? <span class="stType">GetValue</span>
 *       futureString map { s =>
 *         assert(s == <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * If a test fails, the future returned by the <code>OneArgAsyncTest</code> function will result in
 * an [[org.scalatest.Failed org.scalatest.Failed]] wrapping the exception describing
 * the failure. To ensure clean up happens even if a test fails, you should invoke the test function and do the cleanup using
 * <code>complete</code>-<code>lastly</code>, as shown in the previous example. The <code>complete</code>-<code>lastly</code> syntax, defined in <code>CompleteLastly</code>, which is extended by <code>AsyncTestSuite</code>, ensures
 * the second, cleanup block of code is executed, whether the the first block throws an exception or returns a future. If it returns a
 * future, the cleanup will be executed when the future completes.
 * </p>
 *
 * <a name="sharingFixturesAcrossClasses"></a><h2>Sharing fixtures across classes</h2>
 *
 * <p>
 * If multiple test classes need the same fixture, you can define the <code>FixtureParam</code> and <code>withFixture(OneArgAsyncTest)</code>
 * implementations in a trait, then mix that trait into the test classes that need it. For example, if your application requires a database and your
 * integration tests use that database, you will likely have many test classes that need a database fixture. You can create a "database fixture" trait
 * that creates a database with a unique name, passes the connector into the test, then removes the database once the test completes. This is shown in
 * the following example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.fixture.asyncwordspec.sharing
 * <br/><span class="stReserved">import</span> java.util.concurrent.ConcurrentHashMap
 * <span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> DbServer._
 * <span class="stReserved">import</span> java.util.UUID.randomUUID
 * <span class="stReserved">import</span> scala.concurrent.Future
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
 * <br/><span class="stReserved">trait</span> <span class="stType">DbFixture</span> { <span class="stReserved">this</span>: <span class="stType">FixtureAsyncTestSuite</span> =&gt;
 * <br/>  <span class="stReserved">type</span> <span class="stType">FixtureParam</span> = <span class="stType">Db</span>
 * <br/>  <span class="stLineComment">// Allow clients to populate the database after</span>
 *   <span class="stLineComment">// it is created</span>
 *   <span class="stReserved">def</span> populateDb(db: <span class="stType">Db</span>) {}
 * <br/>  <span class="stReserved">def</span> withFixture(test: <span class="stType">OneArgAsyncTest</span>): <span class="stType">FutureOutcome</span> = {
 *     <span class="stReserved">val</span> dbName = randomUUID.toString
 *     <span class="stReserved">val</span> db = createDb(dbName) <span class="stLineComment">// create the fixture</span>
 *     complete {
 *       populateDb(db) <span class="stLineComment">// setup the fixture</span>
 *       withFixture(test.toNoArgAsyncTest(db)) <span class="stLineComment">// "loan" the fixture to the test</span>
 *     } lastly {
 *       removeDb(dbName) <span class="stLineComment">// ensure the fixture will be cleaned up</span>
 *     }
 *   }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">wordspec.FixtureAsyncWordSpec</span> <span class="stReserved">with</span> <span class="stType">DbFixture</span> {
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> populateDb(db: <span class="stType">Db</span>) { <span class="stLineComment">// setup the fixture</span>
 *     db.append(<span class="stQuotedString">"ScalaTest is "</span>)
 *   }
 * <br/>  <span class="stQuotedString">"Testing"</span> should {
 *     <span class="stQuotedString">"be easy"</span> in { db =&gt;
 *       <span class="stType">Future</span> {
 *         db.append(<span class="stQuotedString">"easy!"</span>)
 *         assert(db.toString === <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *       }
 *     }
 * <br/>    <span class="stQuotedString">"be fun"</span> in { db =&gt;
 *       <span class="stType">Future</span> {
 *         db.append(<span class="stQuotedString">"fun!"</span>)
 *         assert(db.toString === <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *       }
 *     }
 *   }
 * <br/>  <span class="stQuotedString">"Testing code"</span> should {
 *     <span class="stLineComment">// This test doesn't need a Db</span>
 *     <span class="stQuotedString">"be clear"</span> in { () =&gt;
 *       <span class="stType">Future</span> {
 *         <span class="stReserved">val</span> buf = <span class="stReserved">new</span> <span class="stType">StringBuffer</span>
 *         buf.append(<span class="stQuotedString">"ScalaTest code is "</span>)
 *         buf.append(<span class="stQuotedString">"clear!"</span>)
 *         assert(buf.toString === <span class="stQuotedString">"ScalaTest code is clear!"</span>)
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * Often when you create fixtures in a trait like <code>DbFixture</code>, you'll still need to enable individual test classes
 * to "setup" a newly created fixture before it gets passed into the tests. A good way to accomplish this is to pass the newly
 * created fixture into a setup method, like <code>populateDb</code> in the previous example, before passing it to the test
 * function. Classes that need to perform such setup can override the method, as does <code>ExampleSuite</code>.
 * </p>
 *
 * <p>
 * If a test doesn't need the fixture, you can indicate that by providing a no-arg instead of a one-arg function, as is done in the
 * third test in the previous example, &ldquo;<code>test code should be clear</code>&rdquo;. In other words, instead of starting your function literal
 * with something like &ldquo;<code>db =&gt;</code>&rdquo;, you'd start it with &ldquo;<code>() =&gt;</code>&rdquo;. For such tests, <code>runTest</code>
 * will not invoke <code>withFixture(OneArgAsyncTest)</code>. It will instead directly invoke <code>withFixture(NoArgAsyncTest)</code>.
 * </p>
 *
 *
 * <p>
 * Both examples shown above demonstrate the technique of giving each test its own "fixture sandbox" to play in. When your fixtures
 * involve external side-effects, like creating files or databases, it is a good idea to give each file or database a unique name as is
 * done in these examples. This keeps tests completely isolated, allowing you to run them in parallel if desired. You could mix
 * <a href="../ParallelTestExecution.html"><code>ParallelTestExecution</code></a> into either of these <code>ExampleSuite</code> classes, and the tests would run in parallel just fine.
 * </p>
 *
 * @author Bill Venners
 */
abstract class FixtureAsyncWordSpec extends org.scalatest.wordspec.FixtureAsyncWordSpecLike {

  /**
   * Returns a user friendly string for this suite, composed of the
   * simple name of the class (possibly simplified further by removing dollar signs if added by the Scala interpeter) and, if this suite
   * contains nested suites, the result of invoking <code>toString</code> on each
   * of the nested suites, separated by commas and surrounded by parentheses.
   *
   * @return a user-friendly string for this suite
   */
  override def toString: String = org.scalatest.Suite.suiteToString(None, this)
}
