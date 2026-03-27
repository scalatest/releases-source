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

import org.scalactic._
import org.scalatest.enablers._
import org.scalatest.matchers._
import org.scalatest._
import org.scalactic.TripleEqualsSupport.Spread
import org.scalactic.DefaultEquality.areEqualComparingArraysStructurally
import scala.collection.GenTraversable
import TripleEqualsSupport.TripleEqualsInvocation
// SKIP-SCALATESTJS,NATIVE-START
import org.scalatest.matchers.MatchersHelper.matchSymbolToPredicateMethod
// SKIP-SCALATESTJS,NATIVE-END
import org.scalatest.FailureMessages
import org.scalatest.matchers.MatchersHelper.endWithRegexWithGroups
import org.scalatest.matchers.MatchersHelper.fullyMatchRegexWithGroups
import org.scalatest.matchers.MatchersHelper.includeRegexWithGroups
import org.scalatest.matchers.MatchersHelper.startWithRegexWithGroups
import org.scalatest.Resources
import org.scalatest.Suite.getObjectsForFailureMessage
import org.scalatest.UnquotedString
import scala.annotation.tailrec
import org.scalatest.exceptions.NotAllowedException

/**
 * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
 * the matchers DSL.
 *
 * @author Bill Venners
 */
final class NotWord {

  /**
   * This method enables the following syntax, where <code>tempFile</code>, for example, refers to a <code>java.io.File</code>
   * and <code>exist</code> is a <code>Matcher[java.io.File]</code>: 
   *
   * <pre class="stHighlighted">
   * tempFile should not (exist)
   *                     ^
   * </pre>
   **/
  def apply[S](matcher: Matcher[S]): Matcher[S] =
    new Matcher[S] {
      def apply(left: S): MatchResult = matcher(left).negated
      override def toString: String = "not (" + Prettifier.default(matcher) + ")"
    }

  import scala.language.higherKinds

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * hasNoSize should not { have size (<span class="stLiteral">2</span>) and equal (hasNoSize) }
   *                      ^
   * </pre>
   **/
  def apply[S, TYPECLASS[_]](matcherGen1: MatcherFactory1[S, TYPECLASS]): MatcherFactory1[S, TYPECLASS] = {
    new MatcherFactory1[S, TYPECLASS] {
      def matcher[V <: S : TYPECLASS]: Matcher[V] = {
        val innerMatcher: Matcher[V] = matcherGen1.matcher
        new Matcher[V] {
          def apply(left: V): MatchResult = innerMatcher(left).negated
          override def toString: String = "not (" + Prettifier.default(matcherGen1) + ")"
        }
      }
      override def toString: String = "not (" + Prettifier.default(matcherGen1) + ")"
    }
  }

  def apply[S, TYPECLASS1[_], TYPECLASS2[_]](matcherGen2: MatcherFactory2[S, TYPECLASS1, TYPECLASS2]): MatcherFactory2[S, TYPECLASS1, TYPECLASS2] = {
    new MatcherFactory2[S, TYPECLASS1, TYPECLASS2] {
      def matcher[V <: S : TYPECLASS1 : TYPECLASS2]: Matcher[V] = {
        val innerMatcher: Matcher[V] = matcherGen2.matcher
        new Matcher[V] {
          def apply(left: V): MatchResult = innerMatcher(left).negated
          override def toString: String = "not (" + Prettifier.default(matcherGen2) + ")"
        }
      }
      override def toString: String = "not (" + Prettifier.default(matcherGen2) + ")"
    }
  }

  /**
   * This method enables any <code>BeMatcher</code> to be negated by passing it to <code>not</code>. 
   * For example, if you have a <code>BeMatcher[Int]</code> called <code>odd</code>, which matches
   * <code>Int</code>s that are odd, you can negate it to get a <code>BeMatcher[Int]</code> that matches
   * even <code>Int</code>s, like this:
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> even = not (odd)
   *                ^
   * </pre>
   *
   * <p>
   * In addition, this method enables you to negate a <code>BeMatcher</code> at its point of use, like this:
   * </p>
   *
   * <pre class="stHighlighted">
   * num should be (not (odd))
   * </pre>
   *
   * <p>
   * Nevertheless, in such as case it would be more idiomatic to write:
   * </p>
   *
   * <pre class="stHighlighted">
   * num should not be (odd)
   * </pre>
   */
  def apply[S](beMatcher: BeMatcher[S]): BeMatcher[S] =
    new BeMatcher[S] {
      def apply(left: S): MatchResult = beMatcher(left).negated
      override def toString: String = "not (" + Prettifier.default(beMatcher) + ")"
    }
  
  /**
   * This method enables syntax such as the following:
   *
   * <pre class="stHighlighted">
   * file should not (exist)
   *             ^
   * </pre>
   **/
  def apply(existWord: ExistWord): ResultOfNotExist = 
    new ResultOfNotExist(this)

