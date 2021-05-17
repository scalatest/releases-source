/*
* Copyright 2001-2017 Artima, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.scalactic.anyvals

import scala.language.implicitConversions
import scala.util.{Try, Success, Failure}
import org.scalactic.{Or, Good, Bad}
import org.scalactic.{Validation, Pass, Fail}

/**
 * An <code>AnyVal</code> for numeric <code>Char</code>s.
 *
 * Note: a <code>NumericChar</code> has a value between '0' and '9'.
 *
 * <p>
 * Because <code>NumericChar</code> is an <code>AnyVal</code> it will usually
 * be as efficient as a <code>Char</code>, being boxed only when a
 * <code>Char</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>NumericChar.apply</code> factory method is implemented in terms
 * of a macro that checks literals for validity at compile time. Calling
 * <code>NumericChar.apply</code> with a literal <code>Char</code> value will
 * either produce a valid <code>NumericChar</code> instance at run time or an
 * error at compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; NumericChar('4')
 * res0: org.scalactic.anyvals.NumericChar = NumericChar('4')
 *
 * scala&gt; NumericChar('a')
 * &lt;console&gt;:14: error: NumericChar.apply can only be invoked on Char literals that are numeric, like NumericChar('4').
 *               NumericChar('a')
 *                          ^
 * </pre>
 *
 * <p>
 * <code>NumericChar.apply</code> cannot be used if the value being passed
 * is a variable (<em>i.e.</em>, not a literal), because the macro cannot
 * determine the validity of variables at compile time (just literals).
 * If you try to pass a variable to <code>NumericChar.apply</code>, you'll
 * get a compiler error that suggests you use a different factory method,
 * <code>NumericChar.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = '1'
 * x: Char = 1
 *
 * scala&gt; NumericChar(x)
 * &lt;console&gt;:15: error: NumericChar.apply can only be invoked on Char literals that are numeric, like NumericChar('4'). Please use NumericChar.from instead.
 *               NumericChar(x)
 *                          ^
 * </pre>
 *
 * <p>
 * The <code>NumericChar.from</code> factory method will inspect the value at
 * runtime and return an <code>Option[NumericChar]</code>. If the value is
 * valid, <code>NumericChar.from</code> will return a
 * <code>Some[NumericChar]</code>, else it will return a <code>None</code>.
 * Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; NumericChar.from(x)
 * res3: Option[org.scalactic.anyvals.NumericChar] = Some(NumericChar('1'))
 *
 * scala&gt; val y = 'a'
 * y: Char = a
 *
 * scala&gt; NumericChar.from(y)
 * res4: Option[org.scalactic.anyvals.NumericChar] = None
 * </pre>
 *
 * <p>
 * The <code>NumericChar.apply</code> factory method is marked implicit, so
 * that you can pass literal <code>Char</code>s into methods that require
 * <code>NumericChar</code>, and get the same compile-time checking you get
 * when calling <code>NumericChar.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(ch: NumericChar): Char = ('9' - ch + '0').toChar
 * invert: (ch: org.scalactic.anyvals.NumericChar)Char
 *
 * scala&gt; invert('1')
 * res6: Char = 8
 *
 * scala&gt; scala> invert('9')
 * res7: Char = 0
 *
 * scala&gt; invert('a')
 * &lt;console&gt;:12: error: NumericChar.apply can only be invoked on Char literals that are numeric, like NumericChar('4').
 *               invert('a')
 *                      ^
 * </pre>
 *
 * @param value The <code>Char</code> value underlying this
 *     <code>NumericChar</code>.
 */
final class NumericChar private (val value: Char) extends AnyVal {

  /**
   * A string representation of this <code>NumericChar</code>.
   */
  override def toString: String = s"NumericChar('${value.toString()}')"

