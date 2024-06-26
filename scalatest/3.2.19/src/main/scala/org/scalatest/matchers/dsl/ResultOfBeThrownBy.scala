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

/**
 * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
 * the matchers DSL.
 *
 * @author Bill Venners
 * @author Chee Seng
 */
final class ResultOfBeThrownBy(codeList: IndexedSeq[() => Unit]) {

  final class ResultOfAndBeWord {

    //DOTTY-ONLY infix def thrownBy(code: => Unit) =
    // SKIP-DOTTY-START
    def thrownBy(code: => Unit) = 
    // SKIP-DOTTY-END
      new ResultOfBeThrownBy(codeList :+ (() => code))

  }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlighted">
   * a[<span class="stType">Exception</span>] should (be thrownBy { <span class="stQuotedString">"hi"</span>.charAt(-<span class="stLiteral">1</span>) } and thrownBy { <span class="stQuotedString">"ha"</span>.charAt(<span class="stLiteral">3</span>) })
   *                         ^
   * </pre>
   */
  //DOTTY-ONLY infix def and(beWord: BeWord) =
  // SKIP-DOTTY-START 
  def and(beWord: BeWord) = 
  // SKIP-DOTTY-END
    new ResultOfAndBeWord

  private[scalatest] lazy val throwables: IndexedSeq[Option[Throwable]] =
    codeList.map { code =>
      try {
        code()
        None
      }
      catch {
        case t: Throwable => Some(t)
      }
    }
}
