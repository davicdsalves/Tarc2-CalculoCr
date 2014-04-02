package br.com.unirio.cr;

import android.content.Context;
import android.content.Intent;

public class Broadcaster {

    public static void updateCr(Context context) {
        Intent intent = new Intent(Constants.BROADCAST_UPDATE_CR);
        context.sendBroadcast(intent);
    }

    public static void updateCra(Context context) {
        Intent intent = new Intent(Constants.BROADCAST_UPDATE_CRA);
        context.sendBroadcast(intent);
    }

}
