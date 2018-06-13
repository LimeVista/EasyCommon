package me.limeice.common.function;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import me.limeice.common.function.CloseUtils;

/**
 * Android internals have been modified to store images in the media folder with
 * the correct date meta data
 * 基于 Android 兼容库代码修改
 */
@SuppressWarnings("FinalStaticMethod")
public class GalleryUtils {

    public static final String TAG = "GalleryUtils";

    private static final String TYPE_JPG = "image/jpeg";

    private static final String TYPE_PNG = "image/png";

    private static final String TYPE_WEBP = "image/webp";

    /**
     * Insert an image and create a thumbnail for it.
     *
     * @param cr          The content resolver to use
     * @param imagePath   The path to the image to insert
     * @param name        The name of the image
     * @param description The description of the image
     * @return The URL to the newly created image
     * @throws FileNotFoundException about IO,{@link FileNotFoundException}
     */
    @Nullable
    public static final String insertImage(ContentResolver cr, String imagePath,
                                           String name, String description) throws FileNotFoundException {
        // Check if file exists with a FileInputStream
        FileInputStream stream = new FileInputStream(imagePath);
        try {
            Bitmap bm = BitmapFactory.decodeFile(imagePath);
            if (bm == null)
                return null;
            String ret = insertImage(cr, bm, name, description, Bitmap.CompressFormat.JPEG, 60);
            bm.recycle();
            return ret;
        } finally {
            CloseUtils.closeIOQuietly(stream);
        }
    }

    /**
     * Insert an image and create a thumbnail for it.
     *
     * @param cr          The content resolver to use
     * @param image       The image of the image data
     * @param name        The name of the image
     * @param description The description of the image
     * @return The URL to the newly created image
     */
    @Nullable
    public static final String insertImage(ContentResolver cr, Bitmap image,
                                           String name, String description) {
        return insertImage(cr, image, name, description, Bitmap.CompressFormat.JPEG, 60);
    }

    /**
     * Insert an image and create a thumbnail for it.
     *
     * @param cr          The content resolver to use
     * @param image       The image of the image data
     * @param name        The name of the image
     * @param description The description of the image
     * @return The URL to the newly created image
     */
    @Nullable
    public static final String insertImagePNG(ContentResolver cr, Bitmap image,
                                              String name, String description) {
        return insertImage(cr, image, name, description, Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * Insert an image and create a thumbnail for it.
     *
     * @param cr          The content resolver to use
     * @param source      The stream to use for the image
     * @param title       The name of the image
     * @param description The description of the image
     * @param format      CompressFormat {@link Bitmap.CompressFormat}
     * @param quality     Image compress quality
     * @return The URL to the newly created image, or <code>null</code> if the image failed to be stored
     * for any reason.
     */
    @Nullable
    public static final String insertImage(ContentResolver cr, Bitmap source,
                                           String title, String description,
                                           Bitmap.CompressFormat format, int quality) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, getMineType(format));

        Uri url = null;

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (source != null) {
                //noinspection ConstantConditions
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    source.compress(format, quality, imageOut);
                } finally {
                    CloseUtils.closeIOQuietly(imageOut);
                }

                long id = ContentUris.parseId(url);
                // Wait until MINI_KIND thumbnail is generated.
                Bitmap miniThumb = MediaStore.Images.Thumbnails.getThumbnail(cr, id,
                        MediaStore.Images.Thumbnails.MINI_KIND, null);
                // This is for backward compatibility.
                storeThumbnail(cr, miniThumb, id, MediaStore.Images.Thumbnails.MICRO_KIND);
            } else {
                Log.e(TAG, "Failed to create thumbnail, removing original");
                if (url != null)
                    cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to insert image", e);
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }
        return url == null ? null : url.toString();
    }


    private static void storeThumbnail(ContentResolver cr, Bitmap source, long id, int kind) {
        // create the matrix to scale it
        Matrix matrix = new Matrix();

        float scaleX = 75F / source.getWidth();
        float scaleY = 75F / source.getHeight();

        matrix.setScale(scaleX, scaleY);

        Bitmap thumb = Bitmap.createBitmap(
                source, 0, 0,
                source.getWidth(),
                source.getHeight(),
                matrix, true
        );

        ContentValues values = new ContentValues(4);
        values.put(MediaStore.Images.Thumbnails.KIND, kind);
        values.put(MediaStore.Images.Thumbnails.IMAGE_ID, (int) id);
        values.put(MediaStore.Images.Thumbnails.HEIGHT, thumb.getHeight());
        values.put(MediaStore.Images.Thumbnails.WIDTH, thumb.getWidth());

        Uri url = cr.insert(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, values);
        if (url == null) return;
        try {
            OutputStream thumbOut = cr.openOutputStream(url);
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
            CloseUtils.closeIOQuietly(thumbOut);
        } catch (FileNotFoundException ex) {
            //
        }
    }

    private static String getMineType(Bitmap.CompressFormat format) {
        switch (format) {
            case PNG:
                return TYPE_PNG;
            case JPEG:
                return TYPE_JPG;
            case WEBP:
                return TYPE_WEBP;
        }
        return TYPE_JPG;
    }
}
