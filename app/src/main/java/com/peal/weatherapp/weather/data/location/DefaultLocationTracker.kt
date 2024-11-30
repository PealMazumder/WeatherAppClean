package com.peal.weatherapp.weather.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.peal.weatherapp.weather.domain.location.LocationTracker
import com.peal.weatherapp.weather.domain.util.LocationError
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    override suspend fun getCurrentLocation(): Result<Location> {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasAccessFineLocationPermission || !hasAccessCoarseLocationPermission) {
            return Result.failure(LocationError.PERMISSION_NOT_GRANTED)
        }

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        if (!isGpsEnabled) {
            return Result.failure(LocationError.GPS_DISABLED)
        }


        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) cont.resume(Result.success(result))
                    else cont.resume(Result.failure(LocationError.LOCATION_NOT_FOUND))
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener {
                    cont.resume(Result.success(it))
                }

                addOnFailureListener {
                    cont.resume(Result.failure(LocationError.LOCATION_FETCH_FAILED))
                }

                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }
}


