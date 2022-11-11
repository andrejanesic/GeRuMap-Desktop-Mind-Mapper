package rs.edu.raf.dsw.rudok.app.observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * For classes that want to listen to all instances of {@link IPublisher} for a specific type of {@link IMessage}.
 */
public class IPublisherGlobal extends IObserver {

    private static Map<Class, Set<IObserver>> observers = new HashMap<>();

    /**
     * Returns the global observers for the given message type.
     *
     * @param type {@link IMessage} class type.
     * @return {@link Set} of {@link IObserver}s, if any.
     */
    public static Set<IObserver> getObserversGlobal(Class type) {
        return observers.getOrDefault(type, new HashSet<>());
    }

    /**
     * Adds a new global observer.
     *
     * @param observer {@link IObserver}
     */
    public static void addObserverGlobal(Class type, IObserver observer) {
        if (type == null) return;
        if (observer == null) return;
        Set<IObserver> obs = observers.getOrDefault(type, null);
        if (obs == null) {
            obs = new HashSet<>();
            obs.add(observer);
            observers.put(type, obs);
        } else {
            if (obs.contains(observer)) return;
            obs.add(observer);
        }
    }

    /**
     * Removes a global observer.
     *
     * @param observer {@link IObserver}
     */
    public static void removeObserverGlobal(Class type, IObserver observer) {
        if (type == null) return;
        if (observer == null) return;
        Set<IObserver> obs = observers.getOrDefault(type, null);
        if (obs == null) {
            return;
        }
        if (!obs.contains(observer)) return;
        obs.remove(observer);
    }
}
