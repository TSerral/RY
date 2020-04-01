package com.Serral.ty;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import android.app.Activity;

public class MainActivity extends Activity {
		private static final int REQUEST_CODE_ACTIVE_COMPONENT = 1;
		private DevicePolicyManager devicePolicyManager = null;
		private ComponentName componentName = null;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);


				devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
				componentName = new ComponentName(this, MyDeviceAdminReceiver.class);

				
				//计算器
				findViewById(R.id.tz).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
									Intent intent=new Intent(MainActivity.this, JSQ.class);
									startActivity(intent);
								}
						});

				//激活设备管理器
				findViewById(R.id.btn_active).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
									if (isAdminActive()) {
											Toast.makeText(MainActivity.this, "设备管理器已激活", Toast.LENGTH_SHORT).show();
										} else {
											// 打开管理器的激活窗口
											Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
											// 指定需要激活的组件
											intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
											intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "安卓秘制管理器，既实惠，又方便，好处多，权限大。\n你看同意行不行，奥利给！兄弟们！造他就完了！");
											startActivityForResult(intent, REQUEST_CODE_ACTIVE_COMPONENT);
										}
								}
						});

				//取消激活设备管理器
				findViewById(R.id.btn_cancel_active).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
									if (isAdminActive()) {
											devicePolicyManager.removeActiveAdmin(componentName);
										}
								}
						});

				//修改锁屏密码
				findViewById(R.id.btn_change_password).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
									if (isAdminActive()) {
										new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("如果你忘了设置的密码会出大问题，所以默认设置密码为0000")
											.setNegativeButton("取消",null)
											.setPositiveButton("确定", new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int which) {
											devicePolicyManager.resetPassword("0000", 0);
											// 立刻锁屏
											devicePolicyManager.lockNow();
												}})
											.show();
										} else {
											Toast.makeText(MainActivity.this, "设备管理器未激活", Toast.LENGTH_SHORT).show();
										}
								}
						});

				// 取消锁屏密码
				findViewById(R.id.btn_cancel_password).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
									if (isAdminActive()) {
											devicePolicyManager.resetPassword("", 0);
											Toast.makeText(MainActivity.this, "已取消", Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(MainActivity.this, "设备管理器未激活", Toast.LENGTH_SHORT).show();
										}
								}
						});
				//设置锁屏时间
				findViewById(R.id.btn_lock_time).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
									if (isAdminActive()) {
											devicePolicyManager.setMaximumTimeToLock(componentName, 5000);
											Toast.makeText(MainActivity.this, "已设置", Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(MainActivity.this, "设备管理器未激活", Toast.LENGTH_SHORT).show();
										}
								}
						});

				//取消锁屏时间
				findViewById(R.id.btn_cancel_lock_time).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
									if (isAdminActive()) {
											devicePolicyManager.setMaximumTimeToLock(componentName, Long.MAX_VALUE);
											Toast.makeText(MainActivity.this, "已取消", Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(MainActivity.this, "设备管理器未激活", Toast.LENGTH_SHORT).show();
										}
								}
						});

				//恢复出厂设置
				findViewById(R.id.btn_wipe_data).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
									if (isAdminActive()) {
											new AlertDialog.Builder(MainActivity.this).setTitle("是否恢复出厂设置？").setMessage("温馨提示:自己选择，风险自担，后果自负，如有问题，不要找我")
												.setNegativeButton("取消",null)
												.setPositiveButton("确定", new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog, int which) {
																devicePolicyManager.wipeData(0);
															}})
												.show();
										} else {
											Toast.makeText(MainActivity.this, "设备管理器未激活", Toast.LENGTH_SHORT).show();
										}
								}
						});
			}

	//判断该组件是否有系统管理员的权限
		private boolean isAdminActive() {
				return devicePolicyManager.isAdminActive(componentName);
			}
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				super.onActivityResult(requestCode, resultCode, data);
			}
}

	//最后一次更新：2019.9.14
	//作者QQ:2********7
