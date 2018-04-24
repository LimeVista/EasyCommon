# EasyCommon [![](https://jitpack.io/v/LimeVista/EasyCommon.svg)](https://jitpack.io/#LimeVista/EasyCommon)
An Android Common Component（一个用于Android快速开发小工具集合）

## How to
* To get a Git project into your build:
* Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

* Step 2. Add the dependency
```groovy
 compile 'com.github.LimeVista:EasyCommon:0.6.0'
```

* Step 3.CompileOptions
```groovy
android {
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
``` 
## Method
* AppManager: Application Manager(应用管理) => [AppManager.kt](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/base/app/AppManager.kt).
```kotlin
    fun finishActivity()                        // 结束当前Activity

    fun addActivity(activity: Activity)         // 添加Activity

    fun currentActivity(): Activity?            // 获取当前Activity

    fun finishActivity(activity: Activity)      // 结束指定Activity

    fun removeActivity(activity: Activity)      // 移除指定的Activity

    fun <T> returnToActivity(clazz: Class<T>    // 返回到指定的Activity

    fun <T> isOpenActivity(clazz: Class<T>)     //是否已经打开指定的activity

    fun appExit(isBackground: Boolean)          // Exit Application, 退出应用程序

    fun Activity.finishCurrent()                // 结束当前Activity
```
* BytesUtils: Bytes Utils(字节流处理工具) => [BytesUtils.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/BytesUtils.java).
```java
    /**
     * Convert byte array to hex string => 将字节数组转换为16进制字符串
     *
     * @param bytes byte array, eg:[0x1A,0x2C,0x3B] => 输入 [0x1A,0x2C,0x3B]
     * @return hex string, eg: 1A2C3B => 得到 1A2C3B
     */
    @NonNull public static String toHexString(@NonNull byte[] bytes) 

    /**
     * Convert byte to hex value => 将字节转换为16进制表示
     *
     * @param b byte, eg: 8 => 输入 8 
     * @return hex value, eg: 0x08 => 得到 0x08
     */
    @NonNull public static String convert(byte b) 

    /**
     * Convert byte array to hex values => 将字节数组转换为16进制字符串表示
     *
     * @param bytes byte array, eg: [8,1] => 输入 [8,1]
     * @return hex value, eg: [0x08, 0x01] => 得到(包含中括号) [0x08, 0x01]
     */
    @NonNull public static String convert(@NonNull final byte[] bytes) 

    /**
     * Convert hex string to byte array=> 将16进制字符串转换为字节数组
     *
     * @param hexString hex string eg: 1A2C3B => 输入 1A2C3B
     * @return byte array eg: [0x1A,0x2C,0x3B] =>得到 [0x1A,0x2C,0x3B]
     */
    @Nullable public static byte[] hexStringToBytes(@Nullable String hexString)
```
* AppUtils: App Utils(App处理工具) => [AppUtils.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/AppUtils.java).
```java
  /**
     * 获取版本信息
     *
     * @param context 上下文容器
     * @return 版本信息（若获取失败返回null）
     */
    public static String getVerName(@NonNull Context context)

    /**
     * 获取版本号
     *
     * @param context 上下文容器
     * @return 版本号（若获取失败返回0）
     */
    public static int getVerCode(@NonNull Context context)

    /**
     * 检测服务是否运行
     *
     * @param context   上下文容器
     * @param className 服务类名
     * @return 运行状态
     */
    public static boolean isServiceRunning(@NonNull Context context, String className) 

    /**
     * 停止运行中的服务
     *
     * @param context   上下文容器
     * @param className 服务类名
     * @return 是否执行成功
     * @throws ClassNotFoundException 无找到此类
     */
    public static boolean stopRunningService(@NonNull Context context, String className)
```

