/*
 * Copyright 2001-2023 Artima, Inc.
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
package org.scalatest.prop

import scala.collection.mutable.Builder
import scala.collection.mutable.ListBuffer
import org.scalactic.ColCompatHelper.IndexedSeqLike
import scala.collection.generic.CanBuildFrom
import org.scalatest.exceptions.StackDepth
import org.scalatest.exceptions.DiscardedEvaluationException
import org.scalatest.exceptions.TableDrivenPropertyCheckFailedException
import org.scalatest.enablers.TableAsserting
import org.scalactic._

/**
 * A table with 1 column.
 *
 * <p>
 * For an overview of using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of objects, where each object represents one row of the (one-column) table.
 * This table also carries with it a <em>heading</em> tuple that gives a string name to the
 * lone column of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor1</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     <span class="stQuotedString">"a"</span>,
 *       <span class="stLiteral">0</span>,
 *       <span class="stLiteral">1</span>,
 *       <span class="stLiteral">2</span>,
 *       <span class="stLiteral">3</span>,
 *       <span class="stLiteral">4</span>,
 *       <span class="stLiteral">5</span>,
 *       <span class="stLiteral">6</span>,
 *       <span class="stLiteral">7</span>,
 *       <span class="stLiteral">8</span>,
 *       <span class="stLiteral">9</span>
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied a list of non-tuple objects, the type you'll get back will be a <code>TableFor1</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the type of the objects contained in this table. The <code>apply</code> method will invoke the
 * function with the object in each row passed as the lone argument, in ascending order by index. (<em>I.e.</em>,
 * the zeroth object is checked first, then the object with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor1</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor1</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a) =>
 *   a should equal (a * <span class="stLiteral">1</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor1</code> is a <code>Seq[(A)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * <p>
 * One other way to use a <code>TableFor1</code> is to test subsequent return values
 * of a stateful function. Imagine, for example, you had an object named <code>FiboGen</code>
 * whose <code>next</code> method returned the <em>next</em> fibonacci number, where next
 * means the next number in the series following the number previously returned by <code>next</code>.
 * So the first time <code>next</code> was called, it would return 0. The next time it was called
 * it would return 1. Then 1. Then 2. Then 3, and so on. <code>FiboGen</code> would need to
 * be stateful, because it has to remember where it is in the series. In such a situation,
 * you could create a <code>TableFor1</code> (a table with one column, which you could alternatively
 * think of as one row), in which each row represents
 * the next value you expect.
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> first14FiboNums =
 *   <span class="stType">Table</span>(<span class="stQuotedString">"n"</span>, <span class="stLiteral">0</span>, <span class="stLiteral">1</span>, <span class="stLiteral">1</span>, <span class="stLiteral">2</span>, <span class="stLiteral">3</span>, <span class="stLiteral">5</span>, <span class="stLiteral">8</span>, <span class="stLiteral">13</span>, <span class="stLiteral">21</span>, <span class="stLiteral">34</span>, <span class="stLiteral">55</span>, <span class="stLiteral">89</span>, <span class="stLiteral">144</span>, <span class="stLiteral">233</span>)
 * </pre>
 *
 * <p>
 * Then in your <code>forAll</code> simply call the function and compare it with the
 * expected return value, like this:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (first14FiboNums) { n =>
 *   FiboGen.next should equal (n)
 * }
 * </pre>
 *
 * @param heading a string name for the lone column of this table
 * @param rows a variable length parameter list of objects containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor1[A](val heading: (String), rows: (A)*) extends IndexedSeq[(A)] with scala.collection.IndexedSeqOps[(A), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A)) => Boolean): TableFor1[A] = new TableFor1[A](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A)]): TableFor1[A] = new TableFor1[A](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor1</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor1</code>
   */
  def apply[ASSERTION](fun: (A) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor1</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor1</code> to return another <code>TableFor1</code>.
 *
 * @author Bill Venners
 */
object TableFor1 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor1</code> to return sequences of type <code>TableFor1</code>.
   */
  implicit def canBuildFrom[A]: scala.collection.BuildFrom[TableFor1[A], (A), TableFor1[A]] =
    new scala.collection.BuildFrom[TableFor1[A], (A), TableFor1[A]] {
      def apply(): Builder[(A), TableFor1[A]] =
        new ListBuffer mapResult { (buf: Seq[(A)]) =>
          new TableFor1(("arg0"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor1[A]): scala.collection.mutable.Builder[(A), TableFor1[A]] =
        new ListBuffer mapResult { (buf: Seq[(A)]) =>
          new TableFor1(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor1[A])(it: IterableOnce[(A)]): org.scalatest.prop.TableFor1[A] =
        new TableFor1(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 2 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple2</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor2</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 2 members in each tuple, the type you'll get back will be a <code>TableFor2</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor2</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor2</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b) =>
 *   a + b should equal (a * <span class="stLiteral">2</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor2</code> is a <code>Seq[(A, B)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple2</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor2[A, B](val heading: (String, String), rows: (A, B)*) extends IndexedSeq[(A, B)] with scala.collection.IndexedSeqOps[(A, B), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B)) => Boolean): TableFor2[A, B] = new TableFor2[A, B](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B)]): TableFor2[A, B] = new TableFor2[A, B](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor2</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor2</code>
   */
  def apply[ASSERTION](fun: (A, B) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor2</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor2</code> to return another <code>TableFor2</code>.
 *
 * @author Bill Venners
 */
object TableFor2 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor2</code> to return sequences of type <code>TableFor2</code>.
   */
  implicit def canBuildFrom[A, B]: scala.collection.BuildFrom[TableFor2[A, B], (A, B), TableFor2[A, B]] =
    new scala.collection.BuildFrom[TableFor2[A, B], (A, B), TableFor2[A, B]] {
      def apply(): Builder[(A, B), TableFor2[A, B]] =
        new ListBuffer mapResult { (buf: Seq[(A, B)]) =>
          new TableFor2(("arg0","arg1"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor2[A, B]): scala.collection.mutable.Builder[(A, B), TableFor2[A, B]] =
        new ListBuffer mapResult { (buf: Seq[(A, B)]) =>
          new TableFor2(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor2[A, B])(it: IterableOnce[(A, B)]): org.scalatest.prop.TableFor2[A, B] =
        new TableFor2(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 3 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple3</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor3</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 3 members in each tuple, the type you'll get back will be a <code>TableFor3</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor3</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor3</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c) =>
 *   a + b + c should equal (a * <span class="stLiteral">3</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor3</code> is a <code>Seq[(A, B, C)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple3</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor3[A, B, C](val heading: (String, String, String), rows: (A, B, C)*) extends IndexedSeq[(A, B, C)] with scala.collection.IndexedSeqOps[(A, B, C), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C)) => Boolean): TableFor3[A, B, C] = new TableFor3[A, B, C](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C)]): TableFor3[A, B, C] = new TableFor3[A, B, C](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor3</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor3</code>
   */
  def apply[ASSERTION](fun: (A, B, C) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor3</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor3</code> to return another <code>TableFor3</code>.
 *
 * @author Bill Venners
 */
object TableFor3 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor3</code> to return sequences of type <code>TableFor3</code>.
   */
  implicit def canBuildFrom[A, B, C]: scala.collection.BuildFrom[TableFor3[A, B, C], (A, B, C), TableFor3[A, B, C]] =
    new scala.collection.BuildFrom[TableFor3[A, B, C], (A, B, C), TableFor3[A, B, C]] {
      def apply(): Builder[(A, B, C), TableFor3[A, B, C]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C)]) =>
          new TableFor3(("arg0","arg1","arg2"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor3[A, B, C]): scala.collection.mutable.Builder[(A, B, C), TableFor3[A, B, C]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C)]) =>
          new TableFor3(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor3[A, B, C])(it: IterableOnce[(A, B, C)]): org.scalatest.prop.TableFor3[A, B, C] =
        new TableFor3(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 4 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple4</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor4</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 4 members in each tuple, the type you'll get back will be a <code>TableFor4</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor4</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor4</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d) =>
 *   a + b + c + d should equal (a * <span class="stLiteral">4</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor4</code> is a <code>Seq[(A, B, C, D)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple4</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor4[A, B, C, D](val heading: (String, String, String, String), rows: (A, B, C, D)*) extends IndexedSeq[(A, B, C, D)] with scala.collection.IndexedSeqOps[(A, B, C, D), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D)) => Boolean): TableFor4[A, B, C, D] = new TableFor4[A, B, C, D](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D)]): TableFor4[A, B, C, D] = new TableFor4[A, B, C, D](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor4</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor4</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor4</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor4</code> to return another <code>TableFor4</code>.
 *
 * @author Bill Venners
 */
object TableFor4 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor4</code> to return sequences of type <code>TableFor4</code>.
   */
  implicit def canBuildFrom[A, B, C, D]: scala.collection.BuildFrom[TableFor4[A, B, C, D], (A, B, C, D), TableFor4[A, B, C, D]] =
    new scala.collection.BuildFrom[TableFor4[A, B, C, D], (A, B, C, D), TableFor4[A, B, C, D]] {
      def apply(): Builder[(A, B, C, D), TableFor4[A, B, C, D]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D)]) =>
          new TableFor4(("arg0","arg1","arg2","arg3"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor4[A, B, C, D]): scala.collection.mutable.Builder[(A, B, C, D), TableFor4[A, B, C, D]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D)]) =>
          new TableFor4(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor4[A, B, C, D])(it: IterableOnce[(A, B, C, D)]): org.scalatest.prop.TableFor4[A, B, C, D] =
        new TableFor4(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 5 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple5</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor5</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 5 members in each tuple, the type you'll get back will be a <code>TableFor5</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor5</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor5</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e) =>
 *   a + b + c + d + e should equal (a * <span class="stLiteral">5</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor5</code> is a <code>Seq[(A, B, C, D, E)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple5</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor5[A, B, C, D, E](val heading: (String, String, String, String, String), rows: (A, B, C, D, E)*) extends IndexedSeq[(A, B, C, D, E)] with scala.collection.IndexedSeqOps[(A, B, C, D, E), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E)) => Boolean): TableFor5[A, B, C, D, E] = new TableFor5[A, B, C, D, E](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E)]): TableFor5[A, B, C, D, E] = new TableFor5[A, B, C, D, E](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor5</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor5</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor5</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor5</code> to return another <code>TableFor5</code>.
 *
 * @author Bill Venners
 */
object TableFor5 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor5</code> to return sequences of type <code>TableFor5</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E]: scala.collection.BuildFrom[TableFor5[A, B, C, D, E], (A, B, C, D, E), TableFor5[A, B, C, D, E]] =
    new scala.collection.BuildFrom[TableFor5[A, B, C, D, E], (A, B, C, D, E), TableFor5[A, B, C, D, E]] {
      def apply(): Builder[(A, B, C, D, E), TableFor5[A, B, C, D, E]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E)]) =>
          new TableFor5(("arg0","arg1","arg2","arg3","arg4"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor5[A, B, C, D, E]): scala.collection.mutable.Builder[(A, B, C, D, E), TableFor5[A, B, C, D, E]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E)]) =>
          new TableFor5(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor5[A, B, C, D, E])(it: IterableOnce[(A, B, C, D, E)]): org.scalatest.prop.TableFor5[A, B, C, D, E] =
        new TableFor5(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 6 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple6</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor6</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 6 members in each tuple, the type you'll get back will be a <code>TableFor6</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor6</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor6</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f) =>
 *   a + b + c + d + e + f should equal (a * <span class="stLiteral">6</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor6</code> is a <code>Seq[(A, B, C, D, E, F)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple6</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor6[A, B, C, D, E, F](val heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*) extends IndexedSeq[(A, B, C, D, E, F)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F)) => Boolean): TableFor6[A, B, C, D, E, F] = new TableFor6[A, B, C, D, E, F](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F)]): TableFor6[A, B, C, D, E, F] = new TableFor6[A, B, C, D, E, F](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor6</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor6</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor6</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor6</code> to return another <code>TableFor6</code>.
 *
 * @author Bill Venners
 */
object TableFor6 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor6</code> to return sequences of type <code>TableFor6</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F]: scala.collection.BuildFrom[TableFor6[A, B, C, D, E, F], (A, B, C, D, E, F), TableFor6[A, B, C, D, E, F]] =
    new scala.collection.BuildFrom[TableFor6[A, B, C, D, E, F], (A, B, C, D, E, F), TableFor6[A, B, C, D, E, F]] {
      def apply(): Builder[(A, B, C, D, E, F), TableFor6[A, B, C, D, E, F]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F)]) =>
          new TableFor6(("arg0","arg1","arg2","arg3","arg4","arg5"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor6[A, B, C, D, E, F]): scala.collection.mutable.Builder[(A, B, C, D, E, F), TableFor6[A, B, C, D, E, F]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F)]) =>
          new TableFor6(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor6[A, B, C, D, E, F])(it: IterableOnce[(A, B, C, D, E, F)]): org.scalatest.prop.TableFor6[A, B, C, D, E, F] =
        new TableFor6(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 7 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple7</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor7</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 7 members in each tuple, the type you'll get back will be a <code>TableFor7</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor7</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor7</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g) =>
 *   a + b + c + d + e + f + g should equal (a * <span class="stLiteral">7</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor7</code> is a <code>Seq[(A, B, C, D, E, F, G)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple7</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor7[A, B, C, D, E, F, G](val heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*) extends IndexedSeq[(A, B, C, D, E, F, G)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G)) => Boolean): TableFor7[A, B, C, D, E, F, G] = new TableFor7[A, B, C, D, E, F, G](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G)]): TableFor7[A, B, C, D, E, F, G] = new TableFor7[A, B, C, D, E, F, G](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor7</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor7</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor7</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor7</code> to return another <code>TableFor7</code>.
 *
 * @author Bill Venners
 */
object TableFor7 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor7</code> to return sequences of type <code>TableFor7</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G]: scala.collection.BuildFrom[TableFor7[A, B, C, D, E, F, G], (A, B, C, D, E, F, G), TableFor7[A, B, C, D, E, F, G]] =
    new scala.collection.BuildFrom[TableFor7[A, B, C, D, E, F, G], (A, B, C, D, E, F, G), TableFor7[A, B, C, D, E, F, G]] {
      def apply(): Builder[(A, B, C, D, E, F, G), TableFor7[A, B, C, D, E, F, G]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G)]) =>
          new TableFor7(("arg0","arg1","arg2","arg3","arg4","arg5","arg6"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor7[A, B, C, D, E, F, G]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G), TableFor7[A, B, C, D, E, F, G]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G)]) =>
          new TableFor7(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor7[A, B, C, D, E, F, G])(it: IterableOnce[(A, B, C, D, E, F, G)]): org.scalatest.prop.TableFor7[A, B, C, D, E, F, G] =
        new TableFor7(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 8 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple8</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor8</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 8 members in each tuple, the type you'll get back will be a <code>TableFor8</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor8</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor8</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h) =>
 *   a + b + c + d + e + f + g + h should equal (a * <span class="stLiteral">8</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor8</code> is a <code>Seq[(A, B, C, D, E, F, G, H)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple8</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor8[A, B, C, D, E, F, G, H](val heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*) extends IndexedSeq[(A, B, C, D, E, F, G, H)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H)) => Boolean): TableFor8[A, B, C, D, E, F, G, H] = new TableFor8[A, B, C, D, E, F, G, H](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H)]): TableFor8[A, B, C, D, E, F, G, H] = new TableFor8[A, B, C, D, E, F, G, H](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor8</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor8</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor8</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor8</code> to return another <code>TableFor8</code>.
 *
 * @author Bill Venners
 */
object TableFor8 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor8</code> to return sequences of type <code>TableFor8</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H]: scala.collection.BuildFrom[TableFor8[A, B, C, D, E, F, G, H], (A, B, C, D, E, F, G, H), TableFor8[A, B, C, D, E, F, G, H]] =
    new scala.collection.BuildFrom[TableFor8[A, B, C, D, E, F, G, H], (A, B, C, D, E, F, G, H), TableFor8[A, B, C, D, E, F, G, H]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H), TableFor8[A, B, C, D, E, F, G, H]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H)]) =>
          new TableFor8(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor8[A, B, C, D, E, F, G, H]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H), TableFor8[A, B, C, D, E, F, G, H]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H)]) =>
          new TableFor8(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor8[A, B, C, D, E, F, G, H])(it: IterableOnce[(A, B, C, D, E, F, G, H)]): org.scalatest.prop.TableFor8[A, B, C, D, E, F, G, H] =
        new TableFor8(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 9 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple9</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor9</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 9 members in each tuple, the type you'll get back will be a <code>TableFor9</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor9</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor9</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i) =>
 *   a + b + c + d + e + f + g + h + i should equal (a * <span class="stLiteral">9</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor9</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple9</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor9[A, B, C, D, E, F, G, H, I](val heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I)) => Boolean): TableFor9[A, B, C, D, E, F, G, H, I] = new TableFor9[A, B, C, D, E, F, G, H, I](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I)]): TableFor9[A, B, C, D, E, F, G, H, I] = new TableFor9[A, B, C, D, E, F, G, H, I](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor9</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor9</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor9</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor9</code> to return another <code>TableFor9</code>.
 *
 * @author Bill Venners
 */
object TableFor9 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor9</code> to return sequences of type <code>TableFor9</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I]: scala.collection.BuildFrom[TableFor9[A, B, C, D, E, F, G, H, I], (A, B, C, D, E, F, G, H, I), TableFor9[A, B, C, D, E, F, G, H, I]] =
    new scala.collection.BuildFrom[TableFor9[A, B, C, D, E, F, G, H, I], (A, B, C, D, E, F, G, H, I), TableFor9[A, B, C, D, E, F, G, H, I]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I), TableFor9[A, B, C, D, E, F, G, H, I]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I)]) =>
          new TableFor9(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor9[A, B, C, D, E, F, G, H, I]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I), TableFor9[A, B, C, D, E, F, G, H, I]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I)]) =>
          new TableFor9(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor9[A, B, C, D, E, F, G, H, I])(it: IterableOnce[(A, B, C, D, E, F, G, H, I)]): org.scalatest.prop.TableFor9[A, B, C, D, E, F, G, H, I] =
        new TableFor9(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 10 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple10</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor10</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 10 members in each tuple, the type you'll get back will be a <code>TableFor10</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor10</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor10</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j) =>
 *   a + b + c + d + e + f + g + h + i + j should equal (a * <span class="stLiteral">10</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor10</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple10</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor10[A, B, C, D, E, F, G, H, I, J](val heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J)) => Boolean): TableFor10[A, B, C, D, E, F, G, H, I, J] = new TableFor10[A, B, C, D, E, F, G, H, I, J](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J)]): TableFor10[A, B, C, D, E, F, G, H, I, J] = new TableFor10[A, B, C, D, E, F, G, H, I, J](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor10</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor10</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor10</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor10</code> to return another <code>TableFor10</code>.
 *
 * @author Bill Venners
 */
object TableFor10 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor10</code> to return sequences of type <code>TableFor10</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J]: scala.collection.BuildFrom[TableFor10[A, B, C, D, E, F, G, H, I, J], (A, B, C, D, E, F, G, H, I, J), TableFor10[A, B, C, D, E, F, G, H, I, J]] =
    new scala.collection.BuildFrom[TableFor10[A, B, C, D, E, F, G, H, I, J], (A, B, C, D, E, F, G, H, I, J), TableFor10[A, B, C, D, E, F, G, H, I, J]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J), TableFor10[A, B, C, D, E, F, G, H, I, J]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J)]) =>
          new TableFor10(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor10[A, B, C, D, E, F, G, H, I, J]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J), TableFor10[A, B, C, D, E, F, G, H, I, J]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J)]) =>
          new TableFor10(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor10[A, B, C, D, E, F, G, H, I, J])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J)]): org.scalatest.prop.TableFor10[A, B, C, D, E, F, G, H, I, J] =
        new TableFor10(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 11 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple11</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor11</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 11 members in each tuple, the type you'll get back will be a <code>TableFor11</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor11</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor11</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k) =>
 *   a + b + c + d + e + f + g + h + i + j + k should equal (a * <span class="stLiteral">11</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor11</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple11</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor11[A, B, C, D, E, F, G, H, I, J, K](val heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K)) => Boolean): TableFor11[A, B, C, D, E, F, G, H, I, J, K] = new TableFor11[A, B, C, D, E, F, G, H, I, J, K](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K)]): TableFor11[A, B, C, D, E, F, G, H, I, J, K] = new TableFor11[A, B, C, D, E, F, G, H, I, J, K](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor11</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor11</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor11</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor11</code> to return another <code>TableFor11</code>.
 *
 * @author Bill Venners
 */
object TableFor11 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor11</code> to return sequences of type <code>TableFor11</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K]: scala.collection.BuildFrom[TableFor11[A, B, C, D, E, F, G, H, I, J, K], (A, B, C, D, E, F, G, H, I, J, K), TableFor11[A, B, C, D, E, F, G, H, I, J, K]] =
    new scala.collection.BuildFrom[TableFor11[A, B, C, D, E, F, G, H, I, J, K], (A, B, C, D, E, F, G, H, I, J, K), TableFor11[A, B, C, D, E, F, G, H, I, J, K]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K), TableFor11[A, B, C, D, E, F, G, H, I, J, K]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K)]) =>
          new TableFor11(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor11[A, B, C, D, E, F, G, H, I, J, K]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K), TableFor11[A, B, C, D, E, F, G, H, I, J, K]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K)]) =>
          new TableFor11(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor11[A, B, C, D, E, F, G, H, I, J, K])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K)]): org.scalatest.prop.TableFor11[A, B, C, D, E, F, G, H, I, J, K] =
        new TableFor11(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 12 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple12</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor12</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 12 members in each tuple, the type you'll get back will be a <code>TableFor12</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor12</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor12</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l should equal (a * <span class="stLiteral">12</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor12</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple12</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor12[A, B, C, D, E, F, G, H, I, J, K, L](val heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L)) => Boolean): TableFor12[A, B, C, D, E, F, G, H, I, J, K, L] = new TableFor12[A, B, C, D, E, F, G, H, I, J, K, L](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L)]): TableFor12[A, B, C, D, E, F, G, H, I, J, K, L] = new TableFor12[A, B, C, D, E, F, G, H, I, J, K, L](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor12</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor12</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor12</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor12</code> to return another <code>TableFor12</code>.
 *
 * @author Bill Venners
 */
object TableFor12 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor12</code> to return sequences of type <code>TableFor12</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L]: scala.collection.BuildFrom[TableFor12[A, B, C, D, E, F, G, H, I, J, K, L], (A, B, C, D, E, F, G, H, I, J, K, L), TableFor12[A, B, C, D, E, F, G, H, I, J, K, L]] =
    new scala.collection.BuildFrom[TableFor12[A, B, C, D, E, F, G, H, I, J, K, L], (A, B, C, D, E, F, G, H, I, J, K, L), TableFor12[A, B, C, D, E, F, G, H, I, J, K, L]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L), TableFor12[A, B, C, D, E, F, G, H, I, J, K, L]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L)]) =>
          new TableFor12(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor12[A, B, C, D, E, F, G, H, I, J, K, L]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L), TableFor12[A, B, C, D, E, F, G, H, I, J, K, L]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L)]) =>
          new TableFor12(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor12[A, B, C, D, E, F, G, H, I, J, K, L])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L)]): org.scalatest.prop.TableFor12[A, B, C, D, E, F, G, H, I, J, K, L] =
        new TableFor12(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 13 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple13</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor13</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 13 members in each tuple, the type you'll get back will be a <code>TableFor13</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor13</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor13</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m should equal (a * <span class="stLiteral">13</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor13</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple13</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M)) => Boolean): TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M] = new TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M)]): TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M] = new TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor13</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor13</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor13</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor13</code> to return another <code>TableFor13</code>.
 *
 * @author Bill Venners
 */
object TableFor13 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor13</code> to return sequences of type <code>TableFor13</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M]: scala.collection.BuildFrom[TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M], (A, B, C, D, E, F, G, H, I, J, K, L, M), TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M]] =
    new scala.collection.BuildFrom[TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M], (A, B, C, D, E, F, G, H, I, J, K, L, M), TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M), TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M)]) =>
          new TableFor13(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M), TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M)]) =>
          new TableFor13(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M)]): org.scalatest.prop.TableFor13[A, B, C, D, E, F, G, H, I, J, K, L, M] =
        new TableFor13(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 14 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple14</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor14</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>, <span class="stQuotedString">"n"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 14 members in each tuple, the type you'll get back will be a <code>TableFor14</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor14</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor14</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m, n) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m + n should equal (a * <span class="stLiteral">14</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor14</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple14</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M, N), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M, N) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M, N)) => Boolean): TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N] = new TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]): TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N] = new TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor14</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor14</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor14</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor14</code> to return another <code>TableFor14</code>.
 *
 * @author Bill Venners
 */
object TableFor14 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor14</code> to return sequences of type <code>TableFor14</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M, N]: scala.collection.BuildFrom[TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N], (A, B, C, D, E, F, G, H, I, J, K, L, M, N), TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]] =
    new scala.collection.BuildFrom[TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N], (A, B, C, D, E, F, G, H, I, J, K, L, M, N), TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N), TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]) =>
          new TableFor14(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12","arg13"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N), TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]) =>
          new TableFor14(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]): org.scalatest.prop.TableFor14[A, B, C, D, E, F, G, H, I, J, K, L, M, N] =
        new TableFor14(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 15 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple15</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor15</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>, <span class="stQuotedString">"n"</span>, <span class="stQuotedString">"o"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 15 members in each tuple, the type you'll get back will be a <code>TableFor15</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor15</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor15</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m + n + o should equal (a * <span class="stLiteral">15</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor15</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple15</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)) => Boolean): TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O] = new TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]): TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O] = new TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor15</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor15</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor15</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor15</code> to return another <code>TableFor15</code>.
 *
 * @author Bill Venners
 */
object TableFor15 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor15</code> to return sequences of type <code>TableFor15</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]: scala.collection.BuildFrom[TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O), TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]] =
    new scala.collection.BuildFrom[TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O), TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O), TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]) =>
          new TableFor15(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12","arg13","arg14"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O), TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]) =>
          new TableFor15(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]): org.scalatest.prop.TableFor15[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O] =
        new TableFor15(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 16 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple16</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor16</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>, <span class="stQuotedString">"n"</span>, <span class="stQuotedString">"o"</span>, <span class="stQuotedString">"p"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 16 members in each tuple, the type you'll get back will be a <code>TableFor16</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor16</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor16</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p should equal (a * <span class="stLiteral">16</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor16</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple16</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)) => Boolean): TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P] = new TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]): TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P] = new TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor16</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor16</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor16</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor16</code> to return another <code>TableFor16</code>.
 *
 * @author Bill Venners
 */
object TableFor16 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor16</code> to return sequences of type <code>TableFor16</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]: scala.collection.BuildFrom[TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P), TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]] =
    new scala.collection.BuildFrom[TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P), TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P), TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]) =>
          new TableFor16(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12","arg13","arg14","arg15"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P), TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]) =>
          new TableFor16(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]): org.scalatest.prop.TableFor16[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P] =
        new TableFor16(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 17 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple17</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor17</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>, <span class="stQuotedString">"n"</span>, <span class="stQuotedString">"o"</span>, <span class="stQuotedString">"p"</span>, <span class="stQuotedString">"q"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 17 members in each tuple, the type you'll get back will be a <code>TableFor17</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor17</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor17</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q should equal (a * <span class="stLiteral">17</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor17</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple17</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)) => Boolean): TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q] = new TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]): TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q] = new TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor17</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor17</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor17</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor17</code> to return another <code>TableFor17</code>.
 *
 * @author Bill Venners
 */
object TableFor17 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor17</code> to return sequences of type <code>TableFor17</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]: scala.collection.BuildFrom[TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q), TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]] =
    new scala.collection.BuildFrom[TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q), TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q), TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]) =>
          new TableFor17(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12","arg13","arg14","arg15","arg16"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q), TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]) =>
          new TableFor17(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]): org.scalatest.prop.TableFor17[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q] =
        new TableFor17(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 18 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple18</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor18</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>, <span class="stQuotedString">"n"</span>, <span class="stQuotedString">"o"</span>, <span class="stQuotedString">"p"</span>, <span class="stQuotedString">"q"</span>, <span class="stQuotedString">"r"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 18 members in each tuple, the type you'll get back will be a <code>TableFor18</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor18</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor18</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r should equal (a * <span class="stLiteral">18</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor18</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple18</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)) => Boolean): TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R] = new TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]): TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R] = new TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor18</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor18</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor18</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor18</code> to return another <code>TableFor18</code>.
 *
 * @author Bill Venners
 */
object TableFor18 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor18</code> to return sequences of type <code>TableFor18</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]: scala.collection.BuildFrom[TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R), TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]] =
    new scala.collection.BuildFrom[TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R), TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R), TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]) =>
          new TableFor18(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12","arg13","arg14","arg15","arg16","arg17"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R), TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]) =>
          new TableFor18(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]): org.scalatest.prop.TableFor18[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R] =
        new TableFor18(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 19 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple19</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor19</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>, <span class="stQuotedString">"n"</span>, <span class="stQuotedString">"o"</span>, <span class="stQuotedString">"p"</span>, <span class="stQuotedString">"q"</span>, <span class="stQuotedString">"r"</span>, <span class="stQuotedString">"s"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 19 members in each tuple, the type you'll get back will be a <code>TableFor19</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor19</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor19</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s should equal (a * <span class="stLiteral">19</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor19</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple19</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)) => Boolean): TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S] = new TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]): TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S] = new TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor19</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor19</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor19</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor19</code> to return another <code>TableFor19</code>.
 *
 * @author Bill Venners
 */
object TableFor19 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor19</code> to return sequences of type <code>TableFor19</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]: scala.collection.BuildFrom[TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S), TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]] =
    new scala.collection.BuildFrom[TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S), TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S), TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]) =>
          new TableFor19(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12","arg13","arg14","arg15","arg16","arg17","arg18"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S), TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]) =>
          new TableFor19(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]): org.scalatest.prop.TableFor19[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S] =
        new TableFor19(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 20 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple20</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor20</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>, <span class="stQuotedString">"n"</span>, <span class="stQuotedString">"o"</span>, <span class="stQuotedString">"p"</span>, <span class="stQuotedString">"q"</span>, <span class="stQuotedString">"r"</span>, <span class="stQuotedString">"s"</span>, <span class="stQuotedString">"t"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 20 members in each tuple, the type you'll get back will be a <code>TableFor20</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor20</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor20</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t should equal (a * <span class="stLiteral">20</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor20</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple20</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)) => Boolean): TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T] = new TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]): TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T] = new TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor20</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor20</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor20</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor20</code> to return another <code>TableFor20</code>.
 *
 * @author Bill Venners
 */
object TableFor20 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor20</code> to return sequences of type <code>TableFor20</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]: scala.collection.BuildFrom[TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T), TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]] =
    new scala.collection.BuildFrom[TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T), TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T), TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]) =>
          new TableFor20(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12","arg13","arg14","arg15","arg16","arg17","arg18","arg19"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T), TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]) =>
          new TableFor20(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]): org.scalatest.prop.TableFor20[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T] =
        new TableFor20(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 21 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple21</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor21</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>, <span class="stQuotedString">"n"</span>, <span class="stQuotedString">"o"</span>, <span class="stQuotedString">"p"</span>, <span class="stQuotedString">"q"</span>, <span class="stQuotedString">"r"</span>, <span class="stQuotedString">"s"</span>, <span class="stQuotedString">"t"</span>, <span class="stQuotedString">"u"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 21 members in each tuple, the type you'll get back will be a <code>TableFor21</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor21</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor21</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t + u should equal (a * <span class="stLiteral">21</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor21</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple21</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)) => Boolean): TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U] = new TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]): TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U] = new TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor21</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor21</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor21</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor21</code> to return another <code>TableFor21</code>.
 *
 * @author Bill Venners
 */
object TableFor21 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor21</code> to return sequences of type <code>TableFor21</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]: scala.collection.BuildFrom[TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U), TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]] =
    new scala.collection.BuildFrom[TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U), TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U), TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]) =>
          new TableFor21(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12","arg13","arg14","arg15","arg16","arg17","arg18","arg19","arg20"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U), TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]) =>
          new TableFor21(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]): org.scalatest.prop.TableFor21[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U] =
        new TableFor21(from.heading, it.toSeq: _*)
        
    }
}

