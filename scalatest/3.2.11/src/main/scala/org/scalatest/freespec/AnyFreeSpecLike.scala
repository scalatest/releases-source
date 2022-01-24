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
package org.scalatest.freespec

import org.scalactic.{source, Prettifier}
import org.scalatest._
import org.scalatest.Suite.autoTagClassAnnotations
import org.scalatest.verbs.BehaveWord
import org.scalatest.exceptions._

/**
 * Implementation trait for class <code>AnyFreeSpec</code>, which 
 * facilitates a &ldquo;behavior-driven&rdquo; style of development (BDD),
 * in which tests are nested inside text clauses denoted with the dash
 * operator (<code>-</code>).
 * 
 * <p>
 * <a href="AnyFreeSpec.html"><code>AnyFreeSpec</code></a> is a class, not a trait,
 * to minimize compile time given there is a slight compiler overhead to
 * mixing in traits compared to extending classes. If you need to mix the
 * behavior of <code>AnyFreeSpec</code> into some other class, you can use this
 * trait instead, because class <code>AnyFreeSpec</code> does nothing more than
 * extend this trait and add a nice <code>toString</code> implementation.
 * </p>
 *
 * <p>
 * See the documentation of the class for a <a href="AnyFreeSpec.html">detailed
 * overview of <code>AnyFreeSpec</code></a>.
 * </p>
 *
 * @author Bill Venners
 */

//SCALATESTJS-ONLY @scala.scalajs.reflect.annotation.EnableReflectiveInstantiation
//SCALATESTNATIVE-ONLY @scala.scalajs.reflect.annotation.EnableReflectiveInstantiation
trait AnyFreeSpecLike extends TestSuite with TestRegistration with Informing with Notifying with Alerting with Documenting { thisSuite =>

  private final val engine = new Engine(Resources.concurrentFreeSpecMod, "FreeSpec")
  import engine._

  /**
   * Returns an <code>Informer</code> that during test execution will forward strings passed to its
   * <code>apply</code> method to the current reporter. If invoked in a constructor, it
   * will register the passed string for forwarding later during test execution. If invoked from inside a scope,
   * it will forward the information to the current reporter immediately.  If invoked from inside a test function,
   * it will record the information and forward it to the current reporter only after the test completed, as <code>recordedEvents</code>
   * of the test completed event, such as <code>TestSucceeded</code>. If invoked at any other time, it will print to the standard output.
   * This method can be called safely by any thread.
   */
  protected def info: Informer = atomicInformer.get

  /**
   * Returns a <code>Notifier</code> that during test execution will forward strings passed to its
   * <code>apply</code> method to the current reporter. If invoked in a constructor, it
   * will register the passed string for forwarding later during test execution. If invoked while this
   * <code>AnyFreeSpec</code> is being executed, such as from inside a test function, it will forward the information to
   * the current reporter immediately. If invoked at any other time, it will
   * print to the standard output. This method can be called safely by any thread.
   */
  protected def note: Notifier = atomicNotifier.get

  /**
   * Returns an <code>Alerter</code> that during test execution will forward strings passed to its
   * <code>apply</code> method to the current reporter. If invoked in a constructor, it
   * will register the passed string for forwarding later during test execution. If invoked while this
   * <code>AnyFreeSpec</code> is being executed, such as from inside a test function, it will forward the information to
   * the current reporter immediately. If invoked at any other time, it will
   * print to the standard output. This method can be called safely by any thread.
   */
  protected def alert: Alerter = atomicAlerter.get

  /**
   * Returns a <code>Documenter</code> that during test execution will forward strings passed to its
   * <code>apply</code> method to the current reporter. If invoked in a constructor, it
   * will register the passed string for forwarding later during test execution. If invoked from inside a scope,
   * it will forward the information to the current reporter immediately.  If invoked from inside a test function,
   * it will record the information and forward it to the current reporter only after the test completed, as <code>recordedEvents</code>
   * of the test completed event, such as <code>TestSucceeded</code>. If invoked at any other time, it will print to the standard output.
   * This method can be called safely by any thread.
   */
  protected def markup: Documenter = atomicDocumenter.get