  /**
   * Converts this <code>NumericChar</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>NumericChar</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>NumericChar</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>NumericChar</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>NumericChar</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>NumericChar</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>NumericChar</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble

  def max(that: NumericChar): NumericChar =
    if (math.max(value.toInt, that.value.toInt) == value.toInt) this
    else that
  def min(that: NumericChar): NumericChar =
    if (math.min(value.toInt, that.value.toInt) == value.toInt) this
    else that
  def asDigit: Int = Character.digit(value, Character.MAX_RADIX) // from RichChar
  def asDigitPosZInt: PosZInt = PosZInt.ensuringValid(asDigit)

  /**
   * Returns the bitwise negation of this value.
   * @example {{{
   * ~5 == -6
   * // in binary: ~00000101 ==
   * //             11111010
   * }}}
   */
  def unary_~ : Int = ~value
  /** Returns this value, unmodified. */
  def unary_+ : NumericChar = this
  /** Returns the negation of this value. */
  def unary_- : NegZInt = NegZInt.ensuringValid(-value)

  /**
   * Prepends this <code>NumericChar</code>'s value to a string.
   */
  def +(x: String): String = s"${value.toString()}${x.toString()}"

  /**
   * Returns this value bit-shifted left by the specified number of bits,
   *         filling in the new right bits with zeroes.
   * @example {{{ 6 << 3 == 48 // in binary: 0110 << 3 == 0110000 }}}
   */
  def <<(x: Int): Int = value << x
  /**
   * Returns this value bit-shifted left by the specified number of bits,
   *         filling in the new right bits with zeroes.
   * @example {{{ 6 << 3 == 48 // in binary: 0110 << 3 == 0110000 }}}
   */
  def <<(x: Long): Int = value << x
  /**
   * Returns this value bit-shifted right by the specified number of bits,
   *         filling the new left bits with zeroes.
   * @example {{{ 21 >>> 3 == 2 // in binary: 010101 >>> 3 == 010 }}}
   * @example {{{
   * -21 >>> 3 == 536870909
   * // in binary: 11111111 11111111 11111111 11101011 >>> 3 ==
   * //            00011111 11111111 11111111 11111101
   * }}}
   */
  def >>>(x: Int): Int = value >>> x
  /**
   * Returns this value bit-shifted right by the specified number of bits,
   *         filling the new left bits with zeroes.
   * @example {{{ 21 >>> 3 == 2 // in binary: 010101 >>> 3 == 010 }}}
   * @example {{{
   * -21 >>> 3 == 536870909
   * // in binary: 11111111 11111111 11111111 11101011 >>> 3 ==
   * //            00011111 11111111 11111111 11111101
   * }}}
   */
  def >>>(x: Long): Int = value >>> x
  /**
   * Returns this value bit-shifted left by the specified number of bits,
   *         filling in the right bits with the same value as the left-most bit of this.
   *         The effect of this is to retain the sign of the value.
   * @example {{{
   * -21 >> 3 == -3
   * // in binary: 11111111 11111111 11111111 11101011 >> 3 ==
   * //            11111111 11111111 11111111 11111101
   * }}}
   */
  def >>(x: Int): Int = value >> x
  /**
   * Returns this value bit-shifted left by the specified number of bits,
   *         filling in the right bits with the same value as the left-most bit of this.
   *         The effect of this is to retain the sign of the value.
   * @example {{{
   * -21 >> 3 == -3
   * // in binary: 11111111 11111111 11111111 11101011 >> 3 ==
   * //            11111111 11111111 11111111 11111101
   * }}}
   */
  def >>(x: Long): Int = value >> x

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

