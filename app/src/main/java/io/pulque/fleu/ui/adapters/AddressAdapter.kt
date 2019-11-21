package io.pulque.fleu.ui.adapters

import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.pulque.fleu.R
import kotlinx.android.synthetic.main.item_address.view.*
import kotlinx.android.synthetic.main.item_adress_footer.view.*

private const val TYPE_ADDRESS = 0
private const val TYPE_FOOTER = 1

class AddressAdapter(private val chooseOnMapListener: () -> Unit = {}, private val onAddressSelected: (Address) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var addressList: List<Address> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_ADDRESS -> ItemAddressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false))
            TYPE_FOOTER -> FooterAddressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_adress_footer, parent, false))
            else -> ItemAddressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false))
        }

    }

    override fun getItemCount() = addressList.count() + 1

    override fun getItemViewType(position: Int): Int {
        return if (position in 0 until addressList.count()) TYPE_ADDRESS else TYPE_FOOTER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ItemAddressViewHolder -> holder.bind(addressList[position], onAddressSelected)
            is FooterAddressViewHolder -> holder.bind(chooseOnMapListener)
        }
    }

    inner class ItemAddressViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(address: Address, onAddressSelected: (Address) -> Unit) = with(itemView){
            street.text = address.thoroughfare
            addressComplement.text = address.getAddressLine(0)

            setOnClickListener{
                onAddressSelected(address)
            }
        }
    }

    inner class FooterAddressViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(chooseOnMapListener: () -> Unit) = with(itemView){
            chooseMap.setOnClickListener{
                chooseOnMapListener()
            }
        }
    }

}
