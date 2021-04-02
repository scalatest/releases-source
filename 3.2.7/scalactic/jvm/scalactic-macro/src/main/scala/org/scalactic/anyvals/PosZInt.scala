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

import scala.collection.immutable.Range
import scala.language.implicitConversions
import scala.util.{Try, Success, Failure}

import org.scalactic.{Validation, Pass, Fail}
import org.scalactic.{Or, Good, Bad}

/**
 * An <code>AnyVal</code> for non-negative <code>Int</code>s.
 *
 * 
 *
 * <p>
 * Because <code>PosZInt</code> is an <code>AnyVal</code> it will usually be
 * as efficient as an <code>Int</code>, being boxed only when an <code>Int</code>
 * would have been boxed.
 * </p>
 *
 * <p>
 * The <code>PosZInt.apply</code> factory method is implemented in terms of a macro that
 * checks literals for validity at compile time. Calling <code>PosZInt.apply</code> with
 * a literal <code>Int</code> value will either produce a valid <code>PosZInt</code> instance
 * at run time or an error at compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; PosZInt(42)
 * res0: org.scalactic.anyvals.PosZInt = PosZInt(42)
 *
 * scala&gt; PosZInt(-1)
 * &lt;console&gt;:14: error: PosZInt.apply can only be invoked on a non-negative (i >= 0) literal, like PosZInt(42).
 *               PosZInt(-1)
 *                     ^
 * </pre>
 *
 * <p>
 * <code>PosZInt.apply</code> cannot be used if the value being passed is a variable (<em>i.e.</em>, not a literal), because
 * the macro cannot determine the validity of variables at compile time (just literals). If you try to pass a variable
 * to <code>PosZInt.apply</code>, you'll get a compiler error that suggests you use a different factor method,
 * <code>PosZInt.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = 1
 * x: Int = 1
 *
 * scala&gt; PosZInt(x)
 * &lt;console&gt;:15: error: PosZInt.apply can only be invoked on a non-negative integer literal, like PosZInt(42). Please use PosZInt.from instead.
 *               PosZInt(x)
 *                     ^
 * </pre>
 *
 * <p>
 * The <code>PosZInt.from</code> factory method will inspect the value at runtime and return an <code>Option[PosZInt]</code>. If
 * the value is valid, <code>PosZInt.from</code> will return a <code>Some[PosZInt]</code>, else it will return a <code>None</code>.
 * Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; PosZInt.from(x)
 * res3: Option[org.scalactic.anyvals.PosZInt] = Some(PosZInt(1))
 *
 * scala&gt; val y = 0
 * y: Int = 0
 *
 * scala&gt; PosZInt.from(y)
 * res4: Option[org.scalactic.anyvals.PosZInt] = None
 * </pre>
 *
 * <p>
 * The <code>PosZInt.apply</code> factory method is marked implicit, so that you can pass literal <code>Int</code>s
 * into methods that require <code>PosZInt</code>, and get the same compile-time checking you get when calling
 * <code>PosZInt.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: PosZInt): Int = Int.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.PosZInt)Int
 *
 * scala&gt; invert(1)
 * res0: Int = 2147483646
 *
 * scala&gt; invert(Int.MaxValue)
 * res1: Int = 0
 *
 * scala&gt; invert(0)
 * &lt;console&gt;:15: error: PosZInt.apply can only be invoked on a non-negative (i >= 0) integer literal, like PosZInt(42).
 *               invert(0)
 *                      ^
 *
 * scala&gt; invert(-1)
 * &lt;console&gt;:15: error: PosZInt.apply can only be invoked on a non-negative (i >= 0) integer literal, like PosZInt(42).
 *               invert(-1)
 *                       ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the <code>PosZInt</code> companion object also defines implicit widening conversions
 * when either no loss of precision will occur or a similar conversion is provided in Scala. (For example, the implicit
 * conversion from <code>Int</code> to </code>Float</code> in Scala can lose precision.) This makes it convenient to
 * use a <code>PosZInt</code> where an <code>Int</code> or wider type is needed. An example is the subtraction in the body
 * of the <code>invert</code> method defined above, <code>Int.MaxValue - pos</code>. Although <code>Int.MaxValue</code> is
 * an <code>Int</code>, which has no <code>-</code> method that takes a <code>PosZInt</code> (the type of <code>pos</code>),
 * you can still subtract <code>pos</code>, because the <code>PosZInt</code> will be implicitly widened to <code>Int</code>.
 * </p>
 *
 * @param value The <code>Int</code> value underlying this <code>PosZInt</code>.
 */
