
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
 * A matcher factory that can produce a matcher given nine typeclass instances.
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
abstract class MatcherFactory9[-SC, TC1[_], TC2[_], TC3[_], TC4[_], TC5[_], TC6[_], TC7[_], TC8[_], TC9[_]] { thisMatcherFactory =>

  /**
   * Factory method that will produce a <code>Matcher[T]</code>, where <code>T</code> is a subtype of (or the same type
   * as) <code>SC</code>, given a typeclass instance for each <code>TC<em>n</em></code>
   * implicit parameter (for example, a <code>TC1[T]</code>, <code>TC2[T]</code>, <em>etc.</em>).
   */
  def matcher[T <: SC : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7 : TC8 : TC9]: Matcher[T]

  /**
   * Ands this matcher factory with the passed matcher.
   */
  def and[U <: SC](rightMatcher: Matcher[U]): MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] =
    new MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7 : TC8 : TC9]: Matcher[V] = {
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
  def or[U <: SC](rightMatcher: Matcher[U]): MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] =
    new MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7 : TC8 : TC9]: Matcher[V] = {
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
  def and[U <: SC](rightMatcherFactory: MatcherFactory1[U, TC9]): MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] =
    new MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7 : TC8 : TC9]: Matcher[V] = {
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
  def or[U <: SC](rightMatcherFactory: MatcherFactory1[U, TC9]): MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] =
    new MatcherFactory9[U, TC1, TC2, TC3, TC4, TC5, TC6, TC7, TC8, TC9] {
      def matcher[V <: U : TC1 : TC2 : TC3 : TC4 : TC5 : TC6 : TC7 : TC8 : TC9]: Matcher[V] = {
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
                }
