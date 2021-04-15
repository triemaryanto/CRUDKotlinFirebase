package com.example.verivikator

import android.Manifest
import android.R.id
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color.BLACK
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.itextpdf.text.*
import com.itextpdf.text.PageSize.A4
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class pdf : AppCompatActivity() {
    companion object{
        const val EXTRA_ID =  "extra_id"
        const val EXTRA_NOMORKASUS= "EXTRA_NOMORKASUS"
        const val EXTRA_NAMAVERIVIKATOR= "EXTRA_NAMAVERIVIKATOR"
        const val EXTRA_NAMACABANG = "EXTRA_NAMACABANG"
        const val EXTRA_TURUTTERLIBAT = "EXTRA_TURUTTERLIBAT"
        const val EXTRA_PERIODE = "EXTRA_PERIODE"
        const val EXTRA_KELOMPOK = "EXTRA_KELOMPOK"
        const val EXTRA_KERUGIANSEMENTARA = "EXTRA_KERUGIANSEMENTARA"
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
        const val EXTRA_DATA14 = "EXTRA_DATA14"
        const val EXTRA_DATA15 = "EXTRA_DATA15"
        const val EXTRA_DATA16 = "EXTRA_DATA16"
        const val EXTRA_DATA17 = "EXTRA_DATA17"
        const val EXTRA_DATA18 = "EXTRA_DATA18"
        const val EXTRA_DATA19 = "EXTRA_DATA19"
        const val EXTRA_DATA20 = "EXTRA_DATA20"
        const val EXTRA_DATA21 = "EXTRA_DATA21"
        const val EXTRA_DATA22 = "EXTRA_DATA22"
        const val EXTRA_DATA23 = "EXTRA_DATA23"
        const val EXTRA_DATA24 = "EXTRA_DATA24"
        const val EXTRA_ALASANPELAKU = "EXTRA_ALASANPELAKU"
        const val EXTRA_ALASANKACAB = "EXTRA_ALASANKACAB"

    }

    private val STORAGE_CODE: Int = 100;

    val colorPrimary = BaseColor(BLACK)
    val FONT_SIZE_DEFAULT = 12f
    val FONT_SIZE_SMALL = 8f
    var basfontLight: BaseFont =
            BaseFont.createFont("assets/fonts/app_font_light.ttf", "UTF-8", BaseFont.EMBEDDED)
    var appFontLight = Font(basfontLight, FONT_SIZE_SMALL)

    var basfontRegular: BaseFont =
            BaseFont.createFont("assets/fonts/app_font_regular.ttf", "UTF-8", BaseFont.EMBEDDED)
    var appFontRegular = Font(basfontRegular, FONT_SIZE_DEFAULT)


    var basfontSemiBold: BaseFont =
            BaseFont.createFont("assets/fonts/app_font_semi_bold.ttf", "UTF-8", BaseFont.EMBEDDED)
    var appFontSemiBold = Font(basfontSemiBold, 8f)


    var basfontBold: BaseFont =
            BaseFont.createFont("assets/fonts/app_font_bold.ttf", "UTF-8", BaseFont.EMBEDDED)
    var appFontBold = Font(basfontBold, FONT_SIZE_DEFAULT)

    val PADDING_EDGE = 20f
    val TEXT_TOP_PADDING = 3f
    val TABLE_TOP_PADDING = 10f
    val TEXT_TOP_PADDING_EXTRA = 30f
    val BILL_DETAILS_TOP_PADDING = 50f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf)
        val namaveripdf = findViewById<TextView>(R.id.namaveripdf)
        val cabangpdf = findViewById<TextView>(R.id.cabangpdf)
        val terdugapdf = findViewById<TextView>(R.id.terdugapdf)
        val kelompokpdf =findViewById<TextView>(R.id.kelompokpdf)
        val kerugianpdf = findViewById<TextView>(R.id.perkiraankerugianpdf)
        val inamaveri = intent.getStringExtra(EXTRA_NAMAVERIVIKATOR).toString().trim()
        val icabang = intent.getStringExtra(EXTRA_NAMACABANG).toString().trim()
        val iterduga = intent.getStringExtra(EXTRA_TURUTTERLIBAT).toString().trim()
        val ikelompok = intent.getStringExtra(EXTRA_KELOMPOK).toString().trim()
        val ikerugian = intent.getStringExtra(EXTRA_KERUGIANSEMENTARA).toString().trim()

        namaveripdf.setText(inamaveri)
        cabangpdf.setText(icabang)
        terdugapdf.setText(iterduga)
        kelompokpdf.setText(ikelompok)
        kerugianpdf.setText(ikerugian)

        //handle button click
        val saveBtn = findViewById<Button>(R.id.btnPdf)
        saveBtn.setOnClickListener {
            //we need to handle runtime permission for devices with marshmallow and above
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                //system OS >= Marshmallow(6.0), check permission is enabled or not
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                    //permission was not granted, request it
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                }
                else{
                    //permission already granted, call savePdf() method
                    savePdf()
                }
            }
            else{
                //system OS < marshmallow, call savePdf() method
                savePdf()
            }
        }
    }

    private fun savePdf() {
        val namapdff = intent.getStringExtra(EXTRA_NAMAVERIVIKATOR).toString().trim()
        //create object of Document class
        appFontRegular.color = BaseColor.WHITE
        appFontRegular.size = 10f
        val mDoc = Document(A4, 0f, 0f, 0f, 0f)
        //pdf file name
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        //pdf file path
        val mFilePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + mFileName +".pdf"
        try {
            //create instance of PdfWriter class
           PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            val file = Uri.fromFile(File(mFilePath))
            val pdfRef = FirebaseStorage.getInstance().getReference().child("pdf/")
            pdfRef.putFile(file)
                    .addOnSuccessListener() { taskSnapshot ->
                        pdfRef.downloadUrl.addOnCompleteListener () { taskSnapshot ->

                            var url = taskSnapshot.result
                            println("url =" + url.toString())
                            val request = DownloadManager.Request(url)
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                            request.setTitle("laporan_$mFileName")
                            request.setDescription("The File Is Downloading ...")
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$mFileName.pdf")

                            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                            manager.enqueue(request)

                        }
                    }

            Log.d("loc", mFileName)
            //open the document for writing
            mDoc.open()
            kosong(mDoc)
            initInvoiceHeader(mDoc)
            kosong2(mDoc)
            line2(mDoc)
            kosong3(mDoc)
            hasil(mDoc)
            kosong3(mDoc)
            dilanggar(mDoc)
            kosong3(mDoc)
            alasan(mDoc)
            kosong3(mDoc)
            signature(mDoc)
            kosong4(mDoc)
            kosong3(mDoc)
            tindakan(mDoc)
            footer(mDoc)
            //add author of the document (metadata)
            mDoc.addAuthor(namapdff)
            mDoc.close()
            //show file saved message with file name and path $mFileName.pdf\nis saved to\n$mFilePath"
            Toast.makeText(this, "Succes Convert To Download", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            //if anything goes wrong causing exception, get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    private fun tindakan(mDoc: Document) {
        val tindakan = PdfPTable(1)
        tindakan.totalWidth = 2f
        appFontBold.size = 8f
        val texttindakan = PdfPCell(Phrase("Tindakan Kantor Pusat", appFontBold))
        texttindakan.border = Rectangle.LISTITEM
        texttindakan.paddingTop = 2f
        texttindakan.horizontalAlignment = Rectangle.ALIGN_LEFT
        tindakan.addCell(texttindakan)
        val kosong = PdfPTable(1)
        val ckosong = PdfPCell(Phrase("\b"))
        ckosong.border = Rectangle.LISTITEM
        ckosong.paddingTop = 30f
        kosong.addCell(ckosong)
        mDoc.add(tindakan)
        mDoc.add(kosong)
    }

    private fun kosong4(mDoc: Document) {
        val kosong = PdfPTable(3)
        kosong.setWidths(floatArrayOf(2f, 2f, 2f))
        kosong.totalWidth = 2f
        val veri = intent.getStringExtra(EXTRA_NAMAVERIVIKATOR).toString().trim()
        appFontBold.size = 8f
        val ckosong = PdfPCell(Phrase(veri, appFontBold))
        ckosong.border = Rectangle.LISTITEM
        ckosong.paddingTop = BILL_DETAILS_TOP_PADDING
        ckosong.horizontalAlignment = Rectangle.ALIGN_CENTER
        ckosong.verticalAlignment = Rectangle.ALIGN_BOTTOM
        val ckosong2 = PdfPCell(Phrase("\b"))
        ckosong2.border = Rectangle.LISTITEM
        ckosong2.paddingTop = BILL_DETAILS_TOP_PADDING
        val ckosong3 = PdfPCell(Phrase("\b"))
        ckosong3.border = Rectangle.LISTITEM
        ckosong3.paddingTop = BILL_DETAILS_TOP_PADDING
        kosong.addCell(ckosong)
        kosong.addCell(ckosong2)
        kosong.addCell(ckosong3)
        mDoc.add(kosong)
    }

    private fun signature(mDoc: Document) {
        appFontBold.size = 8f
        val signature = PdfPTable(3)
        signature.setWidths(
                floatArrayOf(
                        2f, 2f, 2f
                )
        )
        signature.totalWidth = 2f
        val textpemeriksa = PdfPCell(Phrase("Pemeriksa", appFontBold))
        textpemeriksa.border = Rectangle.LISTITEM
        textpemeriksa.paddingTop = 2f
        textpemeriksa.horizontalAlignment = Rectangle.ALIGN_CENTER
        signature.addCell(textpemeriksa)
        val textpelaku = PdfPCell(Phrase("Pelaku", appFontBold))
        textpelaku.border = Rectangle.LISTITEM
        textpelaku.paddingTop = 2f
        textpelaku.horizontalAlignment = Rectangle.ALIGN_CENTER
        signature.addCell(textpelaku)
        val textatasan = PdfPCell(Phrase("Atasan Langsung", appFontBold))
        textatasan.border = Rectangle.LISTITEM
        textatasan.paddingTop = 2f
        textatasan.horizontalAlignment = Rectangle.ALIGN_CENTER
        signature.addCell(textatasan)
        mDoc.add(signature)

    }

    private fun alasan(mDoc: Document) {
        val EXTRA_ALASANPELAKU = intent.getStringExtra(EXTRA_ALASANPELAKU).toString().trim()
        val EXTRA_ALASANKACAB = intent.getStringExtra(EXTRA_ALASANKACAB).toString().trim()

        val talasanPelaku = PdfPTable(1)
        talasanPelaku.totalWidth = 2f
        appFontRegular.color = BaseColor.BLACK
        appFontRegular.size = 12f
        appFontLight.size = 8f
        appFontBold.size = 8f
        val textAlasanPelaku = PdfPCell(Phrase("Alasan/Pembelaan Pelaku", appFontBold))
        textAlasanPelaku.border = Rectangle.LISTITEM
        textAlasanPelaku.paddingTop = 2f
        textAlasanPelaku.horizontalAlignment = Rectangle.ALIGN_LEFT
        talasanPelaku.addCell(textAlasanPelaku)

        val talasanPelaku2 = PdfPTable(1)
        talasanPelaku2.totalWidth = 2f
        appFontRegular.color = BaseColor.BLACK
        appFontRegular.size = 12f
        appFontLight.size = 8f
        val ialasanPelaku =
                PdfPCell(Phrase(EXTRA_ALASANPELAKU, appFontLight))
        ialasanPelaku.border = Rectangle.LISTITEM
        ialasanPelaku.paddingTop = 2f
        ialasanPelaku.horizontalAlignment = Rectangle.ALIGN_LEFT
        talasanPelaku2.addCell(ialasanPelaku)


        val talasanKacab = PdfPTable(1)
        talasanKacab.totalWidth = 2f
        appFontRegular.color = BaseColor.BLACK
        appFontRegular.size = 12f
        appFontLight.size = 8f
        val textAlasanKacab =
                PdfPCell(Phrase("Alasan/Pembelaan Atasan Langsung", appFontBold))
        textAlasanKacab.border = Rectangle.LISTITEM
        textAlasanKacab.paddingTop = 2f
        textAlasanKacab.horizontalAlignment = Rectangle.ALIGN_LEFT
        talasanKacab.addCell(textAlasanKacab)

        val talasanKacab2 = PdfPTable(1)
        talasanKacab2.totalWidth = 2f
        appFontRegular.color = BaseColor.BLACK
        appFontRegular.size = 12f
        appFontLight.size = 8f
        val ialasanKacab2 =
                PdfPCell(Phrase(EXTRA_ALASANKACAB, appFontLight))
        ialasanKacab2.border = Rectangle.LISTITEM
        ialasanKacab2.paddingTop = 2f
        ialasanKacab2.horizontalAlignment = Rectangle.ALIGN_LEFT
        talasanKacab2.addCell(ialasanKacab2)

        mDoc.add(talasanPelaku)
        mDoc.add(talasanPelaku2)
        mDoc.add(talasanKacab)
        mDoc.add(talasanKacab2)
    }

    private fun dilanggar(mDoc: Document) {
        val EXTRA_DATA14 = intent.getStringExtra(EXTRA_DATA14).toString().trim()
        val EXTRA_DATA15 = intent.getStringExtra(EXTRA_DATA15).toString().trim()
        val EXTRA_DATA16 = intent.getStringExtra(EXTRA_DATA16).toString().trim()
        val EXTRA_DATA17 = intent.getStringExtra(EXTRA_DATA17).toString().trim()
        val EXTRA_DATA18 = intent.getStringExtra(EXTRA_DATA18).toString().trim()
        val EXTRA_DATA19 = intent.getStringExtra(EXTRA_DATA19).toString().trim()
        val EXTRA_DATA20 = intent.getStringExtra(EXTRA_DATA20).toString().trim()
        val EXTRA_DATA21 = intent.getStringExtra(EXTRA_DATA21).toString().trim()
        val EXTRA_DATA22 = intent.getStringExtra(EXTRA_DATA22).toString().trim()
        val EXTRA_DATA23 = intent.getStringExtra(EXTRA_DATA23).toString().trim()
        val EXTRA_DATA24 = intent.getStringExtra(EXTRA_DATA24).toString().trim()

        val dilanggarTable = PdfPTable(1)
        dilanggarTable.totalWidth = 2f
        appFontRegular.color = BaseColor.BLACK
        appFontRegular.size = 12f
        appFontLight.size = 8f
        appFontBold.size = 8f
        val textdilanggar =
                PdfPCell(Phrase("Ketentuan Yang Dilanggar", appFontBold))
        textdilanggar.border = Rectangle.LISTITEM
        textdilanggar.paddingTop = 2f
        textdilanggar.horizontalAlignment = Rectangle.ALIGN_LEFT
        dilanggarTable.addCell(textdilanggar)
        val data14 = PdfPTable(1)
        data14.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA14!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA14", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data14.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data14.addCell(idata1)
        }
        val data15 = PdfPTable(1)
        data15.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA15!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA15", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data15.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data15.addCell(idata1)
        }
        val data16 = PdfPTable(1)
        data16.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA16!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA16", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data16.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data16.addCell(idata1)
        }
        val data17 = PdfPTable(1)
        data17.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA17!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA17", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data17.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data17.addCell(idata1)
        }
        val data18 = PdfPTable(1)
        data18.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA18!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA18", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data18.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data18.addCell(idata1)
        }
        val data19 = PdfPTable(1)
        data19.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA19!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA19", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data19.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data19.addCell(idata1)
        }
        val data20 = PdfPTable(1)
        data20.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA20!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA20", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data20.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data20.addCell(idata1)
        }
        val data21 = PdfPTable(1)
        data21.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA21!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA21", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data21.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data21.addCell(idata1)
        }
        val data22 = PdfPTable(1)
        data22.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA1!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA22", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data22.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data22.addCell(idata1)
        }
        val data23 = PdfPTable(1)
        data23.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA23!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA23", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data23.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data23.addCell(idata1)
        }
        val data24 = PdfPTable(1)
        data24.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA24!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA24", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data23.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data24.addCell(idata1)
        }
        mDoc.add(dilanggarTable)
        mDoc.add(data14)
        mDoc.add(data15)
        mDoc.add(data16)
        mDoc.add(data17)
        mDoc.add(data18)
        mDoc.add(data19)
        mDoc.add(data20)
        mDoc.add(data21)
        mDoc.add(data22)
        mDoc.add(data23)
        mDoc.add(data24)
    }

    private fun hasil(mDoc: Document) {
        val EXTRA_DATA1 = intent.getStringExtra(EXTRA_DATA1).toString().trim()
        val EXTRA_DATA2 = intent.getStringExtra(EXTRA_DATA2).toString().trim()
        val EXTRA_DATA3 = intent.getStringExtra(EXTRA_DATA3).toString().trim()
        val EXTRA_DATA4 = intent.getStringExtra(EXTRA_DATA4).toString().trim()
        val EXTRA_DATA5 = intent.getStringExtra(EXTRA_DATA5).toString().trim()
        val EXTRA_DATA6 = intent.getStringExtra(EXTRA_DATA6).toString().trim()
        val EXTRA_DATA7 = intent.getStringExtra(EXTRA_DATA7).toString().trim()
        val EXTRA_DATA8 = intent.getStringExtra(EXTRA_DATA8).toString().trim()
        val EXTRA_DATA9 = intent.getStringExtra(EXTRA_DATA9).toString().trim()
        val EXTRA_DATA10 = intent.getStringExtra(EXTRA_DATA10).toString().trim()
        val EXTRA_DATA11 = intent.getStringExtra(EXTRA_DATA11).toString().trim()
        val EXTRA_DATA12 = intent.getStringExtra(EXTRA_DATA12).toString().trim()
        val EXTRA_DATA13 = intent.getStringExtra(EXTRA_DATA13).toString().trim()

        appFontRegular.color = colorPrimary
        val risalahTable = PdfPTable(1)
        risalahTable.totalWidth = 2f
        appFontRegular.color = BaseColor.BLACK
        appFontRegular.size = 12f
        appFontLight.size = 8f
        appFontBold.size = 8f
        val textrisalah =
                PdfPCell(Phrase("Risalah Temuan", appFontBold))
        textrisalah.border = Rectangle.LISTITEM
        textrisalah.paddingTop = 2f
        textrisalah.horizontalAlignment = Rectangle.ALIGN_LEFT
        risalahTable.addCell(textrisalah)
        val data1 = PdfPTable(1)
        data1.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA1!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA1", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
           data1.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data1.addCell(idata1)
        }
        val data2 = PdfPTable(1)
        data2.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA2!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA2", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data2.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data2.addCell(idata1)
        }
        val data3 = PdfPTable(1)
        data3.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA3!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA3", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data3.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data3.addCell(idata1)
        }
        val data4 = PdfPTable(1)
        data4.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA4!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA4", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data4.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data4.addCell(idata1)
        }
        val data5 = PdfPTable(1)
        data5.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA5!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA5", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data5.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data5.addCell(idata1)
        }
        val data6 = PdfPTable(1)
        data6.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA6!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA6", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data6.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data6.addCell(idata1)
        }
        val data7 = PdfPTable(1)
        data7.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA7!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA7", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data7.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data7.addCell(idata1)
        }
        val data8 = PdfPTable(1)
        data8.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA8!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA8", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data8.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data8.addCell(idata1)
        }
        val data9 = PdfPTable(1)
        data9.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA9!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA9", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data9.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data9.addCell(idata1)
        }
        val data10 = PdfPTable(1)
        data10.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA10!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA10", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data10.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data10.addCell(idata1)
        }
        val data11 = PdfPTable(1)
        data11.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA11!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA11", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data11.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data11.addCell(idata1)
        }
        val data12 = PdfPTable(1)
        data12.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA12!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA12", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data12.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data12.addCell(idata1)
        }
        val data13 = PdfPTable(1)
        data13.totalWidth = 2f
        appFontLight.size = 8f
        if (EXTRA_DATA13!=""){
            val idata1 = PdfPCell(Phrase("- $EXTRA_DATA13", appFontLight))
            idata1.border = Rectangle.LISTITEM
            idata1.paddingTop = 2f
            idata1.horizontalAlignment = Rectangle.ALIGN_LEFT
            data13.addCell(idata1)
        }   else{
            val idata1 = PdfPCell(Phrase(""))
            data13.addCell(idata1)
        }

        mDoc.add(risalahTable)
        mDoc.add(data1)
        mDoc.add(data2)
        mDoc.add(data3)
        mDoc.add(data4)
        mDoc.add(data5)
        mDoc.add(data6)
        mDoc.add(data7)
        mDoc.add(data8)
        mDoc.add(data9)
        mDoc.add(data10)
        mDoc.add(data11)
        mDoc.add(data12)
        mDoc.add(data13)
    }

    private fun kosong2(mDoc: Document) {
        val kosong2 = PdfPTable(1)
        val ckosong2 = PdfPCell(Phrase("\b"))
        ckosong2.border = Rectangle.LISTITEM
        ckosong2.backgroundColor = colorPrimary
        kosong2.addCell(ckosong2)
        val kosong = PdfPTable(1)
        val ckosong = PdfPCell(Phrase("\b"))
        ckosong.border = Rectangle.LISTITEM
        kosong.addCell(ckosong)
        mDoc.add(kosong2)
        mDoc.add(kosong)
    }


    private fun kosong(mDoc: Document) {
        val kosong = PdfPTable(1)
        kosong.spacingAfter = 20f
        val ckosong = PdfPCell(Phrase(""))
        ckosong.border = Rectangle.NO_BORDER
        kosong.addCell(ckosong)
        mDoc.add(kosong)
    }
    private fun kosong3(mDoc: Document) {
        val kosong = PdfPTable(1)
        val ckosong = PdfPCell(Phrase("\b"))
        ckosong.border = Rectangle.LISTITEM
        kosong.addCell(ckosong)
        mDoc.add(kosong)
    }




    private fun footer(mDoc: Document) {
        appFontRegular.color = colorPrimary
        val footerTable = PdfPTable(1)
        footerTable.totalWidth = 2f
        appFontLight.size = 5f
        val thankYouCell =
                PdfPCell(Phrase("*This is a computer generated printout and no signature is required", appFontLight))
        thankYouCell.border = Rectangle.NO_BORDER
        thankYouCell.paddingTop = 50f
        thankYouCell.verticalAlignment = Rectangle.ALIGN_BOTTOM
        thankYouCell.horizontalAlignment = Rectangle.ALIGN_LEFT

        footerTable.addCell(thankYouCell)
        mDoc.add(footerTable)
    }

    private fun line2(mDoc: Document) {
        appFontLight.size = 8f
        appFontBold.size = 8f
        val terduga = PdfPTable(2)
        terduga.setWidths(
                floatArrayOf(
                        1f, 3f
                )
        )
        val textterduga = PdfPCell(Phrase("Terduga Pelaku", appFontBold))
        textterduga.border
        textterduga.paddingTop = 2f
        textterduga.horizontalAlignment = Rectangle.ALIGN_LEFT
        terduga.addCell(textterduga)
        val EXTRA_TERDUGA = intent.getStringExtra(EXTRA_TURUTTERLIBAT).toString().trim()
        val iterduga = PdfPCell(Phrase(EXTRA_TERDUGA, appFontLight))
        iterduga.border
        iterduga.paddingTop = 2f
        iterduga.horizontalAlignment= Rectangle.ALIGN_LEFT
        terduga.addCell(iterduga)

        val kelompok = PdfPTable(2)
        kelompok.setWidths(
                floatArrayOf(
                        1f, 3f
                )
        )
        val textkelompok = PdfPCell(Paragraph("Kelompok/Peminjam", appFontBold))
        textkelompok.border
        textkelompok.horizontalAlignment = Rectangle.ALIGN_LEFT
        kelompok.addCell(textkelompok)
        val EXTRAKELOMPOK =intent.getStringExtra(EXTRA_KELOMPOK).toString().trim()
        val ikelompok = PdfPCell(Phrase(EXTRAKELOMPOK, appFontLight))
        ikelompok.border
        ikelompok.horizontalAlignment = Rectangle.ALIGN_LEFT
        kelompok.addCell(ikelompok)

        val kerugian = PdfPTable(2)
        kerugian.setWidths(floatArrayOf(1f, 3f))
        val textkerugian = PdfPCell(Paragraph("Perkiraan Kerugian", appFontBold))
        textkerugian.border
        textkerugian.horizontalAlignment = Rectangle.ALIGN_LEFT
        kerugian.addCell(textkerugian)
        val EXTRAKERUGIAN = intent.getStringExtra(EXTRA_KERUGIANSEMENTARA).toString().trim()
        val ikerugian = PdfPCell(Phrase(EXTRAKERUGIAN, appFontLight))
        ikerugian.border
        ikerugian.horizontalAlignment = Rectangle.ALIGN_LEFT
        kerugian.addCell(ikerugian)
        mDoc.add(terduga)
        mDoc.add(kelompok)
        mDoc.add(kerugian)

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initInvoiceHeader(mDoc: Document) {
        appFontRegular.color = colorPrimary
        appFontBold.size = 8f
        val judulTable = PdfPTable(1)
        judulTable.totalWidth = 2f
        appFontRegular.color = BaseColor.BLACK
        appFontRegular.size = 12f
        appFontLight.size = 8f
        val thankYouCell =
                PdfPCell(Phrase("LAPORAN TEMUAN VERIFIKATOR", appFontRegular))
        thankYouCell.border = Rectangle.LISTITEM
        judulTable.spacingBefore = 20f
        thankYouCell.paddingTop = 2f
        thankYouCell.horizontalAlignment = Rectangle.ALIGN_LEFT
        judulTable.addCell(thankYouCell)
        val cabang = PdfPTable(2)
        cabang.setWidths(
                floatArrayOf(
                        1f, 3f
                )
        )
        val textcabang = PdfPCell(Phrase("Cabang", appFontBold))
        textcabang.border = Rectangle.LISTITEM
        textcabang.paddingTop = 2f
        textcabang.horizontalAlignment = Rectangle.ALIGN_LEFT
        cabang.addCell(textcabang)
        val EXTRANAMACABANG = intent.getStringExtra(EXTRA_NAMACABANG).toString().trim()
        val intentcabang = PdfPCell(Phrase(EXTRANAMACABANG, appFontLight))
        intentcabang.border = Rectangle.LISTITEM
        intentcabang.paddingTop = 2f
        intentcabang.horizontalAlignment = Rectangle.ALIGN_LEFT
        cabang.addCell(intentcabang)
        val periode = PdfPTable(2)
        periode.setWidths(
                floatArrayOf(
                        1f, 3f
                )
        )
        val textperiode = PdfPCell(Phrase("Periode", appFontBold))
        textperiode.border
        textperiode.paddingTop = 2f
        textperiode.horizontalAlignment = Rectangle.ALIGN_LEFT
        periode.addCell(textperiode)
        val EXTRA_PERIODE = intent.getStringExtra(EXTRA_PERIODE).toString().trim()
        val iperiode = PdfPCell(Phrase(EXTRA_PERIODE, appFontLight))
        iperiode.border
        iperiode.paddingTop = 2f
        iperiode.horizontalAlignment = Rectangle.ALIGN_LEFT
        periode.addCell(iperiode)
        mDoc.add(judulTable)
        mDoc.add(cabang)
        mDoc.add(periode)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted, call savePdf() method
                    savePdf()
                } else {
                    //permission from popup was denied, show error message
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}