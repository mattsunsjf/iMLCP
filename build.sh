echo "Cleaning the previous built files..."
#echo "rm -r target deliverable"
rm -r target deliverable

echo "Building the package the project..."
#echo "mvn package"
mvn package

echo "Creating deliverable directories"
#echo "mkdir deliverable" 
mkdir deliverable

#echo "mkdir deliverable/lib"
mkdir deliverable/lib

#echo "mkdir deliverable/bin"
mkdir deliverable/bin

echo "Copying dependency jar files..."
#echo "cp target/imlcp-*.jar deliverable/lib"
cp target/imlcp-*.jar deliverable/lib

#echo "cp target/jlineplus-*.jar deliverable/lib"
cp target/jlineplus-*.jar deliverable/lib

echo "Copying binary script..."
#echo "cp src/main/imlcp.sh deliverable/bin"
cp src/main/imlcp.sh deliverable/bin

