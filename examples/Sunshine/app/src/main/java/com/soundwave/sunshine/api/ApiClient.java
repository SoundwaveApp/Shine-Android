package com.soundwave.sunshine.api;

import com.soundwave.sunshine.model.User;

import java.util.Date;
import java.util.UUID;

public class ApiClient {

    private Callback callback;

    public ApiClient(Callback callback) {
        setCallback(callback);
    }

    public interface Callback {
        void onRegisterSuccess(User user);
    }

    public void register(final String email, final String fullname, final Date dateOfBirth,
                         final String gender) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                randomDelay();
                if (callback != null) {
                    callback.onRegisterSuccess(new User(generateId(), email, fullname, dateOfBirth,
                            gender));
                }
            }
        }).start();

    }

    private void randomDelay() {
        long max = 1000;
        long min = 500;
        long delay = (long) (Math.random() * (max - min) + min);

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
