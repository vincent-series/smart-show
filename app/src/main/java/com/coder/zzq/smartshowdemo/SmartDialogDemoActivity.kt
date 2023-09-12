package com.coder.zzq.smartshowdemo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.coder.vincent.smart_dialog.SmartDialog
import com.coder.vincent.smart_dialog.input_num.NumberType
import com.coder.vincent.smart_dialog.loading.BoxSize
import com.coder.vincent.smart_toast.SmartToast
import com.coder.zzq.smartshowdemo.databinding.ActivityTestDialogBinding


class SmartDialogDemoActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityTestDialogBinding.inflate(layoutInflater)
                .apply { listView.onItemClickListener = this@SmartDialogDemoActivity }.root
        )
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        when (position) {
            0 -> onShowNotificationDialog()
            1 -> onShowAcknowledgeDialog()
            2 -> onShowAcknowledgeDelayDialog()
            3 -> onShowInputDialog()
            4 -> onShowInputNumDialog()
            5 -> onShowClickedListDialog()
            6 -> onShowSingleChooseDialog()
            7 -> onShowMultipleChooseDialog()
            8 -> onShowLargeLoading()
            9 -> onShowMiddleLoading()
            10 -> onShowSmallLoading()
        }
    }

    private val smallLoading by lazy {
        SmartDialog.builderOfLoading()
            .boxSize(BoxSize.SMALL)
            .build(this)
    }

    private fun onShowSmallLoading() {
        smallLoading.show()
    }

    private val middleLoading by lazy {
        SmartDialog.builderOfLoading()
            .message("正在加载")
            .boxSize(BoxSize.MIDDLE)
            .build(this)
    }

    private fun onShowMiddleLoading() {
        middleLoading.show()
    }

    private val largeLoading by lazy {
        SmartDialog.builderOfLoading()
            .message("正在加载")
            .boxSize(BoxSize.LARGE)
            .build(this)
    }

    private fun onShowLargeLoading() {
        largeLoading.show()
    }

    private val multipleChoice by lazy {
        SmartDialog.builderOfChosenList()
            .singleChoice(false)
            .defaultChosenPos(listOf(0, 1))
            .title("你喜欢哪些城市")
            .items(listOf("上海", "北京", "广州", "深圳", "杭州", "青岛", "苏州"))
            .confirmBtnListener { dialog, chosenItems ->
                dialog.dismiss()
                SmartToast.classic().showInCenter(chosenItems.toString())
            }
            .build(this)
    }

    private fun onShowMultipleChooseDialog() {
        multipleChoice.show()
    }

    private val singleChoice by lazy {
        SmartDialog.builderOfChosenList()
            .title("请选择语言")
            .items(listOf("Java", "Kotlin", "C", "C++", "C#", "Html"))
            .confirmBtnListener { dialog, chosenItems ->
                dialog.dismiss()
                SmartToast.classic().showInCenter(chosenItems.toString())
            }
            .build(this)

    }

    private fun onShowSingleChooseDialog() {
        singleChoice.show()
    }

    private val clickList by lazy {
        SmartDialog.builderOfClickedList()
            .items(listOf("回复", "转发", "私信回复", "复制", "举报"))
            .itemClickedListener { dialog, clickedItem ->
                dialog.dismiss()
                SmartToast.classic().showInCenter(clickedItem.value)
            }
            .build(this)
    }

    private fun onShowClickedListDialog() {
        clickList.show()
    }

    private val inputTextDialog by lazy {
        SmartDialog.builderOfInputText()
            .title("输入")
            .defaultFilledText("默认填充的文本")
            .hint("请输入建议")
            .maxInputLength(30)
            .confirmBtnListener { dialog, content ->
                dialog.dismiss()
                SmartToast.classic().showInCenter("您输入的内容是:$content")
            }
            .build(this)
    }

    private fun onShowInputDialog() {
        inputTextDialog.show()
    }

    private val inputNumberDialog by lazy {
        SmartDialog.builderOfInputNumber()
            .title("请输入货物件数")
            .defaultFilledNumber("5")
            .numberUnit("件")
            .numberType(NumberType.INTEGER)
            .confirmBtnListener { dialog, number ->
                dialog.dismiss()
                SmartToast.classic().showInCenter("您选择的件数为:$number")
            }
            .build(this)
    }

    private fun onShowInputNumDialog() {
        inputNumberDialog.show()
    }

    private val acknowledgeDelayDialog by lazy {
        SmartDialog.builderOfAcknowledge()
            .message("确定启用开发者模式？")
            .delayToConfirm(5)
            .confirmBtnListener {
                it.dismiss()
                SmartToast.emotion().success("开启成功")
            }
            .build(this)
    }

    private fun onShowAcknowledgeDelayDialog() {
        acknowledgeDelayDialog.show()
    }

    private val acknowledgeDialog by lazy {
        SmartDialog.builderOfAcknowledge()
            .message("确定不再关注此人？")
            .confirmBtnListener {
                it.dismiss()
                SmartToast.emotion().success("取消成功")
            }
            .build(this)
    }

    private fun onShowAcknowledgeDialog() {
        acknowledgeDialog.show()
    }

    private val notificationDialog by lazy {
        SmartDialog.builderOfNotification()
            .message("重置成功")
            .build(this)
    }

    private fun onShowNotificationDialog() {
        notificationDialog.show()
    }
}