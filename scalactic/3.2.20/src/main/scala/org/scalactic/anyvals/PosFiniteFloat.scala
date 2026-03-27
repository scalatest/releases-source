/*
 * Copyright 2001-2016 Artima, Inc.
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
package org.scalactic.anyvals

import scala.collection.immutable.NumericRange
import scala.language.implicitConversions
import scala.util.{Try, Success, Failure}
import org.scalactic.{Validation, Pass, Fail}
import org.scalactic.{Or, Good, Bad}

/**
 * An <code>AnyVal</code> for finite positive <code>Float</code>s.
 *
 * <p>
 * Note: a <code>PosFiniteFloat</code> may not equal 0.0. If you want positive number or 0, use [[PosZFiniteFloat]].
 * </p>
 *
 * <p>
 * Because <code>PosFiniteFloat</code> is an <code>AnyVal</code> it
 * will usually be as efficient as an <code>Float</code>, being
 * boxed only when an <code>Float</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>PosFiniteFloat.apply</code> factory method is implemented
 * in terms of a macro that checks literals for validity at
 * compile time. Calling <code>PosFiniteFloat.apply</code> with a
 * literal <code>Float</code> value will either produce a valid
 * <code>PosFiniteFloat</code> instance at run time or an error at
 * compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; PosFiniteFloat(42.1fF)
 * res0: org.scalactic.anyvals.PosFiniteFloat = PosFiniteFloat(42.1f)
 *
 * scala&gt; PosFiniteFloat(0.0fF)
 * &lt;console&gt;:14: error: PosFiniteFloat.apply can only be invoked on a finite positive (i > 0.0f && i != Float.PositiveInfinity) floating point literal, like PosFiniteFloat(42.1fF).
 *               PosFiniteFloat(42.1fF)
 *                       ^
 * </pre>
 *
 * <p>
 * <code>PosFiniteFloat.apply</code> cannot be used if the value being
 * passed is a variable (<em>i.e.</em>, not a literal), because
 * the macro cannot determine the validity of variables at
 * compile time (just literals). If you try to pass a variable
 * to <code>PosFiniteFloat.apply</code>, you'll get a compiler error
 * that suggests you use a different factor method,
 * <code>PosFiniteFloat.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = 42.1fF
 * x: Float = 42.1f
 *
 * scala&gt; PosFiniteFloat(x)
 * &lt;console&gt;:15: error: PosFiniteFloat.apply can only be invoked on a floating point literal, like PosFiniteFloat(42.1fF). Please use PosFiniteFloat.from instead.
 *               PosFiniteFloat(x)
 *                       ^
 * </pre>
 *
 * <p>
 * The <code>PosFiniteFloat.from</code> factory method will inspect
 * the value at runtime and return an
 * <code>Option[PosFiniteFloat]</code>. If the value is valid,
 * <code>PosFiniteFloat.from</code> will return a
 * <code>Some[PosFiniteFloat]</code>, else it will return a
 * <code>None</code>.  Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; PosFiniteFloat.from(x)
 * res3: Option[org.scalactic.anyvals.PosFiniteFloat] = Some(PosFiniteFloat(42.1f))
 *
 * scala&gt; val y = 0.0fF
 * y: Float = 0.0f
 *
 * scala&gt; PosFiniteFloat.from(y)
 * res4: Option[org.scalactic.anyvals.PosFiniteFloat] = None
 * </pre>
 *
 * <p>
 * The <code>PosFiniteFloat.apply</code> factory method is marked
 * implicit, so that you can pass literal <code>Float</code>s
 * into methods that require <code>PosFiniteFloat</code>, and get the
 * same compile-time checking you get when calling
 * <code>PosFiniteFloat.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: PosFiniteFloat): Float = Float.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.PosFiniteFloat)Float
 *
 * scala&gt; invert(42.1fF)
 * res5: Float = 3.4028235E38
 *
 * scala&gt; invert(Float.MaxValue)
 * res6: Float = 0.0
 *
 * scala&gt; invert(0.0fF)
 * &lt;console&gt;:15: error: PosFiniteFloat.apply can only be invoked on a finite positive (i > 0.0f && i != Float.PositiveInfinity) floating point literal, like PosFiniteFloat(42.1fF).
 *               invert(0.0F)
 *                      ^
 *
 * scala&gt; invert(0.0fF)
 * &lt;console&gt;:15: error: PosFiniteFloat.apply can only be invoked on a finite positive (i > 0.0f && i != Float.PositiveInfinity) floating point literal, like PosFiniteFloat(42.1fF).
 *               invert(0.0fF)
 *                       ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the <code>PosFiniteFloat</code>
 * companion object also defines implicit widening conversions
 * when no loss of precision will occur. This makes it convenient to use a
 * <code>PosFiniteFloat</code> where a <code>Float</code> or wider
 * type is needed. An example is the subtraction in the body of
 * the <code>invert</code> method defined above,
 * <code>Float.MaxValue - pos</code>. Although
 * <code>Float.MaxValue</code> is a <code>Float</code>, which
 * has no <code>-</code> method that takes a
 * <code>PosFiniteFloat</code> (the type of <code>pos</code>), you can
 * still subtract <code>pos</code>, because the
 * <code>PosFiniteFloat</code> will be implicitly widened to
 * <code>Float</code>.
 * </p>
 *
 * @param value The <code>Float</code> value underlying this <code>PosFiniteFloat</code>.
 */
