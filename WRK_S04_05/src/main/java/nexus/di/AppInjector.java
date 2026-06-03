/**********************************************
 Workshop 4&5
 Course: APD545 - Semester Winter 2026
 Last Name: Mahfuz
 First Name: Abdullah Al
 ID:180377236
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature: Abdullah Al Mahfuz
 Date: 24 Feb 2026
 **********************************************/
package nexus.di;

import com.airhacks.afterburner.injection.Injector;
import javafx.util.Callback;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AppInjector {
    private static final Map<Class<?>, Object> SINGLETONS = new ConcurrentHashMap<>();

    private AppInjector() {
    }

    public static void configure() {
        Injector.setInstanceSupplier(type -> SINGLETONS.computeIfAbsent(type, AppInjector::newInstance));
    }

    public static Callback<Class<?>, Object> controllerFactory() {
        return Injector::instantiateModelOrService;
    }

    private static Object newInstance(Class<?> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create DI instance for " + type.getName(), e);
        }
    }
}
