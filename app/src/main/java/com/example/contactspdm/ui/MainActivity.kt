package com.example.contactspdm.ui


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
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.contactspdm.databinding.ActivityMainBinding
import com.example.contactspdm.model.Contact
import com.example.contactspdm.R

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var parl: ActivityResultLauncher<Intent>

    private val contactList: MutableList<Contact> = mutableListOf()

    class ContactAdapter(context: Context, contacts: List<Contact>) : ArrayAdapter<Contact>(context, android.R.layout.simple_list_item_1, contacts) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val contact = getItem(position)
            val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
            view.findViewById<TextView>(android.R.id.text1).text = contact?.name + '\n' + contact?.phone
            return view
        }
    }

    private val contactAdapter : ContactAdapter by lazy{
        ContactAdapter(this, contactList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        amb.toolbarIn.apply {
            setSupportActionBar(this.toolbar)
        }

        amb.contactsLv.adapter = contactAdapter

        parl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val lastId = if (contactList.isNotEmpty()) {
                    contactList.last().id
                } else {
                    1
                }

                result.data?.getBundleExtra("bundle")?.let {
                    val name = it.getString("name")
                    val address = it.getString("address")
                    val phone = it.getString("phone")
                    val email = it.getString("email")
                    if (!name.isNullOrBlank() && !phone.isNullOrBlank() && !email.isNullOrBlank() && !address.isNullOrBlank()) {
                        contactList.add(
                            Contact(lastId + 1, name, address, phone, email)
                        )
                        (contactAdapter as? ArrayAdapter<Contact>)?.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            this,
                            "Há dados obrigatórios que não foram preenchidos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        return when(item.itemId){
            R.id.viewMi -> {

                Intent(this, ContactActivity::class.java).also{
                    parl.launch(it)
                }
                true }

            else -> {false}
        }
    }
}