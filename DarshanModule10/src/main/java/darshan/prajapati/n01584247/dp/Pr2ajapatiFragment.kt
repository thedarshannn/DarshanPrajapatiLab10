package darshan.prajapati.n01584247.dp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView

class Pr2ajapatiFragment : Fragment() {

    private lateinit var sharedPreferencesTemp: SharedPreferences
    private lateinit var temperatureUnit: String

    private lateinit var darIdTV: TextView
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

        darIdTV = view.findViewById(R.id.darIdTV)
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

        return view
    }

}