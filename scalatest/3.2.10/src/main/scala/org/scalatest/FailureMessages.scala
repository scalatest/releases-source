package org.scalatest

private[scalatest] object FailureMessages {

def decorateToStringValue(prettifier: org.scalactic.Prettifier, o: Any): String = prettifier.apply(o)

def factExceptionWasThrown(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.factExceptionWasThrown(prettifier.apply(param0))

def factNoExceptionWasThrown: String = Resources.factNoExceptionWasThrown

def midSentenceFactExceptionWasThrown(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.midSentenceFactExceptionWasThrown(prettifier.apply(param0))

def midSentenceFactNoExceptionWasThrown: String = Resources.midSentenceFactNoExceptionWasThrown

def exceptionExpected(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.exceptionExpected(prettifier.apply(param0))

def expectedExceptionWasThrown(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.expectedExceptionWasThrown(prettifier.apply(param0))

def midSentenceExceptionExpected(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.midSentenceExceptionExpected(prettifier.apply(param0))

def midSentenceExpectedExceptionWasThrown(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.midSentenceExpectedExceptionWasThrown(prettifier.apply(param0))

def noExceptionWasThrown: String = Resources.noExceptionWasThrown

def resultWas(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.resultWas(prettifier.apply(param0))

def exceptionThrown(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.exceptionThrown(prettifier.apply(param0))

def didNotEqual(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotEqual(prettifier.apply(param0), prettifier.apply(param1))

def wrongException(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wrongException(prettifier.apply(param0), prettifier.apply(param1))

def midSentenceWrongException(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.midSentenceWrongException(prettifier.apply(param0), prettifier.apply(param1))

def anException(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.anException(prettifier.apply(param0))

def exceptionNotExpected(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.exceptionNotExpected(prettifier.apply(param0))

def expectedButGot(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.expectedButGot(prettifier.apply(param0), prettifier.apply(param1))

def expectedAndGot(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.expectedAndGot(prettifier.apply(param0), prettifier.apply(param1))

def midSentenceExpectedButGot(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.midSentenceExpectedButGot(prettifier.apply(param0), prettifier.apply(param1))

def midSentenceExpectedAndGot(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.midSentenceExpectedAndGot(prettifier.apply(param0), prettifier.apply(param1))

def conditionFalse: String = Resources.conditionFalse

def refNotNull: String = Resources.refNotNull

def refNull: String = Resources.refNull

def floatInfinite(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.floatInfinite(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def floatNaN(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.floatNaN(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def doubleInfinite(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.doubleInfinite(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def doubleNaN(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.doubleNaN(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def testEvent(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.testEvent(prettifier.apply(param0), prettifier.apply(param1))

def expressionFailed(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.expressionFailed(prettifier.apply(param0))

def testFailed(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testFailed(prettifier.apply(param0))

def testStarting(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testStarting(prettifier.apply(param0))

def testSucceeded(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testSucceeded(prettifier.apply(param0))

def testIgnored(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testIgnored(prettifier.apply(param0))

def testPending(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testPending(prettifier.apply(param0))

def testCanceled(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testCanceled(prettifier.apply(param0))

def suiteStarting(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.suiteStarting(prettifier.apply(param0))

def suiteCompleted(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.suiteCompleted(prettifier.apply(param0))

def suiteAborted(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.suiteAborted(prettifier.apply(param0))

def runAborted: String = Resources.runAborted

def infoProvided(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.infoProvided(prettifier.apply(param0))

def alertProvided(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.alertProvided(prettifier.apply(param0))

def noteProvided(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.noteProvided(prettifier.apply(param0))

def markupProvided(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.markupProvided(prettifier.apply(param0))

def scopeOpened(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.scopeOpened(prettifier.apply(param0))

def scopeClosed(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.scopeClosed(prettifier.apply(param0))

def scopePending(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.scopePending(prettifier.apply(param0))

def payloadToString(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.payloadToString(prettifier.apply(param0))

def noNameSpecified: String = Resources.noNameSpecified

def runStarting(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.runStarting(prettifier.apply(param0))

def rerunStarting(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.rerunStarting(prettifier.apply(param0))

def rerunCompleted(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.rerunCompleted(prettifier.apply(param0))

def rerunStopped(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.rerunStopped(prettifier.apply(param0))

def friendlyFailure: String = Resources.friendlyFailure

def showStackTraceOption: String = Resources.showStackTraceOption

def suitebeforeclass: String = Resources.suitebeforeclass

def reportTestsStarting: String = Resources.reportTestsStarting

def reportTestsSucceeded: String = Resources.reportTestsSucceeded

def reportTestsFailed: String = Resources.reportTestsFailed

def reportAlerts: String = Resources.reportAlerts

def reportInfo: String = Resources.reportInfo

def reportStackTraces: String = Resources.reportStackTraces

def reportRunStarting: String = Resources.reportRunStarting

def reportRunCompleted: String = Resources.reportRunCompleted

def reportSummary: String = Resources.reportSummary

def probarg(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.probarg(prettifier.apply(param0))

def errBuildingDispatchReporter: String = Resources.errBuildingDispatchReporter

def missingFileName: String = Resources.missingFileName

def missingReporterClassName: String = Resources.missingReporterClassName

def errParsingArgs: String = Resources.errParsingArgs

def invalidConfigOption(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.invalidConfigOption(prettifier.apply(param0))

def cantOpenFile: String = Resources.cantOpenFile

def reporterThrew(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.reporterThrew(prettifier.apply(param0))

def reporterDisposeThrew: String = Resources.reporterDisposeThrew

def slowpokeDetectorEventNotFound(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.slowpokeDetectorEventNotFound(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def suiteExecutionStarting: String = Resources.suiteExecutionStarting

def executeException: String = Resources.executeException

def executeExceptionWithMessage(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.executeExceptionWithMessage(prettifier.apply(param0))

def runOnSuiteException: String = Resources.runOnSuiteException

def runOnSuiteExceptionWithMessage(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.runOnSuiteExceptionWithMessage(prettifier.apply(param0))

def suiteCompletedNormally: String = Resources.suiteCompletedNormally

def notOneOfTheChosenStyles(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.notOneOfTheChosenStyles(prettifier.apply(param0), prettifier.apply(param1))

def notTheChosenStyle(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.notTheChosenStyle(prettifier.apply(param0), prettifier.apply(param1))

def Rerun: String = Resources.Rerun

def executeStopping: String = Resources.executeStopping

def illegalReporterArg(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.illegalReporterArg(prettifier.apply(param0))

def cantLoadReporterClass(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cantLoadReporterClass(prettifier.apply(param0))

def cantInstantiateReporter(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cantInstantiateReporter(prettifier.apply(param0))

def overwriteExistingFile(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.overwriteExistingFile(prettifier.apply(param0))

def cannotLoadSuite(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cannotLoadSuite(prettifier.apply(param0))

def cannotLoadDiscoveredSuite(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cannotLoadDiscoveredSuite(prettifier.apply(param0))

def nonSuite: String = Resources.nonSuite

def cannotInstantiateSuite(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cannotInstantiateSuite(prettifier.apply(param0))

def cannotLoadClass(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cannotLoadClass(prettifier.apply(param0))

def bigProblems: String = Resources.bigProblems

def bigProblemsWithMessage(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.bigProblemsWithMessage(prettifier.apply(param0))

def bigProblemsMaybeCustomReporter: String = Resources.bigProblemsMaybeCustomReporter

def cannotFindMethod(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cannotFindMethod(prettifier.apply(param0))

def securityWhenRerunning(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.securityWhenRerunning(prettifier.apply(param0))

def overwriteDialogTitle: String = Resources.overwriteDialogTitle

def openPrefs: String = Resources.openPrefs

def savePrefs: String = Resources.savePrefs

def runsFailures: String = Resources.runsFailures

def allEvents: String = Resources.allEvents

def needFileNameTitle: String = Resources.needFileNameTitle

def needFileNameMessage: String = Resources.needFileNameMessage

def needClassNameTitle: String = Resources.needClassNameTitle

def needClassNameMessage: String = Resources.needClassNameMessage

def NoSuitesFoundText: String = Resources.NoSuitesFoundText

def cantInvokeExceptionText: String = Resources.cantInvokeExceptionText

def multipleTestsFailed(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.multipleTestsFailed(prettifier.apply(param0))

def oneTestFailed: String = Resources.oneTestFailed

def oneSuiteAborted: String = Resources.oneSuiteAborted

def multipleSuitesAborted(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.multipleSuitesAborted(prettifier.apply(param0))

def allTestsPassed: String = Resources.allTestsPassed

def noTestsWereExecuted: String = Resources.noTestsWereExecuted

def eventsLabel: String = Resources.eventsLabel

def detailsLabel: String = Resources.detailsLabel

def testsRun: String = Resources.testsRun

def testsFailed: String = Resources.testsFailed

def testsExpected: String = Resources.testsExpected

def testsIgnored: String = Resources.testsIgnored

def testsPending: String = Resources.testsPending

def testsCanceled: String = Resources.testsCanceled

def ScalaTestTitle: String = Resources.ScalaTestTitle

def ScalaTestMenu: String = Resources.ScalaTestMenu

def Run: String = Resources.Run

def Stop: String = Resources.Stop

def Exit: String = Resources.Exit

def About: String = Resources.About

def AboutBoxTitle: String = Resources.AboutBoxTitle

def AppName: String = Resources.AppName

def AppCopyright: String = Resources.AppCopyright

def AppURL: String = Resources.AppURL

def Reason: String = Resources.Reason

def Trademarks: String = Resources.Trademarks

def ArtimaInc: String = Resources.ArtimaInc

def MoreInfo: String = Resources.MoreInfo

def ViewMenu: String = Resources.ViewMenu

def JavaSuiteRunnerFile: String = Resources.JavaSuiteRunnerFile

def JavaSuiteRunnerFileDescription: String = Resources.JavaSuiteRunnerFileDescription

def defaultConfiguration: String = Resources.defaultConfiguration

def reporterTypeLabel: String = Resources.reporterTypeLabel

def graphicReporterType: String = Resources.graphicReporterType

def customReporterType: String = Resources.customReporterType

def stdoutReporterType: String = Resources.stdoutReporterType

def stderrReporterType: String = Resources.stderrReporterType

def fileReporterType: String = Resources.fileReporterType

def reporterConfigLabel(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.reporterConfigLabel(prettifier.apply(param0))

def unusedField: String = Resources.unusedField

def couldntRun: String = Resources.couldntRun

def couldntRerun: String = Resources.couldntRerun

def MENU_PRESENT_DISCOVERY_STARTING: String = Resources.MENU_PRESENT_DISCOVERY_STARTING

def MENU_PRESENT_DISCOVERY_COMPLETED: String = Resources.MENU_PRESENT_DISCOVERY_COMPLETED

def MENU_PRESENT_RUN_STARTING: String = Resources.MENU_PRESENT_RUN_STARTING

def MENU_PRESENT_TEST_STARTING: String = Resources.MENU_PRESENT_TEST_STARTING

def MENU_PRESENT_TEST_FAILED: String = Resources.MENU_PRESENT_TEST_FAILED

def MENU_PRESENT_TEST_SUCCEEDED: String = Resources.MENU_PRESENT_TEST_SUCCEEDED

def MENU_PRESENT_TEST_IGNORED: String = Resources.MENU_PRESENT_TEST_IGNORED

def MENU_PRESENT_TEST_PENDING: String = Resources.MENU_PRESENT_TEST_PENDING

def MENU_PRESENT_TEST_CANCELED: String = Resources.MENU_PRESENT_TEST_CANCELED

def MENU_PRESENT_SUITE_STARTING: String = Resources.MENU_PRESENT_SUITE_STARTING

def MENU_PRESENT_SUITE_ABORTED: String = Resources.MENU_PRESENT_SUITE_ABORTED

def MENU_PRESENT_SUITE_COMPLETED: String = Resources.MENU_PRESENT_SUITE_COMPLETED

def MENU_PRESENT_INFO_PROVIDED: String = Resources.MENU_PRESENT_INFO_PROVIDED

def MENU_PRESENT_ALERT_PROVIDED: String = Resources.MENU_PRESENT_ALERT_PROVIDED

def MENU_PRESENT_NOTE_PROVIDED: String = Resources.MENU_PRESENT_NOTE_PROVIDED

def MENU_PRESENT_SCOPE_OPENED: String = Resources.MENU_PRESENT_SCOPE_OPENED

def MENU_PRESENT_SCOPE_CLOSED: String = Resources.MENU_PRESENT_SCOPE_CLOSED

def MENU_PRESENT_SCOPE_PENDING: String = Resources.MENU_PRESENT_SCOPE_PENDING

def MENU_PRESENT_MARKUP_PROVIDED: String = Resources.MENU_PRESENT_MARKUP_PROVIDED

def MENU_PRESENT_RUN_STOPPED: String = Resources.MENU_PRESENT_RUN_STOPPED

def MENU_PRESENT_RUN_ABORTED: String = Resources.MENU_PRESENT_RUN_ABORTED

def MENU_PRESENT_RUN_COMPLETED: String = Resources.MENU_PRESENT_RUN_COMPLETED

def RUN_STARTING: String = Resources.RUN_STARTING

def TEST_STARTING: String = Resources.TEST_STARTING

def TEST_FAILED: String = Resources.TEST_FAILED

def TEST_SUCCEEDED: String = Resources.TEST_SUCCEEDED

def TEST_IGNORED: String = Resources.TEST_IGNORED

def TEST_PENDING: String = Resources.TEST_PENDING

def TEST_CANCELED: String = Resources.TEST_CANCELED

def SUITE_STARTING: String = Resources.SUITE_STARTING

def SUITE_ABORTED: String = Resources.SUITE_ABORTED

def SUITE_COMPLETED: String = Resources.SUITE_COMPLETED

def INFO_PROVIDED: String = Resources.INFO_PROVIDED

def ALERT_PROVIDED: String = Resources.ALERT_PROVIDED

def NOTE_PROVIDED: String = Resources.NOTE_PROVIDED

def SCOPE_OPENED: String = Resources.SCOPE_OPENED

def SCOPE_CLOSED: String = Resources.SCOPE_CLOSED

def SCOPE_PENDING: String = Resources.SCOPE_PENDING

def MARKUP_PROVIDED: String = Resources.MARKUP_PROVIDED

def RUN_STOPPED: String = Resources.RUN_STOPPED

def RUN_ABORTED: String = Resources.RUN_ABORTED

def RUN_COMPLETED: String = Resources.RUN_COMPLETED

def DISCOVERY_STARTING: String = Resources.DISCOVERY_STARTING

def DISCOVERY_COMPLETED: String = Resources.DISCOVERY_COMPLETED

def RERUN_DISCOVERY_STARTING: String = Resources.RERUN_DISCOVERY_STARTING

def RERUN_DISCOVERY_COMPLETED: String = Resources.RERUN_DISCOVERY_COMPLETED

def RERUN_RUN_STARTING: String = Resources.RERUN_RUN_STARTING

def RERUN_TEST_STARTING: String = Resources.RERUN_TEST_STARTING

def RERUN_TEST_FAILED: String = Resources.RERUN_TEST_FAILED

def RERUN_TEST_SUCCEEDED: String = Resources.RERUN_TEST_SUCCEEDED

def RERUN_TEST_IGNORED: String = Resources.RERUN_TEST_IGNORED

def RERUN_TEST_PENDING: String = Resources.RERUN_TEST_PENDING

def RERUN_TEST_CANCELED: String = Resources.RERUN_TEST_CANCELED

def RERUN_SUITE_STARTING: String = Resources.RERUN_SUITE_STARTING

def RERUN_SUITE_ABORTED: String = Resources.RERUN_SUITE_ABORTED

def RERUN_SUITE_COMPLETED: String = Resources.RERUN_SUITE_COMPLETED

def RERUN_INFO_PROVIDED: String = Resources.RERUN_INFO_PROVIDED

def RERUN_ALERT_PROVIDED: String = Resources.RERUN_ALERT_PROVIDED

def RERUN_NOTE_PROVIDED: String = Resources.RERUN_NOTE_PROVIDED

def RERUN_MARKUP_PROVIDED: String = Resources.RERUN_MARKUP_PROVIDED

def RERUN_RUN_STOPPED: String = Resources.RERUN_RUN_STOPPED

def RERUN_RUN_ABORTED: String = Resources.RERUN_RUN_ABORTED

def RERUN_RUN_COMPLETED: String = Resources.RERUN_RUN_COMPLETED

def RERUN_SCOPE_OPENED: String = Resources.RERUN_SCOPE_OPENED

def RERUN_SCOPE_CLOSED: String = Resources.RERUN_SCOPE_CLOSED

def RERUN_SCOPE_PENDING: String = Resources.RERUN_SCOPE_PENDING

def DetailsEvent: String = Resources.DetailsEvent

def DetailsSuiteId: String = Resources.DetailsSuiteId

def DetailsName: String = Resources.DetailsName

def DetailsMessage: String = Resources.DetailsMessage

def LineNumber: String = Resources.LineNumber

def DetailsDate: String = Resources.DetailsDate

def DetailsThread: String = Resources.DetailsThread

def DetailsThrowable: String = Resources.DetailsThrowable

def DetailsCause: String = Resources.DetailsCause

def None: String = Resources.None

def DetailsDuration: String = Resources.DetailsDuration

def DetailsSummary: String = Resources.DetailsSummary

def should(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.should(prettifier.apply(param0))

def itShould(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.itShould(prettifier.apply(param0))

def prefixSuffix(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.prefixSuffix(prettifier.apply(param0), prettifier.apply(param1))

def prefixShouldSuffix(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.prefixShouldSuffix(prettifier.apply(param0), prettifier.apply(param1))

def testSucceededIconChar: String = Resources.testSucceededIconChar

def testFailedIconChar: String = Resources.testFailedIconChar

def iconPlusShortName(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.iconPlusShortName(prettifier.apply(param0), prettifier.apply(param1))

def iconPlusShortNameAndNote(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.iconPlusShortNameAndNote(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def infoProvidedIconChar: String = Resources.infoProvidedIconChar

def markupProvidedIconChar: String = Resources.markupProvidedIconChar

def failedNote: String = Resources.failedNote

def abortedNote: String = Resources.abortedNote

def specTextAndNote(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.specTextAndNote(prettifier.apply(param0), prettifier.apply(param1))

def ignoredNote: String = Resources.ignoredNote

def pendingNote: String = Resources.pendingNote

def canceledNote: String = Resources.canceledNote

def infoProvidedNote: String = Resources.infoProvidedNote

def alertProvidedNote: String = Resources.alertProvidedNote

def noteProvidedNote: String = Resources.noteProvidedNote

def scopeOpenedNote: String = Resources.scopeOpenedNote

def scopeClosedNote: String = Resources.scopeClosedNote

def givenMessage(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.givenMessage(prettifier.apply(param0))

def whenMessage(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.whenMessage(prettifier.apply(param0))

def thenMessage(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.thenMessage(prettifier.apply(param0))

def andMessage(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.andMessage(prettifier.apply(param0))

def scenario(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.scenario(prettifier.apply(param0))

def commaBut(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.commaBut(prettifier.apply(param0), prettifier.apply(param1))

def commaAnd(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.commaAnd(prettifier.apply(param0), prettifier.apply(param1))

def commaDoubleAmpersand(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.commaDoubleAmpersand(prettifier.apply(param0), prettifier.apply(param1))

def commaDoublePipe(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.commaDoublePipe(prettifier.apply(param0), prettifier.apply(param1))

def unaryBang(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.unaryBang(prettifier.apply(param0))

def equaled(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.equaled(prettifier.apply(param0), prettifier.apply(param1))

def was(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.was(prettifier.apply(param0), prettifier.apply(param1))

def wasNot(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNot(prettifier.apply(param0), prettifier.apply(param1))

def wasA(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasA(prettifier.apply(param0), prettifier.apply(param1))

def wasNotA(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotA(prettifier.apply(param0), prettifier.apply(param1))

def wasAn(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasAn(prettifier.apply(param0), prettifier.apply(param1))

def wasNotAn(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotAn(prettifier.apply(param0), prettifier.apply(param1))

def wasDefinedAt(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasDefinedAt(prettifier.apply(param0), prettifier.apply(param1))

def wasNotDefinedAt(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotDefinedAt(prettifier.apply(param0), prettifier.apply(param1))

def equaledPlusOrMinus(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.equaledPlusOrMinus(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def didNotEqualPlusOrMinus(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.didNotEqualPlusOrMinus(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def wasPlusOrMinus(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.wasPlusOrMinus(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def wasNotPlusOrMinus(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.wasNotPlusOrMinus(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def wasLessThan(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasLessThan(prettifier.apply(param0), prettifier.apply(param1))

def wasNotLessThan(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotLessThan(prettifier.apply(param0), prettifier.apply(param1))

def wasGreaterThan(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasGreaterThan(prettifier.apply(param0), prettifier.apply(param1))

def wasNotGreaterThan(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotGreaterThan(prettifier.apply(param0), prettifier.apply(param1))

def wasLessThanOrEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasLessThanOrEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def wasNotLessThanOrEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotLessThanOrEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def wasGreaterThanOrEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasGreaterThanOrEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def wasNotGreaterThanOrEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotGreaterThanOrEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def wasSameInstanceAs(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasSameInstanceAs(prettifier.apply(param0), prettifier.apply(param1))

def wasNotSameInstanceAs(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotSameInstanceAs(prettifier.apply(param0), prettifier.apply(param1))

def booleanExpressionWas(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.booleanExpressionWas(prettifier.apply(param0))

def booleanExpressionWasNot(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.booleanExpressionWasNot(prettifier.apply(param0))

def wasAnInstanceOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasAnInstanceOf(prettifier.apply(param0), prettifier.apply(param1))

def wasNotAnInstanceOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.wasNotAnInstanceOf(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def wasEmpty(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasEmpty(prettifier.apply(param0))

def wasNotEmpty(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNotEmpty(prettifier.apply(param0))

def wasNull: String = Resources.wasNull

def midSentenceWasNull: String = Resources.midSentenceWasNull

def wasNotNull(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNotNull(prettifier.apply(param0))

def equaledNull: String = Resources.equaledNull

def midSentenceEqualedNull: String = Resources.midSentenceEqualedNull

def didNotEqualNull(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.didNotEqualNull(prettifier.apply(param0))

def wasNone(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNone(prettifier.apply(param0))

def wasNotNone(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNotNone(prettifier.apply(param0))

def wasNil(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNil(prettifier.apply(param0))

def wasNotNil(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNotNil(prettifier.apply(param0))

def wasSome(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasSome(prettifier.apply(param0), prettifier.apply(param1))

def wasNotSome(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotSome(prettifier.apply(param0), prettifier.apply(param1))

def hasNeitherAOrAnMethod(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hasNeitherAOrAnMethod(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def hasNeitherAnOrAnMethod(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hasNeitherAnOrAnMethod(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def hasBothAAndAnMethod(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hasBothAAndAnMethod(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def hasBothAnAndAnMethod(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hasBothAnAndAnMethod(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def didNotEndWith(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotEndWith(prettifier.apply(param0), prettifier.apply(param1))

def endedWith(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.endedWith(prettifier.apply(param0), prettifier.apply(param1))

def didNotStartWith(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotStartWith(prettifier.apply(param0), prettifier.apply(param1))

def startedWith(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.startedWith(prettifier.apply(param0), prettifier.apply(param1))

def didNotStartWithRegex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotStartWithRegex(prettifier.apply(param0), prettifier.apply(param1))

def startedWithRegex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.startedWithRegex(prettifier.apply(param0), prettifier.apply(param1))

def startedWithRegexButNotGroup(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.startedWithRegexButNotGroup(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def startedWithRegexButNotGroupAtIndex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = Resources.startedWithRegexButNotGroupAtIndex(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4))

def startedWithRegexAndGroup(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.startedWithRegexAndGroup(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def didNotEndWithRegex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotEndWithRegex(prettifier.apply(param0), prettifier.apply(param1))

def endedWithRegex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.endedWithRegex(prettifier.apply(param0), prettifier.apply(param1))

def endedWithRegexButNotGroup(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.endedWithRegexButNotGroup(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def endedWithRegexButNotGroupAtIndex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = Resources.endedWithRegexButNotGroupAtIndex(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4))

def endedWithRegexAndGroup(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.endedWithRegexAndGroup(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def didNotContainNull(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.didNotContainNull(prettifier.apply(param0))

def containedNull(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.containedNull(prettifier.apply(param0))

def didNotContainKey(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainKey(prettifier.apply(param0), prettifier.apply(param1))

def containedKey(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedKey(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainValue(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainValue(prettifier.apply(param0), prettifier.apply(param1))

def containedValue(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedValue(prettifier.apply(param0), prettifier.apply(param1))

def hadSizeInsteadOfExpectedSize(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hadSizeInsteadOfExpectedSize(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def hadSize(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.hadSize(prettifier.apply(param0), prettifier.apply(param1))

def hadMessageInsteadOfExpectedMessage(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hadMessageInsteadOfExpectedMessage(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def hadExpectedMessage(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.hadExpectedMessage(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainExpectedElement(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainExpectedElement(prettifier.apply(param0), prettifier.apply(param1))

def containedExpectedElement(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedExpectedElement(prettifier.apply(param0), prettifier.apply(param1))

def didNotIncludeSubstring(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotIncludeSubstring(prettifier.apply(param0), prettifier.apply(param1))

def includedSubstring(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.includedSubstring(prettifier.apply(param0), prettifier.apply(param1))

def didNotIncludeRegex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotIncludeRegex(prettifier.apply(param0), prettifier.apply(param1))

def includedRegex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.includedRegex(prettifier.apply(param0), prettifier.apply(param1))

def includedRegexButNotGroup(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.includedRegexButNotGroup(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def includedRegexButNotGroupAtIndex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = Resources.includedRegexButNotGroupAtIndex(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4))

def includedRegexAndGroup(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.includedRegexAndGroup(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def hadLengthInsteadOfExpectedLength(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hadLengthInsteadOfExpectedLength(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def hadLength(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.hadLength(prettifier.apply(param0), prettifier.apply(param1))

def didNotFullyMatchRegex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotFullyMatchRegex(prettifier.apply(param0), prettifier.apply(param1))

def fullyMatchedRegex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.fullyMatchedRegex(prettifier.apply(param0), prettifier.apply(param1))

def fullyMatchedRegexButNotGroup(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.fullyMatchedRegexButNotGroup(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def fullyMatchedRegexButNotGroupAtIndex(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = Resources.fullyMatchedRegexButNotGroupAtIndex(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4))

def fullyMatchedRegexAndGroup(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.fullyMatchedRegexAndGroup(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def matchResultedInFalse(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.matchResultedInFalse(prettifier.apply(param0))

def didNotMatch(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.didNotMatch(prettifier.apply(param0))

def matchResultedInTrue(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.matchResultedInTrue(prettifier.apply(param0))

def noLengthStructure(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.noLengthStructure(prettifier.apply(param0))

def noSizeStructure(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.noSizeStructure(prettifier.apply(param0))

def sizeAndGetSize(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.sizeAndGetSize(prettifier.apply(param0))

def negativeOrZeroRange(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.negativeOrZeroRange(prettifier.apply(param0))

def didNotContainSameElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainSameElements(prettifier.apply(param0), prettifier.apply(param1))

def containedSameElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedSameElements(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainSameElementsInOrder(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainSameElementsInOrder(prettifier.apply(param0), prettifier.apply(param1))

def containedSameElementsInOrder(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedSameElementsInOrder(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainAllOfElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainAllOfElements(prettifier.apply(param0), prettifier.apply(param1))

def containedAllOfElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedAllOfElements(prettifier.apply(param0), prettifier.apply(param1))

def allOfDuplicate: String = Resources.allOfDuplicate

def didNotContainAllElementsOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainAllElementsOf(prettifier.apply(param0), prettifier.apply(param1))

def containedAllElementsOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedAllElementsOf(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainAllOfElementsInOrder(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainAllOfElementsInOrder(prettifier.apply(param0), prettifier.apply(param1))

def containedAllOfElementsInOrder(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedAllOfElementsInOrder(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainAllElementsOfInOrder(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainAllElementsOfInOrder(prettifier.apply(param0), prettifier.apply(param1))

def containedAllElementsOfInOrder(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedAllElementsOfInOrder(prettifier.apply(param0), prettifier.apply(param1))

def inOrderDuplicate: String = Resources.inOrderDuplicate

def didNotContainOneOfElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainOneOfElements(prettifier.apply(param0), prettifier.apply(param1))

def containedOneOfElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedOneOfElements(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainOneElementOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainOneElementOf(prettifier.apply(param0), prettifier.apply(param1))

def containedOneElementOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedOneElementOf(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainAtLeastOneOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainAtLeastOneOf(prettifier.apply(param0), prettifier.apply(param1))

def containedAtLeastOneOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedAtLeastOneOf(prettifier.apply(param0), prettifier.apply(param1))

def atLeastOneOfDuplicate: String = Resources.atLeastOneOfDuplicate

def didNotContainAtLeastOneElementOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainAtLeastOneElementOf(prettifier.apply(param0), prettifier.apply(param1))

def containedAtLeastOneElementOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedAtLeastOneElementOf(prettifier.apply(param0), prettifier.apply(param1))

def oneOfDuplicate: String = Resources.oneOfDuplicate

def didNotContainOnlyElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainOnlyElements(prettifier.apply(param0), prettifier.apply(param1))

def containedOnlyElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedOnlyElements(prettifier.apply(param0), prettifier.apply(param1))

def didNotContainOnlyElementsWithFriendlyReminder(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainOnlyElementsWithFriendlyReminder(prettifier.apply(param0), prettifier.apply(param1))

def containedOnlyElementsWithFriendlyReminder(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedOnlyElementsWithFriendlyReminder(prettifier.apply(param0), prettifier.apply(param1))

def onlyDuplicate: String = Resources.onlyDuplicate

def onlyEmpty: String = Resources.onlyEmpty

def didNotContainInOrderOnlyElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainInOrderOnlyElements(prettifier.apply(param0), prettifier.apply(param1))

def containedInOrderOnlyElements(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedInOrderOnlyElements(prettifier.apply(param0), prettifier.apply(param1))

def inOrderOnlyDuplicate: String = Resources.inOrderOnlyDuplicate

def atMostOneOfDuplicate: String = Resources.atMostOneOfDuplicate

def didNotContainAtMostOneOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainAtMostOneOf(prettifier.apply(param0), prettifier.apply(param1))

def containedAtMostOneOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedAtMostOneOf(prettifier.apply(param0), prettifier.apply(param1))

def atMostOneElementOfDuplicate: String = Resources.atMostOneElementOfDuplicate

def didNotContainAtMostOneElementOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainAtMostOneElementOf(prettifier.apply(param0), prettifier.apply(param1))

def containedAtMostOneElementOf(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.containedAtMostOneElementOf(prettifier.apply(param0), prettifier.apply(param1))

def noneOfDuplicate: String = Resources.noneOfDuplicate

def didNotContainA(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainA(prettifier.apply(param0), prettifier.apply(param1))

def containedA(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.containedA(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def didNotContainAn(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotContainAn(prettifier.apply(param0), prettifier.apply(param1))

def containedAn(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.containedAn(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def wasNotSorted(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNotSorted(prettifier.apply(param0))

def wasSorted(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasSorted(prettifier.apply(param0))

def wasNotDefined(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNotDefined(prettifier.apply(param0))

def wasDefined(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasDefined(prettifier.apply(param0))

def doesNotExist(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.doesNotExist(prettifier.apply(param0))

def exists(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.exists(prettifier.apply(param0))

def wasNotReadable(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNotReadable(prettifier.apply(param0))

def wasReadable(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasReadable(prettifier.apply(param0))

def wasNotWritable(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNotWritable(prettifier.apply(param0))

def wasWritable(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasWritable(prettifier.apply(param0))

def didNotMatchTheGivenPattern(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.didNotMatchTheGivenPattern(prettifier.apply(param0))

def matchedTheGivenPattern(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.matchedTheGivenPattern(prettifier.apply(param0))

def duplicateTestName(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.duplicateTestName(prettifier.apply(param0))

def cantNestFeatureClauses: String = Resources.cantNestFeatureClauses

def itCannotAppearInsideAnotherIt: String = Resources.itCannotAppearInsideAnotherIt

def itCannotAppearInsideAnotherItOrThey: String = Resources.itCannotAppearInsideAnotherItOrThey

def theyCannotAppearInsideAnotherItOrThey: String = Resources.theyCannotAppearInsideAnotherItOrThey

def describeCannotAppearInsideAnIt: String = Resources.describeCannotAppearInsideAnIt

def ignoreCannotAppearInsideAnIt: String = Resources.ignoreCannotAppearInsideAnIt

def ignoreCannotAppearInsideAnItOrAThey: String = Resources.ignoreCannotAppearInsideAnItOrAThey

def scenarioCannotAppearInsideAnotherScenario: String = Resources.scenarioCannotAppearInsideAnotherScenario

def featureCannotAppearInsideAScenario: String = Resources.featureCannotAppearInsideAScenario

def ignoreCannotAppearInsideAScenario: String = Resources.ignoreCannotAppearInsideAScenario

def testCannotAppearInsideAnotherTest: String = Resources.testCannotAppearInsideAnotherTest

def propertyCannotAppearInsideAnotherProperty: String = Resources.propertyCannotAppearInsideAnotherProperty

def ignoreCannotAppearInsideATest: String = Resources.ignoreCannotAppearInsideATest

def ignoreCannotAppearInsideAProperty: String = Resources.ignoreCannotAppearInsideAProperty

def shouldCannotAppearInsideAnIn: String = Resources.shouldCannotAppearInsideAnIn

def mustCannotAppearInsideAnIn: String = Resources.mustCannotAppearInsideAnIn

def whenCannotAppearInsideAnIn: String = Resources.whenCannotAppearInsideAnIn

def thatCannotAppearInsideAnIn: String = Resources.thatCannotAppearInsideAnIn

def whichCannotAppearInsideAnIn: String = Resources.whichCannotAppearInsideAnIn

def canCannotAppearInsideAnIn: String = Resources.canCannotAppearInsideAnIn

def behaviorOfCannotAppearInsideAnIn: String = Resources.behaviorOfCannotAppearInsideAnIn

def dashCannotAppearInsideAnIn: String = Resources.dashCannotAppearInsideAnIn

def inCannotAppearInsideAnotherIn: String = Resources.inCannotAppearInsideAnotherIn

def inCannotAppearInsideAnotherInOrIs: String = Resources.inCannotAppearInsideAnotherInOrIs

def isCannotAppearInsideAnotherInOrIs: String = Resources.isCannotAppearInsideAnotherInOrIs

def ignoreCannotAppearInsideAnIn: String = Resources.ignoreCannotAppearInsideAnIn

def ignoreCannotAppearInsideAnInOrAnIs: String = Resources.ignoreCannotAppearInsideAnInOrAnIs

def registrationAlreadyClosed: String = Resources.registrationAlreadyClosed

def itMustAppearAfterTopLevelSubject: String = Resources.itMustAppearAfterTopLevelSubject

def theyMustAppearAfterTopLevelSubject: String = Resources.theyMustAppearAfterTopLevelSubject

def allPropertiesHadExpectedValues(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.allPropertiesHadExpectedValues(prettifier.apply(param0))

def midSentenceAllPropertiesHadExpectedValues(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.midSentenceAllPropertiesHadExpectedValues(prettifier.apply(param0))

def propertyHadExpectedValue(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.propertyHadExpectedValue(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def midSentencePropertyHadExpectedValue(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.midSentencePropertyHadExpectedValue(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def propertyDidNotHaveExpectedValue(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.propertyDidNotHaveExpectedValue(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def midSentencePropertyDidNotHaveExpectedValue(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.midSentencePropertyDidNotHaveExpectedValue(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def propertyNotFound(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.propertyNotFound(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def propertyCheckSucceeded: String = Resources.propertyCheckSucceeded

def lengthPropertyNotAnInteger: String = Resources.lengthPropertyNotAnInteger

def sizePropertyNotAnInteger: String = Resources.sizePropertyNotAnInteger

def wasEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def wasNotEqualTo(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.wasNotEqualTo(prettifier.apply(param0), prettifier.apply(param1))

def printedReportPlusLineNumber(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.printedReportPlusLineNumber(prettifier.apply(param0), prettifier.apply(param1))

def printedReportPlusPath(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.printedReportPlusPath(prettifier.apply(param0), prettifier.apply(param1))

def propertyFailed(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.propertyFailed(prettifier.apply(param0))

def propertyExhausted(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.propertyExhausted(prettifier.apply(param0), prettifier.apply(param1))

def undecoratedPropertyCheckFailureMessage: String = Resources.undecoratedPropertyCheckFailureMessage

def propertyException(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.propertyException(prettifier.apply(param0))

def generatorException(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.generatorException(prettifier.apply(param0))

def thrownExceptionsMessage(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.thrownExceptionsMessage(prettifier.apply(param0))

def thrownExceptionsLocation(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.thrownExceptionsLocation(prettifier.apply(param0))

def propCheckExhausted(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.propCheckExhausted(prettifier.apply(param0), prettifier.apply(param1))

def propCheckExhaustedAfterOne(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.propCheckExhaustedAfterOne(prettifier.apply(param0))

def occurredAtRow(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.occurredAtRow(prettifier.apply(param0))

def occurredOnValues: String = Resources.occurredOnValues

def propCheckLabel: String = Resources.propCheckLabel

def propCheckLabels: String = Resources.propCheckLabels

def suiteAndTestNamesFormattedForDisplay(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.suiteAndTestNamesFormattedForDisplay(prettifier.apply(param0), prettifier.apply(param1))

def initSeed(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.initSeed(prettifier.apply(param0))

def notLoneElement(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.notLoneElement(prettifier.apply(param0), prettifier.apply(param1))

def testSummary(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = Resources.testSummary(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4))

def suiteSummary(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.suiteSummary(prettifier.apply(param0), prettifier.apply(param1))

def suiteScopeSummary(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.suiteScopeSummary(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def runCompletedIn(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.runCompletedIn(prettifier.apply(param0))

def runCompleted: String = Resources.runCompleted

def runAbortedIn(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.runAbortedIn(prettifier.apply(param0))

def runStoppedIn(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.runStoppedIn(prettifier.apply(param0))

def runStopped: String = Resources.runStopped

def totalNumberOfTestsRun(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.totalNumberOfTestsRun(prettifier.apply(param0))

def oneMillisecond: String = Resources.oneMillisecond

def milliseconds(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.milliseconds(prettifier.apply(param0))

def oneSecond: String = Resources.oneSecond

def oneSecondOneMillisecond: String = Resources.oneSecondOneMillisecond

def oneSecondMilliseconds(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.oneSecondMilliseconds(prettifier.apply(param0))

def seconds(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.seconds(prettifier.apply(param0))

def secondsMilliseconds(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.secondsMilliseconds(prettifier.apply(param0), prettifier.apply(param1))

def oneMinute: String = Resources.oneMinute

def oneMinuteOneSecond: String = Resources.oneMinuteOneSecond

def oneMinuteSeconds(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.oneMinuteSeconds(prettifier.apply(param0))

def minutes(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.minutes(prettifier.apply(param0))

def minutesOneSecond(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.minutesOneSecond(prettifier.apply(param0))

def minutesSeconds(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.minutesSeconds(prettifier.apply(param0), prettifier.apply(param1))

def oneHour: String = Resources.oneHour

def oneHourOneSecond: String = Resources.oneHourOneSecond

def oneHourSeconds(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.oneHourSeconds(prettifier.apply(param0))

def oneHourOneMinute: String = Resources.oneHourOneMinute

def oneHourOneMinuteOneSecond: String = Resources.oneHourOneMinuteOneSecond

def oneHourOneMinuteSeconds(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.oneHourOneMinuteSeconds(prettifier.apply(param0))

def oneHourMinutes(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.oneHourMinutes(prettifier.apply(param0))

def oneHourMinutesOneSecond(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.oneHourMinutesOneSecond(prettifier.apply(param0))

def oneHourMinutesSeconds(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.oneHourMinutesSeconds(prettifier.apply(param0), prettifier.apply(param1))

def hours(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.hours(prettifier.apply(param0))

def hoursOneSecond(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.hoursOneSecond(prettifier.apply(param0))

def hoursSeconds(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.hoursSeconds(prettifier.apply(param0), prettifier.apply(param1))

def hoursOneMinute(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.hoursOneMinute(prettifier.apply(param0))

def hoursOneMinuteOneSecond(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.hoursOneMinuteOneSecond(prettifier.apply(param0))

def hoursOneMinuteSeconds(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.hoursOneMinuteSeconds(prettifier.apply(param0), prettifier.apply(param1))

def hoursMinutes(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.hoursMinutes(prettifier.apply(param0), prettifier.apply(param1))

def hoursMinutesOneSecond(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.hoursMinutesOneSecond(prettifier.apply(param0), prettifier.apply(param1))

def hoursMinutesSeconds(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.hoursMinutesSeconds(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def withDuration(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.withDuration(prettifier.apply(param0), prettifier.apply(param1))

def feature(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.feature(prettifier.apply(param0))

def needFixtureInTestName(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.needFixtureInTestName(prettifier.apply(param0))

def testNotFound(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testNotFound(prettifier.apply(param0))

def pendingUntilFixed: String = Resources.pendingUntilFixed

def dashXDeprecated: String = Resources.dashXDeprecated

def threadCalledAfterConductingHasCompleted: String = Resources.threadCalledAfterConductingHasCompleted

def cannotInvokeWhenFinishedAfterConduct: String = Resources.cannotInvokeWhenFinishedAfterConduct

def cantRegisterThreadsWithSameName(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cantRegisterThreadsWithSameName(prettifier.apply(param0))

def cannotCallConductTwice: String = Resources.cannotCallConductTwice

def cannotWaitForBeatZero: String = Resources.cannotWaitForBeatZero

def cannotWaitForNegativeBeat: String = Resources.cannotWaitForNegativeBeat

def cannotPassNonPositiveClockPeriod(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cannotPassNonPositiveClockPeriod(prettifier.apply(param0))

def cannotPassNonPositiveTimeout(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cannotPassNonPositiveTimeout(prettifier.apply(param0))

def whenFinishedCanOnlyBeCalledByMainThread: String = Resources.whenFinishedCanOnlyBeCalledByMainThread

def suspectedDeadlock(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.suspectedDeadlock(prettifier.apply(param0), prettifier.apply(param1))

def testTimedOut(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testTimedOut(prettifier.apply(param0))

def suspectedDeadlockDEPRECATED(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.suspectedDeadlockDEPRECATED(prettifier.apply(param0), prettifier.apply(param1))

def testTimedOutDEPRECATED(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testTimedOutDEPRECATED(prettifier.apply(param0))

def concurrentInformerMod(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.concurrentInformerMod(prettifier.apply(param0))

def concurrentNotifierMod(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.concurrentNotifierMod(prettifier.apply(param0))

def concurrentAlerterMod(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.concurrentAlerterMod(prettifier.apply(param0))

def concurrentDocumenterMod(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.concurrentDocumenterMod(prettifier.apply(param0))

def cantCallInfoNow(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cantCallInfoNow(prettifier.apply(param0))

def cantCallMarkupNow(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.cantCallMarkupNow(prettifier.apply(param0))

def concurrentFunSuiteMod: String = Resources.concurrentFunSuiteMod

def concurrentPropSpecMod: String = Resources.concurrentPropSpecMod

def concurrentFixtureFunSuiteMod: String = Resources.concurrentFixtureFunSuiteMod

def concurrentFixturePropSpecMod: String = Resources.concurrentFixturePropSpecMod

def concurrentSpecMod: String = Resources.concurrentSpecMod

def concurrentFreeSpecMod: String = Resources.concurrentFreeSpecMod

def concurrentFixtureSpecMod: String = Resources.concurrentFixtureSpecMod

def concurrentFlatSpecMod: String = Resources.concurrentFlatSpecMod

def concurrentFixtureFlatSpecMod: String = Resources.concurrentFixtureFlatSpecMod

def concurrentWordSpecMod: String = Resources.concurrentWordSpecMod

def concurrentFixtureWordSpecMod: String = Resources.concurrentFixtureWordSpecMod

def concurrentFixtureFreeSpecMod: String = Resources.concurrentFixtureFreeSpecMod

def concurrentFeatureSpecMod: String = Resources.concurrentFeatureSpecMod

def concurrentFixtureFeatureSpecMod: String = Resources.concurrentFixtureFeatureSpecMod

def concurrentDocSpecMod: String = Resources.concurrentDocSpecMod

def tryNotAFailure(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.tryNotAFailure(prettifier.apply(param0))

def tryNotASuccess(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.tryNotASuccess(prettifier.apply(param0))

def optionValueNotDefined: String = Resources.optionValueNotDefined

def eitherLeftValueNotDefined(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.eitherLeftValueNotDefined(prettifier.apply(param0))

def eitherRightValueNotDefined(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.eitherRightValueNotDefined(prettifier.apply(param0))

def eitherValueNotDefined(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.eitherValueNotDefined(prettifier.apply(param0))

def partialFunctionValueNotDefined(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.partialFunctionValueNotDefined(prettifier.apply(param0))

def insidePartialFunctionNotDefined(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.insidePartialFunctionNotDefined(prettifier.apply(param0))

def insidePartialFunctionAppendSomeMsg(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.insidePartialFunctionAppendSomeMsg(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def insidePartialFunctionAppendNone(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.insidePartialFunctionAppendNone(prettifier.apply(param0), prettifier.apply(param1))

def didNotEventuallySucceed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotEventuallySucceed(prettifier.apply(param0), prettifier.apply(param1))

def didNotEventuallySucceedBecause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.didNotEventuallySucceedBecause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def didNotUltimatelySucceed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.didNotUltimatelySucceed(prettifier.apply(param0), prettifier.apply(param1))

def didNotUltimatelySucceedBecause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.didNotUltimatelySucceedBecause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def wasNeverReady(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.wasNeverReady(prettifier.apply(param0))

def awaitMustBeCalledOnCreatingThread: String = Resources.awaitMustBeCalledOnCreatingThread

def awaitTimedOut: String = Resources.awaitTimedOut

def futureReturnedAnException(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.futureReturnedAnException(prettifier.apply(param0))

def futureReturnedAnExceptionWithMessage(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.futureReturnedAnExceptionWithMessage(prettifier.apply(param0), prettifier.apply(param1))

def futureWasCanceled: String = Resources.futureWasCanceled

def futureExpired(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.futureExpired(prettifier.apply(param0), prettifier.apply(param1))

def timeoutFailedAfter(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.timeoutFailedAfter(prettifier.apply(param0))

def timeoutFailingAfter(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.timeoutFailingAfter(prettifier.apply(param0))

def timeoutCanceledAfter(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.timeoutCanceledAfter(prettifier.apply(param0))

def timeoutCancelingAfter(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.timeoutCancelingAfter(prettifier.apply(param0))

def testTimeLimitExceeded(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.testTimeLimitExceeded(prettifier.apply(param0))

def singularNanosecondUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.singularNanosecondUnits(prettifier.apply(param0))

def pluralNanosecondUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.pluralNanosecondUnits(prettifier.apply(param0))

def singularMicrosecondUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.singularMicrosecondUnits(prettifier.apply(param0))

def pluralMicrosecondUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.pluralMicrosecondUnits(prettifier.apply(param0))

def singularMillisecondUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.singularMillisecondUnits(prettifier.apply(param0))

def pluralMillisecondUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.pluralMillisecondUnits(prettifier.apply(param0))

def singularSecondUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.singularSecondUnits(prettifier.apply(param0))

def pluralSecondUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.pluralSecondUnits(prettifier.apply(param0))

def singularMinuteUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.singularMinuteUnits(prettifier.apply(param0))

def pluralMinuteUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.pluralMinuteUnits(prettifier.apply(param0))

def singularHourUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.singularHourUnits(prettifier.apply(param0))

def pluralHourUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.pluralHourUnits(prettifier.apply(param0))

def singularDayUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.singularDayUnits(prettifier.apply(param0))

def pluralDayUnits(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.pluralDayUnits(prettifier.apply(param0))

def leftAndRight(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.leftAndRight(prettifier.apply(param0), prettifier.apply(param1))

def leftCommaAndRight(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.leftCommaAndRight(prettifier.apply(param0), prettifier.apply(param1))

def configMapEntryNotFound(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.configMapEntryNotFound(prettifier.apply(param0))

def configMapEntryHadUnexpectedType(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.configMapEntryHadUnexpectedType(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def forAssertionsMoreThanZero(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.forAssertionsMoreThanZero(prettifier.apply(param0))

def forAssertionsMoreThanEqualZero(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.forAssertionsMoreThanEqualZero(prettifier.apply(param0))

def forAssertionsMoreThan(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.forAssertionsMoreThan(prettifier.apply(param0), prettifier.apply(param1))

def forAssertionsGenTraversableMessageWithStackDepth(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.forAssertionsGenTraversableMessageWithStackDepth(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def forAssertionsGenTraversableMessageWithoutStackDepth(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.forAssertionsGenTraversableMessageWithoutStackDepth(prettifier.apply(param0), prettifier.apply(param1))

def forAssertionsGenMapMessageWithStackDepth(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.forAssertionsGenMapMessageWithStackDepth(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def forAssertionsGenMapMessageWithoutStackDepth(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.forAssertionsGenMapMessageWithoutStackDepth(prettifier.apply(param0), prettifier.apply(param1))

def forAssertionsNoElement: String = Resources.forAssertionsNoElement

def forAssertionsElement(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.forAssertionsElement(prettifier.apply(param0))

def forAssertionsElements(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.forAssertionsElements(prettifier.apply(param0))

def forAssertionsIndexLabel(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.forAssertionsIndexLabel(prettifier.apply(param0))

def forAssertionsIndexAndLabel(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.forAssertionsIndexAndLabel(prettifier.apply(param0), prettifier.apply(param1))

def forAssertionsKeyLabel(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.forAssertionsKeyLabel(prettifier.apply(param0))

def forAssertionsKeyAndLabel(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.forAssertionsKeyAndLabel(prettifier.apply(param0), prettifier.apply(param1))

def forAllFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.forAllFailed(prettifier.apply(param0), prettifier.apply(param1))

def allShorthandFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.allShorthandFailed(prettifier.apply(param0), prettifier.apply(param1))

def forAtLeastFailedNoElement(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.forAtLeastFailedNoElement(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def forAtLeastFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.forAtLeastFailed(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def atLeastShorthandFailedNoElement(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.atLeastShorthandFailedNoElement(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def atLeastShorthandFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.atLeastShorthandFailed(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def forAtMostFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.forAtMostFailed(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def atMostShorthandFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.atMostShorthandFailed(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def forExactlyFailedNoElement(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.forExactlyFailedNoElement(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def forExactlyFailedLess(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = Resources.forExactlyFailedLess(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4))

def forExactlyFailedMore(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.forExactlyFailedMore(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def exactlyShorthandFailedNoElement(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exactlyShorthandFailedNoElement(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def exactlyShorthandFailedLess(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = Resources.exactlyShorthandFailedLess(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4))

def exactlyShorthandFailedMore(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.exactlyShorthandFailedMore(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def forNoFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.forNoFailed(prettifier.apply(param0), prettifier.apply(param1))

def noShorthandFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.noShorthandFailed(prettifier.apply(param0), prettifier.apply(param1))

def forBetweenFailedNoElement(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.forBetweenFailedNoElement(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def forBetweenFailedLess(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any, param5: Any): String = Resources.forBetweenFailedLess(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4), prettifier.apply(param5))

def forBetweenFailedMore(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = Resources.forBetweenFailedMore(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4))

def betweenShorthandFailedNoElement(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any): String = Resources.betweenShorthandFailedNoElement(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3))

def betweenShorthandFailedLess(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any, param5: Any): String = Resources.betweenShorthandFailedLess(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4), prettifier.apply(param5))

def betweenShorthandFailedMore(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any, param3: Any, param4: Any): String = Resources.betweenShorthandFailedMore(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2), prettifier.apply(param3), prettifier.apply(param4))

def forEveryFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.forEveryFailed(prettifier.apply(param0), prettifier.apply(param1))

def everyShorthandFailed(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.everyShorthandFailed(prettifier.apply(param0), prettifier.apply(param1))

def discoveryStarting: String = Resources.discoveryStarting

def discoveryCompleted: String = Resources.discoveryCompleted

def discoveryCompletedIn(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.discoveryCompletedIn(prettifier.apply(param0))

def doingDiscovery: String = Resources.doingDiscovery

def atCheckpointAt: String = Resources.atCheckpointAt

def slowpokeDetected(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.slowpokeDetected(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def alertFormattedText(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.alertFormattedText(prettifier.apply(param0))

def noteFormattedText(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.noteFormattedText(prettifier.apply(param0))

def testFlickered: String = Resources.testFlickered

def cannotRerun(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.cannotRerun(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def testCannotBeNestedInsideAnotherTest: String = Resources.testCannotBeNestedInsideAnotherTest

def nonEmptyMatchPatternCase: String = Resources.nonEmptyMatchPatternCase

def expectedTypeErrorButGotNone(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.expectedTypeErrorButGotNone(prettifier.apply(param0))

def gotTypeErrorAsExpected(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.gotTypeErrorAsExpected(prettifier.apply(param0))

def expectedCompileErrorButGotNone(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.expectedCompileErrorButGotNone(prettifier.apply(param0))

def didNotCompile(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.didNotCompile(prettifier.apply(param0))

def compiledSuccessfully(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.compiledSuccessfully(prettifier.apply(param0))

def expectedTypeErrorButGotParseError(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.expectedTypeErrorButGotParseError(prettifier.apply(param0), prettifier.apply(param1))

def expectedNoErrorButGotTypeError(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.expectedNoErrorButGotTypeError(prettifier.apply(param0), prettifier.apply(param1))

def expectedNoErrorButGotParseError(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.expectedNoErrorButGotParseError(prettifier.apply(param0), prettifier.apply(param1))

def anExpressionOfTypeNullIsIneligibleForImplicitConversion: String = Resources.anExpressionOfTypeNullIsIneligibleForImplicitConversion

def beTripleEqualsNotAllowed: String = Resources.beTripleEqualsNotAllowed

def assertionShouldBePutInsideItOrTheyClauseNotDescribeClause: String = Resources.assertionShouldBePutInsideItOrTheyClauseNotDescribeClause

def exceptionWasThrownInDescribeClause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exceptionWasThrownInDescribeClause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def assertionShouldBePutInsideInClauseNotDashClause: String = Resources.assertionShouldBePutInsideInClauseNotDashClause

def exceptionWasThrownInDashClause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exceptionWasThrownInDashClause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def assertionShouldBePutInsideScenarioClauseNotFeatureClause: String = Resources.assertionShouldBePutInsideScenarioClauseNotFeatureClause

def exceptionWasThrownInFeatureClause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exceptionWasThrownInFeatureClause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def assertionShouldBePutInsideItOrTheyClauseNotShouldMustWhenThatWhichOrCanClause: String = Resources.assertionShouldBePutInsideItOrTheyClauseNotShouldMustWhenThatWhichOrCanClause

def exceptionWasThrownInShouldClause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exceptionWasThrownInShouldClause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def exceptionWasThrownInMustClause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exceptionWasThrownInMustClause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def exceptionWasThrownInWhenClause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exceptionWasThrownInWhenClause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def exceptionWasThrownInThatClause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exceptionWasThrownInThatClause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def exceptionWasThrownInWhichClause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exceptionWasThrownInWhichClause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def exceptionWasThrownInCanClause(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any, param2: Any): String = Resources.exceptionWasThrownInCanClause(prettifier.apply(param0), prettifier.apply(param1), prettifier.apply(param2))

def assertionShouldBePutInsideDefNotObject: String = Resources.assertionShouldBePutInsideDefNotObject

def exceptionWasThrownInObject(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.exceptionWasThrownInObject(prettifier.apply(param0), prettifier.apply(param1))

def tableDrivenForEveryFailed(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.tableDrivenForEveryFailed(prettifier.apply(param0))

def tableDrivenExistsFailed(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.tableDrivenExistsFailed(prettifier.apply(param0))

def withFixtureNotAllowedInAsyncFixtures: String = Resources.withFixtureNotAllowedInAsyncFixtures

def leftParensCommaBut(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.leftParensCommaBut(prettifier.apply(param0), prettifier.apply(param1))

def rightParensCommaBut(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.rightParensCommaBut(prettifier.apply(param0), prettifier.apply(param1))

def bothParensCommaBut(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.bothParensCommaBut(prettifier.apply(param0), prettifier.apply(param1))

def leftParensCommaAnd(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.leftParensCommaAnd(prettifier.apply(param0), prettifier.apply(param1))

def rightParensCommaAnd(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.rightParensCommaAnd(prettifier.apply(param0), prettifier.apply(param1))

def bothParensCommaAnd(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.bothParensCommaAnd(prettifier.apply(param0), prettifier.apply(param1))

def assertionWasTrue: String = Resources.assertionWasTrue

def fromEqualToToHavingLengthsBetween(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.fromEqualToToHavingLengthsBetween(prettifier.apply(param0))

def fromGreaterThanToHavingLengthsBetween(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.fromGreaterThanToHavingLengthsBetween(prettifier.apply(param0), prettifier.apply(param1))

def fromEqualToToHavingSizesBetween(prettifier: org.scalactic.Prettifier, param0: Any): String = Resources.fromEqualToToHavingSizesBetween(prettifier.apply(param0))

def fromGreaterThanToHavingSizesBetween(prettifier: org.scalactic.Prettifier, param0: Any, param1: Any): String = Resources.fromGreaterThanToHavingSizesBetween(prettifier.apply(param0), prettifier.apply(param1))

def analysis: String = Resources.analysis

def deprecatedChosenStyleWarning: String = Resources.deprecatedChosenStyleWarning

def flexmarkClassNotFound: String = Resources.flexmarkClassNotFound

}
    
