package local.home.unokochegarv2

//import jdk.nashorn.internal.objects.NativeDate.getTime
//import android.support.test.espresso.matcher.ViewMatchers.isChecked
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Switch
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*


class UnoActivity : AppCompatActivity() {
    private val datchiki = LinkedList<Int>()
    val MAXVALUE = 170
    val MINVALUE = 90

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uno)
        this.supportActionBar!!.hide()

        val numberPicker0 = findViewById<View>(R.id.numberPicker) as NumberPicker
        val numberPicker1 = findViewById<View>(R.id.numberPicker2) as NumberPicker
        val numberPicker2 = findViewById<View>(R.id.numberPicker2kotel) as NumberPicker
        val numberPicker3 = findViewById<View>(R.id.numberPicker22kotel) as NumberPicker
        val numberPicker5 = findViewById<View>(R.id.numberPicker23kotel) as NumberPicker
        val numberPicker4 = findViewById<View>(R.id.numberPicker33kotel) as NumberPicker
        val numberPicker6 = findViewById<View>(R.id.numberPicker4kotel) as NumberPicker
        val numberPicker7 = findViewById<View>(R.id.numberPicker24kotel) as NumberPicker
        val dataTimeLastUpdateBase = findViewById<View>(R.id.dataTimeLastBase) as TextView
        val remarkdataTimeLastUpdateBase = findViewById<View>(R.id.remarkDataTimeLastBase) as TextView
        val dateFormat = SimpleDateFormat("dd.MM.yyyy' 'HH:mm:ss.SS")
        val date = Date()
        dataTimeLastUpdateBase.text = dateFormat.format(date)


        //numberPicker0.setShowDividers(0);
        // numberPicker0.getChildAt(1);
        //  EditText edt=(EditText) numberPicker0.getChildAt(1);
        //edt.setTextSize (24);
        // edt.setAutoSizeTextTypeUniformWithConfiguration(24,40,1,1);
        //do customizations here

        //свитчи
        val switch1 = findViewById<View>(R.id.switch1) as Switch
        val switch2 = findViewById<View>(R.id.switch2) as Switch
        val switch3 = findViewById<View>(R.id.switch3) as Switch
        val switch4 = findViewById<View>(R.id.switch4) as Switch
        switch1.isChecked = true
        switch2.isChecked = true
        switch3.isChecked = true
        switch4.isChecked = true
        //4й котел отключен по умолчанию
        //   numberPicker6.setEnabled(false);
        //  numberPicker7.setEnabled(false);

        switch1.setOnClickListener {
            numberPicker0.isEnabled = switch1.isChecked
            numberPicker1.isEnabled = switch1.isChecked
        }

        switch2.setOnClickListener {
            numberPicker2.isEnabled = switch2.isChecked
            numberPicker3.isEnabled = switch2.isChecked
        }

        switch3.setOnClickListener {
            numberPicker4.isEnabled = switch3.isChecked
            numberPicker5.isEnabled = switch3.isChecked
        }

        switch4.setOnClickListener {
            numberPicker6.isEnabled = switch4.isChecked
            numberPicker7.isEnabled =  switch4.isChecked
        }


        //-------------------------------------------

        val sendData = findViewById<View>(R.id.button) as Button

        val getData = findViewById<View>(R.id.button2) as Button


        //getData.setEnabled(false);

        sendData.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("DataFromKotels")
            val myRef2 = database.getReference("SaveDataTime")
            datchiki.clear()
            //получение последних данных с датчиков
            datchiki.add(numberPicker0.value)
            datchiki.add(numberPicker1.value)
            datchiki.add(numberPicker2.value)
            datchiki.add(numberPicker3.value)
            datchiki.add(numberPicker4.value)
            datchiki.add(numberPicker5.value)
            datchiki.add(numberPicker6.value)
            datchiki.add(numberPicker7.value)

            datchiki.add(if (switch1.isChecked) 1 else 0)
            datchiki.add(if (switch2.isChecked) 1 else 0)
            datchiki.add(if (switch3.isChecked) 1 else 0)
            datchiki.add(if (switch4.isChecked) 1 else 0)
            //*********конец получения данных с датчиков

            myRef.setValue(datchiki)


            val date2 = Date()
            myRef2.setValue(dateFormat.format(date2.time))
