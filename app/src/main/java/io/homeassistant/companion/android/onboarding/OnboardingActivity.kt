package io.homeassistant.companion.android.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.homeassistant.companion.android.R
import io.homeassistant.companion.android.onboarding.authentication.AuthenticationFragment
import io.homeassistant.companion.android.onboarding.authentication.AuthenticationListener
import io.homeassistant.companion.android.onboarding.discovery.DiscoveryFragment
import io.homeassistant.companion.android.onboarding.discovery.DiscoveryListener
import io.homeassistant.companion.android.onboarding.integration.MobileAppIntegrationFragment
import io.homeassistant.companion.android.onboarding.integration.MobileAppIntegrationListener
import io.homeassistant.companion.android.onboarding.manual.ManualSetupFragment
import io.homeassistant.companion.android.onboarding.manual.ManualSetupListener
import io.homeassistant.companion.android.webview.WebViewActivity


class OnboardingActivity : AppCompatActivity(), DiscoveryListener, ManualSetupListener, AuthenticationListener, MobileAppIntegrationListener {

    companion object {
        private const val TAG = "OnboardingActivity"

        fun newInstance(context: Context): Intent {
            return Intent(context, OnboardingActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.content, DiscoveryFragment.newInstance())
                .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSelectManualSetup() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, ManualSetupFragment.newInstance())
            .commit()
    }

    override fun onHomeAssistantDiscover() {
        throw NotImplementedError()
    }

    override fun onSelectUrl() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, AuthenticationFragment.newInstance())
            .commit()
    }

    override fun onAuthenticationSuccess() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, MobileAppIntegrationFragment.newInstance())
            .commit()
    }

    override fun onIntegrationRegistrationComplete() {
        startWebView()
    }

    override fun onIntegrationRegistrationSkipped() {
        startWebView()
    }

    private fun startWebView(){
        startActivity(WebViewActivity.newInstance(this))
        finish()
    }

}