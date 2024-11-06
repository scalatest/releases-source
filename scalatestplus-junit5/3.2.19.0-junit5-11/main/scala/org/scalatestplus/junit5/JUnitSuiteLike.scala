/*
 * Copyright 2001-2023 Artima, Inc.
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
package org.scalatestplus.junit5;

import org.scalatest.{Resources => _, _}

import java.lang.reflect.{Method, Modifier}
import JUnitHelper.autoTagClassAnnotations
import org.junit.platform.launcher.core.LauncherFactory
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request
import org.junit.platform.engine.discovery.DiscoverySelectors.{selectClass, selectMethod}

import collection.immutable.TreeSet

/**
 * Implementation trait for class <code>JUnitSuite</code>, which represents
 * a suite of tests that can be run with either JUnit 5 or ScalaTest.
 *
 * <p>
 * <a href="JUnitSuite.html"><code>JUnitSuite</code></a> is a class, not a
 * trait, to minimize compile time given there is a slight compiler overhead to
 * mixing in traits compared to extending classes. If you need to mix the
 * behavior of <code>JUnitSuite</code> into some other class, you can use this
 * trait instead, because class <code>JUnitSuite</code> does nothing more than
 * extend this trait.
 * </p>
 *
 * <p>
 * See the documentation of the class for a <a href="JUnitSuite.html">detailed
 * overview of <code>JUnitSuite</code></a>.
 * </p>
 *
 * @author Bill Venners
 * @author Chua Chee Seng
 */
trait JUnitSuiteLike extends Suite with AssertionsForJUnit { thisSuite =>

  // This is volatile, because who knows what Thread JUnit will fire through this.
  @volatile private var theTracker = new Tracker

  /**
   * Throws <code>UnsupportedOperationException</code>, because this method is unused by this
   * trait, given this trait's <code>run</code> method delegates to JUnit to run
   * its tests.
   *
   * <p>
   * The main purpose of this method implementation is to render a compiler error an attempt
   * to mix in a trait that overrides <code>runNestedSuites</code>. Because this
   * trait does not actually use <code>runNestedSuites</code>, the attempt to mix
   * in behavior would very likely not work.
   * </p>
   *
   * @param args the <code>Args</code> for this run
   *
   * @throws UnsupportedOperationException always.
   */
  override final protected def runNestedSuites(args: Args): Status = {

    throw new UnsupportedOperationException
  }

  /**
   * Throws <code>UnsupportedOperationException</code>, because this method is unused by this
   * trait, given this trait's <code>run</code> method delegates to JUnit to run
   * its tests.
   *
   * <p>
   * The main purpose of this method implementation is to render a compiler error an attempt
   * to mix in a trait that overrides <code>runTests</code>. Because this
   * trait does not actually use <code>runTests</code>, the attempt to mix
   * in behavior would very likely not work.
   * </p>
   *
   * @param testName an optional name of one test to run. If <code>None</code>, all relevant tests should be run.
   *                 I.e., <code>None</code> acts like a wildcard that means run all relevant tests in this <code>Suite</code>.
   * @param args the <code>Args</code> for this run
   *
   * @throws UnsupportedOperationException always.
   */
  override protected final def runTests(testName: Option[String], args: Args): Status = {
    throw new UnsupportedOperationException
  }

  /**
   * Throws <code>UnsupportedOperationException</code>, because this method is unused by this
   * trait, given this traits's <code>run</code> method delegates to JUnit to run
   * its tests.
   *
   * <p>
   * The main purpose of this method implementation is to render a compiler error an attempt
   * to mix in a trait that overrides <code>runTest</code>. Because this
   * trait does not actually use <code>runTest</code>, the attempt to mix
   * in behavior would very likely not work.
   * </p>
   *
   * @param testName the name of one test to run.
   * @param args the <code>Args</code> for this run
   *
   * @throws UnsupportedOperationException always.
   */
  override protected final def runTest(testName: String, args: Args): Status = {
    throw new UnsupportedOperationException
  }

  /**
   * Returns the set of test names that will be executed by JUnit when <code>run</code> is invoked
   * on an instance of this class, or the instance is passed directly to JUnit for running.
   *
   * <p>
   * The iterator obtained by invoking <code>elements</code> on this
   * returned <code>Set</code> will produce the test names in their <em>natural order</em>, as determined by <code>String</code>'s
   * <code>compareTo</code> method. Nevertheless, this method is not consulted by JUnit when it
   * runs the tests, and JUnit may run the tests in any order.
   * </p>
   */
  override def testNames: Set[String] = {

    // TODO: Check to see if JUnit discovers static methods, private methods, etc.
    // Also, JUnit has something about test methods that can be parameterized. Will
    // eventually need to find those here too. What a pain.
    def isTestMethod(m: Method) = {

      val isInstanceMethod = !Modifier.isStatic(m.getModifiers())

      val paramTypes = m.getParameterTypes
      val hasNoParams = paramTypes.length == 0
      // val hasVoidReturnType = m.getReturnType == Void.TYPE
      val hasTestAnnotation = m.getAnnotation(classOf[org.junit.jupiter.api.Test]) != null

      isInstanceMethod && hasNoParams && hasTestAnnotation
    }

    val testNameArray =
      for (m <- getClass.getMethods; if isTestMethod(m))
        yield m.getName

    TreeSet[String]() ++ testNameArray
  }

