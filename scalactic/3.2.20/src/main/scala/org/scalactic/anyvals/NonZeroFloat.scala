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
 * An <code>AnyVal</code> for non-zero <code>Float</code>s.
 *
 * <p>
 * Note: a <code>NonZeroFloat</code> may not equal 0.0.
 * </p>
 *
 * <p>
 * Because <code>NonZeroFloat</code> is an <code>AnyVal</code> it
 * will usually be as efficient as an <code>Float</code>, being
 * boxed only when an <code>Float</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>NonZeroFloat.apply</code> factory method is implemented
 * in terms of a macro that checks literals for validity at
 * compile time. Calling <code>NonZeroFloat.apply</code> with a
 * literal <code>Float</code> value will either produce a valid
 * <code>NonZeroFloat</code> instance at run time or an error at
 * compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; NonZeroFloat(1.1F)
 * res0: org.scalactic.anyvals.NonZeroFloat = NonZeroFloat(1.1)
 *
 * scala&gt; NonZeroFloat(0.0F)
 * &lt;console&gt;:14: error: NonZeroFloat.apply can only be invoked on a non-zero (i != 0.0f && !i.isNaN) floating point literal, like NonZeroFloat(1.1F).
 *               NonZeroFloat(1.1F)
 *                       ^
 * </pre>
 *
 * <p>
 * <code>NonZeroFloat.apply</code> cannot be used if the value being
 * passed is a variable (<em>i.e.</em>, not a literal), because
 * the macro cannot determine the validity of variables at
 * compile time (just literals). If you try to pass a variable
 * to <code>NonZeroFloat.apply</code>, you'll get a compiler error
 * that suggests you use a different factor method,
 * <code>NonZeroFloat.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = 1.1F
 * x: Float = 1.1
 *
 * scala&gt; NonZeroFloat(x)
 * &lt;console&gt;:15: error: NonZeroFloat.apply can only be invoked on a floating point literal, like NonZeroFloat(1.1F). Please use NonZeroFloat.from instead.
 *               NonZeroFloat(x)
 *                       ^
 * </pre>
 *
 * <p>
 * The <code>NonZeroFloat.from</code> factory method will inspect
 * the value at runtime and return an
 * <code>Option[NonZeroFloat]</code>. If the value is valid,
 * <code>NonZeroFloat.from</code> will return a
 * <code>Some[NonZeroFloat]</code>, else it will return a
 * <code>None</code>.  Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; NonZeroFloat.from(x)
 * res3: Option[org.scalactic.anyvals.NonZeroFloat] = Some(NonZeroFloat(1.1))
 *
 * scala&gt; val y = 0.0F
 * y: Float = 0.0
 *
 * scala&gt; NonZeroFloat.from(y)
 * res4: Option[org.scalactic.anyvals.NonZeroFloat] = None
 * </pre>
 *
 * <p>
 * The <code>NonZeroFloat.apply</code> factory method is marked
 * implicit, so that you can pass literal <code>Float</code>s
 * into methods that require <code>NonZeroFloat</code>, and get the
 * same compile-time checking you get when calling
 * <code>NonZeroFloat.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: NonZeroFloat): Float = Float.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.NonZeroFloat)Float
 *
 * scala&gt; invert(1.1F)
 * res5: Float = 3.4028235E38
 *
 * scala&gt; invert(Float.MaxValue)
 * res6: Float = 0.0
 *
 * scala&gt; invert(0.0F)
 * &lt;console&gt;:15: error: NonZeroFloat.apply can only be invoked on a non-zero (i != 0.0f && !i.isNaN) floating point literal, like NonZeroFloat(1.1F).
 *               invert(0.0F)
 *                      ^
 *
 * scala&gt; invert(0.0F)
 * &lt;console&gt;:15: error: NonZeroFloat.apply can only be invoked on a non-zero (i != 0.0f && !i.isNaN) floating point literal, like NonZeroFloat(1.1F).
 *               invert(0.0F)
 *                       ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the <code>NonZeroFloat</code>
 * companion object also defines implicit widening conversions
 * when no loss of precision will occur. This makes it convenient to use a
 * <code>NonZeroFloat</code> where a <code>Float</code> or wider
 * type is needed. An example is the subtraction in the body of
 * the <code>invert</code> method defined above,
 * <code>Float.MaxValue - pos</code>. Although
 * <code>Float.MaxValue</code> is a <code>Float</code>, which
 * has no <code>-</code> method that takes a
 * <code>NonZeroFloat</code> (the type of <code>pos</code>), you can
 * still subtract <code>pos</code>, because the
 * <code>NonZeroFloat</code> will be implicitly widened to
 * <code>Float</code>.
 * </p>
 *
 * @param value The <code>Float</code> value underlying this <code>NonZeroFloat</code>.
 */
