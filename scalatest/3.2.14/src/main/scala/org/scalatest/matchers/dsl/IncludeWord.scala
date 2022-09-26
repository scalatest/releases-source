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
import org.scalatest.UnquotedString
import org.scalatest.Resources
import org.scalatest.matchers.MatchersHelper.includeRegexWithGroups

/**
 * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
 * the matchers DSL.
 *
 * @author Bill Venners
 */
final class IncludeWord {

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stQuotedString">"1.7"</span> should (include (<span class="stQuotedString">"1.7"</span>) and include (<span class="stQuotedString">"1.8"</span>))
   *                       ^
   * </pre>
   */ 
  def apply(expectedSubstring: String): Matcher[String] =
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          left.indexOf(expectedSubstring) >= 0, 
          Resources.rawDidNotIncludeSubstring,
          Resources.rawIncludedSubstring,
          Vector(left, expectedSubstring)
        )
      override def toString: String = "include (" + Prettifier.default(expectedSubstring) + ")"
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> decimal = <span class="stQuotedString">"""(-)?(\d+)(\.\d*)?"""</span>
   * <span class="stQuotedString">"a1.7b"</span> should (include regex (decimal) and include regex (decimal))
   *                         ^
   * </pre>
   */
  //DOTTY-ONLY infix def regex[T <: String](right: T): Matcher[T] = regex(right.r)
  // SKIP-DOTTY-START 
  def regex[T <: String](right: T): Matcher[T] = regex(right.r)
  // SKIP-DOTTY-END
  
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * string should not { include regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>) } 
   *                             ^
   * </pre>
   */
  //DOTTY-ONLY infix def regex(regexWithGroups: RegexWithGroups) = 
  // SKIP-DOTTY-START 	
  def regex(regexWithGroups: RegexWithGroups) = 
  // SKIP-DOTTY-END
    new Matcher[String] {
      def apply(left: String): MatchResult = 
        includeRegexWithGroups(left, regexWithGroups.regex, regexWithGroups.groups)
      override def toString: String = "include regex " + Prettifier.default(regexWithGroups)
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> decimalRegex = <span class="stQuotedString">"""(-)?(\d+)(\.\d*)?"""</span>.r
   * <span class="stQuotedString">"a1.7"</span> should (include regex (decimalRegex) and include regex (decimalRegex))
   *                        ^
   * </pre>
   */
  //DOTTY-ONLY infix def regex(expectedRegex: Regex): Matcher[String] =
  // SKIP-DOTTY-START 
  def regex(expectedRegex: Regex): Matcher[String] =
  // SKIP-DOTTY-END
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          expectedRegex.findFirstIn(left).isDefined,
          Resources.rawDidNotIncludeRegex,
          Resources.rawIncludedRegex,
          Vector(left, UnquotedString(expectedRegex.toString))
        )
      override def toString: String = "include regex \"" + Prettifier.default(expectedRegex) + "\""
    }
  
  /**
   * Overrides toString to return "include"
   */
  override def toString: String = "include"
}
