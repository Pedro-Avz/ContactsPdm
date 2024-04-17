package com.example.contactspdm.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.contactspdm.R
import com.example.contactspdm.databinding.TileContactBinding
import com.example.contactspdm.model.Contact

class ContactAdapter(context: Context, private val contactList: MutableList<Contact>):
    ArrayAdapter<Contact>(context, R.layout.tile_contact, contactList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val contact = contactList[position]

        var contactTileView = convertView
        if (contactTileView == null){
            contactTileView = LayoutInflater.from(context).inflate(
                R.layout.tile_contact,
                parent,
                false
            ).apply {
                tag = TileContactHolder(
                    findViewById(R.id.nameTv),
                    findViewById(R.id.emailTv)
                )
            }
        }
        (contactTileView?.tag as TileContactHolder).apply {
            nameTv.text = contact.name
            emailTv.text = contact.email
        }

        return contactTileView!!
    }
    private  data class TileContactHolder(val nameTv: TextView, val emailTv: TextView)
}