package darshan.prajapati.n01584247.dp

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat

class Pr2ajapatiFragment : Fragment() {

    private lateinit var sharedPreferencesTemp: SharedPreferences
    private lateinit var temperatureUnit: String

    private lateinit var darTempRadioGroup: RadioGroup
    private lateinit var darCelsiusRadioButton: RadioButton
    private lateinit var darFahrenheitRadioButton: RadioButton
    private lateinit var darCitySpinner: Spinner
    private lateinit var darTempTV: TextView
    private lateinit var darWeatherTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pr2ajapati, container, false)

        sharedPreferencesTemp = requireContext().getSharedPreferences("WeatherPreferences", Context.MODE_PRIVATE)
        temperatureUnit = sharedPreferencesTemp.getString("temperatureUnit", "Celsius") ?: "Celsius"

        darTempRadioGroup = view.findViewById(R.id.darTempRadioGroup)
        darCelsiusRadioButton = view.findViewById(R.id.darCelsiusRadioButton)
        darFahrenheitRadioButton = view.findViewById(R.id.darFahrenheitRadioButton)
        darCitySpinner = view.findViewById(R.id.darCitySpinner)
        darTempTV = view.findViewById(R.id.darTempTV)
        darWeatherTV = view.findViewById(R.id.darWeatherTV)

        // Set the temperature unit and check the radio button
        if (temperatureUnit == "Celsius") {
            darTempRadioGroup.check(R.id.darCelsiusRadioButton)
        } else {
            darTempRadioGroup.check(R.id.darFahrenheitRadioButton)
        }

        // Save the temperature unit in the shared preferences
        darTempRadioGroup.setOnCheckedChangeListener {_, checkedId ->
            temperatureUnit = if (checkedId == R.id.darCelsiusRadioButton) {
                "Celsius"
            } else {
                "Fahrenheit"
            }
            sharedPreferencesTemp.edit().putString("temperatureUnit", temperatureUnit).apply()
        }

        val resources: Resources = resources
        val cities: Array<String> = resources.getStringArray(R.array.cities_array)
        val citiesCoordinates: Array<String> = resources.getStringArray(R.array.cities_coordinates)

        // Set the city spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        darCitySpinner.adapter = adapter

        darCitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCity = cities[position]
                val cityCoordinates = citiesCoordinates[position].split(",")
                val latitude = cityCoordinates[0].toDouble()
                val longitude = cityCoordinates[1].toDouble()

                fetchData(latitude, longitude, darWeatherTV, darTempTV)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing or handle 'nothing selected' state
            }
        }

        return view
    }

    private fun fetchData(latitude: Double, longitude: Double, textViewWeatherInfo: TextView, tempTV : TextView){
        val apiKey = "de59a00c881ac96758bf689eca6caa43"
        val apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&appid=$apiKey"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                val jsonResponse = stringBuilder.toString()
                val jsonObject = JSONObject(jsonResponse)

                val lon = jsonObject.getJSONObject("coord").getDouble("lon")
                val lat = jsonObject.getJSONObject("coord").getDouble("lat")
                val country = jsonObject.getJSONObject("sys").getString("country")
                val humidity = jsonObject.getJSONObject("main").getInt("humidity")
                val cityName = jsonObject.getString("name")
                val weather = jsonObject.getJSONArray("weather").getJSONObject(0)
                val description = weather.getString("description")
                var temp = jsonObject.getJSONObject("main").getDouble("temp")

                if (temperatureUnit == "Fahrenheit") {
                    temp = (temp - 273.15) * 9 / 5 + 32
                } else {
                    temp -= 273.15
                }

                val decimalFormat = DecimalFormat("#.##")

                requireActivity().runOnUiThread {
                    textViewWeatherInfo.text = "Longitude: $lon\n\nLatitude: $lat\n\nCountry: $country\n\nHumidity: $humidity %\n\nCity: $cityName\n\nDescription: $description"
                    tempTV.text = "Temperature: ${decimalFormat.format(temp)} ${if (temperatureUnit.equals("Celsius", ignoreCase = true)) "°C" else "°F"}"

                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Error fetching the weather data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}