/**
 * A table with 22 columns.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * <p>
 * This table is a sequence of <code>Tuple22</code> objects, where each tuple represents one row of the table.
 * The first element of each tuple comprise the first column of the table, the second element of
 * each tuple comprise the second column, and so on.  This table also carries with it
 * a <em>heading</em> tuple that gives string names to the columns of the table.
 * </p>
 *
 * <p>
 * A handy way to create a <code>TableFor22</code> is via an <code>apply</code> factory method in the <code>Table</code>
 * singleton object provided by the <code>Tables</code> trait. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">val</span> examples =
 *   <span class="stType">Table</span>(
 *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>, <span class="stQuotedString">"k"</span>, <span class="stQuotedString">"l"</span>, <span class="stQuotedString">"m"</span>, <span class="stQuotedString">"n"</span>, <span class="stQuotedString">"o"</span>, <span class="stQuotedString">"p"</span>, <span class="stQuotedString">"q"</span>, <span class="stQuotedString">"r"</span>, <span class="stQuotedString">"s"</span>, <span class="stQuotedString">"t"</span>, <span class="stQuotedString">"u"</span>, <span class="stQuotedString">"v"</span>),
 *     (  <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>,   <span class="stLiteral">0</span>),
 *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>,   <span class="stLiteral">1</span>),
 *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">2</span>),
 *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">3</span>),
 *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">4</span>),
 *     (  <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">5</span>),
 *     (  <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">6</span>),
 *     (  <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">7</span>),
 *     (  <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">8</span>),
 *     (  <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>,   <span class="stLiteral">9</span>)
 *   )
 * </pre>
 *
 * <p>
 * Because you supplied 22 members in each tuple, the type you'll get back will be a <code>TableFor22</code>.
 * </p>
 *
 * <p>
 * The table provides an <code>apply</code> method that takes a function with a parameter list that matches
 * the types and arity of the tuples contained in this table. The <code>apply</code> method will invoke the
 * function with the members of each row tuple passed as arguments, in ascending order by index. (<em>I.e.</em>,
 * the zeroth tuple is checked first, then the tuple with index 1, then index 2, and so on until all the rows
 * have been checked (or until a failure occurs). The function represents a property of the code under test
 * that should succeed for every row of the table. If the function returns normally, that indicates the property
 * check succeeded for that row. If the function completes abruptly with an exception, that indicates the
 * property check failed and the <code>apply</code> method will complete abruptly with a
 * <code>TableDrivenPropertyCheckFailedException</code> that wraps the exception thrown by the supplied property function.
 * </p>
 *
 * <p>
 * The usual way you'd invoke the <code>apply</code> method that checks a property is via a <code>forAll</code> method
 * provided by trait <code>TableDrivenPropertyChecks</code>. The <code>forAll</code> method takes a <code>TableFor22</code> as its
 * first argument, then in a curried argument list takes the property check function. It invokes <code>apply</code> on
 * the <code>TableFor22</code>, passing in the property check function. Here's an example:
 * </p>
 *
 * <pre class="stHighlighted">
 * forAll (examples) { (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v) =>
 *   a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t + u + v should equal (a * <span class="stLiteral">22</span>)
 * }
 * </pre>
 *
 * <p>
 * Because <code>TableFor22</code> is a <code>Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]</code>, you can use it as a <code>Seq</code>. For example, here's how
 * you could get a sequence of <a href="../Outcome.html"><code>Outcome</code></a>s for each row of the table, indicating whether a property check succeeded or failed
 * on each row of the table:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stReserved">for</span> (row <- examples) <span class="stReserved">yield</span> {
 *   outcomeOf { row._1 should not equal (<span class="stLiteral">7</span>) }
 * }
 * </pre>
 *
 * <p>
 * Note: the <code>outcomeOf</code> method, contained in the <code>OutcomeOf</code> trait, will execute the supplied code (a by-name parameter) and
 * transform it to an <code>Outcome</code>. If no exception is thrown by the code, <code>outcomeOf</code> will result in a
 * <a href="../Succeeded$.html"><code>Succeeded</code></a>, indicating the "property check"
 * succeeded. If the supplied code completes abruptly in an exception that would normally cause a test to fail, <code>outcomeOf</code> will result in
 * in a <a href="../Failed.html"><code>Failed</code></a> instance containing that exception. For example, the previous for expression would give you:
 * </p>
 *
 * <pre class="stHighlighted">
 * <span class="stType">Vector</span>(<span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>,
 *     <span class="stType">Failed</span>(<span class="stType">org.scalatest.TestFailedException</span>: <span class="stLiteral">7</span> equaled <span class="stLiteral">7</span>), <span class="stType">Succeeded</span>, <span class="stType">Succeeded</span>)
 * </pre>
 *
 * <p>
 * This shows that all the property checks succeeded, except for the one at index 7.
 * </p>
 *
 * @param heading a tuple containing string names of the columns in this table
 * @param rows a variable length parameter list of <code>Tuple22</code>s containing the data of this table
 *
 * @author Bill Venners
 */

class TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](val heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*) extends IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)] with scala.collection.IndexedSeqOps[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V), scala.collection.IndexedSeq, scala.collection.IndexedSeq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]] {

  /**
   * Selects a row of data by its index.
   */
  def apply(idx: Int): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) = rows(idx)

  /**
   * The number of rows of data in the table. (This does not include the <code>heading</code> tuple)
   */
  def length: Int = rows.length

  override def filter(p: ((A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)) => Boolean): TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V] = new TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading, rows.filter(p): _*)

  def ++(others: Iterable[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]): TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V] = new TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading, (rows ++ others): _*)

  

  /**
   * Applies the passed property check function to each row of this <code>TableFor22</code>.
   *
   * <p>
   * If the property checks for all rows succeed (the property check function returns normally when passed
   * the data for each row), this <code>apply</code> method returns normally. If the property check function
   * completes abruptly with an exception for any row, this <code>apply</code> method wraps that exception
   * in a <code>TableDrivenPropertyCheckFailedException</code> and completes abruptly with that exception. Once
   * the property check function throws an exception for a row, this <code>apply</code> method will complete
   * abruptly immediately and subsequent rows will not be checked against the function.
   * </p>
   *
   * @param fun the property check function to apply to each row of this <code>TableFor22</code>
   */
  def apply[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forAll(heading, rows: _*)(fun)
  }

  def forEvery[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.forEvery(heading, rows: _*)(fun)
  }

  def exists[ASSERTION](fun: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) => ASSERTION)(implicit asserting: TableAsserting[ASSERTION], prettifier: Prettifier, pos: source.Position): asserting.Result = {
    asserting.exists(heading, rows: _*)(fun)
  }

  /**
   * A string representation of this object, which includes the heading strings as well as the rows of data.
   */
  override def toString: String = stringPrefix + "(" + heading.toString + ", " +  rows.mkString(", ") + ")"
}

