package com.example.contactspdm.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contactspdm.databinding.ActivityContactBinding
import com.example.contactspdm.model.Constant.EXTRA_CONTACT
import com.example.contactspdm.model.Constant.EXTRA_VIEW_CONTACT
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
        val receiveContact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_CONTACT, Contact::class.java)
        }
        else {
            intent.getParcelableExtra(EXTRA_CONTACT)
        }

        with(acb){
            receiveContact?.let {
                nameEt.setText(receiveContact.name)
                addressEt.setText(receiveContact.address)
                phoneEt.setText(receiveContact.phone)
                emailEt.setText(receiveContact.email)
                if (intent.getBooleanExtra(EXTRA_VIEW_CONTACT, false)){
                    nameEt.isEnabled = false
                    addressEt.isEnabled = false
                    phoneEt.isEnabled = false
                    emailEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }

            }
            saveBt.setOnClickListener{
                Contact(
                    id =  receiveContact?.id?: hashCode(),
                    name = nameEt.text.toString(),
                    address = addressEt.text.toString(),
                    phone = phoneEt.text.toString(),
                    email = emailEt.text.toString()
                ).let {  contact ->
                    Intent().apply {
                        putExtra(EXTRA_CONTACT,contact)
                        setResult(RESULT_OK, this)
                        finish()
                    }

                }

            }


        }

    }
}