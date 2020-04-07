package ir.alirezaiyan.touchword

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.BreakIterator
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sampleText = getString(R.string.sampleText)

        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.setText(sampleText, TextView.BufferType.SPANNABLE)
        val iterator = BreakIterator.getWordInstance(Locale.US)

        iterator.setText(sampleText)

        var start = iterator.first()
        var end = iterator.next()

        while (end != BreakIterator.DONE) {
            val possibleWord = sampleText.substring(start, end)
            if (Character.isLetterOrDigit(possibleWord[0])) {
                val clickSpan = getClickableSpan(possibleWord)
                (textView.text as Spannable).setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            start = end
            end = iterator.next()
        }
    }

    private fun getClickableSpan(word: String): ClickableSpan? {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                Log.d("tapped on:", word)
                Toast.makeText(widget.context, word, Toast.LENGTH_SHORT)
                        .show()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.isAntiAlias = true
            }
        }
    }
}
