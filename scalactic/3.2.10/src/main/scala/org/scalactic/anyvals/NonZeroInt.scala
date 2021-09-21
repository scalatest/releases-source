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
 * An <code>AnyVal</code> for non-zero <code>Int</code>s.
 *
 * Note: a <code>NonZeroInt</code> may not equal 0.
 *
 * <p>
 * Because <code>NonZeroInt</code> is an <code>AnyVal</code> it will usually be
 * as efficient as an <code>Int</code>, being boxed only when an <code>Int</code>
 * would have been boxed.
 * </p>
 *
 * <p>
 * The <code>NonZeroInt.apply</code> factory method is implemented in terms of a macro that
 * checks literals for validity at compile time. Calling <code>NonZeroInt.apply</code> with
 * a literal <code>Int</code> value will either produce a valid <code>NonZeroInt</code> instance
 * at run time or an error at compile time. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; import anyvals._
 * import anyvals._
 *
 * scala&gt; NonZeroInt(42)
 * res0: org.scalactic.anyvals.NonZeroInt = NonZeroInt(42)
 *
 * scala&gt; NonZeroInt(0)
 * &lt;console&gt;:14: error: NonZeroInt.apply can only be invoked on a non-zero (i != 0) literal, like NonZeroInt(42).
 *               NonZeroInt(0)
 *                     ^
 * </pre>
 *
 * <p>
 * <code>NonZeroInt.apply</code> cannot be used if the value being passed is a variable (<em>i.e.</em>, not a literal), because
 * the macro cannot determine the validity of variables at compile time (just literals). If you try to pass a variable
 * to <code>NonZeroInt.apply</code>, you'll get a compiler error that suggests you use a different factor method,
 * <code>NonZeroInt.from</code>, instead:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; val x = 1
 * x: Int = 1
 *
 * scala&gt; NonZeroInt(x)
 * &lt;console&gt;:15: error: NonZeroInt.apply can only be invoked on a non-zero integer literal, like NonZeroInt(42). Please use NonZeroInt.from instead.
 *               NonZeroInt(x)
 *                     ^
 * </pre>
 *
 * <p>
 * The <code>NonZeroInt.from</code> factory method will inspect the value at runtime and return an <code>Option[NonZeroInt]</code>. If
 * the value is valid, <code>NonZeroInt.from</code> will return a <code>Some[NonZeroInt]</code>, else it will return a <code>None</code>.
 * Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; NonZeroInt.from(x)
 * res3: Option[org.scalactic.anyvals.NonZeroInt] = Some(NonZeroInt(1))
 *
 * scala&gt; val y = 0
 * y: Int = 0
 *
 * scala&gt; NonZeroInt.from(y)
 * res4: Option[org.scalactic.anyvals.NonZeroInt] = None
 * </pre>
 *
 * <p>
 * The <code>NonZeroInt.apply</code> factory method is marked implicit, so that you can pass literal <code>Int</code>s
 * into methods that require <code>NonZeroInt</code>, and get the same compile-time checking you get when calling
 * <code>NonZeroInt.apply</code> explicitly. Here's an example:
 * </p>
 *
 * <pre class="stREPL">
 * scala&gt; def invert(pos: NonZeroInt): Int = Int.MaxValue - pos
 * invert: (pos: org.scalactic.anyvals.NonZeroInt)Int
 *
 * scala&gt; invert(1)
 * res0: Int = 2147483646
 *
 * scala&gt; invert(Int.MaxValue)
 * res1: Int = 0
 *
 * scala&gt; invert(0)
 * &lt;console&gt;:15: error: NonZeroInt.apply can only be invoked on a non-zero (i != 0) integer literal, like NonZeroInt(42).
 *               invert(0)
 *                      ^
 *
 * scala&gt; invert(-1)
 * &lt;console&gt;:15: error: NonZeroInt.apply can only be invoked on a non-zero (i != 0) integer literal, like NonZeroInt(42).
 *               invert(-1)
 *                       ^
 *
 * </pre>
 *
 * <p>
 * This example also demonstrates that the <code>NonZeroInt</code> companion object also defines implicit widening conversions
 * when either no loss of precision will occur or a similar conversion is provided in Scala. (For example, the implicit
 * conversion from <code>Int</code> to </code>Float</code> in Scala can lose precision.) This makes it convenient to
 * use a <code>NonZeroInt</code> where an <code>Int</code> or wider type is needed. An example is the subtraction in the body
 * of the <code>invert</code> method defined above, <code>Int.MaxValue - pos</code>. Although <code>Int.MaxValue</code> is
 * an <code>Int</code>, which has no <code>-</code> method that takes a <code>NonZeroInt</code> (the type of <code>pos</code>),
 * you can still subtract <code>pos</code>, because the <code>NonZeroInt</code> will be implicitly widened to <code>Int</code>.
 * </p>
 *
 * @param value The <code>Int</code> value underlying this <code>NonZeroInt</code>.
 */
