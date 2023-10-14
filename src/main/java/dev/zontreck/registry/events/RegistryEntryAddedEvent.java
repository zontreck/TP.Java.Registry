package dev.zontreck.registry.events;

import dev.zontreck.eventsbus.Cancellable;
import dev.zontreck.eventsbus.Event;
import dev.zontreck.registry.Entry;

/**
 * This event will be removed in a future update, and not all systems implement this.
 */
@Cancellable
@Deprecated(forRemoval = true)
public class RegistryEntryAddedEvent extends Event {
	public Entry newEntry;
	public String entryPath;

	public RegistryEntryAddedEvent(Entry newEntry, String entryPath) {
		this.newEntry = newEntry;
		this.entryPath = entryPath;
	}
}
