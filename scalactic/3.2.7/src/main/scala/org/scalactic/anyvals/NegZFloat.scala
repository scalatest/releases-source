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
 * An <code>AnyVal</code> for non-positive <code>Float</code>s.
 *
 * <p>
 * 
 * </p>
 *
 * <p>
 * Because <code>NegZFloat</code> is an <code>AnyVal</code> it
 * will usually be as efficient as an <code>Float</code>, being
 * boxed only when an <code>Float</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>NegZFloat.apply</code> factory method is implemented
 * in terms of a macro that checks literals for validity at
 * compile time. Calling <code>NegZFloat.apply</code> with a
 * literal <code>Float</code> value will either produce a valid
 * <code>NegZFloat</code> instance at run time or an error at
 * compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; NegZFloat(-1.1fF)
 * res0: org.scalactic.anyvals.NegZFloat = NegZFloat(-1.1f)
 *
 * scala&gt; NegZFloat(1.1fF)
 * &lt;console&gt;:14: error: NegZFloat.apply can only be invoked on a non-positive (i <= 0.0f) floating point literal, like NegZFloat(-1.1fF).
 *               NegZFloat(-1.1fF)
 *                       ^
 * </pre>
 *
 * <p>
 * <code>NegZFloat.apply</code> cannot be used if the value being
 * passed is a variable (<em>i.e.</em>, not a literal), because
 * the macro cannot determine the validity of variables at
 * compile time (just literals). If you try to pass a variable
 * to <code>NegZFloat.apply</code>, you'll get a compiler error
 * that suggests you use a different factor method,
 * <code>NegZFloat.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = -1.1fF
 * x: Float = -1.1f
 *
 * scala&gt; NegZFloat(x)
 * &lt;console&gt;:15: error: NegZFloat.apply can only be invoked on a floating point literal, like NegZFloat(-1.1fF). Please use NegZFloat.from instead.
 *               NegZFloat(x)
 *                       ^
 * </pre>
 *
 * <p>
 * The <code>NegZFloat.from</code> factory method will inspect
 * the value at runtime and return an
 * <code>Option[NegZFloat]</code>. If the value is valid,
 * <code>NegZFloat.from</code> will return a
 * <code>Some[NegZFloat]</code>, else it will return a
 * <code>None</code>.  Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; NegZFloat.from(x)
 * res3: Option[org.scalactic.anyvals.NegZFloat] = Some(NegZFloat(-1.1f))
 *
 * scala&gt; val y = 1.1fF
 * y: Float = 1.1f
 *
 * scala&gt; NegZFloat.from(y)
 * res4: Option[org.scalactic.anyvals.NegZFloat] = None
 * </pre>
 *
 * <p>
 * The <code>NegZFloat.apply</code> factory method is marked
 * implicit, so that you can pass literal <code>Float</code>s
 * into methods that require <code>NegZFloat</code>, and get the
 * same compile-time checking you get when calling
 * <code>NegZFloat.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: NegZFloat): Float = Float.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.NegZFloat)Float
 *
 * scala&gt; invert(-1.1fF)
 * res5: Float = 3.4028235E38
 *
 * scala&gt; invert(Float.MaxValue)
 * res6: Float = 0.0
 *
 * scala&gt; invert(1.1fF)
 * &lt;console&gt;:15: error: NegZFloat.apply can only be invoked on a non-positive (i <= 0.0f) floating point literal, like NegZFloat(-1.1fF).
 *               invert(0.0F)
 *                      ^
 *
 * scala&gt; invert(1.1fF)
 * &lt;console&gt;:15: error: NegZFloat.apply can only be invoked on a non-positive (i <= 0.0f) floating point literal, like NegZFloat(-1.1fF).
 *               invert(1.1fF)
 *                       ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the <code>NegZFloat</code>
 * companion object also defines implicit widening conversions
 * when no loss of precision will occur. This makes it convenient to use a
 * <code>NegZFloat</code> where a <code>Float</code> or wider
 * type is needed. An example is the subtraction in the body of
 * the <code>invert</code> method defined above,
 * <code>Float.MaxValue - pos</code>. Although
 * <code>Float.MaxValue</code> is a <code>Float</code>, which
 * has no <code>-</code> method that takes a
 * <code>NegZFloat</code> (the type of <code>pos</code>), you can
 * still subtract <code>pos</code>, because the
 * <code>NegZFloat</code> will be implicitly widened to
 * <code>Float</code>.
 * </p>
 *
 * @param value The <code>Float</code> value underlying this <code>NegZFloat</code>.
 */
