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

import org.scalatest.{Suite, Finders}

/**
 * Facilitates a &ldquo;behavior-driven&rdquo; style of development (BDD), in which tests
 * are combined with text that specifies the behavior the tests verify.
 * 
 * <table><tr><td class="usage">
 * <strong>Recommended Usage</strong>:
 * For teams coming from Ruby's RSpec tool, <code>AnyFunSpec</code> will feel familiar and comfortable; More generally, for any team that prefers BDD, <code>AnyFunSpec</code>'s nesting
 * and gentle guide to structuring text (with <code>describe</code> and <code>it</code>) provide an excellent general-purpose choice for writing specification-style tests. 
 * </td></tr></table>
 * 
 * <p>
 * Here's an example <code>AnyFunSpec</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec
 * <br/><span class="stReserved">import</span> org.scalatest.funspec.AnyFunSpec
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> {
 * <br/>  describe(<span class="stQuotedString">"A Set"</span>) {
 *     describe(<span class="stQuotedString">"when empty"</span>) {
 *       it(<span class="stQuotedString">"should have size 0"</span>) {
 *         assert(Set.empty.size === <span class="stLiteral">0</span>)
 *       }
 * <br/>      it(<span class="stQuotedString">"should produce NoSuchElementException when head is invoked"</span>) {
 *         assertThrows[<span class="stType">NoSuchElementException</span>] {
 *           Set.empty.head
 *         }
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * A <code>AnyFunSpec</code> contains <em>describe clauses</em> and tests. You define a describe clause
 * with <code>describe</code>, and a test with either <code>it</code> or <code>they</code>. 
 * <code>describe</code>,  <code>it</code>, and <code>they</code> are methods, defined in
 * <code>AnyFunSpec</code>, which will be invoked
 * by the primary constructor of <code>SetSpec</code>. 
 * A describe clause names, or gives more information about, the <em>subject</em> (class or other entity) you are specifying
 * and testing. In the previous example, <code>"A Set"</code>
 * is the subject under specification and test. With each test you provide a string (the <em>spec text</em>) that specifies
 * one bit of behavior of the subject, and a block of code that tests that behavior.
 * You place the spec text between the parentheses, followed by the test code between curly
 * braces.  The test code will be wrapped up as a function passed as a by-name parameter to
 * <code>it</code> (or <code>they</code>), which will register the test for later execution.
 * </p>
 *
 * <p>
 * Note: the <code>they</code> method is intended for use when the subject is plural, for example:
 * </p>
 *
 * <pre class="stHighlighted">
 * describe(<span class="stQuotedString">"The combinators"</span>) {
 *   they(<span class="stQuotedString">"should be easy to learn"</span>) {}
 *   they(<span class="stQuotedString">"should be efficient"</span>) {}
 *   they(<span class="stQuotedString">"should do something cool"</span>) {}
 * }
 * </pre>
 *
 * <p>
 * A <code>AnyFunSpec</code>'s lifecycle has two phases: the <em>registration</em> phase and the
 * <em>ready</em> phase. It starts in registration phase and enters ready phase the first time
 * <code>run</code> is called on it. It then remains in ready phase for the remainder of its lifetime.
 * </p>
 *
 * <p>
 * Tests can only be registered with the <code>it</code> or <code>they</code> methods while the <code>AnyFunSpec</code> is
 * in its registration phase. Any attempt to register a test after the <code>AnyFunSpec</code> has
 * entered its ready phase, <em>i.e.</em>, after <code>run</code> has been invoked on the <code>AnyFunSpec</code>,
 * will be met with a thrown <code>TestRegistrationClosedException</code>. The recommended style
 * of using <code>AnyFunSpec</code> is to register tests during object construction as is done in all
 * the examples shown here. If you keep to the recommended style, you should never see a
 * <code>TestRegistrationClosedException</code>.
 * </p>
 *
 * <p>
 * When you execute a <code>AnyFunSpec</code>, it will send <code>Formatter</code>s in the events it sends to the
 * <code>Reporter</code>. ScalaTest's built-in reporters will report these events in such a way
 * that the output is easy to read as an informal specification of the <em>subject</em> being tested.
 * For example, were you to run <code>SetSpec</code> from within the Scala interpreter:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new SetSpec)
 * </pre>
 *
 * <p>
 * You would see:
 * </p>
 *
 * <pre class="stREPL">
 * <span class="stGreen">A Set</span>
 * <span class="stGreen">  when empty</span>
 * <span class="stGreen">  - should have size 0</span>
 * <span class="stGreen">  - should produce NoSuchElementException when head is invoked</span>
 * </pre>
 *
 * <p>
 * Or, to run just the &ldquo;<code>A Set when empty should have size 0</code>&rdquo; test, you could pass that test's name, or any unique substring of the
 * name, such as <code>"size 0"</code> or even just <code>"0"</code>. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new SetSuite, "size 0")
 * <span class="stGreen">A Set</span>
 * <span class="stGreen">  when empty</span>
 * <span class="stGreen">  - should have size 0</span>
 * </pre>
 *
 * <p>
 * You can also pass to <code>execute</code> a <a href="../ConfigMap.html"><em>config map</em></a> of key-value
 * pairs, which will be passed down into suites and tests, as well as other parameters that configure the run itself.
 * For more information on running in the Scala interpreter, see the documentation for <code>execute</code> (below) and the
 * <a href="../Shell.html">ScalaTest shell</a>.
 * </p>
 *
 * <p>
 * The <code>execute</code> method invokes a <code>run</code> method that takes two
 * parameters. This <code>run</code> method, which actually executes the suite, will usually be invoked by a test runner, such
 * as <a href="run$.html"><code>run</code></a>, <a href="../tools/Runner$.html"><code>tools.Runner</code></a>, a build tool, or an IDE.
 * </p>
 * <p>
 * <em>Note: <code>AnyFunSpec</code>'s syntax is in great part inspired by <a href="http://rspec.info/" target="_blank">RSpec</a>, a Ruby BDD framework.</em>
 *</p>
 *
 * <a name="ignoredTests"></a><h2>Ignored tests</h2>
 *
 * <p>
 * To support the common use case of temporarily disabling a test, with the
 * good intention of resurrecting the test at a later time, <code>AnyFunSpec</code> provides registration
 * methods that start with <code>ignore</code> instead of <code>it</code> or <code>they</code>. For example, to temporarily
 * disable the test with the text <code>"should have size 0"</code>, just change &ldquo;<code>it</code>&rdquo; into &#8220;<code>ignore</code>,&#8221; like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.ignore
 * <br/><span class="stReserved">import</span> org.scalatest.funspec.AnyFunSpec
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> {
 * <br/>  describe(<span class="stQuotedString">"A Set"</span>) {
 *     describe(<span class="stQuotedString">"when empty"</span>) {
 *       ignore(<span class="stQuotedString">"should have size 0"</span>) {
 *         assert(Set.empty.size === <span class="stLiteral">0</span>)
 *       }
 * <br/>      it(<span class="stQuotedString">"should produce NoSuchElementException when head is invoked"</span>) {
 *         assertThrows[<span class="stType">NoSuchElementException</span>] {
 *           Set.empty.head
 *         }
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * If you run this version of <code>SetSpec</code> with:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new SetSpec)
 * </pre>
 *
 * <p>
 * It will run only the second test and report that the first test was ignored:
 * </p>
 *
 * <pre class="stREPL">
 * <span class="stGreen">A Set</span>
 * <span class="stGreen">  when empty</span>
 * <span class="stYellow">  - should have size 0 !!! IGNORED !!!</span>
 * <span class="stGreen">  - should produce NoSuchElementException when head is invoked</span>
 * </pre>
 *
 * <p>
 * If you wish to temporarily ignore an entire suite of tests, you can (on the JVM, not Scala.js) annotate the test class with <code>@Ignore</code>, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.ignoreall
 * <br/><span class="stReserved">import</span> org.scalatest.funsuite.AnyFunSpec
 * <span class="stReserved">import</span> org.scalatest.Ignore
 * <br/>@<span class="stType">Ignore</span>
 * <span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> {
 * <br/>  describe(<span class="stQuotedString">"A Set"</span>) {
 *     describe(<span class="stQuotedString">"when empty"</span>) {
 *       it(<span class="stQuotedString">"should have size 0"</span>) {
 *         assert(Set.empty.size === <span class="stLiteral">0</span>)
 *       }
 * <br/>      it(<span class="stQuotedString">"should produce NoSuchElementException when head is invoked"</span>) {
 *         assertThrows[<span class="stType">NoSuchElementException</span>] {
 *           Set.empty.head
 *         }
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * When you mark a test class with a tag annotation, ScalaTest will mark each test defined in that class with that tag.
 * Thus, marking the <code>SetSpec</code> in the above example with the <code>@Ignore</code> tag annotation means that both tests
 * in the class will be ignored. If you run the above <code>SetSpec</code> in the Scala interpreter, you'll see:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new SetSpec)
 * <span class="stGreen">SetSpec:
 * A Set
 *   when empty</span>
 * <span class="stYellow">  - should have size 0 !!! IGNORED !!!</span>
 * <span class="stYellow">  - should produce NoSuchElementException when head is invoked !!! IGNORED !!!</span>
 * </pre>
 *
 * <p>
 * Note that marking a test class as ignored won't prevent it from being discovered by ScalaTest. Ignored classes
 * will be discovered and run, and all their tests will be reported as ignored. This is intended to keep the ignored
 * class visible, to encourage the developers to eventually fix and &ldquo;un-ignore&rdquo; it. If you want to
 * prevent a class from being discovered at all (on the JVM, not Scala.js), use the <a href="../DoNotDiscover.html"><code>DoNotDiscover</code></a> annotation instead.
 * </p>
 *
 * <a name="informers"></a><h2>Informers</h2>
 *
 * <p>
 * One of the parameters to <code>AnyFunSpec</code>'s <code>run</code> method is a <code>Reporter</code>, which
 * will collect and report information about the running suite of tests.
 * Information about suites and tests that were run, whether tests succeeded or failed, 
 * and tests that were ignored will be passed to the <code>Reporter</code> as the suite runs.
 * Most often the reporting done by default by <code>AnyFunSpec</code>'s methods will be sufficient, but
 * occasionally you may wish to provide custom information to the <code>Reporter</code> from a test.
 * For this purpose, an <code>Informer</code> that will forward information to the current <code>Reporter</code>
 * is provided via the <code>info</code> parameterless method.
 * You can pass the extra information to the <code>Informer</code> via one of its <code>apply</code> methods.
 * The <code>Informer</code> will then pass the information to the <code>Reporter</code> via an <code>InfoProvided</code> event.
 * Here's an example in which the <code>Informer</code> returned by <code>info</code> is used implicitly by the
 * <code>Given</code>, <code>When</code>, and <code>Then</code> methods of trait <code>GivenWhenThen</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.info
 * <br/><span class="stReserved">import</span> collection.mutable
 * <span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">funspec.AnyFunSpec</span> <span class="stReserved">with</span> <span class="stType">GivenWhenThen</span> {
 * <br/>  describe(<span class="stQuotedString">"A mutable Set"</span>) {
 *     it(<span class="stQuotedString">"should allow an element to be added"</span>) {
 *       <span class="stType">Given</span>(<span class="stQuotedString">"an empty mutable Set"</span>)
 *       <span class="stReserved">val</span> set = mutable.Set.empty[<span class="stType">String</span>]
 * <br/>      <span class="stType">When</span>(<span class="stQuotedString">"an element is added"</span>)
 *       set += <span class="stQuotedString">"clarity"</span>
 * <br/>      <span class="stType">Then</span>(<span class="stQuotedString">"the Set should have size 1"</span>)
 *       assert(set.size === <span class="stLiteral">1</span>)
 * <br/>      <span class="stType">And</span>(<span class="stQuotedString">"the Set should contain the added element"</span>)
 *       assert(set.contains(<span class="stQuotedString">"clarity"</span>))
 * <br/>      info(<span class="stQuotedString">"That's all folks!"</span>)
 *     }
 *   }
 * }
 * </pre>
 *
 * If you run this <code>AnyFunSpec</code> from the interpreter, you will see the following output:
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
 * <a name="documenters"></a><h2>Documenters</h2>
 *
 * <p>
 * <code>AnyFunSpec</code> also provides a <code>markup</code> method that returns a <a href="../Documenter.html"><code>Documenter</code></a>, which allows you to send
 * to the <code>Reporter</code> text formatted in <a href="http://daringfireball.net/projects/markdown/" target="_blank">Markdown syntax</a>.
 * You can pass the extra information to the <code>Documenter</code> via its <code>apply</code> method.
 * The <code>Documenter</code> will then pass the information to the <code>Reporter</code> via an <a href="../events/MarkupProvided.html"><code>MarkupProvided</code></a> event.
 * </p>
 *
 * <p>
 * Here's an example <code>AnyFunSpec</code> that uses <code>markup</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.markup
 * <br/><span class="stReserved">import</span> collection.mutable
 * <span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">funspec.AnyFunSpec</span> <span class="stReserved">with</span> <span class="stType">GivenWhenThen</span> {
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
 * <br/>  describe(<span class="stQuotedString">"A mutable Set"</span>) {
 *     it(<span class="stQuotedString">"should allow an element to be added"</span>) {
 *       <span class="stType">Given</span>(<span class="stQuotedString">"an empty mutable Set"</span>)
 *       <span class="stReserved">val</span> set = mutable.Set.empty[<span class="stType">String</span>]
 * <br/>      <span class="stType">When</span>(<span class="stQuotedString">"an element is added"</span>)
 *       set += <span class="stQuotedString">"clarity"</span>
 * <br/>      <span class="stType">Then</span>(<span class="stQuotedString">"the Set should have size 1"</span>)
 *       assert(set.size === <span class="stLiteral">1</span>)
 * <br/>      <span class="stType">And</span>(<span class="stQuotedString">"the Set should contain the added element"</span>)
 *       assert(set.contains(<span class="stQuotedString">"clarity"</span>))
 * <br/>      markup(<span class="stQuotedString">"This test finished with a **bold** statement!"</span>)
 *     }
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
 * <img class="stScreenShot" src="../../../lib/funSpec.gif">
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
 * To get immediate (<em>i.e.</em>, non-recorded) notifications from tests, you can use <code>note</code> (a <a href="../Notifier.html"><code>Notifier</code></a>) and <code>alert</code>
 * (an <a href="../Alerter.html"><code>Alerter</code></a>). Here's an example showing the differences:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.note
 * <br/><span class="stReserved">import</span> collection.mutable
 * <span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">funspec.AnyFunSpec</span> {
 * <br/>  describe(<span class="stQuotedString">"A mutable Set"</span>) {
 *     it(<span class="stQuotedString">"should allow an element to be added"</span>) {
 * <br/>      info(<span class="stQuotedString">"info is recorded"</span>)
 *       markup(<span class="stQuotedString">"markup is *also* recorded"</span>)
 *       note(<span class="stQuotedString">"notes are sent immediately"</span>)
 *       alert(<span class="stQuotedString">"alerts are also sent immediately"</span>)
 * <br/>      <span class="stReserved">val</span> set = mutable.Set.empty[<span class="stType">String</span>]
 *       set += <span class="stQuotedString">"clarity"</span>
 *       assert(set.size === <span class="stLiteral">1</span>)
 *       assert(set.contains(<span class="stQuotedString">"clarity"</span>))
 *     }
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
 * Another example is <a href="../tools/Runner$.html#slowpokeNotifications">slowpoke notifications</a>.
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
 * bit of behavior required by the system being tested. The test can also include some code that
 * sends more information about the behavior to the reporter when the tests run. At the end of the test,
 * it can call method <code>pending</code>, which will cause it to complete abruptly with <code>TestPendingException</code>.
 * </p>
 *
 * <p>
 * Because tests in ScalaTest can be designated as pending with <code>TestPendingException</code>, both the test name and any information
 * sent to the reporter when running the test can appear in the report of a test run. (In other words,
 * the code of a pending test is executed just like any other test.) However, because the test completes abruptly
 * with <code>TestPendingException</code>, the test will be reported as pending, to indicate
 * the actual test, and possibly the functionality, has not yet been implemented.
 * </p>
 *
 * <p>
 * You can mark a test as pending in <code>AnyFunSpec</code> by placing "<code>(pending)</code>" after the
 * test name, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.pending
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">funspec.AnyFunSpec</span> {
 * <br/>  describe(<span class="stQuotedString">"A Set"</span>) {
 *     describe(<span class="stQuotedString">"when empty"</span>) {
 *       it(<span class="stQuotedString">"should have size 0"</span>) (pending)
 * <br/>      it(<span class="stQuotedString">"should produce NoSuchElementException when head is invoked"</span>) {
 *         assertThrows[<span class="stType">NoSuchElementException</span>] {
 *           Set.empty.head
 *         }
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * (Note: "<code>(pending)</code>" is the body of the test. Thus the test contains just one statement, an invocation
 * of the <code>pending</code> method, which throws <code>TestPendingException</code>.)
 * If you run this version of <code>SetSpec</code> with:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new SetSpec)
 * </pre>
 *
 * <p>
 * It will run both tests, but report that the test named "<code>should have size 0</code>" is pending. You'll see:
 * </p>
 *
 * <pre class="stREPL">
 * <span class="stGreen">A Set</span>
 * <span class="stGreen">  when empty</span>
 * <span class="stYellow">  - should have size 0 (pending)</span>
 * <span class="stGreen">  - should produce NoSuchElementException when head is invoked</span>
 * </pre>
 * 
 * <a name="taggingTests"></a><h2>Tagging tests</h2>
 *
 * <p>
 * A <code>AnyFunSpec</code>'s tests may be classified into groups by <em>tagging</em> them with string names.
 * As with any suite, when executing a <code>AnyFunSpec</code>, groups of tests can
 * optionally be included and/or excluded. To tag a <code>AnyFunSpec</code>'s tests,
 * you pass objects that extend class <code>org.scalatest.Tag</code> to methods
 * that register tests. Class <code>Tag</code> takes one parameter, a string name.  If you have
 * created tag annotation interfaces as described in the <a href="../Tag.html"><code>Tag</code> documentation</a>, then you
 * will probably want to use tag names on your test functions that match. To do so, simply 
 * pass the fully qualified names of the tag interfaces to the <code>Tag</code> constructor. For example, if you've
 * defined a tag annotation interface with fully qualified name,
 * <code>com.mycompany.tags.DbTest</code>, then you could
 * create a matching tag for <code>AnyFunSpec</code>s like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.tagging
 * <br/><span class="stReserved">import</span> org.scalatest.Tag
 * <br/><span class="stReserved">object</span> <span class="stType">DbTest</span> <span class="stReserved">extends</span> <span class="stType">Tag</span>(<span class="stQuotedString">"com.mycompany.tags.DbTest"</span>)
 * </pre>
 *
 * <p>
 * Given these definitions, you could place <code>AnyFunSpec</code> tests into groups with tags like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> org.scalatest.funspec.AnyFunSpec
 * <span class="stReserved">import</span> org.scalatest.tagobjects.Slow
 * <br/><span class="stReserved">class</span> <span class="stType">SetSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> {
 * <br/>  describe(<span class="stQuotedString">"A Set"</span>) {
 *     describe(<span class="stQuotedString">"when empty"</span>) {
 *       it(<span class="stQuotedString">"should have size 0"</span>, <span class="stType">Slow</span>) {
 *         assert(Set.empty.size === <span class="stLiteral">0</span>)
 *       }
 * <br/>      it(<span class="stQuotedString">"should produce NoSuchElementException when head is invoked"</span>, <span class="stType">Slow</span>, <span class="stType">DbTest</span>) {
 *         assertThrows[<span class="stType">NoSuchElementException</span>] {
 *           Set.empty.head
 *         }
 *       }
 *     }
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
 * create a <code>Tag</code> object. A tag annotation (on the JVM, not Scala.js) allows you to tag all the tests of a <code>AnyFunSpec</code> in
 * one stroke by annotating the class. For more information and examples, see the
 * <a href="../Tag.html">documentation for class <code>Tag</code></a>. On Scala.js, to tag all tests of a suite, you'll need to
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
 * ScalaTest recommends three techniques to eliminate such code duplication:
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
 * mutable state across tests will make your test code easier to reason about and more amenable for parallel
 * test execution.</p><p>The following sections
 * describe these techniques, including explaining the recommended usage
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
 *     <a href="#fixtureContextObjects">fixture-context objects</a>
 *   </td>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: left">
 *     By placing fixture methods and fields into traits, you can easily give each test just the newly created
 *     fixtures it needs by mixing together traits.  Use this technique when you need <em>different combinations
 *     of mutable fixture objects in different tests</em>, and don't need to clean up after.
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
 *     <a href="#withFixtureNoArgTest">
 *       <code>withFixture(NoArgTest)</code></a>
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
 *  <dd style="display: list-item; list-style-type: disc; margin-left: 1.2em;">You have objects to pass into tests (override <code>withFixture(<em>One</em>ArgTest)</code> instead)</dd>
 *  </dl>
 *  </td>
 * </tr>
 *
 * <tr>
 *   <td style="border-width: 1px; padding: 3px; border: 1px solid black; text-align: right">
 *     <a href="#withFixtureOneArgTest">
 *       <code>withFixture(OneArgTest)</code>
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
 * more <em>get-fixture</em> methods. A get-fixture method returns a new instance of a needed fixture object (or an holder object containing
 * multiple fixture objects) each time it is called. You can call a get-fixture method at the beginning of each
 * test that needs the fixture, storing the returned object or objects in local variables. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.getfixture
 * <br/><span class="stReserved">import</span> org.scalatest.funspec.AnyFunSpec
 * <span class="stReserved">import</span> collection.mutable.ListBuffer
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> {
 * <br/>  <span class="stReserved">class</span> <span class="stType">Fixture</span> {
 *     <span class="stReserved">val</span> builder = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>(<span class="stQuotedString">"ScalaTest is "</span>)
 *     <span class="stReserved">val</span> buffer = <span class="stReserved">new</span> <span class="stType">ListBuffer[String]</span>
 *   }
 * <br/>  <span class="stReserved">def</span> fixture = <span class="stReserved">new</span> <span class="stType">Fixture</span>
 * <br/>  describe(<span class="stQuotedString">"Testing"</span>) {
 *     it(<span class="stQuotedString">"should be easy"</span>) {
 *       <span class="stReserved">val</span> f = fixture
 *       f.builder.append(<span class="stQuotedString">"easy!"</span>)
 *       assert(f.builder.toString === <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *       assert(f.buffer.isEmpty)
 *       f.buffer += <span class="stQuotedString">"sweet"</span>
 *     }
 * <br/>    it(<span class="stQuotedString">"should be fun"</span>) {
 *       <span class="stReserved">val</span> f = fixture
 *       f.builder.append(<span class="stQuotedString">"fun!"</span>)
 *       assert(f.builder.toString === <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *       assert(f.buffer.isEmpty)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * The &ldquo;<code>f.</code>&rdquo; in front of each use of a fixture object provides a visual indication of which objects 
 * are part of the fixture, but if you prefer, you can import the the members with &ldquo;<code>import f._</code>&rdquo; and use the names directly.
 * </p>
 *
 * <p>
 * If you need to configure fixture objects differently in different tests, you can pass configuration into the get-fixture method. For example, if you could pass
 * in an initial value for a mutable fixture object as a parameter to the get-fixture method.
 * </p>
 *
 * <a name="fixtureContextObjects"></a>
 * <h4>Instantiating fixture-context objects </h4>
 *
 * <p>
 * An alternate technique that is especially useful when different tests need different combinations of fixture objects is to define the fixture objects as instance variables
 * of <em>fixture-context objects</em> whose instantiation forms the body of tests. Like get-fixture methods, fixture-context objects are only
 * appropriate if you don't need to clean up the fixtures after using them.
 * </p>
 *
 * To use this technique, you define instance variables intialized with fixture objects in traits and/or classes, then in each test instantiate an object that
 * contains just the fixture objects needed by the test. Traits allow you to mix together just the fixture objects needed by each test, whereas classes
 * allow you to pass data in via a constructor to configure the fixture objects. Here's an example in which fixture objects are partitioned into two traits
 * and each test just mixes together the traits it needs:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.fixturecontext
 * <br/><span class="stReserved">import</span> collection.mutable.ListBuffer
 * <span class="stReserved">import</span> org.scalatest.funspec.AnyFunSpec
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> {
 * <br/>  <span class="stReserved">trait</span> <span class="stType">Builder</span> {
 *     <span class="stReserved">val</span> builder = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>(<span class="stQuotedString">"ScalaTest is "</span>)
 *   }
 * <br/>  <span class="stReserved">trait</span> <span class="stType">Buffer</span> {
 *     <span class="stReserved">val</span> buffer = <span class="stType">ListBuffer</span>(<span class="stQuotedString">"ScalaTest"</span>, <span class="stQuotedString">"is"</span>)
 *   }
 * <br/>  describe(<span class="stQuotedString">"Testing"</span>) {
 *     <span class="stLineComment">// This test needs the StringBuilder fixture</span>
 *     it(<span class="stQuotedString">"should be productive"</span>) {
 *       <span class="stReserved">new</span> <span class="stType">Builder</span> {
 *         builder.append(<span class="stQuotedString">"productive!"</span>)
 *         assert(builder.toString === <span class="stQuotedString">"ScalaTest is productive!"</span>)
 *       }
 *     }
 *   }
 * <br/>  describe(<span class="stQuotedString">"Test code"</span>) {
 *     <span class="stLineComment">// This test needs the ListBuffer[String] fixture</span>
 *     it(<span class="stQuotedString">"should be readable"</span>) {
 *       <span class="stReserved">new</span> <span class="stType">Buffer</span> {
 *         buffer += (<span class="stQuotedString">"readable!"</span>)
 *         assert(buffer === <span class="stType">List</span>(<span class="stQuotedString">"ScalaTest"</span>, <span class="stQuotedString">"is"</span>, <span class="stQuotedString">"readable!"</span>))
 *       }
 *     }
 * <br/>    <span class="stLineComment">// This test needs both the StringBuilder and ListBuffer</span>
 *     it(<span class="stQuotedString">"should be clear and concise"</span>) {
 *       <span class="stReserved">new</span> <span class="stType">Builder</span> <span class="stReserved">with</span> <span class="stType">Buffer</span> {
 *         builder.append(<span class="stQuotedString">"clear!"</span>)
 *         buffer += (<span class="stQuotedString">"concise!"</span>)
 *         assert(builder.toString === <span class="stQuotedString">"ScalaTest is clear!"</span>)
 *         assert(buffer === <span class="stType">List</span>(<span class="stQuotedString">"ScalaTest"</span>, <span class="stQuotedString">"is"</span>, <span class="stQuotedString">"concise!"</span>))
 *       }
 *     }
 *   }
 * }
 * </pre>
 *
 * <a name="withFixtureNoArgTest"></a>
 * <h4>Overriding <code>withFixture(NoArgTest)</code></h4>
 *
 * <p>
 * Although the get-fixture method and fixture-context object approaches take care of setting up a fixture at the beginning of each
 * test, they don't address the problem of cleaning up a fixture at the end of the test. If you just need to perform a side-effect at the beginning or end of
 * a test, and don't need to actually pass any fixture objects into the test, you can override <code>withFixture(NoArgTest)</code>, one of ScalaTest's
 * lifecycle methods defined in trait <a href="../Suite.html"><code>Suite</code></a>.
 * </p>
 *
 * <p>
 * Trait <code>Suite</code>'s implementation of <code>runTest</code> passes a no-arg test function to <code>withFixture(NoArgTest)</code>. It is <code>withFixture</code>'s
 * responsibility to invoke that test function. <code>Suite</code>'s implementation of <code>withFixture</code> simply
 * invokes the function, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stLineComment">// Default implementation in trait Suite</span>
 * <span class="stReserved">protected</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgTest</span>) = {
 *   test()
 * }
 * </pre>
 *
 * <p>
 * You can, therefore, override <code>withFixture</code> to perform setup before and/or cleanup after invoking the test function. If
 * you have cleanup to perform, you should invoke the test function inside a <code>try</code> block and perform the cleanup in
 * a <code>finally</code> clause, in case an exception propagates back through <code>withFixture</code>. (If a test fails because of an exception,
 * the test function invoked by withFixture will result in a [[org.scalatest.Failed <code>Failed</code>]] wrapping the exception. Nevertheless,
 * best practice is to perform cleanup in a finally clause just in case an exception occurs.)
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
 * <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgTest</span>) = {
 *   <span class="stLineComment">// Perform setup</span>
 *   <span class="stReserved">try</span> <span class="stReserved">super</span>.withFixture(test) <span class="stLineComment">// Invoke the test function</span>
 *   <span class="stReserved">finally</span> {
 *     <span class="stLineComment">// Perform cleanup</span>
 *   }
 * }
 * </pre>
 *
 * <p>
 * Here's an example in which <code>withFixture(NoArgTest)</code> is used to take a snapshot of the working directory if a test fails, and 
 * send that information to the reporter:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.noargtest
 * <br/><span class="stReserved">import</span> java.io.File
 * <span class="stReserved">import</span> org.scalatest._
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">funspec.AnyFunSpec</span> {
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgTest</span>) = {
 * <br/>    <span class="stReserved">try</span> <span class="stReserved">super</span>.withFixture(test) <span class="stReserved">match</span> {
 *       <span class="stReserved">case</span> failed: <span class="stType">Failed</span> =&gt;
 *         <span class="stReserved">val</span> currDir = <span class="stReserved">new</span> <span class="stType">File</span>(<span class="stQuotedString">"."</span>)
 *         <span class="stReserved">val</span> fileNames = currDir.list()
 *         info(<span class="stQuotedString">"Dir snapshot: "</span> + fileNames.mkString(<span class="stQuotedString">", "</span>))
 *         failed
 *       <span class="stReserved">case</span> other =&gt; other
 *     }
 *   }
 * <br/>  describe(<span class="stQuotedString">"This test"</span>) {
 *     it(<span class="stQuotedString">"should succeed"</span>) {
 *       assert(<span class="stLiteral">1</span> + <span class="stLiteral">1</span> === <span class="stLiteral">2</span>)
 *     }
 * <br/>    it(<span class="stQuotedString">"should fail"</span>) {
 *       assert(<span class="stLiteral">1</span> + <span class="stLiteral">1</span> === <span class="stLiteral">3</span>)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * Running this version of <code>ExampleSuite</code> in the interpreter in a directory with two files, <code>hello.txt</code> and <code>world.txt</code>
 * would give the following output:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; org.scalatest.run(new ExampleSuite)
 * <span class="stGreen">ExampleSuite:
 * This test
 * - should succeed</span>
 * <span class="stRed">- should fail *** FAILED ***
 *   2 did not equal 3 (<console>:33)
 *   + Dir snapshot: hello.txt, world.txt </span>
 * </pre>
 *
 * <p>
 * Note that the <a href="../Suite$NoArgTest.html"><code>NoArgTest</code></a> passed to <code>withFixture</code>, in addition to
 * an <code>apply</code> method that executes the test, also includes the test name and the <a href="../ConfigMap.html">config
 * map</a> passed to <code>runTest</code>. Thus you can also use the test name and configuration objects in your <code>withFixture</code>
 * implementation.
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
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.loanfixture
 * <br/><span class="stReserved">import</span> java.util.concurrent.ConcurrentHashMap
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
 * <br/><span class="stReserved">import</span> org.scalatest.funspec.AnyFunSpec
 * <span class="stReserved">import</span> DbServer._
 * <span class="stReserved">import</span> java.util.UUID.randomUUID
 * <span class="stReserved">import</span> java.io._
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> {
 * <br/>  <span class="stReserved">def</span> withDatabase(testCode: <span class="stType">Db</span> =&gt; <span class="stType">Any</span>) {
 *     <span class="stReserved">val</span> dbName = randomUUID.toString
 *     <span class="stReserved">val</span> db = createDb(dbName) <span class="stLineComment">// create the fixture</span>
 *     <span class="stReserved">try</span> {
 *       db.append(<span class="stQuotedString">"ScalaTest is "</span>) <span class="stLineComment">// perform setup</span>
 *       testCode(db) <span class="stLineComment">// "loan" the fixture to the test</span>
 *     }
 *     <span class="stReserved">finally</span> removeDb(dbName) <span class="stLineComment">// clean up the fixture</span>
 *   }
 * <br/>  <span class="stReserved">def</span> withFile(testCode: (<span class="stType">File</span>, <span class="stType">FileWriter</span>) =&gt; <span class="stType">Any</span>) {
 *     <span class="stReserved">val</span> file = File.createTempFile(<span class="stQuotedString">"hello"</span>, <span class="stQuotedString">"world"</span>) <span class="stLineComment">// create the fixture</span>
 *     <span class="stReserved">val</span> writer = <span class="stReserved">new</span> <span class="stType">FileWriter</span>(file)
 *     <span class="stReserved">try</span> {
 *       writer.write(<span class="stQuotedString">"ScalaTest is "</span>) <span class="stLineComment">// set up the fixture</span>
 *       testCode(file, writer) <span class="stLineComment">// "loan" the fixture to the test</span>
 *     }
 *     <span class="stReserved">finally</span> writer.close() <span class="stLineComment">// clean up the fixture</span>
 *   }
 * <br/>  describe(<span class="stQuotedString">"Testing"</span>) {
 *     <span class="stLineComment">// This test needs the file fixture</span>
 *     it(<span class="stQuotedString">"should be productive"</span>) {
 *       withFile { (file, writer) =&gt;
 *         writer.write(<span class="stQuotedString">"productive!"</span>)
 *         writer.flush()
 *         assert(file.length === <span class="stLiteral">24</span>)
 *       }
 *     }
 *   }
 * <br/>  describe(<span class="stQuotedString">"Test code"</span>) {
 *     <span class="stLineComment">// This test needs the database fixture</span>
 *     it(<span class="stQuotedString">"should be readable"</span>) {
 *       withDatabase { db =&gt;
 *         db.append(<span class="stQuotedString">"readable!"</span>)
 *         assert(db.toString === <span class="stQuotedString">"ScalaTest is readable!"</span>)
 *       }
 *     }
 * <br/>    <span class="stLineComment">// This test needs both the file and the database</span>
 *     it(<span class="stQuotedString">"should be clear and concise"</span>) {
 *       withDatabase { db =&gt;
 *        withFile { (file, writer) =&gt; <span class="stLineComment">// loan-fixture methods compose</span>
 *           db.append(<span class="stQuotedString">"clear!"</span>)
 *           writer.write(<span class="stQuotedString">"concise!"</span>)
 *           writer.flush()
 *           assert(db.toString === <span class="stQuotedString">"ScalaTest is clear!"</span>)
 *           assert(file.length === <span class="stLiteral">21</span>)
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
 * involve external side-effects, like creating files or databases, it is a good idea to give each file or database a unique name as is
 * done in this example. This keeps tests completely isolated, allowing you to run them in parallel if desired.
 * </p>
 *
 * <a name="withFixtureOneArgTest"></a>
 * <h4>Overriding <code>withFixture(OneArgTest)</code></h4>
 *
 * <p>
 * If all or most tests need the same fixture, you can avoid some of the boilerplate of the loan-fixture method approach by using a <code>FixtureSuite</code>
 * and overriding <code>withFixture(OneArgTest)</code>.
 * Each test in a <code>FixtureSuite</code> takes a fixture as a parameter, allowing you to pass the fixture into
 * the test. You must indicate the type of the fixture parameter by specifying <code>FixtureParam</code>, and implement a
 * <code>withFixture</code> method that takes a <code>OneArgTest</code>. This <code>withFixture</code> method is responsible for
 * invoking the one-arg test function, so you can perform fixture set up before, and clean up after, invoking and passing
 * the fixture into the test function.
 * </p>
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
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">funspec.FixtureAnyFunSpec</span> {
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
 * In this example, the tests actually required two fixture objects, a <code>File</code> and a <code>FileWriter</code>. In such situations you can
 * simply define the <code>FixtureParam</code> type to be a tuple containing the objects, or as is done in this example, a case class containing
 * the objects.  For more information on the <code>withFixture(OneArgTest)</code> technique, see the <a href="FixtureAnyFunSpec.html">documentation for <code>FixtureAnyFunSpec</code></a>.
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
 * to mix in trait <a href="../BeforeAndAfter.html"><code>BeforeAndAfter</code></a>.  With this trait you can denote a bit of code to run before each test
 * with <code>before</code> and/or after each test each test with <code>after</code>, like this:
 * </p>
 * 
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.beforeandafter
 * <br/><span class="stReserved">import</span> org.scalatest.funspec.AnyFunSpec
 * <span class="stReserved">import</span> org.scalatest.BeforeAndAfter
 * <span class="stReserved">import</span> collection.mutable.ListBuffer
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> <span class="stReserved">with</span> <span class="stType">BeforeAndAfter</span> {
 * <br/>  <span class="stReserved">val</span> builder = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>
 *   <span class="stReserved">val</span> buffer = <span class="stReserved">new</span> <span class="stType">ListBuffer[String]</span>
 * <br/>  before {
 *     builder.append(<span class="stQuotedString">"ScalaTest is "</span>)
 *   }
 * <br/>  after {
 *     builder.clear()
 *     buffer.clear()
 *   }
 * <br/>  describe(<span class="stQuotedString">"Testing"</span>) {
 *     it(<span class="stQuotedString">"should be easy"</span>) {
 *       builder.append(<span class="stQuotedString">"easy!"</span>)
 *       assert(builder.toString === <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *       assert(buffer.isEmpty)
 *       buffer += <span class="stQuotedString">"sweet"</span>
 *     }
 * <br/>    it(<span class="stQuotedString">"should be fun"</span>) {
 *       builder.append(<span class="stQuotedString">"fun!"</span>)
 *       assert(builder.toString === <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *       assert(buffer.isEmpty)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * Note that the only way <code>before</code> and <code>after</code> code can communicate with test code is via some side-effecting mechanism, commonly by
 * reassigning instance <code>var</code>s or by changing the state of mutable objects held from instance <code>val</code>s (as in this example). If using
 * instance <code>var</code>s or mutable objects held from instance <code>val</code>s you wouldn't be able to run tests in parallel in the same instance
 * of the test class (on the JVM, not Scala.js) unless you synchronized access to the shared, mutable state. This is why ScalaTest's <code>ParallelTestExecution</code> trait extends
 * <a href="../OneInstancePerTest.html"><code>OneInstancePerTest</code></a>. By running each test in its own instance of the class, each test has its own copy of the instance variables, so you
 * don't need to synchronize. If you mixed <code>ParallelTestExecution</code> into the <code>ExampleSuite</code> above, the tests would run in parallel just fine
 * without any synchronization needed on the mutable <code>StringBuilder</code> and <code>ListBuffer[String]</code> objects.
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
 * which the <code>StringBuilder</code> and <code>ListBuffer[String]</code> fixtures used in the previous examples have been
 * factored out into two <em>stackable fixture traits</em> named <code>Builder</code> and <code>Buffer</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.composingwithfixture
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> collection.mutable.ListBuffer
 * <br/><span class="stReserved">trait</span> <span class="stType">Builder</span> <span class="stReserved">extends</span> <span class="stType">TestSuiteMixin</span> { <span class="stReserved">this</span>: <span class="stType">TestSuite</span> =&gt;
 * <br/>  <span class="stReserved">val</span> builder = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>
 * <br/>  <span class="stReserved">abstract</span> <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgTest</span>) = {
 *     builder.append(<span class="stQuotedString">"ScalaTest is "</span>)
 *     <span class="stReserved">try</span> <span class="stReserved">super</span>.withFixture(test) <span class="stLineComment">// To be stackable, must call super.withFixture</span>
 *     <span class="stReserved">finally</span> builder.clear()
 *   }
 * }
 * <br/><span class="stReserved">trait</span> <span class="stType">Buffer</span> <span class="stReserved">extends</span> <span class="stType">TestSuiteMixin</span> { <span class="stReserved">this</span>: <span class="stType">TestSuite</span> =&gt;
 * <br/>  <span class="stReserved">val</span> buffer = <span class="stReserved">new</span> <span class="stType">ListBuffer[String]</span>
 * <br/>  <span class="stReserved">abstract</span> <span class="stReserved">override</span> <span class="stReserved">def</span> withFixture(test: <span class="stType">NoArgTest</span>) = {
 *     <span class="stReserved">try</span> <span class="stReserved">super</span>.withFixture(test) <span class="stLineComment">// To be stackable, must call super.withFixture</span>
 *     <span class="stReserved">finally</span> buffer.clear()
 *   }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">funspec.AnyFunSpec</span> <span class="stReserved">with</span> <span class="stType">Builder</span> <span class="stReserved">with</span> <span class="stType">Buffer</span> {
 * <br/>  describe(<span class="stQuotedString">"Testing"</span>) {
 *     it(<span class="stQuotedString">"should be easy"</span>) {
 *       builder.append(<span class="stQuotedString">"easy!"</span>)
 *       assert(builder.toString === <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *       assert(buffer.isEmpty)
 *       buffer += <span class="stQuotedString">"sweet"</span>
 *     }
 * <br/>    it(<span class="stQuotedString">"should be fun"</span>) {
 *       builder.append(<span class="stQuotedString">"fun!"</span>)
 *       assert(builder.toString === <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *       assert(buffer.isEmpty)
 *       buffer += <span class="stQuotedString">"clear"</span>
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * By mixing in both the <code>Builder</code> and <code>Buffer</code> traits, <code>ExampleSuite</code> gets both fixtures, which will be
 * initialized before each test and cleaned up after. The order the traits are mixed together determines the order of execution.
 * In this case, <code>Builder</code> is &ldquo;super&rdquo; to <code>Buffer</code>. If you wanted <code>Buffer</code> to be &ldquo;super&rdquo;
 * to <code>Builder</code>, you need only switch the order you mix them together, like this: 
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">class</span> <span class="stType">Example2Spec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> <span class="stReserved">with</span> <span class="stType">Buffer</span> <span class="stReserved">with</span> <span class="stType">Builder</span>
 * </pre>
 *
 * <p>
 * And if you only need one fixture you mix in only that trait:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">class</span> <span class="stType">Example3Spec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> <span class="stReserved">with</span> <span class="stType">Builder</span>
 * </pre>
 *
 * <p>
 * Another way to create stackable fixture traits is by extending the <a href="../BeforeAndAfterEach.html"><code>BeforeAndAfterEach</code></a>
 * and/or <a href="../BeforeAndAfterAll.html"><code>BeforeAndAfterAll</code></a> traits.
 * <code>BeforeAndAfterEach</code> has a <code>beforeEach</code> method that will be run before each test (like JUnit's <code>setUp</code>),
 * and an <code>afterEach</code> method that will be run after (like JUnit's <code>tearDown</code>).
 * Similarly, <code>BeforeAndAfterAll</code> has a <code>beforeAll</code> method that will be run before all tests,
 * and an <code>afterAll</code> method that will be run after all tests. Here's what the previously shown example would look like if it
 * were rewritten to use the <code>BeforeAndAfterEach</code> methods instead of <code>withFixture</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">package</span> org.scalatest.examples.funspec.composingbeforeandaftereach
 * <br/><span class="stReserved">import</span> org.scalatest._
 * <span class="stReserved">import</span> org.scalatest.BeforeAndAfterEach
 * <span class="stReserved">import</span> collection.mutable.ListBuffer
 * <br/><span class="stReserved">trait</span> <span class="stType">Builder</span> <span class="stReserved">extends</span> <span class="stType">BeforeAndAfterEach</span> { <span class="stReserved">this</span>: <span class="stType">Suite</span> =&gt;
 * <br/>  <span class="stReserved">val</span> builder = <span class="stReserved">new</span> <span class="stType">StringBuilder</span>
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> beforeEach() {
 *     builder.append(<span class="stQuotedString">"ScalaTest is "</span>)
 *     <span class="stReserved">super</span>.beforeEach() <span class="stLineComment">// To be stackable, must call super.beforeEach</span>
 *   }
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> afterEach() {
 *     <span class="stReserved">try</span> <span class="stReserved">super</span>.afterEach() <span class="stLineComment">// To be stackable, must call super.afterEach</span>
 *     <span class="stReserved">finally</span> builder.clear()
 *   }
 * }
 * <br/><span class="stReserved">trait</span> <span class="stType">Buffer</span> <span class="stReserved">extends</span> <span class="stType">BeforeAndAfterEach</span> { <span class="stReserved">this</span>: <span class="stType">Suite</span> =&gt;
 * <br/>  <span class="stReserved">val</span> buffer = <span class="stReserved">new</span> <span class="stType">ListBuffer[String]</span>
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> afterEach() {
 *     <span class="stReserved">try</span> <span class="stReserved">super</span>.afterEach() <span class="stLineComment">// To be stackable, must call super.afterEach</span>
 *     <span class="stReserved">finally</span> buffer.clear()
 *   }
 * }
 * <br/><span class="stReserved">class</span> <span class="stType">ExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">funspec.AnyFunSpec</span> <span class="stReserved">with</span> <span class="stType">Builder</span> <span class="stReserved">with</span> <span class="stType">Buffer</span> {
 * <br/>  describe(<span class="stQuotedString">"Testing"</span>) {
 *     it(<span class="stQuotedString">"should be easy"</span>) {
 *       builder.append(<span class="stQuotedString">"easy!"</span>)
 *       assert(builder.toString === <span class="stQuotedString">"ScalaTest is easy!"</span>)
 *       assert(buffer.isEmpty)
 *       buffer += <span class="stQuotedString">"sweet"</span>
 *     }
 * <br/>    it(<span class="stQuotedString">"should be fun"</span>) {
 *       builder.append(<span class="stQuotedString">"fun!"</span>)
 *       assert(builder.toString === <span class="stQuotedString">"ScalaTest is fun!"</span>)
 *       assert(buffer.isEmpty)
 *       buffer += <span class="stQuotedString">"clear"</span>
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
 * complete abruptly, it is considered an aborted suite, which will result in a <a href="../events/SuiteAborted.html"><code>SuiteAborted</code></a> event.
 * </p>
 * 
 * <a name="sharedTests"></a><h2>Shared tests</h2>
 *
 * <p>
 * Sometimes you may want to run the same test code on different fixture objects. In other words, you may want to write tests that are "shared"
 * by different fixture objects.
 * To accomplish this in a <code>AnyFunSpec</code>, you first place shared tests in <em>behavior functions</em>. These behavior functions will be
 * invoked during the construction phase of any <code>AnyFunSpec</code> that uses them, so that the tests they contain will be registered as tests in that <code>AnyFunSpec</code>.
 * For example, given this stack class:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">import</span> scala.collection.mutable.ListBuffer
 * <br/><span class="stReserved">class</span> <span class="stType">Stack[T]</span> {
 * <br/>  <span class="stReserved">val</span> MAX = <span class="stLiteral">10</span>
 *   <span class="stReserved">private</span> <span class="stReserved">val</span> buf = <span class="stReserved">new</span> <span class="stType">ListBuffer[T]</span>
 * <br/>  <span class="stReserved">def</span> push(o: T) {
 *     <span class="stReserved">if</span> (!full)
 *       buf.prepend(o)
 *     <span class="stReserved">else</span>
 *       <span class="stReserved">throw</span> <span class="stReserved">new</span> <span class="stType">IllegalStateException</span>(<span class="stQuotedString">"can't push onto a full stack"</span>)
 *   }
 * <br/>  <span class="stReserved">def</span> pop(): T = {
 *     <span class="stReserved">if</span> (!empty)
 *       buf.remove(<span class="stLiteral">0</span>)
 *     <span class="stReserved">else</span>
 *       <span class="stReserved">throw</span> <span class="stReserved">new</span> <span class="stType">IllegalStateException</span>(<span class="stQuotedString">"can't pop an empty stack"</span>)
 *   }
 * <br/>  <span class="stReserved">def</span> peek: T = {
 *     <span class="stReserved">if</span> (!empty)
 *       buf(<span class="stLiteral">0</span>)
 *     <span class="stReserved">else</span>
 *       <span class="stReserved">throw</span> <span class="stReserved">new</span> <span class="stType">IllegalStateException</span>(<span class="stQuotedString">"can't pop an empty stack"</span>)
 *   }
 * <br/>  <span class="stReserved">def</span> full: <span class="stType">Boolean</span> = buf.size == MAX
 *   <span class="stReserved">def</span> empty: <span class="stType">Boolean</span> = buf.size == <span class="stLiteral">0</span>
 *   <span class="stReserved">def</span> size = buf.size
 * <br/>  <span class="stReserved">override</span> <span class="stReserved">def</span> toString = buf.mkString(<span class="stQuotedString">"Stack("</span>, <span class="stQuotedString">", "</span>, <span class="stQuotedString">")"</span>)
 * }
 * </pre>
 *
 * <p>
 * You may want to test the <code>Stack</code> class in different states: empty, full, with one item, with one item less than capacity,
 * <em>etc</em>. You may find you have several tests that make sense any time the stack is non-empty. Thus you'd ideally want to run
 * those same tests for three stack fixture objects: a full stack, a stack with a one item, and a stack with one item less than
 * capacity. With shared tests, you can factor these tests out into a behavior function, into which you pass the
 * stack fixture to use when running the tests. So in your <code>AnyFunSpec</code> for stack, you'd invoke the
 * behavior function three times, passing in each of the three stack fixtures so that the shared tests are run for all three fixtures. You
 * can define a behavior function that encapsulates these shared tests inside the <code>AnyFunSpec</code> that uses them. If they are shared
 * between different <code>AnyFunSpec</code>s, however, you could also define them in a separate trait that is mixed into each <code>AnyFunSpec</code> that uses them.
 * </p>
 *
 * <p>
 * <a name="StackBehaviors">For</a> example, here the <code>nonEmptyStack</code> behavior function (in this case, a behavior <em>method</em>) is defined in a trait along with another
 * method containing shared tests for non-full stacks:
 * </p>
 * 
 * <pre class="stHighlighted">
 * <span class="stReserved">trait</span> <span class="stType">StackBehaviors</span> { <span class="stReserved">this</span>: <span class="stType">AnyFunSpec</span> =&gt;
 * <br/>  <span class="stReserved">def</span> nonEmptyStack(newStack: =&gt; <span class="stType">Stack[Int]</span>, lastItemAdded: <span class="stType">Int</span>) {
 * <br/>    it(<span class="stQuotedString">"should be non-empty"</span>) {
 *       assert(!newStack.empty)
 *     }
 * <br/>    it(<span class="stQuotedString">"should return the top item on peek"</span>) {
 *       assert(newStack.peek === lastItemAdded)
 *     }
 * <br/>    it(<span class="stQuotedString">"should not remove the top item on peek"</span>) {
 *       <span class="stReserved">val</span> stack = newStack
 *       <span class="stReserved">val</span> size = stack.size
 *       assert(stack.peek === lastItemAdded)
 *       assert(stack.size === size)
 *     }
 * <br/>    it(<span class="stQuotedString">"should remove the top item on pop"</span>) {
 *       <span class="stReserved">val</span> stack = newStack
 *       <span class="stReserved">val</span> size = stack.size
 *       assert(stack.pop === lastItemAdded)
 *       assert(stack.size === size - <span class="stLiteral">1</span>)
 *     }
 *   }
 * <br/>  <span class="stReserved">def</span> nonFullStack(newStack: =&gt; <span class="stType">Stack[Int]</span>) {
 * <br/>    it(<span class="stQuotedString">"should not be full"</span>) {
 *       assert(!newStack.full)
 *     }
 * <br/>    it(<span class="stQuotedString">"should add to the top on push"</span>) {
 *       <span class="stReserved">val</span> stack = newStack
 *       <span class="stReserved">val</span> size = stack.size
 *       stack.push(<span class="stLiteral">7</span>)
 *       assert(stack.size === size + <span class="stLiteral">1</span>)
 *       assert(stack.peek === <span class="stLiteral">7</span>)
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>
 * Given these behavior functions, you could invoke them directly, but <code>AnyFunSpec</code> offers a DSL for the purpose,
 * which looks like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * it should behave like nonEmptyStack(stackWithOneItem, lastValuePushed)
 * it should behave like nonFullStack(stackWithOneItem)
 * </pre>
 *
 * <p>
 * If you prefer to use an imperative style to change fixtures, for example by mixing in <code>BeforeAndAfterEach</code> and
 * reassigning a <code>stack</code> <code>var</code> in <code>beforeEach</code>, you could write your behavior functions
 * in the context of that <code>var</code>, which means you wouldn't need to pass in the stack fixture because it would be
 * in scope already inside the behavior function. In that case, your code would look like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * it should behave like nonEmptyStack <span class="stLineComment">// assuming lastValuePushed is also in scope inside nonEmptyStack</span>
 * it should behave like nonFullStack
 * </pre>
 *
 * <p>
 * The recommended style, however, is the functional, pass-all-the-needed-values-in style. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">class</span> <span class="stType">SharedTestExampleSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFunSpec</span> <span class="stReserved">with</span> <span class="stType">StackBehaviors</span> {
 * <br/>  <span class="stLineComment">// Stack fixture creation methods</span>
 *   <span class="stReserved">def</span> emptyStack = <span class="stReserved">new</span> <span class="stType">Stack[Int]</span>
 * <br/>  <span class="stReserved">def</span> fullStack = {
 *     <span class="stReserved">val</span> stack = <span class="stReserved">new</span> <span class="stType">Stack[Int]</span>
 *     <span class="stReserved">for</span> (i <- <span class="stLiteral">0</span> until stack.MAX)
 *       stack.push(i)
 *     stack
 *   }
 * <br/>  <span class="stReserved">def</span> stackWithOneItem = {
 *     <span class="stReserved">val</span> stack = <span class="stReserved">new</span> <span class="stType">Stack[Int]</span>
 *     stack.push(<span class="stLiteral">9</span>)
 *     stack
 *   }
 * <br/>  <span class="stReserved">def</span> stackWithOneItemLessThanCapacity = {
 *     <span class="stReserved">val</span> stack = <span class="stReserved">new</span> <span class="stType">Stack[Int]</span>
 *     <span class="stReserved">for</span> (i &lt;- <span class="stLiteral">1</span> to <span class="stLiteral">9</span>)
 *       stack.push(i)
 *     stack
 *   }
 * <br/>  <span class="stReserved">val</span> lastValuePushed = <span class="stLiteral">9</span>
 * <br/>  describe(<span class="stQuotedString">"A Stack"</span>) {
 * <br/>    describe(<span class="stQuotedString">"(when empty)"</span>) {
 * <br/>      it(<span class="stQuotedString">"should be empty"</span>) {
 *         assert(emptyStack.empty)
 *       }
 * <br/>      it(<span class="stQuotedString">"should complain on peek"</span>) {
 *         assertThrows[<span class="stType">IllegalStateException</span>] {
 *           emptyStack.peek
 *         }
 *       }
 * <br/>      it(<span class="stQuotedString">"should complain on pop"</span>) {
 *         assertThrows[<span class="stType">IllegalStateException</span>] {
 *           emptyStack.pop
 *         }
 *       }
 *     }
 * <br/>    describe(<span class="stQuotedString">"(with one item)"</span>) {
 *       it should behave like nonEmptyStack(stackWithOneItem, lastValuePushed)
 *       it should behave like nonFullStack(stackWithOneItem)
 *     }
 * <br/>    describe(<span class="stQuotedString">"(with one item less than capacity)"</span>) {
 *       it should behave like nonEmptyStack(stackWithOneItemLessThanCapacity, lastValuePushed)
 *       it should behave like nonFullStack(stackWithOneItemLessThanCapacity)
 *     }
 * <br/>    describe(<span class="stQuotedString">"(full)"</span>) {
 * <br/>      it(<span class="stQuotedString">"should be full"</span>) {
 *         assert(fullStack.full)
 *       }
 * <br/>      it should behave like nonEmptyStack(fullStack, lastValuePushed)
 * <br/>      it(<span class="stQuotedString">"should complain on a push"</span>) {
 *         assertThrows[<span class="stType">IllegalStateException</span>] {
 *           fullStack.push(<span class="stLiteral">10</span>)
 *         }
 *       }
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
 * <span class="stGreen">A Stack (when empty) 
 * - should be empty
 * - should complain on peek
 * - should complain on pop
 * A Stack (with one item) 
 * - should be non-empty
 * - should return the top item on peek
 * - should not remove the top item on peek
 * - should remove the top item on pop
 * - should not be full
 * - should add to the top on push
 * A Stack (with one item less than capacity) 
 * - should be non-empty
 * - should return the top item on peek
 * - should not remove the top item on peek
 * - should remove the top item on pop
 * - should not be full
 * - should add to the top on push
 * A Stack (full) 
 * - should be full
 * - should be non-empty
 * - should return the top item on peek
 * - should not remove the top item on peek
 * - should remove the top item on pop
 * - should complain on a push</span>
 * </pre>
 * 
 * <p>
 * One thing to keep in mind when using shared tests is that in ScalaTest, each test in a suite must have a unique name.
 * If you register the same tests repeatedly in the same suite, one problem you may encounter is an exception at runtime
 * complaining that multiple tests are being registered with the same test name. A good way to solve this problem in a <code>AnyFunSpec</code> is to surround
 * each invocation of a behavior function with a <code>describe</code> clause, which will prepend a string to each test name.
 * For example, the following code in a <code>AnyFunSpec</code> would register a test with the name <code>"A Stack (when empty) should be empty"</code>:
 * </p>
 *
 * <pre class="stHighlighted">
 * describe(<span class="stQuotedString">"A Stack"</span>) {
 * <br/>  describe(<span class="stQuotedString">"(when empty)"</span>) {
 * <br/>    it(<span class="stQuotedString">"should be empty"</span>) {
 *       assert(emptyStack.empty)
 *     }
 *     <span class="stLineComment">// ...</span>
 * </pre>
 *
 * <p>
 * If the <code>"should be empty"</code> test was factored out into a behavior function, it could be called repeatedly so long
 * as each invocation of the behavior function is inside a different set of <code>describe</code> clauses.
 *
 * @author Bill Venners
 */

class AnyFunSpec extends AnyFunSpecLike {

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