final class PosZInt private (val value: Int) extends AnyVal {

  /**
   * A string representation of this <code>PosZInt</code>.
   */
  override def toString: String = s"PosZInt(${value.toString()})"

  /**
   * Converts this <code>PosZInt</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>PosZInt</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>PosZInt</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>PosZInt</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>PosZInt</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>PosZInt</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>PosZInt</code> to a <code>Double</code>.
   */
  def toDouble: Double = value.toDouble
  /**
   * Returns the bitwise negation of this value.
   * @example {{{
   * ~5 == -6
   * // in binary: ~00000101 ==
   * // 11111010
   * }}}
   */
  def unary_~ : Int = ~value
  /** Returns this value, unmodified. */
  def unary_+ : PosZInt = this
  /** Returns the negation of this value. */
  def unary_- : NegZInt = NegZInt.ensuringValid(-value)
  /**
   * Converts this <code>PosZInt</code>'s value to a string then concatenates the given string.
   */
  def +(x: String): String = s"${value.toString()}${x.toString()}"
  /**
   * Returns this value bit-shifted left by the specified number of bits,
   * filling in the new right bits with zeroes.
   * @example {{{ 6 << 3 == 48 // in binary: 0110 << 3 == 0110000 }}}
   */
  def <<(x: Int): Int = value << x
  /**
   * Returns this value bit-shifted left by the specified number of bits,
   * filling in the new right bits with zeroes.
   * @example {{{ 6 << 3 == 48 // in binary: 0110 << 3 == 0110000 }}}
   */
  def <<(x: Long): Int = value << x
  /**
   * Returns this value bit-shifted right by the specified number of bits,
   * filling the new left bits with zeroes.
   * @example {{{ 21 >>> 3 == 2 // in binary: 010101 >>> 3 == 010 }}}
   * @example {{{
   * -21 >>> 3 == 536870909
   * // in binary: 11111111 11111111 11111111 11101011 >>> 3 ==
   * // 00011111 11111111 11111111 11111101
   * }}}
   */
  def >>>(x: Int): Int = value >>> x
  /**
   * Returns this value bit-shifted right by the specified number of bits,
   * filling the new left bits with zeroes.
   * @example {{{ 21 >>> 3 == 2 // in binary: 010101 >>> 3 == 010 }}}
   * @example {{{
   * -21 >>> 3 == 536870909
   * // in binary: 11111111 11111111 11111111 11101011 >>> 3 ==
   * // 00011111 11111111 11111111 11111101
   * }}}
   */
  def >>>(x: Long): Int = value >>> x
  /**
   * Returns this value bit-shifted left by the specified number of bits,
   * filling in the right bits with the same value as the left-most bit of this.
   * The effect of this is to retain the sign of the value.
   * @example {{{
   * -21 >> 3 == -3
   * // in binary: 11111111 11111111 11111111 11101011 >> 3 ==
   * // 11111111 11111111 11111111 11111101
   * }}}
   */
  def >>(x: Int): Int = value >> x
  /**
   * Returns this value bit-shifted left by the specified number of bits,
   * filling in the right bits with the same value as the left-most bit of this.
   * The effect of this is to retain the sign of the value.
   * @example {{{
   * -21 >> 3 == -3
   * // in binary: 11111111 11111111 11111111 11101011 >> 3 ==
   * // 11111111 11111111 11111111 11111101
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
   * // in binary: 11110000
   * // | 10101010
   * // --------
   * // 11111010
   * }}}
   */
  def |(x: Byte): Int = value | x
  /**
   * Returns the bitwise OR of this value and `x`.
   * @example {{{
   * (0xf0 | 0xaa) == 0xfa
   * // in binary: 11110000
   * // | 10101010
   * // --------
   * // 11111010
   * }}}
   */
  def |(x: Short): Int = value | x
  /**
   * Returns the bitwise OR of this value and `x`.
   * @example {{{
   * (0xf0 | 0xaa) == 0xfa
   * // in binary: 11110000
   * // | 10101010
   * // --------
   * // 11111010
   * }}}
   */
  def |(x: Char): Int = value | x
  /**
   * Returns the bitwise OR of this value and `x`.
   * @example {{{
   * (0xf0 | 0xaa) == 0xfa
   * // in binary: 11110000
   * // | 10101010
   * // --------
   * // 11111010
   * }}}
   */
  def |(x: Int): Int = value | x
  /**
   * Returns the bitwise OR of this value and `x`.
   * @example {{{
   * (0xf0 | 0xaa) == 0xfa
   * // in binary: 11110000
   * // | 10101010
   * // --------
   * // 11111010
   * }}}
   */
  def |(x: Long): Long = value | x
  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary: 11110000
   * // & 10101010
   * // --------
   * // 10100000
   * }}}
   */
  def &(x: Byte): Int = value & x
  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary: 11110000
   * // & 10101010
   * // --------
   * // 10100000
   * }}}
   */
  def &(x: Short): Int = value & x
  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary: 11110000
   * // & 10101010
   * // --------
   * // 10100000
   * }}}
   */
  def &(x: Char): Int = value & x
  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary: 11110000
   * // & 10101010
   * // --------
   * // 10100000
   * }}}
   */
  def &(x: Int): Int = value & x
  /**
   * Returns the bitwise AND of this value and `x`.
   * @example {{{
   * (0xf0 & 0xaa) == 0xa0
   * // in binary: 11110000
   * // & 10101010
   * // --------
   * // 10100000
   * }}}
   */
  def &(x: Long): Long = value & x
  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary: 11110000
   * // ^ 10101010
   * // --------
   * // 01011010
   * }}}
   */
  def ^(x: Byte): Int = value ^ x
  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary: 11110000
   * // ^ 10101010
   * // --------
   * // 01011010
   * }}}
   */
  def ^(x: Short): Int = value ^ x
  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary: 11110000
   * // ^ 10101010
   * // --------
   * // 01011010
   * }}}
   */
  def ^(x: Char): Int = value ^ x
  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary: 11110000
   * // ^ 10101010
   * // --------
   * // 01011010
   * }}}
   */
  def ^(x: Int): Int = value ^ x
  /**
   * Returns the bitwise XOR of this value and `x`.
   * @example {{{
   * (0xf0 ^ 0xaa) == 0x5a
   * // in binary: 11110000
   * // ^ 10101010
   * // --------
   * // 01011010
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

  // Stuff from RichInt:
  /**
   * Returns a string representation of this <code>PosZInt</code>'s underlying <code>Int</code> as an
   * unsigned integer in base&nbsp;2.
   *
   * <p>
   * The unsigned integer value is the argument plus 2<sup>32</sup>
   * if this <code>PosZInt</code>'s underlying <code>Int</code> is negative; otherwise it is equal to the
   * underlying <code>Int</code>.  This value is converted to a string of ASCII digits
   * in binary (base&nbsp;2) with no extra leading <code>0</code>s.
   * If the unsigned magnitude is zero, it is represented by a
   * single zero character <code>'0'</code>
   * (<code>'&#92;u0030'</code>); otherwise, the first character of
   * the representation of the unsigned magnitude will not be the
   * zero character. The characters <code>'0'</code>
   * (<code>'&#92;u0030'</code>) and <code>'1'</code>
   * (<code>'&#92;u0031'</code>) are used as binary digits.
   * </p>
   *
   * @return  the string representation of the unsigned integer value
   *          represented by this <code>PosZInt</code>'s underlying <code>Int</code> in binary (base&nbsp;2).
   */
  def toBinaryString: String = java.lang.Integer.toBinaryString(value)

  /**
   * Returns a string representation of this <code>PosZInt</code>'s underlying <code>Int</code> as an
   * unsigned integer in base&nbsp;16.
   *
   * <p>
   * The unsigned integer value is the argument plus 2<sup>32</sup>
   * if this <code>PosZInt</code>'s underlying <code>Int</code> is negative; otherwise, it is equal to the
   * this <code>PosZInt</code>'s underlying <code>Int</code>  This value is converted to a string of ASCII digits
   * in hexadecimal (base&nbsp;16) with no extra leading
   * <code>0</code>s. If the unsigned magnitude is zero, it is
   * represented by a single zero character <code>'0'</code>
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
   * These are the characters <code>'&#92;u0030'</code> through
   * <code>'&#92;u0039'</code> and <code>'&#92;u0061'</code> through
   * <code>'&#92;u0066'</code>. If uppercase letters are
   * desired, the <code>toUpperCase</code> method may
   * be called on the result.
   *
   * @return  the string representation of the unsigned integer value
   *          represented by this <code>PosZInt</code>'s underlying <code>Int</code> in hexadecimal (base&nbsp;16).
   */
  def toHexString: String = java.lang.Integer.toHexString(value)

  /**
   * Returns a string representation of this <code>PosZInt</code>'s underlying <code>Int</code> as an
   * unsigned integer in base&nbsp;8.
   *
   * <p>The unsigned integer value is this <code>PosZInt</code>'s underlying <code>Int</code> plus 2<sup>32</sup>
   * if the underlying <code>Int</code> is negative; otherwise, it is equal to the
   * underlying <code>Int</code>.  This value is converted to a string of ASCII digits
   * in octal (base&nbsp;8) with no extra leading <code>0</code>s.
   *
   * <p>If the unsigned magnitude is zero, it is represented by a
   * single zero character <code>'0'</code>
   * (<code>'&#92;u0030'</code>); otherwise, the first character of
   * the representation of the unsigned magnitude will not be the
   * zero character. The following characters are used as octal
   * digits:
   *
   * <blockquote>
   * <code>01234567</code>
   * </blockquote>
   *
   * These are the characters <code>'&#92;u0030'</code> through
   * <code>'&#92;u0037'</code>.
   *
   * @return  the string representation of the unsigned integer value
   *          represented by this <code>PosZInt</code>'s underlying <code>Int</code> in octal (base&nbsp;8).
   */
  def toOctalString: String = java.lang.Integer.toOctalString(value)

  /**
   * Create a <code>Range</code> from this <code>PosZInt</code> value
   * until the specified <code>end</code> (exclusive) with step value 1.
   *
   * @param end The final bound of the range to make.
   * @return A [[scala.collection.immutable.Range]] from `this` up to but
   * not including `end`.
   */
  def until(end: Int): Range = Range(value, end)

  /**
   * Create a <code>Range</code> from this <code>PosZInt</code> value
   * until the specified <code>end</code> (exclusive) with the specified <code>step</code> value.
   *
   * @param end The final bound of the range to make.
   * @param step The number to increase by for each step of the range.
   * @return A [[scala.collection.immutable.Range]] from `this` up to but
   * not including `end`.
   */
  def until(end: Int, step: Int): Range = Range(value, end, step)

  /**
   * Create an inclusive <code>Range</code> from this <code>PosZInt</code> value
   * to the specified <code>end</code> with step value 1.
   *
   * @param end The final bound of the range to make.
   * @return A [[scala.collection.immutable.Range]] from `'''this'''` up to
   * and including `end`.
   */
  def to(end: Int): Range.Inclusive = Range.inclusive(value, end)

  /**
   * Create an inclusive <code>Range</code> from this <code>PosZInt</code> value
   * to the specified <code>end</code> with the specified <code>step</code> value.
   *
   * @param end The final bound of the range to make.
   * @param step The number to increase by for each step of the range.
   * @return A [[scala.collection.immutable.Range]] from `'''this'''` up to
   * and including `end`.
   */
  def to(end: Int, step: Int): Range.Inclusive = Range.inclusive(value, end, step)

  /**
   * Returns <code>this</code> if <code>this &gt; that</code> or <code>that</code> otherwise.
   */
  def max(that: PosZInt): PosZInt = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: PosZInt): PosZInt = if (math.min(value, that.value) == value) this else that

  /**
   * Applies the passed <code>Int =&gt; Int</code> function to the underlying <code>Int</code>
   * value, and if the result is positive, returns the result wrapped in a <code>PosZInt</code>,
   * else throws <code>AssertionError</code>.
   *
   * A factory/assertion method that produces a <code>PosInt</code> given a
   * valid <code>Int</code> value, or throws <code>AssertionError</code>,
   * if given an invalid <code>Int</code> value.
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
   * This method will inspect the result of applying the given function to this
   * <code>PosZInt</code>'s underlying <code>Int</code> value and if the result
   * is non-negative, it will return a <code>PosZInt</code> representing that value.
   * Otherwise, the <code>Int</code> value returned by the given function is
   * not non-negative, so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Int</code> is non-negative.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>PosZInt</code>'s
   * value will not overflow. Instead of overflowing silently like <code>Int</code>, this
   * method will signal an overflow with a loud <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Int =&gt; Int</code> function to apply to this <code>PosZInt</code>'s
   *     underlying <code>Int</code> value.
   * @return the result of applying this <code>PosZInt</code>'s underlying <code>Int</code> value to
   *     to the passed function, wrapped in a <code>PosZInt</code> if it is non-negative (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>PosZInt</code>'s underlying <code>Int</code> value to
   *     to the passed function is not non-negative.
   */
  def ensuringValid(f: Int => Int): PosZInt = {
    val candidateResult: Int = f(value)
    if (PosZIntMacro.isValid(candidateResult)) new PosZInt(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid PosZInt")
  }
}