/**
 * Companion object for class <code>TableFor22</code> that provides an implicit <code>canBuildFrom</code> method
 * that enables higher order functions defined on <code>TableFor22</code> to return another <code>TableFor22</code>.
 *
 * @author Bill Venners
 */
object TableFor22 {

  /**
   * Implicit method enabling higher order functions of <code>TableFor22</code> to return sequences of type <code>TableFor22</code>.
   */
  implicit def canBuildFrom[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]: scala.collection.BuildFrom[TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V), TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]] =
    new scala.collection.BuildFrom[TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V], (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V), TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]] {
      def apply(): Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V), TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]) =>
          new TableFor22(("arg0","arg1","arg2","arg3","arg4","arg5","arg6","arg7","arg8","arg9","arg10","arg11","arg12","arg13","arg14","arg15","arg16","arg17","arg18","arg19","arg20","arg21"))
        }
            def newBuilder(from: org.scalatest.prop.TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]): scala.collection.mutable.Builder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V), TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V]] =
        new ListBuffer mapResult { (buf: Seq[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]) =>
          new TableFor22(from.heading, buf: _*)
        }
      def fromSpecific(from: org.scalatest.prop.TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V])(it: IterableOnce[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]): org.scalatest.prop.TableFor22[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V] =
        new TableFor22(from.heading, it.toSeq: _*)
        
    }
}
