package com.example.contactspdm.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactspdm.databinding.ActivityContactBinding
import com.example.contactspdm.model.Contact

class ContactActivity: AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)
        acb.toolbarIn.toolbar.apply {
            subtitle = this@ContactActivity.javaClass.simpleName
            setSupportActionBar(this)
        }

        with(acb) {
            acb.saveBt.setOnClickListener {
                Contact(
                    id = hashCode(),
                    name = nameEt.text.toString(),
                    address = addressEt.text.toString(),
                    phone = phoneEt.text.toString(),
                    email = emailEt.text.toString()
                ).let { contact ->
                    Intent().apply {
                        putExtra("EXTRA_CONTACT", contact)
                        setResult(RESULT_OK, this)
                        finish()
                    }
                }

            }
        }
    }
}