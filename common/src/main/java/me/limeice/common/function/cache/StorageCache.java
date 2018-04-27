package me.limeice.common.function.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import me.limeice.common.function.IOUtils;
import me.limeice.common.function.Objects;

public class StorageCache<V, BEAN> implements Cache<V, BEAN> {

    private static final String CACHE_DIR = "StorageCacheV1";

    private MemCache<V> memCache = null;

    private StorageCacheHelper<V, BEAN> mHelper;

    private File folder;

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
     * 磁盘缓存，不带内存缓存
     *
     * @param context  上下文容器
     * @param helper   存储助手
     * @param memCache 内存缓存助手
     */
    public StorageCache(@NonNull Context context, @NonNull StorageCacheHelper<V, BEAN> helper, @NonNull MemCache<V> memCache) {
        init(new File(context.getCacheDir(), CACHE_DIR), helper, null);
    }

    /**
     * 磁盘缓存，带内存缓存
     *
     * @param cacheFolder 文件路径
     * @param helper      存储助手
     * @param memCache    内存缓存助手
     */
    public StorageCache(@NonNull String cacheFolder, @NonNull StorageCacheHelper<V, BEAN> helper, @NonNull MemCache<V> memCache) {
        File file = new File(cacheFolder);
        memCache = Objects.requireNonNull(memCache, "Mem Cache must not null.");
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
        if (memCache != null)
            memCache.add(key, item);
        File cache = getCacheFile(key);
        if (cache.exists()) return false;
        WriterHelper helper = new WriterHelper(cache);
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
        if (cache.exists()) {
            File cacheBak = getCacheFileBak(key);
            if (cacheBak.exists()) {
                if (cacheBak.delete())
                    IOUtils.moveFile(cache, cacheBak);
            } else {
                IOUtils.moveFile(cache, cacheBak);
            }
        }
        WriterHelper helper = new WriterHelper(cache);
        try {
            mHelper.write(key, item, bean, helper);
        } catch (IOException ex) {
            ex.printStackTrace();
            IOUtils.moveFile(getCacheFileBak(key), cache);
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
        ReaderHelper helper = new ReaderHelper(cache);
        try {
            V item = mHelper.read(key, bean, helper);
            if (item != null && memCache != null)
                memCache.add(key, item);
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

    private File getCacheFile(String key) {
        return new File(folder, key);
    }

    private File getCacheFileBak(String key) {
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
