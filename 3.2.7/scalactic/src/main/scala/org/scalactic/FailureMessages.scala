package org.scalactic

private[scalactic] object FailureMessages {

def decorateToStringValue(prettifier: org.scalactic.Prettifier, o: Any): String = prettifier.apply(o)

def bigProblems: String = Resources.bigProblems

def bigProblemsWithMessage(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.bigProblemsWithMessage(prettifier.apply(param0))

def didNotEqual(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotEqual(prettifier.apply(param0), prettifier.apply(param1))

def equaled(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.equaled(prettifier.apply(param0), prettifier.apply(param1))

def wasPlusOrMinus(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.wasPlusOrMinus(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def wasNotPlusOrMinus(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.wasNotPlusOrMinus(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def expressionWasFalse: String = Resources.expressionWasFalse

def expressionWasTrue: String = Resources.expressionWasTrue

def wasNull(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNull(prettifier.apply(param0))

def wereNull(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wereNull(prettifier.apply(param0))

def comma: String = Resources.comma

def and(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.and(prettifier.apply(param0), prettifier.apply(param1))

def wasTrue(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasTrue(prettifier.apply(param0))

def wasFalse(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasFalse(prettifier.apply(param0))

def wasLessThan(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasLessThan(prettifier.apply(param0), prettifier.apply(param1))

def wasNotLessThan(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotLessThan(prettifier.apply(param0), prettifier.apply(param1))

def wasGreaterThan(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasGreaterThan(prettifier.apply(param0), prettifier.apply(param1))

def wasNotGreaterThan(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotGreaterThan(prettifier.apply(param0), prettifier.apply(param1))

def wasLessThanOrEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasLessThanOrEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def wasNotLessThanOrEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotLessThanOrEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def wasGreaterThanOrEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasGreaterThanOrEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def wasNotGreaterThanOrEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotGreaterThanOrEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def didNotStartWith(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotStartWith(prettifier.apply(param0), prettifier.apply(param1))

def startedWith(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.startedWith(prettifier.apply(param0), prettifier.apply(param1))

def didNotEndWith(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotEndWith(prettifier.apply(param0), prettifier.apply(param1))

def endedWith(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.endedWith(prettifier.apply(param0), prettifier.apply(param1))

def didNotContain(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContain(prettifier.apply(param0), prettifier.apply(param1))

def contained(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.contained(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainKey(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainKey(prettifier.apply(param0), prettifier.apply(param1))

def containedKey(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedKey(prettifier.apply(param0), prettifier.apply(param1))

def wasTheSameInstanceAs(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasTheSameInstanceAs(prettifier.apply(param0), prettifier.apply(param1))

def wasNotTheSameInstanceAs(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotTheSameInstanceAs(prettifier.apply(param0), prettifier.apply(param1))

def wasNotEmpty(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNotEmpty(prettifier.apply(param0))

def wasEmpty(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasEmpty(prettifier.apply(param0))

def wasNotInstanceOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotInstanceOf(prettifier.apply(param0), prettifier.apply(param1))

def wasInstanceOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasInstanceOf(prettifier.apply(param0), prettifier.apply(param1))

def hadLengthInsteadOfExpectedLength(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hadLengthInsteadOfExpectedLength(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def hadLength(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.hadLength(prettifier.apply(param0), prettifier.apply(param1))

def hadSizeInsteadOfExpectedSize(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hadSizeInsteadOfExpectedSize(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def hadSize(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.hadSize(prettifier.apply(param0), prettifier.apply(param1))

def commaBut(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.commaBut(prettifier.apply(param0), prettifier.apply(param1))

def commaAnd(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.commaAnd(prettifier.apply(param0), prettifier.apply(param1))

def variableWasValue(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.variableWasValue(prettifier.apply(param0), prettifier.apply(param1))

def notValidPosInt: String = Resources.notValidPosInt

def notLiteralPosInt: String = Resources.notLiteralPosInt

def notValidPosLong: String = Resources.notValidPosLong

def notLiteralPosLong: String = Resources.notLiteralPosLong

def notValidPosFloat: String = Resources.notValidPosFloat

def notLiteralPosFloat: String = Resources.notLiteralPosFloat

def notValidPosDouble: String = Resources.notValidPosDouble

def notLiteralPosDouble: String = Resources.notLiteralPosDouble

def notValidPosZInt: String = Resources.notValidPosZInt

def notLiteralPosZInt: String = Resources.notLiteralPosZInt

def notValidPosZLong: String = Resources.notValidPosZLong

def notLiteralPosZLong: String = Resources.notLiteralPosZLong

def notValidPosZFloat: String = Resources.notValidPosZFloat

def notLiteralPosZFloat: String = Resources.notLiteralPosZFloat

def notValidPosZDouble: String = Resources.notValidPosZDouble

def notLiteralPosZDouble: String = Resources.notLiteralPosZDouble

def pleaseDefineScalacticFillFilePathnameEnvVar: String = Resources.pleaseDefineScalacticFillFilePathnameEnvVar

def notValidNonZeroInt: String = Resources.notValidNonZeroInt

def notLiteralNonZeroInt: String = Resources.notLiteralNonZeroInt

def notValidNonZeroLong: String = Resources.notValidNonZeroLong

def notLiteralNonZeroLong: String = Resources.notLiteralNonZeroLong

def notValidNonZeroFloat: String = Resources.notValidNonZeroFloat

def notLiteralNonZeroFloat: String = Resources.notLiteralNonZeroFloat

def notValidNonZeroDouble: String = Resources.notValidNonZeroDouble

def notLiteralNonZeroDouble: String = Resources.notLiteralNonZeroDouble

def notValidNegInt: String = Resources.notValidNegInt

def notLiteralNegInt: String = Resources.notLiteralNegInt

def notValidNegLong: String = Resources.notValidNegLong

def notLiteralNegLong: String = Resources.notLiteralNegLong

def notValidNegFloat: String = Resources.notValidNegFloat

def notLiteralNegFloat: String = Resources.notLiteralNegFloat

def notValidNegDouble: String = Resources.notValidNegDouble

def notLiteralNegDouble: String = Resources.notLiteralNegDouble

def notValidNegZInt: String = Resources.notValidNegZInt

def notLiteralNegZInt: String = Resources.notLiteralNegZInt

def notValidNegZLong: String = Resources.notValidNegZLong

def notLiteralNegZLong: String = Resources.notLiteralNegZLong

def notValidNegZFloat: String = Resources.notValidNegZFloat

def notLiteralNegZFloat: String = Resources.notLiteralNegZFloat

def notValidNegZDouble: String = Resources.notValidNegZDouble

def notLiteralNegZDouble: String = Resources.notLiteralNegZDouble

def notValidPosFiniteFloat: String = Resources.notValidPosFiniteFloat

def notLiteralPosFiniteFloat: String = Resources.notLiteralPosFiniteFloat

def notValidPosFiniteDouble: String = Resources.notValidPosFiniteDouble

def notLiteralPosFiniteDouble: String = Resources.notLiteralPosFiniteDouble

def notValidPosZFiniteFloat: String = Resources.notValidPosZFiniteFloat

def notLiteralPosZFiniteFloat: String = Resources.notLiteralPosZFiniteFloat

def notValidPosZFiniteDouble: String = Resources.notValidPosZFiniteDouble

def notLiteralPosZFiniteDouble: String = Resources.notLiteralPosZFiniteDouble

def notValidNegFiniteFloat: String = Resources.notValidNegFiniteFloat

def notLiteralNegFiniteFloat: String = Resources.notLiteralNegFiniteFloat

def notValidNegFiniteDouble: String = Resources.notValidNegFiniteDouble

def notLiteralNegFiniteDouble: String = Resources.notLiteralNegFiniteDouble

def notValidNegZFiniteFloat: String = Resources.notValidNegZFiniteFloat

def notLiteralNegZFiniteFloat: String = Resources.notLiteralNegZFiniteFloat

def notValidNegZFiniteDouble: String = Resources.notValidNegZFiniteDouble

def notLiteralNegZFiniteDouble: String = Resources.notLiteralNegZFiniteDouble

def notValidNonZeroFiniteFloat: String = Resources.notValidNonZeroFiniteFloat

def notLiteralNonZeroFiniteFloat: String = Resources.notLiteralNonZeroFiniteFloat

def notValidNonZeroFiniteDouble: String = Resources.notValidNonZeroFiniteDouble

def notLiteralNonZeroFiniteDouble: String = Resources.notLiteralNonZeroFiniteDouble

def notValidFiniteFloat: String = Resources.notValidFiniteFloat

def notLiteralFiniteFloat: String = Resources.notLiteralFiniteFloat

def notValidFiniteDouble: String = Resources.notValidFiniteDouble

def notLiteralFiniteDouble: String = Resources.notLiteralFiniteDouble

def notValidNumericChar: String = Resources.notValidNumericChar

def notLiteralNumericChar: String = Resources.notLiteralNumericChar

def invalidSize(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.invalidSize(prettifier.apply(param0))

}
    