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

import org.junit.platform.launcher.{TestExecutionListener, TestIdentifier}
import org.junit.platform.engine.{EngineExecutionListener, TestDescriptor, TestExecutionResult}
import org.scalatest.{Reporter, StatefulStatus, Tracker}
import org.scalatest.events.{MotionToSuppress, SeeStackDepthException, TestFailed, TestIgnored, TestStarting, TestSucceeded, TopOfMethod}
import org.scalatest.exceptions.PayloadField
import JUnitHelper.getIndentedTextForTest

import java.util.Collections
import java.util.HashSet
import java.util.regex.Pattern

private[junit5] class JUnitExecutionListener(report: Reporter,
                                                 config: Map[String, Any],
                                                 theTracker: Tracker,
                                                 status: StatefulStatus) extends TestExecutionListener {

  val failedTests = Collections.synchronizedSet(new HashSet[String])
  def getTopOfMethod(className: String, methodName: String) = Some(TopOfMethod(className, "public void " + className + "." + methodName + "()"))

  override def executionFinished(testIdentifier: TestIdentifier, testExecutionResult: TestExecutionResult): Unit = {
    if (testIdentifier.isTest) {
      val (testName, testClass, testClassName) =
        parseTestDescription(testIdentifier.getUniqueId)
      if (testExecutionResult.getStatus == TestExecutionResult.Status.SUCCESSFUL) {
        // success
        val formatter = getIndentedTextForTest(testName, 1, true)
        report(TestSucceeded(theTracker.nextOrdinal(), testClassName, testClass, Some(testClass), testName, testName, Vector.empty, None, Some(formatter), getTopOfMethod(testClass, testName)))
        // TODO: can I add a duration?
      }
      else {
        // fail or aborted
        failedTests.add(testIdentifier.getDisplayName)
        val throwable = Option(testExecutionResult.getThrowable.orElseGet(null))
        val message = throwable.map(_.toString).getOrElse(Resources.jUnitTestFailed())
        val formatter = getIndentedTextForTest(testName, 1, true)
        val payload =
          throwable match {
            case optPayload: PayloadField =>
              optPayload.payload
            case _ =>
              None
          }
        report(TestFailed(theTracker.nextOrdinal(), message, testClassName, testClass, Some(testClass), testName, testName, Vector.empty, Vector.empty, throwable, None, Some(formatter), Some(SeeStackDepthException), None, payload))
        // TODO: can I add a duration?
        status.setFailed()
      }
    }
  }

  override def executionSkipped(testIdentifier: TestIdentifier, reason: String): Unit = {
    if (testIdentifier.isTest) {
      val (testName, testClass, testClassName) =
        parseTestDescription(testIdentifier.getUniqueId)
      val formatter = getIndentedTextForTest(testName, 1, true)
      // TODO: What to do with reason?
      report(TestIgnored(theTracker.nextOrdinal(), testClassName, testClass, Some(testClass), testName, testName, Some(formatter), getTopOfMethod(testClass, testName)))
    }
  }

  override def executionStarted(testIdentifier: TestIdentifier): Unit = {
    if (testIdentifier.isTest) {
      val (testName, testClass, testClassName) =
        parseTestDescription(testIdentifier.getUniqueId)

      report(TestStarting(theTracker.nextOrdinal(), testClassName, testClass, Some(testClass), testName, testName, Some(MotionToSuppress), getTopOfMethod(testClass, testName)))
    }
  }

  //private val TEST_DESCRIPTION_PATTERN = Pattern.compile("""^(.*)\((.*)\)""")
  private val TEST_DESCRIPTION_PATTERN = Pattern.compile("""\[(.*)\]/\[class:(.*)\]/\[method:(.*)\(\)\]""")

  // ###uniqueId: [engine:junit-jupiter]/[class:org.scalatestplus.junit.helpers5.HappySuite]/[method:verifySomething()]

  private def parseTestDescription(displayName: String):
  (String, String, String) = {
    val matcher =
      TEST_DESCRIPTION_PATTERN.matcher(displayName)

    if (!matcher.find())
      throw new RuntimeException("unexpected displayName [" +
        displayName + "]")

    val testName = matcher.group(3)
    val testClass = matcher.group(2)
    val testClassName = testClass.replaceAll(".*\\.", "")

    (testName, testClass, testClassName)
  }

}
