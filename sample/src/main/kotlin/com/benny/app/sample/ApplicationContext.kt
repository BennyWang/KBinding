package com.benny.app.sample

import android.content.Context
import com.benny.app.sample.network.service.caishuo.model.User
import com.benny.app.sample.utils.fromJson
import com.benny.app.sample.utils.toJson
import org.jetbrains.anko.defaultSharedPreferences
import java.util.*
import kotlin.properties.Delegates.observable
import kotlin.reflect.KProperty

/**
 * Created by benny on 9/5/15.
 */

object ApplicationContext {
    private lateinit var context: Context

    var user: User? by observable(null) { property: KProperty<*>, oldValue: User?, newValue: User? ->
        saveUser(newValue)
    }

    val userId: String?
        get() = user?.id ?: null

    val accessToken: String?
        get() = user?.accessToken ?: null

    val hasLogin: Boolean
        get() = user != null

    val version: String
        get() = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    val deviceId: String
        get() {
            var deviceId: String = UUID.randomUUID().toString()
            context.defaultSharedPreferences.edit().putString(Constants.PREF_DEVICE_UUID, deviceId).apply()
            return deviceId
        }

    fun init(context: Context) {
        this.context = context
        user = loadUser()
    }

    private fun loadUser(): User? {
        return fromJson(context.defaultSharedPreferences.getString(Constants.PREF_USER, ""), User::class.java)
    }

    private fun saveUser(user: User?) {
        context.defaultSharedPreferences.edit().putString(Constants.PREF_USER, if (user == null) "" else toJson(user)).apply()
    }
}
