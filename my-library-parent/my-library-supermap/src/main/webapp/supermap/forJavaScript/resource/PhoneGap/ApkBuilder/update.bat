@set curdir=%cd%
cd %curdir%
md SuperPhone\bin\
md SuperPhone\assets\www\
md SuperPhone\libs\
md SuperPhone\res\xml\
md SuperPhone\src\com\supermap\javascript

md APP_ICO_BAK\drawable-hdpi
md APP_ICO_BAK\drawable-ldpi
md APP_ICO_BAK\drawable-mdpi
md APP_ICO_BAK\drawable-xhdpi
::生成文件夹

Xcopy ..\libs %curdir%\SuperPhone\libs  /e /y

Xcopy ..\res\xml %curdir%\SuperPhone\res\xml  /e /y

copy ..\res\splash.png %curdir%\SuperPhone\res\drawable-hdpi /y

Xcopy ..\src\com\supermap\javascript %curdir%\SuperPhone\src\com\supermap\javascript /e /y

Xcopy %2 SuperPhone\assets\www /e /y
cd %curdir%/SuperPhone/
android update project --name %customName% -t %1 -p ./
::android update project --name <project_name> --target <target_ID> --path <path_to_your_project>