package ru.n00byara.wallet.hook

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import ru.n00byara.wallet.hook.entity.WalletHooker

@InjectYukiHookWithXposed
object HookEntry : IYukiHookXposedInit {
    override fun onInit() = configs {
        debugLog { tag = "Wallet" }
        isDebug = false
        isEnableDataChannel = false
    }

    override fun onHook() = encase {
        loadApp(name = "ru.cardsmobile.mw3") {
            loadHooker(WalletHooker)
        }
    }
}