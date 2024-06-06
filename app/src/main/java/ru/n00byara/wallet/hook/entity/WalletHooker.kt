package ru.n00byara.wallet.hook.entity

import android.app.Activity
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType

object WalletHooker : YukiBaseHooker() {
    private val MainActivityClass by lazyClassOrNull("ru.cardsmobile.mw3.product.cardholder.main.presentation.ui.MainActivity")
    private val AntiTamperingClass by lazyClassOrNull("ru.tinkoff.core.components.security.security.AntiTampering")

    override fun onHook() {
        MainActivityClass?.let {
            it.method {
                name = "onCreate"
                param(BundleClass)
            }.hook().before {
                val instance = instance as Activity
                instance.intent?.let {
                    it.putExtra("ignore_root", true)
                }
            }
        }

        AntiTamperingClass?.let {
            // Для разработчиков. Отключение проверки на наличие Frida
            it.method {
                name = "checkFridaLibrariesExist"
                returnType = BooleanType
            }.hook().replaceToFalse()
        }
    }
}