  /**
   * Returns the bitwise OR of this value and `x`.
   * @example {{{
   * (0xf0 | 0xaa) == 0xfa
   * // in binary:   11110000
   * //            | 10101010
   * //              --------
   * //              11111010
   * }}}
   */
  def |(x: Byte): Int = value | x
  /**
   * Returns the bitwise OR of this value and `x`.
   * @example {{{
   * (0xf0 | 0xaa) == 0xfa
   * // in binary:   11110000
   * //            | 10101010
   * //              --------
   * //              11111010
   * }}}
   */
  def |(x: Short): Int = value | x
  /**
   * Returns the bitwise OR of this value and `x`.
   * @example {{{
   * (0xf0 | 0xaa) == 0xfa
   * // in binary:   11110000
   * //            | 10101010
   * //              --------
   * //              11111010
   * }}}
   */
  def |(x: Char): Int = value | x
  /**
   * Returns the bitwise OR of this value and `x`.
   * @example {{{
   * (0xf0 | 0xaa) == 0xfa
   * // in binary:   11110000
   * //            | 10101010
   * //              --------
   * //              11111010
   * }}}
   */
  def |(x: Int): Int = value | x
  /**
   * Returns the bitwise OR of this value and `x`.
   * @example {{{
   * (0xf0 | 0xaa) == 0xfa
   * // in binary:   11110000
   * //            | 10101010
   * //              --------
   * //              11111010
   * }}}
   */
  def |(x: Long): Long = value | x

  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary:   11110000
   * //            & 10101010
   * //              --------
   * //              10100000
   * }}}
   */
  def &(x: Byte): Int = value & x
  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary:   11110000
   * //            & 10101010
   * //              --------
   * //              10100000
   * }}}
   */
  def &(x: Short): Int = value & x
  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary:   11110000
   * //            & 10101010
   * //              --------
   * //              10100000
   * }}}
   */
  def &(x: Char): Int = value & x
  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary:   11110000
   * //            & 10101010
   * //              --------
   * //              10100000
   * }}}
   */
  def &(x: Int): Int = value & x
  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary:   11110000
   * //            & 10101010
   * //              --------
   * //              10100000
   * }}}
  */
  def &(x: Long): Long = value & x

  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary:   11110000
   * //            ^ 10101010
   * //              --------
   * //              01011010
   * }}}
   */
  def ^(x: Byte): Int = value ^ x
  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary:   11110000
   * //            ^ 10101010
   * //              --------
   * //              01011010
   * }}}
   */
  def ^(x: Short): Int = value ^ x
  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary:   11110000
   * //            ^ 10101010
   * //              --------
   * //              01011010
   * }}}
   */
  def ^(x: Char): Int = value ^ x
  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary:   11110000
   * //            ^ 10101010
   * //              --------
   * //              01011010
   * }}}
   */
  def ^(x: Int): Int = value ^ x
  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary:   11110000
   * //            ^ 10101010
   * //              --------
   * //              01011010
   * }}}
   */
  def ^(x: Long): Long = value ^ x

  /** Returns the sum of this value and `x`. */
  def +(x: Byte): Int = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Short): Int = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Char): Int = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Int): Int = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Long): Long = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Float): Float = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Double): Double = value + x

  /** Returns the difference of this value and `x`. */
  def -(x: Byte): Int = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Short): Int = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Char): Int = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Int): Int = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Long): Long = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Float): Float = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Double): Double = value - x

  /** Returns the product of this value and `x`. */
  def *(x: Byte): Int = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Short): Int = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Char): Int = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Int): Int = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Long): Long = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Float): Float = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Double): Double = value * x

  /** Returns the quotient of this value and `x`. */
  def /(x: Byte): Int = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Short): Int = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Char): Int = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Int): Int = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Long): Long = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Float): Float = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Double): Double = value / x

  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Byte): Int = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Short): Int = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Char): Int = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Int): Int = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Long): Long = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Float): Float = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Double): Double = value % x
}

/**
 * The companion object for <code>NumericChar</code> that offers factory
 * methods that produce <code>NumericChar</code>s and maximum and minimum
 * constant values for <code>NumericChar</code>.
 */
object NumericChar {

  /**
   * A factory method that produces an <code>Option[NumericChar]</code> given
   * a <code>Char</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Char</code> value and if
   * it is a numeric <code>Char</code>, <em>i.e.</em>, between '0' and '9',
   * it will return a <code>NumericChar</code> representing that value,
   * wrapped in a <code>Some</code>. Otherwise, the passed <code>Char</code>
   * value is not a numeric character value, so this method will return
   * <code>None</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Char</code> literals at compile time, whereas <code>from</code>
   * inspects <code>Char</code> values at run time.
   * </p>
   *
   * @param value the <code>Char</code> to inspect, and if numeric, return
   *     wrapped in a <code>Some[NumericChar]</code>.
   * @return the specified <code>Char</code> value wrapped
   *     in a <code>Some[NumericChar]</code>, if it is numeric, else
   *     <code>None</code>.
   */
  def from(value: Char): Option[NumericChar] =
    if (NumericCharMacro.isValid(value)) Some(new NumericChar(value)) else None

