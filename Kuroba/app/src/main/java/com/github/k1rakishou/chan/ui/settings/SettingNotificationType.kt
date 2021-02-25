package com.github.k1rakishou.chan.ui.settings

import androidx.annotation.ColorRes
import com.github.k1rakishou.chan.R

enum class SettingNotificationType(@ColorRes val notificationIconTintColor: Int) {
  /**
   * No active notification
   * */
  Default(android.R.color.transparent),

  /**
   * New apk update is available notification
   * */
  ApkUpdate(R.color.new_apk_update_icon_color),

  /**
   * There is at least one crash log/ANR available notification
   * */
  CrashLogOrAnr(R.color.new_crash_log_icon_color)
}