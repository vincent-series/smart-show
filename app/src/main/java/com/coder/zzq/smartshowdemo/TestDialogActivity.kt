package com.coder.zzq.smartshowdemo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.smart_dialog.SmartDialog
import com.coder.vincent.smart_dialog.choose_list.LIST_CHOICE_MODE_MULTIPLE
import com.coder.vincent.smart_dialog.choose_list.LIST_CHOICE_MODE_SINGLE
import com.coder.vincent.smart_dialog.input_num.NUMBER_TYPE_INT
import com.coder.vincent.smart_dialog.loading.BOX_SIZE_LARGE
import com.coder.vincent.smart_dialog.loading.BOX_SIZE_MIDDLE
import com.coder.vincent.smart_dialog.loading.BOX_SIZE_SMALL
import com.coder.vincent.smart_toast.SmartToast
import com.coder.zzq.smartshowdemo.databinding.ActivityTestDialogBinding

class TestDialogActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityTestDialogBinding.inflate(layoutInflater)
                .apply { listView.onItemClickListener = this@TestDialogActivity }.root
        )
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        when (position) {
            0 -> onShowNotificationDialog()
            1 -> onShowEnsureDialog()
            2 -> onShowEnsureDelayDialog()
            3 -> onShowInputDialog()
            4 -> onShowInputNumDialog()
            5 -> onShowClickListDialog()
            6 -> onShowSingleChooseDialog()
            7 -> onShowMultipleChooseDialog()
            8 -> onShowLargeLoading()
            9 -> onShowMiddleLoading()
            10 -> onShowSmallLoading()
        }
    }

    private fun onShowSmallLoading() {
        SmartDialog.loading()
            .config {
                it.boxSize(BOX_SIZE_SMALL)
            }
            .create(this)
            .show()
    }


    private fun onShowMiddleLoading() {
        SmartDialog.loading()
            .config {
                it.boxSize(BOX_SIZE_MIDDLE)
                it.message("正在加载")
            }
            .create(this)
            .show()
    }


    private fun onShowLargeLoading() {
        SmartDialog.loading()
            .config {
                it.boxSize(BOX_SIZE_LARGE)
                it.message("正在加载")
            }
            .create(this)
            .show()
    }

    private fun onShowMultipleChooseDialog() {
        SmartDialog.chooseList()
            .config {
                it.title("你喜欢哪些城市")
                it.defaultChoosePos(listOf(0, 1))
                it.choiceMode(LIST_CHOICE_MODE_MULTIPLE)
                it.items(listOf("上海", "北京", "广州", "深圳", "杭州", "青岛", "苏州"))
                it.confirmBtnListener { appCompatDialog, list ->
                    appCompatDialog.dismiss()
                    SmartToast.classic().showInCenter(list.toString())
                }
            }
            .create(this)
            .show()
    }

    private fun onShowSingleChooseDialog() {
        SmartDialog.chooseList()
            .config {
                it.title("请选择语言")
                it.defaultChoosePos(listOf(0))
                it.choiceMode(LIST_CHOICE_MODE_SINGLE)
                it.items(listOf("Java", "Kotlin", "C", "C++", "C#", "Html"))
                it.confirmBtnListener { appCompatDialog, list ->
                    appCompatDialog.dismiss()
                    SmartToast.classic().showInCenter(list.toString())
                }
            }
            .create(this)
            .show()
    }

    private fun onShowClickListDialog() {
        SmartDialog.clickList()
            .config {
                it.items(listOf("回复", "转发", "私信回复", "复制", "举报"))
                it.itemCenter(true)
                it.itemClickedListener { appCompatDialog, clickedItem ->
                    appCompatDialog.dismiss()
                    SmartToast.classic().showInCenter(clickedItem.value)
                }
            }
            .create(this)
            .show()
    }


    private fun onShowInputDialog() {
        SmartDialog.inputText()
            .config {
                it.title("输入")
                it.defaultFilledText("默认填充的文本")
                it.hint("请输入建议")
                it.mostInputNum(30)
                it.confirmBtnListener { appCompatDialog, s ->
                    appCompatDialog.dismiss()
                    SmartToast.classic().showInCenter("输入的内容为：$s")
                }
            }
            .create(this)
            .show()
    }

    private fun onShowInputNumDialog() {
        SmartDialog.inputNumber()
            .config {
                it.title("请输入货物件数")
                it.defaultFilledNumber("5")
                it.numberUnit("件")
                it.numberType(NUMBER_TYPE_INT)
                it.confirmBtnListener { appCompatDialog, s ->
                    appCompatDialog.dismiss()
                    SmartToast.classic().showInCenter("您选择的件数为:$s")
                }
            }
            .create(this)
            .show()
    }

    private fun onShowEnsureDelayDialog() {
        SmartDialog.ensure()
            .config {
                it.message("确定启用开发者模式？")
                it.delayToConfirm(5)
                it.confirmBtnListener { dialog ->
                    dialog.dismiss()
                    SmartToast.emotion().success("开启成功")
                }
            }
            .create(this)
            .show()
    }

    private fun onShowEnsureDialog() {
        SmartDialog.ensure()
            .config {
                it.message("确定不再关注此人？")
                it.confirmBtnListener { dialog ->
                    dialog.dismiss()
                    SmartToast.emotion().success("取消成功")
                }
            }
            .create(this)
            .show()
    }

    private fun onShowNotificationDialog() {
        SmartDialog.notification()
            .config {
                it.message("重置成功")
            }
            .create(this)
            .show()
    }
}