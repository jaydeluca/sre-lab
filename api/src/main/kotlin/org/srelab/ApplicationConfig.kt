package org.srelab

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory

class ApplicationConfig : Configuration() {
    var template: String? = null
    var defaultName: String = "SRE-lab"
    var usersClient: String = ""

    private var database: DataSourceFactory = DataSourceFactory()

    @JsonProperty("database")
    fun setDataSourceFactory(factory: DataSourceFactory) {
        database = factory
    }

    @JsonProperty("database")
    fun getDataSourceFactory(): DataSourceFactory {
        return database
    }
}
