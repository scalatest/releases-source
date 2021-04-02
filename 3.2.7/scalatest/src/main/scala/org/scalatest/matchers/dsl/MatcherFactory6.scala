
package org.scalatest.matchers.dsl

import org.scalatest.enablers._
import org.scalatest.matchers.MatchersHelper.andMatchersAndApply
import org.scalatest.matchers.MatchersHelper.orMatchersAndApply
import org.scalatest.matchers.dsl.MatcherWords
import scala.collection.GenTraversable
import scala.util.matching.Regex
import org.scalactic._
import TripleEqualsSupport.Spread
import TripleEqualsSupport.TripleEqualsInvocation
import org.scalatest.FailureMessages
import org.scalatest.Resources
import org.scalatest.matchers.Matcher
import org.scalatest.matchers.MatchResult
import org.scalatest.matchers.BeMatcher
import org.scalatest.matchers.BePropertyMatcher
import org.scalatest.matchers.HavePropertyMatcher
import org.scalatest.matchers.AMatcher
import org.scalatest.matchers.AnMatcher
import org.scalatest.matchers.MatchPatternMacro
import org.scalatest.matchers.TypeMatcherMacro
import org.scalatest.matchers.dsl.FullyMatchWord
import org.scalatest.matchers.dsl.StartWithWord
import org.scalatest.matchers.dsl.EndWithWord
import org.scalatest.matchers.dsl.IncludeWord
import org.scalatest.matchers.dsl.HaveWord
import org.scalatest.matchers.dsl.BeWord
import org.scalatest.matchers.dsl.NotWord
import org.scalatest.matchers.dsl.ContainWord
import org.scalatest.matchers.dsl.ResultOfLengthWordApplication
import org.scalatest.matchers.dsl.ResultOfSizeWordApplication
import org.scalatest.matchers.dsl.ResultOfMessageWordApplication
import org.scalatest.matchers.dsl.ResultOfLessThanComparison
import org.scalatest.matchers.dsl.ResultOfGreaterThanComparison
import org.scalatest.matchers.dsl.ResultOfLessThanOrEqualToComparison
import org.scalatest.matchers.dsl.ResultOfGreaterThanOrEqualToComparison
import org.scalatest.matchers.dsl.ResultOfAWordToSymbolApplication
import org.scalatest.matchers.dsl.ResultOfAWordToBePropertyMatcherApplication
import org.scalatest.matchers.dsl.ResultOfAWordToAMatcherApplication
import org.scalatest.matchers.dsl.ResultOfAnWordToSymbolApplication
import org.scalatest.matchers.dsl.ResultOfAnWordToBePropertyMatcherApplication
import org.scalatest.matchers.dsl.ResultOfAnWordToAnMatcherApplication
import org.scalatest.matchers.dsl.ResultOfTheSameInstanceAsApplication
import org.scalatest.matchers.dsl.ResultOfRegexWordApplication
import org.scalatest.matchers.dsl.ResultOfKeyWordApplication
import org.scalatest.matchers.dsl.ResultOfValueWordApplication
import org.scalatest.matchers.dsl.RegexWithGroups
import org.scalatest.matchers.dsl.ResultOfDefinedAt
import org.scalatest.matchers.dsl.ResultOfOneOfApplication
import org.scalatest.matchers.dsl.ResultOfOneElementOfApplication
import org.scalatest.matchers.dsl.ResultOfAtLeastOneOfApplication
import org.scalatest.matchers.dsl.ResultOfAtLeastOneElementOfApplication
import org.scalatest.matchers.dsl.ResultOfNoneOfApplication
import org.scalatest.matchers.dsl.ResultOfNoElementsOfApplication
import org.scalatest.matchers.dsl.ResultOfTheSameElementsAsApplication
import org.scalatest.matchers.dsl.ResultOfTheSameElementsInOrderAsApplication
import org.scalatest.matchers.dsl.ResultOfOnlyApplication
import org.scalatest.matchers.dsl.ResultOfAllOfApplication
import org.scalatest.matchers.dsl.ResultOfAllElementsOfApplication
import org.scalatest.matchers.dsl.ResultOfInOrderOnlyApplication
import org.scalatest.matchers.dsl.ResultOfInOrderApplication
import org.scalatest.matchers.dsl.ResultOfInOrderElementsOfApplication
import org.scalatest.matchers.dsl.ResultOfAtMostOneOfApplication
import org.scalatest.matchers.dsl.ResultOfAtMostOneElementOfApplication
import org.scalatest.matchers.dsl.SortedWord
import org.scalatest.matchers.dsl.ResultOfATypeInvocation
import org.scalatest.matchers.dsl.ResultOfAnTypeInvocation
import org.scalatest.matchers.dsl.ExistWord
import org.scalatest.matchers.dsl.ResultOfNotExist
import org.scalatest.matchers.dsl.ReadableWord
import org.scalatest.matchers.dsl.WritableWord
import org.scalatest.matchers.dsl.EmptyWord
import org.scalatest.matchers.dsl.DefinedWord

import scala.language.higherKinds

/**
 * A matcher factory that can produce a matcher given six typeclass instances.
 *
 * <p>
 * In the type parameters for this class, "<code>SC</code>" means <em>superclass</em>; "<code>TC</code>"
 * (in <code>TC1</code>, <code>TC2</code>, <em>etc.</em>) means <em>typeclass</em>.
 * This class's <code>matcher</code> factory method will produce a <code>Matcher[T]</code>, where <code>T</code> is a subtype of (or the same type
 * as) <code>SC</code>, given a typeclass instance for each <code>TC<em>n</em></code>
 * implicit parameter (for example, a <code>TC1[T]</code>, <code>TC2[T]</code>, <em>etc.</em>).
 * </p>
 *
 * @author Bill Venners
 */
// Add a TYPECLASSN for each N
abstract class MatcherFactory6[-SC, TC1[_], TC2[_], TC3[_], TC4[_], TC5[_], TC6[_]] { thisMatcherFactory =>

  /**
   * Factory method that will produce a <code>Matcher[T]</code>, where <code>T</code> is a subtype of (or the same type
   * as) <code>SC</code>, given a typeclass instance for each <code>TC<em>n</em></code>
   * implicit parameter (for example, a <code>TC1[T]</code>, <code>TC2[T]</code>, <em>etc.</em>).
   */
  def matcher[T <: SC : TC1 : TC2 : TC3 : TC4 : TC5 : TC6]: Matcher[T]

