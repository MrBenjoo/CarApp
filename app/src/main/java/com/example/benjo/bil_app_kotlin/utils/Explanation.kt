package com.example.benjo.bil_app_kotlin.utils

import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.ANNAT
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.AUTOMAT
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.AUTOMAT_TILLSATS
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.BENSIN
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.BIODIESEL
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.DIESEL
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.EL
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.EMISSION_EEV
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.EMISSION_EL
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.EMISSION_EL_HYBDRID
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.EMISSION_EURO_4
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.EMISSION_EURO_5
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.EMISSION_EURO_6
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.EMISSION_LADD_HYBDRID
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.ETANOL
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.FOTOGEN
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.GENGAS
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.MANUELL
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.MANUELL_TILLSATS
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.METANGAS
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.MOTORGAS
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.STATUS_1
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.STATUS_2
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.STATUS_3
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.STATUS_4
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VARIOMATIC
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VATGAS
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_BUSS
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_LB
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_MC
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_MOPED
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_MRED
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_PB
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_SLAP
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_TGHJUL
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_TGSK
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_TGSL
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_TGSNO
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_TGV
import com.example.benjo.bil_app_kotlin.utils.ExplanationConstants.Companion.VEHICLE_TR


class Explanation {

    fun vehType(vehicle: String?): String? = when (vehicle) {
        "BUSS" -> VEHICLE_BUSS
        "LB" -> VEHICLE_LB
        "MC" -> VEHICLE_MC
        "MOPED" -> VEHICLE_MOPED
        "MRED" -> VEHICLE_MRED
        "PB" -> VEHICLE_PB
        "SLÄP" -> VEHICLE_SLAP
        "TGHJUL" -> VEHICLE_TGHJUL
        "TGSK" -> VEHICLE_TGSK
        "TGSL" -> VEHICLE_TGSL
        "TGSNÖ" -> VEHICLE_TGSNO
        "TGV" -> VEHICLE_TGV
        "TR" -> VEHICLE_TR
        else -> vehicle
    }

    fun statusType(status: String?): String? = when (status) {
        "1" -> STATUS_1
        "2" -> STATUS_2
        "3" -> STATUS_3
        "4" -> STATUS_4
        else -> status
    }

    fun fuelType(fuel: String?): String? = when (fuel) {
        "1" -> BENSIN
        "2" -> DIESEL
        "3" -> EL
        "4" -> FOTOGEN
        "6" -> GENGAS
        "7" -> ETANOL
        "9" -> MOTORGAS
        "16" -> METANGAS
        "17" -> VATGAS
        "18" -> ANNAT
        "19" -> BIODIESEL
        else -> fuel
    }

    fun transType(transmission: String?): String? = when (transmission) {
        "1" -> MANUELL
        "2" -> AUTOMAT
        "3" -> MANUELL_TILLSATS
        "4" -> AUTOMAT_TILLSATS
        "5" -> VARIOMATIC
        else -> transmission
    }

    fun boolType(bool: String?): String? = when (bool) {
        "true" -> "ja"
        "false" -> "nej"
        else -> bool
    }

    fun ecoClassType(ecoClass: String?): String? = when(ecoClass) {
        else -> ecoClass
    }

    fun emissionType(emission: String?): String? = when(emission) {
        "1" -> EMISSION_EURO_4
        "2" -> EMISSION_EURO_5
        "3" -> EMISSION_EURO_6
        "4" -> EMISSION_EL
        "5" -> EMISSION_EL_HYBDRID
        "6" -> EMISSION_LADD_HYBDRID
        "7" -> EMISSION_EEV
        else -> emission
    }


}