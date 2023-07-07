package org.daimhim.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.daimhim.container.ContextHelper
import org.daimhim.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView.text = "进程名：${ContextHelper.getProcessName()}"
        binding.textView2.setOnClickListener {
            startActivity(Intent(this,ChildProcessActivity::class.java))
        }
    }
}