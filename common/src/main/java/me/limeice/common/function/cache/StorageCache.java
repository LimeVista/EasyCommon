package me.limeice.common.function.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import me.limeice.common.function.IOUtils;
import me.limeice.common.function.Objects;
import me.limeice.common.function.helper.ReaderSource;
import me.limeice.common.function.helper.StorageCacheHelper;
import me.limeice.common.function.helper.WriterSource;

@SuppressWarnings("WeakerAccess")
public class StorageCache<V, BEAN> implements Cache<V, BEAN> {

    public static final String CACHE_DIR = "RxStorageCacheV1"; // 默认缓存路径

    protected MemCache<V> memCache = null;                     // 内存缓存

    private StorageCacheHelper<V, BEAN> mHelper;               // 磁盘操作助手

    private File folder;                                       // 缓存文件夹

    private int duration = 0;                                  // 缓存生命，默认不过期

    /**
     * 磁盘缓存，不带内存缓存
     *
     * @param context 上下文容器
     * @param helper  存储助手
     */
    public StorageCache(@NonNull Context context, @NonNull StorageCacheHelper<V, BEAN> helper) {
        init(new File(context.getCacheDir(), CACHE_DIR), helper, null);
    }

    /**
     * 磁盘缓存，不带内存缓存,自定义存储路径
     *
     * @param cachePath 保存文件夹
     * @param helper    存储助手
     */
    public StorageCache(@NonNull String cachePath, @NonNull StorageCacheHelper<V, BEAN> helper) {
        init(new File(cachePath), helper, null);
    }

    /**
     * 磁盘缓存，带内存缓存
     *
     * @param context  上下文容器
     * @param helper   存储助手
     * @param memCache 内存缓存助手
     */
    public StorageCache(
            @NonNull Context context,
            @NonNull StorageCacheHelper<V, BEAN> helper,
            @NonNull MemCache<V> memCache) {
        init(new File(context.getCacheDir(), CACHE_DIR), helper, memCache);
    }

    /**
     * 磁盘缓存，带内存缓存
     *
     * @param cacheFolder 文件路径
     * @param helper      存储助手
     * @param memCache    内存缓存助手
     */
    public StorageCache(
            @NonNull String cacheFolder,
            @NonNull StorageCacheHelper<V, BEAN> helper,
            @NonNull MemCache<V> memCache) {
        File file = new File(cacheFolder);
        memCache = Objects.requireNonNull(memCache, "Mem Cache must not null.");
        init(file, helper, memCache);
    }

    /**
     * 生成精简版磁盘缓存，不带内存缓存，不使用 Bean
     *
     * @param context 上下文容器
     * @param helper  存储助手
     */
    public static <VAL> StorageCacheLite<VAL> buildLite(
            @NonNull Context context,
            @NonNull StorageCacheHelper<VAL, Object> helper) {
        return new StorageCacheLite<>(context, helper);
    }

    /**
     * 生成精简版磁盘缓存，带内存缓存，不使用 Bean
     *
     * @param context  上下文容器
     * @param helper   存储助手
     * @param memCache 内存缓存助手
     */
    public static <VAL> StorageCacheLite<VAL> buildLite(
            @NonNull Context context,
            @NonNull StorageCacheHelper<VAL, Object> helper,
            @NonNull MemCache<VAL> memCache) {
        return new StorageCacheLite<>(context, helper, memCache);
    }

    /**
     * 生成精简版磁盘缓存，带内存缓存,自定义路径，不使用 Bean
     *
     * @param cacheFolder 上下文容器
     * @param helper      存储助手
     * @param memCache    内存缓存助手
     */
    public static <VAL> StorageCacheLite<VAL> buildLite(
            @NonNull String cacheFolder,
            @NonNull StorageCacheHelper<VAL, Object> helper,
            @NonNull MemCache<VAL> memCache) {
        return new StorageCacheLite<>(cacheFolder, helper, memCache);
    }

    protected StorageCache(File file, StorageCacheHelper<V, BEAN> helper, MemCache<V> memCache) {
        init(file, helper, memCache);
    }

    private void init(File file, StorageCacheHelper<V, BEAN> helper, MemCache<V> memCache) {
        if (!file.exists())
            if (!file.mkdirs())
                throw new RuntimeException(file.getAbsolutePath() + "creation failure");
        mHelper = Objects.requireNonNull(helper);
        this.memCache = memCache;
        folder = file;
    }

    /**
     * 添加到缓存（如果存在则放弃添加）
     *
     * @param key  唯一编号
     * @param item 数据
     * @param bean 数据Bean(用于辅助数据操作)
     * @return 是否添加成功，{@code true}，添加成功，且不存在重复数据
     */
    @Override
    public boolean add(@NonNull String key, V item, @Nullable BEAN bean) {
        if (memCache != null && memCache.get(key) == null) {
            memCache.add(key, item);
        }
        File cache = getCacheFile(key);
        if (cache.exists()) return false;
        WriterSource helper = new WriterSource(cache);
        try {
            mHelper.write(key, item, bean, helper);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            helper.close();
        }
        return false;
    }

    /**
     * 添加到缓存（如果存在则放弃添加）
     *
     * @param key  唯一编号
     * @param item 数据
     * @return 是否添加成功，{@code true}，添加成功，且不存在重复数据
     */
    @Override
    public boolean add(@NonNull String key, V item) {
        return add(key, item, null);
    }

