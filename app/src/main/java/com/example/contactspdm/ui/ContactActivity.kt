package com.example.contactspdm.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactspdm.databinding.ActivityContactBinding

class ContactActivity: AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        acb.saveBt.setOnClickListener{
            val parametros = Bundle()
            val resIntent = Intent()
            parametros.putString("name", acb.nameEt.text.toString())
            parametros.putString("address", acb.addressEt.text.toString())
            parametros.putString("phone", acb.phoneEt.text.toString())
            parametros.putString("email", acb.emailEt.text.toString())
            resIntent.putExtra("bundle", parametros).also {
                setResult(RESULT_OK, it)
                finish()
            }
        }
    }
}