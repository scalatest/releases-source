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
 * An <code>AnyVal</code> for non-positive <code>Long</code>s.
 *
 * 
 *
 * <p>
 * Because <code>NegZLong</code> is an <code>AnyVal</code> it
 * will usually be as efficient as an <code>Long</code>, being
 * boxed only when an <code>Long</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>NegZLong.apply</code> factory method is implemented
 * in terms of a macro that checks literals for validity at
 * compile time. Calling <code>NegZLong.apply</code> with a
 * literal <code>Long</code> value will either produce a valid
 * <code>NegZLong</code> instance at run time or an error at
 * compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; NegZLong(-42L)
 * res0: org.scalactic.anyvals.NegZLong = NegZLong(-42L)
 *
 * scala&gt; NegZLong(-1L)
 * &lt;console&gt;:14: error: NegZLong.apply can only be invoked on a non-positive (i <= 0L) integer literal, like NegZLong(-42L).
 *               NegZLong(-1L)
 *                      ^
 * </pre>
 *
 * <p>
 * <code>NegZLong.apply</code> cannot be used if the value being
 * passed is a variable (<em>i.e.</em>, not a literal), because
 * the macro cannot determine the validity of variables at
 * compile time (just literals). If you try to pass a variable
 * to <code>NegZLong.apply</code>, you'll get a compiler error
 * that suggests you use a different factor method,
 * <code>NegZLong.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = -42L
 * x: Long = -42
 *
 * scala&gt; NegZLong(x)
 * &lt;console&gt;:15: error: NegZLong.apply can only be invoked on an long literal, like NegZLong(-42L). Please use NegZLong.from instead.
 *               NegZLong(x)
 *                      ^
 * </pre>
 *
 * <p>
 * The <code>NegZLong.from</code> factory method will inspect the
 * value at runtime and return an
 * <code>Option[NegZLong]</code>. If the value is valid,
 * <code>NegZLong.from</code> will return a
 * <code>Some[NegZLong]</code>, else it will return a
 * <code>None</code>.  Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; NegZLong.from(x)
 * res3: Option[org.scalactic.anyvals.NegZLong] = Some(NegZLong(-42))
 *
 * scala&gt; val y = 1L
 * y: Long = 1
 *
 * scala&gt; NegZLong.from(y)
 * res4: Option[org.scalactic.anyvals.NegZLong] = None
 * </pre>
 *
 * <p>
 * The <code>NegZLong.apply</code> factory method is marked
 * implicit, so that you can pass literal <code>Long</code>s
 * into methods that require <code>NegZLong</code>, and get the
 * same compile-time checking you get when calling
 * <code>NegZLong.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: NegZLong): Long = Long.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.NegZLong)Long
 *
 * scala&gt; invert(1L)
 * res5: Long = 9223372036854775806
 *
 * scala&gt; invert(Long.MaxValue)
 * res6: Long = 0
 *
 * scala&gt; invert(1L)
 * &lt;console&gt;:15: error: NegZLong.apply can only be invoked on a non-positive (i <= 0L) integer literal, like NegZLong(-42L).
 *               invert(1L)
 *                      ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the <code>NegZLong</code>
 * companion object also defines implicit widening conversions
 * when either no loss of precision will occur or a similar
 * conversion is provided in Scala. (For example, the implicit
 * conversion from <code>Long</code> to </code>Double</code> in
 * Scala can lose precision.) This makes it convenient to use a
 * <code>NegZLong</code> where a <code>Long</code> or wider type
 * is needed. An example is the subtraction in the body of the
 * <code>invert</code> method defined above, <code>Long.MaxValue
 * - pos</code>. Although <code>Long.MaxValue</code> is a
 * <code>Long</code>, which has no <code>-</code> method that
 * takes a <code>NegZLong</code> (the type of <code>pos</code>),
 * you can still subtract <code>pos</code>, because the
 * <code>NegZLong</code> will be implicitly widened to
 * <code>Long</code>.
 * </p>
 *
 * @param value The <code>Long</code> value underlying this <code>NegZLong</code>.
 */
final class NegZLong private (val value: Long) extends AnyVal {

