package com.coder.zzq.smartshow.dialog.dialog.type;


import com.coder.zzq.smartshow.dialog.dialog.ICancelBtnBuilder;
import com.coder.zzq.smartshow.dialog.dialog.IConfirmBtnBuilder;
import com.coder.zzq.smartshow.dialog.dialog.ITitleBuilder;

public interface IBranchDialogBuilder<B> extends ITitleBuilder<B>, IConfirmBtnBuilder<B>, ICancelBtnBuilder<B> {

}
