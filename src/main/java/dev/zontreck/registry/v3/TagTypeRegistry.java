package dev.zontreck.registry.v3;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TagTypeRegistry {
	private static Map<Type, Class<? extends Tag>> types = new HashMap<>();

	public static void RegisterType(Type T, Class<? extends Tag> clazz) {
		types.put(T, clazz);
	}

	public static Class<? extends Tag> getByType(Type T) {
		if (types.containsKey(T))
			return types.get(T);
		return null;
	}

	public static Tag getInstanceOf(Type type) {

		Class<? extends Tag> base = TagTypeRegistry.getByType(type);
		try {
			Tag instance = base.getConstructor().newInstance();

			return instance;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
}
