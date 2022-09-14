package com.example.iquii_test.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iquii_test.database.Pokemon
import com.example.iquii_test.utils.Api
import com.example.iquii_test.utils.RVItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class HomeViewModel : ViewModel() {

    private val TAG = "Home-ViewModel"
    private var _dataList = MutableLiveData<ArrayList<RVItemModel>>()
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getAllPokemon()
    }

    private fun getAllPokemon() {

        //With Kotlin coroutines
        coroutineScope.launch {
            var getStoresResponse = Api.WebService.getPokemon()
            try {
                val serviceResponse = getStoresResponse.await()
                val obj: JSONObject = JSONObject(serviceResponse.body().toString())
                val arr: JSONArray = obj.getJSONArray("results")

                parseData(arr)

                /*_response.value = serviceResponse.body()?.let {
                                JSONObject(it).getJSONArray("results")
                            }!!*/
                //Log.d(TAG,"" + serviceResponse.body())) as JSONArray

            } catch (e: Exception) {
                Log.i(TAG, "error: ${e.message} ")
            }
        }
    }

    private fun parseData(array: JSONArray){

        val list: ArrayList<RVItemModel> = ArrayList<RVItemModel>()

        for (i in 0 until array.length()) {
            val name = array.getJSONObject(i).getString("name")
            val url = array.getJSONObject(i).getString("url")

            //adding pokemon list that are showing on home fragment screen
            list.add(
                RVItemModel("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${i+1}.png",
                name, url, i+1,false)
            )

        }

        _dataList.value = list

    }

    val dataList: LiveData<ArrayList<RVItemModel>>
        get() = _dataList


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}