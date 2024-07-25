package com.eveningoutpost.dexdrip.diabetesm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.diabetesm.addons.api.DiabetesAppConnection
import com.diabetesm.addons.api.DiabetesAppConnection.DiabetesMCheck
import com.diabetesm.addons.api.dto.LogEntry
import com.eveningoutpost.dexdrip.diabetesm.DiabetesmApi.Companion.REQUEST_CODE
import com.eveningoutpost.dexdrip.insulin.opennov.mt.InsulinDose

interface DiabetesmApi {

    fun checkStatus(context: Context): Boolean

    fun pushWithoutPrimingDoses(
        context: Context,
        doses: List<InsulinDose>,
        primingDoseTimeWindowMillis: Long,
    )

    fun authenticate(activity: Activity)

    fun isAuthenticated(context: Context): Boolean

    fun onActivityResult(context: Context, resultCode: Int, data: Intent?)

    companion object {
        const val REQUEST_CODE = 843
        private val singletone = DiabetesmApiImpl()
        fun create(): DiabetesmApi = singletone
    }
}

private class DiabetesmApiImpl : DiabetesmApi {
    override fun checkStatus(context: Context): Boolean =
        DiabetesAppConnection(context).let { diaConnection ->
            val checkStatus = diaConnection.checkDiabetesMApp()
            if (checkStatus != DiabetesMCheck.OK) {
                val message =
                    if (checkStatus == DiabetesMCheck.NOT_FOUND) {
                        "Missing Diabetes:M app!"
                    } else {
                        "Incompatible Diabetes:M version. Must be 5.0.5 or above!"
                    }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                false
            } else {
                true
            }
        }

    override fun pushWithoutPrimingDoses(
        context: Context,
        doses: List<InsulinDose>,
        primingDoseTimeWindowMillis: Long,
    ) {
        doses
            .sortedByDescending { it.absoluteTime }
            .fold(emptyList<InsulinDose>()) { acc, dose ->
                if (acc.isEmpty() ||
                    acc.last().absoluteTime - dose.absoluteTime >= primingDoseTimeWindowMillis
                ) {
                    acc + dose
                } else {
                    acc
                }
            }
            .let { filteredDoses ->
                DiabetesAppConnection(context)
                    .pushData(
                        filteredDoses.map {
                            LogEntry().apply {
                                this.dateTime = it.absoluteTime
                                this.bolusInsulin = it.units.toFloat()
                                this.note = "Automatically pushed by xDrip+"
                            }
                        }
                    ) {
                        val result = it.getString(DiabetesAppConnection.RESULT_KEY, "")
                        if (result.equals(DiabetesAppConnection.RESULT_UNAUTHORIZED)) {
                            Toast
                                .makeText(context, "Unauthorized", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast
                                .makeText(context, "Pushed to Diabetes:M", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
    }

    override fun authenticate(activity: Activity) {
        DiabetesAppConnection(activity)
            .requestAccess(activity, true, REQUEST_CODE)
    }

    override fun isAuthenticated(context: Context): Boolean =
        DiabetesAppConnection(context).isAuthenticated

    override fun onActivityResult(context: Context, resultCode: Int, data: Intent?) {
        when (DiabetesAppConnection(context).onActivityResult(resultCode, data)) {
            DiabetesAppConnection.AccessPermission.GRANTED -> {
                val isPushEnabled =
                    data?.extras?.getBoolean("pushDataPermission", false) == true
                val message =
                    "Access granted! (" + (if (isPushEnabled) "FULL ACCESS" else "READ ONLY") + ")"
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }

            DiabetesAppConnection.AccessPermission.REJECTED -> {
                val message = "Access rejected!"
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }

            else -> {
                val message = "Canceled!"
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
