package com.eselman.android.books.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.eselman.android.books.R
import com.eselman.android.books.databinding.FragmentBooksLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class BooksLocationFragment : Fragment(),
    OnMapReadyCallback {

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val ZOOM_LEVEL = 18f
        private const val BOOKSTORES_MAX_RESULTS = 5
    }

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentBooksLocationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBooksLocationBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.locationSearchBtn.setOnClickListener {
            findBookStores()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                setCurrentLocation()
                map.isMyLocationEnabled = true
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }
        } else {
            setCurrentLocation()
            map.isMyLocationEnabled = true
        }
    }

    @SuppressLint("MissingPermission")
    fun setCurrentLocation() {
        map.clear()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    map.addMarker(MarkerOptions().position(latLng))
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL))
                }
            }
    }

    private fun findBookStores() {
        val booksStoresKeyWord = binding.locationSearch.text.toString()
            val geoCoder = Geocoder(requireActivity())
            try {
                if (booksStoresKeyWord.isNotEmpty()) {
                    val addressList: List<Address>? = geoCoder.getFromLocationName(
                        booksStoresKeyWord,
                        BOOKSTORES_MAX_RESULTS
                    )
                    if (addressList?.isNotEmpty() == true) {
                        addressList.forEach { address ->
                            val latLng = LatLng(address.latitude, address.longitude)
                            map.addMarker(
                                MarkerOptions().position(latLng)
                                    .title("$booksStoresKeyWord ${address.getAddressLine(0)}")
                            )
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL))
                        }
                    }
                }
            } catch (e: IOException) {
                Log.d("BOOKS", "Error getting bookstores ${e.localizedMessage}")
            }
    }
}
