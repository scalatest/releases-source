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
 * An <code>AnyVal</code> for megative <code>Float</code>s.
 *
 * <p>
 * Note: a <code>NegFloat</code> may not equal 0.0. If you want negative number or 0, use [[NegZFloat]].
 * </p>
 *
 * <p>
 * Because <code>NegFloat</code> is an <code>AnyVal</code> it
 * will usually be as efficient as an <code>Float</code>, being
 * boxed only when an <code>Float</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>NegFloat.apply</code> factory method is implemented
 * in terms of a macro that checks literals for validity at
 * compile time. Calling <code>NegFloat.apply</code> with a
 * literal <code>Float</code> value will either produce a valid
 * <code>NegFloat</code> instance at run time or an error at
 * compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; NegFloat(-42.1fF)
 * res0: org.scalactic.anyvals.NegFloat = NegFloat(-42.1f)
 *
 * scala&gt; NegFloat(0.0fF)
 * &lt;console&gt;:14: error: NegFloat.apply can only be invoked on a megative (i < 0.0f) floating point literal, like NegFloat(-42.1fF).
 *               NegFloat(-42.1fF)
 *                       ^
 * </pre>
 *
 * <p>
 * <code>NegFloat.apply</code> cannot be used if the value being
 * passed is a variable (<em>i.e.</em>, not a literal), because
 * the macro cannot determine the validity of variables at
 * compile time (just literals). If you try to pass a variable
 * to <code>NegFloat.apply</code>, you'll get a compiler error
 * that suggests you use a different factor method,
 * <code>NegFloat.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = -42.1fF
 * x: Float = -42.1f
 *
 * scala&gt; NegFloat(x)
 * &lt;console&gt;:15: error: NegFloat.apply can only be invoked on a floating point literal, like NegFloat(-42.1fF). Please use NegFloat.from instead.
 *               NegFloat(x)
 *                       ^
 * </pre>
 *
 * <p>
 * The <code>NegFloat.from</code> factory method will inspect
 * the value at runtime and return an
 * <code>Option[NegFloat]</code>. If the value is valid,
 * <code>NegFloat.from</code> will return a
 * <code>Some[NegFloat]</code>, else it will return a
 * <code>None</code>.  Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; NegFloat.from(x)
 * res3: Option[org.scalactic.anyvals.NegFloat] = Some(NegFloat(-42.1f))
 *
 * scala&gt; val y = 0.0fF
 * y: Float = 0.0f
 *
 * scala&gt; NegFloat.from(y)
 * res4: Option[org.scalactic.anyvals.NegFloat] = None
 * </pre>
 *
 * <p>
 * The <code>NegFloat.apply</code> factory method is marked
 * implicit, so that you can pass literal <code>Float</code>s
 * into methods that require <code>NegFloat</code>, and get the
 * same compile-time checking you get when calling
 * <code>NegFloat.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: NegFloat): Float = Float.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.NegFloat)Float
 *
 * scala&gt; invert(-42.1fF)
 * res5: Float = 3.4028235E38
 *
 * scala&gt; invert(Float.MaxValue)
 * res6: Float = 0.0
 *
 * scala&gt; invert(0.0fF)
 * &lt;console&gt;:15: error: NegFloat.apply can only be invoked on a megative (i < 0.0f) floating point literal, like NegFloat(-42.1fF).
 *               invert(0.0F)
 *                      ^
 *
 * scala&gt; invert(0.0fF)
 * &lt;console&gt;:15: error: NegFloat.apply can only be invoked on a megative (i < 0.0f) floating point literal, like NegFloat(-42.1fF).
 *               invert(0.0fF)
 *                       ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the <code>NegFloat</code>
 * companion object also defines implicit widening conversions
 * when no loss of precision will occur. This makes it convenient to use a
 * <code>NegFloat</code> where a <code>Float</code> or wider
 * type is needed. An example is the subtraction in the body of
 * the <code>invert</code> method defined above,
 * <code>Float.MaxValue - pos</code>. Although
 * <code>Float.MaxValue</code> is a <code>Float</code>, which
 * has no <code>-</code> method that takes a
 * <code>NegFloat</code> (the type of <code>pos</code>), you can
 * still subtract <code>pos</code>, because the
 * <code>NegFloat</code> will be implicitly widened to
 * <code>Float</code>.
 * </p>
 *
 * @param value The <code>Float</code> value underlying this <code>NegFloat</code>.
 */
