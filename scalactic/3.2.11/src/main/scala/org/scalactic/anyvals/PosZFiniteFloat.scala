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
 * An <code>AnyVal</code> for finite non-negative <code>Float</code>s.
 *
 * <p>
 * 
 * </p>
 *
 * <p>
 * Because <code>PosZFiniteFloat</code> is an <code>AnyVal</code> it
 * will usually be as efficient as an <code>Float</code>, being
 * boxed only when an <code>Float</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>PosZFiniteFloat.apply</code> factory method is implemented
 * in terms of a macro that checks literals for validity at
 * compile time. Calling <code>PosZFiniteFloat.apply</code> with a
 * literal <code>Float</code> value will either produce a valid
 * <code>PosZFiniteFloat</code> instance at run time or an error at
 * compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; PosZFiniteFloat(1.1fF)
 * res0: org.scalactic.anyvals.PosZFiniteFloat = PosZFiniteFloat(1.1f)
 *
 * scala&gt; PosZFiniteFloat(-1.1fF)
 * &lt;console&gt;:14: error: PosZFiniteFloat.apply can only be invoked on a finite non-negative (i >= 0.0f && i != Float.PositiveInfinity) floating point literal, like PosZFiniteFloat(1.1fF).
 *               PosZFiniteFloat(1.1fF)
 *                       ^
 * </pre>
 *
 * <p>
 * <code>PosZFiniteFloat.apply</code> cannot be used if the value being
 * passed is a variable (<em>i.e.</em>, not a literal), because
 * the macro cannot determine the validity of variables at
 * compile time (just literals). If you try to pass a variable
 * to <code>PosZFiniteFloat.apply</code>, you'll get a compiler error
 * that suggests you use a different factor method,
 * <code>PosZFiniteFloat.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = 1.1fF
 * x: Float = 1.1f
 *
 * scala&gt; PosZFiniteFloat(x)
 * &lt;console&gt;:15: error: PosZFiniteFloat.apply can only be invoked on a floating point literal, like PosZFiniteFloat(1.1fF). Please use PosZFiniteFloat.from instead.
 *               PosZFiniteFloat(x)
 *                       ^
 * </pre>
 *
 * <p>
 * The <code>PosZFiniteFloat.from</code> factory method will inspect
 * the value at runtime and return an
 * <code>Option[PosZFiniteFloat]</code>. If the value is valid,
 * <code>PosZFiniteFloat.from</code> will return a
 * <code>Some[PosZFiniteFloat]</code>, else it will return a
 * <code>None</code>.  Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; PosZFiniteFloat.from(x)
 * res3: Option[org.scalactic.anyvals.PosZFiniteFloat] = Some(PosZFiniteFloat(1.1f))
 *
 * scala&gt; val y = -1.1fF
 * y: Float = -1.1f
 *
 * scala&gt; PosZFiniteFloat.from(y)
 * res4: Option[org.scalactic.anyvals.PosZFiniteFloat] = None
 * </pre>
 *
 * <p>
 * The <code>PosZFiniteFloat.apply</code> factory method is marked
 * implicit, so that you can pass literal <code>Float</code>s
 * into methods that require <code>PosZFiniteFloat</code>, and get the
 * same compile-time checking you get when calling
 * <code>PosZFiniteFloat.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: PosZFiniteFloat): Float = Float.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.PosZFiniteFloat)Float
 *
 * scala&gt; invert(1.1fF)
 * res5: Float = 3.4028235E38
 *
 * scala&gt; invert(Float.MaxValue)
 * res6: Float = 0.0
 *
 * scala&gt; invert(-1.1fF)
 * &lt;console&gt;:15: error: PosZFiniteFloat.apply can only be invoked on a finite non-negative (i >= 0.0f && i != Float.PositiveInfinity) floating point literal, like PosZFiniteFloat(1.1fF).
 *               invert(0.0F)
 *                      ^
 *
 * scala&gt; invert(-1.1fF)
 * &lt;console&gt;:15: error: PosZFiniteFloat.apply can only be invoked on a finite non-negative (i >= 0.0f && i != Float.PositiveInfinity) floating point literal, like PosZFiniteFloat(1.1fF).
 *               invert(-1.1fF)
 *                       ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the <code>PosZFiniteFloat</code>
 * companion object also defines implicit widening conversions
 * when no loss of precision will occur. This makes it convenient to use a
 * <code>PosZFiniteFloat</code> where a <code>Float</code> or wider
 * type is needed. An example is the subtraction in the body of
 * the <code>invert</code> method defined above,
 * <code>Float.MaxValue - pos</code>. Although
 * <code>Float.MaxValue</code> is a <code>Float</code>, which
 * has no <code>-</code> method that takes a
 * <code>PosZFiniteFloat</code> (the type of <code>pos</code>), you can
 * still subtract <code>pos</code>, because the
 * <code>PosZFiniteFloat</code> will be implicitly widened to
 * <code>Float</code>.
 * </p>
 *
 * @param value The <code>Float</code> value underlying this <code>PosZFiniteFloat</code>.
 */
