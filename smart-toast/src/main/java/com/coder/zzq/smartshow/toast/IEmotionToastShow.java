package com.coder.zzq.smartshow.toast;


import androidx.annotation.StringRes;

public interface IEmotionToastShow {

    void info(CharSequence msg);

    void info(@StringRes int msg);



    void infoLong(CharSequence msg);

    void infoLong(@StringRes int msg);



    void warning(CharSequence msg);

    void warning(@StringRes int msg);




    void warningLong(CharSequence msg);

    void warningLong(@StringRes int msg);



    void success(CharSequence msg);

    void success(@StringRes int msg);


    void successLong(CharSequence msg);

    void successLong(@StringRes int msg);



    void error(CharSequence msg);

    void error(@StringRes int msg);



    void errorLong(CharSequence msg);

    void errorLong(@StringRes int msg);



    void fail(CharSequence msg);

    void fail(@StringRes int msg);



    void failLong(CharSequence msg);

    void failLong(@StringRes int msg);



    void complete(CharSequence msg);

    void complete(@StringRes int msg);



    void completeLong(CharSequence msg);

    void completeLong(@StringRes int msg);



    void forbid(CharSequence msg);

    void forbid(@StringRes int msg);



    void forbidLong(CharSequence msg);

    void forbidLong(@StringRes int msg);



    void waiting(CharSequence msg);

    void waiting(@StringRes int msg);



    void waitingLong(CharSequence msg);

    void waitingLong(@StringRes int msg);


}
