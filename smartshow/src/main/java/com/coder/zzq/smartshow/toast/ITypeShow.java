package com.coder.zzq.smartshow.toast;

import android.widget.Toast;

public interface ITypeShow {

    void normal(String msg);

    void normalLong(String msg);

    void warning(String msg);

    void warningLong(String msg);

    void fail(String msg);

    void failLong(String msg);

    void success(String msg);

    void successLong(String msg);

    void error(String msg);

    void errorLong(String msg);
}