final class NegFloat private (val value: Float) extends AnyVal {

  /**
   * A string representation of this <code>NegFloat</code>.
   */
  override def toString: String = s"NegFloat(${value.toString()}f)"

  /**
   * Converts this <code>NegFloat</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>NegFloat</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>NegFloat</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>NegFloat</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>NegFloat</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>NegFloat</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>NegFloat</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble

  /** Returns this value, unmodified. */
  def unary_+ : NegFloat = this
  /** Returns the negation of this value. */
  def unary_- : PosFloat = PosFloat.ensuringValid(-value)

  /**
   * Converts this <code>NegFloat</code>'s value to a string then concatenates the given string.
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
  def max(that: NegFloat): NegFloat = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: NegFloat): NegFloat = if (math.min(value, that.value) == value) this else that

  /**
   * Indicates whether this `NegFloat` has a value that is a whole number: it is finite and it has no fraction part.
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
   * value, and if the result is positive, returns the result wrapped in a <code>NegFloat</code>,
   * else throws <code>AssertionError</code>.
   *
   * <p>
   * This method will inspect the result of applying the given function to this
   * <code>NegFloat</code>'s underlying <code>Float</code> value and if the result
   * is megative, it will return a <code>NegFloat</code> representing that value.
   * Otherwise, the <code>Float</code> value returned by the given function is
   * not megative, so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Float</code> is megative.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>NegFloat</code>'s
   * value will not produce invalid value.
   * Instead of producing such invalid values, this method will throw <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Float =&gt; Float</code> function to apply to this <code>NegFloat</code>'s
   *     underlying <code>Float</code> value.
   * @return the result of applying this <code>NegFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function, wrapped in a <code>NegFloat</code> if it is megative (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>NegFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function is not megative.
   */
  def ensuringValid(f: Float => Float): NegFloat = {
    val candidateResult: Float = f(value)
    if (NegFloatMacro.isValid(candidateResult)) new NegFloat(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid NegFloat")
  }


  /**
   * Rounds this `NegFloat` value to the nearest whole number value that can be expressed as an `NegZInt`, returning the result as a `NegZInt`.
   */
  def round: NegZInt = NegZInt.ensuringValid(math.round(value))

  /**
   * Returns the smallest (closest to 0) `NegZFloat` that is greater than or equal to this `NegZFloat`
   * and represents a mathematical integer.
   */
  def ceil: NegZFloat = NegZFloat.ensuringValid(math.ceil(value).toFloat)

  /**
   * Returns the greatest (closest to infinity) `NegFloat` that is less than or equal to
   * this `NegFloat` and represents a mathematical integer.
   */
  def floor: NegFloat = NegFloat.ensuringValid(math.floor(value).toFloat)

  /**
   * Returns the <code>NegFloat</code> sum of this <code>NegFloat</code>'s value and the given <code>NegZFloat</code> value.
   *
   * <p>
   * This method will always succeed (not throw an exception) because
   * adding a negative Float and non-positive Float and another
   * negative Float will always result in another negative Float
   * value (though the result may be infinity).
   * </p>
   */
  def plus(x: NegZFloat): NegFloat = NegFloat.ensuringValid(value + x.value)

  /**
   * True if this <code>NegFloat</code> value represents negative infinity, else false.
   */
  def isNegInfinity: Boolean = Float.NegativeInfinity == value

  /**
   * True if this <code>NegFloat</code> value is any finite value (i.e., it is neither positive nor negative infinity), else false.
   */
  def isFinite: Boolean = !value.isInfinite

}

/**
 * The companion object for <code>NegFloat</code> that offers
 * factory methods that produce <code>NegFloat</code>s,
 * implicit widening conversions from <code>NegFloat</code> to
 * other numeric types, and maximum and minimum constant values
 * for <code>NegFloat</code>.
 */
object NegFloat {