//


            remarkdataTimeLastUpdateBase.text = "ОТПРАВИЛ В ТЫРНЕТ: "

            getData.isEnabled = true
        }


        getData.setOnClickListener {
            ////чтение из базы********************************
            val database = FirebaseDatabase.getInstance()
            val myRefget = database.getReference("DataFromKotels")
            val myRefget2 = database.getReference("SaveDataTime")
            myRefget.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.


                    numberPicker0.value = dataSnapshot.child("0").getValue(Int::class.java)?:0
                    numberPicker1.value = dataSnapshot.child("1").getValue(Int::class.java)?:0
                    numberPicker2.value = dataSnapshot.child("2").getValue(Int::class.java)?:0
                    numberPicker3.value = dataSnapshot.child("3").getValue(Int::class.java)?:0
                    numberPicker4.value = dataSnapshot.child("4").getValue(Int::class.java)?:0
                    numberPicker5.value = dataSnapshot.child("5").getValue(Int::class.java)?:0
                    numberPicker6.value = dataSnapshot.child("6").getValue(Int::class.java)?:0
                    numberPicker7.value = dataSnapshot.child("7").getValue(Int::class.java)?:0

                    switch1.isChecked = 1 == dataSnapshot.child("8").getValue(Int::class.java)?:0
                    numberPicker0.isEnabled = switch1.isChecked
                    numberPicker1.isEnabled = switch1.isChecked

                    switch2.isChecked = 1 == dataSnapshot.child("9").getValue(Int::class.java)?:0
                    numberPicker2.isEnabled = switch2.isChecked
                    numberPicker3.isEnabled = switch2.isChecked


                    switch3.isChecked = 1 == dataSnapshot.child("10").getValue(Int::class.java)?:0
                    numberPicker4.isEnabled = switch3.isChecked
                    numberPicker5.isEnabled =  switch3.isChecked

                    switch4.isChecked = 1 == dataSnapshot.child("11").getValue(Int::class.java)?:0
                    numberPicker6.isEnabled = switch4.isChecked
                    numberPicker7.isEnabled =  switch4.isChecked

                    getData.isEnabled = false


                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("HEY", "Failed to read value.", error.toException())
                }


            })

            myRefget2.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataTimeLastUpdateBase.text = dataSnapshot.getValue(String::class.java)


                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
            //************************

            remarkdataTimeLastUpdateBase.text = "ЗАГР. С ТЫРНЕТА: "
        }

        val textV1KDo = findViewById<View>(R.id.textView1kotelDo) as TextView
        val textV2Kposle = findViewById<View>(R.id.textView1kotelPosle) as TextView
        val textV2KDo = findViewById<View>(R.id.textView2kotelDo) as TextView
        val textV22Kposle = findViewById<View>(R.id.textView2kotelPosle) as TextView
        val textV3KDo = findViewById<View>(R.id.textView3kotelDo) as TextView
        val textV23Kposle = findViewById<View>(R.id.textView3kotelPosle) as TextView
        val textV4KDo = findViewById<View>(R.id.textView4kotelDo) as TextView
        val textV24Kposle = findViewById<View>(R.id.textView4kotelPosle) as TextView

