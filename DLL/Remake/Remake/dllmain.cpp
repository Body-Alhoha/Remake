#include <Windows.h>
#include <jvmti.h>
#include "fr_bodyalhoha_remake_NativeManager.h"
#include <iostream>
#include <algorithm>


JNIEnv* env = 0;
jvmtiEnv* jvmti = 0;
const char* to_remap = "";

static void JNICALL
ClassFileLoadHook(jvmtiEnv* jvmti, JNIEnv* jni, jclass class_being_redefined, jobject loader, const char* name, jobject protection_domain, jint class_data_len, const unsigned char* class_data, jint* new_class_data_len, unsigned char** new_class_data) {
    if (name != nullptr) {
        std::string sName(to_remap);
        std::replace(sName.begin(), sName.end(), '.', '/');
        if (strcmp(name, sName.c_str()) == 0) {
            to_remap = "";
            jclass remake = env->FindClass("fr/bodyalhoha/remake/Remake");
            jmethodID remakeMID = env->GetStaticMethodID(remake, "remake", "(Ljava/lang/String;[B)[B");
            jstring str = env->NewStringUTF(name);
            jbyteArray classfileBuffer = jni->NewByteArray(class_data_len);
            env->SetByteArrayRegion(classfileBuffer, 0, class_data_len, (signed char*)class_data);
            jbyteArray result = (jbyteArray)env->CallStaticObjectMethod(remake, remakeMID, str, classfileBuffer);
            if (result == nullptr) {
                return;
            }
            jint length = jni->GetArrayLength(result);
            if (length == 0) {
                return;
            }
            *new_class_data_len = length;
            *new_class_data = (unsigned char*)jni->GetByteArrayElements(result, 0);
        }
    }

}

void set_env(JNIEnv* genv) {
    env = genv;
    JavaVM* vm;
    JNI_GetCreatedJavaVMs(&vm, 1, nullptr);
    vm->GetEnv(reinterpret_cast<void**>(&jvmti), JVMTI_VERSION_1_0);
    static jvmtiCapabilities capa;
    (void)memset(&capa, 0, sizeof(jvmtiCapabilities));
    capa.can_retransform_classes = 1;
    jvmti->AddCapabilities(&capa);
    jvmtiEventCallbacks callbacks = { 0 };
    callbacks.ClassFileLoadHook = ClassFileLoadHook;
    jvmti->SetEventCallbacks(&callbacks, sizeof(callbacks));
    jvmti->SetEventNotificationMode(JVMTI_ENABLE,
        JVMTI_EVENT_CLASS_FILE_LOAD_HOOK,
        (jthread)NULL);


}
const char* get_class_name(JNIEnv* env, jclass klass) {
    jclass cls = env->FindClass("java/lang/Class");
    jmethodID mid_getName = env->GetMethodID(cls, "getName", "()Ljava/lang/String;");
    jstring jname = (jstring)env->CallObjectMethod(klass, mid_getName);
    const char* name = env->GetStringUTFChars(jname, 0);
    env->ReleaseStringUTFChars(jname, name);
    return name;
}

JNIEXPORT void JNICALL Java_fr_bodyalhoha_remake_NativeManager_remake
(JNIEnv* genv, jclass from, jclass klass) {
    if (env == 0) 
        set_env(genv);
    
    const char* name = get_class_name(env, klass);
    to_remap = name;
    jvmti->RetransformClasses(1, &klass);
        

}


BOOL APIENTRY DllMain(HMODULE hModule, DWORD reason_for_call, LPVOID lpReserved)
{
    return TRUE;
}

