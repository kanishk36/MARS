package com.mars.models.dashboard

import com.mars.models.GeoLocationModel
import com.mars.network.APIResponse

class GeoLocationResponse: APIResponse() {
    lateinit var results: ArrayList<GeoLocationModel>
    lateinit var status: String
}