  /**
   * A factory/assertion method that produces a <code>NumericChar</code> given
   * a valid <code>Char</code> value, or throws <code>AssertionError</code>,
   * if given an invalid <code>Char</code> value.
   *
   * Note: you should use this method only when you are convinced that it will
   * always succeed, i.e., never throw an exception. It is good practice to
   * add a comment near the invocation of this method indicating ''why'' you
   * think it will always succeed to document your reasoning. If you are not
   * sure an `ensuringValid` call will always succeed, you should use one of
   * the other factory or validation methods provided on this object instead:
   * `isValid`, `tryingValid`, `passOrElse`, `goodOrElse`, or `rightOrElse`.
   *
   * <p>
   * This method will inspect the passed <code>Char</code> value and if
   * it is a numeric <code>Char</code>, it will return a
   * <code>NumericChar</code> representing that value.  Otherwise, the
   * passed <code>Char</code> value is not numeric, so this method will
   * throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Char</code> literals at compile time, whereas this method inspects
   * <code>Char</code> values at run time.
   * It differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises a <code>Char</code> is numeric.
   * </p>
   *
   * @param value the <code>Char</code> to inspect, and if numeric, return
   *     wrapped in a <code>NumericChar</code>.
   * @return the specified <code>Char</code> value wrapped
   *     in a <code>NumericChar</code>, if it is numeric, else throws
   *     <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not numeric
   */
  def ensuringValid(value: Char): NumericChar =
    if (NumericCharMacro.isValid(value)) new NumericChar(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid NumericChar")
    }

  import scala.language.experimental.macros

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>NumericChar</code> if passed a valid <code>Char</code> literal,
   * otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the specified
   * <code>Char</code> expression at compile time. If the expression is a
   * numeric <code>Char</code> literal, <em>i.e.</em>, a value between '0'
   * and '9', it will return a <code>NumericChar</code> representing that
   * value. Otherwise, the passed <code>Char</code> expression is either a
   * literal that is not numeric, or is not a literal, so this method will
   * give a compiler error.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>from</code> factory method
   * in that this method is implemented via a macro that inspects
   * <code>Char</code> literals at compile time, whereas <code>from</code>
   * inspects <code>Char</code> values at run time.
   * </p>
   *
   * @param value the <code>Char</code> literal expression to inspect at
   *     compile time, and if numeric, to return wrapped in a
   *     <code>NumericChar</code> at run time.
   * @return the specified, valid <code>Char</code> literal value wrapped
   *     in a <code>NumericChar</code>. (If the specified expression is not
   *     a valid <code>Char</code> literal, the invocation of this method
   *     will not compile.)
   */
  implicit def apply(value: Char): NumericChar = macro NumericCharMacro.apply

  /** The smallest value representable as a NumericChar. */
  final val MinValue: NumericChar = NumericChar.ensuringValid('0')

  /** The largest value representable as a NumericChar. */
  final val MaxValue: NumericChar = NumericChar.ensuringValid('9')

