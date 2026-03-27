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
 * An <code>AnyVal</code> for finite <code>Double</code>s.
 *
 * <p>
 * 
 * </p>
 *
 * <p>
 * Because <code>FiniteDouble</code> is an <code>AnyVal</code> it
 * will usually be as efficient as an <code>Double</code>, being
 * boxed only when a <code>Double</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>FiniteDouble.apply</code> factory method is
 * implemented in terms of a macro that checks literals for
 * validity at compile time. Calling
 * <code>FiniteDouble.apply</code> with a literal
 * <code>Double</code> value will either produce a valid
 * <code>FiniteDouble</code> instance at run time or an error at
 * compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; FiniteDouble(1.1)
 * res1: org.scalactic.anyvals.FiniteDouble = FiniteDouble(1.1)
 *
 * scala&gt; FiniteDouble(Finite.PositiveInfinity)
 * &lt;console&gt;:14: error: FiniteDouble.apply can only be invoked on a finite (i != Double.NegativeInfinity && i != Double.PositiveInfinity && !i.isNaN) floating point literal, like FiniteDouble(1.1).
 *               FiniteDouble(Finite.PositiveInfinity)
 *                        ^
 * </pre>
 *
 * <p>
 * <code>FiniteDouble.apply</code> cannot be used if the value
 * being passed is a variable (<em>i.e.</em>, not a literal),
 * because the macro cannot determine the validity of variables
 * at compile time (just literals). If you try to pass a
 * variable to <code>FiniteDouble.apply</code>, you'll get a
 * compiler error that suggests you use a different factor
 * method, <code>FiniteDouble.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = 1.1
 * x: Double = 1.1
 *
 * scala&gt; FiniteDouble(x)
 * &lt;console&gt;:15: error: FiniteDouble.apply can only be invoked on a floating point literal, like FiniteDouble(1.1). Please use FiniteDouble.from instead.
 *               FiniteDouble(x)
 *                        ^
 * </pre>
 *
 * <p>
 * The <code>FiniteDouble.from</code> factory method will inspect
 * the value at runtime and return an
 * <code>Option[FiniteDouble]</code>. If the value is valid,
 * <code>FiniteDouble.from</code> will return a
 * <code>Some[FiniteDouble]</code>, else it will return a
 * <code>None</code>.  Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; FiniteDouble.from(x)
 * res4: Option[org.scalactic.anyvals.FiniteDouble] = Some(FiniteDouble(1.1))
 *
 * scala&gt; val y = Finite.PositiveInfinity
 * y: Double = Finite.PositiveInfinity
 *
 * scala&gt; FiniteDouble.from(y)
 * res5: Option[org.scalactic.anyvals.FiniteDouble] = None
 * </pre>
 *
 * <p>
 * The <code>FiniteDouble.apply</code> factory method is marked
 * implicit, so that you can pass literal <code>Double</code>s
 * into methods that require <code>FiniteDouble</code>, and get the
 * same compile-time checking you get when calling
 * <code>FiniteDouble.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: FiniteDouble): Double = Double.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.FiniteDouble)Double
 *
 * scala&gt; invert(1.1)
 * res6: Double = 1.7976931348623157E308
 *
 * scala&gt; invert(Double.MaxValue)
 * res8: Double = 0.0
 *
 * scala&gt; invert(Finite.PositiveInfinity)
 * &lt;console&gt;:15: error: FiniteDouble.apply can only be invoked on a finite (i != Double.NegativeInfinity && i != Double.PositiveInfinity && !i.isNaN) floating point literal, like FiniteDouble(1.1).
 *               invert(Finite.PositiveInfinity)
 *                      ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the
 * <code>FiniteDouble</code> companion object also defines implicit
 * widening conversions when a similar conversion is provided in
 * Scala. This makes it convenient to use a
 * <code>FiniteDouble</code> where a <code>Double</code> is
 * needed. An example is the subtraction in the body of the
 * <code>invert</code> method defined above,
 * <code>Double.MaxValue - pos</code>. Although
 * <code>Double.MaxValue</code> is a <code>Double</code>, which
 * has no <code>-</code> method that takes a
 * <code>FiniteDouble</code> (the type of <code>pos</code>), you
 * can still subtract <code>pos</code>, because the
 * <code>FiniteDouble</code> will be implicitly widened to
 * <code>Double</code>.
 * </p>
 *
 * @param value The <code>Double</code> value underlying this <code>FiniteDouble</code>.
 */
final class FiniteDouble private (val value: Double) extends AnyVal {