  /**
   * Ands this matcher factory with the passed matcher.
   */
  def and[U <: SC](rightMatcher: Matcher[U]): MatcherFactory6[U, TC1, TC2, TC3, TC4, TC5, TC6] =
    new MatcherFactory6[U, TC1, TC2, TC3, TC4, TC5, TC6] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            andMatchersAndApply(left, leftMatcher, rightMatcher)
          }
          override def toString: String = "(" + Prettifier.default(thisMatcherFactory) + ") and (" + Prettifier.default(rightMatcher) + ")"
        }
      }
      override def toString: String = "(" + Prettifier.default(thisMatcherFactory) + ") and (" + Prettifier.default(rightMatcher) + ")"
    }

  /**
   * Ors this matcher factory with the passed matcher.
   */
  def or[U <: SC](rightMatcher: Matcher[U]): MatcherFactory6[U, TC1, TC2, TC3, TC4, TC5, TC6] =
    new MatcherFactory6[U, TC1, TC2, TC3, TC4, TC5, TC6] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            orMatchersAndApply(left, leftMatcher, rightMatcher)
          }
          override def toString: String = "(" + Prettifier.default(thisMatcherFactory) + ") or (" + Prettifier.default(rightMatcher) + ")"
        }
      }
      override def toString: String = "(" + Prettifier.default(thisMatcherFactory) + ") or (" + Prettifier.default(rightMatcher) + ")"
    }

  /**
   * Ands this matcher factory with the passed <code>MatcherFactory1</code> that has the same final typeclass as this one.
   */
  def and[U <: SC](rightMatcherFactory: MatcherFactory1[U, TC6]): MatcherFactory6[U, TC1, TC2, TC3, TC4, TC5, TC6] =
    new MatcherFactory6[U, TC1, TC2, TC3, TC4, TC5, TC6] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            val rightMatcher = rightMatcherFactory.matcher
            andMatchersAndApply(left, leftMatcher, rightMatcher)
          }
          override def toString: String = "(" + Prettifier.default(thisMatcherFactory) + ") and (" + Prettifier.default(rightMatcherFactory) + ")"
        }
      }
      override def toString: String = "(" + Prettifier.default(thisMatcherFactory) + ") and (" + Prettifier.default(rightMatcherFactory) + ")"
    }

  /**
   * Ors this matcher factory with the passed <code>MatcherFactory1</code> that has the same final typeclass as this one.
   */
  def or[U <: SC](rightMatcherFactory: MatcherFactory1[U, TC6]): MatcherFactory6[U, TC1, TC2, TC3, TC4, TC5, TC6] =
    new MatcherFactory6[U, TC1, TC2, TC3, TC4, TC5, TC6] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            val rightMatcher = rightMatcherFactory.matcher
            orMatchersAndApply(left, leftMatcher, rightMatcher)
          }
          override def toString: String = "(" + Prettifier.default(thisMatcherFactory) + ") or (" + Prettifier.default(rightMatcherFactory) + ")"
        }
      }
      override def toString: String = "(" + Prettifier.default(thisMatcherFactory) + ") or (" + Prettifier.default(rightMatcherFactory) + ")"
    }
                

  /**
   * Ands this matcher factory with the passed matcher factory.
   */
  def and[U <: SC, TC7[_]](rightMatcherFactory: MatcherFactory1[U, TC7]): MatcherFactory7[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7] =
    new MatcherFactory7[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            val rightMatcher = rightMatcherFactory.matcher
            andMatchersAndApply(left, leftMatcher, rightMatcher)
          }
        }
      }
    }

  /**
   * Ors this matcher factory with the passed matcher factory.
   */
  def or[U <: SC, TC7[_]](rightMatcherFactory: MatcherFactory1[U, TC7]): MatcherFactory7[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7] =
    new MatcherFactory7[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            val rightMatcher = rightMatcherFactory.matcher
            orMatchersAndApply(left, leftMatcher, rightMatcher)
          }
        }
      }
    }

  /**
   * Ands this matcher factory with the passed matcher factory.
   */
  def and[U <: SC, TC7[_], TC8[_]](rightMatcherFactory: MatcherFactory2[U, TC7, TC8]): MatcherFactory8[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8] =
    new MatcherFactory8[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7 : TC8]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            val rightMatcher = rightMatcherFactory.matcher
            andMatchersAndApply(left, leftMatcher, rightMatcher)
          }
        }
      }
    }

  /**
   * Ors this matcher factory with the passed matcher factory.
   */
  def or[U <: SC, TC7[_], TC8[_]](rightMatcherFactory: MatcherFactory2[U, TC7, TC8]): MatcherFactory8[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8] =
    new MatcherFactory8[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7 : TC8]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            val rightMatcher = rightMatcherFactory.matcher
            orMatchersAndApply(left, leftMatcher, rightMatcher)
          }
        }
      }
    }

  /**
   * Ands this matcher factory with the passed matcher factory.
   */
  def and[U <: SC, TC7[_], TC8[_], TC9[_]](rightMatcherFactory: MatcherFactory3[U, TC7, TC8, TC9]): MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] =
    new MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7 : TC8 : TC9]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            val rightMatcher = rightMatcherFactory.matcher
            andMatchersAndApply(left, leftMatcher, rightMatcher)
          }
        }
      }
    }

  /**
   * Ors this matcher factory with the passed matcher factory.
   */
  def or[U <: SC, TC7[_], TC8[_], TC9[_]](rightMatcherFactory: MatcherFactory3[U, TC7, TC8, TC9]): MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] =
    new MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7 : TC8 : TC9]: Matcher[V] = {
        new Matcher[V] {
          def apply(left: V): MatchResult = {
            val leftMatcher = thisMatcherFactory.matcher
            val rightMatcher = rightMatcherFactory.matcher
            orMatchersAndApply(left, leftMatcher, rightMatcher)
          }
        }
      }
    }
  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndHaveWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and have length (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                          ^
     * </pre>
     */
    def length(expectedLength: Long): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Length] = and(MatcherWords.have.length(expectedLength))

    // These guys need to generate a MatcherFactory of N+1. And it needs N-1 TC's, with the last one being Length.

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and have size (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                          ^
     * </pre>
     */
    def size(expectedSize: Long): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Size] = and(MatcherWords.have.size(expectedSize))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and have message (<span class="stQuotedString">"A message from Mars!"</span>)
     *                          ^
     * </pre>
     */
    def message(expectedMessage: String): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Messaging] = and(MatcherWords.have.message(expectedMessage))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and have size (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
   *                     ^
   * </pre>
   */
  def and(haveWord: HaveWord): AndHaveWord = new AndHaveWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndContainWord(prettifier: Prettifier, pos: source.Position) {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                             ^
     * </pre>
     */
    def apply(expectedElement: Any): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] = thisMatcherFactory.and(MatcherWords.contain(expectedElement))

    // And some, the ones that would by themselves already generate a Matcher, just return a MatcherFactoryN where N is the same.

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain key (<span class="stQuotedString">"one"</span>)
     *                             ^
     * </pre>
     */
    def key(expectedKey: Any): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, KeyMapping] = thisMatcherFactory.and(MatcherWords.contain.key(expectedKey))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain value (<span class="stLiteral">1</span>)
     *                             ^
     * </pre>
     */
    def value(expectedValue: Any): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, ValueMapping] = thisMatcherFactory.and(MatcherWords.contain.value(expectedValue))

    // And some, the ones that would by themselves already generate a Matcher, just return a MatcherFactoryN where N is the same.

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain theSameElementsAs <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def theSameElementsAs(right: GenTraversable[_]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] = 
      thisMatcherFactory.and(MatcherWords.contain.theSameElementsAs(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain theSameElementsInOrderAs <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def theSameElementsInOrderAs(right: GenTraversable[_]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] = 
      thisMatcherFactory.and(MatcherWords.contain.theSameElementsInOrderAs(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain inOrderOnly (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def inOrderOnly(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.and(MatcherWords.contain.inOrderOnly(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain allOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def allOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.contain.allOf(firstEle, secondEle, remainingEles  .toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain allElementsOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def allElementsOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.contain.allElementsOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain inOrder (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def inOrder(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.and(MatcherWords.contain.inOrder(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain inOrderElementsOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def inOrderElementsOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.and(MatcherWords.contain.inOrderElementsOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain oneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def oneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.and(MatcherWords.contain.oneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain oneElementOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def oneElementOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.and(MatcherWords.contain.oneElementOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain atLeastOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def atLeastOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.contain.atLeastOneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain atLeastOneElementOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def atLeastOneElementOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.contain.atLeastOneElementOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain only (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def only(right: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] = 
      thisMatcherFactory.and(MatcherWords.contain.only(right.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain noneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def noneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.and(MatcherWords.contain.noneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain noElementsOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def noElementsOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.and(MatcherWords.contain.noElementsOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain atMostOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def atMostOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.contain.atMostOneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and contain atMostOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                             ^
     * </pre>
     */
    def atMostOneElementOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.contain.atMostOneElementOf(elements))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and contain key (<span class="stQuotedString">"one"</span>)
   *                 ^
   * </pre>
   */
  def and(containWord: ContainWord)(implicit prettifier: Prettifier, pos: source.Position): AndContainWord = new AndContainWord(prettifier, pos)

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndBeWord {

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>file</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and be a (file)
     *                        ^
     * </pre>
     */
    def a[U](bePropertyMatcher: BePropertyMatcher[U]): MatcherFactory6[SC with AnyRef with U, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.be.a(bePropertyMatcher))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>validNumber</code> is an <a href="AMatcher.html"><code>AMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and be a (validNumber)
     *                        ^
     * </pre>
     */
    def a[U](aMatcher: AMatcher[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.be.a(aMatcher))

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>apple</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and be an (apple)
     *                        ^
     * </pre>
     */
    def an[U](bePropertyMatcher: BePropertyMatcher[U]): MatcherFactory6[SC with AnyRef with U, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.be.an(bePropertyMatcher))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>integerNumber</code> is an <a href="AnMatcher.html"><code>AnMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and be an (integerNumber)
     *                        ^
     * </pre>
     */
    def an[U](anMatcher: AnMatcher[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.be.an(anMatcher))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and be theSameInstanceAs (string)
     *                        ^
     * </pre>
     */
    def theSameInstanceAs(anyRef: AnyRef): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.be.theSameInstanceAs(anyRef))

    /**
     * This method enables the following syntax, where <code>fraction</code> refers to a <code>PartialFunction</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and be definedAt (<span class="stLiteral">8</span>)
     *                        ^
     * </pre>
     */
    def definedAt[A, U <: PartialFunction[A, _]](right: A): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.be.definedAt(right))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and be a (<span class="stQuotedString">'file</span>)
   *                 ^
   * </pre>
   */
  def and(beWord: BeWord): AndBeWord = new AndBeWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndFullyMatchWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and fullyMatch regex (decimal)
     *                                ^
     * </pre>
     */
    def regex(regexString: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.fullyMatch.regex(regexString))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and fullyMatch regex ((<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>))
     *                                ^
     * </pre>
     */
    def regex(regexWithGroups: RegexWithGroups): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.fullyMatch.regex(regexWithGroups))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and fullyMatch regex (decimalRegex)
     *                                ^
     * </pre>
     */
    def regex(regex: Regex): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.fullyMatch.regex(regex))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and fullyMatch regex (decimalRegex)
   *                 ^
   * </pre>
   */
  def and(fullyMatchWord: FullyMatchWord): AndFullyMatchWord = new AndFullyMatchWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndIncludeWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and include regex (decimal)
     *                             ^
     * </pre>
     */
    def regex(regexString: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.include.regex(regexString))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and include regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                             ^
     * </pre>
     */
    def regex(regexWithGroups: RegexWithGroups): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.include.regex(regexWithGroups))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and include regex (decimalRegex)
     *                             ^
     * </pre>
     */
    def regex(regex: Regex): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.include.regex(regex))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and include regex (<span class="stQuotedString">"wor.d"</span>)
   *                 ^
   * </pre>
   */
  def and(includeWord: IncludeWord): AndIncludeWord = new AndIncludeWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndStartWithWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and startWith regex (decimal)
     *                               ^
     * </pre>
     */
    def regex(regexString: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.startWith.regex(regexString))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and startWith regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                               ^
     * </pre>
     */
    def regex(regexWithGroups: RegexWithGroups): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.startWith.regex(regexWithGroups))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and startWith regex (decimalRegex)
     *                               ^
     * </pre>
     */
    def regex(regex: Regex): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.startWith.regex(regex))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and startWith regex (<span class="stQuotedString">"1.7"</span>)
   *                 ^
   * </pre>
   */
  def and(startWithWord: StartWithWord): AndStartWithWord = new AndStartWithWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndEndWithWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and endWith regex (decimal)
     *                             ^
     * </pre>
     */
    def regex(regexString: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.endWith.regex(regexString))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and endWith regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                             ^
     * </pre>
     */
    def regex(regexWithGroups: RegexWithGroups): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.endWith.regex(regexWithGroups))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and endWith regex (decimalRegex)
     *                             ^
     * </pre>
     */
    def regex(regex: Regex): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = and(MatcherWords.endWith.regex(regex))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and endWith regex (decimalRegex)
   *                 ^
   * </pre>
   */
  def and(endWithWord: EndWithWord): AndEndWithWord = new AndEndWithWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class AndNotWord(prettifier: Prettifier, pos: source.Position) {

    /**
     * Get the <code>MatcherFactory</code> instance, currently used by macro only.
     */
     val owner = thisMatcherFactory

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not equal (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                         ^
     * </pre>
     */
    def equal(any: Any): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Equality] =
      thisMatcherFactory.and(MatcherWords.not.apply(MatcherWords.equal(any)))

    /**
     * This method enables the following syntax, for the "primitive" numeric types:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not equal (<span class="stLiteral">17.0</span> +- <span class="stLiteral">0.2</span>)
     *                         ^
     * </pre>
     */
    def equal[U](spread: Spread[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.and(MatcherWords.not.equal(spread))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not equal (<span class="stReserved">null</span>)
     *                         ^
     * </pre>
     */
    def equal(o: Null): MatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6] = {
      thisMatcherFactory and {
        new Matcher[SC] {
          def apply(left: SC): MatchResult = {
            MatchResult(
              left != null,
              Resources.rawEqualedNull,
              Resources.rawDidNotEqualNull,
              Resources.rawMidSentenceEqualedNull,
              Resources.rawDidNotEqualNull,
              Vector.empty, 
              Vector(left)
            )
          }
          override def toString: String = "not equal null"
        }
      }
    }

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                         ^
     * </pre>
     */
    def be(any: Any): MatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.apply(MatcherWords.be(any)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not have length (<span class="stLiteral">3</span>)
     *                         ^
     * </pre>
     */
    def have(resultOfLengthWordApplication: ResultOfLengthWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Length] =
      thisMatcherFactory.and(MatcherWords.not.apply(MatcherWords.have.length(resultOfLengthWordApplication.expectedLength)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not have size (<span class="stLiteral">3</span>)
     *                         ^
     * </pre>
     */
    def have(resultOfSizeWordApplication: ResultOfSizeWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Size] =
      thisMatcherFactory.and(MatcherWords.not.apply(MatcherWords.have.size(resultOfSizeWordApplication.expectedSize)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not have message (<span class="stQuotedString">"Message from Mars!"</span>)
     *                         ^
     * </pre>
     */
    def have(resultOfMessageWordApplication: ResultOfMessageWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Messaging] =
      thisMatcherFactory.and(MatcherWords.not.apply(MatcherWords.have.message(resultOfMessageWordApplication.expectedMessage)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not have (author (<span class="stQuotedString">"Melville"</span>))
     *                         ^
     * </pre>
     */
    def have[U](firstPropertyMatcher: HavePropertyMatcher[U, _], propertyMatchers: HavePropertyMatcher[U, _]*): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.apply(MatcherWords.have(firstPropertyMatcher, propertyMatchers: _*)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be &lt; (<span class="stLiteral">6</span>)
     *                         ^
     * </pre>
     */
    def be[U](resultOfLessThanComparison: ResultOfLessThanComparison[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.be(resultOfLessThanComparison))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be (<span class="stReserved">null</span>)
     *                         ^
     * </pre>
     */
    def be(o: Null): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.and(MatcherWords.not.be(o))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory (<span class="stLiteral">8</span>) and not be &gt; (<span class="stLiteral">6</span>)
     *                             ^
     * </pre>
     */
    def be[U](resultOfGreaterThanComparison: ResultOfGreaterThanComparison[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.be(resultOfGreaterThanComparison))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be &lt;= (<span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def be[U](resultOfLessThanOrEqualToComparison: ResultOfLessThanOrEqualToComparison[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.be(resultOfLessThanOrEqualToComparison))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be &gt;= (<span class="stLiteral">6</span>)
     *                         ^
     * </pre>
     */
    def be[U](resultOfGreaterThanOrEqualToComparison: ResultOfGreaterThanOrEqualToComparison[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.be(resultOfGreaterThanOrEqualToComparison))

    /**
     * <strong>
     * The deprecation period for the should be === syntax has expired, and the syntax may no longer be
     * used.  Please use should equal, should ===, shouldEqual,
     * should be, or shouldBe instead.
     * </strong>
     * 
     * <p>
     * Note: usually syntax will be removed after its deprecation period. This was left in because otherwise the syntax could in some
     * cases still compile, but silently wouldn't work.
     * </p>
     */
    def be(tripleEqualsInvocation: TripleEqualsInvocation[_]): MatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.be(tripleEqualsInvocation)(pos))

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>odd</code> is a <a href="BeMatcher.html"><code>BeMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be (odd)
     *                         ^
     * </pre>
     */
    def be[U](beMatcher: BeMatcher[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.and(MatcherWords.not.be(beMatcher))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>directory</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be (directory)
     *                         ^
     * </pre>
     */
    def be[U](bePropertyMatcher: BePropertyMatcher[U]): MatcherFactory6[SC with AnyRef with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.and(MatcherWords.not.be(bePropertyMatcher))

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>validMarks</code> is an <a href="AMatcher.html"><code>AMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be a (validMarks)
     *                         ^
     * </pre>
     */
    def be[U](resultOfAWordApplication: ResultOfAWordToAMatcherApplication[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.and(MatcherWords.not.be(resultOfAWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>directory</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be a (directory)
     *                         ^
     * </pre>
     */
    def be[U <: AnyRef](resultOfAWordApplication: ResultOfAWordToBePropertyMatcherApplication[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.and(MatcherWords.not.be(resultOfAWordApplication))

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>apple</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be an (apple)
     *                         ^
     * </pre>
     */
    def be[SC <: AnyRef](resultOfAnWordApplication: ResultOfAnWordToBePropertyMatcherApplication[SC]) = thisMatcherFactory.and(MatcherWords.not.be(resultOfAnWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>invalidMarks</code> is a <a href="AnMatcher.html"><code>AnMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be an (invalidMarks)
     *                         ^
     * </pre>
     */
    def be[U](resultOfAnWordApplication: ResultOfAnWordToAnMatcherApplication[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.and(MatcherWords.not.be(resultOfAnWordApplication))

    import scala.language.experimental.macros

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be a [<span class="stType">Book</span>]
     *                         ^
     * </pre>
     */
    def be(aType: ResultOfATypeInvocation[_]): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = macro MatcherFactory6.andNotATypeMatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6]

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be an [<span class="stType">Apple</span>]
     *                         ^
     * </pre>
     */
    def be(anType: ResultOfAnTypeInvocation[_]): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = macro MatcherFactory6.andNotAnTypeMatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6]

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be theSameInstanceAs (otherString)
     *                         ^
     * </pre>
     */
    def be(resultOfTheSameInstanceAsApplication: ResultOfTheSameInstanceAsApplication): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.and(MatcherWords.not.be(resultOfTheSameInstanceAsApplication))

    /**
     * This method enables the following syntax, for the "primitive" numeric types:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be (<span class="stLiteral">17.0</span> +- <span class="stLiteral">0.2</span>)
     *                         ^
     * </pre>
     */
    def be[U](spread: Spread[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.and(MatcherWords.not.be(spread))

    /**
     * This method enables the following syntax, where <code>fraction</code> is a <code>PartialFunction</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be definedAt (<span class="stLiteral">8</span>)
     *                         ^
     * </pre>
     */
    def be[A, U <: PartialFunction[A, _]](resultOfDefinedAt: ResultOfDefinedAt[A]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = 
      thisMatcherFactory.and(MatcherWords.not.be(resultOfDefinedAt))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be sorted
     *                         ^
     * </pre>
     */
    def be(sortedWord: SortedWord) = 
      thisMatcherFactory.and(MatcherWords.not.be(sortedWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be readable
     *                         ^
     * </pre>
     */
    def be(readableWord: ReadableWord) = 
      thisMatcherFactory.and(MatcherWords.not.be(readableWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be writable
     *                         ^
     * </pre>
     */
    def be(writableWord: WritableWord) = 
      thisMatcherFactory.and(MatcherWords.not.be(writableWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be empty
     *                         ^
     * </pre>
     */
    def be(emptyWord: EmptyWord) = 
      thisMatcherFactory.and(MatcherWords.not.be(emptyWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be defined
     *                         ^
     * </pre>
     */
    def be(definedWord: DefinedWord) = 
      thisMatcherFactory.and(MatcherWords.not.be(definedWord))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not fullyMatch regex (decimal)
     *                         ^
     * </pre>
     */
    def fullyMatch(resultOfRegexWordApplication: ResultOfRegexWordApplication): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.fullyMatch(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not include regex (decimal)
     *                         ^
     * </pre>
     */
    def include(resultOfRegexWordApplication: ResultOfRegexWordApplication): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.include(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not include (<span class="stQuotedString">"1.7"</span>)
     *                         ^
     * </pre>
     */
    def include(expectedSubstring: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.include(expectedSubstring))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not startWith regex (decimal)
     *                         ^
     * </pre>
     */
    def startWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.startWith(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not startWith (<span class="stQuotedString">"1.7"</span>)
     *                         ^
     * </pre>
     */
    def startWith(expectedSubstring: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.startWith(expectedSubstring))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not endWith regex (decimal)
     *                         ^
     * </pre>
     */
    def endWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.endWith(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not endWith (<span class="stQuotedString">"1.7"</span>)
     *                         ^
     * </pre>
     */
    def endWith(expectedSubstring: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.and(MatcherWords.not.endWith(expectedSubstring))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain (<span class="stLiteral">3</span>)
     *                         ^
     * </pre>
     */
    def contain[U](expectedElement: U): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.and(MatcherWords.not.contain(expectedElement))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain key (<span class="stQuotedString">"three"</span>)
     *                         ^
     * </pre>
     */
    def contain(resultOfKeyWordApplication: ResultOfKeyWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, KeyMapping] =
      thisMatcherFactory.and(MatcherWords.not.contain(resultOfKeyWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain value (<span class="stLiteral">3</span>)
     *                         ^
     * </pre>
     */
    def contain(resultOfValueWordApplication: ResultOfValueWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, ValueMapping] =
      thisMatcherFactory.and(MatcherWords.not.contain(resultOfValueWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain oneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfOneOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain oneElementOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfOneElementOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain atLeastOneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfAtLeastOneOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain atLeastOneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfAtLeastOneElementOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain noneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfNoneOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain noElementsOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfNoElementsOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain theSameElementsAs (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfTheSameElementsAsApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain theSameElementsInOrderAs (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfTheSameElementsInOrderAsApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain only (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfOnlyApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain inOrderOnly (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfInOrderOnlyApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain allOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfAllOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain allElementsOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfAllElementsOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain inOrder (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfInOrderApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain inOrder (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfInOrderElementsOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain atMostOneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfAtMostOneOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not contain atMostOneElementOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                         ^
     * </pre>
     */
    def contain(right: ResultOfAtMostOneElementOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.and(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not matchPattern { <span class="stReserved">case</span> <span class="stType">Person</span>(<span class="stQuotedString">"Bob"</span>, _) =>}
     *                         ^
     * </pre>
     */
     def matchPattern(right: PartialFunction[Any, _]): Matcher[Any] = macro MatchPatternMacro.andNotMatchPatternMatcher
  }
                    

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and not contain value (<span class="stLiteral">3</span>)
   *                 ^
   * </pre>
   */
  def and(notWord: NotWord)(implicit prettifier: Prettifier, pos: source.Position): AndNotWord = new AndNotWord(prettifier, pos)

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and exist
   *                 ^
   * </pre>
   */
  def and(existWord: ExistWord): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Existence] = 
    thisMatcherFactory.and(MatcherWords.exist.matcherFactory)

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory and not (exist)
   *                 ^
   * </pre>
   */
  def and(notExist: ResultOfNotExist): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Existence] = 
    thisMatcherFactory.and(MatcherWords.not.exist)

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrHaveWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or have length (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                         ^
     * </pre>
     */
    def length(expectedLength: Long): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Length] = or(MatcherWords.have.length(expectedLength))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or have size (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                         ^
     * </pre>
     */
    def size(expectedSize: Long): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Size] = or(MatcherWords.have.size(expectedSize))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or have message (<span class="stQuotedString">"Message from Mars!"</span>)
     *                         ^
     * </pre>
     */
    def message(expectedMessage: String): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Messaging] = or(MatcherWords.have.message(expectedMessage))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory or have size (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
   *                 ^
   * </pre>
   */
  def or(haveWord: HaveWord): OrHaveWord = new OrHaveWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrContainWord(prettifier: Prettifier, pos: source.Position) {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                            ^
     * </pre>
     */
    def apply(expectedElement: Any): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] = thisMatcherFactory.or(MatcherWords.contain(expectedElement))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain key (<span class="stQuotedString">"one"</span>)
     *                            ^
     * </pre>
     */
    def key(expectedKey: Any): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, KeyMapping] = thisMatcherFactory.or(MatcherWords.contain.key(expectedKey))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain value (<span class="stLiteral">1</span>)
     *                            ^
     * </pre>
     */
    def value(expectedValue: Any): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, ValueMapping] = thisMatcherFactory.or(MatcherWords.contain.value(expectedValue))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain theSameElementsAs <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def theSameElementsAs(right: GenTraversable[_]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] = 
      thisMatcherFactory.or(MatcherWords.contain.theSameElementsAs(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain theSameElementsInOrderAs <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def theSameElementsInOrderAs(right: GenTraversable[_]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] = 
      thisMatcherFactory.or(MatcherWords.contain.theSameElementsInOrderAs(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain inOrderOnly (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def inOrderOnly(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.or(MatcherWords.contain.inOrderOnly(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain allOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def allOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.contain.allOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain allElementsOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def allElementsOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.contain.allElementsOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain inOrder (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def inOrder(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.or(MatcherWords.contain.inOrder(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain inOrderElementsOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def inOrderElementsOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.or(MatcherWords.contain.inOrderElementsOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain oneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def oneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.or(MatcherWords.contain.oneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain oneElementOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def oneElementOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.or(MatcherWords.contain.oneElementOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain atLeastOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def atLeastOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.contain.atLeastOneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain atLeastOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def atLeastOneElementOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.contain.atLeastOneElementOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain only (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def only(right: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] = 
      thisMatcherFactory.or(MatcherWords.contain.only(right.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain noneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def noneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.or(MatcherWords.contain.noneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain noElementsOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def noElementsOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.or(MatcherWords.contain.noElementsOf(elements))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain atMostOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def atMostOneOf(firstEle: Any, secondEle: Any, remainingEles: Any*): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.contain.atMostOneOf(firstEle, secondEle, remainingEles.toList: _*)(prettifier, pos))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or contain atMostOneOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)
     *                            ^
     * </pre>
     */
    def atMostOneElementOf(elements: GenTraversable[Any]): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.contain.atMostOneElementOf(elements))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * (aMatcherFactory or contain value (<span class="stLiteral">1</span>))
   *                  ^
   * </pre>
   */
  def or(containWord: ContainWord)(implicit prettifier: Prettifier, pos: source.Position): OrContainWord = new OrContainWord(prettifier, pos)

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrBeWord {

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or be a (directory)
     *                       ^
     * </pre>
     */
    def a[U](bePropertyMatcher: BePropertyMatcher[U]): MatcherFactory6[SC with AnyRef with U, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.be.a(bePropertyMatcher))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or be a (validNumber)
     *                       ^
     * </pre>
     */
    def a[U](aMatcher: AMatcher[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.be.a(aMatcher))

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>apple</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or be an (apple)
     *                       ^
     * </pre>
     */
    def an[U](bePropertyMatcher: BePropertyMatcher[U]): MatcherFactory6[SC with AnyRef with U, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.be.an(bePropertyMatcher))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>integerNumber</code> is a <a href="AnMatcher.html"><code>AnMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or be an (integerNumber)
     *                       ^
     * </pre>
     */
    def an[U](anMatcher: AnMatcher[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.be.an(anMatcher))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or be theSameInstanceAs (otherString)
     *                       ^
     * </pre>
     */
    def theSameInstanceAs(anyRef: AnyRef): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.be.theSameInstanceAs(anyRef))

    /**
     * This method enables the following syntax, where <code>fraction</code> refers to a <code>PartialFunction</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or be definedAt (<span class="stLiteral">8</span>)
     *                       ^
     * </pre>
     */
    def definedAt[A, U <: PartialFunction[A, _]](right: A): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.be.definedAt(right))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory or be a (<span class="stQuotedString">'directory</span>)
   *                 ^
   * </pre>
   */
  def or(beWord: BeWord): OrBeWord = new OrBeWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrFullyMatchWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or fullyMatch regex (decimal)
     *                               ^
     * </pre>
     */
    def regex(regexString: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.fullyMatch.regex(regexString))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or fullyMatch regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                               ^
     * </pre>
     */
    def regex(regexWithGroups: RegexWithGroups): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.fullyMatch.regex(regexWithGroups))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or fullyMatch regex (decimal)
     *                               ^
     * </pre>
     */
    def regex(regex: Regex): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.fullyMatch.regex(regex))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory or fullyMatch regex (decimal)
   *                 ^
   * </pre>
   */
  def or(fullyMatchWord: FullyMatchWord): OrFullyMatchWord = new OrFullyMatchWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrIncludeWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or include regex (decimal)
     *                            ^
     * </pre>
     */
    def regex(regexString: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.include.regex(regexString))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or include regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                            ^
     * </pre>
     */
    def regex(regexWithGroups: RegexWithGroups): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.include.regex(regexWithGroups))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or include regex (decimal)
     *                            ^
     * </pre>
     */
    def regex(regex: Regex): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.include.regex(regex))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory or include regex (<span class="stQuotedString">"1.7"</span>)
   *                 ^
   * </pre>
   */
  def or(includeWord: IncludeWord): OrIncludeWord = new OrIncludeWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrStartWithWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or startWith regex (decimal)
     *                              ^
     * </pre>
     */
    def regex(regexString: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.startWith.regex(regexString))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or startWith regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>)
     *                              ^
     * </pre>
     */
    def regex(regexWithGroups: RegexWithGroups): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.startWith.regex(regexWithGroups))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or startWith regex (decimal)
     *                              ^
     * </pre>
     */
    def regex(regex: Regex): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.startWith.regex(regex))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory or startWith regex (<span class="stQuotedString">"1.7"</span>)
   *                 ^
   * </pre>
   */
  def or(startWithWord: StartWithWord): OrStartWithWord = new OrStartWithWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrEndWithWord {

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or endWith regex (decimal)
     *                            ^
     * </pre>
     */
    def regex(regexString: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.endWith.regex(regexString))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or endWith regex (<span class="stQuotedString">"d(e*)f"</span> withGroup <span class="stQuotedString">"ee"</span>)
     *                            ^
     * </pre>
     */
    def regex(regexWithGroups: RegexWithGroups): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.endWith.regex(regexWithGroups))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or endWith regex (decimal)
     *                            ^
     * </pre>
     */
    def regex(regex: Regex): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] = or(MatcherWords.endWith.regex(regex))
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory or endWith regex (<span class="stQuotedString">"7b"</span>)
   *                 ^
   * </pre>
   */
  def or(endWithWord: EndWithWord): OrEndWithWord = new OrEndWithWord

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * @author Bill Venners
   */
  final class OrNotWord(prettifier: Prettifier, pos: source.Position) {

    /**
     * Get the <code>MatcherFactory</code> instance, currently used by macro.
     */
    val owner = thisMatcherFactory

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not equal (<span class="stLiteral">3</span> - <span class="stLiteral">1</span>)
     *                        ^
     * </pre>
     */
    def equal(any: Any): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Equality] =
      thisMatcherFactory.or(MatcherWords.not.apply(MatcherWords.equal(any)))

    /**
     * This method enables the following syntax for the "primitive" numeric types:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not equal (<span class="stLiteral">17.0</span> +- <span class="stLiteral">0.2</span>)
     *                        ^
     * </pre>
     */
    def equal[U](spread: Spread[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.equal(spread))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not equal (<span class="stReserved">null</span>)
     *                        ^
     * </pre>
     */
    def equal(o: Null): MatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6] = {
      thisMatcherFactory or {
        new Matcher[SC] {
          def apply(left: SC): MatchResult = {
            MatchResult(
              left != null,
              Resources.rawEqualedNull,
              Resources.rawDidNotEqualNull,
              Resources.rawMidSentenceEqualedNull,
              Resources.rawDidNotEqualNull,
              Vector.empty, 
              Vector(left)
            )
          }
          override def toString: String = "not equal null"
        }
      }
    }

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be (<span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def be(any: Any): MatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.apply(MatcherWords.be(any)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not have length (<span class="stLiteral">3</span>)
     *                        ^
     * </pre>
     */
    def have(resultOfLengthWordApplication: ResultOfLengthWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Length] =
      thisMatcherFactory.or(MatcherWords.not.apply(MatcherWords.have.length(resultOfLengthWordApplication.expectedLength)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not have size (<span class="stLiteral">3</span>)
     *                        ^
     * </pre>
     */
    def have(resultOfSizeWordApplication: ResultOfSizeWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Size] =
      thisMatcherFactory.or(MatcherWords.not.apply(MatcherWords.have.size(resultOfSizeWordApplication.expectedSize)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not have message (<span class="stQuotedString">"Message from Mars!"</span>)
     *                        ^
     * </pre>
     */
    def have(resultOfMessageWordApplication: ResultOfMessageWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Messaging] =
      thisMatcherFactory.or(MatcherWords.not.apply(MatcherWords.have.message(resultOfMessageWordApplication.expectedMessage)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not have (author (<span class="stQuotedString">"Melville"</span>))
     *                        ^
     * </pre>
     */
    def have[U](firstPropertyMatcher: HavePropertyMatcher[U, _], propertyMatchers: HavePropertyMatcher[U, _]*): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.apply(MatcherWords.have(firstPropertyMatcher, propertyMatchers: _*)))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be (<span class="stReserved">null</span>)
     *                        ^
     * </pre>
     */
    def be(o: Null): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.be(o))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be &lt; (<span class="stLiteral">8</span>)
     *                        ^
     * </pre>
     */
    def be[U](resultOfLessThanComparison: ResultOfLessThanComparison[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.be(resultOfLessThanComparison))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be &gt; (<span class="stLiteral">6</span>)
     *                        ^
     * </pre>
     */
    def be[U](resultOfGreaterThanComparison: ResultOfGreaterThanComparison[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.be(resultOfGreaterThanComparison))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be &lt;= (<span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def be[U](resultOfLessThanOrEqualToComparison: ResultOfLessThanOrEqualToComparison[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.be(resultOfLessThanOrEqualToComparison))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be &gt;= (<span class="stLiteral">6</span>)
     *                        ^
     * </pre>
     */
    def be[U](resultOfGreaterThanOrEqualToComparison: ResultOfGreaterThanOrEqualToComparison[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.be(resultOfGreaterThanOrEqualToComparison))

    /**
     * <strong>
     * The deprecation period for the "be ===" syntax has expired, and the syntax 
     * will now throw <code>NotAllowedException</code>.  Please use should equal, should ===, shouldEqual,
     * should be, or shouldBe instead.
     * </strong>
     * 
     * <p>
     * Note: usually syntax will be removed after its deprecation period. This was left in because otherwise the syntax could in some
     * cases still compile, but silently wouldn't work.
     * </p>
     */
    def be(tripleEqualsInvocation: TripleEqualsInvocation[_]): MatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.be(tripleEqualsInvocation)(pos))

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>odd</code> is a <a href="BeMatcher.html"><code>BeMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be (odd)
     *                        ^
     * </pre>
     */
    def be[U](beMatcher: BeMatcher[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.be(beMatcher))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>file</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be (file)
     *                        ^
     * </pre>
     */
    def be[U](bePropertyMatcher: BePropertyMatcher[U]): MatcherFactory6[SC with AnyRef with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.be(bePropertyMatcher))

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>validMarks</code> is an <a href="AMatcher.html"><code>AMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be a (validMarks)
     *                        ^
     * </pre>
     */
    def be[U](resultOfAWordApplication: ResultOfAWordToAMatcherApplication[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.be(resultOfAWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>file</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be a (file)
     *                        ^
     * </pre>
     */
    def be[U <: AnyRef](resultOfAWordApplication: ResultOfAWordToBePropertyMatcherApplication[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.be(resultOfAWordApplication))

()









()

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>apple</code> is a <a href="BePropertyMatcher.html"><code>BePropertyMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be an (apple)
     *                        ^
     * </pre>
     */
    def be[U <: AnyRef](resultOfAnWordApplication: ResultOfAnWordToBePropertyMatcherApplication[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.be(resultOfAnWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>, where <code>invalidMarks</code> is an <a href="AnMatcher.html"><code>AnMatcher</code></a>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory and not be an (invalidMarks)
     *                         ^
     * </pre>
     */
    def be[U](resultOfAnWordApplication: ResultOfAnWordToAnMatcherApplication[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.be(resultOfAnWordApplication))

    import scala.language.experimental.macros

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be a [<span class="stType">Book</span>]
     *                        ^
     * </pre>
     */
    def be(aType: ResultOfATypeInvocation[_]): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = macro MatcherFactory6.orNotATypeMatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6]

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be an [<span class="stType">Apple</span>]
     *                        ^
     * </pre>
     */
    def be(anType: ResultOfAnTypeInvocation[_]): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = macro MatcherFactory6.orNotAnTypeMatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6]

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be theSameInstanceAs (string)
     *                        ^
     * </pre>
     */
    def be(resultOfTheSameInstanceAsApplication: ResultOfTheSameInstanceAsApplication): MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.be(resultOfTheSameInstanceAsApplication))

    /**
     * This method enables the following syntax for the "primitive" numeric types:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be (<span class="stLiteral">17.0</span> +- <span class="stLiteral">0.2</span>)
     *                        ^
     * </pre>
     */
    def be[U](spread: Spread[U]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = thisMatcherFactory.or(MatcherWords.not.be(spread))

    /**
     * This method enables the following syntax, where <code>fraction</code> is a <code>PartialFunction</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be definedAt (<span class="stLiteral">8</span>)
     *                        ^
     * </pre>
     */
    def be[A, U <: PartialFunction[A, _]](resultOfDefinedAt: ResultOfDefinedAt[A]): MatcherFactory6[SC with U, TC1, TC2, TC3, TC4, TC5, TC6] = 
      thisMatcherFactory.or(MatcherWords.not.be(resultOfDefinedAt))

    /**
     * This method enables the following syntax, where <code>fraction</code> is a <code>PartialFunction</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be sorted
     *                        ^
     * </pre>
     */
    def be(sortedWord: SortedWord) = 
      thisMatcherFactory.or(MatcherWords.not.be(sortedWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be readable
     *                        ^
     * </pre>
     */
    def be(readableWord: ReadableWord) = 
      thisMatcherFactory.or(MatcherWords.not.be(readableWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be writable
     *                        ^
     * </pre>
     */
    def be(writableWord: WritableWord) = 
      thisMatcherFactory.or(MatcherWords.not.be(writableWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be empty
     *                        ^
     * </pre>
     */
    def be(emptyWord: EmptyWord) = 
      thisMatcherFactory.or(MatcherWords.not.be(emptyWord))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not be defined
     *                        ^
     * </pre>
     */
    def be(definedWord: DefinedWord) = 
      thisMatcherFactory.or(MatcherWords.not.be(definedWord))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not fullyMatch regex (decimal)
     *                        ^
     * </pre>
     */
    def fullyMatch(resultOfRegexWordApplication: ResultOfRegexWordApplication): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.fullyMatch(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not include regex (decimal)
     *                        ^
     * </pre>
     */
    def include(resultOfRegexWordApplication: ResultOfRegexWordApplication): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.include(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not include (<span class="stQuotedString">"1.7"</span>)
     *                        ^
     * </pre>
     */
    def include(expectedSubstring: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.include(expectedSubstring))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not startWith regex (decimal)
     *                        ^
     * </pre>
     */
    def startWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.startWith(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not startWith (<span class="stQuotedString">"1.7"</span>)
     *                        ^
     * </pre>
     */
    def startWith(expectedSubstring: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.startWith(expectedSubstring))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not endWith regex (decimal)
     *                        ^
     * </pre>
     */
    def endWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.endWith(resultOfRegexWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not endWith (<span class="stQuotedString">"1.7"</span>)
     *                        ^
     * </pre>
     */
    def endWith(expectedSubstring: String): MatcherFactory6[SC with String, TC1, TC2, TC3, TC4, TC5, TC6] =
      thisMatcherFactory.or(MatcherWords.not.endWith(expectedSubstring))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain (<span class="stLiteral">3</span>)
     *                        ^
     * </pre>
     */
    def contain[U](expectedElement: U): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.or(MatcherWords.not.contain(expectedElement))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain key (<span class="stQuotedString">"three"</span>)
     *                        ^
     * </pre>
     */
    def contain(resultOfKeyWordApplication: ResultOfKeyWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, KeyMapping] =
      thisMatcherFactory.or(MatcherWords.not.contain(resultOfKeyWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain value (<span class="stLiteral">3</span>)
     *                        ^
     * </pre>
     */
    def contain(resultOfValueWordApplication: ResultOfValueWordApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, ValueMapping] =
      thisMatcherFactory.or(MatcherWords.not.contain(resultOfValueWordApplication))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain oneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfOneOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain oneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfOneElementOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain atLeastOneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfAtLeastOneOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain atLeastOneElementOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
     def contain(right: ResultOfAtLeastOneElementOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
       thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain noneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfNoneOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain noElementsOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfNoElementsOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Containing] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain theSameElementsAs (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfTheSameElementsAsApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain theSameElementsInOrderAs (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfTheSameElementsInOrderAsApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain only (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfOnlyApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain inOrderOnly (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfInOrderOnlyApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain allOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfAllOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain allOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfAllElementsOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain inOrder (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfInOrderApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain inOrderElementsOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfInOrderElementsOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Sequencing] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain atMostOneOf (<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>)
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfAtMostOneOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax given a <code>MatcherFactory6</code>:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not contain atMostOneElementOf (<span class="stType">List</span>(<span class="stLiteral">8</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>))
     *                        ^
     * </pre>
     */
    def contain(right: ResultOfAtMostOneElementOfApplication): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Aggregating] =
      thisMatcherFactory.or(MatcherWords.not.contain(right))

    /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * aMatcherFactory or not matchPattern { <span class="stReserved">case</span> <span class="stType">Person</span>(<span class="stQuotedString">"Bob"</span>, _) =>}
     *                        ^
     * </pre>
     */
    def matchPattern(right: PartialFunction[Any, _]): Matcher[Any] = macro MatchPatternMacro.orNotMatchPatternMatcher
  }

  /**
   * This method enables the following syntax given a <code>MatcherFactory6</code>:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory or not contain value (<span class="stLiteral">3</span>)
   *                 ^
   * </pre>
   */
  def or(notWord: NotWord)(implicit prettifier: Prettifier, pos: source.Position): OrNotWord = new OrNotWord(prettifier, pos)

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory or exist
   *                 ^
   * </pre>
   */
  def or(existWord: ExistWord): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Existence] = 
    thisMatcherFactory.or(MatcherWords.exist.matcherFactory)

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * aMatcherFactory or not (exist)
   *                 ^
   * </pre>
   */
  def or(notExist: ResultOfNotExist): MatcherFactory7[SC, TC1, TC2, TC3, TC4, TC5, TC6, Existence] = 
    thisMatcherFactory.or(MatcherWords.not.exist)
}

/**
 * Companion object containing an implicit method that converts a <code>MatcherFactory6</code> to a <code>Matcher</code>.
 *
 * @author Bill Venners
 */
object MatcherFactory6 {

  import scala.language.implicitConversions

  /**
   * Converts a <code>MatcherFactory6</code> to a <code>Matcher</code>.
   *
   * @param matcherFactory a MatcherFactory6 to convert
   * @return a Matcher produced by the passed MatcherFactory6
   */
  implicit def produceMatcher[SC, TC1[_], TC2[_], TC3[_], TC4[_], TC5[_], TC6[_], T <: SC : TC1 : TC2 : TC3 : TC4 : TC5 : TC6](matcherFactory: MatcherFactory6[SC, TC1, TC2, TC3, TC4, TC5, TC6]): Matcher[T] =
    matcherFactory.matcher

  import scala.reflect.macros.Context

  /**
   * This method is called by macro that supports 'and not a [Type]' syntax.
   */
  def andNotATypeMatcherFactory6[SC, TC1[_], TC2[_], TC3[_], TC4[_], TC5[_], TC6[_]](context: Context)(aType: context.Expr[ResultOfATypeInvocation[_]]): context.Expr[MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6]] =
    new MatcherFactory6Macro[SC, TC1, TC2, TC3, TC4, TC5, TC6].andNotATypeMatcherFactory6(context)(aType)

  /**
   * This method is called by macro that supports 'or not a [Type]' syntax.
   */
  def orNotATypeMatcherFactory6[SC, TC1[_], TC2[_], TC3[_], TC4[_], TC5[_], TC6[_]](context: Context)(aType: context.Expr[ResultOfATypeInvocation[_]]): context.Expr[MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6]] =
    new MatcherFactory6Macro[SC, TC1, TC2, TC3, TC4, TC5, TC6].orNotATypeMatcherFactory6(context)(aType)

  /**
   * This method is called by macro that supports 'and not a [Type]' syntax.
   */
  def andNotAnTypeMatcherFactory6[SC, TC1[_], TC2[_], TC3[_], TC4[_], TC5[_], TC6[_]](context: Context)(anType: context.Expr[ResultOfAnTypeInvocation[_]]): context.Expr[MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6]] =
    new MatcherFactory6Macro[SC, TC1, TC2, TC3, TC4, TC5, TC6].andNotAnTypeMatcherFactory6(context)(anType)

  /**
   * This method is called by macro that supports 'or not a [Type]' syntax.
   */
  def orNotAnTypeMatcherFactory6[SC, TC1[_], TC2[_], TC3[_], TC4[_], TC5[_], TC6[_]](context: Context)(anType: context.Expr[ResultOfAnTypeInvocation[_]]): context.Expr[MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6]] =
    new MatcherFactory6Macro[SC, TC1, TC2, TC3, TC4, TC5, TC6].orNotAnTypeMatcherFactory6(context)(anType)
}

private[scalatest] class MatcherFactory6Macro[-SC, TC1[_], TC2[_], TC3[_], TC4[_], TC5[_], TC6[_]] {

  import scala.reflect.macros.Context

  def andNotATypeMatcherFactory6(context: Context)(aType: context.Expr[ResultOfATypeInvocation[_]]): context.Expr[MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6]] = {
    import context.universe._

    val rhs = TypeMatcherMacro.notATypeMatcher(context)(aType)

    context.macroApplication match {
      case Apply(Select(qualifier, _), _) =>
        context.Expr(
          Apply(
            Select(
              Select(
                qualifier,
                "owner"
              ),
              newTermName("and")
            ),
            List(rhs.tree)
          )
        )
      case _ => context.abort(context.macroApplication.pos, "This macro should be used with 'and not' syntax only.")
    }
  }

  def orNotATypeMatcherFactory6(context: Context)(aType: context.Expr[ResultOfATypeInvocation[_]]): context.Expr[MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6]] = {
    import context.universe._

    val rhs = TypeMatcherMacro.notATypeMatcher(context)(aType)

    context.macroApplication match {
      case Apply(Select(qualifier, _), _) =>
        context.Expr(
          Apply(
            Select(
              Select(
                qualifier,
                "owner"
              ),
              newTermName("or")
            ),
            List(rhs.tree)
          )
        )
      case _ => context.abort(context.macroApplication.pos, "This macro should be used with 'or not' syntax only.")
    }
  }

  def andNotAnTypeMatcherFactory6(context: Context)(anType: context.Expr[ResultOfAnTypeInvocation[_]]): context.Expr[MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6]] = {
    import context.universe._

    val rhs = TypeMatcherMacro.notAnTypeMatcher(context)(anType)

    context.macroApplication match {
      case Apply(Select(qualifier, _), _) =>
        context.Expr(
          Apply(
            Select(
              Select(
                qualifier,
                "owner"
              ),
              newTermName("and")
            ),
            List(rhs.tree)
          )
        )
      case _ => context.abort(context.macroApplication.pos, "This macro should be used with 'and not' syntax only.")
    }
  }

  def orNotAnTypeMatcherFactory6(context: Context)(anType: context.Expr[ResultOfAnTypeInvocation[_]]): context.Expr[MatcherFactory6[SC with AnyRef, TC1, TC2, TC3, TC4, TC5, TC6]] = {
    import context.universe._

    val rhs = TypeMatcherMacro.notAnTypeMatcher(context)(anType)

    context.macroApplication match {
      case Apply(Select(qualifier, _), _) =>
        context.Expr(
          Apply(
            Select(
              Select(
                qualifier,
                "owner"
              ),
              newTermName("or")
            ),
            List(rhs.tree)
          )
        )
      case _ => context.abort(context.macroApplication.pos, "This macro should be used with 'or not' syntax only.")
    }
  }

}

                    