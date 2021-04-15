package com.example.verivikator

import android.app.Activity
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


class MonitoringAdapter(val mCtx: Context, val layoutResId: Int, val monitoringList: List<Monitoring>) :ArrayAdapter<Monitoring>(mCtx, layoutResId, monitoringList)  {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)

        val view: View = layoutInflater.inflate(layoutResId, null)
        val tvnomor: TextView = view.findViewById(R.id.tv_nomor)
        val tvnama: TextView = view.findViewById(R.id.tv_namaveri)
        val lihatdata: LinearLayout = view.findViewById(R.id.lihatdata)
        val pdf: LinearLayout = view.findViewById(R.id.getpdf)
        val pdf2: TextView = view.findViewById(R.id.getpdf2)
        val monitoring = monitoringList[position]
        tvnomor.text = monitoring.nomorkasus
        tvnama.text = monitoring.namaverivikator
        lihatdata.setOnClickListener {
            lihatdata(monitoring)
        }

        pdf.setOnClickListener {
            lihatdata(monitoring)
        }
        pdf2.setOnClickListener{
            lihatdata(monitoring)
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



    private fun lihatdata(monitoring: Monitoring) {
        val builder = AlertDialog.Builder(mCtx)
        val inflater = LayoutInflater.from(mCtx)
        val view: View = inflater.inflate(R.layout.detail, null)
        val tnomor: TextView = view.findViewById(R.id.tnomor)
        val tnama: TextView = view.findViewById(R.id.tnamaveri)
        val tnamacabang : TextView = view.findViewById(R.id.tnamacabang)
        val tnamakelompok: TextView = view.findViewById(R.id.tnamakelompok)
        val tnamapo: TextView = view.findViewById(R.id.tnamapo)
        val tperiodepinjaman: TextView = view.findViewById(R.id.tperiodepinjaman)
        val tevaluasioleh: TextView = view.findViewById(R.id.tevaluasioleh)
        val tangsuran: TextView = view.findViewById(R.id.tangsuran)
        val tpraduga: TextView = view.findViewById(R.id.tpraduga)
        val tdata1: TextView = view.findViewById(R.id.tdata1)
        val tdata2: TextView = view.findViewById(R.id.tdata2)
        val tdata3: TextView = view.findViewById(R.id.tdata3)
        val tdata4: TextView = view.findViewById(R.id.tdata4)
        val tdata5: TextView = view.findViewById(R.id.tdata5)
        val tdata6: TextView = view.findViewById(R.id.tdata6)
        val tdata7: TextView = view.findViewById(R.id.tdata7)
        val tdata8: TextView = view.findViewById(R.id.tdata8)
        val tdata9: TextView = view.findViewById(R.id.tdata9)
        val tdata10: TextView = view.findViewById(R.id.tdata10)
        val tdata11: TextView = view.findViewById(R.id.tdata11)
        val tdata12: TextView = view.findViewById(R.id.tdata12)
        val tdata13: TextView = view.findViewById(R.id.tdata13)
        val tkerugiansementara: TextView = view.findViewById(R.id.tkerugiansementara)
        val tdiketahui: TextView = view.findViewById(R.id.tdiketahui)


        tnomor.setText(monitoring.nomorkasus)
        tnama.setText(monitoring.namaverivikator)
        tnamacabang.setText(monitoring.namacabang)
        tnamakelompok.setText(monitoring.namakelompok)
        tnamapo.setText(monitoring.namapo)
        tperiodepinjaman.setText(monitoring.periode)
        tevaluasioleh.setText(monitoring.evaluasi)
        tangsuran.setText(monitoring.angsuran)
        tpraduga.setText(monitoring.praduga)

        if (monitoring.data1 != "") {
            tdata1.isVisible = true
            tdata1.setText(monitoring.data1)
        } else {
            tdata1.isVisible = false
        }
        if (monitoring.data2 != "") {
            tdata2.isVisible = true
            tdata2.setText(monitoring.data2)
        } else {
            tdata2.isVisible = false
        }
        if (monitoring.data3 != "") {
            tdata3.isVisible = true
            tdata3.setText(monitoring.data3)
        } else {
            tdata3.isVisible = false
        }
        if (monitoring.data4 != "") {
            tdata4.isVisible = true
            tdata4.setText(monitoring.data4)
        } else {
            tdata4.isVisible = false
        }
        if (monitoring.data5 != "") {
            tdata5.isVisible = true
            tdata5.setText(monitoring.data4)
        } else {
            tdata5.isVisible = false
        }
        if (monitoring.data6 != "") {
            tdata6.isVisible = true
            tdata6.setText(monitoring.data6)
        } else {
            tdata6.isVisible = false
        }
        if (monitoring.data7 != "") {
            tdata7.isVisible = true
            tdata7.setText(monitoring.data7)
        } else {
            tdata7.isVisible = false
        }
        if (monitoring.data8 != "") {
            tdata8.isVisible = true
            tdata8.setText(monitoring.data8)
        } else {
            tdata8.isVisible = false
        }
        if (monitoring.data9 != "") {
            tdata9.isVisible = true
            tdata9.setText(monitoring.data9)
        } else {
            tdata9.isVisible = false
        }
        if (monitoring.data10 != "") {
            tdata10.isVisible = true
            tdata10.setText(monitoring.data10)
        } else {
            tdata10.isVisible = false
        }
        if (monitoring.data11 != "") {
            tdata11.isVisible = true
            tdata11.setText(monitoring.data11)
        } else {
            tdata11.isVisible = false
        }
        if (monitoring.data12 != "") {
            tdata12.isVisible = true
            tdata12.setText(monitoring.data12)
        } else {
            tdata12.isVisible = false
        }
        if (monitoring.data13 != "") {
            tdata13.isVisible = true
            tdata13.setText(monitoring.data13)
        } else {
            tdata13.isVisible = false
        }
        tkerugiansementara.setText(monitoring.kerugian)
        tdiketahui.setText(monitoring.diketahui)
        builder.setView(view)

        builder.setNegativeButton("Data Temuan") { p0, p1 ->

            val builder = AlertDialog.Builder(mCtx)
            val inflater = LayoutInflater.from(mCtx)
            val view: View = inflater.inflate(R.layout.temuan, null)
            val urlimage: ImageView = view.findViewById(R.id.urlimage)
            val imageUrl: String = monitoring.imageUrl
            if (monitoring.imageUrl != "") {
                Picasso.get().load(imageUrl).resize(500, 500).centerInside().into(urlimage)
            } else {
                Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/600px-No_image_available.svg.png").into(urlimage)
            }
            val tv_periode_pemeriksaan : TextView = view.findViewById(R.id.tv_periode_pemeriksaan)
            val tv_pelaku_utama : TextView = view.findViewById(R.id.tv_pelaku_utama)
            val tv_turut_terlibat : TextView = view.findViewById(R.id.tv_turut_terlibat)
            val tv_tempus_delicti : TextView = view.findViewById(R.id.tv_tempus_delicti)
            val tv_modus_operandi : TextView = view.findViewById(R.id.tv_modus_operandi)
            val tv_data_14 : TextView = view.findViewById(R.id.tvdata14)
            val tv_data_15 : TextView = view.findViewById(R.id.tvdata15)
            val tv_data_16 : TextView = view.findViewById(R.id.tvdata16)
            val tv_data_17 : TextView = view.findViewById(R.id.tvdata17)
            val tv_data_18 : TextView = view.findViewById(R.id.tvdata18)
            val tv_data_19 : TextView = view.findViewById(R.id.tvdata19)
            val tv_data_20 : TextView = view.findViewById(R.id.tvdata20)
            val tv_data_21 : TextView = view.findViewById(R.id.tvdata21)
            val tv_data_22 : TextView = view.findViewById(R.id.tvdata22)
            val tv_data_23 : TextView = view.findViewById(R.id.tvdata23)
            val tv_data_24 : TextView = view.findViewById(R.id.tvdata24)
            val tv_alasan_pelaku : TextView = view.findViewById(R.id.tv_alasan_pelaku)
            val tv_alasan_kacab : TextView = view.findViewById(R.id.tv_alasan_kacab)
            val tv_kerugian_dikembalikan : TextView = view.findViewById(R.id.tv_kerugian_dikembalikan)
            val tv_data_25 : TextView = view.findViewById(R.id.tvdata25)
            val tv_data_26 : TextView = view.findViewById(R.id.tvdata26)
            tv_periode_pemeriksaan.setText(monitoring.periodePemeriksaan)
            tv_pelaku_utama.setText(monitoring.pelakuUtama)
            tv_turut_terlibat.setText(monitoring.turutTerlibat)
            tv_tempus_delicti.setText(monitoring.tempusDelicti)
            tv_modus_operandi.setText(monitoring.modusOperandi)
            if (monitoring.data14 != "") {
                tv_data_14.isVisible = true
                tv_data_14.setText(monitoring.data14)
            } else {
                tv_data_14.isVisible = false
            }
            if (monitoring.data15 != "") {
                tv_data_15.isVisible = true
                tv_data_15.setText(monitoring.data15)
            } else {
                tv_data_15.isVisible = false
            }
            if (monitoring.data16 != "") {
                tv_data_16.isVisible = true
                tv_data_16.setText(monitoring.data16)
            } else {
                tv_data_16.isVisible = false
            }
            if (monitoring.data17 != "") {
                tv_data_17.isVisible = true
                tv_data_17.setText(monitoring.data17)
            } else {
                tv_data_17.isVisible = false
            }
            if (monitoring.data18 != "") {
                tv_data_18.isVisible = true
                tv_data_18.setText(monitoring.data18)
            } else {
                tv_data_18.isVisible = false
            }
            if (monitoring.data19 != "") {
                tv_data_19.isVisible = true
                tv_data_19.setText(monitoring.data19)
            } else {
                tv_data_19.isVisible = false
            }
            if (monitoring.data20 != "") {
                tv_data_20.isVisible = true
                tv_data_20.setText(monitoring.data20)
            } else {
                tv_data_20.isVisible = false
            }
            if (monitoring.data21 != "") {
                tv_data_21.isVisible = true
                tv_data_21.setText(monitoring.data21)
            } else {
                tv_data_21.isVisible = false
            }
            if (monitoring.data22 != "") {
                tv_data_22.isVisible = true
                tv_data_22.setText(monitoring.data22)
            } else {
                tv_data_22.isVisible = false
            }
            if (monitoring.data23 != "") {
                tv_data_23.isVisible = true
                tv_data_23.setText(monitoring.data23)
            } else {
                tv_data_23.isVisible = false
            }
            if (monitoring.data24 != "") {
                tv_data_24.isVisible = true
                tv_data_24.setText(monitoring.data24)
            } else {
                tv_data_24.isVisible = false
            }
            tv_alasan_pelaku.setText(monitoring.alasanPelaku)
            tv_alasan_kacab.setText(monitoring.alasanKacab)
            tv_kerugian_dikembalikan.setText(monitoring.kerugianDikembalikan)
            if (monitoring.data25 != "") {
                tv_data_25.isVisible = true
                tv_data_25.setText(monitoring.data25)
            } else {
                tv_data_25.isVisible = false
            }
            if (monitoring.data26 != "") {
                tv_data_26.isVisible = true
                tv_data_26.setText(monitoring.data26)
            } else {
                tv_data_26.isVisible = false
            }
            builder.setNegativeButton("Close") { p0, p1 ->

            }
            builder.setView(view)

            val alert = builder.create()
            alert.show()
        }
        builder.setPositiveButton("Tambah Temuan") { p0, p1 ->
            val builder = AlertDialog.Builder(mCtx)
            val inflater = LayoutInflater.from(mCtx)
            val view: View = inflater.inflate(R.layout.add_temuan, null)
            val tnomor: TextView = view.findViewById(R.id.tnomor)
            val tnama: TextView = view.findViewById(R.id.tnamaveri)
            tnomor.setText(monitoring.nomorkasus)
            val intent = Intent(context, Add_Temuan::class.java)
            intent.putExtra(Section2.EXTRA_ID, monitoring.id)
            intent.putExtra(Section2.EXTRA_NOMORKASUS, monitoring.nomorkasus)
            intent.putExtra(Section2.EXTRA_NAMAVERIVIKATOR, monitoring.namaverivikator)
            intent.putExtra(Section2.EXTRA_NAMACABANG, monitoring.namacabang)
            intent.putExtra(Section2.EXTRA_NAMAKELOMPOK, monitoring.namakelompok)
            intent.putExtra(Section2.EXTRA_NAMAPO, monitoring.namapo)
            intent.putExtra(Section2.EXTRA_PERIODE, monitoring.periode)
            intent.putExtra(Section2.EXTRA_EVALUASI, monitoring.evaluasi)
            intent.putExtra(Section2.EXTRA_ANGSURAN, monitoring.angsuran)
            intent.putExtra(Section2.EXTRA_PRADUGA, monitoring.praduga)
            intent.putExtra(Section2.EXTRA_KERUGIAN, monitoring.kerugian)
            intent.putExtra(Section2.EXTRA_DIKETAHUI, monitoring.diketahui)
            intent.putExtra(Section2.EXTRA_DATA1, monitoring.data1)
            intent.putExtra(Section2.EXTRA_DATA2, monitoring.data2)
            intent.putExtra(Section2.EXTRA_DATA3, monitoring.data3)
            intent.putExtra(Section2.EXTRA_DATA4, monitoring.data4)
            intent.putExtra(Section2.EXTRA_DATA5, monitoring.data5)
            intent.putExtra(Section2.EXTRA_DATA6, monitoring.data6)
            intent.putExtra(Section2.EXTRA_DATA7, monitoring.data7)
            intent.putExtra(Section2.EXTRA_DATA8, monitoring.data8)
            intent.putExtra(Section2.EXTRA_DATA9, monitoring.data9)
            intent.putExtra(Section2.EXTRA_DATA10, monitoring.data10)
            intent.putExtra(Section2.EXTRA_DATA11, monitoring.data11)
            intent.putExtra(Section2.EXTRA_DATA12, monitoring.data12)
            intent.putExtra(Section2.EXTRA_DATA13, monitoring.data13)
            context.startActivity(intent)
            builder.setView(view)
            }

            builder.setNeutralButton("Hapus") { p0, p1 ->
                val dbMonitori = FirebaseDatabase.getInstance().getReference("monitoring").child(monitoring.id)
                dbMonitori.removeValue()
                Toast.makeText(mCtx, "Data Terhapus", Toast.LENGTH_SHORT).show()

            }
            val alert = builder.create()
            alert.show()

        }



}
    