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
package org.scalatest.matchers.dsl

import scala.collection.GenTraversable
import org.scalatest.enablers.Containing
import org.scalatest.enablers.Aggregating
import org.scalatest.enablers.Sequencing
import org.scalatest.enablers.KeyMapping
import org.scalatest.enablers.ValueMapping
import org.scalatest.FailureMessages
import org.scalatest.UnquotedString
import org.scalatest.exceptions.NotAllowedException
import org.scalatest.Assertion
import org.scalatest.matchers.MatchersHelper.indicateSuccess
import org.scalatest.matchers.MatchersHelper.indicateFailure
import org.scalactic._

/**
 * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="Matchers.html"><code>Matchers</code></a> for an overview of
 * the matchers DSL.
 *
 * @author Bill Venners
 */
class ResultOfContainWord[L](left: L, shouldBeTrue: Boolean, prettifier: Prettifier, pos: source.Position) {

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain oneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def oneOf(firstEle: Any, secondEle: Any, remainingEles: Any*)(implicit containing: Containing[L]): Assertion = {
    val right = firstEle :: secondEle :: remainingEles.toList
    if (right.distinct.size != right.size)
      throw new NotAllowedException(FailureMessages.oneOfDuplicate, pos)
    if (containing.containsOneOf(left, right) != shouldBeTrue)
      indicateFailure(
        if (shouldBeTrue)
          FailureMessages.didNotContainOneOfElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
        else
          FailureMessages.containedOneOfElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        None,
        pos
      )
    else
      indicateSuccess(
        shouldBeTrue,
        FailureMessages.containedOneOfElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        FailureMessages.didNotContainOneOfElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
      )
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * xs should contain oneElementOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def oneElementOf(elements: GenTraversable[Any])(implicit containing: Containing[L]): Assertion = {
    val right = elements.toList
    if (containing.containsOneOf(left, right.distinct) != shouldBeTrue)
      indicateFailure(if (shouldBeTrue) FailureMessages.didNotContainOneElementOf(prettifier, left, right) else FailureMessages.containedOneElementOf(prettifier, left, right), None, pos)
    else indicateSuccess(shouldBeTrue, FailureMessages.containedOneElementOf(prettifier, left, right), FailureMessages.didNotContainOneElementOf(prettifier, left, right))
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain atLeastOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def atLeastOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*)(implicit aggregating: Aggregating[L]): Assertion = {
    val right = firstEle :: secondEle :: remainingEles.toList
    if (right.distinct.size != right.size)
      throw new NotAllowedException(FailureMessages.atLeastOneOfDuplicate, pos)
    if (aggregating.containsAtLeastOneOf(left, right) != shouldBeTrue)
      indicateFailure(
        if (shouldBeTrue)
          FailureMessages.didNotContainAtLeastOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
        else
          FailureMessages.containedAtLeastOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        None,
        pos
      )
    else
      indicateSuccess(
        shouldBeTrue,
        FailureMessages.containedAtLeastOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        FailureMessages.didNotContainAtLeastOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
      )
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * xs should contain atLeastOneElementOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def atLeastOneElementOf(elements: GenTraversable[Any])(implicit aggregating: Aggregating[L]): Assertion = {
    val right = elements.toList
    if (aggregating.containsAtLeastOneOf(left, right.distinct) != shouldBeTrue)
      indicateFailure(if (shouldBeTrue) FailureMessages.didNotContainAtLeastOneElementOf(prettifier, left, right) else FailureMessages.containedAtLeastOneElementOf(prettifier, left, right), None, pos)
    else
      indicateSuccess(shouldBeTrue, FailureMessages.containedAtLeastOneElementOf(prettifier, left, right), FailureMessages.didNotContainAtLeastOneElementOf(prettifier, left, right))
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain noneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def noneOf(firstEle: Any, secondEle: Any, remainingEles: Any*)(implicit containing: Containing[L]): Assertion = {
    val right = firstEle :: secondEle :: remainingEles.toList
    if (right.distinct.size != right.size)
      throw new NotAllowedException(FailureMessages.noneOfDuplicate, pos)
    if (containing.containsNoneOf(left, right) != shouldBeTrue)
      indicateFailure(
        if (shouldBeTrue)
          FailureMessages.containedAtLeastOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
        else
          FailureMessages.didNotContainAtLeastOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        None,
        pos
      )
    else
      indicateSuccess(
        shouldBeTrue,
        FailureMessages.didNotContainAtLeastOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        FailureMessages.containedAtLeastOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
      )
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * xs should contain noElementsOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def noElementsOf(elements: GenTraversable[Any])(implicit containing: Containing[L]): Assertion = {
    val right = elements.toList
    if (containing.containsNoneOf(left, right.distinct) != shouldBeTrue)
      indicateFailure(if (shouldBeTrue) FailureMessages.containedAtLeastOneElementOf(prettifier, left, right) else FailureMessages.didNotContainAtLeastOneElementOf(prettifier, left, right), None, pos)
    else
      indicateSuccess(shouldBeTrue, FailureMessages.didNotContainAtLeastOneElementOf(prettifier, left, right), FailureMessages.containedAtLeastOneElementOf(prettifier, left, right))
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain theSameElementsAs (<span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
   *                   ^
   * </pre>
   **/
  def theSameElementsAs(right: GenTraversable[_])(implicit aggregating: Aggregating[L]): Assertion = {
    if (aggregating.containsTheSameElementsAs(left, right) != shouldBeTrue)
      indicateFailure(if (shouldBeTrue) FailureMessages.didNotContainSameElements(prettifier, left, right) else FailureMessages.containedSameElements(prettifier, left, right), None, pos)
    else
      indicateSuccess(shouldBeTrue, FailureMessages.containedSameElements(prettifier, left, right), FailureMessages.didNotContainSameElements(prettifier, left, right))
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain theSameElementsInOrderAs (<span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
   *                   ^
   * </pre>
   **/
  def theSameElementsInOrderAs(right: GenTraversable[_])(implicit sequencing: Sequencing[L]): Assertion = {
    if (sequencing.containsTheSameElementsInOrderAs(left, right) != shouldBeTrue)
      indicateFailure(if (shouldBeTrue) FailureMessages.didNotContainSameElementsInOrder(prettifier, left, right) else FailureMessages.containedSameElementsInOrder(prettifier, left, right), None, pos)
    else
      indicateSuccess(shouldBeTrue, FailureMessages.containedSameElementsInOrder(prettifier, left, right), FailureMessages.didNotContainSameElementsInOrder(prettifier, left, right))
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain only (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def only(right: Any*)(implicit aggregating: Aggregating[L]): Assertion = {
    if (right.isEmpty)
      throw new NotAllowedException(FailureMessages.onlyEmpty, pos)
    if (right.distinct.size != right.size)
      throw new NotAllowedException(FailureMessages.onlyDuplicate, pos)
    val withFriendlyReminder = right.size == 1 && (right(0).isInstanceOf[scala.collection.GenTraversable[_]] || right(0).isInstanceOf[Every[_]])
    if (aggregating.containsOnly(left, right) != shouldBeTrue) {
      indicateFailure(
        if (shouldBeTrue)
          if (withFriendlyReminder)
            FailureMessages.didNotContainOnlyElementsWithFriendlyReminder(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
          else
            FailureMessages.didNotContainOnlyElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
        else
          if (withFriendlyReminder)
            FailureMessages.containedOnlyElementsWithFriendlyReminder(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
          else
            FailureMessages.containedOnlyElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        None,
        pos
      )
    }
    else
      indicateSuccess(
        if (shouldBeTrue)
          if (withFriendlyReminder)
            FailureMessages.containedOnlyElementsWithFriendlyReminder(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
          else
            FailureMessages.containedOnlyElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
        else
          if (withFriendlyReminder)
            FailureMessages.didNotContainOnlyElementsWithFriendlyReminder(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
          else
            FailureMessages.didNotContainOnlyElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))

      )
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain inOrderOnly (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def inOrderOnly(firstEle: Any, secondEle: Any, remainingEles: Any*)(implicit sequencing: Sequencing[L]): Assertion = {
    val right = firstEle :: secondEle :: remainingEles.toList
    if (right.distinct.size != right.size)
      throw new NotAllowedException(FailureMessages.inOrderOnlyDuplicate, pos)
    if (sequencing.containsInOrderOnly(left, right) != shouldBeTrue)
      indicateFailure(
        if (shouldBeTrue)
          FailureMessages.didNotContainInOrderOnlyElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
        else
          FailureMessages.containedInOrderOnlyElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        None,
        pos
      )
    else
      indicateSuccess(
        shouldBeTrue,
        FailureMessages.containedInOrderOnlyElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        FailureMessages.didNotContainInOrderOnlyElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
      )
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain allOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def allOf(firstEle: Any, secondEle: Any, remainingEles: Any*)(implicit aggregating: Aggregating[L]): Assertion = {
    val right = firstEle :: secondEle :: remainingEles.toList
    if (right.distinct.size != right.size)
      throw new NotAllowedException(FailureMessages.allOfDuplicate, pos)
    if (aggregating.containsAllOf(left, right) != shouldBeTrue)
      indicateFailure(
        if (shouldBeTrue)
          FailureMessages.didNotContainAllOfElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
        else
          FailureMessages.containedAllOfElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        None,
        pos
      )
    else
      indicateSuccess(
        shouldBeTrue,
        FailureMessages.didNotContainAllOfElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        FailureMessages.containedAllOfElements(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
      )
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * xs should contain allElementsOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def allElementsOf[R](elements: GenTraversable[R])(implicit aggregating: Aggregating[L]): Assertion = {
    val right = elements.toList
    if (aggregating.containsAllOf(left, right.distinct) != shouldBeTrue)
      indicateFailure(if (shouldBeTrue) FailureMessages.didNotContainAllElementsOf(prettifier, left, right) else FailureMessages.containedAllElementsOf(prettifier, left, right), None, pos)
    else
      indicateSuccess(shouldBeTrue, FailureMessages.containedAllElementsOf(prettifier, left, right), FailureMessages.didNotContainAllElementsOf(prettifier, left, right))
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain inOrder (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def inOrder(firstEle: Any, secondEle: Any, remainingEles: Any*)(implicit sequencing: Sequencing[L]): Assertion = {
    val right = firstEle :: secondEle :: remainingEles.toList
    if (right.distinct.size != right.size)
      throw new NotAllowedException(FailureMessages.inOrderDuplicate, pos)
    if (sequencing.containsInOrder(left, right) != shouldBeTrue)
      indicateFailure(
        if (shouldBeTrue)
          FailureMessages.didNotContainAllOfElementsInOrder(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
        else
          FailureMessages.containedAllOfElementsInOrder(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        None,
        pos
      )
    else
      indicateSuccess(
        shouldBeTrue,
        FailureMessages.containedAllOfElementsInOrder(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        FailureMessages.didNotContainAllOfElementsInOrder(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
      )
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * xs should contain inOrderElementsOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def inOrderElementsOf[R](elements: GenTraversable[R])(implicit sequencing: Sequencing[L]): Assertion = {
    val right = elements.toList
    if (sequencing.containsInOrder(left, right.distinct) != shouldBeTrue)
      indicateFailure(if (shouldBeTrue) FailureMessages.didNotContainAllElementsOfInOrder(prettifier, left, right) else FailureMessages.containedAllElementsOfInOrder(prettifier, left, right), None, pos)
    else
      indicateSuccess(shouldBeTrue, FailureMessages.containedAllElementsOfInOrder(prettifier, left, right), FailureMessages.didNotContainAllElementsOfInOrder(prettifier, left, right))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * map should contain key (<span class="stQuotedString">"one"</span>)
   *                    ^
   * </pre>
   **/
  def key(expectedKey: Any)(implicit keyMapping: KeyMapping[L]): Assertion = {
    if (keyMapping.containsKey(left, expectedKey) != shouldBeTrue)
      indicateFailure(if (shouldBeTrue) FailureMessages.didNotContainKey(prettifier, left, expectedKey) else FailureMessages.containedKey(prettifier, left, expectedKey), None, pos)
    else
      indicateSuccess(shouldBeTrue, FailureMessages.containedKey(prettifier, left, expectedKey), FailureMessages.didNotContainKey(prettifier, left, expectedKey))
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * map should contain value (<span class="stQuotedString">"one"</span>)
   *                    ^
   * </pre>
   **/
  def value(expectedValue: Any)(implicit valueMapping: ValueMapping[L]): Assertion = {
    if (valueMapping.containsValue(left, expectedValue) != shouldBeTrue)
      indicateFailure(if (shouldBeTrue) FailureMessages.didNotContainValue(prettifier, left, expectedValue) else FailureMessages.containedValue(prettifier, left, expectedValue), None, pos)
    else
      indicateSuccess(shouldBeTrue, FailureMessages.containedValue(prettifier, left, expectedValue), FailureMessages.didNotContainValue(prettifier, left, expectedValue))
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * xs should contain atMostOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def atMostOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*)(implicit aggregating: Aggregating[L]): Assertion = {
    val right = firstEle :: secondEle :: remainingEles.toList
    if (right.distinct.size != right.size)
      throw new NotAllowedException(FailureMessages.atMostOneOfDuplicate, pos)
    if (aggregating.containsAtMostOneOf(left, right) != shouldBeTrue)
      indicateFailure(
        if (shouldBeTrue)
          FailureMessages.didNotContainAtMostOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
        else
          FailureMessages.containedAtMostOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        None,
        pos
      )
    else
      indicateSuccess(
        shouldBeTrue,
        FailureMessages.containedAtMostOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", "))),
        FailureMessages.didNotContainAtMostOneOf(prettifier, left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
      )
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * xs should contain atMostOneElementOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
   *                   ^
   * </pre>
   **/
  def atMostOneElementOf[R](elements: GenTraversable[R])(implicit aggregating: Aggregating[L]): Assertion = {
    val right = elements.toList
    if (aggregating.containsAtMostOneOf(left, right.distinct) != shouldBeTrue)
      indicateFailure(
        if (shouldBeTrue)
          FailureMessages.didNotContainAtMostOneElementOf(prettifier, left, right)
        else
          FailureMessages.containedAtMostOneElementOf(prettifier, left, right),
        None,
        pos
      )
    else
      indicateSuccess(
        shouldBeTrue,
        FailureMessages.containedAtMostOneElementOf(prettifier, left, right),
        FailureMessages.didNotContainAtMostOneElementOf(prettifier, left, right)
      )
  }
  
  override def toString: String = "ResultOfContainWord(" + Prettifier.default(left) + ", " + Prettifier.default(shouldBeTrue) + ")"
}