  /**
   * A string representation of this <code>FiniteDouble</code>.
   */
  override def toString: String = s"FiniteDouble(${value.toString()})"

  /**
   * Converts this <code>FiniteDouble</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>FiniteDouble</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>FiniteDouble</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>FiniteDouble</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>FiniteDouble</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>FiniteDouble</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>FiniteDouble</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble

  /** Returns this value, unmodified. */
  def unary_+ : FiniteDouble = this
  /** Returns the negation of this value. */
  def unary_- : FiniteDouble = FiniteDouble.ensuringValid(-value)

  /**
   * Converts this <code>FiniteDouble</code>'s value to a string then concatenates the given string.
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
  def +(x: Byte): Double = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Short): Double = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Char): Double = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Int): Double = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Long): Double = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Float): Double = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Double): Double = value + x

  /** Returns the difference of this value and `x`. */
  def -(x: Byte): Double = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Short): Double = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Char): Double = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Int): Double = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Long): Double = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Float): Double = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Double): Double = value - x

  /** Returns the product of this value and `x`. */
  def *(x: Byte): Double = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Short): Double = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Char): Double = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Int): Double = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Long): Double = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Float): Double = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Double): Double = value * x

  /** Returns the quotient of this value and `x`. */
  def /(x: Byte): Double = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Short): Double = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Char): Double = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Int): Double = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Long): Double = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Float): Double = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Double): Double = value / x

  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Byte): Double = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Short): Double = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Char): Double = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Int): Double = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Long): Double = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Float): Double = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Double): Double = value % x

  // TODO: Need Scaladoc
  // Stuff from RichDouble

  /**
   * Returns <code>this</code> if <code>this &gt; that</code> or <code>that</code> otherwise.
   */
  def max(that: FiniteDouble): FiniteDouble = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: FiniteDouble): FiniteDouble = if (math.min(value, that.value) == value) this else that

  /**
   * Indicates whether this `FiniteDouble` has a value that is a whole number: it is finite and it has no fraction part.
   */
  def isWhole = {
    val longValue = value.toLong
    longValue.toDouble == value || longValue == Long.MaxValue && value < Double.PositiveInfinity || longValue == Long.MinValue && value > Double.NegativeInfinity
  }

  /** Converts an angle measured in degrees to an approximately equivalent
   * angle measured in radians.
   *
   * @return the measurement of the angle x in radians.
   */
  def toRadians: Double = math.toRadians(value)

  /** Converts an angle measured in radians to an approximately equivalent
   * angle measured in degrees.
   * @return the measurement of the angle x in degrees.
   */
  def toDegrees: Double = math.toDegrees(value)

  /**
   * Applies the passed <code>Double =&gt; Double</code> function to the underlying <code>Double</code>
   * value, and if the result is positive, returns the result wrapped in a <code>FiniteDouble</code>,
   * else throws <code>AssertionError</code>.
   *
   * <p>
   * This method will inspect the result of applying the given function to this
   * <code>FiniteDouble</code>'s underlying <code>Double</code> value and if the result
   * is greater than <code>0.0</code>, it will return a <code>FiniteDouble</code> representing that value.
   * Otherwise, the <code>Double</code> value returned by the given function is
   * <code>0.0</code> or negative, so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Double</code> is positive.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>FiniteDouble</code>'s
   * value will not produce zero, a negative number, including <code>Double.NegativeInfinity</code>, or <code>Double.NaN</code>.
   * Instead of producing such invalid values, this method will throw <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Double =&gt; Double</code> function to apply to this <code>FiniteDouble</code>'s
   *     underlying <code>Double</code> value.
   * @return the result of applying this <code>FiniteDouble</code>'s underlying <code>Double</code> value to
   *     to the passed function, wrapped in a <code>FiniteDouble</code> if it is positive (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>FiniteDouble</code>'s underlying <code>Double</code> value to
   *     to the passed function is not positive.
   */
  def ensuringValid(f: Double => Double): FiniteDouble = {
    val candidateResult: Double = f(value)
    if (FiniteDoubleMacro.isValid(candidateResult)) new FiniteDouble(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid FiniteDouble")
  }


  /**
   * Rounds this `FiniteDouble` value to the nearest whole number value that can be expressed as an `Long`, returning the result as a `Long`.
   */
  def round: Long = math.round(value)

  /**
   * Returns the smallest (closest to 0) `FiniteDouble` that is greater than or equal to this `FiniteDouble`
   * and represents a mathematical integer.
   */
  def ceil: FiniteDouble = FiniteDouble.ensuringValid(math.ceil(value).toDouble)

  /**
   * Returns the greatest (closest to infinity) `FiniteDouble` that is less than or equal to
   * this `FiniteDouble` and represents a mathematical integer.
   */
  def floor: FiniteDouble = FiniteDouble.ensuringValid(math.floor(value).toDouble)

}