final class PosFiniteFloat private (val value: Float) extends AnyVal {

  /**
   * A string representation of this <code>PosFiniteFloat</code>.
   */
  override def toString: String = s"PosFiniteFloat(${value.toString()}f)"

  /**
   * Converts this <code>PosFiniteFloat</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>PosFiniteFloat</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>PosFiniteFloat</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>PosFiniteFloat</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>PosFiniteFloat</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>PosFiniteFloat</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>PosFiniteFloat</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble

  /** Returns this value, unmodified. */
  def unary_+ : PosFiniteFloat = this
  /** Returns the negation of this value. */
  def unary_- : NegFiniteFloat = NegFiniteFloat.ensuringValid(-value)

  /**
   * Converts this <code>PosFiniteFloat</code>'s value to a string then concatenates the given string.
   */
  def +(x: String): String = s"${value.toString()}${x.toString()}"

  /** Returns `true` if this value is less than x, `false` otherwise. */
  def <(x: Byte): Boolean = value < x
  /** Returns `true` if this value is less than x, `false` otherwise. */
  def <(x: Short): Boolean = value < x
  /** Returns `true` if this value is less than x, `false` otherwise. */
  def <(x: Char): Boolean = value < x
  /** Returns `true` if this value is less than x, `false` otherwise. */
  def <(x: Int): Boolean = value < x
  /** Returns `true` if this value is less than x, `false` otherwise. */
  def <(x: Long): Boolean = value < x
  /** Returns `true` if this value is less than x, `false` otherwise. */
  def <(x: Float): Boolean = value < x
  /** Returns `true` if this value is less than x, `false` otherwise. */
  def <(x: Double): Boolean = value < x

  /** Returns `true` if this value is less than or equal to x, `false` otherwise. */
  def <=(x: Byte): Boolean = value <= x
  /** Returns `true` if this value is less than or equal to x, `false` otherwise. */
  def <=(x: Short): Boolean = value <= x
  /** Returns `true` if this value is less than or equal to x, `false` otherwise. */
  def <=(x: Char): Boolean = value <= x
  /** Returns `true` if this value is less than or equal to x, `false` otherwise. */
  def <=(x: Int): Boolean = value <= x
  /** Returns `true` if this value is less than or equal to x, `false` otherwise. */
  def <=(x: Long): Boolean = value <= x
  /** Returns `true` if this value is less than or equal to x, `false` otherwise. */
  def <=(x: Float): Boolean = value <= x
  /** Returns `true` if this value is less than or equal to x, `false` otherwise. */
  def <=(x: Double): Boolean = value <= x

  /** Returns `true` if this value is greater than x, `false` otherwise. */
  def >(x: Byte): Boolean = value > x
  /** Returns `true` if this value is greater than x, `false` otherwise. */
  def >(x: Short): Boolean = value > x
  /** Returns `true` if this value is greater than x, `false` otherwise. */
  def >(x: Char): Boolean = value > x
  /** Returns `true` if this value is greater than x, `false` otherwise. */
  def >(x: Int): Boolean = value > x
  /** Returns `true` if this value is greater than x, `false` otherwise. */
  def >(x: Long): Boolean = value > x
  /** Returns `true` if this value is greater than x, `false` otherwise. */
  def >(x: Float): Boolean = value > x
  /** Returns `true` if this value is greater than x, `false` otherwise. */
  def >(x: Double): Boolean = value > x