  /**
   * A string representation of this <code>NegZLong</code>.
   */
  override def toString: String = s"NegZLong(${value}L)"

  /**
   * Converts this <code>NegZLong</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>NegZLong</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>NegZLong</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>NegZLong</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>NegZLong</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>NegZLong</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>NegZLong</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble

 /**
  * Returns the bitwise negation of this value.
  * @example {{{
  * ~5 == -6
  * // in binary: ~00000101 ==
  * //             11111010
  * }}}
  */
  def unary_~ : Long = ~value
  /** Returns this value, unmodified. */
  def unary_+ : NegZLong = this
  /** Returns the negation of this value. */
  def unary_- : Long = -value

  /**
   * Converts this <code>NegZLong</code>'s value to a string then concatenates the given string.
   */
  def +(x: String): String = s"${value.toString()}$x"

  /**
   * Returns this value bit-shifted left by the specified number of bits,
   *         filling in the new right bits with zeroes.
   * @example {{{ 6 << 3 == 48 // in binary: 0110 << 3 == 0110000 }}}
   */
  def <<(x: Int): Long = value << x
  /**
   * Returns this value bit-shifted left by the specified number of bits,
   *         filling in the new right bits with zeroes.
   * @example {{{ 6 << 3 == 48 // in binary: 0110 << 3 == 0110000 }}}
   */
  def <<(x: Long): Long = value << x
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
  def >>>(x: Int): Long = value >>> x
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
  def >>>(x: Long): Long = value >>> x
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
  def >>(x: Int): Long = value >> x
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
  def >>(x: Long): Long = value >> x

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
  def |(x: Byte): Long = value | x
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
  def |(x: Short): Long = value | x
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
  def |(x: Char): Long = value | x
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
  def |(x: Int): Long = value | x
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
  def &(x: Byte): Long = value & x
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
  def &(x: Short): Long = value & x
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
  def &(x: Char): Long = value & x
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
  def &(x: Int): Long = value & x
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
  def ^(x: Byte): Long = value ^ x
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
  def ^(x: Short): Long = value ^ x
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
  def ^(x: Char): Long = value ^ x
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
  def ^(x: Int): Long = value ^ x
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
  def +(x: Byte): Long = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Short): Long = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Char): Long = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Int): Long = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Long): Long = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Float): Float = value + x
  /** Returns the sum of this value and `x`. */
  def +(x: Double): Double = value + x

  /** Returns the difference of this value and `x`. */
  def -(x: Byte): Long = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Short): Long = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Char): Long = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Int): Long = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Long): Long = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Float): Float = value - x
  /** Returns the difference of this value and `x`. */
  def -(x: Double): Double = value - x

  /** Returns the product of this value and `x`. */
  def *(x: Byte): Long = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Short): Long = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Char): Long = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Int): Long = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Long): Long = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Float): Float = value * x
  /** Returns the product of this value and `x`. */
  def *(x: Double): Double = value * x

  /** Returns the quotient of this value and `x`. */
  def /(x: Byte): Long = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Short): Long = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Char): Long = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Int): Long = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Long): Long = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Float): Float = value / x
  /** Returns the quotient of this value and `x`. */
  def /(x: Double): Double = value / x

  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Byte): Long = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Short): Long = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Char): Long = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Int): Long = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Long): Long = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Float): Float = value % x
  /** Returns the remainder of the division of this value by `x`. */
  def %(x: Double): Double = value % x

  // Stuff from RichLong:
  /**
   * Returns a string representation of this <code>NegZLong</code>'s underlying <code>Long</code>
   * as an unsigned integer in base&nbsp;2.
   *
   * <p>
   * The unsigned <code>long</code> value is this <code>NegZLong</code>'s underlying <code>Long</code> plus
   * 2<sup>64</sup> if the underlying <code>Long</code> is negative; otherwise, it is
   * equal to the underlying <code>Long</code>.  This value is converted to a string of
   * ASCII digits in binary (base&nbsp;2) with no extra leading
   * <code>0</code>s.  If the unsigned magnitude is zero, it is
   * represented by a single zero character <code>'0'</code>
   * (<code>'&#92;u0030'</code>); otherwise, the first character of
   * the representation of the unsigned magnitude will not be the
   * zero character. The characters <code>'0'</code>
   * (<code>'&#92;u0030'</code>) and <code>'1'</code>
   * (<code>'&#92;u0031'</code>) are used as binary digits.
   * </p>
   *
   * @return  the string representation of the unsigned <code>long</code>
   *          value represented by this <code>NegZLong</code>'s underlying <code>Long</code> in binary (base&nbsp;2).
   */
  def toBinaryString: String = java.lang.Long.toBinaryString(value)

  /**
   * Returns a string representation of this <code>NegZLong</code>'s underlying <code>Long</code>
   * as an unsigned integer in base&nbsp;16.
   *
   * <p>
   * The unsigned <code>long</code> value is this <code>NegZLong</code>'s underlying <code>Long</code> plus
   * 2<sup>64</sup> if the underlying <code>Long</code> is negative; otherwise, it is
   * equal to the underlying <code>Long</code>.  This value is converted to a string of
   * ASCII digits in hexadecimal (base&nbsp;16) with no extra
   * leading <code>0</code>s.  If the unsigned magnitude is zero, it
   * is represented by a single zero character <code>'0'</code>
   * (<code>'&#92;u0030'</code>); otherwise, the first character of
   * the representation of the unsigned magnitude will not be the
   * zero character. The following characters are used as
   * hexadecimal digits:
   * </p>
   *
   * <blockquote>
   *  <code>0123456789abcdef</code>
   * </blockquote>
   *
   * <p>
   * These are the characters <code>'&#92;u0030'</code> through
   * <code>'&#92;u0039'</code> and  <code>'&#92;u0061'</code> through
   * <code>'&#92;u0066'</code>.  If uppercase letters are desired,
   * the <code>toUpperCase</code> method may be called
   * on the result.
   * </p>
   *
   * @return  the string representation of the unsigned <code>long</code>
   *          value represented by this <code>NegZLong</code>'s underlying <code>Long</code> in hexadecimal
   *          (base&nbsp;16).
   */
  def toHexString: String = java.lang.Long.toHexString(value)

  /**
   * Returns a string representation of this <code>NegZLong</code>'s underlying <code>Long</code>
   * as an unsigned integer in base&nbsp;8.
   *
   * <p>
   * The unsigned <code>long</code> value is this <code>NegZLong</code>'s underlying <code>Long</code> plus
   * 2<sup>64</sup> if the underlying <code>Long</code> is negative; otherwise, it is
   * equal to the underlying <code>Long</code>.  This value is converted to a string of
   * ASCII digits in octal (base&nbsp;8) with no extra leading
   * <code>0</code>s.
   * </p>
   *
   * <p>
   * If the unsigned magnitude is zero, it is represented by a
   * single zero character <code>'0'</code>
   * (<code>'&#92;u0030'</code>); otherwise, the first character of
   * the representation of the unsigned magnitude will not be the
   * zero character. The following characters are used as octal
   * digits:
   * </p>
   *
   * <blockquote>
   *  <code>01234567</code>
   * </blockquote>
   *
   * <p>
   * These are the characters <code>'&#92;u0030'</code> through
   * <code>'&#92;u0037'</code>.
   * </p>
   *
   * @return  the string representation of the unsigned <code>long</code>
   *          value represented by this <code>NegZLong</code>'s underlying <code>Long</code> in octal (base&nbsp;8).
   */
  def toOctalString: String = java.lang.Long.toOctalString(value)

  /**
   * Returns <code>this</code> if <code>this &gt; that</code> or <code>that</code> otherwise.
   */
  def max(that: NegZLong): NegZLong = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: NegZLong): NegZLong = if (math.min(value, that.value) == value) this else that

  // adapted from RichInt:
  /**
   * Create a <code>Range</code> from this <code>NegZLong</code> value
   * until the specified <code>end</code> (exclusive) with step value 1.
   *
   * @param end The final bound of the range to make.
   * @return A [[scala.collection.immutable.NumericRange.Exclusive[Long]]] from `this` up to but
   * not including `end`.
   */
  def until(end: Long): NumericRange.Exclusive[Long] = value.until(end)

  /**
   * Create a <code>Range</code> from this <code>NegZLong</code> value
   * until the specified <code>end</code> (exclusive) with the specified <code>step</code> value.
   *
   * @param end The final bound of the range to make.
   * @param end The final bound of the range to make.
   * @param step The number to increase by for each step of the range.
   * @return A [[scala.collection.immutable.NumericRange.Exclusive[Long]]] from `this` up to but
   * not including `end`.
   */
  def until(end: Long, step: Long): NumericRange.Exclusive[Long] =
    value.until(end, step)

  /**
   * Create an inclusive <code>Range</code> from this <code>NegZLong</code> value
   * to the specified <code>end</code> with step value 1.
   *
   * @param end The final bound of the range to make.
   * @return A [[scala.collection.immutable.NumericRange.Inclusive[Long]]] from `'''this'''` up to
   * and including `end`.
   */
  def to(end: Long): NumericRange.Inclusive[Long] = value.to(end)

  /**
   * Create an inclusive <code>Range</code> from this <code>NegZLong</code> value
   * to the specified <code>end</code> with the specified <code>step</code> value.
   *
   * @param end The final bound of the range to make.
   * @param step The number to increase by for each step of the range.
   * @return A [[scala.collection.immutable.NumericRange.Inclusive[Long]]] from `'''this'''` up to
   * and including `end`.
   */
  def to(end: Long, step: Long): NumericRange.Inclusive[Long] =
    value.to(end, step)

  /**
   * Applies the passed <code>Long =&gt; Long</code> function to the underlying <code>Long</code>
   * value, and if the result is positive, returns the result wrapped in a <code>NegZLong</code>,
   * else throws <code>AssertionError</code>.
   *
   * <p>
   * This method will inspect the result of applying the given function to this
   * <code>NegZLong</code>'s underlying <code>Long</code> value and if the result
   * is non-positive, it will return a <code>NegZLong</code> representing that value.
   * Otherwise, the <code>Long</code> value returned by the given function is
   * not non-positive, this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Long</code> is non-positive.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>NegZLong</code>'s
   * value will not overflow. Instead of overflowing silently like <code>Long</code>, this
   * method will signal an overflow with a loud <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Long =&gt; Long</code> function to apply to this <code>NegZLong</code>'s
   *     underlying <code>Long</code> value.
   * @return the result of applying this <code>NegZLong</code>'s underlying <code>Long</code> value to
   *     to the passed function, wrapped in a <code>NegZLong</code> if it is non-positive (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>NegZLong</code>'s underlying <code>Long</code> value to
   *     to the passed function is not positive.
   */
  def ensuringValid(f: Long => Long): NegZLong = {
    val candidateResult: Long = f(value)
    if (NegZLongMacro.isValid(candidateResult)) new NegZLong(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid NegZLong")
  }
}

