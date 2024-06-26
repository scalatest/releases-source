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
package org.scalatestplus.jmock

import org.jmock.Mockery
import org.jmock.lib.legacy.ClassImposteriser
import scala.reflect.ClassTag

/**
 * Class that wraps and manages the lifecycle of a single <code>org.jmock.Mockery</code> context object,
 * provides some basic syntax sugar for using <a href="http://www.jmock.org/" target="_blank">JMock</a>
 * in Scala.
 *
 * <p>
 * Using the JMock API directly, you first need a <code>Mockery</code> context object:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> context = <span class="stReserved">new</span> <span class="stType">Mockery</span>
 * </pre>
 *
 * <p>
 * <code>JMockCycle</code> uses jMock's <code>ClassImposterizer</code> to support mocking of classes, so the following line
 * would also be needed if you wanted that functionality as well:
 * </p>
 *
 * <pre class="stHighlighted">
 * context.setImposteriser(ClassImposteriser.INSTANCE)
 * </pre>
 *
 * <p>
 * When using this class, you would instead create an instance of this class (which will create and
 * wrap a <code>Mockery</code> object) and import its members, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> cycle = <span class="stReserved">new</span> <span class="stType">JMockCycle</span>
 * <span class="stReserved">import</span> cycle._
 * </pre>
 *
 * <p>
 * Using the JMock API directly, you would create a mock object like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> mockCollaborator = context.mock(classOf[<span class="stType">Collaborator</span>])
 * </pre>
 *
 * <p>
 * Having imported the members of an instance of this class, you can shorten that to:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> mockCollaborator = mock[<span class="stType">Collaborator</span>]
 * </pre>
 *
 * <p>
 * After creating mocks, you set expectations on them, using syntax like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * context.checking(
 *   <span class="stReserved">new</span> <span class="stType">Expectations</span>() {
 *     oneOf (mockCollaborator).documentAdded(<span class="stQuotedString">"Document"</span>)
 *     exactly(<span class="stLiteral">3</span>).of (mockCollaborator).documentChanged(<span class="stQuotedString">"Document"</span>)
 *    }
 *  )
 * </pre>
 *
 * <p>
 * Having imported the members of an instance of this class, you can shorten this step to:
 * </p>
 *
 * <pre class="stHighlighted">
 * expecting { e => <span class="stReserved">import</span> e._
 *   oneOf (mockCollaborator).documentAdded(<span class="stQuotedString">"Document"</span>)
 *   exactly(<span class="stLiteral">3</span>).of (mockCollaborator).documentChanged(<span class="stQuotedString">"Document"</span>)
 * }
 * </pre>
 *
 * <p>
 * The <code>expecting</code> method will create a new <code>Expectations</code> object, pass it into
 * the function you provide, which sets the expectations. After the function returns, the <code>expecting</code>
 * method will pass the <code>Expectations</code> object to the <code>checking</code>
 * method of its internal <code>Mockery</code> context.
 * </p>
 *
 * <p>
 * The <code>expecting</code> method passes an instance of class
 * <code>org.scalatest.mock.JMockExpectations</code> to the function you pass into
 * <code>expectations</code>. <code>JMockExpectations</code> extends <code>org.jmock.Expectations</code> and
 * adds several overloaded <code>withArg</code> methods. These <code>withArg</code> methods simply
 * invoke corresponding <code>with</code> methods on themselves. Because <code>with</code> is
 * a keyword in Scala, to invoke these directly you must surround them in back ticks, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * oneOf (mockCollaborator).documentAdded(`<span class="stReserved">with</span>`(<span class="stQuotedString">"Document"</span>))
 * </pre>
 *
 * <p>
 * By importing the members of the passed <code>JMockExpectations</code> object, you can
 * instead call <code>withArg</code> with no back ticks needed:
 * </p>
 *
 * <pre class="stHighlighted">
 * oneOf (mockCollaborator).documentAdded(withArg(<span class="stQuotedString">"Document"</span>))
 * </pre>
 *
 * <p>
 * Once you've set expectations on the mock objects, when using the JMock API directly, you use the mock, then invoke
 * <code>assertIsSatisfied</code> on the <code>Mockery</code> context to make sure the mock
 * was used in accordance with the expectations you set on it. Here's how that looks:
 * </p>
 *
 * <pre class="stHighlighted">
 * classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 * classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 * classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 * classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 * context.assertIsSatisfied()
 * </pre>
 *
 * <p>
 * This class enables you to use the following, more declarative syntax instead:
 * </p>
 *
 * <pre class="stHighlighted">
 * whenExecuting {
 *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 * }
 * </pre>
 *
 * <p>
 * The <code>whenExecuting</code> method will execute the passed function, then
 * invoke <code>assertIsSatisfied</code> on its internal <code>Mockery</code>
 * context object.
 * </p>
 *
 * <p>
 * To summarize, here's what a typical test using <code>JMockCycle</code> looks like:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> cycle = <span class="stReserved">new</span> <span class="stType">JMockCycle</span>
 * <span class="stReserved">import</span> cycle._
 * <br/><span class="stReserved">val</span> mockCollaborator = mock[<span class="stType">Collaborator</span>]
 * <br/>expecting { e => <span class="stReserved">import</span> e._
 *   oneOf (mockCollaborator).documentAdded(<span class="stQuotedString">"Document"</span>)
 *   exactly(<span class="stLiteral">3</span>).of (mockCollaborator).documentChanged(<span class="stQuotedString">"Document"</span>)
 * }
 * <br/>whenExecuting {
 *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
 * }
 * </pre>
 *
 * <p>
 * ScalaTest also provides a <a href="JMockCycleFixture.html"><code>JMockCycleFixture</code></a> trait, which
 * will pass a new <code>JMockCycle</code> into each test that needs one.
 * </p>
 *
 * @author Bill Venners
 */
