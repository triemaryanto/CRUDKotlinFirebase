package com.example.verivikator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class PdfAdapter (val mCtx: Context, val layoutResId: Int, val monitoringList: List<Monitoring>) : ArrayAdapter<Monitoring>(mCtx, layoutResId, monitoringList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val tvnomor: TextView = view.findViewById(R.id.pdf_nomor)
        val tvnama: TextView = view.findViewById(R.id.pdf_namaveri)
        val convertPdf: LinearLayout = view.findViewById(R.id.convertpdf)

        val monitoring = monitoringList[position]
        tvnomor.text = monitoring.nomorkasus
        tvnama.text = monitoring.namaverivikator

        convertPdf.setOnClickListener {
            printpdf(monitoring)
        }
        return view
    }

    private fun printpdf(monitoring: Monitoring) {
        val intent = Intent(context, pdf::class.java)
        context.startActivity(intent)
        intent.putExtra(pdf.EXTRA_ID, monitoring.id)
        intent.putExtra(pdf.EXTRA_NOMORKASUS, monitoring.nomorkasus)
        intent.putExtra(pdf.EXTRA_NAMAVERIVIKATOR, monitoring.namaverivikator)
        intent.putExtra(pdf.EXTRA_NAMACABANG, monitoring.namacabang)
        intent.putExtra(pdf.EXTRA_PERIODE, monitoring.periode)
        intent.putExtra(pdf.EXTRA_TURUTTERLIBAT, monitoring.turutTerlibat)
        intent.putExtra(pdf.EXTRA_KELOMPOK, monitoring.namakelompok)
        intent.putExtra(pdf.EXTRA_KERUGIANSEMENTARA, monitoring.kerugian)
        intent.putExtra(pdf.EXTRA_DATA1, monitoring.data1)
        intent.putExtra(pdf.EXTRA_DATA2, monitoring.data2)
        intent.putExtra(pdf.EXTRA_DATA3, monitoring.data3)
        intent.putExtra(pdf.EXTRA_DATA4, monitoring.data4)
        intent.putExtra(pdf.EXTRA_DATA5, monitoring.data5)
        intent.putExtra(pdf.EXTRA_DATA6, monitoring.data6)
        intent.putExtra(pdf.EXTRA_DATA7, monitoring.data7)
        intent.putExtra(pdf.EXTRA_DATA8, monitoring.data8)
        intent.putExtra(pdf.EXTRA_DATA9, monitoring.data9)
        intent.putExtra(pdf.EXTRA_DATA10, monitoring.data10)
        intent.putExtra(pdf.EXTRA_DATA11, monitoring.data11)
        intent.putExtra(pdf.EXTRA_DATA12, monitoring.data12)
        intent.putExtra(pdf.EXTRA_DATA13, monitoring.data13)
        intent.putExtra(pdf.EXTRA_DATA14, monitoring.data14)
        intent.putExtra(pdf.EXTRA_DATA15, monitoring.data15)
        intent.putExtra(pdf.EXTRA_DATA16, monitoring.data16)
        intent.putExtra(pdf.EXTRA_DATA17, monitoring.data17)
        intent.putExtra(pdf.EXTRA_DATA18, monitoring.data18)
        intent.putExtra(pdf.EXTRA_DATA19, monitoring.data19)
        intent.putExtra(pdf.EXTRA_DATA20, monitoring.data20)
        intent.putExtra(pdf.EXTRA_DATA21, monitoring.data21)
        intent.putExtra(pdf.EXTRA_DATA22, monitoring.data22)
        intent.putExtra(pdf.EXTRA_DATA23, monitoring.data23)
        intent.putExtra(pdf.EXTRA_DATA24, monitoring.data24)
        intent.putExtra(pdf.EXTRA_ALASANPELAKU, monitoring.alasanPelaku)
        intent.putExtra(pdf.EXTRA_ALASANKACAB, monitoring.alasanKacab)
        context.startActivity(intent)
    }
}