final class NegZFloat private (val value: Float) extends AnyVal {

  /**
   * A string representation of this <code>NegZFloat</code>.
   */
  override def toString: String = s"NegZFloat(${value.toString()}f)"

  /**
   * Converts this <code>NegZFloat</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>NegZFloat</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>NegZFloat</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>NegZFloat</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>NegZFloat</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>NegZFloat</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>NegZFloat</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble

  /** Returns this value, unmodified. */
  def unary_+ : NegZFloat = this
  /** Returns the negation of this value. */
  def unary_- : PosZFloat = PosZFloat.ensuringValid(-value)

  /**
   * Converts this <code>NegZFloat</code>'s value to a string then concatenates the given string.
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
  def max(that: NegZFloat): NegZFloat = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: NegZFloat): NegZFloat = if (math.min(value, that.value) == value) this else that

  /**
   * Indicates whether this `NegZFloat` has a value that is a whole number: it is finite and it has no fraction part.
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
   * value, and if the result is positive, returns the result wrapped in a <code>NegZFloat</code>,
   * else throws <code>AssertionError</code>.
   *
   * <p>
   * This method will inspect the result of applying the given function to this
   * <code>NegZFloat</code>'s underlying <code>Float</code> value and if the result
   * is non-positive, it will return a <code>NegZFloat</code> representing that value.
   * Otherwise, the <code>Float</code> value returned by the given function is
   * not non-positive, so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Float</code> is non-positive.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>NegZFloat</code>'s
   * value will not produce invalid value.
   * Instead of producing such invalid values, this method will throw <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Float =&gt; Float</code> function to apply to this <code>NegZFloat</code>'s
   *     underlying <code>Float</code> value.
   * @return the result of applying this <code>NegZFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function, wrapped in a <code>NegZFloat</code> if it is non-positive (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>NegZFloat</code>'s underlying <code>Float</code> value to
   *     to the passed function is not non-positive.
   */
  def ensuringValid(f: Float => Float): NegZFloat = {
    val candidateResult: Float = f(value)
    if (NegZFloatMacro.isValid(candidateResult)) new NegZFloat(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid NegZFloat")
  }


  /**
   * Rounds this `NegZFloat` value to the nearest whole number value that can be expressed as an `NegZInt`, returning the result as a `NegZInt`.
   */
  def round: NegZInt = NegZInt.ensuringValid(math.round(value))

  /**
   * Returns the smallest (closest to 0) `NegZFloat` that is greater than or equal to this `NegZFloat`
   * and represents a mathematical integer.
   */
  def ceil: NegZFloat = NegZFloat.ensuringValid(math.ceil(value).toFloat)

  /**
   * Returns the greatest (closest to infinity) `NegZFloat` that is less than or equal to
   * this `NegZFloat` and represents a mathematical integer.
   */
  def floor: NegZFloat = NegZFloat.ensuringValid(math.floor(value).toFloat)

  /**
   * Returns the <code>NegZFloat</code> sum of this value and `x`.
   *
   * <p>
   * This method will always succeed (not throw an exception) because
   * adding a non-positive Float to another non-positive Float
   * will always result in another non-positive Float
   * value (though the result may be infinity).
   * </p>
   */
  def plus(x: NegZFloat): NegZFloat = NegZFloat.ensuringValid(value + x)

  /**
   * True if this <code>NegZFloat</code> value represents negative infinity, else false.
   */
  def isNegInfinity: Boolean = Float.NegativeInfinity == value

  /**
   * True if this <code>NegZFloat</code> value is any finite value (i.e., it is neither positive nor negative infinity), else false.
   */
  def isFinite: Boolean = !value.isInfinite

}

/**
 * The companion object for <code>NegZFloat</code> that offers
 * factory methods that produce <code>NegZFloat</code>s,
 * implicit widening conversions from <code>NegZFloat</code> to
 * other numeric types, and maximum and minimum constant values
 * for <code>NegZFloat</code>.
 */
object NegZFloat {