  private final def registerTestImpl(testText: String, testTags: Tag*)(testFun: => Any /* Assertion */, pos: source.Position): Unit = {
    // SKIP-SCALATESTJS,NATIVE-START
    val stackDepthAdjustment = -2
    // SKIP-SCALATESTJS,NATIVE-END
    //SCALATESTJS,NATIVE-ONLY val stackDepthAdjustment = -4
    engine.registerTest(testText, Transformer(() => testFun), Resources.testCannotBeNestedInsideAnotherTest, "AnyFreeSpecLike.scala", "registerTest", 5, stackDepthAdjustment, None, None, Some(pos), None, testTags: _*)
  }

  // SKIP-DOTTY-START
  final def registerTest(testText: String, testTags: Tag*)(testFun: => Any /* Assertion */)(implicit pos: source.Position): Unit = {
    registerTestImpl(testText, testTags: _*)(testFun, pos)
  }
  // SKIP-DOTTY-END
  //DOTTY-ONLY inline def registerTest(testText: String, testTags: Tag*)(testFun: => Any /* Assertion */)(implicit pos: source.Position): Unit = {
  //DOTTY-ONLY   ${ source.Position.withPosition[Unit]('{(pos: source.Position) => registerTestImpl(testText, testTags: _*)(testFun, pos) }) } 
  //DOTTY-ONLY }

  private final def registerIgnoredTestImpl(testText: String, testTags: Tag*)(testFun: => Any /* Assertion */, pos: source.Position): Unit = {
    // SKIP-SCALATESTJS,NATIVE-START
    val stackDepthAdjustment = -3
    // SKIP-SCALATESTJS,NATIVE-END
    //SCALATESTJS,NATIVE-ONLY val stackDepthAdjustment = -4
    engine.registerIgnoredTest(testText, Transformer(() => testFun), Resources.testCannotBeNestedInsideAnotherTest, "AnyFreeSpecLike.scala", "registerIgnoredTest", 4, stackDepthAdjustment, None, Some(pos), testTags: _*)
  }

  // SKIP-DOTTY-START
  final def registerIgnoredTest(testText: String, testTags: Tag*)(testFun: => Any /* Assertion */)(implicit pos: source.Position): Unit = {
    registerIgnoredTestImpl(testText, testTags: _*)(testFun, pos)
  }
  // SKIP-DOTTY-END
  //DOTTY-ONLY inline def registerIgnoredTest(testText: String, testTags: Tag*)(testFun: => Any /* Assertion */)(implicit pos: source.Position): Unit = {
  //DOTTY-ONLY   ${ source.Position.withPosition[Unit]('{(pos: source.Position) => registerIgnoredTestImpl(testText, testTags: _*)(testFun, pos) }) } 
  //DOTTY-ONLY }

  /**
   * Register a test with the given spec text, optional tags, and test function value that takes no arguments.
   * An invocation of this method is called an &ldquo;example.&rdquo;
   *
   * This method will register the test for later execution via an invocation of one of the <code>execute</code>
   * methods. The name of the test will be a concatenation of the text of all surrounding describers,
   * from outside in, and the passed spec text, with one space placed between each item. (See the documenation
   * for <code>testNames</code> for an example.) The resulting test name must not have been registered previously on
   * this <code>AnyFreeSpec</code> instance.
   *
   * @param specText the specification text, which will be combined with the descText of any surrounding describers
   * to form the test name
   * @param testTags the optional list of tags for this test
   * @param methodName caller's method name
   * @param testFun the test function
   * @throws DuplicateTestNameException if a test with the same name has been registered previously
   * @throws TestRegistrationClosedException if invoked after <code>run</code> has been invoked on this suite
   * @throws NullArgumentException if <code>specText</code> or any passed test tag is <code>null</code>
   */
  private def registerTestToRun(specText: String, testTags: List[Tag], methodName: String, testFun: () => Any /* Assertion */, pos: source.Position): Unit = {
    // SKIP-SCALATESTJS,NATIVE-START
    val stackDepth = 4
    val stackDepthAdjustment = -3
    // SKIP-SCALATESTJS,NATIVE-END
    //SCALATESTJS,NATIVE-ONLY val stackDepth = 6
    //SCALATESTJS,NATIVE-ONLY val stackDepthAdjustment = -5
    engine.registerTest(specText, Transformer(testFun), Resources.inCannotAppearInsideAnotherIn, "AnyFreeSpecLike.scala", methodName, stackDepth, stackDepthAdjustment, None, None, Some(pos), None, testTags: _*)
  }

