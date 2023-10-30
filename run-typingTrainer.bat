java --add-opens java.base/java.lang=ALL-UNNAMED -Dlogging.level.org.springframework=d -jar "./target/typingTrainer-1.0.jar" 
pause

::_JAVA_OPTIONS=-Dlogging.level.org.springframework=DEBUG
::--add-opens java.base/java.lang=ALL-UNNAMED