cd %curdir%
rmdir /s/q SuperPhone\bin\
rmdir /s/q SuperPhone\assets\
rmdir /s/q SuperPhone\libs\
rmdir /s/q SuperPhone\res\xml\
rmdir /s/q SuperPhone\src\
rmdir /s/q APP_ICO_BAK
::删除文件夹

del SuperPhone\project.properties
del SuperPhone\proguard-project.txt
del SuperPhone\local.properties
del SuperPhone\build.xml
del SuperPhone\res\drawable-hdpi\splash.png
::删除创建过程中生成文件

cd %curdir%
del %1-debug-unaligned.apk
del %1-release-unsigned.apk

move %1-debug.apk %2
move %1-release.apk %2