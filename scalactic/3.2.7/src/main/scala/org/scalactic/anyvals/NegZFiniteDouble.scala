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
 * An <code>AnyVal</code> for finite non-positive <code>Double</code>s.
 *
 * <p>
 * 
 * </p>
 *
 * <p>
 * Because <code>NegZFiniteDouble</code> is an <code>AnyVal</code> it
 * will usually be as efficient as an <code>Double</code>, being
 * boxed only when a <code>Double</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>NegZFiniteDouble.apply</code> factory method is
 * implemented in terms of a macro that checks literals for
 * validity at compile time. Calling
 * <code>NegZFiniteDouble.apply</code> with a literal
 * <code>Double</code> value will either produce a valid
 * <code>NegZFiniteDouble</code> instance at run time or an error at
 * compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; NegZFiniteDouble(-1.1)
 * res1: org.scalactic.anyvals.NegZFiniteDouble = NegZFiniteDouble(-1.1)
 *
 * scala&gt; NegZFiniteDouble(1.1)
 * &lt;console&gt;:14: error: NegZFiniteDouble.apply can only be invoked on a finite non-positive (i <= 0.0 && i != Double.NegativeInfinity) floating point literal, like NegZFiniteDouble(-1.1).
 *               NegZFiniteDouble(1.1)
 *                        ^
 * </pre>
 *
 * <p>
 * <code>NegZFiniteDouble.apply</code> cannot be used if the value
 * being passed is a variable (<em>i.e.</em>, not a literal),
 * because the macro cannot determine the validity of variables
 * at compile time (just literals). If you try to pass a
 * variable to <code>NegZFiniteDouble.apply</code>, you'll get a
 * compiler error that suggests you use a different factor
 * method, <code>NegZFiniteDouble.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = -1.1
 * x: Double = -1.1
 *
 * scala&gt; NegZFiniteDouble(x)
 * &lt;console&gt;:15: error: NegZFiniteDouble.apply can only be invoked on a floating point literal, like NegZFiniteDouble(-1.1). Please use NegZFiniteDouble.from instead.
 *               NegZFiniteDouble(x)
 *                        ^
 * </pre>
 *
 * <p>
 * The <code>NegZFiniteDouble.from</code> factory method will inspect
 * the value at runtime and return an
 * <code>Option[NegZFiniteDouble]</code>. If the value is valid,
 * <code>NegZFiniteDouble.from</code> will return a
 * <code>Some[NegZFiniteDouble]</code>, else it will return a
 * <code>None</code>.  Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; NegZFiniteDouble.from(x)
 * res4: Option[org.scalactic.anyvals.NegZFiniteDouble] = Some(NegZFiniteDouble(-1.1))
 *
 * scala&gt; val y = 1.1
 * y: Double = 1.1
 *
 * scala&gt; NegZFiniteDouble.from(y)
 * res5: Option[org.scalactic.anyvals.NegZFiniteDouble] = None
 * </pre>
 *
 * <p>
 * The <code>NegZFiniteDouble.apply</code> factory method is marked
 * implicit, so that you can pass literal <code>Double</code>s
 * into methods that require <code>NegZFiniteDouble</code>, and get the
 * same compile-time checking you get when calling
 * <code>NegZFiniteDouble.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: NegZFiniteDouble): Double = Double.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.NegZFiniteDouble)Double
 *
 * scala&gt; invert(1.1)
 * res6: Double = 1.7976931348623157E308
 *
 * scala&gt; invert(Double.MaxValue)
 * res8: Double = 0.0
 *
 * scala&gt; invert(1.1)
 * &lt;console&gt;:15: error: NegZFiniteDouble.apply can only be invoked on a finite non-positive (i <= 0.0 && i != Double.NegativeInfinity) floating point literal, like NegZFiniteDouble(-1.1).
 *               invert(1.1)
 *                      ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the
 * <code>NegZFiniteDouble</code> companion object also defines implicit
 * widening conversions when a similar conversion is provided in
 * Scala. This makes it convenient to use a
 * <code>NegZFiniteDouble</code> where a <code>Double</code> is
 * needed. An example is the subtraction in the body of the
 * <code>invert</code> method defined above,
 * <code>Double.MaxValue - pos</code>. Although
 * <code>Double.MaxValue</code> is a <code>Double</code>, which
 * has no <code>-</code> method that takes a
 * <code>NegZFiniteDouble</code> (the type of <code>pos</code>), you
 * can still subtract <code>pos</code>, because the
 * <code>NegZFiniteDouble</code> will be implicitly widened to
 * <code>Double</code>.
 * </p>
 *
 * @param value The <code>Double</code> value underlying this <code>NegZFiniteDouble</code>.
 */
