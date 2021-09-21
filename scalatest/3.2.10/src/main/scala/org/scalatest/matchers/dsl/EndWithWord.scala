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

import org.scalatest.matchers._
import org.scalactic._
import scala.util.matching.Regex
import org.scalatest.FailureMessages
import org.scalatest.Resources
import org.scalatest.UnquotedString
import org.scalatest.matchers.MatchersHelper.endWithRegexWithGroups

/**
 * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
 * the matchers DSL.
 *
 * @author Bill Venners
 */
final class EndWithWord {

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stQuotedString">"1.7b"</span> should (endWith (<span class="stQuotedString">"1.7b"</span>) and endWith (<span class="stQuotedString">"7b"</span>))
   *                        ^
   * </pre>
   */
  def apply(right: String): Matcher[String] =
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          left endsWith right,
          Resources.rawDidNotEndWith,
          Resources.rawEndedWith,
          Vector(left, right)
        )
      override def toString: String = "endWith (" + Prettifier.default(right) + ")"
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> decimal = <span class="stQuotedString">"""(-)?(\d+)(\.\d*)?"""</span>
   * <span class="stQuotedString">"b1.7"</span> should (endWith regex (decimal) and endWith regex (decimal))
   *                        ^
   * </pre>
   */
  def regex[T <: String](right: T): Matcher[T] = regex(right.r)
  
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * string should not { endWith regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>) } 
   *                             ^
   * </pre>
   */	
  def regex(regexWithGroups: RegexWithGroups) = 
    new Matcher[String] {
      def apply(left: String): MatchResult = 
        endWithRegexWithGroups(left, regexWithGroups.regex, regexWithGroups.groups)
      override def toString: String = "endWith regex " + Prettifier.default(regexWithGroups)
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> decimalRegex = <span class="stQuotedString">"""(-)?(\d+)(\.\d*)?"""</span>.r
   * <span class="stQuotedString">"b1.7"</span> should (endWith regex (decimalRegex) and endWith regex (decimalRegex))
   *                        ^
   * </pre>
   */
  def regex(rightRegex: Regex): Matcher[String] =
    new Matcher[String] {
      def apply(left: String): MatchResult = {
        val allMatches = rightRegex.findAllIn(left)
        MatchResult(
          allMatches.hasNext && (allMatches.end == left.length),
          Resources.rawDidNotEndWithRegex,
          Resources.rawEndedWithRegex,
          Vector(left, UnquotedString(rightRegex.toString))
        )
      }
      override def toString: String = "endWith regex \"" + Prettifier.default(rightRegex) + "\""
    }
  
  /**
   * Overrides toString to return "endWith"
   */
  override def toString: String = "endWith"
}
