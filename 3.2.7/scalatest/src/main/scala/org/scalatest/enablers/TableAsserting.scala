/*
 * Copyright 2001-2015 Artima, Inc.
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
package org.scalatest.enablers

import org.scalatest.Assertion
import org.scalatest.Succeeded
import org.scalatest.FailureMessages
import org.scalatest.UnquotedString
import org.scalatest.Resources
import org.scalatest.exceptions.StackDepthException
import org.scalatest.exceptions.TableDrivenPropertyCheckFailedException
import org.scalatest.exceptions.DiscardedEvaluationException
import org.scalatest.exceptions.StackDepth
import org.scalactic._
import scala.concurrent.Future

/**
 * Supertrait for <code>TableAsserting</code> typeclasses, which are used to implement and determine the result
 * type of [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code>, <code>forEvery</code> and <code>exists</code> method.
 *
 * <p>
 * Currently, an [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]] expression will have result type <code>Assertion</code>, if the function passed has result type <code>Assertion</code>,
 * else it will have result type <code>Unit</code>.
 * </p>
 */
trait TableAsserting[ASSERTION] {
  /**
   * Return type of <code>forAll</code>, <code>forEvery</code> and <code>exists</code> method.
   */
  type Result
  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A](heading: (String), rows: (A)*)(fun: (A) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B](heading: (String, String), rows: (A, B)*)(fun: (A, B) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C](heading: (String, String, String), rows: (A, B, C)*)(fun: (A, B, C) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*)(fun: (A, B, C, D) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*)(fun: (A, B, C, D, E) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*)(fun: (A, B, C, D, E, F) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*)(fun: (A, B, C, D, E, F, G) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*)(fun: (A, B, C, D, E, F, G, H) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*)(fun: (A, B, C, D, E, F, G, H, I) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*)(fun: (A, B, C, D, E, F, G, H, I, J) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*)(fun: (A, B, C, D, E, F, G, H, I, J, K) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
   */
  def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        
  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A](heading: (String), rows: (A)*)(fun: (A) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B](heading: (String, String), rows: (A, B)*)(fun: (A, B) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C](heading: (String, String, String), rows: (A, B, C)*)(fun: (A, B, C) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*)(fun: (A, B, C, D) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*)(fun: (A, B, C, D, E) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*)(fun: (A, B, C, D, E, F) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*)(fun: (A, B, C, D, E, F, G) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*)(fun: (A, B, C, D, E, F, G, H) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*)(fun: (A, B, C, D, E, F, G, H, I) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*)(fun: (A, B, C, D, E, F, G, H, I, J) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*)(fun: (A, B, C, D, E, F, G, H, I, J, K) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result


  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
   */
  def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A](heading: (String), rows: (A)*)(fun: (A) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B](heading: (String, String), rows: (A, B)*)(fun: (A, B) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C](heading: (String, String, String), rows: (A, B, C)*)(fun: (A, B, C) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*)(fun: (A, B, C, D) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*)(fun: (A, B, C, D, E) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*)(fun: (A, B, C, D, E, F) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*)(fun: (A, B, C, D, E, F, G) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*)(fun: (A, B, C, D, E, F, G, H) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*)(fun: (A, B, C, D, E, F, G, H, I) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*)(fun: (A, B, C, D, E, F, G, H, I, J) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*)(fun: (A, B, C, D, E, F, G, H, I, J, K) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        

  /**
   * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
   */
  def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
        
}

/**
  * Class holding lowest priority <code>TableAsserting</code> implicit, which enables [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]] expressions that have result type <code>Unit</code>.
  */
abstract class UnitTableAsserting {