  /**
   * The largest value representable as a megative <code>Float</code>,
   * which is <code>NegFloat(-1.4E-45)</code>.
   */
  final val MaxValue: NegFloat = NegFloat.ensuringValid(-Float.MinPositiveValue)

  /**
   * The smallest value representable as a megative
   * <code>Float</code>, which is <code>NegFloat(-3.4028235E38)</code>.
   */
  final val MinValue: NegFloat = NegFloat.ensuringValid(Float.MinValue) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[NegFloat]</code> given a
   * <code>Float</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a megative <code>Float</code>, it will return a <code>NegFloat</code>
   * representing that value wrapped in a <code>Some</code>. Otherwise, the passed <code>Float</code>
   * value is not megative, so this method will return <code>None</code>.
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
   * @param value the <code>Float</code> to inspect, and if megative, return
   *     wrapped in a <code>Some[NegFloat]</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>Some[NegFloat]</code>, if it is megative, else
   *     <code>None</code>.
   */
  def from(value: Float): Option[NegFloat] =
    if (NegFloatMacro.isValid(value)) Some(new NegFloat(value)) else None

  /**
   * A factory/assertion method that produces a <code>NegFloat</code> given a
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
   * it is a megative <code>Float</code>, it will return a <code>NegFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not megative, so
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
   * @param value the <code>Float</code> to inspect, and if megative, return
   *     wrapped in a <code>NegFloat</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>NegFloat</code>, if it is megative, else
   *     throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not megative
   */
  def ensuringValid(value: Float): NegFloat =
    if (NegFloatMacro.isValid(value)) new NegFloat(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid NegFloat")
    }