final class NonZeroInt private (val value: Int) extends AnyVal {

  /**
   * A string representation of this <code>NonZeroInt</code>.
   */
  override def toString: String = s"NonZeroInt(${value.toString()})"

  /**
   * Converts this <code>NonZeroInt</code> to a <code>Byte</code>.
   */
  def toByte: Byte = value.toByte

  /**
   * Converts this <code>NonZeroInt</code> to a <code>Short</code>.
   */
  def toShort: Short = value.toShort

  /**
   * Converts this <code>NonZeroInt</code> to a <code>Char</code>.
   */
  def toChar: Char = value.toChar

  /**
   * Converts this <code>NonZeroInt</code> to an <code>Int</code>.
   */
  def toInt: Int = value.toInt

  /**
   * Converts this <code>NonZeroInt</code> to a <code>Long</code>.
   */
  def toLong: Long = value.toLong

  /**
   * Converts this <code>NonZeroInt</code> to a <code>Float</code>.
   */
  def toFloat: Float = value.toFloat

  /**
   * Converts this <code>NonZeroInt</code> to a <code>Double</code>.
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
  def unary_+ : NonZeroInt = this
  /** Returns the negation of this value. */
  def unary_- : NonZeroInt = NonZeroInt.ensuringValid(-value)
  /**
   * Converts this <code>NonZeroInt</code>'s value to a string then concatenates the given string.
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
   * Returns a string representation of this <code>NonZeroInt</code>'s underlying <code>Int</code> as an
   * unsigned integer in base&nbsp;2.
   *
   * <p>
   * The unsigned integer value is the argument plus 2<sup>32</sup>
   * if this <code>NonZeroInt</code>'s underlying <code>Int</code> is negative; otherwise it is equal to the
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
   *          represented by this <code>NonZeroInt</code>'s underlying <code>Int</code> in binary (base&nbsp;2).
   */
  def toBinaryString: String = java.lang.Integer.toBinaryString(value)

  /**
   * Returns a string representation of this <code>NonZeroInt</code>'s underlying <code>Int</code> as an
   * unsigned integer in base&nbsp;16.
   *
   * <p>
   * The unsigned integer value is the argument plus 2<sup>32</sup>
   * if this <code>NonZeroInt</code>'s underlying <code>Int</code> is negative; otherwise, it is equal to the
   * this <code>NonZeroInt</code>'s underlying <code>Int</code>  This value is converted to a string of ASCII digits
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
   *          represented by this <code>NonZeroInt</code>'s underlying <code>Int</code> in hexadecimal (base&nbsp;16).
   */
  def toHexString: String = java.lang.Integer.toHexString(value)

  /**
   * Returns a string representation of this <code>NonZeroInt</code>'s underlying <code>Int</code> as an
   * unsigned integer in base&nbsp;8.
   *
   * <p>The unsigned integer value is this <code>NonZeroInt</code>'s underlying <code>Int</code> plus 2<sup>32</sup>
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
   *          represented by this <code>NonZeroInt</code>'s underlying <code>Int</code> in octal (base&nbsp;8).
   */
  def toOctalString: String = java.lang.Integer.toOctalString(value)

  /**
   * Create a <code>Range</code> from this <code>NonZeroInt</code> value
   * until the specified <code>end</code> (exclusive) with step value 1.
   *
   * @param end The final bound of the range to make.
   * @return A [[scala.collection.immutable.Range]] from `this` up to but
   * not including `end`.
   */
  def until(end: Int): Range = Range(value, end)

  /**
   * Create a <code>Range</code> from this <code>NonZeroInt</code> value
   * until the specified <code>end</code> (exclusive) with the specified <code>step</code> value.
   *
   * @param end The final bound of the range to make.
   * @param step The number to increase by for each step of the range.
   * @return A [[scala.collection.immutable.Range]] from `this` up to but
   * not including `end`.
   */
  def until(end: Int, step: Int): Range = Range(value, end, step)

  /**
   * Create an inclusive <code>Range</code> from this <code>NonZeroInt</code> value
   * to the specified <code>end</code> with step value 1.
   *
   * @param end The final bound of the range to make.
   * @return A [[scala.collection.immutable.Range]] from `'''this'''` up to
   * and including `end`.
   */
  def to(end: Int): Range.Inclusive = Range.inclusive(value, end)

