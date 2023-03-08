package pro.ksart.rickandmorty.ui.components

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun showToast(context: Context, @StringRes stringId: Int) {
    Toast.makeText(context, context.getText(stringId), Toast.LENGTH_LONG).show()
}