  /**
   * Abstract subclass of <code>TableAsserting</code> that provides the bulk of the implementations of <code>TableAsserting</code>'s
   * <code>forAll</code>, <code>forEvery</code> and <code>exists</code>.
   */
  abstract class TableAssertingImpl[ASSERTION] extends TableAsserting[ASSERTION] {

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A](heading: (String), rows: (A)*)(fun: (A) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a), idx) <- rows.zipWithIndex) {
        try {
          fun(a)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a),
              List(aName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B](heading: (String, String), rows: (A, B)*)(fun: (A, B) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b),
              List(aName, bName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C](heading: (String, String, String), rows: (A, B, C)*)(fun: (A, B, C) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c),
              List(aName, bName, cName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*)(fun: (A, B, C, D) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d),
              List(aName, bName, cName, dName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*)(fun: (A, B, C, D, E) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e),
              List(aName, bName, cName, dName, eName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*)(fun: (A, B, C, D, E, F) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f),
              List(aName, bName, cName, dName, eName, fName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*)(fun: (A, B, C, D, E, F, G) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g),
              List(aName, bName, cName, dName, eName, fName, gName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*)(fun: (A, B, C, D, E, F, G, H) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h),
              List(aName, bName, cName, dName, eName, fName, gName, hName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*)(fun: (A, B, C, D, E, F, G, H, I) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*)(fun: (A, B, C, D, E, F, G, H, I, J) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*)(fun: (A, B, C, D, E, F, G, H, I, J, K) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m, n), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "," + "\n" +
                              "    " + nName + " = " + n + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m, n),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m, n, o), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "," + "\n" +
                              "    " + nName + " = " + n + "," + "\n" +
                              "    " + oName + " = " + o + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "," + "\n" +
                              "    " + nName + " = " + n + "," + "\n" +
                              "    " + oName + " = " + o + "," + "\n" +
                              "    " + pName + " = " + p + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "," + "\n" +
                              "    " + nName + " = " + n + "," + "\n" +
                              "    " + oName + " = " + o + "," + "\n" +
                              "    " + pName + " = " + p + "," + "\n" +
                              "    " + qName + " = " + q + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "," + "\n" +
                              "    " + nName + " = " + n + "," + "\n" +
                              "    " + oName + " = " + o + "," + "\n" +
                              "    " + pName + " = " + p + "," + "\n" +
                              "    " + qName + " = " + q + "," + "\n" +
                              "    " + rName + " = " + r + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "," + "\n" +
                              "    " + nName + " = " + n + "," + "\n" +
                              "    " + oName + " = " + o + "," + "\n" +
                              "    " + pName + " = " + p + "," + "\n" +
                              "    " + qName + " = " + q + "," + "\n" +
                              "    " + rName + " = " + r + "," + "\n" +
                              "    " + sName + " = " + s + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "," + "\n" +
                              "    " + nName + " = " + n + "," + "\n" +
                              "    " + oName + " = " + o + "," + "\n" +
                              "    " + pName + " = " + p + "," + "\n" +
                              "    " + qName + " = " + q + "," + "\n" +
                              "    " + rName + " = " + r + "," + "\n" +
                              "    " + sName + " = " + s + "," + "\n" +
                              "    " + tName + " = " + t + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "," + "\n" +
                              "    " + nName + " = " + n + "," + "\n" +
                              "    " + oName + " = " + o + "," + "\n" +
                              "    " + pName + " = " + p + "," + "\n" +
                              "    " + qName + " = " + q + "," + "\n" +
                              "    " + rName + " = " + r + "," + "\n" +
                              "    " + sName + " = " + s + "," + "\n" +
                              "    " + tName + " = " + t + "," + "\n" +
                              "    " + uName + " = " + u + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      for (((a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v), idx) <- rows.zipWithIndex) {
        try {
          fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v)
        }
        catch {
          case _: DiscardedEvaluationException => // discard this evaluation and move on to the next
          case ex: Throwable =>
            val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName, vName) = heading

            // SKIP-SCALATESTJS,NATIVE-START
            val stackDepth = 2
            // SKIP-SCALATESTJS,NATIVE-END
            //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

            indicateFailure(
              (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                (
                  ex match {
                    case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                      "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                    case _ => ""
                  }
                ) +
                "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                              "    " + aName + " = " + a + "," + "\n" +
                              "    " + bName + " = " + b + "," + "\n" +
                              "    " + cName + " = " + c + "," + "\n" +
                              "    " + dName + " = " + d + "," + "\n" +
                              "    " + eName + " = " + e + "," + "\n" +
                              "    " + fName + " = " + f + "," + "\n" +
                              "    " + gName + " = " + g + "," + "\n" +
                              "    " + hName + " = " + h + "," + "\n" +
                              "    " + iName + " = " + i + "," + "\n" +
                              "    " + jName + " = " + j + "," + "\n" +
                              "    " + kName + " = " + k + "," + "\n" +
                              "    " + lName + " = " + l + "," + "\n" +
                              "    " + mName + " = " + m + "," + "\n" +
                              "    " + nName + " = " + n + "," + "\n" +
                              "    " + oName + " = " + o + "," + "\n" +
                              "    " + pName + " = " + p + "," + "\n" +
                              "    " + qName + " = " + q + "," + "\n" +
                              "    " + rName + " = " + r + "," + "\n" +
                              "    " + sName + " = " + s + "," + "\n" +
                              "    " + tName + " = " + t + "," + "\n" +
                              "    " + uName + " = " + u + "," + "\n" +
                              "    " + vName + " = " + v + "\n" +

                "  )",
              FailureMessages.undecoratedPropertyCheckFailureMessage,
              List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v),
              List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName, vName),
              Some(ex),
              None, // Payload
              prettifier,
              pos,
              idx
            )
          }
      }
      indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A](heading: (String), rows: (A)*)(fun: (A) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple1[A]](List(heading), rows.map(Tuple1.apply[A]), Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: Tuple1[A]) => fun(row._1))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B](heading: (String, String), rows: (A, B)*)(fun: (A, B) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple2[A, B]](List(heading._1, heading._2), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B)) => fun(row._1, row._2))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C](heading: (String, String, String), rows: (A, B, C)*)(fun: (A, B, C) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple3[A, B, C]](List(heading._1, heading._2, heading._3), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C)) => fun(row._1, row._2, row._3))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*)(fun: (A, B, C, D) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple4[A, B, C, D]](List(heading._1, heading._2, heading._3, heading._4), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D)) => fun(row._1, row._2, row._3, row._4))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*)(fun: (A, B, C, D, E) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple5[A, B, C, D, E]](List(heading._1, heading._2, heading._3, heading._4, heading._5), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E)) => fun(row._1, row._2, row._3, row._4, row._5))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*)(fun: (A, B, C, D, E, F) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple6[A, B, C, D, E, F]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F)) => fun(row._1, row._2, row._3, row._4, row._5, row._6))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*)(fun: (A, B, C, D, E, F, G) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple7[A, B, C, D, E, F, G]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*)(fun: (A, B, C, D, E, F, G, H) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple8[A, B, C, D, E, F, G, H]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*)(fun: (A, B, C, D, E, F, G, H, I) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple9[A, B, C, D, E, F, G, H, I]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*)(fun: (A, B, C, D, E, F, G, H, I, J) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple10[A, B, C, D, E, F, G, H, I, J]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*)(fun: (A, B, C, D, E, F, G, H, I, J, K) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple11[A, B, C, D, E, F, G, H, I, J, K]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple12[A, B, C, D, E, F, G, H, I, J, K, L]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple13[A, B, C, D, E, F, G, H, I, J, K, L, M]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20, heading._21), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20, row._21))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20, heading._21, heading._22), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20, row._21, row._22))
    }
                                     

    private[scalatest] case class ForResult[E](passedCount: Int = 0,
                          discardedCount: Int = 0,
                          messageAcc: IndexedSeq[String] = IndexedSeq.empty,
                          passedElements: IndexedSeq[(Int, E)] = IndexedSeq.empty,
                          failedElements: IndexedSeq[(Int, E, Throwable)] = IndexedSeq.empty)

    private[scalatest] def runAndCollectResult[E <: Product](namesOfArgs: List[String], rows: Seq[E], sourceFileName: String, methodName: String, stackDepthAdjustment: Int, prettifier: Prettifier, pos: source.Position)(fun: E => ASSERTION): ForResult[E] = {
      import org.scalatest.InspectorsHelper.{shouldPropagate, indentErrorMessages}

      @scala.annotation.tailrec
      def innerRunAndCollectResult(itr: Iterator[E], result: ForResult[E], index: Int)(fun: E => ASSERTION): ForResult[E] = {
        if (itr.hasNext) {
          val head = itr.next
          val newResult =
            try {
              fun(head)
              result.copy(passedCount = result.passedCount + 1, passedElements = result.passedElements :+ (index, head))
            }
            catch {
              case _: org.scalatest.exceptions.DiscardedEvaluationException => result.copy(discardedCount = result.discardedCount + 1) // discard this evaluation and move on to the next
              case ex if !shouldPropagate(ex) =>
                result.copy(failedElements =
                  result.failedElements :+ ((index,
                    head,
                    new org.scalatest.exceptions.TableDrivenPropertyCheckFailedException(
                      ((sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                        (sde.failedCodeFileNameAndLineNumberString match {
                          case Some(s) => " (" + s + ")";
                          case None => ""
                        }) + "\n" +
                        "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                        (
                          ex match {
                            case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                              "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                            case _ => ""
                          }
                          ) +
                        "  " + FailureMessages.occurredAtRow(prettifier, index) + "\n" +
                        indentErrorMessages(namesOfArgs.zip(head.productIterator.toSeq).map { case (name, value) =>
                          name + " = " + value
                        }.toIndexedSeq).mkString("\n") +
                        "  )"),
                      Some(ex),
                      pos,
                      None,
                      FailureMessages.undecoratedPropertyCheckFailureMessage,
                      head.productIterator.toList,
                      namesOfArgs,
                      index
                    ))
                  )
                )
            }

          innerRunAndCollectResult(itr, newResult, index + 1)(fun)
        }
        else
          result
      }
      innerRunAndCollectResult(rows.toIterator, ForResult(), 0)(fun)
    }

    private def doForEvery[E <: Product](namesOfArgs: List[String], rows: Seq[E], messageFun: Any => String, sourceFileName: String, methodName: String, stackDepthAdjustment: Int, prettifier: Prettifier, pos: source.Position)(fun: E => ASSERTION)(implicit asserting: TableAsserting[ASSERTION]): Result = {
      import org.scalatest.InspectorsHelper.indentErrorMessages
      val result = runAndCollectResult(namesOfArgs, rows, sourceFileName, methodName, stackDepthAdjustment + 2, prettifier, pos)(fun)
      val messageList = result.failedElements.map(_._3)
      if (messageList.size > 0)
        indicateFailure(
          messageFun(UnquotedString(indentErrorMessages(messageList.map(_.toString)).mkString(", \n"))),
          messageList.headOption,
          prettifier,
          pos
        )
      else indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A](heading: (String), rows: (A)*)(fun: (A) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple1[A]](List(heading), rows.map(Tuple1.apply[A]), Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: Tuple1[A]) => fun(row._1))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B](heading: (String, String), rows: (A, B)*)(fun: (A, B) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple2[A, B]](List(heading._1, heading._2), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B)) => fun(row._1, row._2))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C](heading: (String, String, String), rows: (A, B, C)*)(fun: (A, B, C) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple3[A, B, C]](List(heading._1, heading._2, heading._3), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C)) => fun(row._1, row._2, row._3))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*)(fun: (A, B, C, D) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple4[A, B, C, D]](List(heading._1, heading._2, heading._3, heading._4), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D)) => fun(row._1, row._2, row._3, row._4))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*)(fun: (A, B, C, D, E) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple5[A, B, C, D, E]](List(heading._1, heading._2, heading._3, heading._4, heading._5), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E)) => fun(row._1, row._2, row._3, row._4, row._5))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*)(fun: (A, B, C, D, E, F) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple6[A, B, C, D, E, F]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F)) => fun(row._1, row._2, row._3, row._4, row._5, row._6))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*)(fun: (A, B, C, D, E, F, G) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple7[A, B, C, D, E, F, G]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*)(fun: (A, B, C, D, E, F, G, H) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple8[A, B, C, D, E, F, G, H]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*)(fun: (A, B, C, D, E, F, G, H, I) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple9[A, B, C, D, E, F, G, H, I]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*)(fun: (A, B, C, D, E, F, G, H, I, J) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple10[A, B, C, D, E, F, G, H, I, J]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*)(fun: (A, B, C, D, E, F, G, H, I, J, K) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple11[A, B, C, D, E, F, G, H, I, J, K]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple12[A, B, C, D, E, F, G, H, I, J, K, L]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple13[A, B, C, D, E, F, G, H, I, J, K, L, M]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20, heading._21), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20, row._21))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => ASSERTION)(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20, heading._21, heading._22), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20, row._21, row._22))
    }
                                   

    private def doExists[E <: Product](namesOfArgs: List[String], rows: Seq[E], messageFun: Any => String, sourceFileName: String, methodName: String, stackDepthAdjustment: Int, prettifier: Prettifier, pos: source.Position)(fun: E => ASSERTION)(implicit asserting: TableAsserting[ASSERTION]): Result = {
      import org.scalatest.InspectorsHelper.indentErrorMessages
      val result = runAndCollectResult(namesOfArgs, rows, sourceFileName, methodName, stackDepthAdjustment + 2, prettifier, pos)(fun)
      if (result.passedCount == 0) {
        val messageList = result.failedElements.map(_._3)
        indicateFailure(
          messageFun(UnquotedString(indentErrorMessages(messageList.map(_.toString)).mkString(", \n"))),
          messageList.headOption,
          prettifier,
          pos
        )
      }
      else indicateSuccess(FailureMessages.propertyCheckSucceeded)
    }

    private[scalatest] def indicateSuccess(message: => String): Result

    private[scalatest] def indicateFailure(messageFun: StackDepthException => String, undecoratedMessage: => String, args: List[Any], namesOfArgs: List[String], optionalCause: Option[Throwable], payload: Option[Any], prettifier: Prettifier, pos: source.Position, idx: Int): Result

    private[scalatest] def indicateFailure(message: => String, optionalCause: Option[Throwable], prettifier: Prettifier, pos: source.Position): Result
  }

  abstract class FutureTableAssertingImpl[ASSERTION] extends TableAsserting[Future[ASSERTION]] {

    implicit val executionContext: scala.concurrent.ExecutionContext

    type Result = Future[Assertion]

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A](heading: (String), rows: (A)*)(fun: (A) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A)]): Future[Assertion] =
        rows match {
          case (a) :: tail =>
            try {
              val future = fun(a)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a),
                    List(aName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a),
                  List(aName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B](heading: (String, String), rows: (A, B)*)(fun: (A, B) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B)]): Future[Assertion] =
        rows match {
          case (a, b) :: tail =>
            try {
              val future = fun(a, b)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b),
                    List(aName, bName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b),
                  List(aName, bName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C](heading: (String, String, String), rows: (A, B, C)*)(fun: (A, B, C) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C)]): Future[Assertion] =
        rows match {
          case (a, b, c) :: tail =>
            try {
              val future = fun(a, b, c)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c),
                    List(aName, bName, cName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c),
                  List(aName, bName, cName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*)(fun: (A, B, C, D) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D)]): Future[Assertion] =
        rows match {
          case (a, b, c, d) :: tail =>
            try {
              val future = fun(a, b, c, d)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d),
                    List(aName, bName, cName, dName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d),
                  List(aName, bName, cName, dName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*)(fun: (A, B, C, D, E) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e) :: tail =>
            try {
              val future = fun(a, b, c, d, e)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e),
                    List(aName, bName, cName, dName, eName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e),
                  List(aName, bName, cName, dName, eName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*)(fun: (A, B, C, D, E, F) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f),
                    List(aName, bName, cName, dName, eName, fName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f),
                  List(aName, bName, cName, dName, eName, fName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*)(fun: (A, B, C, D, E, F, G) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g),
                    List(aName, bName, cName, dName, eName, fName, gName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g),
                  List(aName, bName, cName, dName, eName, fName, gName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*)(fun: (A, B, C, D, E, F, G, H) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h),
                    List(aName, bName, cName, dName, eName, fName, gName, hName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h),
                  List(aName, bName, cName, dName, eName, fName, gName, hName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*)(fun: (A, B, C, D, E, F, G, H, I) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*)(fun: (A, B, C, D, E, F, G, H, I, J) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*)(fun: (A, B, C, D, E, F, G, H, I, J, K) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m, n) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "," + "\n" +
                                    "    " + nName + " = " + n + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m, n),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "," + "\n" +
                                  "    " + nName + " = " + n + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m, n),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "," + "\n" +
                                    "    " + nName + " = " + n + "," + "\n" +
                                    "    " + oName + " = " + o + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "," + "\n" +
                                  "    " + nName + " = " + n + "," + "\n" +
                                  "    " + oName + " = " + o + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "," + "\n" +
                                    "    " + nName + " = " + n + "," + "\n" +
                                    "    " + oName + " = " + o + "," + "\n" +
                                    "    " + pName + " = " + p + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "," + "\n" +
                                  "    " + nName + " = " + n + "," + "\n" +
                                  "    " + oName + " = " + o + "," + "\n" +
                                  "    " + pName + " = " + p + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "," + "\n" +
                                    "    " + nName + " = " + n + "," + "\n" +
                                    "    " + oName + " = " + o + "," + "\n" +
                                    "    " + pName + " = " + p + "," + "\n" +
                                    "    " + qName + " = " + q + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "," + "\n" +
                                  "    " + nName + " = " + n + "," + "\n" +
                                  "    " + oName + " = " + o + "," + "\n" +
                                  "    " + pName + " = " + p + "," + "\n" +
                                  "    " + qName + " = " + q + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "," + "\n" +
                                    "    " + nName + " = " + n + "," + "\n" +
                                    "    " + oName + " = " + o + "," + "\n" +
                                    "    " + pName + " = " + p + "," + "\n" +
                                    "    " + qName + " = " + q + "," + "\n" +
                                    "    " + rName + " = " + r + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "," + "\n" +
                                  "    " + nName + " = " + n + "," + "\n" +
                                  "    " + oName + " = " + o + "," + "\n" +
                                  "    " + pName + " = " + p + "," + "\n" +
                                  "    " + qName + " = " + q + "," + "\n" +
                                  "    " + rName + " = " + r + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "," + "\n" +
                                    "    " + nName + " = " + n + "," + "\n" +
                                    "    " + oName + " = " + o + "," + "\n" +
                                    "    " + pName + " = " + p + "," + "\n" +
                                    "    " + qName + " = " + q + "," + "\n" +
                                    "    " + rName + " = " + r + "," + "\n" +
                                    "    " + sName + " = " + s + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "," + "\n" +
                                  "    " + nName + " = " + n + "," + "\n" +
                                  "    " + oName + " = " + o + "," + "\n" +
                                  "    " + pName + " = " + p + "," + "\n" +
                                  "    " + qName + " = " + q + "," + "\n" +
                                  "    " + rName + " = " + r + "," + "\n" +
                                  "    " + sName + " = " + s + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "," + "\n" +
                                    "    " + nName + " = " + n + "," + "\n" +
                                    "    " + oName + " = " + o + "," + "\n" +
                                    "    " + pName + " = " + p + "," + "\n" +
                                    "    " + qName + " = " + q + "," + "\n" +
                                    "    " + rName + " = " + r + "," + "\n" +
                                    "    " + sName + " = " + s + "," + "\n" +
                                    "    " + tName + " = " + t + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "," + "\n" +
                                  "    " + nName + " = " + n + "," + "\n" +
                                  "    " + oName + " = " + o + "," + "\n" +
                                  "    " + pName + " = " + p + "," + "\n" +
                                  "    " + qName + " = " + q + "," + "\n" +
                                  "    " + rName + " = " + r + "," + "\n" +
                                  "    " + sName + " = " + s + "," + "\n" +
                                  "    " + tName + " = " + t + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "," + "\n" +
                                    "    " + nName + " = " + n + "," + "\n" +
                                    "    " + oName + " = " + o + "," + "\n" +
                                    "    " + pName + " = " + p + "," + "\n" +
                                    "    " + qName + " = " + q + "," + "\n" +
                                    "    " + rName + " = " + r + "," + "\n" +
                                    "    " + sName + " = " + s + "," + "\n" +
                                    "    " + tName + " = " + t + "," + "\n" +
                                    "    " + uName + " = " + u + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "," + "\n" +
                                  "    " + nName + " = " + n + "," + "\n" +
                                  "    " + oName + " = " + o + "," + "\n" +
                                  "    " + pName + " = " + p + "," + "\n" +
                                  "    " + qName + " = " + q + "," + "\n" +
                                  "    " + rName + " = " + r + "," + "\n" +
                                  "    " + sName + " = " + s + "," + "\n" +
                                  "    " + tName + " = " + t + "," + "\n" +
                                  "    " + uName + " = " + u + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forAll</code> syntax.
     */
    def forAll[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      def loop(idx: Int, rows: List[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]): Future[Assertion] =
        rows match {
          case (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v) :: tail =>
            try {
              val future = fun(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v)
              future map { r =>
                org.scalatest.Succeeded
              } recover {
                case _: DiscardedEvaluationException =>
                  org.scalatest.Succeeded // discard this evaluation and move on to the next
                case ex: Throwable =>
                  val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName, vName) = heading

                  // SKIP-SCALATESTJS,NATIVE-START
                  val stackDepth = 2
                  // SKIP-SCALATESTJS,NATIVE-END
                  //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                  indicateFailure(
                    (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                      ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                    "    " + aName + " = " + a + "," + "\n" +
                                    "    " + bName + " = " + b + "," + "\n" +
                                    "    " + cName + " = " + c + "," + "\n" +
                                    "    " + dName + " = " + d + "," + "\n" +
                                    "    " + eName + " = " + e + "," + "\n" +
                                    "    " + fName + " = " + f + "," + "\n" +
                                    "    " + gName + " = " + g + "," + "\n" +
                                    "    " + hName + " = " + h + "," + "\n" +
                                    "    " + iName + " = " + i + "," + "\n" +
                                    "    " + jName + " = " + j + "," + "\n" +
                                    "    " + kName + " = " + k + "," + "\n" +
                                    "    " + lName + " = " + l + "," + "\n" +
                                    "    " + mName + " = " + m + "," + "\n" +
                                    "    " + nName + " = " + n + "," + "\n" +
                                    "    " + oName + " = " + o + "," + "\n" +
                                    "    " + pName + " = " + p + "," + "\n" +
                                    "    " + qName + " = " + q + "," + "\n" +
                                    "    " + rName + " = " + r + "," + "\n" +
                                    "    " + sName + " = " + s + "," + "\n" +
                                    "    " + tName + " = " + t + "," + "\n" +
                                    "    " + uName + " = " + u + "," + "\n" +
                                    "    " + vName + " = " + v + "\n" +

                      "  )",
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v),
                    List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName, vName),
                    Some(ex),
                    None, // Payload
                    prettifier,
                    pos,
                    idx
                  )
              } flatMap { u =>
                loop(idx + 1, tail)
              }
            }
            catch {
              case _: DiscardedEvaluationException => loop(idx + 1, tail)
              case ex: Throwable =>
                val (aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName, vName) = heading

                // SKIP-SCALATESTJS,NATIVE-START
                val stackDepth = 2
                // SKIP-SCALATESTJS,NATIVE-END
                //SCALATESTJS,NATIVE-ONLY val stackDepth = 1

                indicateFailure(
                  (sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                    ( sde.failedCodeFileNameAndLineNumberString match { case Some(s) => " (" + s + ")"; case None => "" }) + "\n" +
                    "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                    (
                      ex match {
                        case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                          "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                        case _ => ""
                      }
                    ) +
                    "  " + FailureMessages.occurredAtRow(prettifier, idx) + "\n" +
                                  "    " + aName + " = " + a + "," + "\n" +
                                  "    " + bName + " = " + b + "," + "\n" +
                                  "    " + cName + " = " + c + "," + "\n" +
                                  "    " + dName + " = " + d + "," + "\n" +
                                  "    " + eName + " = " + e + "," + "\n" +
                                  "    " + fName + " = " + f + "," + "\n" +
                                  "    " + gName + " = " + g + "," + "\n" +
                                  "    " + hName + " = " + h + "," + "\n" +
                                  "    " + iName + " = " + i + "," + "\n" +
                                  "    " + jName + " = " + j + "," + "\n" +
                                  "    " + kName + " = " + k + "," + "\n" +
                                  "    " + lName + " = " + l + "," + "\n" +
                                  "    " + mName + " = " + m + "," + "\n" +
                                  "    " + nName + " = " + n + "," + "\n" +
                                  "    " + oName + " = " + o + "," + "\n" +
                                  "    " + pName + " = " + p + "," + "\n" +
                                  "    " + qName + " = " + q + "," + "\n" +
                                  "    " + rName + " = " + r + "," + "\n" +
                                  "    " + sName + " = " + s + "," + "\n" +
                                  "    " + tName + " = " + t + "," + "\n" +
                                  "    " + uName + " = " + u + "," + "\n" +
                                  "    " + vName + " = " + v + "\n" +

                    "  )",
                  FailureMessages.undecoratedPropertyCheckFailureMessage,
                  List(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v),
                  List(aName, bName, cName, dName, eName, fName, gName, hName, iName, jName, kName, lName, mName, nName, oName, pName, qName, rName, sName, tName, uName, vName),
                  Some(ex),
                  None, // Payload
                  prettifier,
                  pos,
                  idx
                )
                Future.successful(org.scalatest.Succeeded)
            }

          case Nil => Future.successful(org.scalatest.Succeeded)
        }
      loop(0, rows.toList)
    }
                                   

    private[scalatest] case class ForResult[E](passedCount: Int = 0,
                          discardedCount: Int = 0,
                          messageAcc: IndexedSeq[String] = IndexedSeq.empty,
                          passedElements: IndexedSeq[(Int, E)] = IndexedSeq.empty,
                          failedElements: IndexedSeq[(Int, E, Throwable)] = IndexedSeq.empty)

    private[scalatest] def runAndCollectResult[E <: Product](namesOfArgs: List[String], rows: Seq[E], sourceFileName: String, methodName: String, stackDepthAdjustment: Int, prettifier: Prettifier, pos: source.Position)(fun: E => Future[ASSERTION]): Future[ForResult[E]] = {
      import org.scalatest.InspectorsHelper.{shouldPropagate, indentErrorMessages}

      def innerRunAndCollectResult(itr: Iterator[E], result: ForResult[E], index: Int)(fun: E => Future[ASSERTION]): Future[ForResult[E]] = {
        if (itr.hasNext) {
          val head = itr.next
          try {
          val future = fun(head)
          future map { r =>
            result.copy(passedCount = result.passedCount + 1, passedElements = result.passedElements :+ (index, head))
          } recover {
            case _: org.scalatest.exceptions.DiscardedEvaluationException => result.copy(discardedCount = result.discardedCount + 1) // discard this evaluation and move on to the next
            case ex if !shouldPropagate(ex) =>
              result.copy(failedElements =
                result.failedElements :+ ((index,
                  head,
                  new org.scalatest.exceptions.TableDrivenPropertyCheckFailedException(
                    ((sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                      (sde.failedCodeFileNameAndLineNumberString match {
                        case Some(s) => " (" + s + ")";
                        case None => ""
                      }) + "\n" +
                      "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                      (
                        ex match {
                          case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                            "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                          case _ => ""
                        }
                        ) +
                      "  " + FailureMessages.occurredAtRow(prettifier, index) + "\n" +
                      indentErrorMessages(namesOfArgs.zip(head.productIterator.toSeq).map { case (name, value) =>
                        name + " = " + value
                      }.toIndexedSeq).mkString("\n") +
                      "  )"),
                    Some(ex),
                    pos,
                    None,
                    FailureMessages.undecoratedPropertyCheckFailureMessage,
                    head.productIterator.toList,
                    namesOfArgs,
                    index
                  ))
                )
              )
          } flatMap { newResult =>
            innerRunAndCollectResult(itr, newResult, index + 1)(fun)
          }
          }
          catch {
            case _: org.scalatest.exceptions.DiscardedEvaluationException => innerRunAndCollectResult(itr, result.copy(discardedCount = result.discardedCount + 1), index + 1)(fun) // discard this evaluation and move on to the next
            case ex if !shouldPropagate(ex) =>
              innerRunAndCollectResult(
                itr,
                result.copy(failedElements =
                  result.failedElements :+ ((index,
                    head,
                    new org.scalatest.exceptions.TableDrivenPropertyCheckFailedException(
                      ((sde: StackDepthException) => FailureMessages.propertyException(prettifier, UnquotedString(ex.getClass.getSimpleName)) +
                        (sde.failedCodeFileNameAndLineNumberString match {
                          case Some(s) => " (" + s + ")";
                          case None => ""
                        }) + "\n" +
                        "  " + FailureMessages.thrownExceptionsMessage(prettifier, if (ex.getMessage == null) "None" else UnquotedString(ex.getMessage)) + "\n" +
                        (
                          ex match {
                            case sd: StackDepth if sd.failedCodeFileNameAndLineNumberString.isDefined =>
                              "  " + FailureMessages.thrownExceptionsLocation(prettifier, UnquotedString(sd.failedCodeFileNameAndLineNumberString.get)) + "\n"
                            case _ => ""
                          }
                          ) +
                        "  " + FailureMessages.occurredAtRow(prettifier, index) + "\n" +
                        indentErrorMessages(namesOfArgs.zip(head.productIterator.toSeq).map { case (name, value) =>
                          name + " = " + value
                        }.toIndexedSeq).mkString("\n") +
                      "  )"),
                      Some(ex),
                      pos,
                      None,
                      FailureMessages.undecoratedPropertyCheckFailureMessage,
                      head.productIterator.toList,
                      namesOfArgs,
                      index
                    ))
                  )
                ),
                index + 1
              )(fun)
          }
        }
        else
          Future.successful(result)
      }
      innerRunAndCollectResult(rows.toIterator, ForResult(), 0)(fun)
    }

    private def doForEvery[E <: Product](namesOfArgs: List[String], rows: Seq[E], messageFun: Any => String, sourceFileName: String, methodName: String, stackDepthAdjustment: Int, prettifier: Prettifier, pos: source.Position)(fun: E => Future[ASSERTION])(implicit asserting: TableAsserting[Future[ASSERTION]]): Result = {
      import org.scalatest.InspectorsHelper.indentErrorMessages
      val future = runAndCollectResult(namesOfArgs, rows, sourceFileName, methodName, stackDepthAdjustment + 2, prettifier, pos)(fun)
      future map { result =>
        val messageList = result.failedElements.map(_._3)
        if (messageList.size > 0)
          indicateFailure(
            messageFun(UnquotedString(indentErrorMessages(messageList.map(_.toString)).mkString(", \n"))),
            messageList.headOption,
            prettifier,
            pos
          )
        else indicateSuccess(FailureMessages.propertyCheckSucceeded)
        org.scalatest.Succeeded
      }
    }

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A](heading: (String), rows: (A)*)(fun: (A) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple1[A]](List(heading), rows.map(Tuple1.apply[A]), Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: Tuple1[A]) => fun(row._1))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B](heading: (String, String), rows: (A, B)*)(fun: (A, B) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple2[A, B]](List(heading._1, heading._2), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B)) => fun(row._1, row._2))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C](heading: (String, String, String), rows: (A, B, C)*)(fun: (A, B, C) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple3[A, B, C]](List(heading._1, heading._2, heading._3), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C)) => fun(row._1, row._2, row._3))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*)(fun: (A, B, C, D) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple4[A, B, C, D]](List(heading._1, heading._2, heading._3, heading._4), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D)) => fun(row._1, row._2, row._3, row._4))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*)(fun: (A, B, C, D, E) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple5[A, B, C, D, E]](List(heading._1, heading._2, heading._3, heading._4, heading._5), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E)) => fun(row._1, row._2, row._3, row._4, row._5))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*)(fun: (A, B, C, D, E, F) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple6[A, B, C, D, E, F]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F)) => fun(row._1, row._2, row._3, row._4, row._5, row._6))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*)(fun: (A, B, C, D, E, F, G) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple7[A, B, C, D, E, F, G]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*)(fun: (A, B, C, D, E, F, G, H) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple8[A, B, C, D, E, F, G, H]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*)(fun: (A, B, C, D, E, F, G, H, I) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple9[A, B, C, D, E, F, G, H, I]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*)(fun: (A, B, C, D, E, F, G, H, I, J) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple10[A, B, C, D, E, F, G, H, I, J]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*)(fun: (A, B, C, D, E, F, G, H, I, J, K) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple11[A, B, C, D, E, F, G, H, I, J, K]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple12[A, B, C, D, E, F, G, H, I, J, K, L]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple13[A, B, C, D, E, F, G, H, I, J, K, L, M]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20, heading._21), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20, row._21))
    }
                                     

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>forEvery</code> syntax.
     */
    def forEvery[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
     = {
      doForEvery[Tuple22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20, heading._21, heading._22), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "forEvery", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20, row._21, row._22))
    }
                                     

    private def doExists[E <: Product](namesOfArgs: List[String], rows: Seq[E], messageFun: Any => String, sourceFileName: String, methodName: String, stackDepthAdjustment: Int, prettifier: Prettifier, pos: source.Position)(fun: E => Future[ASSERTION])(implicit asserting: TableAsserting[ASSERTION]): Result = {
      import org.scalatest.InspectorsHelper.indentErrorMessages
      val future = runAndCollectResult(namesOfArgs, rows, sourceFileName, methodName, stackDepthAdjustment + 2, prettifier, pos)(fun)
      future map { result =>
        if (result.passedCount == 0) {
          val messageList = result.failedElements.map(_._3)
          indicateFailure(
            messageFun(UnquotedString(indentErrorMessages(messageList.map(_.toString)).mkString(", \n"))),
            messageList.headOption,
            prettifier,
            pos
          )
        }
        else indicateSuccess(FailureMessages.propertyCheckSucceeded)
        org.scalatest.Succeeded
      }
    }

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A](heading: (String), rows: (A)*)(fun: (A) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple1[A]](List(heading), rows.map(Tuple1.apply[A]), Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: Tuple1[A]) => fun(row._1))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B](heading: (String, String), rows: (A, B)*)(fun: (A, B) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple2[A, B]](List(heading._1, heading._2), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B)) => fun(row._1, row._2))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C](heading: (String, String, String), rows: (A, B, C)*)(fun: (A, B, C) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple3[A, B, C]](List(heading._1, heading._2, heading._3), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C)) => fun(row._1, row._2, row._3))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*)(fun: (A, B, C, D) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple4[A, B, C, D]](List(heading._1, heading._2, heading._3, heading._4), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D)) => fun(row._1, row._2, row._3, row._4))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*)(fun: (A, B, C, D, E) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple5[A, B, C, D, E]](List(heading._1, heading._2, heading._3, heading._4, heading._5), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E)) => fun(row._1, row._2, row._3, row._4, row._5))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*)(fun: (A, B, C, D, E, F) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple6[A, B, C, D, E, F]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F)) => fun(row._1, row._2, row._3, row._4, row._5, row._6))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*)(fun: (A, B, C, D, E, F, G) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple7[A, B, C, D, E, F, G]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*)(fun: (A, B, C, D, E, F, G, H) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple8[A, B, C, D, E, F, G, H]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*)(fun: (A, B, C, D, E, F, G, H, I) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple9[A, B, C, D, E, F, G, H, I]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*)(fun: (A, B, C, D, E, F, G, H, I, J) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple10[A, B, C, D, E, F, G, H, I, J]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*)(fun: (A, B, C, D, E, F, G, H, I, J, K) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple11[A, B, C, D, E, F, G, H, I, J, K]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple12[A, B, C, D, E, F, G, H, I, J, K, L]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple13[A, B, C, D, E, F, G, H, I, J, K, L, M]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20, heading._21), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20, row._21))
    }
                                   

    /**
     * Implementation method for [[org.scalatest.prop.TableDrivenPropertyChecks TableDrivenPropertyChecks]]'s <code>exists</code> syntax.
     */
    def exists[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*)(fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => Future[ASSERTION])(implicit prettifier: Prettifier, pos: source.Position): Result
           = {
      doExists[Tuple22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]](List(heading._1, heading._2, heading._3, heading._4, heading._5, heading._6, heading._7, heading._8, heading._9, heading._10, heading._11, heading._12, heading._13, heading._14, heading._15, heading._16, heading._17, heading._18, heading._19, heading._20, heading._21, heading._22), rows, Resources.tableDrivenForEveryFailed _, "TableAsserting.scala", "doExists", 2, prettifier, pos)((row: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)) => fun(row._1, row._2, row._3, row._4, row._5, row._6, row._7, row._8, row._9, row._10, row._11, row._12, row._13, row._14, row._15, row._16, row._17, row._18, row._19, row._20, row._21, row._22))
    }
                                   

    private[scalatest] def indicateSuccess(message: => String): Assertion

    private[scalatest] def indicateFailure(messageFun: StackDepthException => String, undecoratedMessage: => String, args: List[Any], namesOfArgs: List[String], optionalCause: Option[Throwable], payload: Option[Any], prettifier: Prettifier, pos: source.Position, idx: Int): Assertion

    private[scalatest] def indicateFailure(message: => String, optionalCause: Option[Throwable], prettifier: Prettifier, pos: source.Position): Assertion
  }

  /**
   * Provides support of [[org.scalatest.enablers.TableAsserting TableAsserting]] for Unit.  Do nothing when the check succeeds,
   * but throw [[org.scalatest.exceptions.TableDrivenPropertyCheckFailedException TableDrivenPropertyCheckFailedException]]
   * when check fails.
   */
  implicit def assertingNatureOfT[T]: TableAsserting[T] { type Result = Unit } = {
    new TableAssertingImpl[T] {
      type Result = Unit
      def indicateSuccess(message: => String): Unit = ()
      def indicateFailure(messageFun: StackDepthException => String, undecoratedMessage: => String, args: List[Any], namesOfArgs: List[String], optionalCause: Option[Throwable], payload: Option[Any], prettifier: Prettifier, pos: source.Position, idx: Int): Unit =
        throw new TableDrivenPropertyCheckFailedException(
          messageFun,
          optionalCause,
          pos,
          payload,
          undecoratedMessage,
          args,
          namesOfArgs,
          idx
        )
      def indicateFailure(message: => String, optionalCause: Option[Throwable], prettifier: Prettifier, pos: source.Position): Unit =
        throw new org.scalatest.exceptions.TestFailedException(
          (_: StackDepthException) => Some(message),
          optionalCause,
          pos
        )
    }
  }
}

 /**
  * Abstract class that in the future will hold an intermediate priority <code>TableAsserting</code> implicit, which will enable inspector expressions
  * that have result type <code>Expectation</code>, a more composable form of assertion that returns a result instead of throwing an exception when it fails.
  */
