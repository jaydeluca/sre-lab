package org.srelab

import io.dropwizard.Configuration;

class ApplicationConfig : Configuration() {
    var template : String? = null
    var defaultName : String = "SRE-lab"
}