  /**
   * The largest value representable as a non-positive <code>Float</code>,
   * which is <code>NegZFloat(0.0f)</code>.
   */
  final val MaxValue: NegZFloat = NegZFloat.ensuringValid(0.0f)

  /**
   * The smallest value representable as a non-positive
   * <code>Float</code>, which is <code>NegZFloat(-3.4028235E38)</code>.
   */
  final val MinValue: NegZFloat = NegZFloat.ensuringValid(Float.MinValue) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[NegZFloat]</code> given a
   * <code>Float</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-positive <code>Float</code>, it will return a <code>NegZFloat</code>
   * representing that value wrapped in a <code>Some</code>. Otherwise, the passed <code>Float</code>
   * value is not non-positive, so this method will return <code>None</code>.
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
   * @param value the <code>Float</code> to inspect, and if non-positive, return
   *     wrapped in a <code>Some[NegZFloat]</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>Some[NegZFloat]</code>, if it is non-positive, else
   *     <code>None</code>.
   */
  def from(value: Float): Option[NegZFloat] =
    if (NegZFloatMacro.isValid(value)) Some(new NegZFloat(value)) else None

  /**
   * A factory/assertion method that produces a <code>NegZFloat</code> given a
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
   * it is a non-positive <code>Float</code>, it will return a <code>NegZFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not non-positive, so
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
   * @param value the <code>Float</code> to inspect, and if non-positive, return
   *     wrapped in a <code>NegZFloat</code>.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>NegZFloat</code>, if it is non-positive, else
   *     throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not non-positive
   */
  def ensuringValid(value: Float): NegZFloat =
    if (NegZFloatMacro.isValid(value)) new NegZFloat(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid NegZFloat")
    }