final class NegZFiniteDouble private (val value: Double) extends AnyVal {

  /**
   * A string representation of this <code>NegZFiniteDouble</code>.
   */
  override def toString: String = s"NegZFiniteDouble(${value.toString()})"

  /**
   * Converts this <code>NegZFiniteDouble</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>NegZFiniteDouble</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>NegZFiniteDouble</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>NegZFiniteDouble</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>NegZFiniteDouble</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>NegZFiniteDouble</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>NegZFiniteDouble</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble

  /** Returns this value, unmodified. */
  def unary_+ : NegZFiniteDouble = this
  /** Returns the negation of this value. */
  def unary_- : PosZFiniteDouble = PosZFiniteDouble.ensuringValid(-value)

  /**
   * Converts this <code>NegZFiniteDouble</code>'s value to a string then concatenates the given string.
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
  def max(that: NegZFiniteDouble): NegZFiniteDouble = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: NegZFiniteDouble): NegZFiniteDouble = if (math.min(value, that.value) == value) this else that

  /**
   * Indicates whether this `NegZFiniteDouble` has a value that is a whole number: it is finite and it has no fraction part.
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
   * value, and if the result is positive, returns the result wrapped in a <code>NegZFiniteDouble</code>,
   * else throws <code>AssertionError</code>.
   *
   * <p>
   * This method will inspect the result of applying the given function to this
   * <code>NegZFiniteDouble</code>'s underlying <code>Double</code> value and if the result
   * is greater than <code>0.0</code>, it will return a <code>NegZFiniteDouble</code> representing that value.
   * Otherwise, the <code>Double</code> value returned by the given function is
   * <code>0.0</code> or negative, so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Double</code> is positive.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>NegZFiniteDouble</code>'s
   * value will not produce zero, a negative number, including <code>Double.NegativeInfinity</code>, or <code>Double.NaN</code>.
   * Instead of producing such invalid values, this method will throw <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Double =&gt; Double</code> function to apply to this <code>NegZFiniteDouble</code>'s
   *     underlying <code>Double</code> value.
   * @return the result of applying this <code>NegZFiniteDouble</code>'s underlying <code>Double</code> value to
   *     to the passed function, wrapped in a <code>NegZFiniteDouble</code> if it is positive (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>NegZFiniteDouble</code>'s underlying <code>Double</code> value to
   *     to the passed function is not positive.
   */
  def ensuringValid(f: Double => Double): NegZFiniteDouble = {
    val candidateResult: Double = f(value)
    if (NegZFiniteDoubleMacro.isValid(candidateResult)) new NegZFiniteDouble(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid NegZFiniteDouble")
  }


  /**
   * Rounds this `NegZFiniteDouble` value to the nearest whole number value that can be expressed as an `NegZLong`, returning the result as a `NegZLong`.
   */
  def round: NegZLong = NegZLong.ensuringValid(math.round(value))

  /**
   * Returns the smallest (closest to 0) `NegZFiniteDouble` that is greater than or equal to this `NegZFiniteDouble`
   * and represents a mathematical integer.
   */
  def ceil: NegZFiniteDouble = NegZFiniteDouble.ensuringValid(math.ceil(value).toDouble)

  /**
   * Returns the greatest (closest to infinity) `NegZFiniteDouble` that is less than or equal to
   * this `NegZFiniteDouble` and represents a mathematical integer.
   */
  def floor: NegZFiniteDouble = NegZFiniteDouble.ensuringValid(math.floor(value).toDouble)

}

/**
 * The companion object for <code>NegZFiniteDouble</code> that offers
 * factory methods that produce <code>NegZFiniteDouble</code>s,
 * implicit widening conversions from <code>NegZFiniteDouble</code> to
 * other numeric types, and maximum and minimum constant values
 * for <code>NegZFiniteDouble</code>.
 */
object NegZFiniteDouble {

