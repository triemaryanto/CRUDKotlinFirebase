package com.example.verivikator

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.database.*

class ViewData:AppCompatActivity() {
    private lateinit var ref : DatabaseReference
    private lateinit var monitoringList : MutableList<Monitoring>
    private lateinit var listMonitoring : ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_mnt)
        monitoringList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("monitoring")

        listMonitoring = findViewById(R.id.listView)

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()){
                    monitoringList.clear()
                    for (h in p0.children){
                        val monitoring : Monitoring? = h.getValue(Monitoring::class.java)
                        if (monitoring != null  ) {
                            monitoringList.add(monitoring)

                        }
                    }
                    val adapter = MonitoringAdapter(this@ViewData,R.layout.rv_layout,monitoringList)
                    listMonitoring.adapter = adapter
                }

            }
        })
    }
}