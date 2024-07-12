# delete directory if exists
rm -rf target && rm -rf lib

# create directory
mkdir target && mkdir lib

# download libs
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar
curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar -o lib/JCDP-4.0.0.jar

# add libs to the target
cd ./target
jar xf ../lib/JCDP-4.0.0.jar/
jar xf ../lib/jcommander-1.72.jar
cd ..

# copy resources
cp -R src/resources target/.

# compile
javac -d target -sourcepath src/java -cp lib/\* src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/*.java

# create archive
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

# run
java -jar target/images-to-chars-printer.jar