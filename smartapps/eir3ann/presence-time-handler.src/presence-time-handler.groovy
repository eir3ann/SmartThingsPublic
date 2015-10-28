/**
 *  Presence Time Handler
 *
 *  Copyright 2015 Chris Brosnan
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Presence Time Handler",
    namespace: "eir3ann",
    author: "Chris Brosnan",
    description: "Trigger Routine based on Presence, Mode & Time",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
	section("Presence Time Handler") {
		paragraph "Run Routine Based on Presence, Mode & TimeTEST"
	}
}

def installed() {
    log.debug "Installed with settings: ${settings}"
    initialize()
}

def updated(settings) {
    log.debug "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}

def initialize() {
	subscribe(location, modeChangeHandler)
}

def modeChangeHandler(evt) {
    log.debug "mode changed to ${evt.value}"
	if (evt.value == "Home") {
    	log.debug "mode home handler"
        if(getSunriseAndSunset().sunrise.time < now() && getSunriseAndSunset().sunset.time > now()){
            log.debug "routine home day"
            location.helloHome?.execute("Home Day")
            sendNotificationEvent("Home Day routine run")
        }else {
            log.debug "routine home night"
            location.helloHome?.execute("Home Night")
            sendNotificationEvent("Home Night routine run")
        }
	}else if (evt.value == "Away") {
    	log.debug "mode away handler"
        if(getSunriseAndSunset().sunrise.time < now() && getSunriseAndSunset().sunset.time > now()){
            log.debug "routine away day"
            location.helloHome?.execute("Away Day")
            sendNotificationEvent("Away Day routine run")
        }else {
            log.debug "routine away night"
            location.helloHome?.execute("Away Night")
            sendNotificationEvent("Away Night routine run")
        }
	}
}