final class NonZeroFloat private (val value: Float) extends AnyVal {

  /**
   * A string representation of this <code>NonZeroFloat</code>.
   */
  override def toString: String = s"NonZeroFloat(${value.toString()}f)"

  /**
   * Converts this <code>NonZeroFloat</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>NonZeroFloat</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>NonZeroFloat</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>NonZeroFloat</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>NonZeroFloat</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>NonZeroFloat</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>NonZeroFloat</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble

  /** Returns this value, unmodified. */
  def unary_+ : NonZeroFloat = this
  /** Returns the negation of this value. */
  def unary_- : NonZeroFloat = NonZeroFloat.ensuringValid(-value)

  /**
   * Converts this <code>NonZeroFloat</code>'s value to a string then concatenates the given string.
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
  def max(that: NonZeroFloat): NonZeroFloat = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: NonZeroFloat): NonZeroFloat = if (math.min(value, that.value) == value) this else that

  /**
   * Indicates whether this `NonZeroFloat` has a value that is a whole number: it is finite and it has no fraction part.
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
   * value, and if the result is positive, returns the result wrapped in a <code>NonZeroFloat</code>,
   * else throws <code>AssertionError</code>.
   *
   * <p>
   * This method will inspect the result of applying the given function to this
   * <code>NonZeroFloat</code>'s underlying <code>Float</code> value and if the result
   * is non-zero, it will return a <code>NonZeroFloat</code> representing that value.
   * Otherwise, the <code>Float</code> value returned by the given function is
   * not non-zero, so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Float</code> is non-zero.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>NonZeroFloat</code>'s
   * value will not produce invalid value.
   * Instead of producing such invalid values, this method will throw <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Float =&gt; Float</code> function to apply to this <code>NonZeroFloat</code>'s
   *     underlying <code>Float</code> value.
   * @return the result of applying this <code>NonZeroFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function, wrapped in a <code>NonZeroFloat</code> if it is non-zero (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>NonZeroFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function is not non-zero.
   */
  def ensuringValid(f: Float => Float): NonZeroFloat = {
    val candidateResult: Float = f(value)
    if (NonZeroFloatMacro.isValid(candidateResult)) new NonZeroFloat(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid NonZeroFloat")
  }


  /**
   * True if this <code>NonZeroFloat</code> value represents positive infinity, else false.
   */
  def isPosInfinity: Boolean = Float.PositiveInfinity == value

  /**
   * True if this <code>NonZeroFloat</code> value represents negative infinity, else false.
   */
  def isNegInfinity: Boolean = Float.NegativeInfinity == value

  /**
   * True if this <code>NonZeroFloat</code> value represents positive or negative infinity, else false.
   */
  def isInfinite: Boolean = value.isInfinite

  /**
   * True if this <code>NonZeroFloat</code> value is any finite value (i.e., it is neither positive nor negative infinity), else false.
   */
  def isFinite: Boolean = !value.isInfinite

}

/**
 * The companion object for <code>NonZeroFloat</code> that offers
 * factory methods that produce <code>NonZeroFloat</code>s,
 * implicit widening conversions from <code>NonZeroFloat</code> to
 * other numeric types, and maximum and minimum constant values
 * for <code>NonZeroFloat</code>.
 */
object NonZeroFloat {

