find $PATH_TO_FX/{javafx.base.jar,javafx.graphics.jar,javafx.controls.jar} -exec unzip -nq {} -d out \;

#uncomment for Linux:
#cp $PATH_TO_FX/{libprism*.so,libjavafx*.so,libglass*.so,libdecora_sse.so} out

#uncomment for Mac:
#cp $PATH_TO_FX/{libprism*.dylib,libjavafx*.dylib,libglass.dylib,libdecora_sse.dylib} out

rm out/META-INF/MANIFEST.MF out/module-info.class
mkdir libs
jar --create --file=libs/hellofx.jar --main-class=com.example.cryptojavafx.HelloApplication -C out .
java -jar libs/hellofx.jar 
