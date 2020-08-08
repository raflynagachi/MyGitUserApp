package com.dicoding.picodiploma.mygituserapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.dicoding.picodiploma.mygituserapp.BuildConfig
import com.dicoding.picodiploma.mygituserapp.model.Following
import org.json.JSONArray
import org.json.JSONObject

class FollowingViewModel: ViewModel() {
    companion object{
        val TAG = FollowerViewModel::class.java.simpleName
    }

    private val listUserMutable = MutableLiveData<ArrayList<Following>>()
    private val listUserNonMutable = ArrayList<Following>()

    fun getListUser(): LiveData<ArrayList<Following>> {
        return listUserMutable
    }

    fun getDataUser(context: Context, id: String){
        AndroidNetworking.get("https://api.github.com/users/{id}/following")
            .addPathParameter("id", id)
            .addHeaders("Authorization", "token $UserViewModel.API_KEY")
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    Log.d(TAG, response.toString())
                    try {
                        for (i in 0 until response.length()){
                            val username = response.getJSONObject(i).getString("login")
                            getDataUserDetail(username, context)
                        }
                    }catch (e: Exception){
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }

                override fun onError(error: ANError?) {
                    if (error?.errorCode != 0) {
                        // received error from server
                        // error.getErrorCode() - the error code from server
                        // error.getErrorBody() - the error body from server
                        // error.getErrorDetail() - just an error detail
                        Log.d(TAG, "onError errorCode : " + error?.errorCode)
                        Log.d(TAG, "onError errorBody : " + error?.errorBody)
                        Log.d(TAG, "onError errorDetail : " + error?.errorDetail)
                    } else {
                        // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                        Log.d(TAG, "onError errorDetail : " + error.errorDetail)
                    }
                    Toast.makeText(context, error?.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getDataUserDetail(username: String, context: Context) {
        AndroidNetworking.get("https://api.github.com/users/{username}")
            .addPathParameter("username", username)
            .addHeaders("Authorization", "token $UserViewModel.API_KEY")
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d(TAG, response.toString())
                    try {
                        val jsonObject = response
                        val userData = Following()
                        userData.username = jsonObject.getString("login")
                        userData.fullname = jsonObject.getString("name")
                        userData.avatar = jsonObject.getString("avatar_url")
                        userData.company = jsonObject.getString("company")
                        userData.location = jsonObject.getString("location")
                        userData.repository = jsonObject.getString("public_repos")
                        userData.follower = jsonObject.getString("followers")
                        userData.following = jsonObject.getString("following")
                        listUserNonMutable.add(userData)
                        listUserMutable.postValue(listUserNonMutable)
                    }catch (e: Exception){
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }

                override fun onError(error: ANError?) {
                    if (error?.errorCode != 0) {
                        // received error from server
                        // error.getErrorCode() - the error code from server
                        // error.getErrorBody() - the error body from server
                        // error.getErrorDetail() - just an error detail
                        Log.d(TAG, "onError errorCode : " + error?.errorCode)
                        Log.d(TAG, "onError errorBody : " + error?.errorBody)
                        Log.d(TAG, "onError errorDetail : " + error?.errorDetail)
                    } else {
                        // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                        Log.d(TAG, "onError errorDetail : " + error.errorDetail)
                    }
                    Toast.makeText(context, error?.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}