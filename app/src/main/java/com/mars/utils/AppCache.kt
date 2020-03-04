package com.mars.utils

import com.mars.models.UserInfo
import java.util.concurrent.ConcurrentHashMap

enum class AppCache {

    INSTANCE;

    private var appCache: MutableMap<String, Any>? = ConcurrentHashMap()
    private lateinit var mUserInfo: UserInfo

    /**
     * Thsi method return the data stored in hashmap
     *
     * @return data Actual data
     */
    val data: Map<String, Any>?
        get() = appCache

    /**
     * This will allow to set shared variable
     */
    fun addToAppCache(key: String, value: Any) {
        if (appCache != null) {
            setData(key, value)
        }
    }

    /**
     * This will give the data mapped with particular key
     */
    fun getValueOfAppCache(key: String): Any? {
        return if (appCache != null) {
            if (data != null) {
                data!![key]
            } else {
                null
            }
        } else {
            null
        }
    }

    /**
     * This function clear cache, and will retain the values mentioned in retainData()
     */


    /**
     * Set the data in the hashmap
     *
     * @param className class name where data is located
     * @param data      actual data returned from webservice
     */
    fun setData(className: String, data: Any?) {
        if (data != null) {
            appCache!![className] = data
        }
    }

    /**
     * This function clear whole cache.
     */
    fun clearCache() {
        if (appCache != null) {
            appCache!!.clear()
        }
    }

    /**
     * This will delete cache object by sending key
     */
    fun deleteCacheObject(key: String) {
        if (appCache != null) {
            appCache!!.remove(key)
        }
    }

    fun containsKey(key: String): Boolean {
        if(appCache != null) {
            return data?.containsKey(key)!!
        }

        return false
    }

    fun setUserInfo(userInfo: UserInfo) {
        mUserInfo = userInfo
    }

    fun getUserInfo() : UserInfo {
        return mUserInfo
    }

}