  /**
   * Register a test to ignore, which has the given spec text, optional tags, and test function value that takes no arguments.
   * This method will register the test for later ignoring via an invocation of one of the <code>execute</code>
   * methods. This method exists to make it easy to ignore an existing test by changing the call to <code>it</code>
   * to <code>ignore</code> without deleting or commenting out the actual test code. The test will not be executed, but a
   * report will be sent that indicates the test was ignored. The name of the test will be a concatenation of the text of all surrounding describers,
   * from outside in, and the passed spec text, with one space placed between each item. (See the documenation
   * for <code>testNames</code> for an example.) The resulting test name must not have been registered previously on
   * this <code>AnyFreeSpec</code> instance.
   *
   * @param specText the specification text, which will be combined with the descText of any surrounding describers
   * to form the test name
   * @param testTags the optional list of tags for this test
   * @param methodName caller's method name
   * @param testFun the test function
   * @throws DuplicateTestNameException if a test with the same name has been registered previously
   * @throws TestRegistrationClosedException if invoked after <code>run</code> has been invoked on this suite
   * @throws NullArgumentException if <code>specText</code> or any passed test tag is <code>null</code>
   */
  private def registerTestToIgnore(specText: String, testTags: List[Tag], methodName: String, testFun: () => Any /* Assertion */, pos: source.Position): Unit = {
    // SKIP-SCALATESTJS,NATIVE-START
    val stackDepth = 4
    val stackDepthAdjustment = -4
    // SKIP-SCALATESTJS,NATIVE-END
    //SCALATESTJS,NATIVE-ONLY val stackDepth = 6
    //SCALATESTJS,NATIVE-ONLY val stackDepthAdjustment = -6
    engine.registerIgnoredTest(specText, Transformer(testFun), Resources.ignoreCannotAppearInsideAnIn, "AnyFreeSpecLike.scala", methodName, stackDepth, stackDepthAdjustment, None, Some(pos), testTags: _*)
  }

  /**
   * Class that supports the registration of tagged tests.
   *
   * <p>
   * Instances of this class are returned by the <code>taggedAs</code> method of 
   * class <code>FreeSpecStringWrapper</code>.
   * </p>
   *
   * @author Bill Venners
   */
  protected final class ResultOfTaggedAsInvocationOnString(specText: String, tags: List[Tag], pos: source.Position) {

    /**
     * Supports tagged test registration.
     *
     * <p>
     * For example, this method supports syntax such as the following:
     * </p>
     *
     * <pre class="stHighlighted">
     * <span class="stQuotedString">"complain on peek"</span> taggedAs(<span class="stType">SlowTest</span>) in { ... }
     *                                       ^
     * </pre>
     *
     * <p>
     * For more information and examples of this method's use, see the <a href="AnyFreeSpec.html">main documentation</a> for trait <code>AnyFreeSpec</code>.
     * </p>
     */
    def in(testFun: => Any /* Assertion */): Unit = {
      registerTestToRun(specText, tags, "in", () => testFun, pos)
    }

    /**
     * Supports registration of tagged, pending tests.
     *
     * <p>
     * For example, this method supports syntax such as the following:
     * </p>
     *
     * <pre class="stHighlighted">
     * <span class="stQuotedString">"complain on peek"</span> taggedAs(<span class="stType">SlowTest</span>) is (pending)
     *                                       ^
     * </pre>
     *
     * <p>
     * For more information and examples of this method's use, see the <a href="AnyFreeSpec.html">main documentation</a> for trait <code>AnyFreeSpec</code>.
     * </p>
     */
    def is(testFun: => PendingStatement): Unit = {
      registerTestToRun(specText, tags, "is", () => { testFun; succeed }, pos)
    }