/**
 * The companion object for <code>NegZLong</code> that offers
 * factory methods that produce <code>NegZLong</code>s, implicit
 * widening conversions from <code>NegZLong</code> to other
 * numeric types, and maximum and minimum constant values for
 * <code>NegZLong</code>.
 */
object NegZLong {
  /**
   * The largest value representable as a non-positive
   * <code>Long</code>, which is <code>NegZLong(0L)</code>.
   */
  final val MaxValue: NegZLong = NegZLong.ensuringValid(0L)

  /**
   * The smallest value representable as a positive
   * <code>Long</code>, which is <code>NegZLong(-9223372036854775808)</code>.
   */
  final val MinValue: NegZLong = NegZLong.ensuringValid(Long.MinValue) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[NegZLong]</code> given a
   * <code>Long</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a non-positive <code>Long</code>, it will return a <code>NegZLong</code> representing that value,
   * wrapped in a <code>Some</code>. Otherwise, the passed <code>Long</code>
   * value is not non-positive, so this method will return <code>None</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Long</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Long</code> values at run time.
   * </p>
   *
   * @param value the <code>Long</code> to inspect, and if non-positive, return
   *     wrapped in a <code>Some[NegZLong]</code>.
   * @return the specified <code>Long</code> value wrapped in a
   *     <code>Some[NegZLong]</code>, if it is non-positive, else
   *     <code>None</code>.
   */
  def from(value: Long): Option[NegZLong] =
    if (NegZLongMacro.isValid(value)) Some(new NegZLong(value)) else None

