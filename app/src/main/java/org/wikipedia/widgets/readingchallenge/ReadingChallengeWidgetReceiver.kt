package org.wikipedia.widgets.readingchallenge

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import org.wikipedia.settings.Prefs

class ReadingChallengeWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = ReadingChallengeWidget()

    override fun onEnabled(context: Context) {
        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(ComponentName(context, ReadingChallengeWidgetReceiver::class.java))
        if (ids.isEmpty()) return

        super.onEnabled(context)
        // If the user has already added the widget, we should not display the install dialog
        Prefs.readingChallengeInstallPromptShown = true
        ReadingChallengeWidgetWorker.scheduleNextWidgetUpdate(context)
    }

    override fun onDisabled(context: Context) {
        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(ComponentName(context, ReadingChallengeWidgetReceiver::class.java))
        if (ids.isNotEmpty()) return

        super.onDisabled(context)
        Prefs.readingChallengeInstallPromptShown = false
        ReadingChallengeWidgetWorker.cancelScheduledUpdates(context)
    }
}