    /**
     * Supports registration of tagged, ignored tests.
     *
     * <p>
     * For example, this method supports syntax such as the following:
     * </p>
     *
     * <pre class="stHighlighted">
     * <span class="stQuotedString">"complain on peek"</span> taggedAs(<span class="stType">SlowTest</span>) ignore { ... }
     *                                       ^
     * </pre>
     *
     * <p>
     * For more information and examples of this method's use, see the <a href="AnyFreeSpec.html">main documentation</a> for trait <code>AnyFreeSpec</code>.
     * </p>
     */
    def ignore(testFun: => Any /* Assertion */): Unit = {
      registerTestToIgnore(specText, tags, "ignore", () => testFun, pos)
    }
  }       

  /**
   * A class that via an implicit conversion (named <code>convertToFreeSpecStringWrapper</code>) enables
   * methods <code>in</code>, <code>is</code>, <code>taggedAs</code> and <code>ignore</code>,
   * as well as the dash operator (<code>-</code>), to be invoked on <code>String</code>s.
   *
   * @author Bill Venners
   */
  protected final class FreeSpecStringWrapper(string: String, pos: source.Position) {

    /**
     * Register some text that may surround one or more tests. Thepassed function value may contain surrounding text
     * registrations (defined with dash (<code>-</code>)) and/or tests (defined with <code>in</code>). This trait's
     * implementation of this method will register the text (passed to the contructor of <code>FreeSpecStringWrapper</code>
     * and immediately invoke the passed function.
     */
    def -(fun: => Unit): Unit = {

      // SKIP-SCALATESTJS,NATIVE-START
      val stackDepth = 3
      // SKIP-SCALATESTJS,NATIVE-END
      //SCALATESTJS,NATIVE-ONLY val stackDepth = 5

      try {
        registerNestedBranch(string, None, fun, Resources.dashCannotAppearInsideAnIn, "AnyFreeSpecLike.scala", "-", stackDepth, -2, None, Some(pos))
      }
      catch {
        case e: TestFailedException => throw new NotAllowedException(FailureMessages.assertionShouldBePutInsideInClauseNotDashClause, Some(e), e.position.getOrElse(pos))
        case e: TestCanceledException => throw new NotAllowedException(FailureMessages.assertionShouldBePutInsideInClauseNotDashClause, Some(e), e.position.getOrElse(pos))
        case tgce: TestRegistrationClosedException => throw tgce
        case e: DuplicateTestNameException => throw new NotAllowedException(FailureMessages.exceptionWasThrownInDashClause(Prettifier.default, UnquotedString(e.getClass.getName), string, e.getMessage), Some(e), e.position.getOrElse(pos))
        case other: Throwable if (!Suite.anExceptionThatShouldCauseAnAbort(other)) => throw new NotAllowedException(FailureMessages.exceptionWasThrownInDashClause(Prettifier.default, UnquotedString(other.getClass.getName), string, other.getMessage), Some(other), pos)
        case other: Throwable => throw other
      }
    }

    /**
     * Supports test registration.
     *
     * <p>
     * For example, this method supports syntax such as the following:
     * </p>
     *
     * <pre class="stHighlighted">
     * <span class="stQuotedString">"complain on peek"</span> in { ... }
     *                    ^
     * </pre>
     *
     * <p>
     * For more information and examples of this method's use, see the <a href="AnyFreeSpec.html">main documentation</a> for trait <code>AnyFreeSpec</code>.
     * </p>
     */
    def in(f: => Any /* Assertion */): Unit = {
      registerTestToRun(string, List(), "in", () => f, pos)
    }

