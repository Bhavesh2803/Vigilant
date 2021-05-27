package com.vigilant.Utility

import android.text.InputFilter
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText


open class Util {

    companion object {


        fun makeLinks(textView: TextView, links: Array<String>, clickableSpans: Array<ClickableSpan>) {
            val spannableString = SpannableString(textView.text)

            for (i in links.indices) {
                val clickableSpan = clickableSpans[i]
                val link = links[i]

                val startIndexOfLink = textView.text.indexOf(link)

                spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.setText(spannableString, TextView.BufferType.SPANNABLE)

        }

        fun isValidPanNo(value:String): Boolean {
            val pattern = "^[A-Z]{5}[0-9]{4}[A-Z]{1}\$"
            return pattern.toRegex().matches(value)
        }

        fun isValidAadhar(value:String): Boolean {
            val pattern = "^\\d{4}\\d{4}\\d{4}\$"
            return pattern.toRegex().matches(value)
        }

        fun isValidVoterCard(value:String): Boolean {
            val pattern = "^([a-zA-Z]){3}([0-9]){7}?\$"
            return pattern.toRegex().matches(value)
        }

        fun isValidPassport(value:String): Boolean {
            val pattern = "^(?!^0+\$)[a-zA-Z0-9]{3,20}\$"
            return pattern.toRegex().matches(value)
        }

        fun isValidDL(value:String): Boolean {
            val pattern = "^(([A-Z]{2}[0-9]{2})( )|([A-Z]{2}-[0-9]{2}))((19|20)[0-9][0-9])[0-9]{7}\$"
            return pattern.toRegex().matches(value)
        }

        fun isIFSCValid(value:String): Boolean {
            val pattern = "^[A-Z]{4}0[A-Z0-9]{6}$"
            return pattern.toRegex().matches(value)
        }

        fun isValidPassword(value: String): Boolean {
            val pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!#%*?&])[A-Za-z\\d@\$!#%*?&]{8,15}\$"
            return pattern.toRegex().matches(value)
        }

        fun isSet(value: String?): Boolean {
            return value != null && !TextUtils.isEmpty(value.trim { it <= ' ' }) && !"null".equals(
                value.trim { it <= ' ' },
                ignoreCase = true
            )
        }

        fun changeInputType(text: String, editText: AppCompatEditText) {
            when (text.length) {
                in 0..4 -> {
                    editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                }
                in 5..8 -> {
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                }
                in 9..10 -> {
                    editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                }
                else ->{
                    editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                }
            }
        }

        fun getEmojiFilter(): InputFilter {
            val allowedCharacterSet = ".,_'-&/@"
            val emojiFilter = InputFilter { source, start, end, dest, dstart, dend ->
                for (index in start until end) {
                    val type = Character.getType(source[index])

                    when (type) {
                        '*'.toInt(),
                        Character.OTHER_SYMBOL.toInt() -> {
                            if (source!=null && !allowedCharacterSet.contains(""+source[index], true)){
                                return@InputFilter ""
                            }
                        }
                        Character.SURROGATE.toInt() -> {
                            return@InputFilter ""
                        }
                        Character.LOWERCASE_LETTER.toInt() -> {
                            val index2 = index + 1
                            if (index2 < end && Character.getType(source[index + 1]) == Character.NON_SPACING_MARK.toInt()) {
                                return@InputFilter ""
                            }
                        }
                        Character.DECIMAL_DIGIT_NUMBER.toInt() -> {
                            val index2 = index + 1
                            val index3 = index + 2
                            if (index2 < end && index3 < end &&
                                Character.getType(source[index2]) == Character.NON_SPACING_MARK.toInt() &&
                                Character.getType(source[index3]) == Character.ENCLOSING_MARK.toInt()
                            ) {
                                return@InputFilter ""
                            }
                        }
                        Character.OTHER_PUNCTUATION.toInt() -> {
                            val index2 = index + 1
                            if (source!=null && !allowedCharacterSet.contains(""+source[index], true)){
                                return@InputFilter ""
                            }
                            if (index2 < end && Character.getType(source[index2]) == Character.NON_SPACING_MARK.toInt()) {
                                return@InputFilter ""
                            }
                        }
                        Character.MATH_SYMBOL.toInt() -> {
                            val index2 = index + 1
                            if (source!=null && !allowedCharacterSet.contains(""+source[index], true)){
                                return@InputFilter ""
                            }
                            if (index2 < end && Character.getType(source[index2]) == Character.NON_SPACING_MARK.toInt()) {
                                return@InputFilter ""
                            }
                        }
                    }
                }
                return@InputFilter null
            }
            return emojiFilter
        }

        fun getRelationMaritalStatus(relation: String):String{
            when (relation) {
                "Brother" -> {
                    return ""
                }
                "Daughter" -> {
                    return ""
                }
                "Daughter In Law" -> {
                    return ""
                }
                "Employee" -> {
                    return ""
                }
                "Father" -> {
                    return "married"
                }
                "Father In Law" -> {
                    return "married"
                }
                "Husband" -> {
                    return "married"
                }
                "Mother" -> {
                    return "married"
                }
                "Mother In Law" -> {
                    return "married"
                }
                "Partner" -> {
                    return ""
                }
                "Son" -> {
                    return ""
                }
                "Wife" ->{
                    return "married"
                }
                else -> {
                    return ""
                }
            }
        }
    }
}