    /**
     * 添加到缓存（如果存在则覆盖）
     *
     * @param key  唯一编号
     * @param item 数据
     * @param bean 数据Bean(用于辅助数据操作)
     */
    @Override
    public void addOrOverlay(@NonNull String key, V item, @Nullable BEAN bean) {
        if (memCache != null)
            memCache.add(key, item);

        File cache = getCacheFile(key);
        File cacheBak = getCacheFileBak(key);
        if (cacheBak.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cacheBak.delete();
        }
        WriterSource helper = new WriterSource(cacheBak);
        try {
            mHelper.write(key, item, bean, helper);
            helper.close();
            if (cache.exists() && (!cache.delete()))
                return;
            IOUtils.moveFile(cacheBak, cache);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            helper.close();
        }
    }

    /**
     * 添加到缓存（如果存在则覆盖）
     *
     * @param key  唯一编号
     * @param item 数据
     */
    @Override
    public void addOrOverlay(@NonNull String key, V item) {
        addOrOverlay(key, item, null);
    }

    /**
     * 获得数据
     *
     * @param key 唯一编号
     * @return 数据，如果 {@code return null} , 则数据不存在
     */
    @Nullable
    @Override
    public V get(@NonNull String key) {
        return get(key, null);
    }

    /**
     * 获得数据
     *
     * @param key  唯一编号
     * @param bean 数据Bean(用于辅助数据操作)
     * @return 数据，如果 {@code return null} , 则数据不存在
     */
    @Nullable
    @Override
    public V get(@NonNull String key, @Nullable BEAN bean) {
        if (memCache != null) {
            V item = memCache.get(key);
            if (item != null) return item;
        }
        File cache = getCacheFile(key);
        if (!cache.exists())
            return null;
        long lastModified = cache.lastModified();
        if (duration > 0 && System.currentTimeMillis() - lastModified > duration) {
            remove(key);
            return null;
        }
        ReaderSource helper = new ReaderSource(cache);
        try {
            V item = mHelper.read(key, bean, helper);
            if (item != null && memCache != null)
                memCache.add(key, item, lastModified + duration);
            return item;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            helper.close();
        }
        return null;
    }

    /**
     * 很据编号获取缓存
     *
     * @param key 唯一编号
     */
    @Override
    public void remove(@NonNull String key) {
        if (memCache != null)
            memCache.remove(key);
        File cache = getCacheFile(key);
        if (!cache.exists())
            return;
        //noinspection ResultOfMethodCallIgnored
        cache.delete();
        //noinspection ResultOfMethodCallIgnored
        getCacheFileBak(key).delete();
    }

    /**
     * 清空缓存
     */
    @Override
    public void clean() {
        if (memCache != null)
            memCache.clean();
        deleteFilesInDir(folder);
        //noinspection ResultOfMethodCallIgnored
        folder.mkdirs();
    }

    /**
     * 生命周期（内存缓存在内存中最大超时，超出时间后，删除），单位：秒
     *
     * @return 生命周期
     */
    @Override
    public int getDuration() {
        return duration;
    }

    /**
     * 设置生命周期(当内存缓存时，内存缓存会被设置与磁盘缓存超时相同)
     *
     * @param duration 维持时间：单位秒
     */
    @Override
    public void setDuration(int duration) {
        this.duration = duration <= 0 ? 0 : duration * 1000;
        if (memCache != null) memCache.setDuration(duration);
    }

    /**
     * 清除超生命周期（过期）数据
     */
    @Override
    public synchronized void cleanInvalid() {
        for (File f : folder.listFiles()) {
            if (f.isFile() && f.lastModified() + duration < System.currentTimeMillis()) {
                //noinspection ResultOfMethodCallIgnored
                f.delete();
            }
        }
    }

    /**
     * 精简版
     *
     * @param <VAL>
     */
    public static class StorageCacheLite<VAL> extends StorageCache<VAL, Object> {

        /**
         * 磁盘缓存，不带内存缓存
         *
         * @param context 上下文容器
         * @param helper  存储助手
         */
        StorageCacheLite(@NonNull Context context,
                         @NonNull StorageCacheHelper<VAL, Object> helper) {
            super(context, helper);
        }

        /**
         * 磁盘缓存，不带内存缓存
         *
         * @param context  上下文容器
         * @param helper   存储助手
         * @param memCache 内存缓存助手
         */
        StorageCacheLite(@NonNull Context context,
                         @NonNull StorageCacheHelper<VAL, Object> helper,
                         @NonNull MemCache<VAL> memCache) {
            super(context, helper, memCache);
        }

        /**
         * 磁盘缓存，带内存缓存
         *
         * @param cacheFolder 文件路径
         * @param helper      存储助手
         * @param memCache    内存缓存助手
         */
        StorageCacheLite(@NonNull String cacheFolder,
                         @NonNull StorageCacheHelper<VAL, Object> helper,
                         @NonNull MemCache<VAL> memCache) {
            super(cacheFolder, helper, memCache);
        }
    }

    public File getCacheFile(String key) {
        return new File(folder, key);
    }

    public File getCacheFileBak(String key) {
        return new File(folder, key + "_$FILE$BAK$_");
    }

    @SuppressWarnings("UnusedReturnValue")
    private static boolean deleteFilesInDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }
}