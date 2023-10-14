package dev.zontreck.registry.events;

import dev.zontreck.eventsbus.Event;
import dev.zontreck.registry.Key;

public class RegistrySavedEvent extends Event {
    public Key root;
    public String filename;

    public RegistrySavedEvent(Key root, String filename) {
        this.root = root;
        this.filename = filename;
    }
}