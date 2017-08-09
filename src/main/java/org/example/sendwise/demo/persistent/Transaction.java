package org.example.sendwise.demo.persistent;

import org.example.sendwise.demo.persistent.auto._Transaction;

import java.time.LocalDateTime;

public class Transaction extends _Transaction {

    private static final long serialVersionUID = 1L;

    @Override
    protected void onPostAdd() {
        setWhen(LocalDateTime.now());
    }
}
