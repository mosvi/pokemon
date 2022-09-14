package com.example.iquii_test.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iquii_test.ui.home.HomeFragment
import com.example.iquii_test.utils.Api
import com.example.iquii_test.utils.RVItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class DetailViewModel : ViewModel() {

    private val TAG = "Detail-ViewModel"

    private var _image = MutableLiveData<String>()
    private var _name = MutableLiveData<String>()

    private var _hp = MutableLiveData<Int>()
    private var _attack = MutableLiveData<Int>()
    private var _defense = MutableLiveData<Int>()
    private var _spAttack = MutableLiveData<Int>()
    private var _spDefense = MutableLiveData<Int>()
    private var _speed = MutableLiveData<Int>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getAllPokemon()
    }

    private fun getAllPokemon() {

        //With Kotlin coroutines
        coroutineScope.launch {
            var getStoresResponse = Api.WebService.getPoke(HomeFragment.newURL)
            try {

                val serviceResponse = getStoresResponse.await()
                val obj: JSONObject = JSONObject(serviceResponse.body().toString())

                //from here it is being parsed the json
                //to get required data

                _image.value = obj.getJSONObject("sprites").getString("front_default")

                _name.value = obj.getString("name")

                _hp.value = obj.getJSONArray("stats").getJSONObject(0).getInt("base_stat")

                _attack.value = obj.getJSONArray("stats").getJSONObject(1).getInt("base_stat")

                _defense.value = obj.getJSONArray("stats").getJSONObject(2).getInt("base_stat")

                _spAttack.value = obj.getJSONArray("stats").getJSONObject(3).getInt("base_stat")

                _spDefense.value = obj.getJSONArray("stats").getJSONObject(4).getInt("base_stat")

                _speed.value = obj.getJSONArray("stats").getJSONObject(5).getInt("base_stat")

            } catch (e: Exception) {
                Log.i(TAG, "error: ${e.message} ")
            }
        }
    }



    val image: LiveData<String>
    get() = _image

    val name: LiveData<String>
        get() = _name

    val hp: LiveData<Int>
        get() = _hp

    val attack: LiveData<Int>
        get() = _attack

    val defense: LiveData<Int>
        get() = _defense

    val spAttack: LiveData<Int>
        get() = _spAttack

    val spDefense: LiveData<Int>
        get() = _spDefense

    val speed: LiveData<Int>
        get() = _speed


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}