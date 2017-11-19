package com.abishov.hexocat.common.picasso;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public final class PicassoServiceLocator {
    private static String SERVICE_PICASSO = "service:picasso";
    private static String SERVICE_PICASSO_TRANSFORMATION = "service:picassoTransformation";

    private PicassoServiceLocator() {
        throw new AssertionError();
    }

    public static boolean matchesService(String name) {
        return SERVICE_PICASSO.equals(name);
    }

    public static boolean matchesTransformationService(String name) {
        return SERVICE_PICASSO_TRANSFORMATION.equals(name);
    }

    @SuppressWarnings({"ResourceType", "WrongConstant"})
    public static Picasso obtain(Context context) {
        return (Picasso) context.getSystemService(SERVICE_PICASSO);
    }

    @SuppressWarnings({"ResourceType", "WrongConstant"})
    public static Transformation obtainTransformation(Context context) {
        return (Transformation) context.getSystemService(SERVICE_PICASSO_TRANSFORMATION);
    }
}