/**
 * The companion object for <code>PosZInt</code> that offers factory methods that
 * produce <code>PosZInt</code>s, implicit widening conversions from <code>PosZInt</code>
 * to other numeric types, and maximum and minimum constant values for <code>PosZInt</code>.
 */
object PosZInt {
  /**
   * The largest value representable as a non-negative <code>Int</code>, which is <code>PosZInt(2147483647)</code>.
   */
  final val MaxValue: PosZInt = PosZInt.ensuringValid(Int.MaxValue)
  /**
   * The smallest value representable as a non-negative <code>Int</code>, which is <code>PosZInt(0)</code>.
   */
  final val MinValue: PosZInt = PosZInt.ensuringValid(0) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[PosZInt]</code> given an
   * <code>Int</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-negative <code>Int</code>, <em>i.e.</em>, a non-negative integer value,
   * it will return a <code>PosZInt</code> representing that value,
   * wrapped in a <code>Some</code>. Otherwise, the passed <code>Int</code>
   * value is not non-negative integer value, so this method will return <code>None</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Int</code> literals at compile time, whereas <code>from</code> inspects
   * <code>Int</code> values at run time.
   * </p>
   *
   * @param value the <code>Int</code> to inspect, and if non-negative, return
   *     wrapped in a <code>Some[PosZInt]</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Some[PosZInt]</code>, if it is non-negative, else <code>None</code>.
   */
  def from(value: Int): Option[PosZInt] =
    if (PosZIntMacro.isValid(value)) Some(new PosZInt(value)) else None

