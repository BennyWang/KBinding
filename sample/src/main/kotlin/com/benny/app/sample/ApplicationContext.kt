package com.benny.app.sample

import android.content.Context
import android.telephony.TelephonyManager
import com.benny.app.sample.extension.fromJson
import com.benny.app.sample.extension.toJson
import com.benny.app.sample.model.User
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

    public val hasLogin: Boolean
        get() = user != null

    public val version: String
        get() = context.packageManager.getPackageInfo(context.packageName, 0).versionName

    public val deviceId: String
        get() {
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var deviceId: String? = manager.deviceId
            if (deviceId.isNullOrBlank()) {
                val pref = context.defaultSharedPreferences
                deviceId = pref.getString(Constants.PREF_DEVICE_UUID, null)
                if (deviceId == null) {
                    deviceId = UUID.randomUUID().toString()
                    pref.edit().putString(Constants.PREF_DEVICE_UUID, deviceId).apply()
                }
            }
            return deviceId!!
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
