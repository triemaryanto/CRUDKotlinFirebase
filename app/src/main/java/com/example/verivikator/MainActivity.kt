 package com.example.verivikator

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import com.afollestad.materialdialogs.MaterialDialog


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var monitoringList : MutableList<Monitoring>

    private lateinit var txtnamacabang : EditText
    private lateinit var txtnamakelompok : EditText
    private lateinit var txtnamapo : EditText
    private lateinit var namaveri : TextView
    private lateinit var txtPeriode : EditText
    private lateinit var txtEvaluasi : EditText


    //Spiner to text
    private lateinit var mySpinner: Spinner
    private lateinit var txtAngsuran : TextView
    private lateinit var txtDiketahui : TextView




    private lateinit var btnsave : Button

    //checkbox
    private lateinit var data1 : String
    private lateinit var data2 : String
    private lateinit var data3 : String
    private lateinit var data4 : String
    private lateinit var data5 : String
    private lateinit var data6 : String
    private lateinit var data7 : String
    private lateinit var data8 : String
    private lateinit var data9 : String
    private lateinit var data10 : String
    private lateinit var data11 : String
    private lateinit var data12 : String
    private lateinit var data13 : String




    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup11()

        //Spinner
        val personNames = arrayOf("Hadi Priyanto", "Tuana Rubent M", "Al Slamet Sutopo", "Daniel Sihombing")
        val spinner = findViewById<Spinner>(R.id.mySpinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, personNames)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(
                            this@MainActivity,
                            getString(R.string.selected_item) + " " + personNames[position],
                            Toast.LENGTH_SHORT
                    ).show()
                    namaveri.text = personNames[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        //Spinner
        val aAngsuran = arrayOf("Ya" , "Tidak")
        val vAngsuran = this.findViewById<Spinner>(R.id.spAngsuran)
        if (vAngsuran != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, aAngsuran)
            vAngsuran.adapter = arrayAdapter
            vAngsuran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                   txtAngsuran.text = aAngsuran[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        }

        //Spinner
        val aDiketahui = arrayOf("YA", "TIDAK", "MUNGKIN")
        val vDiketahui = findViewById<Spinner>(R.id.spDiketahui)
        if (vDiketahui != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, aDiketahui)
            vDiketahui.adapter = arrayAdapter
            vDiketahui.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    txtDiketahui.text = aDiketahui[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        }

        txtnamacabang = findViewById(R.id.txtnamacabang)
        txtnamakelompok = findViewById(R.id.txtnamakelompok)
        txtnamapo = findViewById(R.id.txtnamapo)
        namaveri = findViewById(R.id.namaveri)
        mySpinner = findViewById(R.id.mySpinner)
        txtPeriode = findViewById(R.id.txtperiode)
        txtEvaluasi = findViewById(R.id.txtevaluasi)
        txtAngsuran = findViewById(R.id.txtAngsuran)
        txtDiketahui = findViewById(R.id.txtDiketahui)
    }

    private fun setup11() {
        val btnsave111 = findViewById<Button>(R.id.btnsave111)
        btnsave111.setOnClickListener{
            val areYouSureCallback = object: AreYouSureCallback{
                override fun proceed() {

                    saveData2()
                    displayToast("successfully")
                }

                override fun cancel() {
                    displayToast("Cancelled.")
                }
                override fun temuan (){
                    saveData()
                    displayToast("form temuan")
                }
            }
            areYouSureDialog(
                    "Are you sure you want to do that? This can't be un-done.",
                    areYouSureCallback
            )
        }
    }
    interface AreYouSureCallback {

        fun proceed()

        fun cancel()

        fun temuan()
    }
    fun displayToast(message:String?){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
    fun areYouSureDialog(message: String, callback: AreYouSureCallback){
        MaterialDialog(this)
                .show{
                    title(R.string.are_you_sure)
                    message(text = message)
                    negativeButton(R.string.text_cancel){
                        callback.cancel()
                    }
                    positiveButton(R.string.text_yes){
                        callback.proceed()
                    }
                    neutralButton (R.string.text_temuan){
                        callback.temuan()

                    }
                }
    }


    override fun onClick(v: View?) {
        saveData2()
    }

    private fun saveData() {
        val txtnomorkasus = findViewById<EditText>(R.id.txtnomorkasur)
        val txtPraduga = findViewById<EditText>(R.id.txtpraduga)
        val txtkerugian = findViewById<EditText>(R.id.txtkerugianSementara)

        val nomorkasus : String = txtnomorkasus.text.toString().trim()
        val namacabang : String = txtnamacabang.text.toString().trim()
        val namakelompok : String = txtnamakelompok.text.toString().trim()
        val namapo : String = txtnamapo.text.toString().trim()
        val namaverivikator : String = namaveri.text.toString().trim()
        val periode : String = txtPeriode.text.toString().trim()
        val evaluasi : String = txtEvaluasi.text.toString().trim()
        val angsuran : String = txtAngsuran.text.toString().trim()
        val diketahui : String = txtDiketahui.text.toString().trim()
        val praduga : String = txtPraduga.text.toString().trim()
        val kerugian : String = txtkerugian.text.toString().trim()

        //checkbox
        val cb1 = findViewById<CheckBox>(R.id.cb1)
        val cb2 = findViewById<CheckBox>(R.id.cb2)
        val cb3 = findViewById<CheckBox>(R.id.cb3)
        val cb4 = findViewById<CheckBox>(R.id.cb4)
        val cb5 = findViewById<CheckBox>(R.id.cb5)
        val cb6 = findViewById<CheckBox>(R.id.cb6)
        val cb7 = findViewById<CheckBox>(R.id.cb7)
        val cb8 = findViewById<CheckBox>(R.id.cb8)
        val cb9 = findViewById<CheckBox>(R.id.cb9)
        val cb10 = findViewById<CheckBox>(R.id.cb10)
        val cb11 = findViewById<CheckBox>(R.id.cb11)
        val cb12 = findViewById<CheckBox>(R.id.cb12)
        val cb13 = findViewById<CheckBox>(R.id.cb13)

        if (cb1.isChecked){
            data1 = "TIDAK ADA INDIKASI KECURANGAN"
        }else{
            data1 = ""
        }
        if (cb2.isChecked){
            data2 = "F3/F9 Biru TIDAK UPDATE"
        }else{
            data2 = ""
        }
        if (cb3.isChecked){
            data3 = "F3/F9 Kuning TIDAK UPDATE"
        }else{
            data3 = ""
        }
        if (cb4.isChecked){
            data4 = "Slip ANGSURAN putih TIDAK ADA"
        }else{
            data4 = ""
        }
        if (cb5.isChecked){
            data5 = "Angsuran TRANSFER TIDAK ke Rekening DIMAN"
        }else{
            data5 = ""
        }
        if (cb6.isChecked){
            data6 = "Sebagian/Semua Pinjaman digunakan ORANG LAIN"
        }
        else{
            data6 = ""
        }
        if (cb7.isChecked){
            data7 = "TB/TM TIDAK DITERIMA klien"
        }else{
            data7 = ""
        }
        if (cb8.isChecked){
            data8 = "SURVEY TIDAK dilakukan"
        }else{
            data8 = ""
        }
        if (cb9.isChecked){
            data9 = "Klien TIDAK punya USAHA"
        }else{
            data9 = ""
        }
        if (cb10.isChecked){
            data10 = "Klien TIDAK TINGGAL dalam 1 wilayah"
        }else{
            data10 = ""
        }
        if (cb11.isChecked){
            data11 = "Bukti REKAMAN Pengakuan Klien"
        }else{
            data11 = ""
        }
        if (cb12.isChecked){
            data12 = "Bukti TERTULIS"
        }else{
            data12 = ""
        }
        if (cb13.isChecked){
            data13 = "PENGAKUAN tertulis"
        }else{
            data13 = ""
        }

        var image_url = ""
        var periode_pemeriksaan = ""
        var pelaku_utama = ""
        var turut_terlibat = ""
        var tempus_delicti = ""
        var modus_operandi = ""
        var data14 = ""
        var data15 = ""
        var data16 = ""
        var data17 = ""
        var data18 = ""
        var data19 = ""
        var data20 = ""
        var data21 = ""
        var data22 = ""
        var data23 = ""
        var data24 = ""
        var alasan_pelaku = ""
        var alasan_kacab = ""
        var kerugian_dikembalikan = ""
        var data25 = ""
        var data26 = ""

        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("monitoring");
        val monitoringId : String? = ref.push().key
        val mnt = Monitoring(monitoringId!!, nomorkasus, namaverivikator, namacabang, namakelompok, namapo, periode,
                evaluasi, angsuran, praduga,kerugian,diketahui, data1, data2,data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13,
                image_url, periode_pemeriksaan, pelaku_utama, turut_terlibat, tempus_delicti, modus_operandi, data14, data15,data16, data17, data18, data19, data20,
                data21, data22, data23, data24, alasan_pelaku, alasan_kacab, kerugian_dikembalikan, data25, data26)

        if (monitoringId !=null){
            var pe = ProgressDialog(this)
            pe.setTitle("Uploading")
            pe.show()
            ref.child(monitoringId).setValue(mnt).addOnSuccessListener {p0 ->
                pe.dismiss()
                Toast.makeText(applicationContext, "File Upload", Toast.LENGTH_LONG).show()

            }
            ref.child(monitoringId).setValue(mnt).addOnFailureListener{ p0 ->
                pe.dismiss()
                Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
            }

            ref.child(monitoringId).setValue(mnt).addOnCanceledListener {
            }


        }
        val intent = Intent (this@MainActivity, Section2::class.java)
        intent.putExtra(Section2.EXTRA_ID, monitoringId)
        intent.putExtra(Section2.EXTRA_NOMORKASUS, nomorkasus)
        intent.putExtra(Section2.EXTRA_NAMAVERIVIKATOR, namaverivikator)
        intent.putExtra(Section2.EXTRA_NAMACABANG, namacabang)
        intent.putExtra(Section2.EXTRA_NAMAKELOMPOK, namakelompok)
        intent.putExtra(Section2.EXTRA_NAMAPO, namapo)
        intent.putExtra(Section2.EXTRA_PERIODE, periode)
        intent.putExtra(Section2.EXTRA_EVALUASI, evaluasi)
        intent.putExtra(Section2.EXTRA_ANGSURAN, angsuran)
        intent.putExtra(Section2.EXTRA_PRADUGA, praduga)
        intent.putExtra(Section2.EXTRA_KERUGIAN, kerugian)
        intent.putExtra(Section2.EXTRA_DIKETAHUI, diketahui)
        intent.putExtra(Section2.EXTRA_DATA1, data1)
        intent.putExtra(Section2.EXTRA_DATA2, data2)
        intent.putExtra(Section2.EXTRA_DATA3, data3)
        intent.putExtra(Section2.EXTRA_DATA4, data4)
        intent.putExtra(Section2.EXTRA_DATA5, data5)
        intent.putExtra(Section2.EXTRA_DATA6, data6)
        intent.putExtra(Section2.EXTRA_DATA7, data7)
        intent.putExtra(Section2.EXTRA_DATA8, data8)
        intent.putExtra(Section2.EXTRA_DATA9, data9)
        intent.putExtra(Section2.EXTRA_DATA10, data10)
        intent.putExtra(Section2.EXTRA_DATA11, data11)
        intent.putExtra(Section2.EXTRA_DATA12, data12)
        intent.putExtra(Section2.EXTRA_DATA13, data13)
        startActivity(intent)
    }

    private fun saveData2() {
        val txtnomorkasus = findViewById<EditText>(R.id.txtnomorkasur)
        val txtPraduga = findViewById<EditText>(R.id.txtpraduga)
        val txtkerugian = findViewById<EditText>(R.id.txtkerugianSementara)

        val nomorkasus : String = txtnomorkasus.text.toString().trim()
        val namacabang : String = txtnamacabang.text.toString().trim()
        val namakelompok : String = txtnamakelompok.text.toString().trim()
        val namapo : String = txtnamapo.text.toString().trim()
        val namaverivikator : String = namaveri.text.toString().trim()
        val periode : String = txtPeriode.text.toString().trim()
        val evaluasi : String = txtEvaluasi.text.toString().trim()
        val angsuran : String = txtAngsuran.text.toString().trim()
        val diketahui : String = txtDiketahui.text.toString().trim()
        val praduga : String = txtPraduga.text.toString().trim()
        val kerugian : String = txtkerugian.text.toString().trim()

        //checkbox
        val cb1 = findViewById<CheckBox>(R.id.cb1)
        val cb2 = findViewById<CheckBox>(R.id.cb2)
        val cb3 = findViewById<CheckBox>(R.id.cb3)
        val cb4 = findViewById<CheckBox>(R.id.cb4)
        val cb5 = findViewById<CheckBox>(R.id.cb5)
        val cb6 = findViewById<CheckBox>(R.id.cb6)
        val cb7 = findViewById<CheckBox>(R.id.cb7)
        val cb8 = findViewById<CheckBox>(R.id.cb8)
        val cb9 = findViewById<CheckBox>(R.id.cb9)
        val cb10 = findViewById<CheckBox>(R.id.cb10)
        val cb11 = findViewById<CheckBox>(R.id.cb11)
        val cb12 = findViewById<CheckBox>(R.id.cb12)
        val cb13 = findViewById<CheckBox>(R.id.cb13)



        if (cb1.isChecked){
            data1 = "TIDAK ADA INDIKASI KECURANGAN"
        }else{
            data1 = ""
        }
        if (cb2.isChecked){
            data2 = "F3/F9 Biru TIDAK UPDATE"
        }else{
            data2 = ""
        }
        if (cb3.isChecked){
            data3 = "F3/F9 Kuning TIDAK UPDATE"
        }else{
            data3 = ""
        }
        if (cb4.isChecked){
            data4 = "Slip ANGSURAN putih TIDAK ADA"
        }else{
            data4 = ""
        }
        if (cb5.isChecked){
            data5 = "Angsuran TRANSFER TIDAK ke Rekening DIMAN"
        }else{
            data5 = ""
        }
        if (cb6.isChecked){
            data6 = "Sebagian/Semua Pinjaman digunakan ORANG LAIN"
        }
        else{
            data6 = ""
        }
        if (cb7.isChecked){
            data7 = "TB/TM TIDAK DITERIMA klien"
        }else{
            data7 = ""
        }
        if (cb8.isChecked){
            data8 = "SURVEY TIDAK dilakukan"
        }else{
            data8 = ""
        }
        if (cb9.isChecked){
            data9 = "Klien TIDAK punya USAHA"
        }else{
            data9 = ""
        }
        if (cb10.isChecked){
            data10 = "Klien TIDAK TINGGAL dalam 1 wilayah"
        }else{
            data10 = ""
        }
        if (cb11.isChecked){
            data11 = "Bukti REKAMAN Pengakuan Klien"
        }else{
            data11 = ""
        }
        if (cb12.isChecked){
            data12 = "Bukti TERTULIS"
        }else{
            data12 = ""
        }
        if (cb13.isChecked){
            data13 = "PENGAKUAN tertulis"
        }else{
            data13 = ""
        }
        var image_url = ""
        var periode_pemeriksaan = ""
        var pelaku_utama = ""
        var turut_terlibat = ""
        var tempus_delicti = ""
        var modus_operandi = ""
        var data14 = ""
        var data15 = ""
        var data16 = ""
        var data17 = ""
        var data18 = ""
        var data19 = ""
        var data20 = ""
        var data21 = ""
        var data22 = ""
        var data23 = ""
        var data24 = ""
        var alasan_pelaku = ""
        var alasan_kacab = ""
        var kerugian_dikembalikan = ""
        var data25 = ""
        var data26 = ""

        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("monitoring");
        val monitoringId : String? = ref.push().key
        val mnt = Monitoring(monitoringId!!, nomorkasus, namaverivikator, namacabang, namakelompok, namapo, periode,
                evaluasi, angsuran, praduga,kerugian,diketahui, data1, data2,data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, data13,
                image_url, periode_pemeriksaan, pelaku_utama, turut_terlibat, tempus_delicti, modus_operandi, data14, data15,data16, data17, data18, data19, data20,
                data21, data22, data23, data24, alasan_pelaku, alasan_kacab, kerugian_dikembalikan, data25, data26)

        if (monitoringId !=null){
            var pe = ProgressDialog(this)
            pe.setTitle("Uploading")
            pe.show()
            ref.child(monitoringId).setValue(mnt).addOnSuccessListener {p0 ->
                pe.dismiss()
                Toast.makeText(applicationContext, "File Upload", Toast.LENGTH_LONG).show()
                startActivity(Intent(applicationContext, Splash::class.java))
            }
            ref.child(monitoringId).setValue(mnt).addOnFailureListener{ p0 ->
                pe.dismiss()
                Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
            }

            ref.child(monitoringId).setValue(mnt).addOnCanceledListener {
            }


        }

    }

}


