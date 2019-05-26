package cdd.group.echoers.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cdd.group.echoers.api.Api
import cdd.group.echoers.api.END_POINT
import cdd.group.echoers.entity.LoginResponse
import com.echoers.library.http.ApiFactory
import com.echoers.library.mvvm.viewmodel.AbsViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Raphael Zhang
 * @project echoers.and
 * @package cdd.group.echoers.mvvm.viewmodel
 * @date    2019-05-23 22:25
 * @author  Raphael Zhang
 * @discription  登录ViewModel
 * @org     cdd.group
 * @email   raphael_zhang@echoers.cn
 **/
class LoginViewModel(context: Context): BaseViewModel(context) {

    private var loginResponse = MutableLiveData<LoginResponse>()

    fun getLoginResponse(): LiveData<LoginResponse> {
        return loginResponse
    }

    fun login() {
        viewModelScope.launch {
            startLoading()
            val login = withContext(IO) {
                repository.loginAsync("test006", "96e79218965eb72c92a549dd5a330112")
            }
            withContext(Main) {
                stopLoading()
                try {
                    val result = login.await()
                    if (result.code == 10000) {
                        loginResponse.value = result.results
                    } else {
                        toast(result.message)
                    }
                } catch (e: Exception) {
                    toast("${e.message}")
                    e.printStackTrace()
                }
            }
        }
    }
}