package com.example.verivikator

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class PdfView:AppCompatActivity() {
    private lateinit var ref : DatabaseReference
    private lateinit var monitoringList : MutableList<Monitoring>
    private lateinit var listMonitoring : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_pdf)
        monitoringList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("monitoring")

        listMonitoring = findViewById(R.id.listPdf)

        ref.addValueEventListener(object : ValueEventListener {
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
                    val adapter = PdfAdapter(this@PdfView,R.layout.pdf_layout,monitoringList)
                    listMonitoring.adapter = adapter
                }

            }
        })

    }
}