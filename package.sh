mvn clean install

cd target/
mkdir NovartisMetastorePlugin/
mkdir NovartisMetastorePlugin/config
cp -r ../config/ NovartisMetastorePlugin/config/
mv NovartisMetastore-0.1.jar NovartisMetastorePlugin/
