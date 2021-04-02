
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

import org.scalactic.Resources
import reflect.macros.Context

private[anyvals] object PosLongMacro extends CompileTimeAssertions {

  def isValid(i: Long): Boolean = i > 0L

  
  def apply(c: Context)(value: c.Expr[Long]): c.Expr[PosLong] = {
    val notValidMsg = Resources.notValidPosLong
    val notLiteralMsg = Resources.notLiteralPosLong
    import c.universe._
    ensureValidLongLiteral(c)(value, notValidMsg, notLiteralMsg)(isValid)
    reify { PosLong.ensuringValid(value.splice) }
  }

}
