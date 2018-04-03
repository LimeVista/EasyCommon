package me.limeice.common.datahelper.internal;

final class MetaData {

    MetaData() {
        super();
    }

    MetaData(short id) {
        this.id = id;
    }

    short id;

    short type;

    int size;

    long address;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MetaData && id == ((MetaData) obj).id;
    }
}
