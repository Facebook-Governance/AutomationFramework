SET port=%1
SET BatchDirectory=%~dp0
echo Batch directory path is %BatchDirectory%
cd %BatchDirectory%
java -jar selenium-server-standalone-2.53.0.jar -port %port% -Dwebdriver.chrome.driver=%2