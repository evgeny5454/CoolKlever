package com.evgeny5454.testapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.evgeny5454.testapp.R
import com.evgeny5454.testapp.data.database.AppDao
import com.evgeny5454.testapp.data.entity.CharacterModel
import com.evgeny5454.testapp.data.network.ApiService
import com.evgeny5454.testapp.domain.repository.Repository
import io.reactivex.Single
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.text.SimpleDateFormat
import java.util.*


class RepositoryImpl(
    private val apiService: ApiService,
    private val context: Context,
    private val appDao: AppDao
) : Repository {
    private val calendar: Calendar = Calendar.getInstance()
    private val wifi =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
        )
    private val mobile =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).getNetworkInfo(
            ConnectivityManager.TYPE_MOBILE
        )
    private val connect: Boolean =
        (wifi?.state == NetworkInfo.State.CONNECTED) || (mobile?.state == NetworkInfo.State.CONNECTED)

    @SuppressLint("SimpleDateFormat")
    override suspend fun getCharacters(): Boolean {
        val timeStamp = calendar.time.time.toString()
        val publicKey = context.getString(R.string.public_key)
        val privateKey = context.getString(R.string.private_key)

        if (connect) {
            apiService.getCharacters(
                timeStamp = timeStamp,
                apikey = publicKey,
                hashMD5 = String(
                    Hex.encodeHex(
                        DigestUtils
                            .md5(timeStamp + privateKey + publicKey)
                    )
                )
            ).body()?.data?.results?.forEach {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-SSSS")
                val character = CharacterModel(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    imageUri = String.format(
                        context.getString(R.string.image_string),
                        it.thumbnail.path,
                        it.thumbnail.extension
                    ),
                    modified = format.parse(it.modified) ?: Date()
                )
                appDao.insertCharacter(character)
            }
            return true
        } else {
            return false
        }
    }

    override fun getData(): Single<List<CharacterModel>> {
        return appDao.getAllRecords()
    }
}