package org.scalactic

import java.util.ResourceBundle
import java.text.MessageFormat

private[scalactic] object Resources {

lazy val resourceBundle = ResourceBundle.getBundle("org.scalactic.ScalacticBundle")

private def makeString(resourceName: String, args: Array[Any]): String = {
  val raw = resourceBundle.getString(resourceName)
  formatString(raw, args)
}

def formatString(rawString: String, args: Array[Any]): String = {
  val msgFmt = new MessageFormat(rawString)
  msgFmt.format(args.toArray)
}

def bigProblems: String = resourceBundle.getString("bigProblems")

def rawBigProblems: String = resourceBundle.getString("bigProblems")

def bigProblemsWithMessage(param0: Any): String = makeString("bigProblemsWithMessage", Array(param0))

def rawBigProblemsWithMessage: String = resourceBundle.getString("bigProblemsWithMessage")

def didNotEqual(param0: Any, param1: Any): String = makeString("didNotEqual", Array(param0, param1))

def rawDidNotEqual: String = resourceBundle.getString("didNotEqual")

def equaled(param0: Any, param1: Any): String = makeString("equaled", Array(param0, param1))

def rawEqualed: String = resourceBundle.getString("equaled")

def wasPlusOrMinus(param0: Any, param1: Any, param2: Any): String = makeString("wasPlusOrMinus", Array(param0, param1, param2))

def rawWasPlusOrMinus: String = resourceBundle.getString("wasPlusOrMinus")

def wasNotPlusOrMinus(param0: Any, param1: Any, param2: Any): String = makeString("wasNotPlusOrMinus", Array(param0, param1, param2))

def rawWasNotPlusOrMinus: String = resourceBundle.getString("wasNotPlusOrMinus")

def expressionWasFalse: String = resourceBundle.getString("expressionWasFalse")

def rawExpressionWasFalse: String = resourceBundle.getString("expressionWasFalse")

def expressionWasTrue: String = resourceBundle.getString("expressionWasTrue")

def rawExpressionWasTrue: String = resourceBundle.getString("expressionWasTrue")

def wasNull(param0: Any): String = makeString("wasNull", Array(param0))

def rawWasNull: String = resourceBundle.getString("wasNull")

def wereNull(param0: Any): String = makeString("wereNull", Array(param0))

def rawWereNull: String = resourceBundle.getString("wereNull")

def comma: String = resourceBundle.getString("comma")

def rawComma: String = resourceBundle.getString("comma")

def and(param0: Any, param1: Any): String = makeString("and", Array(param0, param1))

def rawAnd: String = resourceBundle.getString("and")

def wasTrue(param0: Any): String = makeString("wasTrue", Array(param0))

def rawWasTrue: String = resourceBundle.getString("wasTrue")

def wasFalse(param0: Any): String = makeString("wasFalse", Array(param0))

def rawWasFalse: String = resourceBundle.getString("wasFalse")

def wasLessThan(param0: Any, param1: Any): String = makeString("wasLessThan", Array(param0, param1))

def rawWasLessThan: String = resourceBundle.getString("wasLessThan")

def wasNotLessThan(param0: Any, param1: Any): String = makeString("wasNotLessThan", Array(param0, param1))

def rawWasNotLessThan: String = resourceBundle.getString("wasNotLessThan")

def wasGreaterThan(param0: Any, param1: Any): String = makeString("wasGreaterThan", Array(param0, param1))

def rawWasGreaterThan: String = resourceBundle.getString("wasGreaterThan")

def wasNotGreaterThan(param0: Any, param1: Any): String = makeString("wasNotGreaterThan", Array(param0, param1))

def rawWasNotGreaterThan: String = resourceBundle.getString("wasNotGreaterThan")

def wasLessThanOrEqualTo(param0: Any, param1: Any): String = makeString("wasLessThanOrEqualTo", Array(param0, param1))

def rawWasLessThanOrEqualTo: String = resourceBundle.getString("wasLessThanOrEqualTo")

def wasNotLessThanOrEqualTo(param0: Any, param1: Any): String = makeString("wasNotLessThanOrEqualTo", Array(param0, param1))

def rawWasNotLessThanOrEqualTo: String = resourceBundle.getString("wasNotLessThanOrEqualTo")

def wasGreaterThanOrEqualTo(param0: Any, param1: Any): String = makeString("wasGreaterThanOrEqualTo", Array(param0, param1))

def rawWasGreaterThanOrEqualTo: String = resourceBundle.getString("wasGreaterThanOrEqualTo")

def wasNotGreaterThanOrEqualTo(param0: Any, param1: Any): String = makeString("wasNotGreaterThanOrEqualTo", Array(param0, param1))

def rawWasNotGreaterThanOrEqualTo: String = resourceBundle.getString("wasNotGreaterThanOrEqualTo")

def didNotStartWith(param0: Any, param1: Any): String = makeString("didNotStartWith", Array(param0, param1))

def rawDidNotStartWith: String = resourceBundle.getString("didNotStartWith")

def startedWith(param0: Any, param1: Any): String = makeString("startedWith", Array(param0, param1))

def rawStartedWith: String = resourceBundle.getString("startedWith")

def didNotEndWith(param0: Any, param1: Any): String = makeString("didNotEndWith", Array(param0, param1))

def rawDidNotEndWith: String = resourceBundle.getString("didNotEndWith")

def endedWith(param0: Any, param1: Any): String = makeString("endedWith", Array(param0, param1))

def rawEndedWith: String = resourceBundle.getString("endedWith")

def didNotContain(param0: Any, param1: Any): String = makeString("didNotContain", Array(param0, param1))

def rawDidNotContain: String = resourceBundle.getString("didNotContain")

def contained(param0: Any, param1: Any): String = makeString("contained", Array(param0, param1))

def rawContained: String = resourceBundle.getString("contained")

def didNotContainKey(param0: Any, param1: Any): String = makeString("didNotContainKey", Array(param0, param1))

def rawDidNotContainKey: String = resourceBundle.getString("didNotContainKey")

def containedKey(param0: Any, param1: Any): String = makeString("containedKey", Array(param0, param1))

def rawContainedKey: String = resourceBundle.getString("containedKey")

def wasTheSameInstanceAs(param0: Any, param1: Any): String = makeString("wasTheSameInstanceAs", Array(param0, param1))

def rawWasTheSameInstanceAs: String = resourceBundle.getString("wasTheSameInstanceAs")

def wasNotTheSameInstanceAs(param0: Any, param1: Any): String = makeString("wasNotTheSameInstanceAs", Array(param0, param1))

def rawWasNotTheSameInstanceAs: String = resourceBundle.getString("wasNotTheSameInstanceAs")

def wasNotEmpty(param0: Any): String = makeString("wasNotEmpty", Array(param0))

def rawWasNotEmpty: String = resourceBundle.getString("wasNotEmpty")

def wasEmpty(param0: Any): String = makeString("wasEmpty", Array(param0))

def rawWasEmpty: String = resourceBundle.getString("wasEmpty")

def wasNotInstanceOf(param0: Any, param1: Any): String = makeString("wasNotInstanceOf", Array(param0, param1))

def rawWasNotInstanceOf: String = resourceBundle.getString("wasNotInstanceOf")

def wasInstanceOf(param0: Any, param1: Any): String = makeString("wasInstanceOf", Array(param0, param1))

def rawWasInstanceOf: String = resourceBundle.getString("wasInstanceOf")

def hadLengthInsteadOfExpectedLength(param0: Any, param1: Any, param2: Any): String = makeString("hadLengthInsteadOfExpectedLength", Array(param0, param1, param2))

def rawHadLengthInsteadOfExpectedLength: String = resourceBundle.getString("hadLengthInsteadOfExpectedLength")

def hadLength(param0: Any, param1: Any): String = makeString("hadLength", Array(param0, param1))

def rawHadLength: String = resourceBundle.getString("hadLength")

def hadSizeInsteadOfExpectedSize(param0: Any, param1: Any, param2: Any): String = makeString("hadSizeInsteadOfExpectedSize", Array(param0, param1, param2))

def rawHadSizeInsteadOfExpectedSize: String = resourceBundle.getString("hadSizeInsteadOfExpectedSize")

def hadSize(param0: Any, param1: Any): String = makeString("hadSize", Array(param0, param1))

def rawHadSize: String = resourceBundle.getString("hadSize")

def commaBut(param0: Any, param1: Any): String = makeString("commaBut", Array(param0, param1))

def rawCommaBut: String = resourceBundle.getString("commaBut")

def commaAnd(param0: Any, param1: Any): String = makeString("commaAnd", Array(param0, param1))

def rawCommaAnd: String = resourceBundle.getString("commaAnd")

def variableWasValue(param0: Any, param1: Any): String = makeString("variableWasValue", Array(param0, param1))

def rawVariableWasValue: String = resourceBundle.getString("variableWasValue")

def notValidPosInt: String = resourceBundle.getString("notValidPosInt")

def rawNotValidPosInt: String = resourceBundle.getString("notValidPosInt")

def notLiteralPosInt: String = resourceBundle.getString("notLiteralPosInt")

def rawNotLiteralPosInt: String = resourceBundle.getString("notLiteralPosInt")

def notValidPosLong: String = resourceBundle.getString("notValidPosLong")

def rawNotValidPosLong: String = resourceBundle.getString("notValidPosLong")

def notLiteralPosLong: String = resourceBundle.getString("notLiteralPosLong")

def rawNotLiteralPosLong: String = resourceBundle.getString("notLiteralPosLong")

def notValidPosFloat: String = resourceBundle.getString("notValidPosFloat")

def rawNotValidPosFloat: String = resourceBundle.getString("notValidPosFloat")

def notLiteralPosFloat: String = resourceBundle.getString("notLiteralPosFloat")

def rawNotLiteralPosFloat: String = resourceBundle.getString("notLiteralPosFloat")

def notValidPosDouble: String = resourceBundle.getString("notValidPosDouble")

def rawNotValidPosDouble: String = resourceBundle.getString("notValidPosDouble")

def notLiteralPosDouble: String = resourceBundle.getString("notLiteralPosDouble")

def rawNotLiteralPosDouble: String = resourceBundle.getString("notLiteralPosDouble")

def notValidPosZInt: String = resourceBundle.getString("notValidPosZInt")

def rawNotValidPosZInt: String = resourceBundle.getString("notValidPosZInt")

def notLiteralPosZInt: String = resourceBundle.getString("notLiteralPosZInt")

def rawNotLiteralPosZInt: String = resourceBundle.getString("notLiteralPosZInt")

def notValidPosZLong: String = resourceBundle.getString("notValidPosZLong")

def rawNotValidPosZLong: String = resourceBundle.getString("notValidPosZLong")

def notLiteralPosZLong: String = resourceBundle.getString("notLiteralPosZLong")

def rawNotLiteralPosZLong: String = resourceBundle.getString("notLiteralPosZLong")

def notValidPosZFloat: String = resourceBundle.getString("notValidPosZFloat")

def rawNotValidPosZFloat: String = resourceBundle.getString("notValidPosZFloat")

def notLiteralPosZFloat: String = resourceBundle.getString("notLiteralPosZFloat")

def rawNotLiteralPosZFloat: String = resourceBundle.getString("notLiteralPosZFloat")

def notValidPosZDouble: String = resourceBundle.getString("notValidPosZDouble")

def rawNotValidPosZDouble: String = resourceBundle.getString("notValidPosZDouble")

def notLiteralPosZDouble: String = resourceBundle.getString("notLiteralPosZDouble")

def rawNotLiteralPosZDouble: String = resourceBundle.getString("notLiteralPosZDouble")

def pleaseDefineScalacticFillFilePathnameEnvVar: String = resourceBundle.getString("pleaseDefineScalacticFillFilePathnameEnvVar")

def rawPleaseDefineScalacticFillFilePathnameEnvVar: String = resourceBundle.getString("pleaseDefineScalacticFillFilePathnameEnvVar")

def notValidNonZeroInt: String = resourceBundle.getString("notValidNonZeroInt")

def rawNotValidNonZeroInt: String = resourceBundle.getString("notValidNonZeroInt")

def notLiteralNonZeroInt: String = resourceBundle.getString("notLiteralNonZeroInt")

def rawNotLiteralNonZeroInt: String = resourceBundle.getString("notLiteralNonZeroInt")

def notValidNonZeroLong: String = resourceBundle.getString("notValidNonZeroLong")

def rawNotValidNonZeroLong: String = resourceBundle.getString("notValidNonZeroLong")

def notLiteralNonZeroLong: String = resourceBundle.getString("notLiteralNonZeroLong")

def rawNotLiteralNonZeroLong: String = resourceBundle.getString("notLiteralNonZeroLong")

def notValidNonZeroFloat: String = resourceBundle.getString("notValidNonZeroFloat")

def rawNotValidNonZeroFloat: String = resourceBundle.getString("notValidNonZeroFloat")

def notLiteralNonZeroFloat: String = resourceBundle.getString("notLiteralNonZeroFloat")

def rawNotLiteralNonZeroFloat: String = resourceBundle.getString("notLiteralNonZeroFloat")

def notValidNonZeroDouble: String = resourceBundle.getString("notValidNonZeroDouble")

def rawNotValidNonZeroDouble: String = resourceBundle.getString("notValidNonZeroDouble")

def notLiteralNonZeroDouble: String = resourceBundle.getString("notLiteralNonZeroDouble")

def rawNotLiteralNonZeroDouble: String = resourceBundle.getString("notLiteralNonZeroDouble")

def notValidNegInt: String = resourceBundle.getString("notValidNegInt")

def rawNotValidNegInt: String = resourceBundle.getString("notValidNegInt")

def notLiteralNegInt: String = resourceBundle.getString("notLiteralNegInt")

def rawNotLiteralNegInt: String = resourceBundle.getString("notLiteralNegInt")

def notValidNegLong: String = resourceBundle.getString("notValidNegLong")

def rawNotValidNegLong: String = resourceBundle.getString("notValidNegLong")

def notLiteralNegLong: String = resourceBundle.getString("notLiteralNegLong")

def rawNotLiteralNegLong: String = resourceBundle.getString("notLiteralNegLong")

def notValidNegFloat: String = resourceBundle.getString("notValidNegFloat")

def rawNotValidNegFloat: String = resourceBundle.getString("notValidNegFloat")

def notLiteralNegFloat: String = resourceBundle.getString("notLiteralNegFloat")

def rawNotLiteralNegFloat: String = resourceBundle.getString("notLiteralNegFloat")

def notValidNegDouble: String = resourceBundle.getString("notValidNegDouble")

def rawNotValidNegDouble: String = resourceBundle.getString("notValidNegDouble")

def notLiteralNegDouble: String = resourceBundle.getString("notLiteralNegDouble")

def rawNotLiteralNegDouble: String = resourceBundle.getString("notLiteralNegDouble")

def notValidNegZInt: String = resourceBundle.getString("notValidNegZInt")

def rawNotValidNegZInt: String = resourceBundle.getString("notValidNegZInt")

def notLiteralNegZInt: String = resourceBundle.getString("notLiteralNegZInt")

def rawNotLiteralNegZInt: String = resourceBundle.getString("notLiteralNegZInt")

def notValidNegZLong: String = resourceBundle.getString("notValidNegZLong")

def rawNotValidNegZLong: String = resourceBundle.getString("notValidNegZLong")

def notLiteralNegZLong: String = resourceBundle.getString("notLiteralNegZLong")

def rawNotLiteralNegZLong: String = resourceBundle.getString("notLiteralNegZLong")

def notValidNegZFloat: String = resourceBundle.getString("notValidNegZFloat")

def rawNotValidNegZFloat: String = resourceBundle.getString("notValidNegZFloat")

def notLiteralNegZFloat: String = resourceBundle.getString("notLiteralNegZFloat")

def rawNotLiteralNegZFloat: String = resourceBundle.getString("notLiteralNegZFloat")

def notValidNegZDouble: String = resourceBundle.getString("notValidNegZDouble")

def rawNotValidNegZDouble: String = resourceBundle.getString("notValidNegZDouble")

def notLiteralNegZDouble: String = resourceBundle.getString("notLiteralNegZDouble")

def rawNotLiteralNegZDouble: String = resourceBundle.getString("notLiteralNegZDouble")

def notValidPosFiniteFloat: String = resourceBundle.getString("notValidPosFiniteFloat")

def rawNotValidPosFiniteFloat: String = resourceBundle.getString("notValidPosFiniteFloat")

def notLiteralPosFiniteFloat: String = resourceBundle.getString("notLiteralPosFiniteFloat")

def rawNotLiteralPosFiniteFloat: String = resourceBundle.getString("notLiteralPosFiniteFloat")

def notValidPosFiniteDouble: String = resourceBundle.getString("notValidPosFiniteDouble")

def rawNotValidPosFiniteDouble: String = resourceBundle.getString("notValidPosFiniteDouble")

def notLiteralPosFiniteDouble: String = resourceBundle.getString("notLiteralPosFiniteDouble")

def rawNotLiteralPosFiniteDouble: String = resourceBundle.getString("notLiteralPosFiniteDouble")

def notValidPosZFiniteFloat: String = resourceBundle.getString("notValidPosZFiniteFloat")

def rawNotValidPosZFiniteFloat: String = resourceBundle.getString("notValidPosZFiniteFloat")

def notLiteralPosZFiniteFloat: String = resourceBundle.getString("notLiteralPosZFiniteFloat")

def rawNotLiteralPosZFiniteFloat: String = resourceBundle.getString("notLiteralPosZFiniteFloat")

def notValidPosZFiniteDouble: String = resourceBundle.getString("notValidPosZFiniteDouble")

def rawNotValidPosZFiniteDouble: String = resourceBundle.getString("notValidPosZFiniteDouble")

def notLiteralPosZFiniteDouble: String = resourceBundle.getString("notLiteralPosZFiniteDouble")

def rawNotLiteralPosZFiniteDouble: String = resourceBundle.getString("notLiteralPosZFiniteDouble")

def notValidNegFiniteFloat: String = resourceBundle.getString("notValidNegFiniteFloat")

def rawNotValidNegFiniteFloat: String = resourceBundle.getString("notValidNegFiniteFloat")

def notLiteralNegFiniteFloat: String = resourceBundle.getString("notLiteralNegFiniteFloat")

def rawNotLiteralNegFiniteFloat: String = resourceBundle.getString("notLiteralNegFiniteFloat")

def notValidNegFiniteDouble: String = resourceBundle.getString("notValidNegFiniteDouble")

def rawNotValidNegFiniteDouble: String = resourceBundle.getString("notValidNegFiniteDouble")

def notLiteralNegFiniteDouble: String = resourceBundle.getString("notLiteralNegFiniteDouble")

def rawNotLiteralNegFiniteDouble: String = resourceBundle.getString("notLiteralNegFiniteDouble")

def notValidNegZFiniteFloat: String = resourceBundle.getString("notValidNegZFiniteFloat")

def rawNotValidNegZFiniteFloat: String = resourceBundle.getString("notValidNegZFiniteFloat")

def notLiteralNegZFiniteFloat: String = resourceBundle.getString("notLiteralNegZFiniteFloat")

def rawNotLiteralNegZFiniteFloat: String = resourceBundle.getString("notLiteralNegZFiniteFloat")

def notValidNegZFiniteDouble: String = resourceBundle.getString("notValidNegZFiniteDouble")

def rawNotValidNegZFiniteDouble: String = resourceBundle.getString("notValidNegZFiniteDouble")

def notLiteralNegZFiniteDouble: String = resourceBundle.getString("notLiteralNegZFiniteDouble")

def rawNotLiteralNegZFiniteDouble: String = resourceBundle.getString("notLiteralNegZFiniteDouble")

def notValidNonZeroFiniteFloat: String = resourceBundle.getString("notValidNonZeroFiniteFloat")

def rawNotValidNonZeroFiniteFloat: String = resourceBundle.getString("notValidNonZeroFiniteFloat")

def notLiteralNonZeroFiniteFloat: String = resourceBundle.getString("notLiteralNonZeroFiniteFloat")

def rawNotLiteralNonZeroFiniteFloat: String = resourceBundle.getString("notLiteralNonZeroFiniteFloat")

def notValidNonZeroFiniteDouble: String = resourceBundle.getString("notValidNonZeroFiniteDouble")

def rawNotValidNonZeroFiniteDouble: String = resourceBundle.getString("notValidNonZeroFiniteDouble")

def notLiteralNonZeroFiniteDouble: String = resourceBundle.getString("notLiteralNonZeroFiniteDouble")

def rawNotLiteralNonZeroFiniteDouble: String = resourceBundle.getString("notLiteralNonZeroFiniteDouble")

def notValidFiniteFloat: String = resourceBundle.getString("notValidFiniteFloat")

def rawNotValidFiniteFloat: String = resourceBundle.getString("notValidFiniteFloat")

def notLiteralFiniteFloat: String = resourceBundle.getString("notLiteralFiniteFloat")

def rawNotLiteralFiniteFloat: String = resourceBundle.getString("notLiteralFiniteFloat")

def notValidFiniteDouble: String = resourceBundle.getString("notValidFiniteDouble")

def rawNotValidFiniteDouble: String = resourceBundle.getString("notValidFiniteDouble")

def notLiteralFiniteDouble: String = resourceBundle.getString("notLiteralFiniteDouble")

def rawNotLiteralFiniteDouble: String = resourceBundle.getString("notLiteralFiniteDouble")

def notValidNumericChar: String = resourceBundle.getString("notValidNumericChar")

def rawNotValidNumericChar: String = resourceBundle.getString("notValidNumericChar")

def notLiteralNumericChar: String = resourceBundle.getString("notLiteralNumericChar")

def rawNotLiteralNumericChar: String = resourceBundle.getString("notLiteralNumericChar")

def invalidSize(param0: Any): String = makeString("invalidSize", Array(param0))

def rawInvalidSize: String = resourceBundle.getString("invalidSize")

}
    
