package com.example.contactspdm.ui

import android.os.Build
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.contactspdm.R
import com.example.contactspdm.adapter.ContactAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.contactspdm.databinding.ActivityMainBinding
import com.example.contactspdm.model.Contact

import com.example.contactspdm.ui.ContactActivity

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    // data source igual ao professor
    private val contactList: MutableList<Contact> = mutableListOf()

    // adapter ...
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(this, contactList
        )
    }
    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        amb.toolbarIn.toolbar.apply {

            subtitle = this@MainActivity.javaClass.simpleName

            setSupportActionBar(this)
        }
        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val contact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("EXTRA_CONTACT", Contact::class.java)
                }
                else {
                    result.data?.getParcelableExtra("EXTRA_CONTACT")
                }
                if (contact != null) {

                    contactList.add(contact)
                    contactAdapter.notifyDataSetChanged()
                }
            }
        }
        //fillContacts()
        amb.contactsLv.adapter = contactAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) =

        if (item.itemId == R.id.viewMi) {

            Intent(this, ContactActivity::class.java).let {
                carl.launch(it)
            }
            true
        } else
            false
//    private fun fillContacts() {
//
//        for (i in 1..10) {
//            contactList.add(
//                Contact(
//                    i,
//                    "Name $i",
//                    "Address $i",
//                    "Phone $i",
//                    "Email $i"
//                )
//            )
//        }
//    }
}