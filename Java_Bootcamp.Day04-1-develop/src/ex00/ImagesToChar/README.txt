# delete directory if exists
rm -rf target

# create directory
mkdir target

# compile
javac -d ./target src/java/edu.school21.printer/*/*.java

# run
java -classpath target edu.school21.printer.app.Program . 0 `PATH`
