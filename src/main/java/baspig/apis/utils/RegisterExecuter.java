package baspig.apis.utils;

import baspig.apis.utils.util.BP;

import java.util.*;

@SuppressWarnings("unused")
class RegisterExecuter{
    private static final Map<String, List<Class<? extends RegisterExecuter>>> MOD_REGISTRY_CLASSES = new HashMap<>();
    private static final Set<Class<? extends RegisterExecuter>> INITIALIZED_CLASSES = new HashSet<>();
    private static final Set<String> INITIALIZED_MODS = new HashSet<>();

    static {
        initializeStatics(null);
    }

    private final String modId;

    protected RegisterExecuter(String modId) {
        this.modId = modId;

        registerClass(modId, this.getClass());

        if (INITIALIZED_CLASSES.add(this.getClass())) {
            registerThis();
        }
    }

    protected RegisterExecuter() {
        this(null);
    }

    public void registerThis() {
        /// Pls user, override this :(
    }

    public static <T extends RegisterExecuter> void register(String modId, Class<T> clazz) {
        registerClass(modId, clazz);
    }

    private static <T extends RegisterExecuter> void registerClass(String modId, Class<T> clazz) {

        List<Class<? extends RegisterExecuter>> classes = MOD_REGISTRY_CLASSES
                .computeIfAbsent(modId == null ? "unknown" : modId, k -> new ArrayList<>());
        if (!classes.contains(clazz)) {
            classes.add(clazz);
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
            } catch (ClassNotFoundException e) {
                BP.LOG.error("[Mod: {}] Failed to load class: {}", modId, clazz.getName());
            }
        }
    }

    public static void initializeAll(String modId) {
        if (INITIALIZED_MODS.contains(modId)) {
            BP.LOG.debug("[Mod: {}] Registration already initialized, skipping", modId);
            return;
        }

        INITIALIZED_MODS.add(modId);

        List<Class<? extends RegisterExecuter>> classes = MOD_REGISTRY_CLASSES.get(modId);
        if (classes == null) {
            BP.LOG.warn("[Mod: {}] No register classes found", modId);
            return;
        }

        BP.LOG.info("[Mod: {}] Initializing {} register classes", modId, classes.size());

        for (Class<? extends RegisterExecuter> clazz : classes) {
            try {
                clazz.getDeclaredConstructor(String.class).newInstance(modId);
            } catch (NoSuchMethodException e) {
                try {
                    clazz.getDeclaredConstructor().newInstance();
                } catch (Exception innerE) {
                    BP.LOG.error("[Mod: {}] Failed to initialize registry class: {}",
                            modId, clazz.getName(), innerE);
                }
            } catch (Exception e) {
                BP.LOG.error("[Mod: {}] Failed to initialize registry class: {}",
                        modId, clazz.getName(), e);
            }
        }
    }

    private static void initializeAll() {
        for (String modId : MOD_REGISTRY_CLASSES.keySet()) {
            if (!INITIALIZED_MODS.contains(modId)) {
                initializeAll(modId);
            }
        }
    }

    public static void initializeStatics(String modId) {
        if (modId != null) {
            BP.LOG.debug("[Mod: {}] Initializing statics", modId);
        }
    }
}
