package com.duing.reactor.masterSlave;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class HandlerLoop implements Runnable {

    private Selector selector;

    public HandlerLoop(Selector selector) {
        this.selector = selector;
    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                int select = selector.select();
                if (select != 0) {
                    Set<SelectionKey> readKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = readKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        Runnable runnable = (Runnable) key.attachment();
                        runnable.run();
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
