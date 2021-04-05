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
 * An <code>AnyVal</code> for positive <code>Long</code>s.
 *
 * Note: a <code>PosLong</code> may not equal 0. If you want positive number or 0, use [[PosZLong]].
 *
 * <p>
 * Because <code>PosLong</code> is an <code>AnyVal</code> it
 * will usually be as efficient as an <code>Long</code>, being
 * boxed only when an <code>Long</code> would have been boxed.
 * </p>
 *
 * <p>
 * The <code>PosLong.apply</code> factory method is implemented
 * in terms of a macro that checks literals for validity at
 * compile time. Calling <code>PosLong.apply</code> with a
 * literal <code>Long</code> value will either produce a valid
 * <code>PosLong</code> instance at run time or an error at
 * compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; PosLong(42L)
 * res0: org.scalactic.anyvals.PosLong = PosLong(42L)
 *
 * scala&gt; PosLong(0L)
 * &lt;console&gt;:14: error: PosLong.apply can only be invoked on a positive (i > 0L) integer literal, like PosLong(42L).
 *               PosLong(0L)
 *                      ^
 * </pre>
 *
 * <p>
 * <code>PosLong.apply</code> cannot be used if the value being
 * passed is a variable (<em>i.e.</em>, not a literal), because
 * the macro cannot determine the validity of variables at
 * compile time (just literals). If you try to pass a variable
 * to <code>PosLong.apply</code>, you'll get a compiler error
 * that suggests you use a different factor method,
 * <code>PosLong.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = 42LL
 * x: Long = 42L
 *
 * scala&gt; PosLong(x)
 * &lt;console&gt;:15: error: PosLong.apply can only be invoked on an long literal, like PosLong(42L). Please use PosLong.from instead.
 *               PosLong(x)
 *                      ^
 * </pre>
 *
 * <p>
 * The <code>PosLong.from</code> factory method will inspect the
 * value at runtime and return an
 * <code>Option[PosLong]</code>. If the value is valid,
 * <code>PosLong.from</code> will return a
 * <code>Some[PosLong]</code>, else it will return a
 * <code>None</code>.  Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; PosLong.from(x)
 * res3: Option[org.scalactic.anyvals.PosLong] = Some(PosLong(42L))
 *
 * scala&gt; val y = 0LL
 * y: Long = 0L
 *
 * scala&gt; PosLong.from(y)
 * res4: Option[org.scalactic.anyvals.PosLong] = None
 * </pre>
 *
 * <p>
 * The <code>PosLong.apply</code> factory method is marked
 * implicit, so that you can pass literal <code>Long</code>s
 * into methods that require <code>PosLong</code>, and get the
 * same compile-time checking you get when calling
 * <code>PosLong.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: PosLong): Long = Long.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.PosLong)Long
 *
 * scala&gt; invert(1L)
 * res5: Long = 9223372036854775806
 *
 * scala&gt; invert(Long.MaxValue)
 * res6: Long = 0
 *
 * scala&gt; invert(0LL)
 * &lt;console&gt;:15: error: PosLong.apply can only be invoked on a positive (i > 0L) integer literal, like PosLong(42LL).
 *               invert(0LL)
 *                      ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the <code>PosLong</code>
 * companion object also defines implicit widening conversions
 * when either no loss of precision will occur or a similar
 * conversion is provided in Scala. (For example, the implicit
 * conversion from <code>Long</code> to </code>Double</code> in
 * Scala can lose precision.) This makes it convenient to use a
 * <code>PosLong</code> where a <code>Long</code> or wider type
 * is needed. An example is the subtraction in the body of the
 * <code>invert</code> method defined above, <code>Long.MaxValue
 * - pos</code>. Although <code>Long.MaxValue</code> is a
 * <code>Long</code>, which has no <code>-</code> method that
 * takes a <code>PosLong</code> (the type of <code>pos</code>),
 * you can still subtract <code>pos</code>, because the
 * <code>PosLong</code> will be implicitly widened to
 * <code>Long</code>.
 * </p>
 *
 * @param value The <code>Long</code> value underlying this <code>PosLong</code>.
 */
final class PosLong private (val value: Long) extends AnyVal {

  /**
   * A string representation of this <code>PosLong</code>.
   */
  override def toString: String = s"PosLong(${value}L)"

  /**
   * Converts this <code>PosLong</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>PosLong</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>PosLong</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>PosLong</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>PosLong</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>PosLong</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>PosLong</code> to a <code>Double</code>.
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
  def unary_+ : PosLong = this
  /** Returns the negation of this value. */
  def unary_- : NegLong = NegLong.ensuringValid(-value)

