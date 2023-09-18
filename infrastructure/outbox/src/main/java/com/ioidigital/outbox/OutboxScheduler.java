package com.ioidigital.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}
