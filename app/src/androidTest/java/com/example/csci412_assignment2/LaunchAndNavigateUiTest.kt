package com.example.csci412_assignment2

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchAndNavigateUiTest {

    companion object {
        private const val PACKAGE = "com.example.csci412_assignment2"
        private const val APP_NAME = "CSCI412_Assignment2"
        private const val CHALLENGE_TEXT = "Fragmentation across devices"
        private const val TIMEOUT = 5_000L
    }

    private lateinit var device: UiDevice

    @Before
    fun goToHomeScreen() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()
        val launcher = device.launcherPackageName
        assertNotNull("Launcher package not found", launcher)
        device.wait(Until.hasObject(By.pkg(launcher).depth(0)), TIMEOUT)
    }

    @Test
    fun launchFromHome_clickExplicitButton_secondActivityShowsChallenge() {
        openAppDrawer()
        val appIcon = device.wait(Until.findObject(By.text(APP_NAME)), TIMEOUT)
        assertNotNull("Launcher icon '$APP_NAME' not found", appIcon)
        appIcon.click()

        val launched = device.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)), TIMEOUT)
        assertTrue("App did not launch", launched == true)

        dismissNotificationPermissionDialog()

        val explicitButton = device.wait(Until.findObject(By.text("Start Activity Explicitly")), TIMEOUT)
        assertNotNull("'Start Activity Explicitly' button not found", explicitButton)
        explicitButton.click()

        val challenge = device.wait(Until.findObject(By.textContains(CHALLENGE_TEXT)), TIMEOUT)
        assertNotNull("Challenge '$CHALLENGE_TEXT' not shown in SecondActivity", challenge)
    }

    private fun openAppDrawer() {
        val x = device.displayWidth / 2
        device.swipe(x, (device.displayHeight * 0.85).toInt(), x, (device.displayHeight * 0.2).toInt(), 15)
        device.waitForIdle()
    }

    private fun dismissNotificationPermissionDialog() {
        val allow = device.wait(
            Until.findObject(By.res("com.android.permissioncontroller", "permission_allow_button")),
            3_000
        )
        allow?.click()
    }
}