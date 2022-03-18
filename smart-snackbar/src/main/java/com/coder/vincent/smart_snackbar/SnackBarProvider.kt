package com.coder.vincent.smart_snackbar

import com.coder.vincent.smart_snackbar.schedule.ScheduledBottomSnackBar
import com.coder.vincent.smart_snackbar.schedule.ScheduledSnackBar
import com.coder.vincent.smart_snackbar.schedule.ScheduledTopSnackBar

class SnackBarProvider {
    fun provideSnackBar(config: SnackBarConfig): ScheduledSnackBar =
        if (config.position == SNACK_BAR_POSITION_BOTTOM)
            ScheduledBottomSnackBar(config)
        else
            ScheduledTopSnackBar(config)
}