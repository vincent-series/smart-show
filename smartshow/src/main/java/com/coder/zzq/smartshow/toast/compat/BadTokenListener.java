package com.coder.zzq.smartshow.toast.compat;

import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * @author drakeet
 */
public interface BadTokenListener {

  void onBadTokenCaught(@NonNull Toast toast);
}