  /**
   * Converts this <code>PosLong</code>'s value to a string then concatenates the given string.
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
   * Returns a string representation of this <code>PosLong</code>'s underlying <code>Long</code>
   * as an unsigned integer in base&nbsp;2.
   *
   * <p>
   * The unsigned <code>long</code> value is this <code>PosLong</code>'s underlying <code>Long</code> plus
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
   *          value represented by this <code>PosLong</code>'s underlying <code>Long</code> in binary (base&nbsp;2).
   */
  def toBinaryString: String = java.lang.Long.toBinaryString(value)

  /**
   * Returns a string representation of this <code>PosLong</code>'s underlying <code>Long</code>
   * as an unsigned integer in base&nbsp;16.
   *
   * <p>
   * The unsigned <code>long</code> value is this <code>PosLong</code>'s underlying <code>Long</code> plus
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
   *          value represented by this <code>PosLong</code>'s underlying <code>Long</code> in hexadecimal
   *          (base&nbsp;16).
   */
  def toHexString: String = java.lang.Long.toHexString(value)

  /**
   * Returns a string representation of this <code>PosLong</code>'s underlying <code>Long</code>
   * as an unsigned integer in base&nbsp;8.
   *
   * <p>
   * The unsigned <code>long</code> value is this <code>PosLong</code>'s underlying <code>Long</code> plus
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
   *          value represented by this <code>PosLong</code>'s underlying <code>Long</code> in octal (base&nbsp;8).
   */
  def toOctalString: String = java.lang.Long.toOctalString(value)

  /**
   * Returns <code>this</code> if <code>this &gt; that</code> or <code>that</code> otherwise.
   */
  def max(that: PosLong): PosLong = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: PosLong): PosLong = if (math.min(value, that.value) == value) this else that

  // adapted from RichInt:
  /**
   * Create a <code>Range</code> from this <code>PosLong</code> value
   * until the specified <code>end</code> (exclusive) with step value 1.
   *
   * @param end The final bound of the range to make.
   * @return A [[scala.collection.immutable.NumericRange.Exclusive[Long]]] from `this` up to but
   * not including `end`.
   */
  def until(end: Long): NumericRange.Exclusive[Long] = value.until(end)

  /**
   * Create a <code>Range</code> from this <code>PosLong</code> value
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
   * Create an inclusive <code>Range</code> from this <code>PosLong</code> value
   * to the specified <code>end</code> with step value 1.
   *
   * @param end The final bound of the range to make.
   * @return A [[scala.collection.immutable.NumericRange.Inclusive[Long]]] from `'''this'''` up to
   * and including `end`.
   */
  def to(end: Long): NumericRange.Inclusive[Long] = value.to(end)

  /**
   * Create an inclusive <code>Range</code> from this <code>PosLong</code> value
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
   * value, and if the result is positive, returns the result wrapped in a <code>PosLong</code>,
   * else throws <code>AssertionError</code>.
   *
   * <p>
   * This method will inspect the result of applying the given function to this
   * <code>PosLong</code>'s underlying <code>Long</code> value and if the result
   * is positive, it will return a <code>PosLong</code> representing that value.
   * Otherwise, the <code>Long</code> value returned by the given function is
   * not positive, this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Long</code> is positive.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>PosLong</code>'s
   * value will not overflow. Instead of overflowing silently like <code>Long</code>, this
   * method will signal an overflow with a loud <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Long =&gt; Long</code> function to apply to this <code>PosLong</code>'s
   *     underlying <code>Long</code> value.
   * @return the result of applying this <code>PosLong</code>'s underlying <code>Long</code> value to
   *     to the passed function, wrapped in a <code>PosLong</code> if it is positive (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>PosLong</code>'s underlying <code>Long</code> value to
   *     to the passed function is not positive.
   */
  def ensuringValid(f: Long => Long): PosLong = {
    val candidateResult: Long = f(value)
    if (PosLongMacro.isValid(candidateResult)) new PosLong(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid PosLong")
  }
}

/**
 * The companion object for <code>PosLong</code> that offers
 * factory methods that produce <code>PosLong</code>s, implicit
 * widening conversions from <code>PosLong</code> to other
 * numeric types, and maximum and minimum constant values for
 * <code>PosLong</code>.
 */
object PosLong {
  /**
   * The largest value representable as a positive
   * <code>Long</code>, which is <code>PosLong(9223372036854775807)</code>.
   */
  final val MaxValue: PosLong = PosLong.ensuringValid(Long.MaxValue)