  /** Returns `true` if this value is greater than or equal to x, `false` otherwise. */
  def >=(x: Byte): Boolean = value >= x
  /** Returns `true` if this value is greater than or equal to x, `false` otherwise. */
  def >=(x: Short): Boolean = value >= x
  /** Returns `true` if this value is greater than or equal to x, `false` otherwise. */
  def >=(x: Char): Boolean = value >= x
  /** Returns `true` if this value is greater than or equal to x, `false` otherwise. */
  def >=(x: Int): Boolean = value >= x
  /** Returns `true` if this value is greater than or equal to x, `false` otherwise. */
  def >=(x: Long): Boolean = value >= x
  /** Returns `true` if this value is greater than or equal to x, `false` otherwise. */
  def >=(x: Float): Boolean = value >= x
  /** Returns `true` if this value is greater than or equal to x, `false` otherwise. */
  def >=(x: Double): Boolean = value >= x

  /** Returns the sum of this value and `x`. */
  def +(x: Byte): Float = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Short): Float = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Char): Float = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Int): Float = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Long): Float = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Float): Float = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Double): Double = value + x

  /** Returns the difference of this value and `x`. */
  def -(x: Byte): Float = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Short): Float = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Char): Float = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Int): Float = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Long): Float = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Float): Float = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Double): Double = value - x

  /** Returns the product of this value and `x`. */
  def *(x: Byte): Float = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Short): Float = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Char): Float = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Int): Float = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Long): Float = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Float): Float = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Double): Double = value * x

  /** Returns the quotient of this value and `x`. */
  def /(x: Byte): Float = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Short): Float = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Char): Float = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Int): Float = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Long): Float = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Float): Float = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Double): Double = value / x

  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Byte): Float = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Short): Float = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Char): Float = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Int): Float = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Long): Float = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Float): Float = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Double): Double = value % x

  // Stuff from RichFloat

  /**
   * Returns <code>this</code> if <code>this &gt; that</code> or <code>that</code> otherwise.
   */
  def max(that: PosFiniteFloat): PosFiniteFloat = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: PosFiniteFloat): PosFiniteFloat = if (math.min(value, that.value) == value) this else that

  /**
   * Indicates whether this `PosFiniteFloat` has a value that is a whole number: it is finite and it has no fraction part.
   */
  def isWhole = {
    val longValue = value.toLong
    longValue.toFloat == value || longValue == Long.MaxValue && value < Float.PositiveInfinity || longValue == Long.MinValue && value > Float.NegativeInfinity
  }

  /** Converts an angle measured in degrees to an approximately equivalent
   * angle measured in radians.
   *
   * @return the measurement of the angle x in radians.
   */
  def toRadians: Float = math.toRadians(value.toDouble).toFloat

  /** Converts an angle measured in radians to an approximately equivalent
   * angle measured in degrees.
   * @return the measurement of the angle x in degrees.
   */
  def toDegrees: Float = math.toDegrees(value.toDouble).toFloat

  /**
   * Applies the passed <code>Float =&gt; Float</code> function to the underlying <code>Float</code>
   * value, and if the result is positive, returns the result wrapped in a <code>PosFiniteFloat</code>,
   * else throws <code>AssertionError</code>.
   *
   * <p>
   * This method will inspect the result of applying the given function to this
   * <code>PosFiniteFloat</code>'s underlying <code>Float</code> value and if the result
   * is finite positive, it will return a <code>PosFiniteFloat</code> representing that value.
   * Otherwise, the <code>Float</code> value returned by the given function is
   * not finite positive, so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Float</code> is finite positive.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>PosFiniteFloat</code>'s
   * value will not produce invalid value.
   * Instead of producing such invalid values, this method will throw <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Float =&gt; Float</code> function to apply to this <code>PosFiniteFloat</code>'s
   *     underlying <code>Float</code> value.
   * @return the result of applying this <code>PosFiniteFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function, wrapped in a <code>PosFiniteFloat</code> if it is finite positive (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>PosFiniteFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function is not finite positive.
   */
  def ensuringValid(f: Float => Float): PosFiniteFloat = {
    val candidateResult: Float = f(value)
    if (PosFiniteFloatMacro.isValid(candidateResult)) new PosFiniteFloat(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid PosFiniteFloat")
  }


  /**
   * Rounds this `PosFiniteFloat` value to the nearest whole number value that can be expressed as an `PosZInt`, returning the result as a `PosZInt`.
   */
  def round: PosZInt = PosZInt.ensuringValid(math.round(value))

  /**
   * Returns the smallest (closest to 0) `PosFiniteFloat` that is greater than or equal to this `PosFiniteFloat`
   * and represents a mathematical integer.
   */
  def ceil: PosFiniteFloat = PosFiniteFloat.ensuringValid(math.ceil(value).toFloat)

  /**
   * Returns the greatest (closest to infinity) `PosZFiniteFloat` that is less than or equal to
   * this `PosZFiniteFloat` and represents a mathematical integer.
   */
  def floor: PosZFiniteFloat = PosZFiniteFloat.ensuringValid(math.floor(value).toFloat)

}