  /**
   * Create an inclusive <code>Range</code> from this <code>NonZeroInt</code> value
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
  def max(that: NonZeroInt): NonZeroInt = if (math.max(value, that.value) == value) this else that

  /**
   * Returns <code>this</code> if <code>this &lt; that</code> or <code>that</code> otherwise.
   */
  def min(that: NonZeroInt): NonZeroInt = if (math.min(value, that.value) == value) this else that

  /**
   * Applies the passed <code>Int =&gt; Int</code> function to the underlying <code>Int</code>
   * value, and if the result is positive, returns the result wrapped in a <code>NonZeroInt</code>,
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
   * <code>NonZeroInt</code>'s underlying <code>Int</code> value and if the result
   * is non-zero, it will return a <code>NonZeroInt</code> representing that value.
   * Otherwise, the <code>Int</code> value returned by the given function is
   * not non-zero, so this method will throw <code>AssertionError</code>.
   * </p>
   *
   * <p>
   * This method differs from a vanilla <code>assert</code> or <code>ensuring</code>
   * call in that you get something you didn't already have if the assertion
   * succeeds: a <em>type</em> that promises an <code>Int</code> is non-zero.
   * With this method, you are asserting that you are convinced the result of
   * the computation represented by applying the given function to this <code>NonZeroInt</code>'s
   * value will not overflow. Instead of overflowing silently like <code>Int</code>, this
   * method will signal an overflow with a loud <code>AssertionError</code>.
   * </p>
   *
   * @param f the <code>Int =&gt; Int</code> function to apply to this <code>NonZeroInt</code>'s
   *     underlying <code>Int</code> value.
   * @return the result of applying this <code>NonZeroInt</code>'s underlying <code>Int</code> value to
   *     to the passed function, wrapped in a <code>NonZeroInt</code> if it is non-zero (else throws <code>AssertionError</code>).
   * @throws AssertionError if the result of applying this <code>NonZeroInt</code>'s underlying <code>Int</code> value to
   *     to the passed function is not non-zero.
   */
  def ensuringValid(f: Int => Int): NonZeroInt = {
    val candidateResult: Int = f(value)
    if (NonZeroIntMacro.isValid(candidateResult)) new NonZeroInt(candidateResult)
    else throw new AssertionError(s"${candidateResult.toString()}, the result of applying the passed function to ${value.toString()}, was not a valid NonZeroInt")
  }
}

/**
 * The companion object for <code>NonZeroInt</code> that offers factory methods that
 * produce <code>NonZeroInt</code>s, implicit widening conversions from <code>NonZeroInt</code>
 * to other numeric types, and maximum and minimum constant values for <code>NonZeroInt</code>.
 */
object NonZeroInt {
  /**
   * The largest value representable as a non-zero <code>Int</code>, which is <code>NonZeroInt(2147483647)</code>.
   */
  final val MaxValue: NonZeroInt = NonZeroInt.ensuringValid(Int.MaxValue)
  /**
   * The smallest value representable as a non-zero <code>Int</code>, which is <code>NonZeroInt(-2147483648)</code>.
   */
  final val MinValue: NonZeroInt = NonZeroInt.ensuringValid(Int.MinValue) // Can't use the macro here

  /**
   * A factory method that produces an <code>Option[NonZeroInt]</code> given an
   * <code>Int</code> value.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-zero <code>Int</code>, <em>i.e.</em>, a non-zero integer value,
   * it will return a <code>NonZeroInt</code> representing that value,
   * wrapped in a <code>Some</code>. Otherwise, the passed <code>Int</code>
   * value is not non-zero integer value, so this method will return <code>None</code>.
   * </p>
   *
   * <p>
   * This factory method differs from the <code>apply</code> factory method
   * in that <code>apply</code> is implemented via a macro that inspects
   * <code>Int</code> literals at compile time, whereas <code>from</code> inspects
   * <code>Int</code> values at run time.
   * </p>
   *
   * @param value the <code>Int</code> to inspect, and if non-zero, return
   *     wrapped in a <code>Some[NonZeroInt]</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Some[NonZeroInt]</code>, if it is non-zero, else <code>None</code>.
   */
  def from(value: Int): Option[NonZeroInt] =
    if (NonZeroIntMacro.isValid(value)) Some(new NonZeroInt(value)) else None

