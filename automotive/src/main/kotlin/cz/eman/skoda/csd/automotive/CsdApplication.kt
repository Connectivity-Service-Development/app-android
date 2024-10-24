package cz.eman.skoda.csd.automotive

import android.app.Application
import cz.eman.skoda.csd.automotive.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

class CsdApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CsdApplication)
            modules(
                defaultModule,
                AppModule().module,
            )
        }
    }
}