  /**
   * A factory/validation method that produces a <code>NumericChar</code>,
   * wrapped in a <code>Good</code>, given a valid <code>Char</code> value,
   * or if the given <code>Char</code> is invalid, an error value of type
   * <code>B</code> produced by passing the given <em>invalid</em>
   * <code>Char</code> value to the given function <code>f</code>, wrapped
   * in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Char</code> value and if
   * it is a numeric <code>Char</code>, it will return a
   * <code>NumericChar</code> representing that value, wrapped in a
   * <code>Good</code>. Otherwise, the passed <code>Char</code> value is
   * NOT numeric, so this method will return a result of type <code>B</code>
   * obtained by passing the invalid <code>Char</code> value to the given
   * function <code>f</code>, wrapped in a `Bad`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Char</code> literals at compile time, whereas this method inspects
   * <code>Char</code> values at run time.
   * </p>
   *
   * @param value the <code>Char</code> to inspect, and if numeric, return
   *     wrapped in a <code>Good(NumericChar)</code>.
   * @return the specified <code>Char</code> value wrapped
   *     in a <code>Good(NumericChar)</code>, if it is numeric, else a
   *     <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Char)(f: Char => B): NumericChar Or B =
    if (NumericCharMacro.isValid(value)) Good(NumericChar.ensuringValid(value))
    else Bad(f(value))

  /**
   * A validation method that produces a <code>Pass</code> given a valid
   * <code>Char</code> value, or an error value of type <code>E</code>
   * produced by passing the given <em>invalid</em> <code>Char</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Char</code> value and if
   * it is a numeric <code>Char</code> (between '0' and '9'), it will return
   * a <code>Pass</code>. Otherwise, the passed <code>Char</code> value is
   * non-numeric, so this method will return a result of type <code>E</code>
   * obtained by passing the invalid <code>Char</code> value to the given
   * function <code>f</code>, wrapped in a `Fail`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Char</code> literals at compile time, whereas this method inspects
   * <code>Char</code> values at run time.
   * </p>
   *
   * @param value the `Char` to validate that it is numeric.
   * @return a `Pass` if the specified `Char` value is numeric,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Char` to the given function `f`.
   */
  def passOrElse[E](value: Char)(f: Char => E): Validation[E] =
    if (NumericCharMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>NumericChar</code>,
   * wrapped in a <code>Right</code>, given a valid <code>Char</code> value,
   * or if the given <code>Char</code> is invalid, an error value of type
   * <code>L</code> produced by passing the given <em>invalid</em>
   * <code>Char</code> value to the given function <code>f</code>, wrapped
   * in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Char</code> value and if
   * it is a numeric <code>Char</code> (between '0' and '9'), it will return a
   * <code>NumericChar</code> representing that value, wrapped in a
   * <code>Right</code>. Otherwise, the passed <code>Char</code> value is
   * NOT numeric, so this method will return a result of type <code>L</code>
   * obtained by passing the invalid <code>Char</code> value to the given
   * function <code>f</code>, wrapped in a `Left`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Char</code> literals at compile time, whereas this method inspects
   * <code>Char</code> values at run time.
   * </p>
   *
   * @param value the <code>Char</code> to inspect, and if numeric, return
   *     wrapped in a <code>Right(NumericChar)</code>.
   * @return the specified <code>Char</code> value wrapped
   *     in a <code>Right(NumericChar)</code>, if it is numeric, else a
   *     <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Char)(f: Char => L): Either[L, NumericChar] =
    if (NumericCharMacro.isValid(value)) Right(NumericChar.ensuringValid(value))
    else Left(f(value))

  /**
   * A factory/validation method that produces a <code>NumericChar</code>,
   * wrapped in a <code>Success</code>, given a valid <code>Char</code>
   * value, or if the given <code>Char</code> is invalid, an
   * <code>AssertionError</code>, wrapped in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Char</code> value and if
   * it represents a numeric value (between '0' and '9'), it will return a
   * <code>NumericChar</code> representing that value, wrapped in a
   * <code>Success</code>. Otherwise, the passed <code>Char</code> value is
   * not numeric, so this method will return an <code>AssertionError</code>,
   * wrapped in a <code>Failure</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Char</code> literals at compile time, whereas this method inspects
   * <code>Char</code> values at run time.
   * </p>
   *
   * @param value the <code>Char</code> to inspect, and if numeric, return
   *     wrapped in a <code>Success(NumericChar)</code>.
   * @return the specified <code>Char</code> value wrapped
   *     in a <code>Success(NumericChar)</code>, if it is numeric, else a
   *     <code>Failure(AssertionError)</code>.
   */
   def tryingValid(value: Char): Try[NumericChar] =
     if (NumericCharMacro.isValid(value)) Success(new NumericChar(value))
     else Failure(new AssertionError(s"${value.toString()} was not a valid NumericChar"))

  /**
   * A factory method that produces a <code>NumericChar</code> given a
   * <code>Char</code> value and a default <code>NumericChar</code>.
   *
   * <p>
   * This method will inspect the passed <code>Char</code> value and if
   * it is a valid numeric Char (between '0' and '9'), it will return a
   * <code>NumericChar</code> representing that value. Otherwise, the passed
   * <code>Char</code> value is a non-digit character, so this method will
   * return the passed <code>default</code> value.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Char</code> literals at compile time, whereas
   * <code>fromOrElse</code> inspects <code>Char</code> values at run time.
   * </p>
   *
   * @param value the <code>Char</code> to inspect, and if numeric, return.
   * @param default the <code>NumericChar</code> to return if the passed
   *     <code>Char</code> value is not numeric.
   * @return the specified <code>Char</code> value wrapped in a
   *     <code>NumericChar</code>, if it is numeric, else the
   *     <code>default</code> <code>NumericChar</code> value.
   */
  def fromOrElse(value: Char, default: => NumericChar): NumericChar =
    if (NumericCharMacro.isValid(value)) new NumericChar(value)
    else default