  /**
   * The largest value representable as a finite non-positive <code>Double</code>,
   * which is <code>NegZFiniteDouble(0.0)</code>.
   */
  final val MaxValue: NegZFiniteDouble = NegZFiniteDouble.ensuringValid(0.0)

  /**
   * The smallest value representable as a finite non-positive
   * <code>Double</code>, which is <code>NegZFiniteDouble(-1.7976931348623157E308)</code>.
   */
  final val MinValue: NegZFiniteDouble = NegZFiniteDouble.ensuringValid(Double.MinValue) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[NegZFiniteDouble]</code> given a
   * <code>Double</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite non-positive <code>Double</code>, it will return a <code>NegZFiniteDouble</code>
   * representing that value, wrapped in a <code>Some</code>. Otherwise, the passed <code>Double</code>
   * value is not finite non-positive, so this method will return <code>None</code>.
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
   * @param value the <code>Double</code> to inspect, and if finite non-positive, return
   *     wrapped in a <code>Some[NegZFiniteDouble]</code>.
   * @return the specified <code>Double</code> value wrapped in a
   *     <code>Some[NegZFiniteDouble]</code>, if it is NegZFiniteDouble, else
   *     <code>None</code>.
   */
  def from(value: Double): Option[NegZFiniteDouble] =
    if (NegZFiniteDoubleMacro.isValid(value)) Some(new NegZFiniteDouble(value)) else None

  /**
   * A factory/assertion method that produces a <code>NegZFiniteDouble</code> given a
   * valid <code>Double</code> value, or throws <code>AssertionError</code>,
   * if given an invalid <code>Double</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite non-positive <code>Double</code>, it will return a <code>NegZFiniteDouble</code>
   * representing that value. Otherwise, the passed <code>Double</code> value is not finite non-positive,
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
   * succeeds: a <em>type</em> that promises a <code>Double</code> is finite non-positive.
   * </p>
   *
   * @param value the <code>Double</code> to inspect, and if finite non-positive, return
   *     wrapped in a <code>NegZFiniteDouble</code>.
   * @return the specified <code>Double</code> value wrapped in a
   *     <code>NegZFiniteDouble</code>, if it is finite non-positive, else
   *     throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not finite non-positive
   */
  def ensuringValid(value: Double): NegZFiniteDouble =
    if (NegZFiniteDoubleMacro.isValid(value)) new NegZFiniteDouble(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid NegZFiniteDouble")
    }

