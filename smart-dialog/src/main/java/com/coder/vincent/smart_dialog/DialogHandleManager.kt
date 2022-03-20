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
            if ((iterator.next() as LifecycleIndicator).isDirty()) {
                iterator.remove()
            }
        }
    }

    override fun onCleared() {
        dialogHandles.clear()
    }

    fun generateDialogHandle(
        activity: AppCompatActivity,
        dialogCreator: DialogCreator,
    ): DialogHandle {
        val visibilityIndicator = VisibilityIndicator()
        return DialogHandleImpl(
            visibilityIndicator,
            VisibilityController().apply {
                observe(activity, DialogObserver(visibilityIndicator, dialogCreator, activity))
            }
        ).apply { dialogHandles.add(this) }
    }
}

interface VisibilityControlHandle {
    fun show()

    fun dismiss()

    fun cancel()
}

interface VisibilityStatusHandle {
    fun isShowing(): Boolean
}

interface DialogHandle : VisibilityControlHandle, VisibilityStatusHandle
interface LifecycleIndicator {
    fun isDirty(): Boolean
}

class DoNothingDialogHandle : DialogHandle, LifecycleIndicator {
    override fun show() {}

    override fun dismiss() {}

    override fun cancel() {}

    override fun isShowing() = false


    override fun isDirty() = true
}

class DialogHandleImpl(
    private val visibilityIndicator: VisibilityIndicator,
    private val visibilityController: VisibilityController
) : DialogHandle, LifecycleIndicator {
    override fun isDirty() = !visibilityController.hasObservers()
    override fun show() {
        visibilityController.show()
    }

    override fun dismiss() {
        visibilityController.dismiss()
    }

    override fun cancel() {
        visibilityController.cancel()
    }

    override fun isShowing() = visibilityIndicator.isShowing()
}

class DialogObserver(
    private val visibilityIndicator: VisibilityIndicator,
    private val dialogCreator: DialogCreator,
    activity: AppCompatActivity,
) : Observer<Instruction>, LifecycleObserver {
    var dialog: AppCompatDialog? = dialogCreator.createDialog(activity)

    init {
        activity.lifecycle.addObserver(this)
        dialog?.window?.decorView?.addOnAttachStateChangeListener(
            object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    visibilityIndicator.visibilityStatus.set(true)
                }

                override fun onViewDetachedFromWindow(v: View) {
                    visibilityIndicator.visibilityStatus.set(false)
                }
            })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onReleaseDialog(owner: LifecycleOwner) {
        dialog = null
        owner.lifecycle.removeObserver(this)
    }


    override fun onChanged(status: Instruction) {
        when (status) {
            Instruction.SHOW -> kotlin.runCatching {
                dialogCreator.resetWhenShowAgain()
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

class VisibilityController : MutableLiveData<Instruction>(), VisibilityControlHandle {

    override fun show() {
        postValue(Instruction.SHOW)
    }

    override fun dismiss() {
        LinkedList<String>().peek()
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

class VisibilityIndicator : VisibilityStatusHandle {
    val visibilityStatus: AtomicReference<Boolean> = AtomicReference(false)
    override fun isShowing(): Boolean = visibilityStatus.get()
}

interface DialogCreator {
    fun createDialog(activity: AppCompatActivity): AppCompatDialog
    fun resetWhenShowAgain()
}