final class PosZFiniteFloat private (val value: Float) extends AnyVal {

  /**
   * A string representation of this <code>PosZFiniteFloat</code>.
   */
  override def toString: String = s"PosZFiniteFloat(${value.toString()}f)"

  /**
   * Converts this <code>PosZFiniteFloat</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>PosZFiniteFloat</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>PosZFiniteFloat</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>PosZFiniteFloat</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>PosZFiniteFloat</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>PosZFiniteFloat</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>PosZFiniteFloat</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble

  /** Returns this value, unmodified. */
  def unary_+ : PosZFiniteFloat = this
  /** Returns the negation of this value. */
  def unary_- : NegZFiniteFloat = NegZFiniteFloat.ensuringValid(-value)

  /**
   * Converts this <code>PosZFiniteFloat</code>'s value to a string then concatenates the given string.
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
  def max(that: PosZFiniteFloat): PosZFiniteFloat = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: PosZFiniteFloat): PosZFiniteFloat = if (math.min(value, that.value) == value) this else that

  /**
   * Indicates whether this `PosZFiniteFloat` has a value that is a whole number: it is finite and it has no fraction part.
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
   * value, and if the result is positive, returns the result wrapped in a <code>PosZFiniteFloat</code>,
   * else throws <code>AssertionError</code>.
   *
   * <p>
   * This method will inspect the result of applying the given function to this
   * <code>PosZFiniteFloat</code>'s underlying <code>Float</code> value and if the result
   * is finite non-negative, it will return a <code>PosZFiniteFloat</code> representing that value.
   * Otherwise, the <code>Float</code> value returned by the given function is
   * not finite non-negative, so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Float</code> is finite non-negative.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>PosZFiniteFloat</code>'s
   * value will not produce invalid value.
   * Instead of producing such invalid values, this method will throw <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Float =&gt; Float</code> function to apply to this <code>PosZFiniteFloat</code>'s
   *     underlying <code>Float</code> value.
   * @return the result of applying this <code>PosZFiniteFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function, wrapped in a <code>PosZFiniteFloat</code> if it is finite non-negative (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>PosZFiniteFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function is not finite non-negative.
   */
  def ensuringValid(f: Float => Float): PosZFiniteFloat = {
    val candidateResult: Float = f(value)
    if (PosZFiniteFloatMacro.isValid(candidateResult)) new PosZFiniteFloat(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid PosZFiniteFloat")
  }


  /**
   * Rounds this `PosZFiniteFloat` value to the nearest whole number value that can be expressed as an `PosZInt`, returning the result as a `PosZInt`.
   */
  def round: PosZInt = PosZInt.ensuringValid(math.round(value))

  /**
   * Returns the smallest (closest to 0) `PosZFiniteFloat` that is greater than or equal to this `PosZFiniteFloat`
   * and represents a mathematical integer.
   */
  def ceil: PosZFiniteFloat = PosZFiniteFloat.ensuringValid(math.ceil(value).toFloat)

  /**
   * Returns the greatest (closest to infinity) `PosZFiniteFloat` that is less than or equal to
   * this `PosZFiniteFloat` and represents a mathematical integer.
   */
  def floor: PosZFiniteFloat = PosZFiniteFloat.ensuringValid(math.floor(value).toFloat)

}

/**
 * The companion object for <code>PosZFiniteFloat</code> that offers
 * factory methods that produce <code>PosZFiniteFloat</code>s,
 * implicit widening conversions from <code>PosZFiniteFloat</code> to
 * other numeric types, and maximum and minimum constant values
 * for <code>PosZFiniteFloat</code>.
 */
object PosZFiniteFloat {

