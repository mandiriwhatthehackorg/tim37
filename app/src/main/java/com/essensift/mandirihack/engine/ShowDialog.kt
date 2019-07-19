package com.essensift.mandirihack.engine

import android.content.Context
import android.graphics.Color
import com.essensift.mandirihack.R
import com.labters.lottiealertdialoglibrary.ClickListener2
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog

class ShowDialog {

    companion object {

        fun showDoneDialog(context: Context, msg: String) {
            try {
                showLottieDone(context, "Sukses!", msg, object : LottieDialogResponse2 {
                    override fun onDone(dialog: LottieAlertDialog) {
                        dialog.dismiss()
                    }
                })
            } catch (e: Exception) {
            }
        }

        fun showDoneDialog(context: Context, msg: String, c: LottieDialogResponse2) {
            try {
                showLottieDone(context, "Sukses!", msg, object : LottieDialogResponse2 {
                    override fun onDone(dialog: LottieAlertDialog) {
                        c.onDone(dialog)
                    }
                })
            } catch (e: Exception) {
            }
        }

        fun showErrorDialog(context: Context, msg: String) {
            try {
                showLottieError(context, "Error!", msg, object : LottieDialogResponse2 {
                    override fun onDone(dialog: LottieAlertDialog) {
                        dialog.dismiss()
                    }
                })
            } catch (e: Exception) {
            }
        }

        private fun showLottieDone(
            context: Context,
            title: String,
            message: String,
            call: LottieDialogResponse2
        ) {
            val alertDialog: LottieAlertDialog =
                LottieAlertDialog.Builder(context, DialogTypes.TYPE_SUCCESS)
                    .setTitle(title)
                    .setDescription(message)
                    .setPositiveText(context.getString(R.string.general_done))
                    .setPositiveButtonColor(Color.parseColor(context.getString(0 + R.color.colorGreenA700)))
                    .setPositiveTextColor(Color.parseColor(context.getString(0 + R.color.colorWhite)))
                    .setEnableResponse(false)
                    .setPositiveListener(object : ClickListener2 {
                        override fun onClick(dialog: LottieAlertDialog, message: String?) {
                            call.onDone(dialog)
                        }
                    }).build()
            alertDialog.show()
        }

        private fun showLottieError(
            context: Context,
            title: String,
            message: String,
            call: LottieDialogResponse2
        ) {
            val alertDialog: LottieAlertDialog =
                LottieAlertDialog.Builder(context, DialogTypes.TYPE_ERROR)
                    .setTitle(title)
                    .setDescription(message)
                    .setPositiveText(context.getString(R.string.general_close))
                    .setPositiveButtonColor(Color.parseColor(context.getString(0 + R.color.colorRed700)))
                    .setPositiveTextColor(Color.parseColor(context.getString(0 + R.color.colorWhite)))
                    .setEnableResponse(false)
                    .setPositiveListener(object : ClickListener2 {
                        override fun onClick(dialog: LottieAlertDialog, message: String?) {
                            call.onDone(dialog)
                        }
                    }).build()
            alertDialog.show()
        }
    }
    interface LottieDialogResponse2 {
        fun onDone(dialog: LottieAlertDialog)
    }

}