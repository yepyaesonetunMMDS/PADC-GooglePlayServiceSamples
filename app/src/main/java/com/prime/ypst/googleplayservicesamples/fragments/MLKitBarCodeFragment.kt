package com.prime.ypst.googleplayservicesamples.fragments


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.prime.ypst.googleplayservicesamples.R
import kotlinx.android.synthetic.main.activity_barcode.*

/**
 * A simple [Fragment] subclass.
 */
class MLKitBarCodeFragment : Fragment() {

    companion object {
        const val TAG = "MLKitBarCodeFragment"
        fun newInstance(): Fragment {
            return MLKitBarCodeFragment()
        }
    }

    lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_barcode, container, false)
        val imageView: ImageView = view.findViewById(R.id.image_view)
        textView = view.findViewById(R.id.text_view)
        val btnDetect: Button = view.findViewById(R.id.btnDetectBarCode)

        val barcodeBitmap = BitmapFactory.decodeResource(
            resources, R.drawable.barcode_1
        )

        imageView.setImageBitmap(barcodeBitmap)

        btnDetect.setOnClickListener { barcodeDetector(barcodeBitmap) }


        return view


    }


    private fun barcodeDetector(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        //FirebaseVisionBarcodeDetectorOptions.Builder builder = new FirebaseVisionBarcodeDetectorOptions.Builder();
        //FirebaseVisionBarcodeDetectorOptions options = builder.setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE).build();
        val detector = FirebaseVision.getInstance().visionBarcodeDetector
        detector.detectInImage(image).addOnSuccessListener { firebaseVisionBarcodes ->
            textView.setText(
                getInfoFromBarcode(firebaseVisionBarcodes)
            )
        }
            .addOnFailureListener { textView.setText("Error") }
    }

    private fun getInfoFromBarcode(barcodes: List<FirebaseVisionBarcode>): String {
        val result = StringBuilder()
        for (barcode in barcodes) {
            //int valueType = barcode.getValueType();
            result.append(barcode.rawValue!! + "\n")
        }
        return if ("" == result.toString()) {
            "Error"
        } else {
            result.toString()
        }
    }

}