  /**
   * The largest value representable as a finite non-negative <code>Float</code>,
   * which is <code>PosZFiniteFloat(3.4028235E38)</code>.
   */
  final val MaxValue: PosZFiniteFloat = PosZFiniteFloat.ensuringValid(Float.MaxValue)

  /**
   * The smallest value representable as a finite non-negative
   * <code>Float</code>, which is <code>PosZFiniteFloat(0.0f)</code>.
   */
  final val MinValue: PosZFiniteFloat = PosZFiniteFloat.ensuringValid(0.0f) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[PosZFiniteFloat]</code> given a
   * <code>Float</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite non-negative <code>Float</code>, it will return a <code>PosZFiniteFloat</code>
   * representing that value wrapped in a <code>Some</code>. Otherwise, the passed <code>Float</code>
   * value is not finite non-negative, so this method will return <code>None</code>.
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
   * @param value the <code>Float</code> to inspect, and if finite non-negative, return
   *     wrapped in a <code>Some[PosZFiniteFloat]</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>Some[PosZFiniteFloat]</code>, if it is finite non-negative, else
   *     <code>None</code>.
   */
  def from(value: Float): Option[PosZFiniteFloat] =
    if (PosZFiniteFloatMacro.isValid(value)) Some(new PosZFiniteFloat(value)) else None

  /**
   * A factory/assertion method that produces a <code>PosZFiniteFloat</code> given a
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
   * it is a finite non-negative <code>Float</code>, it will return a <code>PosZFiniteFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not finite non-negative, so
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
   * @param value the <code>Float</code> to inspect, and if finite non-negative, return
   *     wrapped in a <code>PosZFiniteFloat</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>PosZFiniteFloat</code>, if it is finite non-negative, else
   *     throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not finite non-negative
   */
  def ensuringValid(value: Float): PosZFiniteFloat =
    if (PosZFiniteFloatMacro.isValid(value)) new PosZFiniteFloat(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid PosZFiniteFloat")
    }

