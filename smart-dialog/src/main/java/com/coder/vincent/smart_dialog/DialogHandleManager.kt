package com.coder.vincent.smart_dialog

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import java.util.*

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
        dialogHandles.forEach {
            if (it.status() == VisibilityStatus.VISIBLE) {
                it.dismiss()
            }
            (it as LifecycleManager).onClear()
        }
        dialogHandles.clear()
    }

    fun generateDialogHandle(
        activity: AppCompatActivity,
        dialogCombiner: DialogCombiner,
    ): DialogHandle {
        val statusChecker = StatusChecker()
        return DialogHandleImpl(
            statusChecker,
            InstructionEmitter().apply {
                observe(activity, DialogObserver(statusChecker, dialogCombiner, activity))
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

interface DialogHandle : InstructionHandle, StatusHandle {}
interface LifecycleManager {
    fun isDirty(): Boolean

    fun onClear()
}

class DoNothingDialogHandle : DialogHandle, LifecycleManager {
    override fun show() {}

    override fun dismiss() {}

    override fun cancel() {}

    override fun isShowing() = false

    override fun status() = VisibilityStatus.INVISIBLE

    override fun isDirty() = true

    override fun onClear() {}
}

class DialogHandleImpl(
    private var statusChecker: StatusChecker?,
    private val instructionEmitter: InstructionEmitter
) : DialogHandle, LifecycleManager {
    override fun isDirty() = !instructionEmitter.hasObservers()

    override fun onClear() {
        statusChecker = null
    }

    override fun show() {
        instructionEmitter.show()
    }

    override fun dismiss() {
        instructionEmitter.dismiss()
    }

    override fun cancel() {
        instructionEmitter.cancel()
    }

    override fun isShowing() = statusChecker?.isShowing() == true

    override fun status() = statusChecker?.status() ?: VisibilityStatus.INVISIBLE

}

class DialogObserver(
    private val statusChecker: StatusChecker,
    private val dialogCombiner: DialogCombiner,
    activity: AppCompatActivity,
) : Observer<Instruction> {
    init {
        activity.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onOwnerDestroyed() {
                dialog?.dismiss()
                dialog = null
                statusChecker.statusFetcher = null
            }
        })
    }

    var dialog: AppCompatDialog? = dialogCombiner.createDialog(activity).apply {
        statusChecker.statusFetcher =
            { if (isShowing) VisibilityStatus.VISIBLE else VisibilityStatus.INVISIBLE }
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

class StatusChecker : StatusHandle {
    var statusFetcher: (() -> VisibilityStatus)? = null
    override fun isShowing() = statusFetcher?.invoke() == VisibilityStatus.VISIBLE

    override fun status() = statusFetcher?.invoke() ?: VisibilityStatus.INVISIBLE
}

enum class VisibilityStatus {
    VISIBLE,
    INVISIBLE,
}

interface DialogCombiner {
    fun createDialog(activity: AppCompatActivity): AppCompatDialog
    fun resetWhenShowAgain()
}
