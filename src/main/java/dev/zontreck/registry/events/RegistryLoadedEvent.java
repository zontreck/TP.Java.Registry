package dev.zontreck.registry.events;

import dev.zontreck.eventsbus.Event;
import dev.zontreck.registry.Key;

public class RegistryLoadedEvent extends Event {
	public Key root;

	public RegistryLoadedEvent(Key root) {
		this.root = root;
	}
}