/**
 * The companion object for <code>PosFiniteFloat</code> that offers
 * factory methods that produce <code>PosFiniteFloat</code>s,
 * implicit widening conversions from <code>PosFiniteFloat</code> to
 * other numeric types, and maximum and minimum constant values
 * for <code>PosFiniteFloat</code>.
 */
object PosFiniteFloat {

  /**
   * The largest value representable as a finite positive <code>Float</code>,
   * which is <code>PosFiniteFloat(3.4028235E38)</code>.
   */
  final val MaxValue: PosFiniteFloat = PosFiniteFloat.ensuringValid(Float.MaxValue)

  /**
   * The smallest value representable as a finite positive
   * <code>Float</code>, which is <code>PosFiniteFloat(1.4E-45)</code>.
   */
  final val MinValue: PosFiniteFloat = PosFiniteFloat.ensuringValid(Float.MinPositiveValue) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[PosFiniteFloat]</code> given a
   * <code>Float</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite positive <code>Float</code>, it will return a <code>PosFiniteFloat</code>
   * representing that value wrapped in a <code>Some</code>. Otherwise, the passed <code>Float</code>
   * value is not finite positive, so this method will return <code>None</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Float</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Float</code> values at run time.
   * </p>
   *
   * @param value the <code>Float</code> to inspect, and if finite positive, return
   *     wrapped in a <code>Some[PosFiniteFloat]</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>Some[PosFiniteFloat]</code>, if it is finite positive, else
   *     <code>None</code>.
   */
  def from(value: Float): Option[PosFiniteFloat] =
    if (PosFiniteFloatMacro.isValid(value)) Some(new PosFiniteFloat(value)) else None

  /**
   * A factory/assertion method that produces a <code>PosFiniteFloat</code> given a
   * valid <code>Float</code> value, or throws <code>AssertionError</code>,
   * if given an invalid <code>Float</code> value.
   *
   * Note: you should use this method only when you are convinced that it will
   * always succeed, i.e., never throw an exception. It is good practice to
   * add a comment near the invocation of this method indicating ''why'' you think
   * it will always succeed to document your reasoning. If you are not sure an
   * `ensuringValid` call will always succeed, you should use one of the other
   * factory or validation methods provided on this object instead: `isValid`,
   * `tryingValid`, `passOrElse`, `goodOrElse`, or `rightOrElse`.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite positive <code>Float</code>, it will return a <code>PosFiniteFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not finite positive, so
   * this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Float</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Float</code> values at run time.
   * It differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises a <code>Float</code> is positive.
   * </p>
   *
   * @param value the <code>Float</code> to inspect, and if finite positive, return
   *     wrapped in a <code>PosFiniteFloat</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>PosFiniteFloat</code>, if it is finite positive, else
   *     throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not finite positive
   */
  def ensuringValid(value: Float): PosFiniteFloat =
    if (PosFiniteFloatMacro.isValid(value)) new PosFiniteFloat(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid PosFiniteFloat")
    }