  /**
   * A factory/assertion method that produces a <code>NonZeroInt</code> given a
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
   * it is a non-zero <code>Int</code>, it will return a <code>NonZeroInt</code>
   * representing that value.  Otherwise, the passed <code>Int</code> value is not non-zero, so this
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
   * succeeds: a <em>type</em> that promises an <code>Int</code> is non-zero.
   * </p>
   *
   * @param value the <code>Int</code> to inspect, and if non-zero, return
   *     wrapped in a <code>NonZeroInt</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>NonZeroInt</code>, if it is non-zero, else throws <code>AssertionError</code>.
   * @throws AssertionError if the passed value is not non-zero
   */
  def ensuringValid(value: Int): NonZeroInt =
    if (NonZeroIntMacro.isValid(value)) new NonZeroInt(value) else {
      throw new AssertionError(s"${value.toString()} was not a valid NonZeroInt")
    }

  /**
   * A factory/validation method that produces a <code>NonZeroInt</code>, wrapped
   * in a <code>Success</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an <code>AssertionError</code>, wrapped
   * in a <code>Failure</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-zero <code>Int</code>, it will return a <code>NonZeroInt</code>
   * representing that value, wrapped in a <code>Success</code>.
   * Otherwise, the passed <code>Int</code> value is not non-zero, so this
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
   * @param value the <code>Int</code> to inspect, and if non-zero, return
   *     wrapped in a <code>Success(NonZeroInt)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Success(NonZeroInt)</code>, if it is non-zero, else a <code>Failure(AssertionError)</code>.
   */
   def tryingValid(value: Int): Try[NonZeroInt] =
     if (NonZeroIntMacro.isValid(value))
       Success(new NonZeroInt(value))
     else
       Failure(new AssertionError(s"${value.toString()} was not a valid NonZeroInt"))

  /**
   * A validation method that produces a <code>Pass</code>
   * given a valid <code>Int</code> value, or
   * an error value of type <code>E</code> produced by passing the
   * given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Fail</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-zero <code>Int</code>, it will return a <code>Pass</code>.
   * Otherwise, the passed <code>Int</code> value is non-zero, so this
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
   * @param value the `Int` to validate that it is non-zero.
   * @return a `Pass` if the specified `Int` value is non-zero,
   *   else a `Fail` containing an error value produced by passing the
   *   specified `Int` to the given function `f`.
   */
  def passOrElse[E](value: Int)(f: Int => E): Validation[E] =
    if (NonZeroIntMacro.isValid(value)) Pass else Fail(f(value))

  /**
   * A factory/validation method that produces a <code>NonZeroInt</code>, wrapped
   * in a <code>Good</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>B</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Bad</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-zero <code>Int</code>, it will return a <code>NonZeroInt</code>
   * representing that value, wrapped in a <code>Good</code>.
   * Otherwise, the passed <code>Int</code> value is not non-zero, so this
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
   * @param value the <code>Int</code> to inspect, and if non-zero, return
   *     wrapped in a <code>Good(NonZeroInt)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Good(NonZeroInt)</code>, if it is non-zero, else a <code>Bad(f(value))</code>.
   */
  def goodOrElse[B](value: Int)(f: Int => B): NonZeroInt Or B =
    if (NonZeroIntMacro.isValid(value)) Good(NonZeroInt.ensuringValid(value)) else Bad(f(value))

  /**
   * A factory/validation method that produces a <code>NonZeroInt</code>, wrapped
   * in a <code>Right</code>, given a valid <code>Int</code> value, or if the
   * given <code>Int</code> is invalid, an error value of type <code>L</code>
   * produced by passing the given <em>invalid</em> <code>Int</code> value
   * to the given function <code>f</code>, wrapped in a <code>Left</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a non-zero <code>Int</code>, it will return a <code>NonZeroInt</code>
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
   *     wrapped in a <code>Right(NonZeroInt)</code>.
   * @return the specified <code>Int</code> value wrapped
   *     in a <code>Right(NonZeroInt)</code>, if it is non-zero, else a <code>Left(f(value))</code>.
   */
  def rightOrElse[L](value: Int)(f: Int => L): Either[L, NonZeroInt] =
    if (NonZeroIntMacro.isValid(value)) Right(NonZeroInt.ensuringValid(value)) else Left(f(value))

  /**
   * A predicate method that returns true if a given
   * <code>Int</code> value is non-zero.
   *
   * @param value the <code>Int</code> to inspect, and if non-zero, return true.
   * @return true if the specified <code>Int</code> is non-zero, else false.
   */
  def isValid(value: Int): Boolean = NonZeroIntMacro.isValid(value)

