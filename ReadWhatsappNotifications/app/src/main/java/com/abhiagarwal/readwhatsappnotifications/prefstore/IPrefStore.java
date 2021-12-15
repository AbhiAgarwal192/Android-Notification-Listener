package com.abhiagarwal.readwhatsappnotifications.prefstore;

import android.content.Context;

import java.util.Set;

public interface IPrefStore {
    void initialize(Context context);

    Set<String> read(Context context);

    void write(Context context, String message);

    void delete(Context context, String message);

    void clearAll(Context context);
}
