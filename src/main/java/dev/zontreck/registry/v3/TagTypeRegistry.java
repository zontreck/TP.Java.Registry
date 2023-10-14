package dev.zontreck.registry.v3;

import java.util.HashMap;
import java.util.Map;

public class TagTypeRegistry
{
	private static Map<Type, Class<? extends Tag>> types = new HashMap<>();

	public static void RegisterType(Type T, Class<? extends Tag> clazz)
	{
		types.put(T, clazz);
	}

	public static Class<? extends Tag> getByType(Type T)
	{
		if(types.containsKey(T))return types.get(T);
		return null;
	}
}
