package me.limeice.common.datahelper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Map;

import me.limeice.common.datahelper.internal.AllDataReader;
import me.limeice.common.datahelper.internal.MetaDataReader;
import me.limeice.common.function.BytesUtils;

@SuppressWarnings("WeakerAccess")
public class DataHelper implements IDataHelper {

    static final byte STATE_EXIST = 1;

    static final byte STATE_NOT_EXIST = 2;

    static final byte STATE_TYPE_ERROR = 3;

    static final byte STATE_UNKNOW = 0;

    static final byte STATE_NOT_LOAD = -1;

    static final byte STATE_OK = 4;

    //////////////////////////////////////////

    private File mFile;

    private byte mState = STATE_NOT_LOAD;

    private long mFileLength = 0L;

    private byte mode;

    private DataHelperVerInfo mInfo = new DataHelperVerInfo();

    RandomAccessFile mRAF;

    private Reader mReader;

    public DataHelper(@NonNull String filePath) {
        this(new File(filePath), MODE_READ_ALL);
    }

    public DataHelper(@NonNull File file) {
        this(file, MODE_READ_ALL);
    }

    public DataHelper(@NonNull String filePath, byte mode) {
        this(new File(filePath), mode);
    }

    public DataHelper(@NonNull File file, byte mode) {
        mFile = file;
        switch (mode) {
            case MODE_READ_ALL:
                mode = MODE_READ_ALL;
                break;
            case MODE_READ_ID:
                mode = MODE_READ_ID;
            default:
                throw new RuntimeException("Mode is error.");
        }
    }

    public void load() throws IOException {
        mState = mFile.exists() ? STATE_EXIST : STATE_NOT_EXIST;
        if (mState == STATE_EXIST) {
            if ((mFileLength = mFile.length()) < DataHelperVerInfo.MIN_SIZE)
                mState = STATE_TYPE_ERROR;
            else {
                try {
                    mRAF = new RandomAccessFile(mFile, "r");
                    if (!readHead()) {
                        mState = STATE_TYPE_ERROR;
                        return;
                    }
                    mState = STATE_OK;
                    reloadData();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    @NonNull
    public DataHelperVerInfo getInfo() {
        return null;
    }

    @Override
    @Nullable
    public <T> T read(short id) {
        return null;
    }

    @Override
    @NonNull
    public <T> T read(short id, @NonNull T defaultVal) {
        return null;
    }

    @Override
    public <T> void write(short id, @NonNull T value) {

    }

    @Override
    @Nullable
    public <T extends List> T readList(short id) {
        return null;
    }

    @Override
    @NonNull
    public <T extends List> T readList(short id, @NonNull T inst) {
        return null;
    }

    @Override
    public <T extends Map> T readMap(short id) {
        return null;
    }

    @Override
    @NonNull
    public <T extends Map> T readMap(short id, @NonNull T inst) {
        return null;
    }

    private boolean readHead() throws IOException {
        byte[] bs = new byte[DataHelperVerInfo.MIN_SIZE];
        int len = mRAF.read(bs);
        if (len < DataHelperVerInfo.MIN_SIZE) return false;
        for (int i = 0; i < DataHelperVerInfo.TAG.length; i++) {
            if (DataHelperVerInfo.TAG[i] != bs[i])
                return false;
        }
        mInfo.setVer(BytesUtils.getShort(bs, 6));
        mInfo.setUserVer(BytesUtils.getShort(bs, 8));
        return true;
    }

    private void reloadData() throws IOException {
        if (mode == MODE_READ_ALL) {
            mReader = new AllDataReader(mRAF);
        } else {
            mReader = new MetaDataReader(mRAF);
        }
    }
}
