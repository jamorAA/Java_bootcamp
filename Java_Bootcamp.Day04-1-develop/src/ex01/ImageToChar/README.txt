# delete directory if exists
rm -rf target

# create directory
mkdir target

# compile
javac -d ./target src/java/edu.school21.printer/*/*.java

# copy resources
cp -R src/resources target/.

# create archive
jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target

# run
java -jar target/images-to-chars-printer.jar . 0
