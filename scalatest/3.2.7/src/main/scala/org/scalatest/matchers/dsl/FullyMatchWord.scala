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
import org.scalatest.Resources
import org.scalatest.UnquotedString
import org.scalatest.matchers.MatchersHelper.fullyMatchRegexWithGroups

/**
 * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
 * the matchers DSL.
 *
 * @author Bill Venners
 */
final class FullyMatchWord {

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> decimal = <span class="stQuotedString">"""(-)?(\d+)(\.\d*)?"""</span>
   * <span class="stQuotedString">"1.7"</span> should (fullyMatch regex (decimal) and fullyMatch regex (decimal))
   *                          ^
   * </pre>
   */
  def regex(rightRegexString: String): Matcher[String] =
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          java.util.regex.Pattern.matches(rightRegexString, left),
          Resources.rawDidNotFullyMatchRegex,
          Resources.rawFullyMatchedRegex,
          Vector(left, UnquotedString(rightRegexString))
        )
      override def toString: String = "fullyMatch regex " + Prettifier.default(rightRegexString)
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * string should not { fullyMatch regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>) } 
   *                          ^
   * </pre>
   */	
  def regex(regexWithGroups: RegexWithGroups) = 
    new Matcher[String] {
      def apply(left: String): MatchResult = 
        fullyMatchRegexWithGroups(left, regexWithGroups.regex, regexWithGroups.groups)
      override def toString: String = "fullyMatch regex " + Prettifier.default(regexWithGroups)
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> decimalRegex = <span class="stQuotedString">"""(-)?(\d+)(\.\d*)?"""</span>.r
   * <span class="stQuotedString">"1.7"</span> should (fullyMatch regex (decimalRegex) and fullyMatch regex (decimalRegex))
   *                          ^
   * </pre>
   */
  def regex(rightRegex: Regex): Matcher[String] =
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          rightRegex.pattern.matcher(left).matches,
          Resources.rawDidNotFullyMatchRegex,
          Resources.rawFullyMatchedRegex,
          Vector(left, UnquotedString(rightRegex.toString))
        )
      override def toString: String = "fullyMatch regex \"" + Prettifier.default(rightRegex) + "\""
    }
  
  /**
   * Overrides toString to return "fullyMatch"
   */
  override def toString: String = "fullyMatch"
}
