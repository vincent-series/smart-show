package com.coder.zzq.smartshow.toast;


public interface ITypeShow {

    void info(CharSequence msg);

    void infoLong(CharSequence msg);

    void warning(CharSequence msg);

    void warningLong(CharSequence msg);

    void success(CharSequence msg);

    void successLong(CharSequence msg);

    void error(CharSequence msg);

    void errorLong(CharSequence msg);

    void fail(CharSequence msg);

    void failLong(CharSequence msg);

    void complete(CharSequence msg);

    void completeLong(CharSequence msg);

    void forbid(CharSequence msg);

    void forbidLong(CharSequence msg);

    void waiting(CharSequence msg);

    void waitingLong(CharSequence msg);

}
