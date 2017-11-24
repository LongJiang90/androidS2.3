package com.longj.androids23;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

public class ImageLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);

        SimpleDraweeView mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.fresco_image_view);
        SimpleDraweeView mSimpleDraweeView1 = (SimpleDraweeView) findViewById(R.id.fresco_image_view1);
        SimpleDraweeView mSimpleDraweeView2 = (SimpleDraweeView) findViewById(R.id.fresco_image_view2);
        SimpleDraweeView mSimpleDraweeView3 = (SimpleDraweeView) findViewById(R.id.fresco_image_view3);
        final SimpleDraweeView mSimpleDraweeView4 = (SimpleDraweeView) findViewById(R.id.fresco_image_view4);

        //最简单的调用
        Uri uri = Uri.parse("http://upload-images.jianshu.io/upload_images/1714291-09e6f32b31e1b55c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700");
//        mSimpleDraweeView.setImageURI(uri);

        //使用DraweeController显示
        ControllerListener listener = new BaseControllerListener();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setOldController(mSimpleDraweeView.getController())
                .setControllerListener(listener)
                .build();
        mSimpleDraweeView.setController(controller);

        //自定义加载效果
        Drawable backgroundD = getDrawable(R.drawable.ok_line);
        Drawable overlayD = getDrawable(R.drawable.bg_line);

        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(3000)
                .setBackground(backgroundD)
//                .setOverlay(overlayD)
//                .setProgressBarImage(new ProgressBarDrawable())
                .setRoundingParams(new RoundingParams().setCornersRadius(100))
                .setPressedStateOverlay(overlayD)
                .build();

        mSimpleDraweeView1.setImageURI(uri);
        mSimpleDraweeView1.setHierarchy(hierarchy);

        //加载Gif
        Uri gifUrl = Uri.parse("http://upload-images.jianshu.io/upload_images/7560827-2b631ae072078180.gif?imageMogr2/auto-orient/strip");

        //可以监控图片是否加载完成
        ControllerListener controListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {

                if (imageInfo == null) { return; }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                FLog.d("图片信息：Size %d x %d","Quality level %d, good enough: %s, full quality: %s"
                        ,imageInfo.getWidth(),imageInfo.getHeight(),qualityInfo.getQuality(), qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality());


                if (animatable != null) {
                    Toast.makeText(ImageLoadActivity.this,"Gif加载成功", 3);
                }
                super.onFinalImageSet(id, imageInfo, animatable);
            }

            @Override
            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

                super.onIntermediateImageSet(id, imageInfo);
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                FLog.e(getClass(), throwable, "Error loading %s", id);
                super.onFailure(id, throwable);
            }
        };

        DraweeController dController = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controListener)
                .setUri(gifUrl)
                .setAutoPlayAnimations(true)
                .build();
        mSimpleDraweeView2.setController(dController);

        //可以对动画灵活控制 操作开始结束
//        Animatable animatable = mSimpleDraweeView2.getController().getAnimatable();
//        if (animatable != null) {
//            animatable.start();
//
//            animatable.stop();
//        }


        //自动旋转图片 图片呈现方向和设备屏幕方向一致
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setRotationOptions(RotationOptions.autoRotate())
                .build();
        PipelineDraweeController pipController = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(mSimpleDraweeView3.getController())
                .setImageRequest(request)
                .build();
        mSimpleDraweeView3.setController(pipController);

        //给图片加上网格
        Postprocessor redMeshPost = new BasePostprocessor() {
            @Override
            public String getName() {
                return "redMeshPost";
            }

            @Override
            public void process(Bitmap bitmap) {

                for (int x = 0; x < bitmap.getWidth(); x+=2) {
                    for (int y = 0; y < bitmap.getHeight(); y+=2) {
                        bitmap.setPixel(x, y, Color.WHITE);
                    }
                }

            }
        };

        //ImageRequest 的属性和成员
          //uri - 唯一的必选的成员. 参考 支持的URIs
          //autoRotateEnabled - 是否支持自动旋转.
          //progressiveEnabled - 是否支持渐进式加载.
          //postprocessor - 后处理器(postprocess).
          //resizeOptions - 图片缩放选项
        ImageRequest request1 = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(redMeshPost)
                .build();
        PipelineDraweeController pipController1 = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request1)
                .setOldController(mSimpleDraweeView4.getController())
                .build();
        mSimpleDraweeView4.setController(pipController1);


    }




    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

}