  /**
   * Returns the number of tests expected to be run by JUnit when <code>run</code> is invoked
   * on this <code>JUnitSuite</code>.
   *
   * <p>
   * If <code>tagsToInclude</code> in the passed <code>Filter</code> is defined, this class's
   * implementation of this method returns 0. Else this class's implementation of this method
   * returns the size of the set returned by <code>testNames</code> on the current instance,
   * less the number of tests that were annotated with <code>org.junit.Ignore</code>.
   * </p>
   *
   * @param filter a <code>Filter</code> for test filtering
   * @return number of expected test count
   */
  override def expectedTestCount(filter: Filter) =
    if (filter.tagsToInclude.isDefined) 0 else (testNames.size - tags.size)

  /**
   * Overrides to return just tests that have org.junit.Ignore on them, but calls it org.scalatest.Ignore.
   * It also auto-tags suite level annotation.
   */
  override def tags: Map[String, Set[String]] = {

    val elements =
      for (testName <- testNames; if hasDisabledTag(testName))
        yield testName -> Set("org.scalatest.Ignore")

    autoTagClassAnnotations(Map() ++ elements, this)
  }

  private def getMethodForJUnitTestName(testName: String) =
    getClass.getMethod(testName, new Array[Class[_]](0): _*)

  private def hasDisabledTag(testName: String) = getMethodForJUnitTestName(testName).getAnnotation(classOf[org.junit.jupiter.api.Disabled]) != null

  /**
   * Overrides to retrieve suite and test tags from annotations.
   *
   * @param testName the name of the test for which to return a <code>TestData</code> instance
   * @param theConfigMap the config map to include in the returned <code>TestData</code>
   * @return a <code>TestData</code> instance for the specified test, which includes the specified config map
   */
  override def testDataFor(testName: String, theConfigMap: ConfigMap = ConfigMap.empty): TestData = {
    val suiteTags = for {
      a <- this.getClass.getAnnotations
      annotationClass = a.annotationType
      if annotationClass.isAnnotationPresent(classOf[TagAnnotation])
    } yield annotationClass.getName
    val testTags: Set[String] =
      try {
        if (hasDisabledTag(testName))
          Set("org.scalatest.Ignore")
        else
          Set.empty[String]
      }
      catch {
        case e: IllegalArgumentException => Set.empty[String]
      }
    new TestData {
      val configMap = theConfigMap
      val name = testName
      val scopes = Vector.empty
      val text = testName
      val tags = Set.empty ++ suiteTags ++ testTags
      val pos = None
    }
  }

  /**
   * Overrides to use JUnit 5 to run the test(s).
   *
   * @param testName an optional name of one test to run. If <code>None</code>, all relevant tests should be run.
   *                 I.e., <code>None</code> acts like a wildcard that means run all relevant tests in this <code>Suite</code>.
   * @param args the <code>Args</code> for this run
   * @return a <code>Status</code> object that indicates when all tests and nested suites started by this method have completed, and whether or not a failure occurred.
   *
   */
  override def run(testName: Option[String], args: Args): Status = {

    import args._

    theTracker = tracker
    val status = new StatefulStatus

    if (!filter.tagsToInclude.isDefined) {
      // JUnit 5 references:
      // https://junit.org/junit5/docs/current/api/org.junit.platform.engine/org/junit/platform/engine/TestEngine.html
      // https://junit.org/junit5/docs/current/api/org.junit.platform.engine/org/junit/platform/engine/EngineExecutionListener.html
      // https://junit.org/junit5/docs/current/api/org.junit.platform.console/org/junit/platform/console/ConsoleLauncher.html
      // https://www.javatips.net/api/org.junit.jupiter.engine.jupitertestengine
      // https://www.javatips.net/api/junit5-master/junit-jupiter-migration-support/src/test/java/org/junit/jupiter/migrationsupport/rules/ExpectedExceptionSupportTests.java
      // https://junit.org/junit5/docs/5.7.2/api/org.junit.platform.testkit/org/junit/platform/testkit/engine/EngineTestKit.html
      // https://junit.org/junit5/docs/5.8.2/api/org.junit.platform.launcher/org/junit/platform/launcher/Launcher.html

      val testClass = this.getClass

      val listener = new JUnitExecutionListener(reporter, configMap, tracker, status)
      val req = request()
        .selectors(
          testName.map { tn =>
            if (testNames.contains(tn))
              selectMethod(testClass, tn)
            else
              throw new IllegalArgumentException(Resources.testNotFound(testName))
          }.getOrElse(selectClass(testClass)))
        .build()
      val launcher = LauncherFactory.create()
      launcher.execute(req, listener)
    }

    status.setCompleted()
    status
  }
}
