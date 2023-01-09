package com.ao.co.tobe.appbuap.view_models

import androidx.lifecycle.*
import com.ao.co.tobe.appbuap.data.place.dao.DaoMovimento
import com.ao.co.tobe.appbuap.models.Movimentos
import kotlinx.coroutines.launch

class MovimentoLocalViewModel(private val movimentoDao: DaoMovimento): ViewModel() {
    private var movimento: Movimentos? = null
    var viewMovimentoCash = MutableLiveData(movimento)
    var viewMovimentoMult = MutableLiveData(movimento)
    var viewMovimento = MutableLiveData(movimento)
    var isSucesso = MutableLiveData(false)

    fun allMovimento(type: String): LiveData<List<Movimentos>>{
        return movimentoDao.getMovimentos(type).asLiveData()
    }

    val maxIdCash : Int = movimentoDao.findLastMovimentoId("Dinheiro")
    val maxIdMulticaixa : Int = movimentoDao.findLastMovimentoId("Multicaixa")

    private val lastMovimentoInCash : Movimentos = movimentoDao.getMovimento(maxIdCash)
    private val lastMovimentoInMult : Movimentos = movimentoDao.getMovimento(maxIdMulticaixa)

    private fun maxId(type:String):Int{
      return  movimentoDao.findLastMovimentoId(type)
    }

    private fun lastMoviment(type:String):Movimentos{
        return  movimentoDao.getMovimento(maxId(type))
    }

    private fun insertMovimento(movimentos: Movimentos) {
        viewModelScope.launch {
            movimentoDao.insert(movimentos)
        }
        isSucesso.postValue(true)
    }

    fun getLastMovimento(type:String){
        viewMovimento.postValue(lastMoviment(type))
    }

    fun getLastMovimentoInCash(){
        viewMovimentoCash.postValue(lastMovimentoInCash)
    }

    fun getLastMovimentoInMulticaixa(){
        viewMovimentoMult.postValue(lastMovimentoInMult)
    }

    private fun deleteMovimento(){
        viewModelScope.launch {
            movimentoDao.delete()
        }
    }


    fun addMovimento(idOrdem: String,rupe:String,valor:String,pago:String,troco:String, forma:String
                     ,imagem:ByteArray,saldoAnterior:String,saldoApos:String,
                     idLocal:String, description:String,isOnline:String,idUser:String,
                     integration:String,lastData:String) {
        val newMovimento = getNewMovimentoEntry(idOrdem, rupe, valor,pago,troco, forma, imagem, saldoAnterior,
            saldoApos, idLocal, description,isOnline, idUser, integration, lastData)

        insertMovimento(newMovimento)

    }

     fun updateMovimento(nRecibo: String,isOnline:String,
                         integration:String,lastData:String) {
        viewModelScope.launch {
            movimentoDao.update(nRecibo, isOnline, integration, lastData)
        }
    }

    fun deleteMovimentos(){
        deleteMovimento()
    }


    private fun getNewMovimentoEntry(idOrdem: String,rupe:String,valor:String,pago:String,troco:String,forma:String
                                     ,imagem:ByteArray,saldoAnterior:String,saldoApos:String,
                                 idLocal:String, description:String,isOnline:String,idUser:String,
                                 integration:String,lastData:String): Movimentos {
        return Movimentos(
            id_orden = idOrdem,
            numero_rupe = rupe,
            valor = valor,
            pago = pago,
            troco = troco,
            forma = forma,
            image = imagem,
            saldoAnterior = saldoAnterior ,
            saldoApos = saldoApos,
            IdLocal = idLocal,
            description = description,
            isOnline = isOnline,
            IdUser = idUser,
            lastUpdateIntegration = integration,
            lastUpdate = lastData
        )
    }
}

class MovimentoLocalViewModelFactory(private val movimentoDao: DaoMovimento) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovimentoLocalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovimentoLocalViewModel(movimentoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}