  /**
   * The largest value representable as a non-zero <code>Float</code>,
   * which is <code>NonZeroFloat(3.4028235E38)</code>.
   */
  final val MaxValue: NonZeroFloat = NonZeroFloat.ensuringValid(Float.MaxValue)

  /**
   * The smallest value representable as a non-zero
   * <code>Float</code>, which is <code>NonZeroFloat(-3.4028235E38)</code>.
   */
  final val MinValue: NonZeroFloat = NonZeroFloat.ensuringValid(Float.MinValue) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[NonZeroFloat]</code> given a
   * <code>Float</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-zero <code>Float</code>, it will return a <code>NonZeroFloat</code>
   * representing that value wrapped in a <code>Some</code>. Otherwise, the passed <code>Float</code>
   * value is not non-zero, so this method will return <code>None</code>.
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
   * @param value the <code>Float</code> to inspect, and if non-zero, return
   *     wrapped in a <code>Some[NonZeroFloat]</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>Some[NonZeroFloat]</code>, if it is non-zero, else
   *     <code>None</code>.
   */
  def from(value: Float): Option[NonZeroFloat] =
    if (NonZeroFloatMacro.isValid(value)) Some(new NonZeroFloat(value)) else None

  /**
   * A factory/assertion method that produces a <code>NonZeroFloat</code> given a
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
   * it is a non-zero <code>Float</code>, it will return a <code>NonZeroFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not non-zero, so
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
   * @param value the <code>Float</code> to inspect, and if non-zero, return
   *     wrapped in a <code>NonZeroFloat</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>NonZeroFloat</code>, if it is non-zero, else
   *     throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not non-zero
   */
  def ensuringValid(value: Float): NonZeroFloat =
    if (NonZeroFloatMacro.isValid(value)) new NonZeroFloat(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid NonZeroFloat")
    }

