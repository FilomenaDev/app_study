package com.ao.co.tobe.appbuap.search


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ao.co.tobe.appbuap.databinding.ItemSearchBinding
import com.ao.co.tobe.appbuap.models.MovimentosModel
import com.orhanobut.hawk.Hawk
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale

class ConsultaAdapter: ListAdapter<MovimentosModel, ConsultaAdapter.ItemViewHolder>(DiffCallback) {
    class ItemViewHolder(private var binding: ItemSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(taxa: MovimentosModel) {
        val decimalFormat : DecimalFormat = DecimalFormat("#,###.00", DecimalFormatSymbols.getInstance(Locale.GERMAN))
        var dateTimeFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val numCard = "${taxa.NumeroCartao}"

        var dataFinal = ""
        if (taxa.DataMov.contains(".")){
            var data: List<String> = taxa.DataMov.split("T")
            var data2: List<String> = data[0].split("-")
            var dataHora: List<String> = data[1].split(".")
             dataFinal =  data2[2] + "-" + data2[1] +"-"+ data2[0] + " " + dataHora[0]
        }else{
            dataFinal = taxa.DataMov
        }

            binding.moveData.text = dataFinal
            binding.moveTaxa.text = "${decimalFormat.format(taxa.Valor)} AKZ"
            binding.moveType.text = taxa.DescricaoMov
            binding.moveNumberCard.text = "Rupe : ${taxa.NumeroCartao}"

    }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {

        }
        holder.bind(current)

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MovimentosModel>() {
            override fun areItemsTheSame(oldItem: MovimentosModel, newItem: MovimentosModel): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: MovimentosModel, newItem: MovimentosModel): Boolean {
                return oldItem.NumeroRecibo == newItem.NumeroRecibo
            }
        }
    }
}