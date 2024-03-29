package darshan.prajapati.n01584247.dp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class PrajapatiActivity10 : AppCompatActivity() {

    private var bnv: BottomNavigationView? = null
    private val fragmentList: ArrayList<Fragment> = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add fragments to the list
        fragmentList.add(Da1rshanFragment())
        fragmentList.add(Pr2ajapatiFragment())
        fragmentList.add(N013584247Fragment())
        fragmentList.add(D4pFragment())

        // Set the first fragment
        bnv = findViewById(R.id.darBottomNavigationView)
        supportFragmentManager.beginTransaction().replace(R.id.darflFragment, fragmentList[0])
            .commit()

        // Set the listener for the BottomNavigationView
        bnv?.setOnItemSelectedListener() { item ->
            when (item.itemId) {
                R.id.da1rshan -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.darflFragment, fragmentList[0]).commit()
                    true
                }
                R.id.pr2ajapati -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.darflFragment, fragmentList[1]).commit()
                    true
                }
                R.id.n013584247 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.darflFragment, fragmentList[2]).commit()
                    true
                }
                R.id.d4p -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.darflFragment, fragmentList[3]).commit()
                    true
                }
                else -> false
            }
        }
    }
}