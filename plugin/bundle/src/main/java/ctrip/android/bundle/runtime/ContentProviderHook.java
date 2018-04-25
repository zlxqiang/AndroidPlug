package ctrip.android.bundle.runtime;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import ctrip.android.bundle.hack.AndroidHack;

import static ctrip.android.bundle.hack.AndroidHack.getActivityThread;

/**
 * Created by admin on 2018/4/25.
 */

public class ContentProviderHook {

    /** * 在进程内部安装provider, 也就是调用 ActivityThread.installContentProviders方法
     *
     * @param context you know
     * @param apkFile
     * @throws Exception
     */
    public static void installProviders(Context context, File apkFile) throws Exception {
        List<ProviderInfo> providerInfos = parseProviders(apkFile);

        for (ProviderInfo providerInfo : providerInfos) {
            String pn = context.getPackageName();
            providerInfo.applicationInfo.packageName = pn;
        }

        Log.d("ContentProviderHook", providerInfos.toString());
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
      //  Object currentActivityThread = currentActivityThreadMethod.invoke(null);
        Object currentActivityThread =AndroidHack.getActivityThread();
        Method installProvidersMethod = activityThreadClass.getDeclaredMethod("installContentProviders", Context.class, List.class);
        installProvidersMethod.setAccessible(true);
        installProvidersMethod.invoke(currentActivityThread, context, providerInfos);
    }

    /**
     * 解析Apk文件中的 <provider>, 并存储起来
     * 主要是调用PackageParser类的generateProviderInfo方法
     *
     * @param apkFile 插件对应的apk文件
     * @throws Exception 解析出错或者反射调用出错, 均会抛出异常
     */
    public static List<ProviderInfo> parseProviders(File apkFile) throws Exception {
        Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");

        //*****************************
        Constructor<?>[] con = packageParserClass.getConstructors();
        Method m[] = packageParserClass.getDeclaredMethods(); // 取得全部的方法
        for (int i = 0; i < m.length; i++) {
            String mod = Modifier.toString(m[i].getModifiers()); // 取得访问权限
            String metName = m[i].getName(); // 取得方法名称
            Class<?> ret = m[i].getReturnType();// 取得返回值类型
            Class<?> param[] = m[i].getParameterTypes(); // 得到全部的参数类型
            Class<?> exc[] = m[i].getExceptionTypes(); // 得到全部的异常
            System.out.print(mod + " " + con.length);//输出每一方法的访问权限
            System.out.print(ret + " ");//输出每一方法的返回值类型
            System.out.print(metName + " (");//输出每一方法的名字
            for (int x = 0; x < param.length; x++) {//循环输出每一方法的参数的类型
                System.out.print(param[x] + "arg-" + x);
                if (x < param.length - 1) {
                    System.out.print(",");
                }
            }
            System.out.print(") ");
            if (exc.length > 0) {// 有异常抛出
                System.out.print("throws ");
                for (int x = 0; x < exc.length; x++) {//循环输出每一方法所抛出的异常名字
                    System.out.print(exc[x].getName());
                    if (x < param.length - 1) {
                        System.out.print(",");
                    }
                }
            }
            System.out.println();
        }


        //***************************************
        Method parsePackageMethod;
        Object packageParser;
        Object packageObj;
        try {
            parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);
            packageParser = packageParserClass.newInstance();
            // 首先调用parsePackage获取到apk对象对应的Package对象
            packageObj = parsePackageMethod.invoke(packageParser, apkFile, PackageManager.GET_PROVIDERS);
        } catch (NoSuchMethodException e) {
            //华为
            parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, String.class, DisplayMetrics.class, int.class);
            packageParser = packageParserClass.getConstructor(String.class).newInstance(apkFile.getAbsolutePath());
            // 首先调用parsePackage获取到apk对象对应的Package对象
            packageObj = parsePackageMethod.invoke(packageParser, apkFile,null,null, PackageManager.GET_PROVIDERS);
        }

        // 读取Package对象里面的services字段
        // 接下来要做的就是根据这个List<Provider> 获取到Provider对应的ProviderInfo
        Field providersField = packageObj.getClass().getDeclaredField("providers");
        List providers = (List) providersField.get(packageObj);

        // 调用generateProviderInfo 方法, 把PackageParser.Provider转换成ProviderInfo
        Class<?> packageParser$ProviderClass = Class.forName("android.content.pm.PackageParser$Provider");
        Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
        Class<?> userHandler = Class.forName("android.os.UserHandle");
        Method getCallingUserIdMethod = userHandler.getDeclaredMethod("getCallingUserId");
        int userId = (Integer) getCallingUserIdMethod.invoke(null);
        Object defaultUserState = packageUserStateClass.newInstance();

        // 需要调用 android.content.pm.PackageParser#generateProviderInfo
        Method generateProviderInfo = packageParserClass.getDeclaredMethod("generateProviderInfo",
                packageParser$ProviderClass, int.class, packageUserStateClass, int.class);

        List<ProviderInfo> ret = new ArrayList<>();
        // 解析出intent对应的Provider组件
        for (Object service : providers) {
            ProviderInfo info = (ProviderInfo) generateProviderInfo.invoke(packageParser, service, 0, defaultUserState, userId);
            ret.add(info);
        }

        return ret;
    }


}
