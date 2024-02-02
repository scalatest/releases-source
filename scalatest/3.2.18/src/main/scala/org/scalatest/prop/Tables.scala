/*
 * Copyright 2001-2024 Artima, Inc.
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

/**
 * Trait containing the <code>Table</code> object, which offers one <code>apply</code> factory method for
 * each <code>TableForN</code> class, <code>TableFor1</code> through <code>TableFor22</code>.
 *
 * <p>
 * For an introduction to using tables, see the documentation for trait
 * <a href="TableDrivenPropertyChecks.html">TableDrivenPropertyChecks</a>.
 * </p>
 *
 * @author Bill Venners
 */
trait Tables {

  /**
   * Object containing one <code>apply</code> factory method for each <code>TableFor&lt;n&gt;</code> class.
   *
   * <p>
   * For example, you could create a table of 5 rows and 2 colums like this:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">import</span> org.scalatest.prop.Tables._
   * <br/><span class="stReserved">val</span> examples =
   *   <span class="stType">Table</span>(
   *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>),
   *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">2</span>),
   *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">4</span>),
   *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">8</span>),
   *     (  <span class="stLiteral">8</span>,  <span class="stLiteral">16</span>),
   *     ( <span class="stLiteral">16</span>,  <span class="stLiteral">32</span>)
   *   )
   * </pre>
   *
   * <p>
   * Because you supplied 2 members in each tuple, the type you'll get back will be a <code>TableFor2</code>. If
   * you wanted a table with just one column you could write this:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> moreExamples =
   *   <span class="stType">Table</span>(
   *     <span class="stQuotedString">"powerOfTwo"</span>,
   *          <span class="stLiteral">1</span>,
   *          <span class="stLiteral">2</span>,
   *          <span class="stLiteral">4</span>,
   *          <span class="stLiteral">8</span>,
   *          <span class="stLiteral">16</span>
   *   )
   * </pre>
   *
   * <p>
   * Or if you wanted a table with 10 columns and 10 rows, you could do this:
   * </p>
   *
   * <pre class="stHighlighted">
   * <span class="stReserved">val</span> multiplicationTable =
   *   <span class="stType">Table</span>(
   *     (<span class="stQuotedString">"a"</span>, <span class="stQuotedString">"b"</span>, <span class="stQuotedString">"c"</span>, <span class="stQuotedString">"d"</span>, <span class="stQuotedString">"e"</span>, <span class="stQuotedString">"f"</span>, <span class="stQuotedString">"g"</span>, <span class="stQuotedString">"h"</span>, <span class="stQuotedString">"i"</span>, <span class="stQuotedString">"j"</span>),
   *     (  <span class="stLiteral">1</span>,   <span class="stLiteral">2</span>,   <span class="stLiteral">3</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">5</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">7</span>,   <span class="stLiteral">8</span>,   <span class="stLiteral">9</span>,  <span class="stLiteral">10</span>),
   *     (  <span class="stLiteral">2</span>,   <span class="stLiteral">4</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">8</span>,  <span class="stLiteral">10</span>,  <span class="stLiteral">12</span>,  <span class="stLiteral">14</span>,  <span class="stLiteral">16</span>,  <span class="stLiteral">18</span>,  <span class="stLiteral">20</span>),
   *     (  <span class="stLiteral">3</span>,   <span class="stLiteral">6</span>,   <span class="stLiteral">9</span>,  <span class="stLiteral">12</span>,  <span class="stLiteral">15</span>,  <span class="stLiteral">18</span>,  <span class="stLiteral">21</span>,  <span class="stLiteral">24</span>,  <span class="stLiteral">27</span>,  <span class="stLiteral">30</span>),
   *     (  <span class="stLiteral">4</span>,   <span class="stLiteral">8</span>,  <span class="stLiteral">12</span>,  <span class="stLiteral">16</span>,  <span class="stLiteral">20</span>,  <span class="stLiteral">24</span>,  <span class="stLiteral">28</span>,  <span class="stLiteral">32</span>,  <span class="stLiteral">36</span>,  <span class="stLiteral">40</span>),
   *     (  <span class="stLiteral">5</span>,  <span class="stLiteral">10</span>,  <span class="stLiteral">15</span>,  <span class="stLiteral">20</span>,  <span class="stLiteral">25</span>,  <span class="stLiteral">30</span>,  <span class="stLiteral">35</span>,  <span class="stLiteral">40</span>,  <span class="stLiteral">45</span>,  <span class="stLiteral">50</span>),
   *     (  <span class="stLiteral">6</span>,  <span class="stLiteral">12</span>,  <span class="stLiteral">18</span>,  <span class="stLiteral">24</span>,  <span class="stLiteral">30</span>,  <span class="stLiteral">36</span>,  <span class="stLiteral">42</span>,  <span class="stLiteral">48</span>,  <span class="stLiteral">54</span>,  <span class="stLiteral">60</span>),
   *     (  <span class="stLiteral">7</span>,  <span class="stLiteral">14</span>,  <span class="stLiteral">21</span>,  <span class="stLiteral">28</span>,  <span class="stLiteral">35</span>,  <span class="stLiteral">42</span>,  <span class="stLiteral">49</span>,  <span class="stLiteral">56</span>,  <span class="stLiteral">63</span>,  <span class="stLiteral">70</span>),
   *     (  <span class="stLiteral">8</span>,  <span class="stLiteral">16</span>,  <span class="stLiteral">24</span>,  <span class="stLiteral">32</span>,  <span class="stLiteral">40</span>,  <span class="stLiteral">48</span>,  <span class="stLiteral">56</span>,  <span class="stLiteral">64</span>,  <span class="stLiteral">72</span>,  <span class="stLiteral">80</span>),
   *     (  <span class="stLiteral">9</span>,  <span class="stLiteral">18</span>,  <span class="stLiteral">27</span>,  <span class="stLiteral">36</span>,  <span class="stLiteral">45</span>,  <span class="stLiteral">54</span>,  <span class="stLiteral">63</span>,  <span class="stLiteral">72</span>,  <span class="stLiteral">81</span>,  <span class="stLiteral">90</span>),
   *     ( <span class="stLiteral">10</span>,  <span class="stLiteral">20</span>,  <span class="stLiteral">30</span>,  <span class="stLiteral">40</span>,  <span class="stLiteral">50</span>,  <span class="stLiteral">60</span>,  <span class="stLiteral">70</span>,  <span class="stLiteral">80</span>,  <span class="stLiteral">90</span>, <span class="stLiteral">100</span>)
   *   )
   * </pre>
   *
   * <p>
   * The type of <code>multiplicationTable</code> would be <code>TableFor10</code>. You can pass the resulting
   * tables to a <code>forAll</code> method (defined in trait <code>PropertyChecks</code>), to perform a property
   * check with the data in the table. Or, because tables are sequences of tuples, you can treat them as a <code>Seq</code>.
   * </p>
   *
   * @author Bill Venners
   */
  object Table {