  /**
   * A factory/assertion method that produces an <code>NegZLong</code> given a
   * valid <code>Long</code> value, or throws <code>AssertionError</code>,
   * if given an invalid <code>Long</code> value.
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
   * This method will inspect the passed <code>Long</code> value and if
   * it is a non-positive <code>Long</code>, it will return a <code>NegZLong</code> representing that value.
   * Otherwise, the passed <code>Long</code> value is not non-positive, so
   * this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Long</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Long</code> values at run time.
   * It differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises a <code>Long</code> is positive.
   * </p>
   *
   * @param value the <code>Long</code> to inspect, and if non-positive, return
   *     wrapped in a <code>NegZLong</code>.
   * @return the specified <code>Long</code> value wrapped in a
   *     <code>NegZLong</code>, if it is non-positive, else
   *     throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not non-positive
   */
  def ensuringValid(value: Long): NegZLong =
    if (NegZLongMacro.isValid(value)) new NegZLong(value) else {
      throw new AssertionError(s"${value.toString()}  was not a valid NegZLong")
    }

  /**
   * A factory/validation method that produces a <code>NegZLong</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Long</code> value, or if the
   * given <code>Long</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a non-positive <code>Long</code>, it will return a <code>NegZLong</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Long</code> value is not non-positive, so this
   * method will return an <code>AssertionError</code>, wrapped in a <code>Failure</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Long</code> literals at compile time, whereas this method inspects
   * <code>Long</code> values at run time.
   * </p>
   *
   * @param value the <code>Long</code> to inspect, and if non-positive, return
   *     wrapped in a <code>Success(NegZLong)</code>.
   * @return the specified <code>Long</code> value wrapped
   *     in a <code>Success(NegZLong)</code>, if it is non-positive, else a <code>Failure(AssertionError)</code>.
   */
   def tryingValid(value: Long): Try[NegZLong] =
     if (NegZLongMacro.isValid(value))
       Success(new NegZLong(value))
     else
       Failure(new AssertionError(s"${value.toString()} was not a valid NegZLong"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Long</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a non-positive <code>Long</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Long</code> value is non-positive, so this
   * method will return a result of type <code>E</code> obtained by passing
   * the invalid <code>Long</code> value to the given function <code>f</code>,
   * wrapped in a `Fail`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Long</code> literals at compile time, whereas this method inspects
   * <code>Long</code> values at run time.
   * </p>
   *
   * @param value the `Long` to validate that it is non-positive.
   * @return a `Pass` if the specified `Long` value is non-positive,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Long` to the given function `f`.
   */
  def passOrElse[E](value: Long)(f: Long => E): Validation[E] =
    if (NegZLongMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>NegZLong</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Long</code> value, or if the
   * given <code>Long</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Long</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a non-positive <code>Long</code>, it will return a <code>NegZLong</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Long</code> value is not non-positive, so this
   * method will return a result of type <code>B</code> obtained by passing
   * the invalid <code>Long</code> value to the given function <code>f</code>,
   * wrapped in a `Bad`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Long</code> literals at compile time, whereas this method inspects
   * <code>Long</code> values at run time.
   * </p>
   *
   * @param value the <code>Long</code> to inspect, and if non-positive, return
   *     wrapped in a <code>Good(NegZLong)</code>.
   * @return the specified <code>Long</code> value wrapped
   *     in a <code>Good(NegZLong)</code>, if it is non-positive, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Long)(f: Long => B): NegZLong Or B =
    if (NegZLongMacro.isValid(value)) Good(NegZLong.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>NegZLong</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-positive <code>Int</code>, it will return a <code>NegZLong</code>
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
   *     wrapped in a <code>Right(NegZLong)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Right(NegZLong)</code>, if it is non-positive, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Long)(f: Long => L): Either[L, NegZLong] =
    if (NegZLongMacro.isValid(value)) Right(NegZLong.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Long</code> value is non-positive.
   *
   * @param value the <code>Long</code> to inspect, and if non-positive, return true.
   * @return true if the specified <code>Long</code> is non-positive, else false.
   */
  def isValid(value: Long): Boolean = NegZLongMacro.isValid(value)

  /**
   * A factory method that produces a <code>NegZLong</code> given a
   * <code>Long</code> value and a default <code>NegZLong</code>.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a non-positive <code>Long</code>, it will return a <code>NegZLong</code> representing that value.
   * Otherwise, the passed <code>Long</code> value is not non-positive, so this
   * method will return the passed <code>default</code> value.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Long</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Long</code> values at run time.
   * </p>
   *
   * @param value the <code>Long</code> to inspect, and if non-positive, return.
   * @param default the <code>NegZLong</code> to return if the passed
   *     <code>Long</code> value is not non-positive.
   * @return the specified <code>Long</code> value wrapped in a
   *     <code>NegZLong</code>, if it is non-positive, else the
   *     <code>default</code> <code>NegZLong</code> value.
   */
  def fromOrElse(value: Long, default: => NegZLong): NegZLong =
    if (NegZLongMacro.isValid(value)) new NegZLong(value) else default

  import language.experimental.macros

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>NegZLong</code> if passed a valid <code>Long</code>
   * literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the
   * specified <code>Long</code> expression at compile time. If
   * the expression is a non-positive <code>Long</code> literal,
   * it will return a <code>NegZLong</code> representing that value.
   * Otherwise, the passed <code>Long</code> expression is either a literal
   * that is not non-positive, or is not a literal, so this method
   * will give a compiler error.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>from</code>
   * factory method in that this method is implemented via a
   * macro that inspects <code>Long</code> literals at compile
   * time, whereas <code>from</code> inspects <code>Long</code>
   * values at run time.
   * </p>
   *
   * @param value the <code>Long</code> literal expression to
   *     inspect at compile time, and if non-positive, to return
   *     wrapped in a <code>NegZLong</code> at run time.
   * @return the specified, valid <code>Long</code> literal
   *     value wrapped in a <code>NegZLong</code>. (If the
   *     specified expression is not a valid <code>Long</code>
   *     literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Long): NegZLong = macro NegZLongMacro.apply

  /**
   * Implicit widening conversion from <code>NegZLong</code> to
   * <code>Long</code>.
   *
   * @param pos the <code>NegZLong</code> to widen
   * @return the <code>Long</code> value underlying the specified
   *     <code>NegZLong</code>.
   */
  implicit def widenToLong(pos: NegZLong): Long = pos.value

  /**
   * Implicit widening conversion from <code>NegZLong</code> to
   * <code>Float</code>.
   *
   * @param pos the <code>NegZLong</code> to widen
   * @return the <code>Long</code> value underlying the specified
   *     <code>NegZLong</code>, widened to <code>Float</code>.
   */
  implicit def widenToFloat(pos: NegZLong): Float = pos.value

  /**
   * Implicit widening conversion from <code>NegZLong</code> to
   * <code>Double</code>.
   *
   * @param pos the <code>NegZLong</code> to widen
   * @return the <code>Long</code> value underlying the specified
   *     <code>NegZLong</code>, widened to <code>Double</code>.
   */
  implicit def widenToDouble(pos: NegZLong): Double = pos.value


  /**
   * Implicit widening conversion from <code>NegZLong</code> to <code>NegZFloat</code>.
   *
   * @param pos the <code>NegZLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>NegZLong</code>,
   *     widened to <code>Float</code> and wrapped in a <code>NegZFloat</code>.
   */
  implicit def widenToNegZFloat(pos: NegZLong): NegZFloat = NegZFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NegZLong</code> to <code>NegZDouble</code>.
   *
   * @param pos the <code>NegZLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>NegZLong</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NegZDouble</code>.
   */
  implicit def widenToNegZDouble(pos: NegZLong): NegZDouble = NegZDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[NegZLong] =
    new Ordering[NegZLong] {
      def compare(x: NegZLong, y: NegZLong): Int = x.toLong.compare(y)
    }

  
}
