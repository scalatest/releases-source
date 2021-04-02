/*
 * Copyright 2001-2014 Artima, Inc.
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
package org.scalatest.flatspec

import org.scalatest.Suite

/**
 * Enables testing of asynchronous code without blocking,
 * using a style consistent with traditional <code>AsyncFlatSpec</code> tests.
 *
 * <table><tr><td class="usage">
 * <strong>Recommended Usage</strong>:
 * <code>AsyncFlatSpec</code> is intended to enable users of <a href="AnyFlatSpec.html"><code>AnyFlatSpec</code></a>
 * to write non-blocking asynchronous tests that are consistent with their traditional <code>AnyFlatSpec</code> tests. 
 * <em>Note: <code>AsyncFlatSpec</code> is intended for use in special situations where non-blocking asynchronous
 * testing is needed, with class <code>AnyFlatSpec</code> used for general needs.</em>
 * </td></tr></table>
 * 
 * <p>
 * Given a <code>Future</code> returned by the code you are testing,
 * you need not block until the <code>Future</code> completes before
 * performing assertions against its value. You can instead map those
 * assertions onto the <code>Future</code> and return the resulting
 * <code>Future[Assertion]</code> to ScalaTest. The test will complete
 * asynchronously, when the <code>Future[Assertion]</code> completes.
 * </p>
 *
 * <p>
 * Trait <code>AsyncFlatSpec</code> is so named because
 * your specification text and tests line up flat against the left-side indentation level, with no nesting needed.
 * Here's an example <code>AsyncFlatSpec</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec
 * <br/><span class="stReserved">import</span> org.scalatest.flatspec.AsyncFlatSpec
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <br/><span class="stReserved">class</span> <span class="stType">AddSpec</span> <span class="stReserved">extends</span> <span class="stType">AsyncFlatSpec</span> {
 * <br/>  <span class="stReserved">def</span> addSoon(addends: <span class="stType">Int</span>*): <span class="stType">Future[Int]</span> = <span class="stType">Future</span> { addends.sum }
 * <br/>  behavior of <span class="stQuotedString">"addSoon"</span>
 * <br/>  it should <span class="stQuotedString">"eventually compute a sum of passed Ints"</span> in {
 *     <span class="stReserved">val</span> futureSum: <span class="stType">Future[Int]</span> = addSoon(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *     <span class="stLineComment">// You can map assertions onto a Future, then return</span>
 *     <span class="stLineComment">// the resulting Future[Assertion] to ScalaTest:</span>
 *     futureSum map { sum =&gt; assert(sum == <span class="stLiteral">3</span>) }
 *   }
 * <br/>  <span class="stReserved">def</span> addNow(addends: <span class="stType">Int</span>*): <span class="stType">Int</span> = addends.sum
 * <br/>  <span class="stQuotedString">"addNow"</span> should <span class="stQuotedString">"immediately compute a sum of passed Ints"</span> in {
 *     <span class="stReserved">val</span> sum: <span class="stType">Int</span> = addNow(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *     <span class="stLineComment">// You can also write synchronous tests. The body</span>
 *     <span class="stLineComment">// must have result type Assertion:</span>
 *     assert(sum == <span class="stLiteral">3</span>)
 *   }
 * }
 * </pre>
 *
 * <p>
 * The initial test in this example demonstrates the use of an explicit <code>behavior of</code> clause, which establishes
 * <code>addSoon</code> as the subject.  The second test demonstrates the alternate syntax of replacing the first <code>it</code>
 * with the subject string, in this case, <code>"addNow"</code>.
 * As with traditional <code>AnyFlatSpec</code>s, you can use <code>must</code> or <code>can</code> as well as <code>should</code>.
 * For example, instead of <code>it should "eventually</code>..., you could write
 * <code>it must "eventually</code>... or <code>it can "eventually</code>....
 * You can also write <code>they</code> instead of <code>it</code>. See the documentation for <a href="AsyncFlatSpec.html"><code>AsyncFlatSpec</code></a> for
 * more detail.
 * </p>
 *
 * <p>
 * Running the above <code>AddSpec</code> in the Scala interpreter would yield:
 * </p>
 * 
 * <pre class="stREPL">
 * <span class="stGreen">addSoon
 * - should eventually compute a sum of passed Ints
 * - should immediately compute a sum of passed Ints</span>
 * </pre>
 *
 * <p>
 * Starting with version 3.0.0, ScalaTest assertions and matchers have result type <code>Assertion</code>.
 * The result type of the first test in the example above, therefore, is <code>Future[Assertion]</code>.
 * For clarity, here's the relevant code in a REPL session:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import org.scalatest._
 * import org.scalatest._
 *
 * scala&gt; import Assertions._
 * import Assertions._
 *
 * scala&gt; import scala.concurrent.Future
 * import scala.concurrent.Future
 *
 * scala&gt; import scala.concurrent.ExecutionContext
 * import scala.concurrent.ExecutionContext
 *
 * scala&gt; implicit val executionContext = ExecutionContext.Implicits.global
 * executionContext: scala.concurrent.ExecutionContextExecutor = scala.concurrent.impl.ExecutionContextImpl@26141c5b
 *
 * scala&gt; def addSoon(addends: Int*): Future[Int] = Future { addends.sum }
 * addSoon: (addends: Int*)scala.concurrent.Future[Int]
 *
 * scala&gt; val futureSum: Future[Int] = addSoon(1, 2)
 * futureSum: scala.concurrent.Future[Int] = scala.concurrent.impl.Promise$DefaultPromise@721f47b2
 *
 * scala&gt; futureSum map { sum =&gt; assert(sum == 3) }
 * res0: scala.concurrent.Future[org.scalatest.Assertion] = scala.concurrent.impl.Promise$DefaultPromise@3955cfcb
 * </pre>
 *
 * <p>
 * The second test has result type <code>Assertion</code>:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def addNow(addends: Int*): Int = addends.sum
 * addNow: (addends: Int*)Int
 *
 * scala&gt; val sum: Int = addNow(1, 2)
 * sum: Int = 3
 *
 * scala&gt; assert(sum == 3)
 * res1: org.scalatest.Assertion = Succeeded
 * </pre>
 *
 * <p>
 * When <code>AddSpec</code> is constructed, the second test will be implicitly converted to
 * <code>Future[Assertion]</code> and registered. The implicit conversion is from <code>Assertion</code>
 * to <code>Future[Assertion]</code>, so you must end synchronous tests in some ScalaTest assertion
 * or matcher expression. If a test would not otherwise end in type <code>Assertion</code>, you can
 * place <code>succeed</code> at the end of the test. <code>succeed</code>, a field in trait <code>Assertions</code>,
 * returns the <code>Succeeded</code> singleton:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; succeed
 * res2: org.scalatest.Assertion = Succeeded
 * </pre>
 *
 * <p>
 * Thus placing <code>succeed</code> at the end of a test body will satisfy the type checker:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stQuotedString">"addNow"</span> should <span class="stQuotedString">"immediately compute a sum of passed Ints"</span> in {
 *   <span class="stReserved">val</span> sum: <span class="stType">Int</span> = addNow(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *   assert(sum == <span class="stLiteral">3</span>)
 *   println(<span class="stQuotedString">"hi"</span>) <span class="stLineComment">// println has result type Unit</span>
 *   succeed       <span class="stLineComment">// succeed has result type Assertion</span>
 * }
 * </pre>
 *
 * <p>
 * An <code>AsyncFlatSpec</code>'s lifecycle has two phases: the <em>registration</em> phase and the
 * <em>ready</em> phase. It starts in registration phase and enters ready phase the first time
 * <code>run</code> is called on it. It then remains in ready phase for the remainder of its lifetime.
 * </p>
 *
 * <p>
 * Tests can only be registered with the <code>it</code> method while the <code>AsyncFlatSpec</code> is
 * in its registration phase. Any attempt to register a test after the <code>AsyncFlatSpec</code> has
 * entered its ready phase, <em>i.e.</em>, after <code>run</code> has been invoked on the <code>AsyncFlatSpec</code>,
 * will be met with a thrown <code>TestRegistrationClosedException</code>. The recommended style
 * of using <code>AsyncFlatSpec</code> is to register tests during object construction as is done in all
 * the examples shown here. If you keep to the recommended style, you should never see a
 * <code>TestRegistrationClosedException</code>.
 * </p>
 *
 * <a name="asyncExecutionModel"></a><h2>Asynchronous execution model</h2>
 *
 * <p>
 * <code>AsyncFlatSpec</code> extends <a href="AsyncTestSuite.html"><code>AsyncTestSuite</code></a>, which provides an
 * implicit <code>scala.concurrent.ExecutionContext</code>
 * named <code>executionContext</code>. This
 * execution context is used by <code>AsyncFlatSpec</code> to 
 * transform the <code>Future[Assertion]</code>s returned by each test
 * into the <a href="FutureOutcome.html"><code>FutureOutcome</code></a> returned by the <code>test</code> function
 * passed to <code>withFixture</code>.
 * This <code>ExecutionContext</code> is also intended to be used in the tests,
 * including when you map assertions onto futures.
 * </p>
 * 
 * <p>
 * On both the JVM and Scala.js, the default execution context provided by ScalaTest's asynchronous
 * testing styles confines execution to a single thread per test. On JavaScript, where single-threaded
 * execution is the only possibility, the default execution context is
 * <code>scala.scalajs.concurrent.JSExecutionContext.Implicits.queue</code>. On the JVM, 
 * the default execution context is a <em>serial execution context</em> provided by ScalaTest itself.
 * </p>
 * 
 * <p>
 * When ScalaTest's serial execution context is called upon to execute a task, that task is recorded
 * in a queue for later execution. For example, one task that will be placed in this queue is the
 * task that transforms the <code>Future[Assertion]</code> returned by an asynchronous test body
 * to the <code>FutureOutcome</code> returned from the <code>test</code> function.
 * Other tasks that will be queued are any transformations of, or callbacks registered on, <code>Future</code>s that occur
 * in your test body, including any assertions you map onto <code>Future</code>s. Once the test body returns,
 * the thread that executed the test body will execute the tasks in that queue one after another, in the order they
 * were enqueued.
 * </p>
 *
 * <p>
 * ScalaTest provides its serial execution context as the default on the JVM for three reasons. First, most often
 * running both tests and suites in parallel does not give a significant performance boost compared to
 * just running suites in parallel. Thus parallel execution of <code>Future</code> transformations within
 * individual tests is not generally needed for performance reasons.
 * </p>
 *
 * <p>
 * Second, if multiple threads are operating in the same suite
 * concurrently, you'll need to make sure access to any mutable fixture objects by multiple threads is synchronized.
 * Although access to mutable state along
 * the same linear chain of <code>Future</code> transformations need not be synchronized,
 * this does not hold true for callbacks, and in general it is easy to make a mistake. Simply put: synchronizing access to
 * shared mutable state is difficult and error prone.
 * Because ScalaTest's default execution context on the JVM confines execution of <code>Future</code> transformations
 * and call backs to a single thread, you need not (by default) worry about synchronizing access to mutable state
 * in your asynchronous-style tests.
 * </p>
 *
 * <p>
 * Third, asynchronous-style tests need not be complete when the test body returns, because the test body returns
 * a <code>Future[Assertion]</code>. This <code>Future[Assertion]</code> will often represent a test that has not yet
 * completed. As a result, when using a more traditional execution context backed by a thread-pool, you could
 * potentially start many more tests executing concurrently than there are threads in the thread pool. The more
 * concurrently execute tests you have competing for threads from the same limited thread pool, the more likely it
 * will be that tests will intermitently fail due to timeouts.
 * </p>
 * 
 * <p>
 * Using ScalaTest's serial execution context on the JVM will ensure the same thread that produced the <code>Future[Assertion]</code>
 * returned from a test body is also used to execute any tasks given to the execution context while executing the test
 * body&#8212;<em>and that thread will not be allowed to do anything else until the test completes.</em>
 * If the serial execution context's task queue ever becomes empty while the <code>Future[Assertion]</code> returned by
 * that test's body has not yet completed, the thread will <em>block</em> until another task for that test is enqueued. Although
 * it may seem counter-intuitive, this blocking behavior means the total number of tests allowed to run concurrently will be limited
 * to the total number of threads executing suites. This fact means you can tune the thread pool such that maximum performance
 * is reached while avoiding (or at least, reducing the likelihood of) tests that fail due to timeouts because of thread competition.
 * </p>
 *
 * <p>
 * This thread confinement strategy does mean, however, that when you are using the default execution context on the JVM, you
 * must be sure to <em>never block</em> in the test body waiting for a task to be completed by the
 * execution context. If you block, your test will never complete. This kind of problem will be obvious, because the test will
 * consistently hang every time you run it. (If a test is hanging, and you're not sure which one it is, 
 * enable <a href="Runner.scala#slowpokeNotifications">slowpoke notifications</a>.) If you really do 
 * want to block in your tests, you may wish to just use a
 * traditional <a href="AnyFlatSpec.html"><code>AnyFlatSpec</code></a> with
 * <a href="concurrent/ScalaFutures.html"><code>ScalaFutures</code></a> instead. Alternatively, you could override
 * the <code>executionContext</code> and use a traditional <code>ExecutionContext</code> backed by a thread pool. This
 * will enable you to block in an asynchronous-style test on the JVM, but you'll need to worry about synchronizing access to
 * shared mutable state.
 * </p>
 *
 * <p>
 * To use a different execution context, just override <code>executionContext</code>. For example, if you prefer to use
 * the <code>runNow</code> execution context on Scala.js instead of the default <code>queue</code>, you would write:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stLineComment">// on Scala.js</span>
 * <span class="stReserved">implicit</span> <span class="stReserved">override</span> <span class="stReserved">def</span> executionContext =
 *     org.scalatest.concurrent.TestExecutionContext.runNow
 * </pre>
 *
 * <p>
 * If you prefer on the JVM to use the global execution context, which is backed by a thread pool, instead of ScalaTest's default
 * serial execution contex, which confines execution to a single thread, you would write:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stLineComment">// on the JVM (and also compiles on Scala.js, giving</span>
 * <span class="stLineComment">// you the queue execution context)</span>
 * <span class="stReserved">implicit</span> <span class="stReserved">override</span> <span class="stReserved">def</span> executionContext =
 *     scala.concurrent.ExecutionContext.Implicits.global
 * </pre>
 *
 * <a name="serialAndParallel"></a><h2>Serial and parallel test execution</h2>
 *
 * <p>
 * By default (unless you mix in <code>ParallelTestExecution</code>), tests in an <code>AsyncFlatSpec</code> will be executed one after
 * another, <em>i.e.</em>, serially. This is true whether those tests return <code>Assertion</code> or <code>Future[Assertion]</code>,
 * no matter what threads are involved. This default behavior allows
 * you to re-use a shared fixture, such as an external database that needs to be cleaned
 * after each test, in multiple tests in async-style suites. This is implemented by registering each test, other than the first test, to run
 * as a <em>continuation</em> after the previous test completes.
 * </p>
 *
 * <p>
 * If you want the tests of an <code>AsyncFlatSpec</code> to be executed in parallel, you
 * must mix in <code>ParallelTestExecution</code> and enable parallel execution of tests in your build.
 * You enable parallel execution in <a href="tools/Runner$.html"><code>Runner</code></a> with the <code>-P</code> command line flag. 
 * In the ScalaTest Maven Plugin, set <code>parallel</code> to <code>true</code>.
 * In <code>sbt</code>, parallel execution is the default, but to be explicit you can write:
 * 
 * <pre>
 * parallelExecution in Test := true // the default in sbt
 * </pre>
 * 
 * On the JVM, if both <a href="ParallelTestExecution.html"><code>ParallelTestExecution</code></a> is mixed in and 
 * parallel execution is enabled in the build, tests in an async-style suite will be started in parallel, using threads from
 * the <a href="Distributor"><code>Distributor</code></a>, and allowed to complete in parallel, using threads from the
 * <code>executionContext</code>. If you are using ScalaTest's serial execution context, the JVM default, asynchronous tests will
 * run in parallel very much like traditional (such as <a href="AnyFlatSpec.html"><code>AnyFlatSpec</code></a>) tests run in
 * parallel: 1) Because <code>ParallelTestExecution</code> extends
 * <code>OneInstancePerTest</code>, each test will run in its own instance of the test class, you need not worry about synchronizing
 * access to mutable instance state shared by different tests in the same suite.
 * 2) Because the serial execution context will confine the execution of each test to the single thread that executes the test body,
 * you need not worry about synchronizing access to shared mutable state accessed by transformations and callbacks of <code>Future</code>s
 * inside the test.
 * </p>
 * 
 * <p>
 * If <a href="ParallelTestExecution.html"><code>ParallelTestExecution</code></a> is mixed in but
 * parallel execution of suites is <em>not</em> enabled, asynchronous tests on the JVM will be started sequentially, by the single thread
 * that invoked <code>run</code>, but without waiting for one test to complete before the next test is started. As a result,
 * asynchronous tests will be allowed to <em>complete</em> in parallel, using threads
 * from the <code>executionContext</code>. If you are using the serial execution context, however, you'll see
 * the same behavior you see when parallel execution is disabled and a traditional suite that mixes in <code>ParallelTestExecution</code>
 * is executed: the tests will run sequentially. If you use an execution context backed by a thread-pool, such as <code>global</code>,
 * however, even though tests will be started sequentially by one thread, they will be allowed to run concurrently using threads from the
 * execution context's thread pool.
 * </p>
 * 
 * <p>
 * The latter behavior is essentially what you'll see on Scala.js when you execute a suite that mixes in <code>ParallelTestExecution</code>.
 * Because only one thread exists when running under JavaScript, you can't "enable parallel execution of suites." However, it may
 * still be useful to run tests in parallel on Scala.js, because tests can invoke API calls that are truly asynchronous by calling into 
 * external APIs that take advantage of non-JavaScript threads. Thus on Scala.js, <code>ParallelTestExecution</code> allows asynchronous
 * tests to run in parallel, even though they must be started sequentially. This may give you better performance when you are using API
 * calls in your Scala.js tests that are truly asynchronous.
 * </p>
 *
 * <a name="futuresAndExpectedExceptions"></a><h2>Futures and expected exceptions</h2>
 *
 * <p>
 * If you need to test for expected exceptions in the context of futures, you can use the
 * <code>recoverToSucceededIf</code> and <code>recoverToExceptionIf</code> methods of trait
 * <a href="RecoverMethods.html"><code>RecoverMethods</code></a>. Because this trait is mixed into
 * supertrait <code>AsyncTestSuite</code>, both of these methods are
 * available by default in an <code>AsyncFlatSpec</code>.
 * </p>
 *
 * <p>
 * If you just want to ensure that a future fails with a particular exception type, and do
 * not need to inspect the exception further, use <code>recoverToSucceededIf</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * recoverToSucceededIf[<span class="stType">IllegalStateException</span>] { <span class="stLineComment">// Result type: Future[Assertion]</span>
 *   emptyStackActor ? <span class="stType">Peek</span>
 * }
 * </pre>
 *
 * <p>
 * The <code>recoverToSucceededIf</code> method performs a job similar to
 * <a href="Assertions.html#assertThrowsMethod"><code>assertThrows</code></a>, except
 * in the context of a future. It transforms a <code>Future</code> of any type into a
 * <code>Future[Assertion]</code> that succeeds only if the original future fails with the specified
 * exception. Here's an example in the REPL:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import org.scalatest.RecoverMethods._
 * import org.scalatest.RecoverMethods._
 *
 * scala&gt; import scala.concurrent.Future
 * import scala.concurrent.Future
 *
 * scala&gt; import scala.concurrent.ExecutionContext.Implicits.global
 * import scala.concurrent.ExecutionContext.Implicits.global
 *
 * scala&gt; recoverToSucceededIf[IllegalStateException] {
 *      |   Future { throw new IllegalStateException }
 *      | }
 * res0: scala.concurrent.Future[org.scalatest.Assertion] = ...
 *
 * scala&gt; res0.value
 * res1: Option[scala.util.Try[org.scalatest.Assertion]] = Some(Success(Succeeded))
 * </pre>
 *
 * <p>
 * Otherwise it fails with an error message similar to those given by <code>assertThrows</code>:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; recoverToSucceededIf[IllegalStateException] {
 *      |   Future { throw new RuntimeException }
 *      | }
 * res2: scala.concurrent.Future[org.scalatest.Assertion] = ...
 *
 * scala&gt; res2.value
 * res3: Option[scala.util.Try[org.scalatest.Assertion]] =
 *     Some(Failure(org.scalatest.exceptions.TestFailedException: Expected exception
 *       java.lang.IllegalStateException to be thrown, but java.lang.RuntimeException
 *       was thrown))
 *
 * scala&gt; recoverToSucceededIf[IllegalStateException] {
 *      |   Future { 42 }
 *      | }
 * res4: scala.concurrent.Future[org.scalatest.Assertion] = ...
 *
 * scala&gt; res4.value
 * res5: Option[scala.util.Try[org.scalatest.Assertion]] =
 *     Some(Failure(org.scalatest.exceptions.TestFailedException: Expected exception
 *       java.lang.IllegalStateException to be thrown, but no exception was thrown))
 * </pre>
 *
 * <p>
 * The <code>recoverToExceptionIf</code> method differs from the <code>recoverToSucceededIf</code> in
 * its behavior when the assertion succeeds: <code>recoverToSucceededIf</code> yields a <code>Future[Assertion]</code>,
 * whereas <code>recoverToExceptionIf</code> yields a <code>Future[T]</code>, where <code>T</code> is the
 * expected exception type.
 * </p>
 *
 * <pre class="stHighlighted">
 * recoverToExceptionIf[<span class="stType">IllegalStateException</span>] { <span class="stLineComment">// Result type: Future[IllegalStateException]</span>
 *   emptyStackActor ? <span class="stType">Peek</span>
 * }
 * </pre>
 *
 * <p>
 * In other words, <code>recoverToExpectionIf</code> is to
 * <a href="Assertions.html#interceptMethod"><code>intercept</code></a> as
 * <code>recovertToSucceededIf</code> is to <a href="Assertions.html#assertThrowsMethod"><code>assertThrows</code></a>. The first one allows you to
 * perform further assertions on the expected exception. The second one gives you a result type that will satisfy the type checker
 * at the end of the test body. Here's an example showing <code>recoverToExceptionIf</code> in the REPL:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val futureEx =
 *      |   recoverToExceptionIf[IllegalStateException] {
 *      |     Future { throw new IllegalStateException("hello") }
 *      |   }
 * futureEx: scala.concurrent.Future[IllegalStateException] = ...
 *
 * scala&gt; futureEx.value
 * res6: Option[scala.util.Try[IllegalStateException]] =
 *     Some(Success(java.lang.IllegalStateException: hello))
 *
 * scala&gt; futureEx map { ex =&gt; assert(ex.getMessage == "world") }
 * res7: scala.concurrent.Future[org.scalatest.Assertion] = ...
 *
 * scala&gt; res7.value
 * res8: Option[scala.util.Try[org.scalatest.Assertion]] =
 *     Some(Failure(org.scalatest.exceptions.TestFailedException: "[hello]" did not equal "[world]"))
 * </pre>
 *
 * <a name="ignoredTests"></a><h2>Ignored tests</h2>
 *
 * <p>
 * To support the common use case of temporarily disabling a test, with the
 * good intention of resurrecting the test at a later time, <code>AsyncFlatSpec</code> provides two ways
 * to <em>ignore</em> a test, both demonstrated in the following example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.ignore
 * <br/><span class="stReserved">import</span> org.scalatest.flatspec.AsyncFlatSpec
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <br/><span class="stReserved">class</span> <span class="stType">AddSpec</span> <span class="stReserved">extends</span> <span class="stType">AsyncFlatSpec</span> {
 * <br/>  <span class="stReserved">def</span> addSoon(addends: <span class="stType">Int</span>*): <span class="stType">Future[Int]</span> = <span class="stType">Future</span> { addends.sum }
 * <br/>  behavior of <span class="stQuotedString">"addSoon"</span>
 * <br/>  ignore should <span class="stQuotedString">"eventually compute a sum of passed Ints"</span> in {
 *     <span class="stReserved">val</span> futureSum: <span class="stType">Future[Int]</span> = addSoon(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *     <span class="stLineComment">// You can map assertions onto a Future, then return</span>
 *     <span class="stLineComment">// the resulting Future[Assertion] to ScalaTest:</span>
 *     futureSum map { sum =&gt; assert(sum == <span class="stLiteral">3</span>) }
 *   }
 * <br/>  <span class="stReserved">def</span> addNow(addends: <span class="stType">Int</span>*): <span class="stType">Int</span> = addends.sum
 * <br/>  <span class="stQuotedString">"addNow"</span> should <span class="stQuotedString">"immediately compute a sum of passed Ints"</span> ignore {
 *     <span class="stReserved">val</span> sum: <span class="stType">Int</span> = addNow(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *     <span class="stLineComment">// You can also write synchronous tests. The body</span>
 *     <span class="stLineComment">// must have result type Assertion:</span>
 *     assert(sum == <span class="stLiteral">3</span>)
 *   }
 * }
 * </pre>
 *
 * <p>
 * In the first test, <code>ignore</code> is used instead of <code>it</code>.
 * In the second test, which uses the shorthand notation, no <code>it</code> exists to change into <code>ignore</code>.
 * To ignore such tests, you must instead change <code>in</code> to <code>ignore</code>, as shown in the above example.
 * If you run this version of <code>AddSpec</code> with:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new AddSpec)
 * </pre>
 *
 * <p>
 * It will report both tests as ignored:
 * </p>
 *
 * <pre class="stREPL">
 * <span class="stGreen">AddSpec:</span>
 * <span class="stGreen">addSoon</span>
 * <span class="stYellow">- should eventually compute a sum of passed Ints !!! IGNORED !!!</span>
 * <span class="stGreen">addNow</span>
 * <span class="stYellow">- should immediately compute a sum of passed Ints !!! IGNORED !!!</span>
 * </pre>
 *
 * <p>
 * If you wish to temporarily ignore an entire suite of tests, you can (on the JVM, not Scala.js) annotate the test class with <code>@Ignore</code>, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.ignoreall
 * <br/><span class="stReserved">import</span> org.scalatest.flatspec.AsyncFlatSpec
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <span class="stReserved">import</span> org.scalatest.Ignore
 * <br/>@<span class="stType">Ignore</span>
 * <span class="stReserved">class</span> <span class="stType">AddSpec</span> <span class="stReserved">extends</span> <span class="stType">AsyncFlatSpec</span> {
 * <br/>  <span class="stReserved">def</span> addSoon(addends: <span class="stType">Int</span>*): <span class="stType">Future[Int]</span> = <span class="stType">Future</span> { addends.sum }
 * <br/>  <span class="stQuotedString">"addSoon"</span> should <span class="stQuotedString">"eventually compute a sum of passed Ints"</span> in {
 *     <span class="stReserved">val</span> futureSum: <span class="stType">Future[Int]</span> = addSoon(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *     <span class="stLineComment">// You can map assertions onto a Future, then return</span>
 *     <span class="stLineComment">// the resulting Future[Assertion] to ScalaTest:</span>
 *     futureSum map { sum =&gt; assert(sum == <span class="stLiteral">3</span>) }
 *   }
 * <br/>  <span class="stReserved">def</span> addNow(addends: <span class="stType">Int</span>*): <span class="stType">Int</span> = addends.sum
 * <br/>  <span class="stQuotedString">"addNow"</span> should <span class="stQuotedString">"immediately compute a sum of passed Ints"</span> in {
 *     <span class="stReserved">val</span> sum: <span class="stType">Int</span> = addNow(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *     <span class="stLineComment">// You can also write synchronous tests. The body</span>
 *     <span class="stLineComment">// must have result type Assertion:</span>
 *     assert(sum == <span class="stLiteral">3</span>)
 *   }
 * }
 * </pre>
 *
 * <p>
 * When you mark a test class with a tag annotation, ScalaTest will mark each test defined in that class with that tag.
 * Thus, marking the <code>AddSpec</code> in the above example with the <code>@Ignore</code> tag annotation means that both tests
 * in the class will be ignored. If you run the above <code>AddSpec</code> in the Scala interpreter, you'll see:
 * </p>
 *
 * <pre class="stREPL">
 * <span class="stGreen">AddSpec:</span>
 * <span class="stGreen">addSoon</span>
 * <span class="stYellow">- should eventually compute a sum of passed Ints !!! IGNORED !!!</span>
 * <span class="stGreen">addNow</span>
 * <span class="stYellow">- should immediately compute a sum of passed Ints !!! IGNORED !!!</span>
 * </pre>
 *
 * <p>
 * Note that marking a test class as ignored won't prevent it from being discovered by ScalaTest. Ignored classes
 * will be discovered and run, and all their tests will be reported as ignored. This is intended to keep the ignored
 * class visible, to encourage the developers to eventually fix and &ldquo;un-ignore&rdquo; it. If you want to
 * prevent a class from being discovered at all (on the JVM, not Scala.js), use the <a href="DoNotDiscover.html"><code>DoNotDiscover</code></a>
 * annotation instead.
 * </p>
 *
 * <p>
 * If you want to ignore all tests of a suite on Scala.js, where annotations can't be inspected at runtime, you'll need
 * to change <code>it</code> to <code>ignore</code> at each test site. To make a suite non-discoverable on Scala.js, ensure it
 * does not declare a public no-arg constructor.  You can either declare a public constructor that takes one or more
 * arguments, or make the no-arg constructor non-public.  Because this technique will also make the suite non-discoverable
 * on the JVM, it is a good approach for suites you want to run (but not be discoverable) on both Scala.js and the JVM.
 * </p>
 *
 * <a name="informers"></a><h2>Informers</h2>
 *
 * <p>
 * One of the parameters to <code>AsyncFlatSpec</code>'s <code>run</code> method is a <a href="Reporter.html"><code>Reporter</code></a>, which
 * will collect and report information about the running suite of tests.
 * Information about suites and tests that were run, whether tests succeeded or failed, 
 * and tests that were ignored will be passed to the <code>Reporter</code> as the suite runs.
 * Most often the reporting done by default by <code>AsyncFlatSpec</code>'s methods will be sufficient, but
 * occasionally you may wish to provide custom information to the <code>Reporter</code> from a test.
 * For this purpose, an <a href="Informer.html"><code>Informer</code></a> that will forward information to the current <code>Reporter</code>
 * is provided via the <code>info</code> parameterless method.
 * You can pass the extra information to the <code>Informer</code> via its <code>apply</code> method.
 * The <code>Informer</code> will then pass the information to the <code>Reporter</code> via an <a href="events/InfoProvided.html"><code>InfoProvided</code></a> event.
 * </p>
 * 
 * <p>
 * One use case for the <code>Informer</code> is to pass more information about a specification to the reporter. For example,
 * the <a href="GivenWhenThen.html"><code>GivenWhenThen</code></a> trait provides methods that use the implicit <code>info</code> provided by <code>AsyncFlatSpec</code>
 * to pass such information to the reporter.  Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.info
 * <br/><span class="stReserved">import</span> collection.mutable
 * <span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">flatspec.AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">GivenWhenThen</span> {
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
 *     succeed
 *   }
 * }
 * </pre>
 *
 * <p>
 * If you run this <code>AsyncFlatSpec</code> from the interpreter, you will see the following output:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new SetSpec)
 * <span class="stGreen">SetSpec:
 * A mutable Set
 * - should allow an element to be added
 *   + Given an empty mutable Set 
 *   + When an element is added 
 *   + Then the Set should have size 1 
 *   + And the Set should contain the added element 
 *   + That's all folks! </span>
 * </pre>
 *
 * <a name="documenters"></a><h2>Documenters</h2>
 *
 * <p>
 * <code>AsyncFlatSpec</code> also provides a <code>markup</code> method that returns a <a href="Documenter.html"><code>Documenter</code></a>, which allows you to send
 * to the <code>Reporter</code> text formatted in <a href="http://daringfireball.net/projects/markdown/" target="_blank">Markdown syntax</a>.
 * You can pass the extra information to the <code>Documenter</code> via its <code>apply</code> method.
 * The <code>Documenter</code> will then pass the information to the <code>Reporter</code> via an <a href="events/MarkupProvided.html"><code>MarkupProvided</code></a> event.
 * </p>
 *
 * <p>
 * Here's an example <code>AsyncFlatSpec</code> that uses <code>markup</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.markup
 * <br/><span class="stReserved">import</span> collection.mutable
 * <span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">flatspec.AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">GivenWhenThen</span> {
 * <br/>  markup { <span class="stQuotedString">"""</span>
 * <span class="stQuotedString"></span>
 * <span class="stQuotedString">Mutable Set</span>
 * <span class="stQuotedString">&mdash;&mdash;&mdash;--</span>
 * <span class="stQuotedString"></span>
 * <span class="stQuotedString">A set is a collection that contains no duplicate elements.</span>
 * <span class="stQuotedString"></span>
 * <span class="stQuotedString">To implement a concrete mutable set, you need to provide implementations</span>
 * <span class="stQuotedString">of the following methods:</span>
 * <span class="stQuotedString"></span>
 *     <span class="stQuotedString">def contains(elem: A): Boolean</span>
 *     <span class="stQuotedString">def iterator: Iterator[A]</span>
 *     <span class="stQuotedString">def += (elem: A): this.type</span>
 *     <span class="stQuotedString">def -= (elem: A): this.type</span>
 * <span class="stQuotedString"></span>
 * <span class="stQuotedString">If you wish that methods like `take`,</span>
 * <span class="stQuotedString">`drop`, `filter` return the same kind of set,</span>
 * <span class="stQuotedString">you should also override:</span>
 * <span class="stQuotedString"></span>
 *     <span class="stQuotedString">def empty: This</span>
 * <span class="stQuotedString"></span>
 * <span class="stQuotedString">It is also good idea to override methods `foreach` and</span>
 * <span class="stQuotedString">`size` for efficiency.</span>
 * <span class="stQuotedString"></span>
 *   <span class="stQuotedString">"""</span> }
 * <br/>  <span class="stQuotedString">"A mutable Set"</span> should <span class="stQuotedString">"allow an element to be added"</span> in {
 *     <span class="stType">Given</span>(<span class="stQuotedString">"an empty mutable Set"</span>)
 *     <span class="stReserved">val</span> set = mutable.Set.empty[<span class="stType">String</span>]
 * <br/>    <span class="stType">When</span>(<span class="stQuotedString">"an element is added"</span>)
 *     set += <span class="stQuotedString">"clarity"</span>
 * <br/>    <span class="stType">Then</span>(<span class="stQuotedString">"the Set should have size 1"</span>)
 *     assert(set.size === <span class="stLiteral">1</span>)
 * <br/>    <span class="stType">And</span>(<span class="stQuotedString">"the Set should contain the added element"</span>)
 *     assert(set.contains(<span class="stQuotedString">"clarity"</span>))
 * <br/>    markup(<span class="stQuotedString">"This test finished with a **bold** statement!"</span>)
 *     succeed
 *   }
 * }
 * </pre>
 *
 * <p>
 * Although all of ScalaTest's built-in reporters will display the markup text in some form,
 * the HTML reporter will format the markup information into HTML. Thus, the main purpose of <code>markup</code> is to
 * add nicely formatted text to HTML reports. Here's what the above <code>SetSpec</code> would look like in the HTML reporter:
 * </p>
 *
 * <img class="stScreenShot" src="../../lib/flatSpec.gif">
 *
 * <a name="notifiersAlerters"></a><h2>Notifiers and alerters</h2>
 *
 * <p>
 * ScalaTest records text passed to <code>info</code> and <code>markup</code> during tests, and sends the recorded text in the <code>recordedEvents</code> field of 
 * test completion events like <code>TestSucceeded</code> and <code>TestFailed</code>. This allows string reporters (like the standard out reporter) to show
 * <code>info</code> and <code>markup</code> text <em>after</em> the test name in a color determined by the outcome of the test. For example, if the test fails, string
 * reporters will show the <code>info</code> and <code>markup</code> text in red. If a test succeeds, string reporters will show the <code>info</code>
 * and <code>markup</code> text in green. While this approach helps the readability of reports, it means that you can't use <code>info</code> to get status
 * updates from long running tests.
 * </p>
 *
 * <p>
 * To get immediate (<em>i.e.</em>, non-recorded) notifications from tests, you can use <code>note</code> (a <a href="Notifier.html"><code>Notifier</code></a>) and <code>alert</code>
 * (an <a href="Alerter.html"><code>Alerter</code></a>). Here's an example showing the differences:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.note
 * <br/><span class="stReserved">import</span> collection.mutable
 * <span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">flatspec.AsyncFlatSpec</span> {
 * <br/>  <span class="stQuotedString">"A mutable Set"</span> should <span class="stQuotedString">"allow an element to be added"</span> in {
 * <br/>    info(<span class="stQuotedString">"info is recorded"</span>)
 *     markup(<span class="stQuotedString">"markup is *also* recorded"</span>)
 *     note(<span class="stQuotedString">"notes are sent immediately"</span>)
 *     alert(<span class="stQuotedString">"alerts are also sent immediately"</span>)
 * <br/>    <span class="stReserved">val</span> set = mutable.Set.empty[<span class="stType">String</span>]
 *     set += <span class="stQuotedString">"clarity"</span>
 *     assert(set.size === <span class="stLiteral">1</span>)
 *     assert(set.contains(<span class="stQuotedString">"clarity"</span>))
 *   }
 * }
 * </pre>
 *
 * <p>
 * Because <code>note</code> and <code>alert</code> information is sent immediately, it will appear <em>before</em> the test name in string reporters, and its color will
 * be unrelated to the ultimate outcome of the test: <code>note</code> text will always appear in green, <code>alert</code> text will always appear in yellow.
 * Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new SetSpec)
 * <span class="stGreen">SetSpec:
 * A mutable Set
 *   + notes are sent immediately</span>
 *   <span class="stYellow">+ alerts are also sent immediately</span>
 * <span class="stGreen">- should allow an element to be added
 *   + info is recorded 
 *   + markup is *also* recorded</span>
 * </pre>
 *
 * <p>
 * Another example is <a href="tools/Runner$.html#slowpokeNotifications">slowpoke notifications</a>.
 * If you find a test is taking a long time to complete, but you're not sure which test, you can enable 
 * slowpoke notifications. ScalaTest will use an <code>Alerter</code> to fire an event whenever a test has been running
 * longer than a specified amount of time.
 * </p>
 *
 * <p>
 * In summary, use <code>info</code> and <code>markup</code> for text that should form part of the specification output. Use
 * <code>note</code> and <code>alert</code> to send status notifications. (Because the HTML reporter is intended to produce a
 * readable, printable specification, <code>info</code> and <code>markup</code> text will appear in the HTML report, but
 * <code>note</code> and <code>alert</code> text will not.)
 * </p>
 *
 * <a name="pendingTests"></a><h2>Pending tests</h2>
 *
 * <p>
 * A <em>pending test</em> is one that has been given a name but is not yet implemented. The purpose of
 * pending tests is to facilitate a style of testing in which documentation of behavior is sketched
 * out before tests are written to verify that behavior (and often, before the behavior of
 * the system being tested is itself implemented). Such sketches form a kind of specification of
 * what tests and functionality to implement later.
 * </p>
 *
 * <p>
 * To support this style of testing, a test can be given a name that specifies one
 * bit of behavior required by the system being tested. At the end of the test,
 * it can call method <code>pending</code>, which will cause it to complete abruptly with <code>TestPendingException</code>.
 * </p>
 *
 * <p>
 * Because tests in ScalaTest can be designated as pending with <code>TestPendingException</code>, both the test name and any information
 * sent to the reporter when running the test can appear in the report of a test run. (In other words,
 * the code of a pending test is executed just like any other test.) However, because the test completes abruptly
 * with <code>TestPendingException</code>, the test will be reported as pending, to indicate
 * the actual test, and possibly the functionality, has not yet been implemented. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.pending
 * <br/><span class="stReserved">import</span> org.scalatest.flatspec.AsyncFlatSpec
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <br/><span class="stReserved">class</span> <span class="stType">AddSpec</span> <span class="stReserved">extends</span> <span class="stType">AsyncFlatSpec</span> {
 * <br/>  <span class="stReserved">def</span> addSoon(addends: <span class="stType">Int</span>*): <span class="stType">Future[Int]</span> = <span class="stType">Future</span> { addends.sum }
 * <br/>  <span class="stQuotedString">"addSoon"</span> should <span class="stQuotedString">"eventually compute a sum of passed Ints"</span> in (pending)
 * <br/>  <span class="stReserved">def</span> addNow(addends: <span class="stType">Int</span>*): <span class="stType">Int</span> = addends.sum
 * <br/>  <span class="stQuotedString">"addNow"</span> should <span class="stQuotedString">"immediately compute a sum of passed Ints"</span> in {
 *     <span class="stReserved">val</span> sum: <span class="stType">Int</span> = addNow(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *     <span class="stLineComment">// You can also write synchronous tests. The body</span>
 *     <span class="stLineComment">// must have result type Assertion:</span>
 *     assert(sum == <span class="stLiteral">3</span>)
 *   }
 * }
 * </pre>
 *
 * <p>
 * (Note: "<code>(pending)</code>" is the body of the test. Thus the test contains just one statement, an invocation
 * of the <code>pending</code> method, which throws <code>TestPendingException</code>.)
 * If you run this version of <code>AddSpec</code> with:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new AddSpec)
 * </pre>
 *
 * <p>
 * It will run both tests, but report that first test is pending. You'll see:
 * </p>
 *
 * <pre class="stREPL">
 * <span class="stGreen">AddSpec:</span>
 * <span class="stGreen">addSoon</span>
 * <span class="stYellow">- should eventually compute a sum of passed Ints (pending)</span>
 * <span class="stGreen">addNow</span>
 * <span class="stGreen">- should immediately compute a sum of passed Ints</span>
 * </pre>
 *
 * <p>
 * One difference between an ignored test and a pending one is that an ignored test is intended to be used during
 * significant refactorings of the code under test, when tests break and you don't want to spend the time to fix
 * all of them immediately. You can mark some of those broken tests as ignored temporarily, so that you can focus the red
 * bar on just failing tests you actually want to fix immediately. Later you can go back and fix the ignored tests.
 * In other words, by ignoring some failing tests temporarily, you can more easily notice failed tests that you actually
 * want to fix. By contrast, a pending test is intended to be used before a test and/or the code under test is written.
 * Pending indicates you've decided to write a test for a bit of behavior, but either you haven't written the test yet, or
 * have only written part of it, or perhaps you've written the test but don't want to implement the behavior it tests
 * until after you've implemented a different bit of behavior you realized you need first. Thus ignored tests are designed
 * to facilitate refactoring of existing code whereas pending tests are designed to facilitate the creation of new code.
 * </p>
 *
 * <p>
 * One other difference between ignored and pending tests is that ignored tests are implemented as a test tag that is
 * excluded by default. Thus an ignored test is never executed. By contrast, a pending test is implemented as a
 * test that throws <code>TestPendingException</code> (which is what calling the <code>pending</code> method does). Thus
 * the body of pending tests are executed up until they throw <code>TestPendingException</code>.
 * </p>
 *
 * <a name="taggingTests"></a><h2>Tagging tests</h2>
 *
 * <p>
 * An <code>AsyncFlatSpec</code>'s tests may be classified into groups by <em>tagging</em> them with string names.
 * As with any suite, when executing an <code>AsyncFlatSpec</code>, groups of tests can
 * optionally be included and/or excluded. To tag an <code>AsyncFlatSpec</code>'s tests,
 * you pass objects that extend class <code>org.scalatest.Tag</code> to methods
 * that register tests. Class <code>Tag</code> takes one parameter, a string name.  If you have
 * created tag annotation interfaces as described in the <a href="Tag.html"><code>Tag</code> documentation</a>, then you
 * will probably want to use tag names on your test functions that match. To do so, simply
 * pass the fully qualified names of the tag interfaces to the <code>Tag</code> constructor. For example, if you've
 * defined a tag annotation interface with fully qualified name,
 * <code>com.mycompany.tags.DbTest</code>, then you could
 * create a matching tag for <code>AsyncFlatSpec</code>s like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.tagging
 * <br/><span class="stReserved">import</span> org.scalatest.Tag
 * <br/><span class="stReserved">object</span> <span class="stType">DbTest</span> <span class="stReserved">extends</span> <span class="stType">Tag</span>(<span class="stQuotedString">"com.mycompany.tags.DbTest"</span>)
 * </pre>
 *
 * <p>
 * Given these definitions, you could place <code>AsyncFlatSpec</code> tests into groups with tags like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest.flatspec.AsyncFlatSpec
 * <span class="stReserved">import</span> org.scalatest.tagobjects.Slow
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <br/><span class="stReserved">class</span> <span class="stType">AddSpec</span> <span class="stReserved">extends</span> <span class="stType">AsyncFlatSpec</span> {
 * <br/>  <span class="stReserved">def</span> addSoon(addends: <span class="stType">Int</span>*): <span class="stType">Future[Int]</span> = <span class="stType">Future</span> { addends.sum }
 * <br/>  <span class="stQuotedString">"addSoon"</span> should <span class="stQuotedString">"eventually compute a sum of passed Ints"</span> taggedAs(<span class="stType">Slow</span>) in {
 *     <span class="stReserved">val</span> futureSum: <span class="stType">Future[Int]</span> = addSoon(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *     <span class="stLineComment">// You can map assertions onto a Future, then return</span>
 *     <span class="stLineComment">// the resulting Future[Assertion] to ScalaTest:</span>
 *     futureSum map { sum =&gt; assert(sum == <span class="stLiteral">3</span>) }
 *   }
 * <br/>  <span class="stReserved">def</span> addNow(addends: <span class="stType">Int</span>*): <span class="stType">Int</span> = addends.sum
 * <br/>  <span class="stQuotedString">"addNow"</span> should <span class="stQuotedString">"immediately compute a sum of passed Ints"</span> taggedAs(<span class="stType">Slow</span>, <span class="stType">DbTest</span>) in {
 *     <span class="stReserved">val</span> sum: <span class="stType">Int</span> = addNow(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
 *     <span class="stLineComment">// You can also write synchronous tests. The body</span>
 *     <span class="stLineComment">// must have result type Assertion:</span>
 *     assert(sum == <span class="stLiteral">3</span>)
 *   }
 * }
 * </pre>
 *
 * <p>
 * This code marks both tests with the <code>org.scalatest.tags.Slow</code> tag,
 * and the second test with the <code>com.mycompany.tags.DbTest</code> tag.
 * </p>
 *
 * <p>
 * The <code>run</code> method takes a <code>Filter</code>, whose constructor takes an optional
 * <code>Set[String]</code> called <code>tagsToInclude</code> and a <code>Set[String]</code> called
 * <code>tagsToExclude</code>. If <code>tagsToInclude</code> is <code>None</code>, all tests will be run
 * except those those belonging to tags listed in the
 * <code>tagsToExclude</code> <code>Set</code>. If <code>tagsToInclude</code> is defined, only tests
 * belonging to tags mentioned in the <code>tagsToInclude</code> set, and not mentioned in <code>tagsToExclude</code>,
 * will be run.
 * </p>
 *
 * <p>
 * It is recommended, though not required, that you create a corresponding tag annotation when you
 * create a <code>Tag</code> object. A tag annotation (on the JVM, not Scala.js) allows you to tag all the tests of an <code>AsyncFlatSpec</code> in
 * one stroke by annotating the class. For more information and examples, see the
 * <a href="Tag.html">documentation for class <code>Tag</code></a>. On Scala.js, to tag all tests of a suite, you'll need to
 * tag each test individually at the test site.
 * </p>
 *
 * <a name="sharedFixtures"></a>
 * <h2>Shared fixtures</h2>
 *
 * <p>
 * A test <em>fixture</em> is composed of the objects and other artifacts (files, sockets, database
 * connections, <em>etc.</em>) tests use to do their work.
 * When multiple tests need to work with the same fixtures, it is important to try and avoid
 * duplicating the fixture code across those tests. The more code duplication you have in your
 * tests, the greater drag the tests will have on refactoring the actual production code.
 * </p>
 *
 * <p>
 * ScalaTest recommends three techniques to eliminate such code duplication in async styles:
 * </p>
 *
 * <ul>
 * <li>Refactor using Scala</li>
 * <li>Override <code>withFixture</code></li>
 * <li>Mix in a <em>before-and-after</em> trait</li>
 * </ul>
 *
 * <p>Each technique is geared towards helping you reduce code duplication without introducing
 * instance <code>var</code>s, shared mutable objects, or other dependencies between tests. Eliminating shared
 * mutable state across tests will make your test code easier to reason about and eliminate the need to
 * synchronize access to shared mutable state on the JVM.
 * </p>
 *
 * <p>
 * The following sections describe these techniques, including explaining the recommended usage
 * for each. But first, here's a table summarizing the options:</p>
 *
 * <table style="border-collapse: collapse; border: 1px solid black">
 *
 * <tr>
 *   <td colspan="2" style="background-color: #CCCCCC; border-width: 1px; padding: 3px; padding-top: 7px; border: 1px solid black; text-align: left">
 *     <strong>Refactor using Scala when different tests need different fixtures.</strong>
 *   </td>
 * </tr>
 *
 * <tr>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: right">
 *     <a href="#getFixtureMethods">get-fixture methods</a>
 *   </td>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 *     The <em>extract method</em> refactor helps you create a fresh instances of mutable fixture objects in each test
 *     that needs them, but doesn't help you clean them up when you're done.
 *   </td>
 * </tr>
 *
 * <tr>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: right">
 *     <a href="#loanFixtureMethods">loan-fixture methods</a>
 *   </td>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 *     Factor out dupicate code with the <em>loan pattern</em> when different tests need different fixtures <em>that must be cleaned up afterwards</em>.
 *   </td>
 * </tr>
 *
 * <tr>
 *   <td colspan="2" style="background-color: #CCCCCC; border-width: 1px; padding: 3px; padding-top: 7px; border: 1px solid black; text-align: left">
 *     <strong>Override <code>withFixture</code> when most or all tests need the same fixture.</strong>
 *   </td>
 * </tr>
 *
 * <tr>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: right">
 *     <a href="#withFixtureNoArgAsyncTest">
 *       <code>withFixture(NoArgAsyncTest)</code></a>
 *     </td>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 *     <p>
 *     The recommended default approach when most or all tests need the same fixture treatment. This general technique
 *     allows you, for example, to perform side effects at the beginning and end of all or most tests,
 *     transform the outcome of tests, retry tests, make decisions based on test names, tags, or other test data.
 *     Use this technique unless:
 *     </p>
 *  <dl>
 *  <dd style="display: list-item; list-style-type: disc; margin-left: 1.2em;">Different tests need different fixtures (refactor using Scala instead)</dd>
 *  <dd style="display: list-item; list-style-type: disc; margin-left: 1.2em;">An exception in fixture code should abort the suite, not fail the test (use a <em>before-and-after</em> trait instead)</dd>
 *  <dd style="display: list-item; list-style-type: disc; margin-left: 1.2em;">You have objects to pass into tests (override <code>withFixture(<em>One</em>ArgAsyncTest)</code> instead)</dd>
 *  </dl>
 *  </td>
 * </tr>
 *
 * <tr>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: right">
 *     <a href="#withFixtureOneArgAsyncTest">
 *       <code>withFixture(OneArgAsyncTest)</code>
 *     </a>
 *   </td>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 *     Use when you want to pass the same fixture object or objects as a parameter into all or most tests.
 *   </td>
 * </tr>
 *
 * <tr>
 *   <td colspan="2" style="background-color: #CCCCCC; border-width: 1px; padding: 3px; padding-top: 7px; border: 1px solid black; text-align: left">
 *     <strong>Mix in a before-and-after trait when you want an aborted suite, not a failed test, if the fixture code fails.</strong>
 *   </td>
 * </tr>
 *
 * <tr>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: right">
 *     <a href="#beforeAndAfter"><code>BeforeAndAfter</code></a>
 *   </td>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 *     Use this boilerplate-buster when you need to perform the same side-effects before and/or after tests, rather than at the beginning or end of tests.
 *   </td>
 * </tr>
 *
 * <tr>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: right">
 *     <a href="#composingFixtures"><code>BeforeAndAfterEach</code></a>
 *   </td>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 *     Use when you want to <em>stack traits</em> that perform the same side-effects before and/or after tests, rather than at the beginning or end of tests.
 *   </td>
 * </tr>
 *
 * </table>
 *
 * <a name="getFixtureMethods"></a>
 * <h4>Calling get-fixture methods</h4>
 *
 * <p>
 * If you need to create the same mutable fixture objects in multiple tests, and don't need to clean them up after using them, the simplest approach is to write one or
 * more <em>get-fixture</em> methods. A get-fixture method returns a new instance of a needed fixture object (or a holder object containing
 * multiple fixture objects) each time it is called. You can call a get-fixture method at the beginning of each
 * test that needs the fixture, storing the returned object or objects in local variables. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.getfixture
 * <br/><span class="stReserved">import</span> org.scalatest.flatspec.AsyncFlatSpec
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">AsyncFlatSpec</span> {
 * <br/>  <span class="stReserved">def</span> fixture: <span class="stType">Future[String]</span> = <span class="stType">Future</span> { <span class="stQuotedString">"ScalaTest is "</span> }
 * <br/>  <span class="stQuotedString">"Testing"</span> should <span class="stQuotedString">"be easy"</span> in {
 *     <span class="stReserved">val</span> future = fixture
 *     <span class="stReserved">val</span> result = future map { s =&gt; s + <span class="stQuotedString">"easy!"</span> }
 *     result map { s =&gt;
 *       assert(s == <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *     }
 *   }
 * <br/>  it should <span class="stQuotedString">"be fun"</span> in {
 *     <span class="stReserved">val</span> future = fixture
 *     <span class="stReserved">val</span> result = future map { s =&gt; s + <span class="stQuotedString">"fun!"</span> }
 *     result map { s =&gt;
 *       assert(s == <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * If you need to configure fixture objects differently in different tests, you can pass configuration into the get-fixture method.
 * For example, you could pass in an initial value for a fixture object as a parameter to the get-fixture method.
 * </p>
 *
 * <a name="withFixtureNoArgAsyncTest"></a>
 * <h4>Overriding <code>withFixture(NoArgAsyncTest)</code></h4>
 *
 * <p>
 * Although the get-fixture method approach takes care of setting up a fixture at the beginning of each
 * test, it doesn't address the problem of cleaning up a fixture at the end of the test. If you just need to perform a side-effect at the beginning or end of
 * a test, and don't need to actually pass any fixture objects into the test, you can override <code>withFixture(NoArgAsyncTest)</code>, a
 * method defined in trait <a href="AsyncTestSuite.html"><code>AsyncTestSuite</code></a>, a supertrait of <code>AsyncFlatSpec</code>.
 * </p>
 *
 * <p>
 * Trait <code>AsyncFlatSpec</code>'s <code>runTest</code> method passes a no-arg async test function to
 * <code>withFixture(NoArgAsyncTest)</code>. It is <code>withFixture</code>'s
 * responsibility to invoke that test function. The default implementation of <code>withFixture</code> simply
 * invokes the function and returns the result, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stLineComment">// Default implementation in trait AsyncTestSuite</span>
 * <span class="stReserved">protected</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgAsyncTest</span>): <span class="stType">FutureOutcome</span> = {
 *   test()
 * }
 * </pre>
 *
 * <p>
 * You can, therefore, override <code>withFixture</code> to perform setup before invoking the test function,
 * and/or perform cleanup after the test completes. The recommended way to ensure cleanup is performed after a test completes is
 * to use the <code>complete</code>-<code>lastly</code> syntax, defined in supertrait <a href="CompleteLastly.html"><code>CompleteLastly</code></a>.
 * The <code>complete</code>-<code>lastly</code> syntax will ensure that
 * cleanup will occur whether future-producing code completes abruptly by throwing an exception, or returns
 * normally yielding a future. In the latter case, <code>complete</code>-<code>lastly</code> will register the cleanup code
 * to execute asynchronously when the future completes.
 * </p>
 *
 * <p>
 * The <code>withFixture</code> method is designed to be stacked, and to enable this, you should always call the <code>super</code> implementation
 * of <code>withFixture</code>, and let it invoke the test function rather than invoking the test function directly. In other words, instead of writing
 * &ldquo;<code>test()</code>&rdquo;, you should write &ldquo;<code>super.withFixture(test)</code>&rdquo;, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stLineComment">// Your implementation</span>
 * <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgAsyncTest</span>) = {
 * <br/>  <span class="stLineComment">// Perform setup here</span>
 * <br/>  complete {
 *     <span class="stReserved">super</span>.withFixture(test) <span class="stLineComment">// Invoke the test function</span>
 *   } lastly {
 *     <span class="stLineComment">// Perform cleanup here</span>
 *   }
 * }
 * </pre>
 *
 * <p>
 * If you have no cleanup to perform, you can write <code>withFixture</code> like this instead:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stLineComment">// Your implementation</span>
 * <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgAsyncTest</span>) = {
 * <br/>  <span class="stLineComment">// Perform setup here</span>
 * <br/>  <span class="stReserved">super</span>.withFixture(test) <span class="stLineComment">// Invoke the test function</span>
 * }
 * </pre>
 *
 * <p>
 * If you want to perform an action only for certain outcomes, you'll need to
 * register code performing that action as a callback on the <code>Future</code> using
 * one of <code>Future</code>'s registration methods: <code>onComplete</code>, <code>onSuccess</code>,
 * or <code>onFailure</code>. Note that if a test fails, that will be treated as a
 * <code>scala.util.Success(org.scalatest.Failed)</code>. So if you want to perform an
 * action if a test fails, for example, you'd register the callback using <code>onSuccess</code>.
 * </p>
 *
 * <p>
 * Here's an example in which <code>withFixture(NoArgAsyncTest)</code> is used to take a
 * snapshot of the working directory if a test fails, and
 * send that information to the standard output stream:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.noargasynctest
 * <br/><span class="stReserved">import</span> java.io.File
 * <span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">flatspec.AsyncFlatSpec</span> {
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgAsyncTest</span>) = {
 * <br/>    <span class="stReserved">super</span>.withFixture(test) onFailedThen { _ =&gt;
 *       <span class="stReserved">val</span> currDir = <span class="stReserved">new</span> <span class="stType">File</span>(<span class="stQuotedString">"."</span>)
 *       <span class="stReserved">val</span> fileNames = currDir.list()
 *       info(<span class="stQuotedString">"Dir snapshot: "</span> + fileNames.mkString(<span class="stQuotedString">", "</span>))
 *     }
 *   }
 * <br/>  <span class="stReserved">def</span> addSoon(addends: <span class="stType">Int</span>*): <span class="stType">Future[Int]</span> = <span class="stType">Future</span> { addends.sum }
 * <br/>  <span class="stQuotedString">"This test"</span> should <span class="stQuotedString">"succeed"</span> in {
 *     addSoon(<span class="stLiteral">1</span>, <span class="stLiteral">1</span>) map { sum =&gt; assert(sum == <span class="stLiteral">2</span>) }
 *   }
 * <br/>  it should <span class="stQuotedString">"fail"</span> in {
 *     addSoon(<span class="stLiteral">1</span>, <span class="stLiteral">1</span>) map { sum =&gt; assert(sum == <span class="stLiteral">3</span>) }
 *   }
 * }
 * </pre>
 *
 * <p>
 * Running this version of <code>ExampleSpec</code> in the interpreter in a directory with two files, <code>hello.txt</code> and <code>world.txt</code>
 * would give the following output:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new ExampleSpec)
 * <span class="stGreen">ExampleSpec:
 * This test
 * - should succeed</span>
 * <span class="stRed">- should fail *** FAILED ***
 *   2 did not equal 3 (<console>:33)</span>
 * </pre>
 *
 * <p>
 * Note that the <a href="Suite$NoArgTest.html"><code>NoArgAsyncTest</code></a> passed to <code>withFixture</code>, in addition to
 * an <code>apply</code> method that executes the test, also includes the test name and the <a href="ConfigMap.html">config
 * map</a> passed to <code>runTest</code>. Thus you can also use the test name and configuration objects in your <code>withFixture</code>
 * implementation.
 * </p>
 *
 * <p>
 * Lastly, if you want to transform the outcome in some way in <code>withFixture</code>, you'll need to use either the
 * <code>map</code> or <code>transform</code> methods of <code>Future</code>, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stLineComment">// Your implementation</span>
 * <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgAsyncTest</span>) = {
 * <br/>  <span class="stLineComment">// Perform setup here</span>
 * <br/>  <span class="stReserved">val</span> futureOutcome = <span class="stReserved">super</span>.withFixture(test) <span class="stLineComment">// Invoke the test function</span>
 * <br/>  futureOutcome change { outcome =&gt;
 *     <span class="stLineComment">// transform the outcome into a new outcome here</span>
 *   }
 * }
 * </pre>
 *
 * <p>
 * Note that a <code>NoArgAsyncTest</code>'s <code>apply</code> method will return a <code>scala.util.Failure</code> only if
 * the test completes abruptly with a "test-fatal" exception (such as <code>OutOfMemoryError</code>) that should
 * cause the suite to abort rather than the test to fail. Thus usually you would use <code>map</code>
 * to transform future outcomes, not <code>transform</code>, so that such test-fatal exceptions pass through
 * unchanged. The suite will abort asynchronously with any exception returned from <code>NoArgAsyncTest</code>'s
 * apply method in a <code>scala.util.Failure</code>.
 * </p>
 *
 * <a name="loanFixtureMethods"></a>
 * <h4>Calling loan-fixture methods</h4>
 *
 * <p>
 * If you need to both pass a fixture object into a test <em>and</em> perform cleanup at the end of the test, you'll need to use the <em>loan pattern</em>.
 * If different tests need different fixtures that require cleanup, you can implement the loan pattern directly by writing <em>loan-fixture</em> methods.
 * A loan-fixture method takes a function whose body forms part or all of a test's code. It creates a fixture, passes it to the test code by invoking the
 * function, then cleans up the fixture after the function returns.
 * </p>
 *
 * <p>
 * The following example shows three tests that use two fixtures, a database and a file. Both require cleanup after, so each is provided via a
 * loan-fixture method. (In this example, the database is simulated with a <code>StringBuffer</code>.)
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.loanfixture
 * <br/><span class="stReserved">import</span> java.util.concurrent.ConcurrentHashMap
 * <br/><span class="stReserved">import</span> scala.concurrent.Future
 * <span class="stReserved">import</span> scala.concurrent.ExecutionContext
 * <br/><span class="stReserved">object</span> <span class="stType">DbServer</span> { <span class="stLineComment">// Simulating a database server</span>
 *   <span class="stReserved">type</span> <span class="stType">Db</span> = <span class="stType">StringBuffer</span>
 *   <span class="stReserved">private</span> <span class="stReserved">final</span> <span class="stReserved">val</span> databases = <span class="stReserved">new</span> <span class="stType">ConcurrentHashMap[String, Db]</span>
 *   <span class="stReserved">def</span> createDb(name: <span class="stType">String</span>): <span class="stType">Db</span> = {
 *     <span class="stReserved">val</span> db = <span class="stReserved">new</span> <span class="stType">StringBuffer</span> <span class="stLineComment">// java.lang.StringBuffer is thread-safe</span>
 *     databases.put(name, db)
 *     db
 *   }
 *   <span class="stReserved">def</span> removeDb(name: <span class="stType">String</span>): <span class="stType">Unit</span> = {
 *     databases.remove(name)
 *   }
 * }
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
 *         <span class="stReserved">case</span> <span class="stType">Append</span>(value) =&gt; sb.append(value)
 *         <span class="stReserved">case</span> <span class="stType">Clear</span> =&gt; sb.clear()
 *       }
 *     }
 *   <span class="stReserved">def</span> ?(get: GetValue.type)(<span class="stReserved">implicit</span> c: <span class="stType">ExecutionContext</span>): <span class="stType">Future[String]</span> =
 *     <span class="stType">Future</span> {
 *       synchronized { sb.toString }
 *     }
 * }
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> DbServer._
 * <span class="stReserved">import</span> java.util.UUID.randomUUID
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">flatspec.AsyncFlatSpec</span> {
 * <br/>  <span class="stReserved">def</span> withDatabase(testCode: <span class="stType">Future[Db]</span> =&gt; <span class="stType">Future[Assertion]</span>) = {
 *     <span class="stReserved">val</span> dbName = randomUUID.toString <span class="stLineComment">// generate a unique db name</span>
 *     <span class="stReserved">val</span> futureDb = <span class="stType">Future</span> { createDb(dbName) } <span class="stLineComment">// create the fixture</span>
 *     complete {
 *       <span class="stReserved">val</span> futurePopulatedDb =
 *         futureDb map { db =&gt;
 *           db.append(<span class="stQuotedString">"ScalaTest is "</span>) <span class="stLineComment">// perform setup</span>
 *         }
 *       testCode(futurePopulatedDb) <span class="stLineComment">// "loan" the fixture to the test code</span>
 *     } lastly {
 *       removeDb(dbName) <span class="stLineComment">// ensure the fixture will be cleaned up</span>
 *     }
 *   }
 * <br/>  <span class="stReserved">def</span> withActor(testCode: <span class="stType">StringActor</span> =&gt; <span class="stType">Future[Assertion]</span>) = {
 *     <span class="stReserved">val</span> actor = <span class="stReserved">new</span> <span class="stType">StringActor</span>
 *     complete {
 *       actor ! <span class="stType">Append</span>(<span class="stQuotedString">"ScalaTest is "</span>) <span class="stLineComment">// set up the fixture</span>
 *       testCode(actor) <span class="stLineComment">// "loan" the fixture to the test code</span>
 *     } lastly {
 *       actor ! <span class="stType">Clear</span> <span class="stLineComment">// ensure the fixture will be cleaned up</span>
 *     }
 *   }
 * <br/>  <span class="stLineComment">// This test needs the actor fixture</span>
 *   <span class="stQuotedString">"Testing"</span> should <span class="stQuotedString">"be productive"</span> in {
 *     withActor { actor =&gt;
 *       actor ! <span class="stType">Append</span>(<span class="stQuotedString">"productive!"</span>)
 *       <span class="stReserved">val</span> futureString = actor ? <span class="stType">GetValue</span>
 *       futureString map { s =&gt;
 *         assert(s == <span class="stQuotedString">"ScalaTest is productive!"</span>)
 *       }
 *     }
 *   }
 * <br/>  <span class="stLineComment">// This test needs the database fixture</span>
 *   <span class="stQuotedString">"Test code"</span> should <span class="stQuotedString">"be readable"</span> in {
 *     withDatabase { futureDb =&gt;
 *       futureDb map { db =&gt;
 *         db.append(<span class="stQuotedString">"readable!"</span>)
 *         assert(db.toString == <span class="stQuotedString">"ScalaTest is readable!"</span>)
 *       }
 *     }
 *   }
 * <br/>  <span class="stLineComment">// This test needs both the actor and the database</span>
 *   it should <span class="stQuotedString">"be clear and concise"</span> in {
 *     withDatabase { futureDb =&gt;
 *       withActor { actor =&gt; <span class="stLineComment">// loan-fixture methods compose</span>
 *         actor ! <span class="stType">Append</span>(<span class="stQuotedString">"concise!"</span>)
 *         <span class="stReserved">val</span> futureString = actor ? <span class="stType">GetValue</span>
 *         <span class="stReserved">val</span> futurePair: <span class="stType">Future[(Db, String)]</span> =
 *           futureDb zip futureString
 *         futurePair map { <span class="stReserved">case</span> (db, s) =&gt;
 *           db.append(<span class="stQuotedString">"clear!"</span>)
 *           assert(db.toString == <span class="stQuotedString">"ScalaTest is clear!"</span>)
 *           assert(s == <span class="stQuotedString">"ScalaTest is concise!"</span>)
 *         }
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * As demonstrated by the last test, loan-fixture methods compose. Not only do loan-fixture methods allow you to
 * give each test the fixture it needs, they allow you to give a test multiple fixtures and clean everything up afterwards.
 * </p>
 *
 * <p>
 * Also demonstrated in this example is the technique of giving each test its own "fixture sandbox" to play in. When your fixtures
 * involve external side-effects, like creating databases, it is a good idea to give each database a unique name as is
 * done in this example. This keeps tests completely isolated, allowing you to run them in parallel if desired.
 * </p>
 *
 * <a name="withFixtureOneArgAsyncTest"></a>
 * <h4>Overriding <code>withFixture(OneArgTest)</code></h4>
 *
 * <p>
 * If all or most tests need the same fixture, you can avoid some of the boilerplate of the loan-fixture method approach by using a
 * <code>FixtureAsyncTestSuite</code> and overriding <code>withFixture(OneArgAsyncTest)</code>.
 * Each test in a <code>FixtureAsyncTestSuite</code> takes a fixture as a parameter, allowing you to pass the fixture into
 * the test. You must indicate the type of the fixture parameter by specifying <code>FixtureParam</code>, and implement a
 * <code>withFixture</code> method that takes a <code>OneArgAsyncTest</code>. This <code>withFixture</code> method is responsible for
 * invoking the one-arg async test function, so you can perform fixture set up before invoking and passing
 * the fixture into the test function, and ensure clean up is performed after the test completes.
 * </p>
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
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.oneargasynctest
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
 *         <span class="stReserved">case</span> <span class="stType">Append</span>(value) =&gt; sb.append(value)
 *         <span class="stReserved">case</span> <span class="stType">Clear</span> =&gt; sb.clear()
 *       }
 *     }
 *   <span class="stReserved">def</span> ?(get: GetValue.type)(<span class="stReserved">implicit</span> c: <span class="stType">ExecutionContext</span>): <span class="stType">Future[String]</span> =
 *     <span class="stType">Future</span> {
 *       synchronized { sb.toString }
 *     }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">FixtureAsyncFlatSpec</span> {
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
 * <br/>  <span class="stQuotedString">"Testing"</span> should <span class="stQuotedString">"be easy"</span> in { actor =&gt;
 *     actor ! <span class="stType">Append</span>(<span class="stQuotedString">"easy!"</span>)
 *     <span class="stReserved">val</span> futureString = actor ? <span class="stType">GetValue</span>
 *     futureString map { s =&gt;
 *       assert(s == <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *     }
 *   }
 * <br/>  it should <span class="stQuotedString">"be fun"</span> in { actor =&gt;
 *     actor ! <span class="stType">Append</span>(<span class="stQuotedString">"fun!"</span>)
 *     <span class="stReserved">val</span> futureString = actor ? <span class="stType">GetValue</span>
 *     futureString map { s =&gt;
 *       assert(s == <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * In this example, the tests required one fixture object, a <code>StringActor</code>. If your tests need multiple fixture objects, you can
 * simply define the <code>FixtureParam</code> type to be a tuple containing the objects or, alternatively, a case class containing
 * the objects.  For more information on the <code>withFixture(OneArgAsyncTest)</code> technique, see
 * the <a href="FixtureAsyncFlatSpec.html">documentation for <code>FixtureAsyncFlatSpec</code></a>.
 * </p>
 *
 * <a name="beforeAndAfter"></a>
 * <h4>Mixing in <code>BeforeAndAfter</code></h4>
 *
 * <p>
 * In all the shared fixture examples shown so far, the activities of creating, setting up, and cleaning up the fixture objects have been
 * performed <em>during</em> the test.  This means that if an exception occurs during any of these activities, it will be reported as a test failure.
 * Sometimes, however, you may want setup to happen <em>before</em> the test starts, and cleanup <em>after</em> the test has completed, so that if an
 * exception occurs during setup or cleanup, the entire suite aborts and no more tests are attempted. The simplest way to accomplish this in ScalaTest is
 * to mix in trait <a href="BeforeAndAfter.html"><code>BeforeAndAfter</code></a>.  With this trait you can denote a bit of code to run before each test
 * with <code>before</code> and/or after each test each test with <code>after</code>, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.beforeandafter
 * <br/><span class="stReserved">import</span> org.scalatest.flatspec.AsyncFlatSpec
 * <span class="stReserved">import</span> org.scalatest.BeforeAndAfter
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
 *         <span class="stReserved">case</span> <span class="stType">Append</span>(value) =&gt; sb.append(value)
 *         <span class="stReserved">case</span> <span class="stType">Clear</span> =&gt; sb.clear()
 *       }
 *     }
 *   <span class="stReserved">def</span> ?(get: GetValue.type)(<span class="stReserved">implicit</span> c: <span class="stType">ExecutionContext</span>): <span class="stType">Future[String]</span> =
 *     <span class="stType">Future</span> {
 *       synchronized { sb.toString }
 *     }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">BeforeAndAfter</span> {
 * <br/>  <span class="stReserved">final</span> <span class="stReserved">val</span> actor = <span class="stReserved">new</span> <span class="stType">StringActor</span>
 * <br/>  before {
 *     actor ! <span class="stType">Append</span>(<span class="stQuotedString">"ScalaTest is "</span>) <span class="stLineComment">// set up the fixture</span>
 *   }
 * <br/>  after {
 *     actor ! <span class="stType">Clear</span> <span class="stLineComment">// clean up the fixture</span>
 *   }
 * <br/>  <span class="stQuotedString">"Testing"</span> should <span class="stQuotedString">"be easy"</span> in {
 *     actor ! <span class="stType">Append</span>(<span class="stQuotedString">"easy!"</span>)
 *     <span class="stReserved">val</span> futureString = actor ? <span class="stType">GetValue</span>
 *     futureString map { s =&gt;
 *       assert(s == <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *     }
 *   }
 * <br/>  it should <span class="stQuotedString">"be fun"</span> in {
 *     actor ! <span class="stType">Append</span>(<span class="stQuotedString">"fun!"</span>)
 *     <span class="stReserved">val</span> futureString = actor ? <span class="stType">GetValue</span>
 *     futureString map { s =&gt;
 *       assert(s == <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * Note that the only way <code>before</code> and <code>after</code> code can communicate with test code is via some
 * side-effecting mechanism, commonly by reassigning instance <code>var</code>s or by changing the state of mutable
 * objects held from instance <code>val</code>s (as in this example). If using instance <code>var</code>s or
 * mutable objects held from instance <code>val</code>s you wouldn't be able to run tests in parallel in the same instance
 * of the test class (on the JVM, not Scala.js) unless you synchronized access to the shared, mutable state.
 * </p>
 *
 * <p>
 * Note that on the JVM, if you override ScalaTest's default
 * <a href="#asyncExecutionModel"><em>serial execution context</em></a>, you will likely need to
 * worry about synchronizing access to shared mutable fixture state, because the execution
 * context may assign different threads to process
 * different <code>Future</code> transformations. Although access to mutable state along
 * the same linear chain of <code>Future</code> transformations need not be synchronized,
 * it can be difficult to spot cases where these constraints are violated. The best approach
 * is to use only immutable objects when transforming <code>Future</code>s. When that's not
 * practical, involve only thread-safe mutable objects, as is done in the above example.
 * On Scala.js, by contrast, you need not worry about thread synchronization, because
 * in effect only one thread exists.
 * </p>
 *
 * <p>
 * Although <code>BeforeAndAfter</code> provides a minimal-boilerplate way to execute code before and after tests, it isn't designed to enable stackable
 * traits, because the order of execution would be non-obvious.  If you want to factor out before and after code that is common to multiple test suites, you
 * should use trait <code>BeforeAndAfterEach</code> instead, as shown later in the next section,
 * <a href="#composingFixtures.html">composing fixtures by stacking traits</a>.
 * </p>
 *
 * <a name="composingFixtures"></a><h2>Composing fixtures by stacking traits</h2>
 *
 * <p>
 * In larger projects, teams often end up with several different fixtures that test classes need in different combinations,
 * and possibly initialized (and cleaned up) in different orders. A good way to accomplish this in ScalaTest is to factor the individual
 * fixtures into traits that can be composed using the <em>stackable trait</em> pattern. This can be done, for example, by placing
 * <code>withFixture</code> methods in several traits, each of which call <code>super.withFixture</code>. Here's an example in
 * which the <code>StringBuilderActor</code> and <code>StringBufferActor</code> fixtures used in the previous examples have been
 * factored out into two <em>stackable fixture traits</em> named <code>Builder</code> and <code>Buffer</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.composingwithasyncfixture
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> org.scalatest.SuiteMixin
 * <span class="stReserved">import</span> collection.mutable.ListBuffer
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <span class="stReserved">import</span> scala.concurrent.ExecutionContext
 * <br/><span class="stLineComment">// Defining actor messages</span>
 * <span class="stReserved">sealed</span> <span class="stReserved">abstract</span> <span class="stReserved">class</span> <span class="stType">StringOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">object</span> <span class="stType">Clear</span> <span class="stReserved">extends</span> <span class="stType">StringOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">Append</span>(value: <span class="stType">String</span>) <span class="stReserved">extends</span> <span class="stType">StringOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">object</span> <span class="stType">GetValue</span>
 * <br/><span class="stReserved">class</span> <span class="stType">StringBuilderActor</span> { <span class="stLineComment">// Simulating an actor</span>
 *   <span class="stReserved">private</span> <span class="stReserved">final</span> <span class="stReserved">val</span> sb = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>
 *   <span class="stReserved">def</span> !(op: <span class="stType">StringOp</span>): <span class="stType">Unit</span> =
 *     synchronized {
 *       op <span class="stReserved">match</span> {
 *         <span class="stReserved">case</span> <span class="stType">Append</span>(value) =&gt; sb.append(value)
 *         <span class="stReserved">case</span> <span class="stType">Clear</span> =&gt; sb.clear()
 *       }
 *     }
 *   <span class="stReserved">def</span> ?(get: GetValue.type)(<span class="stReserved">implicit</span> c: <span class="stType">ExecutionContext</span>): <span class="stType">Future[String]</span> =
 *     <span class="stType">Future</span> {
 *       synchronized { sb.toString }
 *     }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">StringBufferActor</span> {
 *   <span class="stReserved">private</span> <span class="stReserved">final</span> <span class="stReserved">val</span> buf = ListBuffer.empty[<span class="stType">String</span>]
 *   <span class="stReserved">def</span> !(op: <span class="stType">StringOp</span>): <span class="stType">Unit</span> =
 *     synchronized {
 *       op <span class="stReserved">match</span> {
 *         <span class="stReserved">case</span> <span class="stType">Append</span>(value) =&gt; buf += value
 *         <span class="stReserved">case</span> <span class="stType">Clear</span> =&gt; buf.clear()
 *       }
 *     }
 *   <span class="stReserved">def</span> ?(get: GetValue.type)(<span class="stReserved">implicit</span> c: <span class="stType">ExecutionContext</span>): <span class="stType">Future[List[String]]</span> =
 *     <span class="stType">Future</span> {
 *       synchronized { buf.toList }
 *     }
 * }
 * <br/><span class="stReserved">trait</span> <span class="stType">Builder</span> <span class="stReserved">extends</span> <span class="stType">AsyncTestSuiteMixin</span> { <span class="stReserved">this</span>: <span class="stType">AsyncTestSuite</span> =&gt;
 * <br/>  <span class="stReserved">final</span> <span class="stReserved">val</span> builderActor = <span class="stReserved">new</span> <span class="stType">StringBuilderActor</span>
 * <br/>  <span class="stReserved">abstract</span> <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgAsyncTest</span>) = {
 *     builderActor ! <span class="stType">Append</span>(<span class="stQuotedString">"ScalaTest is "</span>)
 *     complete {
 *       <span class="stReserved">super</span>.withFixture(test) <span class="stLineComment">// To be stackable, must call super.withFixture</span>
 *     } lastly {
 *       builderActor ! <span class="stType">Clear</span>
 *     }
 *   }
 * }
 * <br/><span class="stReserved">trait</span> <span class="stType">Buffer</span> <span class="stReserved">extends</span> <span class="stType">AsyncTestSuiteMixin</span> { <span class="stReserved">this</span>: <span class="stType">AsyncTestSuite</span> =&gt;
 * <br/>  <span class="stReserved">final</span> <span class="stReserved">val</span> bufferActor = <span class="stReserved">new</span> <span class="stType">StringBufferActor</span>
 * <br/>  <span class="stReserved">abstract</span> <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgAsyncTest</span>) = {
 *     complete {
 *       <span class="stReserved">super</span>.withFixture(test) <span class="stLineComment">// To be stackable, must call super.withFixture</span>
 *     } lastly {
 *       bufferActor ! <span class="stType">Clear</span>
 *     }
 *   }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">flatspec.AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">Builder</span> <span class="stReserved">with</span> <span class="stType">Buffer</span> {
 * <br/>  <span class="stQuotedString">"Testing"</span> should <span class="stQuotedString">"be easy"</span> in {
 *     builderActor ! <span class="stType">Append</span>(<span class="stQuotedString">"easy!"</span>)
 *     <span class="stReserved">val</span> futureString = builderActor ? <span class="stType">GetValue</span>
 *     <span class="stReserved">val</span> futureList = bufferActor ? <span class="stType">GetValue</span>
 *     <span class="stReserved">val</span> futurePair: <span class="stType">Future[(String, List[String])]</span> = futureString zip futureList
 *     futurePair map { <span class="stReserved">case</span> (str, lst) =&gt;
 *       assert(str == <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *       assert(lst.isEmpty)
 *       bufferActor ! <span class="stType">Append</span>(<span class="stQuotedString">"sweet"</span>)
 *       succeed
 *     }
 *   }
 * <br/>  it should <span class="stQuotedString">"be fun"</span> in {
 *     builderActor ! <span class="stType">Append</span>(<span class="stQuotedString">"fun!"</span>)
 *     <span class="stReserved">val</span> futureString = builderActor ? <span class="stType">GetValue</span>
 *     <span class="stReserved">val</span> futureList = bufferActor ? <span class="stType">GetValue</span>
 *     <span class="stReserved">val</span> futurePair: <span class="stType">Future[(String, List[String])]</span> = futureString zip futureList
 *     futurePair map { <span class="stReserved">case</span> (str, lst) =&gt;
 *       assert(str == <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *       assert(lst.isEmpty)
 *       bufferActor ! <span class="stType">Append</span>(<span class="stQuotedString">"awesome"</span>)
 *       succeed
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * By mixing in both the <code>Builder</code> and <code>Buffer</code> traits, <code>ExampleSpec</code> gets both fixtures, which will be
 * initialized before each test and cleaned up after. The order the traits are mixed together determines the order of execution.
 * In this case, <code>Builder</code> is &ldquo;super&rdquo; to <code>Buffer</code>. If you wanted <code>Buffer</code> to be &ldquo;super&rdquo;
 * to <code>Builder</code>, you need only switch the order you mix them together, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">class</span> <span class="stType">Example2Spec</span> <span class="stReserved">extends</span> <span class="stType">flatspec.AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">Buffer</span> <span class="stReserved">with</span> <span class="stType">Builder</span>
 * </pre>
 *
 * <p>
 * If you only need one fixture you mix in only that trait:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">class</span> <span class="stType">Example3Spec</span> <span class="stReserved">extends</span> <span class="stType">flatspec.AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">Builder</span>
 * </pre>
 *
 * <p>
 * Another way to create stackable fixture traits is by extending the <a href="BeforeAndAfterEach.html"><code>BeforeAndAfterEach</code></a>
 * and/or <a href="BeforeAndAfterAll.html"><code>BeforeAndAfterAll</code></a> traits.
 * <code>BeforeAndAfterEach</code> has a <code>beforeEach</code> method that will be run before each test (like JUnit's <code>setUp</code>),
 * and an <code>afterEach</code> method that will be run after (like JUnit's <code>tearDown</code>).
 * Similarly, <code>BeforeAndAfterAll</code> has a <code>beforeAll</code> method that will be run before all tests,
 * and an <code>afterAll</code> method that will be run after all tests. Here's what the previously shown example would look like if it
 * were rewritten to use the <code>BeforeAndAfterEach</code> methods instead of <code>withFixture</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.composingbeforeandaftereach
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> org.scalatest.BeforeAndAfterEach
 * <span class="stReserved">import</span> collection.mutable.ListBuffer
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <span class="stReserved">import</span> scala.concurrent.ExecutionContext
 * <br/><span class="stLineComment">// Defining actor messages</span>
 * <span class="stReserved">sealed</span> <span class="stReserved">abstract</span> <span class="stReserved">class</span> <span class="stType">StringOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">object</span> <span class="stType">Clear</span> <span class="stReserved">extends</span> <span class="stType">StringOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">Append</span>(value: <span class="stType">String</span>) <span class="stReserved">extends</span> <span class="stType">StringOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">object</span> <span class="stType">GetValue</span>
 * <br/><span class="stReserved">class</span> <span class="stType">StringBuilderActor</span> { <span class="stLineComment">// Simulating an actor</span>
 *   <span class="stReserved">private</span> <span class="stReserved">final</span> <span class="stReserved">val</span> sb = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>
 *   <span class="stReserved">def</span> !(op: <span class="stType">StringOp</span>): <span class="stType">Unit</span> =
 *     synchronized {
 *       op <span class="stReserved">match</span> {
 *         <span class="stReserved">case</span> <span class="stType">Append</span>(value) =&gt; sb.append(value)
 *         <span class="stReserved">case</span> <span class="stType">Clear</span> =&gt; sb.clear()
 *       }
 *     }
 *   <span class="stReserved">def</span> ?(get: GetValue.type)(<span class="stReserved">implicit</span> c: <span class="stType">ExecutionContext</span>): <span class="stType">Future[String]</span> =
 *     <span class="stType">Future</span> {
 *       synchronized { sb.toString }
 *     }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">StringBufferActor</span> {
 *   <span class="stReserved">private</span> <span class="stReserved">final</span> <span class="stReserved">val</span> buf = ListBuffer.empty[<span class="stType">String</span>]
 *   <span class="stReserved">def</span> !(op: <span class="stType">StringOp</span>): <span class="stType">Unit</span> =
 *     synchronized {
 *       op <span class="stReserved">match</span> {
 *         <span class="stReserved">case</span> <span class="stType">Append</span>(value) =&gt; buf += value
 *         <span class="stReserved">case</span> <span class="stType">Clear</span> =&gt; buf.clear()
 *       }
 *     }
 *   <span class="stReserved">def</span> ?(get: GetValue.type)(<span class="stReserved">implicit</span> c: <span class="stType">ExecutionContext</span>): <span class="stType">Future[List[String]]</span> =
 *     <span class="stType">Future</span> {
 *       synchronized { buf.toList }
 *     }
 * }
 * <br/><span class="stReserved">trait</span> <span class="stType">Builder</span> <span class="stReserved">extends</span> <span class="stType">BeforeAndAfterEach</span> { <span class="stReserved">this</span>: <span class="stType">Suite</span> =&gt;
 * <br/>  <span class="stReserved">final</span> <span class="stReserved">val</span> builderActor = <span class="stReserved">new</span> <span class="stType">StringBuilderActor</span>
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> beforeEach() {
 *     builderActor ! <span class="stType">Append</span>(<span class="stQuotedString">"ScalaTest is "</span>)
 *     <span class="stReserved">super</span>.beforeEach() <span class="stLineComment">// To be stackable, must call super.beforeEach</span>
 *   }
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> afterEach() {
 *     <span class="stReserved">try</span> <span class="stReserved">super</span>.afterEach() <span class="stLineComment">// To be stackable, must call super.afterEach</span>
 *     <span class="stReserved">finally</span> builderActor ! <span class="stType">Clear</span>
 *   }
 * }
 * <br/><span class="stReserved">trait</span> <span class="stType">Buffer</span> <span class="stReserved">extends</span> <span class="stType">BeforeAndAfterEach</span> { <span class="stReserved">this</span>: <span class="stType">Suite</span> =&gt;
 * <br/>  <span class="stReserved">final</span> <span class="stReserved">val</span> bufferActor = <span class="stReserved">new</span> <span class="stType">StringBufferActor</span>
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> afterEach() {
 *     <span class="stReserved">try</span> <span class="stReserved">super</span>.afterEach() <span class="stLineComment">// To be stackable, must call super.afterEach</span>
 *     <span class="stReserved">finally</span> bufferActor ! <span class="stType">Clear</span>
 *   }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">flatspec.AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">Builder</span> <span class="stReserved">with</span> <span class="stType">Buffer</span> {
 * <br/>  <span class="stQuotedString">"Testing"</span> should <span class="stQuotedString">"be easy"</span> in {
 *     builderActor ! <span class="stType">Append</span>(<span class="stQuotedString">"easy!"</span>)
 *     <span class="stReserved">val</span> futureString = builderActor ? <span class="stType">GetValue</span>
 *     <span class="stReserved">val</span> futureList = bufferActor ? <span class="stType">GetValue</span>
 *     <span class="stReserved">val</span> futurePair: <span class="stType">Future[(String, List[String])]</span> = futureString zip futureList
 *     futurePair map { <span class="stReserved">case</span> (str, lst) =&gt;
 *       assert(str == <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *       assert(lst.isEmpty)
 *       bufferActor ! <span class="stType">Append</span>(<span class="stQuotedString">"sweet"</span>)
 *       succeed
 *     }
 *   }
 * <br/>  it should <span class="stQuotedString">"be fun"</span> in {
 *     builderActor ! <span class="stType">Append</span>(<span class="stQuotedString">"fun!"</span>)
 *     <span class="stReserved">val</span> futureString = builderActor ? <span class="stType">GetValue</span>
 *     <span class="stReserved">val</span> futureList = bufferActor ? <span class="stType">GetValue</span>
 *     <span class="stReserved">val</span> futurePair: <span class="stType">Future[(String, List[String])]</span> = futureString zip futureList
 *     futurePair map { <span class="stReserved">case</span> (str, lst) =&gt;
 *       assert(str == <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *       assert(lst.isEmpty)
 *       bufferActor ! <span class="stType">Append</span>(<span class="stQuotedString">"awesome"</span>)
 *       succeed
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * To get the same ordering as <code>withFixture</code>, place your <code>super.beforeEach</code> call at the end of each
 * <code>beforeEach</code> method, and the <code>super.afterEach</code> call at the beginning of each <code>afterEach</code>
 * method, as shown in the previous example. It is a good idea to invoke <code>super.afterEach</code> in a <code>try</code>
 * block and perform cleanup in a <code>finally</code> clause, as shown in the previous example, because this ensures the
 * cleanup code is performed even if <code>super.afterEach</code> throws an exception.
 * </p>
 *
 * <p>
 * The difference between stacking traits that extend <code>BeforeAndAfterEach</code> versus traits that implement <code>withFixture</code> is
 * that setup and cleanup code happens before and after the test in <code>BeforeAndAfterEach</code>, but at the beginning and
 * end of the test in <code>withFixture</code>. Thus if a <code>withFixture</code> method completes abruptly with an exception, it is
 * considered a failed test. By contrast, if any of the <code>beforeEach</code> or <code>afterEach</code> methods of <code>BeforeAndAfterEach</code>
 * complete abruptly, it is considered an aborted suite, which will result in a <a href="events/SuiteAborted.html"><code>SuiteAborted</code></a> event.
 * </p>
 *
 * <a name="sharedTests"></a><h2>Shared tests</h2>
 *
 * <p>
 * Sometimes you may want to run the same test code on different fixture objects. In other words, you may want to write tests that are "shared"
 * by different fixture objects.
 * To accomplish this in an <code>AsyncFlatSpec</code>, you first place shared tests in
 * <em>behavior functions</em>. These behavior functions will be
 * invoked during the construction phase of any <code>AsyncFlatSpec</code> that uses them, so that the tests they contain will
 * be registered as tests in that <code>AsyncFlatSpec</code>.
 * For example, given this <code>StackActor</code> class:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.asyncflatspec.sharedtests
 * <br/><span class="stReserved">import</span> scala.collection.mutable.ListBuffer
 * <span class="stReserved">import</span> scala.concurrent.Future
 * <span class="stReserved">import</span> scala.concurrent.ExecutionContext
 * <br/><span class="stLineComment">// Stack operations</span>
 * <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">Push[T]</span>(value: T)
 * <span class="stReserved">sealed</span> <span class="stReserved">abstract</span> <span class="stReserved">class</span> <span class="stType">StackOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">object</span> <span class="stType">Pop</span> <span class="stReserved">extends</span> <span class="stType">StackOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">object</span> <span class="stType">Peek</span> <span class="stReserved">extends</span> <span class="stType">StackOp</span>
 * <span class="stReserved">case</span> <span class="stReserved">object</span> <span class="stType">Size</span> <span class="stReserved">extends</span> <span class="stType">StackOp</span>
 * <br/><span class="stLineComment">// Stack info</span>
 * <span class="stReserved">case</span> <span class="stReserved">class</span> <span class="stType">StackInfo[T]</span>(top: <span class="stType">Option[T]</span>, size: <span class="stType">Int</span>, max: <span class="stType">Int</span>) {
 *   require(size &gt; <span class="stLiteral">0</span>, <span class="stQuotedString">"size was less than zero"</span>)
 *   require(max &gt; size, <span class="stQuotedString">"max was less than size"</span>)
 *   <span class="stReserved">val</span> isFull: <span class="stType">Boolean</span> = size == max
 *   <span class="stReserved">val</span> isEmpty: <span class="stType">Boolean</span> = size == <span class="stLiteral">0</span>
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">StackActor[T]</span>(<span class="stType">Max</span>: <span class="stType">Int</span>, name: <span class="stType">String</span>) {
 * <br/>  <span class="stReserved">private</span> <span class="stReserved">final</span> <span class="stReserved">val</span> buf = <span class="stReserved">new</span> <span class="stType">ListBuffer[T]</span>
 * <br/>  <span class="stReserved">def</span> !(push: <span class="stType">Push[T]</span>): <span class="stType">Unit</span> =
 *     synchronized {
 *       <span class="stReserved">if</span> (buf.size != <span class="stType">Max</span>)
 *         buf.prepend(push.value)
 *       <span class="stReserved">else</span>
 *         <span class="stReserved">throw</span> <span class="stReserved">new</span> <span class="stType">IllegalStateException</span>(<span class="stQuotedString">"can't push onto a full stack"</span>)
 *     }
 * <br/>  <span class="stReserved">def</span> ?(op: <span class="stType">StackOp</span>)(<span class="stReserved">implicit</span> c: <span class="stType">ExecutionContext</span>): <span class="stType">Future[StackInfo[T]]</span> =
 *     synchronized {
 *       op <span class="stReserved">match</span> {
 *         <span class="stReserved">case</span> <span class="stType">Pop</span> =&gt;
 *           <span class="stType">Future</span> {
 *             <span class="stReserved">if</span> (buf.size != <span class="stLiteral">0</span>)
 *               <span class="stType">StackInfo</span>(<span class="stType">Some</span>(buf.remove(<span class="stLiteral">0</span>)), buf.size, <span class="stType">Max</span>)
 *             <span class="stReserved">else</span>
 *               <span class="stReserved">throw</span> <span class="stReserved">new</span> <span class="stType">IllegalStateException</span>(<span class="stQuotedString">"can't pop an empty stack"</span>)
 *           }
 *         <span class="stReserved">case</span> <span class="stType">Peek</span> =&gt;
 *           <span class="stType">Future</span> {
 *             <span class="stReserved">if</span> (buf.size != <span class="stLiteral">0</span>)
 *               <span class="stType">StackInfo</span>(<span class="stType">Some</span>(buf(<span class="stLiteral">0</span>)), buf.size, <span class="stType">Max</span>)
 *             <span class="stReserved">else</span>
 *               <span class="stReserved">throw</span> <span class="stReserved">new</span> <span class="stType">IllegalStateException</span>(<span class="stQuotedString">"can't peek an empty stack"</span>)
 *           }
 *         <span class="stReserved">case</span> <span class="stType">Size</span> =&gt;
 *           <span class="stType">Future</span> { <span class="stType">StackInfo</span>(<span class="stType">None</span>, buf.size, <span class="stType">Max</span>) }
 *       }
 *     }
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> toString: <span class="stType">String</span> = name
 * }
 * </pre>
 *
 * <p>
 * You may want to test the stack represented by the <code>StackActor</code> class in different states: empty, full, with one item, with one item less than capacity,
 * <em>etc</em>. You may find you have several tests that make sense any time the stack is non-empty. Thus you'd ideally want to run
 * those same tests for three stack fixture objects: a full stack, a stack with a one item, and a stack with one item less than
 * capacity. With shared tests, you can factor these tests out into a behavior function, into which you pass the
 * stack fixture to use when running the tests. So in your <code>AsyncFlatSpec</code> for <code>StackActor</code>, you'd invoke the
 * behavior function three times, passing in each of the three stack fixtures so that the shared tests are run for all three fixtures.
 * </p>
 *
 * <p>
 * You can define a behavior function that encapsulates these shared tests inside the <code>AsyncFlatSpec</code> that uses them. If they are shared
 * between different <code>AsyncFlatSpec</code>s, however, you could also define them in a separate trait that is mixed into
 * each <code>AsyncFlatSpec</code> that uses them.
 * <a name="StackBehaviors">For</a> example, here the <code>nonEmptyStackActor</code> behavior function (in this case, a
 * behavior <em>method</em>) is defined in a trait along with another
 * method containing shared tests for non-full stacks:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest.AsyncFlatSpec
 * <br/><span class="stReserved">trait</span> <span class="stType">AsyncFlatSpecStackBehaviors</span> { <span class="stReserved">this</span>: <span class="stType">AsyncFlatSpec</span> =&gt;
 * <br/>  <span class="stReserved">def</span> nonEmptyStackActor(createNonEmptyStackActor: =&gt; <span class="stType">StackActor[Int]</span>,
 *         lastItemAdded: <span class="stType">Int</span>, name: <span class="stType">String</span>): <span class="stType">Unit</span> = {
 * <br/>    it should (<span class="stQuotedString">"return non-empty StackInfo when Size is fired at non-empty stack actor: "</span> + name) in {
 *       <span class="stReserved">val</span> stackActor = createNonEmptyStackActor
 *       <span class="stReserved">val</span> futureStackInfo = stackActor ? <span class="stType">Size</span>
 *       futureStackInfo map { stackInfo =&gt;
 *         assert(!stackInfo.isEmpty)
 *       }
 *     }
 * <br/>    it should (<span class="stQuotedString">"return before and after StackInfo that has existing size and lastItemAdded as top when Peek is fired at non-empty stack actor: "</span> + name) in {
 *       <span class="stReserved">val</span> stackActor = createNonEmptyStackActor
 *       <span class="stReserved">val</span> futurePair: <span class="stType">Future[(StackInfo[Int], StackInfo[Int])]</span> =
 *         <span class="stReserved">for</span> {
 *           beforePeek &lt;- stackActor ? <span class="stType">Size</span>
 *           afterPeek &lt;- stackActor ? <span class="stType">Peek</span>
 *         } <span class="stReserved">yield</span> (beforePeek, afterPeek)
 *       futurePair map { <span class="stReserved">case</span> (beforePeek, afterPeek) =&gt;
 *         assert(afterPeek.top == <span class="stType">Some</span>(lastItemAdded))
 *         assert(afterPeek.size == beforePeek.size)
 *       }
 *     }
 * <br/>    it should (<span class="stQuotedString">"return before and after StackInfo that has existing size - 1 and lastItemAdded as top when Pop is fired at non-empty stack actor: "</span> + name) in {
 *       <span class="stReserved">val</span> stackActor = createNonEmptyStackActor
 *       <span class="stReserved">val</span> futurePair: <span class="stType">Future[(StackInfo[Int], StackInfo[Int])]</span> =
 *         <span class="stReserved">for</span> {
 *           beforePop &lt;- stackActor ? <span class="stType">Size</span>
 *           afterPop &lt;- stackActor ? <span class="stType">Pop</span>
 *         } <span class="stReserved">yield</span> (beforePop, afterPop)
 *       futurePair map { <span class="stReserved">case</span> (beforePop, afterPop) =&gt;
 *         assert(afterPop.top == <span class="stType">Some</span>(lastItemAdded))
 *         assert(afterPop.size == beforePop.size - <span class="stLiteral">1</span>)
 *       }
 *     }
 *   }
 * <br/>  <span class="stReserved">def</span> nonFullStackActor(createNonFullStackActor: =&gt; <span class="stType">StackActor[Int]</span>, name: <span class="stType">String</span>): <span class="stType">Unit</span> = {
 * <br/>    it should (<span class="stQuotedString">"return non-full StackInfo when Size is fired at non-full stack actor: "</span> + name) in {
 *       <span class="stReserved">val</span> stackActor = createNonFullStackActor
 *       <span class="stReserved">val</span> futureStackInfo = stackActor ? <span class="stType">Size</span>
 *       futureStackInfo map { stackInfo =&gt;
 *         assert(!stackInfo.isFull)
 *       }
 *     }
 * <br/>    it should (<span class="stQuotedString">"return before and after StackInfo that has existing size + 1 and new item as top when Push is fired at non-full stack actor: "</span> + name) in {
 *       <span class="stReserved">val</span> stackActor = createNonFullStackActor
 *       <span class="stReserved">val</span> futurePair: <span class="stType">Future[(StackInfo[Int], StackInfo[Int])]</span> =
 *         <span class="stReserved">for</span> {
 *           beforePush &lt;- stackActor ? <span class="stType">Size</span>
 *           afterPush &lt;- { stackActor ! <span class="stType">Push</span>(<span class="stLiteral">7</span>); stackActor ? <span class="stType">Peek</span> }
 *         } <span class="stReserved">yield</span> (beforePush, afterPush)
 *       futurePair map { <span class="stReserved">case</span> (beforePush, afterPush) =&gt;
 *         assert(afterPush.top == <span class="stType">Some</span>(<span class="stLiteral">7</span>))
 *         assert(afterPush.size == beforePush.size + <span class="stLiteral">1</span>)
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * Given these behavior functions, you could invoke them directly, but <code>AsyncFlatSpec</code> offers a DSL for the purpose,
 * which looks like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * it should behave like nonEmptyStackActor(almostEmptyStackActor, <span class="stType">LastValuePushed</span>, almostEmptyStackActorName)
 * it should behave like nonFullStackActor(almostEmptyStackActor, almostEmptyStackActorName)
 * </pre>
 *
 * <p>
 * Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">class</span> <span class="stType">StackSpec</span> <span class="stReserved">extends</span> <span class="stType">AsyncFlatSpec</span> <span class="stReserved">with</span> <span class="stType">AsyncFlatSpecStackBehaviors</span> {
 * <br/>  <span class="stReserved">val</span> <span class="stType">Max</span> = <span class="stLiteral">10</span>
 *   <span class="stReserved">val</span> <span class="stType">LastValuePushed</span> = <span class="stType">Max</span> - <span class="stLiteral">1</span>
 * <br/>  <span class="stLineComment">// Stack fixture creation methods</span>
 *   <span class="stReserved">val</span> emptyStackActorName = <span class="stQuotedString">"empty stack actor"</span>
 *   <span class="stReserved">def</span> emptyStackActor = <span class="stReserved">new</span> <span class="stType">StackActor[Int]</span>(<span class="stType">Max</span>, emptyStackActorName )
 * <br/>  <span class="stReserved">val</span> fullStackActorName = <span class="stQuotedString">"full stack actor"</span>
 *   <span class="stReserved">def</span> fullStackActor = {
 *     <span class="stReserved">val</span> stackActor = <span class="stReserved">new</span> <span class="stType">StackActor[Int]</span>(<span class="stType">Max</span>, fullStackActorName )
 *     <span class="stReserved">for</span> (i <- <span class="stLiteral">0</span> until <span class="stType">Max</span>)
 *       stackActor ! <span class="stType">Push</span>(i)
 *     stackActor
 *   }
 * <br/>  <span class="stReserved">val</span> almostEmptyStackActorName = <span class="stQuotedString">"almost empty stack actor"</span>
 *   <span class="stReserved">def</span> almostEmptyStackActor = {
 *     <span class="stReserved">val</span> stackActor = <span class="stReserved">new</span> <span class="stType">StackActor[Int]</span>(<span class="stType">Max</span>, almostEmptyStackActorName )
 *     stackActor ! <span class="stType">Push</span>(<span class="stType">LastValuePushed</span>)
 *     stackActor
 *   }
 * <br/>  <span class="stReserved">val</span> almostFullStackActorName = <span class="stQuotedString">"almost full stack actor"</span>
 *   <span class="stReserved">def</span> almostFullStackActor = {
 *     <span class="stReserved">val</span> stackActor = <span class="stReserved">new</span> <span class="stType">StackActor[Int]</span>(<span class="stType">Max</span>, almostFullStackActorName)
 *     <span class="stReserved">for</span> (i <- <span class="stLiteral">1</span> to <span class="stType">LastValuePushed</span>)
 *       stackActor ! <span class="stType">Push</span>(i)
 *     stackActor
 *   }
 * <br/>  <span class="stQuotedString">"A Stack actor (when empty)"</span> should <span class="stQuotedString">"return empty StackInfo when Size is fired at it"</span> in {
 *     <span class="stReserved">val</span> stackActor = emptyStackActor
 *     <span class="stReserved">val</span> futureStackInfo = stackActor ? <span class="stType">Size</span>
 *     futureStackInfo map { stackInfo =>
 *       assert(stackInfo.isEmpty)
 *     }
 *   }
 * <br/>  it should <span class="stQuotedString">"complain when Peek is fired at it"</span> in {
 *     recoverToSucceededIf[<span class="stType">IllegalStateException</span>] {
 *       emptyStackActor ? <span class="stType">Peek</span>
 *     }
 *   }
 * <br/>  it should <span class="stQuotedString">"complain when Pop is fired at it"</span> in {
 *     recoverToSucceededIf[<span class="stType">IllegalStateException</span>] {
 *       emptyStackActor ? <span class="stType">Pop</span>
 *     }
 *   }
 * <br/>  <span class="stQuotedString">"A Stack actor (when non-empty)"</span> should behave like nonEmptyStackActor(almostEmptyStackActor, <span class="stType">LastValuePushed</span>, almostEmptyStackActorName)
 * <br/>  it should behave like nonFullStackActor(almostEmptyStackActor, almostEmptyStackActorName)
 * <br/>  it should behave like nonEmptyStackActor(almostFullStackActor, <span class="stType">LastValuePushed</span>, almostFullStackActorName)
 * <br/>  it should behave like nonFullStackActor(almostFullStackActor, almostFullStackActorName)
 * <br/>  <span class="stQuotedString">"A Stack actor (when full)"</span> should <span class="stQuotedString">"return full StackInfo when Size is fired at it"</span> in {
 *     <span class="stReserved">val</span> stackActor = fullStackActor
 *     <span class="stReserved">val</span> futureStackInfo = stackActor ? <span class="stType">Size</span>
 *     futureStackInfo map { stackInfo =>
 *       assert(stackInfo.isFull)
 *     }
 *   }
 * <br/>  it should behave like nonEmptyStackActor(fullStackActor, <span class="stType">LastValuePushed</span>, fullStackActorName)
 * <br/>  it should <span class="stQuotedString">"complain when Push is fired at it"</span> in {
 *     <span class="stReserved">val</span> stackActor = fullStackActor
 *     assertThrows[<span class="stType">IllegalStateException</span>] {
 *       stackActor ! <span class="stType">Push</span>(<span class="stLiteral">10</span>)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * If you load these classes into the Scala interpreter (with scalatest's JAR file on the class path), and execute it,
 * you'll see:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new StackSpec)
 * <span class="stGreen">StackSpec:
 * A Stack actor (when empty)
 * - should return empty StackInfo when Size is fired at it
 * - should complain when Peek is fired at it
 * - should complain when Pop is fired at it
 * A Stack actor (when non-empty)
 * - should return non-empty StackInfo when Size is fired at non-empty stack actor: almost empty stack actor
 * - should return before and after StackInfo that has existing size and lastItemAdded as top when Peek is fired at non-empty stack actor: almost empty stack actor
 * - should return before and after StackInfo that has existing size - 1 and lastItemAdded as top when Pop is fired at non-empty stack actor: almost empty stack actor
 * - should return non-full StackInfo when Size is fired at non-full stack actor: almost empty stack actor
 * - should return before and after StackInfo that has existing size + 1 and new item as top when Push is fired at non-full stack actor: almost empty stack actor
 * - should return non-empty StackInfo when Size is fired at non-empty stack actor: almost full stack actor
 * - should return before and after StackInfo that has existing size and lastItemAdded as top when Peek is fired at non-empty stack actor: almost full stack actor
 * - should return before and after StackInfo that has existing size - 1 and lastItemAdded as top when Pop is fired at non-empty stack actor: almost full stack actor
 * - should return non-full StackInfo when Size is fired at non-full stack actor: almost full stack actor
 * - should return before and after StackInfo that has existing size + 1 and new item as top when Push is fired at non-full stack actor: almost full stack actor
 * A Stack actor (when full)
 * - should return full StackInfo when Size is fired at it
 * - should return non-empty StackInfo when Size is fired at non-empty stack actor: full stack actor
 * - should return before and after StackInfo that has existing size and lastItemAdded as top when Peek is fired at non-empty stack actor: full stack actor
 * - should return before and after StackInfo that has existing size - 1 and lastItemAdded as top when Pop is fired at non-empty stack actor: full stack actor
 * - should complain when Push is fired at it</span>
 * </pre>
 *
 * <p>
 * One thing to keep in mind when using shared tests is that in ScalaTest, each test in a suite must have a unique name.
 * If you register the same tests repeatedly in the same suite, one problem you may encounter is an exception at runtime
 * complaining that multiple tests are being registered with the same test name.
 * Although in an <code>AsyncFlatSpec</code>, the <code>behavior of</code> clause is a nesting construct analogous to
 * <code>AsyncFunSpec</code>'s <code>describe</code> clause, you many sometimes need to do a bit of
 * extra work to ensure that the test names are unique. If a duplicate test name problem shows up in an
 * <code>AsyncFlatSpec</code>, you'll need to pass in a prefix or suffix string to add to each test name. You can call
 * <code>toString</code> on the shared fixture object, or pass this string
 * the same way you pass any other data needed by the shared tests.
 * This is the approach taken by the previous <code>AsyncFlatSpecStackBehaviors</code> example.
 * </p>
 *
 * <p>
 * Given this <code>AsyncFlatSpecStackBehaviors</code> trait, calling it with the <code>almostEmptyStackActor</code> fixture, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stQuotedString">"A Stack actor (when non-empty)"</span> should behave like nonEmptyStackActor(almostEmptyStackActor, <span class="stType">LastValuePushed</span>, almostEmptyStackActorName)
 * </pre>
 *
 * <p>
 * yields test names:
 * </p>
 *
 * <ul>
 * <li><code>A Stack actor (when non-empty) should return non-empty StackInfo when Size is fired at non-empty stack actor: almost empty stack actor</code></li>
 * <li><code>A Stack actor (when non-empty) should return before and after StackInfo that has existing size and lastItemAdded as top when Peek is fired at non-empty stack actor: almost empty stack actor</code></li>
 * <li><code>A Stack actor (when non-empty) should return before and after StackInfo that has existing size - 1 and lastItemAdded as top when Pop is fired at non-empty stack actor: almost empty stack actor</code></li>
 * </ul>
 *
 * <p>
 * Whereas calling it with the <code>almostFullStackActor</code> fixture, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * it should behave like nonEmptyStackActor(almostFullStackActor, <span class="stType">LastValuePushed</span>, almostFullStackActorName)
 * </pre>
 *
 * <p>
 * yields different test names:
 * </p>
 *
 * <ul>
 * <li><code>A Stack actor (when non-empty) should return non-empty StackInfo when Size is fired at non-empty stack actor: almost full stack actor</code></li>
 * <li><code>A Stack actor (when non-empty) should return before and after StackInfo that has existing size and lastItemAdded as top when Peek is fired at non-empty stack actor: almost full stack actor</code></li>
 * <li><code>A Stack actor (when non-empty) should return before and after StackInfo that has existing size - 1 and lastItemAdded as top when Pop is fired at non-empty stack actor: almost full stack actor</code></li>
 * </ul>
 */
abstract class AsyncFlatSpec extends AsyncFlatSpecLike {

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
