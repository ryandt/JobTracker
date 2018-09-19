package com.nerdery.rtaza.jobtracker.log

class LogUtil {

    companion object {

        /**
         * Returns the simple class name of [objectInstance].
         */
        fun getLogTag(objectInstance: Any): String {
            val clazz = objectInstance::class
            return String.format("%s{%s}", clazz.simpleName, Integer.toHexString(clazz.hashCode()))
        }
    }
}