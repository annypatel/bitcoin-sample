package bitcoin.test.ui

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.GrantPermissionRule
import bitcoin.base.ui.BaseApplication
import okreplay.AndroidTapeRoot
import okreplay.MatchRules
import okreplay.OkReplayConfig
import okreplay.RecorderRule
import okreplay.TapeMode
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

/**
 * Creates a Junit [TestRule] for OkReplay.
 *
 * Note: OkReplay's OkReplayRuleChain depends on ActivityTestRule. To use OkReplay with
 * FragmentScenario and ActivityScenario we need to create custom [RuleChain] which wraps
 * [RecorderRule] (records tapes) into [GrantPermissionRule] (grant write permission on device).
 */
fun Any.okReplayRule(): TestRule {
    val app = getApplicationContext() as? BaseApplication
        ?: throw IllegalStateException("Your TestApp must extends DaggerTestApplication")

    val component = app.applicationInjector() as? OkReplayInterceptorProvider
        ?: throw IllegalStateException(
            "Your TestAppComponent must implement OkReplayInterceptorProvider"
        )

    val config = OkReplayConfig.Builder()
        .tapeRoot(AndroidTapeRoot(getInstrumentation().targetContext, this::class.java))
        .defaultMode(TapeMode.READ_WRITE)
        .defaultMatchRules(MatchRules.method, MatchRules.path)
        .sslEnabled(true)
        .interceptor(component.interceptor)
        .build()

    return RuleChain.outerRule(GrantPermissionRule.grant(WRITE_EXTERNAL_STORAGE))
        .around(RecorderRule(config))
}