final class JMockCycle {

  private val context = new Mockery
  context.setImposteriser(ClassImposteriser.INSTANCE)

  /**
   * Invokes the <code>mock</code> method on this <code>JMockCycle</code>'s internal
   * <code>Mockery</code> context object, passing in a class instance for the
   * specified type parameter.
   *
   * <p>
   * Using the JMock API directly, you create a mock with:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = context.mock(classOf[<span class="stType">Collaborator</span>])
   * </pre>
   *
   * <p>
   * Having imported the members of an instance of this class, you can shorten that to:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = mock[<span class="stType">Collaborator</span>]
   * </pre>
   */
  def mock[T <: AnyRef](implicit classTag: ClassTag[T]): T = {
    context.mock(classTag.runtimeClass.asInstanceOf[Class[T]])
  }

  /**
   * Sets expectations on mock objects.
   *
   * <p>
   * After creating mocks, you set expectations on them, using syntax like this:
   * </p>
   *
   * <pre class="stHighlighted">
   * context.checking(
   *   <span class="stReserved">new</span> <span class="stType">Expectations</span>() {
   *     oneOf (mockCollaborator).documentAdded(<span class="stQuotedString">"Document"</span>)
   *     exactly(<span class="stLiteral">3</span>).of (mockCollaborator).documentChanged(<span class="stQuotedString">"Document"</span>)
   *    }
   *  )
   * </pre>
   *
   * <p>
   * Having imported the members of an instance of this class, you can shorten this step to:
   * </p>
   *
   * <pre class="stHighlighted">
   * expecting { e => <span class="stReserved">import</span> e._
   *   oneOf (mockCollaborator).documentAdded(<span class="stQuotedString">"Document"</span>)
   *   exactly(<span class="stLiteral">3</span>).of (mockCollaborator).documentChanged(<span class="stQuotedString">"Document"</span>)
   * }
   * </pre>
   *
   * <p>
   * The <code>expecting</code> method will create a new <code>Expectations</code> object, pass it into
   * the function you provide, which sets the expectations. After the function returns, the <code>expecting</code>
   * method will pass the <code>Expectations</code> object to the <code>checking</code>
   * method of its internal <code>Mockery</code> context.
   * </p>
   *
   * <p>
   * This method passes an instance of class <code>org.scalatest.mock.JMockExpectations</code> to the
   * passed function. <code>JMockExpectations</code> extends <code>org.jmock.Expectations</code> and
   * adds several overloaded <code>withArg</code> methods. These <code>withArg</code> methods simply
   * invoke corresponding <code>with</code> methods on themselves. Because <code>with</code> is
   * a keyword in Scala, to invoke these directly you must surround them in back ticks, like this:
   * </p>
   *
   * <pre class="stHighlighted">
   * oneOf (mockCollaborator).documentAdded(`<span class="stReserved">with</span>`(<span class="stQuotedString">"Document"</span>))
   * </pre>
   *
   * <p>
   * By importing the members of the passed <code>JMockExpectations</code> object, you can
   * instead call <code>withArg</code> with no back ticks needed:
   * </p>
   *
   * <pre class="stHighlighted">
   * oneOf (mockCollaborator).documentAdded(withArg(<span class="stQuotedString">"Document"</span>))
   * </pre>
   *
   * @param fun a function that sets expectations on the passed <code>JMockExpectations</code>
   *    object
   */
  def expecting(fun: JMockExpectations => Unit): Unit = {
    val e = new JMockExpectations
    fun(e)
    context.checking(e)
  }

  /**
   * Executes code using mocks with expectations set.
   * 
   * <p>
   * Once you've set expectations on the mock objects, when using the JMock API directly, you use the mock, then invoke
   * <code>assertIsSatisfied</code> on the <code>Mockery</code> context to make sure the mock
   * was used in accordance with the expectations you set on it. Here's how that looks:
   * </p>
   *
   * <pre class="stHighlighted">
   * classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
   * classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
   * classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
   * classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
   * context.assertIsSatisfied()
   * </pre>
   *
   * <p>
   * This class enables you to use the following, more declarative syntax instead:
   * </p>
   *
   * <pre class="stHighlighted">
   * whenExecuting {
   *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
   *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
   *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
   *   classUnderTest.addDocument(<span class="stQuotedString">"Document"</span>, <span class="stReserved">new</span> <span class="stType">Array[Byte]</span>(<span class="stLiteral">0</span>))
   * }
   * </pre>
   *
   * <p>
   * The <code>whenExecuting</code> method will execute the passed function, then
   * invoke <code>assertIsSatisfied</code> on its internal <code>Mockery</code>
   * context object.
   * </p>
   *
   * @param fun the code to execute under previously set expectations
   * @throws org.mock.ExpectationError if an expectation is not met
   */
  def whenExecuting(fun: => Unit): Unit = {
    fun
    context.assertIsSatisfied()
  }
}
