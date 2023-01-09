package com.ao.co.tobe.appbuap.search.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ao.co.tobe.appbuap.AppBuap
import com.ao.co.tobe.appbuap.controllers.MainViewModel
import com.ao.co.tobe.appbuap.controllers.type
import com.ao.co.tobe.appbuap.databinding.FragmentSearchBinding
import com.ao.co.tobe.appbuap.models.MovimentosModel
import com.ao.co.tobe.appbuap.search.ConsultaAdapter
import com.ao.co.tobe.appbuap.view_models.MovimentoLocalViewModel
import com.ao.co.tobe.appbuap.view_models.MovimentoLocalViewModelFactory
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceMultcaixaFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private val viewModel by viewModels<MainViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val adapter = ConsultaAdapter()
    var listMovimento : MutableList<MovimentosModel> = mutableListOf()
    private val viewMovimentoPlace: MovimentoLocalViewModel by viewModels() {
        MovimentoLocalViewModelFactory((activity?.application as AppBuap).database.movimentoDao())}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        getMovimentoLocal("Multicaixa")
        binding.recyclerMovCahs.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMovCahs.adapter = adapter

        return  binding.root
    }


    private fun getMovimentoLocal(type:String){
        var movimento_model: MovimentosModel? =null
        var listMovimento : MutableList<MovimentosModel> = mutableListOf()
        viewMovimentoPlace.allMovimento(type).observe(requireActivity()){ allMov ->
            allMov.forEach {
                movimento_model = MovimentosModel(it.lastUpdate.toString(),it.id_orden.toString(),it.description.toString(),it.lastUpdate.toString(),
                    it.numero_rupe.toString(),it.saldoAnterior!!.toDouble(),it.valor!!.toDouble())
                listMovimento.add(movimento_model!!)
            }
            adapter.submitList(listMovimento)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}