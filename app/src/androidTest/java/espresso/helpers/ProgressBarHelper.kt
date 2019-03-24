package espresso.helpers

import android.support.test.uiautomator.UiObject
import com.twitter.R

object ProgressBarHelper {
  private const val DEFAULT_TIMEOUT = 5000L

  fun waitUntilProgressBarGone() {
    progressBar().waitUntilGone(DEFAULT_TIMEOUT)
  }

  private fun progressBar(): UiObject = findUiObjectWithId(R.id.progress_bar)

}