  /**
   * The smallest value representable as a positive
   * <code>Long</code>, which is <code>PosLong(1L)</code>.
   */
  final val MinValue: PosLong = PosLong.ensuringValid(1L) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[PosLong]</code> given a
   * <code>Long</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a positive <code>Long</code>, it will return a <code>PosLong</code> representing that value,
   * wrapped in a <code>Some</code>. Otherwise, the passed <code>Long</code>
   * value is not positive, so this method will return <code>None</code>.
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
   * @param value the <code>Long</code> to inspect, and if positive, return
   *     wrapped in a <code>Some[PosLong]</code>.
   * @return the specified <code>Long</code> value wrapped in a
   *     <code>Some[PosLong]</code>, if it is positive, else
   *     <code>None</code>.
   */
  def from(value: Long): Option[PosLong] =
    if (PosLongMacro.isValid(value)) Some(new PosLong(value)) else None

  /**
   * A factory/assertion method that produces an <code>PosLong</code> given a
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
   * it is a positive <code>Long</code>, it will return a <code>PosLong</code> representing that value.
   * Otherwise, the passed <code>Long</code> value is not positive, so
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
   * @param value the <code>Long</code> to inspect, and if positive, return
   *     wrapped in a <code>PosLong</code>.
   * @return the specified <code>Long</code> value wrapped in a
   *     <code>PosLong</code>, if it is positive, else
   *     throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not positive
   */
  def ensuringValid(value: Long): PosLong =
    if (PosLongMacro.isValid(value)) new PosLong(value) else {
      throw new AssertionError(s"${value.toString()}  was not a valid PosLong")
    }