  /**
   * A factory/validation method that produces a <code>NonZeroFloat</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-zero <code>Float</code>, it will return a <code>NonZeroFloat</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Float</code> value is not non-zero, so this
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
   * @param value the <code>Float</code> to inspect, and if non-zero, return
   *     wrapped in a <code>Success(NonZeroFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Success(NonZeroFloat)</code>, if it is non-zero, else a <code>Failure(AssertionError)</code>.
   */
  def tryingValid(value: Float): Try[NonZeroFloat] =
    if (NonZeroFloatMacro.isValid(value))
      Success(new NonZeroFloat(value))
    else
      Failure(new AssertionError(s"${value.toString()} was not a valid NonZeroFloat"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Float</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-zero <code>Float</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Float</code> value is non-zero, so this
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
   * @param value the `Float` to validate that it is non-zero.
   * @return a `Pass` if the specified `Float` value is non-zero,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Float` to the given function `f`.
   */
  def passOrElse[E](value: Float)(f: Float => E): Validation[E] =
    if (NonZeroFloatMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>NonZeroFloat</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Float</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-zero <code>Float</code>, it will return a <code>NonZeroFloat</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Float</code> value is not non-zero, so this
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
   * @param value the <code>Float</code> to inspect, and if non-zero, return
   *     wrapped in a <code>Good(NonZeroFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Good(NonZeroFloat)</code>, if it is non-zero, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Float)(f: Float => B): NonZeroFloat Or B =
    if (NonZeroFloatMacro.isValid(value)) Good(NonZeroFloat.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>NonZeroFloat</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-zero <code>Int</code>, it will return a <code>NonZeroFloat</code>
   * representing that value, wrapped in a <code>Right</code>.
   * Otherwise, the passed <code>Int</code> value is not non-zero, so this
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
   * @param value the <code>Int</code> to inspect, and if non-zero, return
   *     wrapped in a <code>Right(NonZeroFloat)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Right(NonZeroFloat)</code>, if it is non-zero, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Float)(f: Float => L): Either[L, NonZeroFloat] =
    if (NonZeroFloatMacro.isValid(value)) Right(NonZeroFloat.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Float</code> value is non-zero.
   *
   * @param value the <code>Float</code> to inspect, and if non-zero, return true.
   * @return true if the specified <code>Float</code> is non-zero, else false.
   */
  def isValid(value: Float): Boolean = NonZeroFloatMacro.isValid(value)

  /**
   * A factory method that produces a <code>NonZeroFloat</code> given a
   * <code>Float</code> value and a default <code>NonZeroFloat</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-zero <code>Float</code>, it will return a <code>NonZeroFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not non-zero, so this
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
   * @param value the <code>Float</code> to inspect, and if non-zero, return.
   * @param default the <code>NonZeroFloat</code> to return if the passed
   *     <code>Float</code> value is not non-zero.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>NonZeroFloat</code>, if it is non-zero, else the
   *     <code>default</code> <code>NonZeroFloat</code> value.
   */
  def fromOrElse(value: Float, default: => NonZeroFloat): NonZeroFloat =
    if (NonZeroFloatMacro.isValid(value)) new NonZeroFloat(value) else default

  import language.experimental.macros
  import scala.language.implicitConversions

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>NonZeroFloat</code> if passed a valid <code>Float</code>
   * literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the
   * specified <code>Float</code> expression at compile time. If
   * the expression is a non-zero <code>Float</code> literal,
   * it will return a <code>NonZeroFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> expression is either a literal
   * that is not non-zero, or is not a literal, so this method
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
   *     inspect at compile time, and if non-zero, to return
   *     wrapped in a <code>NonZeroFloat</code> at run time.
   * @return the specified, valid <code>Float</code> literal
   *     value wrapped in a <code>NonZeroFloat</code>. (If the
   *     specified expression is not a valid <code>Float</code>
   *     literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Float): NonZeroFloat = macro NonZeroFloatMacro.apply

  /**
   * Implicit widening conversion from <code>NonZeroFloat</code> to
   * <code>Float</code>.
   *
   * @param pos the <code>NonZeroFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>NonZeroFloat</code>
   */
 implicit def widenToFloat(pos: NonZeroFloat): Float = pos.value

  /**
   * Implicit widening conversion from <code>NonZeroFloat</code> to
   * <code>Double</code>.
   *
   * @param pos the <code>NonZeroFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>NonZeroFloat</code>, widened to
   *     <code>Double</code>.
   */
  implicit def widenToDouble(pos: NonZeroFloat): Double = pos.value


  /**
   * Implicit widening conversion from <code>NonZeroFloat</code> to <code>NonZeroDouble</code>.
   *
   * @param pos the <code>NonZeroFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>NonZeroFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NonZeroDouble</code>.
   */
  implicit def widenToNonZeroDouble(pos: NonZeroFloat): NonZeroDouble = NonZeroDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[NonZeroFloat] =
    new Ordering[NonZeroFloat] {
      def compare(x: NonZeroFloat, y: NonZeroFloat): Int = x.toFloat.compare(y)
    }


  /**
   * The positive infinity value, which is <code>NonZeroFloat.ensuringValid(Float.PositiveInfinity)</code>.
   */
  final val PositiveInfinity: NonZeroFloat = NonZeroFloat.ensuringValid(Float.PositiveInfinity) // Can't use the macro here

  /**
   * The negative infinity value, which is <code>NonZeroFloat.ensuringValid(Float.NegativeInfinity)</code>.
   */
  final val NegativeInfinity: NonZeroFloat = NonZeroFloat.ensuringValid(Float.NegativeInfinity) // Can't use the macro here

  /**
   * The smallest positive value greater than 0.0d representable as a <code>NonZeroFloat</code>, which is NonZeroFloat(1.4E-45).
   */
  final val MinPositiveValue: NonZeroFloat = NonZeroFloat.ensuringValid(Float.MinPositiveValue)

}
