package com.example.verivikator

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class Add_Temuan : AppCompatActivity(), View.OnClickListener{
    private lateinit var txtUrl : TextView
    private lateinit var txtSemester : TextView
    private  lateinit var txtpelaku : EditText
    private lateinit var txtterlibat : EditText
    private lateinit var txttgl : TextView
    private lateinit var txtmodusoperandi : EditText
    private lateinit var data15 : String
    private lateinit var data16 : String
    private lateinit var data17 : String
    private lateinit var data18 : String
    private lateinit var data19 : String
    private lateinit var data20 : String
    private lateinit var data21 : String
    private lateinit var data22 : String
    private lateinit var data23 : String
    private lateinit var data24 : String
    private lateinit var data25 : String
    private lateinit var txtalasanpelaku : EditText
    private lateinit var txtalasankacab : EditText
    private lateinit var txtkerugiandikembalikan : EditText
    private lateinit var data26 : String
    private lateinit var data14 : String

    companion object{
        const val EXTRA_ID =  "extra_id"
        const val EXTRA_NOMORKASUS= "EXTRA_NOMORKASUS"
        const val EXTRA_NAMAVERIVIKATOR= "EXTRA_NAMAVERIVIKATOR"
        const val EXTRA_NAMACABANG= "EXTRA_NAMACABANG"
        const val EXTRA_NAMAKELOMPOK= "EXTRA_NAMAKELOMPOK"
        const val EXTRA_NAMAPO= "EXTRA_NAMAPO"
        const val EXTRA_PERIODE= "EXTRA_PERIODE"
        const val EXTRA_EVALUASI= "EXTRA_EVALUASI"
        const val EXTRA_ANGSURAN= "EXTRA_ANGSURAN"
        const val EXTRA_PRADUGA= "EXTRA_PRADUGA"
        const val EXTRA_KERUGIAN= "EXTRA_KERUGIAN"
        const val EXTRA_DIKETAHUI="EXTRA_DIKETAHUI"
        const val EXTRA_DATA1 = "EXTRA_DATA1"
        const val EXTRA_DATA2 = "EXTRA_DATA2"
        const val EXTRA_DATA3 = "EXTRA_DATA3"
        const val EXTRA_DATA4 = "EXTRA_DATA4"
        const val EXTRA_DATA5 = "EXTRA_DATA5"
        const val EXTRA_DATA6 = "EXTRA_DATA6"
        const val EXTRA_DATA7 = "EXTRA_DATA7"
        const val EXTRA_DATA8 = "EXTRA_DATA8"
        const val EXTRA_DATA9 = "EXTRA_DATA9"
        const val EXTRA_DATA10 = "EXTRA_DATA10"
        const val EXTRA_DATA11 = "EXTRA_DATA11"
        const val EXTRA_DATA12 = "EXTRA_DATA12"
        const val EXTRA_DATA13 = "EXTRA_DATA13"
    }

    //url firebase to text


    //camera
    private val PERMISSION_CODE2 = 1000;
    private val IMAGE_CAPTURE_CODE2 = 1001
    var image_uri2: Uri? = null

    private lateinit var btnsavesection : Button

    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.section2)


        val id = intent.getStringExtra(EXTRA_ID)
        val txtid = findViewById<TextView>(R.id.txtid)
        txtid.text = id
        val tnomor = findViewById<TextView>(R.id.tnomor)
        val tnamaveri = findViewById<TextView>(R.id.tnamaveri)
        tnomor.setText(intent.getStringExtra(Add_Temuan.EXTRA_NOMORKASUS))
        tnamaveri.setText(intent.getStringExtra(Add_Temuan.EXTRA_NAMAVERIVIKATOR))
        val now = Calendar.getInstance()
        var btntanggal = findViewById<Button>(R.id.btntanggal)

        //date
        btntanggal.setOnClickListener{
            val datePicker = DatePickerDialog(
                    this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                now.set(Calendar.YEAR, year)
                now.set(Calendar.MONTH, month)
                now.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                txttgl.text = dateFormat.format(now.time)
            },
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        //camera
        val btncamera2 = findViewById<Button>(R.id.btncamera2)
        btncamera2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(permission, PERMISSION_CODE2)
                } else {
                    openCamera2()
                }
            } else {
                openCamera2()
            }
        }

        //Spinner
        val aSemester = arrayOf("Semester 1", "Semester 2")
        val vSemester = findViewById<Spinner>(R.id.spSemester)
        if (vSemester != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, aSemester)
            vSemester.adapter = arrayAdapter
            vSemester.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    txtSemester.text = aSemester[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        }



        txtpelaku = findViewById(R.id.txtpelaku)
        txtterlibat = findViewById(R.id.txtterlibat)
        txtalasanpelaku = findViewById(R.id.txtalasanpelaku)
        txtalasankacab = findViewById(R.id.txtalasankacab)
        txtkerugiandikembalikan = findViewById(R.id.txtkerugiandikembalikan)
        txtSemester = findViewById(R.id.txtSemester)
        txtUrl = findViewById(R.id.txtUrl)
        txttgl = findViewById(R.id.txttanggal)
        txtmodusoperandi = findViewById(R.id.txtmodusoperandi)
        btnsavesection = findViewById(R.id.btnsavesection2)
        btnsavesection.setOnClickListener(this)
    }

    private fun savesection() {
        val nomorkasus = intent.getStringExtra(EXTRA_NOMORKASUS).toString().trim()
        val namaverivikator = intent.getStringExtra(EXTRA_NAMAVERIVIKATOR).toString().trim()
        val namacabang = intent.getStringExtra(EXTRA_NAMACABANG).toString().trim()
        val namakelompok = intent.getStringExtra(EXTRA_NAMAKELOMPOK).toString().trim()
        val namapo = intent.getStringExtra(EXTRA_NAMAPO).toString().trim()
        val periode = intent.getStringExtra(EXTRA_PERIODE).toString().trim()
        val evaluasi = intent.getStringExtra(EXTRA_EVALUASI).toString().trim()
        val angsuran = intent.getStringExtra(EXTRA_ANGSURAN).toString().trim()
        val praduga = intent.getStringExtra(EXTRA_PRADUGA).toString().trim()
        val kerugian = intent.getStringExtra(EXTRA_KERUGIAN).toString().trim()
        val diketahui = intent.getStringExtra(EXTRA_DIKETAHUI).toString().trim()
        val data1 = intent.getStringExtra(EXTRA_DATA1).toString().trim()
        val data2 = intent.getStringExtra(EXTRA_DATA2).toString().trim()
        val data3 = intent.getStringExtra(EXTRA_DATA3).toString().trim()
        val data4 = intent.getStringExtra(EXTRA_DATA4).toString().trim()
        val data5 = intent.getStringExtra(EXTRA_DATA5).toString().trim()
        val data6 = intent.getStringExtra(EXTRA_DATA6).toString().trim()
        val data7 = intent.getStringExtra(EXTRA_DATA7).toString().trim()
        val data8 = intent.getStringExtra(EXTRA_DATA8).toString().trim()
        val data9 = intent.getStringExtra(EXTRA_DATA9).toString().trim()
        val data10 = intent.getStringExtra(EXTRA_DATA10).toString().trim()
        val data11 = intent.getStringExtra(EXTRA_DATA11).toString().trim()
        val data12 = intent.getStringExtra(EXTRA_DATA12).toString().trim()
        val data13 = intent.getStringExtra(EXTRA_DATA13).toString().trim()

        val image_url : String =txtUrl.text.toString().trim()
        val periode_pemeriksaan : String =txtSemester.text.toString().trim()
        val pelaku_utama: String = txtpelaku.text.toString().trim()
        val turut_terlibat: String = txtterlibat.text.toString().trim()
        val tempus_delicti : String= txttgl.text.toString().trim()
        val modus_operandi: String = txtmodusoperandi.text.toString().trim()
        val alasan_pelaku: String = txtalasanpelaku.text.toString().trim()
        val alasan_kacab: String = txtalasankacab.text.toString().trim()
        val kerugian_dikembalikan: String = txtkerugiandikembalikan.text.toString().trim()

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
            data14 = "SOP Proses Persiapan Realisasi Kelompok TB SG: SOP/ops/2015, Hal 40"
        }else{
            data14 = ""
        }
        if (cb2.isChecked){
            data15 = "SOP Proses Realisasi Kelompok TB SG: SOP/ops/2015, Hal 41-42"
        }else{
            data15 = ""
        }
        if (cb3.isChecked){
            data16 = "SOP Proses Realisasi Individu: SOP/ops/2015, Hal 42-43"
        }else{
            data16 = ""
        }
        if (cb4.isChecked){
            data17 = "SOP Proses Realisasi SME: SOP/ops/2015, Hal 43-45"
        }else{
            data17 = ""
        }
        if (cb5.isChecked){
            data18 = "SOP Proses Pembayaran Angsuran:  SOP/ops/2015: hal 45-46"
        }else{
            data18 = ""
        }
        if (cb6.isChecked){
            data19 = "SOP Ketentuan Penulisan Kartu F3:  SOP/ops/2015: hal 47"
        }
        else{
            data19 = ""
        }
        if (cb7.isChecked){
            data20 = "SOP Langkah-Langkah Menangani Tunggakan:  SOP/ops/2015: hal 49-51"
        }else{
            data20 = ""
        }
        if (cb8.isChecked){
            data21 = "SOP Proses Pengajuan TB: SOP/ops/2015: hal 51-52"
        }else{
            data21 = ""
        }
        if (cb9.isChecked){
            data22 = "SOP Proses Penyerahan TM: SOP/ops/2015: hal 53"
        }else{
            data22 = ""
        }
        if (cb10.isChecked){
            data23 = "SOP Kebijakan Dana Santunan: SOP/ops/2015: hal 54-55"
        }else{
            data23 = ""
        }
        if (cb11.isChecked){
            data24 = "SOP Proses Permohonan Pinjaman: SOP/ops/2015, hal 14-15"
        }else{
            data24 = ""
        }
        if (cb12.isChecked){
            data25 = "Lakukan Perbaikan segera sesuai dengan SOP yang ada dan tidak mengulangi Kesalahan"
        }else{
            data25 = ""
        }
        if (cb13.isChecked){
            data26 = "Mintakan Persetujuan ke Level yang lebih tinggi agar tetap dalam kontrol yang ada"
        }else{
            data26 = ""
        }
        val id = intent.getStringExtra(EXTRA_ID).toString().trim()
        val  ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("monitoring")
        val monitoringId = ref.push().key
        val monitoring = Monitoring(id, nomorkasus,namaverivikator,namacabang,namakelompok,namapo,periode,evaluasi,angsuran,praduga,kerugian, diketahui,data1,data2, data3,data4,data5,data6,
                data7,data8,data9,data10,data11,data12,data13,image_url,periode_pemeriksaan,pelaku_utama,turut_terlibat,tempus_delicti,modus_operandi,data14,data15, data16, data17,data18,data19,
                data20,data21,data22,data23, data24,alasan_pelaku,alasan_kacab,kerugian_dikembalikan,data25,data26)
        if (id != null) {
            ref.child(id).setValue(monitoring).addOnCompleteListener {
                Toast.makeText(
                        applicationContext,
                        "Data Temuan berhasil ditambahkan",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    //camera
    private fun openCamera2() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera")
        image_uri2 = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri2)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE2)
    }
    //camera
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE2 -> {
                if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED
                ) {
                    openCamera2()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //camera
    @SuppressLint("ResourceType")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && image_uri2!=null){
            var pd = ProgressDialog(this)
            pd.setTitle("Proses Gambar")
            pd.show()
            val filename = "images/" + UUID.randomUUID().toString()
            var imageRef = FirebaseStorage.getInstance().getReference().child(filename)

            imageRef.putFile(image_uri2!!)
                    .addOnSuccessListener { p0 ->
                        pd.dismiss()
                        Toast.makeText(applicationContext, "File Upload", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{ p0 ->
                        pd.dismiss()
                        Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener { p0 ->
                        var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                        pd.setMessage("Proses ${progress.toInt()}%")
                    }
                    .addOnSuccessListener () { taskSnapshot ->
                        imageRef.downloadUrl.addOnCompleteListener () { taskSnapshot ->

                            var url = taskSnapshot.result
                            println("url =" + url.toString())
                            txtUrl.setText(url.toString())
                        }
                    }
            var image_View2 =findViewById<ImageView>(R.id.image_View2)
            image_View2.setImageURI(image_uri2)

        }
    }

    override fun onClick(v: View?) {
        savesection()
    }






}