  /**
   * A factory/validation method that produces a <code>PosLong</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Long</code> value, or if the
   * given <code>Long</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a positive <code>Long</code>, it will return a <code>PosLong</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Long</code> value is not positive, so this
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
   * @param value the <code>Long</code> to inspect, and if positive, return
   *     wrapped in a <code>Success(PosLong)</code>.
   * @return the specified <code>Long</code> value wrapped
   *     in a <code>Success(PosLong)</code>, if it is positive, else a <code>Failure(AssertionError)</code>.
   */
   def tryingValid(value: Long): Try[PosLong] =
     if (PosLongMacro.isValid(value))
       Success(new PosLong(value))
     else
       Failure(new AssertionError(s"${value.toString()} was not a valid PosLong"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Long</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a positive <code>Long</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Long</code> value is positive, so this
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
   * @param value the `Long` to validate that it is positive.
   * @return a `Pass` if the specified `Long` value is positive,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Long` to the given function `f`.
   */
  def passOrElse[E](value: Long)(f: Long => E): Validation[E] =
    if (PosLongMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>PosLong</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Long</code> value, or if the
   * given <code>Long</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Long</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a positive <code>Long</code>, it will return a <code>PosLong</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Long</code> value is not positive, so this
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
   * @param value the <code>Long</code> to inspect, and if positive, return
   *     wrapped in a <code>Good(PosLong)</code>.
   * @return the specified <code>Long</code> value wrapped
   *     in a <code>Good(PosLong)</code>, if it is positive, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Long)(f: Long => B): PosLong Or B =
    if (PosLongMacro.isValid(value)) Good(PosLong.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>PosLong</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a positive <code>Int</code>, it will return a <code>PosLong</code>
   * representing that value, wrapped in a <code>Right</code>.
   * Otherwise, the passed <code>Int</code> value is not positive, so this
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
   * @param value the <code>Int</code> to inspect, and if positive, return
   *     wrapped in a <code>Right(PosLong)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Right(PosLong)</code>, if it is positive, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Long)(f: Long => L): Either[L, PosLong] =
    if (PosLongMacro.isValid(value)) Right(PosLong.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Long</code> value is positive.
   *
   * @param value the <code>Long</code> to inspect, and if positive, return true.
   * @return true if the specified <code>Long</code> is positive, else false.
   */
  def isValid(value: Long): Boolean = PosLongMacro.isValid(value)

  /**
   * A factory method that produces a <code>PosLong</code> given a
   * <code>Long</code> value and a default <code>PosLong</code>.
   *
   * <p>
   * This method will inspect the passed <code>Long</code> value and if
   * it is a positive <code>Long</code>, it will return a <code>PosLong</code> representing that value.
   * Otherwise, the passed <code>Long</code> value is not positive, so this
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
   * @param value the <code>Long</code> to inspect, and if positive, return.
   * @param default the <code>PosLong</code> to return if the passed
   *     <code>Long</code> value is not positive.
   * @return the specified <code>Long</code> value wrapped in a
   *     <code>PosLong</code>, if it is positive, else the
   *     <code>default</code> <code>PosLong</code> value.
   */
  def fromOrElse(value: Long, default: => PosLong): PosLong =
    if (PosLongMacro.isValid(value)) new PosLong(value) else default

  import language.experimental.macros

  /**
   * A factory method, implemented via a macro, that produces a
   * <code>PosLong</code> if passed a valid <code>Long</code>
   * literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the
   * specified <code>Long</code> expression at compile time. If
   * the expression is a positive <code>Long</code> literal,
   * it will return a <code>PosLong</code> representing that value.
   * Otherwise, the passed <code>Long</code> expression is either a literal
   * that is not positive, or is not a literal, so this method
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
   *     inspect at compile time, and if positive, to return
   *     wrapped in a <code>PosLong</code> at run time.
   * @return the specified, valid <code>Long</code> literal
   *     value wrapped in a <code>PosLong</code>. (If the
   *     specified expression is not a valid <code>Long</code>
   *     literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Long): PosLong = macro PosLongMacro.apply

  /**
   * Implicit widening conversion from <code>PosLong</code> to
   * <code>Long</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified
   *     <code>PosLong</code>.
   */
  implicit def widenToLong(pos: PosLong): Long = pos.value

  /**
   * Implicit widening conversion from <code>PosLong</code> to
   * <code>Float</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified
   *     <code>PosLong</code>, widened to <code>Float</code>.
   */
  implicit def widenToFloat(pos: PosLong): Float = pos.value

  /**
   * Implicit widening conversion from <code>PosLong</code> to
   * <code>Double</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified
   *     <code>PosLong</code>, widened to <code>Double</code>.
   */
  implicit def widenToDouble(pos: PosLong): Double = pos.value


  /**
   * Implicit widening conversion from <code>PosLong</code> to <code>PosFloat</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>PosLong</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosFloat</code>.
   */
  implicit def widenToPosFloat(pos: PosLong): PosFloat = PosFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosLong</code> to <code>PosDouble</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>PosLong</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosDouble</code>.
   */
  implicit def widenToPosDouble(pos: PosLong): PosDouble = PosDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosLong</code> to <code>PosZLong</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>PosLong</code>,
   *     widened to <code>Long</code> and wrapped in a <code>PosZLong</code>.
   */
  implicit def widenToPosZLong(pos: PosLong): PosZLong = PosZLong.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosLong</code> to <code>PosZFloat</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>PosLong</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosZFloat</code>.
   */
  implicit def widenToPosZFloat(pos: PosLong): PosZFloat = PosZFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosLong</code> to <code>PosZDouble</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>PosLong</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosZDouble</code>.
   */
  implicit def widenToPosZDouble(pos: PosLong): PosZDouble = PosZDouble.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosLong</code> to <code>NonZeroLong</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>PosLong</code>,
   *     widened to <code>Long</code> and wrapped in a <code>NonZeroLong</code>.
   */
  implicit def widenToNonZeroLong(pos: PosLong): NonZeroLong = NonZeroLong.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosLong</code> to <code>NonZeroFloat</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>PosLong</code>,
   *     widened to <code>Float</code> and wrapped in a <code>NonZeroFloat</code>.
   */
  implicit def widenToNonZeroFloat(pos: PosLong): NonZeroFloat = NonZeroFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosLong</code> to <code>NonZeroDouble</code>.
   *
   * @param pos the <code>PosLong</code> to widen
   * @return the <code>Long</code> value underlying the specified <code>PosLong</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NonZeroDouble</code>.
   */
  implicit def widenToNonZeroDouble(pos: PosLong): NonZeroDouble = NonZeroDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[PosLong] =
    new Ordering[PosLong] {
      def compare(x: PosLong, y: PosLong): Int = x.toLong.compare(y)
    }


  /**
   * <strong>The formerly implicit <code>posLongOrd</code> field has been deprecated and will be removed in a future version of ScalaTest. Please use the <code>ordering</code> field instead.</strong>
   */
  @deprecated("The formerly implicit posLongOrd field has been deprecated and will be removed in a future version of ScalaTest. Please use the ordering field instead.")
  val posLongOrd: Ordering[PosLong] =
    new Ordering[PosLong] {
      def compare(x: PosLong, y: PosLong): Int = ordering.compare(x, y)
    }
        
}
