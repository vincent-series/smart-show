package com.coder.vincent.smart_dialog

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class DialogHandleManager : ViewModel() {
    private val dialogHandles = LinkedList<DialogHandle>()
    fun onOwnerRecreate() {
        val iterator = dialogHandles.iterator()
        while (iterator.hasNext()) {
            if ((iterator.next() as LifecycleManager).isDirty()) {
                iterator.remove()
            }
        }
    }

    override fun onCleared() {
        dialogHandles.clear()
    }

    fun generateDialogHandle(
        activity: AppCompatActivity,
        dialogCombiner: DialogCombiner,
    ): DialogHandle {
        val statusStorage = VisibilityStatusStorage()
        return DialogHandleImpl(
            statusStorage,
            InstructionEmitter().apply {
                observe(activity, DialogObserver(statusStorage, dialogCombiner, activity))
            }
        ).apply { dialogHandles.add(this) }
    }
}

interface InstructionHandle {
    fun show()

    fun dismiss()

    fun cancel()
}

interface StatusHandle {
    fun isShowing(): Boolean
    fun status(): VisibilityStatus
}

interface DialogHandle : InstructionHandle, StatusHandle
interface LifecycleManager {
    fun isDirty(): Boolean
}

class DoNothingDialogHandle : DialogHandle, LifecycleManager {
    override fun show() {}

    override fun dismiss() {}

    override fun cancel() {}

    override fun isShowing() = false

    override fun status() = VisibilityStatus.INVISIBLE

    override fun isDirty() = true
}

class DialogHandleImpl(
    private val statusStorage: VisibilityStatusStorage,
    private val instructionEmitter: InstructionEmitter
) : DialogHandle, LifecycleManager {
    override fun isDirty() = !instructionEmitter.hasObservers()
    override fun show() {
        instructionEmitter.show()
    }

    override fun dismiss() {
        instructionEmitter.dismiss()
    }

    override fun cancel() {
        instructionEmitter.cancel()
    }

    override fun isShowing() = statusStorage.isShowing()

    override fun status() = statusStorage.status()

}

class DialogObserver(
    private val visibilityStatusStorage: VisibilityStatusStorage,
    private val dialogCombiner: DialogCombiner,
    activity: AppCompatActivity,
) : Observer<Instruction>, LifecycleObserver {
    var dialog: AppCompatDialog? = null

    init {
        activity.lifecycle.addObserver(this)
        dialog = dialogCombiner.createDialog(activity)
        dialog?.window?.decorView?.addOnAttachStateChangeListener(
            object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    visibilityStatusStorage.visibilityStatus.set(VisibilityStatus.VISIBLE)
                }

                override fun onViewDetachedFromWindow(v: View) {
                    visibilityStatusStorage.visibilityStatus.set(VisibilityStatus.VISIBLE)
                }
            })
    }


    override fun onChanged(status: Instruction) {
        when (status) {
            Instruction.SHOW -> kotlin.runCatching {
                dialogCombiner.resetWhenShowAgain()
                dialog?.show()
            }
            Instruction.DISMISS -> kotlin.runCatching {
                dialog?.dismiss()

            }
            Instruction.CANCEL -> kotlin.runCatching {
                dialog?.cancel()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onClear(lifecycleOwner: LifecycleOwner) {
        if (dialog?.isShowing == true){
            dialog?.dismiss()
        }
        dialog = null
        lifecycleOwner.lifecycle.removeObserver(this)
    }
}

class InstructionEmitter : MutableLiveData<Instruction>(), InstructionHandle {

    override fun show() {
        postValue(Instruction.SHOW)
    }

    override fun dismiss() {
        postValue(Instruction.DISMISS)
    }

    override fun cancel() {
        postValue(Instruction.CANCEL)
    }
}

enum class Instruction {
    SHOW,
    DISMISS,
    CANCEL,
}

class VisibilityStatusStorage : StatusHandle {
    val visibilityStatus: AtomicReference<VisibilityStatus> = AtomicReference()
    override fun isShowing() = visibilityStatus.get() == VisibilityStatus.VISIBLE

    override fun status() = visibilityStatus.get() ?: VisibilityStatus.INVISIBLE
}

enum class VisibilityStatus {
    VISIBLE,
    INVISIBLE,
}

interface DialogCombiner {
    fun createDialog(activity: AppCompatActivity): AppCompatDialog
    fun resetWhenShowAgain()
}