/**
 * The companion object for <code>FiniteDouble</code> that offers
 * factory methods that produce <code>FiniteDouble</code>s,
 * implicit widening conversions from <code>FiniteDouble</code> to
 * other numeric types, and maximum and minimum constant values
 * for <code>FiniteDouble</code>.
 */
object FiniteDouble {

  /**
   * The largest value representable as a finite <code>Double</code>,
   * which is <code>FiniteDouble(1.7976931348623157E308)</code>.
   */
  final val MaxValue: FiniteDouble = FiniteDouble.ensuringValid(Double.MaxValue)

  /**
   * The smallest value representable as a finite
   * <code>Double</code>, which is <code>FiniteDouble(-1.7976931348623157E308)</code>.
   */
  final val MinValue: FiniteDouble = FiniteDouble.ensuringValid(Double.MinValue) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[FiniteDouble]</code> given a
   * <code>Double</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite <code>Double</code>, it will return a <code>FiniteDouble</code>
   * representing that value, wrapped in a <code>Some</code>. Otherwise, the passed <code>Double</code>
   * value is not finite, so this method will return <code>None</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Double</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Double</code> values at run time.
   * </p>
   *
   * @param value the <code>Double</code> to inspect, and if finite, return
   *     wrapped in a <code>Some[FiniteDouble]</code>.
   * @return the specified <code>Double</code> value wrapped in a
   *     <code>Some[FiniteDouble]</code>, if it is FiniteDouble, else
   *     <code>None</code>.
   */
  def from(value: Double): Option[FiniteDouble] =
    if (FiniteDoubleMacro.isValid(value)) Some(new FiniteDouble(value)) else None

  /**
   * A factory/assertion method that produces a <code>FiniteDouble</code> given a
   * valid <code>Double</code> value, or throws <code>AssertionError</code>,
   * if given an invalid <code>Double</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite <code>Double</code>, it will return a <code>FiniteDouble</code>
   * representing that value. Otherwise, the passed <code>Double</code> value is not finite,
   * so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Double</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Double</code> values at run time.
   * It differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises a <code>Double</code> is finite.
   * </p>
   *
   * @param value the <code>Double</code> to inspect, and if finite, return
   *     wrapped in a <code>FiniteDouble</code>.
   * @return the specified <code>Double</code> value wrapped in a
   *     <code>FiniteDouble</code>, if it is finite, else
   *     throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not finite
   */
  def ensuringValid(value: Double): FiniteDouble =
    if (FiniteDoubleMacro.isValid(value)) new FiniteDouble(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid FiniteDouble")
    }