    /**
     * Supports ignored test registration.
     *
     * <p>
     * For example, this method supports syntax such as the following:
     * </p>
     *
     * <pre class="stHighlighted">
     * <span class="stQuotedString">"complain on peek"</span> ignore { ... }
     *                    ^
     * </pre>
     *
     * <p>
     * For more information and examples of this method's use, see the <a href="AnyFreeSpec.html">main documentation</a> for trait <code>AnyFreeSpec</code>.
     * </p>
     */
    def ignore(f: => Any /* Assertion */): Unit = {
      registerTestToIgnore(string, List(), "ignore", () => f, pos)
    }

    /**
     * Supports pending test registration.
     *
     * <p>
     * For example, this method supports syntax such as the following:
     * </p>
     *
     * <pre class="stHighlighted">
     * <span class="stQuotedString">"complain on peek"</span> is (pending)
     *                    ^
     * </pre>
     *
     * <p>
     * For more information and examples of this method's use, see the <a href="AnyFreeSpec.html">main documentation</a> for trait <code>AnyFreeSpec</code>.
     * </p>
     */
    def is(f: => PendingStatement): Unit = {
      registerTestToRun(string, List(), "is", () => { f; succeed }, pos)
    }

    /**
     * Supports tagged test registration.
     *
     * <p>
     * For example, this method supports syntax such as the following:
     * </p>
     *
     * <pre class="stHighlighted">
     * <span class="stQuotedString">"complain on peek"</span> taggedAs(<span class="stType">SlowTest</span>) in { ... }
     *                    ^
     * </pre>
     *
     * <p>
     * For more information and examples of this method's use, see the <a href="AnyFreeSpec.html">main documentation</a> for trait <code>AnyFreeSpec</code>.
     * </p>
     */
    def taggedAs(firstTestTag: Tag, otherTestTags: Tag*): ResultOfTaggedAsInvocationOnString = {
      val tagList = firstTestTag :: otherTestTags.toList
      new ResultOfTaggedAsInvocationOnString(string, tagList, pos)
    }
  }

  import scala.language.implicitConversions

  /**
   * Implicitly converts <code>String</code>s to <code>FreeSpecStringWrapper</code>, which enables
   * methods <code>in</code>, <code>is</code>, <code>taggedAs</code> and <code>ignore</code>,
   * as well as the dash operator (<code>-</code>), to be invoked on <code>String</code>s.
   */
  // SKIP-DOTTY-START
  protected implicit def convertToFreeSpecStringWrapper(s: String)(implicit pos: source.Position): FreeSpecStringWrapper = new FreeSpecStringWrapper(s, pos)
  // SKIP-DOTTY-END
  //DOTTY-ONLY inline implicit def convertToFreeSpecStringWrapper(s: String): FreeSpecStringWrapper = {
  //DOTTY-ONLY   ${ source.Position.withPosition[FreeSpecStringWrapper]('{(pos: source.Position) => new FreeSpecStringWrapper(s, pos) }) } 
  //DOTTY-ONLY }

  /**
   * A <code>Map</code> whose keys are <code>String</code> names of tagged tests and whose associated values are
   * the <code>Set</code> of tags for the test. If this <code>AnyFreeSpec</code> contains no tags, this method returns an empty <code>Map</code>.
   *
   * <p>
   * This trait's implementation returns tags that were passed as strings contained in <code>Tag</code> objects passed to 
   * <code>taggedAs</code>. 
   * </p>
   * 
   * <p>
   * In addition, this trait's implementation will also auto-tag tests with class level annotations.  
   * For example, if you annotate <code>@Ignore</code> at the class level, all test methods in the class will be auto-annotated with
   * <code>org.scalatest.Ignore</code>.
   * </p>
   */
  override def tags: Map[String, Set[String]] = autoTagClassAnnotations(atomic.get.tagsMap, this)

