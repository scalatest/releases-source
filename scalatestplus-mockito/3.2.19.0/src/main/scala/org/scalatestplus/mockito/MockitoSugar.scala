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
package org.scalatestplus.mockito

import org.mockito.Mockito.{mock => mockitoMock}
import reflect.ClassTag
import org.mockito.stubbing.Answer
import org.mockito.ArgumentCaptor
import org.mockito.MockSettings

/**
 * Trait that provides some basic syntax sugar for <a href="http://mockito.org/" target="_blank">Mockito</a>.
 *
 * <p>
 * Using the Mockito API directly, you create a mock with:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> mockCollaborator = mock(classOf[<span class="stType">Collaborator</span>])
 * </pre>
 *
 * <p>
 * Using this trait, you can shorten that to:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> mockCollaborator = mock[<span class="stType">Collaborator</span>]
 * </pre>
 *
 * <p>
 * This trait also provides shorthands for the three other (non-deprecated) overloaded <code>mock</code> methods,
 * which allow you to pass in a default answer, a name, or settings.
 * </p>
 *
 * @author Bill Venners
 * @author Chua Chee Seng
 */
trait MockitoSugar {

  /**
   * Invokes the <code>mock(classToMock: Class[T])</code> method on the <code>Mockito</code> companion object (<em>i.e.</em>, the
   * static <code>mock(java.lang.Class<T> classToMock)</code> method in Java class <code>org.mockito.Mockito</code>).
   *
   * <p>
   * Using the Mockito API directly, you create a mock with:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = mock(classOf[<span class="stType">Collaborator</span>])
   * </pre>
   *
   * <p>
   * Using this method, you can shorten that to:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = mock[<span class="stType">Collaborator</span>]
   * </pre>
   */
  def mock[T <: AnyRef](implicit classTag: ClassTag[T]): T = {
    mockitoMock(classTag.runtimeClass.asInstanceOf[Class[T]])
  }
  
  /**
   * Invokes the <code>mock(classToMock: Class[T], defaultAnswer: Answer[_])</code> method on the <code>Mockito</code> companion object (<em>i.e.</em>, the
   * static <code>mock(java.lang.Class<T> classToMock, org.mockito.stubbing.Answer defaultAnswer)</code> method in Java class <code>org.mockito.Mockito</code>).
   *
   * <p>
   * Using the Mockito API directly, you create a mock with:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = mock(classOf[<span class="stType">Collaborator</span>], defaultAnswer)
   * </pre>
   *
   * <p>
   * Using this method, you can shorten that to:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = mock[<span class="stType">Collaborator</span>](defaultAnswer)
   * </pre>
   */
  def mock[T <: AnyRef](defaultAnswer: Answer[_])(implicit classTag: ClassTag[T]): T = {
    mockitoMock(classTag.runtimeClass.asInstanceOf[Class[T]], defaultAnswer)
  }
  
  /**
   * Invokes the <code>mock(classToMock: Class[T], mockSettings: MockSettings)</code> method on the <code>Mockito</code> companion object (<em>i.e.</em>, the
   * static <code>mock(java.lang.Class<T> classToMock, org.mockito.MockSettings mockSettings)</code> method in Java class <code>org.mockito.Mockito</code>).
   *
   * <p>
   * Using the Mockito API directly, you create a mock with:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = mock(classOf[<span class="stType">Collaborator</span>], mockSettings)
   * </pre>
   *
   * <p>
   * Using this method, you can shorten that to:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = mock[<span class="stType">Collaborator</span>](mockSettings)
   * </pre>
   */
  def mock[T <: AnyRef](mockSettings: MockSettings)(implicit classTag: ClassTag[T]): T = {
    mockitoMock(classTag.runtimeClass.asInstanceOf[Class[T]], mockSettings)
  }
  
  /**
   * Invokes the <code>mock(classToMock: Class[T], name: String)</code> method on the <code>Mockito</code> companion object (<em>i.e.</em>, the
   * static <code>mock(java.lang.Class<T> classToMock, java.lang.String name)</code> method in Java class <code>org.mockito.Mockito</code>).
   *
   * <p>
   * Using the Mockito API directly, you create a mock with:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = mock(classOf[<span class="stType">Collaborator</span>], name)
   * </pre>
   *
   * <p>
   * Using this method, you can shorten that to:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> mockCollaborator = mock[<span class="stType">Collaborator</span>](name)
   * </pre>
   */
  def mock[T <: AnyRef](name: String)(implicit classTag: ClassTag[T]): T = {
    mockitoMock(classTag.runtimeClass.asInstanceOf[Class[T]], name)
  }


  /**
   * Invokes the <code>forClass(classToMock: Class[T])</code> method on the <code>ArgumentCaptor</code> companion object (<em>i.e.</em>, the
   * static <code>forClass(java.lang.Class<T> classToMock)</code> method in Java class <code>org.mockito.Mockito</code>).
   *
   * <p>
   * Using the Mockito API directly, you create a ArgumentCaptor with:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> collaboratorCaptor = ArgumentCaptor.forClass(classOf[<span class="stType">Collaborator</span>])
   * </pre>
   *
   * <p>
   * Using this method, you can shorten that to:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> collaboratorCaptor = capture[<span class="stType">Collaborator</span>]
   * </pre>
   */
  def capture[T <: AnyRef](implicit classTag: ClassTag[T]): ArgumentCaptor[T] = {
    ArgumentCaptor.forClass(classTag.runtimeClass.asInstanceOf[Class[T]])
  }

  /**
   * Invoke the <code>capture(): T</code> method on the <code>ArgumentCaptor</code> for convenience.
   */
  implicit def invokeCaptureOnArgumentCaptor[T](captor: ArgumentCaptor[T]): T = {
    captor.capture()
  }
}

/**
 * Companion object that facilitates the importing of <code>MockitoSugar</code> members as 
 * an alternative to mixing it in. One use case is to import <code>MockitoSugar</code> members so you can use
 * them in the Scala interpreter.
 */
// TODO: Fill in an example
object MockitoSugar extends MockitoSugar

