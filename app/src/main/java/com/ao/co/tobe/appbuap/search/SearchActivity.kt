package com.ao.co.tobe.appbuap.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ao.co.tobe.appbuap.AppBuap
import com.ao.co.tobe.appbuap.MenuActivity
import com.ao.co.tobe.appbuap.controllers.MainViewModel
import com.ao.co.tobe.appbuap.controllers.type
import com.ao.co.tobe.appbuap.databinding.ActivitySearchBinding
import com.ao.co.tobe.appbuap.models.MovimentosModel
import com.ao.co.tobe.appbuap.search.ui.main.*
import com.ao.co.tobe.appbuap.view_models.MovimentoLocalViewModel
import com.ao.co.tobe.appbuap.view_models.MovimentoLocalViewModelFactory
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class SearchActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!
    private val viewMovimentoPlace: MovimentoLocalViewModel by viewModels() {
        MovimentoLocalViewModelFactory((application as AppBuap).database.movimentoDao())
    }
    val decimalFormat : DecimalFormat = DecimalFormat("#,###.00", DecimalFormatSymbols.getInstance(
        Locale.GERMAN))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sectionsPagerAdapter = SectionsPagerAdapter(this.supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        sectionsPagerAdapter.AddFragment(PlaceholderFragment(),"Dinheiro")
        sectionsPagerAdapter.AddFragment(PlaceMultcaixaFragment(),"Multicaixa")

        //val sectionsPagerAdapter = PagerSearchAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        viewMovimentoPlace.getLastMovimentoInCash()
        viewMovimentoPlace.getLastMovimentoInMulticaixa()

        viewModel.isCash.observe(this){
            println(it)
        }
       tabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
           @SuppressLint("SetTextI18n")
           override fun onTabSelected(tab: TabLayout.Tab?) {
                   if (tab!!.position == 0) {
                   viewMovimentoPlace.viewMovimentoCash.observe(this@SearchActivity) { value ->
                       if (value != null)
                       binding.textSaldo.text = "${decimalFormat.format(value.saldoApos.toString().toDouble())} AKZ"
                       else
                           binding.textSaldo.text = "0,00 AKZ"

                   }

               }else {
                   viewMovimentoPlace.viewMovimentoMult.observe(this@SearchActivity) { value ->
                       if (value != null)
                       binding.textSaldo.text = "${decimalFormat.format(value.saldoApos.toString().toDouble())} AKZ"
                       else
                           binding.textSaldo.text = "0,00 AKZ"
                   }
               }
               type = tab.text.toString()
           }
           override fun onTabUnselected(tab: TabLayout.Tab?) {}

           override fun onTabReselected(tab: TabLayout.Tab?) {}

       })
        type = "Dinheiro"
        binding.arrowTitle.setOnClickListener {
            startActivity(Intent(this,MenuActivity::class.java))
            finish()
        }

        viewMovimentoPlace.viewMovimentoCash.observe(this){ value->
            if (value != null) {
                binding.textSaldo.text = "${decimalFormat.format(value.saldoApos.toString().toDouble())} AKZ"

            }
        }
    }

}