  /**
   * Run a test. This trait's implementation runs the test registered with the name specified by
   * <code>testName</code>. Each test's name is a concatenation of the text of all describers surrounding a test,
   * from outside in, and the test's  spec text, with one space placed between each item. (See the documentation
   * for <code>testNames</code> for an example.)
   *
   * @param testName the name of one test to execute.
   * @param args the <code>Args</code> for this run
   * @return a <code>Status</code> object that indicates when the test started by this method has completed, and whether or not it failed .
   *
   * @throws NullArgumentException if any of <code>testName</code>, <code>reporter</code>, <code>stopper</code>, or <code>configMap</code>
   *     is <code>null</code>.
   */
  protected override def runTest(testName: String, args: Args): Status = {

    def invokeWithFixture(theTest: TestLeaf): Outcome = {
      val theConfigMap = args.configMap
      val testData = testDataFor(testName, theConfigMap)
      withFixture(
        new NoArgTest {
          val name = testData.name
          def apply(): Outcome = { theTest.testFun() }
          val configMap = testData.configMap
          val scopes = testData.scopes
          val text = testData.text
          val tags = testData.tags
          val pos = testData.pos
        }
      )
    }

    runTestImpl(thisSuite, testName, args, true, invokeWithFixture)
  }

  /**
   * Run zero to many of this <code>AnyFreeSpec</code>'s tests.
   *
   * <p>
   * This method takes a <code>testName</code> parameter that optionally specifies a test to invoke.
   * If <code>testName</code> is <code>Some</code>, this trait's implementation of this method
   * invokes <code>runTest</code> on this object, passing in:
   * </p>
   *
   * <ul>
   * <li><code>testName</code> - the <code>String</code> value of the <code>testName</code> <code>Option</code> passed
   *   to this method</li>
   * <li><code>reporter</code> - the <code>Reporter</code> passed to this method, or one that wraps and delegates to it</li>
   * <li><code>stopper</code> - the <code>Stopper</code> passed to this method, or one that wraps and delegates to it</li>
   * <li><code>configMap</code> - the <code>configMap</code> passed to this method, or one that wraps and delegates to it</li>
   * </ul>
   *
   * <p>
   * This method takes a <code>Set</code> of tag names that should be included (<code>tagsToInclude</code>), and a <code>Set</code>
   * that should be excluded (<code>tagsToExclude</code>), when deciding which of this <code>Suite</code>'s tests to execute.
   * If <code>tagsToInclude</code> is empty, all tests will be executed
   * except those those belonging to tags listed in the <code>tagsToExclude</code> <code>Set</code>. If <code>tagsToInclude</code> is non-empty, only tests
   * belonging to tags mentioned in <code>tagsToInclude</code>, and not mentioned in <code>tagsToExclude</code>
   * will be executed. However, if <code>testName</code> is <code>Some</code>, <code>tagsToInclude</code> and <code>tagsToExclude</code> are essentially ignored.
   * Only if <code>testName</code> is <code>None</code> will <code>tagsToInclude</code> and <code>tagsToExclude</code> be consulted to
   * determine which of the tests named in the <code>testNames</code> <code>Set</code> should be run. For more information on trait tags, see the main documentation for this trait.
   * </p>
   *
   * <p>
   * If <code>testName</code> is <code>None</code>, this trait's implementation of this method
   * invokes <code>testNames</code> on this <code>Suite</code> to get a <code>Set</code> of names of tests to potentially execute.
   * (A <code>testNames</code> value of <code>None</code> essentially acts as a wildcard that means all tests in
   * this <code>Suite</code> that are selected by <code>tagsToInclude</code> and <code>tagsToExclude</code> should be executed.)
   * For each test in the <code>testName</code> <code>Set</code>, in the order
   * they appear in the iterator obtained by invoking the <code>elements</code> method on the <code>Set</code>, this trait's implementation
   * of this method checks whether the test should be run based on the <code>tagsToInclude</code> and <code>tagsToExclude</code> <code>Set</code>s.
   * If so, this implementation invokes <code>runTest</code>, passing in:
   * </p>
   *
   * <ul>
   * <li><code>testName</code> - the <code>String</code> name of the test to run (which will be one of the names in the <code>testNames</code> <code>Set</code>)</li>
   * <li><code>reporter</code> - the <code>Reporter</code> passed to this method, or one that wraps and delegates to it</li>
   * <li><code>stopper</code> - the <code>Stopper</code> passed to this method, or one that wraps and delegates to it</li>
   * <li><code>configMap</code> - the <code>configMap</code> passed to this method, or one that wraps and delegates to it</li>
   * </ul>
   *
   * @param testName an optional name of one test to run. If <code>None</code>, all relevant tests should be run.
   *                 I.e., <code>None</code> acts like a wildcard that means run all relevant tests in this <code>Suite</code>.
   * @param args the <code>Args</code> for this run
   * @return a <code>Status</code> object that indicates when all tests started by this method have completed, and whether or not a failure occurred.
   *
   * @throws NullArgumentException if any of the passed parameters is <code>null</code>.
   * @throws IllegalArgumentException if <code>testName</code> is defined, but no test with the specified test name
   *     exists in this <code>Suite</code>
   */
  protected override def runTests(testName: Option[String], args: Args): Status = {
    runTestsImpl(thisSuite, testName, args, info, true, runTest)
  }