///*******
        numberPicker0.setOnScrollListener { _, _ ->
            val v = numberPicker0.value
            textV1KDo.text = String.format("%d.%d", v / 10, v % 10)
            getData.isEnabled = true
        }
        numberPicker1.setOnScrollListener { _, _ ->
            val v = numberPicker1.value
            textV2Kposle.text = String.format("%d.%d", v / 10, v % 10)
            getData.isEnabled = true
        }
        numberPicker2.setOnScrollListener { _, _ ->
            val v = numberPicker2.value
            textV2KDo.text = String.format("%d.%d", v / 10, v % 10)
            getData.isEnabled = true
        }
        numberPicker3.setOnScrollListener { _, _ ->
            val v = numberPicker3.value
            textV22Kposle.text = String.format("%d.%d", v / 10, v % 10)
            getData.isEnabled = true
        }
        numberPicker4.setOnScrollListener { _, _ ->
            val v = numberPicker4.value
            textV3KDo.text = String.format("%d.%d", v / 10, v % 10)
            getData.isEnabled = true
        }
        numberPicker5.setOnScrollListener { _, _ ->
            val v = numberPicker5.value
            textV23Kposle.text = String.format("%d.%d", v / 10, v % 10)
            getData.isEnabled = true
        }
        numberPicker6.setOnScrollListener { _, _ ->
            val v = numberPicker6.value
            textV4KDo.text = String.format("%d.%d", v / 10, v % 10)
            getData.isEnabled = true
        }
        numberPicker7.setOnScrollListener { _, _ ->
            val v = numberPicker7.value
            textV24Kposle.text = String.format("%d.%d", v / 10, v % 10)
            getData.isEnabled = true
        }

        switch1.setOnCheckedChangeListener { _, _ -> getData.isEnabled = true }
        switch2.setOnCheckedChangeListener { _, _ -> getData.isEnabled = true }
        switch3.setOnCheckedChangeListener { _, _ -> getData.isEnabled = true }
        switch4.setOnCheckedChangeListener { _, _ -> getData.isEnabled = true }

        ///*****************

        numberPicker0.setFormatter { i -> String.format("%d.%d", i / 10, i % 10) }
        numberPicker1.setFormatter { i -> String.format("%d.%d", i / 10, i % 10) }
        numberPicker2.setFormatter { i -> String.format("%d.%d", i / 10, i % 10) }
        numberPicker3.setFormatter { i -> String.format("%d.%d", i / 10, i % 10) }
        numberPicker4.setFormatter { i -> String.format("%d.%d", i / 10, i % 10) }
        numberPicker5.setFormatter { i -> String.format("%d.%d", i / 10, i % 10) }
        numberPicker6.setFormatter { i -> String.format("%d.%d", i / 10, i % 10) }
        numberPicker7.setFormatter { i -> String.format("%d.%d", i / 10, i % 10) }

        numberPicker0.invalidate()
        numberPicker0.maxValue = MAXVALUE
        numberPicker0.minValue = MINVALUE
        numberPicker0.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker0.value = 155


        //попытка исправить баг с первым значением пикера
//
//        try {
//            numberPicker0.getClass().getSuperclass().getDeclaredMethod("changeValueByOne", boolean.class);
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }

        ///********


        numberPicker1.maxValue = MAXVALUE
        numberPicker1.minValue = MINVALUE
        numberPicker1.value = 135

        numberPicker1.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker1.invalidate()


        //22222222222222222222222*************2


        numberPicker2.maxValue = MAXVALUE
        numberPicker2.minValue = MINVALUE
        numberPicker2.value = 155
        numberPicker2.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker2.invalidate()



        numberPicker3.maxValue = MAXVALUE
        numberPicker3.minValue = MINVALUE
        numberPicker3.value = 135
        numberPicker3.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker3.invalidate()
        //**********

        //33333333333333333333333333333333333


        numberPicker4.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker4.maxValue = MAXVALUE
        numberPicker4.minValue = MINVALUE
        numberPicker4.value = 135
        numberPicker4.invalidate()



        numberPicker5.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker5.maxValue = MAXVALUE
        numberPicker5.minValue = MINVALUE
        numberPicker5.value = 155
        numberPicker5.invalidate()
        //**********

        //44444444444444444444444444444



        numberPicker6.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker6.maxValue = MAXVALUE
        numberPicker6.minValue = MINVALUE
        numberPicker6.value = 135
        numberPicker6.invalidate()



        numberPicker7.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker7.maxValue = MAXVALUE
        numberPicker7.minValue = MINVALUE
        numberPicker7.value = 155
        numberPicker7.invalidate()
        //**********

///убираем зеленые цифры

//        textV1KDo.visibility = View.INVISIBLE
//        textV2Kposle.visibility = View.INVISIBLE
//
//        textV2KDo.visibility = View.INVISIBLE
//        textV22Kposle.visibility = View.INVISIBLE
//
//        textV3KDo.visibility = View.INVISIBLE
//        textV23Kposle.visibility = View.INVISIBLE
//
//        textV4KDo.visibility = View.INVISIBLE
//        textV24Kposle.visibility = View.INVISIBLE
        ///

        val textKotel1 = findViewById<View>(R.id.textView1Kotel) as TextView
        val textKotel2 = findViewById<View>(R.id.textView1Kotel2) as TextView
        val textKotel3 = findViewById<View>(R.id.textView1Kotel3) as TextView
        val textKotel4 = findViewById<View>(R.id.textView1Kotel4) as TextView


    }
}