  /**
   * A predicate method that returns true if a given <code>Char</code> value
   * is between '0' and '9'.
   *
   * @param value the <code>Char</code> to inspect, and if numeric, return true.
   * @return true if the specified <code>Char</code> is numeric, else false.
   */
  def isValid(value: Char): Boolean = NumericCharMacro.isValid(value)

  /** Language mandated coercions from Char to "wider" types. */

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>Int</code>.
   *
   * @param value the <code>NumericChar</code> to widen
   * @return the <code>Int</code> widen from the specified  <code>NumericChar</code>.
   */
  implicit def widenToInt(value: NumericChar): Int = value.toInt

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>Long</code>.
   *
   * @param value the <code>NumericChar</code> to widen
   * @return the <code>Long</code> widen from the specified  <code>NumericChar</code>.
   */
  implicit def widenToLong(value: NumericChar): Long = value.toLong

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>Float</code>.
   *
   * @param value the <code>NumericChar</code> to widen
   * @return the <code>Float</code> widen from the specified  <code>NumericChar</code>.
   */
  implicit def widenToFloat(value: NumericChar): Float = value.toFloat

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>Double</code>.
   *
   * @param value the <code>NumericChar</code> to widen
   * @return the <code>Double</code> widen from the specified <code>NumericChar</code>.
   */
  implicit def widenToDouble(value: NumericChar): Double = value.toDouble


  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>FiniteFloat</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Float</code> and wrapped in a <code>FiniteFloat</code>.
   */
  implicit def widenToFiniteFloat(pos: NumericChar): FiniteFloat = FiniteFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>FiniteDouble</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Double</code> and wrapped in a <code>FiniteDouble</code>.
   */
  implicit def widenToFiniteDouble(pos: NumericChar): FiniteDouble = FiniteDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosInt</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Int</code> and wrapped in a <code>PosInt</code>.
   */
  implicit def widenToPosInt(pos: NumericChar): PosInt = PosInt.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosLong</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Long</code> and wrapped in a <code>PosLong</code>.
   */
  implicit def widenToPosLong(pos: NumericChar): PosLong = PosLong.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosFloat</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosFloat</code>.
   */
  implicit def widenToPosFloat(pos: NumericChar): PosFloat = PosFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosDouble</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosDouble</code>.
   */
  implicit def widenToPosDouble(pos: NumericChar): PosDouble = PosDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosZInt</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Int</code> and wrapped in a <code>PosZInt</code>.
   */
  implicit def widenToPosZInt(pos: NumericChar): PosZInt = PosZInt.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosZLong</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Long</code> and wrapped in a <code>PosZLong</code>.
   */
  implicit def widenToPosZLong(pos: NumericChar): PosZLong = PosZLong.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosZFloat</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosZFloat</code>.
   */
  implicit def widenToPosZFloat(pos: NumericChar): PosZFloat = PosZFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosZDouble</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosZDouble</code>.
   */
  implicit def widenToPosZDouble(pos: NumericChar): PosZDouble = PosZDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosFiniteFloat</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosFiniteFloat</code>.
   */
  implicit def widenToPosFiniteFloat(pos: NumericChar): PosFiniteFloat = PosFiniteFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosFiniteDouble</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosFiniteDouble</code>.
   */
  implicit def widenToPosFiniteDouble(pos: NumericChar): PosFiniteDouble = PosFiniteDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosZFiniteFloat</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosZFiniteFloat</code>.
   */
  implicit def widenToPosZFiniteFloat(pos: NumericChar): PosZFiniteFloat = PosZFiniteFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NumericChar</code> to <code>PosZFiniteDouble</code>.
   *
   * @param pos the <code>NumericChar</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NumericChar</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosZFiniteDouble</code>.
   */
  implicit def widenToPosZFiniteDouble(pos: NumericChar): PosZFiniteDouble = PosZFiniteDouble.ensuringValid(pos.value)


}
