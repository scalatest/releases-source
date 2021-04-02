/*
 * Copyright 2001-2018 Artima, Inc.
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
package org.scalactic

import scala.annotation.unchecked.{ uncheckedVariance => uV }

private[org] object ColCompatHelper {

  type IndexedSeqLike[+A, +Repr] = scala.collection.IndexedSeqOps[A, IndexedSeq, Repr]

  def aggregate[A, B](col: Iterable[A], z: =>B)(seqop: (B, A) => B, combop: (B, B) => B): B = col.foldLeft(z)(seqop)

  type WithFilter[+A, +CC[_]] = scala.collection.WithFilter[A, CC]

  type IterableOnce[+A] = scala.collection.IterableOnce[A]

  type Factory[-A, +C] = scala.collection.Factory[A, C]

  object Factory {}

  def className(col: scala.collection.Iterable[_]): String = {
    val colToString = col.toString
    val bracketIdx = colToString.indexOf("(")
    if (bracketIdx >= 0)
      colToString.take(bracketIdx)
    else
      org.scalactic.NameUtil.getSimpleNameOfAnObjectsClass(col)
  }

  def newBuilder[A, C](f: Factory[A, C]): scala.collection.mutable.Builder[A, C] = f.newBuilder

  type StringOps = scala.collection.StringOps
}

        