  /**
   * A factory/validation method that produces a <code>PosZFiniteFloat</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite non-negative <code>Float</code>, it will return a <code>PosZFiniteFloat</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Float</code> value is not finite non-negative, so this
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
   * @param value the <code>Float</code> to inspect, and if finite non-negative, return
   *     wrapped in a <code>Success(PosZFiniteFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Success(PosZFiniteFloat)</code>, if it is finite non-negative, else a <code>Failure(AssertionError)</code>.
   */
  def tryingValid(value: Float): Try[PosZFiniteFloat] =
    if (PosZFiniteFloatMacro.isValid(value))
      Success(new PosZFiniteFloat(value))
    else
      Failure(new AssertionError(s"${value.toString()} was not a valid PosZFiniteFloat"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Float</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite non-negative <code>Float</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Float</code> value is finite non-negative, so this
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
   * @param value the `Float` to validate that it is finite non-negative.
   * @return a `Pass` if the specified `Float` value is finite non-negative,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Float` to the given function `f`.
   */
  def passOrElse[E](value: Float)(f: Float => E): Validation[E] =
    if (PosZFiniteFloatMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>PosZFiniteFloat</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Float</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite non-negative <code>Float</code>, it will return a <code>PosZFiniteFloat</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Float</code> value is not finite non-negative, so this
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
   * @param value the <code>Float</code> to inspect, and if finite non-negative, return
   *     wrapped in a <code>Good(PosZFiniteFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Good(PosZFiniteFloat)</code>, if it is finite non-negative, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Float)(f: Float => B): PosZFiniteFloat Or B =
    if (PosZFiniteFloatMacro.isValid(value)) Good(PosZFiniteFloat.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>PosZFiniteFloat</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a finite non-negative <code>Int</code>, it will return a <code>PosZFiniteFloat</code>
   * representing that value, wrapped in a <code>Right</code>.
   * Otherwise, the passed <code>Int</code> value is not finite non-negative, so this
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
   * @param value the <code>Int</code> to inspect, and if finite non-negative, return
   *     wrapped in a <code>Right(PosZFiniteFloat)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Right(PosZFiniteFloat)</code>, if it is finite non-negative, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Float)(f: Float => L): Either[L, PosZFiniteFloat] =
    if (PosZFiniteFloatMacro.isValid(value)) Right(PosZFiniteFloat.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Float</code> value is finite non-negative.
   *
   * @param value the <code>Float</code> to inspect, and if finite non-negative, return true.
   * @return true if the specified <code>Float</code> is finite non-negative, else false.
   */
  def isValid(value: Float): Boolean = PosZFiniteFloatMacro.isValid(value)

  /**
   * A factory method that produces a <code>PosZFiniteFloat</code> given a
   * <code>Float</code> value and a default <code>PosZFiniteFloat</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a finite non-negative <code>Float</code>, it will return a <code>PosZFiniteFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not finite non-negative, so this
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
   * @param value the <code>Float</code> to inspect, and if finite non-negative, return.
   * @param default the <code>PosZFiniteFloat</code> to return if the passed
   *     <code>Float</code> value is not finite non-negative.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>PosZFiniteFloat</code>, if it is finite non-negative, else the
   *     <code>default</code> <code>PosZFiniteFloat</code> value.
   */
  def fromOrElse(value: Float, default: => PosZFiniteFloat): PosZFiniteFloat =
    if (PosZFiniteFloatMacro.isValid(value)) new PosZFiniteFloat(value) else default

  import language.experimental.macros
  import scala.language.implicitConversions

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>PosZFiniteFloat</code> if passed a valid <code>Float</code>
   * literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the
   * specified <code>Float</code> expression at compile time. If
   * the expression is a finite non-negative <code>Float</code> literal,
   * it will return a <code>PosZFiniteFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> expression is either a literal
   * that is not finite non-negative, or is not a literal, so this method
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
   *     inspect at compile time, and if finite non-negative, to return
   *     wrapped in a <code>PosZFiniteFloat</code> at run time.
   * @return the specified, valid <code>Float</code> literal
   *     value wrapped in a <code>PosZFiniteFloat</code>. (If the
   *     specified expression is not a valid <code>Float</code>
   *     literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Float): PosZFiniteFloat = macro PosZFiniteFloatMacro.apply

  /**
   * Implicit widening conversion from <code>PosZFiniteFloat</code> to
   * <code>Float</code>.
   *
   * @param pos the <code>PosZFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>PosZFiniteFloat</code>
   */
 implicit def widenToFloat(pos: PosZFiniteFloat): Float = pos.value

  /**
   * Implicit widening conversion from <code>PosZFiniteFloat</code> to
   * <code>Double</code>.
   *
   * @param pos the <code>PosZFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>PosZFiniteFloat</code>, widened to
   *     <code>Double</code>.
   */
  implicit def widenToDouble(pos: PosZFiniteFloat): Double = pos.value


  /**
   * Implicit widening conversion from <code>PosZFiniteFloat</code> to <code>PosZFloat</code>.
   *
   * @param pos the <code>PosZFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosZFiniteFloat</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosZFloat</code>.
   */
  implicit def widenToPosZFloat(pos: PosZFiniteFloat): PosZFloat = PosZFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosZFiniteFloat</code> to <code>PosZDouble</code>.
   *
   * @param pos the <code>PosZFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosZFiniteFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosZDouble</code>.
   */
  implicit def widenToPosZDouble(pos: PosZFiniteFloat): PosZDouble = PosZDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosZFiniteFloat</code> to <code>PosZFiniteDouble</code>.
   *
   * @param pos the <code>PosZFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosZFiniteFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosZFiniteDouble</code>.
   */
  implicit def widenToPosZFiniteDouble(pos: PosZFiniteFloat): PosZFiniteDouble = PosZFiniteDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosZFiniteFloat</code> to <code>FiniteDouble</code>.
   *
   * @param pos the <code>PosZFiniteFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>PosZFiniteFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>FiniteDouble</code>.
   */
  implicit def widenToFiniteDouble(pos: PosZFiniteFloat): FiniteDouble = FiniteDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[PosZFiniteFloat] =
    new Ordering[PosZFiniteFloat] {
      def compare(x: PosZFiniteFloat, y: PosZFiniteFloat): Int = x.toFloat.compare(y)
    }


  /**
   * The smallest positive value greater than 0.0d representable as a <code>PosZFiniteFloat</code>, which is PosZFiniteFloat(1.4E-45).
   */
  final val MinPositiveValue: PosZFiniteFloat = PosZFiniteFloat.ensuringValid(Float.MinPositiveValue)

}
