package com.ao.co.tobe.appbuap.payment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ao.co.tobe.appbuap.AppBuap
import com.ao.co.tobe.appbuap.MenuActivity
import com.ao.co.tobe.appbuap.R
import com.google.gson.Gson
import com.nexgo.oaf.apiv3.SdkResult
import com.nexgo.oaf.apiv3.device.scanner.OnScannerListener
import com.nexgo.oaf.apiv3.device.scanner.Scanner
import com.nexgo.oaf.apiv3.device.scanner.ScannerCfgEntity
import com.ao.co.tobe.appbuap.controllers.MainViewModel
import com.ao.co.tobe.appbuap.controllers.deviceEng
import com.ao.co.tobe.appbuap.databinding.ActivityPaymentBinding
import com.ao.co.tobe.appbuap.models.ScannerModel
import com.ao.co.tobe.appbuap.utils.RealPathUtil
import com.ao.co.tobe.appbuap.view_models.MovimentoLocalViewModel
import com.ao.co.tobe.appbuap.view_models.MovimentoLocalViewModelFactory
import com.ao.co.tobe.solucaotpe.views_models.print.TicketPagmentPrint
import com.nexgo.oaf.apiv3.device.printer.Printer
import es.dmoral.toasty.Toasty
import java.io.ByteArrayOutputStream
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class PaymentActivity : AppCompatActivity(),OnClickListener,OnScannerListener {
    private var _binding: ActivityPaymentBinding? = null
    private  val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()
    private val viewMovimentoPlace: MovimentoLocalViewModel by viewModels() {
        MovimentoLocalViewModelFactory((this.application as AppBuap).database.movimentoDao())}
    private lateinit var scanner : Scanner
    private lateinit var cfgEntity: ScannerCfgEntity
    private var ourRequestCode: Int = 123
    var print: TicketPagmentPrint = TicketPagmentPrint()
    private var image: ByteArray = ByteArray(0)
    private var decimalFormat : DecimalFormat = DecimalFormat("#,###.00", DecimalFormatSymbols.getInstance(
        Locale.GERMAN))
    lateinit var path: ByteArray
    private lateinit var bitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            viewModel = this@PaymentActivity.viewModel
        }
        scanner = deviceEng!!.scanner
        cfgEntity = ScannerCfgEntity()
        cfgEntity.isAutoFocus = true
        cfgEntity.isUsedFrontCcd = false
        scanner.initScanner(cfgEntity,this@PaymentActivity)

        val items = listOf("Dinheiro", "Multicaixa",)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        binding.typePay.setAdapter(adapter)

        viewModel.isScanner.observe(this){
            if (!it.equals("")){
                binding.linearValuePay.visibility = View.VISIBLE
               ScannerSucesso(it.toString())
                scanner.stopScan()
            }
        }

        viewModel.payTypeView.observe(this){ value ->
           if (value.equals("Multicaixa")) {
               binding.takeFoto.visibility = View.VISIBLE
               binding.valoPagoPay.editText!!.setText(binding.valorPay.editText!!.text.toString())
               binding.trocoPay.editText!!.setText("00")
               viewMovimentoPlace.getLastMovimento("Multicaixa")
           } else if(value.equals("Dinheiro")) {
               binding.takeFoto.visibility = View.GONE
               viewMovimentoPlace.getLastMovimento("Dinheiro")
           }
        }
        binding.takeFoto.setOnClickListener(this)
        binding.payBtn.setOnClickListener(this)
        binding.submeterPayBtn.setOnClickListener(this)


        binding.valoPagoPay.editText!!.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.valoPagoPay.editText!!.text.isNotEmpty()){
                    val calculo = calcularTroco(
                        binding.valorPay.editText!!.text.toString().toDouble(),
                        binding.valoPagoPay.editText!!.text.toString().toDouble()
                    )
                    if (calculo >= "0")
                         binding.trocoPay.editText!!.setText(calculo)
                    else
                        binding.trocoPay.editText!!.setText("0,00")
                }else{
                    binding.valoPagoPay.editText!!.setText("0")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun ScannerSucesso(data: String){
        val gson = Gson()
        val objectList = gson.fromJson(data,ScannerModel::class.java)

        binding.ordemPay.editText!!.setText(objectList.Operacao)
        binding.rupePay.editText!!.setText(objectList.Data)
        binding.valorPay.editText!!.setText(objectList.Operador)

    }

    private  fun takePhoto(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED ){
                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE),ourRequestCode)
        }else{
            ActivityCompat.requestPermissions(this,
                mutableListOf<String>(Manifest.permission.CAMERA).toTypedArray(),1)
        }
    }

    private fun AddMovimento(idOrdem: String,rupe:String,valor:String,pago:String,troco:String,forma:String
                             ,imagem:ByteArray,saldoAnterior:String,saldoApos:String,
                             idLocal:String, description:String,isOnline:String,idUser:String,
                             integration:String,lastData:String){
            viewMovimentoPlace.addMovimento(idOrdem, rupe, valor,pago,troco, forma, imagem, saldoAnterior, saldoApos, idLocal, description, isOnline, idUser, integration, lastData)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ourRequestCode && resultCode == RESULT_OK) {
            try {
                bitmap = data?.extras!!.get("data") as  Bitmap
                binding.ticketMulticaixa.setImageBitmap(bitmap)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                image = stream.toByteArray()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun addPaymentPlace(ordemPay:String,rupePay:String,valoPay:String,valorPago:String,
                                trocoPay:String,typePay:String){
        var saldoAnterior = "0"
        var saldoApos = "0"
        viewMovimentoPlace.viewMovimento.observe(this) { mov ->
            println(mov)
            if (mov != null) {
                if (mov.isOnline.equals("1") && mov.lastUpdateIntegration.equals("false")) {
                    saldoAnterior = "0,00"
                    saldoApos = valoPay
                } else {
                    saldoAnterior = mov.saldoApos.toString()
                    saldoApos =
                        "${mov.saldoApos.toString().toDouble() + valoPay.toDouble()}"
                }

                AddMovimento(
                    ordemPay,
                    rupePay,
                    valoPay,
                    valorPago,
                    trocoPay,
                    typePay,
                    image,
                    saldoAnterior,
                    saldoApos,
                    "1",
                    "referencia",
                    "0",
                    "1013",
                    "true",
                    "08-12-2022"
                )

                viewMovimentoPlace.isSucesso.observe(this){ value ->
                    if (value) {
                        print.printReceipt(
                            this@PaymentActivity,
                            binding.ordemPay.editText!!.text.toString(),
                            binding.rupePay.editText!!.text.toString(),
                            binding.valorPay.editText!!.text.toString(),
                            binding.valoPagoPay.editText!!.text.toString(),
                            binding.trocoPay.editText!!.text.toString(),
                            binding.typePay.text.toString()
                        )
                        startActivity(Intent(this,MenuActivity::class.java))
                    }
                }
            }else{
                AddMovimento(
                    ordemPay,
                    rupePay,
                    valoPay,
                    valorPago,
                    trocoPay,
                    typePay,
                    image,
                    valoPay,
                    valoPay,
                    "1",
                    "referencia",
                    "0",
                    "1013",
                    "true",
                    "08-12-2022"
                )

                viewMovimentoPlace.isSucesso.observe(this){ value ->
                    if (value) {
                        print.printReceipt(
                            this@PaymentActivity,
                            ordemPay,
                            rupePay,
                            valoPay,
                            valorPago,
                            trocoPay,
                            typePay
                        )
                        startActivity(Intent(this,MenuActivity::class.java))
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.take_foto ->{
                if (binding.ordemPay.editText!!.text.isEmpty())
                    Toasty.error(this@PaymentActivity,"Ordem de pagamento vazio",Toast.LENGTH_LONG).show()
                else
                takePhoto()
            }
            R.id.pay_btn ->{
                scanner.startScan(60,this@PaymentActivity)
            }
            R.id.submeter_pay_btn ->{
                if (binding.valoPagoPay.editText!!.text.isEmpty() ||  viewModel.payTypeView.value == "" ) {
                    Toasty.error(this@PaymentActivity,"Campos vazios",Toast.LENGTH_LONG).show()
                } else {
                    addPaymentPlace(binding.ordemPay.editText!!.text.toString(),
                        binding.rupePay.editText!!.text.toString(),
                        binding.valorPay.editText!!.text.toString(),
                        binding.valoPagoPay.editText!!.text.toString(),
                        binding.trocoPay.editText!!.text.toString(),
                        binding.typePay.text.toString())
                }
            }
        }
    }

    private fun calcularTroco(conta: Double,pago:Double): String{
        if (pago < conta)
            return decimalFormat.format(pago - conta)
        else {
            val nota = listOf<Int>(200,100,50,20,10,5)
            val centavos = listOf<Int>(50,25,10,5,2,1)
            var result = ""
            var troco = 0.0
            var i = 0
            var vlr: Int = 0
            var ct = 0
            troco = (pago - conta)
            result = decimalFormat.format(troco)
            vlr = troco.toInt()


            vlr = Math.round((troco - troco.toInt())).toInt()
            i = 0
            while (vlr != 0) {
                ct = vlr / centavos[i] // calculando a qtde de moedas
                if (ct != 0) {
                    result += "$ct"
                    vlr %= centavos[i] // sobra
                }
                i += 1 // próximo centavo
            }
            return result
        }
    }

    override fun onInitResult(p0: Int) {}

    override fun onScannerResult(retCode: Int, data: String?) {
        when (retCode) {
            SdkResult.Success -> runOnUiThread {
                viewModel.isScanner.postValue(data!!)
            }
            SdkResult.TimeOut -> runOnUiThread {
                Toast.makeText(
                    this@PaymentActivity,
                    "Tempo terminado",
                    Toast.LENGTH_SHORT
                ).show()
            }
            SdkResult.Scanner_Customer_Exit -> runOnUiThread {
                Toast.makeText(
                    this@PaymentActivity,
                    "Saiu",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> runOnUiThread {
                Toast.makeText(
                    this@PaymentActivity,
                    "Algo deu errado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}