  /**
   * A factory/validation method that produces a <code>NegZFiniteDouble</code>, wrapped
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
   * it is a finite non-positive <code>Float</code>, it will return a <code>NegZFiniteDouble</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Float</code> value is not finite non-positive, so this
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
   * @param value the <code>Float</code> to inspect, and if finite non-positive, return
   *     wrapped in a <code>Success(NegZFiniteDouble)</code>.
   * @return the specified <code>Float</code> value wrapped
   *     in a <code>Success(NegZFiniteDouble)</code>, if it is finite non-positive, else a <code>Failure(AssertionError)</code>.
   */
  def tryingValid(value: Double): Try[NegZFiniteDouble] =
    if (NegZFiniteDoubleMacro.isValid(value))
      Success(new NegZFiniteDouble(value))
    else
      Failure(new AssertionError(s"${value.toString()} was not a valid NegZFiniteDouble"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Double</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite non-positive <code>Double</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Double</code> value is finite non-positive, so this
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
   * @param value the `Int` to validate that it is finite non-positive.
   * @return a `Pass` if the specified `Int` value is finite non-positive,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Double` to the given function `f`.
   */
  def passOrElse[E](value: Double)(f: Double => E): Validation[E] =
    if (NegZFiniteDoubleMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>NegZFiniteDouble</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Double</code> value, or if the
   * given <code>Double</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Double</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite non-positive <code>Double</code>, it will return a <code>NegZFiniteDouble</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Double</code> value is not finite non-positive, so this
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
   * @param value the <code>Double</code> to inspect, and if finite non-positive, return
   *     wrapped in a <code>Good(NegZFiniteDouble)</code>.
   * @return the specified <code>Double</code> value wrapped
   *     in a <code>Good(NegZFiniteDouble)</code>, if it is finite non-positive, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Double)(f: Double => B): NegZFiniteDouble Or B =
    if (NegZFiniteDoubleMacro.isValid(value)) Good(NegZFiniteDouble.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>NegZFiniteDouble</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Double</code> value, or if the
   * given <code>Double</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Double</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite non-positive <code>Double</code>, it will return a <code>NegZFiniteDouble</code>
   * representing that value, wrapped in a <code>Right</code>.
   * Otherwise, the passed <code>Double</code> value is not finite non-positive, so this
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
   * @param value the <code>Double</code> to inspect, and if finite non-positive, return
   *     wrapped in a <code>Right(NegZFiniteDouble)</code>.
   * @return the specified <code>Double</code> value wrapped
   *     in a <code>Right(NegZFiniteDouble)</code>, if it is finite non-positive, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Double)(f: Double => L): Either[L, NegZFiniteDouble] =
    if (NegZFiniteDoubleMacro.isValid(value)) Right(NegZFiniteDouble.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Double</code> value is finite non-positive.
   *
   * @param value the <code>Double</code> to inspect, and if finite non-positive, return true.
   * @return true if the specified <code>Double</code> is positive, else false.
   */
  def isValid(value: Double): Boolean = NegZFiniteDoubleMacro.isValid(value)

  /**
   * A factory method that produces a <code>NegZFiniteDouble</code> given a
   * <code>Double</code> value and a default <code>NegZFiniteDouble</code>.
   *
   * <p>
   * This method will inspect the passed <code>Double</code> value and if
   * it is a finite non-positive <code>Double</code>, it will return a <code>NegZFiniteDouble</code>
   * representing that value.  Otherwise, the passed <code>Double</code> value is finite non-positive,
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
   * @param value the <code>Double</code> to inspect, and if finite non-positive, return.
   * @param default the <code>NegZFiniteDouble</code> to return if the passed
   *     <code>Double</code> value is not finite non-positive.
   * @return the specified <code>Double</code> value wrapped in a
   *     <code>NegZFiniteDouble</code>, if it is finite non-positive, else the
   *     <code>default</code> <code>NegZFiniteDouble</code> value.
   */
  def fromOrElse(value: Double, default: => NegZFiniteDouble): NegZFiniteDouble =
    if (NegZFiniteDoubleMacro.isValid(value)) new NegZFiniteDouble(value) else default

  import language.experimental.macros
  import scala.language.implicitConversions

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>NegZFiniteDouble</code> if passed a valid <code>Double</code>
   * literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the
   * specified <code>Double</code> expression at compile time. If
   * the expression is a finite non-positive <code>Double</code> literal,
   * it will return a <code>NegZFiniteDouble</code> representing that value.
   * Otherwise, the passed <code>Double</code> expression is either a literal
   * that is not finite non-positive, or is not a literal, so this method
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
   *     inspect at compile time, and if finite non-positive, to return
   *     wrapped in a <code>NegZFiniteDouble</code> at run time.
   * @return the specified, valid <code>Double</code> literal
   *     value wrapped in a <code>NegZFiniteDouble</code>. (If the
   *     specified expression is not a valid <code>Double</code>
   *     literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Double): NegZFiniteDouble = macro NegZFiniteDoubleMacro.apply

  /**
   * Implicit widening conversion from <code>NegZFiniteDouble</code> to
   * <code>Double</code>.
   *
   * @param pos the <code>NegZFiniteDouble</code> to widen
   * @return the <code>Double</code> value underlying the specified
   *     <code>NegZFiniteDouble</code>
   */
  implicit def widenToDouble(pos: NegZFiniteDouble): Double = pos.value


  /**
   * Implicit widening conversion from <code>NegZFiniteDouble</code> to <code>NegZDouble</code>.
   *
   * @param pos the <code>NegZFiniteDouble</code> to widen
   * @return the <code>Double</code> value underlying the specified <code>NegZFiniteDouble</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NegZDouble</code>.
   */
  implicit def widenToNegZDouble(pos: NegZFiniteDouble): NegZDouble = NegZDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[NegZFiniteDouble] =
    new Ordering[NegZFiniteDouble] {
      def compare(x: NegZFiniteDouble, y: NegZFiniteDouble): Int = x.toDouble.compare(y.toDouble)
    }

}
