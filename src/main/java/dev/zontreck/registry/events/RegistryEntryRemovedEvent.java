package dev.zontreck.registry.events;

import dev.zontreck.eventsbus.Cancellable;
import dev.zontreck.eventsbus.Event;
import dev.zontreck.registry.Entry;

/**
 * This event will be removed in a future update, and not all systems implement
 * this.
 */
@Cancellable
@Deprecated(forRemoval = true)
public class RegistryEntryRemovedEvent extends Event {
	public Entry newEntry;
	public String oldEntryPath;
	public Entry oldParent;

	public RegistryEntryRemovedEvent(Entry newEntry, String entryPath, Entry oldParent) {
		this.newEntry = newEntry;
		this.oldEntryPath = entryPath;
		this.oldParent = oldParent;
	}
}