/*abstract class ExpectationTableAsserting extends UnitTableAsserting with  {

  implicit def assertingNatureOfExpectation: TableAsserting[Expectation] { type Result = Expectation } = {
    new TableAsserting[Expectation] {
      type Result = Expectation
    }
  }
}*/

/**
 * Companion object to <code>TableAsserting</code> that provides two implicit providers, a higher priority one for passed functions that have result
 * type <code>Assertion</code>, which also yields result type <code>Assertion</code>, and one for any other type, which yields result type <code>Unit</code>.
 */
object TableAsserting extends UnitTableAsserting /*ExpectationTableAsserting*/ {

  /**
    * Provides support of [[org.scalatest.enablers.TableAsserting TableAsserting]] for Assertion.  Returns [[org.scalatest.Succeeded Succeeded]] when the check succeeds,
    * but throw [[org.scalatest.exceptions.TableDrivenPropertyCheckFailedException TableDrivenPropertyCheckFailedException]]
    * when check fails.
    */
  implicit def assertingNatureOfAssertion: TableAsserting[Assertion] { type Result = Assertion } = {
    new TableAssertingImpl[Assertion] {
      type Result = Assertion
      def indicateSuccess(message: => String): Assertion = Succeeded
      def indicateFailure(messageFun: StackDepthException => String, undecoratedMessage: => String, args: List[Any], namesOfArgs: List[String], optionalCause: Option[Throwable], payload: Option[Any], prettifier: Prettifier, pos: source.Position, idx: Int): Assertion =
        throw new TableDrivenPropertyCheckFailedException(
          messageFun,
          optionalCause,
          pos,
          payload,
          undecoratedMessage,
          args,
          namesOfArgs,
          idx
        )
      def indicateFailure(message: => String, optionalCause: Option[Throwable], prettifier: Prettifier, pos: source.Position): Assertion =
        throw new org.scalatest.exceptions.TestFailedException(
          (_: StackDepthException) => Some(message),
          optionalCause,
          pos
        )
    }
  }

  implicit def assertingNatureOfFutureAssertion(implicit exeCtx: scala.concurrent.ExecutionContext): TableAsserting[Future[Assertion]] { type Result = Future[Assertion] } = {
    new FutureTableAssertingImpl[Assertion] {
      implicit val executionContext = exeCtx
      def indicateSuccess(message: => String): Assertion = org.scalatest.Succeeded
      def indicateFailure(messageFun: StackDepthException => String, undecoratedMessage: => String, args: List[Any], namesOfArgs: List[String], optionalCause: Option[Throwable], payload: Option[Any], prettifier: Prettifier, pos: source.Position, idx: Int): Assertion =
        throw new TableDrivenPropertyCheckFailedException(
          messageFun,
          optionalCause,
          pos,
          payload,
          undecoratedMessage,
          args,
          namesOfArgs,
          idx
        )
      def indicateFailure(message: => String, optionalCause: Option[Throwable], prettifier: Prettifier, pos: source.Position): Assertion =
        throw new org.scalatest.exceptions.TestFailedException(
          (_: StackDepthException) => Some(message),
          optionalCause,
          pos
        )
    }
  }
}


      