  /**
   * A factory method that produces a <code>NonZeroInt</code> given a
   * <code>Int</code> value and a default <code>NonZeroInt</code>.
   *
   * <p>
   * This method will inspect the passed <code>Int</code> value and if
   * it is a positive <code>Int</code>, <em>i.e.</em>, a value greater
   * than 0.0, it will return a <code>NonZeroInt</code> representing that value.
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
   * @param default the <code>NonZeroInt</code> to return if the passed
   *     <code>Int</code> value is not positive.
   * @return the specified <code>Int</code> value wrapped in a
   *     <code>NonZeroInt</code>, if it is positive, else the
   *     <code>default</code> <code>NonZeroInt</code> value.
   */
  def fromOrElse(value: Int, default: => NonZeroInt): NonZeroInt =
    if (NonZeroIntMacro.isValid(value)) new NonZeroInt(value) else default

  import language.experimental.macros

  /**
   * A factory method, implemented via a macro, that produces a <code>NonZeroInt</code>
   * if passed a valid <code>Int</code> literal, otherwise a compile time error.
   *
   * <p>
   * The macro that implements this method will inspect the specified <code>Int</code>
   * expression at compile time. If
   * the expression is a positive <code>Int</code> literal, <em>i.e.</em>, with a
   * value greater than 0, it will return a <code>NonZeroInt</code> representing that value.
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
   *     and if positive, to return wrapped in a <code>NonZeroInt</code> at run time.
   * @return the specified, valid <code>Int</code> literal value wrapped
   *     in a <code>NonZeroInt</code>. (If the specified expression is not a valid
   *     <code>Int</code> literal, the invocation of this method will not
   *     compile.)
   */
  implicit def apply(value: Int): NonZeroInt = macro NonZeroIntMacro.apply

  /**
   * Implicit widening conversion from <code>NonZeroInt</code> to <code>Int</code>.
   *
   * @param pos the <code>NonZeroInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NonZeroInt</code>.
   */
  implicit def widenToInt(pos: NonZeroInt): Int = pos.value

  /**
   * Implicit widening conversion from <code>NonZeroInt</code> to <code>Long</code>.
   *
   * @param pos the <code>NonZeroInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NonZeroInt</code>,
   *     widened to <code>Long</code>.
   */
  implicit def widenToLong(pos: NonZeroInt): Long = pos.value

  /**
   * Implicit widening conversion from <code>NonZeroInt</code> to <code>Float</code>.
   *
   * @param pos the <code>NonZeroInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NonZeroInt</code>,
   *     widened to <code>Float</code>.
   */
  implicit def widenToFloat(pos: NonZeroInt): Float = pos.value

  /**
   * Implicit widening conversion from <code>NonZeroInt</code> to <code>Double</code>.
   *
   * @param pos the <code>NonZeroInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NonZeroInt</code>,
   *     widened to <code>Double</code>.
   */
  implicit def widenToDouble(pos: NonZeroInt): Double = pos.value


  /**
   * Implicit widening conversion from <code>NonZeroInt</code> to <code>NonZeroLong</code>.
   *
   * @param pos the <code>NonZeroInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NonZeroInt</code>,
   *     widened to <code>Long</code> and wrapped in a <code>NonZeroLong</code>.
   */
  implicit def widenToNonZeroLong(pos: NonZeroInt): NonZeroLong = NonZeroLong.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NonZeroInt</code> to <code>NonZeroFloat</code>.
   *
   * @param pos the <code>NonZeroInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NonZeroInt</code>,
   *     widened to <code>Float</code> and wrapped in a <code>NonZeroFloat</code>.
   */
  implicit def widenToNonZeroFloat(pos: NonZeroInt): NonZeroFloat = NonZeroFloat.ensuringValid(pos.value)

  /**
   * Implicit widening conversion from <code>NonZeroInt</code> to <code>NonZeroDouble</code>.
   *
   * @param pos the <code>NonZeroInt</code> to widen
   * @return the <code>Int</code> value underlying the specified <code>NonZeroInt</code>,
   *     widened to <code>Double</code> and wrapped in a <code>NonZeroDouble</code>.
   */
  implicit def widenToNonZeroDouble(pos: NonZeroInt): NonZeroDouble = NonZeroDouble.ensuringValid(pos.value)


  /**
   * Implicit Ordering instance.
   */
  implicit val ordering: Ordering[NonZeroInt] =
    new Ordering[NonZeroInt] {
      def compare(x: NonZeroInt, y: NonZeroInt): Int = x.toInt.compare(y)
    }

  
}