  /**
   * A factory/assertion method that produces a <code>PosZInt</code> given a
   * valid <code>Int</code> value, or throws <code>AssertionError</code>,
   * if given an invalid <code>Int</code> value.
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
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-negative <code>Int</code>, it will return a <code>PosZInt</code>
   * representing that value.  Otherwise, the passed <code>Int</code> value is not non-negative, so this
   * method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Int</code> literals at compile time, whereas this method inspects
   * <code>Int</code> values at run time.
   * It differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Int</code> is non-negative.
   * </p>
   *
   * @param value the <code>Int</code> to inspect, and if non-negative, return
   *     wrapped in a <code>PosZInt</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>PosZInt</code>, if it is non-negative, else throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not non-negative
   */
  def ensuringValid(value: Int): PosZInt =
    if (PosZIntMacro.isValid(value)) new PosZInt(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid PosZInt")
    }

  /**
   * A factory/validation method that produces a <code>PosZInt</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-negative <code>Int</code>, it will return a <code>PosZInt</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Int</code> value is not non-negative, so this
   * method will return an <code>AssertionError</code>, wrapped in a <code>Failure</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Int</code> literals at compile time, whereas this method inspects
   * <code>Int</code> values at run time.
   * </p>
   *
   * @param value the <code>Int</code> to inspect, and if non-negative, return
   *     wrapped in a <code>Success(PosZInt)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Success(PosZInt)</code>, if it is non-negative, else a <code>Failure(AssertionError)</code>.
   */
   def tryingValid(value: Int): Try[PosZInt] =
     if (PosZIntMacro.isValid(value))
       Success(new PosZInt(value))
     else
       Failure(new AssertionError(s"${value.toString()} was not a valid PosZInt"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Int</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-negative <code>Int</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Int</code> value is non-negative, so this
   * method will return a result of type <code>E</code> obtained by passing
   * the invalid <code>Int</code> value to the given function <code>f</code>,
   * wrapped in a `Fail`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Int</code> literals at compile time, whereas this method inspects
   * <code>Int</code> values at run time.
   * </p>
   *
   * @param value the `Int` to validate that it is non-negative.
   * @return a `Pass` if the specified `Int` value is non-negative,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Int` to the given function `f`.
   */
  def passOrElse[E](value: Int)(f: Int => E): Validation[E] =
    if (PosZIntMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>PosZInt</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-negative <code>Int</code>, it will return a <code>PosZInt</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Int</code> value is not non-negative, so this
   * method will return a result of type <code>B</code> obtained by passing
   * the invalid <code>Int</code> value to the given function <code>f</code>,
   * wrapped in a `Bad`.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Int</code> literals at compile time, whereas this method inspects
   * <code>Int</code> values at run time.
   * </p>
   *
   * @param value the <code>Int</code> to inspect, and if non-negative, return
   *     wrapped in a <code>Good(PosZInt)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Good(PosZInt)</code>, if it is non-negative, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Int)(f: Int => B): PosZInt Or B =
    if (PosZIntMacro.isValid(value)) Good(PosZInt.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>PosZInt</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-negative <code>Int</code>, it will return a <code>PosZInt</code>
   * representing that value, wrapped in a <code>Right</code>.
   * Otherwise, the passed <code>Int</code> value is not non-negative, so this
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
   * @param value the <code>Int</code> to inspect, and if non-negative, return
   *     wrapped in a <code>Right(PosZInt)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Right(PosZInt)</code>, if it is non-negative, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Int)(f: Int => L): Either[L, PosZInt] =
    if (PosZIntMacro.isValid(value)) Right(PosZInt.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Int</code> value is non-negative.
   *
   * @param value the <code>Int</code> to inspect, and if non-negative, return true.
   * @return true if the specified <code>Int</code> is non-negative, else false.
   */
  def isValid(value: Int): Boolean = PosZIntMacro.isValid(value)

  /**
   * A factory method that produces a <code>PosZInt</code> given a
   * <code>Int</code> value and a default <code>PosZInt</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a positive <code>Int</code>, <em>i.e.</em>, a value greater
   * than 0.0, it will return a <code>PosZInt</code> representing that value.
   * Otherwise, the passed <code>Int</code> value is 0 or negative, so this
   * method will return the passed <code>default</code> value.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code>
   * factory method in that <code>apply</code> is implemented
   * via a macro that inspects <code>Int</code> literals at
   * compile time, whereas <code>from</code> inspects
   * <code>Int</code> values at run time.
   * </p>
   *
   * @param value the <code>Int</code> to inspect, and if positive, return.
   * @param default the <code>PosZInt</code> to return if the passed
   *     <code>Int</code> value is not positive.
   * @return the specified <code>Int</code> value wrapped in a
   *     <code>PosZInt</code>, if it is positive, else the
   *     <code>default</code> <code>PosZInt</code> value.
   */
  def fromOrElse(value: Int, default: => PosZInt): PosZInt =
    if (PosZIntMacro.isValid(value)) new PosZInt(value) else default

  import language.experimental.macros

  /**
   * A factory method, implemented via a macro, that produces a <code>PosZInt</code>
   * if passed a valid <code>Int</code> literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the specified <code>Int</code>
   * expression at compile time. If
   * the expression is a positive <code>Int</code> literal, <em>i.e.</em>, with a
   * value greater than 0, it will return a <code>PosZInt</code> representing that value.
   * Otherwise, the passed <code>Int</code>
   * expression is either a literal that is 0 or negative, or is not a literal, so
   * this method will give a compiler error.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>from</code> factory method
   * in that this method is implemented via a macro that inspects
   * <code>Int</code> literals at compile time, whereas <code>from</code> inspects
   * <code>Int</code> values at run time.
   * </p>
   *
   * @param value the <code>Int</code> literal expression to inspect at compile time,
   *     and if positive, to return wrapped in a <code>PosZInt</code> at run time.
   * @return the specified, valid <code>Int</code> literal value wrapped
   *     in a <code>PosZInt</code>. (If the specified expression is not a valid
   *     <code>Int</code> literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Int): PosZInt = macro PosZIntMacro.apply

  /**
   * Implicit widening conversion from <code>PosZInt</code> to <code>Int</code>.
   *
   * @param pos the <code>PosZInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>PosZInt</code>.
   */
  implicit def widenToInt(pos: PosZInt): Int = pos.value

  /**
   * Implicit widening conversion from <code>PosZInt</code> to <code>Long</code>.
   *
   * @param pos the <code>PosZInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>PosZInt</code>,
   *     widened to <code>Long</code>.
   */
  implicit def widenToLong(pos: PosZInt): Long = pos.value

  /**
   * Implicit widening conversion from <code>PosZInt</code> to <code>Float</code>.
   *
   * @param pos the <code>PosZInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>PosZInt</code>,
   *     widened to <code>Float</code>.
   */
  implicit def widenToFloat(pos: PosZInt): Float = pos.value

  /**
   * Implicit widening conversion from <code>PosZInt</code> to <code>Double</code>.
   *
   * @param pos the <code>PosZInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>PosZInt</code>,
   *     widened to <code>Double</code>.
   */
  implicit def widenToDouble(pos: PosZInt): Double = pos.value


  /**
   * Implicit widening conversion from <code>PosZInt</code> to <code>PosZLong</code>.
   *
   * @param pos the <code>PosZInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>PosZInt</code>,
   *     widened to <code>Long</code> and wrapped in a <code>PosZLong</code>.
   */
  implicit def widenToPosZLong(pos: PosZInt): PosZLong = PosZLong.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosZInt</code> to <code>PosZFloat</code>.
   *
   * @param pos the <code>PosZInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>PosZInt</code>,
   *     widened to <code>Float</code> and wrapped in a <code>PosZFloat</code>.
   */
  implicit def widenToPosZFloat(pos: PosZInt): PosZFloat = PosZFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>PosZInt</code> to <code>PosZDouble</code>.
   *
   * @param pos the <code>PosZInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>PosZInt</code>,
   *     widened to <code>Double</code> and wrapped in a <code>PosZDouble</code>.
   */
  implicit def widenToPosZDouble(pos: PosZInt): PosZDouble = PosZDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[PosZInt] =
    new Ordering[PosZInt] {
      def compare(x: PosZInt, y: PosZInt): Int = x.toInt.compare(y)
    }


  /**
   * <strong>The formerly implicit <code>posZIntOrd</code> field has been deprecated and will be removed in a future version of ScalaTest. Please use the <code>ordering</code> field instead.</strong>
   */
  @deprecated("The formerly implicit posZIntOrd field has been deprecated and will be removed in a future version of ScalaTest. Please use the ordering field instead.")
  val posZIntOrd: Ordering[PosZInt] =
    new Ordering[PosZInt] {
      def compare(x: PosZInt, y: PosZInt): Int = ordering.compare(x, y)
    }
        
}
