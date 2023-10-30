# typingTrainer
Desktop program runs on Windows.
If you are interested in training your typing ability and speed, with this app you can do it.
Most interesting here is that you can open any text files on your computer and type text simultaneously while reading it.
For example open a book and type text from it. When you are finished, close app and your progress is saved for next time.

Run with a following command (Java Runtime Edition should be installed):
**java -Xmx100m -jar "typingTrainer-1.0.jar"**
or
**java --add-opens java.base/java.lang=ALL-UNNAMED -jar "./target/typingTrainer-1.0.jar"**


If error appears:

*Exception in thread "main" java.lang.IllegalStateException: Cannot load configuration class: roksard.typingTrainer.Main*

*... module java.base does not "opens java.lang" ...*

Then add VM-Options:

**--add-opens java.base/java.lang=ALL-UNNAMED**