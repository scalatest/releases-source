
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

private[anyvals] object NumericCharMacro extends CompileTimeAssertions {

  def isValid(i: Char): Boolean = i >= '0' && i <= '9'

  
  def apply(c: Context)(value: c.Expr[Char]): c.Expr[NumericChar] = {
    val notValidMsg = Resources.notValidNumericChar
    val notLiteralMsg = Resources.notLiteralNumericChar
    import c.universe._
    ensureValidCharLiteral(c)(value, notValidMsg, notLiteralMsg)(isValid)
    reify { NumericChar.ensuringValid(value.splice) }
  }

}