  /**
   * A factory/validation method that produces a <code>PosFiniteFloat</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite positive <code>Float</code>, it will return a <code>PosFiniteFloat</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Float</code> value is not finite positive, so this
   * method will return an <code>AssertionError</code>, wrapped in a <code>Failure</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Float</code> literals at compile time, whereas this method inspects
   * <code>Float</code> values at run time.
   * </p>
   *
   * @param value the <code>Float</code> to inspect, and if finite positive, return
   *     wrapped in a <code>Success(PosFiniteFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Success(PosFiniteFloat)</code>, if it is finite positive, else a <code>Failure(AssertionError)</code>.
   */
  def tryingValid(value: Float): Try[PosFiniteFloat] =
    if (PosFiniteFloatMacro.isValid(value))
      Success(new PosFiniteFloat(value))
    else
      Failure(new AssertionError(s"${value.toString()} was not a valid PosFiniteFloat"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Float</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite positive <code>Float</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Float</code> value is finite positive, so this
   * method will return a result of type <code>E</code> obtained by passing
   * the invalid <code>Float</code> value to the given function <code>f</code>,
   * wrapped in a `Fail`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Float</code> literals at compile time, whereas this method inspects
   * <code>Float</code> values at run time.
   * </p>
   *
   * @param value the `Float` to validate that it is finite positive.
   * @return a `Pass` if the specified `Float` value is finite positive,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Float` to the given function `f`.
   */
  def passOrElse[E](value: Float)(f: Float => E): Validation[E] =
    if (PosFiniteFloatMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>PosFiniteFloat</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Float</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite positive <code>Float</code>, it will return a <code>PosFiniteFloat</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Float</code> value is not finite positive, so this
   * method will return a result of type <code>B</code> obtained by passing
   * the invalid <code>Float</code> value to the given function <code>f</code>,
   * wrapped in a `Bad`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Float</code> literals at compile time, whereas this method inspects
   * <code>Float</code> values at run time.
   * </p>
   *
   * @param value the <code>Float</code> to inspect, and if finite positive, return
   *     wrapped in a <code>Good(PosFiniteFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Good(PosFiniteFloat)</code>, if it is finite positive, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Float)(f: Float => B): PosFiniteFloat Or B =
    if (PosFiniteFloatMacro.isValid(value)) Good(PosFiniteFloat.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>PosFiniteFloat</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a finite positive <code>Int</code>, it will return a <code>PosFiniteFloat</code>
   * representing that value, wrapped in a <code>Right</code>.
   * Otherwise, the passed <code>Int</code> value is not finite positive, so this
   * method will return a result of type <code>L</code> obtained by passing
   * the invalid <code>Int</code> value to the given function <code>f</code>,
   * wrapped in a `Left`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Int</code> literals at compile time, whereas this method inspects
   * <code>Int</code> values at run time.
   * </p>
   *
   * @param value the <code>Int</code> to inspect, and if finite positive, return
   *     wrapped in a <code>Right(PosFiniteFloat)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Right(PosFiniteFloat)</code>, if it is finite positive, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Float)(f: Float => L): Either[L, PosFiniteFloat] =
    if (PosFiniteFloatMacro.isValid(value)) Right(PosFiniteFloat.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Float</code> value is finite positive.
   *
   * @param value the <code>Float</code> to inspect, and if finite positive, return true.
   * @return true if the specified <code>Float</code> is finite positive, else false.
   */
  def isValid(value: Float): Boolean = PosFiniteFloatMacro.isValid(value)

  /**
   * A factory method that produces a <code>PosFiniteFloat</code> given a
   * <code>Float</code> value and a default <code>PosFiniteFloat</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite positive <code>Float</code>, it will return a <code>PosFiniteFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not finite positive, so this
   * method will return the passed <code>default</code> value.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Float</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Float</code> values at run time.
   * </p>
   *
   * @param value the <code>Float</code> to inspect, and if finite positive, return.
   * @param default the <code>PosFiniteFloat</code> to return if the passed
   *     <code>Float</code> value is not finite positive.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>PosFiniteFloat</code>, if it is finite positive, else the
   *     <code>default</code> <code>PosFiniteFloat</code> value.
   */
  def fromOrElse(value: Float, default: => PosFiniteFloat): PosFiniteFloat =
    if (PosFiniteFloatMacro.isValid(value)) new PosFiniteFloat(value) else default

  import language.experimental.macros
  import scala.language.implicitConversions

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>PosFiniteFloat</code> if passed a valid <code>Float</code>
   * literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the
   * specified <code>Float</code> expression at compile time. If
   * the expression is a finite positive <code>Float</code> literal,
   * it will return a <code>PosFiniteFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> expression is either a literal
   * that is not finite positive, or is not a literal, so this method
   * will give a compiler error.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>from</code>
   * factory method in that this method is implemented via a
   * macro that inspects <code>Float</code> literals at compile
   * time, whereas <code>from</code> inspects <code>Float</code>
   * values at run time.
   * </p>
   *
   * @param value the <code>Float</code> literal expression to
   *     inspect at compile time, and if finite positive, to return
   *     wrapped in a <code>PosFiniteFloat</code> at run time.
   * @return the specified, valid <code>Float</code> literal
   *     value wrapped in a <code>PosFiniteFloat</code>. (If the
   *     specified expression is not a valid <code>Float</code>
   *     literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Float): PosFiniteFloat = macro PosFiniteFloatMacro.apply

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to
   * <code>Float</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>PosFiniteFloat</code>
   */
 implicit def widenToFloat(pos: PosFiniteFloat): Float = pos.value

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to
   * <code>Double</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>PosFiniteFloat</code>, widened to
   *     <code>Double</code>.
   */
  implicit def widenToDouble(pos: PosFiniteFloat): Double = pos.value


  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>PosFloat</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosFloat</code>.
   */
  implicit def widenToPosFloat(pos: PosFiniteFloat): PosFloat = PosFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>PosDouble</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosDouble</code>.
   */
  implicit def widenToPosDouble(pos: PosFiniteFloat): PosDouble = PosDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>PosZFloat</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosZFloat</code>.
   */
  implicit def widenToPosZFloat(pos: PosFiniteFloat): PosZFloat = PosZFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>PosZDouble</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosZDouble</code>.
   */
  implicit def widenToPosZDouble(pos: PosFiniteFloat): PosZDouble = PosZDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>NonZeroFloat</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Float</code> and wrapped in a <code>NonZeroFloat</code>.
   */
  implicit def widenToNonZeroFloat(pos: PosFiniteFloat): NonZeroFloat = NonZeroFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>NonZeroDouble</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NonZeroDouble</code>.
   */
  implicit def widenToNonZeroDouble(pos: PosFiniteFloat): NonZeroDouble = NonZeroDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>PosFiniteDouble</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosFiniteDouble</code>.
   */
  implicit def widenToPosFiniteDouble(pos: PosFiniteFloat): PosFiniteDouble = PosFiniteDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>PosZFiniteFloat</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosZFiniteFloat</code>.
   */
  implicit def widenToPosZFiniteFloat(pos: PosFiniteFloat): PosZFiniteFloat = PosZFiniteFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>PosZFiniteDouble</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosZFiniteDouble</code>.
   */
  implicit def widenToPosZFiniteDouble(pos: PosFiniteFloat): PosZFiniteDouble = PosZFiniteDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>FiniteFloat</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Float</code> and wrapped in a <code>FiniteFloat</code>.
   */
  implicit def widenToFiniteFloat(pos: PosFiniteFloat): FiniteFloat = FiniteFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosFiniteFloat</code> to <code>FiniteDouble</code>.
   *
   * @param pos the <code>PosFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosFiniteFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>FiniteDouble</code>.
   */
  implicit def widenToFiniteDouble(pos: PosFiniteFloat): FiniteDouble = FiniteDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[PosFiniteFloat] =
    new Ordering[PosFiniteFloat] {
      def compare(x: PosFiniteFloat, y: PosFiniteFloat): Int = x.toFloat.compare(y)
    }


  /**
   * The smallest positive value greater than 0.0d representable as a <code>PosFloat</code>, which is PosFloat(1.4E-45).
   */
  final val MinPositiveValue: PosFloat = PosFloat.ensuringValid(Float.MinPositiveValue)

}
