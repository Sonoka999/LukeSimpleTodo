package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }

        loadItems()

        //define recyclerView and adapter in scope and then attach them to each other and the layout manager
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks , onLongClickListener)
        recyclerView.adapter= adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //on click of "Button"...
        findViewById<Button>(R.id.button).setOnClickListener {
            //...execute following code
            //grab text from field and add it to the list of tasks
            val userInputtedTask = findViewById<EditText>(R.id.addTaskField).text.toString()
            listOfTasks.add(userInputtedTask)
            //notify adapter
            adapter.notifyItemInserted(listOfTasks.size-1)
            //reset text field
            findViewById<EditText>(R.id.addTaskField).setText("")
            //save items
            saveItems()



        }



    }

    //save data
    //by writing and reading from a file

    //make method to get file we need
    fun getDataFile() : File {


        //Every line is going to represent a specific task in our list of tasks
        return File(filesDir,"data.txt")
    }

    //load items by reading every line in file
    fun loadItems(){
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch(ioException : IOException){}

    }
    //save items by writing them into file
    fun saveItems(){
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        }
        catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
}