  /**
   * A factory/validation method that produces a <code>NegZFloat</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-positive <code>Float</code>, it will return a <code>NegZFloat</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Float</code> value is not non-positive, so this
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
   * @param value the <code>Float</code> to inspect, and if non-positive, return
   *     wrapped in a <code>Success(NegZFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Success(NegZFloat)</code>, if it is non-positive, else a <code>Failure(AssertionError)</code>.
   */
  def tryingValid(value: Float): Try[NegZFloat] =
    if (NegZFloatMacro.isValid(value))
      Success(new NegZFloat(value))
    else
      Failure(new AssertionError(s"${value.toString()} was not a valid NegZFloat"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Float</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-positive <code>Float</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Float</code> value is non-positive, so this
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
   * @param value the `Float` to validate that it is non-positive.
   * @return a `Pass` if the specified `Float` value is non-positive,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Float` to the given function `f`.
   */
  def passOrElse[E](value: Float)(f: Float => E): Validation[E] =
    if (NegZFloatMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>NegZFloat</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Float</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-positive <code>Float</code>, it will return a <code>NegZFloat</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Float</code> value is not non-positive, so this
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
   * @param value the <code>Float</code> to inspect, and if non-positive, return
   *     wrapped in a <code>Good(NegZFloat)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Good(NegZFloat)</code>, if it is non-positive, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Float)(f: Float => B): NegZFloat Or B =
    if (NegZFloatMacro.isValid(value)) Good(NegZFloat.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>NegZFloat</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-positive <code>Int</code>, it will return a <code>NegZFloat</code>
   * representing that value, wrapped in a <code>Right</code>.
   * Otherwise, the passed <code>Int</code> value is not non-positive, so this
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
   * @param value the <code>Int</code> to inspect, and if non-positive, return
   *     wrapped in a <code>Right(NegZFloat)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Right(NegZFloat)</code>, if it is non-positive, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Float)(f: Float => L): Either[L, NegZFloat] =
    if (NegZFloatMacro.isValid(value)) Right(NegZFloat.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Float</code> value is non-positive.
   *
   * @param value the <code>Float</code> to inspect, and if non-positive, return true.
   * @return true if the specified <code>Float</code> is non-positive, else false.
   */
  def isValid(value: Float): Boolean = NegZFloatMacro.isValid(value)

  /**
   * A factory method that produces a <code>NegZFloat</code> given a
   * <code>Float</code> value and a default <code>NegZFloat</code>.
   *
   * <p>
   * This method will inspect the passed <code>Float</code> value and if
   * it is a non-positive <code>Float</code>, it will return a <code>NegZFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> value is not non-positive, so this
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
   * @param value the <code>Float</code> to inspect, and if non-positive, return.
   * @param default the <code>NegZFloat</code> to return if the passed
   *     <code>Float</code> value is not non-positive.
   * @return the specified <code>Float</code> value wrapped in a
   *     <code>NegZFloat</code>, if it is non-positive, else the
   *     <code>default</code> <code>NegZFloat</code> value.
   */
  def fromOrElse(value: Float, default: => NegZFloat): NegZFloat =
    if (NegZFloatMacro.isValid(value)) new NegZFloat(value) else default

  import language.experimental.macros
  import scala.language.implicitConversions

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>NegZFloat</code> if passed a valid <code>Float</code>
   * literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the
   * specified <code>Float</code> expression at compile time. If
   * the expression is a non-positive <code>Float</code> literal,
   * it will return a <code>NegZFloat</code> representing that value.
   * Otherwise, the passed <code>Float</code> expression is either a literal
   * that is not non-positive, or is not a literal, so this method
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
   *     inspect at compile time, and if non-positive, to return
   *     wrapped in a <code>NegZFloat</code> at run time.
   * @return the specified, valid <code>Float</code> literal
   *     value wrapped in a <code>NegZFloat</code>. (If the
   *     specified expression is not a valid <code>Float</code>
   *     literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Float): NegZFloat = macro NegZFloatMacro.apply

  /**
   * Implicit widening conversion from <code>NegZFloat</code> to
   * <code>Float</code>.
   *
   * @param pos the <code>NegZFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>NegZFloat</code>
   */
 implicit def widenToFloat(pos: NegZFloat): Float = pos.value

  /**
   * Implicit widening conversion from <code>NegZFloat</code> to
   * <code>Double</code>.
   *
   * @param pos the <code>NegZFloat</code> to widen
   * @return the <code>Float</code> value underlying the
   *     specified <code>NegZFloat</code>, widened to
   *     <code>Double</code>.
   */
  implicit def widenToDouble(pos: NegZFloat): Double = pos.value


  /**
   * Implicit widening conversion from <code>NegZFloat</code> to <code>NegZDouble</code>.
   *
   * @param pos the <code>NegZFloat</code> to widen
   * @return the <code>Float</code> value underlying the specified <code>NegZFloat</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NegZDouble</code>.
   */
  implicit def widenToNegZDouble(pos: NegZFloat): NegZDouble = NegZDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[NegZFloat] =
    new Ordering[NegZFloat] {
      def compare(x: NegZFloat, y: NegZFloat): Int = x.toFloat.compare(y)
    }


  /**
   * The negative infinity value, which is <code>NegZFloat.ensuringValid(Float.NegativeInfinity)</code>.
   */
  final val NegativeInfinity: NegZFloat = NegZFloat.ensuringValid(Float.NegativeInfinity) // Can't use the macro here

}
