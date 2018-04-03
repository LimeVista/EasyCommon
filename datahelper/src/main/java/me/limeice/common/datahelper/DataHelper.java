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
import me.limeice.common.datahelper.internal.IDataReader;
import me.limeice.common.datahelper.internal.MetaDataReader;
import me.limeice.common.datahelper.internal.WriterImpl;
import me.limeice.common.function.BytesUtils;

public class DataHelper implements IDataHelper {

    private File mFile;

    private byte mState = STATE_NOT_LOAD;

    private byte mode;

    private DataHelperVerInfo mInfo = new DataHelperVerInfo();

    private RandomAccessFile mRAF;

    private IDataReader mReader;

    private boolean isLoad = false;

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
                this.mode = MODE_READ_ALL;
                break;
            case MODE_READ_ID:
                this.mode = MODE_READ_ID;
            default:
                throw new IllegalArgumentException("mode type error");
        }
    }

    public byte getState() {
        return mState;
    }

    @Override
    @NonNull
    public DataHelperVerInfo getInfo() {
        return mInfo;
    }

    @Override
    @NonNull
    public Reader reader() {
        if (!isLoad)
            try {
                reload();
            } catch (IOException ex) {
                throw new IORuntimeException(ex);
            }
        requireStateOK();
        return mReader;
    }

    @Override
    @NonNull
    public Writer writer() {
        return new WriterImpl(mReader, mFile, mInfo);
    }

    @Override
    public void reload() throws IOException {
        mState = mFile.exists() ? STATE_EXIST : STATE_NOT_EXIST;
        if (mState == STATE_EXIST) {
            if (mFile.length() < DataHelperVerInfo.MIN_SIZE)
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
        isLoad = true;
    }

    /**
     * 读取头信息
     *
     * @return 是否读取成功
     */
    private boolean readHead() throws IOException {
        byte[] bs = new byte[DataHelperVerInfo.MIN_SIZE];
        int len = mRAF.read(bs);
        if (len < DataHelperVerInfo.MIN_SIZE) return false;
        for (int i = 0; i < DataHelperVerInfo.TAG.length; i++) {
            if (DataHelperVerInfo.TAG[i] != bs[i])
                return false;
        }
        mInfo.setVer(BytesUtils.getShort(bs, DataHelperVerInfo.TAG.length));
        mInfo.setUserVer(BytesUtils.getShort(bs, DataHelperVerInfo.TAG.length + 2));
        return true;
    }

    /**
     * 重新加载数据
     */
    private void reloadData() throws IOException {
        if (mode == MODE_READ_ALL) {
            mReader = new AllDataReader(mRAF);
        } else {
            mReader = new MetaDataReader(mRAF);
        }
    }

    private void requireStateOK() {
        if (mState != STATE_OK) {
            String str = "";
            switch (mState) {
                case STATE_TYPE_ERROR:
                    str += "File corrupted or file type error!";
                    break;
                case STATE_NOT_EXIST:
                    str += "File not found!";
                    break;
            }
            throw new IllegalStateException("State exception -> " + mState + ", " + str);
        }
    }
}
