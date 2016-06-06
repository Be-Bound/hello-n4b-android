# N4B SDK
-keep class com.bebound.engine.model.** { *; }
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class com.bebound.common.model.ValueMap { *; }
-keep class ** {
    public !static com.bebound.common.model.ValueMap toValueMap();
    public static java.lang.Object fromValueMap(com.bebound.common.model.ValueMap);
}

# EventBus
-keepclassmembers class ** {
    public void onEvent*(**);
}

-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keepclassmembers,includedescriptorclasses class ** { public void onEvent*(**); }