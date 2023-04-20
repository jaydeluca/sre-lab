package org.srelab.guice

import com.google.inject.Provides
import com.google.inject.Singleton
import dev.misfitlabs.kotlinguice4.KotlinModule
import org.hibernate.SessionFactory
import org.srelab.dao.OrderDao

class DaoModule(private val sessionFactory: SessionFactory) : KotlinModule() {

    override fun configure() = Unit

    @Provides
    @Singleton
    fun provideOrderDao(): OrderDao {
        return OrderDao(sessionFactory)
    }

}