  /**
   * A factory/validation method that produces a <code>FiniteDouble</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Float</code> value, or if the
   * given <code>Float</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
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
   * it is a finite <code>Float</code>, it will return a <code>FiniteDouble</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Float</code> value is not finite, so this
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
   * @param value the <code>Float</code> to inspect, and if finite, return
   *     wrapped in a <code>Success(FiniteDouble)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Success(FiniteDouble)</code>, if it is finite, else a <code>Failure(AssertionError)</code>.
   */
  def tryingValid(value: Double): Try[FiniteDouble] =
    if (FiniteDoubleMacro.isValid(value))
      Success(new FiniteDouble(value))
    else
      Failure(new AssertionError(s"${value.toString()} was not a valid FiniteDouble"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Double</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite <code>Double</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Double</code> value is finite, so this
   * method will return a result of type <code>E</code> obtained by passing
   * the invalid <code>Double</code> value to the given function <code>f</code>,
   * wrapped in a `Fail`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Double</code> literals at compile time, whereas this method inspects
   * <code>Double</code> values at run time.
   * </p>
   *
   * @param value the `Int` to validate that it is finite.
   * @return a `Pass` if the specified `Int` value is finite,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Double` to the given function `f`.
   */
  def passOrElse[E](value: Double)(f: Double => E): Validation[E] =
    if (FiniteDoubleMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>FiniteDouble</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Double</code> value, or if the
   * given <code>Double</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Double</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite <code>Double</code>, it will return a <code>FiniteDouble</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Double</code> value is not finite, so this
   * method will return a result of type <code>B</code> obtained by passing
   * the invalid <code>Double</code> value to the given function <code>f</code>,
   * wrapped in a `Bad`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Double</code> literals at compile time, whereas this method inspects
   * <code>Double</code> values at run time.
   * </p>
   *
   * @param value the <code>Double</code> to inspect, and if finite, return
   *     wrapped in a <code>Good(FiniteDouble)</code>.
   * @return the specified <code>Double</code> value wrapped
   *     in a <code>Good(FiniteDouble)</code>, if it is finite, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Double)(f: Double => B): FiniteDouble Or B =
    if (FiniteDoubleMacro.isValid(value)) Good(FiniteDouble.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>FiniteDouble</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Double</code> value, or if the
   * given <code>Double</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Double</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite <code>Double</code>, it will return a <code>FiniteDouble</code>
   * representing that value, wrapped in a <code>Right</code>.
   * Otherwise, the passed <code>Double</code> value is not finite, so this
   * method will return a result of type <code>L</code> obtained by passing
   * the invalid <code>Double</code> value to the given function <code>f</code>,
   * wrapped in a `Left`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Double</code> literals at compile time, whereas this method inspects
   * <code>Double</code> values at run time.
   * </p>
   *
   * @param value the <code>Double</code> to inspect, and if finite, return
   *     wrapped in a <code>Right(FiniteDouble)</code>.
   * @return the specified <code>Double</code> value wrapped
   *     in a <code>Right(FiniteDouble)</code>, if it is finite, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Double)(f: Double => L): Either[L, FiniteDouble] =
    if (FiniteDoubleMacro.isValid(value)) Right(FiniteDouble.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Double</code> value is finite.
   *
   * @param value the <code>Double</code> to inspect, and if finite, return true.
   * @return true if the specified <code>Double</code> is positive, else false.
   */
  def isValid(value: Double): Boolean = FiniteDoubleMacro.isValid(value)

  /**
   * A factory method that produces a <code>FiniteDouble</code> given a
   * <code>Double</code> value and a default <code>FiniteDouble</code>.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite <code>Double</code>, it will return a <code>FiniteDouble</code>
   * representing that value.  Otherwise, the passed <code>Double</code> value is finite,
   * so this method will return the passed <code>default</code> value.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Double</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Double</code> values at run time.
   * </p>
   *
   * @param value the <code>Double</code> to inspect, and if finite, return.
   * @param default the <code>FiniteDouble</code> to return if the passed
   *     <code>Double</code> value is not finite.
   * @return the specified <code>Double</code> value wrapped in a
   *     <code>FiniteDouble</code>, if it is finite, else the
   *     <code>default</code> <code>FiniteDouble</code> value.
   */
  def fromOrElse(value: Double, default: => FiniteDouble): FiniteDouble =
    if (FiniteDoubleMacro.isValid(value)) new FiniteDouble(value) else default

  import language.experimental.macros
  import scala.language.implicitConversions

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>FiniteDouble</code> if passed a valid <code>Double</code>
   * literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the
   * specified <code>Double</code> expression at compile time. If
   * the expression is a finite <code>Double</code> literal,
   * it will return a <code>FiniteDouble</code> representing that value.
   * Otherwise, the passed <code>Double</code> expression is either a literal
   * that is not finite, or is not a literal, so this method
   * will give a compiler error.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>from</code>
   * factory method in that this method is implemented via a
   * macro that inspects <code>Double</code> literals at compile
   * time, whereas <code>from</code> inspects <code>Double</code>
   * values at run time.
   * </p>
   *
   * @param value the <code>Double</code> literal expression to
   *     inspect at compile time, and if finite, to return
   *     wrapped in a <code>FiniteDouble</code> at run time.
   * @return the specified, valid <code>Double</code> literal
   *     value wrapped in a <code>FiniteDouble</code>. (If the
   *     specified expression is not a valid <code>Double</code>
   *     literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Double): FiniteDouble = macro FiniteDoubleMacro.apply

  /**
   * Implicit widening conversion from <code>FiniteDouble</code> to
   * <code>Double</code>.
   *
   * @param pos the <code>FiniteDouble</code> to widen
   * @return the <code>Double</code> value underlying the specified
   *     <code>FiniteDouble</code>
   */
  implicit def widenToDouble(pos: FiniteDouble): Double = pos.value


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[FiniteDouble] =
    new Ordering[FiniteDouble] {
      def compare(x: FiniteDouble, y: FiniteDouble): Int = x.toDouble.compare(y.toDouble)
    }


  /**
   * The smallest positive value greater than 0.0d representable as a <code>FiniteDouble</code>, which is FiniteDouble(4.9E-324).
   */
  final val MinPositiveValue: FiniteDouble = FiniteDouble.ensuringValid(Double.MinPositiveValue)

}