      /**
       * Factory method for creating a new <code>TableFor1</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple1</code>s containing the data of this table
       */
      def apply[A](heading: (String), rows: (A)*) =
        new TableFor1(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor2</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple2</code>s containing the data of this table
       */
      def apply[A, B](heading: (String, String), rows: (A, B)*) =
        new TableFor2(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor3</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple3</code>s containing the data of this table
       */
      def apply[A, B, C](heading: (String, String, String), rows: (A, B, C)*) =
        new TableFor3(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor4</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple4</code>s containing the data of this table
       */
      def apply[A, B, C, D](heading: (String, String, String, String), rows: (A, B, C, D)*) =
        new TableFor4(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor5</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple5</code>s containing the data of this table
       */
      def apply[A, B, C, D, E](heading: (String, String, String, String, String), rows: (A, B, C, D, E)*) =
        new TableFor5(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor6</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple6</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F](heading: (String, String, String, String, String, String), rows: (A, B, C, D, E, F)*) =
        new TableFor6(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor7</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple7</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G](heading: (String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G)*) =
        new TableFor7(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor8</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple8</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H](heading: (String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H)*) =
        new TableFor8(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor9</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple9</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I](heading: (String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I)*) =
        new TableFor9(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor10</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple10</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J](heading: (String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J)*) =
        new TableFor10(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor11</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple11</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K](heading: (String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K)*) =
        new TableFor11(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor12</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple12</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L](heading: (String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L)*) =
        new TableFor12(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor13</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple13</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M)*) =
        new TableFor13(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor14</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple14</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)*) =
        new TableFor14(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor15</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple15</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)*) =
        new TableFor15(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor16</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple16</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)*) =
        new TableFor16(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor17</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple17</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)*) =
        new TableFor17(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor18</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple18</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)*) =
        new TableFor18(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor19</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple19</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)*) =
        new TableFor19(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor20</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple20</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)*) =
        new TableFor20(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor21</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple21</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)*) =
        new TableFor21(heading, rows: _*)

      /**
       * Factory method for creating a new <code>TableFor22</code>.
       *
       * @param heading a tuple containing string names of the columns in this table
       * @param rows a variable length parameter list of <code>Tuple22</code>s containing the data of this table
       */
      def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](heading: (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String), rows: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)*) =
        new TableFor22(heading, rows: _*)
  }
}

/**
 * Companion object that facilitates the importing of <code>Tables</code> members as
 * an alternative to mixing it in. One use case is to import <code>Tables</code> members so you can use
 * them in the Scala interpreter:
 *
 * <pre>
 * Welcome to Scala version 2.8.0.final (Java HotSpot(TM) 64-Bit Server VM, Java 1.6.0_22).
 * Type in expressions to have them evaluated.
 * Type :help for more information.
 *
 * scala> import org.scalatest.prop.Tables._
 * import org.scalatest.prop.Tables._
 *
 * scala> val examples =
 *   |   Table(
 *   |     ("a", "b"),
 *   |     (  1,   2),
 *   |     (  3,   4)
 *   |   )
 * examples: org.scalatest.prop.TableFor2[Int,Int] = TableFor2((1,2), (3,4))
 * </pre>
 *
 * @author Bill Venners
 */
object Tables extends Tables
