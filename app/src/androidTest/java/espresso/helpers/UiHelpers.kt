package espresso.helpers

import android.support.annotation.IdRes
import android.support.test.InstrumentationRegistry
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObject
import android.support.test.uiautomator.UiSelector

fun findUiObjectWithId(@IdRes id: Int): UiObject =
    InstrumentationRegistry.getTargetContext().resources.getResourceName(id)
        .let { resourceName ->
          UiSelector().resourceId(resourceName)
        }.let { uiSelector ->
          UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).findObject(uiSelector)
        }
