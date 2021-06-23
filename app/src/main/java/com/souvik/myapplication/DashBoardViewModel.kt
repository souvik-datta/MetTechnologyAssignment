package com.souvik.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.souvik.myapplication.Model.ProductDetails
import com.souvik.myapplication.Network.ApiClient
import com.souvik.myapplication.Utils.Resource

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashBoardViewModel : ViewModel() {

    private val _dataList = MutableLiveData<Resource<ProductDetails>>()
    val dataList: LiveData<Resource<ProductDetails>>
        get() = _dataList

    fun getProductDetails(data: HashMap<String, String>) {
        _dataList.value = Resource.loading(null)
        val result = ApiClient.apiInterface?.getProductDetailsApi(data)
        result?.enqueue(object : Callback<ProductDetails> {
            override fun onResponse(
                call: Call<ProductDetails>,
                response: Response<ProductDetails>
            ) {
                Log.d("TAG", "onResponse: ${response.body()}")
                if(response.body()?.status?.message.equals("Success"))
                    _dataList.value = Resource.success(response.body())
                else
                    _dataList.value = Resource.error("Error Occurred!", null)
            }

            override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
                _dataList.value = Resource.error("Something went wrong!", null)
            }

        })
    }
}