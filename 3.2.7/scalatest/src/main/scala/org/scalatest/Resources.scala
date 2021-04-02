package org.scalatest

import java.util.ResourceBundle
import java.text.MessageFormat

private[scalatest] object Resources {

lazy val resourceBundle = ResourceBundle.getBundle("org.scalatest.ScalaTestBundle")

def makeString(resourceName: String, args: Array[Any]): String = {
  val raw = resourceBundle.getString(resourceName)
  formatString(raw, args)
}

def formatString(rawString: String, args: Array[Any]): String = {
  val msgFmt = new MessageFormat(rawString)
  msgFmt.format(args.toArray)
}

def factExceptionWasThrown(param0: Any): String = makeString("factExceptionWasThrown", Array(param0))

def rawFactExceptionWasThrown: String = resourceBundle.getString("factExceptionWasThrown")

def factNoExceptionWasThrown: String = resourceBundle.getString("factNoExceptionWasThrown")

def rawFactNoExceptionWasThrown: String = resourceBundle.getString("factNoExceptionWasThrown")

def midSentenceFactExceptionWasThrown(param0: Any): String = makeString("midSentenceFactExceptionWasThrown", Array(param0))

def rawMidSentenceFactExceptionWasThrown: String = resourceBundle.getString("midSentenceFactExceptionWasThrown")

def midSentenceFactNoExceptionWasThrown: String = resourceBundle.getString("midSentenceFactNoExceptionWasThrown")

def rawMidSentenceFactNoExceptionWasThrown: String = resourceBundle.getString("midSentenceFactNoExceptionWasThrown")

def exceptionExpected(param0: Any): String = makeString("exceptionExpected", Array(param0))

def rawExceptionExpected: String = resourceBundle.getString("exceptionExpected")

def expectedExceptionWasThrown(param0: Any): String = makeString("expectedExceptionWasThrown", Array(param0))

def rawExpectedExceptionWasThrown: String = resourceBundle.getString("expectedExceptionWasThrown")

def midSentenceExceptionExpected(param0: Any): String = makeString("midSentenceExceptionExpected", Array(param0))

def rawMidSentenceExceptionExpected: String = resourceBundle.getString("midSentenceExceptionExpected")

def midSentenceExpectedExceptionWasThrown(param0: Any): String = makeString("midSentenceExpectedExceptionWasThrown", Array(param0))

def rawMidSentenceExpectedExceptionWasThrown: String = resourceBundle.getString("midSentenceExpectedExceptionWasThrown")

def noExceptionWasThrown: String = resourceBundle.getString("noExceptionWasThrown")

def rawNoExceptionWasThrown: String = resourceBundle.getString("noExceptionWasThrown")

def resultWas(param0: Any): String = makeString("resultWas", Array(param0))

def rawResultWas: String = resourceBundle.getString("resultWas")

def exceptionThrown(param0: Any): String = makeString("exceptionThrown", Array(param0))

def rawExceptionThrown: String = resourceBundle.getString("exceptionThrown")

def didNotEqual(param0: Any, param1: Any): String = makeString("didNotEqual", Array(param0, param1))

def rawDidNotEqual: String = resourceBundle.getString("didNotEqual")

def wrongException(param0: Any, param1: Any): String = makeString("wrongException", Array(param0, param1))

def rawWrongException: String = resourceBundle.getString("wrongException")

def midSentenceWrongException(param0: Any, param1: Any): String = makeString("midSentenceWrongException", Array(param0, param1))

def rawMidSentenceWrongException: String = resourceBundle.getString("midSentenceWrongException")

def anException(param0: Any): String = makeString("anException", Array(param0))

def rawAnException: String = resourceBundle.getString("anException")

def exceptionNotExpected(param0: Any): String = makeString("exceptionNotExpected", Array(param0))

def rawExceptionNotExpected: String = resourceBundle.getString("exceptionNotExpected")

def expectedButGot(param0: Any, param1: Any): String = makeString("expectedButGot", Array(param0, param1))

def rawExpectedButGot: String = resourceBundle.getString("expectedButGot")

def expectedAndGot(param0: Any, param1: Any): String = makeString("expectedAndGot", Array(param0, param1))

def rawExpectedAndGot: String = resourceBundle.getString("expectedAndGot")

def midSentenceExpectedButGot(param0: Any, param1: Any): String = makeString("midSentenceExpectedButGot", Array(param0, param1))

def rawMidSentenceExpectedButGot: String = resourceBundle.getString("midSentenceExpectedButGot")

def midSentenceExpectedAndGot(param0: Any, param1: Any): String = makeString("midSentenceExpectedAndGot", Array(param0, param1))

def rawMidSentenceExpectedAndGot: String = resourceBundle.getString("midSentenceExpectedAndGot")

def conditionFalse: String = resourceBundle.getString("conditionFalse")

def rawConditionFalse: String = resourceBundle.getString("conditionFalse")

def refNotNull: String = resourceBundle.getString("refNotNull")

def rawRefNotNull: String = resourceBundle.getString("refNotNull")

def refNull: String = resourceBundle.getString("refNull")

def rawRefNull: String = resourceBundle.getString("refNull")

def floatInfinite(param0: Any, param1: Any, param2: Any): String = makeString("floatInfinite", Array(param0, param1, param2))

def rawFloatInfinite: String = resourceBundle.getString("floatInfinite")

def floatNaN(param0: Any, param1: Any, param2: Any): String = makeString("floatNaN", Array(param0, param1, param2))

def rawFloatNaN: String = resourceBundle.getString("floatNaN")

def doubleInfinite(param0: Any, param1: Any, param2: Any): String = makeString("doubleInfinite", Array(param0, param1, param2))

def rawDoubleInfinite: String = resourceBundle.getString("doubleInfinite")

def doubleNaN(param0: Any, param1: Any, param2: Any): String = makeString("doubleNaN", Array(param0, param1, param2))

def rawDoubleNaN: String = resourceBundle.getString("doubleNaN")

def testEvent(param0: Any, param1: Any): String = makeString("testEvent", Array(param0, param1))

def rawTestEvent: String = resourceBundle.getString("testEvent")

def expressionFailed(param0: Any): String = makeString("expressionFailed", Array(param0))

def rawExpressionFailed: String = resourceBundle.getString("expressionFailed")

def testFailed(param0: Any): String = makeString("testFailed", Array(param0))

def rawTestFailed: String = resourceBundle.getString("testFailed")

def testStarting(param0: Any): String = makeString("testStarting", Array(param0))

def rawTestStarting: String = resourceBundle.getString("testStarting")

def testSucceeded(param0: Any): String = makeString("testSucceeded", Array(param0))

def rawTestSucceeded: String = resourceBundle.getString("testSucceeded")

def testIgnored(param0: Any): String = makeString("testIgnored", Array(param0))

def rawTestIgnored: String = resourceBundle.getString("testIgnored")

def testPending(param0: Any): String = makeString("testPending", Array(param0))

def rawTestPending: String = resourceBundle.getString("testPending")

def testCanceled(param0: Any): String = makeString("testCanceled", Array(param0))

def rawTestCanceled: String = resourceBundle.getString("testCanceled")

def suiteStarting(param0: Any): String = makeString("suiteStarting", Array(param0))

def rawSuiteStarting: String = resourceBundle.getString("suiteStarting")

def suiteCompleted(param0: Any): String = makeString("suiteCompleted", Array(param0))

def rawSuiteCompleted: String = resourceBundle.getString("suiteCompleted")

def suiteAborted(param0: Any): String = makeString("suiteAborted", Array(param0))

def rawSuiteAborted: String = resourceBundle.getString("suiteAborted")

def runAborted: String = resourceBundle.getString("runAborted")

def rawRunAborted: String = resourceBundle.getString("runAborted")

def infoProvided(param0: Any): String = makeString("infoProvided", Array(param0))

def rawInfoProvided: String = resourceBundle.getString("infoProvided")

def alertProvided(param0: Any): String = makeString("alertProvided", Array(param0))

def rawAlertProvided: String = resourceBundle.getString("alertProvided")

def noteProvided(param0: Any): String = makeString("noteProvided", Array(param0))

def rawNoteProvided: String = resourceBundle.getString("noteProvided")

def markupProvided(param0: Any): String = makeString("markupProvided", Array(param0))

def rawMarkupProvided: String = resourceBundle.getString("markupProvided")

def scopeOpened(param0: Any): String = makeString("scopeOpened", Array(param0))

def rawScopeOpened: String = resourceBundle.getString("scopeOpened")

def scopeClosed(param0: Any): String = makeString("scopeClosed", Array(param0))

def rawScopeClosed: String = resourceBundle.getString("scopeClosed")

def scopePending(param0: Any): String = makeString("scopePending", Array(param0))

def rawScopePending: String = resourceBundle.getString("scopePending")

def payloadToString(param0: Any): String = makeString("payloadToString", Array(param0))

def rawPayloadToString: String = resourceBundle.getString("payloadToString")

def noNameSpecified: String = resourceBundle.getString("noNameSpecified")

def rawNoNameSpecified: String = resourceBundle.getString("noNameSpecified")

def runStarting(param0: Any): String = makeString("runStarting", Array(param0))

def rawRunStarting: String = resourceBundle.getString("runStarting")

def rerunStarting(param0: Any): String = makeString("rerunStarting", Array(param0))

def rawRerunStarting: String = resourceBundle.getString("rerunStarting")

def rerunCompleted(param0: Any): String = makeString("rerunCompleted", Array(param0))

def rawRerunCompleted: String = resourceBundle.getString("rerunCompleted")

def rerunStopped(param0: Any): String = makeString("rerunStopped", Array(param0))

def rawRerunStopped: String = resourceBundle.getString("rerunStopped")

def friendlyFailure: String = resourceBundle.getString("friendlyFailure")

def rawFriendlyFailure: String = resourceBundle.getString("friendlyFailure")

def showStackTraceOption: String = resourceBundle.getString("showStackTraceOption")

def rawShowStackTraceOption: String = resourceBundle.getString("showStackTraceOption")

def suitebeforeclass: String = resourceBundle.getString("suitebeforeclass")

def rawSuitebeforeclass: String = resourceBundle.getString("suitebeforeclass")

def reportTestsStarting: String = resourceBundle.getString("reportTestsStarting")

def rawReportTestsStarting: String = resourceBundle.getString("reportTestsStarting")

def reportTestsSucceeded: String = resourceBundle.getString("reportTestsSucceeded")

def rawReportTestsSucceeded: String = resourceBundle.getString("reportTestsSucceeded")

def reportTestsFailed: String = resourceBundle.getString("reportTestsFailed")

def rawReportTestsFailed: String = resourceBundle.getString("reportTestsFailed")

def reportAlerts: String = resourceBundle.getString("reportAlerts")

def rawReportAlerts: String = resourceBundle.getString("reportAlerts")

def reportInfo: String = resourceBundle.getString("reportInfo")

def rawReportInfo: String = resourceBundle.getString("reportInfo")

def reportStackTraces: String = resourceBundle.getString("reportStackTraces")

def rawReportStackTraces: String = resourceBundle.getString("reportStackTraces")

def reportRunStarting: String = resourceBundle.getString("reportRunStarting")

def rawReportRunStarting: String = resourceBundle.getString("reportRunStarting")

def reportRunCompleted: String = resourceBundle.getString("reportRunCompleted")

def rawReportRunCompleted: String = resourceBundle.getString("reportRunCompleted")

def reportSummary: String = resourceBundle.getString("reportSummary")

def rawReportSummary: String = resourceBundle.getString("reportSummary")

def probarg(param0: Any): String = makeString("probarg", Array(param0))

def rawProbarg: String = resourceBundle.getString("probarg")

def errBuildingDispatchReporter: String = resourceBundle.getString("errBuildingDispatchReporter")

def rawErrBuildingDispatchReporter: String = resourceBundle.getString("errBuildingDispatchReporter")

def missingFileName: String = resourceBundle.getString("missingFileName")

def rawMissingFileName: String = resourceBundle.getString("missingFileName")

def missingReporterClassName: String = resourceBundle.getString("missingReporterClassName")

def rawMissingReporterClassName: String = resourceBundle.getString("missingReporterClassName")

def errParsingArgs: String = resourceBundle.getString("errParsingArgs")

def rawErrParsingArgs: String = resourceBundle.getString("errParsingArgs")

def invalidConfigOption(param0: Any): String = makeString("invalidConfigOption", Array(param0))

def rawInvalidConfigOption: String = resourceBundle.getString("invalidConfigOption")

def cantOpenFile: String = resourceBundle.getString("cantOpenFile")

def rawCantOpenFile: String = resourceBundle.getString("cantOpenFile")

def reporterThrew(param0: Any): String = makeString("reporterThrew", Array(param0))

def rawReporterThrew: String = resourceBundle.getString("reporterThrew")

def reporterDisposeThrew: String = resourceBundle.getString("reporterDisposeThrew")

def rawReporterDisposeThrew: String = resourceBundle.getString("reporterDisposeThrew")

def slowpokeDetectorEventNotFound(param0: Any, param1: Any, param2: Any): String = makeString("slowpokeDetectorEventNotFound", Array(param0, param1, param2))

def rawSlowpokeDetectorEventNotFound: String = resourceBundle.getString("slowpokeDetectorEventNotFound")

def suiteExecutionStarting: String = resourceBundle.getString("suiteExecutionStarting")

def rawSuiteExecutionStarting: String = resourceBundle.getString("suiteExecutionStarting")

def executeException: String = resourceBundle.getString("executeException")

def rawExecuteException: String = resourceBundle.getString("executeException")

def executeExceptionWithMessage(param0: Any): String = makeString("executeExceptionWithMessage", Array(param0))

def rawExecuteExceptionWithMessage: String = resourceBundle.getString("executeExceptionWithMessage")

def runOnSuiteException: String = resourceBundle.getString("runOnSuiteException")

def rawRunOnSuiteException: String = resourceBundle.getString("runOnSuiteException")

def runOnSuiteExceptionWithMessage(param0: Any): String = makeString("runOnSuiteExceptionWithMessage", Array(param0))

def rawRunOnSuiteExceptionWithMessage: String = resourceBundle.getString("runOnSuiteExceptionWithMessage")

def suiteCompletedNormally: String = resourceBundle.getString("suiteCompletedNormally")

def rawSuiteCompletedNormally: String = resourceBundle.getString("suiteCompletedNormally")

def notOneOfTheChosenStyles(param0: Any, param1: Any): String = makeString("notOneOfTheChosenStyles", Array(param0, param1))

def rawNotOneOfTheChosenStyles: String = resourceBundle.getString("notOneOfTheChosenStyles")

def notTheChosenStyle(param0: Any, param1: Any): String = makeString("notTheChosenStyle", Array(param0, param1))

def rawNotTheChosenStyle: String = resourceBundle.getString("notTheChosenStyle")

def Rerun: String = resourceBundle.getString("Rerun")

def rawRerun: String = resourceBundle.getString("Rerun")

def executeStopping: String = resourceBundle.getString("executeStopping")

def rawExecuteStopping: String = resourceBundle.getString("executeStopping")

def illegalReporterArg(param0: Any): String = makeString("illegalReporterArg", Array(param0))

def rawIllegalReporterArg: String = resourceBundle.getString("illegalReporterArg")

def cantLoadReporterClass(param0: Any): String = makeString("cantLoadReporterClass", Array(param0))

def rawCantLoadReporterClass: String = resourceBundle.getString("cantLoadReporterClass")

def cantInstantiateReporter(param0: Any): String = makeString("cantInstantiateReporter", Array(param0))

def rawCantInstantiateReporter: String = resourceBundle.getString("cantInstantiateReporter")

def overwriteExistingFile(param0: Any): String = makeString("overwriteExistingFile", Array(param0))

def rawOverwriteExistingFile: String = resourceBundle.getString("overwriteExistingFile")

def cannotLoadSuite(param0: Any): String = makeString("cannotLoadSuite", Array(param0))

def rawCannotLoadSuite: String = resourceBundle.getString("cannotLoadSuite")

def cannotLoadDiscoveredSuite(param0: Any): String = makeString("cannotLoadDiscoveredSuite", Array(param0))

def rawCannotLoadDiscoveredSuite: String = resourceBundle.getString("cannotLoadDiscoveredSuite")

def nonSuite: String = resourceBundle.getString("nonSuite")

def rawNonSuite: String = resourceBundle.getString("nonSuite")

def cannotInstantiateSuite(param0: Any): String = makeString("cannotInstantiateSuite", Array(param0))

def rawCannotInstantiateSuite: String = resourceBundle.getString("cannotInstantiateSuite")

def cannotLoadClass(param0: Any): String = makeString("cannotLoadClass", Array(param0))

def rawCannotLoadClass: String = resourceBundle.getString("cannotLoadClass")

def bigProblems: String = resourceBundle.getString("bigProblems")

def rawBigProblems: String = resourceBundle.getString("bigProblems")

def bigProblemsWithMessage(param0: Any): String = makeString("bigProblemsWithMessage", Array(param0))

def rawBigProblemsWithMessage: String = resourceBundle.getString("bigProblemsWithMessage")

def bigProblemsMaybeCustomReporter: String = resourceBundle.getString("bigProblemsMaybeCustomReporter")

def rawBigProblemsMaybeCustomReporter: String = resourceBundle.getString("bigProblemsMaybeCustomReporter")

def cannotFindMethod(param0: Any): String = makeString("cannotFindMethod", Array(param0))

def rawCannotFindMethod: String = resourceBundle.getString("cannotFindMethod")

def securityWhenRerunning(param0: Any): String = makeString("securityWhenRerunning", Array(param0))

def rawSecurityWhenRerunning: String = resourceBundle.getString("securityWhenRerunning")

def overwriteDialogTitle: String = resourceBundle.getString("overwriteDialogTitle")

def rawOverwriteDialogTitle: String = resourceBundle.getString("overwriteDialogTitle")

def openPrefs: String = resourceBundle.getString("openPrefs")

def rawOpenPrefs: String = resourceBundle.getString("openPrefs")

def savePrefs: String = resourceBundle.getString("savePrefs")

def rawSavePrefs: String = resourceBundle.getString("savePrefs")

def runsFailures: String = resourceBundle.getString("runsFailures")

def rawRunsFailures: String = resourceBundle.getString("runsFailures")

def allEvents: String = resourceBundle.getString("allEvents")

def rawAllEvents: String = resourceBundle.getString("allEvents")

def needFileNameTitle: String = resourceBundle.getString("needFileNameTitle")

def rawNeedFileNameTitle: String = resourceBundle.getString("needFileNameTitle")

def needFileNameMessage: String = resourceBundle.getString("needFileNameMessage")

def rawNeedFileNameMessage: String = resourceBundle.getString("needFileNameMessage")

def needClassNameTitle: String = resourceBundle.getString("needClassNameTitle")

def rawNeedClassNameTitle: String = resourceBundle.getString("needClassNameTitle")

def needClassNameMessage: String = resourceBundle.getString("needClassNameMessage")

def rawNeedClassNameMessage: String = resourceBundle.getString("needClassNameMessage")

def NoSuitesFoundText: String = resourceBundle.getString("NoSuitesFoundText")

def rawNoSuitesFoundText: String = resourceBundle.getString("NoSuitesFoundText")

def cantInvokeExceptionText: String = resourceBundle.getString("cantInvokeExceptionText")

def rawCantInvokeExceptionText: String = resourceBundle.getString("cantInvokeExceptionText")

def multipleTestsFailed(param0: Any): String = makeString("multipleTestsFailed", Array(param0))

def rawMultipleTestsFailed: String = resourceBundle.getString("multipleTestsFailed")

def oneTestFailed: String = resourceBundle.getString("oneTestFailed")

def rawOneTestFailed: String = resourceBundle.getString("oneTestFailed")

def oneSuiteAborted: String = resourceBundle.getString("oneSuiteAborted")

def rawOneSuiteAborted: String = resourceBundle.getString("oneSuiteAborted")

def multipleSuitesAborted(param0: Any): String = makeString("multipleSuitesAborted", Array(param0))

def rawMultipleSuitesAborted: String = resourceBundle.getString("multipleSuitesAborted")

def allTestsPassed: String = resourceBundle.getString("allTestsPassed")

def rawAllTestsPassed: String = resourceBundle.getString("allTestsPassed")

def noTestsWereExecuted: String = resourceBundle.getString("noTestsWereExecuted")

def rawNoTestsWereExecuted: String = resourceBundle.getString("noTestsWereExecuted")

def eventsLabel: String = resourceBundle.getString("eventsLabel")

def rawEventsLabel: String = resourceBundle.getString("eventsLabel")

def detailsLabel: String = resourceBundle.getString("detailsLabel")

def rawDetailsLabel: String = resourceBundle.getString("detailsLabel")

def testsRun: String = resourceBundle.getString("testsRun")

def rawTestsRun: String = resourceBundle.getString("testsRun")

def testsFailed: String = resourceBundle.getString("testsFailed")

def rawTestsFailed: String = resourceBundle.getString("testsFailed")

def testsExpected: String = resourceBundle.getString("testsExpected")

def rawTestsExpected: String = resourceBundle.getString("testsExpected")

def testsIgnored: String = resourceBundle.getString("testsIgnored")

def rawTestsIgnored: String = resourceBundle.getString("testsIgnored")

def testsPending: String = resourceBundle.getString("testsPending")

def rawTestsPending: String = resourceBundle.getString("testsPending")

def testsCanceled: String = resourceBundle.getString("testsCanceled")

def rawTestsCanceled: String = resourceBundle.getString("testsCanceled")

def ScalaTestTitle: String = resourceBundle.getString("ScalaTestTitle")

def rawScalaTestTitle: String = resourceBundle.getString("ScalaTestTitle")

def ScalaTestMenu: String = resourceBundle.getString("ScalaTestMenu")

def rawScalaTestMenu: String = resourceBundle.getString("ScalaTestMenu")

def Run: String = resourceBundle.getString("Run")

def rawRun: String = resourceBundle.getString("Run")

def Stop: String = resourceBundle.getString("Stop")

def rawStop: String = resourceBundle.getString("Stop")

def Exit: String = resourceBundle.getString("Exit")

def rawExit: String = resourceBundle.getString("Exit")

def About: String = resourceBundle.getString("About")

def rawAbout: String = resourceBundle.getString("About")

def AboutBoxTitle: String = resourceBundle.getString("AboutBoxTitle")

def rawAboutBoxTitle: String = resourceBundle.getString("AboutBoxTitle")

def AppName: String = resourceBundle.getString("AppName")

def rawAppName: String = resourceBundle.getString("AppName")

def AppCopyright: String = resourceBundle.getString("AppCopyright")

def rawAppCopyright: String = resourceBundle.getString("AppCopyright")

def AppURL: String = resourceBundle.getString("AppURL")

def rawAppURL: String = resourceBundle.getString("AppURL")

def Reason: String = resourceBundle.getString("Reason")

def rawReason: String = resourceBundle.getString("Reason")

def Trademarks: String = resourceBundle.getString("Trademarks")

def rawTrademarks: String = resourceBundle.getString("Trademarks")

def ArtimaInc: String = resourceBundle.getString("ArtimaInc")

def rawArtimaInc: String = resourceBundle.getString("ArtimaInc")

def MoreInfo: String = resourceBundle.getString("MoreInfo")

def rawMoreInfo: String = resourceBundle.getString("MoreInfo")

def ViewMenu: String = resourceBundle.getString("ViewMenu")

def rawViewMenu: String = resourceBundle.getString("ViewMenu")

def JavaSuiteRunnerFile: String = resourceBundle.getString("JavaSuiteRunnerFile")

def rawJavaSuiteRunnerFile: String = resourceBundle.getString("JavaSuiteRunnerFile")

def JavaSuiteRunnerFileDescription: String = resourceBundle.getString("JavaSuiteRunnerFileDescription")

def rawJavaSuiteRunnerFileDescription: String = resourceBundle.getString("JavaSuiteRunnerFileDescription")

def defaultConfiguration: String = resourceBundle.getString("defaultConfiguration")

def rawDefaultConfiguration: String = resourceBundle.getString("defaultConfiguration")

def reporterTypeLabel: String = resourceBundle.getString("reporterTypeLabel")

def rawReporterTypeLabel: String = resourceBundle.getString("reporterTypeLabel")

def graphicReporterType: String = resourceBundle.getString("graphicReporterType")

def rawGraphicReporterType: String = resourceBundle.getString("graphicReporterType")

def customReporterType: String = resourceBundle.getString("customReporterType")

def rawCustomReporterType: String = resourceBundle.getString("customReporterType")

def stdoutReporterType: String = resourceBundle.getString("stdoutReporterType")

def rawStdoutReporterType: String = resourceBundle.getString("stdoutReporterType")

def stderrReporterType: String = resourceBundle.getString("stderrReporterType")

def rawStderrReporterType: String = resourceBundle.getString("stderrReporterType")

def fileReporterType: String = resourceBundle.getString("fileReporterType")

def rawFileReporterType: String = resourceBundle.getString("fileReporterType")

def reporterConfigLabel(param0: Any): String = makeString("reporterConfigLabel", Array(param0))

def rawReporterConfigLabel: String = resourceBundle.getString("reporterConfigLabel")

def unusedField: String = resourceBundle.getString("unusedField")

def rawUnusedField: String = resourceBundle.getString("unusedField")

def couldntRun: String = resourceBundle.getString("couldntRun")

def rawCouldntRun: String = resourceBundle.getString("couldntRun")

def couldntRerun: String = resourceBundle.getString("couldntRerun")

def rawCouldntRerun: String = resourceBundle.getString("couldntRerun")

def MENU_PRESENT_DISCOVERY_STARTING: String = resourceBundle.getString("MENU_PRESENT_DISCOVERY_STARTING")

def rawMENU_PRESENT_DISCOVERY_STARTING: String = resourceBundle.getString("MENU_PRESENT_DISCOVERY_STARTING")

def MENU_PRESENT_DISCOVERY_COMPLETED: String = resourceBundle.getString("MENU_PRESENT_DISCOVERY_COMPLETED")

def rawMENU_PRESENT_DISCOVERY_COMPLETED: String = resourceBundle.getString("MENU_PRESENT_DISCOVERY_COMPLETED")

def MENU_PRESENT_RUN_STARTING: String = resourceBundle.getString("MENU_PRESENT_RUN_STARTING")

def rawMENU_PRESENT_RUN_STARTING: String = resourceBundle.getString("MENU_PRESENT_RUN_STARTING")

def MENU_PRESENT_TEST_STARTING: String = resourceBundle.getString("MENU_PRESENT_TEST_STARTING")

def rawMENU_PRESENT_TEST_STARTING: String = resourceBundle.getString("MENU_PRESENT_TEST_STARTING")

def MENU_PRESENT_TEST_FAILED: String = resourceBundle.getString("MENU_PRESENT_TEST_FAILED")

def rawMENU_PRESENT_TEST_FAILED: String = resourceBundle.getString("MENU_PRESENT_TEST_FAILED")

def MENU_PRESENT_TEST_SUCCEEDED: String = resourceBundle.getString("MENU_PRESENT_TEST_SUCCEEDED")

def rawMENU_PRESENT_TEST_SUCCEEDED: String = resourceBundle.getString("MENU_PRESENT_TEST_SUCCEEDED")

def MENU_PRESENT_TEST_IGNORED: String = resourceBundle.getString("MENU_PRESENT_TEST_IGNORED")

def rawMENU_PRESENT_TEST_IGNORED: String = resourceBundle.getString("MENU_PRESENT_TEST_IGNORED")

def MENU_PRESENT_TEST_PENDING: String = resourceBundle.getString("MENU_PRESENT_TEST_PENDING")

def rawMENU_PRESENT_TEST_PENDING: String = resourceBundle.getString("MENU_PRESENT_TEST_PENDING")

def MENU_PRESENT_TEST_CANCELED: String = resourceBundle.getString("MENU_PRESENT_TEST_CANCELED")

def rawMENU_PRESENT_TEST_CANCELED: String = resourceBundle.getString("MENU_PRESENT_TEST_CANCELED")

def MENU_PRESENT_SUITE_STARTING: String = resourceBundle.getString("MENU_PRESENT_SUITE_STARTING")

def rawMENU_PRESENT_SUITE_STARTING: String = resourceBundle.getString("MENU_PRESENT_SUITE_STARTING")

def MENU_PRESENT_SUITE_ABORTED: String = resourceBundle.getString("MENU_PRESENT_SUITE_ABORTED")

def rawMENU_PRESENT_SUITE_ABORTED: String = resourceBundle.getString("MENU_PRESENT_SUITE_ABORTED")

def MENU_PRESENT_SUITE_COMPLETED: String = resourceBundle.getString("MENU_PRESENT_SUITE_COMPLETED")

def rawMENU_PRESENT_SUITE_COMPLETED: String = resourceBundle.getString("MENU_PRESENT_SUITE_COMPLETED")

def MENU_PRESENT_INFO_PROVIDED: String = resourceBundle.getString("MENU_PRESENT_INFO_PROVIDED")

def rawMENU_PRESENT_INFO_PROVIDED: String = resourceBundle.getString("MENU_PRESENT_INFO_PROVIDED")

def MENU_PRESENT_ALERT_PROVIDED: String = resourceBundle.getString("MENU_PRESENT_ALERT_PROVIDED")

def rawMENU_PRESENT_ALERT_PROVIDED: String = resourceBundle.getString("MENU_PRESENT_ALERT_PROVIDED")

def MENU_PRESENT_NOTE_PROVIDED: String = resourceBundle.getString("MENU_PRESENT_NOTE_PROVIDED")

def rawMENU_PRESENT_NOTE_PROVIDED: String = resourceBundle.getString("MENU_PRESENT_NOTE_PROVIDED")

def MENU_PRESENT_SCOPE_OPENED: String = resourceBundle.getString("MENU_PRESENT_SCOPE_OPENED")

def rawMENU_PRESENT_SCOPE_OPENED: String = resourceBundle.getString("MENU_PRESENT_SCOPE_OPENED")

def MENU_PRESENT_SCOPE_CLOSED: String = resourceBundle.getString("MENU_PRESENT_SCOPE_CLOSED")

def rawMENU_PRESENT_SCOPE_CLOSED: String = resourceBundle.getString("MENU_PRESENT_SCOPE_CLOSED")

def MENU_PRESENT_SCOPE_PENDING: String = resourceBundle.getString("MENU_PRESENT_SCOPE_PENDING")

def rawMENU_PRESENT_SCOPE_PENDING: String = resourceBundle.getString("MENU_PRESENT_SCOPE_PENDING")

def MENU_PRESENT_MARKUP_PROVIDED: String = resourceBundle.getString("MENU_PRESENT_MARKUP_PROVIDED")

def rawMENU_PRESENT_MARKUP_PROVIDED: String = resourceBundle.getString("MENU_PRESENT_MARKUP_PROVIDED")

def MENU_PRESENT_RUN_STOPPED: String = resourceBundle.getString("MENU_PRESENT_RUN_STOPPED")

def rawMENU_PRESENT_RUN_STOPPED: String = resourceBundle.getString("MENU_PRESENT_RUN_STOPPED")

def MENU_PRESENT_RUN_ABORTED: String = resourceBundle.getString("MENU_PRESENT_RUN_ABORTED")

def rawMENU_PRESENT_RUN_ABORTED: String = resourceBundle.getString("MENU_PRESENT_RUN_ABORTED")

def MENU_PRESENT_RUN_COMPLETED: String = resourceBundle.getString("MENU_PRESENT_RUN_COMPLETED")

def rawMENU_PRESENT_RUN_COMPLETED: String = resourceBundle.getString("MENU_PRESENT_RUN_COMPLETED")

def RUN_STARTING: String = resourceBundle.getString("RUN_STARTING")

def rawRUN_STARTING: String = resourceBundle.getString("RUN_STARTING")

def TEST_STARTING: String = resourceBundle.getString("TEST_STARTING")

def rawTEST_STARTING: String = resourceBundle.getString("TEST_STARTING")

def TEST_FAILED: String = resourceBundle.getString("TEST_FAILED")

def rawTEST_FAILED: String = resourceBundle.getString("TEST_FAILED")

def TEST_SUCCEEDED: String = resourceBundle.getString("TEST_SUCCEEDED")

def rawTEST_SUCCEEDED: String = resourceBundle.getString("TEST_SUCCEEDED")

def TEST_IGNORED: String = resourceBundle.getString("TEST_IGNORED")

def rawTEST_IGNORED: String = resourceBundle.getString("TEST_IGNORED")

def TEST_PENDING: String = resourceBundle.getString("TEST_PENDING")

def rawTEST_PENDING: String = resourceBundle.getString("TEST_PENDING")

def TEST_CANCELED: String = resourceBundle.getString("TEST_CANCELED")

def rawTEST_CANCELED: String = resourceBundle.getString("TEST_CANCELED")

def SUITE_STARTING: String = resourceBundle.getString("SUITE_STARTING")

def rawSUITE_STARTING: String = resourceBundle.getString("SUITE_STARTING")

def SUITE_ABORTED: String = resourceBundle.getString("SUITE_ABORTED")

def rawSUITE_ABORTED: String = resourceBundle.getString("SUITE_ABORTED")

def SUITE_COMPLETED: String = resourceBundle.getString("SUITE_COMPLETED")

def rawSUITE_COMPLETED: String = resourceBundle.getString("SUITE_COMPLETED")

def INFO_PROVIDED: String = resourceBundle.getString("INFO_PROVIDED")

def rawINFO_PROVIDED: String = resourceBundle.getString("INFO_PROVIDED")

def ALERT_PROVIDED: String = resourceBundle.getString("ALERT_PROVIDED")

def rawALERT_PROVIDED: String = resourceBundle.getString("ALERT_PROVIDED")

def NOTE_PROVIDED: String = resourceBundle.getString("NOTE_PROVIDED")

def rawNOTE_PROVIDED: String = resourceBundle.getString("NOTE_PROVIDED")

def SCOPE_OPENED: String = resourceBundle.getString("SCOPE_OPENED")

def rawSCOPE_OPENED: String = resourceBundle.getString("SCOPE_OPENED")

def SCOPE_CLOSED: String = resourceBundle.getString("SCOPE_CLOSED")

def rawSCOPE_CLOSED: String = resourceBundle.getString("SCOPE_CLOSED")

def SCOPE_PENDING: String = resourceBundle.getString("SCOPE_PENDING")

def rawSCOPE_PENDING: String = resourceBundle.getString("SCOPE_PENDING")

def MARKUP_PROVIDED: String = resourceBundle.getString("MARKUP_PROVIDED")

def rawMARKUP_PROVIDED: String = resourceBundle.getString("MARKUP_PROVIDED")

def RUN_STOPPED: String = resourceBundle.getString("RUN_STOPPED")

def rawRUN_STOPPED: String = resourceBundle.getString("RUN_STOPPED")

def RUN_ABORTED: String = resourceBundle.getString("RUN_ABORTED")

def rawRUN_ABORTED: String = resourceBundle.getString("RUN_ABORTED")

def RUN_COMPLETED: String = resourceBundle.getString("RUN_COMPLETED")

def rawRUN_COMPLETED: String = resourceBundle.getString("RUN_COMPLETED")

def DISCOVERY_STARTING: String = resourceBundle.getString("DISCOVERY_STARTING")

def rawDISCOVERY_STARTING: String = resourceBundle.getString("DISCOVERY_STARTING")

def DISCOVERY_COMPLETED: String = resourceBundle.getString("DISCOVERY_COMPLETED")

def rawDISCOVERY_COMPLETED: String = resourceBundle.getString("DISCOVERY_COMPLETED")

def RERUN_DISCOVERY_STARTING: String = resourceBundle.getString("RERUN_DISCOVERY_STARTING")

def rawRERUN_DISCOVERY_STARTING: String = resourceBundle.getString("RERUN_DISCOVERY_STARTING")

def RERUN_DISCOVERY_COMPLETED: String = resourceBundle.getString("RERUN_DISCOVERY_COMPLETED")

def rawRERUN_DISCOVERY_COMPLETED: String = resourceBundle.getString("RERUN_DISCOVERY_COMPLETED")

def RERUN_RUN_STARTING: String = resourceBundle.getString("RERUN_RUN_STARTING")

def rawRERUN_RUN_STARTING: String = resourceBundle.getString("RERUN_RUN_STARTING")

def RERUN_TEST_STARTING: String = resourceBundle.getString("RERUN_TEST_STARTING")

def rawRERUN_TEST_STARTING: String = resourceBundle.getString("RERUN_TEST_STARTING")

def RERUN_TEST_FAILED: String = resourceBundle.getString("RERUN_TEST_FAILED")

def rawRERUN_TEST_FAILED: String = resourceBundle.getString("RERUN_TEST_FAILED")

def RERUN_TEST_SUCCEEDED: String = resourceBundle.getString("RERUN_TEST_SUCCEEDED")

def rawRERUN_TEST_SUCCEEDED: String = resourceBundle.getString("RERUN_TEST_SUCCEEDED")

def RERUN_TEST_IGNORED: String = resourceBundle.getString("RERUN_TEST_IGNORED")

def rawRERUN_TEST_IGNORED: String = resourceBundle.getString("RERUN_TEST_IGNORED")

def RERUN_TEST_PENDING: String = resourceBundle.getString("RERUN_TEST_PENDING")

def rawRERUN_TEST_PENDING: String = resourceBundle.getString("RERUN_TEST_PENDING")

def RERUN_TEST_CANCELED: String = resourceBundle.getString("RERUN_TEST_CANCELED")

def rawRERUN_TEST_CANCELED: String = resourceBundle.getString("RERUN_TEST_CANCELED")

def RERUN_SUITE_STARTING: String = resourceBundle.getString("RERUN_SUITE_STARTING")

def rawRERUN_SUITE_STARTING: String = resourceBundle.getString("RERUN_SUITE_STARTING")

def RERUN_SUITE_ABORTED: String = resourceBundle.getString("RERUN_SUITE_ABORTED")

def rawRERUN_SUITE_ABORTED: String = resourceBundle.getString("RERUN_SUITE_ABORTED")

def RERUN_SUITE_COMPLETED: String = resourceBundle.getString("RERUN_SUITE_COMPLETED")

def rawRERUN_SUITE_COMPLETED: String = resourceBundle.getString("RERUN_SUITE_COMPLETED")

def RERUN_INFO_PROVIDED: String = resourceBundle.getString("RERUN_INFO_PROVIDED")

def rawRERUN_INFO_PROVIDED: String = resourceBundle.getString("RERUN_INFO_PROVIDED")

def RERUN_ALERT_PROVIDED: String = resourceBundle.getString("RERUN_ALERT_PROVIDED")

def rawRERUN_ALERT_PROVIDED: String = resourceBundle.getString("RERUN_ALERT_PROVIDED")

def RERUN_NOTE_PROVIDED: String = resourceBundle.getString("RERUN_NOTE_PROVIDED")

def rawRERUN_NOTE_PROVIDED: String = resourceBundle.getString("RERUN_NOTE_PROVIDED")

def RERUN_MARKUP_PROVIDED: String = resourceBundle.getString("RERUN_MARKUP_PROVIDED")

def rawRERUN_MARKUP_PROVIDED: String = resourceBundle.getString("RERUN_MARKUP_PROVIDED")

def RERUN_RUN_STOPPED: String = resourceBundle.getString("RERUN_RUN_STOPPED")

def rawRERUN_RUN_STOPPED: String = resourceBundle.getString("RERUN_RUN_STOPPED")

def RERUN_RUN_ABORTED: String = resourceBundle.getString("RERUN_RUN_ABORTED")

def rawRERUN_RUN_ABORTED: String = resourceBundle.getString("RERUN_RUN_ABORTED")

def RERUN_RUN_COMPLETED: String = resourceBundle.getString("RERUN_RUN_COMPLETED")

def rawRERUN_RUN_COMPLETED: String = resourceBundle.getString("RERUN_RUN_COMPLETED")

def RERUN_SCOPE_OPENED: String = resourceBundle.getString("RERUN_SCOPE_OPENED")

def rawRERUN_SCOPE_OPENED: String = resourceBundle.getString("RERUN_SCOPE_OPENED")

def RERUN_SCOPE_CLOSED: String = resourceBundle.getString("RERUN_SCOPE_CLOSED")

def rawRERUN_SCOPE_CLOSED: String = resourceBundle.getString("RERUN_SCOPE_CLOSED")

def RERUN_SCOPE_PENDING: String = resourceBundle.getString("RERUN_SCOPE_PENDING")

def rawRERUN_SCOPE_PENDING: String = resourceBundle.getString("RERUN_SCOPE_PENDING")

def DetailsEvent: String = resourceBundle.getString("DetailsEvent")

def rawDetailsEvent: String = resourceBundle.getString("DetailsEvent")

def DetailsSuiteId: String = resourceBundle.getString("DetailsSuiteId")

def rawDetailsSuiteId: String = resourceBundle.getString("DetailsSuiteId")

def DetailsName: String = resourceBundle.getString("DetailsName")

def rawDetailsName: String = resourceBundle.getString("DetailsName")

def DetailsMessage: String = resourceBundle.getString("DetailsMessage")

def rawDetailsMessage: String = resourceBundle.getString("DetailsMessage")

def LineNumber: String = resourceBundle.getString("LineNumber")

def rawLineNumber: String = resourceBundle.getString("LineNumber")

def DetailsDate: String = resourceBundle.getString("DetailsDate")

def rawDetailsDate: String = resourceBundle.getString("DetailsDate")

def DetailsThread: String = resourceBundle.getString("DetailsThread")

def rawDetailsThread: String = resourceBundle.getString("DetailsThread")

def DetailsThrowable: String = resourceBundle.getString("DetailsThrowable")

def rawDetailsThrowable: String = resourceBundle.getString("DetailsThrowable")

def DetailsCause: String = resourceBundle.getString("DetailsCause")

def rawDetailsCause: String = resourceBundle.getString("DetailsCause")

def None: String = resourceBundle.getString("None")

def rawNone: String = resourceBundle.getString("None")

def DetailsDuration: String = resourceBundle.getString("DetailsDuration")

def rawDetailsDuration: String = resourceBundle.getString("DetailsDuration")

def DetailsSummary: String = resourceBundle.getString("DetailsSummary")

def rawDetailsSummary: String = resourceBundle.getString("DetailsSummary")

def should(param0: Any): String = makeString("should", Array(param0))

def rawShould: String = resourceBundle.getString("should")

def itShould(param0: Any): String = makeString("itShould", Array(param0))

def rawItShould: String = resourceBundle.getString("itShould")

def prefixSuffix(param0: Any, param1: Any): String = makeString("prefixSuffix", Array(param0, param1))

def rawPrefixSuffix: String = resourceBundle.getString("prefixSuffix")

def prefixShouldSuffix(param0: Any, param1: Any): String = makeString("prefixShouldSuffix", Array(param0, param1))

def rawPrefixShouldSuffix: String = resourceBundle.getString("prefixShouldSuffix")

def testSucceededIconChar: String = resourceBundle.getString("testSucceededIconChar")

def rawTestSucceededIconChar: String = resourceBundle.getString("testSucceededIconChar")

def testFailedIconChar: String = resourceBundle.getString("testFailedIconChar")

def rawTestFailedIconChar: String = resourceBundle.getString("testFailedIconChar")

def iconPlusShortName(param0: Any, param1: Any): String = makeString("iconPlusShortName", Array(param0, param1))

def rawIconPlusShortName: String = resourceBundle.getString("iconPlusShortName")

def iconPlusShortNameAndNote(param0: Any, param1: Any, param2: Any): String = makeString("iconPlusShortNameAndNote", Array(param0, param1, param2))

def rawIconPlusShortNameAndNote: String = resourceBundle.getString("iconPlusShortNameAndNote")

def infoProvidedIconChar: String = resourceBundle.getString("infoProvidedIconChar")

def rawInfoProvidedIconChar: String = resourceBundle.getString("infoProvidedIconChar")

def markupProvidedIconChar: String = resourceBundle.getString("markupProvidedIconChar")

def rawMarkupProvidedIconChar: String = resourceBundle.getString("markupProvidedIconChar")

def failedNote: String = resourceBundle.getString("failedNote")

def rawFailedNote: String = resourceBundle.getString("failedNote")

def abortedNote: String = resourceBundle.getString("abortedNote")

def rawAbortedNote: String = resourceBundle.getString("abortedNote")

def specTextAndNote(param0: Any, param1: Any): String = makeString("specTextAndNote", Array(param0, param1))

def rawSpecTextAndNote: String = resourceBundle.getString("specTextAndNote")

def ignoredNote: String = resourceBundle.getString("ignoredNote")

def rawIgnoredNote: String = resourceBundle.getString("ignoredNote")

def pendingNote: String = resourceBundle.getString("pendingNote")

def rawPendingNote: String = resourceBundle.getString("pendingNote")

def canceledNote: String = resourceBundle.getString("canceledNote")

def rawCanceledNote: String = resourceBundle.getString("canceledNote")

def infoProvidedNote: String = resourceBundle.getString("infoProvidedNote")

def rawInfoProvidedNote: String = resourceBundle.getString("infoProvidedNote")

def alertProvidedNote: String = resourceBundle.getString("alertProvidedNote")

def rawAlertProvidedNote: String = resourceBundle.getString("alertProvidedNote")

def noteProvidedNote: String = resourceBundle.getString("noteProvidedNote")

def rawNoteProvidedNote: String = resourceBundle.getString("noteProvidedNote")

def scopeOpenedNote: String = resourceBundle.getString("scopeOpenedNote")

def rawScopeOpenedNote: String = resourceBundle.getString("scopeOpenedNote")

def scopeClosedNote: String = resourceBundle.getString("scopeClosedNote")

def rawScopeClosedNote: String = resourceBundle.getString("scopeClosedNote")

def givenMessage(param0: Any): String = makeString("givenMessage", Array(param0))

def rawGivenMessage: String = resourceBundle.getString("givenMessage")

def whenMessage(param0: Any): String = makeString("whenMessage", Array(param0))

def rawWhenMessage: String = resourceBundle.getString("whenMessage")

def thenMessage(param0: Any): String = makeString("thenMessage", Array(param0))

def rawThenMessage: String = resourceBundle.getString("thenMessage")

def andMessage(param0: Any): String = makeString("andMessage", Array(param0))

def rawAndMessage: String = resourceBundle.getString("andMessage")

def scenario(param0: Any): String = makeString("scenario", Array(param0))

def rawScenario: String = resourceBundle.getString("scenario")

def commaBut(param0: Any, param1: Any): String = makeString("commaBut", Array(param0, param1))

def rawCommaBut: String = resourceBundle.getString("commaBut")

def commaAnd(param0: Any, param1: Any): String = makeString("commaAnd", Array(param0, param1))

def rawCommaAnd: String = resourceBundle.getString("commaAnd")

def commaDoubleAmpersand(param0: Any, param1: Any): String = makeString("commaDoubleAmpersand", Array(param0, param1))

def rawCommaDoubleAmpersand: String = resourceBundle.getString("commaDoubleAmpersand")

def commaDoublePipe(param0: Any, param1: Any): String = makeString("commaDoublePipe", Array(param0, param1))

def rawCommaDoublePipe: String = resourceBundle.getString("commaDoublePipe")

def unaryBang(param0: Any): String = makeString("unaryBang", Array(param0))

def rawUnaryBang: String = resourceBundle.getString("unaryBang")

def equaled(param0: Any, param1: Any): String = makeString("equaled", Array(param0, param1))

def rawEqualed: String = resourceBundle.getString("equaled")

def was(param0: Any, param1: Any): String = makeString("was", Array(param0, param1))

def rawWas: String = resourceBundle.getString("was")

def wasNot(param0: Any, param1: Any): String = makeString("wasNot", Array(param0, param1))

def rawWasNot: String = resourceBundle.getString("wasNot")

def wasA(param0: Any, param1: Any): String = makeString("wasA", Array(param0, param1))

def rawWasA: String = resourceBundle.getString("wasA")

def wasNotA(param0: Any, param1: Any): String = makeString("wasNotA", Array(param0, param1))

def rawWasNotA: String = resourceBundle.getString("wasNotA")

def wasAn(param0: Any, param1: Any): String = makeString("wasAn", Array(param0, param1))

def rawWasAn: String = resourceBundle.getString("wasAn")

def wasNotAn(param0: Any, param1: Any): String = makeString("wasNotAn", Array(param0, param1))

def rawWasNotAn: String = resourceBundle.getString("wasNotAn")

def wasDefinedAt(param0: Any, param1: Any): String = makeString("wasDefinedAt", Array(param0, param1))

def rawWasDefinedAt: String = resourceBundle.getString("wasDefinedAt")

def wasNotDefinedAt(param0: Any, param1: Any): String = makeString("wasNotDefinedAt", Array(param0, param1))

def rawWasNotDefinedAt: String = resourceBundle.getString("wasNotDefinedAt")

def equaledPlusOrMinus(param0: Any, param1: Any, param2: Any): String = makeString("equaledPlusOrMinus", Array(param0, param1, param2))

def rawEqualedPlusOrMinus: String = resourceBundle.getString("equaledPlusOrMinus")

def didNotEqualPlusOrMinus(param0: Any, param1: Any, param2: Any): String = makeString("didNotEqualPlusOrMinus", Array(param0, param1, param2))

def rawDidNotEqualPlusOrMinus: String = resourceBundle.getString("didNotEqualPlusOrMinus")

def wasPlusOrMinus(param0: Any, param1: Any, param2: Any): String = makeString("wasPlusOrMinus", Array(param0, param1, param2))

def rawWasPlusOrMinus: String = resourceBundle.getString("wasPlusOrMinus")

def wasNotPlusOrMinus(param0: Any, param1: Any, param2: Any): String = makeString("wasNotPlusOrMinus", Array(param0, param1, param2))

def rawWasNotPlusOrMinus: String = resourceBundle.getString("wasNotPlusOrMinus")

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

def wasSameInstanceAs(param0: Any, param1: Any): String = makeString("wasSameInstanceAs", Array(param0, param1))

def rawWasSameInstanceAs: String = resourceBundle.getString("wasSameInstanceAs")

def wasNotSameInstanceAs(param0: Any, param1: Any): String = makeString("wasNotSameInstanceAs", Array(param0, param1))

def rawWasNotSameInstanceAs: String = resourceBundle.getString("wasNotSameInstanceAs")

def booleanExpressionWas(param0: Any): String = makeString("booleanExpressionWas", Array(param0))

def rawBooleanExpressionWas: String = resourceBundle.getString("booleanExpressionWas")

def booleanExpressionWasNot(param0: Any): String = makeString("booleanExpressionWasNot", Array(param0))

def rawBooleanExpressionWasNot: String = resourceBundle.getString("booleanExpressionWasNot")

def wasAnInstanceOf(param0: Any, param1: Any): String = makeString("wasAnInstanceOf", Array(param0, param1))

def rawWasAnInstanceOf: String = resourceBundle.getString("wasAnInstanceOf")

def wasNotAnInstanceOf(param0: Any, param1: Any, param2: Any): String = makeString("wasNotAnInstanceOf", Array(param0, param1, param2))

def rawWasNotAnInstanceOf: String = resourceBundle.getString("wasNotAnInstanceOf")

def wasEmpty(param0: Any): String = makeString("wasEmpty", Array(param0))

def rawWasEmpty: String = resourceBundle.getString("wasEmpty")

def wasNotEmpty(param0: Any): String = makeString("wasNotEmpty", Array(param0))

def rawWasNotEmpty: String = resourceBundle.getString("wasNotEmpty")

def wasNull: String = resourceBundle.getString("wasNull")

def rawWasNull: String = resourceBundle.getString("wasNull")

def midSentenceWasNull: String = resourceBundle.getString("midSentenceWasNull")

def rawMidSentenceWasNull: String = resourceBundle.getString("midSentenceWasNull")

def wasNotNull(param0: Any): String = makeString("wasNotNull", Array(param0))

def rawWasNotNull: String = resourceBundle.getString("wasNotNull")

def equaledNull: String = resourceBundle.getString("equaledNull")

def rawEqualedNull: String = resourceBundle.getString("equaledNull")

def midSentenceEqualedNull: String = resourceBundle.getString("midSentenceEqualedNull")

def rawMidSentenceEqualedNull: String = resourceBundle.getString("midSentenceEqualedNull")

def didNotEqualNull(param0: Any): String = makeString("didNotEqualNull", Array(param0))

def rawDidNotEqualNull: String = resourceBundle.getString("didNotEqualNull")

def wasNone(param0: Any): String = makeString("wasNone", Array(param0))

def rawWasNone: String = resourceBundle.getString("wasNone")

def wasNotNone(param0: Any): String = makeString("wasNotNone", Array(param0))

def rawWasNotNone: String = resourceBundle.getString("wasNotNone")

def wasNil(param0: Any): String = makeString("wasNil", Array(param0))

def rawWasNil: String = resourceBundle.getString("wasNil")

def wasNotNil(param0: Any): String = makeString("wasNotNil", Array(param0))

def rawWasNotNil: String = resourceBundle.getString("wasNotNil")

def wasSome(param0: Any, param1: Any): String = makeString("wasSome", Array(param0, param1))

def rawWasSome: String = resourceBundle.getString("wasSome")

def wasNotSome(param0: Any, param1: Any): String = makeString("wasNotSome", Array(param0, param1))

def rawWasNotSome: String = resourceBundle.getString("wasNotSome")

def hasNeitherAOrAnMethod(param0: Any, param1: Any, param2: Any): String = makeString("hasNeitherAOrAnMethod", Array(param0, param1, param2))

def rawHasNeitherAOrAnMethod: String = resourceBundle.getString("hasNeitherAOrAnMethod")

def hasNeitherAnOrAnMethod(param0: Any, param1: Any, param2: Any): String = makeString("hasNeitherAnOrAnMethod", Array(param0, param1, param2))

def rawHasNeitherAnOrAnMethod: String = resourceBundle.getString("hasNeitherAnOrAnMethod")

def hasBothAAndAnMethod(param0: Any, param1: Any, param2: Any): String = makeString("hasBothAAndAnMethod", Array(param0, param1, param2))

def rawHasBothAAndAnMethod: String = resourceBundle.getString("hasBothAAndAnMethod")

def hasBothAnAndAnMethod(param0: Any, param1: Any, param2: Any): String = makeString("hasBothAnAndAnMethod", Array(param0, param1, param2))

def rawHasBothAnAndAnMethod: String = resourceBundle.getString("hasBothAnAndAnMethod")

def didNotEndWith(param0: Any, param1: Any): String = makeString("didNotEndWith", Array(param0, param1))

def rawDidNotEndWith: String = resourceBundle.getString("didNotEndWith")

def endedWith(param0: Any, param1: Any): String = makeString("endedWith", Array(param0, param1))

def rawEndedWith: String = resourceBundle.getString("endedWith")

def didNotStartWith(param0: Any, param1: Any): String = makeString("didNotStartWith", Array(param0, param1))

def rawDidNotStartWith: String = resourceBundle.getString("didNotStartWith")

def startedWith(param0: Any, param1: Any): String = makeString("startedWith", Array(param0, param1))

def rawStartedWith: String = resourceBundle.getString("startedWith")

def didNotStartWithRegex(param0: Any, param1: Any): String = makeString("didNotStartWithRegex", Array(param0, param1))

def rawDidNotStartWithRegex: String = resourceBundle.getString("didNotStartWithRegex")

def startedWithRegex(param0: Any, param1: Any): String = makeString("startedWithRegex", Array(param0, param1))

def rawStartedWithRegex: String = resourceBundle.getString("startedWithRegex")

def startedWithRegexButNotGroup(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("startedWithRegexButNotGroup", Array(param0, param1, param2, param3))

def rawStartedWithRegexButNotGroup: String = resourceBundle.getString("startedWithRegexButNotGroup")

def startedWithRegexButNotGroupAtIndex(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = makeString("startedWithRegexButNotGroupAtIndex", Array(param0, param1, param2, param3, param4))

def rawStartedWithRegexButNotGroupAtIndex: String = resourceBundle.getString("startedWithRegexButNotGroupAtIndex")

def startedWithRegexAndGroup(param0: Any, param1: Any, param2: Any): String = makeString("startedWithRegexAndGroup", Array(param0, param1, param2))

def rawStartedWithRegexAndGroup: String = resourceBundle.getString("startedWithRegexAndGroup")

def didNotEndWithRegex(param0: Any, param1: Any): String = makeString("didNotEndWithRegex", Array(param0, param1))

def rawDidNotEndWithRegex: String = resourceBundle.getString("didNotEndWithRegex")

def endedWithRegex(param0: Any, param1: Any): String = makeString("endedWithRegex", Array(param0, param1))

def rawEndedWithRegex: String = resourceBundle.getString("endedWithRegex")

def endedWithRegexButNotGroup(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("endedWithRegexButNotGroup", Array(param0, param1, param2, param3))

def rawEndedWithRegexButNotGroup: String = resourceBundle.getString("endedWithRegexButNotGroup")

def endedWithRegexButNotGroupAtIndex(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = makeString("endedWithRegexButNotGroupAtIndex", Array(param0, param1, param2, param3, param4))

def rawEndedWithRegexButNotGroupAtIndex: String = resourceBundle.getString("endedWithRegexButNotGroupAtIndex")

def endedWithRegexAndGroup(param0: Any, param1: Any, param2: Any): String = makeString("endedWithRegexAndGroup", Array(param0, param1, param2))

def rawEndedWithRegexAndGroup: String = resourceBundle.getString("endedWithRegexAndGroup")

def didNotContainNull(param0: Any): String = makeString("didNotContainNull", Array(param0))

def rawDidNotContainNull: String = resourceBundle.getString("didNotContainNull")

def containedNull(param0: Any): String = makeString("containedNull", Array(param0))

def rawContainedNull: String = resourceBundle.getString("containedNull")

def didNotContainKey(param0: Any, param1: Any): String = makeString("didNotContainKey", Array(param0, param1))

def rawDidNotContainKey: String = resourceBundle.getString("didNotContainKey")

def containedKey(param0: Any, param1: Any): String = makeString("containedKey", Array(param0, param1))

def rawContainedKey: String = resourceBundle.getString("containedKey")

def didNotContainValue(param0: Any, param1: Any): String = makeString("didNotContainValue", Array(param0, param1))

def rawDidNotContainValue: String = resourceBundle.getString("didNotContainValue")

def containedValue(param0: Any, param1: Any): String = makeString("containedValue", Array(param0, param1))

def rawContainedValue: String = resourceBundle.getString("containedValue")

def hadSizeInsteadOfExpectedSize(param0: Any, param1: Any, param2: Any): String = makeString("hadSizeInsteadOfExpectedSize", Array(param0, param1, param2))

def rawHadSizeInsteadOfExpectedSize: String = resourceBundle.getString("hadSizeInsteadOfExpectedSize")

def hadSize(param0: Any, param1: Any): String = makeString("hadSize", Array(param0, param1))

def rawHadSize: String = resourceBundle.getString("hadSize")

def hadMessageInsteadOfExpectedMessage(param0: Any, param1: Any, param2: Any): String = makeString("hadMessageInsteadOfExpectedMessage", Array(param0, param1, param2))

def rawHadMessageInsteadOfExpectedMessage: String = resourceBundle.getString("hadMessageInsteadOfExpectedMessage")

def hadExpectedMessage(param0: Any, param1: Any): String = makeString("hadExpectedMessage", Array(param0, param1))

def rawHadExpectedMessage: String = resourceBundle.getString("hadExpectedMessage")

def didNotContainExpectedElement(param0: Any, param1: Any): String = makeString("didNotContainExpectedElement", Array(param0, param1))

def rawDidNotContainExpectedElement: String = resourceBundle.getString("didNotContainExpectedElement")

def containedExpectedElement(param0: Any, param1: Any): String = makeString("containedExpectedElement", Array(param0, param1))

def rawContainedExpectedElement: String = resourceBundle.getString("containedExpectedElement")

def didNotIncludeSubstring(param0: Any, param1: Any): String = makeString("didNotIncludeSubstring", Array(param0, param1))

def rawDidNotIncludeSubstring: String = resourceBundle.getString("didNotIncludeSubstring")

def includedSubstring(param0: Any, param1: Any): String = makeString("includedSubstring", Array(param0, param1))

def rawIncludedSubstring: String = resourceBundle.getString("includedSubstring")

def didNotIncludeRegex(param0: Any, param1: Any): String = makeString("didNotIncludeRegex", Array(param0, param1))

def rawDidNotIncludeRegex: String = resourceBundle.getString("didNotIncludeRegex")

def includedRegex(param0: Any, param1: Any): String = makeString("includedRegex", Array(param0, param1))

def rawIncludedRegex: String = resourceBundle.getString("includedRegex")

def includedRegexButNotGroup(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("includedRegexButNotGroup", Array(param0, param1, param2, param3))

def rawIncludedRegexButNotGroup: String = resourceBundle.getString("includedRegexButNotGroup")

def includedRegexButNotGroupAtIndex(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = makeString("includedRegexButNotGroupAtIndex", Array(param0, param1, param2, param3, param4))

def rawIncludedRegexButNotGroupAtIndex: String = resourceBundle.getString("includedRegexButNotGroupAtIndex")

def includedRegexAndGroup(param0: Any, param1: Any, param2: Any): String = makeString("includedRegexAndGroup", Array(param0, param1, param2))

def rawIncludedRegexAndGroup: String = resourceBundle.getString("includedRegexAndGroup")

def hadLengthInsteadOfExpectedLength(param0: Any, param1: Any, param2: Any): String = makeString("hadLengthInsteadOfExpectedLength", Array(param0, param1, param2))

def rawHadLengthInsteadOfExpectedLength: String = resourceBundle.getString("hadLengthInsteadOfExpectedLength")

def hadLength(param0: Any, param1: Any): String = makeString("hadLength", Array(param0, param1))

def rawHadLength: String = resourceBundle.getString("hadLength")

def didNotFullyMatchRegex(param0: Any, param1: Any): String = makeString("didNotFullyMatchRegex", Array(param0, param1))

def rawDidNotFullyMatchRegex: String = resourceBundle.getString("didNotFullyMatchRegex")

def fullyMatchedRegex(param0: Any, param1: Any): String = makeString("fullyMatchedRegex", Array(param0, param1))

def rawFullyMatchedRegex: String = resourceBundle.getString("fullyMatchedRegex")

def fullyMatchedRegexButNotGroup(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("fullyMatchedRegexButNotGroup", Array(param0, param1, param2, param3))

def rawFullyMatchedRegexButNotGroup: String = resourceBundle.getString("fullyMatchedRegexButNotGroup")

def fullyMatchedRegexButNotGroupAtIndex(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = makeString("fullyMatchedRegexButNotGroupAtIndex", Array(param0, param1, param2, param3, param4))

def rawFullyMatchedRegexButNotGroupAtIndex: String = resourceBundle.getString("fullyMatchedRegexButNotGroupAtIndex")

def fullyMatchedRegexAndGroup(param0: Any, param1: Any, param2: Any): String = makeString("fullyMatchedRegexAndGroup", Array(param0, param1, param2))

def rawFullyMatchedRegexAndGroup: String = resourceBundle.getString("fullyMatchedRegexAndGroup")

def matchResultedInFalse(param0: Any): String = makeString("matchResultedInFalse", Array(param0))

def rawMatchResultedInFalse: String = resourceBundle.getString("matchResultedInFalse")

def didNotMatch(param0: Any): String = makeString("didNotMatch", Array(param0))

def rawDidNotMatch: String = resourceBundle.getString("didNotMatch")

def matchResultedInTrue(param0: Any): String = makeString("matchResultedInTrue", Array(param0))

def rawMatchResultedInTrue: String = resourceBundle.getString("matchResultedInTrue")

def noLengthStructure(param0: Any): String = makeString("noLengthStructure", Array(param0))

def rawNoLengthStructure: String = resourceBundle.getString("noLengthStructure")

def noSizeStructure(param0: Any): String = makeString("noSizeStructure", Array(param0))

def rawNoSizeStructure: String = resourceBundle.getString("noSizeStructure")

def sizeAndGetSize(param0: Any): String = makeString("sizeAndGetSize", Array(param0))

def rawSizeAndGetSize: String = resourceBundle.getString("sizeAndGetSize")

def negativeOrZeroRange(param0: Any): String = makeString("negativeOrZeroRange", Array(param0))

def rawNegativeOrZeroRange: String = resourceBundle.getString("negativeOrZeroRange")

def didNotContainSameElements(param0: Any, param1: Any): String = makeString("didNotContainSameElements", Array(param0, param1))

def rawDidNotContainSameElements: String = resourceBundle.getString("didNotContainSameElements")

def containedSameElements(param0: Any, param1: Any): String = makeString("containedSameElements", Array(param0, param1))

def rawContainedSameElements: String = resourceBundle.getString("containedSameElements")

def didNotContainSameElementsInOrder(param0: Any, param1: Any): String = makeString("didNotContainSameElementsInOrder", Array(param0, param1))

def rawDidNotContainSameElementsInOrder: String = resourceBundle.getString("didNotContainSameElementsInOrder")

def containedSameElementsInOrder(param0: Any, param1: Any): String = makeString("containedSameElementsInOrder", Array(param0, param1))

def rawContainedSameElementsInOrder: String = resourceBundle.getString("containedSameElementsInOrder")

def didNotContainAllOfElements(param0: Any, param1: Any): String = makeString("didNotContainAllOfElements", Array(param0, param1))

def rawDidNotContainAllOfElements: String = resourceBundle.getString("didNotContainAllOfElements")

def containedAllOfElements(param0: Any, param1: Any): String = makeString("containedAllOfElements", Array(param0, param1))

def rawContainedAllOfElements: String = resourceBundle.getString("containedAllOfElements")

def allOfDuplicate: String = resourceBundle.getString("allOfDuplicate")

def rawAllOfDuplicate: String = resourceBundle.getString("allOfDuplicate")

def didNotContainAllElementsOf(param0: Any, param1: Any): String = makeString("didNotContainAllElementsOf", Array(param0, param1))

def rawDidNotContainAllElementsOf: String = resourceBundle.getString("didNotContainAllElementsOf")

def containedAllElementsOf(param0: Any, param1: Any): String = makeString("containedAllElementsOf", Array(param0, param1))

def rawContainedAllElementsOf: String = resourceBundle.getString("containedAllElementsOf")

def didNotContainAllOfElementsInOrder(param0: Any, param1: Any): String = makeString("didNotContainAllOfElementsInOrder", Array(param0, param1))

def rawDidNotContainAllOfElementsInOrder: String = resourceBundle.getString("didNotContainAllOfElementsInOrder")

def containedAllOfElementsInOrder(param0: Any, param1: Any): String = makeString("containedAllOfElementsInOrder", Array(param0, param1))

def rawContainedAllOfElementsInOrder: String = resourceBundle.getString("containedAllOfElementsInOrder")

def didNotContainAllElementsOfInOrder(param0: Any, param1: Any): String = makeString("didNotContainAllElementsOfInOrder", Array(param0, param1))

def rawDidNotContainAllElementsOfInOrder: String = resourceBundle.getString("didNotContainAllElementsOfInOrder")

def containedAllElementsOfInOrder(param0: Any, param1: Any): String = makeString("containedAllElementsOfInOrder", Array(param0, param1))

def rawContainedAllElementsOfInOrder: String = resourceBundle.getString("containedAllElementsOfInOrder")

def inOrderDuplicate: String = resourceBundle.getString("inOrderDuplicate")

def rawInOrderDuplicate: String = resourceBundle.getString("inOrderDuplicate")

def didNotContainOneOfElements(param0: Any, param1: Any): String = makeString("didNotContainOneOfElements", Array(param0, param1))

def rawDidNotContainOneOfElements: String = resourceBundle.getString("didNotContainOneOfElements")

def containedOneOfElements(param0: Any, param1: Any): String = makeString("containedOneOfElements", Array(param0, param1))

def rawContainedOneOfElements: String = resourceBundle.getString("containedOneOfElements")

def didNotContainOneElementOf(param0: Any, param1: Any): String = makeString("didNotContainOneElementOf", Array(param0, param1))

def rawDidNotContainOneElementOf: String = resourceBundle.getString("didNotContainOneElementOf")

def containedOneElementOf(param0: Any, param1: Any): String = makeString("containedOneElementOf", Array(param0, param1))

def rawContainedOneElementOf: String = resourceBundle.getString("containedOneElementOf")

def didNotContainAtLeastOneOf(param0: Any, param1: Any): String = makeString("didNotContainAtLeastOneOf", Array(param0, param1))

def rawDidNotContainAtLeastOneOf: String = resourceBundle.getString("didNotContainAtLeastOneOf")

def containedAtLeastOneOf(param0: Any, param1: Any): String = makeString("containedAtLeastOneOf", Array(param0, param1))

def rawContainedAtLeastOneOf: String = resourceBundle.getString("containedAtLeastOneOf")

def atLeastOneOfDuplicate: String = resourceBundle.getString("atLeastOneOfDuplicate")

def rawAtLeastOneOfDuplicate: String = resourceBundle.getString("atLeastOneOfDuplicate")

def didNotContainAtLeastOneElementOf(param0: Any, param1: Any): String = makeString("didNotContainAtLeastOneElementOf", Array(param0, param1))

def rawDidNotContainAtLeastOneElementOf: String = resourceBundle.getString("didNotContainAtLeastOneElementOf")

def containedAtLeastOneElementOf(param0: Any, param1: Any): String = makeString("containedAtLeastOneElementOf", Array(param0, param1))

def rawContainedAtLeastOneElementOf: String = resourceBundle.getString("containedAtLeastOneElementOf")

def oneOfDuplicate: String = resourceBundle.getString("oneOfDuplicate")

def rawOneOfDuplicate: String = resourceBundle.getString("oneOfDuplicate")

def didNotContainOnlyElements(param0: Any, param1: Any): String = makeString("didNotContainOnlyElements", Array(param0, param1))

def rawDidNotContainOnlyElements: String = resourceBundle.getString("didNotContainOnlyElements")

def containedOnlyElements(param0: Any, param1: Any): String = makeString("containedOnlyElements", Array(param0, param1))

def rawContainedOnlyElements: String = resourceBundle.getString("containedOnlyElements")

def didNotContainOnlyElementsWithFriendlyReminder(param0: Any, param1: Any): String = makeString("didNotContainOnlyElementsWithFriendlyReminder", Array(param0, param1))

def rawDidNotContainOnlyElementsWithFriendlyReminder: String = resourceBundle.getString("didNotContainOnlyElementsWithFriendlyReminder")

def containedOnlyElementsWithFriendlyReminder(param0: Any, param1: Any): String = makeString("containedOnlyElementsWithFriendlyReminder", Array(param0, param1))

def rawContainedOnlyElementsWithFriendlyReminder: String = resourceBundle.getString("containedOnlyElementsWithFriendlyReminder")

def onlyDuplicate: String = resourceBundle.getString("onlyDuplicate")

def rawOnlyDuplicate: String = resourceBundle.getString("onlyDuplicate")

def onlyEmpty: String = resourceBundle.getString("onlyEmpty")

def rawOnlyEmpty: String = resourceBundle.getString("onlyEmpty")

def didNotContainInOrderOnlyElements(param0: Any, param1: Any): String = makeString("didNotContainInOrderOnlyElements", Array(param0, param1))

def rawDidNotContainInOrderOnlyElements: String = resourceBundle.getString("didNotContainInOrderOnlyElements")

def containedInOrderOnlyElements(param0: Any, param1: Any): String = makeString("containedInOrderOnlyElements", Array(param0, param1))

def rawContainedInOrderOnlyElements: String = resourceBundle.getString("containedInOrderOnlyElements")

def inOrderOnlyDuplicate: String = resourceBundle.getString("inOrderOnlyDuplicate")

def rawInOrderOnlyDuplicate: String = resourceBundle.getString("inOrderOnlyDuplicate")

def atMostOneOfDuplicate: String = resourceBundle.getString("atMostOneOfDuplicate")

def rawAtMostOneOfDuplicate: String = resourceBundle.getString("atMostOneOfDuplicate")

def didNotContainAtMostOneOf(param0: Any, param1: Any): String = makeString("didNotContainAtMostOneOf", Array(param0, param1))

def rawDidNotContainAtMostOneOf: String = resourceBundle.getString("didNotContainAtMostOneOf")

def containedAtMostOneOf(param0: Any, param1: Any): String = makeString("containedAtMostOneOf", Array(param0, param1))

def rawContainedAtMostOneOf: String = resourceBundle.getString("containedAtMostOneOf")

def atMostOneElementOfDuplicate: String = resourceBundle.getString("atMostOneElementOfDuplicate")

def rawAtMostOneElementOfDuplicate: String = resourceBundle.getString("atMostOneElementOfDuplicate")

def didNotContainAtMostOneElementOf(param0: Any, param1: Any): String = makeString("didNotContainAtMostOneElementOf", Array(param0, param1))

def rawDidNotContainAtMostOneElementOf: String = resourceBundle.getString("didNotContainAtMostOneElementOf")

def containedAtMostOneElementOf(param0: Any, param1: Any): String = makeString("containedAtMostOneElementOf", Array(param0, param1))

def rawContainedAtMostOneElementOf: String = resourceBundle.getString("containedAtMostOneElementOf")

def noneOfDuplicate: String = resourceBundle.getString("noneOfDuplicate")

def rawNoneOfDuplicate: String = resourceBundle.getString("noneOfDuplicate")

def didNotContainA(param0: Any, param1: Any): String = makeString("didNotContainA", Array(param0, param1))

def rawDidNotContainA: String = resourceBundle.getString("didNotContainA")

def containedA(param0: Any, param1: Any, param2: Any): String = makeString("containedA", Array(param0, param1, param2))

def rawContainedA: String = resourceBundle.getString("containedA")

def didNotContainAn(param0: Any, param1: Any): String = makeString("didNotContainAn", Array(param0, param1))

def rawDidNotContainAn: String = resourceBundle.getString("didNotContainAn")

def containedAn(param0: Any, param1: Any, param2: Any): String = makeString("containedAn", Array(param0, param1, param2))

def rawContainedAn: String = resourceBundle.getString("containedAn")

def wasNotSorted(param0: Any): String = makeString("wasNotSorted", Array(param0))

def rawWasNotSorted: String = resourceBundle.getString("wasNotSorted")

def wasSorted(param0: Any): String = makeString("wasSorted", Array(param0))

def rawWasSorted: String = resourceBundle.getString("wasSorted")

def wasNotDefined(param0: Any): String = makeString("wasNotDefined", Array(param0))

def rawWasNotDefined: String = resourceBundle.getString("wasNotDefined")

def wasDefined(param0: Any): String = makeString("wasDefined", Array(param0))

def rawWasDefined: String = resourceBundle.getString("wasDefined")

def doesNotExist(param0: Any): String = makeString("doesNotExist", Array(param0))

def rawDoesNotExist: String = resourceBundle.getString("doesNotExist")

def exists(param0: Any): String = makeString("exists", Array(param0))

def rawExists: String = resourceBundle.getString("exists")

def wasNotReadable(param0: Any): String = makeString("wasNotReadable", Array(param0))

def rawWasNotReadable: String = resourceBundle.getString("wasNotReadable")

def wasReadable(param0: Any): String = makeString("wasReadable", Array(param0))

def rawWasReadable: String = resourceBundle.getString("wasReadable")

def wasNotWritable(param0: Any): String = makeString("wasNotWritable", Array(param0))

def rawWasNotWritable: String = resourceBundle.getString("wasNotWritable")

def wasWritable(param0: Any): String = makeString("wasWritable", Array(param0))

def rawWasWritable: String = resourceBundle.getString("wasWritable")

def didNotMatchTheGivenPattern(param0: Any): String = makeString("didNotMatchTheGivenPattern", Array(param0))

def rawDidNotMatchTheGivenPattern: String = resourceBundle.getString("didNotMatchTheGivenPattern")

def matchedTheGivenPattern(param0: Any): String = makeString("matchedTheGivenPattern", Array(param0))

def rawMatchedTheGivenPattern: String = resourceBundle.getString("matchedTheGivenPattern")

def duplicateTestName(param0: Any): String = makeString("duplicateTestName", Array(param0))

def rawDuplicateTestName: String = resourceBundle.getString("duplicateTestName")

def cantNestFeatureClauses: String = resourceBundle.getString("cantNestFeatureClauses")

def rawCantNestFeatureClauses: String = resourceBundle.getString("cantNestFeatureClauses")

def itCannotAppearInsideAnotherIt: String = resourceBundle.getString("itCannotAppearInsideAnotherIt")

def rawItCannotAppearInsideAnotherIt: String = resourceBundle.getString("itCannotAppearInsideAnotherIt")

def itCannotAppearInsideAnotherItOrThey: String = resourceBundle.getString("itCannotAppearInsideAnotherItOrThey")

def rawItCannotAppearInsideAnotherItOrThey: String = resourceBundle.getString("itCannotAppearInsideAnotherItOrThey")

def theyCannotAppearInsideAnotherItOrThey: String = resourceBundle.getString("theyCannotAppearInsideAnotherItOrThey")

def rawTheyCannotAppearInsideAnotherItOrThey: String = resourceBundle.getString("theyCannotAppearInsideAnotherItOrThey")

def describeCannotAppearInsideAnIt: String = resourceBundle.getString("describeCannotAppearInsideAnIt")

def rawDescribeCannotAppearInsideAnIt: String = resourceBundle.getString("describeCannotAppearInsideAnIt")

def ignoreCannotAppearInsideAnIt: String = resourceBundle.getString("ignoreCannotAppearInsideAnIt")

def rawIgnoreCannotAppearInsideAnIt: String = resourceBundle.getString("ignoreCannotAppearInsideAnIt")

def ignoreCannotAppearInsideAnItOrAThey: String = resourceBundle.getString("ignoreCannotAppearInsideAnItOrAThey")

def rawIgnoreCannotAppearInsideAnItOrAThey: String = resourceBundle.getString("ignoreCannotAppearInsideAnItOrAThey")

def scenarioCannotAppearInsideAnotherScenario: String = resourceBundle.getString("scenarioCannotAppearInsideAnotherScenario")

def rawScenarioCannotAppearInsideAnotherScenario: String = resourceBundle.getString("scenarioCannotAppearInsideAnotherScenario")

def featureCannotAppearInsideAScenario: String = resourceBundle.getString("featureCannotAppearInsideAScenario")

def rawFeatureCannotAppearInsideAScenario: String = resourceBundle.getString("featureCannotAppearInsideAScenario")

def ignoreCannotAppearInsideAScenario: String = resourceBundle.getString("ignoreCannotAppearInsideAScenario")

def rawIgnoreCannotAppearInsideAScenario: String = resourceBundle.getString("ignoreCannotAppearInsideAScenario")

def testCannotAppearInsideAnotherTest: String = resourceBundle.getString("testCannotAppearInsideAnotherTest")

def rawTestCannotAppearInsideAnotherTest: String = resourceBundle.getString("testCannotAppearInsideAnotherTest")

def propertyCannotAppearInsideAnotherProperty: String = resourceBundle.getString("propertyCannotAppearInsideAnotherProperty")

def rawPropertyCannotAppearInsideAnotherProperty: String = resourceBundle.getString("propertyCannotAppearInsideAnotherProperty")

def ignoreCannotAppearInsideATest: String = resourceBundle.getString("ignoreCannotAppearInsideATest")

def rawIgnoreCannotAppearInsideATest: String = resourceBundle.getString("ignoreCannotAppearInsideATest")

def ignoreCannotAppearInsideAProperty: String = resourceBundle.getString("ignoreCannotAppearInsideAProperty")

def rawIgnoreCannotAppearInsideAProperty: String = resourceBundle.getString("ignoreCannotAppearInsideAProperty")

def shouldCannotAppearInsideAnIn: String = resourceBundle.getString("shouldCannotAppearInsideAnIn")

def rawShouldCannotAppearInsideAnIn: String = resourceBundle.getString("shouldCannotAppearInsideAnIn")

def mustCannotAppearInsideAnIn: String = resourceBundle.getString("mustCannotAppearInsideAnIn")

def rawMustCannotAppearInsideAnIn: String = resourceBundle.getString("mustCannotAppearInsideAnIn")

def whenCannotAppearInsideAnIn: String = resourceBundle.getString("whenCannotAppearInsideAnIn")

def rawWhenCannotAppearInsideAnIn: String = resourceBundle.getString("whenCannotAppearInsideAnIn")

def thatCannotAppearInsideAnIn: String = resourceBundle.getString("thatCannotAppearInsideAnIn")

def rawThatCannotAppearInsideAnIn: String = resourceBundle.getString("thatCannotAppearInsideAnIn")

def whichCannotAppearInsideAnIn: String = resourceBundle.getString("whichCannotAppearInsideAnIn")

def rawWhichCannotAppearInsideAnIn: String = resourceBundle.getString("whichCannotAppearInsideAnIn")

def canCannotAppearInsideAnIn: String = resourceBundle.getString("canCannotAppearInsideAnIn")

def rawCanCannotAppearInsideAnIn: String = resourceBundle.getString("canCannotAppearInsideAnIn")

def behaviorOfCannotAppearInsideAnIn: String = resourceBundle.getString("behaviorOfCannotAppearInsideAnIn")

def rawBehaviorOfCannotAppearInsideAnIn: String = resourceBundle.getString("behaviorOfCannotAppearInsideAnIn")

def dashCannotAppearInsideAnIn: String = resourceBundle.getString("dashCannotAppearInsideAnIn")

def rawDashCannotAppearInsideAnIn: String = resourceBundle.getString("dashCannotAppearInsideAnIn")

def inCannotAppearInsideAnotherIn: String = resourceBundle.getString("inCannotAppearInsideAnotherIn")

def rawInCannotAppearInsideAnotherIn: String = resourceBundle.getString("inCannotAppearInsideAnotherIn")

def inCannotAppearInsideAnotherInOrIs: String = resourceBundle.getString("inCannotAppearInsideAnotherInOrIs")

def rawInCannotAppearInsideAnotherInOrIs: String = resourceBundle.getString("inCannotAppearInsideAnotherInOrIs")

def isCannotAppearInsideAnotherInOrIs: String = resourceBundle.getString("isCannotAppearInsideAnotherInOrIs")

def rawIsCannotAppearInsideAnotherInOrIs: String = resourceBundle.getString("isCannotAppearInsideAnotherInOrIs")

def ignoreCannotAppearInsideAnIn: String = resourceBundle.getString("ignoreCannotAppearInsideAnIn")

def rawIgnoreCannotAppearInsideAnIn: String = resourceBundle.getString("ignoreCannotAppearInsideAnIn")

def ignoreCannotAppearInsideAnInOrAnIs: String = resourceBundle.getString("ignoreCannotAppearInsideAnInOrAnIs")

def rawIgnoreCannotAppearInsideAnInOrAnIs: String = resourceBundle.getString("ignoreCannotAppearInsideAnInOrAnIs")

def registrationAlreadyClosed: String = resourceBundle.getString("registrationAlreadyClosed")

def rawRegistrationAlreadyClosed: String = resourceBundle.getString("registrationAlreadyClosed")

def itMustAppearAfterTopLevelSubject: String = resourceBundle.getString("itMustAppearAfterTopLevelSubject")

def rawItMustAppearAfterTopLevelSubject: String = resourceBundle.getString("itMustAppearAfterTopLevelSubject")

def theyMustAppearAfterTopLevelSubject: String = resourceBundle.getString("theyMustAppearAfterTopLevelSubject")

def rawTheyMustAppearAfterTopLevelSubject: String = resourceBundle.getString("theyMustAppearAfterTopLevelSubject")

def allPropertiesHadExpectedValues(param0: Any): String = makeString("allPropertiesHadExpectedValues", Array(param0))

def rawAllPropertiesHadExpectedValues: String = resourceBundle.getString("allPropertiesHadExpectedValues")

def midSentenceAllPropertiesHadExpectedValues(param0: Any): String = makeString("midSentenceAllPropertiesHadExpectedValues", Array(param0))

def rawMidSentenceAllPropertiesHadExpectedValues: String = resourceBundle.getString("midSentenceAllPropertiesHadExpectedValues")

def propertyHadExpectedValue(param0: Any, param1: Any, param2: Any): String = makeString("propertyHadExpectedValue", Array(param0, param1, param2))

def rawPropertyHadExpectedValue: String = resourceBundle.getString("propertyHadExpectedValue")

def midSentencePropertyHadExpectedValue(param0: Any, param1: Any, param2: Any): String = makeString("midSentencePropertyHadExpectedValue", Array(param0, param1, param2))

def rawMidSentencePropertyHadExpectedValue: String = resourceBundle.getString("midSentencePropertyHadExpectedValue")

def propertyDidNotHaveExpectedValue(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("propertyDidNotHaveExpectedValue", Array(param0, param1, param2, param3))

def rawPropertyDidNotHaveExpectedValue: String = resourceBundle.getString("propertyDidNotHaveExpectedValue")

def midSentencePropertyDidNotHaveExpectedValue(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("midSentencePropertyDidNotHaveExpectedValue", Array(param0, param1, param2, param3))

def rawMidSentencePropertyDidNotHaveExpectedValue: String = resourceBundle.getString("midSentencePropertyDidNotHaveExpectedValue")

def propertyNotFound(param0: Any, param1: Any, param2: Any): String = makeString("propertyNotFound", Array(param0, param1, param2))

def rawPropertyNotFound: String = resourceBundle.getString("propertyNotFound")

def propertyCheckSucceeded: String = resourceBundle.getString("propertyCheckSucceeded")

def rawPropertyCheckSucceeded: String = resourceBundle.getString("propertyCheckSucceeded")

def lengthPropertyNotAnInteger: String = resourceBundle.getString("lengthPropertyNotAnInteger")

def rawLengthPropertyNotAnInteger: String = resourceBundle.getString("lengthPropertyNotAnInteger")

def sizePropertyNotAnInteger: String = resourceBundle.getString("sizePropertyNotAnInteger")

def rawSizePropertyNotAnInteger: String = resourceBundle.getString("sizePropertyNotAnInteger")

def wasEqualTo(param0: Any, param1: Any): String = makeString("wasEqualTo", Array(param0, param1))

def rawWasEqualTo: String = resourceBundle.getString("wasEqualTo")

def wasNotEqualTo(param0: Any, param1: Any): String = makeString("wasNotEqualTo", Array(param0, param1))

def rawWasNotEqualTo: String = resourceBundle.getString("wasNotEqualTo")

def printedReportPlusLineNumber(param0: Any, param1: Any): String = makeString("printedReportPlusLineNumber", Array(param0, param1))

def rawPrintedReportPlusLineNumber: String = resourceBundle.getString("printedReportPlusLineNumber")

def printedReportPlusPath(param0: Any, param1: Any): String = makeString("printedReportPlusPath", Array(param0, param1))

def rawPrintedReportPlusPath: String = resourceBundle.getString("printedReportPlusPath")

def propertyFailed(param0: Any): String = makeString("propertyFailed", Array(param0))

def rawPropertyFailed: String = resourceBundle.getString("propertyFailed")

def propertyExhausted(param0: Any, param1: Any): String = makeString("propertyExhausted", Array(param0, param1))

def rawPropertyExhausted: String = resourceBundle.getString("propertyExhausted")

def undecoratedPropertyCheckFailureMessage: String = resourceBundle.getString("undecoratedPropertyCheckFailureMessage")

def rawUndecoratedPropertyCheckFailureMessage: String = resourceBundle.getString("undecoratedPropertyCheckFailureMessage")

def propertyException(param0: Any): String = makeString("propertyException", Array(param0))

def rawPropertyException: String = resourceBundle.getString("propertyException")

def generatorException(param0: Any): String = makeString("generatorException", Array(param0))

def rawGeneratorException: String = resourceBundle.getString("generatorException")

def thrownExceptionsMessage(param0: Any): String = makeString("thrownExceptionsMessage", Array(param0))

def rawThrownExceptionsMessage: String = resourceBundle.getString("thrownExceptionsMessage")

def thrownExceptionsLocation(param0: Any): String = makeString("thrownExceptionsLocation", Array(param0))

def rawThrownExceptionsLocation: String = resourceBundle.getString("thrownExceptionsLocation")

def propCheckExhausted(param0: Any, param1: Any): String = makeString("propCheckExhausted", Array(param0, param1))

def rawPropCheckExhausted: String = resourceBundle.getString("propCheckExhausted")

def propCheckExhaustedAfterOne(param0: Any): String = makeString("propCheckExhaustedAfterOne", Array(param0))

def rawPropCheckExhaustedAfterOne: String = resourceBundle.getString("propCheckExhaustedAfterOne")

def occurredAtRow(param0: Any): String = makeString("occurredAtRow", Array(param0))

def rawOccurredAtRow: String = resourceBundle.getString("occurredAtRow")

def occurredOnValues: String = resourceBundle.getString("occurredOnValues")

def rawOccurredOnValues: String = resourceBundle.getString("occurredOnValues")

def propCheckLabel: String = resourceBundle.getString("propCheckLabel")

def rawPropCheckLabel: String = resourceBundle.getString("propCheckLabel")

def propCheckLabels: String = resourceBundle.getString("propCheckLabels")

def rawPropCheckLabels: String = resourceBundle.getString("propCheckLabels")

def suiteAndTestNamesFormattedForDisplay(param0: Any, param1: Any): String = makeString("suiteAndTestNamesFormattedForDisplay", Array(param0, param1))

def rawSuiteAndTestNamesFormattedForDisplay: String = resourceBundle.getString("suiteAndTestNamesFormattedForDisplay")

def initSeed(param0: Any): String = makeString("initSeed", Array(param0))

def rawInitSeed: String = resourceBundle.getString("initSeed")

def notLoneElement(param0: Any, param1: Any): String = makeString("notLoneElement", Array(param0, param1))

def rawNotLoneElement: String = resourceBundle.getString("notLoneElement")

def testSummary(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = makeString("testSummary", Array(param0, param1, param2, param3, param4))

def rawTestSummary: String = resourceBundle.getString("testSummary")

def suiteSummary(param0: Any, param1: Any): String = makeString("suiteSummary", Array(param0, param1))

def rawSuiteSummary: String = resourceBundle.getString("suiteSummary")

def suiteScopeSummary(param0: Any, param1: Any, param2: Any): String = makeString("suiteScopeSummary", Array(param0, param1, param2))

def rawSuiteScopeSummary: String = resourceBundle.getString("suiteScopeSummary")

def runCompletedIn(param0: Any): String = makeString("runCompletedIn", Array(param0))

def rawRunCompletedIn: String = resourceBundle.getString("runCompletedIn")

def runCompleted: String = resourceBundle.getString("runCompleted")

def rawRunCompleted: String = resourceBundle.getString("runCompleted")

def runAbortedIn(param0: Any): String = makeString("runAbortedIn", Array(param0))

def rawRunAbortedIn: String = resourceBundle.getString("runAbortedIn")

def runStoppedIn(param0: Any): String = makeString("runStoppedIn", Array(param0))

def rawRunStoppedIn: String = resourceBundle.getString("runStoppedIn")

def runStopped: String = resourceBundle.getString("runStopped")

def rawRunStopped: String = resourceBundle.getString("runStopped")

def totalNumberOfTestsRun(param0: Any): String = makeString("totalNumberOfTestsRun", Array(param0))

def rawTotalNumberOfTestsRun: String = resourceBundle.getString("totalNumberOfTestsRun")

def oneMillisecond: String = resourceBundle.getString("oneMillisecond")

def rawOneMillisecond: String = resourceBundle.getString("oneMillisecond")

def milliseconds(param0: Any): String = makeString("milliseconds", Array(param0))

def rawMilliseconds: String = resourceBundle.getString("milliseconds")

def oneSecond: String = resourceBundle.getString("oneSecond")

def rawOneSecond: String = resourceBundle.getString("oneSecond")

def oneSecondOneMillisecond: String = resourceBundle.getString("oneSecondOneMillisecond")

def rawOneSecondOneMillisecond: String = resourceBundle.getString("oneSecondOneMillisecond")

def oneSecondMilliseconds(param0: Any): String = makeString("oneSecondMilliseconds", Array(param0))

def rawOneSecondMilliseconds: String = resourceBundle.getString("oneSecondMilliseconds")

def seconds(param0: Any): String = makeString("seconds", Array(param0))

def rawSeconds: String = resourceBundle.getString("seconds")

def secondsMilliseconds(param0: Any, param1: Any): String = makeString("secondsMilliseconds", Array(param0, param1))

def rawSecondsMilliseconds: String = resourceBundle.getString("secondsMilliseconds")

def oneMinute: String = resourceBundle.getString("oneMinute")

def rawOneMinute: String = resourceBundle.getString("oneMinute")

def oneMinuteOneSecond: String = resourceBundle.getString("oneMinuteOneSecond")

def rawOneMinuteOneSecond: String = resourceBundle.getString("oneMinuteOneSecond")

def oneMinuteSeconds(param0: Any): String = makeString("oneMinuteSeconds", Array(param0))

def rawOneMinuteSeconds: String = resourceBundle.getString("oneMinuteSeconds")

def minutes(param0: Any): String = makeString("minutes", Array(param0))

def rawMinutes: String = resourceBundle.getString("minutes")

def minutesOneSecond(param0: Any): String = makeString("minutesOneSecond", Array(param0))

def rawMinutesOneSecond: String = resourceBundle.getString("minutesOneSecond")

def minutesSeconds(param0: Any, param1: Any): String = makeString("minutesSeconds", Array(param0, param1))

def rawMinutesSeconds: String = resourceBundle.getString("minutesSeconds")

def oneHour: String = resourceBundle.getString("oneHour")

def rawOneHour: String = resourceBundle.getString("oneHour")

def oneHourOneSecond: String = resourceBundle.getString("oneHourOneSecond")

def rawOneHourOneSecond: String = resourceBundle.getString("oneHourOneSecond")

def oneHourSeconds(param0: Any): String = makeString("oneHourSeconds", Array(param0))

def rawOneHourSeconds: String = resourceBundle.getString("oneHourSeconds")

def oneHourOneMinute: String = resourceBundle.getString("oneHourOneMinute")

def rawOneHourOneMinute: String = resourceBundle.getString("oneHourOneMinute")

def oneHourOneMinuteOneSecond: String = resourceBundle.getString("oneHourOneMinuteOneSecond")

def rawOneHourOneMinuteOneSecond: String = resourceBundle.getString("oneHourOneMinuteOneSecond")

def oneHourOneMinuteSeconds(param0: Any): String = makeString("oneHourOneMinuteSeconds", Array(param0))

def rawOneHourOneMinuteSeconds: String = resourceBundle.getString("oneHourOneMinuteSeconds")

def oneHourMinutes(param0: Any): String = makeString("oneHourMinutes", Array(param0))

def rawOneHourMinutes: String = resourceBundle.getString("oneHourMinutes")

def oneHourMinutesOneSecond(param0: Any): String = makeString("oneHourMinutesOneSecond", Array(param0))

def rawOneHourMinutesOneSecond: String = resourceBundle.getString("oneHourMinutesOneSecond")

def oneHourMinutesSeconds(param0: Any, param1: Any): String = makeString("oneHourMinutesSeconds", Array(param0, param1))

def rawOneHourMinutesSeconds: String = resourceBundle.getString("oneHourMinutesSeconds")

def hours(param0: Any): String = makeString("hours", Array(param0))

def rawHours: String = resourceBundle.getString("hours")

def hoursOneSecond(param0: Any): String = makeString("hoursOneSecond", Array(param0))

def rawHoursOneSecond: String = resourceBundle.getString("hoursOneSecond")

def hoursSeconds(param0: Any, param1: Any): String = makeString("hoursSeconds", Array(param0, param1))

def rawHoursSeconds: String = resourceBundle.getString("hoursSeconds")

def hoursOneMinute(param0: Any): String = makeString("hoursOneMinute", Array(param0))

def rawHoursOneMinute: String = resourceBundle.getString("hoursOneMinute")

def hoursOneMinuteOneSecond(param0: Any): String = makeString("hoursOneMinuteOneSecond", Array(param0))

def rawHoursOneMinuteOneSecond: String = resourceBundle.getString("hoursOneMinuteOneSecond")

def hoursOneMinuteSeconds(param0: Any, param1: Any): String = makeString("hoursOneMinuteSeconds", Array(param0, param1))

def rawHoursOneMinuteSeconds: String = resourceBundle.getString("hoursOneMinuteSeconds")

def hoursMinutes(param0: Any, param1: Any): String = makeString("hoursMinutes", Array(param0, param1))

def rawHoursMinutes: String = resourceBundle.getString("hoursMinutes")

def hoursMinutesOneSecond(param0: Any, param1: Any): String = makeString("hoursMinutesOneSecond", Array(param0, param1))

def rawHoursMinutesOneSecond: String = resourceBundle.getString("hoursMinutesOneSecond")

def hoursMinutesSeconds(param0: Any, param1: Any, param2: Any): String = makeString("hoursMinutesSeconds", Array(param0, param1, param2))

def rawHoursMinutesSeconds: String = resourceBundle.getString("hoursMinutesSeconds")

def withDuration(param0: Any, param1: Any): String = makeString("withDuration", Array(param0, param1))

def rawWithDuration: String = resourceBundle.getString("withDuration")

def feature(param0: Any): String = makeString("feature", Array(param0))

def rawFeature: String = resourceBundle.getString("feature")

def needFixtureInTestName(param0: Any): String = makeString("needFixtureInTestName", Array(param0))

def rawNeedFixtureInTestName: String = resourceBundle.getString("needFixtureInTestName")

def testNotFound(param0: Any): String = makeString("testNotFound", Array(param0))

def rawTestNotFound: String = resourceBundle.getString("testNotFound")

def pendingUntilFixed: String = resourceBundle.getString("pendingUntilFixed")

def rawPendingUntilFixed: String = resourceBundle.getString("pendingUntilFixed")

def dashXDeprecated: String = resourceBundle.getString("dashXDeprecated")

def rawDashXDeprecated: String = resourceBundle.getString("dashXDeprecated")

def threadCalledAfterConductingHasCompleted: String = resourceBundle.getString("threadCalledAfterConductingHasCompleted")

def rawThreadCalledAfterConductingHasCompleted: String = resourceBundle.getString("threadCalledAfterConductingHasCompleted")

def cannotInvokeWhenFinishedAfterConduct: String = resourceBundle.getString("cannotInvokeWhenFinishedAfterConduct")

def rawCannotInvokeWhenFinishedAfterConduct: String = resourceBundle.getString("cannotInvokeWhenFinishedAfterConduct")

def cantRegisterThreadsWithSameName(param0: Any): String = makeString("cantRegisterThreadsWithSameName", Array(param0))

def rawCantRegisterThreadsWithSameName: String = resourceBundle.getString("cantRegisterThreadsWithSameName")

def cannotCallConductTwice: String = resourceBundle.getString("cannotCallConductTwice")

def rawCannotCallConductTwice: String = resourceBundle.getString("cannotCallConductTwice")

def cannotWaitForBeatZero: String = resourceBundle.getString("cannotWaitForBeatZero")

def rawCannotWaitForBeatZero: String = resourceBundle.getString("cannotWaitForBeatZero")

def cannotWaitForNegativeBeat: String = resourceBundle.getString("cannotWaitForNegativeBeat")

def rawCannotWaitForNegativeBeat: String = resourceBundle.getString("cannotWaitForNegativeBeat")

def cannotPassNonPositiveClockPeriod(param0: Any): String = makeString("cannotPassNonPositiveClockPeriod", Array(param0))

def rawCannotPassNonPositiveClockPeriod: String = resourceBundle.getString("cannotPassNonPositiveClockPeriod")

def cannotPassNonPositiveTimeout(param0: Any): String = makeString("cannotPassNonPositiveTimeout", Array(param0))

def rawCannotPassNonPositiveTimeout: String = resourceBundle.getString("cannotPassNonPositiveTimeout")

def whenFinishedCanOnlyBeCalledByMainThread: String = resourceBundle.getString("whenFinishedCanOnlyBeCalledByMainThread")

def rawWhenFinishedCanOnlyBeCalledByMainThread: String = resourceBundle.getString("whenFinishedCanOnlyBeCalledByMainThread")

def suspectedDeadlock(param0: Any, param1: Any): String = makeString("suspectedDeadlock", Array(param0, param1))

def rawSuspectedDeadlock: String = resourceBundle.getString("suspectedDeadlock")

def testTimedOut(param0: Any): String = makeString("testTimedOut", Array(param0))

def rawTestTimedOut: String = resourceBundle.getString("testTimedOut")

def suspectedDeadlockDEPRECATED(param0: Any, param1: Any): String = makeString("suspectedDeadlockDEPRECATED", Array(param0, param1))

def rawSuspectedDeadlockDEPRECATED: String = resourceBundle.getString("suspectedDeadlockDEPRECATED")

def testTimedOutDEPRECATED(param0: Any): String = makeString("testTimedOutDEPRECATED", Array(param0))

def rawTestTimedOutDEPRECATED: String = resourceBundle.getString("testTimedOutDEPRECATED")

def concurrentInformerMod(param0: Any): String = makeString("concurrentInformerMod", Array(param0))

def rawConcurrentInformerMod: String = resourceBundle.getString("concurrentInformerMod")

def concurrentNotifierMod(param0: Any): String = makeString("concurrentNotifierMod", Array(param0))

def rawConcurrentNotifierMod: String = resourceBundle.getString("concurrentNotifierMod")

def concurrentAlerterMod(param0: Any): String = makeString("concurrentAlerterMod", Array(param0))

def rawConcurrentAlerterMod: String = resourceBundle.getString("concurrentAlerterMod")

def concurrentDocumenterMod(param0: Any): String = makeString("concurrentDocumenterMod", Array(param0))

def rawConcurrentDocumenterMod: String = resourceBundle.getString("concurrentDocumenterMod")

def cantCallInfoNow(param0: Any): String = makeString("cantCallInfoNow", Array(param0))

def rawCantCallInfoNow: String = resourceBundle.getString("cantCallInfoNow")

def cantCallMarkupNow(param0: Any): String = makeString("cantCallMarkupNow", Array(param0))

def rawCantCallMarkupNow: String = resourceBundle.getString("cantCallMarkupNow")

def concurrentFunSuiteMod: String = resourceBundle.getString("concurrentFunSuiteMod")

def rawConcurrentFunSuiteMod: String = resourceBundle.getString("concurrentFunSuiteMod")

def concurrentPropSpecMod: String = resourceBundle.getString("concurrentPropSpecMod")

def rawConcurrentPropSpecMod: String = resourceBundle.getString("concurrentPropSpecMod")

def concurrentFixtureFunSuiteMod: String = resourceBundle.getString("concurrentFixtureFunSuiteMod")

def rawConcurrentFixtureFunSuiteMod: String = resourceBundle.getString("concurrentFixtureFunSuiteMod")

def concurrentFixturePropSpecMod: String = resourceBundle.getString("concurrentFixturePropSpecMod")

def rawConcurrentFixturePropSpecMod: String = resourceBundle.getString("concurrentFixturePropSpecMod")

def concurrentSpecMod: String = resourceBundle.getString("concurrentSpecMod")

def rawConcurrentSpecMod: String = resourceBundle.getString("concurrentSpecMod")

def concurrentFreeSpecMod: String = resourceBundle.getString("concurrentFreeSpecMod")

def rawConcurrentFreeSpecMod: String = resourceBundle.getString("concurrentFreeSpecMod")

def concurrentFixtureSpecMod: String = resourceBundle.getString("concurrentFixtureSpecMod")

def rawConcurrentFixtureSpecMod: String = resourceBundle.getString("concurrentFixtureSpecMod")

def concurrentFlatSpecMod: String = resourceBundle.getString("concurrentFlatSpecMod")

def rawConcurrentFlatSpecMod: String = resourceBundle.getString("concurrentFlatSpecMod")

def concurrentFixtureFlatSpecMod: String = resourceBundle.getString("concurrentFixtureFlatSpecMod")

def rawConcurrentFixtureFlatSpecMod: String = resourceBundle.getString("concurrentFixtureFlatSpecMod")

def concurrentWordSpecMod: String = resourceBundle.getString("concurrentWordSpecMod")

def rawConcurrentWordSpecMod: String = resourceBundle.getString("concurrentWordSpecMod")

def concurrentFixtureWordSpecMod: String = resourceBundle.getString("concurrentFixtureWordSpecMod")

def rawConcurrentFixtureWordSpecMod: String = resourceBundle.getString("concurrentFixtureWordSpecMod")

def concurrentFixtureFreeSpecMod: String = resourceBundle.getString("concurrentFixtureFreeSpecMod")

def rawConcurrentFixtureFreeSpecMod: String = resourceBundle.getString("concurrentFixtureFreeSpecMod")

def concurrentFeatureSpecMod: String = resourceBundle.getString("concurrentFeatureSpecMod")

def rawConcurrentFeatureSpecMod: String = resourceBundle.getString("concurrentFeatureSpecMod")

def concurrentFixtureFeatureSpecMod: String = resourceBundle.getString("concurrentFixtureFeatureSpecMod")

def rawConcurrentFixtureFeatureSpecMod: String = resourceBundle.getString("concurrentFixtureFeatureSpecMod")

def concurrentDocSpecMod: String = resourceBundle.getString("concurrentDocSpecMod")

def rawConcurrentDocSpecMod: String = resourceBundle.getString("concurrentDocSpecMod")

def tryNotAFailure(param0: Any): String = makeString("tryNotAFailure", Array(param0))

def rawTryNotAFailure: String = resourceBundle.getString("tryNotAFailure")

def tryNotASuccess(param0: Any): String = makeString("tryNotASuccess", Array(param0))

def rawTryNotASuccess: String = resourceBundle.getString("tryNotASuccess")

def optionValueNotDefined: String = resourceBundle.getString("optionValueNotDefined")

def rawOptionValueNotDefined: String = resourceBundle.getString("optionValueNotDefined")

def eitherLeftValueNotDefined(param0: Any): String = makeString("eitherLeftValueNotDefined", Array(param0))

def rawEitherLeftValueNotDefined: String = resourceBundle.getString("eitherLeftValueNotDefined")

def eitherRightValueNotDefined(param0: Any): String = makeString("eitherRightValueNotDefined", Array(param0))

def rawEitherRightValueNotDefined: String = resourceBundle.getString("eitherRightValueNotDefined")

def eitherValueNotDefined(param0: Any): String = makeString("eitherValueNotDefined", Array(param0))

def rawEitherValueNotDefined: String = resourceBundle.getString("eitherValueNotDefined")

def partialFunctionValueNotDefined(param0: Any): String = makeString("partialFunctionValueNotDefined", Array(param0))

def rawPartialFunctionValueNotDefined: String = resourceBundle.getString("partialFunctionValueNotDefined")

def insidePartialFunctionNotDefined(param0: Any): String = makeString("insidePartialFunctionNotDefined", Array(param0))

def rawInsidePartialFunctionNotDefined: String = resourceBundle.getString("insidePartialFunctionNotDefined")

def insidePartialFunctionAppendSomeMsg(param0: Any, param1: Any, param2: Any): String = makeString("insidePartialFunctionAppendSomeMsg", Array(param0, param1, param2))

def rawInsidePartialFunctionAppendSomeMsg: String = resourceBundle.getString("insidePartialFunctionAppendSomeMsg")

def insidePartialFunctionAppendNone(param0: Any, param1: Any): String = makeString("insidePartialFunctionAppendNone", Array(param0, param1))

def rawInsidePartialFunctionAppendNone: String = resourceBundle.getString("insidePartialFunctionAppendNone")

def didNotEventuallySucceed(param0: Any, param1: Any): String = makeString("didNotEventuallySucceed", Array(param0, param1))

def rawDidNotEventuallySucceed: String = resourceBundle.getString("didNotEventuallySucceed")

def didNotEventuallySucceedBecause(param0: Any, param1: Any, param2: Any): String = makeString("didNotEventuallySucceedBecause", Array(param0, param1, param2))

def rawDidNotEventuallySucceedBecause: String = resourceBundle.getString("didNotEventuallySucceedBecause")

def didNotUltimatelySucceed(param0: Any, param1: Any): String = makeString("didNotUltimatelySucceed", Array(param0, param1))

def rawDidNotUltimatelySucceed: String = resourceBundle.getString("didNotUltimatelySucceed")

def didNotUltimatelySucceedBecause(param0: Any, param1: Any, param2: Any): String = makeString("didNotUltimatelySucceedBecause", Array(param0, param1, param2))

def rawDidNotUltimatelySucceedBecause: String = resourceBundle.getString("didNotUltimatelySucceedBecause")

def wasNeverReady(param0: Any): String = makeString("wasNeverReady", Array(param0))

def rawWasNeverReady: String = resourceBundle.getString("wasNeverReady")

def awaitMustBeCalledOnCreatingThread: String = resourceBundle.getString("awaitMustBeCalledOnCreatingThread")

def rawAwaitMustBeCalledOnCreatingThread: String = resourceBundle.getString("awaitMustBeCalledOnCreatingThread")

def awaitTimedOut: String = resourceBundle.getString("awaitTimedOut")

def rawAwaitTimedOut: String = resourceBundle.getString("awaitTimedOut")

def futureReturnedAnException(param0: Any): String = makeString("futureReturnedAnException", Array(param0))

def rawFutureReturnedAnException: String = resourceBundle.getString("futureReturnedAnException")

def futureReturnedAnExceptionWithMessage(param0: Any, param1: Any): String = makeString("futureReturnedAnExceptionWithMessage", Array(param0, param1))

def rawFutureReturnedAnExceptionWithMessage: String = resourceBundle.getString("futureReturnedAnExceptionWithMessage")

def futureWasCanceled: String = resourceBundle.getString("futureWasCanceled")

def rawFutureWasCanceled: String = resourceBundle.getString("futureWasCanceled")

def futureExpired(param0: Any, param1: Any): String = makeString("futureExpired", Array(param0, param1))

def rawFutureExpired: String = resourceBundle.getString("futureExpired")

def timeoutFailedAfter(param0: Any): String = makeString("timeoutFailedAfter", Array(param0))

def rawTimeoutFailedAfter: String = resourceBundle.getString("timeoutFailedAfter")

def timeoutFailingAfter(param0: Any): String = makeString("timeoutFailingAfter", Array(param0))

def rawTimeoutFailingAfter: String = resourceBundle.getString("timeoutFailingAfter")

def timeoutCanceledAfter(param0: Any): String = makeString("timeoutCanceledAfter", Array(param0))

def rawTimeoutCanceledAfter: String = resourceBundle.getString("timeoutCanceledAfter")

def timeoutCancelingAfter(param0: Any): String = makeString("timeoutCancelingAfter", Array(param0))

def rawTimeoutCancelingAfter: String = resourceBundle.getString("timeoutCancelingAfter")

def testTimeLimitExceeded(param0: Any): String = makeString("testTimeLimitExceeded", Array(param0))

def rawTestTimeLimitExceeded: String = resourceBundle.getString("testTimeLimitExceeded")

def singularNanosecondUnits(param0: Any): String = makeString("singularNanosecondUnits", Array(param0))

def rawSingularNanosecondUnits: String = resourceBundle.getString("singularNanosecondUnits")

def pluralNanosecondUnits(param0: Any): String = makeString("pluralNanosecondUnits", Array(param0))

def rawPluralNanosecondUnits: String = resourceBundle.getString("pluralNanosecondUnits")

def singularMicrosecondUnits(param0: Any): String = makeString("singularMicrosecondUnits", Array(param0))

def rawSingularMicrosecondUnits: String = resourceBundle.getString("singularMicrosecondUnits")

def pluralMicrosecondUnits(param0: Any): String = makeString("pluralMicrosecondUnits", Array(param0))

def rawPluralMicrosecondUnits: String = resourceBundle.getString("pluralMicrosecondUnits")

def singularMillisecondUnits(param0: Any): String = makeString("singularMillisecondUnits", Array(param0))

def rawSingularMillisecondUnits: String = resourceBundle.getString("singularMillisecondUnits")

def pluralMillisecondUnits(param0: Any): String = makeString("pluralMillisecondUnits", Array(param0))

def rawPluralMillisecondUnits: String = resourceBundle.getString("pluralMillisecondUnits")

def singularSecondUnits(param0: Any): String = makeString("singularSecondUnits", Array(param0))

def rawSingularSecondUnits: String = resourceBundle.getString("singularSecondUnits")

def pluralSecondUnits(param0: Any): String = makeString("pluralSecondUnits", Array(param0))

def rawPluralSecondUnits: String = resourceBundle.getString("pluralSecondUnits")

def singularMinuteUnits(param0: Any): String = makeString("singularMinuteUnits", Array(param0))

def rawSingularMinuteUnits: String = resourceBundle.getString("singularMinuteUnits")

def pluralMinuteUnits(param0: Any): String = makeString("pluralMinuteUnits", Array(param0))

def rawPluralMinuteUnits: String = resourceBundle.getString("pluralMinuteUnits")

def singularHourUnits(param0: Any): String = makeString("singularHourUnits", Array(param0))

def rawSingularHourUnits: String = resourceBundle.getString("singularHourUnits")

def pluralHourUnits(param0: Any): String = makeString("pluralHourUnits", Array(param0))

def rawPluralHourUnits: String = resourceBundle.getString("pluralHourUnits")

def singularDayUnits(param0: Any): String = makeString("singularDayUnits", Array(param0))

def rawSingularDayUnits: String = resourceBundle.getString("singularDayUnits")

def pluralDayUnits(param0: Any): String = makeString("pluralDayUnits", Array(param0))

def rawPluralDayUnits: String = resourceBundle.getString("pluralDayUnits")

def leftAndRight(param0: Any, param1: Any): String = makeString("leftAndRight", Array(param0, param1))

def rawLeftAndRight: String = resourceBundle.getString("leftAndRight")

def leftCommaAndRight(param0: Any, param1: Any): String = makeString("leftCommaAndRight", Array(param0, param1))

def rawLeftCommaAndRight: String = resourceBundle.getString("leftCommaAndRight")

def configMapEntryNotFound(param0: Any): String = makeString("configMapEntryNotFound", Array(param0))

def rawConfigMapEntryNotFound: String = resourceBundle.getString("configMapEntryNotFound")

def configMapEntryHadUnexpectedType(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("configMapEntryHadUnexpectedType", Array(param0, param1, param2, param3))

def rawConfigMapEntryHadUnexpectedType: String = resourceBundle.getString("configMapEntryHadUnexpectedType")

def forAssertionsMoreThanZero(param0: Any): String = makeString("forAssertionsMoreThanZero", Array(param0))

def rawForAssertionsMoreThanZero: String = resourceBundle.getString("forAssertionsMoreThanZero")

def forAssertionsMoreThanEqualZero(param0: Any): String = makeString("forAssertionsMoreThanEqualZero", Array(param0))

def rawForAssertionsMoreThanEqualZero: String = resourceBundle.getString("forAssertionsMoreThanEqualZero")

def forAssertionsMoreThan(param0: Any, param1: Any): String = makeString("forAssertionsMoreThan", Array(param0, param1))

def rawForAssertionsMoreThan: String = resourceBundle.getString("forAssertionsMoreThan")

def forAssertionsGenTraversableMessageWithStackDepth(param0: Any, param1: Any, param2: Any): String = makeString("forAssertionsGenTraversableMessageWithStackDepth", Array(param0, param1, param2))

def rawForAssertionsGenTraversableMessageWithStackDepth: String = resourceBundle.getString("forAssertionsGenTraversableMessageWithStackDepth")

def forAssertionsGenTraversableMessageWithoutStackDepth(param0: Any, param1: Any): String = makeString("forAssertionsGenTraversableMessageWithoutStackDepth", Array(param0, param1))

def rawForAssertionsGenTraversableMessageWithoutStackDepth: String = resourceBundle.getString("forAssertionsGenTraversableMessageWithoutStackDepth")

def forAssertionsGenMapMessageWithStackDepth(param0: Any, param1: Any, param2: Any): String = makeString("forAssertionsGenMapMessageWithStackDepth", Array(param0, param1, param2))

def rawForAssertionsGenMapMessageWithStackDepth: String = resourceBundle.getString("forAssertionsGenMapMessageWithStackDepth")

def forAssertionsGenMapMessageWithoutStackDepth(param0: Any, param1: Any): String = makeString("forAssertionsGenMapMessageWithoutStackDepth", Array(param0, param1))

def rawForAssertionsGenMapMessageWithoutStackDepth: String = resourceBundle.getString("forAssertionsGenMapMessageWithoutStackDepth")

def forAssertionsNoElement: String = resourceBundle.getString("forAssertionsNoElement")

def rawForAssertionsNoElement: String = resourceBundle.getString("forAssertionsNoElement")

def forAssertionsElement(param0: Any): String = makeString("forAssertionsElement", Array(param0))

def rawForAssertionsElement: String = resourceBundle.getString("forAssertionsElement")

def forAssertionsElements(param0: Any): String = makeString("forAssertionsElements", Array(param0))

def rawForAssertionsElements: String = resourceBundle.getString("forAssertionsElements")

def forAssertionsIndexLabel(param0: Any): String = makeString("forAssertionsIndexLabel", Array(param0))

def rawForAssertionsIndexLabel: String = resourceBundle.getString("forAssertionsIndexLabel")

def forAssertionsIndexAndLabel(param0: Any, param1: Any): String = makeString("forAssertionsIndexAndLabel", Array(param0, param1))

def rawForAssertionsIndexAndLabel: String = resourceBundle.getString("forAssertionsIndexAndLabel")

def forAssertionsKeyLabel(param0: Any): String = makeString("forAssertionsKeyLabel", Array(param0))

def rawForAssertionsKeyLabel: String = resourceBundle.getString("forAssertionsKeyLabel")

def forAssertionsKeyAndLabel(param0: Any, param1: Any): String = makeString("forAssertionsKeyAndLabel", Array(param0, param1))

def rawForAssertionsKeyAndLabel: String = resourceBundle.getString("forAssertionsKeyAndLabel")

def forAllFailed(param0: Any, param1: Any): String = makeString("forAllFailed", Array(param0, param1))

def rawForAllFailed: String = resourceBundle.getString("forAllFailed")

def allShorthandFailed(param0: Any, param1: Any): String = makeString("allShorthandFailed", Array(param0, param1))

def rawAllShorthandFailed: String = resourceBundle.getString("allShorthandFailed")

def forAtLeastFailedNoElement(param0: Any, param1: Any, param2: Any): String = makeString("forAtLeastFailedNoElement", Array(param0, param1, param2))

def rawForAtLeastFailedNoElement: String = resourceBundle.getString("forAtLeastFailedNoElement")

def forAtLeastFailed(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("forAtLeastFailed", Array(param0, param1, param2, param3))

def rawForAtLeastFailed: String = resourceBundle.getString("forAtLeastFailed")

def atLeastShorthandFailedNoElement(param0: Any, param1: Any, param2: Any): String = makeString("atLeastShorthandFailedNoElement", Array(param0, param1, param2))

def rawAtLeastShorthandFailedNoElement: String = resourceBundle.getString("atLeastShorthandFailedNoElement")

def atLeastShorthandFailed(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("atLeastShorthandFailed", Array(param0, param1, param2, param3))

def rawAtLeastShorthandFailed: String = resourceBundle.getString("atLeastShorthandFailed")

def forAtMostFailed(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("forAtMostFailed", Array(param0, param1, param2, param3))

def rawForAtMostFailed: String = resourceBundle.getString("forAtMostFailed")

def atMostShorthandFailed(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("atMostShorthandFailed", Array(param0, param1, param2, param3))

def rawAtMostShorthandFailed: String = resourceBundle.getString("atMostShorthandFailed")

def forExactlyFailedNoElement(param0: Any, param1: Any, param2: Any): String = makeString("forExactlyFailedNoElement", Array(param0, param1, param2))

def rawForExactlyFailedNoElement: String = resourceBundle.getString("forExactlyFailedNoElement")

def forExactlyFailedLess(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = makeString("forExactlyFailedLess", Array(param0, param1, param2, param3, param4))

def rawForExactlyFailedLess: String = resourceBundle.getString("forExactlyFailedLess")

def forExactlyFailedMore(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("forExactlyFailedMore", Array(param0, param1, param2, param3))

def rawForExactlyFailedMore: String = resourceBundle.getString("forExactlyFailedMore")

def exactlyShorthandFailedNoElement(param0: Any, param1: Any, param2: Any): String = makeString("exactlyShorthandFailedNoElement", Array(param0, param1, param2))

def rawExactlyShorthandFailedNoElement: String = resourceBundle.getString("exactlyShorthandFailedNoElement")

def exactlyShorthandFailedLess(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = makeString("exactlyShorthandFailedLess", Array(param0, param1, param2, param3, param4))

def rawExactlyShorthandFailedLess: String = resourceBundle.getString("exactlyShorthandFailedLess")

def exactlyShorthandFailedMore(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("exactlyShorthandFailedMore", Array(param0, param1, param2, param3))

def rawExactlyShorthandFailedMore: String = resourceBundle.getString("exactlyShorthandFailedMore")

def forNoFailed(param0: Any, param1: Any): String = makeString("forNoFailed", Array(param0, param1))

def rawForNoFailed: String = resourceBundle.getString("forNoFailed")

def noShorthandFailed(param0: Any, param1: Any): String = makeString("noShorthandFailed", Array(param0, param1))

def rawNoShorthandFailed: String = resourceBundle.getString("noShorthandFailed")

def forBetweenFailedNoElement(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("forBetweenFailedNoElement", Array(param0, param1, param2, param3))

def rawForBetweenFailedNoElement: String = resourceBundle.getString("forBetweenFailedNoElement")

def forBetweenFailedLess(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any, param5: Any): String = makeString("forBetweenFailedLess", Array(param0, param1, param2, param3, param4, param5))

def rawForBetweenFailedLess: String = resourceBundle.getString("forBetweenFailedLess")

def forBetweenFailedMore(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = makeString("forBetweenFailedMore", Array(param0, param1, param2, param3, param4))

def rawForBetweenFailedMore: String = resourceBundle.getString("forBetweenFailedMore")

def betweenShorthandFailedNoElement(param0: Any, param1: Any, param2: Any, param3: Any): String = makeString("betweenShorthandFailedNoElement", Array(param0, param1, param2, param3))

def rawBetweenShorthandFailedNoElement: String = resourceBundle.getString("betweenShorthandFailedNoElement")

def betweenShorthandFailedLess(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any, param5: Any): String = makeString("betweenShorthandFailedLess", Array(param0, param1, param2, param3, param4, param5))

def rawBetweenShorthandFailedLess: String = resourceBundle.getString("betweenShorthandFailedLess")

def betweenShorthandFailedMore(param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = makeString("betweenShorthandFailedMore", Array(param0, param1, param2, param3, param4))

def rawBetweenShorthandFailedMore: String = resourceBundle.getString("betweenShorthandFailedMore")

def forEveryFailed(param0: Any, param1: Any): String = makeString("forEveryFailed", Array(param0, param1))

def rawForEveryFailed: String = resourceBundle.getString("forEveryFailed")

def everyShorthandFailed(param0: Any, param1: Any): String = makeString("everyShorthandFailed", Array(param0, param1))

def rawEveryShorthandFailed: String = resourceBundle.getString("everyShorthandFailed")

def discoveryStarting: String = resourceBundle.getString("discoveryStarting")

def rawDiscoveryStarting: String = resourceBundle.getString("discoveryStarting")

def discoveryCompleted: String = resourceBundle.getString("discoveryCompleted")

def rawDiscoveryCompleted: String = resourceBundle.getString("discoveryCompleted")

def discoveryCompletedIn(param0: Any): String = makeString("discoveryCompletedIn", Array(param0))

def rawDiscoveryCompletedIn: String = resourceBundle.getString("discoveryCompletedIn")

def doingDiscovery: String = resourceBundle.getString("doingDiscovery")

def rawDoingDiscovery: String = resourceBundle.getString("doingDiscovery")

def atCheckpointAt: String = resourceBundle.getString("atCheckpointAt")

def rawAtCheckpointAt: String = resourceBundle.getString("atCheckpointAt")

def slowpokeDetected(param0: Any, param1: Any, param2: Any): String = makeString("slowpokeDetected", Array(param0, param1, param2))

def rawSlowpokeDetected: String = resourceBundle.getString("slowpokeDetected")

def alertFormattedText(param0: Any): String = makeString("alertFormattedText", Array(param0))

def rawAlertFormattedText: String = resourceBundle.getString("alertFormattedText")

def noteFormattedText(param0: Any): String = makeString("noteFormattedText", Array(param0))

def rawNoteFormattedText: String = resourceBundle.getString("noteFormattedText")

def testFlickered: String = resourceBundle.getString("testFlickered")

def rawTestFlickered: String = resourceBundle.getString("testFlickered")

def cannotRerun(param0: Any, param1: Any, param2: Any): String = makeString("cannotRerun", Array(param0, param1, param2))

def rawCannotRerun: String = resourceBundle.getString("cannotRerun")

def testCannotBeNestedInsideAnotherTest: String = resourceBundle.getString("testCannotBeNestedInsideAnotherTest")

def rawTestCannotBeNestedInsideAnotherTest: String = resourceBundle.getString("testCannotBeNestedInsideAnotherTest")

def nonEmptyMatchPatternCase: String = resourceBundle.getString("nonEmptyMatchPatternCase")

def rawNonEmptyMatchPatternCase: String = resourceBundle.getString("nonEmptyMatchPatternCase")

def expectedTypeErrorButGotNone(param0: Any): String = makeString("expectedTypeErrorButGotNone", Array(param0))

def rawExpectedTypeErrorButGotNone: String = resourceBundle.getString("expectedTypeErrorButGotNone")

def gotTypeErrorAsExpected(param0: Any): String = makeString("gotTypeErrorAsExpected", Array(param0))

def rawGotTypeErrorAsExpected: String = resourceBundle.getString("gotTypeErrorAsExpected")

def expectedCompileErrorButGotNone(param0: Any): String = makeString("expectedCompileErrorButGotNone", Array(param0))

def rawExpectedCompileErrorButGotNone: String = resourceBundle.getString("expectedCompileErrorButGotNone")

def didNotCompile(param0: Any): String = makeString("didNotCompile", Array(param0))

def rawDidNotCompile: String = resourceBundle.getString("didNotCompile")

def compiledSuccessfully(param0: Any): String = makeString("compiledSuccessfully", Array(param0))

def rawCompiledSuccessfully: String = resourceBundle.getString("compiledSuccessfully")

def expectedTypeErrorButGotParseError(param0: Any, param1: Any): String = makeString("expectedTypeErrorButGotParseError", Array(param0, param1))

def rawExpectedTypeErrorButGotParseError: String = resourceBundle.getString("expectedTypeErrorButGotParseError")

def expectedNoErrorButGotTypeError(param0: Any, param1: Any): String = makeString("expectedNoErrorButGotTypeError", Array(param0, param1))

def rawExpectedNoErrorButGotTypeError: String = resourceBundle.getString("expectedNoErrorButGotTypeError")

def expectedNoErrorButGotParseError(param0: Any, param1: Any): String = makeString("expectedNoErrorButGotParseError", Array(param0, param1))

def rawExpectedNoErrorButGotParseError: String = resourceBundle.getString("expectedNoErrorButGotParseError")

def anExpressionOfTypeNullIsIneligibleForImplicitConversion: String = resourceBundle.getString("anExpressionOfTypeNullIsIneligibleForImplicitConversion")

def rawAnExpressionOfTypeNullIsIneligibleForImplicitConversion: String = resourceBundle.getString("anExpressionOfTypeNullIsIneligibleForImplicitConversion")

def beTripleEqualsNotAllowed: String = resourceBundle.getString("beTripleEqualsNotAllowed")

def rawBeTripleEqualsNotAllowed: String = resourceBundle.getString("beTripleEqualsNotAllowed")

def assertionShouldBePutInsideItOrTheyClauseNotDescribeClause: String = resourceBundle.getString("assertionShouldBePutInsideItOrTheyClauseNotDescribeClause")

def rawAssertionShouldBePutInsideItOrTheyClauseNotDescribeClause: String = resourceBundle.getString("assertionShouldBePutInsideItOrTheyClauseNotDescribeClause")

def exceptionWasThrownInDescribeClause(param0: Any, param1: Any, param2: Any): String = makeString("exceptionWasThrownInDescribeClause", Array(param0, param1, param2))

def rawExceptionWasThrownInDescribeClause: String = resourceBundle.getString("exceptionWasThrownInDescribeClause")

def assertionShouldBePutInsideInClauseNotDashClause: String = resourceBundle.getString("assertionShouldBePutInsideInClauseNotDashClause")

def rawAssertionShouldBePutInsideInClauseNotDashClause: String = resourceBundle.getString("assertionShouldBePutInsideInClauseNotDashClause")

def exceptionWasThrownInDashClause(param0: Any, param1: Any, param2: Any): String = makeString("exceptionWasThrownInDashClause", Array(param0, param1, param2))

def rawExceptionWasThrownInDashClause: String = resourceBundle.getString("exceptionWasThrownInDashClause")

def assertionShouldBePutInsideScenarioClauseNotFeatureClause: String = resourceBundle.getString("assertionShouldBePutInsideScenarioClauseNotFeatureClause")

def rawAssertionShouldBePutInsideScenarioClauseNotFeatureClause: String = resourceBundle.getString("assertionShouldBePutInsideScenarioClauseNotFeatureClause")

def exceptionWasThrownInFeatureClause(param0: Any, param1: Any, param2: Any): String = makeString("exceptionWasThrownInFeatureClause", Array(param0, param1, param2))

def rawExceptionWasThrownInFeatureClause: String = resourceBundle.getString("exceptionWasThrownInFeatureClause")

def assertionShouldBePutInsideItOrTheyClauseNotShouldMustWhenThatWhichOrCanClause: String = resourceBundle.getString("assertionShouldBePutInsideItOrTheyClauseNotShouldMustWhenThatWhichOrCanClause")

def rawAssertionShouldBePutInsideItOrTheyClauseNotShouldMustWhenThatWhichOrCanClause: String = resourceBundle.getString("assertionShouldBePutInsideItOrTheyClauseNotShouldMustWhenThatWhichOrCanClause")

def exceptionWasThrownInShouldClause(param0: Any, param1: Any, param2: Any): String = makeString("exceptionWasThrownInShouldClause", Array(param0, param1, param2))

def rawExceptionWasThrownInShouldClause: String = resourceBundle.getString("exceptionWasThrownInShouldClause")

def exceptionWasThrownInMustClause(param0: Any, param1: Any, param2: Any): String = makeString("exceptionWasThrownInMustClause", Array(param0, param1, param2))

def rawExceptionWasThrownInMustClause: String = resourceBundle.getString("exceptionWasThrownInMustClause")

def exceptionWasThrownInWhenClause(param0: Any, param1: Any, param2: Any): String = makeString("exceptionWasThrownInWhenClause", Array(param0, param1, param2))

def rawExceptionWasThrownInWhenClause: String = resourceBundle.getString("exceptionWasThrownInWhenClause")

def exceptionWasThrownInThatClause(param0: Any, param1: Any, param2: Any): String = makeString("exceptionWasThrownInThatClause", Array(param0, param1, param2))

def rawExceptionWasThrownInThatClause: String = resourceBundle.getString("exceptionWasThrownInThatClause")

def exceptionWasThrownInWhichClause(param0: Any, param1: Any, param2: Any): String = makeString("exceptionWasThrownInWhichClause", Array(param0, param1, param2))

def rawExceptionWasThrownInWhichClause: String = resourceBundle.getString("exceptionWasThrownInWhichClause")

def exceptionWasThrownInCanClause(param0: Any, param1: Any, param2: Any): String = makeString("exceptionWasThrownInCanClause", Array(param0, param1, param2))

def rawExceptionWasThrownInCanClause: String = resourceBundle.getString("exceptionWasThrownInCanClause")

def assertionShouldBePutInsideDefNotObject: String = resourceBundle.getString("assertionShouldBePutInsideDefNotObject")

def rawAssertionShouldBePutInsideDefNotObject: String = resourceBundle.getString("assertionShouldBePutInsideDefNotObject")

def exceptionWasThrownInObject(param0: Any, param1: Any): String = makeString("exceptionWasThrownInObject", Array(param0, param1))

def rawExceptionWasThrownInObject: String = resourceBundle.getString("exceptionWasThrownInObject")

def tableDrivenForEveryFailed(param0: Any): String = makeString("tableDrivenForEveryFailed", Array(param0))

def rawTableDrivenForEveryFailed: String = resourceBundle.getString("tableDrivenForEveryFailed")

def tableDrivenExistsFailed(param0: Any): String = makeString("tableDrivenExistsFailed", Array(param0))

def rawTableDrivenExistsFailed: String = resourceBundle.getString("tableDrivenExistsFailed")

def withFixtureNotAllowedInAsyncFixtures: String = resourceBundle.getString("withFixtureNotAllowedInAsyncFixtures")

def rawWithFixtureNotAllowedInAsyncFixtures: String = resourceBundle.getString("withFixtureNotAllowedInAsyncFixtures")

def leftParensCommaBut(param0: Any, param1: Any): String = makeString("leftParensCommaBut", Array(param0, param1))

def rawLeftParensCommaBut: String = resourceBundle.getString("leftParensCommaBut")

def rightParensCommaBut(param0: Any, param1: Any): String = makeString("rightParensCommaBut", Array(param0, param1))

def rawRightParensCommaBut: String = resourceBundle.getString("rightParensCommaBut")

def bothParensCommaBut(param0: Any, param1: Any): String = makeString("bothParensCommaBut", Array(param0, param1))

def rawBothParensCommaBut: String = resourceBundle.getString("bothParensCommaBut")

def leftParensCommaAnd(param0: Any, param1: Any): String = makeString("leftParensCommaAnd", Array(param0, param1))

def rawLeftParensCommaAnd: String = resourceBundle.getString("leftParensCommaAnd")

def rightParensCommaAnd(param0: Any, param1: Any): String = makeString("rightParensCommaAnd", Array(param0, param1))

def rawRightParensCommaAnd: String = resourceBundle.getString("rightParensCommaAnd")

def bothParensCommaAnd(param0: Any, param1: Any): String = makeString("bothParensCommaAnd", Array(param0, param1))

def rawBothParensCommaAnd: String = resourceBundle.getString("bothParensCommaAnd")

def assertionWasTrue: String = resourceBundle.getString("assertionWasTrue")

def rawAssertionWasTrue: String = resourceBundle.getString("assertionWasTrue")

def fromEqualToToHavingLengthsBetween(param0: Any): String = makeString("fromEqualToToHavingLengthsBetween", Array(param0))

def rawFromEqualToToHavingLengthsBetween: String = resourceBundle.getString("fromEqualToToHavingLengthsBetween")

def fromGreaterThanToHavingLengthsBetween(param0: Any, param1: Any): String = makeString("fromGreaterThanToHavingLengthsBetween", Array(param0, param1))

def rawFromGreaterThanToHavingLengthsBetween: String = resourceBundle.getString("fromGreaterThanToHavingLengthsBetween")

def fromEqualToToHavingSizesBetween(param0: Any): String = makeString("fromEqualToToHavingSizesBetween", Array(param0))

def rawFromEqualToToHavingSizesBetween: String = resourceBundle.getString("fromEqualToToHavingSizesBetween")

def fromGreaterThanToHavingSizesBetween(param0: Any, param1: Any): String = makeString("fromGreaterThanToHavingSizesBetween", Array(param0, param1))

def rawFromGreaterThanToHavingSizesBetween: String = resourceBundle.getString("fromGreaterThanToHavingSizesBetween")

def analysis: String = resourceBundle.getString("analysis")

def rawAnalysis: String = resourceBundle.getString("analysis")

def deprecatedChosenStyleWarning: String = resourceBundle.getString("deprecatedChosenStyleWarning")

def rawDeprecatedChosenStyleWarning: String = resourceBundle.getString("deprecatedChosenStyleWarning")

def bigProblems(ex: Throwable): String = {
  val message = if (ex.getMessage == null) "" else ex.getMessage.trim
  if (message.length > 0) Resources.bigProblemsWithMessage(message) else Resources.bigProblems
}
}
    