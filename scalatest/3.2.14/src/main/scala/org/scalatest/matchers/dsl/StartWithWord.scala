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
import org.scalatest.matchers.MatchersHelper.startWithRegexWithGroups

/**
 * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
 * the matchers DSL.
 *
 * @author Bill Venners
 */
final class StartWithWord {

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stQuotedString">"1.7b"</span> should (startWith (<span class="stQuotedString">"1.7"</span>) and startWith (<span class="stQuotedString">"1.7b"</span>))
   *                          ^
   * </pre>
   */ 
  def apply(right: String): Matcher[String] =
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          left startsWith right,
          Resources.rawDidNotStartWith,
          Resources.rawStartedWith,
          Vector(left, right)
        )
      override def toString: String = "startWith (" + Prettifier.default(right) + ")"
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> decimal = <span class="stQuotedString">"""(-)?(\d+)(\.\d*)?"""</span>
   * <span class="stQuotedString">"1.7b"</span> should (startWith regex (decimal) and startWith regex (decimal))
   *                          ^
   * </pre>
   */
  //DOTTY-ONLY infix def regex[T <: String](right: T): Matcher[T] =
  // SKIP-DOTTY-START 
  def regex[T <: String](right: T): Matcher[T] = 
  // SKIP-DOTTY-END
    regex(right.r)
  
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * string should not { startWith regex (<span class="stQuotedString">"a(b*)c"</span> withGroup <span class="stQuotedString">"bb"</span>) } 
   *                               ^
   * </pre>
   */
  //DOTTY-ONLY infix def regex(regexWithGroups: RegexWithGroups) =
  // SKIP-DOTTY-START 	
  def regex(regexWithGroups: RegexWithGroups) = 
  // SKIP-DOTTY-END
    new Matcher[String] {
      def apply(left: String): MatchResult = 
        startWithRegexWithGroups(left, regexWithGroups.regex, regexWithGroups.groups)
      override def toString: String = "startWith regex " + Prettifier.default(regexWithGroups)
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> decimalRegex = <span class="stQuotedString">"""(-)?(\d+)(\.\d*)?"""</span>.r
   * <span class="stQuotedString">"1.7"</span> should (startWith regex (decimalRegex) and startWith regex (decimalRegex))
   *                         ^
   * </pre>
   */
  //DOTTY-ONLY infix def regex(rightRegex: Regex): Matcher[String] =
  // SKIP-DOTTY-START 
  def regex(rightRegex: Regex): Matcher[String] =
  // SKIP-DOTTY-END
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          rightRegex.pattern.matcher(left).lookingAt,
          Resources.rawDidNotStartWithRegex,
          Resources.rawStartedWithRegex,
          Vector(left, UnquotedString(rightRegex.toString))
        )
      override def toString: String = "startWith regex " + Prettifier.default(rightRegex)
    }
  
  /**
   * Overrides toString to return "startWith"
   */
  override def toString: String = "startWith"
}