  /**
   * A factory/validation method that produces a <code>NegFloat</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a megative <code>Float</code>, it will return a <code>NegFloat</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Float</code> value is not megative, so this
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
   * @param value the <code>Float</code> to inspect, and if megative, return
   *     wrapped in a <code>Success(NegFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Success(NegFloat)</code>, if it is megative, else a <code>Failure(AssertionError)</code>.
   */
  def tryingValid(value: Float): Try[NegFloat] =
    if (NegFloatMacro.isValid(value))
      Success(new NegFloat(value))
    else
      Failure(new AssertionError(s"${value.toString()} was not a valid NegFloat"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Float</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a megative <code>Float</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Float</code> value is megative, so this
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
   * @param value the `Float` to validate that it is megative.
   * @return a `Pass` if the specified `Float` value is megative,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Float` to the given function `f`.
   */
  def passOrElse[E](value: Float)(f: Float => E): Validation[E] =
    if (NegFloatMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>NegFloat</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Float</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a megative <code>Float</code>, it will return a <code>NegFloat</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Float</code> value is not megative, so this
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
   * @param value the <code>Float</code> to inspect, and if megative, return
   *     wrapped in a <code>Good(NegFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Good(NegFloat)</code>, if it is megative, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Float)(f: Float => B): NegFloat Or B =
    if (NegFloatMacro.isValid(value)) Good(NegFloat.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>NegFloat</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a megative <code>Int</code>, it will return a <code>NegFloat</code>
   * representing that value, wrapped in a <code>Right</code>.
   * Otherwise, the passed <code>Int</code> value is not megative, so this
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
   * @param value the <code>Int</code> to inspect, and if megative, return
   *     wrapped in a <code>Right(NegFloat)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Right(NegFloat)</code>, if it is megative, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Float)(f: Float => L): Either[L, NegFloat] =
    if (NegFloatMacro.isValid(value)) Right(NegFloat.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Float</code> value is megative.
   *
   * @param value the <code>Float</code> to inspect, and if megative, return true.
   * @return true if the specified <code>Float</code> is megative, else false.
   */
  def isValid(value: Float): Boolean = NegFloatMacro.isValid(value)

  /**
   * A factory method that produces a <code>NegFloat</code> given a
   * <code>Float</code> value and a default <code>NegFloat</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a megative <code>Float</code>, it will return a <code>NegFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not megative, so this
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
   * @param value the <code>Float</code> to inspect, and if megative, return.
   * @param default the <code>NegFloat</code> to return if the passed
   *     <code>Float</code> value is not megative.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>NegFloat</code>, if it is megative, else the
   *     <code>default</code> <code>NegFloat</code> value.
   */
  def fromOrElse(value: Float, default: => NegFloat): NegFloat =
    if (NegFloatMacro.isValid(value)) new NegFloat(value) else default

  import language.experimental.macros
  import scala.language.implicitConversions

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>NegFloat</code> if passed a valid <code>Float</code>
   * literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the
   * specified <code>Float</code> expression at compile time. If
   * the expression is a megative <code>Float</code> literal,
   * it will return a <code>NegFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> expression is either a literal
   * that is not megative, or is not a literal, so this method
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
   *     inspect at compile time, and if megative, to return
   *     wrapped in a <code>NegFloat</code> at run time.
   * @return the specified, valid <code>Float</code> literal
   *     value wrapped in a <code>NegFloat</code>. (If the
   *     specified expression is not a valid <code>Float</code>
   *     literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Float): NegFloat = macro NegFloatMacro.apply

  /**
   * Implicit widening conversion from <code>NegFloat</code> to
   * <code>Float</code>.
   *
   * @param pos the <code>NegFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>NegFloat</code>
   */
 implicit def widenToFloat(pos: NegFloat): Float = pos.value

  /**
   * Implicit widening conversion from <code>NegFloat</code> to
   * <code>Double</code>.
   *
   * @param pos the <code>NegFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>NegFloat</code>, widened to
   *     <code>Double</code>.
   */
  implicit def widenToDouble(pos: NegFloat): Double = pos.value


  /**
   * Implicit widening conversion from <code>NegFloat</code> to <code>NegDouble</code>.
   *
   * @param pos the <code>NegFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>NegFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NegDouble</code>.
   */
  implicit def widenToNegDouble(pos: NegFloat): NegDouble = NegDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NegFloat</code> to <code>NegZFloat</code>.
   *
   * @param pos the <code>NegFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>NegFloat</code>,
   *     widened to <code>Float</code> and wrapped in a <code>NegZFloat</code>.
   */
  implicit def widenToNegZFloat(pos: NegFloat): NegZFloat = NegZFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NegFloat</code> to <code>NegZDouble</code>.
   *
   * @param pos the <code>NegFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>NegFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NegZDouble</code>.
   */
  implicit def widenToNegZDouble(pos: NegFloat): NegZDouble = NegZDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NegFloat</code> to <code>NonZeroFloat</code>.
   *
   * @param pos the <code>NegFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>NegFloat</code>,
   *     widened to <code>Float</code> and wrapped in a <code>NonZeroFloat</code>.
   */
  implicit def widenToNonZeroFloat(pos: NegFloat): NonZeroFloat = NonZeroFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NegFloat</code> to <code>NonZeroDouble</code>.
   *
   * @param pos the <code>NegFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>NegFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NonZeroDouble</code>.
   */
  implicit def widenToNonZeroDouble(pos: NegFloat): NonZeroDouble = NonZeroDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[NegFloat] =
    new Ordering[NegFloat] {
      def compare(x: NegFloat, y: NegFloat): Int = x.toFloat.compare(y)
    }


  /**
   * The negative infinity value, which is <code>NegFloat.ensuringValid(Float.NegativeInfinity)</code>.
   */
  final val NegativeInfinity: NegFloat = NegFloat.ensuringValid(Float.NegativeInfinity) // Can't use the macro here

}