  /* 
   * This is used in logical expression like: 
   * outerInstance.and(MatcherWords.not.exist)
   *                                    ^
   */ 
  private[scalatest] val exist: MatcherFactory1[Any, Existence] = 
    new MatcherFactory1[Any, Existence] {
      def matcher[T : Existence]: Matcher[T] = 
        new Matcher[T] {
          def apply(left: T): MatchResult = {
            val existence = implicitly[Existence[T]]
            MatchResult(
              !existence.exists(left), 
              Resources.rawExists,
              Resources.rawDoesNotExist,
              Vector(left)
            )
          } 
          override def toString: String = "not exist"
        }
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * num should (not equal (<span class="stLiteral">7</span>) and be &lt; (<span class="stLiteral">9</span>))
   *                 ^
   * </pre>
   **/
  def equal(right: Any): MatcherFactory1[Any, Equality] = apply(MatcherWords.equal(right))

  /**
   * This method enables the following syntax for the "primitive" numeric types: 
   *
   * <pre class="stHighlighted">
   * sevenDotOh should ((not equal (<span class="stLiteral">17.1</span> +- <span class="stLiteral">0.2</span>)) and (not equal (<span class="stLiteral">27.1</span> +- <span class="stLiteral">0.2</span>)))
   *                         ^
   * </pre>
   **/
  def equal[U](spread: Spread[U]): Matcher[U] = {
    new Matcher[U] {
      def apply(left: U): MatchResult = {
        MatchResult(
          !(spread.isWithin(left)),
          Resources.rawEqualedPlusOrMinus,
          Resources.rawDidNotEqualPlusOrMinus,
          Vector(left, spread.pivot, spread.tolerance)
        )
      }
      override def toString: String = "not equal " + Prettifier.default(spread)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * map should (not equal (<span class="stReserved">null</span>))
   *                 ^
   * </pre>
   **/
  def equal(o: Null): Matcher[AnyRef] =
    new Matcher[AnyRef] {
      def apply(left: AnyRef): MatchResult = {
        MatchResult(
          left != null,
          Resources.rawEqualedNull,
          Resources.rawDidNotEqualNull,
          Resources.rawMidSentenceEqualedNull,
          Resources.rawDidNotEqualNull,
          Vector.empty, 
          Vector(left), 
          Vector.empty, 
          Vector(left)
        )
      }
      override def toString: String = "not equal null"
    }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not have length (<span class="stLiteral">5</span>) and not have length (<span class="stLiteral">3</span>))
   *                         ^
   * </pre>
   **/
  def have(resultOfLengthWordApplication: ResultOfLengthWordApplication): MatcherFactory1[Any, Length] =
    apply(MatcherWords.have.length(resultOfLengthWordApplication.expectedLength))

  // This looks similar to the AndNotWord one, but not quite the same because no and
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not have size (<span class="stLiteral">5</span>) and not have size (<span class="stLiteral">3</span>))
   *                         ^
   * </pre>
   **/
  def have(resultOfSizeWordApplication: ResultOfSizeWordApplication): MatcherFactory1[Any, Size] =
    apply(MatcherWords.have.size(resultOfSizeWordApplication.expectedSize))
    
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * result should (not have message (<span class="stQuotedString">"Message from Mars!"</span>) and not have message (<span class="stQuotedString">"Message from Mars!"</span>))
   *                    ^
   * </pre>
   **/
  def have(resultOfMessageWordApplication: ResultOfMessageWordApplication): MatcherFactory1[Any, Messaging] =
    apply(MatcherWords.have.message(resultOfMessageWordApplication.expectedMessage))

  /**
   * This method enables the following syntax, where, for example, <code>book</code> is of type <code>Book</code> and <code>title</code> and <code>author</code>
   * are both of type <code>HavePropertyMatcher[Book, String]</code>:
   *
   * <pre class="stHighlighted">
   * book should (not have (title (<span class="stQuotedString">"Moby Dick"</span>)) and (not have (author (<span class="stQuotedString">"Melville"</span>))))
   *                  ^
   * </pre>
   **/
  def have[T](firstPropertyMatcher: HavePropertyMatcher[T, _], propertyMatchers: HavePropertyMatcher[T, _]*): Matcher[T] =
    apply(MatcherWords.have(firstPropertyMatcher, propertyMatchers: _*))

  /**
   * This method enables the following syntax, where, for example, <code>num</code> is an <code>Int</code> and <code>odd</code>
   * of type <code>BeMatcher[Int]</code>:
   *
   * <pre class="stHighlighted">
   * num should (not be (odd) and be &lt;= (<span class="stLiteral">8</span>))
   *                 ^
   * </pre>
   **/
  def be[T](beMatcher: BeMatcher[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = beMatcher(left).negated
      override def toString: String = "not be " + Prettifier.default(beMatcher)
    }
  }

  import scala.language.experimental.macros

  /**
   * This method enables the following syntax, where, for example, <code>num</code> is an <code>Int</code> and <code>odd</code>
   * of type <code>BeMatcher[Int]</code>:
   *
   * <pre class="stHighlighted">
   * result should (not matchPattern { <span class="stReserved">case</span> <span class="stType">Person</span>(<span class="stQuotedString">"Bob"</span>, _)} and equal (result2))
   *                    ^
   * </pre>
   **/
  def matchPattern(right: PartialFunction[Any, _]): Matcher[Any] = macro MatchPatternMacro.notMatchPatternMatcher

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * map should (not be (<span class="stReserved">null</span>))
   *                 ^
   * </pre>
   **/
  def be(o: Null): Matcher[AnyRef] =
    new Matcher[AnyRef] {
      def apply(left: AnyRef): MatchResult = {
        MatchResult(
          left != null,
          Resources.rawWasNull,
          Resources.rawWasNotNull,
          Resources.rawMidSentenceWasNull,
          Resources.rawWasNotNull,
          Vector.empty, 
          Vector(left), 
          Vector.empty, 
          Vector(left)
        )
      }
      override def toString: String = "not be null"
    }

  // These next four are for things like not be </>/<=/>=:
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * num should (not be < (<span class="stLiteral">7</span>) and not be > (<span class="stLiteral">10</span>))
   *                 ^
   * </pre>
   **/
  def be[T](resultOfLessThanComparison: ResultOfLessThanComparison[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult =
        MatchResult(
          !resultOfLessThanComparison(left),
          Resources.rawWasLessThan,
          Resources.rawWasNotLessThan,
          Vector(left, resultOfLessThanComparison.right)
        )
      override def toString: String = "not be " + Prettifier.default(resultOfLessThanComparison)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * num should (not be > (<span class="stLiteral">10</span>) and not be < (<span class="stLiteral">7</span>))
   *                 ^
   * </pre>
   **/
  def be[T](resultOfGreaterThanComparison: ResultOfGreaterThanComparison[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult =
        MatchResult(
          !resultOfGreaterThanComparison(left),
          Resources.rawWasGreaterThan,
          Resources.rawWasNotGreaterThan,
          Vector(left, resultOfGreaterThanComparison.right)
        )
      override def toString: String = "not be " + Prettifier.default(resultOfGreaterThanComparison)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * num should (not be <= (<span class="stLiteral">7</span>) and not be > (<span class="stLiteral">10</span>))
   *                 ^
   * </pre>
   **/
  def be[T](resultOfLessThanOrEqualToComparison: ResultOfLessThanOrEqualToComparison[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult =
        MatchResult(
          !resultOfLessThanOrEqualToComparison(left),
          Resources.rawWasLessThanOrEqualTo,
          Resources.rawWasNotLessThanOrEqualTo,
          Vector(left, resultOfLessThanOrEqualToComparison.right)
        )
      override def toString: String = "not be " + Prettifier.default(resultOfLessThanOrEqualToComparison)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * num should (not be >= (<span class="stLiteral">10</span>) and not be < (<span class="stLiteral">7</span>))
   *                 ^
   * </pre>
   **/
  def be[T](resultOfGreaterThanOrEqualToComparison: ResultOfGreaterThanOrEqualToComparison[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult =
        MatchResult(
          !resultOfGreaterThanOrEqualToComparison(left),
          Resources.rawWasGreaterThanOrEqualTo,
          Resources.rawWasNotGreaterThanOrEqualTo,
          Vector(left, resultOfGreaterThanOrEqualToComparison.right)
        )
      override def toString: String = "not be " + Prettifier.default(resultOfGreaterThanOrEqualToComparison)
    }
  }

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
  @deprecated("The deprecation period for the be === syntax has expired. Please use should equal, should ===, shouldEqual, should be, or shouldBe instead.")
  def be(tripleEqualsInvocation: TripleEqualsInvocation[_])(implicit pos: source.Position): Matcher[Any] = {
    throw new NotAllowedException(FailureMessages.beTripleEqualsNotAllowed, pos)
  }

  // SKIP-SCALATESTJS,NATIVE-START
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * myFile should (not be (<span class="stQuotedString">'hidden</span>) and have (name (<span class="stQuotedString">"temp.txt"</span>)))
   *                    ^
   * </pre>
   **/
  def be[T <: AnyRef](symbol: Symbol)(implicit prettifier: Prettifier, pos: source.Position): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = {
        val positiveMatchResult = matchSymbolToPredicateMethod(left, symbol, false, false, prettifier, pos)
        MatchResult(
          !positiveMatchResult.matches,
          positiveMatchResult.rawNegatedFailureMessage,
          positiveMatchResult.rawFailureMessage, 
          positiveMatchResult.negatedFailureMessageArgs, 
          positiveMatchResult.failureMessageArgs
        )
      }
      override def toString: String = "not be " + Prettifier.default(symbol)
    }
  }
  // SKIP-SCALATESTJS,NATIVE-END

  /**
   * This method enables the following syntax, where <code>tempFile</code>, for example, refers to a <code>java.io.File</code>
   * and <code>hidden</code> is a <code>BePropertyMatcher[java.io.File]</code>: 
   *
   * <pre class="stHighlighted">
   * tempFile should (not be (hidden) and have (<span class="stQuotedString">'name</span> (<span class="stQuotedString">"temp.txt"</span>)))
   *                    ^
   * </pre>
   **/
  def be[T <: AnyRef](bePropertyMatcher: BePropertyMatcher[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = {
        val result = bePropertyMatcher(left)
        MatchResult(
          !result.matches,
          Resources.rawWas,
          Resources.rawWasNot,
          Vector(left, UnquotedString(result.propertyName))
        )
      }
      override def toString: String = "not be " + Prettifier.default(bePropertyMatcher)
    }
  }

  // SKIP-SCALATESTJS,NATIVE-START
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * isNotFileMock should (not be a (<span class="stQuotedString">'file</span>) and have (<span class="stQuotedString">'name</span> (<span class="stQuotedString">"temp.txt"</span>))))
   *                           ^
   * </pre>
   **/
  def be[T <: AnyRef](resultOfAWordApplication: ResultOfAWordToSymbolApplication)(implicit prettifier: Prettifier, pos: source.Position): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = {
        val positiveMatchResult = matchSymbolToPredicateMethod(left, resultOfAWordApplication.symbol, true, true, prettifier, pos)
        MatchResult(
          !positiveMatchResult.matches,
          positiveMatchResult.rawNegatedFailureMessage,
          positiveMatchResult.rawFailureMessage, 
          positiveMatchResult.negatedFailureMessageArgs, 
          positiveMatchResult.failureMessageArgs
        )
      }
      override def toString: String = "not be " + Prettifier.default(resultOfAWordApplication)
    }
  }
  // SKIP-SCALATESTJS,NATIVE-END

  /**
   * This method enables the following syntax, where <code>notSoSecretFile</code>, for example, refers to a <code>java.io.File</code>
   * and <code>directory</code> is a <code>BePropertyMatcher[java.io.File]</code>: 
   *
   * <pre class="stHighlighted">
   * notSoSecretFile should (not be a (directory) and have (<span class="stQuotedString">'name</span> (<span class="stQuotedString">"passwords.txt"</span>)))
   *                             ^
   * </pre>
   **/
  def be[T <: AnyRef](resultOfAWordApplication: ResultOfAWordToBePropertyMatcherApplication[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = {
        val result = resultOfAWordApplication.bePropertyMatcher(left)
        MatchResult(
          !result.matches,
          Resources.rawWasA,
          Resources.rawWasNotA,
          Vector(left, UnquotedString(result.propertyName))
        )
      }
      override def toString: String = "not be " + Prettifier.default(resultOfAWordApplication)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * result should (not be a (passedMarks) and be a (validMarks)))
   *                    ^
   * </pre>
   **/
  def be[T](resultOfAWordApplication: ResultOfAWordToAMatcherApplication[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = {
        val result = resultOfAWordApplication.aMatcher(left)
        MatchResult(
          !result.matches,
          result.rawNegatedFailureMessage,
          result.rawFailureMessage, 
          result.negatedFailureMessageArgs, 
          result.failureMessageArgs
        )
      }
      override def toString: String = "not be " + Prettifier.default(resultOfAWordApplication)
    }
  }

  // SKIP-SCALATESTJS,NATIVE-START
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * isNotAppleMock should (not be an (<span class="stQuotedString">'apple</span>) and not be (<span class="stQuotedString">'rotten</span>))
   *                            ^
   * </pre>
   **/
  def be[T <: AnyRef](resultOfAnWordApplication: ResultOfAnWordToSymbolApplication)(implicit prettifier: Prettifier, pos: source.Position): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = {
        val positiveMatchResult = matchSymbolToPredicateMethod(left, resultOfAnWordApplication.symbol, true, false, prettifier, pos)
        MatchResult(
          !positiveMatchResult.matches,
          positiveMatchResult.rawNegatedFailureMessage,
          positiveMatchResult.rawFailureMessage, 
          positiveMatchResult.negatedFailureMessageArgs, 
          positiveMatchResult.failureMessageArgs
        )
      }
      override def toString: String = "not be " + Prettifier.default(resultOfAnWordApplication)
    }
  }
  // SKIP-SCALATESTJS,NATIVE-END

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * myFile should (not be an (directory) and not be an (directory))
   *                    ^
   * </pre>
   **/
  def be[T <: AnyRef](resultOfAnWordApplication: ResultOfAnWordToBePropertyMatcherApplication[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = {
        val result = resultOfAnWordApplication.bePropertyMatcher(left)
        MatchResult(
          !result.matches,
          Resources.rawWasAn,
          Resources.rawWasNotAn,
          Vector(left, UnquotedString(result.propertyName))
        )
      }
      override def toString: String = "not be " + Prettifier.default(resultOfAnWordApplication)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * result should (not be a (passedMarks) and be a (validMarks)))
   *                    ^
   * </pre>
   **/
  def be[T](resultOfAnWordApplication: ResultOfAnWordToAnMatcherApplication[T]): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = {
        val result = resultOfAnWordApplication.anMatcher(left)
        MatchResult(
          !result.matches,
          result.rawNegatedFailureMessage,
          result.rawFailureMessage, 
          result.negatedFailureMessageArgs, 
          result.failureMessageArgs
        )
      }
      override def toString: String = "not be " + Prettifier.default(resultOfAnWordApplication)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * myFish should (not be theSameInstanceAs (redFish) and not be theSameInstanceAs (blueFish))
   *                    ^
   * </pre>
   **/
  def be[T <: AnyRef](resultOfTheSameInstanceAsApplication: ResultOfTheSameInstanceAsApplication): Matcher[T] = {
    new Matcher[T] {
      def apply(left: T): MatchResult = {
        MatchResult(
          resultOfTheSameInstanceAsApplication.right ne left,
          Resources.rawWasSameInstanceAs,
          Resources.rawWasNotSameInstanceAs,
          Vector(left, resultOfTheSameInstanceAsApplication.right)
        )
      }
      override def toString: String = "not be " + Prettifier.default(resultOfTheSameInstanceAsApplication)
    }
  }

  /**
   * This method enables the following syntax for the "primitive" numeric types: 
   *
   * <pre class="stHighlighted">
   * sevenDotOh should ((not be (<span class="stLiteral">17.1</span> +- <span class="stLiteral">0.2</span>)) and (not be (<span class="stLiteral">27.1</span> +- <span class="stLiteral">0.2</span>)))
   *                         ^
   * </pre>
   **/
  def be[U](spread: Spread[U]): Matcher[U] = {
    new Matcher[U] {
      def apply(left: U): MatchResult = {
        MatchResult(
          !(spread.isWithin(left)),
          Resources.rawWasPlusOrMinus,
          Resources.rawWasNotPlusOrMinus,
          Vector(left, spread.pivot, spread.tolerance)
        )
      }
      override def toString: String = "not be " + Prettifier.default(spread)
    }
  }
  
  /**
   * This method enables the following syntax, where fraction is a <code>PartialFunction</code>:
   *
   * <pre class="stHighlighted">
   * fraction should (not be definedAt (<span class="stLiteral">8</span>) and not be definedAt (<span class="stLiteral">0</span>))
   *                      ^
   * </pre>
   **/
  def be[A, U <: PartialFunction[A, _]](resultOfDefinedAt: ResultOfDefinedAt[A]): Matcher[U] = {
    new Matcher[U] {
      def apply(left: U): MatchResult =
        MatchResult(
          !(left.isDefinedAt(resultOfDefinedAt.right)),
          Resources.rawWasDefinedAt,
          Resources.rawWasNotDefinedAt,
          Vector(left, resultOfDefinedAt.right)
        )
      override def toString: String = "not be " + Prettifier.default(resultOfDefinedAt)
    }
  }

  /**
   * This method enables <code>be</code> to be used for inequality comparison. Here are some examples:
   *
   * <pre class="stHighlighted">
   * result should not be (<span class="stType">None</span>)
   *                      ^
   * result should not be (<span class="stType">Some</span>(<span class="stLiteral">1</span>))
   *                      ^
   * result should not be (<span class="stReserved">true</span>)
   *                      ^
   * result should not be (<span class="stReserved">false</span>)
   *                      ^
   * sum should not be (<span class="stLiteral">19</span>)
   *                   ^
   * </pre>
   **/
  def be(right: Any): Matcher[Any] = {
    new Matcher[Any] {
      def apply(left: Any): MatchResult = {
        left match {
          case null =>
            MatchResult(
              right != null, 
              Resources.rawWasNull,
              Resources.rawWasNotNull,
              Resources.rawMidSentenceWasNull,
              Resources.rawWasNotNull,
              Vector.empty, 
              Vector(right)
            )
          case _ => 
            val (leftee, rightee) = getObjectsForFailureMessage(left, right) // TODO: To move this to reporter
            MatchResult(
              !areEqualComparingArraysStructurally(left, right),
              Resources.rawWasEqualTo,
              Resources.rawWasNotEqualTo,
              Vector(left, right), 
              Vector(leftee, rightee)
            )
        }
      }
      override def toString: String = "not be " + Prettifier.default(right)
    }
  }
  
  /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * fraction should (not be sorted and not be sorted)
     *                      ^
     * </pre>
     **/
  def be[T](sortedWord: SortedWord): MatcherFactory1[Any, Sortable] =
    apply(MatcherWords.be(sortedWord))
    
  /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * fraction should (not be readable and not equal readableFile)
     *                      ^
     * </pre>
     **/
  def be(readableWord: ReadableWord): MatcherFactory1[Any, Readability] =
    apply(MatcherWords.be(readableWord))
  
  /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * fraction should (not be writable and not be writableFile)
     *                      ^
     * </pre>
     **/
  def be(writableWord: WritableWord): MatcherFactory1[Any, Writability] =
    apply(MatcherWords.be(writableWord))
    
  /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * nonEmptyList should (not be empty and not equal emptyList)
     *                          ^
     * </pre>
     **/
  def be(emptyWord: EmptyWord): MatcherFactory1[Any, Emptiness] =
    apply(MatcherWords.be(emptyWord))
    
  /**
     * This method enables the following syntax:
     *
     * <pre class="stHighlighted">
     * result should (not be defined and not equal something)
     *                    ^
     * </pre>
     **/
  def be(definedWord: DefinedWord): MatcherFactory1[Any, Definition] =
    apply(MatcherWords.be(definedWord))
    
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * result should (not be a [<span class="stType">Book</span>] and not be sorted)
   *                    ^
   * </pre>
   **/
  def be(aType: ResultOfATypeInvocation[_]): Matcher[Any] = macro TypeMatcherMacro.notATypeMatcher
  
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * result should (not be an [<span class="stType">Book</span>] and not be sorted)
   *                    ^
   * </pre>
   **/
  def be(anType: ResultOfAnTypeInvocation[_]): Matcher[Any] = macro TypeMatcherMacro.notAnTypeMatcher

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * string should (not fullyMatch regex (<span class="stQuotedString">"Hel*o"</span>) and not include (<span class="stQuotedString">"orld"</span>))
   *                    ^
   * </pre>
   **/
  def fullyMatch(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[String] = {
    new Matcher[String] {
      def apply(left: String): MatchResult = {
        val result = fullyMatchRegexWithGroups(left, resultOfRegexWordApplication.regex, resultOfRegexWordApplication.groups)
        MatchResult(
          !result.matches, 
          result.rawNegatedFailureMessage, 
          result.rawFailureMessage, 
          result.negatedFailureMessageArgs, 
          result.failureMessageArgs
        )
      }
      override def toString: String = "not fullyMatch " + Prettifier.default(resultOfRegexWordApplication)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * string should (not include regex (<span class="stQuotedString">"Hel.o"</span>) and not include regex (<span class="stQuotedString">"""(-)?(\d+)(\.\d*)?"""</span>))
   *                    ^
   * </pre>
   **/
  def include(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[String] = {
    val rightRegex = resultOfRegexWordApplication.regex
    new Matcher[String] {
      def apply(left: String): MatchResult = {
        val result = includeRegexWithGroups(left, resultOfRegexWordApplication.regex, resultOfRegexWordApplication.groups)
        MatchResult(
          !result.matches, 
          result.rawNegatedFailureMessage, 
          result.rawFailureMessage, 
          result.negatedFailureMessageArgs, 
          result.failureMessageArgs
        )
      }
      override def toString: String = "not include " + Prettifier.default(resultOfRegexWordApplication)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * string should (not include (<span class="stQuotedString">"cat"</span>) and not include (<span class="stQuotedString">"1.7"</span>))
   *                    ^
   * </pre>
   **/
  def include(expectedSubstring: String): Matcher[String] = {
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          !(left.indexOf(expectedSubstring) >= 0), 
          Resources.rawIncludedSubstring,
          Resources.rawDidNotIncludeSubstring,
          Vector(left, expectedSubstring)
        )
      override def toString: String = "not include " + Prettifier.default(expectedSubstring)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * string should (not startWith regex (<span class="stQuotedString">"hel*o"</span>) and not endWith regex (<span class="stQuotedString">"wor.d"</span>))
   *                    ^
   * </pre>
   **/
  def startWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[String] = {
    val rightRegex = resultOfRegexWordApplication.regex
    new Matcher[String] {
      def apply(left: String): MatchResult = {
        val result = startWithRegexWithGroups(left, resultOfRegexWordApplication.regex, resultOfRegexWordApplication.groups)
        MatchResult(
          !result.matches, 
          result.rawNegatedFailureMessage, 
          result.rawFailureMessage, 
          result.negatedFailureMessageArgs, 
          result.failureMessageArgs
        )
      }
      override def toString: String = "not startWith " + Prettifier.default(resultOfRegexWordApplication)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * string should ((not startWith (<span class="stQuotedString">"red"</span>)) and (not startWith (<span class="stQuotedString">"1.7"</span>)))
   *                     ^
   * </pre>
   **/
  def startWith(expectedSubstring: String): Matcher[String] = {
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          left.indexOf(expectedSubstring) != 0,
          Resources.rawStartedWith,
          Resources.rawDidNotStartWith,
          Vector(left, expectedSubstring)
        )
      override def toString: String = "not startWith " + Prettifier.default(expectedSubstring)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * string should (not endWith regex (<span class="stQuotedString">"wor.d"</span>) and not startWith regex (<span class="stQuotedString">"Hel*o"</span>))
   *                    ^
   * </pre>
   **/
  def endWith(resultOfRegexWordApplication: ResultOfRegexWordApplication): Matcher[String] = {
    val rightRegex = resultOfRegexWordApplication.regex
    new Matcher[String] {
      def apply(left: String): MatchResult = {
        val result = endWithRegexWithGroups(left, resultOfRegexWordApplication.regex, resultOfRegexWordApplication.groups)
        MatchResult(
          !result.matches, 
          result.rawNegatedFailureMessage, 
          result.rawFailureMessage, 
          result.negatedFailureMessageArgs, 
          result.failureMessageArgs
        )
      }
      override def toString: String = "not endWith " + Prettifier.default(resultOfRegexWordApplication)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * string should (not endWith (<span class="stQuotedString">"blue"</span>) and not endWith (<span class="stQuotedString">"1.7"</span>))
   *                    ^
   * </pre>
   **/
  def endWith(expectedSubstring: String): Matcher[String] = {
    new Matcher[String] {
      def apply(left: String): MatchResult = {
        MatchResult(
          !(left endsWith expectedSubstring),
          Resources.rawEndedWith,
          Resources.rawDidNotEndWith,
          Vector(left, expectedSubstring)
        )
      }
      override def toString: String = "not endWith " + Prettifier.default(expectedSubstring)
    }
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * list should (not contain (<span class="stReserved">null</span>))
   *                  ^
   * </pre>
   **/
  def contain(nullValue: Null): MatcherFactory1[Any, Containing] = {
    new MatcherFactory1[Any, Containing] {
      def matcher[U : Containing]: Matcher[U] =
        new Matcher[U] {
          def apply(left: U): MatchResult = {
            val containing = implicitly[Containing[U]]
            MatchResult(
              !containing.contains(left, null),
              Resources.rawContainedNull,
              Resources.rawDidNotContainNull,
              Vector(left)
            )
          }
          override def toString: String = "not contain null"
        }
      override def toString: String = "not contain null"
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain (<span class="stLiteral">5</span>) and not contain (<span class="stLiteral">3</span>))
   *                         ^
   * </pre>
   **/
  def contain[T](expectedElement: T): MatcherFactory1[Any, Containing] = {
    new MatcherFactory1[Any, Containing] {
      def matcher[U : Containing]: Matcher[U] = 
        new Matcher[U] {
          def apply(left: U): MatchResult = {
            val containing = implicitly[Containing[U]]
            MatchResult(
              !containing.contains(left, expectedElement),
              Resources.rawContainedExpectedElement,
              Resources.rawDidNotContainExpectedElement,
              Vector(left, expectedElement)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(expectedElement)
        }
      override def toString: String = "not contain " + Prettifier.default(expectedElement)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain oneOf (<span class="stLiteral">5</span>, <span class="stLiteral">6</span>, <span class="stLiteral">7</span>))
   *                         ^
   * </pre>
   **/
  def contain[T](oneOf: ResultOfOneOfApplication)(implicit prettifier: Prettifier): MatcherFactory1[Any, Containing] = {
    new MatcherFactory1[Any, Containing] {
      def matcher[T](implicit containing: Containing[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = oneOf.right

            MatchResult(
              !containing.containsOneOf(left, right),
              Resources.rawContainedOneOfElements,
              Resources.rawDidNotContainOneOfElements,
              Vector(left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
            )
          }
          override def toString: String = "not contain " + Prettifier.default(oneOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(oneOf)
    }
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain oneElementOf (<span class="stType">List</span>(<span class="stLiteral">5</span>, <span class="stLiteral">6</span>, <span class="stLiteral">7</span>)))
   *                         ^
   * </pre>
   **/
  def contain[T](oneElementOf: ResultOfOneElementOfApplication): MatcherFactory1[Any, Containing] = {
    new MatcherFactory1[Any, Containing] {
      def matcher[T](implicit containing: Containing[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {

            val right = oneElementOf.right

            MatchResult(
              !containing.containsOneOf(left, right.distinct),
              Resources.rawContainedOneElementOf,
              Resources.rawDidNotContainOneElementOf,
              Vector(left, right)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(oneElementOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(oneElementOf)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain (<span class="stLiteral">5</span>) and not contain (<span class="stLiteral">3</span>))
   *                         ^
   * </pre>
   **/
  def contain[T](atLeastOneOf: ResultOfAtLeastOneOfApplication)(implicit prettifier: Prettifier): MatcherFactory1[Any, Aggregating] = {
    new MatcherFactory1[Any, Aggregating] {
      def matcher[T](implicit aggregating: Aggregating[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = atLeastOneOf.right

            MatchResult(
              !aggregating.containsAtLeastOneOf(left, right),
              Resources.rawContainedAtLeastOneOf,
              Resources.rawDidNotContainAtLeastOneOf,
              Vector(left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
            )
          }
          override def toString: String = "not contain " + Prettifier.default(atLeastOneOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(atLeastOneOf)
    }
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain atLeastOneElementOf <span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>))
   *                         ^
   * </pre>
   **/
  def contain[T](atLeastOneElementOf: ResultOfAtLeastOneElementOfApplication): MatcherFactory1[Any, Aggregating] = {
    new MatcherFactory1[Any, Aggregating] {
      def matcher[T](implicit aggregating: Aggregating[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {

            val right = atLeastOneElementOf.right

            MatchResult(
              !aggregating.containsAtLeastOneOf(left, right),
              Resources.rawContainedAtLeastOneElementOf,
              Resources.rawDidNotContainAtLeastOneElementOf,
              Vector(left, right)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(atLeastOneElementOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(atLeastOneElementOf)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain noneOf (<span class="stLiteral">5</span>, <span class="stLiteral">6</span>, <span class="stLiteral">7</span>))
   *                         ^
   * </pre>
   **/
  def contain[T](noneOf: ResultOfNoneOfApplication)(implicit prettifier: Prettifier): MatcherFactory1[Any, Containing] = {
    new MatcherFactory1[Any, Containing] {
      def matcher[T](implicit containing: Containing[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = noneOf.right

            MatchResult(
              !containing.containsNoneOf(left, right),
              Resources.rawDidNotContainAtLeastOneOf,
              Resources.rawContainedAtLeastOneOf,
              Vector(left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
            )
          }
          override def toString: String = "not contain " + Prettifier.default(noneOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(noneOf)
    }
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain noElementsOf (<span class="stLiteral">5</span>, <span class="stLiteral">6</span>, <span class="stLiteral">7</span>))
   *                         ^
   * </pre>
   **/
  def contain[T](noElementsOf: ResultOfNoElementsOfApplication): MatcherFactory1[Any, Containing] = {
    new MatcherFactory1[Any, Containing] {
      def matcher[T](implicit containing: Containing[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {

            val right = noElementsOf.right

            MatchResult(
              !containing.containsNoneOf(left, right.distinct),
              Resources.rawDidNotContainAtLeastOneElementOf,
              Resources.rawContainedAtLeastOneElementOf,
              Vector(left, right)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(noElementsOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(noElementsOf)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain theSameElementsAs (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>) and not contain (<span class="stLiteral">3</span>))
   *                                 ^
   * </pre>
   **/
  def contain[T](theSameElementAs: ResultOfTheSameElementsAsApplication): MatcherFactory1[Any, Aggregating] = {
    new MatcherFactory1[Any, Aggregating] {
      def matcher[T](implicit aggregating: Aggregating[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = theSameElementAs.right

            MatchResult(
              !aggregating.containsTheSameElementsAs(left, right),
              Resources.rawContainedSameElements,
              Resources.rawDidNotContainSameElements,
              Vector(left, right)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(theSameElementAs)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(theSameElementAs)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain theSameElementsInOrderAs (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>) and not contain (<span class="stLiteral">3</span>))
   *                                 ^
   * </pre>
   **/
  def contain[T](theSameElementInOrderAs: ResultOfTheSameElementsInOrderAsApplication): MatcherFactory1[Any, Sequencing] = {
    new MatcherFactory1[Any, Sequencing] {
      def matcher[T](implicit sequencing: Sequencing[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = theSameElementInOrderAs.right

            MatchResult(
              !sequencing.containsTheSameElementsInOrderAs(left, right),
              Resources.rawContainedSameElementsInOrder,
              Resources.rawDidNotContainSameElementsInOrder,
              Vector(left, right)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(theSameElementInOrderAs)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(theSameElementInOrderAs)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain only (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>) and not contain (<span class="stLiteral">3</span>))
   *                                 ^
   * </pre>
   **/
  def contain[T](only: ResultOfOnlyApplication)(implicit prettifier: Prettifier): MatcherFactory1[Any, Aggregating] = {
    new MatcherFactory1[Any, Aggregating] {
      def matcher[T](implicit aggregating: Aggregating[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = only.right

            val withFriendlyReminder = right.size == 1 && (right(0).isInstanceOf[scala.collection.GenTraversable[_]] || right(0).isInstanceOf[Every[_]])

            MatchResult(
              !aggregating.containsOnly(left, right),
              if (withFriendlyReminder) Resources.rawContainedOnlyElementsWithFriendlyReminder else Resources.rawContainedOnlyElements,
              if (withFriendlyReminder) Resources.rawDidNotContainOnlyElementsWithFriendlyReminder else Resources.rawDidNotContainOnlyElements,
              Vector(left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
            )
          }
          override def toString: String = "not contain " + Prettifier.default(only)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(only)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain only (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>) and not contain (<span class="stLiteral">3</span>))
   *                                 ^
   * </pre>
   **/
  def contain[T](inOrderOnly: ResultOfInOrderOnlyApplication)(implicit prettifier: Prettifier): MatcherFactory1[Any, Sequencing] = {
    new MatcherFactory1[Any, Sequencing] {
      def matcher[T](implicit sequencing: Sequencing[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = inOrderOnly.right

            MatchResult(
              !sequencing.containsInOrderOnly(left, right),
              Resources.rawContainedInOrderOnlyElements,
              Resources.rawDidNotContainInOrderOnlyElements,
              Vector(left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
            )
          }
          override def toString: String = "not contain " + Prettifier.default(inOrderOnly)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(inOrderOnly)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain allOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>) and not contain (<span class="stLiteral">3</span>))
   *                                 ^
   * </pre>
   **/
  def contain[T](allOf: ResultOfAllOfApplication)(implicit prettifier: Prettifier): MatcherFactory1[Any, Aggregating] = {
    new MatcherFactory1[Any, Aggregating] {
      def matcher[T](implicit aggregating: Aggregating[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = allOf.right

            MatchResult(
              !aggregating.containsAllOf(left, right),
              Resources.rawContainedAllOfElements,
              Resources.rawDidNotContainAllOfElements,
              Vector(left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
            )
          }
          override def toString: String = "not contain " + Prettifier.default(allOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(allOf)
    }
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain allOf (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>) and not contain (<span class="stLiteral">3</span>))
   *                                 ^
   * </pre>
   **/
  def contain(allElementsOf: ResultOfAllElementsOfApplication): MatcherFactory1[Any, Aggregating] = {
    new MatcherFactory1[Any, Aggregating] {
      def matcher[T](implicit aggregating: Aggregating[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {

            val right = allElementsOf.right

            MatchResult(
              !aggregating.containsAllOf(left, right.distinct),
              Resources.rawContainedAllElementsOf,
              Resources.rawDidNotContainAllElementsOf,
              Vector(left, right)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(allElementsOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(allElementsOf)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain inOrder (<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>) and not contain (<span class="stLiteral">3</span>))
   *                                 ^
   * </pre>
   **/
  def contain[T](inOrder: ResultOfInOrderApplication)(implicit prettifier: Prettifier): MatcherFactory1[Any, Sequencing] = {
    new MatcherFactory1[Any, Sequencing] {
      def matcher[T](implicit sequencing: Sequencing[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = inOrder.right

            MatchResult(
              !sequencing.containsInOrder(left, right),
              Resources.rawContainedAllOfElementsInOrder,
              Resources.rawDidNotContainAllOfElementsInOrder,
              Vector(left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
            )
          }
          override def toString: String = "not contain " + Prettifier.default(inOrder)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(inOrder)
    }
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain inOrderElementsOf (<span class="stType">List</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>)) and not contain (<span class="stLiteral">3</span>))
   *                                 ^
   * </pre>
   **/
  def contain(inOrderElementsOf: ResultOfInOrderElementsOfApplication): MatcherFactory1[Any, Sequencing] = {
    new MatcherFactory1[Any, Sequencing] {
      def matcher[T](implicit sequencing: Sequencing[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {

            val right = inOrderElementsOf.right

            MatchResult(
              !sequencing.containsInOrder(left, right.distinct),
              Resources.rawContainedAllElementsOfInOrder,
              Resources.rawDidNotContainAllElementsOfInOrder,
              Vector(left, right)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(inOrderElementsOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(inOrderElementsOf)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain atMostOneOf (<span class="stLiteral">5</span>) and not contain (<span class="stLiteral">3</span>))
   *                         ^
   * </pre>
   **/
  def contain[T](atMostOneOf: ResultOfAtMostOneOfApplication)(implicit prettifier: Prettifier): MatcherFactory1[Any, Aggregating] = {
    new MatcherFactory1[Any, Aggregating] {
      def matcher[T](implicit aggregating: Aggregating[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
        
            val right = atMostOneOf.right

            MatchResult(
              !aggregating.containsAtMostOneOf(left, right),
              Resources.rawContainedAtMostOneOf,
              Resources.rawDidNotContainAtMostOneOf,
              Vector(left, UnquotedString(right.map(r => FailureMessages.decorateToStringValue(prettifier, r)).mkString(", ")))
            )
          }
          override def toString: String = "not contain " + Prettifier.default(atMostOneOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(atMostOneOf)
    }
  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stType">Array</span>(<span class="stLiteral">1</span>, <span class="stLiteral">2</span>) should (not contain atMostOneElementOf (<span class="stType">List</span>(<span class="stLiteral">5</span>)) and not contain (<span class="stLiteral">3</span>))
   *                         ^
   * </pre>
   **/
  def contain(atMostOneElementOf: ResultOfAtMostOneElementOfApplication): MatcherFactory1[Any, Aggregating] = {
    new MatcherFactory1[Any, Aggregating] {
      def matcher[T](implicit aggregating: Aggregating[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {

            val right = atMostOneElementOf.right

            MatchResult(
              !aggregating.containsAtMostOneOf(left, right.distinct),
              Resources.rawContainedAtMostOneElementOf,
              Resources.rawDidNotContainAtMostOneElementOf,
              Vector(left, right)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(atMostOneElementOf)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(atMostOneElementOf)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Map</span>(<span class="stQuotedString">"one"</span> -&gt; <span class="stLiteral">1</span>, <span class="stQuotedString">"two"</span> -&gt; <span class="stLiteral">2</span>) should (not contain key (<span class="stQuotedString">"three"</span>))
   *                                         ^
   * </pre>
   **/
  def contain(resultOfKeyWordApplication: ResultOfKeyWordApplication): MatcherFactory1[Any, KeyMapping] = {
    new MatcherFactory1[Any, KeyMapping] {
      def matcher[T](implicit keyMapping: KeyMapping[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
            val expectedKey = resultOfKeyWordApplication.expectedKey
            MatchResult(
              !keyMapping.containsKey(left, expectedKey),
              Resources.rawContainedKey,
              Resources.rawDidNotContainKey,
              Vector(left, expectedKey)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(resultOfKeyWordApplication)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(resultOfKeyWordApplication)
    }
  }

  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * <span class="stType">Map</span>(<span class="stQuotedString">"one"</span> -&gt; <span class="stLiteral">1</span>, <span class="stQuotedString">"two"</span> -&gt; <span class="stLiteral">2</span>) should (not contain value (<span class="stLiteral">3</span>))
   *                                         ^
   * </pre>
   **/
  def contain(resultOfValueWordApplication: ResultOfValueWordApplication): MatcherFactory1[Any, ValueMapping] = {
    new MatcherFactory1[Any, ValueMapping] {
      def matcher[T](implicit valueMapping: ValueMapping[T]): Matcher[T] = {
        new Matcher[T] {
          def apply(left: T): MatchResult = {
            val expectedValue = resultOfValueWordApplication.expectedValue
            MatchResult(
              !valueMapping.containsValue(left, expectedValue),
              Resources.rawContainedValue,
              Resources.rawDidNotContainValue,
              Vector(left, expectedValue)
            )
          }
          override def toString: String = "not contain " + Prettifier.default(resultOfValueWordApplication)
        }
      }
      override def toString: String = "not contain " + Prettifier.default(resultOfValueWordApplication)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * result should (not contain a (passedMarks) and contain a (validMarks)))
   *                    ^
   * </pre>
   **/
  private[scalatest] def contain[T](resultOfAWordApplication: ResultOfAWordToAMatcherApplication[T]): Matcher[GenTraversable[T]] = {
    new Matcher[GenTraversable[T]] {
      def apply(left: GenTraversable[T]): MatchResult = {
        val aMatcher = resultOfAWordApplication.aMatcher
        val matched = left.find(aMatcher(_).matches)
        MatchResult(
          !matched.isDefined, 
          Resources.rawContainedA,
          Resources.rawDidNotContainA,
          Vector(left, UnquotedString(aMatcher.nounName), UnquotedString(if (matched.isDefined) aMatcher(matched.get).negatedFailureMessage(Prettifier.default) else "-")),
          Vector(left, UnquotedString(aMatcher.nounName))
        )
      }
      override def toString: String = "not contain " + Prettifier.default(resultOfAWordApplication)
    }
  }
  
  /**
   * This method enables the following syntax: 
   *
   * <pre class="stHighlighted">
   * result should (not contain an (passedMarks) and contain an (validMarks)))
   *                    ^
   * </pre>
   **/
  private[scalatest] def contain[T](resultOfAnWordApplication: ResultOfAnWordToAnMatcherApplication[T]): Matcher[GenTraversable[T]] = {
    new Matcher[GenTraversable[T]] {
      def apply(left: GenTraversable[T]): MatchResult = {
        val anMatcher = resultOfAnWordApplication.anMatcher
        val matched = left.find(anMatcher(_).matches)
        MatchResult(
          !matched.isDefined, 
          Resources.rawContainedAn,
          Resources.rawDidNotContainAn,
          Vector(left, UnquotedString(anMatcher.nounName), UnquotedString(if (matched.isDefined) anMatcher(matched.get).negatedFailureMessage(Prettifier.default) else "-")),
          Vector(left, UnquotedString(anMatcher.nounName))
        )
      }
      override def toString: String = "not contain " + Prettifier.default(resultOfAnWordApplication)
    }
  }
  
  /**
   * Overrides toString to return "not"
   */
  override def toString: String = "not"
}