  /**
   * An immutable <code>Set</code> of test names. If this <code>AnyFreeSpec</code> contains no tests, this method returns an
   * empty <code>Set</code>.
   *
   * <p>
   * This trait's implementation of this method will return a set that contains the names of all registered tests. The set's
   * iterator will return those names in the order in which the tests were registered. Each test's name is composed
   * of the concatenation of the text of each surrounding describer, in order from outside in, and the text of the
   * example itself, with all components separated by a space. For example, consider this <code>AnyFreeSpec</code>:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalatest.freespec.AnyFreeSpec
   * <br/><span class="stReserved">class</span> <span class="stType">StackSpec</span> <span class="stReserved">extends</span> <span class="stType">AnyFreeSpec</span> {
   *   <span class="stQuotedString">"A Stack"</span> - {
   *     <span class="stQuotedString">"when not empty"</span> - {
   *       <span class="stQuotedString">"must allow me to pop"</span> in {}
   *     }
   *     <span class="stQuotedString">"when not full"</span> - {
   *       <span class="stQuotedString">"must allow me to push"</span> in {}
   *     }
   *   }
   * }
   * </pre>
   *
   * <p>
   * Invoking <code>testNames</code> on this <code>AnyFreeSpec</code> will yield a set that contains the following
   * two test name strings:
   * </p>
   *
   * <pre>
   * "A Stack when not empty must allow me to pop"
   * "A Stack when not full must allow me to push"
   * </pre>
   */
  override def testNames: Set[String] = {
    InsertionOrderSet(atomic.get.testNamesList)
  }

  override def run(testName: Option[String], args: Args): Status = {
    runImpl(thisSuite, testName, args, super.run)
  }

  /**
   * Supports shared test registration in <code>AnyFreeSpec</code>s.
   *
   * <p>
   * This field enables syntax such as the following:
   * </p>
   *
   * <pre class="stHighlighted">
   * behave like nonFullStack(stackWithOneItem)
   * ^
   * </pre>
   *
   * <p>
   * For more information and examples of the use of <cod>behave</code>, see the <a href="#sharedTests">Shared tests section</a>
   * in the main documentation for this trait.
   * </p>
   */
  protected val behave = new BehaveWord
  
  /**
   * <strong>The <code>styleName</code> lifecycle method has been deprecated and will be removed in a future version of ScalaTest.</strong>
   *
   * <p>This method was used to support the chosen styles feature, which was deactivated in 3.1.0. The internal modularization of ScalaTest in 3.2.0
   * will replace chosen styles as the tool to encourage consistency across a project. We do not plan a replacement for <code>styleName</code>.</p>
   */
  @deprecated("The styleName lifecycle method has been deprecated and will be removed in a future version of ScalaTest with no replacement.", "3.1.0")
  final override val styleName: String = "org.scalatest.FreeSpec"
    
  override def testDataFor(testName: String, theConfigMap: ConfigMap = ConfigMap.empty): TestData = createTestDataFor(testName, theConfigMap, this)
}
