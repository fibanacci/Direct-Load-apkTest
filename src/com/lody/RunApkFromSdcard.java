package com.lody;

import java.io.File;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.lody.plugin.LPluginOpener;
import com.lody.plugin.api.LPluginBug;
import com.lody.plugin.api.LPluginBugListener;
import com.lody.plugin.manager.LPluginBugManager;
import com.lody.sample.R;

/**
 * Created by lody  on 2015/4/4.
 */
@SuppressLint("NewApi")
public class RunApkFromSdcard extends Activity {
    public static final String TAG = "wx";
	Button button;
    EditText editText;
    File sdDir = null; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_from_sd);
        button = (Button) findViewById(R.id.open_plugin);
        editText = (EditText) findViewById(R.id.editText);
        
        button.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
				try {

					 boolean sdCardExist = Environment.getExternalStorageState()   
	                          .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在 


					  if (sdCardExist)      //如果SD卡存在，则获取跟目录
					  {                               
					    sdDir = Environment.getExternalStorageDirectory();//获取跟目录 
					 } 
					  else {
			                Toast.makeText(RunApkFromSdcard.this,"sd卡不存在",Toast.LENGTH_SHORT).show();
					}
	                String path = sdDir.toString()+editText.getText().toString();
	                Toast.makeText(RunApkFromSdcard.this, "path:"+path,Toast.LENGTH_SHORT).show();
	                if(path.isEmpty()){
	                    Toast.makeText(RunApkFromSdcard.this, "Please enter the SD card path！", Toast.LENGTH_SHORT).show();
	                    return;
	                }
	                File file = new File(path);
	                if(!file.exists()){
	                    Toast.makeText(RunApkFromSdcard.this,"Not found APK In their path！！",Toast.LENGTH_SHORT).show();
	                    return;
	                }
	                Toast.makeText(RunApkFromSdcard.this,"APK exists！",Toast.LENGTH_SHORT).show();

	                LPluginBugManager.addBugListener(new LPluginBugListener() {
	                    @Override
	                    public void OnError(LPluginBug bug) {

	                        Log.e("DEBUG", bug.error.getMessage());
	                        android.os.Process.killProcess(bug.processId);
	                        System.exit(10);
	                    }
	                });
	                LPluginOpener.startPlugin(RunApkFromSdcard.this, path);
				} catch (Exception e) {
	                Toast.makeText(RunApkFromSdcard.this,"exception",Toast.LENGTH_SHORT).show();

				}
				 
            }
        });
    }
}

