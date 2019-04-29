/*
 * Copyright 2019 ZHENG BAO LE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kotlin.me.zbl.concurrency;

import java.util.Random;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-29
 */
public class MessageBox {

    private String message;
    private boolean empty = true;

    private synchronized String take() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        empty = true;
        notifyAll();
        return message;
    }

    private synchronized void put(String message) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.message = message;
        empty = false;
        notifyAll();
    }

    public static void main(String[] args) {
        MessageBox box = new MessageBox();
        String[] messages = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };
        Thread producer = new Thread(() -> {
            for (String message : messages) {
                box.put(message);
                System.out.format("producer put message: [%s]\n", message);
                try {
                    Thread.sleep(new Random().nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            box.put("DONE");
        });
        Thread consumer = new Thread(() -> {
            String message;
            while (!(message = box.take()).equals("DONE")) {
                System.out.format("consumer get message: [%s]\n", message);
            }
        });
        consumer.start();
        producer.start();
    }
}