* CloseUtils: Close Stream Utils(关闭流处理工具) => [CloseUtils.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/CloseUtils.java).
```java
    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables)

    /**
     * 关闭IO(静默操作)
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(final Closeable... closeables) 

    /**
     * 关闭IO(静默操作)
     *
     * @param closeable closeable
     */
    public static void closeIOQuietly(final Closeable closeable)
```
* ColorUtils: Color Utils(颜色处理工具) => [ColorUtils.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/ColorUtils.java).
```java
    /**
     * 根据Attribute属性的资源ID值，获取颜色值
     *
     * @param context Context容器
     * @param attr    属性ID
     * @return 颜色值
     */
    public static int getAttrColor(@NonNull Context context, @AttrRes int attr)

    /**
     * 根据Resources的Color id值生成ColorDrawable
     *
     * @param context Context容器
     * @param id      res中颜色的id值
     * @return ColorDrawable画布
     */
    @NonNull public static ColorDrawable getColorDrawable(@NonNull Context context, @ColorRes int id) 

    /**
     * 从ColorInt提取颜色值，色值0~255
     *
     * @param color 颜色值
     * @return 数组长度为4, <code> array[0] = A; array[1] = R; array[2] = G; array[3] = B </code>
     */
    @NonNull public static byte[] getARGB(@ColorInt int color)

    /**
     * 从ColorInt提取颜色值，色值0~255
     *
     * @param color 颜色值
     * @return 数组长度为3, array[0] = R; array[1] = G; array[2] = B </code>
     */
    @NonNull public static byte[] getRGB(@ColorInt int color) 
```
* IOUtils: Input Output Utils(输入输出及文件处理工具) => [IOUtils.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/IOUtils.java).
```java
     /**
     * 从输入流中读取数据，并转换为Byte数组
     *
     * @param inStream 待操作的输入流
     * @return Byte数组形式的html文件
     * @throws IOException 各种异常，包括IOException
     */
    @NonNull public static byte[] read(@NonNull InputStream inStream) 

    /**
     * 从输入流中读取数据，并转换为Byte数组
     *
     * @param file 文件
     * @return Byte数组形式的html文件
     * @throws IOException IOException
     */
    @NonNull public static byte[] read(@NonNull File file)

    /**
     * 从输入流中读取数据，并转换为Byte数组
     *
     * @param filePath 文件路径
     * @return Byte数组形式的html文件
     * @throws IOException IOException
     */
    @NonNull public static byte[] read(@NonNull String filePath) 

    /**
     * 写入数据
     *
     * @param filePath 文件路径（如果存在覆盖，否则创建）
     * @param msg      文本（被写入数据）
     * @throws IOException IOException
     */
    public static void write(@NonNull String filePath, @NonNull String msg) 

    /**
     * 写入数据
     *
     * @param filePath 文件路径（如果存在覆盖，否则创建）
     * @param bytes    字节数据（被写入数据）
     * @throws IOException IOException
     */
    public static void write(@NonNull String filePath, @NonNull byte[] bytes) 

    /**
     * 写入数据
     *
     * @param file 文件（如果存在覆盖，否则创建）
     * @param msg  文本（被写入数据）
     * @throws IOException IOException
     */
    public static void write(@NonNull File file, @NonNull String msg)

    /**
     * 写入数据
     *
     * @param file  文件（如果存在覆盖，否则创建）
     * @param bytes 字节数据（被写入数据）
     * @throws IOException IOException
     */
    public static void write(@NonNull File file, @NonNull byte[] bytes) 

    /**
     * 写入数据
     *
     * @param file  文件（如果存在追加，否则创建）
     * @param bytes 字节数据（被写入数据）
     * @throws IOException IOException
     */
    public static void writeAppend(@NonNull File file, @NonNull byte[] bytes)
    /**
     * 写入数据
     *
     * @param file   文件（如果存在覆盖，否则创建）
     * @param bytes  字节数据（被写入数据）
     * @param offset 偏移量
     * @param len    写入长度
     * @throws IOException IOException
     */
    public static void write(@NonNull File file, @NonNull byte[] bytes, int offset, int len) 

    /**
     * 检查文件是否存在，否则创建
     *
     * @param file 文件
     * @throws IOException IOException
     */
    public static void checkFileIfNotExistCreate(File file)

    /**
     * 压缩文件
     *
     * @param input  输入流（源文件）
     * @param output 输出流（压缩文件）
     * @throws IOException IOE
     */
    public static void zip(@NonNull InputStream input, @NonNull OutputStream output)

    /**
     * 解压文件
     *
     * @param input  输入流(压缩文件)
     * @param output 输出流（源文件）
     * @throws IOException IOE
     */
    public static void unzip(@NonNull InputStream input, @NonNull OutputStream output)

    /**
     * 文件复制
     *
     * @param input  输入文件（被复制文件）
     * @param output 输出文件（复制文件）
     * @return 是否复制成功
     */
    public static boolean copyFile(@Nullable File input, @Nullable File output)

    /**
     * 移动文件
     *
     * @param input  输入文件
     * @param output 输出文件
     * @return 是否移动成功{@code true}成功否则失败
     */
    public static boolean moveFile(@Nullable File input, @Nullable File output)
```
* DensityUtils: Density Utils(屏幕分辨率处理工具) => [DensityUtils.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/DensityUtils.java).
```java
 /**
     * 根据手机的分辨率从 dp 值 转成为 px(像素)值
     *
     * @param context 被转换值的所在Context容器
     * @param dpValue dp值
     * @return px(像素)值
     */
    public static int dip2px(Context context, float dpValue) 

    /**
     * 根据手机的分辨率从 px(像素)值 转成为 dp 值
     *
     * @param context 被转换值的所在Context容器
     * @param pxValue px(像素)值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue)

    /**
     * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变
     *
     * @param pxValue px(像素)值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue)
    /**
     * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变
     *
     * @param spValue sp值
     * @return px(像素)值
     */
    public static int sp2px(Context context, float spValue) 

    /**
     * 获取屏幕宽度
     *
     * @param context 被转换值的所在Context容器
     * @return (px像素)屏幕宽度
     */
    public static int getScreenWidth(Context context)

    /**
     * 获取屏幕高度
     *
     * @param context 被转换值的所在Context容器
     * @return (px像素)屏幕高度
     */
    public static int getScreenHeight(Context context)

    /**
     * 获取状态栏高度
     *
     * @param context 被转换值的所在Context容器
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context)

    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeight(Context context)

    /**
     * 获取屏幕真实高度
     * Android 4.2开始才有的API，在这之前可能无效
     *
     * @param context 上下文容器
     * @return 屏幕高度
     */
    public static int getScreenRealHeight(Context context) 

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context 上下文容器
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(@NonNull Context context) 

    /**
     * 判断屏幕最小宽度是否大于600dp
     *
     * @param context 上下文容器
     * @return {@code true} 大于或等于，{@code false}小于
     */
    public static boolean isSW600dp(@NonNull Context context) 
    /**
     * 判断屏幕最小宽度是否大于720dp
     *
     * @param context 上下文容器
     * @return {@code true} 大于或等于，{@code false}小于
     */
    public static boolean isSW720dp(@NonNull Context context)
```
* DeepClone: Deep Clone Utils(深度复制工具) => [DeepClone.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/DeepClone.java).
```java
   /**
     * Deep clone => 深度复制
     *
     * @param obj a Object extends Serializable => 被克隆对象
     * @param <T> Object Type          => 输入类泛型
     * @return copy of the object      => 复制对象
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static <T extends Serializable> T deepClone(T obj) 

    /**
     * serialize a object => 序列化对象
     *
     * @param file save file path   => 保存文件
     * @param obj  object           => 被序列化对象
     * @param <T>  extends Serializable
     */
    public static <T extends Serializable> void serialize(@Nullable File file, @Nullable T obj) 

    /**
     * deserialize a Object => 反序列化对象
     *
     * @param file file         => 获取文件
     * @param <T>  Object Class => 类泛型
     * @return Object           => 实例化对象
     */
    @Nullable public static <T extends Serializable> T deserialize(@NonNull File file) 
```
* AES128: Advanced Encryption Standard Utils(AES加密解密工具) => [AES128.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/algorithm/security/AES128.java).
```java

    public static final int NONE = 0x00;                // NONE MODE

    public static final int CBC = 0x10;                 // CBC MODE

    public static final int CFB = 0x20;                 // CFB MODE

    public static final int ECB = 0x30;                 // ECB MODE

    public static final int OFB = 0x40;                 // OFB MODE

    public static final int NoPadding = 0x00;           // NONE Padding

    public static final int ISO10126Padding = 0x01;     // ISO10126 Padding

    public static final int PKCS5Padding = 0x02;        // PKCS5Padding

    public static final int SSL3Padding = 0x03;         // SSL3Padding

    // 初始化类(包含MODE + Padding)
    AES128 aes=new AES128(AES128.ECB|AES128.PKCS5Padding);
    // 加密实例，得到 X69FC9eSW/nesHOiCCT2Nfs/FVTm8PDvyOC8OcX3rRU=
    String sMsg = aes.encryptBase64("0123456789ABCDEF", "Lime"); 
    // 解密实例,得到 0123456789ABCDEF
    String msg = aes.decryptBase64("X69FC9eSW/nesHOiCCT2Nfs/FVTm8PDvyOC8OcX3rRU=", "Lime");

   /**
     * 使用AES-128算法对数据进行加密
     *
     * @param msg      加密数据，如果NoPadding，加密数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到密文
     */
    public byte[] encrypt(@NonNull byte[] msg, @NonNull byte[] keyBytes)

    /**
     * 使用AES-128算法对数据进行解密
     *
     * @param msg      需要解密的数据，数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到明文
     */
    public byte[] decrypt(@NonNull byte[] msg, @NonNull byte[] keyBytes)

    /**
     * 使用AES-128算法对数据进行加密
     *
     * @param msg      加密数据，如果NoPadding，加密数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到密文
     */
    @NonNull public byte[] encrypt(@NonNull String msg, @NonNull byte[] keyBytes)

    /**
     * 使用AES-128算法对数据进行加密
     *
     * @param msg      加密数据，如果NoPadding，加密数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到密文
     */
    @NonNull public String encryptBase64(@NonNull String msg, @NonNull byte[] keyBytes)

    /**
     * 使用AES-128算法对数据进行加密
     *
     * @param msg  加密数据，如果NoPadding，加密数据长度必须为16的倍数！
     * @param sKey 解密密钥，经过一次MD5
     * @return 得到密文
     */
    @NonNull public String encryptBase64(@NonNull String msg, @NonNull String sKey) 

    /**
     * 使用AES-128算法对数据进行解密
     *
     * @param msg      需要解密的数据，数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到明文
     */
    @NonNull public byte[] decrypt(@NonNull String msg, @NonNull byte[] keyBytes)

    /**
     * 使用AES-128算法对数据进行解密
     *
     * @param msg      需要解密的数据，数据长度必须为16的倍数！
     * @param keyBytes 解密密钥，必须位16位密码
     * @return 得到明文
     */
    @NonNull public String decryptBase64(@NonNull String msg, @NonNull byte[] keyBytes)

    /**
     * 使用AES-128算法对数据进行解密
     *
     * @param msg  需要解密的数据，数据长度必须为16的倍数！
     * @param sKey 解密密钥，经过一次MD5
     * @return 得到明文
     */
    @NonNull public String decryptBase64(@NonNull String msg, @NonNull String sKey) 
```
* Devices: Devices Utils(设备相关工具) => [DevicesUtils.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/DevicesUtils.java).
```java
    /**
     * 获得CPU核心数
     *
     * @return CPU核心数
     */
    public static int getNumCores()

    /**
     * 获取处理器信息
     *
     * @return 处理器信息
     * @throws IOException 获取/读取失败
     */
    @NonNull
    public static String getProcessorName() throws IOException

    /**
     * 获取CPU详细信息
     *
     * @return CPU详细信息文本
     * @throws IOException 获取/读取失败
     */
    @NonNull
    public static String getCpuInfo() throws IOException 
    /**
     * 获取WIFI的MAC地址
     *
     * @param context 上下文容器
     * @return MAC地址
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_WIFI_STATE)
    public static String getWifiMac(@NonNull Context context)

    /**
     * 获取设备内存相关信息
     *
     * @return 返回详细内存信息描述
     * @throws IOException IOE
     */
    @NonNull
    public static Map<String, String> getMemInfo() throws IOException

    /**
     * 获取设备IMEI和设备序列号，数组索引（index）第0个为IMEI，第1个为SimSerialNumber
     *
     * @param context 上下文容器
     * @return IMEI和SimSerialNumber(值可能为空)
     */
    @NonNull
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String[] getImeiWithSN(@NonNull Context context)

    /**
     * 获取设备型号
     *
     * @return 设备型号
     */
    @NonNull
    public static String getDevicesModel()

    /**
     * 获取设备制造商
     *
     * @return 设备制造商
     */
    @NonNull
    public static String getManufacturer()

    /**
     * 获取全局UUID
     *
     * @param context 上下文容器
     * @return UUID
     */
    @Nullable
    public static String getUUID(@NonNull Context context) 

    /**
     * 初始化并获取屏幕相关信息
     *
     * @param activity {@link Activity}
     * @return DisplayMetrics
     */
    @NonNull
    public static DisplayMetrics getDisplayMetrics(@NonNull Activity activity)
```
* Hash: Hash Utils(哈希算法) => [Hash.java](https://github.com/LimeVista/EasyCommon/blob/master/common/src/main/java/me/limeice/common/function/algorithm/security/Hash.java).
```java
    /**
     * MD5加密算法
     *
     * @param msg 被加密源
     * @return 获得加密文本
     */
    @NonNull public static String md5(@NonNull String msg) 

    /**
     * MD5加密算法
     *
     * @param msg 被加密源
     * @return 获得加密数据
     */
    @NonNull public static byte[] md5ToBytes(@NonNull String msg)

    /**
     * SHA-1加密算法
     *
     * @param msg 被加密源
     * @return 获得加密文本
     */
    @NonNull public static String sha1(@NonNull String msg) 
    
    /**
     * SHA-256加密算法
     *
     * @param msg 被加密源
     * @return 获得加密文本
     */
    @NonNull public static String sha256(@NonNull String msg) 

    /**
     * SHA-384加密算法
     *
     * @param msg 被加密源
     * @return 获得加密文本
     */
    @NonNull public static String sha384(@NonNull String msg)

    /**
     * SHA-512加密算法
     *
     * @param msg 被加密源
     * @return 获得加密文本
     */
    @NonNull public static String sha512(@NonNull String msg) 
    /**
     * CRC32加密算法
     *
     * @param msg [String]被加密源
     * @return 加密值
     */
    @NonNull public static long crc32(@NonNull String msg) 

    /**
     * 执行信息摘要算法加密
     *
     * @param algorithm [String]加密算法类型
     * @param msg       [String]需要加密的内容
     * @return 加密数组
     */
    @NonNull public static byte[] encode(@NonNull String algorithm, @NonNull String msg)
```
* Kotlin Extensions
```kotlin
    inline fun Int.r()              // Red(红)

    inline fun Int.g()              // Green(绿)

    inline fun Int.b()              // Blue(蓝)

    inline fun Int.a()              // Alpha(透明通道)

    inline fun Int.getARGB()        // 从ColorInt提取颜色值，色值0~255

    inline fun Int.getRGB()         // 从ColorInt提取颜色值，色值0~255

    inline fun Context.getAttrColor(@AttrRes attr: Int)     // ColorUtils#getAttrColor(Context, Int)

    inline fun Context.getColorDrawable(@ColorRes id: Int)  //  ColorUtils#getColorDrawable(Context, Int)

    inline fun ByteArray.toHexString()      /* Convert byte array to hex string. eg:[0x1A,0x2C,0x3B] -> 1A2C3B */

    inline fun ByteArray.convert()          /* Convert byte array to hex values. eg: [8,1] -> [0x08, 0x01] */

    inline fun Byte.convert()               /* Convert byte to hex value. eg: 8 -> 0x08 */

    inline fun String?.hexStringToBytes()   /* Convert hex string to byte array. eg: 1A2C3B-> [0x1A,0x2C,0x3B] */

    /**
    * 根据手机的分辨率从 dp 值 转成为 px(像素)值
    *
    * @param dpValue dp值
    * @return px(像素)值
    */
    fun Activity.dip2px(dpValue: Float)

    /**
    * 根据手机的分辨率从 px(像素)值 转成为 dp 值
    *
    * @param pxValue px(像素)值
    * @return dp值
    */
    fun Activity.px2dip(pxValue: Float)

    /**
    * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变
    *
    * @param pxValue px(像素)值
    * @return sp值
    */
    fun Activity.px2sp(pxValue: Float)

    /**
    * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变
    *
    * @param spValue sp值
    * @return px(像素)值
    */
    fun Activity.sp2px(spValue: Float)

    /**
    * 获取屏幕宽度
    *
    * @return (px像素)屏幕宽度
    */
    fun Activity.getScreenWidth()

    /**
    * 获取屏幕高度
    *
    * @return (px像素)屏幕高度
    */
    fun Activity.getScreenHeight()

    /**
    * 根据手机的分辨率从 dp 值 转成为 px(像素)值
    *
    * @param dpValue dp值
    * @return px(像素)值
    */
    fun Context.dip2px(dpValue: Float)

    /**
    * 根据手机的分辨率从 px(像素)值 转成为 dp 值
    *
    * @param pxValue px(像素)值
    * @return dp值
    */
    fun Context.px2dip(pxValue: Float)

    /**
    * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变
    *
    * @param pxValue px(像素)值
    * @return sp值
    */
    fun Context.px2sp(pxValue: Float)

    /**
    * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变
    * @param spValue sp值
    * @return px(像素)值
    */
    fun Context.sp2px(spValue: Float)

    /**
    * 获取屏幕宽度
    *
    * @return (px像素)屏幕宽度
    */
    fun Context.getScreenWidth()

    /**
    * 获取屏幕高度
    *
    * @return (px像素)屏幕高度
    */
    fun Context.getScreenHeight()

    /**
    * 根据手机的分辨率从 dp 值 转成为 px(像素)值
    *
    * @param dpValue dp值
    * @return px(像素)值
    */
    fun View.dip2px(dpValue: Float)

    /**
    * 根据手机的分辨率从 px(像素)值 转成为 dp 值
    *
    * @param pxValue px(像素)值
    * @return dp值
    */
    fun View.px2dip(pxValue: Float)

    /**
    * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变
    *
    * @param pxValue px(像素)值
    * @return sp值
    */
    fun View.px2sp(pxValue: Float)

    /**
    * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变
    *
    * @param spValue sp值
    * @return px(像素)值
    */
    fun View.sp2px(spValue: Float)

    /**
    * 获取屏幕宽度
    *
    * @return (px像素)屏幕宽度
    */
    fun View.getScreenWidth()

    /**
    * 获取屏幕高度
    *
    * @return (px像素)屏幕高度
    */
    fun View.getScreenHeight()

    /**
    * 根据手机的分辨率从 dp 值 转成为 px(像素)值
    *
    * @param dpValue dp值
    * @return px(像素)值
    */
    fun BasePresenter<*, *>.dip2px(dpValue: Float)

    /**
    * 根据手机的分辨率从 px(像素)值 转成为 dp 值
    *
    * @param pxValue px(像素)值
    * @return dp值
    */
    fun BasePresenter<*, *>.px2dip(pxValue: Float)

    /**
    * 根据手机分辨率，将 px 值转换为 sp 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
    *
    * @param pxValue px(像素)值
    * @return sp值
    */
    fun BasePresenter<*, *>.px2sp(pxValue: Float)

    /**
    * 根据手机分辨率，将 sp 值转换为 px 值，保证文字大小不变，利用（DisplayMetrics类中属性scaledDensity）
    *
    * @param spValue sp值
    * @return px(像素)值
    */
    fun BasePresenter<*, *>.sp2px(spValue: Float)

    /**
    * 获取屏幕宽度
    *
    * @return (px像素)屏幕宽度
    */
    fun BasePresenter<*, *>.getScreenWidth()

    /**
    * 获取屏幕高度
    *
    * @return (px像素)屏幕高度
    */
    fun BasePresenter<*, *>.getScreenHeight()

    /* 判断Android设备是否为平板 */
    inline fun <reified T : Context> T.isPad()

    /* 判断Android设备是否为平板 */
    inline fun <reified T : View> T.isPad()

    /**
     * 获取版本信息
    *
    * @return 版本信息（若获取失败返回null）
    */
    inline fun <reified T : Context> T.getVerName(): String?

    /**
     * 获取版本号
     *
     * @return 版本号（若获取失败返回0）
     */
    inline fun <reified T : Context> T.getVerCode(): Int

    /**
     * 检测服务是否运行
     *
     * @param className 服务类名
     * @return 运行状态
     */
    inline fun <reified T : Context> T.isServiceRunning(className: String): Boolean

    /**
     * 停止运行中的服务
     *
     * @param className 服务类名
     * @return 是否执行成功
     * @throws ClassNotFoundException 无找到此类
     */
    @Throws(ClassNotFoundException::class)
    inline fun <reified T : Context> T.stopRunningService(className: String): Boolean
```
