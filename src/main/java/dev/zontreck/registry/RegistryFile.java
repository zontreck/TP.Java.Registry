package dev.zontreck.registry;

import dev.zontreck.registry.v3.Tag;

import java.util.HashMap;
import java.util.Map;

public class RegistryFile {
	public Header RegistryHeader;
	public Map<String, Tag> Tags = new HashMap<>();
}
